package br.org.cancer.modred.service;

import java.time.LocalDate;

import br.org.cancer.modred.controller.dto.ContatoDTO;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.interfaces.IDoadorHeader;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades envolvendo a classe Doador.
 * 
 * @author Pizão
 */
public interface IDoadorService extends IService<Doador, Long> {

	/**
	 * Inativa o doador para o processo em que ele está associado (varrer todos os processos/tarefas associados).
	 * 
	 * @param id identificação do doador.
	 * @param motivoStatusId motivo da troca de status.
	 * @param tempoInatividade data de retorno (em milisegundos) da inatividade.
	 * 
	 * @return o doador atualizado.
	 */
	Doador inativarDoador(Long id, Long motivoStatusId, Long tempoInatividade);

	ContatoDTO obterDoadorParaContatoPorDmr(Long id, boolean exibirUltimaFase);

	/**
	 * Alterar o status do doador .
	 * 
	 * @param id identificação do doador.
	 * @param statusId - status a ser alterado
	 * @param motivoStatusId motivo da troca de status.
	 * @param tempoInatividade data de retorno (em milisegundos) da inatividade.
	 * 
	 * @return o doador atualizado.
	 */
	ContatoDTO atualizarStatusDoador(Long id, Long statusId, Long motivoStatusId, Long tempoInatividade);

	/**
	 * Obtém o status do doador.
	 * 
	 * @param id - identificador do doador
	 * @return status do doador
	 */
	StatusDoador obterStatusDoadorPorId(Long id);

	/**
	 * Obtém a quantidade de processos ativos de um doador. Count de matchs ativos e de buscas ativas do doador excluindo a atual.
	 * 
	 * @param id - identificador do doador
	 * @return quantidade de processos ativos
	 */
	int obterQuantidadeProcessosAtivosDoador(Long id);

	/**
	 * Obtém doador associado a solicitação informada.
	 * 
	 * @param solicitacaoId ID da solicitação.
	 * @return id do doador associado.
	 */
	Long obterDoadorPorSolicitacao(Long solicitacaoId);

	/**
	 * Obtém a identificação do doador, de acordo com o tipo, para ser utilizado no cabeçalho.
	 * 
	 * @param idDoador ID do doador.
	 * @return doador preenchido, de acordo com o tipo.
	 */
	IDoadorHeader obterDadosIdentificadaoPorDoador(Long idDoador);
	
	/**
	 * Obtém o doador pelo id.
	 * 
	 * @param id identificador do doador.
	 * @return uma instancia de doador.
	 */
	Doador obterDoador(Long id);

	void inativarDoadorFase3NaoRetornoSms(Long idDoador, Long statusDoadorId, Long motivoStatusId);
	
	Doador salvarAtualizacaoStatusDoador(Long idDoador, Long statusId, Long motivoStatusId, LocalDate dataRetorno);
	
	Long obterDmrDoadorPorSolicitacao(Long solicitacaoId);
	
	/**
	 * Salva doador sem realizar a validação.
	 * @param doador a ser salvo.
	 */
	void salvarSemValidacao(Doador doador);

}
