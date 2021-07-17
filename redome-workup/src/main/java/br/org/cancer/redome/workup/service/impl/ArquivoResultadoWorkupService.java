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

import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.exception.ValidationException;
import br.org.cancer.redome.workup.model.ArquivoResultadoWorkup;
import br.org.cancer.redome.workup.model.Configuracao;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.model.ResultadoWorkup;
import br.org.cancer.redome.workup.persistence.IArquivoResultadoWorkupRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IArquivoResultadoWorkupService;
import br.org.cancer.redome.workup.service.IConfiguracaoService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IResultadoWorkupService;
import br.org.cancer.redome.workup.service.IStorageService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.ArquivoUtil;
import br.org.cancer.redome.workup.util.CampoMensagem;

/**
 * Classe de funcionalidades envolvendo a entidade ArquivoResultadoWorkup.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ArquivoResultadoWorkupService extends AbstractService<ArquivoResultadoWorkup, Long> implements IArquivoResultadoWorkupService {

	@Autowired
	private IArquivoResultadoWorkupRepository arquivoResultadoWorkupRepository;
	
	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	
	@Autowired
	private IResultadoWorkupService resultadoWorkupService;
	
	@Autowired
	private IConfiguracaoService configuracaoService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;
	
	@Override
	public IRepository<ArquivoResultadoWorkup, Long> getRepository() {
		return arquivoResultadoWorkupRepository;
	}

	@Override
	public String enviarArquivoResultadoWorkupParaStorage(Long idPedidoWorkup, MultipartFile arquivo) {
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkupEmAberto(idPedidoWorkup);
		ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupPeloPedidoWorkupId(pedidoWorkup.getId());
		if (resultadoWorkup != null) {
			throw new BusinessException("erro.resultado.workup.ja.cadastrado");
		}
		validarArquivo(arquivo);
		
		return enviarArquivoParaStorage(pedidoWorkup.getId(), arquivo);
	}

	private String enviarArquivoParaStorage(Long idPedidoWorkup, MultipartFile arquivo) {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		
		String diretorio = ArquivoUtil.obterDiretorioArquivosResultadoWorkup(idPedidoWorkup);
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
		
		List<Configuracao> configuracoes = configuracaoService.listarConfiguracao(Configuracao.EXTENSAO_ARQUIVO_RESULTADO_WORKUP, Configuracao.TAMANHO_ARQUIVO_RESULTADO_WORKUP);
		ArquivoUtil.validarArquivo(arquivo, resultadoValidacao, messageSource, configuracoes.get(0), configuracoes.get(1));
		
		if(!resultadoValidacao.isEmpty()){
			throw new ValidationException("erro.resultado.workup.arquivo_invalido", resultadoValidacao);
		}		
	}
	
	@Override
	public void excluirArquivoResultadoWorkupDoStorage(Long idPedidoWorkup, String caminho) {
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkupEmAberto(idPedidoWorkup);
		ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupPeloPedidoWorkupId(pedidoWorkup.getId());
		if (resultadoWorkup != null) {
			throw new BusinessException("erro.resultado.workup.ja.cadastrado");
		}		
		
		if (StringUtils.isBlank(caminho)) {
			throw new BusinessException("erro.resultado.workup.caminho.arquivo_obrigatorio");
		}
		
		storageService.removerArquivo(caminho);
		
	}
	
	@Override
	public File obterArquivoDoStorage(Long id) {
		ArquivoResultadoWorkup arquivo = arquivoResultadoWorkupRepository.findById(id).orElseThrow( () -> new BusinessException(""));
		return obterArquivoStorage(arquivo);
	}

	private File obterArquivoStorage(ArquivoResultadoWorkup arquivo) {
		if (arquivo == null) {
			throw new BusinessException("arquivo.prescricao.nao.encontrado");
		}
		return storageService.download(
				ArquivoUtil.obterNomeArquivoComTimestampPorCaminhoArquivo(arquivo.getCaminho()),
				ArquivoUtil.obterDiretorioArquivosResultadoWorkup(arquivo.getResultadoWorkup().getPedidoWorkup().getId()));
	}
	
}
