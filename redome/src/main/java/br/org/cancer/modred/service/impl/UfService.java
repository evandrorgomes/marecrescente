package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Uf;
import br.org.cancer.modred.persistence.IUfRepository;
import br.org.cancer.modred.service.IUfService;

/**
 * Classe para metodos de negocio de raça.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class UfService implements IUfService {

    @Autowired
    private IUfRepository ufRepository;

    /**
     * Método para obter lista ordenada de ufs.
     * 
     * @Return List<Uf>
     */
    @Override
    public List<Uf> listarUf() {
        return ufRepository.findByOrderByNomeAsc();
    }

    /**
     * Método para obter UF por id.
     * 
     * @param sigla
     * @return Uf uf localizada por id
     */
    @Override
    public Uf getUf(String sigla) {
        return ufRepository.findById(sigla).get();
    }
    
    @Override
    public List<Uf> listarUfSemNaoInformado() {
    	return ufRepository.findBySiglaNotOrderByNomeAsc("NI");    	
    }
    
}
