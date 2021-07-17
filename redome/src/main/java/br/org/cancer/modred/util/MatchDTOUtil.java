package br.org.cancer.modred.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.stream.Collectors;

import br.org.cancer.modred.configuration.ApplicationContextProvider;
import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.TipoPermissividade;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IBuscaChecklistService;
import br.org.cancer.modred.service.IEvolucaoService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.IPermissaoService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.ITipoPermissividadeService;
import br.org.cancer.modred.service.impl.BuscaChecklistService;
import br.org.cancer.modred.service.impl.EvolucaoService;
import br.org.cancer.modred.service.impl.PedidoExameService;
import br.org.cancer.modred.service.impl.PermissaoService;
import br.org.cancer.modred.service.impl.SolicitacaoService;
import br.org.cancer.modred.service.impl.TipoPermissividadeService;

/**
 * Classe Util para montagem do MatchDTO.
 * 
 * @author brunosousa
 *
 */
public class MatchDTOUtil {
	
	private MatchDTOUtil() {
	}
	
	/**
	 * Método responsável por popular o matchDTO com os dados retornados da view MATCH_DTO e MATCH_PRELIMINAR_DTO.
	 * 
	 * @param match Vetor com os dados do match.
	 * @return MatchDTO
	 */
	public static MatchDTO popularMatchDTO(Object[] match) {
		final Long rmr;
		if (match[27] != null) {
			rmr = Long.parseLong(match[27].toString());
		}
		else {
			rmr = null;
		}
		final Long buscaId = Long.parseLong(match[28].toString());
		final TiposDoador tipoDoador = TiposDoador.get(Long.parseLong(match[23].toString()));

		MatchDTO matchDTO = new MatchDTO();
		matchDTO.setId(Long.parseLong(match[0].toString()));
		matchDTO.setRegistroOrigem(match[3].toString());
		if (match[11] != null) {
			matchDTO.setMismatch(match[11].toString());
		}
		if (match[15] != null) {
			matchDTO.setClassificacao(match[15].toString());
		}
		
		if (match[8] != null) {
			matchDTO.setDataAtualizacao(((Timestamp) match[8]).toLocalDateTime());			
		}

		if (match[12] != null) {
			matchDTO.setOutrosProcessos(Integer.parseInt(match[12].toString()));
		}
		
		matchDTO.setPossuiComentario(Boolean.parseBoolean(match[14].toString()));
		matchDTO.setPossuiRessalva(Boolean.parseBoolean(match[13].toString()));
		matchDTO.setPossuiGenotipoDivergente(Boolean.parseBoolean(match[29].toString()) );
		matchDTO.setTemPrescricao(Boolean.parseBoolean(match[16].toString()));
				
		matchDTO.setSexo(match[6].toString());
		if (match[10] != null) {
			matchDTO.setAbo(match[10].toString() );
		}
		if (match[7] != null) {
			matchDTO.setDataNascimento(((Timestamp) match[7]).toLocalDateTime().toLocalDate());			
		}
		
		matchDTO.setIdDoador(Long.parseLong(match[1].toString()));
		matchDTO.setTipoDoador(tipoDoador.getId());
		matchDTO.setNome("");
		
		if (match[17] != null) {
			matchDTO.setIdProcesso(Long.parseLong(match[17].toString()));			
		}

		if (match[18] != null) {
			matchDTO.setIdPedidoExame(Long.parseLong(match[18].toString()));			
		}
		if (match[19] != null) {
			matchDTO.setIdSolicitacao(Long.parseLong(match[19].toString()));			
		}
		
		obterPedidoExameOuSolicitacaoSeExistir(rmr, matchDTO);
				
		popularMatchDTOComDoador(match, rmr, tipoDoador, matchDTO);
		
		IPermissaoService permissaoService = ApplicationContextProvider.obterBean(PermissaoService.class);
		
		if (permissaoService.usuarioLogadoPossuiPermissao(Recurso.VISTAR_CHECKLIST))  {		
			IBuscaChecklistService buscaChecklistService = ApplicationContextProvider.obterBean(BuscaChecklistService.class);
			matchDTO.setBuscaChecklist(buscaChecklistService.listarChecklist(buscaId, matchDTO.getId()));
		}
		
		matchDTO.setFase(match[26].toString());
		matchDTO.setTipoMatch(match[31].toString());
		
		if (match[32] != null) {
			matchDTO.setDisponibilizado(Long.parseLong(match[32].toString()) == 1? true: false);
		}
		
		if (match[33] != null) {
			matchDTO.setTipoSolicitacao(Long.parseLong(match[33].toString()));
		}

		if (match[34] != null) {
			matchDTO.setStatusDoador(new StatusDoador(Long.parseLong(match[34].toString()), match[35].toString()));
		}
		
		if (match[36] != null) {
			matchDTO.setRespostaQtdGestacaoDoador(Long.parseLong(match[36].toString()));
		}
		
		if(match[37] != null) {
				matchDTO.setSomaPesoQualificacao(Integer.parseInt(match[37].toString()));
		}

		if(match[38] != null) {
			ITipoPermissividadeService tipoPermissividadeService = ApplicationContextProvider.obterBean(TipoPermissividadeService.class);
			TipoPermissividade tipoPermissividade = tipoPermissividadeService.findById(Long.parseLong(match[38].toString()));
			matchDTO.setTipoPermissividade(tipoPermissividade);
		}
		if(match[39] != null) {
			matchDTO.setOrdenacaoWmdaMatch(Integer.parseInt(match[39].toString()));
		}
		
		
		return matchDTO;
	}

