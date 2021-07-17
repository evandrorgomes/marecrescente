package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ArquivoPedidoTransporte;
import br.org.cancer.modred.model.PedidoTransporte;
import br.org.cancer.modred.persistence.IArquivoPedidoTransporteRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IArquivoPedidoTransporteService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de serviço para Arquivo Exame.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class ArquivoPedidoTransporteService extends AbstractService<ArquivoPedidoTransporte, Long> implements IArquivoPedidoTransporteService {

	@Autowired
	private IArquivoPedidoTransporteRepository arquivoPedidoTransporteRepository;

	private IStorageService storageService;
	

	@Override
	public IRepository<ArquivoPedidoTransporte, Long> getRepository() {
		return arquivoPedidoTransporteRepository;
	}

	@Override
	public ArquivoPedidoTransporte obterArquivo(Long id) {
		return arquivoPedidoTransporteRepository.findById(id).get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File obterArquivoDoStorage(Long id) throws BusinessException {
		ArquivoPedidoTransporte arquivoPedidoTransporte = obterArquivo(id);
		if (arquivoPedidoTransporte == null) {
			throw new BusinessException("arquivo.exame.nao.encontrado");
		}
		String diretorio = StorageService.DIRETORIO_PEDIDO_TRANSPORTE + "/" + arquivoPedidoTransporte.getPedidoTransporte()
				.getId();
		File arquivo = storageService.download(arquivoPedidoTransporte.obterNomeArquivo(), diretorio);
		return arquivo;
	}

	@Override
	public File listarArquivosTransporteZipados(PedidoTransporte pedidoTransporte) throws BusinessException, IOException {
		List<ArquivoPedidoTransporte> arquivosTransporte = find(new Equals<Long>("pedidoTransporte.id", pedidoTransporte.getId()));
		List<File> arquivos = new ArrayList<File>();
		
		if(CollectionUtils.isNotEmpty(arquivosTransporte)){
			arquivosTransporte.forEach(arquivoPedidoTransporte -> {
				String diretorio = 
						StorageService.DIRETORIO_PEDIDO_TRANSPORTE + "/" + arquivoPedidoTransporte.getPedidoTransporte().getId();
				File arquivo = storageService.download(arquivoPedidoTransporte.obterNomeArquivo(), diretorio);
				arquivos.add(arquivo);
			});
		}
		return storageService.obterArquivoZip(arquivos);
	}


	/**
	 * @param storageService the storageService to set
	 */
	@Autowired
	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}

	
}
