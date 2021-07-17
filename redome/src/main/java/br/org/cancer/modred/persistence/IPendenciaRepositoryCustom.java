package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.dto.RespostaPendenciaDTO;
import br.org.cancer.modred.controller.page.PendenciaJsonPage;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.domain.StatusPendencias;

/**
 * Interface de persistência para controlar a paginação da busca do paciente.
 * 
 * @author Bruno Sousa
 *
 */
public interface IPendenciaRepositoryCustom {

	/**
	 * Método para buscar pendências de uma avaliacao de acordo com a paginação.
	 * 
	 * @param pageable objeto com dados para a paginação.
	 * @param avaliacaoId id da avaliação
	 * @param status para ordenação e filtrar as pendências.
	 * @return PendenciaJsonPage pendenciaJsonPage
	 */
	PendenciaJsonPage<Pendencia> findAllCustom(Pageable pageable, Long avaliacaoId, StatusPendencias... status);

	List<RespostaPendenciaDTO> listarRespostas(Long pendenciaId);

}
