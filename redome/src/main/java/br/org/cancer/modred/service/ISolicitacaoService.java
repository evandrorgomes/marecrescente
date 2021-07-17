package br.org.cancer.modred.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.dto.ResultadoPedidoInternacionalDTO;
import br.org.cancer.modred.controller.dto.SolicitacaoInternacionalDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades para criação de solicitações de pedidos de enriquecimento e de fase 2, 3.
 * 
 * @author Fillipe Queiroz
 */
public interface ISolicitacaoService extends IService<Solicitacao, Long> {

	/**
	 * Cria a solicitação de workup de acordo com o tipo de doador.
	 * 
	 * @param rmr paciente relacionado a solicitação (match)
	 * @param idDoador Doador relacionado a solicitação (mach)
	 * @return solicitação criada.
	 */
	@Deprecated
	Solicitacao criarSolicitacaoWorkup(Long rmr, Long idDoador);

	/**
	 * Cancela as solicitações, todos os pedidos associados e os matchs em que o doador está participando.
	 * 
	 * @param idDoador identificação do doador.
	 */
	void cancelarReferenciasDoador(Long idDoador);

	/**
	 * Obtém uma solicitacao por id.
	 * 
	 * @param solicitacaoId - identificador
	 * @return entidade solicitacao
	 */
	Solicitacao obterPorId(Long solicitacaoId);

	/**
	 * Obtem solicitação em aberto por idDoador e tipo de solicitação.
	 * 
	 * @param idDoador - id do doador.
	 * @param idTipoSolicitacao - id do tipo de soliciatação.
	 * @return solicitação localizada pelos parametros.
	 */
	Solicitacao obterSolicitacaoEmAbertoPor(Long idDoador, Long idTipoSolicitacao);

	/**
	 * Cancela a solicitação por rmr, idDoador e tipo.
	 * 
	 * @param idSolicitacao - id da solicitação.
	 * @param justificativa - justificativa para o cancelamento de fase 2 internacional
	 * @param dataCancelamento - Data de cancelamento de fase 3 internacional
	 * @param motivoStatusId - Identificador do motivo de inativação do doador internacional para as fases 2 e 3.
	 * @param timeRetornoInatividade - tempo de retorno do doador internacional para as fases 2 e 3.
	 * @return solicitacao - solicitacao cancelada.
	 * 
	 */
	Solicitacao cancelarSolicitacao(Long idSolicitacao, String justificativa, LocalDate dataCancelamento, 
			Long motivoStatusId, Long timeRetornoInatividade);

	/**
	 * Consulta solicitações por idDoador do doador.
	 * 
	 * @param idDoador - id do doador.
	 * @return listagem de soliciçãoes por idDoador.
	 */
	List<Solicitacao> obterSolicitacoesEmAbertoPorIdDoador(Long idDoador);

	/**
	 * Obtem a solicitação associada ao pedido de exame.
	 * 
	 * @param pedidoExameId ID do pedido de exame.
	 * @return solicitação associada ao pedido.
	 */
	Solicitacao obterSolicitacaoPorPedidoExame(Long pedidoExameId);

	/**
	 * Método para gerar uma solicitação de fase 3 internacional.
	 * 
	 * @param idMatch
	 */
	void solicitarFase3Internacional(Long idMatch);
	
	
	/**
	 * Método para gerar solicitação de envio de paciente para emdis.
	 * 
	 * @param busca referenciada do paciente ao qual deseja enviar para o emdis.
	 * @return solicitacao salva. 
	 */
	Solicitacao solicitarPedidoDeEnvioPacienteEmdis(Busca busca);

	/**
	 * Retorna uma Solicitacao de doador Internacional com os pedidos de exames solicitados.
	 * 
	 * @param idSolicitacao - identificador de solicitação
	 * @return SolicitacaoInternacionalDTO
	 */
	SolicitacaoInternacionalDTO obterPedidoExamePorSolicitacaoId(Long idSolicitacao);

	/**
	 * Retorna o id do doador pelo id da solicitacao.
	 * 
	 * @param idSolicitacao - identificador da solicitação.
	 */
	Long recuperarIdDoadorPorSolicitacao(Long idSolicitacao);
	
