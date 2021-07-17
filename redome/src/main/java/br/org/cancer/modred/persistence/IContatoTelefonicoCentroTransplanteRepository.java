package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ContatoTelefonicoCentroTransplante;

/**
 * Camada de acesso a base dados de ContatoTelefonicoCentroTransplante.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IContatoTelefonicoCentroTransplanteRepository extends IContatoTelefonicoBaseRepository<ContatoTelefonicoCentroTransplante> {
    
}