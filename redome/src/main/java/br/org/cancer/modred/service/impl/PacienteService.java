package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.AnaliseMatchDTO;
import br.org.cancer.modred.controller.dto.ContatoCentroTransplantadorDTO;
import br.org.cancer.modred.controller.dto.ContatoPacienteDTO;
import br.org.cancer.modred.controller.dto.CriarLogEvolucaoDTO;
import br.org.cancer.modred.controller.dto.DetalheAvaliacaoPacienteDTO;
import br.org.cancer.modred.controller.dto.LogEvolucaoDTO;
import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.controller.dto.MismatchDTO;
import br.org.cancer.modred.controller.dto.PacienteTarefaDTO;
import br.org.cancer.modred.controller.dto.PacienteWmdaDTO;
import br.org.cancer.modred.controller.dto.PedidosPacienteInvoiceDTO;
import br.org.cancer.modred.controller.dto.PesquisaWmdaDTO;
import br.org.cancer.modred.controller.dto.StatusPacienteDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CancelamentoBusca;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Diagnostico;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.FuncaoTransplante;
import br.org.cancer.modred.model.GenotipoPaciente;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.Metodologia;
import br.org.cancer.modred.model.MotivoCancelamentoBusca;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pais;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.PedidoTransferenciaCentro;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.StatusBusca;
import br.org.cancer.modred.model.StatusPaciente;
import br.org.cancer.modred.model.TipoTransplante;
import br.org.cancer.modred.model.Uf;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.FiltroMatch;
import br.org.cancer.modred.model.domain.StatusAvaliacao;
import br.org.cancer.modred.model.domain.StatusPacientes;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IEvolucaoRepository;
import br.org.cancer.modred.persistence.IPacienteRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAvaliacaoNovaBuscaService;
import br.org.cancer.modred.service.IAvaliacaoService;
import br.org.cancer.modred.service.IBuscaChecklistService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.ICancelamentoBuscaService;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IContatoTelefonicoPacienteService;
import br.org.cancer.modred.service.IDiagnosticoService;
import br.org.cancer.modred.service.IEnderecoContatoPacienteService;
import br.org.cancer.modred.service.IEtniaService;
import br.org.cancer.modred.service.IEvolucaoService;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.IGenotipoPacienteService;
import br.org.cancer.modred.service.IHistoricoStatusPacienteService;
import br.org.cancer.modred.service.ILogEvolucaoService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IMedicoService;
import br.org.cancer.modred.service.IMetodologiaService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPaisService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.IPedidoTransferenciaCentroService;
import br.org.cancer.modred.service.IPermissaoService;
import br.org.cancer.modred.service.IPesquisaWmdaService;
import br.org.cancer.modred.service.IRacaService;
import br.org.cancer.modred.service.IRascunhoService;
import br.org.cancer.modred.service.IResponsavelService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IUfService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.Projection;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;
import br.org.cancer.modred.util.DateUtils;
import br.org.cancer.modred.util.sort.ColecoesUtil;
import br.org.cancer.modred.util.sort.CriterioOrdenacao;

/**
 * Classe de negócios para Paciente.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PacienteService extends AbstractLoggingService<Paciente, Long> implements IPacienteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
	
	@Autowired
	private IPacienteRepository pacienteRepository;
	
	private IGenotipoPacienteService genotipoService;
	@Autowired
	private IEvolucaoRepository evolucaoRepository;

	private ILogEvolucaoService logEvolucaoService;
	
	private IRacaService racaService;
	
	private IEtniaService etniaService;
	
	private IPaisService paisService;
		
	private IUfService ufService;
	
	private IDiagnosticoService diagnosticoService;
	
	private IEvolucaoService evolucaoService;
	
	private IMetodologiaService metodologiaService;
		
	private IExamePacienteService exameService;
	
	private ICentroTransplanteService centroTransplanteService;
	
	private IMedicoService medicoService;
	
	private IRascunhoService rascunhoService;
	
	private IAvaliacaoService avaliacaoService;
	
	private IMatchService matchService;

	private IPesquisaWmdaService pesquisaWmdaService;
	
	private IBuscaChecklistService buscaChecklistService;
	
	private IBuscaService buscaService;

	private IEnderecoContatoPacienteService endContatoService;

	private IContatoTelefonicoPacienteService contatoTelefonicoService;

	private IResponsavelService responsavelService;

	private ICancelamentoBuscaService cancelamentoBuscaService;
	
	private IPedidoExameService pedidoExameService;
	
	private IPedidoTransferenciaCentroService pedidoTransferenciaCentroService;
	
	private IHistoricoStatusPacienteService historicoStatusPacienteService;
	
	private IAvaliacaoNovaBuscaService avaliacaoNovaBuscaService;

	private IStatusPacienteService statusPacienteService;

	private ISolicitacaoService solicitacaoService;
	
	private IPermissaoService permissaoService; 
	
	
	@Override
	public IRepository<Paciente, Long> getRepository() {
		return pacienteRepository;
	}

	/**
	 * Método para persistir Paciente.
	 * 
	 * @param Paciente paciente a ser validado. Condição: Se o país for Brasil, desconsiderar Endereço Estrangeiro. Se não for,
	 * desconsiderar todo o form (logradouro, bairro, município, etc) e salvar apenas o campo endereço estrangeiro.
	 */
