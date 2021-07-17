package br.org.cancer.redome.workup.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.model.ArquivoPrescricao;
import br.org.cancer.redome.workup.persistence.IArquivoPrescricaoRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IArquivoPrescricaoService;
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
public class ArquivoPrescricaoService extends AbstractService<ArquivoPrescricao, Long> implements IArquivoPrescricaoService {

	@Autowired
	private IArquivoPrescricaoRepository arquivoPrescricaoRepository;

	@Autowired
	private IStorageService storageService;
	
	
	@Override
	public IRepository<ArquivoPrescricao, Long> getRepository() {
		return arquivoPrescricaoRepository;
	}
	
	
	@Override
	public ArquivoPrescricao obterAutorizacaoPaciente(Long idPrescricao){
		return arquivoPrescricaoRepository.obterAutorizacaoPaciente(idPrescricao);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public File obterArquivoDoStorage(Long id) throws BusinessException {
		ArquivoPrescricao arquivoPrescricao = arquivoPrescricaoRepository.findById(id).get();
		return obterArquivoStorage(arquivoPrescricao);
	}

	@Override
	public File obterArquivoStorage(ArquivoPrescricao arquivoPrescricao) {
		if (arquivoPrescricao == null) {
			throw new BusinessException("arquivo.prescricao.nao.encontrado");
		}
		return storageService.download(
				arquivoPrescricao.nomeComTimestamp(), 
				ArquivoUtil.obterDiretorioArquivosPrescricao(arquivoPrescricao.getPrescricao().getId()));
	}

}
