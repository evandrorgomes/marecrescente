package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Responsavel;

/**
 * Camada de acesso a base dados de Responsável.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IResponsavelRepository extends JpaRepository<Responsavel, Long>{}