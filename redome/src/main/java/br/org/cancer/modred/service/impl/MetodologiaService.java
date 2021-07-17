package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Metodologia;
import br.org.cancer.modred.persistence.IMetodologiaRepository;
import br.org.cancer.modred.service.IMetodologiaService;

/**
 * Classe para metodos de metodologia.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class MetodologiaService implements IMetodologiaService {

    @Autowired
    private IMetodologiaRepository metodologiaRepository;

    /**
     * Método para obter lista ordenada de metodologias.
     * 
     * @Return List<Metodologia>
     */
    @Override
    public List<Metodologia> listarMetodologias() {
        return metodologiaRepository.findAllByOrderBySiglaAsc();
    }

	/* (non-Javadoc)
	 * @see br.org.cancer.modred.service.IMetodologiaService#findById(java.lang.Long)
	 */
	@Override
	public Metodologia findById(Long id) {
		return metodologiaRepository.findById(id).get();
	}
}
