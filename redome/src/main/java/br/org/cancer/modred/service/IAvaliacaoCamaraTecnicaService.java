package br.org.cancer.modred.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.AvaliacaoCamaraTecnicaDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AvaliacaoCamaraTecnica;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.service.custom.IService;

/**
 * 
 * Interface de negócios de avaliação de camara técnica.
 * @author Filipe Paes
 *
 */
public interface IAvaliacaoCamaraTecnicaService extends IService<AvaliacaoCamaraTecnica, Long>{
	
	/**
	 * Retorna uma lista paginada de tarefas de avaliação de câmara técnica.
	 * 
	 * @param somenteSobResponsabilidadeUsuarioLogado - se true trás somente os itens atribuido ao usuário.
	 * @param pageRequest - parâmetros de paginação.
	 * @return lista paginada de tarefas.
	 */
	Page<TarefaDTO> listarTarefasAvaliacaoCamaraTecnica(Boolean somenteSobResponsabilidadeUsuarioLogado, PageRequest pageRequest);
	
	/**
	 * Cria uma Avaliação de Câmara Técnica inicial. 
	 * @param paciente - paciente a ser avaliado.
	 */
	void criarAvaliacaoInicial(Paciente paciente);

	/**
	 * Aprova avaliação de câmara técnica.
	 * @param avaliacao a ser a provada.
	 * @param arquivo arquivo de relatório de cãmara técnica.
	 */
	void aprovarAvaliacao(AvaliacaoCamaraTecnica avaliacao, MultipartFile arquivo);
	
	

	/**
	 * Reprova avaliação de câmara técnica.
	 * @param avaliacao a ser a provada.
	 * @param arquivo arquivo de relatório de cãmara técnica.
	 */
	void reprovarAvaliacao(AvaliacaoCamaraTecnica avaliacao, MultipartFile arquivo);
	
	/**
	 * Altera status de avaliação de câmara técnica.
	 * @param avaliacao com o status a ser alterado.
	 */
	void alterarStatus(AvaliacaoCamaraTecnica avaliacao);
	
	
	/**
	 * Obtém um DTO para avaliação de câmara técnica.
	 * @param idAvaliacao a ser carregado.
	 * @return DTO de avaliação de câmara técnica.
	 */
	AvaliacaoCamaraTecnicaDTO obterAvaliacaoCamaraTecnicaDTO(Long idAvaliacao);
	
	/**
	 * Obtem uma avaliação por RMR.
	 * @param rmr
	 * @return avaliação.
	 */
	AvaliacaoCamaraTecnica obterAvaliacaoPor(Long rmr);
}
