package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.EnderecoContato;

/**
 * Camada de acesso a base dados de EnderecoContatoDoador.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IEnderecoContatoPacienteRepository extends IEnderecoContatoBaseRepository<EnderecoContato>{}