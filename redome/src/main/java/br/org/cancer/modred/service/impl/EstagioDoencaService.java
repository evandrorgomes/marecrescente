package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.EstagioDoenca;
import br.org.cancer.modred.persistence.IEstagioDoencaRepository;
import br.org.cancer.modred.service.IEstagioDoencaService;

/**
 * Classe para metodos de negocio de estágio de doença.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class EstagioDoencaService implements IEstagioDoencaService {

    @Autowired
    private IEstagioDoencaRepository estagioDoencaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EstagioDoenca> listarEstagiosDoencaPorCid(long idCid) {
        return estagioDoencaRepository.findByCidsId(idCid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EstagioDoenca obterEstagioDoenca(Long id) {
        return estagioDoencaRepository.findById(id).get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean isEstagioInvalidoParaCid(Long cidId, Long estagioDoencaId) {
        return estagioDoencaRepository.obterEstagioReferenteAoCid(cidId, estagioDoencaId) == null;
    }
}
