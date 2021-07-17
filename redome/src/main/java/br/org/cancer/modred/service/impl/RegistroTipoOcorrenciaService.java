package br.org.cancer.modred.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.RegistroTipoOcorrencia;
import br.org.cancer.modred.persistence.IRegistroTipoOcorrenciaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IRegistroTipoOcorrenciaService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Métodos de negócio de tipo de ocorrencia service.
 * @author Filipe Paes
 *
 */
@Service
public class RegistroTipoOcorrenciaService extends AbstractService<RegistroTipoOcorrencia, Long>  implements IRegistroTipoOcorrenciaService {

	
	@Autowired
	private IRegistroTipoOcorrenciaRepository registroTipoOcorrenciaRepository;
	
	
	@Override
	public IRepository<RegistroTipoOcorrencia, Long> getRepository() {
		return registroTipoOcorrenciaRepository;
	}

}
