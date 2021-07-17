package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.controller.dto.MatchWmdaDTO;
import br.org.cancer.modred.model.ComentarioMatch;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.FiltroMatch;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de métodos de negócio relacionados a Match.
 * 
 * @author Filipe Paes
 *
 */
public interface IMatchService extends IService<Match, Long> {

	/**
	 * Cria match entre um paciente e doador.
	 * 
	 * @param rmr - id do paciente.
	 * @param idDoador - id do doador.
	 */
	void criarMatch(Long rmr, Long idDoador);

	
	/**
	 * Obtém os comentários relacionados ao match.
	 * 
	 * @param idMatch ID do match a ser pesquisado.
	 * @return lista de comentários relacionados.
	 */
	List<ComentarioMatch> listarComentarios(Long idMatch);

	/**
	 * Obtém a lista de matchs por rmr e a fase atual.
	 * 
	 * @param rmr - id do paciente.
	 * @param situacao - fase atual do doador.
	 * @param tiposDoador - Lista de tipos de doador.
	 * @param ativo - status do match
	 * @param disponibilizado - se o doador está disponibilizado para o paciente.
	 * @return
	 */
	//List<Match> listarMatchsAtivosPorRmrAndSituacao(Long rmr, List<FasesMatch> situacao, List<TiposDoador> tiposDoador, Boolean ativo);	
	List<MatchDTO> buscarListaMatchPorSituacao(Long rmr, List<FasesMatch> situacoes, FiltroMatch filtro, Boolean matchAtivo, Boolean disponibilizado);
	
	/**
	 * Obtém o match por id.
	 * 
	 * @param id Identificador do match.
	 * @return Match convertido em MatchDTO.
	 */
	MatchDTO obterMatchPorId(Long id);
	
	/**
	 * Obtém a lista de matchs por rmr e lista de idDoador.
	 * @param rmr - id do paciente.
	 * @param listaIdsDoador - lista de idDoador de doadores
	 * @return lista de match
	 */
	List<MatchDTO> listarMatchsAtivosPorRmrAndListaIdsDoador(Long rmr, List<Long> listaIdsDoador );

	/**
	 * Obtém a busca a partir do pedido de workup.
	 * 
	 * @param pedidoWorkupId ID do pedido de workup.
	 * @return a busca associada ao pedido de workup.
	 */
	Match obterMatchPorPedidoWorkup(Long pedidoWorkupId);
	
	/**
	 * Obtém a busca a partir do pedido de contato.
	 * 
	 * @param pedidoContatoId ID do pedido de contato.
	 * @return a busca associada ao pedido de contato.
	 */
	Match obterMatchPorPedidoContato(Long pedidoContatoId);
	
	/**
	 * Obtém a busca a partir do pedido de exame.
	 * 
	 * @param pedidoExameId ID do pedido de exame.
	 * @return match associado ao pedido de exame.
	 */
	Match obterMatchPorPedidoExame(Long pedidoExameId);
	
	
	/**
	 * Obtém o match a partir do pedido de exame.
	 * 
	 * @param pedidoIdm ID do pedido de exame.
	 * @return match associado ao pedido de exame.
	 */
	Match obterMatchPorPedidoIdm(Long pedidoIdm);
	
	/**
	 * Disponiliza o doador para prescrição.
	 * 
	 * @param match
	 */
	void disponibilizarDoador(Long idMatch);
	
	/**
	 * Obtém o match ativo entre paciente e doador.
	 * 
	 * @param rmr identificação do paciente.
	 * @param idDoador identificação do doador.
	 * @return match ativo entre os dois.
	 */
	Match obterMatchAtivo(Long rmr, Long idDoador);
	
	/**
	 * Obtém o doador pelo ID do match.
	 * 
	 * @param matchId ID do match.
	 * @return doador associado ao match.
	 */
	Doador obterDoador(Long matchId);
	
	/**
	 * Lista todos os matchs ativos para o doador.
	 * 
	 * @param idDoador ID do doador a ser pesquisado.
	 * @return lista os matchs ativos para o doador.
	 */
	List<Match> listarMatchsAtivos(Long idDoador);
	
