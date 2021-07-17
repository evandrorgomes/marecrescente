package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.TransporteTerrestre;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ITransporteTerrestreRepository;
import br.org.cancer.modred.service.ITransporteTerrestreService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de serviço para transporte terrestre.
 * @author Rafael Pizão
 *
 */
@Service
public class TransporteTerrestreService extends AbstractService<TransporteTerrestre, Long> implements ITransporteTerrestreService {

	@Autowired
	private ITransporteTerrestreRepository transporteTerrestreRepositorio;
	
	@Override
	public IRepository<TransporteTerrestre, Long> getRepository() {
		return transporteTerrestreRepositorio;
	}

}
