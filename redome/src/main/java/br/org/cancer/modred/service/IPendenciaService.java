package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.RespostaPendenciaDTO;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.StatusPendencia;
import br.org.cancer.modred.model.TipoPendencia;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Interface de negócios para pendência.
 * 
 * @author Filipe Paes
 *
 */
public interface IPendenciaService {

	/**
	 * Método para atualizar o status da pendência.
	 * 
	 * @param idPendencia - id da pendencia que será atualizada.
	 * @param status - status que se deseja alterar.
	 */
	void atualizarStatusDePendencia(Long idPendencia, StatusPendencia status);

	/**
	 * Método para atualizar o status de pendencia para respondido.
	 * 
	 * @param id - id da pendencia a ser atualizada.
	 */
	void atualizarStatusDePendenciaParaRespondido(Long idPendencia);

	/**
	 * Método para atualizar o status de pendencia para fechado.
	 * 
	 * @param id - id da pendencia a ser atualizada.
	 */
	void atualizarStatusDePendenciaParaFechado(Long idPendencia);

	/**
	 * Método para atualizar o status de pendencia para cancelado.
	 * 
	 * @param id - id da pendencia a ser atualizada.
	 */
	void atualizarStatusDePendenciaParaCancelado(Long idPendencia);

	/**
	 * Método para atualizar o status de pendencia para aberto.
	 * 
	 * @param id - id da pendencia a ser atualizada.
	 */
	void atualizarStatusDePendenciaParaAberto(Long idPendencia);

	/**
	 * Método para criar pendência.
	 * 
	 * @param pendencia - objeto a ser criado.
	 */
	void criarPendencia(Pendencia pendencia);

	/**
	 * Método para validar pendência.
	 * 
	 * @param pendencia - objeto a ser validado.
	 * @param erros - listagem de erros a ser incrementada.
	 */
	void validar(Pendencia pendencia, List<CampoMensagem> erros);

	/**
	 * Método para obter as respostas associadas a pendência completa.
	 * 
	 * @param pendenciaId - id da pendencia.
	 * @return lista de resposta da pendencia.
	 */
	List<RespostaPendenciaDTO> listarRespostas(Long pendenciaId);

	/**
	 * Método para listar os tipos de pendência disponíveis.
	 * 
	 * @param pendenciaId
	 */
	List<TipoPendencia> listarTiposPendencia();

	/**
	 * Método para listar pendencias por tipo e em aberto da avalição atual em aberto.
	 * 
	 * @param rmr - rmr do paciente
	 * @param idTipoPendencia - id do tipo de pendencia que se deseja localizar.
	 */
	List<Pendencia> listarPendenciasPorTipoEEmAbertoDaAvaliacaoAtualDeUmPaciente(Long rmr, Long idTipoPendencia);
	
	/**
	 * Mpetodo para cancelar uma lista de pendencias.
	 * 
	 * @param pendencias
	 */
	void cancelarPendencias(List<Pendencia> pendencias);

}
