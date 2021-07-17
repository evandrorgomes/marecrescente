package br.org.cancer.modred.persistence;

import org.springframework.data.repository.query.Param;

/**
 * Camada de acesso a base dados de ContatoTelefonicoPaciente.
 * 
 * @author bruno.sousa
 *
 */
public interface IContatoTelefonicoPacienteRepositoryCustom {
    
    /**
     * @param rmr
     * @return
     */
    Integer deletar(@Param("rmr") Long rmr);
    
}