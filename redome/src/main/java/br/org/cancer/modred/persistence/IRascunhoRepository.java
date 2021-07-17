package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Rascunho;

/**
 * Interface de persistencia de Rascunho.
 * @author Filipe Paes
 *
 */
@Repository
public interface IRascunhoRepository extends JpaRepository<Rascunho, Long>{
    Rascunho findByUsuarioId(Long id);
    @Modifying
    @Transactional
    @Query("delete from Rascunho r where r.usuario.id = ?1")
    void delete(Long idUsuario);
}