	/**
	 * Método para popular o matchdto com os dados do doador.
	 * 
	 * @param match
	 * @param rmr
	 * @param tipoDoador
	 * @param matchDTO
	 */
	private static void popularMatchDTOComDoador(Object[] match, final Long rmr, final TiposDoador tipoDoador,
			MatchDTO matchDTO) {
		if (tipoDoador.equals(TiposDoador.INTERNACIONAL) || 
				tipoDoador.equals(TiposDoador.CORDAO_INTERNACIONAL)) {
			
			setarIdTarefaSePossivel(matchDTO, rmr);
			if (TiposDoador.CORDAO_INTERNACIONAL.equals(tipoDoador)) {
				popularMatchDTOComCordaoInternacional(match, rmr, matchDTO);
			}
			else {
				popularMatchDTOComDoadorInternacional(match, matchDTO);
			}
		}
		else if (tipoDoador.equals(TiposDoador.NACIONAL)) {
			popularMatchDTOComDoadorNacional(match, matchDTO);
		}
		else if (tipoDoador.equals(TiposDoador.CORDAO_NACIONAL)) {
			popularMatchDTOComCordaoNacional(match, rmr, matchDTO);
		}
	}

	/**
	 * Método para popular o matchdto com os dados do cordão nacional.
	 * 
	 * @param match
	 * @param rmr
	 * @param matchDTO
	 */
	private static void popularMatchDTOComCordaoNacional(Object[] match, final Long rmr, MatchDTO matchDTO) {
		matchDTO.setIdBscup(match[24].toString() );
		
		final BigDecimal quantidadeTotalTCN = (BigDecimal) match[21];
		final BigDecimal quantidadeTotalCD34 = (BigDecimal) match[22];
		
		BigDecimal peso = null;
		if (match[31].toString().equals("MATCH") ) {
			IEvolucaoService evolucaoService = ApplicationContextProvider.obterBean(EvolucaoService.class);
			final Evolucao evolucao = evolucaoService.obterUltimaEvolucaoDoPaciente(rmr);
			peso = evolucao.getPeso();
		}
		else if (match[31].toString().equals("MATCHPRELIMINAR") ) {
			if (match[30] != null) {
				peso = (BigDecimal) match[30];
			}
			else {
				peso = new BigDecimal(1);
			}
		}
		
		matchDTO.setQuantidadeTCNPorKilo(quantidadeTotalTCN.divide(peso, 2, RoundingMode.HALF_EVEN));
		matchDTO.setQuantidadeCD34PorKilo(quantidadeTotalCD34.divide(peso, 2, RoundingMode.HALF_EVEN));
	}

	/**
	 * Método para popular o matchdto com os dados do doador nacional.
	 * 
	 * @param match
	 * @param matchDTO
	 */
	private static void popularMatchDTOComDoadorNacional(Object[] match, MatchDTO matchDTO) {
		matchDTO.setDmr(Long.parseLong(match[4].toString()));
		if (match[5] != null) {
			matchDTO.setNome(match[5].toString());
		}
		if (match[9] != null) {
			matchDTO.setPeso((BigDecimal) match[9]);
		}
	}

	/**
	 * Método para popular o matchdto com os dados do doador internacional.
	 * 
	 * @param match
	 * @param matchDTO
	 */
	private static void popularMatchDTOComDoadorInternacional(Object[] match, MatchDTO matchDTO) {
		matchDTO.setIdRegistro(match[2].toString());
		matchDTO.setPeso((BigDecimal) match[9]);
	}

