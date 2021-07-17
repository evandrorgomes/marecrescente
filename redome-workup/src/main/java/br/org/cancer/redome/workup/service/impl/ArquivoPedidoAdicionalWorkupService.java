package br.org.cancer.redome.workup.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.cloud.objectstorage.services.s3.model.S3ObjectSummary;

import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.exception.ValidationException;
import br.org.cancer.redome.workup.model.ArquivoPedidoAdicionalWorkup;
import br.org.cancer.redome.workup.model.Configuracao;
import br.org.cancer.redome.workup.model.PedidoAdicionalWorkup;
import br.org.cancer.redome.workup.persistence.IArquivoPedidoAdicionalWorkupRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IArquivoPedidoAdicionalWorkupService;
import br.org.cancer.redome.workup.service.IConfiguracaoService;
import br.org.cancer.redome.workup.service.IPedidoAdicionalWorkupService;
import br.org.cancer.redome.workup.service.IStorageService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.ArquivoUtil;
import br.org.cancer.redome.workup.util.CampoMensagem;

/**
 * Classe de funcionalidades envolvendo a entidade ArquivoPedidoAdicionalWorkup.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class ArquivoPedidoAdicionalWorkupService extends AbstractService<ArquivoPedidoAdicionalWorkup, Long> implements IArquivoPedidoAdicionalWorkupService {

	@Autowired
	private IArquivoPedidoAdicionalWorkupRepository arquivoPedidoAdicionalWorkupRepository;

	@Autowired
	private IPedidoAdicionalWorkupService pedidoAdicionalWorkupService;
	
	@Autowired
	private IConfiguracaoService configuracaoService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;
	
	@Override
	public IRepository<ArquivoPedidoAdicionalWorkup, Long> getRepository() {
		return arquivoPedidoAdicionalWorkupRepository;
	}
	
	@Override
	public List<ArquivoPedidoAdicionalWorkup> salvarArquivosExamesAdicionaisWorkup(Long idPedidoAdicional, List<ArquivoPedidoAdicionalWorkup> listaNovaDeArquivosAdicionais) {

		this.excluirArquivosExamesAdicionais(idPedidoAdicional, listaNovaDeArquivosAdicionais);
		
		this.arquivoPedidoAdicionalWorkupRepository.saveAll(listaNovaDeArquivosAdicionais);

		return this.listarArquivosExamesAdicionais(idPedidoAdicional);
	}
	
	@Override
	public String enviarArquivoPedidoAdicionalWorkupParaStorage(Long idPedidoAdicional, MultipartFile arquivo) {
		
		PedidoAdicionalWorkup pedidoAdicional = pedidoAdicionalWorkupService.obterPedidoAdicionalWorkup(idPedidoAdicional);

		validarArquivo(arquivo);
		
		return enviarArquivoParaStorage(pedidoAdicional.getId(), arquivo);
	}
	
	@Override
	public void excluirArquivoExameAdicionalWorkupDoStorage(Long idArquivoAdicional, String caminho) {

		if (StringUtils.isBlank(caminho)) {
			throw new BusinessException("erro.pedido.adicional.workup.caminho.arquivo_obrigatorio");
		}

		storageService.removerArquivo(caminho);
		
		if(idArquivoAdicional != null) {
			this.arquivoPedidoAdicionalWorkupRepository.deleteById(idArquivoAdicional);
		}
	}
	
	@Override
	public List<ArquivoPedidoAdicionalWorkup> listarArquivosExamesAdicionais(Long idPedidoAdicional) {
		if(idPedidoAdicional == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return arquivoPedidoAdicionalWorkupRepository.findByPedidoAdicional(idPedidoAdicional);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArquivoPedidoAdicionalWorkup> listarArquivosStorage(Long idPedidoAdicional) {
		
		List<ArquivoPedidoAdicionalWorkup> arquivosExamesAdicionais = new ArrayList<>();
		String diretorio = ArquivoUtil.obterDiretorioArquivosExamesAdicionaisWorkup(idPedidoAdicional);		
		List<S3ObjectSummary> arquivos = storageService.localizarArquivosPorDiretorio(diretorio);
		arquivos.forEach(arquivoNoStorage -> {
			ArquivoPedidoAdicionalWorkup arquivo = ArquivoPedidoAdicionalWorkup.builder()
					.caminho(arquivoNoStorage.getKey())
					.build();
			arquivosExamesAdicionais.add(arquivo);
		});	
		return arquivosExamesAdicionais;
	}
	
	@Override
	public File obterArquivoDoStorage(Long idArquivoAdicional) {
		ArquivoPedidoAdicionalWorkup arquivo = this.arquivoPedidoAdicionalWorkupRepository.findById(idArquivoAdicional).orElseThrow( () -> new BusinessException(""));
		return obterArquivoStorage(arquivo);
	}
	
	private void excluirArquivosExamesAdicionais(Long idPedidoAdicional, List<ArquivoPedidoAdicionalWorkup> listaNovaDeArquivosAdicionais) {
		List<ArquivoPedidoAdicionalWorkup> listaExistenteDeArquivosAdicionais = this.listarArquivosExamesAdicionais(idPedidoAdicional);
		listaExistenteDeArquivosAdicionais.forEach(arquivoExistente -> {
			if (listaNovaDeArquivosAdicionais.stream().noneMatch(arquivoNovo -> arquivoExistente.getId().equals(arquivoNovo.getId()))) {
				this.arquivoPedidoAdicionalWorkupRepository.delete(arquivoExistente);
			}
		});
		this.removerArquivosStorageQueNaoEstaoNaLista(idPedidoAdicional, listaNovaDeArquivosAdicionais);
	}
	
	private String enviarArquivoParaStorage(Long idPedidoAdicional, MultipartFile arquivo) {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		
		String diretorio = ArquivoUtil.obterDiretorioArquivosExamesAdicionaisWorkup(idPedidoAdicional);
		String nomeArquivo = ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo);
				
		try {
			storageService.upload(nomeArquivo, diretorio, arquivo);
		}
		catch (IOException e) {
			throw new BusinessException("erro.arquivo.upload", e);
		}
		
		return diretorio + "/" + nomeArquivo;
	}

	private void validarArquivo(MultipartFile arquivo) {
		ArrayList<CampoMensagem> resultadoValidacao = new ArrayList<CampoMensagem>();
		
		List<Configuracao> configuracoes = configuracaoService.listarConfiguracao(Configuracao.EXTENSAO_ARQUIVO_LAUDO, Configuracao.TAMANHO_MAXIMO_ARQUIVO_LAUDO);
		ArquivoUtil.validarArquivo(arquivo, resultadoValidacao, messageSource, configuracoes.get(0), configuracoes.get(1));
		
		if(!resultadoValidacao.isEmpty()){
			throw new ValidationException("erro.pedido.adicional.workup.arquivo_invalido", resultadoValidacao);
		}		
	}
	
	private File obterArquivoStorage(ArquivoPedidoAdicionalWorkup arquivo) {
		if (arquivo == null) {
			throw new BusinessException("arquivo.prescricao.nao.encontrado");
		}
		return storageService.download(
				ArquivoUtil.obterNomeArquivoComTimestampPorCaminhoArquivo(arquivo.getCaminho()),
				ArquivoUtil.obterDiretorioArquivosExamesAdicionaisWorkup(arquivo.getPedidoAdicional()));
	}

	@SuppressWarnings("unchecked")
	private void removerArquivosStorageQueNaoEstaoNaLista(Long idPedidoAdicional, List<ArquivoPedidoAdicionalWorkup> arquivosExamesAdicionais) {
		String diretorio = ArquivoUtil.obterDiretorioArquivosExamesAdicionaisWorkup(idPedidoAdicional);		
		List<S3ObjectSummary> arquivos = storageService.localizarArquivosPorDiretorio(diretorio);
		arquivos.forEach(arquivoNoStorage -> {
			if (arquivosExamesAdicionais.stream().noneMatch(arquivoExameAdicional -> arquivoNoStorage.getKey().equals(arquivoExameAdicional.getCaminho()))) {
				storageService.removerArquivo(arquivoNoStorage.getKey());
			}
		});
	}
	
}
