package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.DoadorNaoContactado;

/**
 * Interface para acesso ao banco de dados envolvendo a classe DoadorNaoContactado.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IDoadorNaoContactadoRepository extends IRepository<DoadorNaoContactado, Long> {



}