//	@CreateLog(TipoLogEvolucao.PACIENTE_CADASTRADO)
	@Override
	public void salvar(Paciente paciente) {
		LOGGER.info("inicio do metodo de salvar paciente");
		try {

			// AppUtil.percorrerObjetoNulandoEntidadesVazias(paciente);

			Usuario usuario = usuarioService.obterUsuarioLogado();

			paciente.setUsuario(usuario);
			paciente.setDataCadastro(LocalDateTime.now());

			boolean isEstrangeiro = paciente.getPais() != null && !Pais.BRASIL.equals(paciente.getPais().getId());
			if (isEstrangeiro) {
				paciente.setNaturalidade(null);
			}
			if (paciente.getEnderecosContato() != null && !paciente.getEnderecosContato().isEmpty()) {
				paciente.getEnderecosContato()
						.forEach(enderecoContato ->
				{
							enderecoContato.setPaciente(paciente);
							endContatoService.normalizarEnderecoContato(enderecoContato);
						});
			}

			if (paciente.getContatosTelefonicos() != null && !paciente.getContatosTelefonicos().isEmpty()) {
				paciente.getContatosTelefonicos()
						.forEach(contatoTelefonico ->
				{
							contatoTelefonico.setPaciente(paciente);
						});

			}

			if (paciente.getEvolucoes() != null && !paciente.getEvolucoes().isEmpty()) {
				paciente.getEvolucoes().forEach(evol -> {
					evol.setPaciente(paciente);
				});
			}

			String diretorioExame = AppUtil.gerarStringAleatoriaAlfanumerica();
			String diretorioEvolucao = AppUtil.gerarStringAleatoriaAlfanumerica();

			if (Optional.ofNullable(paciente.getExames()).isPresent()) {

				paciente.getExames().forEach(exame -> {
					exame.setPaciente(paciente);
					
					
					if (Optional.ofNullable(exame.getLocusExames()).isPresent()) {
						exame.getLocusExames().forEach(locusExame -> {
							locusExame.getId().setExame(exame);
						});
					}
					
					exame.getArquivosExame().forEach(arquivo -> {
						arquivo.setExame(exame);
						arquivo.setCaminhoArquivo(diretorioExame + "/"
								+ arquivo.getCaminhoArquivo());
					});
					removerInstanciaLocusExameComAtributosNulos(exame);
				});
			}
			
			
			if (Optional.ofNullable(paciente.getEvolucoes()).isPresent()) {
				paciente.getEvolucoes().forEach(evolucao -> {
					if(evolucao.getArquivosEvolucao() != null){
						evolucao.getArquivosEvolucao().forEach(arquivo -> {
							arquivo.setCaminhoArquivo(diretorioEvolucao + "/"
									+ arquivo.getCaminhoArquivo());
							arquivo.setEvolucao(evolucao);
						});						
					}
				});
			}

			Medico medicoResponsavel = medicoService.obterMedicoPorUsuario(usuario.getId());
			paciente.setMedicoResponsavel(medicoResponsavel);

			if (paciente.getExames() != null && !paciente.getExames().isEmpty()) {
				if (paciente.getExames().get(0).getLaboratorioParticular() != null && paciente.getExames().get(0)
						.getLaboratorioParticular()) {
					paciente.getExames().get(0).setLaboratorio(null);
				}
			}

			List<CampoMensagem> mensagensRetorno = validar(paciente);

			if (!mensagensRetorno.isEmpty()) {
				throw new ValidationException("erro.validacao", mensagensRetorno);
			}

			LOGGER.info("Salvando o paciente no banco.");
			Paciente pacienteSalvo = save(paciente);

			if (pacienteSalvo == null || pacienteSalvo.getRmr() == null) {
				throw new BusinessException("erro.salvar.paciente");
			}
			
			historicoStatusPacienteService.adicionarStatusPaciente(StatusPacientes.AGUARDANDO_APROVACAO_CENTRO_AVALIDADOR, pacienteSalvo);
			historicoStatusPacienteService.adicionarStatusPaciente(StatusPacientes.AGUARDANDO_APROVACAO_CONFIRMACAO_HLA, pacienteSalvo);

			LOGGER.info("Paciente {} salvo no banco.", pacienteSalvo.getRmr());

			moverArquivosDeExameSalvosComoRascunho(paciente, usuario);
			
			moverArquivosDeEvolucaoSalvosComoRascunho(paciente,usuario);
			
			rascunhoService.excluirPorIdDeUsuario(usuario.getId());

			avaliacaoService.criarAvaliacaoInicial(pacienteSalvo);

			buscaService.criarBuscaInicial(pacienteSalvo);

			this.criarLogEvolucao(pacienteSalvo, TipoLogEvolucao.PACIENTE_CADASTRADO);
			
			LOGGER.info("fim do metodo de salvar paciente");
		}
		catch (ValidationException e) {
			throw e;
		}
		catch (BusinessException e) {
			throw e;
		}
		catch (Exception e) {
			throw new BusinessException("erro.salvar.paciente", e);
		}
	}

	private void moverArquivosDeEvolucaoSalvosComoRascunho(Paciente paciente, Usuario usuario) {
		if (Optional.ofNullable(paciente.getEvolucoes()).isPresent()) {
			paciente.getEvolucoes().forEach(evolucao -> {
				if(Optional.ofNullable(evolucao.getArquivosEvolucao()).isPresent()){
					evolucao.getArquivosEvolucao().forEach(arquivo -> {
						LOGGER.info("Arquivo Movido !!");
//						storageService.moverArquivoEvolucao(usuario.getId(),
//								paciente.getRmr(), arquivo.getCaminhoArquivo());
					});
				}
			});
		}
	}
	
	private void moverArquivosDeExameSalvosComoRascunho(Paciente paciente, Usuario usuario) {
		if (Optional.ofNullable(paciente.getExames()).isPresent()) {
			paciente.getExames().forEach(exame -> {
				exame.getArquivosExame().forEach(arquivo -> {
					LOGGER.info("Arquivo Movido !!");
//					storageService.moverArquivoExame(usuario.getId(),
//							paciente.getRmr(), arquivo.getCaminhoArquivo());
				});
			});
		}
	}

	private void removerInstanciaLocusExameComAtributosNulos(ExamePaciente exame) {
		exame.getLocusExames().removeIf(locusExame -> locusExame.isVazio());
	}

	/**
	 * Método para validar Paciente.
	 * 
	 * @param paciente paciente
	 * @return erros erros
	 */
	@Override
	public List<CampoMensagem> validar(Paciente paciente) {
		List<CampoMensagem> campos = validarIdentificacaoDadosPessoais(paciente);

		campos.addAll(contatoTelefonicoService.validar(paciente.getContatosTelefonicos()));
		validarMedicoResponsavel(paciente, campos);
		// Na inclusão do paciente somente será cadastrado 1 exame, portanto não
		// importa quantos exames serão passados
		// validamos somente o primeiro
		if (Optional.ofNullable(paciente.getExames()).isPresent()) {
			validarSeExameContemLocusAeBeDRB1(paciente.getExames().get(0), campos);
			validarSeMetodologiasSaoValidasPorId(paciente.getExames().get(0).getMetodologias(), campos);
			campos.addAll(exameService.validarExame(paciente.getExames().get(0)));
		}
		diagnosticoService.validar(paciente.getDiagnostico(), campos);
		evolucaoService.validar(paciente.getDiagnostico().getCid(), paciente.getEvolucoes(),
				campos);

		validarCentroAvaliador(paciente, campos);
		validarDadosMismatch(paciente, campos);

		return campos;
	}

	/**
	 * Método para validar Identificação e Dados Pessoais do Paciente.
	 * 
	 * @param paciente paciente
	 * @return erros erros
	 */
	private List<CampoMensagem> validarIdentificacaoDadosPessoais(Paciente paciente) {
		List<CampoMensagem> campos = new ConstraintViolationTransformer(validator.validate(
				paciente)).transform();
		validarPacienteCadastrado(paciente, campos);
		validarRaca(paciente, campos);
		validarEtnia(paciente, campos);
		validarPais(paciente, campos);
		validarUf(paciente, campos);
		return campos;
	}

	private void validarCentroAvaliador(Paciente paciente, List<CampoMensagem> campos) {
		final CentroTransplante centroAvaliador = paciente.getCentroAvaliador();
		if (centroAvaliador != null && centroAvaliador.getId() != null) {
			if (isCentroTransplanteInexistente(centroAvaliador, campos)) {
				campos.add(obterMensagemDeErro("centroAvaliador",
						"erro.centro.avaliador.invalido"));
			}
		}
		else {
			campos.add(obterMensagemDeErro("centroAvaliador", "erro.centro.avaliador.nulo"));
		}
	}

	private CampoMensagem obterMensagemDeErro(String nomeCampo, String chaveMensagem) {
		CampoMensagem campoMensagem = new CampoMensagem(nomeCampo,
				messageSource.getMessage(chaveMensagem, null, LocaleContextHolder.getLocale()));
		return campoMensagem;
	}

	/**
	 * Pesquisa se já existe paciente com os mesmos dados informados cadastrado na base. Como chave candidata de paciente temos os
	 * seguintes campos, nessa ordem de prioridade, caso estejam preenchidos: - CPF - CNS - Nome, data nascimento e nome da mãe
	 * (pode vir nulo e deve ser verificado).
	 * 
	 * @author Pizão
	 * @param campos
	 * @param paciente Objeto com os dados do paciente que está sendo incluso ou alterado.
	 * @return Retorna TRUE se já existe pacientes com as mesmas características únicas consideradas no sistema, FALSE, caso não
	 * haja.
	 */
	private void verificarPacienteJaCadastrado(Paciente paciente, List<CampoMensagem> campos) {
		
		try {

			final String dataNascimento = paciente.getDataNascimento() != null ? 
					paciente.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;

			final boolean seNenhumParametroInformado = 
					StringUtils.isEmpty(paciente.getCpf())
					&& StringUtils.isEmpty(paciente.getCns())
					&& StringUtils.isEmpty(paciente.getNome()) 
					&& StringUtils.isEmpty(dataNascimento)
					&& StringUtils.isEmpty(paciente.getNomeMae());

			if (seNenhumParametroInformado) {
				campos.add(new CampoMensagem("erro", AppUtil.getMensagem(messageSource, "erro.parametros.invalidos")));
			}
			else {
				if (verificarSeCpfExisteNaBase(paciente.getRmr(), paciente.getCpf())) {
					campos.add(new CampoMensagem("cpf", AppUtil.getMensagem(messageSource,"cpf.paciente.ja.utilizado")));
				}
				else {
					if (verificarSeCnsExisteNaBase(paciente.getRmr(), paciente.getCns())) {
						campos.add(new CampoMensagem("cns", AppUtil.getMensagem(messageSource,"cns.paciente.ja.utilizado")));
					}
					else {
						verificarSePacientePossuiInformacoesQueIndicamDuplicidade(
								paciente.getRmr(), 
								paciente.getCpf(),
								paciente.getCns(), 
								paciente.getNome(), 
								dataNascimento, 
								paciente.getNomeMae(), 
								campos);
					}
				}
			}

		}
		catch (Exception e) {
			throw new BusinessException("erro.paciente.verificar.duplicidade", e);
		}
	}

	/**
	 * @param cns
	 * @return
	 */
	private boolean verificarSeCnsExisteNaBase(Long rmr, String cns) {
		final Paciente paciente = StringUtils.isNotEmpty(cns) ? pacienteRepository.findByCns(cns) : null;
		boolean duplicado = StringUtils.isNotEmpty(cns) && paciente != null;
		return ( rmr != null ? duplicado && !rmr.equals(paciente.getRmr()) : duplicado );
	}

	/**
	 * @param cpf
	 * @return
	 */
	private boolean verificarSeCpfExisteNaBase(Long rmr, String cpf) {
		final Paciente paciente = StringUtils.isNotEmpty(cpf) ? pacienteRepository.findByCpf(cpf) : null;
		boolean duplicado = StringUtils.isNotEmpty(cpf) && paciente != null;
		return ( rmr != null ? duplicado && !rmr.equals(paciente.getRmr()) : duplicado );
	}

	/**
	 * Verifica se paciente já existe na base seguindo uma série de combinações que indicam duplicidade ou não.
	 * 
	 * @param rmr
	 * @param cpf
	 * @param cns
	 * @param nome
	 * @param dataNascimento
	 * @param nomeMae
	 * @param campos retorna as mensagens de validação para os erros identificados.
	 */
	private void verificarSePacientePossuiInformacoesQueIndicamDuplicidade(
			Long rmr, String cpf, String cns, String nome, String dataNascimento, String nomeMae, List<CampoMensagem> campos) {

		if (StringUtils.isNotEmpty(nome) && StringUtils.isNotEmpty(dataNascimento)) {

			boolean impedimento = false;

			final List<Paciente> pacientesComNomeDataNascimentoIguais = 
					pacienteRepository.listarPacientePorNomeAndDataNascimento(nome, dataNascimento);

			if (CollectionUtils.isNotEmpty(pacientesComNomeDataNascimentoIguais)) {
				
				for (Paciente pacienteMesmoNomeDtNasc : pacientesComNomeDataNascimentoIguais) {
					
					if (rmr != null && rmr.equals(pacienteMesmoNomeDtNasc.getRmr())) {
						continue;
					}

					final boolean seNomeMaeParametroNulo = StringUtils.isEmpty(nomeMae);
					final boolean seNomeMaeBancoNulo = StringUtils.isEmpty(pacienteMesmoNomeDtNasc.getNomeMae());
					final boolean seCpfNuloParametroECpfPreenchidoBanco = 
							StringUtils.isEmpty(cpf) && StringUtils.isNotEmpty(pacienteMesmoNomeDtNasc.getCpf());
					final boolean seCnsNuloParametroECnsPreenchidoBanco = 
							StringUtils.isEmpty(cns) && StringUtils.isNotEmpty(pacienteMesmoNomeDtNasc.getCns());

					if (seNomeMaeParametroNulo) {

						if (seNomeMaeBancoNulo) {
							
							final boolean seCpfECnsPreenchidosNoParametro = 
									StringUtils.isNotEmpty(cpf) && StringUtils.isNotEmpty(cns);
							final boolean seCpfECnsNulosNoBanco = 
									StringUtils.isEmpty(pacienteMesmoNomeDtNasc.getCpf()) && 
									StringUtils.isEmpty(pacienteMesmoNomeDtNasc.getCns());

							if (( seCpfECnsPreenchidosNoParametro && seCpfECnsNulosNoBanco )
									|| ( seCpfNuloParametroECpfPreenchidoBanco && seCnsNuloParametroECnsPreenchidoBanco )) {
								impedimento = true;
								break;
							}
						}
						else {
							if (seCpfNuloParametroECpfPreenchidoBanco && seCnsNuloParametroECnsPreenchidoBanco) {
								impedimento = true;
								break;
							}
						}
					}
					else {
						final boolean seCpfPreenchidoParametroECpfNuloBanco = 
								StringUtils.isNotEmpty(cpf) && StringUtils.isEmpty(pacienteMesmoNomeDtNasc.getCpf());
						final boolean seCnsPreenchidoParametroECnsCnsNuloBanco = 
								StringUtils.isNotEmpty(cns) && StringUtils.isEmpty(pacienteMesmoNomeDtNasc.getCns());
						final boolean seNomeMaeIgual = StringUtils.equals(
								StringUtils.upperCase(nomeMae), StringUtils.upperCase(pacienteMesmoNomeDtNasc.getNomeMae()));

						if (seNomeMaeBancoNulo) {
							if (( seCpfPreenchidoParametroECpfNuloBanco	&& seCnsPreenchidoParametroECnsCnsNuloBanco )
								|| ( seCpfNuloParametroECpfPreenchidoBanco && seCnsNuloParametroECnsPreenchidoBanco )) {
								impedimento = true;
								break;
							}
						}
						else
							if (seNomeMaeIgual) {
								if (( seCpfPreenchidoParametroECpfNuloBanco	&& seCnsPreenchidoParametroECnsCnsNuloBanco )
									|| ( seCpfNuloParametroECpfPreenchidoBanco && seCnsNuloParametroECnsPreenchidoBanco )) {
									impedimento = true;
									break;
								}
							}
					}
				}
			}
			if (impedimento) {
				campos.add(new CampoMensagem("erro",
						AppUtil.getMensagem(messageSource,"combinacao.nome.datanascimento.nomemae.paciente.ja.utilizados")));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void verificarPacienteJaCadastradoRetorno(Paciente paciente) {
		List<CampoMensagem> campos = new ArrayList<>();
		verificarPacienteJaCadastrado(paciente, campos);
		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}
	}

	/**
	 * Método para validar se o paciente já foi cadastrado na base.
	 * 
	 * @param Paciente dados do endereço do paciente
	 * @param Campos mensagens de erro a serem retornadas
	 */
	private void validarPacienteCadastrado(Paciente paciente, List<CampoMensagem> campos) {
		verificarPacienteJaCadastrado(paciente, campos);
	}

	/**
	 * Método para validar a Nacionalidade do paciente contra a base de dados.
	 * 
	 * @param Paciente dados pessoais do paciente
	 * @param Campos mensagens de erro a serem retornadas
	 */
	private void validarPais(Paciente paciente, List<CampoMensagem> campos) {
		if (paciente.getPais() != null && paciente.getPais().getId() != null) {
			if (paisService.obterPais(paciente.getPais().getId()) == null) {
				campos.add(new CampoMensagem("pais.id", messageSource.getMessage("pais.invalido",
						null, LocaleContextHolder.getLocale())));
			}
		}
	}

	/**
	 * Método para validar a Naturalidade do paciente contra a base de dados.
	 * 
	 * @param Paciente dados pessoais do paciente
	 * @param Campos mensagens de erro a serem retornadas
	 */
	private void validarUf(Paciente paciente, List<CampoMensagem> campos) {
		if (paciente.getNaturalidade() != null && !StringUtils.isEmpty(paciente.getNaturalidade()
				.getSigla())) {
			Uf uf = ufService.getUf(paciente.getNaturalidade().getSigla());
			if (uf == null) {
				campos.add(new CampoMensagem("uf.sigla", messageSource.getMessage("uf.invalido",
						null, LocaleContextHolder.getLocale())));
			}
		}
	}

	/**
	 * Método para validar a Raça do paciente contra a base de dados.
	 * 
	 * @param Paciente dados pessoais do paciente
	 * @param Campos mensagens de erro a serem retornadas
	 */
	private void validarRaca(Paciente paciente, List<CampoMensagem> campos) {
		if (paciente.getRaca() != null && paciente.getRaca().getId() != null) {
			if (racaService.obterRaca(paciente.getRaca().getId()) == null) {
				campos.add(new CampoMensagem("raca.id", messageSource.getMessage("raca.invalido",
						null, LocaleContextHolder.getLocale())));
			}
		}
	}

	/**
	 * Método para validar a Etnia do paciente contra a base de dados.
	 * 
	 * @param Paciente dados pessoais do paciente
	 * @param Campos mensagens de erro a serem retornadas
	 */
	private void validarEtnia(Paciente paciente, List<CampoMensagem> campos) {
		if (paciente.getEtnia() != null && paciente.getEtnia().getId() != null) {
			if (etniaService.getEtnia(paciente.getEtnia().getId()) == null) {
				campos.add(new CampoMensagem("etnia", messageSource.getMessage("etnia.invalido",
						null, LocaleContextHolder.getLocale())));
			}
		}
	}

	/**
	 * Método para validar se exame contém os locus A, B e RDB1.
	 * 
	 * @param exame - exame que contém os locus exames para comparar
	 * @param campos - campos de mensagem caso não seja valido
	 * @author Fillipe Queiroz
	 */
	private void validarSeExameContemLocusAeBeDRB1(ExamePaciente exame,
			List<CampoMensagem> campos) {
		if (!Optional.ofNullable(exame.getLocusExames()).isPresent() || !exameContemLocusAeBeRDB1(
				exame)) {
			campos.add(new CampoMensagem("exame", messageSource.getMessage(
					"primeiro.exame.locus.invalido",
					null,
					LocaleContextHolder.getLocale())));
		}
	}

	/**
	 * Método que retorna um booleano se o locus é valido.
	 * 
	 * @param exame - entidade que contém a lista de locus
	 * @return boolean se locus informado é valido
	 * @author Fillipe Queiroz
	 */
	private boolean exameContemLocusAeBeRDB1(ExamePaciente exame) {
		boolean contemLocusABRDB1 = exame.getLocusExames().stream()
				.filter(obj -> ( Locus.LOCUS_A.equalsIgnoreCase(obj.getId().getLocus().getCodigo())
						|| Locus.LOCUS_B.equalsIgnoreCase(obj.getId().getLocus().getCodigo())
						|| Locus.LOCUS_DRB1.equals(obj.getId().getLocus().getCodigo()) ))
				.count() == 3;
		return contemLocusABRDB1;
	}

	/**
	 * Método para validar Metodologias contra o domínio.
	 * 
	 * @param metodologiasParaComparar
	 * @param campos
	 * @author Fillipe Queiroz
	 */
	private void validarSeMetodologiasSaoValidasPorId(List<Metodologia> metodologiasParaComparar,
			List<CampoMensagem> campos) {
		metodologiasParaComparar.forEach(metodologiaParaComparar -> this
				.compararMetodologiaPorId(metodologiaParaComparar, campos));
	}

	/**
	 * Método para comparar a metodologia informada contra os domínios. existentes
	 * 
	 * @param metodologiaParaComparar
	 * @param metodologiaParaComparar, campos
	 * @author Fillipe Queiroz
	 */
	private void compararMetodologiaPorId(Metodologia metodologiaParaComparar,
			List<CampoMensagem> campos) {
		List<Metodologia> metodologiasExistentes = metodologiaService.listarMetodologias();
		boolean existeMetodologia = metodologiasExistentes.stream().anyMatch(
				metodologia -> metodologia
						.getId().equals(metodologiaParaComparar.getId()));
		if (!existeMetodologia) {
			campos.add(new CampoMensagem("exame", messageSource.getMessage(
					"metodologia.nao.existente",
					null,
					LocaleContextHolder.getLocale())));
		}
	}

	private Boolean isCentroTransplanteInexistente(CentroTransplante centroTransplante,
			List<CampoMensagem> campos) {
		return !centroTransplanteService.isCentroTransplanteExistente(centroTransplante.getId());
	}

	/**
	 * Método para validar o Médico Responsável do paciente contra a base de dados.
	 * 
	 * @param Paciente dados pessoais do paciente
	 * @param Campos mensagens de erro a serem retornadas
	 */
	private void validarMedicoResponsavel(Paciente paciente, List<CampoMensagem> campos) {
		if (paciente.getMedicoResponsavel() != null && paciente.getMedicoResponsavel()
				.getId() != null) {
			if (medicoService.findById(paciente.getMedicoResponsavel().getId()) == null) {
				campos.add(new CampoMensagem("medico.id", messageSource.getMessage(
						"medico.invalido",
						null, LocaleContextHolder.getLocale())));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Paciente obterPaciente(Long rmr) {
		Paciente paciente = pacienteRepository.findById(rmr).get();
		if (paciente == null) {
			throw new BusinessException("erro.paciente.obter");
		}
		paciente.setGenotipo(genotipoService.obterGenotipoPorPaciente(rmr));
		/*List<TarefaDTO> listarTarefasNotificacoes = listarTarefasConferenciaExames(usuarioService.obterUsuarioLogado(), rmr,
				new TipoTarefa(TiposTarefa.NOTIFICACAO.getId()));*/
		paciente.setQuantidadeNotificacoes(contarNotificacoes(rmr, true));
		//paciente.setQuantidadeNotificacoes(listarTarefasNotificacoes == null ? 0 : listarTarefasNotificacoes.size());
		
		if(verificarPacienteEmObito(rmr, false)){
			historicoStatusPacienteService.adicionarStatusPaciente(StatusPacientes.OBITO, paciente);
		}
		
		final boolean avaliacaoEmAndamento = 
				avaliacaoService.verificarSeAvaliacaoEmAndamento(rmr) || avaliacaoNovaBuscaService.verificarAvaliacaoEmAndamento(rmr);
		paciente.setTemAvaliacaoIniciada(avaliacaoEmAndamento);
		paciente.setTemAvaliacaoAprovada(avaliacaoService.verificarSeAvaliacaoAprovada(rmr));
		paciente.setTemBuscaAtiva(buscaService.obterBuscaAtivaPorRmr(rmr) != null);
		paciente.setIdTipoExameFase3(pedidoExameService.obterTipoExameCtParaPacienteComResultadoAguardandoAmostra(rmr));
		
		final PedidoTransferenciaCentro pedidoTransfCentroAvaliador = pedidoTransferenciaCentroService.obterPedidoNaoAvaliado(rmr);
		paciente.setTemPedidoTransferenciaCentroAvaliadorEmAberto(pedidoTransfCentroAvaliador != null);
		
		return paciente;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Evolucao obterUltimaEvolucao(Long rmr) {
		Evolucao evolucaoEncontrada = evolucaoRepository
				.findFirstByPacienteRmrOrderByDataCriacaoDesc(rmr);
		if (evolucaoEncontrada == null) {
			throw new BusinessException("erro.ultima.evolucao.nao.encontrada");
		}
		return evolucaoEncontrada;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<Paciente> listarPacientePorRmrOuNome(Long rmr, String nome, Boolean meusPacientes, Long idFuncaoTransplante, Long idCentroAvaliador,
			PageRequest pageRequest) {
		LOGGER.info("inicio da busca de paciente");

		Page<Paciente> pagina = null;
		
		if (rmr != null) {
			Paciente pacienteEncontrado = pacienteRepository.findById(rmr).orElse(null);
			if (pacienteEncontrado != null) {
				if (meusPacientes) {
					if (pacienteEncontrado.getMedicoResponsavel().getUsuario().getId().equals(usuarioService.obterUsuarioLogadoId())) {
						pagina = new PageImpl<>(Arrays.asList(pacienteEncontrado), pageRequest, 1);		
					}
					else {
						throw new BusinessException("mensagem.nenhum.registro.encontrado", "registro");
					}
				}
				else if (idCentroAvaliador != null && (pacienteEncontrado.getCentroAvaliador().getId().equals(idCentroAvaliador) || 
							pacienteEncontrado.getBuscaAtiva().getCentroTransplante().getId().equals(idCentroAvaliador))) {
					pagina = new PageImpl<>(Arrays.asList(pacienteEncontrado), pageRequest, 1);
				}
				else {
					throw new BusinessException("mensagem.nenhum.registro.encontrado", "registro");
				}				
			}
			else {
				throw new BusinessException("mensagem.nenhum.registro.encontrado", "registro");
			}
		}
		else {
			if (meusPacientes) {
				pagina = pacienteRepository.findByNomeContainingAndMedicoResponsavelUsuarioIdOrderByNome(nome, 
							usuarioService.obterUsuarioLogadoId(), pageRequest);
			}
			else if (idCentroAvaliador != null && idFuncaoTransplante != null) {
				pagina = pacienteRepository.findByNomeContainingAndCentroAvaliadorIdOrderByNome(nome, idFuncaoTransplante,
						idCentroAvaliador, pageRequest);
			}
			if (pagina == null || CollectionUtils.isEmpty(pagina.getContent())) {
				throw new BusinessException("mensagem.nenhum.registro.encontrado", "registro");
			}			
		}
		pagina.getContent().forEach(paciente -> {	
			paciente.setTemBuscaAtiva(paciente.getBuscaAtiva() != null);
			paciente.setQuantidadeNotificacoes(contarNotificacoes(paciente.getRmr(), true));
			
			paciente.setQuantidadeNotificacoesNaoLidas(contarNotificacoes(paciente.getRmr(), false));
		});

		LOGGER.info("fim da busca de paciente");
		return pagina;
	}

	/**
	 * @param rmr
	 * @return
	 */
	public Long contarNotificacoes(Long rmr, Boolean lido) {
		if(lido) {
			return CategoriasNotificacao.contar().comRmr(rmr).apply();
		}
		return  CategoriasNotificacao.contar().somenteNaoLidas().comRmr(rmr).apply();
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Paciente obterDadosIdentificadaoPorPaciente(Long rmr) {
		if (rmr == null) {
			throw new BusinessException("erro.parametros.invalidos", "");
		}

		Paciente pacienteEncontrado = pacienteRepository.carregarPacienteSomenteComIdentificacao(
				rmr);
		if (pacienteEncontrado == null) {
			throw new BusinessException("mensagem.nenhum.registro.encontrado", "paciente");
		}
		
		pacienteEncontrado.setBuscas(Arrays.asList(buscaService.obterBuscaAtivaPorRmr(pacienteEncontrado.getRmr())));
		pacienteEncontrado.setEvolucoes(Arrays.asList(evolucaoService.obterUltimaEvolucaoDoPaciente(rmr)));
		pacienteEncontrado.setGenotipo(genotipoService.obterGenotipoPorPaciente(pacienteEncontrado.getRmr()));

		return pacienteEncontrado;
	}

	/**
	 * Obtém a menor data de criação dentre as tarefas informadas.
	 * 
	 * @param tarefas lista a ser considerada na busca.
	 * @return a menor data de criação entre as tarefas.
	 */
	private LocalDateTime obterMenorDataCriacao(List<TarefaDTO> tarefas) {
		return tarefas.stream().map(tarefa -> tarefa.getDataCriacao()).min(LocalDateTime::compareTo).get();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Paginacao<PacienteTarefaDTO> listarPacientesEmAvaliacaoPorMedicoLogado(Long idCentroAvaliador, PageRequest paginacao) {
		final Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		final Medico medico = medicoService.obterMedicoAssociadoUsuarioLogado();
		//List<Perfil> perfisUsuarioLogado = usuarioLogado.getPerfis();
		
		Predicate<TarefaDTO> filtro = null;
		if (idCentroAvaliador == null ) {
			filtro = (tarefa -> {				
				final Avaliacao avaliacao = ((Pendencia)tarefa.getObjetoRelacaoEntidade()).getAvaliacao();
				return  avaliacao.getStatus().equals(StatusAvaliacao.ABERTA.getId()) &&  avaliacao.getPaciente().getMedicoResponsavel().equals(medico);
			});
		}
		else {
			final CentroTransplante centroAvaliador = centroTransplanteService.obterCentroTransplante(idCentroAvaliador);
			filtro = (tarefa -> { 
				final Avaliacao avaliacao = ((Pendencia)tarefa.getObjetoRelacaoEntidade()).getAvaliacao();
				return avaliacao.getStatus().equals(StatusAvaliacao.ABERTA.getId()) &&  avaliacao.getPaciente().getCentroAvaliador().equals(centroAvaliador);
			});
		}
		
		final JsonViewPage<TarefaDTO> listarTarefas = TiposTarefa.RESPONDER_PENDENCIA.getConfiguracao().listarTarefa()
			.comFiltro(filtro)
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))			
			.comUsuario(usuarioLogado)
			.apply();

		if (CollectionUtils.isNotEmpty(listarTarefas.getValue().getContent() )) {
			List<Long> rmrs = listarTarefas.getValue().getContent()
					.stream().map(tarefa -> tarefa.getProcesso().getPaciente().getRmr()).distinct()
					.collect(Collectors.toList());

			if (CollectionUtils.isNotEmpty(rmrs)) {
				List<PacienteTarefaDTO> listaFiltradaDTOs = new ArrayList<>();

				rmrs.forEach(rmr -> {
					List<TarefaDTO> tarefas = listarTarefas.getValue().getContent().stream().filter(
							tarefa -> tarefa.getProcesso().getPaciente().getRmr().equals(rmr))
							.collect(Collectors.toList());
					
					final Avaliacao avaliacao = ((Pendencia)tarefas.get(0).getObjetoRelacaoEntidade()).getAvaliacao();

					PacienteTarefaDTO dtoAtualizado = new PacienteTarefaDTO();

					dtoAtualizado.setRmr(rmr);					
					dtoAtualizado.setNome(avaliacao.getPaciente().getNome());					
					dtoAtualizado.setMedicoResponsavelPaciente(avaliacao.getPaciente().getMedicoResponsavel().getNome());
					dtoAtualizado.setDataCriacaoAvaliacao(avaliacao.getDataCriacao());
					LocalDateTime menorDataCriacao = obterMenorDataCriacao(tarefas);
					dtoAtualizado.setTotalTarefas(CollectionUtils.isEmpty(tarefas) ? 0 : tarefas.size());
					dtoAtualizado.setDataTarefa(menorDataCriacao);
					dtoAtualizado.setAgingTarefa(DateUtils.obterAging(menorDataCriacao));
					dtoAtualizado.setAgingAvaliacao(DateUtils.obterAging(avaliacao.getDataCriacao()));
					listaFiltradaDTOs.add(dtoAtualizado);
				});

				if (CollectionUtils.isNotEmpty(listaFiltradaDTOs)) {
					CriterioOrdenacao<PacienteTarefaDTO> orderByAgingCadastro = new CriterioOrdenacao<>(
							PacienteTarefaDTO::getDataCriacaoAvaliacao, Boolean.FALSE);
					CriterioOrdenacao<PacienteTarefaDTO> orderByAgingTarefa = new CriterioOrdenacao<>(
							PacienteTarefaDTO::getDataTarefa, Boolean.FALSE);

					ColecoesUtil.ordenar(listaFiltradaDTOs, orderByAgingTarefa, orderByAgingCadastro);

					return new Paginacao<>(listaFiltradaDTOs, paginacao, listaFiltradaDTOs.size());
				}

			}
		}

		return new Paginacao<>(new ArrayList<>(), paginacao, 0);
	}

	/**
	 * Método para informar se o avaliador está logado.
	 * 
	 * @param perfil
	 * @return
	 */
//	private boolean isPerfilAvaliador(Perfil perfil) {
//		return perfil.getId() == Perfis.MEDICO_AVALIADOR.getId();
//	}


	@Override
	public void atualizarDadosPessoais(Paciente pacienteAlterado) {

		LOGGER.info("inicio do metodo de atualizar identificação e dados pessoais do paciente " + pacienteAlterado.getRmr());

		AppUtil.percorrerObjetoNulandoEntidadesVazias(pacienteAlterado);

		verificarPacienteEmObito(pacienteAlterado.getRmr(), true);

		verificarPacienteJaCadastradoRetorno(pacienteAlterado);

		if (pacienteAlterado.getPais() != null && !Pais.BRASIL.equals(pacienteAlterado.getPais().getId())) {
			pacienteAlterado.setNaturalidade(null);
		}

		Paciente paciente = pacienteRepository.findById(pacienteAlterado.getRmr()).get();
		paciente.setDataNascimento(pacienteAlterado.getDataNascimento());
		paciente.setCpf(pacienteAlterado.getCpf());
		paciente.setCns(pacienteAlterado.getCns());
		paciente.setNome(pacienteAlterado.getNome());
		paciente.setNomeMae(pacienteAlterado.getNomeMae());
		paciente.setMaeDesconhecida(pacienteAlterado.getMaeDesconhecida());

		if (paciente.getResponsavel() != null && pacienteAlterado.getResponsavel() == null) {
			responsavelService.apagarResponsavel(paciente.getResponsavel().getId());
			paciente.setResponsavel(pacienteAlterado.getResponsavel());
		}
		else
			if (paciente.getResponsavel() != null && pacienteAlterado.getResponsavel() != null) {
				if (paciente.getResponsavel().getId().equals(pacienteAlterado.getResponsavel().getId())) {
					paciente.getResponsavel().setCpf(pacienteAlterado.getResponsavel().getCpf());
					paciente.getResponsavel().setNome(pacienteAlterado.getResponsavel().getNome());
					paciente.getResponsavel().setParentesco(pacienteAlterado.getResponsavel().getParentesco());
				}
			}
			else
				if (paciente.getResponsavel() == null && pacienteAlterado.getResponsavel() != null) {
					paciente.setResponsavel(pacienteAlterado.getResponsavel());
				}

		paciente.setSexo(pacienteAlterado.getSexo());
		paciente.setPais(pacienteAlterado.getPais());
		paciente.setNaturalidade(pacienteAlterado.getNaturalidade());
		paciente.setAbo(pacienteAlterado.getAbo());
		paciente.setRaca(pacienteAlterado.getRaca());
		paciente.setEtnia(pacienteAlterado.getEtnia());

		List<CampoMensagem> mensagensRetorno = validarIdentificacaoDadosPessoais(paciente);

		if (!mensagensRetorno.isEmpty()) {
			throw new ValidationException("erro.validacao", mensagensRetorno);
		}

		LOGGER.info("Atualizando identificação e dados pessoais do paciente " + paciente.getRmr() + " no banco.");
		pacienteRepository.atualizarDadosPessoais(paciente);

		LOGGER.info("fim do metodo de de atualizar identificação e dados pessoais do paciente");
	}

	@Override
	public Paciente obterDadosPessoais(Long rmr) {
		Paciente paciente = pacienteRepository.findDadosPessoais(rmr);
		if (paciente == null) {
			throw new BusinessException("erro.paciente.obter");
		}
		paciente.setTemAvaliacaoIniciada(avaliacaoService.verificarSeAvaliacaoEmAndamento(rmr));
		paciente.setTemAvaliacaoAprovada(avaliacaoService.verificarSeAvaliacaoAprovada(rmr));
		return paciente;
	}

	@Override
	public ContatoPacienteDTO obterContatoPaciente(Long rmr) {
		ContatoPacienteDTO contatoPaciente = pacienteRepository.obterContatoPaciente(rmr);
		if (contatoPaciente == null) {
			LOGGER.error("Não foi encontrado um contato endereço/telefones para o RMR " + rmr
					+ " possível inconsistência de base.");
			throw new BusinessException("erro.contato.paciente.nao.encontrado", String.valueOf(rmr));
		}
		return contatoPaciente;
	}

	@Override
	public void atualizarContatoPaciente(Paciente paciente) {

		verificarPacienteEmObito(paciente.getRmr(), true);

		List<CampoMensagem> errosEncontrados = new ConstraintViolationTransformer(validator.validate(paciente
				.getEnderecosContato())).transform();

		if (paciente.getEmail() != null) {
			List<CampoMensagem> errosPaciente = new ConstraintViolationTransformer(validator.validate(paciente)).transform();
			if (!errosPaciente.isEmpty()) {
				errosPaciente.stream().forEach(campoMensagem -> {
					if (campoMensagem.getCampo().equals("email")) {
						errosEncontrados.add(campoMensagem);
					}
				});
			}
		}

		errosEncontrados.addAll(contatoTelefonicoService.validar(paciente.getContatosTelefonicos()));

		if (CollectionUtils.isNotEmpty(errosEncontrados)) {
			throw new ValidationException("erro.validacao", errosEncontrados);
		}

		atualizarEmail(paciente);
		paciente.getEnderecosContato()
				.forEach(enderecoContato ->
		{
					enderecoContato.setPaciente(paciente);
					endContatoService.atualizar(enderecoContato);
				});

		contatoTelefonicoService.atualizar(paciente, paciente.getContatosTelefonicos());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean verificarPacienteEmObito(Long rmr, Boolean geraExcecao) {
		Busca buscaAtiva = buscaService.obterBuscaAtivaPorRmr(rmr);
		if (buscaAtiva != null && StatusBusca.CANCELADA.equals(buscaAtiva.getStatus().getId())) {

			CancelamentoBusca cancelamentoBusca = cancelamentoBuscaService.obterUltimoCancelamentoBuscaPeloIdDaBusca(buscaAtiva
					.getId());

			if (cancelamentoBusca.getMotivoCancelamentoBusca().getId().equals(MotivoCancelamentoBusca.OBITO)) {
				if (geraExcecao) {
					throw new BusinessException("erro.paciente.obito", rmr + "");
				}
				return true;
			}

		}
		return false;
	}

	private void atualizarEmail(Paciente paciente) {
		Paciente managed = pacienteRepository.findById(paciente.getRmr()).get();
		managed.setEmail(paciente.getEmail());
		pacienteRepository.atualizarEmail(managed);
	}

	@Override
	public Paciente obterPacientePorSolicitacao(Long solicitacaoId) {
		return pacienteRepository.obterPacientePorSolicitacao(solicitacaoId);
	}

	@Override
	public Paciente obterPacientePorPedidoLogistica(PedidoLogistica pedidoLogistica) {
		if (pedidoLogistica.getPedidoWorkup() != null) {
			return pacienteRepository.obterPacientePorPedidoLogisticaComPedidoWorkup(pedidoLogistica.getId());
		}
		else {
			return pacienteRepository.obterPacientePorPedidoLogisticaComPedidoColeta(pedidoLogistica.getId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AnaliseMatchDTO listarMatchs(Long rmr, FiltroMatch filtro) {
		AnaliseMatchDTO analiseMatchDTO = new AnaliseMatchDTO();
		Busca buscaAtiva = buscaService.obterBuscaAtivaPorRmr(rmr);
		Paciente paciente = buscaAtiva.getPaciente();
		Evolucao ultimaEvolucaoDoPaciente = evolucaoService.obterUltimaEvolucaoDoPaciente(rmr);
		
		List<Evolucao> evolucoesOrdenada = paciente.getEvolucoes().stream().sorted((e1,e2)->e2.getId().compareTo(e1.getId())).collect(Collectors.toList());
		Evolucao ultimaEvolucaoAntiCorpo = evolucoesOrdenada.stream().filter(e -> e.getExameAnticorpo()).findFirst().orElse(null);

		analiseMatchDTO.setRmr(rmr);
		analiseMatchDTO.setBuscaId(buscaAtiva.getId());
		analiseMatchDTO.setPeso(ultimaEvolucaoDoPaciente.getPeso());
		analiseMatchDTO.setAbo(paciente.getAbo());
		
		analiseMatchDTO.setNomeMedicoResponsavel(paciente.getMedicoResponsavel().getNome());
		analiseMatchDTO.setNomeCentroAvaliador(paciente.getCentroAvaliador().getNome());
		
		PesquisaWmdaDTO pesquisaWmdaMedulaRecuperada = buscarPesquisaWmdaMedulaPorMatchs(buscaAtiva.getId());
		if(pesquisaWmdaMedulaRecuperada != null) {
			analiseMatchDTO.setQtdMatchWmdaMedula(pesquisaWmdaMedulaRecuperada.getQtdMatchWmda());
			analiseMatchDTO.setQtdMatchWmdaMedulaImportado(pesquisaWmdaMedulaRecuperada.getQtdMatchWmdaImportado());
		}

		PesquisaWmdaDTO pesquisaWmdaCordaoRecuperada = buscarPesquisaWmdaCordaoPorMatchs(buscaAtiva.getId());
		if(pesquisaWmdaCordaoRecuperada != null) {
			analiseMatchDTO.setQtdMatchWmdaCordao(pesquisaWmdaCordaoRecuperada.getQtdMatchWmda());
			analiseMatchDTO.setQtdMatchWmdaCordaoImportado(pesquisaWmdaCordaoRecuperada.getQtdMatchWmdaImportado());
		}
		
		analiseMatchDTO.setTotalMedula(buscarQuantidadeMatchsMedula(rmr));
		analiseMatchDTO.setTotalCordao(buscarQuantidadeMatchsCordao(rmr));
		
		analiseMatchDTO.setTotalSolicitacaoMedula(buscarQuantidadeSolicitacoesMedula(buscaAtiva.getId())); 
		analiseMatchDTO.setTotalSolicitacaoCordao(buscarQuantidadeSolicitacoesCordao(buscaAtiva.getId())); 

		analiseMatchDTO.setListaFase1(matchService.buscarListaMatchPorSituacao(rmr, Arrays.asList(FasesMatch.FASE_1), filtro, true, false));
		analiseMatchDTO.setListaFase2(matchService.buscarListaMatchPorSituacao(rmr, Arrays.asList(FasesMatch.FASE_2), filtro, true, false));
		analiseMatchDTO.setListaFase3(matchService.buscarListaMatchPorSituacao(rmr, Arrays.asList(FasesMatch.FASE_3), filtro, true, false));
		
		/*analiseMatchDTO.setListaExameFase2(buscarListaMatchPorSituacao(rmr, Arrays.asList(FasesMatch.EXAME_EXTENDIDO), filtro, true));
		analiseMatchDTO.setListaExameFase3(buscarListaMatchPorSituacao(rmr, Arrays.asList(FasesMatch.TESTE_CONFIRMATORIO), filtro, true));*/
		
		List<MatchDTO> matchsDisponibilizados = matchService.buscarListaMatchPorSituacao(rmr, Arrays.asList(FasesMatch.FASE_3), filtro, true, true);
		
		analiseMatchDTO.setListaDisponibilidade(matchsDisponibilizados);
		
		analiseMatchDTO.setAceitaMismatch(paciente.getAceiteMismatch() == 1? true: false);
		if(analiseMatchDTO.getAceitaMismatch()){
			String locusMismatch = paciente.getLocusMismatch().stream().map(locus -> {
				return locus.getCodigo();
			}).collect(Collectors.joining(", "));
			
			analiseMatchDTO.setLocusMismatch(locusMismatch);
		}
		if(ultimaEvolucaoAntiCorpo != null){
			analiseMatchDTO.setTemExameAnticorpo(true);
			analiseMatchDTO.setResultadoExameAnticorpo(ultimaEvolucaoAntiCorpo.getResultadoExameAnticorpo());
			analiseMatchDTO.setDataAnticorpo(ultimaEvolucaoAntiCorpo.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) );
		}
		
		analiseMatchDTO.setTransplantePrevio(obterStringUltimosTransplatesConcatenados(ultimaEvolucaoDoPaciente));
		
		final Long quantidadeSolicitacoesWorkupEWorkupCordao = solicitacaoService.quantidadeSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(buscaAtiva.getId(), 
				Arrays.asList(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL, 
						TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL, TiposSolicitacao.CORDAO_INTERNACIONAL ));
		
		analiseMatchDTO.setTemPrescricao( !quantidadeSolicitacoesWorkupEWorkupCordao.equals(0L) );
		analiseMatchDTO.setQuantidadePrescricoes(quantidadeSolicitacoesWorkupEWorkupCordao);
		if (analiseMatchDTO.getTemPrescricao().booleanValue()) {
			List<Solicitacao> solicitacoesWorkup = solicitacaoService.listarSolicitacoesAbertasDosMatchsDaBuscaComTipoSolicitacao(buscaAtiva.getId(), 
					Arrays.asList(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL, 
							TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL, TiposSolicitacao.CORDAO_INTERNACIONAL ));			
			
			Solicitacao solicitacaoWorkup = solicitacoesWorkup.stream().findFirst().orElse(null);  
			if (solicitacaoWorkup != null) {
				analiseMatchDTO.setTipoDoadorComPrescricao(solicitacaoWorkup.getMatch().getDoador().getTipoDoador().getId());
			}
			
			final String doadoresPrescritos = solicitacoesWorkup.stream()
				.map(sol -> sol.getMatch().getDoador())
				.map(IDoador.class::cast)
				.map(doador -> doador.getIdentificacao().toString())
				.collect(Collectors.joining(", "));
			
			analiseMatchDTO.setDoadoresPrescritos(doadoresPrescritos);
									
		}
		
		if (buscaAtiva.getCentroTransplante() != null) {
			CentroTransplante centro = new CentroTransplante(buscaAtiva.getCentroTransplante().getId(),
				buscaAtiva.getCentroTransplante().getNome());
		
			analiseMatchDTO.setCentroTransplanteConfirmado(centro);
		}
		
		if (permissaoService.usuarioLogadoPossuiPermissao(Recurso.VISTAR_CHECKLIST))  {
			analiseMatchDTO.setBuscaChecklist(buscaChecklistService.listarChecklist(buscaAtiva.getId()));
		}
		
		analiseMatchDTO.setTotalHistoricoFase1(buscaChecklistService.totalChecklistHistoricoPorSituacao(buscaAtiva.getId(), FasesMatch.FASE_1.getId(), filtro.getTiposDoadorAssociados()));
		analiseMatchDTO.setTotalHistoricoFase2(buscaChecklistService.totalChecklistHistoricoPorSituacao(buscaAtiva.getId(), FasesMatch.FASE_2.getId(), filtro.getTiposDoadorAssociados()));
		analiseMatchDTO.setTotalHistoricoFase3(buscaChecklistService.totalChecklistHistoricoPorSituacao(buscaAtiva.getId(), FasesMatch.FASE_3.getId(), filtro.getTiposDoadorAssociados()));

		return analiseMatchDTO;
	}

	private String  obterStringUltimosTransplatesConcatenados(Evolucao ultimaEvolucaoDoPaciente) {
		List<TipoTransplante> tiposTransplante = ultimaEvolucaoDoPaciente.getTiposTransplante();
		
		if(tiposTransplante.size() == 0)
			return "Não";
				
		if (tiposTransplante.size() == 1){ 
			return tiposTransplante.get(tiposTransplante.size() - 1).getDescricao(); 
		 }else {		 
			return tiposTransplante
					.stream()
					.limit(tiposTransplante.size() - 1)
					.map(ti -> ti.getDescricao())
					.collect(Collectors.joining(", "))  
					+ " e " + 
					tiposTransplante.get(tiposTransplante.size() - 1).getDescricao();
		}
	}
	
	private Long buscarQuantidadeSolicitacoesMedula(Long idBusca) {
		return solicitacaoService.obterQuantidadeSolicitacoesAbertosPorIdBuscaAndTiposDoador(idBusca, Arrays.asList(TiposDoador.NACIONAL, TiposDoador.INTERNACIONAL));
	}

	private Long buscarQuantidadeSolicitacoesCordao(Long idBusca) {
		return solicitacaoService.obterQuantidadeSolicitacoesAbertosPorIdBuscaAndTiposDoador(idBusca, Arrays.asList(TiposDoador.CORDAO_NACIONAL, TiposDoador.CORDAO_INTERNACIONAL));
	}

	private Long buscarQuantidadeMatchsMedula(Long rmr) {
		return  matchService.obterQuantidadeMatchsAtivosPorRmrAndTiposDoador(rmr, Arrays.asList(TiposDoador.NACIONAL, TiposDoador.INTERNACIONAL));		
	}
	
	private Long buscarQuantidadeMatchsCordao(Long rmr) {
		return  matchService.obterQuantidadeMatchsAtivosPorRmrAndTiposDoador(rmr, Arrays.asList(TiposDoador.CORDAO_NACIONAL, TiposDoador.CORDAO_INTERNACIONAL));		
	}

	private PesquisaWmdaDTO buscarPesquisaWmdaMedulaPorMatchs(Long buscaId) {
		List<PesquisaWmdaDTO> pesquisaRecuperada = pesquisaWmdaService.obterListaDePesquisaWmdaDeMedulaParaBusca(buscaId);	
		if(pesquisaRecuperada != null && !pesquisaRecuperada.isEmpty()) {
			return pesquisaRecuperada.stream().findAny().orElse(null);
		}
		return null;
	}

	private PesquisaWmdaDTO buscarPesquisaWmdaCordaoPorMatchs(Long buscaId) {
		List<PesquisaWmdaDTO> pesquisaRecuperada = pesquisaWmdaService.obterListaDePesquisaWmdaDeCordaoParaBusca(buscaId);	
		if(pesquisaRecuperada != null && !pesquisaRecuperada.isEmpty()) {
			return pesquisaRecuperada.stream().findAny().orElse(null);
		}
		return null;
	}

	@Override
	public Paginacao<LogEvolucaoDTO> listarLogEvolucao(Long rmr, Pageable pageable) {
		return (Paginacao<LogEvolucaoDTO>) pacienteRepository.listarLogEvolucao(rmr, usuarioService.obterUsuarioLogado().getPerfis(), pageable);
	}

	@Override
	public Paciente obterPacientePorPedidoContato(Long pedidoContatoId) {
		return pacienteRepository.obterPacientePorPedidoContato(pedidoContatoId);
	}

	@Override
	public Paciente obterPacientePorMatch(Long matchId) {
		return pacienteRepository.obterPacientePorMatch(matchId);
	}

	@Override
	public Paciente obterPaciente(Paciente paciente) {
		return paciente;
	}

	@Override
	public String[] obterParametros(Paciente paciente) {
		return new String[] { String.valueOf(paciente.getRmr()) };
	}

	@Override
	public Paciente obterPacientePorGenotipo(Long genotipoId) {
		return pacienteRepository.obterPacientePorGenotipo(genotipoId);
	}

	@Override
	public DetalheAvaliacaoPacienteDTO obterDetalheAvaliacaoPaciente(Long rmr) {
		Projection obterCentroAvaliador = new Projection("centroAvaliador");
		Projection obterMedicoResponsavel = new Projection("medicoResponsavel");

		Filter<Long> filtrarPorRmr = new Equals<Long>("rmr", rmr);

		Paciente paciente = findOne(Arrays.asList(obterCentroAvaliador, obterMedicoResponsavel),
				Arrays.asList(filtrarPorRmr));

		List<ContatoCentroTransplantadorDTO> centrosTransplantadores = centroTransplanteService.listarCentroTransplantesPorFuncao(
				FuncaoTransplante.PAPEL_TRANSPLANTADOR);

		DetalheAvaliacaoPacienteDTO detalhe = new DetalheAvaliacaoPacienteDTO();
		detalhe.setRmr(rmr);
		detalhe.setNomeCentroAvaliador(paciente.getCentroAvaliador().getNome());
		detalhe.setContatosPorCentroTransplante(centrosTransplantadores);
		detalhe.setNomeMedicoResponsavel(paciente.getMedicoResponsavel().getNome());

		Busca buscaAtiva = buscaService.obterBuscaAtivaPorRmr(rmr);
		detalhe.setCentroTransplantadorConfirmado(buscaAtiva.getCentroTransplante() != null);

		return detalhe;
	}

	@Override
	public void confirmarCentroTransplantador(Long rmr, Long centroTransplantadorId) {
		Busca buscaAtiva = buscaService.obterBuscaAtivaPorRmr(rmr);
		if (buscaAtiva == null) {
			throw new BusinessException("Não foi encontrada uma busca ativa para o paciente " + rmr
					+ " verifique se o mesmo continua ativo.");
		}
		buscaService.confirmarCentroTransplate(buscaAtiva, centroTransplantadorId);
	}

	@Override
	public void indefinirCentroTransplantador(Long rmr) {
		Busca buscaAtiva = buscaService.obterBuscaAtivaPorRmr(rmr);
		if (buscaAtiva == null) {
			throw new BusinessException("Não foi encontrada uma busca ativa para o paciente " + rmr
					+ " verifique se o mesmo continua ativo.");
		}
		buscaService.indefinirCentroTransplante(buscaAtiva);
	}

	@Override
	public boolean verificarSePacienteProibidoReceberNovosExames(Long rmr) {
		Busca buscaAtivaDoPaciente = buscaService.obterBuscaAtivaPorRmr(rmr);
		return buscaAtivaDoPaciente == null ? true
				: StatusBusca.MATCH_SELECIONADO.equals(buscaAtivaDoPaciente.getStatus().getId());
	}

	@Override
	public void recusarPacientePeloCentroTransplante(Long rmr, String justificativa) {
		Busca buscaAtiva = buscaService.obterBuscaAtivaPorRmr(rmr);
		if (buscaAtiva == null) {
			throw new BusinessException("Não foi encontrada uma busca ativa para o paciente " + rmr
					+ " verifique se o mesmo continua ativo.");
		}
		buscaService.recusarCentroTransplante(buscaAtiva, justificativa);
	}

	@Override
	public Paciente obterPacientePorPedidoExame(Long pedidoExameId) {
		return pacienteRepository.obterPacientePorPedidoExame(pedidoExameId);
	}

	@Override
	public Paciente obterPacientePorSolicitacaoCT(Long solicitacaoId) {
		return pacienteRepository.obterPacientePorSolicitacaoCT(solicitacaoId);
	}

	@Override
	public Medico obterMedicoResponsavel(Paciente paciente) {
		return pacienteRepository.obterMedicoResponsavel(paciente.getRmr());
	}

	@Override
	public CentroTransplante obterCentroTransplanteConfirmado(Paciente paciente) {
		return pacienteRepository.obterCentroTransplanteConfirmado(paciente.getRmr());
	}
	
	@Override
	public void transferirCentroAvaliador(Long rmr, CentroTransplante centroAvaliadorDestino){
		final Paciente paciente = findById(rmr);
		if(StatusPacientes.OBITO.getId().equals(paciente.getStatus().getId())){
			throw new BusinessException("pedido.transferencia.paciente.obito.falha");
		}
		paciente.setCentroAvaliador(centroAvaliadorDestino);
		save(paciente);
	}

	@Override
	public Diagnostico obterDiagnostico(Long rmr) {
		final Paciente paciente = obterPaciente(rmr);
		return paciente.getDiagnostico();
	}

	@Override
	public MismatchDTO obterDadosMismatch(Long rmr) {
		final Paciente paciente = obterPaciente(rmr);
		final MismatchDTO mismatch = new MismatchDTO();
		mismatch.setRmr(paciente.getRmr());
		mismatch.setAceiteMismatch(paciente.getAceiteMismatch());
		mismatch.setLocusMismatch(paciente.getLocusMismatch());
		return mismatch;
	}

	@Override
	public void alterarDadosMismatch(Long rmr, MismatchDTO mismatchDTO) {
		final Paciente paciente = obterPaciente(rmr);
		paciente.setAceiteMismatch(mismatchDTO.getAceiteMismatch());
		paciente.setLocusMismatch(mismatchDTO.getLocusMismatch());
		
		List<CampoMensagem> mensagensRetorno = validar(paciente);

		if (!mensagensRetorno.isEmpty()) {
			throw new ValidationException("erro.validacao", mensagensRetorno);
		}		
		
		save(paciente);		
	}
	
	private void validarDadosMismatch(Paciente paciente, List<CampoMensagem> campos) {

		if (paciente.getAceiteMismatch() != null && paciente.getAceiteMismatch().equals(1) &&
			(paciente.getLocusMismatch() == null || paciente.getLocusMismatch().isEmpty())) {
			campos.add(obterMensagemDeErro("locus", "erro.locus.mismatch.invalido"));
		}		
		
	}

	@Override
	public void solicitarNovaBusca(Long rmr) {
		final Paciente paciente = findById(rmr);
		avaliacaoNovaBuscaService.criarNovaAvaliacao(paciente);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MatchDTO> listarFaseInativos(Long rmr, FiltroMatch filtro, FasesMatch fase) {
		final Paciente paciente = findById(rmr);
		if (paciente == null) {
			throw new BusinessException("erro.paciente.obter");			
		}
		
		final Busca buscaAtiva = buscaService.obterBuscaAtivaPorRmr(rmr);
		if (buscaAtiva == null) {
			throw new BusinessException("paciente.sem.busca.ativa.mensagem", rmr+"");
		}		
		
		if (FasesMatch.FASE_1.equals(fase)) {
			return matchService.buscarListaMatchInativoComSolicitacao(rmr, FasesMatch.FASE_1.getId(), filtro);
		}
		else if (FasesMatch.FASE_2.equals(fase)) {
			return matchService.buscarListaMatchInativoComSolicitacao(rmr, FasesMatch.FASE_2.getId(), filtro);
		}
		else if (FasesMatch.FASE_3.equals(fase)) {
			return matchService.buscarListaMatchInativoComSolicitacao(rmr, FasesMatch.FASE_3.getId(), filtro);
		}
		else {
			throw new BusinessException("erro.interno");
		}		
	}
	
	
	@Override
	public StatusPacienteDTO obterStatusPacientePorRmr(Long rmr) {
		StatusPaciente statusPaciente = this.statusPacienteService.obterStatusPacientePorRmr(rmr);
		if(statusPaciente != null) {
			StatusPacienteDTO status = new StatusPacienteDTO();
			status.setId(statusPaciente.getId());
			status.setDescricao(statusPaciente.getDescricao());
			status.setOrdem(statusPaciente.getOrdem());
			return status;
		}
		return new StatusPacienteDTO(); 
	}

	/**
	 * @param genotipoService the genotipoService to set
	 */
	@Autowired
	public void setGenotipoService(IGenotipoPacienteService genotipoService) {
		this.genotipoService = genotipoService;
	}

	/**
	 * @param racaService the racaService to set
	 */
	@Autowired
	public void setRacaService(IRacaService racaService) {
		this.racaService = racaService;
	}

	/**
	 * @param racaService the racaService to set
	 */
	@Autowired
	public void setLogEvolucaoService(ILogEvolucaoService logEvolucaoService) {
		this.logEvolucaoService = logEvolucaoService;
	}
	
	/**
	 * @param etniaService the etniaService to set
	 */
	@Autowired
	public void setEtniaService(IEtniaService etniaService) {
		this.etniaService = etniaService;
	}

	/**
	 * @param paisService the paisService to set
	 */
	@Autowired
	public void setPaisService(IPaisService paisService) {
		this.paisService = paisService;
	}

	/**
	 * @param doadorService the doadorService to set
	 */
//	@Autowired
//	public void setDoadorService(IDoadorService doadorService) {
//		this.doadorService = doadorService;
//	}

	/**
	 * @param ufService the ufService to set
	 */
	@Autowired
	public void setUfService(IUfService ufService) {
		this.ufService = ufService;
	}

	/**
	 * @param diagnosticoService the diagnosticoService to set
	 */
	@Autowired
	public void setDiagnosticoService(IDiagnosticoService diagnosticoService) {
		this.diagnosticoService = diagnosticoService;
	}

	/**
	 * @param evolucaoService the evolucaoService to set
	 */
	@Autowired
	public void setEvolucaoService(IEvolucaoService evolucaoService) {
		this.evolucaoService = evolucaoService;
	}

	/**
	 * @param metodologiaService the metodologiaService to set
	 */
	@Autowired
	public void setMetodologiaService(IMetodologiaService metodologiaService) {
		this.metodologiaService = metodologiaService;
	}

	/**
	 * @param exameService the exameService to set
	 */
	@Autowired
	public void setExameService(IExamePacienteService exameService) {
		this.exameService = exameService;
	}

	/**
	 * @param centroTransplanteService the centroTransplanteService to set
	 */
	@Autowired
	public void setCentroTransplanteService(ICentroTransplanteService centroTransplanteService) {
		this.centroTransplanteService = centroTransplanteService;
	}

	/**
	 * @param medicoService the medicoService to set
	 */
	@Autowired
	public void setMedicoService(IMedicoService medicoService) {
		this.medicoService = medicoService;
	}

	/**
	 * @param rascunhoService the rascunhoService to set
	 */
	@Autowired
	public void setRascunhoService(IRascunhoService rascunhoService) {
		this.rascunhoService = rascunhoService;
	}

	/**
	 * @param avaliacaoService the avaliacaoService to set
	 */
	@Autowired
	public void setAvaliacaoService(IAvaliacaoService avaliacaoService) {
		this.avaliacaoService = avaliacaoService;
	}

	/**
	 * @param matchService the matchService to set
	 */
	@Autowired
	public void setMatchService(IMatchService matchService) {
		this.matchService = matchService;
	}

	/**
	 * @param pesquisaWmdaService the pesquisaWmdaService to set
	 */
	@Autowired
	public void setPesquisaWmdaService(IPesquisaWmdaService pesquisaWmdaService) {
		this.pesquisaWmdaService = pesquisaWmdaService;
	}
	
	/**
	 * @param buscaChecklistService the buscaChecklistService to set
	 */
	@Autowired
	public void setBuscaChecklistService(IBuscaChecklistService buscaChecklistService) {
		this.buscaChecklistService = buscaChecklistService;
	}

	/**
	 * @param buscaService the buscaService to set
	 */
	@Autowired
	public void setBuscaService(IBuscaService buscaService) {
		this.buscaService = buscaService;
	}

	/**
	 * @param endContatoService the endContatoService to set
	 */
	@Autowired
	public void setEndContatoService(IEnderecoContatoPacienteService endContatoService) {
		this.endContatoService = endContatoService;
	}

	/**
	 * @param contatoTelefonicoService the contatoTelefonicoService to set
	 */
	@Autowired
	public void setContatoTelefonicoService(IContatoTelefonicoPacienteService contatoTelefonicoService) {
		this.contatoTelefonicoService = contatoTelefonicoService;
	}

	/**
	 * @param responsavelService the responsavelService to set
	 */
	@Autowired
	public void setResponsavelService(IResponsavelService responsavelService) {
		this.responsavelService = responsavelService;
	}

	/**
	 * @param cancelamentoBuscaService the cancelamentoBuscaService to set
	 */
	@Autowired
	public void setCancelamentoBuscaService(ICancelamentoBuscaService cancelamentoBuscaService) {
		this.cancelamentoBuscaService = cancelamentoBuscaService;
	}

	/**
	 * @param pedidoExameService the pedidoExameService to set
	 */
	@Autowired
	public void setPedidoExameService(IPedidoExameService pedidoExameService) {
		this.pedidoExameService = pedidoExameService;
	}

	/**
	 * @param pedidoTransferenciaCentroService the pedidoTransferenciaCentroService to set
	 */
	@Autowired
	public void setPedidoTransferenciaCentroService(IPedidoTransferenciaCentroService pedidoTransferenciaCentroService) {
		this.pedidoTransferenciaCentroService = pedidoTransferenciaCentroService;
	}

	/**
	 * @param historicoStatusPacienteService the historicoStatusPacienteService to set
	 */
	@Autowired
	public void setHistoricoStatusPacienteService(IHistoricoStatusPacienteService historicoStatusPacienteService) {
		this.historicoStatusPacienteService = historicoStatusPacienteService;
	}

	/**
	 * @param avaliacaoNovaBuscaService the avaliacaoNovaBuscaService to set
	 */
	@Autowired
	public void setAvaliacaoNovaBuscaService(IAvaliacaoNovaBuscaService avaliacaoNovaBuscaService) {
		this.avaliacaoNovaBuscaService = avaliacaoNovaBuscaService;
	}

	/**
	 * @param statusPacienteService the statusPacienteService to set
	 */
	@Autowired
	public void setStatusPacienteService(IStatusPacienteService statusPacienteService) {
		this.statusPacienteService = statusPacienteService;
	}

	/**
	 * @param solicitacaoService the solicitacaoService to set
	 */
	@Autowired
	public void setSolicitacaoService(ISolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	/**
	 * @param permissaoService the permissaoService to set
	 */
	@Autowired
	public void setPermissaoService(IPermissaoService permissaoService) {
		this.permissaoService = permissaoService;
	}

	
	/**
	 * @param pacienteRepository the pacienteRepository to set
	 */
	/*@Autowired
	public void setPacienteRepository(IPacienteRepository pacienteRepository) {
		this.pacienteRepository = pacienteRepository;
	}*/
	
	
	
	/**
	 * Lista todos os pedidos de exame conciliados.
	 * 
	 * @param rmr - filtro rmr do paciente
	 * @param nomePaciente - filtro nome do paciente
	 * @param pageable - paginação
	 * @return PedidosPacienteInvoiceDTO - lista paginada de pedidos
	 */
	@Override
	public Paginacao<PedidosPacienteInvoiceDTO> listarPedidosPacienteInvoicePorRmrENomePaciente(Long rmr, String nomePaciente, Pageable pageable) {
		return pacienteRepository.listarPedidosPacienteInvoicePorRmrENomePaciente(rmr, nomePaciente, pageable); 
	}

	@Override
	public List<PedidosPacienteInvoiceDTO> listarPedidosPacienteInvoicePorRmrENomePaciente(Long rmr, String nome) {
		Paginacao<PedidosPacienteInvoiceDTO> listaPaginadaDePedidos = this.listarPedidosPacienteInvoicePorRmrENomePaciente(rmr, nome, null);
		if(!listaPaginadaDePedidos.isEmpty()) {
			return listaPaginadaDePedidos.getContent();
		}
		return new ArrayList<>();
	}

	@Override
	public PacienteWmdaDTO obterPacienteDtoWmdaPorRmr(Long rmr) {
		Paciente paciente = this.pacienteRepository.findById(rmr).orElse(null);
		if (paciente == null) {
			throw new BusinessException("erro.paciente.obter");
		}
		GenotipoPaciente genotipoPaciente = this.genotipoService.obterGenotipoPorPaciente(paciente.getRmr());
		return new PacienteWmdaDTO().parse(paciente, genotipoPaciente);
	}

	@Override
	public void atualizarWmdaIdPorRmrDoPaciente(Long rmr, String idWmda) {
		Paciente pacienteRecuperado = pacienteRepository.findById(rmr).orElse(null);
		if (pacienteRecuperado == null) {
			throw new BusinessException("erro.paciente.obter");
		}
		Paciente pacienteClonado = pacienteRecuperado.clone();
		pacienteClonado.setWmdaId(idWmda);

		pacienteRepository.save(pacienteClonado);
	}

	private void criarLogEvolucao(Paciente paciente, TipoLogEvolucao tipoLogEvolucao) {
		
		String[] parametros = obterParametros(paciente);
		
		CriarLogEvolucaoDTO logPaciente = new CriarLogEvolucaoDTO();
		logPaciente.setRmr(paciente.getRmr());
		logPaciente.setParametros(parametros);
		logPaciente.setTipo(tipoLogEvolucao.name());
		logEvolucaoService.criarLogEvolucao(logPaciente);
				
	}

}
