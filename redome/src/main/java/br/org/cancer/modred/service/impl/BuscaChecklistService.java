package br.org.cancer.modred.service.impl;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.controller.dto.BuscaChecklistDTO;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.BuscaChecklist;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.TipoBuscaChecklist;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposBuscaChecklist;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IBuscaChecklistRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IBuscaChecklistService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;


/**
 * Implementação da classe de negócios de BuscaCheckListService.
 * @author Filipe Paes
 *
 */
@Service
public class BuscaChecklistService  extends AbstractLoggingService<BuscaChecklist, Long> implements IBuscaChecklistService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BuscaChecklistService.class);
	
	@Autowired
	private IBuscaChecklistRepository buscaChecklistRepository;
	
	@Autowired
	private IBuscaService buscaService;
	
	private IUsuarioService usuarioService;
	
	@Override
	public IRepository<BuscaChecklist, Long> getRepository() {
		return buscaChecklistRepository;
	}

	@Override
	public void criarItemCheckList(Busca busca, TiposBuscaChecklist tiposBuscaChecklist) {
		criarItemCheckList(busca, null, tiposBuscaChecklist);
	}
	
	@Override
	public void criarItemCheckList(Busca busca, Match match, TiposBuscaChecklist tiposBuscaChecklist) {
		BuscaChecklist buscaCheck = new BuscaChecklist();
		buscaCheck.setBusca(busca);
		buscaCheck.setMatch(match);
		buscaCheck.setTipoBuscaChecklist(new TipoBuscaChecklist(tiposBuscaChecklist.getId()));
		buscaCheck.setDataCriacao(LocalDateTime.now());
		buscaCheck.setVisto(false);
		buscaChecklistRepository.save(buscaCheck);
	}

	@Override
	public BuscaChecklist obterBuscaChecklist(Long idBusca, Long idTipoBuscaChecklist) {
		return findOne(new Equals<Long>("busca.id", idBusca), 
				new Equals<Long>("tipoBuscaChecklist.id", idTipoBuscaChecklist),
				new Equals<Boolean>("visto", Boolean.FALSE));
	}

	@Override
	public boolean existeBuscaChecklistEmAberto(Long idBusca, Long idTipoBuscaChecklist) {
		return !CollectionUtils.isEmpty(this.find(
				new Equals<Long>("busca.id", idBusca), 
				new Equals<Long>("tipoBuscaChecklist.id", idTipoBuscaChecklist),
				new Equals<Boolean>("visto", Boolean.FALSE)));
	}
	
	@Override
	public List<BuscaChecklistDTO> listarChecklist(Long idBusca, Long idMatch) {
		List<BuscaChecklist> listarBuscaChecklist = 
				idMatch == null ? buscaChecklistRepository.listarBuscaChecklist(idBusca) : buscaChecklistRepository.listarBuscaChecklist(idBusca, idMatch);

		if(CollectionUtils.isNotEmpty(listarBuscaChecklist)){
			return listarBuscaChecklist.stream()
					.map(BuscaChecklistDTO::new)
					.collect(Collectors.toList());
		}
		
		return null;
	}

	@Override
	public List<BuscaChecklistDTO> listarChecklist(Long idBusca) {
		return listarChecklist(idBusca, null);
	}

	@CreateLog(TipoLogEvolucao.BUSCA_CHECK_LIST_VISTADO)
	@Override
	public void marcarVisto(Long id) {
		List<UpdateSet<?>> setList = 
				Arrays.asList(new UpdateSet<Boolean>("visto", Boolean.TRUE), 
						new UpdateSet<LocalDateTime>("dataHoraVisto", LocalDateTime.now()),
						new UpdateSet<Usuario>("usuario", usuarioService.obterUsuarioLogado() ));
		List<Filter<?>> whereList = new ArrayList<>();
		
		whereList.add(new Equals<Long>("id", id));
		
		update(setList, whereList);
		
		BuscaChecklist buscaChecklist = this.findById(id);
		buscaService.alterarDataUltimaAnaliseTecnicaDeBusca(buscaChecklist.getBusca().getId());
	}

	@CreateLog(TipoLogEvolucao.BUSCA_CHECK_LIST_VISTADO)
	@Override
	public void marcarListaDeVistos(List<Long> listaIdsChecklists) {
		try {
			listaIdsChecklists.stream().forEach(idCheckList -> {
				marcarVisto(idCheckList);
			});
		}
		catch (Exception e) {
			LOGGER.error("Erro na altualização do visto do checklist." + e.getMessage());
		}
	}
	
	@Override
	public Long totalChecklistHistoricoPorSituacao(Long idBusca, String fase, List<TiposDoador> tiposDoador) {
		
		final List<Long> listaIdsTipoDoador = tiposDoador.stream().map(tipoDoador -> tipoDoador.getId()).collect(Collectors.toList());
		
		return buscaChecklistRepository.totalBuscaChecklistHistoricoMatchPorSituacao(idBusca, fase, listaIdsTipoDoador);
				
	}

	@Override
	public Paciente obterPaciente(BuscaChecklist entity) {
		return entity.getBusca().getPaciente();
	}

	@Override
	public String[] obterParametros(BuscaChecklist entity) {
		String[] retorno = new String[2];
		if(entity.getTipoBuscaChecklist().getId() == TiposBuscaChecklist.EXAME_SEM_RESULTADO_A_MAIS_DE_30_DIAS.getId()) {
			Doador doador = entity.getBusca().getMatchs().get(0).getDoador();
			retorno[0] = entity.getTipoBuscaChecklist().getDescricao();
			retorno[1] = " para o doador " + ((DoadorNacional) doador).getDmr().toString();
		}
		else if(entity.getTipoBuscaChecklist().getId() == TiposBuscaChecklist.DIALOGO_MEDICO.getId()) {
			retorno[0] = " Paciente - ";
			retorno[1] = entity.getTipoBuscaChecklist().getDescricao();
		}
		return retorno;
	}
	
	@Override
	public Page<BuscaChecklistDTO> listarChecklistPorAnalistaETipo(String loginAnalista, Long idTipoChecklist, PageRequest pageRequest) {
		return buscaChecklistRepository.listarBuscaChecklistPorAnalistaETipoChecklist(pageRequest, loginAnalista, idTipoChecklist)
				.map(BuscaChecklistDTO::new);
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@CreateLog(TipoLogEvolucao.ANALISE_BUSCA_CHECK_LIST_VISTADO)
	@Override
	public void analisarBuscaViaItemCheckList(Long idBusca) {
		buscaService.alterarDataUltimaAnaliseTecnicaDeBusca(idBusca);
	}

	@CreateLog(TipoLogEvolucao.BUSCA_CHECK_LIST_VISTADO)
	@Override
	public void vistarChecklistPorIdBuscaETipo(Long idBusca, Long idTipoBuscaChecklist) {
		if(existeBuscaChecklistEmAberto(idBusca, idTipoBuscaChecklist)) {
			marcarVisto(obterBuscaChecklist(idBusca, idTipoBuscaChecklist).getId());
		}
	}

	@Override
	public boolean existeBuscaChecklistEmAberto(Long idBusca, Long idTipoBuscaChecklist, Long idMatch) {
		return !CollectionUtils.isEmpty(this.find(
				new Equals<Long>("busca.id", idBusca), 
				new Equals<Long>("tipoBuscaChecklist.id", idTipoBuscaChecklist),
				new Equals<Boolean>("visto", Boolean.FALSE),
				new Equals<Long>("match.id", idMatch)));
	}

}
