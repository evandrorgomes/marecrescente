package br.org.cancer.redome.workup.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.exception.ValidationException;
import br.org.cancer.redome.workup.model.ArquivoVoucherLogistica;
import br.org.cancer.redome.workup.model.Configuracao;
import br.org.cancer.redome.workup.model.PedidoLogisticaDoadorColeta;
import br.org.cancer.redome.workup.model.PedidoLogisticaDoadorWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.persistence.IArquivoVoucherLogisticaRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IArquivoVoucherLogisticaService;
import br.org.cancer.redome.workup.service.IConfiguracaoService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaDoadorColetaService;
import br.org.cancer.redome.workup.service.IPedidoLogisticaDoadorWorkupService;
import br.org.cancer.redome.workup.service.IStorageService;
import br.org.cancer.redome.workup.service.IUsuarioService;
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
public class ArquivoVoucherLogisticaService extends AbstractService<ArquivoVoucherLogistica, Long> implements IArquivoVoucherLogisticaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoVoucherLogisticaService.class);

	@Autowired
	private IArquivoVoucherLogisticaRepository arquivoVoucherLogisticaRepository;
	
	@Autowired
	private IPedidoLogisticaDoadorWorkupService pedidoLogisticaDoadorWorkupService;

	@Autowired
	private IPedidoLogisticaDoadorColetaService pedidoLogisticaDoadorColetaService;
	
	@Autowired
	private IConfiguracaoService configuracaoService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;
	
	@Override
	public IRepository<ArquivoVoucherLogistica, Long> getRepository() {
		return arquivoVoucherLogisticaRepository;
	}

	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public String enviarArquivoVoucherLogisticaParaStorage (Long idPedidoLogistica, MultipartFile arquivo) {
		LOGGER.info("Inicio do salvar um novo voucher para o pedido de logistica.");
			
			if (usuarioService.obterUsuarioLogado().getAuthorities().contains(Perfis.ANALISTA_WORKUP.name())) {
				PedidoLogisticaDoadorColeta pedidoLogistica = pedidoLogisticaDoadorColetaService.obterPedidoLogisticaDoadorColetaEmAberto(idPedidoLogistica);
				return enviarArquivoVoucherLogisticaDoadorColetaParaStorage (pedidoLogistica, arquivo);
				
			}else if(usuarioService.obterUsuarioLogado().getAuthorities().contains(Perfis.ANALISTA_WORKUP_INTERNACIONAL.name())) {
				PedidoLogisticaDoadorWorkup pedidoLogistica = pedidoLogisticaDoadorWorkupService.obterPedidoLogisticaEmAberto(idPedidoLogistica);
				return enviarArquivoVoucherLogisticaDoadorWorkupParaStorage (pedidoLogistica, arquivo);
			}
			
			return "";
	}
	
	
	public String enviarArquivoVoucherLogisticaDoadorWorkupParaStorage (PedidoLogisticaDoadorWorkup pedidoLogistica, MultipartFile arquivo) {
		LOGGER.info("Inicio do salvar um novo voucher para o pedido de logistica.");
		try {
			validarArquivo(arquivo);
		
			return enviarArquivoPedidoLogisticaDoadorWorkupParaStorage(pedidoLogistica.getPedidoWorkup().getId(),  pedidoLogistica.getId(), arquivo);
		}
		finally {
			LOGGER.info("Fim do salvar do novo exame.");
		}
	}

	private String enviarArquivoPedidoLogisticaDoadorWorkupParaStorage(Long idPedidoWorkup, Long idPedidoLogistica, MultipartFile arquivo) {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		
		String diretorio = ArquivoUtil.obterDiretorioArquivosVoucherPedidoLogisticaDoadorWorkup(idPedidoWorkup, idPedidoLogistica);
		String nomeArquivo = ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo);
				
		try {
			storageService.upload(nomeArquivo, diretorio, arquivo);
		}
		catch (IOException e) {
			throw new BusinessException("erro.arquivo.upload", e);
		}
		
		return diretorio + "/" + nomeArquivo;
	}

	public String enviarArquivoVoucherLogisticaDoadorColetaParaStorage (PedidoLogisticaDoadorColeta pedidoLogistica, MultipartFile arquivo) {
		LOGGER.info("Inicio do salvar um novo voucher para o pedido de logistica.");
		try {
			validarArquivo(arquivo);
		
			return enviarArquivoPedidoLogisticaDoadorColetaParaStorage(pedidoLogistica.getPedidoColeta().getId(),  pedidoLogistica.getId(), arquivo);
		}
		finally {
			LOGGER.info("Fim do salvar do novo exame.");
		}
	}

	private String enviarArquivoPedidoLogisticaDoadorColetaParaStorage(Long idPedidoColeta, Long idPedidoLogistica, MultipartFile arquivo) {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		
		String diretorio = ArquivoUtil.obterDiretorioArquivosVoucherPedidoLogisticaDoadorColeta(idPedidoColeta, idPedidoLogistica);
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
		
		List<Configuracao> configuracoes = configuracaoService.listarConfiguracao(Configuracao.EXTENSAO_ARQUIVO_VOUCHER, Configuracao.TAMANHO_MAXIMO_VOUCHER);
		ArquivoUtil.validarArquivo(arquivo, resultadoValidacao, messageSource, configuracoes.get(0), configuracoes.get(1));
		
		if(!resultadoValidacao.isEmpty()){
			throw new ValidationException("erro.pedido.logistica.arquivo_invalido", resultadoValidacao);
		}		
	}
	
	@Override
	public void excluirArquivoVoucherLogisticaDoStorage(Long idPedidoLogistica, String caminho) {
		if (StringUtils.isBlank(caminho)) {
			throw new BusinessException("erro.pedido.logistica.caminho.arquivo_obrigatorio");
		}
		
		storageService.removerArquivo(caminho);		
	}
	
	@Override
	public void excluirPorId(Long id) {
		arquivoVoucherLogisticaRepository.deleteById(id);
	}

		
}
