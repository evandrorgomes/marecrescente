package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ContatoTelefonicoPaciente;

/**
 * Camada de acesso a base dados de ContatoTelefonicoPaciente.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IContatoTelefonicoPacienteRepository extends IContatoTelefonicoBaseRepository<ContatoTelefonicoPaciente>, IContatoTelefonicoPacienteRepositoryCustom{
    
    /**
     * Lista todos os contatos telefônicos para o ID do paciente informado.
     * 
     * @param pacienteRmr
     * @return
     */
    List<ContatoTelefonicoPaciente> findByPacienteRmr(Long pacienteRmr);
    
}