package br.org.cancer.redome.workup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.workup.model.TransporteTerrestre;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.persistence.ITransporteTerrestreRepository;
import br.org.cancer.redome.workup.service.ITransporteTerrestreService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;

/**
 * Classe de funcionalidades envolvendo a entidade ArquivoResultadoWorkup.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class TransporteTerrestreService extends AbstractService<TransporteTerrestre, Long> implements ITransporteTerrestreService {
	
	@Autowired
	private ITransporteTerrestreRepository transporteTerrestreRepository;
		
	@Override
	public IRepository<TransporteTerrestre, Long> getRepository() {
		return transporteTerrestreRepository;
	}
	
	@Override
	public void excluirPorId(Long id) {
		transporteTerrestreRepository.deleteById(id);
	}

		
}
