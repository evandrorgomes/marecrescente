package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.dto.ContatoPacienteDTO;
import br.org.cancer.modred.controller.dto.LogEvolucaoDTO;
import br.org.cancer.modred.controller.dto.PacienteTarefaDTO;
import br.org.cancer.modred.controller.dto.PedidosPacienteInvoiceDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.security.Perfil;

/**
 * Interface de persistência para controlar a paginação da busca do paciente.
 * 
 * @author Bruno Sousa
 *
 */
public interface IPacienteRepositoryCustom {

	/**
	 * Método para buscar pacientes de acordo com a paginação.
	 * 
	 * @param nome nome
	 * @param idUsuario - Identificador do usuário
	 * @param pageable pageable
	 * @return PacienteJsonPage PacienteJsonPage
	 */
	Page<Paciente> findByNomeContainingAndMedicoResponsavelUsuarioIdOrderByNome(String nome, Long idUsuario, Pageable pageable);
	
	/**
	 * Método para buscar pacientes de um centro avaliador de acordo com a paginação.
	 * 
	 * @param nome nome
	 * @param idCentroAvaliador - identificador do centro avaliador
	 * @param pageable pageable
	 * @return PacienteJsonPage PacienteJsonPage
	 */
	Page<Paciente> findByNomeContainingAndCentroAvaliadorIdOrderByNome(String nome, Long idFuncaoTransplante, Long idCentroAvaliador, Pageable pageable);

	/**
	 * Lista os pacientes para um determinado médico responsável e o seu total de pendências (não respondidas).
	 * 
	 * @param medicoId ID do médico responsável.
	 * @param paginacao paginação para ser aplicada ao resultado da consulta.
	 * @return lista paginada do DTO de paciente somente as informações necessárias.
	 */
	Paginacao<PacienteTarefaDTO> listarPacientesComAvaliacaoEmAberto(Long medicoId, Pageable paginacao);

	/**
	 * Lista os pacientes associados ao médico informado.
	 * 
	 * @param medicoId ID do médico a ser filtrado.
	 * @param paginacao paginação do resultado.
	 * @return lista dos pacientes, associados ao médico, paginada.
	 */
	Paginacao<PacienteTarefaDTO> listarPacientes(Long medicoId, Pageable paginacao);

	/**
	 * Lista os pacientes a partir dos RMRs informados.
	 * 
	 * @param rmrs RMR dos pacientes a serem filtrados.
	 * @return lista dos pacientes, associados ao médico, paginada.
	 */
	List<PacienteTarefaDTO> listarPacientes(List<Long> rmrs);

	/**
	 * Obtém o contato do paciente a partir do RMR.
	 * 
	 * @param rmr do paciente referência para buscar o contato.
	 * @return contato do paciente.
	 */
	ContatoPacienteDTO obterContatoPaciente(Long rmr);

	/**
	 * Atualiza os dados de identificação e dados pessoais.
	 * 
	 * @param paciente paciente que terá seus dados pessoais atualizados.
	 * @return paciente paciente atualizado na base.
	 */
	Paciente atualizarDadosPessoais(Paciente paciente);

	/**
	 * Atualizar e-mail.
	 * 
	 * @param rmr chave do paciente.
	 * @param email e-mail do paciente.
	 * @return paciente paciente atualizado na base.
	 */
	void atualizarEmail(Paciente paciente);

	/**
	 * Listar o log de evolução do paciente.
	 * 
	 * @param rmr RMR do paciente associado ao log.
	 * @param lista de perfis que o log não deve ser exibido.
	 * @return lista de logs de evolução em formato DTO, representado cada entrada de log.
	 */
	Paginacao<LogEvolucaoDTO> listarLogEvolucao(Long rmr, List<Perfil> perfisExclusao, Pageable pageable);
	

	Page<Paciente> listarPacientesPorRmrOuNome(Long rmr, String nome, Pageable pageable);
	
	/**
	 * Obtem todos os pedidos de exame conciliados.
	 * 
	 * @param rmr - filtro rmr do paciente
	 * @param nomePaciente - filtro nome do paciente
	 * @param pageable - paginação
	 * @return PedidosPacienteInvoiceDTO lista paginada de pedidos
	 */
	Paginacao<PedidosPacienteInvoiceDTO> listarPedidosPacienteInvoicePorRmrENomePaciente(Long rmr, String nomePaciente, Pageable pageable);
}