	/**
	 * Retorna detalhes do pedido de CT e de IDM (basicamente os IDs)
	 * para que possam ser utilizado como vínculo de acesso entre as telas.
	 * 
	 * @param idSolicitacao ID da solicitação associada aos pedidos.
	 * @return detalhes da relação em CT e IDM.
	 */
	ResultadoPedidoInternacionalDTO obterDetalhesPedidoCtPedidoIdmInternacional(Long idSolicitacao);
	
	/**
	 * Obtém o doador a partir da solicitação.
	 * 
	 * @param solicitacao solicitação a ser pesquisada.
	 * @return doador associado. 
	 */
	Doador obterDoador(Solicitacao solicitacao);

	/**
	 * Método para gerar uma solicitação de fase 2 nacional.
	 * 
	 * @param idMatch
	 * @param idTipoExame
	 */
	void solicitarFase2Nacional(Long idMatch, Long idTipoExame);

	/**
	 * Método para gerar uma solicitação de fase 2 internacional.
	 * 
	 * @param idMatch
	 * @param locusSolicitados
	 */
	void solicitarFase2Internacional(Long idMatch, List<Locus> locusSolicitados);

	/**
	 * Método para gerar uma solicitação de fase 3 nacional.
	 * 
	 * @param idMatch
	 * @param idLaboratorio
	 * @param resolverDivergencia
	 */
	void solicitarFase3Nacional(Long idMatch, Long idLaboratorio, Boolean resolverDivergencia);

	/**
	 * Método para gerar uma solicitação de fase 3 para paciente.
	 * 
	 * @param idBusca
	 * @param idLaboratorio
	 * @param idTipoExame
	 */
	void solicitarFase3Paciente(Long idBusca, Long idLaboratorio, Long idTipoExame);

	/**
	 * Método para obter o id da solicitação do doador.
	 * 
	 * @param idMatch - identificação do match
	 * @return identificação da solicitação
	 */
	Long obterIdSolicitacaoPorIdMatch(Long idMatch);
	
	List<Solicitacao> obterSolicitacoesEmFechadaPorIdDoador(Long idDoador);
	
	Solicitacao obterSolicitacaoEmFechadaPorIdDoador(Long idDoador, Integer idStatus);
	
	Solicitacao obterSolicitacaoPorIdDoadorComIdStatusConcluidoECancelado(Long idDoador);
	
	Long obterQuantidadeSolicitacoesAbertosPorIdBuscaAndTiposDoador(Long idBusca, List<TiposDoador> tiposDoador);
	
	/**
	 * Quantidade de solicitações em aberto dos matchs de uma busca especifica e por tipo de solicitacao.
	 * 
	 * @param idBusca - identificador da busca
	 * @param tiposSolicitcao - identificador dos tipos de solicitacao
	 * @return quantidade total de solicitações em aberto dos matchs de uma busca e por tipo de solicitação
	 */
	Long quantidadeSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(Long idBusca, List<TiposSolicitacao> tiposSolicitacao);
	
	/**
	 * Lista de solicitações em aberto dos matchs de uma busca especifica e por tipo de solicitacao.
	 * 
	 * @param idBusca - identificador da busca
	 * @param tiposSolicitcao - identificador dos tipos de solicitacao
	 * @return lista de solicitações em aberto dos matchs de uma busca e por tipo de solicitação
	 */
	List<Solicitacao> listarSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(Long idBusca, List<TiposSolicitacao> tiposSolicitacao);

	/**
	 * Altera o status da solicitacao para "CONCLUIDA".
	 * 
	 * @param idSolicitacao Identificador da solicitacao.
	 */
	void fecharSolicitacao(Long idSolicitacao);

	/**
	 * Reponsável por criar uma solicitacao de workup para doador nacional para paciente nacional.
	 * Caso já exista a solicitação para o paciente relacionada com a busca deste match retorna uma exceção.
	 * 
	 * @param idMatch - identificador do match.
	 * @return solicitação criada.
	 */
	Solicitacao criarSolicitacaoWorkupDoadorNacionalPacienteNacional(Long idMatch);
		
	/**
	 * Reponsável por criar uma solicitacao de workup para doador internacional para paciente nacional.
	 * Caso já exista a solicitação para o paciente relacionada com a busca deste match retorna uma exceção.
	 * 
	 * @param idMatch - identificador do match.
	 * @return solicitação criada.
	 */
	Solicitacao criarSolicitacaoWorkupDoadorInternacional(Long idMatch);
	
