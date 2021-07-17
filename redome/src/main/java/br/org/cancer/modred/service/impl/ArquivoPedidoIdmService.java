package br.org.cancer.modred.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.model.ArquivoPedidoIdm;
import br.org.cancer.modred.model.PedidoIdm;
import br.org.cancer.modred.persistence.IArquivoPedidoIdmRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IArquivoPedidoIdmService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.ArquivoUtil;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade ArquivoPedidoIdm.
 * 
 * @author Pizão
 * 
 */
@Service
@Transactional
public class ArquivoPedidoIdmService extends AbstractService<ArquivoPedidoIdm, Long> implements IArquivoPedidoIdmService {
	
	@Autowired
	private IArquivoPedidoIdmRepository pedidoIdmRepository;
	
	@Autowired
	private IStorageService storageService;
	
	
	@Override
	public IRepository<ArquivoPedidoIdm, Long> getRepository() {
		return pedidoIdmRepository;
	}
	
	/**
	 * Obtém o diretório em que o laudo será salvo.
	 * 
	 * @param arquivo arquivo a ser salvo, que será usado para composição do caminho.
	 * @param idPedidoIdm ID do pedido de IDM, também utilizado para compor o caminho. 
	 * @return diretório criado a partir das informações do pedido.
	 */
	private String obterDiretorioLaudo(MultipartFile arquivo, Long idPedidoIdm) {
		return StorageService.DIRETORIO_RESULTADO_IDM + "/" + String.valueOf(idPedidoIdm) + "/"
				+ StorageService.DIRETORIO_LAUDO_IDM;
	}

	@Override
	public void salvarLaudo(List<MultipartFile> listaArquivos, PedidoIdm pedidoIDM) throws IOException {
		for (MultipartFile arquivo : listaArquivos) {
			final long timeStampMillis = Instant.now().toEpochMilli();
			final String diretorio = obterDiretorioLaudo(arquivo, pedidoIDM.getId());
			final String nomeArquivo = ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo);
			
			ArquivoPedidoIdm arquivoLaudoIDM = new ArquivoPedidoIdm();
			arquivoLaudoIDM.setPedidoIdm(pedidoIDM);
			arquivoLaudoIDM.setCaminho(diretorio + "/" + nomeArquivo);
			save(arquivoLaudoIDM);
			storageService.upload(nomeArquivo, diretorio, arquivo);
		}
	}

}
