package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Motivo;

/**
 * Interface de persistencia de Motivo.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IMotivoRepository extends JpaRepository<Motivo, Long> {

	/**
	 * Lista todos os motivos cadastrados no banco, exceto o motivo CADASTRO INICIAL.
	 * 
	 * @return lista de motivos encontrados
	 */
	@Cacheable(value = "dominio", key = "#root.methodName")
	@Query("select m from Motivo m where m.descricao <> 'CADASTRO INICIAL DE PACIENTE'")
	List<Motivo> listarMotivosExcetoCadastroInicial();

}