	/**
	 * Reponsável por criar uma solicitacao de workup para cordão nacional para paciente nacional.
	 * Caso já exista uma solicitação de workup de medula, ou que a quantidade máxima de solicitações 
	 * de workup de cordão,para o paciente relacionada com a busca deste match, retorna uma exceção.
	 * 
	 * @param idMatch - identificador do match.
	 * @return solicitação criada.
	 */
	Solicitacao criarSolicitacaoWorkupCordaoNacionalPacienteNacional(Long idMatch);
	
	/**
	 * Reponsável por criar uma solicitacao de workup para cordão internacional para paciente nacional.
	 * Caso já exista uma solicitação de workup de medula, ou que a quantidade máxima de solicitações 
	 * de workup de cordão,para o paciente relacionada com a busca deste match, retorna uma exceção.
	 * 
	 * @param idMatch - identificador do match.
	 * @return solicitação criada.
	 */
	Solicitacao criarSolicitacaoWorkupCordaoInternacional(Long idMatch);

	/**
	 * Método para obter a solicitação pelo identificador. 
	 * 
	 * @param id Identificador da solicitação.
	 * @return solicitacao se encontrada ou throw BusinessException se não existir.
	 */
	Solicitacao obterSolicitacao(Long id);
	
	/**
	 * Método para obter a solicitação pelo identificador do centro de transplante e status da solicitacao. 
	 * 
	 * @param id Identificador do centro de transplante.
	 * @param Pageable pageable - paginação
	 * @return lista de solicitacao se encontrada ou throw BusinessException se não existir.
	 */
	Paginacao<Solicitacao> listarSolicitacoesPorCentroTransplanteEStatusSolicitacao(Long idCentroTransplante, String[] statusSolicitacao, Pageable pageable);

	/**
	 * Lista as solicitações.
	 * 
	 * @param tipos  Lista dos tipos de solicitação.
	 * @param status Lista dos status da solcitação. 
	 * @return List<Solicitacao> Lista de solicitações agruapdos por usuário com perfil Anslista
	 */
	List<Solicitacao> listarSolicitacoesPorTiposSolicitacaoEStatus(String[] tipos, String[] status);

	/**
	 * Lista as solicitações.
	 * 
	 * @param tipos  Lista dos tipos de solicitação.
	 * @param status Lista dos status da solcitação.
	 * @param status Lista dos fases da solcitação.
	 * @return List<Solicitacao> Lista de solicitações agruapdos por usuário
	 */
	List<Solicitacao> listarSolicitacoesPorTiposSolicitacaoPorStatusEFasesWorkup(String[] tipos, String[] status, String[] fasesWorkup);
	
	/**
	 * Atribui na solicitação o usuário responsável. 
	 * 
	 * @param id Identificador da solicitação
	 * @param idUsuario  Identificador do usuário
	 * @return Solcitacao
	 */
	Solicitacao atribuirUsuarioResponsavel(Long id, Long idUsuario);

	/**
	 * Atualiza a fase do workup na solicitação.
	 * 
	 * @param id Identificação da solicitação.
	 * @param idFaseWorkup Identificdor da fase.
	 * @return Solicitação.
	 */
	Solicitacao atualizarFaseWorkup(Long id, Long idFaseWorkup);

	/**
	 * Atribui o centro de coleta definido pelo analista de workup na solicitação.
	 * 
	 * @param id Identificação da solicitação
	 * @param idCentroColeta Identificador do centro de cleta.
	 * @return Solicitação
	 */
	Solicitacao atribuirCentroColeta(Long id, Long idCentroColeta);
	
	
	/**
	 * Atualiza a pos coleta na solicitação.
	 * 
	 * @param id Identificação da solicitação.
	 * @param posColeta Identifica que tem poscoleta.
	 * @return Solicitação.
	 */
	Solicitacao atualizarSolicitacaoPosColeta(Long id, Long posColeta);
	
	/**
	 * Atualiza a Contagem Celula na solicitação.
	 * 
	 * @param id Identificação da solicitação.
	 * @param posColeta Identifica que tem poscoleta.
	 * @return Solicitação.
	 */

	Solicitacao atualizarSolicitacaoContagemCelula(Long id);
	
}