	/**
	 * Método para popular o matchdto com os dados do cordao interncional.
	 * 
	 * @param match
	 * @param rmr
	 * @param matchDTO
	 */
	private static void popularMatchDTOComCordaoInternacional(Object[] match, final Long rmr, MatchDTO matchDTO) {
		matchDTO.setIdRegistro(match[2].toString());
				
		final BigDecimal quantidadeTotalTCN = (BigDecimal) match[21];
		final BigDecimal quantidadeTotalCD34 = (BigDecimal) match[22];
		
		BigDecimal peso = null;
		if (match[31].toString().equals("MATCH") ) {
			IEvolucaoService evolucaoService = ApplicationContextProvider.obterBean(EvolucaoService.class);
			final Evolucao evolucao = evolucaoService.obterUltimaEvolucaoDoPaciente(rmr);
			peso = evolucao.getPeso();
		}
		else if (match[31].toString().equals("MATCHPRELIMINAR") ) {
			if (match[30] != null) {
				peso = (BigDecimal) match[30];
			}
			else {
				peso = new BigDecimal(1);
			}
		}
		
		final BigDecimal quantidadeTcnPorKg = quantidadeTotalTCN.divide(peso, 2, RoundingMode.HALF_EVEN);
		matchDTO.setQuantidadeTCNPorKilo(quantidadeTcnPorKg);
		
		final BigDecimal quantidadeCd34PorKg = quantidadeTotalCD34.divide(peso, 2, RoundingMode.HALF_EVEN);
		matchDTO.setQuantidadeCD34PorKilo(quantidadeCd34PorKg);
	}

	/**
	 * Método para popular o matchdto com os dados do pedido de exame ou solicitacao.
	 * 
	 * @param rmr
	 * @param matchDTO
	 */
	private static void obterPedidoExameOuSolicitacaoSeExistir(final Long rmr, MatchDTO matchDTO) {
		if(matchDTO.getIdPedidoExame() != null){
			popularMatchDTOComPedidoExame(rmr, matchDTO);
		}
		else if (matchDTO.getIdSolicitacao() != null) {
			popularMatchDTOcomSolicitacao(rmr, matchDTO);
		}
	}

	/**
	 * Método para popular o matchdto com os dados da solicitação se não existir ainda o pedido de exame.
	 * ex: Solicitacao de fase para doador nacional onde primeiro é criada o pedido de enriquecimento.
	 * 
	 * @param rmr
	 * @param matchDTO
	 */
	private static void popularMatchDTOcomSolicitacao(final Long rmr, MatchDTO matchDTO) {
		ISolicitacaoService solicitacaoService = ApplicationContextProvider.obterBean(SolicitacaoService.class);
		Solicitacao solicitacao = solicitacaoService.obterPorId(matchDTO.getIdSolicitacao());
		if (solicitacao.getTipoExame() != null && solicitacao.getTipoExame().getLocus() != null ) {
			String locus = solicitacao.getTipoExame().getLocus().stream().map(Locus::getCodigo).collect(Collectors.joining(", "));				
			Long rmrPedidoExame = solicitacao.getMatch().getBusca().getPaciente().getRmr();
			
			if(rmrPedidoExame.equals(rmr)){
				matchDTO.setLocusPedidoExameParaPaciente(locus);
			}
			else{
				matchDTO.setLocusPedidoExame(locus);
				matchDTO.setRmrPedidoExame(rmrPedidoExame);
			}				
		}
	}

	/**
	 * Método para popular o matchdto com os dados do do pedido de exame.
	 * 
	 * @param rmr
	 * @param matchDTO
	 */
	private static void popularMatchDTOComPedidoExame(final Long rmr, MatchDTO matchDTO) {
		IPedidoExameService pedidoExameService = ApplicationContextProvider.obterBean(PedidoExameService.class);
		PedidoExame pedidoExame = pedidoExameService.obterPedidoExame(matchDTO.getIdPedidoExame());
		if(pedidoExame.getLocusSolicitados() != null){
			String locus = pedidoExame.getLocusSolicitados().stream().map(Locus::getCodigo).collect(Collectors.joining(", "));
			
			Long rmrPedidoExame = pedidoExame.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
			
			if(rmrPedidoExame.equals(rmr)){
				matchDTO.setLocusPedidoExameParaPaciente(locus);
			}
			else{
				matchDTO.setLocusPedidoExame(locus);
				matchDTO.setRmrPedidoExame(rmrPedidoExame);
			}
		}
	}

	private static void setarIdTarefaSePossivel(MatchDTO matchDTO, Long rmr) {
		if (matchDTO.getIdPedidoExame() != null) {
			TarefaDTO tarefa = TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.getConfiguracao()
					.obterTarefa()
					.comObjetoRelacionado(matchDTO.getIdPedidoExame())
					.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
					.comRmr(rmr)
					.apply();
			if (tarefa != null) {
				matchDTO.setIdTarefa(tarefa.getId());
				matchDTO.setIdTipoTarefa(tarefa.getTipo().getId());
			}
			else {

				tarefa = TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.getConfiguracao()
						.obterTarefa()
						.comObjetoRelacionado(matchDTO.getIdPedidoExame())
						.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
						.comRmr(rmr)
						
						.apply();
				if (tarefa != null) {
					matchDTO.setIdTarefa(tarefa.getId());
					matchDTO.setIdTipoTarefa(tarefa.getTipo().getId());
				}
			}
			
		}
	}
	

}
