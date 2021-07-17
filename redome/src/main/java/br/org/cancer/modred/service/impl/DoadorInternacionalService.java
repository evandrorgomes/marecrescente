package br.org.cancer.modred.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.ExameDoadorInternacionalDto;
import br.org.cancer.modred.controller.dto.PedidoDto;
import br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO;
import br.org.cancer.modred.controller.dto.doador.OrigemPagamentoDoadorDTO;
import br.org.cancer.modred.controller.dto.doador.RegistroDTO;
import br.org.cancer.modred.controller.dto.doador.StatusDoadorDTO;
import br.org.cancer.modred.controller.dto.doador.ValorGenotipoDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CordaoInternacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.Match;
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
import br.org.cancer.modred.persistence.IDoadorInternacionalRepository;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IDoadorInternacionalService;
import br.org.cancer.modred.service.IDoadorService;
import br.org.cancer.modred.service.IExameDoadorService;
import br.org.cancer.modred.service.IExecutarProcedureMatchService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IPedidoIdmService;
import br.org.cancer.modred.service.IReservaDoadorInternacionalService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.Projection;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de funcionalidades envolvendo a entidade DoadorInternacional.
 * 
 * @author bruno.sousa.
 *
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class DoadorInternacionalService extends DoadorService<DoadorInternacional> implements IDoadorInternacionalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DoadorInternacionalService.class);
	
	@Autowired
	private IDoadorInternacionalRepository doadorInternacionalRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IBuscaService buscaService;
	
	private IGenotipoDoadorService<ExameDoadorInternacional> genotipoService;

	@Autowired
	private IReservaDoadorInternacionalService reservaDoadorInternacionalService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IExameDoadorService<ExameDoadorInternacional> exameService;
	
	@Autowired
	private IDoadorService doadorService;
	
	@Autowired
	private IPedidoIdmService pedidoIdmService;
	
	@Autowired
	private IExecutarProcedureMatchService executarProcedureMatchService;
	
	@Autowired
	private IMatchService matchService;

	@Autowired
	private ISolicitacaoService solicitacaoService;
	
	/* (non-Javadoc)
	 * @see br.org.cancer.modred.service.IDoadorInternacionalService#salvarDoadorInternacionalComPedidoExameSeSolicitado(br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO, br.org.cancer.modred.controller.dto.PedidoDto)
	 */
	@Override	
	public DoadorInternacional salvarDoadorInternacionalComPedidoExameSeSolicitado(DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO, PedidoDto pedido) {
			
		DoadorInternacional doadorInternacional = salvarDoadorInternacional(converterParaDoadorInternacional(doadorCordaoInternacionalDTO));
		
		if (doadorCordaoInternacionalDTO.getRmrAssociado() != null ) {
		  executarProcedureMatchService.gerarMatchDoador(doadorInternacional, pedido,  doadorCordaoInternacionalDTO.getRmrAssociado());
  	    }
		 		
		reservarParaPacienteInformado(doadorCordaoInternacionalDTO.getRmrAssociado(), doadorInternacional);
				
		return doadorInternacional;
		
	}
	
	private void criarPedidoExameCasoSejaSolicitado(Doador doador, PedidoDto pedido, Long rmr) {
		if (rmr != null && pedido != null) {
			Match match = matchService.obterMatchAtivo(rmr, doador.getId());
			if (match == null) {
				LOGGER.error("Paciente " + rmr + " não possui match com o doador (" + ((IDoador) doador).getIdentificacao().toString() + "). ");
				//throw new BusinessException("erro.solicitacao.match_inexistente");
			}
			if (pedido.getTipoExame() != null) {
				if (TiposExame.TIPIFICACAO_HLA_ALTA_RESOLUCAO.getId().equals(pedido.getTipoExame())) {
					solicitacaoService.solicitarFase2Internacional(match.getId(), pedido.getLocus());
				}
				else if (TiposExame.TESTE_CONFIRMATORIO.getId().equals(pedido.getTipoExame())) {
					solicitacaoService.solicitarFase3Internacional(match.getId());
				}
			}	
		}
	}
	
	private DoadorInternacional salvarDoadorInternacional(DoadorInternacional doadorInternacional) {
		
		DoadorInternacional doadorSalvo = (DoadorInternacional) save(doadorInternacional);
		genotipoService.gerarGenotipo(doadorSalvo, false);
			
		return doadorSalvo; 		
		 
	}
	
	private void reservarParaPacienteInformado(Long rmr, DoadorInternacional doadorInternacional) {		
		if (rmr != null ) {
			 Busca buscaAtiva = buscaService.obterBuscaAtivaPorRmr(rmr);
			if (buscaAtiva == null) {
				LOGGER.error("Paciente " + rmr + " não possui uma busca ativa. "
						+ "Por concorrência, ele pode ter sido inativo ou algo "
						+ "do gênero por outro usuário enquanto o doador era cadastrado para ele.");
				throw new BusinessException("paciente.sem.busca.ativa.mensagem", String.valueOf(rmr));
			}

			reservaDoadorInternacionalService.salvar(doadorInternacional, buscaAtiva);
		}				
	}
	
	private DoadorInternacional converterParaDoadorInternacional(DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO) {
		
		DoadorInternacional doadorInternacional = new DoadorInternacional();
		doadorInternacional.setDataAtualizacao(LocalDateTime.now());
		doadorInternacional.setIdRegistro(doadorCordaoInternacionalDTO.getIdRegistro() != null ? 
				doadorCordaoInternacionalDTO.getIdRegistro() : doadorCordaoInternacionalDTO.getGrid());
		doadorInternacional.setAbo(doadorCordaoInternacionalDTO.getAbo());
		doadorInternacional.setCadastradoEmdis(doadorCordaoInternacionalDTO.getCadastradoEmdis());
		doadorInternacional.setDataRetornoInatividade(doadorCordaoInternacionalDTO.getDataRetornoInatividade());		
		doadorInternacional.setGrid(doadorCordaoInternacionalDTO.getGrid());
		doadorInternacional.setPeso(doadorCordaoInternacionalDTO.getPeso());
		if (doadorCordaoInternacionalDTO.getRegistroOrigem() != null) {
			doadorInternacional.setRegistroOrigem(new Registro(doadorCordaoInternacionalDTO.getRegistroOrigem().getId()));
		}
		if (doadorCordaoInternacionalDTO.getRegistroPagamento() != null) {
			doadorInternacional.setRegistroPagamento(new Registro(doadorCordaoInternacionalDTO.getRegistroPagamento().getId()));
		}
		doadorInternacional.setSexo(doadorCordaoInternacionalDTO.getSexo());
		if (doadorCordaoInternacionalDTO.getIdade() != null) {
			doadorInternacional.setDataNascimento(calcularDataNascimentoSimulada(doadorCordaoInternacionalDTO.getIdade()));
		}
		else {
			doadorInternacional.setDataNascimento(doadorCordaoInternacionalDTO.getDataNascimento());
		}
		
		if (CollectionUtils.isNotEmpty(doadorCordaoInternacionalDTO.getLocusExames())) {
			ExameDoadorInternacional exame = new ExameDoadorInternacional();
			exame.setDoador(doadorInternacional);								
			exame.setStatusExame(StatusExame.CONFERIDO.getCodigo());
			exame.setLocusExames(
				doadorCordaoInternacionalDTO.getLocusExames().stream()
					.map(locusExameDto -> {
						return new LocusExame((Exame) exame, locusExameDto.getId().getLocus().getCodigo(), locusExameDto.getPrimeiroAlelo(), locusExameDto.getSegundoAlelo());
					})
					.collect(Collectors.toList())
			);
			
			doadorInternacional.setExames(new ArrayList<>());
			doadorInternacional.getExames().add(exame);			
		}

		if (CollectionUtils.isNotEmpty(doadorCordaoInternacionalDTO.getRessalvas())) {
			doadorInternacional.setStatusDoador(new StatusDoador(StatusDoador.ATIVO_RESSALVA));
			final Usuario usuarioResponsavel = usuarioService.obterUsuarioLogado();
			doadorInternacional.setRessalvas(
				doadorCordaoInternacionalDTO.getRessalvas().stream().map(ressalvaDto -> {
					RessalvaDoador ressalva = new RessalvaDoador(doadorInternacional, ressalvaDto); 
					ressalva.setDataCriacao(LocalDate.now());
					ressalva.setUsuarioResponsavel(usuarioResponsavel);
					return ressalva;
				})
				.collect(Collectors.toList())
			);
		}
		else {
			if (doadorCordaoInternacionalDTO.getStatusDoador() != null && doadorCordaoInternacionalDTO.getStatusDoador().getId() != null) {
				doadorInternacional.setStatusDoador(new StatusDoador(doadorCordaoInternacionalDTO.getStatusDoador().getId()));
			}
			else {
				doadorInternacional.setStatusDoador(new StatusDoador(StatusDoador.ATIVO));
			}
		}
		
		return doadorInternacional;
		
	}
	

	/**
	 * Para doadores internacionais, por vezes, a idade é a única informação que recebemos. Para estes casos, visando manter a
	 * compatibilidade com os demais, a data de nascimento é simulada utilizando o ano de nascimento (aproximado) considerando o
	 * nascimento no primeiro dia do ano.
	 * 
	 * @param idade Idade do doador.
	 * @return data de nascimento simulada.
	 */
	private LocalDate calcularDataNascimentoSimulada(Integer idade) {
		Integer anoNascimentoAproximado = ( LocalDate.now().getYear() - idade );
		return LocalDate.of(anoNascimentoAproximado, 1, 1);
	}

	@Override
	protected List<CampoMensagem> validateEntity(Doador doador) {
		
		List<CampoMensagem> camposMensagem = super.validateEntity(doador);

		DoadorInternacional doadorInternacional = (DoadorInternacional) doador;
		
		if (verificarDoadorJaExistente(doadorInternacional)) {
			camposMensagem.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "doador.internacional.duplicidade.falha")));
		}

		camposMensagem.addAll(exameService.validarExames(doadorInternacional.getExames()));

		return camposMensagem;
	}

	@Override
	public boolean verificarDoadorJaExistente(DoadorInternacional doador) {
		boolean doadorJaExiste = false;

		Doador doadorMesmo = doadorInternacionalRepository.obterIdDoadorPorRegistroOrigem(
				doador.getIdRegistro(), doador.getRegistroOrigem().getId(), doador.getId());

		if (doadorMesmo != null) {
			doadorJaExiste = !doadorMesmo.getId().equals(doador.getId());
		}
		else{
			if (doador.getGrid() != null) {
				Long idDoadorMesmoGrid = doadorInternacionalRepository.obterIdDoadorPorGrid(doador.getGrid(), doador.getId());
				doadorJaExiste = idDoadorMesmoGrid != null && !idDoadorMesmoGrid.equals(doador.getId());
			}
		}
		return doadorJaExiste;
	}

	@Override
	public Page<DoadorInternacional> obterDoadoresInternacional(String idDoadorRegistro, Long idRegistro, String grid, Pageable page) {
		return doadorInternacionalRepository.obterDoadoresInternacionaisPaginado(page, idDoadorRegistro, idRegistro, grid);
	}

	@Override
	public String obterIdRegistroPorIdDoador(Long idDoador) {
		DoadorInternacional doadorInternacional = obterDoadorInternacionalPorId(idDoador);
		return doadorInternacional.getIdRegistro() ;
	}

	private DoadorInternacional obterDoadorInternacionalPorId(Long idDoador) {
		Filter<Long> filtrarPorId = new Equals<Long>("id", idDoador);
		Projection projectionDmr = new Projection("idRegistro");
		DoadorInternacional doadorInternacional = 
				(DoadorInternacional) findOne(Arrays.asList(projectionDmr), Arrays.asList(filtrarPorId));
		return doadorInternacional;
	}

	@Override
	public DoadorInternacional obterDoadorInternacionalPorIdRegistro(String idRegistro) {
		Filter<String> filtrarPorIdRegistro = new Equals<String>("idRegistro", idRegistro);
		Projection projectionIdRegistro = new Projection("id");
		DoadorInternacional doadorInternacional = 
				(DoadorInternacional) findOne(Arrays.asList(projectionIdRegistro), Arrays.asList(filtrarPorIdRegistro));
		return doadorInternacional;
	}	
	
	@Override
	public List<Doador> findAll(){
		return find(new Equals<Long>("tipoDoador", TiposDoador.INTERNACIONAL.getId()));
	}

	@Override
	public DoadorCordaoInternacionalDTO obterDoadorPorId(Long idDoador) {
		Doador doador = doadorService.findById(idDoador);
		if(doador.getTipoDoador().getId().equals(TiposDoador.INTERNACIONAL.getId())){
			DoadorInternacional doadorInternacional = (DoadorInternacional) doador;
			return obterDtoDoadorInternacional(doadorInternacional);
		}
		else{
			CordaoInternacional cordaoInternacional = (CordaoInternacional) doador;
			return obterDtoCordaoInternacional(cordaoInternacional);
		}
	}

	private DoadorCordaoInternacionalDTO obterDtoCordaoInternacional(CordaoInternacional cordaoInternacional) {
		DoadorCordaoInternacionalDTO doadorDto = new DoadorCordaoInternacionalDTO();
		doadorDto.setId(cordaoInternacional.getId());
		doadorDto.setAbo(cordaoInternacional.getAbo());
		doadorDto.setCadastradoEmdis(cordaoInternacional.getCadastradoEmdis());
		
		RegistroDTO registroOrigem = new RegistroDTO();
		registroOrigem.setId(cordaoInternacional.getRegistroOrigem().getId());
		registroOrigem.setNome(cordaoInternacional.getRegistroOrigem().getNome());
		doadorDto.setRegistroOrigem(registroOrigem);
		
		doadorDto.setDataNascimento(cordaoInternacional.getDataNascimento());
		doadorDto.setDataRetornoInatividade(cordaoInternacional.getDataRetornoInatividade());
		
		if(cordaoInternacional.getGenotipo() != null){
			List<ValorGenotipoDTO> valoresGenotipo = new ArrayList<ValorGenotipoDTO>();
			cordaoInternacional.getGenotipo().getValores().forEach(valor -> {
				
				Optional<ValorGenotipoDTO> valorGenotipoPorLocus = valoresGenotipo.stream().filter(valorJaIncluido -> {
					return valorJaIncluido.getLocus().equals(valor.getLocus().getCodigo());
				}).findFirst();
				
				ValorGenotipoDTO valorGenotipo = new ValorGenotipoDTO();
				
				if(valorGenotipoPorLocus.isPresent()){
					valorGenotipo = valorGenotipoPorLocus.get();
					valoresGenotipo.remove(valorGenotipo);
				}
				
				valorGenotipo.setLocus(valor.getLocus().getCodigo());
				
				if(valor.getPosicao() == 1L){
					valorGenotipo.setPrimeiroAlelo(valor.getAlelo());
				}
				else {
					valorGenotipo.setSegundoAlelo(valor.getAlelo());
				}
				valoresGenotipo.add(valorGenotipo);
			});
			
			doadorDto.setValoresGenotipo(valoresGenotipo);
		}
		
		doadorDto.setGrid(cordaoInternacional.getGrid());
		doadorDto.setIdRegistro(cordaoInternacional.getIdRegistro());
		
		RegistroDTO registroPagamento = new RegistroDTO();
		registroPagamento.setId(cordaoInternacional.getRegistroPagamento().getId());
		registroPagamento.setNome(cordaoInternacional.getRegistroPagamento().getNome());
		doadorDto.setRegistroPagamento(registroPagamento);
		
		doadorDto.setSexo(cordaoInternacional.getSexo());
		
		StatusDoadorDTO statusDoador = new StatusDoadorDTO();
		statusDoador.setId(cordaoInternacional.getStatusDoador().getId());
		statusDoador.setDescricao(cordaoInternacional.getStatusDoador().getDescricao());
		doadorDto.setStatusDoador(statusDoador);
		
		doadorDto.setVolume(cordaoInternacional.getVolume());
		doadorDto.setQuantidadeTotalCD34(cordaoInternacional.getQuantidadeTotalCD34());
		doadorDto.setQuantidadeTotalTCN(cordaoInternacional.getQuantidadeTotalTCN());
		
		Paciente pacienteAssociado = 
				reservaDoadorInternacionalService.obterPacienteAssociado(cordaoInternacional);
		doadorDto.setRmrAssociado(pacienteAssociado.getRmr());
		
		doadorDto.setTipoDoador(cordaoInternacional.getTipoDoador().getId());
		
		return doadorDto;
	}

	private DoadorCordaoInternacionalDTO obterDtoDoadorInternacional(DoadorInternacional doadorInternacional) {
		DoadorCordaoInternacionalDTO doadorDto = new DoadorCordaoInternacionalDTO();
		doadorDto.setId(doadorInternacional.getId());
		doadorDto.setAbo(doadorInternacional.getAbo());
		doadorDto.setCadastradoEmdis(doadorInternacional.getCadastradoEmdis());
		
		RegistroDTO registroOrigem = new RegistroDTO();
		registroOrigem.setId(doadorInternacional.getRegistroOrigem().getId());
		registroOrigem.setNome(doadorInternacional.getRegistroOrigem().getNome());
		doadorDto.setRegistroOrigem(registroOrigem);
		
		doadorDto.setDataNascimento(doadorInternacional.getDataNascimento());
		doadorDto.setDataRetornoInatividade(doadorInternacional.getDataRetornoInatividade());
		
		if(doadorInternacional.getGenotipo() != null){
			List<ValorGenotipoDTO> valoresGenotipo = new ArrayList<ValorGenotipoDTO>();
			doadorInternacional.getGenotipo().getValores().forEach(valor -> {
				
				Optional<ValorGenotipoDTO> valorGenotipoPorLocus = valoresGenotipo.stream().filter(valorJaIncluido -> {
					return valorJaIncluido.getLocus().equals(valor.getLocus().getCodigo());
				}).findFirst();
				
				ValorGenotipoDTO valorGenotipo = new ValorGenotipoDTO();
				
				if(valorGenotipoPorLocus.isPresent()){
					valorGenotipo = valorGenotipoPorLocus.get();
					valoresGenotipo.remove(valorGenotipo);
				}
				
				valorGenotipo.setLocus(valor.getLocus().getCodigo());
				
				if(valor.getPosicao() == 1L){
					valorGenotipo.setPrimeiroAlelo(valor.getAlelo());
				}
				else {
					valorGenotipo.setSegundoAlelo(valor.getAlelo());
				}
				valorGenotipo.setOrdem(valor.getLocus().getOrdem());
				valoresGenotipo.add(valorGenotipo);
			});
			doadorDto.setValoresGenotipo(valoresGenotipo);
			doadorDto.getValoresGenotipo().sort((l1, l2) -> l1.getOrdem().compareTo(l2.getOrdem()));
		}
		
		doadorDto.setGrid(doadorInternacional.getGrid());
		doadorDto.setIdade(doadorInternacional.getIdade());
		doadorDto.setIdRegistro(doadorInternacional.getIdRegistro());
		doadorDto.setPeso(doadorInternacional.getPeso());
		
		if(doadorInternacional.getRegistroPagamento() != null) {
			RegistroDTO registroPagamento = new RegistroDTO();
			registroPagamento.setId(doadorInternacional.getRegistroPagamento().getId());
			registroPagamento.setNome(doadorInternacional.getRegistroPagamento().getNome());
			doadorDto.setRegistroPagamento(registroPagamento);
		}
		
		doadorDto.setSexo(doadorInternacional.getSexo());
		
		StatusDoadorDTO statusDoador = new StatusDoadorDTO();
		statusDoador.setId(doadorInternacional.getStatusDoador().getId());
		statusDoador.setDescricao(doadorInternacional.getStatusDoador().getDescricao());
		doadorDto.setStatusDoador(statusDoador);
		
//		Paciente pacienteAssociado = 
//				reservaDoadorInternacionalService.obterPacienteAssociado(doadorInternacional);
//		doadorDto.setRmrAssociado(pacienteAssociado.getRmr());
				
		if(CollectionUtils.isNotEmpty(doadorInternacional.getRessalvas())){
			List<String> ressalvas = 
					doadorInternacional.getRessalvas().stream().map(ressalva -> {
						return ressalva.getObservacao();
					}).collect(Collectors.toList());
			doadorDto.setRessalvas(ressalvas);
		}
		
		doadorDto.setTipoDoador(doadorInternacional.getTipoDoador().getId());
		
		return doadorDto;
	}

	@Override
	public DoadorInternacional obterDoadorPorGrid(String grid) {
		return (DoadorInternacional) this.findOne(new Equals<String>("grid", grid));
	}

	@Override
	public List<ExameDoadorInternacionalDto> listarExamesDeDoador(Long idDoador) {
		List<ExameDoadorInternacionalDto> listaResultado = new ArrayList<ExameDoadorInternacionalDto>();
		
		Long tipoExameHLA = 1L;
		Long tipoExameIDM = 2L;
		
		List<ExameDoadorInternacional> examesValidos = exameService.listarExamesPorDoador(idDoador);
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
	public void atualizarDadosPessoais(Long id, DoadorInternacional doador) {
		DoadorInternacional doadorIntSalvar = (DoadorInternacional) obterDoador(id);
		doadorIntSalvar.setIdRegistro(doador.getIdRegistro());
		doadorIntSalvar.setAbo(doador.getAbo());
		doadorIntSalvar.setGrid(doador.getGrid());
		doadorIntSalvar.setIdade(doador.getIdade());
		doadorIntSalvar.setDataNascimento(doador.getDataNascimento());
		doadorIntSalvar.setPeso(doador.getPeso());
		doadorIntSalvar.setSexo(doador.getSexo());
		this.save(doadorIntSalvar);
	}

	@Override
	public void atualizarOrigemPagamento(OrigemPagamentoDoadorDTO registroDto) {
		DoadorInternacional doadorIntSalvar = (DoadorInternacional)doadorService.findById(registroDto.getIdDoador());
		doadorIntSalvar.setRegistroOrigem(new Registro(registroDto.getIdRegistroOrigem()));
		doadorIntSalvar.setRegistroPagamento(new Registro(registroDto.getIdRegistroPagamento()));
		this.save(doadorIntSalvar);
	}

	@Override
	public void salvarExame(Long idDoador, ExameDoadorInternacional exame) throws Exception{
		DoadorInternacional doadorInternacional = (DoadorInternacional) doadorService.findById(idDoador);
		exame.setStatusExame(StatusExame.CONFERIDO.getId());
		exame.setDoador(doadorInternacional);
		
		exameService.salvar(null, exame, new TipoExame(TiposExame.TESTE_CONFIRMATORIO.getId()));
		genotipoService.gerarGenotipo(doadorInternacional);
		//Paciente pacienteAssociado = 
			//	reservaDoadorInternacionalService.obterPacienteAssociado(doadorInternacional);
		
	}

	/**
	 * @param genotipoService the genotipoService to set
	 */
	@Autowired
	public void setGenotipoService(IGenotipoDoadorService<ExameDoadorInternacional> genotipoService) {
		this.genotipoService = genotipoService;
	}
	
	@Override
	public Long obterIdentifiadorDoadorInternacionalPorIdDoRegistro(String idRegistro) {
		DoadorInternacional doadorInternacional = obterDoadorInternacionalPorIdRegistro(idRegistro);
		return doadorInternacional != null ? doadorInternacional.getId() : 0L; 
	}
	
	@Override
	public void atualizar(Long id, DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO) throws Exception {
		
		DoadorInternacional doadorInternacional =  converterParaDoadorInternacional(doadorCordaoInternacionalDTO);
		atualizarDadosPessoais(id, doadorInternacional);
		if (!CollectionUtils.isEmpty(doadorInternacional.getExames())) {
			salvarExame(id, doadorInternacional.getExames().get(0));
		}
	}
	
	@Override
	public Long salvarDoadorInternacionalWmda(DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO) {
		DoadorInternacional doadorSalvo = salvarDoadorInternacional(converterParaDoadorInternacional(doadorCordaoInternacionalDTO));
		return doadorSalvo.getId();
	}

	@Override
	public boolean verificarDoadorWmdaJaExistente(DoadorInternacional doador) {
		boolean doadorJaExiste = false;

		Doador doadorMesmo = doadorInternacionalRepository.obterIdDoadorPorRegistroOrigem(
				doador.getIdRegistro(), doador.getRegistroOrigem().getId(), doador.getId());

		if (doadorMesmo != null) {
			doadorJaExiste = !doadorMesmo.getId().equals(doador.getId());
		}
		else if(doador.getTipoDoador().equals(TiposDoador.INTERNACIONAL)){
			if (doador.getGrid() != null) {
				Long idDoadorMesmoGrid = doadorInternacionalRepository.obterIdDoadorPorGrid(doador.getGrid(), doador.getId());
				doadorJaExiste = idDoadorMesmoGrid != null && !idDoadorMesmoGrid.equals(doador.getId());
			}
		}
		return doadorJaExiste;
	}
	
}
