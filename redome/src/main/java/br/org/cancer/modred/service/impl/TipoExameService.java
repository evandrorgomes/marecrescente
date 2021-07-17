package br.org.cancer.modred.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.TipoExame;
import br.org.cancer.modred.model.domain.TiposExame;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ITipoExameRepository;
import br.org.cancer.modred.service.ITipoExameService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe para metodos de negocio do tipo de transplante.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class TipoExameService extends AbstractService<TipoExame, Long> implements ITipoExameService {

	@Autowired
	private ITipoExameRepository tipoExameRepository;
	
	@Override
	public IRepository<TipoExame, Long> getRepository() {
		return tipoExameRepository;
	}

	@Override
	public List<Locus> listarLocusAssociados(Long tipoExameId) {
		final TipoExame tipoExame = findById(tipoExameId);
		List<Locus> locusPorTipo = new ArrayList<Locus>();
		
		if(CollectionUtils.isNotEmpty(tipoExame.getLocus())){
			tipoExame.getLocus().forEach(locus -> {
				locusPorTipo.add(locus);
			});
		}
		return locusPorTipo;
	}

	@Override
	public List<TipoExame> listarTipoExameFase2Nacional() {
		return find(new Equals<List<Long>>("id", 
				Arrays.asList(TiposExame.LOCUS_C_ALTA_RESOLUCAO_CLASSE_II.getId(), 
							TiposExame.LOCUS_C.getId(), 
							TiposExame.ALTA_RESOLUCAO_CLASSE_II.getId())));
	}

}
