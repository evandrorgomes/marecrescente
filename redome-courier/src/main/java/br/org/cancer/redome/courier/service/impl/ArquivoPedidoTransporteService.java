package br.org.cancer.redome.courier.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.redome.courier.exception.BusinessException;
import br.org.cancer.redome.courier.exception.ValidationException;
import br.org.cancer.redome.courier.model.ArquivoPedidoTransporte;
import br.org.cancer.redome.courier.model.Configuracao;
import br.org.cancer.redome.courier.model.PedidoTransporte;
import br.org.cancer.redome.courier.model.StatusPedidoTransporte;
import br.org.cancer.redome.courier.model.domain.StatusPedidosTransporte;
import br.org.cancer.redome.courier.persistence.IArquivoPedidoTransporteRepository;
import br.org.cancer.redome.courier.persistence.IRepository;
import br.org.cancer.redome.courier.service.IArquivoPedidoTransporteService;
import br.org.cancer.redome.courier.service.IConfiguracaoService;
import br.org.cancer.redome.courier.service.IPedidoTransporteService;
import br.org.cancer.redome.courier.service.IStorageService;
import br.org.cancer.redome.courier.service.impl.config.Equals;
import br.org.cancer.redome.courier.service.impl.custom.AbstractService;
import br.org.cancer.redome.courier.util.ArquivoUtil;
import br.org.cancer.redome.courier.util.CampoMensagem;

/**
 * Implementacao da classe de negócios de ArquivoPedidoTransporte.
 * 
 * @author Bruno Sousa
 *
 */
@Service
@Transactional
public class ArquivoPedidoTransporteService extends AbstractService<ArquivoPedidoTransporte, Long> implements IArquivoPedidoTransporteService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoPedidoTransporteService.class);

	@Autowired
	private IArquivoPedidoTransporteRepository arquivoPedidoTransporteRepository;
	
	private IPedidoTransporteService pedidoTransporteService;
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private IStorageService storageService;

	@Autowired
	private IConfiguracaoService configuracaoService;
	
	@Override
	public IRepository<ArquivoPedidoTransporte, Long> getRepository() {
		return arquivoPedidoTransporteRepository;
	}


	@SuppressWarnings("unchecked")
	@Override
	public File obterArquivosPedidoTransporteZipados(Long idPedidoTransporte) {
		PedidoTransporte pedido = pedidoTransporteService.obterPedidoTransportePorId(idPedidoTransporte);
		validarStatus(pedido.getStatus());
		
		List<ArquivoPedidoTransporte> arquivosTransporte = find(new Equals<Long>("pedidoTransporte.id", pedido.getId()));
		List<File> arquivos = new ArrayList<File>();
		
		if(CollectionUtils.isNotEmpty(arquivosTransporte)){
			arquivosTransporte.forEach(arquivoPedidoTransporte -> {
				String diretorio = 
						BluemixStorageService.DIRETORIO_PEDIDO_TRANSPORTE + "/" + arquivoPedidoTransporte.getPedidoTransporte().getId();
				File arquivo = storageService.download(arquivoPedidoTransporte.obterNomeArquivo(), diretorio);
				arquivos.add(arquivo);
			});
		}
		try {
			return storageService.obterArquivoZip(arquivos);
		} catch (BusinessException | IOException e) {
			LOGGER.error("", e);
			return null;
		}
		
	}

	private void validarStatus(StatusPedidoTransporte status) {
		if (status.getId().equals(StatusPedidosTransporte.EM_ANALISE.getId()) || status.getId().equals(StatusPedidosTransporte.ENTREGUE.getId()) || 
			status.getId().equals(StatusPedidosTransporte.AGUARDANDO_DOCUMENTACAO.getId()) || status.getId().equals(StatusPedidosTransporte.CANCELADO.getId())) {
			throw new BusinessException("");
		}
	}


	@Autowired
	public void setPedidoTransporteService(IPedidoTransporteService pedidoTransporteService) {
		this.pedidoTransporteService = pedidoTransporteService;
	}
	

	@Override
	public void atualizarListaDeArquivosCnt(PedidoTransporte pedidoTransporte, String descricaoAlteracao, List<MultipartFile> arquivos) {
		
		if(arquivos == null || arquivos.isEmpty()) {
			return;
		}
		
		arquivos.forEach(arquivo->{
			
			validarArquivoTransporte(arquivo);
			String caminhoArquivo = null;

			caminhoArquivo = this.enviarArquivoParaStorage(pedidoTransporte.getId(), pedidoTransporte.getPedidoLogistica(), arquivo);

			ArquivoPedidoTransporte arquivoPedidoTransporte = ArquivoPedidoTransporte.builder()
					.pedidoTransporte(pedidoTransporte)
					.caminho(caminhoArquivo)
					.descricaoAlteracao(descricaoAlteracao)
					.build();

			save(arquivoPedidoTransporte);
		});
	}
	
	private void validarArquivoTransporte(MultipartFile arquivo) {
		ArrayList<CampoMensagem> resultadoValidacao = new ArrayList<CampoMensagem>();
		List<Configuracao> configuracoes = configuracaoService.listarConfiguracao(Configuracao.EXTENSAO_ARQUIVO_PEDIDO_TRANSPORTE,
				Configuracao.TAMANHO_ARQUIVO_PEDIDO_TRANSPORTE);
		ArquivoUtil.validarArquivo(arquivo, resultadoValidacao, messageSource, configuracoes.get(0), configuracoes.get(1));
		
		if (!resultadoValidacao.isEmpty()) {
			throw new ValidationException("erro.pedido.transporte.arquivo.invalido", resultadoValidacao);
		}
	}

	private String enviarArquivoParaStorage(Long idPedidoTransporte, Long idPedidoLogistica, MultipartFile arquivo) {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		
		String diretorio = ArquivoUtil.obterDiretorioArquivosCntPedidoTransporte(idPedidoTransporte, idPedidoLogistica);
		String nomeArquivo = ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo);
				
		try {
			storageService.upload(nomeArquivo, diretorio, arquivo);
		}
		catch (IOException e) {
			throw new BusinessException("erro.arquivo.upload", e);
		}
		
		return diretorio + "/" + nomeArquivo;
	}

	

}
