package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Motivo;
import br.org.cancer.modred.persistence.IMotivoRepository;
import br.org.cancer.modred.service.IMotivoService;

/**
 * Classe de implementacao de negócio de Motivo Service.
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class MotivoService implements IMotivoService {

    @Autowired
    private IMotivoRepository motivoRepository;
    
    @Override
    public List<Motivo> listarMotivosExcetoCadastroInicial() {
        return motivoRepository.listarMotivosExcetoCadastroInicial();
    }

    @Override
    public Motivo obterMotivo(Long id) {
        return motivoRepository.findById(id).get();
    }
}
