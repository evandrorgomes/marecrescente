package br.org.cancer.modred.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.org.cancer.modred.configuration.EmptyStringDeserializer;
import br.org.cancer.modred.controller.dto.ResultadoWorkupNacionalDTO;
import br.org.cancer.modred.controller.view.FormularioView;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.IPedidoWorkupFeign;
import br.org.cancer.modred.feign.client.IResultadoWorkupFeign;
import br.org.cancer.modred.feign.dto.PedidoWorkupDTO;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Formulario;
import br.org.cancer.modred.model.FormularioDoador;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.RespostaFormularioDoador;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.domain.FasesWorkup;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposComparacao;
import br.org.cancer.modred.model.domain.TiposFormulario;
import br.org.cancer.modred.model.domain.TiposPergunta;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.formulario.Pagina;
import br.org.cancer.modred.model.formulario.Pergunta;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IFormularioDoadorRepository;
import br.org.cancer.modred.persistence.IFormularioRepository;
import br.org.cancer.modred.persistence.IRespostaFormularioDoadorRepository;
import br.org.cancer.modred.service.IFormularioService;
import br.org.cancer.modred.service.IPedidoContatoService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IUsuarioService;

/**
 * Classe de implementação da interface IFormularioService.java.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class FormularioService implements IFormularioService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FormularioService.class);
	
	@Autowired
	private IFormularioRepository formularioRepository;
	
	@Autowired
	private IFormularioDoadorRepository formularioDoadorRepository;
	
	@Autowired
	private IRespostaFormularioDoadorRepository respostaFormularioDoadorRepository;
	
	@Autowired
	private IPedidoContatoService pedidoContatoService;

	@Autowired
	@Lazy(true)
	private IPedidoWorkupFeign pedidoWorkupFeign;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ISolicitacaoService solicitacaoService;

	@Autowired
	@Lazy(true)
	private IResultadoWorkupFeign resultadoWorkupFeign;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Formulario obterFormulario(Long idPedido, TiposFormulario tipoFormulario) {

		DoadorNacional doadorNacional = null;
		if(tipoFormulario.getId() == TiposFormulario.RESULTADO_WORKUP.getId() || 
				tipoFormulario.getId() == TiposFormulario.RECEPTIVIDADE_WORKUP.getId() || 
				tipoFormulario.getId() == TiposFormulario.POSCOLETA_MEDULA.getId() ||
				tipoFormulario.getId() == TiposFormulario.POSCOLETA_AFERESE.getId()) {
			PedidoWorkupDTO pedidoWorkupDTO = pedidoWorkupFeign.obterPedidoWorkup(idPedido);
			Solicitacao solicitacao = solicitacaoService.obterSolicitacao(pedidoWorkupDTO.getIdSolicitacao());
			doadorNacional = (DoadorNacional) solicitacao.getMatch().getDoador();
		} 
		else {
			PedidoContato pedidoContato = pedidoContatoService.obterPedidoContatoPorId(idPedido);	
			doadorNacional = (DoadorNacional) pedidoContato.getDoador();
		}
		 
		
		Formulario formulario = formularioRepository.findByTipoFormularioIdAndAtivo(tipoFormulario.getId(), true);
		if (formulario == null) {
			throw new BusinessException("mensagem.nenhum.registro.encontrado", "questionário");
		}
		String jsonComRespostas = aplicarRespostasNoFormulario(idPedido, doadorNacional.getId(), formulario);
		
		montarFormularioDeAcordoComOPerfilDoDoadorPorPagina(doadorNacional, formulario, jsonComRespostas);
		aplicarRespostasPadrao(formulario);
		return formulario;
	}

	private void aplicarRespostasPadrao(Formulario formulario) {
		formulario.getPaginas().forEach(pagina -> {
			pagina.getSecoes().forEach(secao ->{		
				secao.getPerguntas().stream()
					.filter(pergunta -> !StringUtils.isBlank(pergunta.getValorDefault()) && StringUtils.isBlank(pergunta.getResposta()))
					.forEach(pergunta -> pergunta.setResposta(pergunta.getValorDefault()));
			});
		});	
	}

	private void montarFormularioDeAcordoComOPerfilDoDoadorPorPagina(final DoadorNacional doadorNacional, Formulario formulario, String formato) {
		Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
		builder.deserializers(new EmptyStringDeserializer(), LocalDateDeserializer.INSTANCE);
		builder.serializers(LocalDateSerializer.INSTANCE);
		builder.featuresToEnable(Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
		ObjectMapper objectMapper =  builder.build();
		try {
			
			formulario.setPaginas(objectMapper.readValue(formato, 
					objectMapper.getTypeFactory().constructCollectionType(List.class, Pagina.class)));
		}
		catch (IOException e) {
			LOGGER.error("", e);
			throw new BusinessException("erro.interno");
		}
		
		formulario.getPaginas().forEach(pagina -> {
			pagina.getSecoes().forEach(secao ->{
				secao.getPerguntas().stream().filter(pergunta -> pergunta.getCondicao() != null)
				.forEach(pergunta -> {
					try {
						List<Method> metodos = Arrays.asList(doadorNacional.getClass().getMethods());
						Optional<Method> opcionalMethod = metodos.stream()
							.filter(metodo -> metodo.getName().toLowerCase().startsWith("get") && 
									metodo.getName().toLowerCase().endsWith(pergunta.getCondicao().getAtributo()) )
							.findFirst();
						
						if (opcionalMethod.isPresent()) {
							Method metodo = opcionalMethod.get();
							Object valor = metodo.invoke(doadorNacional);
							if (pergunta.getCondicao().getTipoComparacao().equals(TiposComparacao.IGUAL) && metodo.getReturnType().equals(String.class) ) {
								pergunta.setRemoverPergunta(!((String)valor).equals(pergunta.getCondicao().getValor()));
							}	
						}
						
						if(pergunta.getDependentes() != null) {
							pergunta.getDependentes().stream().forEach(dependente -> 
								formulario.getPerguntas().stream()
									.filter(perguntaDependente -> perguntaDependente.getId().equals(dependente.getIdPergunta()))
									.forEach(perguntaDependente -> 	perguntaDependente.setRemoverPergunta(pergunta.getRemoverPergunta()))
							);
						}
					} 
					catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {				
						LOGGER.error("", e);
						throw new BusinessException("erro.interno");
					}				
				});
				secao.getPerguntas().removeIf(Pergunta::getRemoverPergunta );
			});
		});
	}

	private String aplicarRespostasNoFormulario(Long idPedido, Long idDoador, Formulario formulario) {
		
		String formato = formulario.getFormato();
		
		Pattern pattern = Pattern.compile("<[^<>\\.]+\\.[^<>\\.]+>");
		Matcher matcher = pattern.matcher(formato);
		if(!matcher.matches()) {
			Pattern pattern2 = Pattern.compile("<[^<>]+[^<>]+>");		
			matcher = pattern2.matcher(formato);
		}
	    while (matcher.find()) {
	    		    		
	    	final String token = formulario.getId() + "." + matcher.group(0).replaceAll("<", "").replaceAll(">", "");

	    	RespostaFormularioDoador respostaFormularioDoador = null;
	    	
	    	if(formulario.getTipoFormulario().getId() == TiposFormulario.RESULTADO_WORKUP.getId() ||
	    			formulario.getTipoFormulario().getId() == TiposFormulario.RECEPTIVIDADE_WORKUP.getId()||
	    			formulario.getTipoFormulario().getId() == TiposFormulario.POSCOLETA_MEDULA.getId() ||
	    			formulario.getTipoFormulario().getId() == TiposFormulario.POSCOLETA_AFERESE.getId()) {
	    		
	    		respostaFormularioDoador = respostaFormularioDoadorRepository
	    				.findFirstByformularioDoadorPedidoWorkupIdAndDoadorIdAndTokenOrderByFormularioDoadorDataRespostaDesc(idPedido, idDoador, token);
	    	}else {
	    		respostaFormularioDoador = respostaFormularioDoadorRepository
	    				.findFirstByformularioDoadorPedidoContatoIdAndDoadorIdAndTokenOrderByFormularioDoadorDataRespostaDesc(idPedido, idDoador, token);
	    	}
	    	
	    	if (respostaFormularioDoador != null && respostaFormularioDoador.getResposta() != null) {
	    		formato = formato.replaceAll(matcher.group(0), respostaFormularioDoador.getResposta());
	    	}
	    	else {
	    		formato = formato.replaceAll(matcher.group(0), "");
	    	}
	    }
		return formato;
	}
	
	private  Boolean validarPergunta(Pergunta pergunta, List<Pergunta> perguntas){
		Boolean valid = !StringUtils.isBlank(pergunta.getResposta());
		if (valid && CollectionUtils.isNotEmpty(pergunta.getDependentes())) {
			Boolean[] dependenteValid = {true};
			pergunta.getDependentes().stream()
				.filter(perguntaDependente -> {
					if (pergunta.getTipo().equals(TiposPergunta.RADIOBUTTON)) {
						return perguntaDependente.getValor().equals(pergunta.getResposta());
					}
					if (pergunta.getTipo().equals(TiposPergunta.CHECKBUTTON)) {
						return Arrays.stream(pergunta.getResposta().split(",")).anyMatch(resposta -> perguntaDependente.getValor().equals(resposta));
					}
					return false;
				})
				.forEach(pd -> {
					Boolean dependenteValido = validarPergunta(pd.getPergunta(), perguntas);
						if (!dependenteValido) {
							dependenteValid[0] = false;
							pd.setComErro(true);
						}else {
							pd.setComErro(false);
						}
				});
			
			if (!dependenteValid[0]) {
				valid = false;
			}			
		}
		return valid;
	}

	private List<Pergunta> popularListaDePerguntas(Formulario formulario){
		List<Pergunta> perguntas = new ArrayList<Pergunta>();
		formulario.getPaginas().forEach(pagina -> { 
			pagina.getSecoes().forEach(secao -> {
				secao.getPerguntas().forEach(pergunta ->{
					perguntas.add(pergunta);
				});
			});
		});
		return perguntas;
	}

	private List<Pergunta> popularListaDePerguntasComRespostas(Formulario formulario){
		List<Pergunta> perguntas = new ArrayList<Pergunta>();
		formulario.getPaginas().forEach(pagina -> { 
			pagina.getSecoes().forEach(secao -> {
				secao.getPerguntas().stream()
					.filter(pergunta -> StringUtils.isNotBlank(pergunta.getResposta()))
					.forEach(pergunta ->{ perguntas.add(pergunta);
					if(pergunta.getDependentes().size() > 0) {
						pergunta.getDependentes().stream()
						.filter(perguntaDp -> StringUtils.isNotBlank(perguntaDp.getPergunta().getResposta()))
						.forEach(dependencia -> {
							perguntas.add(dependencia.getPergunta());
						});
					}
				});
			});
		});
		return perguntas;
	}
	
	private boolean validarTotalmente(Formulario formulario) {
		int[] qtPerguntas = {0};
		int[] qtRespostasNaoRespondidas = {0};
		
		formulario.getPerguntas().stream()
			.filter(pergunta -> pergunta.getPossuiDependencia().equals(false))
			.forEach(pergunta -> {
				qtPerguntas[0]++;
				if (!CollectionUtils.isEmpty(pergunta.getDependentes())) { 
					qtPerguntas[0] += pergunta.getDependentes().size(); 
				}
				Boolean perguntaValida = validarPergunta(pergunta, formulario.getPerguntas());
				if (!perguntaValida) {
					qtRespostasNaoRespondidas[0]++;
				}
			});
		
		return qtPerguntas[0] == qtRespostasNaoRespondidas[0] || qtRespostasNaoRespondidas[0] == 0;
	}

	
	private Formulario validarFormularioPedidoWorkupTotalmente(Formulario formulario) {
		formulario.setComErro(false);
		List<Pergunta> perguntas = popularListaDePerguntas(formulario).stream()
				.filter(pergunta -> pergunta.getComValidacao()).collect(Collectors.toList());
		
		perguntas.stream()
			.filter(pergunta -> pergunta.getPossuiDependencia().equals(false))
			.forEach(pergunta -> {
				Boolean perguntaValida = validarPergunta(pergunta, perguntas);
				if(!perguntaValida) { 
					pergunta.setComErro(true);
					if(!formulario.isComErro()) {
						formulario.setComErro(true);
					}
				}else {
					pergunta.setComErro(false);
				}
			});
		
		return formulario;
	}
	
	private boolean verificarPreenchimento(Formulario formulario) {
		int[] qtRespostasNaoRespondidas = {0};
		
		formulario.getPerguntas().stream()
			.filter(pergunta -> pergunta.getPossuiDependencia().equals(false))
			.forEach(pergunta -> {
				Boolean perguntaValida = validarPergunta(pergunta, formulario.getPerguntas());
				if (!perguntaValida) {
					qtRespostasNaoRespondidas[0]++;
				}
			});
		return qtRespostasNaoRespondidas[0] == 0;
	}
		
	@Override 
	public void salvarFormularioComPedidoWorkup(Long id, Formulario formulario) {
		if(id == null) {
			throw new BusinessException("erro.questionario.invalido");
		}
		salvarformularioDoador(popularFormularioDoadorComIdPedidoWorkup(id, formulario), formulario);
	}

	@Override
	public Formulario finalizarFormularioResultadoWorkup(Long idPedidoWorkup, ResultadoWorkupNacionalDTO resultadoWorkupNacionalDTO, Formulario formulario) {
		
		Formulario formularioValido = validarFormularioPedidoWorkupTotalmente(formulario);
		if (formularioValido.isComErro()) {
			return formularioValido;
		}
				
		PedidoWorkupDTO pedidoWorkupDTO = this.pedidoWorkupFeign.obterPedidoWorkup(idPedidoWorkup);				
		if (pedidoWorkupDTO == null) {
			throw new BusinessException("erro.pedido.workup.invalido");
		}

		this.salvarFormularioComPedidoWorkup(pedidoWorkupDTO.getId(), formulario);
		
		resultadoWorkupFeign.criarResultadoWorkupNacional(idPedidoWorkup, resultadoWorkupNacionalDTO);
			
		LOGGER.info("Salva o formulário, altera a solicitacao e as tarefas");
		
		return formularioValido;
	}
	
	@Override
	public Formulario finalizarFormularioPosColeta(Long idPedidoWorkup, Formulario formulario) {
		
		Formulario formularioValido = validarFormularioPedidoWorkupTotalmente(formulario);
		if (formularioValido.isComErro()) {
			return formularioValido;
		}
				
		PedidoWorkupDTO pedidoWorkupDTO = this.pedidoWorkupFeign.obterPedidoWorkup(idPedidoWorkup);				
		if (pedidoWorkupDTO == null) {
			throw new BusinessException("erro.pedido.workup.invalido");
		}

		this.salvarFormularioComPedidoWorkup(pedidoWorkupDTO.getId(), formulario);
		
		pedidoWorkupFeign.finalizarFormularioPosColeta(idPedidoWorkup);
			
		LOGGER.info("Salva o formulário, altera a solicitacao e as tarefas");
		
		return formularioValido;
	}
	
	
	
	@Override
	public Formulario finalizarFormularioComPedidoWorkup(Long idPedidoWorkup, Formulario formulario) {
		
		Formulario formularioValido = validarFormularioPedidoWorkupTotalmente(formulario);
		if (formularioValido.isComErro()) {
			return formularioValido;
		}
				
		PedidoWorkupDTO pedidoWorkupDTO = this.pedidoWorkupFeign.obterPedidoWorkup(idPedidoWorkup);				
		if (pedidoWorkupDTO == null) {
			throw new BusinessException("erro.pedido.workup.invalido");
		}

		this.salvarFormularioComPedidoWorkup(pedidoWorkupDTO.getId(), formulario);
		
		Solicitacao solicitacao = solicitacaoService.obterSolicitacao(pedidoWorkupDTO.getIdSolicitacao());
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		Usuario usuario = solicitacao.getUsuarioResponsavel();
		
		if (solicitacao.getTipoSolicitacao().getId().equals(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL.getId())) {

			if(solicitacao.getFaseWorkup().equals(FasesWorkup.AGUARDANDO_FORMULARIO_DOADOR.getId())) {
				
				solicitacao.setFaseWorkup(FasesWorkup.AGUARDANDO_DEFINIR_CENTRO.getId());
				solicitacaoService.save(solicitacao);
				
				criarTarefaDefinirCentroColetaDoadorNacional(idPedidoWorkup, rmr, usuario);
				fecharTarefaCadastroFormularioDoador(idPedidoWorkup, rmr, usuario);
			}
		}
		
		if (solicitacao.getTipoSolicitacao().getId().equals(TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId())) {
			
			solicitacao.setFaseWorkup(FasesWorkup.AGUARDANDO_PLANO_WORKUP.getId());
			solicitacaoService.save(solicitacao);
		
			criarTarefaInformarPlanoWorkupInternacional(idPedidoWorkup, rmr, usuario);
			fecharTarefaCadastroFormularioDoador(idPedidoWorkup, rmr, usuario);
		}		
			
		LOGGER.info("Salva o formulário, altera a solicitacao e as tarefas");
		
		return formularioValido;
	}
	
	@Override
	public void salvarFormularioComPedidoContato(Long id, Formulario formulario) {
		Boolean formularioValido = validarTotalmente(formulario);
		
		if (!formularioValido) {
			throw new BusinessException("erro.questionario.invalido");
		}
		
		if(formulario.isComValidacao()) {
			if(verificarPreenchimento(formulario)) {
				salvarformularioDoador(popularFormularioDoadorComIdPedidoContato(id, formulario), formulario);
			}
		}else {
			salvarformularioDoador(popularFormularioDoadorComIdPedidoContato(id, formulario), formulario);
		}
	}
	
	/**
	 * @param pedidoContato
	 * @param formulario
	 * @return FormularioDoador
	 */
	public FormularioDoador popularFormularioDoadorComIdPedidoContato(Long id, Formulario formulario) {
		PedidoContato pedidoContato = pedidoContatoService.obterPedidoContatoPorId(id);				
		if (pedidoContato == null) {
			throw new BusinessException("erro.pedido.contato.invalido");
		}
		FormularioDoador formularioDoador = new FormularioDoador();
		formularioDoador.setDoadorNacional((DoadorNacional)pedidoContato.getDoador());
		formularioDoador.setPedidoContato(pedidoContato);
		formularioDoador.setDataResposta(LocalDateTime.now());
		formularioDoador.setFormulario(formularioRepository.getOne(formulario.getId()));
		formularioDoador.setUsuario(usuarioService.obterUsuarioLogado());
		return formularioDoador;
	}

	/**
	 * @param id - pedidoWorkup
	 * @param formulario
	 * @return FormularioDoador
	 */
	public FormularioDoador popularFormularioDoadorComIdPedidoWorkup(Long id, Formulario formulario) {
		PedidoWorkupDTO pedidoWorkupDTO = this.pedidoWorkupFeign.obterPedidoWorkup(id);				
		if (pedidoWorkupDTO == null) {
			throw new BusinessException("erro.pedido.workup.invalido");
		}

		Solicitacao solicitacao = solicitacaoService.obterSolicitacao(pedidoWorkupDTO.getIdSolicitacao());
		
		FormularioDoador formularioDoador = new FormularioDoador();
		formularioDoador.setDoadorNacional((DoadorNacional)solicitacao.getMatch().getDoador());
		formularioDoador.setPedidoWorkup(pedidoWorkupDTO.getId());
		formularioDoador.setDataResposta(LocalDateTime.now());
		formularioDoador.setFormulario(formularioRepository.getOne(formulario.getId()));
		formularioDoador.setUsuario(usuarioService.obterUsuarioLogado());
		return formularioDoador;
	}
	
	/**
	 * Salva o formulário para o pedido de contato e para o doador.
	 * 
	 * @param pedidoContato Pedido de contato 
	 * @param formulario formulário
	 */
	private void salvarformularioDoador(FormularioDoador formularioDoador, Formulario formulario) {
		
		try {
			Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
			builder.deserializers(new EmptyStringDeserializer(), LocalDateDeserializer.INSTANCE);
			builder.serializers(LocalDateSerializer.INSTANCE);
			ObjectMapper objectMapper =  builder.build();
			
			formularioDoador.setRespostas(objectMapper.writerWithView(FormularioView.FormularioRespondido.class).writeValueAsString(formulario.getPaginas()));
			
		} 
		catch (JsonProcessingException e) {
			LOGGER.error("", e);
			throw new BusinessException("erro.interno");
		}
		
		formularioDoadorRepository.save(formularioDoador);
		
		popularListaDePerguntasComRespostas(formulario).stream()
			.forEach(pergunta -> {
				RespostaFormularioDoador resposta = new RespostaFormularioDoador();
				resposta.setFormularioDoador(formularioDoador);
				resposta.setToken(formulario.getId() + "." + pergunta.getId());
				resposta.setResposta(pergunta.getResposta());
				
				respostaFormularioDoadorRepository.save(resposta);
		});
	}

	/**
	 * @param idPedidoWorkup
	 * @param rmr
	 * @param usuario
	 */
	private void fecharTarefaCadastroFormularioDoador(Long idPedidoWorkup, Long rmr, Usuario usuario) {
		TiposTarefa.CADASTRAR_FORMULARIO_DOADOR.getConfiguracao().fecharTarefa()
			.comRmr(rmr)
			.comObjetoRelacionado(idPedidoWorkup)
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuario)
			.apply();
	}

	/**
	 * @param idPedidoWorkup
	 * @param rmr
	 * @param usuario
	 */
	private void criarTarefaInformarPlanoWorkupInternacional(Long idPedidoWorkup, Long rmr, Usuario usuario) {
		TiposTarefa.INFORMAR_PLANO_WORKUP_INTERNACIONAL.getConfiguracao().criarTarefa()
			.comRmr(rmr)
			.comObjetoRelacionado(idPedidoWorkup)
			.comStatus(StatusTarefa.ATRIBUIDA)
			.comUsuario(usuario)
			.apply();
	}

	/**
	 * @param idPedidoWorkup
	 * @param rmr
	 * @param usuario
	 */
	private void criarTarefaDefinirCentroColetaDoadorNacional(Long idPedidoWorkup, Long rmr, Usuario usuario) {

		TiposTarefa.DEFINIR_CENTRO_COLETA.getConfiguracao().criarTarefa()
		.comRmr(rmr)
		.comObjetoRelacionado(idPedidoWorkup)
		.comStatus(StatusTarefa.ATRIBUIDA)
		.comUsuario(usuario)
		.apply();
	}
}

