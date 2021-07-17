package br.org.cancer.modred.persistence;

import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.model.DoadorInternacional;

/**
 * Interface customizada de doadores internacionais.
 * @author Filipe Paes
 *
 */
public interface IDoadorInternacionalRepositoryCustom {

	/**
	 * Obtem listagem pagina de doadores internacionais.
	 * 
	 * @param page - pagina referenciada
	 * @param idDoadorRegistro - id do doador
	 * @param registro - id do registro
	 * @param grid - numero grid
	 * @return lista paginada de doadores internacionais.
	 */
	Paginacao<DoadorInternacional> obterDoadoresInternacionaisPaginado(Pageable page, String idDoadorRegistro, Long registro, String grid);
}
