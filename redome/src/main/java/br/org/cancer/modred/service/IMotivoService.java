package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Motivo;

/**
 * Interface de negócios para Motivo.
 * @author Filipe Paes
 *
 */
public interface IMotivoService {
    
    /**
     * Método para carregar motivo por id.
     * @param id
     * @return Motivo buscado por id
     */
    Motivo obterMotivo(Long id);
    
    /**
     * Método para listar motivos de evolução, EXCETO motivo CADASTRO INICIAL.
     * 
     * @return List<Motivo> listagem de motivos encontrados
     */
    List<Motivo> listarMotivosExcetoCadastroInicial();
}
