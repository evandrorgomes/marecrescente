package br.org.cancer.modred.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.ExameDoadorInternacionalDto;
import br.org.cancer.modred.controller.dto.PedidoDto;
import br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO;
import br.org.cancer.modred.controller.dto.doador.OrigemPagamentoDoadorDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CordaoInternacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameCordaoInternacional;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoIdm;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.model.RessalvaDoador;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.TipoExame;
import br.org.cancer.modred.model.domain.StatusExame;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposExame;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.ICordaoInternacionalService;
import br.org.cancer.modred.service.IDoadorService;
import br.org.cancer.modred.service.IExameDoadorService;
import br.org.cancer.modred.service.IExecutarProcedureMatchService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.IPedidoIdmService;
import br.org.cancer.modred.service.IReservaDoadorInternacionalService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.Projection;

/**
 * Classe de funcionalidades envolvendo a entidade CordaoInternacional.
 * 
 * @author bruno.sousa.
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CordaoInternacionalService extends DoadorService<CordaoInternacional> implements ICordaoInternacionalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CordaoInternacionalService.class);

	@Autowired
	private IBuscaService buscaService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IGenotipoDoadorService<ExameCordaoInternacional> genotipoService;
	
	@Autowired
	private IReservaDoadorInternacionalService reservaDoadorInternacionalService;
	
	@Autowired
	private IExameDoadorService<ExameCordaoInternacional> exameService;

	@Autowired
	private IDoadorService doadorService;
	
	@Autowired
	private IPedidoIdmService pedidoIdmService;

	@Autowired
	private IExecutarProcedureMatchService executarProcedureMatchService;

	@Override
	public String obterIdRegistroPorIdDoador(Long idDoador) {
		Filter<Long> filtrarPorId = new Equals<Long>("id", idDoador);
		Projection projectionDmr = new Projection("idRegistro");

		CordaoInternacional cordaoInternacional = 
				(CordaoInternacional) findOne(Arrays.asList(projectionDmr), Arrays.asList(filtrarPorId));
		return cordaoInternacional.getIdRegistro();
	}

	/* (non-Javadoc)
	 * @see br.org.cancer.modred.service.ICordaoInternacionalService#salvarCordaoInternacionalComPedidoExameSeSolicitado(br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO, br.org.cancer.modred.controller.dto.PedidoDto)
	 */
	@Override
	public CordaoInternacional salvarCordaoInternacionalComPedidoExameSeSolicitado(DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO, PedidoDto pedido) {
		
		CordaoInternacional cordaoInternacional = salvarCordaoInternacional(converterParaCordaoInternacional(doadorCordaoInternacionalDTO));
		
		if (doadorCordaoInternacionalDTO.getRmrAssociado() != null ) {
			executarProcedureMatchService.gerarMatchDoador(cordaoInternacional, pedido, doadorCordaoInternacionalDTO.getRmrAssociado());
			
		}
		
		reservarParaPacienteInformado(doadorCordaoInternacionalDTO.getRmrAssociado(), cordaoInternacional);

		return cordaoInternacional;
	}
	
	private CordaoInternacional salvarCordaoInternacional(CordaoInternacional cordaoInternacional) {
		
		CordaoInternacional doadorSalvo = (CordaoInternacional) this.save(cordaoInternacional);
		genotipoService.gerarGenotipo(doadorSalvo, false);
		
		return doadorSalvo; 		
	}
	
	private void reservarParaPacienteInformado(Long rmr, CordaoInternacional cordaoInternacional) {		
		if (rmr != null ) {
			 Busca buscaAtiva = buscaService.obterBuscaAtivaPorRmr(rmr);
			if (buscaAtiva == null) {
				LOGGER.error("Paciente " + rmr + " não possui uma busca ativa. "
						+ "Por concorrência, ele pode ter sido inativo ou algo "
						+ "do gênero por outro usuário enquanto o doador era cadastrado para ele.");
				throw new BusinessException("paciente.sem.busca.ativa.mensagem", String.valueOf(rmr));
			}

			reservaDoadorInternacionalService.salvar(cordaoInternacional, buscaAtiva);
		}				
	}
	
	
	private CordaoInternacional converterParaCordaoInternacional(DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO) {

		CordaoInternacional cordaoInternacional = new CordaoInternacional();
		cordaoInternacional.setDataAtualizacao(LocalDateTime.now());
		cordaoInternacional.setIdRegistro(doadorCordaoInternacionalDTO.getIdRegistro() != null ? 
				doadorCordaoInternacionalDTO.getIdRegistro() : doadorCordaoInternacionalDTO.getGrid());
		cordaoInternacional.setAbo(doadorCordaoInternacionalDTO.getAbo());
		cordaoInternacional.setCadastradoEmdis(doadorCordaoInternacionalDTO.getCadastradoEmdis());
		cordaoInternacional.setDataRetornoInatividade(doadorCordaoInternacionalDTO.getDataRetornoInatividade());		
		cordaoInternacional.setGrid(doadorCordaoInternacionalDTO.getGrid());
		if (doadorCordaoInternacionalDTO.getRegistroOrigem() != null) {
			cordaoInternacional.setRegistroOrigem(new Registro(doadorCordaoInternacionalDTO.getRegistroOrigem().getId()));
		}
		if (doadorCordaoInternacionalDTO.getRegistroPagamento() != null) {
			cordaoInternacional.setRegistroPagamento(new Registro(doadorCordaoInternacionalDTO.getRegistroPagamento().getId()));
		}
		
		cordaoInternacional.setSexo(doadorCordaoInternacionalDTO.getSexo());
		cordaoInternacional.setDataNascimento(doadorCordaoInternacionalDTO.getDataNascimento());
		
		cordaoInternacional.setQuantidadeTotalCD34(doadorCordaoInternacionalDTO.getQuantidadeTotalCD34());
		cordaoInternacional.setQuantidadeTotalTCN(doadorCordaoInternacionalDTO.getQuantidadeTotalTCN());
		cordaoInternacional.setVolume(doadorCordaoInternacionalDTO.getVolume());
		
		if (CollectionUtils.isNotEmpty(doadorCordaoInternacionalDTO.getLocusExames())) {
			ExameCordaoInternacional exame = new ExameCordaoInternacional();
			exame.setDoador(cordaoInternacional);								
			exame.setStatusExame(StatusExame.CONFERIDO.getCodigo());
			exame.setLocusExames(
				doadorCordaoInternacionalDTO.getLocusExames().stream()
					.map(locusExameDto -> {
						return new LocusExame((Exame) exame, locusExameDto.getId().getLocus().getCodigo(), locusExameDto.getPrimeiroAlelo(), locusExameDto.getSegundoAlelo());
					})
					.collect(Collectors.toList())
			);
			
			cordaoInternacional.setExames(new ArrayList<>());
			cordaoInternacional.getExames().add(exame);			
		}

		if (CollectionUtils.isNotEmpty(doadorCordaoInternacionalDTO.getRessalvas())) {
			cordaoInternacional.setStatusDoador(new StatusDoador(StatusDoador.ATIVO_RESSALVA));
			final Usuario usuarioResponsavel = usuarioService.obterUsuarioLogado();
			cordaoInternacional.setRessalvas(
				doadorCordaoInternacionalDTO.getRessalvas().stream().map(ressalvaDto -> {
					RessalvaDoador ressalva = new RessalvaDoador(cordaoInternacional, ressalvaDto); 
					ressalva.setDataCriacao(LocalDate.now());
					ressalva.setUsuarioResponsavel(usuarioResponsavel);
					return ressalva;
				})
				.collect(Collectors.toList())
			);
		}
		else {
			if (doadorCordaoInternacionalDTO.getStatusDoador() != null && doadorCordaoInternacionalDTO.getStatusDoador().getId() != null) {
				cordaoInternacional.setStatusDoador(new StatusDoador(doadorCordaoInternacionalDTO.getStatusDoador().getId()));
			}
			else {
				cordaoInternacional.setStatusDoador(new StatusDoador(StatusDoador.ATIVO));
			}
		}
		
		return cordaoInternacional;
		
	}
	
	@Override
	public List<Doador> findAll(){
		return find(new Equals<Long>("tipoDoador", TiposDoador.CORDAO_INTERNACIONAL.getId()));
	}
	
	@Override
	public void atualizarOrigemPagamento(OrigemPagamentoDoadorDTO registroDto) {
		CordaoInternacional doadorIntSalvar = (CordaoInternacional) doadorService.findById(registroDto.getIdDoador());
		doadorIntSalvar.setRegistroOrigem(new Registro(registroDto.getIdRegistroOrigem()));
		doadorIntSalvar.setRegistroPagamento(new Registro(registroDto.getIdRegistroPagamento()));
		this.save(doadorIntSalvar);
	}

	@Override
	public List<ExameDoadorInternacionalDto> listarExamesCordaoInternacional(Long idDoador) {
		
		List<ExameDoadorInternacionalDto> listaResultado = new ArrayList<ExameDoadorInternacionalDto>();
		
		Long tipoExameHLA = 1L;
		Long tipoExameIDM = 2L;
		
		List<ExameCordaoInternacional> examesValidos = exameService.listarExamesPorDoador(idDoador);
		List<PedidoIdm> pedidosIdm = pedidoIdmService.listarPedidosPorDoador(idDoador);
		
		examesValidos  = examesValidos.stream().filter(e -> e.getStatusExame().equals(1)).collect(Collectors.toList());
		examesValidos.forEach(e->{
			ExameDoadorInternacionalDto dto = new ExameDoadorInternacionalDto();
			dto.setId(e.getId());
			dto.setLocusExames(e.getLocusExames());
			dto.setStatusExame(e.getStatusExame());
			dto.setTipoExame(tipoExameHLA);
			dto.setDataCriacao(e.getDataCriacao());
			listaResultado.add(dto);
		});
		pedidosIdm.forEach(p ->{
			ExameDoadorInternacionalDto dto = new ExameDoadorInternacionalDto();
			dto.setId(p.getId());
			dto.setStatusExame(StatusExame.CONFERIDO.getId());
			dto.setTipoExame(tipoExameIDM);
			dto.setCaminhoArquivo(!p.getArquivosResultado().isEmpty()? p.getArquivosResultado().stream().findAny().get().getCaminho(): null);
			dto.setDataCriacao(dto.getDataCriacao());
			listaResultado.add(dto);
		});
		return listaResultado;
	}

	@Override
	public void salvarExameCordaoInternacional(Long idDoador, ExameCordaoInternacional exame) throws Exception{
		CordaoInternacional cordaoInternacional = (CordaoInternacional) doadorService.findById(idDoador);
		exame.setStatusExame(StatusExame.CONFERIDO.getId());
		exame.setDoador(cordaoInternacional);
		
		exameService.salvar(null, exame, new TipoExame(TiposExame.TESTE_CONFIRMATORIO.getId()));
		genotipoService.gerarGenotipo(cordaoInternacional);
		Paciente pacienteAssociado = 
				reservaDoadorInternacionalService.obterPacienteAssociado(cordaoInternacional);
		
	}
	
	@Override
	public void atualizarDadosPessoais(Long id, CordaoInternacional cordao) {
		CordaoInternacional cordaoInternacionalRecuperado = (CordaoInternacional) obterDoador(id);
		cordaoInternacionalRecuperado.setIdRegistro(cordao.getIdRegistro());
		cordaoInternacionalRecuperado.setAbo(cordao.getAbo());
		cordaoInternacionalRecuperado.setGrid(cordao.getGrid());
		cordaoInternacionalRecuperado.setDataNascimento(cordao.getDataNascimento());
		cordaoInternacionalRecuperado.setSexo(cordao.getSexo());
		if (cordao.getDataRetornoInatividade() != null) {
			cordaoInternacionalRecuperado.setDataRetornoInatividade(cordao.getDataRetornoInatividade());
		}
		cordaoInternacionalRecuperado.setQuantidadeTotalCD34(cordao.getQuantidadeTotalCD34());
		cordaoInternacionalRecuperado.setQuantidadeTotalTCN(cordao.getQuantidadeTotalTCN());
		cordaoInternacionalRecuperado.setVolume(cordao.getVolume());
		
		this.save(cordaoInternacionalRecuperado);
	}
	
	
	@Override
	public void atualizar(Long id, DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO) throws Exception {
		
		CordaoInternacional cordaoInternacional =  converterParaCordaoInternacional(doadorCordaoInternacionalDTO);
		atualizarDadosPessoais(id, cordaoInternacional);
		if (!CollectionUtils.isEmpty(cordaoInternacional.getExames())) {
			salvarExame(id, cordaoInternacional.getExames().get(0));
		}
		
	}
	
	@Override
	public void salvarExame(Long idDoador, ExameCordaoInternacional exame) throws Exception {
		CordaoInternacional cordaoInternacional = (CordaoInternacional) obterDoador(idDoador);
		exame.setStatusExame(StatusExame.CONFERIDO.getId());
		exame.setDoador(cordaoInternacional);
		
		exameService.salvar(null, exame, new TipoExame(TiposExame.TESTE_CONFIRMATORIO.getId()));
		genotipoService.gerarGenotipo(cordaoInternacional);
		
	}
	
	@Override
	public Long obterIdentifiadorCordaoInternacionalPorIdDoRegistro(String idRegistro) {
		CordaoInternacional cordaoInternacional = obterCordaoInternacionalPorIdRegistro(idRegistro);
		return cordaoInternacional != null ? cordaoInternacional.getId() : 0L; 
	}
	
	@Override
	public CordaoInternacional obterCordaoInternacionalPorIdRegistro(String idRegistro) {
		Filter<String> filtrarPorIdRegistro = new Equals<String>("idRegistro", idRegistro);
		Projection projectionIdRegistro = new Projection("id");
		CordaoInternacional cordaoInternacional = 
				(CordaoInternacional) findOne(Arrays.asList(projectionIdRegistro), Arrays.asList(filtrarPorIdRegistro));
		return cordaoInternacional;
	}

	@Override
	public Long salvarDoadorCordaoInternacionalWmda(DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO) {
		CordaoInternacional doador = this.salvarCordaoInternacional(converterParaCordaoInternacional(doadorCordaoInternacionalDTO));
		return doador.getId();
	}
	
}
