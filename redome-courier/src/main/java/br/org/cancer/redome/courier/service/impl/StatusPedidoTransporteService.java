package br.org.cancer.redome.courier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.courier.model.StatusPedidoTransporte;
import br.org.cancer.redome.courier.persistence.IRepository;
import br.org.cancer.redome.courier.persistence.IStatusPedidoTransporteRepository;
import br.org.cancer.redome.courier.service.IStatusPedidoTransporteService;
import br.org.cancer.redome.courier.service.impl.custom.AbstractService;

/**
 * Implementacao da classe de negócios de StatusPedidoTransporte.
 * 
 * @author Bruno Sousa
 *
 */
@Service
@Transactional
public class StatusPedidoTransporteService extends AbstractService<StatusPedidoTransporte, Long> implements IStatusPedidoTransporteService {

	@Autowired
	private IStatusPedidoTransporteRepository statusPedidoTransporteRepository;

	
	@Override
	public IRepository<StatusPedidoTransporte, Long> getRepository() {
		return statusPedidoTransporteRepository;
	}

}
