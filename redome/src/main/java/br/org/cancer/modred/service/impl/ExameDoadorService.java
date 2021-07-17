package br.org.cancer.modred.service.impl;

import static java.time.temporal.ChronoUnit.DAYS;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.doador.ExameDoadorNacionalDTO;
import br.org.cancer.modred.controller.dto.doador.LocusExameDTO;
import br.org.cancer.modred.controller.dto.doador.MetodologiaDTO;
import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.LocusExamePk;
import br.org.cancer.modred.model.Metodologia;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.PedidoIdm;
import br.org.cancer.modred.model.TipoExame;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.StatusExame;
import br.org.cancer.modred.model.domain.TiposExame;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.interfaces.IExameDoador;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IExameCordaoNacionalRepository;
import br.org.cancer.modred.persistence.IExameCordaoRepository;
import br.org.cancer.modred.persistence.IExameDoadorBaseRepository;
import br.org.cancer.modred.persistence.IExameDoadorInternacionalRepository;
import br.org.cancer.modred.persistence.IExameDoadorNacionalRepository;
import br.org.cancer.modred.persistence.IExameDoadorRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.IDoadorService;
import br.org.cancer.modred.service.IExameDoadorService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.IPedidoIdmService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.IValorGService;
import br.org.cancer.modred.service.IValorNmdpService;
import br.org.cancer.modred.service.IValorPService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.ArquivoUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de implementação da interface IExameDoadorInternacionalService.java.
 * O objetivo é fornecer os acessos as informações relativas ao exames do doador.
 * @param <T> define o exame que será utilizado para as funções deste service.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ExameDoadorService<T extends Exame> extends ExameService<T> implements IExameDoadorService<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExameDoadorService.class);


	@Autowired
	@Lazy(true)
	private ITarefaFeign tarefaFeign;
	
	@Autowired
	private IExameDoadorNacionalRepository exameDoadorNacionalRepository;
	
	@Autowired
	private IExameDoadorInternacionalRepository exameDoadorInternacionalRepository;
	
	@Autowired
	private IExameCordaoRepository exameCordaoInternacionalRepository;
	
	@Autowired
	private IExameCordaoNacionalRepository exameCordaoNacionalRepository;
	
	@Autowired
	@Qualifier("iExameDoadorRepository")
	private IExameDoadorRepository exameRepository;
	
	@Autowired
	private IConfiguracaoService configuracaoService;
	
	@Autowired
	private IStorageService storageService;
	
	@Autowired
	private IPedidoExameService pedidoExameService;
	
	@Autowired
	private IPedidoIdmService pedidoIdmService;
	
	@Autowired
	private IValorNmdpService valorNmdpService;
	
	@Autowired
	private IValorGService valorGService;
	
	@Autowired
	private IValorPService valorPService;

	@Autowired
	private LocusService locusService;
	
	private IDoadorService doadorService;
	
	
	@Override
	public IRepository<T, Long> getRepository() {
		String className = obtainClassName();
		if ("br.org.cancer.modred.model.Exame".equals(className) ) {
			return (IRepository<T, Long>) exameRepository;
		}
		if ("br.org.cancer.modred.model.ExameDoadorNacional".equals(className) ) {
			return (IRepository<T, Long>) exameDoadorNacionalRepository;
		}
		else if ("br.org.cancer.modred.model.ExameDoadorInternacional".equals(className) ) {
			return (IRepository<T, Long>) exameDoadorInternacionalRepository;
		}
		else if ("br.org.cancer.modred.model.ExameCordaoInternacional".equals(className) ) {
			return (IRepository<T, Long>) exameCordaoInternacionalRepository;
		}
		else if ("br.org.cancer.modred.model.ExameCordaoNacional".equals(className) ) {
			return (IRepository<T, Long>) exameCordaoNacionalRepository;
		}
		
		return null;
	}
	
	@Override
	public Paciente obterPaciente(Exame exame) {
		return null;
	}

	@Override
	public List<T> listarInformacoesExames(Long idDoador) {
		return ((IExameDoadorBaseRepository<T>) getRepository()).listarExamesDoador(idDoador);
		/*List<T> examesPorPaciente = exameDoadorRepository.listarExamesDoador(idDoador);
		if (CollectionUtils.isNotEmpty(examesPorPaciente)) {
			examesPorPaciente.forEach(exame -> {
				List<LocusExame> locusExamesPorExame = locusService.listarLocusExames(exame.getId());
				exame.setLocusExames(locusExamesPorExame);
			});
		}
		return examesPorPaciente;*/
	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public List<T> listarInformacoesExames(Long idDoador) {
//		List<T> examesPorPaciente = (List<T>) exameRepository.listarExamesDoador(idDoador);
//		if (CollectionUtils.isNotEmpty(examesPorPaciente)) {
//			examesPorPaciente.forEach(exame -> {
//				List<LocusExame> locusExamesPorExame = locusService.listarLocusExames(exame.getId());
//				exame.setLocusExames(locusExamesPorExame);
//			});
//		}
//		return examesPorPaciente;
//	}

	
	@Override
	public void salvar(List<MultipartFile> listaArquivosLaudo, T exame, TipoExame tipoExame) throws Exception {
		LOGGER.info("Inicio do salvar um novo exame doador internacional.");
		try {			
			validarExame(exame);

			if (TiposExame.TESTE_CONFIRMATORIO.getId().equals(tipoExame.getId()) && listaArquivosLaudo != null) {
				ArquivoUtil.validarArquivoExame(listaArquivosLaudo, messageSource, configuracaoService);
				final String diretorioExame = AppUtil.gerarStringAleatoriaAlfanumerica();
				
				Instant instant = Instant.now();
				final long timeStampMillis = instant.toEpochMilli();
				
				exame.getArquivosExame().forEach(arquivo -> {
					arquivo.setCaminhoArquivo(diretorioExame + "/"
							+ ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo.getCaminhoArquivo()));
				});
				LOGGER.info("Inicio do upload de arquivos no storage.");
				
				uploadArquivos(listaArquivosLaudo, ((IExameDoador) exame).getDoador().getId(), diretorioExame, timeStampMillis);
				LOGGER.info("Fim do upload de arquivos no storage.");
				
			}

			save(exame);

		}
		catch (IOException e) {
			throw new BusinessException("arquivo.erro.upload.arquivo", e);
		}
		finally {
			LOGGER.info("Fim do salvar do novo exame doador internacional.");
		}
		
	}
	
	/**
	 * Método para fazer o upload dos arquivos de laudo para o storage.
	 * 
	 * @param listaArquivos
	 * @param timeStampMillis
	 * @return
	 * @throws IOException
	 */
	private List<CampoMensagem> uploadArquivos(List<MultipartFile> listaArquivos, Long idDoador,
			String diretorioExame, long timeStampMillis)
			throws IOException {

		for (MultipartFile arquivo : listaArquivos) {
			String diretorio = 
					StorageService.DIRETORIO_DOADORES_STORAGE + "/" + String.valueOf(idDoador) + "/" + 
							StorageService.DIRETORIO_DOADOR_EXAME_STORAGE + "/" + diretorioExame;
			storageService.upload(ArquivoUtil.obterNomeArquivo(timeStampMillis, arquivo), diretorio,
					arquivo);
		}
		return null;
	}
	
	@Override
	public Doador obterDoador(T exame){
		return ((IExameDoadorBaseRepository<T>) getRepository()).obterDoador(exame.getId());
	}

	@Override
	public List<T> listarExamesPorDoador(Long idDoador) {
		return ((IExameDoadorBaseRepository<T>) getRepository()).listarExamesDoador(idDoador);
	}

	@Override
	public List<T> listarExamesConferidos(Long idDoador, TiposExame tipoExame) {
		return ((IExameDoadorBaseRepository<T>) getRepository()).listarExamesConferidos(idDoador, tipoExame);
	}
	
	@Override
	public List<T> listarExamesPorDoadorOrdernadoPorDataCriacaoDecrescente(Long idDoador) {
		List<T> exames = ((IExameDoadorBaseRepository<T>) getRepository()).listarExamesDoadorOrderByDataCriacaoDesc(idDoador);
		
		exames.stream().forEach(exame -> {
			exame.getLocusExames().stream().forEach(locusExame -> {
				ComposicaoAlelo composicao = ComposicaoAlelo.obterTipo(locusExame.getPrimeiroAlelo());
				if (ComposicaoAlelo.NMDP.equals(composicao)) {
					locusExame.setPrimeiroAleloComposicao(valorNmdpService.obterSubTipos(locusExame.getPrimeiroAlelo().substring(locusExame.getPrimeiroAlelo().indexOf(":") + 1)));
				}
				else if (ComposicaoAlelo.GRUPO_G.equals(composicao)) {
					locusExame.setPrimeiroAleloComposicao(valorGService.obterGrupo(locusExame.getId().getLocus().getCodigo(), locusExame.getPrimeiroAlelo()));
				}
				else if (ComposicaoAlelo.GRUPO_P.equals(composicao)) {
					locusExame.setPrimeiroAleloComposicao(valorPService.obterGrupo(locusExame.getId().getLocus().getCodigo(), locusExame.getPrimeiroAlelo()));
				}
				
				composicao = ComposicaoAlelo.obterTipo(locusExame.getSegundoAlelo());
				if (ComposicaoAlelo.NMDP.equals(composicao)) {
					locusExame.setSegundoAleloComposicao(valorNmdpService.obterSubTipos(locusExame.getSegundoAlelo().substring(locusExame.getSegundoAlelo().indexOf(":") + 1)));
				}
				else if (ComposicaoAlelo.GRUPO_G.equals(composicao)) {
					locusExame.setSegundoAleloComposicao(valorGService.obterGrupo(locusExame.getId().getLocus().getCodigo(), locusExame.getSegundoAlelo()));
				}
				else if (ComposicaoAlelo.GRUPO_P.equals(composicao)) {
					locusExame.setSegundoAleloComposicao(valorPService.obterGrupo(locusExame.getId().getLocus().getCodigo(), locusExame.getSegundoAlelo()));
				}
				
			});
		});
		
		return exames;
	}

	@Override
	public ExameDoadorNacional criarExameDoadorNacional(ExameDoadorNacionalDTO exameDto) {
		
		ExameDoadorNacional exame = new ExameDoadorNacional();
		exame.setStatusExame(StatusExame.CONFERIDO.getId());
		
		Laboratorio laboratorio = new Laboratorio();
		laboratorio.setId(exameDto.getLaboratorio().getId());
		exame.setLaboratorio(laboratorio);

		exame.setDoador( (DoadorNacional) doadorService.obterDoador(exameDto.getDoador().getDmr()));

		List<Metodologia> metodologias = new ArrayList<Metodologia>();
		for(MetodologiaDTO metdto : exameDto.getMetodologias()) {
			Metodologia metodologia = new Metodologia();
			metodologia.setSigla(metdto.getSigla());
			metodologias.add(metodologia);
		}
		exame.setMetodologias(metodologias);
		
		exameDoadorNacionalRepository.save(exame);
		
		List<LocusExame> locusExames = new ArrayList<>();
		for(LocusExameDTO dto : exameDto.getLocusExames()) {
			
			LocusExame locusExame = new LocusExame();
			
			locusExame.setPrimeiroAlelo(dto.getPrimeiroAlelo());
			locusExame.setSegundoAlelo(dto.getSegundoAlelo());
	
			LocusExamePk pk = new LocusExamePk();
			pk.setLocus(new Locus(dto.getCodigo()));
			pk.setExame(exame);
			locusExame.setId(pk);
			
			locusExames.add(locusExame);
		}
		exame.setLocusExames(locusExames);
		
		exameDoadorNacionalRepository.save(exame);
		
		return exame;
	}

	/**
	 * @param doadorService the doadorService to set
	 */
	@Autowired
	public void setDoadorService(IDoadorService doadorService) {
		this.doadorService = doadorService;
	}

	@Override
	public void notificarUsuariosSobreResultadoDeExameDeDoadorInternacional(Long idTarefa) {
		
		TarefaDTO tarefa = tarefaFeign.obterTarefa(idTarefa);
		String[] parametros = obterParametrosMensagemNotificacaoExameCtInternacional(tarefa);
		List<Usuario> usuarios = usuarioService.listarUsuarioPorPerfil(tarefa.getPerfilResponsavel());
		
		String descricao = messageSource.getMessage("notificacao.cadastrar.resultado.exame.internacional", 
				parametros, LocaleContextHolder.getLocale());
		usuarios.forEach(usuario -> {
			CategoriasNotificacao.PEDIDO_EXAME.getConfiguracao()
			.criar()
			.paraUsuario(usuario.getId())
			.comPaciente(tarefa.getProcesso().getPaciente().getRmr())
			.comDescricao(descricao)
			.apply();
															
		});
		
	}

	@Override
	public void notificacarCadastroResultadoExameCtInternacional15(Long idTarefa) {
		TarefaDTO tarefa = tarefaFeign.obterTarefa(idTarefa);
		this.notificacarCadastroResultadoExameCtInternacional7(idTarefa);
		TiposTarefa.RESULTADO_EXAME_CT_INTERNACIONAL_15DIAS_FOLLOW_UP.getConfiguracao()
		.criarTarefa()
		.comRmr(tarefa.getProcesso().getPaciente().getRmr())
		.comProcessoId(tarefa.getProcesso().getId())
		.comTarefaPai(tarefa)
		.apply();		
	}
	
	
	@Override
	public void notificacarCadastroResultadoExameCtInternacional7(Long idTarefa) {
		TarefaDTO tarefa = tarefaFeign.obterTarefa(idTarefa);
		String[] parametros = obterParametrosMensagemNotificacaoExameCtInternacional(tarefa);
		
		List<Usuario> usuarios = usuarioService.listarUsuarioPorPerfil(tarefa.getPerfilResponsavel());
		String descricao = messageSource.getMessage("notificacao.cadastrar.resultado.exame.ct.internacional", 
				parametros, LocaleContextHolder.getLocale());
		usuarios.forEach(usuario -> {
			CategoriasNotificacao.PEDIDO_EXAME.getConfiguracao()
			.criar()
			.paraUsuario(usuario.getId())
			.comPaciente(tarefa.getProcesso().getPaciente().getRmr())
			.comDescricao(descricao)
			.apply();
															
		});
	}
	
	@SuppressWarnings("rawtypes")
	private String[] obterParametrosMensagemNotificacaoExameCtInternacional(TarefaDTO tarefa) {
		Long tempoExecutando = DAYS.between(tarefa.getDataCriacao(), LocalDateTime.now());
		PedidoExame pedidoExame = pedidoExameService.findById(tarefa.getRelacaoEntidade());		
		IDoador doador = (IDoador)pedidoExame.getSolicitacao().getMatch().getDoador();
		return new String[]{doador.getIdentificacao().toString(), tempoExecutando.toString()};
	}
	
	@SuppressWarnings("rawtypes")
	private String[] obterParametrosMensagemNotificacaoExameIdmInternacional(TarefaDTO tarefa) {
		Long tempoExecutando = DAYS.between(tarefa.getDataCriacao(), LocalDateTime.now());
		PedidoIdm pedidoExame = pedidoIdmService.findById(tarefa.getRelacaoEntidade());		
		IDoador doador = (IDoador)pedidoExame.getSolicitacao().getMatch().getDoador();
		return new String[]{doador.getIdentificacao().toString(), tempoExecutando.toString()};
	}

	@Override
	public void notificacarCadastroResultadoExameIdmInternacional15(Long idTarefa) {
		TarefaDTO tarefa = tarefaFeign.obterTarefa(idTarefa);
		this.notificacarCadastroResultadoExameIdmInternacional7(idTarefa);
		TiposTarefa.RESULTADO_IDM_INTERNACIONAL_7DIAS_FOLLOW_UP.getConfiguracao()
		.criarTarefa()
		.comRmr(tarefa.getProcesso().getPaciente().getRmr())
		.comProcessoId(tarefa.getProcesso().getId())
		.comTarefaPai(tarefa)
		.apply();		
	}

	@Override
	public void notificacarCadastroResultadoExameIdmInternacional7(Long idTarefa) {
		TarefaDTO tarefa = tarefaFeign.obterTarefa(idTarefa);
		String[] parametros = obterParametrosMensagemNotificacaoExameIdmInternacional(tarefa);
		
		List<Usuario> usuarios = usuarioService.listarUsuarioPorPerfil(tarefa.getPerfilResponsavel());
		String descricao = messageSource.getMessage("notificacao.cadastrar.resultado.idm.internacional", 
				parametros, LocaleContextHolder.getLocale());
		usuarios.forEach(usuario -> {
			CategoriasNotificacao.PEDIDO_EXAME.getConfiguracao()
			.criar()
			.paraUsuario(usuario.getId())
			.comPaciente(tarefa.getProcesso().getPaciente().getRmr())
			.comDescricao(descricao)
			.apply();
															
		});
	}

}
