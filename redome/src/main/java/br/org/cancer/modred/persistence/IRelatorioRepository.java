package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Relatorio;

/**
 * Classe de persistencias spring data de repositorios.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IRelatorioRepository extends JpaRepository<Relatorio, Long> {

	/**
	 * Buscar por codigo unico.
	 * 
	 * @param codigo unico
	 * @return relatorio encontrado
	 */
	Relatorio findByCodigo(String codigo);

}
