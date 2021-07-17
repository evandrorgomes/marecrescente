package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ArquivoEvolucao;

/**
 * Classe com métodos de acesso ao banco de Arquivos e Evolução.
 * @author Filipe Paes
 *
 */
@Repository
public interface IArquivoEvolucaoRepository  extends IRepository<ArquivoEvolucao, Long>{

}
