package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.CondicaoPaciente;

/**
 * Interface de negócios para Condição Paciente.
 * @author Filipe Paes
 *
 */
public interface ICondicaoPacienteService {
    /**
     * Método para listar condições.
     * @return List<CondicaoPaciente> listagem de condições.
     */
    List<CondicaoPaciente> listarCondicoesPaciente();
    
    /**
     * Buscar condicao paciente por id.
     * @param Long identicacao da condição
     * @return CondicaoPaciente objeto de condicao paciente localizado.
     */
    CondicaoPaciente obterCondicaoPaciente(Long id);
}
