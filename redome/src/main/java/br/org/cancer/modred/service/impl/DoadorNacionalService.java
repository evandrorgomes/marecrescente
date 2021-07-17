package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.ContatoTelefonicoDoador;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.EmailContatoDoador;
import br.org.cancer.modred.model.EnderecoContatoDoador;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.PedidoEnriquecimento;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.RessalvaDoador;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.TentativaContatoDoador;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposFluxosContatoPassivo;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.persistence.IDoadorNacionalRepository;
import br.org.cancer.modred.persistence.IEnderecoContatoDoadorRepository;
import br.org.cancer.modred.persistence.IRessalvaDoadorRepository;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IContatoTelefonicoDoadorService;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IEmailContatoDoadorService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.IPedidoContatoService;
import br.org.cancer.modred.service.IPedidoContatoSmsService;
import br.org.cancer.modred.service.IPedidoEnriquecimentoService;
import br.org.cancer.modred.service.ITentativaContatoDoadorService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.Projection;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;
import br.org.cancer.modred.vo.ConsultaDoadorNacionalVo;

/**
 * Classe de funcionalidades envolvendo a entidade Doador.
 * 
 * @author Pizão.
 *
 */
@Service
@Transactional
public class DoadorNacionalService extends DoadorService<DoadorNacional> implements IDoadorNacionalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DoadorNacionalService.class);
	
	private static final int QUANTIDADE_MINIMA_CARACTERES = 3;

	@Autowired
	private IDoadorNacionalRepository doadorNacionalRepository;
	
	@Autowired
	private IRessalvaDoadorRepository ressalvaDoadorRepository;

	private IContatoTelefonicoDoadorService contatoDoadorService;
	
	@Autowired
	private IEnderecoContatoDoadorRepository repositoryEnderecoContatoDoador;

	private IEmailContatoDoadorService emailDoadorService;
	
	private IGenotipoDoadorService<ExameDoadorNacional> genotipoDoadorService;
		
	private IPedidoEnriquecimentoService pedidoEnriquecimentoService;
	
	private IPedidoContatoService pedidoContatoService;

	private ITentativaContatoDoadorService tentativaContatoDoadorService;
	
	private IPedidoContatoSmsService pedidoContatoSmsService;
	
	@Autowired
	private IConfiguracaoService configuracaoService;
	
	@Override
	public DoadorNacional obterDoadorPorPedidoContato(Long pedidoContatoId) {
		return doadorNacionalRepository.obterDoadorPorPedidoContato(pedidoContatoId);
	}

	@Override
	public DoadorNacional obterDoadorPorTentativaDeContato(Long tentativaContatoId) {
		return (DoadorNacional) doadorNacionalRepository.obterDoadorPorTentativaContato(tentativaContatoId);
	}
	
	@Override
	public RetornoInclusaoDTO adicionarContatoTelefonico(Long idDoador, ContatoTelefonicoDoador contatoDoador) {
		
		DoadorNacional doador = (DoadorNacional) obterDoador(idDoador);
		if (doador == null) {
			throw new BusinessException("erro.doador.invalido");
		}

		if (doador.getContatosTelefonicos() == null) {
			doador.setContatosTelefonicos(new ArrayList<>());
		}
		doador.setDataAtualizacao(LocalDateTime.now());
		doador.getContatosTelefonicos().add(contatoDoador);
		contatoDoador.setDoador(doador);

		RetornoInclusaoDTO retorno = contatoDoadorService.salvar(contatoDoador);
		pedidoEnriquecimentoService.atualizarEnriquecimentoDoadorNacionalRedomeWeb(doador);

		return retorno;
	}

	@Override
	public boolean verificarDoadorTemContatoPrincipal(Long idDoador) {
		return doadorNacionalRepository.verificarDoadorTemContatoPrincipal(idDoador);
	}
	
	
	@Override
	public RetornoInclusaoDTO adicionarEnderecoContato(Long idDoador, EnderecoContatoDoador enderecoContato) {
		RetornoInclusaoDTO retornoDto = new RetornoInclusaoDTO();

		DoadorNacional doador = (DoadorNacional) doadorNacionalRepository.findById(idDoador).orElse(null);
		if (doador == null) {
			throw new BusinessException("erro.doador.invalido");
		}

		if (doador.getEnderecosContato() == null) {
			doador.setEnderecosContato(new ArrayList<>());
		}
		doador.setDataAtualizacao(LocalDateTime.now());
		doador.getEnderecosContato().add(enderecoContato);
		enderecoContato.setDoador(doador);

		List<CampoMensagem> mensagensRetorno = validarEnderecoContato(enderecoContato);
		if (!mensagensRetorno.isEmpty()) {
			throw new ValidationException("erro.validacao", mensagensRetorno);
		}
		repositoryEnderecoContatoDoador.save(enderecoContato);
		pedidoEnriquecimentoService.atualizarEnriquecimentoDoadorNacionalRedomeWeb(doador);

		retornoDto.setIdObjeto(enderecoContato.getId());
		retornoDto.setMensagem(AppUtil.getMensagem(messageSource, "enderecocontato.adicionado.sucesso"));
		return retornoDto;
	}

	private List<CampoMensagem> validarEnderecoContato(EnderecoContatoDoador endereco) {
		return new ConstraintViolationTransformer(validator.validate(
				endereco)).transform();
	}

	@Override
	public void atualizarIdentificacao(Long id, DoadorNacional doador) {
		DoadorNacional doadorLocalizado = (DoadorNacional) doadorNacionalRepository.findById(id).orElse(null);

		if (doadorLocalizado == null) {
			throw new BusinessException("erro.doador.doador_nao_existente_na_base");
		}

		doadorLocalizado.setNome(doador.getNome());
		doadorLocalizado.setNomeSocial(doador.getNomeSocial());
		doadorLocalizado.setDataNascimento(doador.getDataNascimento());
		doadorLocalizado.setNomeMae(doador.getNomeMae());
		doadorLocalizado.setSexo(doador.getSexo());
		doadorLocalizado.setCpf(doador.getCpf());
		doadorLocalizado.setRg(doador.getRg());
		doadorLocalizado.setOrgaoExpedidor(doador.getOrgaoExpedidor());
		doadorLocalizado.setDataAtualizacao(LocalDateTime.now());
		List<CampoMensagem> mensagensRetorno = validar(doadorLocalizado);

		if (!mensagensRetorno.isEmpty()) {
			throw new ValidationException("erro.validacao", mensagensRetorno);
		}
		else {
			doadorNacionalRepository.save(doadorLocalizado);
			pedidoEnriquecimentoService.atualizarEnriquecimentoDoadorNacionalRedomeWeb(doadorLocalizado);
		}
	}
	
	
	@Override
	public RetornoInclusaoDTO adicionarEmail(Long idDoador, EmailContatoDoador email) {

		DoadorNacional doador = (DoadorNacional) obterDoador(idDoador);
		if (doador == null) {
			throw new BusinessException("erro.doador.invalido");
		}

		if (doador.getEmailsContato() == null) {
			doador.setEmailsContato(new ArrayList<>());
		}
		doador.setDataAtualizacao(LocalDateTime.now());
		doador.getEmailsContato().add(email);
		
		email.setDoador(doador);
		emailDoadorService.salvar(email);
		
		pedidoEnriquecimentoService.atualizarEnriquecimentoDoadorNacionalRedomeWeb(doador);
		
		RetornoInclusaoDTO retornoDto = new RetornoInclusaoDTO();
		retornoDto.setIdObjeto(email.getId());
		retornoDto.setMensagem(AppUtil.getMensagem(messageSource, "emailcontato.adicionado.sucesso"));
		
		return retornoDto;
	}

	@Override
	public List<CampoMensagem> validar(DoadorNacional doador) {
		List<CampoMensagem> campos = new ConstraintViolationTransformer(validator.validate(
				doador)).transform();

		boolean cpfDuplicado = verificarDuplicidadePorCpf(doador);

		if (cpfDuplicado) {
			campos.add(new CampoMensagem("cpf",
					AppUtil.getMensagem(messageSource, "cpf.doador.ja.utilizado")));
		}

		boolean nomeDataNascimentoNomeMaeDuplicado = verificarDuplicidadePorNomeDataNascNomeMae(doador);

		if (nomeDataNascimentoNomeMaeDuplicado) {
			campos.add(new CampoMensagem(
					AppUtil.getMensagem(messageSource, "nomedatanascimentonomemae.doador.ja.utilizado")));
		}

		return campos;
	}

	private boolean verificarDuplicidadePorCpf(DoadorNacional doador) {
		return doadorNacionalRepository.verificarSeCpfDuplicado(doador.getId(), doador.getCpf());
	}

	@Override
	public void atualizarDadosPessoais(Long idDoador, DoadorNacional doador) {
		DoadorNacional doadorLocalizado = (DoadorNacional) doadorNacionalRepository.findById(idDoador).orElse(null);
		if (doadorLocalizado == null) {
			throw new BusinessException("erro.doador.doador_nao_existente_na_base");
		}
		doadorLocalizado.setRaca(doador.getRaca());
		doadorLocalizado.setEtnia(doador.getEtnia());
		doadorLocalizado.setAbo(doador.getAbo());
		doadorLocalizado.setPais(doador.getPais());
		doadorLocalizado.setNaturalidade(doador.getNaturalidade());
		doadorLocalizado.setDataAtualizacao(LocalDateTime.now());
		doadorLocalizado.setAltura(doador.getAltura());
		doadorLocalizado.setPeso(doador.getPeso());
		doadorLocalizado.setNomePai(doador.getNomePai());
		doadorLocalizado.setFumante(doador.getFumante());
		doadorLocalizado.setEstadoCivil(doador.getEstadoCivil());

		if (doador.getRessalvas() != null) {

			// Foi acordado que por enquanto sera tratada apenas 1 ressalva na atualizacao dos dados pessoais
			if (doador.getRessalvas().get(0).getObservacao() == null && !doadorLocalizado.getRessalvas().isEmpty()) {
				ressalvaDoadorRepository.deleteById(doadorLocalizado.getRessalvas().get(0).getId());
				doadorLocalizado.setRessalvas(null);
			}
			else
				if (doador.getRessalvas().get(0).getObservacao() != null) {
					if (doadorLocalizado.getRessalvas() == null && doador.getRessalvas().get(0)
							.getObservacao() != null) {
						doadorLocalizado.setRessalvas(new ArrayList<RessalvaDoador>());
					}
					if (!doadorLocalizado.getRessalvas().isEmpty()) {
						RessalvaDoador ressalvaDoador = doadorLocalizado.getRessalvas().get(0);
						ressalvaDoador.setObservacao(doador.getRessalvas().get(0).getObservacao());
					}
					else {
						doadorLocalizado.getRessalvas().add(new RessalvaDoador(doadorLocalizado, doador.getRessalvas().get(0)
								.getObservacao()));
					}
				}
		}

		doadorNacionalRepository.save(doadorLocalizado);
		pedidoEnriquecimentoService.atualizarEnriquecimentoDoadorNacionalRedomeWeb(doadorLocalizado);
	}

	private boolean verificarDuplicidadePorNomeDataNascNomeMae(DoadorNacional doador) {
		return doadorNacionalRepository.verificarSeNomeDtNascNomeMaeDuplicado(
				doador.getId(), doador.getNome(), doador.getDataNascimento(), doador.getNomeMae());
	}

	@Override
	public String obterNaturalidadeDoador(Long idDoador) {
		return doadorNacionalRepository.obterUfEnderecoPrincipal(idDoador);
	}

	@Override
	public Paginacao<ConsultaDoadorNacionalVo> listarDoadoresNacionaisVo(PageRequest pageable, Long dmr, String nome, String cpf) {

		if (dmr == null && (  nome == null || "".equals(nome)) && ( cpf == null || "".equals(cpf))) {
			throw new BusinessException("filtros.nulos");
		}

		if (nome != null && !"".equals(nome) && nome.length() < QUANTIDADE_MINIMA_CARACTERES) {
			throw new BusinessException("doador.consulta.minimo.caracter");
		}
		
		List<ConsultaDoadorNacionalVo> doadores = doadorNacionalRepository.listarDoadoresNacionaisVo(pageable, dmr, nome, cpf);
		doadores.forEach(doador -> {
			obterDadosPedidoContatoPorDoador(doador);
		});
		
		return new Paginacao<>(doadores, pageable, doadores.size());
	}

	@Override
	public Paginacao<DoadorNacional> listarDoadoresNacionais(PageRequest pageable, Long dmr, String nome) {

		if (dmr == null && (  nome == null || "".equals(nome))) {
			throw new BusinessException("filtros.nulos");
		}

		if (nome != null && !"".equals(nome) && nome.length() < QUANTIDADE_MINIMA_CARACTERES) {
			throw new BusinessException("doador.consulta.minimo.caracter");
		}
		
		List<DoadorNacional> doadores = doadorNacionalRepository.listarDoadoresNacionais(pageable, dmr, nome);
		
		return new Paginacao<>(doadores, pageable, doadores.size());
	}
	
	@Override
	public ConsultaDoadorNacionalVo obterDadosPedidoContatoPorDoador(ConsultaDoadorNacionalVo doador) {

		PedidoEnriquecimento pedidoEnriquecimento = pedidoEnriquecimentoService.obterPedidoDeEnriqueciomentoPorIdDoadorEStatus(doador.getIdDoador(), Boolean.TRUE);
		if(pedidoEnriquecimento != null) {
			doador.setIdEnriquecimento(pedidoEnriquecimento.getId());
			doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_ENRIQUECIMENTO.getId());
		}
		else {
		
			PedidoContato pedido = pedidoContatoService.obterUltimoPedidoContato(doador.getIdDoador());
			if(pedido != null) {
	
				doador.setTipoContato(pedido.getTipoContato());
				doador.setPassivo(pedido.getPassivo());
				
				TentativaContatoDoador tentativa = tentativaContatoDoadorService.obterUltimaTentativaContatoPorPedidoContatoId(pedido.getId());
				doador.setIdTentativa(tentativa.getId());
	
				if(tentativa != null && !pedido.getPassivo()) {
				TarefaDTO tarefa = tentativaContatoDoadorService.obterTarefaAssociadaATentativaContatoEStatusTarefa(tentativa, 
						Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA), true, true);
					if(tarefa != null) {
						doador.setIdTarefa(tarefa.getId());
						doador.setIdStatusTarefa(tarefa.getStatusTarefa().getId());
						doador.setIdProcesso(tarefa.getProcesso().getId());
					}
				}
				definirFluxoExibicaoDoPedidoContato(doador, pedido);
			}
			else {
				doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_ATUALIZACAO.getId());
			}
		}
		return doador;
	}

	private void definirFluxoExibicaoDoPedidoContato(ConsultaDoadorNacionalVo doador, PedidoContato pedido) {
		if(pedido.getTipoContato() == TiposSolicitacao.FASE_2.getId()){
			if(StatusDoador.isAtivo(doador.getIdStatusDoador())){
				if(pedido.isAberto()) {
					doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_NORMAL.getId());
				}
				else {
					doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_ATUALIZACAO.getId());
				}
			}
			else {
				doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_ATUALIZACAO.getId());
			}
		}
		else if(pedido.getTipoContato() == TiposSolicitacao.FASE_3.getId()) {
			
			if(!pedido.getPassivo()) {
			
				if(StatusDoador.isAtivo(doador.getIdStatusDoador())){
					if(pedido.isAberto()) {
						doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_NORMAL.getId());
					}
					else {
						if(!pedido.getContactado() && pedidoContatoSmsService.existePedidoContatoSmsDentroDoPrazo(pedido)) {
							doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_CRIACAO.getId()); 
						}
						else {
							doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_ATUALIZACAO.getId());
						}
					}
				}
				else {
					if(pedidoContatoService.obterVerificacaoUltimoPedidoContatoContactadoFechado(doador.getIdDoador())) {
						doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_ATUALIZACAO.getId());
					}
					else {
						doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_CRIACAO.getId()); 
					}
				}
			}
			else {
				if(pedido.isAberto()) {
					doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_PASSIVO.getId());
				}
				else {
					doador.setTipoFluxo(TiposFluxosContatoPassivo.FLUXO_ATUALIZACAO.getId());
				}
			}
		}
	}

	@Override
	public DoadorNacional obterDoadorPorPedidoLogistica(PedidoLogistica pedidoLogistica) {
		return pedidoLogistica.isLogisticaWorkup() ? doadorNacionalRepository.obterDoadorPorLogisticaWorkup(pedidoLogistica.getId()) : 
				doadorNacionalRepository.obterDoadorPorLogisticaColeta(pedidoLogistica.getId());
	}
	
	@Override
	public Long obterDmrPorIdDoador(Long idDoador){
		Filter<Long> filtrarPorId = new Equals<>("id", idDoador);
		Projection projectionDmr = new Projection("dmr");
		Doador doador = findOne(Arrays.asList(projectionDmr), Arrays.asList(filtrarPorId));
		return ((DoadorNacional) doador).getDmr();
	}
	
	@Override
	public List<Doador> findAll(){
		return find(new Equals<Long>("tipoDoador", TiposDoador.NACIONAL.getId()));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void criarDoadorNacional(DoadorNacional doadorNacional) {
		DoadorNacional doadorNacionalSalvo = (DoadorNacional) save(doadorNacional);
		genotipoDoadorService.gerarGenotipo(doadorNacionalSalvo);
    }

	/**
	 * @param genotipoDoadorService the genotipoDoadorService to set
	 */
	@Autowired
	public void setGenotipoDoadorService(IGenotipoDoadorService<ExameDoadorNacional> genotipoDoadorService) {
		this.genotipoDoadorService = genotipoDoadorService;
	}

	/**
	 * @param pedidoEnriquecimentoService the pedidoEnriquecimentoService to set
	 */
	@Autowired
	public void setPedidoEnriquecimentoService(IPedidoEnriquecimentoService pedidoEnriquecimentoService) {
		this.pedidoEnriquecimentoService = pedidoEnriquecimentoService;
	}

	/**
	 * @param pedidoContatoService the pedidoContatoService to set
	 */
	@Autowired
	public void setPedidoContatoService(IPedidoContatoService pedidoContatoService) {
		this.pedidoContatoService = pedidoContatoService;
	}

	/**
	 * @param contatoDoadorService the contatoDoadorService to set
	 */
	@Autowired
	public void setContatoDoadorService(IContatoTelefonicoDoadorService contatoDoadorService) {
		this.contatoDoadorService = contatoDoadorService;
	}

	/**
	 * @param emailDoadorService the emailDoadorService to set
	 */
	@Autowired
	public void setEmailDoadorService(IEmailContatoDoadorService emailDoadorService) {
		this.emailDoadorService = emailDoadorService;
	}

	/**
	 * @param tentativaContatoDoadorService the tentativaContatoDoadorService to set
	 */
	@Autowired
	public void setTentativaContatoDoadorService(ITentativaContatoDoadorService tentativaContatoDoadorService) {
		this.tentativaContatoDoadorService = tentativaContatoDoadorService;
	}

	/**
	 * @param pedidoContatoSmsService the pedidoContatoSmsService to set
	 */
	@Autowired
	public void setPedidoContatoSmsService(IPedidoContatoSmsService pedidoContatoSmsService) {
		this.pedidoContatoSmsService = pedidoContatoSmsService;
	}

	@Override
	public DoadorNacional obterDoadorNacionalPorDmr(Long dmr) {
		return doadorNacionalRepository.findByDmr(dmr).orElse(null);
		//return doadorNacionalRepository.obterDoadorNacionalPorDmr(dmr);
	}
}