	/**
	 * Cancela todos os matchs ativos associados ao doador.
	 * 
	 * @param idDoador ID do doador.
	 * @return TRUE se existia algum match para ser cancelado.
	 */
	boolean cancelarMatchsAtivos(Long idDoador);
	
	
	/**
	 * Obtém a quantidade de matchs por rmr e os tipos de doador.
	 * 
	 * @param rmr - id do paciente.
	 * @param tiposDoador - Lista de tipos de doador.
	 * @return Quantidade de matchs
	 */
	Long obterQuantidadeMatchsAtivosPorRmrAndTiposDoador(Long rmr, List<TiposDoador> tiposDoador);

	/**
	 * Obtém a quantidade de matchs por idBusca e os tipos de doador.
	 * 
	 * @param buscaId - id da busca.
	 * @param tiposDoador - Lista de tipos de doador.
	 * @return Quantidade de matchs
	 */
	Integer obterQuantidadeMatchsAtivosPorBuscaIdAndTiposDoador(Long buscaId, List<TiposDoador> tiposDoador) ;

	/**
	 * Obtém a lista de matchs inativos por rmr e a fase atual que possuem solicitação com este paciente.
	 * 
	 * @param rmr - Identificação do paciente
	 * @param situacao - Fase do doador no match.
	 * @param filtro - Filtro com os tipos de doadores
	 * @return Lista de matchs inativos.
	 */
	List<MatchDTO> buscarListaMatchInativoComSolicitacao(Long rmr, String situacao, FiltroMatch filtro);
	
	/**
	 * Obtém o match por id do match.
	 * 
	 * @param id identificação do match.
	 * 
	 * @return match.
	 */
	Match obterMatch(Long id);
	
	/**
	 * Cria checklist para caso exista match com divergencia.
	 * @param id da tarefa de follow up.
	 */
	void criarCheckListParaMatchDivergente(Long idTarefa);
	
	/**
	 * Lista os matchs ativos e disponibilizados para uma busca especifica. Caso não exista retorna uma lista vazia. 
	 * 
	 * @param idBusca identificador da busca.
	 * @return Lista de matchs ativos e disponibilizados. 
	 */
	List<Match> listarMatchsAtivosEDisponibilizadosPeloIdentificadorDaBusca(Long idBusca);


	/**
	 * Executa procedure de criação do match com doador associado.
	 * @param idTarefa do doador associado.
	 */
	void executarProcedureMatchDoadorPorIdTarefa(Long idTarefa);

	/**
	 * Executa procedure de criação do match com paciente associado.
	 * @param idTarefa do paciente associado.
	 */
	void executarProcedureMatchPacientePorIdTarefa(Long idTarefa);

	/**
	 * Executa procedure de criação do match com paciente associado.
	 * @param idDoador do paciente associado.
	 */
	void executarProcedureMatchDoadorPorIdDoador(Long idDoador);

	/**
	 * Executa procedure de criação do match com paciente associado.
	 * @param rmr do paciente associado.
	 */
	void executarProcedureMatchPacientePorRmr(Long rmr);


	/**
	 * Obtém o match pelo identificador e verifica se está disponibilizado.
	 * Caso não esteja disponibilizado lança um exceção.
	 * 
	 * @param idMatch - identificador do match.
	 * @return Match disponibilizado.
	 */
	Match obterMatchDisponibilizado(Long id);
	
	
	/**
	 * Criar a tarefa de cadastrar_prescrição para o match informado.
	 * 
	 * @param match O Match com o doador. 
	 */
	void criarTarefaCadastrarPrescricao(Match match);

	/**
	 * Criar a match com as informações do wmda.
	 * 
	 * @param match Objeto MatchWmdaDTO.
	 * @return Match. 
	 */
	Match criarMatchWmda(MatchWmdaDTO match);

	/**
	 * Atualizar match com as informações do wmda.
	 * 
	 * @param match Objeto Match.
	 * @param matchWmdaDto Objeto MatchWmdaDTO.
	 * @return Match. 
	 */
	Match atualizarMatchWmda(Match match, MatchWmdaDTO matchWmdaDto);
	
}