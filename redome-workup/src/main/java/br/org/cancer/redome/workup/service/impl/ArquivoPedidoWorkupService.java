package br.org.cancer.redome.workup.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.model.ArquivoPedidoWorkup;
import br.org.cancer.redome.workup.persistence.IArquivoPedidoWorkupRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IArquivoPedidoWorkupService;
import br.org.cancer.redome.workup.service.IStorageService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.ArquivoUtil;

/**
 * Classe de funcionalidades envolvendo a entidade ArquivoPrescricao.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ArquivoPedidoWorkupService extends AbstractService<ArquivoPedidoWorkup, Long> implements IArquivoPedidoWorkupService {

	@Autowired
	private IArquivoPedidoWorkupRepository arquivoPedidoWorkupRepository;

	@Autowired
	private IStorageService storageService;
	
	
	@Override
	public IRepository<ArquivoPedidoWorkup, Long> getRepository() {
		return arquivoPedidoWorkupRepository;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public File obterArquivoDoStorage(Long id) throws BusinessException {
		ArquivoPedidoWorkup arquivo = arquivoPedidoWorkupRepository.findById(id).get();
		return obterArquivoStorage(arquivo);
	}

	private File obterArquivoStorage(ArquivoPedidoWorkup arquivo) {
		if (arquivo == null) {
			throw new BusinessException("arquivo.prescricao.nao.encontrado");
		}
		return storageService.download(
				arquivo.nomeComTimestamp(), 
				ArquivoUtil.obterDiretorioArquivosPedidoWorkup(arquivo.getPedidoWorkup().getId()));
	}

	@Override
	public List<ArquivoPedidoWorkup> obterArquivosPorPedidoWorkup(Long idPedido) throws BusinessException {
		return arquivoPedidoWorkupRepository.findByPedidoWorkupId(idPedido);
	}
	
}
