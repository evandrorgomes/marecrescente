package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import br.org.cancer.modred.configuration.IntegracaoRedomeWebConfig;
import br.org.cancer.modred.controller.dto.PedidosPacienteInvoiceDTO;
import br.org.cancer.modred.controller.dto.UltimoPedidoExameDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ArquivoExame;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CordaoInternacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.EnderecoContatoLaboratorio;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameCordaoInternacional;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.InstrucaoColeta;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pagamento;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.PedidoIdm;
import br.org.cancer.modred.model.Relatorio;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.SolicitacaoRedomeweb;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.StatusPedidoExame;
import br.org.cancer.modred.model.TipoExame;
import br.org.cancer.modred.model.TipoSolicitacao;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.OwnerPedidoExame;
import br.org.cancer.modred.model.domain.ParametrosRelatorios;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.Relatorios;
import br.org.cancer.modred.model.domain.StatusExame;
import br.org.cancer.modred.model.domain.StatusPagamentos;
import br.org.cancer.modred.model.domain.StatusPedidosExame;
import br.org.cancer.modred.model.domain.StatusPedidosIdm;
import br.org.cancer.modred.model.domain.StatusSolicitacao;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposBuscaChecklist;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.domain.TiposExame;
import br.org.cancer.modred.model.domain.TiposInstrucaoColeta;
import br.org.cancer.modred.model.domain.TiposServico;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposSolicitacaoRedomeweb;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.interfaces.IExameDoador;
import br.org.cancer.modred.persistence.IPedidoExameRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IBuscaChecklistService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IDoadorInternacionalService;
import br.org.cancer.modred.service.IDoadorService;
import br.org.cancer.modred.service.IExameDoadorService;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.IExecutarProcedureMatchService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.IGenotipoPacienteService;
import br.org.cancer.modred.service.ILaboratorioService;
import br.org.cancer.modred.service.ILocusService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoColetaService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.IPedidoIdmService;
import br.org.cancer.modred.service.IRelatorioService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.ITipoExameService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.IsNull;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.PedidoExameDoadorInternacionalVo;
import br.org.cancer.modred.vo.PedidoExameDoadorNacionalVo;
import br.org.cancer.modred.vo.report.HtmlReportGenerator;
import br.org.cancer.modred.webservice.helper.TEnvelope;
import br.org.cancer.modred.webservice.helper.TRetorno;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade PedidoExame.
 * 
 * @author Pizão
 * 
 */
@Service
@Transactional
public class PedidoExameService extends AbstractLoggingService<PedidoExame, Long> implements IPedidoExameService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoExameService.class);
	
	private static final Long CODIGO_ERRO_REDOMEWEB_EXCEPTION = 99L;
	private static final Long TIPO_EXAME_IDM = 4L;
	private static final Long TODOS_TIPO_EXAME = 0L;
	
	@Autowired
	private IPedidoExameRepository pedidoExameRepository;
	
	private IPacienteService pacienteService;
	
	private IDoadorService doadorService;
		
	private IDoadorInternacionalService doadorInternacionalService;
	
	@Autowired
	@Lazy(true)
	private ITarefaFeign tarefaFeign;
	
	private ILocusService locusService;
	
	private IUsuarioService usuarioService;
		
	private IExamePacienteService exameService;
		
	private IGenotipoPacienteService genotipoService;

	private IBuscaService buscaService;
	
	private IRelatorioService relatorioService;
	
	private ITipoExameService tipoExameService;
	
	private IMatchService matchService;
	
	private IExameDoadorService<ExameDoadorInternacional> exameDoadorService;
	
	private IExameDoadorService<ExameCordaoInternacional> exameCordaoService;
		
	private IGenotipoDoadorService<ExameDoadorInternacional> genotipoDoadorInternacionalService;
		
	private IGenotipoDoadorService<ExameCordaoInternacional> genotipoCordaoInternacionalService;
	
	private IPedidoIdmService pedidoIdmService;

	@Autowired
	private IPedidoColetaService pedidoColetaService;
	
	private ILaboratorioService laboratorioService;
	
	private IBuscaChecklistService buscaChecklistService;

	@Autowired
	public IntegracaoRedomeWebConfig redomeWebConfig;
	
	@Autowired
	private ISolicitacaoService solicitacaoService;
	
	@Autowired
	private IExecutarProcedureMatchService executarProcedureMatchService;
	
	@Override
	public IRepository<PedidoExame, Long> getRepository() {
		return pedidoExameRepository;
	}
	
	@CreateLog
	@Override
	public PedidoExame criarPedidoDoadorNacional(Solicitacao solicitacao) {
		
		if (isFase3(solicitacao.getTipoSolicitacao())) {
			return criarPedidoExameFase3Nacional(solicitacao);
		}
		
		return criarPedidoExameFase2Nacional(solicitacao);
		
	}
	
	private PedidoExame criarPedidoExameFase2Nacional(Solicitacao solicitacao) {
		final DoadorNacional doador = (DoadorNacional) solicitacao.getMatch().getDoador();
		
		PedidoExame pedido = new PedidoExame();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setSolicitacao(solicitacao);
		pedido.setLaboratorio(doador.getLaboratorio());
		
		pedido.setOwner(OwnerPedidoExame.DOADOR);
		pedido.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.AGUARDANDO_ACEITE));			
		pedido.setTipoExame(solicitacao.getTipoExame());
		
		/**
		 * FIXME: Setando os lócus solicitados no pedido de exame fase 3 do paciente.
		 * Precisamos analisar o uso da lista de lócus que tem no TipoExame e utiliza-lo ou não.
		 */
		pedido.setLocusSolicitados( tipoExameService.listarLocusAssociados(pedido.getTipoExame().getId()) );	
		
		//######## INTEGRAÇÃO REDOME WEB ######## 
		
		//criarSolicitacaoExameRedomeWeb(pedido);
		
		// ###################################
		
		PedidoExame pedidoExame = save(pedido);
		
		TiposTarefa	.CADASTRAR_RESULTADO_EXAME.getConfiguracao()
					.criarTarefa()					
					.comRmr(pedido.getSolicitacao().getMatch().getBusca().getPaciente().getRmr())
					.comObjetoRelacionado(pedidoExame.getId())
					.apply();
		
		return pedido;
	}

	private void criarSolicitacaoExameRedomeWeb(PedidoExame pedido) {
		Long idSolicitacaoRedomeWeb = null;
		try {
			idSolicitacaoRedomeWeb = obterIdSolicitacaoExameRedomeWeb(pedido);
		} 
		catch (IOException e) {
			LOGGER.error("WEBSERVICE - REDOMENET", e);					
		}
		if (idSolicitacaoRedomeWeb == null) {
			throw new BusinessException("erro.interno");
		}
		
		SolicitacaoRedomeweb solicitacaoRedomeweb = new SolicitacaoRedomeweb();
		solicitacaoRedomeweb.setDataCriacao(pedido.getDataCriacao());
		solicitacaoRedomeweb.setIdSolicitacaoRedomeweb(idSolicitacaoRedomeWeb);
		solicitacaoRedomeweb.setTipoSolicitacaoRedomeweb(TiposSolicitacaoRedomeweb.EXAME);
		solicitacaoRedomeweb.setStatusPedidoExame(pedido.getStatusPedidoExame().getId());
		solicitacaoRedomeweb.setPedidoExame(pedido);
		
		pedido.setSolicitacoesRedomeweb(Arrays.asList(solicitacaoRedomeweb));
	}
	
	private PedidoExame criarPedidoExameFase3Nacional(Solicitacao solicitacao) {
		PedidoExame pedido = new PedidoExame();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setSolicitacao(solicitacao);
		pedido.setLaboratorio(solicitacao.getLaboratorio());
		
		pedido.setOwner(OwnerPedidoExame.DOADOR);
		pedido.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.AGUARDANDO_AMOSTRA));
		pedido.setTipoExame(new TipoExame(TiposExame.TESTE_CONFIRMATORIO.getId()));
		
		if (solicitacao.getResolverDivergencia() != null && solicitacao.getResolverDivergencia()) {
			pedido.setDataAberturaResolverDivergencia(LocalDateTime.now());
		}
		
		/**
		 * FIXME: Setando os lócus solicitados no pedido de exame fase 3 do paciente.
		 * Precisamos analisar o uso da lista de lócus que tem no TipoExame e utiliza-lo ou não.
		 */
		pedido.setLocusSolicitados( tipoExameService.listarLocusAssociados(TiposExame.TESTE_CONFIRMATORIO.getId()) );
		
		// ###### INTEGRAÇÃO REDOME WEB ######
		
		//criarSolicitacaoAmostraRedomeWeb(pedido);
		
		// ######  ######
		
		PedidoExame pedidoExame = save(pedido);
		
		TiposTarefa	.CADASTRAR_RESULTADO_CT.getConfiguracao()
			.criarTarefa()
			.comRmr(pedidoExame.getSolicitacao().getMatch().getBusca().getPaciente().getRmr())
			.comParceiro(pedidoExame.getLaboratorio().getId())
			.comObjetoRelacionado(pedidoExame.getId())
			.apply();
		
		return pedidoExame;
	}

	private void criarSolicitacaoAmostraRedomeWeb(PedidoExame pedido) {
		Long idSolicitacaoRedomeWeb = null;
		try {
			idSolicitacaoRedomeWeb = obterIdSolicitacaoAmostraRedomeWeb(pedido);
		} 
		catch (IOException e) {
			LOGGER.error("WEBSERVICE - REDOMENET", e);					
		}
		if (idSolicitacaoRedomeWeb == null) {
			throw new BusinessException("erro.interno");
		}
		
		SolicitacaoRedomeweb solicitacaoRedomeweb = new SolicitacaoRedomeweb();
		solicitacaoRedomeweb.setDataCriacao(pedido.getDataCriacao());
		solicitacaoRedomeweb.setIdSolicitacaoRedomeweb(idSolicitacaoRedomeWeb);
		solicitacaoRedomeweb.setTipoSolicitacaoRedomeweb(TiposSolicitacaoRedomeweb.AMOSTRA);
		solicitacaoRedomeweb.setStatusPedidoExame(pedido.getStatusPedidoExame().getId());
		solicitacaoRedomeweb.setPedidoExame(pedido);
		
		pedido.setSolicitacoesRedomeweb(Arrays.asList(solicitacaoRedomeweb));
	}

	private Long obterIdSolicitacaoExameRedomeWeb(PedidoExame pedido) throws ClientProtocolException, IOException {
		/* 
		 * @FIXME: Funcionamento fora de produção não está ocorrendo, por conta do acesso ao RedomeWeb no ambiente de DSV.
		 */  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(redomeWebConfig.obterHostRedomenetWsdl());
			
			final DoadorNacional doador = (DoadorNacional)  pedido.getSolicitacao().getMatch().getDoador();
			
			StringEntity data = new StringEntity(montarXMLSolicitacaoExameRedomeWeb(doador.getDmr(), 150L/*doador.getLaboratorio().getIdRedomeWeb()*/, pedido.getTipoExame().getId()),
					ContentType.TEXT_XML);
			
			post.setEntity(data);
			
			CloseableHttpResponse response = httpclient.execute(post);
	        try {
	        	ObjectMapper xmlMapper = new XmlMapper();	        	
	        	final TEnvelope envelope = xmlMapper.readValue(EntityUtils.toString(response.getEntity()), TEnvelope.class);
	        	if (envelope.getBody() != null) {
	        		if (envelope.getBody().getSolicitarExameResponse() != null && envelope.getBody().getSolicitarExameResponse().getDados() != null) {
	        			final TRetorno retorno = xmlMapper.readValue(envelope.getBody().getSolicitarExameResponse().getDados(), TRetorno.class);
			        	if (!retorno.getErros().isEmpty()) {
			        		retorno.getErros().stream().forEach(erro -> {		        			
			        			LOGGER.error(erro.toString());	
			        		});
			        		throw new BusinessException("erro.interno");
			        	}
			        	if (!retorno.getSucessos().isEmpty()) {
			        		return retorno.getSucessos().get(0).getId();
			        	}
	        		}
	        	}
	        } 
	        finally {
	            response.close();
	        }
		} 
		finally {
			httpclient.close();
        }
		return null;
	}
	
	private String montarXMLSolicitacaoExameRedomeWeb(Long dmr, Long laboratorioRedomeWeb, Long  idTipoExame) {		
		
		StringBuffer xml = new StringBuffer("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
				.append("xmlns:se=\\"+redomeWebConfig.getHost()+"\\> ")
				.append("<soap:Body> ")
				.append("<se:solicitarExame> ")
				.append("<dados> ")
				.append("<![CDATA[ ")
				.append("<ns1:solicitarExame xmlns:ns1=\\"+redomeWebConfig.obterHostSolicitarExame()+"\\ ") 
				.append("	ns1:login=\"sismatch\" ns1:senha=\"!master!\"> ")
				.append("		<ns1:dataSolicitacao>" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "</ns1:dataSolicitacao> ")
				.append("		<ns1:dmr>" + dmr + "</ns1:dmr> ")
				.append(" 		<ns1:laboratorio>" + laboratorioRedomeWeb + "</ns1:laboratorio> ");
		if (TiposExame.LOCUS_C_ALTA_RESOLUCAO_CLASSE_II.getId().equals(idTipoExame)) {
			xml = xml.append("		<ns1:exames>2</ns1:exames> ")
				.append("		<ns1:exames>3</ns1:exames> ");			
		}
		else if (TiposExame.LOCUS_C.getId().equals(idTipoExame)) {
			xml = xml.append("		<ns1:exames>2</ns1:exames> ");
		}
		else if (TiposExame.ALTA_RESOLUCAO_CLASSE_II.getId().equals(idTipoExame)) {
			xml = xml.append("		<ns1:exames>3</ns1:exames> ");
		}
		xml = xml.append("</ns1:solicitarExame> ")
				.append("]]> ")
				.append("</dados> ")
				.append("</se:solicitarExame> ")
				.append("</soap:Body> ")
				.append("</soap:Envelope> ");
		
		return xml.toString();
	}
	
	private Long obterIdSolicitacaoAmostraRedomeWeb(PedidoExame pedido) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(redomeWebConfig.obterHostRedomenetWsdl());
			
			final DoadorNacional doador = (DoadorNacional)  pedido.getSolicitacao().getMatch().getDoador();
			
			StringEntity data = new StringEntity(montarXMLSolicitacaoAmostraRedomeWeb(doador.getDmr(), doador.getHemoEntidade().getIdHemocentroRedomeWeb(), pedido.getLaboratorio()), ContentType.TEXT_XML);
			
			post.setEntity(data);
			
			CloseableHttpResponse response = httpclient.execute(post);
	        try {
	        	ObjectMapper xmlMapper = new XmlMapper();	        	
	        	final TEnvelope envelope = xmlMapper.readValue(EntityUtils.toString(response.getEntity()), TEnvelope.class);
	        	if (envelope.getBody() != null) {
	        		if (envelope.getBody().getSolicitarAmostraResponse() != null && envelope.getBody().getSolicitarAmostraResponse().getDados() != null) {
	        			final TRetorno retorno = xmlMapper.readValue(envelope.getBody().getSolicitarAmostraResponse().getDados(), TRetorno.class);
			        	if (!retorno.getErros().isEmpty()) {
			        		retorno.getErros().stream().forEach(erro -> {		        			
			        			LOGGER.error(erro.toString());	
			        		});
			        		throw new BusinessException("erro.interno");
			        	}
			        	if (!retorno.getSucessos().isEmpty()) {
			        		return retorno.getSucessos().get(0).getId();
			        	}
	        		}
	        	}
	        } 
	        finally {
	            response.close();
	        }
		} 
		finally {
			httpclient.close();
        }
		return null;
	}
	
	private String montarXMLSolicitacaoAmostraRedomeWeb(Long dmr, Long hemocentroRedomeWeb, Laboratorio laboratorio) {		
		
		StringBuffer xml = new StringBuffer("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
				.append("xmlns:se=\\"+redomeWebConfig.getHost()+"\\> ")
				.append("<soap:Body> ")
				.append("<se:solicitarAmostra> ")
				.append("<dados> ")
				.append("<![CDATA[ ")
				.append("<ns1:solicitarAmostra xmlns:ns1=\\"+redomeWebConfig.obterHostSolicitarAmostra()+"\\ ") 
				.append("	ns1:login=\"sismatch\" ns1:senha=\"!master!\"> ")
				.append("		<ns1:dataSolicitacao>" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "</ns1:dataSolicitacao> ")
				.append("		<ns1:dmr>" + dmr + "</ns1:dmr> ")
				.append(" 		<ns1:laboratorio>" + laboratorio.getIdRedomeWeb() + "</ns1:laboratorio> ")
				.append(" 		<ns1:hemocentro>" +  hemocentroRedomeWeb + "</ns1:hemocentro> ")
				.append(" 		<ns1:instrucoesColeta>" +  laboratorio.getInstrucoesColeta() + "</ns1:instrucoesColeta> ")
				.append("</ns1:solicitarAmostra> ")
				.append("]]> ")
				.append("</dados> ")
				.append("</se:solicitarAmostra> ")
				.append("</soap:Body> ")
				.append("</soap:Envelope> ");
		
		return xml.toString();
	}
		
		
	/**
	 * Cria a tarefa para realização do teste confirmatório pelo laboratório selecionado.
	 * @param paciente Paciente que está realizando o procedimento.
	 */
	private void criarTarefaParaLaboratorio(PedidoExame pedidoExame) {
		final Paciente paciente = pacienteService.obterPacientePorSolicitacaoCT(pedidoExame.getSolicitacao().getId());
		
		TiposTarefa.RECEBER_COLETA_PARA_EXAME.getConfiguracao().criarTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(pedidoExame.getId())
			.comParceiro(pedidoExame.getLaboratorio().getId())			
			.apply();
		
	}
	
	private boolean isFase3(TipoSolicitacao tipoSolicitacao) {
		return TiposSolicitacao.FASE_3.getId().equals(tipoSolicitacao.getId()) || TiposSolicitacao.FASE_3_INTERNACIONAL.getId().equals(tipoSolicitacao.getId());
	}

	@Override
	@CreateLog
	public void cancelarPedido(Solicitacao solicitacao, String justificativa, LocalDate dataCancelamentoPedido, Long motivoStatusId, Long timeRetornoInatividade) {
		PedidoExame pedidoExameSolicitado = 
				findOne(new Equals<Long>("solicitacao.id", solicitacao.getId()), 
						new Equals<Long>("solicitacao.tipoSolicitacao.id", solicitacao.getTipoSolicitacao().getId()));

		boolean possivelCancelar = false;		
		if (pedidoExameSolicitado != null && pedidoExameSolicitado.getOwner().equals(OwnerPedidoExame.PACIENTE)) {		
			possivelCancelar = cancelarPedidoExameCtPaciente(pedidoExameSolicitado, justificativa);
		}
		else if (pedidoExameSolicitado != null && pedidoExameSolicitado.getOwner().equals(OwnerPedidoExame.DOADOR)) {
			if (TiposSolicitacao.FASE_2.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
				possivelCancelar = cancelarPedidoExameComplementarNacional(pedidoExameSolicitado);
			}
			else if (TiposSolicitacao.FASE_2_INTERNACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
				possivelCancelar = cancelarPedidoExameComplementarInternacional(pedidoExameSolicitado, justificativa, motivoStatusId, timeRetornoInatividade);
			}
			else if (TiposSolicitacao.FASE_3.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
				possivelCancelar = cancelarPedidoExameCtNacional(pedidoExameSolicitado);
			}
			else if (TiposSolicitacao.FASE_3_INTERNACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
				possivelCancelar = cancelarPedidoExameCtInternacional(pedidoExameSolicitado, dataCancelamentoPedido,  motivoStatusId, timeRetornoInatividade);
			}			
		}

		if (!possivelCancelar) {
			if (pedidoExameSolicitado != null) {
				pedidoExameSolicitado = findById(pedidoExameSolicitado.getId());
				
				if (!StatusPedidosExame.RESULTADO_CADASTRADO.getId().equals(pedidoExameSolicitado.getStatusPedidoExame().getId()) 
					&& !StatusPedidosExame.CANCELADO.getId().equals(pedidoExameSolicitado.getStatusPedidoExame().getId())) {
				
					if (pedidoExameSolicitado.getOwner().equals(OwnerPedidoExame.PACIENTE)) {
						throw new BusinessException(AppUtil.getMensagem(messageSource, "pedido.exame.alterado.cancelado.falha", 
								"paciente " + pedidoExameSolicitado.getSolicitacao().getBusca().getPaciente().getRmr()));
					}
					else {
						String identificadorDoador = "";
						final Doador doador = pedidoExameSolicitado.getSolicitacao().getMatch().getDoador();
						if (doador instanceof DoadorNacional) {
							identificadorDoador = ((DoadorNacional) doador).getDmr().toString();
						}
						else {
							identificadorDoador = ((DoadorInternacional) doador).getIdRegistro();
						}
						
						throw new BusinessException(AppUtil.getMensagem(messageSource, "pedido.exame.alterado.cancelado.falha", 
								"doador " + identificadorDoador));					
					}
				}
			}
		}
		
		
		
	}
	
	private boolean cancelarPedidoExameComplementarNacional(PedidoExame pedidoExame) {
		boolean possivelCancelar = 	StatusPedidosExame.AGUARDANDO_ACEITE.getId().equals(pedidoExame.getStatusPedidoExame().getId());
		if (possivelCancelar) {
			pedidoExame.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.CANCELADO));
			pedidoExame.setJustificativa(null);
			pedidoExame.setDataCancelamento(LocalDate.now());
			
			save(pedidoExame);
			
			// ##### COMENTADO #####
			//possivelCancelar = cancelarSolicitacaoRedomeWeb(pedidoExame.getSolicitacoesRedomeweb().get(0).getIdSolicitacaoRedomeweb());
			//##########
		}

		return possivelCancelar;		
	}
	
	private boolean cancelarPedidoExameComplementarInternacional(PedidoExame pedidoExame, String justificativa, Long motivoStatusId, Long timeRetornoInatividade) {
		boolean possivelCancelar = 	StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM.getId().equals(pedidoExame.getStatusPedidoExame().getId());
		if (possivelCancelar) {
			pedidoExame.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.CANCELADO));
			pedidoExame.setJustificativa(justificativa);
			pedidoExame.setDataCancelamento(LocalDate.now());

			if (motivoStatusId != null) {
				final Doador doador = pedidoExame.getSolicitacao().getMatch().getDoador();
				
				doadorService.atualizarStatusDoador(doador.getId(), 
						timeRetornoInatividade == null ? StatusDoador.INATIVO_PERMANENTE : StatusDoador.INATIVO_TEMPORARIO, 
								motivoStatusId, timeRetornoInatividade);
			}
			
			save(pedidoExame);
			
			cancelarTarefaAnalistaBuscaSePossivel(pedidoExame);
			cancelarTarefaEnviarPedidoComplementarEmdisSePossivel(pedidoExame);
			
		}

		return possivelCancelar;
	}
	
	private boolean cancelarPedidoExameCtNacional(PedidoExame pedidoExame) {
		boolean possivelCancelar = 	StatusPedidosExame.AGUARDANDO_ACEITE.getId().equals(pedidoExame.getStatusPedidoExame().getId());
		if (possivelCancelar) {
			pedidoExame.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.CANCELADO));
			pedidoExame.setJustificativa(null);
			pedidoExame.setDataCancelamento(LocalDate.now());
			
			save(pedidoExame);
			
			// ###### INTEGRAÇÃO ######
			//possivelCancelar = cancelarSolicitacaoAmostraRedomeWeb(pedidoExame.getSolicitacoesRedomeweb().get(0).getIdSolicitacaoRedomeweb());
			// ########################
		}

		return possivelCancelar;		
	}
	
	private boolean cancelarPedidoExameCtInternacional(PedidoExame pedidoExame, LocalDate dataCancelamento, Long motivoStatusId, Long timeRetornoInatividade) {
		if (dataCancelamento == null) {
			throw new BusinessException("erro.cancelamento.pedido.data");
		}
		
		boolean possivelCancelar = 	StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM.getId().equals(pedidoExame.getStatusPedidoExame().getId());
		if (possivelCancelar) {
			pedidoExame.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.CANCELADO));
			pedidoExame.setJustificativa(null);
			pedidoExame.setDataCancelamento(dataCancelamento);

			save(pedidoExame);
			
			if (motivoStatusId != null) {
				final Doador doador = pedidoExame.getSolicitacao().getMatch().getDoador();
				
				doadorService.atualizarStatusDoador(doador.getId(), 
						timeRetornoInatividade == null ? StatusDoador.INATIVO_PERMANENTE : StatusDoador.INATIVO_TEMPORARIO, 
								motivoStatusId, timeRetornoInatividade);
			}
			
			cancelarTarefaAnalistaBuscaSePossivel(pedidoExame);
			
			TiposServico.PEDIDO_EXAME_CT.getConfiguracao()
				.cancelarPagamento()
				.comMatch(pedidoExame.getSolicitacao().getMatch().getId())
				.comObjetoRelacionado(pedidoExame.getId())
				.comRegistro(pedidoExame.getSolicitacao().getMatch().getDoador().getRegistroOrigem().getId())
				.apply();
		}
		
		

		return possivelCancelar;
	}

	private boolean cancelarPedidoExameCtPaciente(PedidoExame pedidoExame, String justificativa) {
		boolean possivelCancelar = 	StatusPedidosExame.AGUARDANDO_AMOSTRA.getId().equals(pedidoExame.getStatusPedidoExame().getId());
		if (possivelCancelar) {
			pedidoExame.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.CANCELADO));
			pedidoExame.setJustificativa(justificativa);
			pedidoExame.setDataCancelamento(LocalDate.now());
			
			save(pedidoExame);
			
			cancelarTarefaLaboratorioSePossivel(pedidoExame);			
		}
		
		return possivelCancelar;
	}
	
	
	private boolean cancelarSolicitacaoAmostraRedomeWeb(Long idSolicitacaoRedomeWeb) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(redomeWebConfig.obterHostRedomenetWsdl());
			
			StringEntity data = new StringEntity(montarXMLCancelarAmostraRedomeWeb(idSolicitacaoRedomeWeb), ContentType.TEXT_XML);
			
			post.setEntity(data);
			
			CloseableHttpResponse response = httpclient.execute(post);
	        try {
	        	ObjectMapper xmlMapper = new XmlMapper();	        	
	        	final TEnvelope envelope = xmlMapper.readValue(EntityUtils.toString(response.getEntity()), TEnvelope.class);
	        	if (envelope.getBody() != null) {
	        		if (envelope.getBody().getCancelarAmostraResponse() != null && envelope.getBody().getCancelarAmostraResponse().getDados() != null) {
	        			final TRetorno retorno = xmlMapper.readValue(envelope.getBody().getCancelarAmostraResponse().getDados(), TRetorno.class);
			        	if (!retorno.getErros().isEmpty()) {
			        		retorno.getErros().stream().forEach(erro -> {		        			
			        			LOGGER.error(erro.toString());	
			        			if (CODIGO_ERRO_REDOMEWEB_EXCEPTION.equals(erro.getId())) {
			        				throw new BusinessException("erro.interno");		
			        			}
			        		});
			        		return false;
			        	}
	        		}
	        	}
	        } 
	        finally {
	            response.close();
	            httpclient.close();
	        }
		}
		catch (Exception e) {
			LOGGER.error("", e);
			try {
				httpclient.close();
			} 
			catch (IOException e1) {
				LOGGER.error("", e1);
				throw new BusinessException("erro.interno");
			}	
		}
		return true;
	}

	private String montarXMLCancelarAmostraRedomeWeb(Long idSolicitacaoRedomeWeb) {		
		
		StringBuffer xml = new StringBuffer("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" ")
				.append("xmlns:se=\\"+redomeWebConfig.getHost()+"\\> ")
				.append("<soap:Body> ")
				.append("<se:cancelarAmostra> ")
				.append("<dados> ")
				.append("<![CDATA[ ")
				.append("<ns1:cancelarAmostra xmlns:ns1=\\"+redomeWebConfig.obterHostCancelarAmostra()+"\\ ") 
				.append("	ns1:login=\"sismatch\" ns1:senha=\"!master!\"> ")
				.append("		<ns1:solicitacao>" + idSolicitacaoRedomeWeb + "</ns1:solicitacao> ")
				.append("</ns1:cancelarAmostra> ")
				.append("]]> ")
				.append("</dados> ")
				.append("</se:cancelarAmostra> ")
				.append("</soap:Body> ")
				.append("</soap:Envelope> ");
		
		return xml.toString();
	}
	
	@Override
	public List<PedidoExame> buscarPedidosDeExamePorSolicitacao(Long idSolicitacao) {
		return pedidoExameRepository.findBySolicitacaoId(idSolicitacao);
	}
	
	@Override
	public LogEvolucao criarLog(PedidoExame pedidoExame, TipoLogEvolucao tipoLog, Perfis[] perfisExcluidos) {
		LogEvolucao log = super.criarLog(pedidoExame, tipoLog, perfisExcluidos);
		Solicitacao solicitacao = pedidoExame.getSolicitacao();
		boolean isCancelamento = 
				StatusPedidosExame.CANCELADO.getId().equals(pedidoExame.getStatusPedidoExame().getId());
		
		TipoLogEvolucao tipoLogPedido = tipoLog;
		if (TipoLogEvolucao.INDEFINIDO.equals(tipoLogPedido) ) {
			if(OwnerPedidoExame.DOADOR.equals(pedidoExame.getOwner())){
				if(TiposSolicitacao.FASE_2.getId().equals(solicitacao.getTipoSolicitacao().getId()) || 
						TiposSolicitacao.FASE_2_INTERNACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId())){
					tipoLogPedido = isCancelamento ? 
							TipoLogEvolucao.PEDIDO_EXAME_FASE_2_CANCELADO_PARA_DOADOR : 
								TipoLogEvolucao.PEDIDO_EXAME_FASE_2_REALIZADO_PARA_DOADOR;
				}
				else if(TiposSolicitacao.FASE_3.getId().equals(solicitacao.getTipoSolicitacao().getId())){
					tipoLogPedido = isCancelamento ? 
							TipoLogEvolucao.PEDIDO_EXAME_FASE_3_CANCELADO_PARA_DOADOR : 
								TipoLogEvolucao.PEDIDO_EXAME_FASE_3_REALIZADO_PARA_DOADOR;
				}
				else if(TiposSolicitacao.FASE_3_INTERNACIONAL.getId().equals(solicitacao.getTipoSolicitacao().getId())) {
							boolean isCordaoInternacional = TiposDoador.CORDAO_INTERNACIONAL.equals(solicitacao.getMatch().getDoador()
									.getTipoDoador());
							if (isCordaoInternacional) {
								tipoLogPedido = isCancelamento
										? TipoLogEvolucao.PEDIDO_EXAME_CT_CANCELADO_PARA_CORDAO_INTERNACIONAL
										: TipoLogEvolucao.SOLICITADO_PEDIDO_EXAME_CT_PARA_CORDAO_INTERNACIONAL;
							}
							else {
								tipoLogPedido = isCancelamento
										? TipoLogEvolucao.PEDIDO_EXAME_CT_CANCELADO_PARA_DOADOR_INTERNACIONAL
										: TipoLogEvolucao.SOLICITADO_PEDIDO_EXAME_CT_PARA_DOADOR_INTERNACIONAL;
							}
				}
			}
			else {
				if(TiposSolicitacao.FASE_3.getId().equals(solicitacao.getTipoSolicitacao().getId())){
					tipoLogPedido = isCancelamento ? 
							TipoLogEvolucao.PEDIDO_EXAME_FASE_3_CANCELADO_PARA_PACIENTE : 
								TipoLogEvolucao.PEDIDO_EXAME_FASE_3_REALIZADO_PARA_PACIENTE;
				}
			}
		}
				
		if (TipoLogEvolucao.INDEFINIDO.equals(tipoLogPedido)) {
			throw new IllegalArgumentException("Tipo de solicitação para o pedido " + 
												pedidoExame.getId() + " é nulo ou desconhecido.");
		}
		
		log.setTipoEvento(tipoLogPedido);
		return log;
	}

	@Override
	public Paciente obterPaciente(PedidoExame pedidoExame) {
		return pedidoExame.getSolicitacao().getPaciente() != null? pedidoExame.getSolicitacao().getPaciente():pacienteService.obterPacientePorSolicitacao(pedidoExame.getSolicitacao().getId());
	}

	@Override
	public String[] obterParametros(PedidoExame pedidoExame) {
		String[] retorno = {null};
		
		if (pedidoExame.getSolicitacao().getMatch() != null && pedidoExame.getSolicitacao().getMatch().getDoador() != null) {
			Doador doador = pedidoExame.getSolicitacao().getMatch().getDoador();
			if (doador instanceof DoadorNacional) {
				retorno[0] = ((DoadorNacional) doador).getDmr().toString();
			}
			else if (doador instanceof CordaoInternacional) {
				retorno[0] = ((CordaoInternacional)doador).getIdRegistro();
			}
			else {
				retorno[0] = ((DoadorInternacional)doador).getIdRegistro();
			}			
		}
		else if (pedidoExame.getSolicitacao().getBusca() != null) {
			retorno[0] = pedidoExame.getSolicitacao().getBusca().getPaciente().getRmr().toString();
		}
		
		return retorno;
	}

	@Override
	public void receberPedido(PedidoExame pedidoExame) {
		PedidoExame pedidoExamePersistir = this.findById(pedidoExame.getId());
		pedidoExamePersistir.setDataRecebimentoAmostra(pedidoExame.getDataRecebimentoAmostra());
		pedidoExamePersistir.setDataColetaAmostra(pedidoExame.getDataColetaAmostra());
		pedidoExamePersistir.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.AMOSTRA_RECEBIDA));

		fecharTarefaRecebimentoColeta(pedidoExamePersistir);
		criarTarefaCadastroResultado(pedidoExamePersistir);
		
		List<CampoMensagem> validationResult = validateEntity(pedidoExamePersistir);
		if (!validationResult.isEmpty()) {
			throw new ValidationException("erro.validacao", validationResult);
		}
		this.save(pedidoExamePersistir);
	}
	
	private void criarTarefaCadastroResultado(PedidoExame pedidoExame) {
		final Long rmr = pedidoExame.getSolicitacao().getBusca().getPaciente().getRmr();
		
		TiposTarefa.CADASTRAR_RESULTADO_CT.getConfiguracao().criarTarefa()
			.comRmr(rmr)
			.comObjetoRelacionado(pedidoExame.getId())
			.comParceiro(pedidoExame.getLaboratorio().getId())
			.apply();
	}
	
	private void fecharTarefaRecebimentoColeta(PedidoExame pedidoExame) {
		final Long rmr = pedidoExame.getSolicitacao().getBusca().getPaciente().getRmr();
		
		TiposTarefa.RECEBER_COLETA_PARA_EXAME.getConfiguracao().fecharTarefa()
		.comRmr(rmr)
		.comObjetoRelacionado(pedidoExame.getId())		
		.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
		.comUsuario(usuarioService.obterUsuarioLogado())
		.apply();
	}

	@Override
	protected List<CampoMensagem> validateEntity(PedidoExame pedidoExame) {
		List<CampoMensagem> erros = super.validateEntity(pedidoExame);
		if (!erros.isEmpty()) {
			return erros;
		}
		if(StatusPedidosExame.AMOSTRA_RECEBIDA.getId().equals(pedidoExame.getStatusPedidoExame().getId())){
			if(pedidoExame.getDataColetaAmostra() == null){
				CampoMensagem campoMensagem = new CampoMensagem("dataColeta", AppUtil.getMensagem(messageSource, "erro.pedido.exame.data_coleta_obrigatoria"));
				erros.add(campoMensagem);
			}
			if(pedidoExame.getDataRecebimentoAmostra() == null){
				CampoMensagem campoMensagem = new CampoMensagem("dataRecebimento", AppUtil.getMensagem(messageSource, "erro.pedido.exame.data_recebimento_obrigatoria"));
				erros.add(campoMensagem);
			}
			if(erros.isEmpty() && pedidoExame.getDataRecebimentoAmostra().isBefore(pedidoExame.getDataColetaAmostra())){
				CampoMensagem campoMensagem = new CampoMensagem("dataRecebimento", AppUtil.getMensagem(messageSource, "erro.pedido.exame.data_recebimento_deve_ser_posterior"));
				erros.add(campoMensagem);
			}
		}
		return erros;
	}

	@Override
	public void salvarResultadoPedidoExamePaciente(PedidoExame pedidoExame, List<MultipartFile> listaArquivosLaudo) throws Exception {
		PedidoExame pedidoExamePersistir = this.findById(pedidoExame.getId());
		Paciente paciente = obterPaciente(pedidoExamePersistir);
		
		Busca buscaAtiva = paciente.getBuscaAtiva();
		
		ExamePaciente exame = (ExamePaciente) pedidoExame.getExame();
		exame.setPaciente(paciente);
		exame.setLaboratorio(pedidoExamePersistir.getLaboratorio());
		exame.setDataColetaAmostra(pedidoExamePersistir.getDataColetaAmostra());
		exame.setLaboratorioParticular(false);
		exame.setStatusExame(StatusExame.CONFERIDO.getCodigo());
		exameService.salvar(listaArquivosLaudo, exame);
		
		genotipoService.gerarGenotipo(paciente, Boolean.FALSE);
		
		pedidoExamePersistir.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.RESULTADO_CADASTRADO));		
		pedidoExamePersistir.setExame(exame);
		
		TiposTarefa.CADASTRAR_RESULTADO_CT.getConfiguracao().fecharTarefa()
			.comObjetoRelacionado(pedidoExame.getId())
			.comRmr(paciente.getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuarioService.obterUsuarioLogado())
			.apply();
		
		
		save(pedidoExamePersistir);
		if(TiposExame.TESTE_CONFIRMATORIO.getId().equals(pedidoExame.getTipoExame().getId())){
			buscaChecklistService.criarItemCheckList(buscaAtiva, TiposBuscaChecklist.RESULTADO_EXAME_CT); 
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UltimoPedidoExameDTO obterUltimoPedidoExamePelaBusca(Long buscaId) {
		UltimoPedidoExameDTO ultimoPedidoExameDTO = new UltimoPedidoExameDTO();
		
		PedidoExame pedidoExame = 
				pedidoExameRepository
					.findFirstBySolicitacaoTipoSolicitacaoIdAndSolicitacaoBuscaIdOrderByDataCriacaoDesc(
							TiposSolicitacao.FASE_3.getId(), buscaId);
		
		Busca busca = null;
		if (pedidoExame != null) {
			busca = pedidoExame.getSolicitacao().getBusca();
			ultimoPedidoExameDTO.setPedidoExame(pedidoExame);
		}
		else {
			busca = buscaService.obterBusca(buscaId);			
		}
		if (busca == null) {
			throw new BusinessException("erro.busca.nao_localizada");
		}
		ultimoPedidoExameDTO.setRmr(busca.getPaciente().getRmr());
		boolean temContato = busca.getPaciente().getEnderecosContato() != null && !busca.getPaciente().getEnderecosContato().isEmpty();
		
		ultimoPedidoExameDTO.setMunicipioEnderecoPaciente(temContato ? busca.getPaciente().getEnderecosContato().get(0).getMunicipio().getDescricao() : null);
		ultimoPedidoExameDTO.setUfEnderecoPaciente(temContato ? busca.getPaciente().getEnderecosContato().get(0).getMunicipio().getUf().getSigla() : null);
		if (busca.getPaciente().getCentroAvaliador() != null && 
				busca.getPaciente().getCentroAvaliador().getLaboratorioPreferencia() != null &&
				busca.getPaciente().getCentroAvaliador().getLaboratorioPreferencia().getFazExameCT()) {
			ultimoPedidoExameDTO.setLaboratorioDePrefencia(busca.getPaciente().getCentroAvaliador().getLaboratorioPreferencia());
		}
		
		return ultimoPedidoExameDTO;
	}

	/**
	 * Fecha a tarefa aberta para o laboratório que estava como responsável pelo pedido de exame.
	 * Só é possível cancelar fechar a tarefa se ela ainda não estiver sido finalizada.
	 * 
	 * @param pedidoExameId ID do pedido de exame envolvido no procedimento.
	 * @return TRUE se foi possível cancelar a tarefa.
	 */
	private boolean fecharTarefaLaboratorioSePossivel(PedidoExame pedidoExame) {
		final Paciente paciente = pacienteService.obterPacientePorSolicitacaoCT(pedidoExame.getSolicitacao().getId());
		
		TarefaDTO tarefaRecebimentoColetaLab = TiposTarefa.RECEBER_COLETA_PARA_EXAME.getConfiguracao()
			.obterTarefa()
			.comRmr(paciente.getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
			.comObjetoRelacionado(pedidoExame.getId())
			.comParceiros(Arrays.asList(pedidoExame.getLaboratorio().getId()))
			.apply();
		
		if(tarefaRecebimentoColetaLab != null && tarefaRecebimentoColetaLab.emAndamento()){
			tarefaRecebimentoColetaLab.getTipo().getConfiguracao().fecharTarefa()
				.comTarefa(tarefaRecebimentoColetaLab.getId())
				.apply();
			
			return true;
		}
		return false;
	}
	
	/**
	 * Cancelar a tarefa aberta para o laboratório que estava como responsável pelo pedido de exame.
	 * Só é possível cancelar a tarefa se ela ainda não estiver sido finalizada.
	 * 
	 * @param pedidoExameId ID do pedido de exame envolvido no procedimento.
	 * @return TRUE se foi possível cancelar a tarefa.
	 */
	private boolean cancelarTarefaLaboratorioSePossivel(PedidoExame pedidoExame) {
		final Paciente paciente = pacienteService.obterPacientePorSolicitacaoCT(pedidoExame.getSolicitacao().getId());
		
		TarefaDTO tarefaRecebimentoColetaLab = TiposTarefa.RECEBER_COLETA_PARA_EXAME.getConfiguracao()
			.obterTarefa()
			.comRmr(paciente.getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
			.comObjetoRelacionado(pedidoExame.getId())
			.comParceiros(Arrays.asList(pedidoExame.getLaboratorio().getId()))
			.apply();
		
		if(tarefaRecebimentoColetaLab != null && tarefaRecebimentoColetaLab.emAndamento()){
			tarefaRecebimentoColetaLab.getTipo().getConfiguracao().fecharTarefa()
				.comTarefa(tarefaRecebimentoColetaLab.getId())
				.apply();
			
			return true;
		}
		return false;
	}

	@Override
	public boolean alterarLaboratorioCT(Long pedidoExameId, Long idLaboratorio) {
		PedidoExame pedidoExame = findById(pedidoExameId);
		boolean foiPossivelFecharTarefa = fecharTarefaLaboratorioSePossivel(pedidoExame);
		if(foiPossivelFecharTarefa){
			pedidoExame.setLaboratorio(new Laboratorio());
			pedidoExame.getLaboratorio().setId(idLaboratorio);
			save(pedidoExame);
			
			criarTarefaParaLaboratorio(pedidoExame);
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see br.org.cancer.modred.service.IPedidoExameService#obterPedidoExame(java.lang.Long)
	 */
	@Override
	public PedidoExame obterPedidoExame(Long idPedidoExame) {
		
		return pedidoExameRepository.findById(idPedidoExame).get();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true)
	public File obterDocumentoInstrucaoColeta(Long idBusca, Long idTipoExame) {
		PedidoExame pedidoExame = pedidoExameRepository
				.findFirstBySolicitacaoTipoSolicitacaoIdAndSolicitacaoBuscaIdOrderByDataCriacaoDesc(
						TiposSolicitacao.FASE_3.getId(), idBusca);
		if (pedidoExame == null) {
			throw new BusinessException("");
		}
		Relatorio relatorio = relatorioService.obterRelatorioPorCodigo(Relatorios.INSTRUCAO_COLETA_SANGUE_SWAB_ORAL_CT.getCodigo());

		Paciente paciente = pedidoExame.getSolicitacao().getBusca().getPaciente();
		Laboratorio laboratorio = pedidoExame.getLaboratorio();
		EnderecoContatoLaboratorio endereco = laboratorio.getEndereco();
		
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		InstrucaoColeta instrucaoColeta = null;
		if(laboratorio.getInstrucoesColeta() != null && !laboratorio.getInstrucoesColeta().isEmpty()) {
			if(TiposExame.TESTE_CONFIRMATORIO.getId().equals(idTipoExame)) {
				instrucaoColeta = laboratorio.getInstrucoesColeta().stream().filter(coleta -> coleta.getTipo().equals(TiposInstrucaoColeta.EXAME)).findFirst().get();
			}
			else if(TiposExame.TESTE_CONFIRMATORIO_SWAB.getId().equals(idTipoExame)) {
				instrucaoColeta = laboratorio.getInstrucoesColeta().stream().filter(coleta -> coleta.getTipo().equals(TiposInstrucaoColeta.SWAB)).findFirst().get();
			}
			else if(TiposExame.TESTE_CONFIRMATORIO_SOMENTE_SWAB.getId().equals(idTipoExame)) {
				instrucaoColeta = laboratorio.getInstrucoesColeta().stream().filter(coleta -> coleta.getTipo().equals(TiposInstrucaoColeta.SWAB)).findFirst().get();
			}
		}
		
		return new HtmlReportGenerator()
				.comParametro(ParametrosRelatorios.DATA_ATUAL, hoje.format(formatter))
				.comParametro(ParametrosRelatorios.RMR, paciente.getRmr().toString())
				.comParametro(ParametrosRelatorios.NOME_PACIENTE, paciente.getNome())
				.comParametro(ParametrosRelatorios.DATA_NASCIMENTO_PACIENTE, paciente.getDataNascimento().format(formatter))
				.comParametro(ParametrosRelatorios.NOME_MEDICO, paciente.getMedicoResponsavel().getNome())
				.comParametro(ParametrosRelatorios.TIPO_AMOSTRA, TiposExame.getDescricaoPorId(idTipoExame))	
				
				.comParametro(ParametrosRelatorios.INSTRUCAO_COLETA, instrucaoColeta != null ? instrucaoColeta.getDescricao(): "")

				.comParametro(ParametrosRelatorios.NOME_LABORATORIO, laboratorio.getNome())
				.comParametro(ParametrosRelatorios.LOGRADOURO_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getTipoLogradouro() != null && endereco.getNomeLogradouro() != null
						? endereco.getTipoLogradouro() + "; " + endereco.getNomeLogradouro() : "")
				.comParametro(ParametrosRelatorios.NUMERO_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getNumero() != null ? endereco.getNumero().toString() : "")
				.comParametro(ParametrosRelatorios.COMPLEMENTO_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getComplemento() != null ? endereco.getComplemento() : "")
				.comParametro(ParametrosRelatorios.BAIRRO_ENDERECO_LOGRADOURO, 
						endereco != null && endereco.getBairro() != null ? ", " + endereco.getBairro() : "")
				.comParametro(ParametrosRelatorios.MUNICIPIO_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getMunicipio() != null ? endereco.getMunicipio().getDescricao() : "")
				.comParametro(ParametrosRelatorios.UF_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getMunicipio() != null && endereco.getMunicipio().getUf() != null ? endereco.getMunicipio().getUf().getSigla() : "")
				.comParametro(ParametrosRelatorios.CEP_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getCep() != null? ", " + endereco.getCep() : "")
				.comParametro(ParametrosRelatorios.CONTATO_LABORATORIO, 
						laboratorio.getNomeContato() != null ? laboratorio.getNomeContato() : "" )
				
				.gerarPdf(relatorio);
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=true)
	public File obterDocumentoPedidoExame(Long idBusca) {
		PedidoExame pedidoExame = pedidoExameRepository
				.findFirstBySolicitacaoTipoSolicitacaoIdAndSolicitacaoBuscaIdOrderByDataCriacaoDesc(
						TiposSolicitacao.FASE_3.getId(), idBusca);
		if (pedidoExame == null) {
			throw new BusinessException("");
		}
		Relatorio relatorio = relatorioService.obterRelatorioPorCodigo(Relatorios.PEDIDO_EXAME_ALTA_RESOLUCAO.getCodigo());

		Paciente paciente = pedidoExame.getSolicitacao().getBusca().getPaciente();
		Laboratorio laboratorio = pedidoExame.getLaboratorio();
		EnderecoContatoLaboratorio endereco = laboratorio.getEndereco();
		
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return new HtmlReportGenerator()
				.comParametro(ParametrosRelatorios.DATA_ATUAL, hoje.format(formatter))
				.comParametro(ParametrosRelatorios.DATA_NASCIMENTO_PACIENTE, paciente.getDataNascimento().format(formatter))
				.comParametro(ParametrosRelatorios.NOME_MEDICO, paciente.getMedicoResponsavel().getNome())
				.comParametro(ParametrosRelatorios.NOME_PACIENTE, paciente.getNome())
				.comParametro(ParametrosRelatorios.RMR, paciente.getRmr().toString())
				.comParametro(ParametrosRelatorios.INSTRUCAO_COLETA, 
						laboratorio.getInstrucoesColeta() != null && !laboratorio.getInstrucoesColeta().isEmpty() ?
								laboratorio.getInstrucoesColeta().get(0).getDescricao(): "")
				.comParametro(ParametrosRelatorios.NOME_LABORATORIO, laboratorio.getNome())
				.comParametro(ParametrosRelatorios.LOGRADOURO_ENDERECO_LABORATORIO, 
						endereco != null ? endereco.getTipoLogradouro() + " " + endereco.getNomeLogradouro() : "")
				.comParametro(ParametrosRelatorios.NUMERO_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getNumero() != null ? endereco.getNumero().toString() : "")
				.comParametro(ParametrosRelatorios.COMPLEMENTO_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getComplemento() != null ? endereco.getComplemento() : "")
				.comParametro(ParametrosRelatorios.BAIRRO_ENDERECO_LOGRADOURO, 
						endereco != null && endereco.getBairro() != null ? endereco.getBairro() : "")
				.comParametro(ParametrosRelatorios.MUNICIPIO_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getMunicipio() != null ? endereco.getMunicipio().getDescricao() : "")
				.comParametro(ParametrosRelatorios.UF_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getMunicipio() != null && endereco.getMunicipio().getUf() != null ? endereco.getMunicipio().getUf().getSigla() : "")
				.comParametro(ParametrosRelatorios.CEP_ENDERECO_LABORATORIO, 
						endereco != null && endereco.getCep() != null? endereco.getCep() : "")
				.comParametro(ParametrosRelatorios.CONTATO_LABORATORIO, 
						laboratorio.getNomeContato() != null ? laboratorio.getNomeContato() : "" )
				.gerarPdf(relatorio);
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public File obterArquivoPedidoExameCT(Long idPedidoExame, String codigo) {
		PedidoExame pedidoExame = findById(idPedidoExame);
		Relatorio relatorio = relatorioService.obterRelatorioPorCodigo(codigo);

		Paciente paciente = pedidoExame.getSolicitacao().getBusca().getPaciente();
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return new HtmlReportGenerator()
				.comParametro(ParametrosRelatorios.UF_PACIENTE, paciente.getEnderecosContato().get(0).getMunicipio().getUf().getSigla())
				.comParametro(ParametrosRelatorios.CIDADE_PACIENTE, paciente.getEnderecosContato().get(0).getMunicipio().getDescricao())
				.comParametro(ParametrosRelatorios.DATA_ATUAL, hoje.format(formatter))
				.comParametro(ParametrosRelatorios.DATA_NASCIMENTO_PACIENTE, paciente.getDataNascimento().format(formatter))
				.comParametro(ParametrosRelatorios.DIAGNOSTICO_PACIENTE, paciente.getDiagnostico().getCid().getDescricao())
				.comParametro(ParametrosRelatorios.NOME_MEDICO, paciente.getMedicoResponsavel().getNome())
				.comParametro(ParametrosRelatorios.NOME_PACIENTE, paciente.getNome())
				.comParametro(ParametrosRelatorios.NOME_LABORATORIO, pedidoExame.getLaboratorio().getNome())
				.comParametro(ParametrosRelatorios.RACA_PACIENTE, paciente.getRaca().getNome())
				.comParametro(ParametrosRelatorios.RMR, paciente.getRmr().toString())
				.comParametro(ParametrosRelatorios.INICIAIS_NOME_PACIENTE, paciente.nomeAbreviado().toString())
				.comParametro(ParametrosRelatorios.CEP_PACIENTE, paciente.getEnderecosContato().get(0).getCep().toString())
				.comParametro(ParametrosRelatorios.LOGRADOURO_PACIENTE, paciente.getEnderecosContato().get(0).getNomeLogradouro().toString())
				.comParametro(ParametrosRelatorios.NUMERO_PACIENTE, paciente.getEnderecosContato().get(0).getNumero().toString())
				.comParametro(ParametrosRelatorios.COMPLEMENTO_PACIENTE, paciente.getEnderecosContato().get(0).getComplemento() != null? paciente.getEnderecosContato().get(0).getComplemento().toString(): "")
				.comParametro(ParametrosRelatorios.BAIRRO_PACIENTE, paciente.getEnderecosContato().get(0).getBairro().toString())
				.gerarPdf(relatorio);
	}

	@CreateLog
	@Override
	public void criarPedidoFase2Internacional(Solicitacao solicitacao, List<Locus> locusSolicitados ) {
		
		final Doador doador = solicitacao.getMatch().getDoador();
		if (temPedidoExameCtComResultadoCadastrado(doador.getId())) {
			throw new BusinessException("pedido.exame.ct.com.resultado.ja.existe.doador.falha");
		}
		
		Paciente paciente = solicitacao.getMatch().getBusca().getPaciente();
		PedidoExame pedido = new PedidoExame();
		pedido.setSolicitacao(solicitacao);
		pedido.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM.getId()));
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setOwner(OwnerPedidoExame.DOADOR);
		pedido.setTipoExame(new TipoExame(TiposExame.TIPIFICACAO_HLA_ALTA_RESOLUCAO.getId()));
		
		List<Locus> listLocus = locusService.findAll();
		List<Locus> locusSelecionado = locusSolicitados.stream().map(locus->{
			return listLocus.stream().filter(locusBanco -> locus.getCodigo().equals(locusBanco.getCodigo())).findFirst().get();
		}).collect(Collectors.toList());
		
		pedido.setLocusSolicitados(locusSelecionado);
		
		save(pedido);
				
		TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.getConfiguracao()
				.criarTarefa()
				.comRmr(paciente.getRmr())
				.comObjetoRelacionado(pedido.getId())
				.apply();
		
		TiposTarefa.ENVIAR_PEDIDO_FASE2_PARA_EMDIS.getConfiguracao()
			.criarTarefa()
			.comRmr(paciente.getRmr())
			.comObjetoRelacionado(pedido.getId())
			.apply();
	}

	@Override
	@CreateLog(TipoLogEvolucao.RESULTADO_EXAME_PARA_DOADOR)
	public void salvarResultadoPedidoExameDoadorInternacional(Long idPedidoExame, ExameDoadorInternacional exame, Long motivoStatusId,
			Long timeRetornoInatividade) throws Exception {
		PedidoExame pedidoExamePersistir = this.findById(idPedidoExame);
		
		
		salvarResultadoPedidoExameDoador(exame, motivoStatusId, timeRetornoInatividade, pedidoExamePersistir, null);
		

		fecharTarefaCadastrarResultadoExameInternacional(pedidoExamePersistir);
		
		pedidoExamePersistir.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		pedidoExamePersistir.getSolicitacao().getMatch().getDoador();
		

	}
		
	@Override
	@CreateLog(TipoLogEvolucao.RESULTADO_EXAME_CT_PARA_DOADOR)
	public PedidoExame salvarResultadoPedidoExameCTDoadorInternacional(Long id, ExameDoadorInternacional exame, Long motivoStatusId,
			Long timeRetornoInatividade, List<MultipartFile> listaArquivosLaudo) throws Exception {
		PedidoExame pedidoExamePersistir = this.findById(id);
		
		salvarResultadoPedidoExameDoador(exame, motivoStatusId, timeRetornoInatividade, pedidoExamePersistir, listaArquivosLaudo);

		fecharTarefaCadastrarResultadoExameCTInternacional(pedidoExamePersistir);
		
		final Long rmr = 
				pedidoExamePersistir.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		final Doador doador = pedidoExamePersistir.getSolicitacao().getMatch().getDoador();
		
		executarProcedureMatchService.gerarMatchDoador(doador, null, null);
		
		return pedidoExamePersistir; 
		
	}

	/**
	 * Salva o resultado do pedido de exame para um doador.
	 * 
	 * TODO: Refatorar este método.
	 * Por conta do pedido de exame tratar cordão e doador como entidades distintas (duas colunas)
	 * tem código replicado e ilegível.
	 * 
	 * @param exame
	 * @param motivoStatusId
	 * @param timeRetornoInatividade
	 * @param pedidoExamePersistir
	 * @param listaArquivosLaudo
	 * @return
	 * @throws Exception
	 */
	private Doador salvarResultadoPedidoExameDoador(Exame exame, Long motivoStatusId,
			Long timeRetornoInatividade, PedidoExame pedidoExamePersistir, List<MultipartFile> listaArquivosLaudo) throws Exception {
		ehPossivelSalvarResultadoPedidoExameDoadorInternacional(pedidoExamePersistir.getStatusPedidoExame());
				
		Doador doador = pedidoExamePersistir.getSolicitacao().getMatch().getDoador();
		Busca busca = pedidoExamePersistir.getSolicitacao().getMatch().getBusca();
		pedidoExamePersistir.getSolicitacao().getMatch();
		
		List<ArquivoExame> arquivosExame = null;
		
		if (CollectionUtils.isNotEmpty(listaArquivosLaudo)) {
			arquivosExame = listaArquivosLaudo.stream().map(arquivoLaudo -> {
				ArquivoExame arquivoExame = new ArquivoExame();
				arquivoExame.setCaminhoArquivo(arquivoLaudo.getOriginalFilename());
				arquivoExame.setExame(exame);
				return arquivoExame;
			}).collect(Collectors.toList());
		}
		
		exame.setStatusExame(StatusExame.CONFERIDO.getCodigo());
		validate(pedidoExamePersistir, exame);
		((IExameDoador)exame).setDoador(doador);
		exame.setArquivosExame(arquivosExame);
		
		if(exame instanceof ExameDoadorInternacional) {
			exameDoadorService.salvar(listaArquivosLaudo, (ExameDoadorInternacional)exame, pedidoExamePersistir.getTipoExame());
		}else if(exame instanceof ExameCordaoInternacional) {
			exameCordaoService.salvar(listaArquivosLaudo, (ExameCordaoInternacional)exame, pedidoExamePersistir.getTipoExame());
		}
		pedidoExamePersistir.setExame(exame);
		
		Solicitacao solicitacao = pedidoExamePersistir.getSolicitacao();
		
		if(solicitacao.getTipoSolicitacao().getId().equals(TiposSolicitacao.FASE_2.getId()) || 
				solicitacao.getTipoSolicitacao().getId().equals(TiposSolicitacao.FASE_2_INTERNACIONAL.getId())){
			buscaChecklistService.criarItemCheckList(busca,  pedidoExamePersistir.getSolicitacao().getMatch(), TiposBuscaChecklist.RESULTADO_EXAME_SEGUNDA_FASE);			
		}
		else if(solicitacao.getTipoSolicitacao().getId().equals(TiposSolicitacao.FASE_3.getId()) 
				|| solicitacao.getTipoSolicitacao().getId().equals(TiposSolicitacao.FASE_3_INTERNACIONAL.getId())){
			buscaChecklistService.criarItemCheckList(busca,  pedidoExamePersistir.getSolicitacao().getMatch(), TiposBuscaChecklist.RESULTADO_EXAME_CT);
		}
						
		pedidoExamePersistir.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.RESULTADO_CADASTRADO));
		
		if (doador instanceof DoadorInternacional) {
			genotipoDoadorInternacionalService.gerarGenotipo(doador);
		}
		else if (doador instanceof CordaoInternacional) {
			genotipoCordaoInternacionalService.gerarGenotipo(doador);
		}
		
		pedidoExamePersistir.getSolicitacao().setStatus(StatusSolicitacao.CONCLUIDA.getId());
		
		save(pedidoExamePersistir);
		
		if (motivoStatusId != null) {
			doadorInternacionalService.atualizarStatusDoador(doador.getId(), 
				timeRetornoInatividade == null ? StatusDoador.INATIVO_PERMANENTE : StatusDoador.INATIVO_TEMPORARIO, 
						motivoStatusId, timeRetornoInatividade);
		}
		return doador;
	}
	
	private void validate(PedidoExame pedidoExame, Exame exame) {
		
		Boolean[] encontrados = new Boolean[pedidoExame.getLocusSolicitados().size()];
		int[] posicao = {0};
		pedidoExame.getLocusSolicitados().forEach(locusSolicitado -> {
			encontrados[posicao[0]] =  exame.getLocusExames()
				.stream()
				.anyMatch( locusExame -> 
					locusExame.getId().getLocus().getCodigo().equals(locusSolicitado.getCodigo())
				);
			
			posicao[0]++;
		});
		
		if (!Arrays.stream(encontrados).allMatch(encontrado -> encontrado == true))  {
			CampoMensagem campoMensagem = new CampoMensagem("locusSolicitados", AppUtil.getMensagem(messageSource, "erro.pedido.exame.locus.solicitados"));
			throw new ValidationException("erro.validacao", Arrays.asList(campoMensagem));		
		}
	}
	
	private void ehPossivelSalvarResultadoPedidoExameDoadorInternacional(StatusPedidoExame statusPedidoExame) {
		if (StatusPedidosExame.RESULTADO_CADASTRADO.getId().equals(statusPedidoExame.getId())) {			
			throw new BusinessException("erro.pedido.exame.ja.concluido");
		}
		else if (StatusPedidosExame.CANCELADO.getId().equals(statusPedidoExame.getId())) {			
			throw new BusinessException("erro.pedido.exame.cancelado");
		}
	}
	
	private void fecharTarefaCadastrarResultadoExameInternacional(PedidoExame pedidoExame) {
		TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.getConfiguracao()
			.fecharTarefa()
			.comObjetoRelacionado(pedidoExame.getId())
			.comUsuario(usuarioService.obterUsuarioLogado())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comRmr(pedidoExame.getSolicitacao().getMatch().getBusca().getPaciente().getRmr())
			.apply();		
	}
	
	private void fecharTarefaCadastrarResultadoExameCTInternacional(PedidoExame pedidoExame) {
		final Long rmr = pedidoExame.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		
		TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.getConfiguracao()			
			.fecharTarefa()
			.comRmr(rmr)
			.comObjetoRelacionado(pedidoExame.getId())
			.comUsuario(usuarioService.obterUsuarioLogado())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.apply();		
	}
	
	
	@Override
	public List<PedidoExame> listarPedidoExamePorDoador(Long matchId, List<StatusPedidosExame> status) {
		return pedidoExameRepository.listarPedidoExamePorDoador(
				matchId, status.stream().map(statusItem -> statusItem.getId()).collect(Collectors.toList()));
	}

	@Override
	public List<PedidoExame> listarPedidoExamePorDoadorEmAndamento(Long matchId) {
		Match match = matchService.findById(matchId);
		final Doador doador = match.getDoador();
		final boolean isDoadorInternacional = TiposDoador.INTERNACIONAL.equals(doador.getTipoDoador());
		
		if(isDoadorInternacional){
			List<PedidoExame> pedidosComStatusInternacionais = 
					listarPedidoExamePorDoador(match.getId(), Arrays.asList(StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM));
			return pedidosComStatusInternacionais;
		}
		else {
			List<PedidoExame> pedidosComStatusNacionais= 
					listarPedidoExamePorDoador(match.getId(), 
							Arrays.asList(StatusPedidosExame.AGUARDANDO_AMOSTRA, StatusPedidosExame.AMOSTRA_RECEBIDA, 
									StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM, StatusPedidosExame.NAO_ATENDIDA_COM_HLA_IDM));
			
			return pedidosComStatusNacionais;
		}
	}
	
	/**
	 * cancelar a tarefa aberta para o perfil do analista de busca .
	 * Só é possível cancelar a tarefa se ela ainda não estiver sido finalizada.
	 * 
	 * @param pedidoExame - pedido de exame envolvido no procedimento.
	 * @return TRUE se foi possível cancelar a tarefa.
	 */
	private boolean cancelarTarefaAnalistaBuscaSePossivel(PedidoExame pedidoExame) {
		final Paciente paciente = pedidoExame.getSolicitacao().getMatch().getBusca().getPaciente();
		
		TarefaDTO tarefaCadastrarResultadoExameInternacional = TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.getConfiguracao()
			.obterTarefa()
			.comRmr(paciente.getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA, StatusTarefa.ATRIBUIDA))
			.comObjetoRelacionado(pedidoExame.getId())
			.comUsuario(usuarioService.obterUsuarioLogado())
			.apply();
				
		if(tarefaCadastrarResultadoExameInternacional != null && tarefaCadastrarResultadoExameInternacional.emAndamento()){
			tarefaCadastrarResultadoExameInternacional.getTipo().getConfiguracao().cancelarTarefa()
				.comTarefa(tarefaCadastrarResultadoExameInternacional.getId())
				.apply();
			
			return true;
		}
		return false;
	}
	
	/**
	 * cancelar a tarefa aberta para o perfil do analista de busca .
	 * Só é possível cancelar a tarefa se ela ainda não estiver sido finalizada.
	 * 
	 * @param pedidoExame - pedido de exame envolvido no procedimento.
	 * @return TRUE se foi possível cancelar a tarefa.
	 */
	private boolean cancelarTarefaEnviarPedidoComplementarEmdisSePossivel(PedidoExame pedidoExame) {
		final Paciente paciente = pedidoExame.getSolicitacao().getMatch().getBusca().getPaciente();
		
		TarefaDTO tarefaEnviarPedidoComplmentarEmdisInternacional = TiposTarefa.ENVIAR_PEDIDO_FASE2_PARA_EMDIS.getConfiguracao()
			.obterTarefa()
			.comRmr(paciente.getRmr())
			.comStatus(Arrays.asList(StatusTarefa.ABERTA))
			.comObjetoRelacionado(pedidoExame.getId())			
			.apply();
				
		if(tarefaEnviarPedidoComplmentarEmdisInternacional != null && tarefaEnviarPedidoComplmentarEmdisInternacional.emAndamento()){
			tarefaEnviarPedidoComplmentarEmdisInternacional.getTipo().getConfiguracao().cancelarTarefa()
				.comTarefa(tarefaEnviarPedidoComplmentarEmdisInternacional.getId())				
				.apply();
			
			return true;
		}
		return false;
	}
	

	@Override
	public void atualizarPedidoExameInternacional(PedidoExame pedidoExame) {
		PedidoExame pedidoLocalizado = this.findById(pedidoExame.getId());
		
		if (StatusPedidosExame.RESULTADO_CADASTRADO.getId().equals(pedidoLocalizado.getStatusPedidoExame().getId())) {			
			throw new BusinessException("erro.pedido.exame.ja.concluido");
		}
		
		List<Locus> listLocus = locusService.findAll();
		List<Locus> locusSelecionado = pedidoExame.getLocusSolicitados().stream().map(locus->{
			return listLocus.stream().filter(locusBanco -> locus.getCodigo().equals(locusBanco.getCodigo())).findFirst().get();
		}).collect(Collectors.toList());
		
		pedidoLocalizado.setLocusSolicitados(locusSelecionado);
		this.save(pedidoLocalizado);
	}

	@Override
	public Long obterIdPedidoExamePorIdMatch(Long idMatch) {		
		return pedidoExameRepository.obterIdPedidoExamePorIdMatch(idMatch);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@CreateLog
	@Override
	public void criarPedidoExameCtInternacional(Solicitacao solicitacao) {
		PedidoExame pedidoExame = new PedidoExame();
		pedidoExame.setDataCriacao(LocalDateTime.now());
		pedidoExame.setOwner(OwnerPedidoExame.DOADOR);
		pedidoExame.setSolicitacao(solicitacao);
		pedidoExame.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM.getId()));
		TipoExame tipoExame = tipoExameService.findById(TiposExame.TESTE_CONFIRMATORIO.getId());				
		pedidoExame.setTipoExame(tipoExame);
		pedidoExame.setLocusSolicitados(tipoExame.getLocus().stream().map(locus -> locusService.findById(locus.getCodigo())).collect(Collectors.toList()));
		
		save(pedidoExame);
		
		criarTarefaCadastrarResultadoExameCtInternacional(pedidoExame);
		
		if(TiposDoador.INTERNACIONAL.equals(solicitacao.getMatch().getDoador().getTipoDoador())){
			criarPagamentosPedidoExameCtInternacional(pedidoExame);	
		}
	}
	
	@Override
	public PedidoExame obterPedidoExamePorSolicitacaoId(Long idSolicitacao) {
		List<PedidoExame> pedidosExames = pedidoExameRepository.findBySolicitacaoId(idSolicitacao);
		PedidoExame pedidoAberto = null;
		if (pedidosExames != null && !pedidosExames.isEmpty()) {
			
			Optional<PedidoExame> pedidoExameOpt = pedidosExames.stream()
					.filter(pedido -> {
						return pedido.getStatusPedidoExame().getId().equals(
								StatusPedidosExame.NAO_ATENDIDA_SEM_HLA_IDM.getId());
					})
					.findFirst();
			if(pedidoExameOpt.isPresent()){
				pedidoAberto = pedidoExameOpt.get();
			}
		}
		return pedidoAberto;
	}
	

	private void criarTarefaCadastrarResultadoExameCtInternacional(PedidoExame pedidoExame) {
		TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.getConfiguracao()
			.criarTarefa()
			.comRmr(pedidoExame.getSolicitacao().getMatch().getBusca().getPaciente().getRmr())
			.comObjetoRelacionado(pedidoExame.getId())
			.apply();		
	}
	
	@Override
	public boolean temPedidoIdmAberto(PedidoExame pedidoExame){
		
		PedidoIdm pedidoIdm = pedidoIdmService.findBySolicitacaoId(pedidoExame.getSolicitacao().getId());
		if (pedidoIdm != null && StatusPedidosIdm.AGUARDANDO_RESULTADO.getId().equals(pedidoIdm.getStatusPedidoIdm().getId())) {
			return true;
		}
		return false;
		
	}
	
	@Override
	public TarefaDTO obterTarefaPorPedidoEmAberto(PedidoExame pedidoExame){
		final Long rmr = pedidoExame.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		
		return 
				TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.getConfiguracao()
					.obterTarefa()
					.comRmr(rmr)
					.comStatus(Arrays.asList(StatusTarefa.ABERTA))
					.comObjetoRelacionado(pedidoExame.getId())
					.apply();		
	}
	
	
	@Override
	@CreateLog(TipoLogEvolucao.RESULTADO_EXAME_CT_PARA_CORDAO)
	public PedidoExame salvarResultadoPedidoExameCTCordaoInternacional(Long id, ExameCordaoInternacional exame, Long motivoStatusId,
			Long timeRetornoInatividade, List<MultipartFile> listaArquivosLaudo) throws Exception {
		PedidoExame pedidoExamePersistir = this.findById(id);
		Long rmr = pedidoExamePersistir.getSolicitacao().getMatch().getBusca().getPaciente().getRmr();
		
		CordaoInternacional doador = (CordaoInternacional) salvarResultadoPedidoExameDoador(exame, motivoStatusId, timeRetornoInatividade,
				pedidoExamePersistir, listaArquivosLaudo);

		fecharTarefaCadastrarResultadoExameCTInternacional(pedidoExamePersistir);
		
		executarProcedureMatchService.gerarMatchDoador(doador, null, null);
		
		return pedidoExamePersistir; 

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean temPedidoExameCtComResultadoCadastrado(Long idDoador) {
		List<PedidoExame> lista =  
				find(new Equals<Long>("tipoExame.id", TiposExame.TESTE_CONFIRMATORIO.getId()), 
						new Equals<Long>("statusPedidoExame.id", StatusPedidosExame.RESULTADO_CADASTRADO.getId() ),
						new Equals<Long>("solicitacao.match.doador.id", idDoador),
						new Equals<Long[]>("solicitacao.tipoSolicitacao.id", 
								new Long[] {TiposSolicitacao.FASE_3.getId(), TiposSolicitacao.FASE_3_INTERNACIONAL.getId()} )
					);
		return  lista != null && !lista.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean temPedidoExameCtParaPacienteComResultadoCadastrado(Long rmr) {
		List<PedidoExame> lista =  
				find(new Equals<Long>("tipoExame.id", TiposExame.TESTE_CONFIRMATORIO.getId()), 
						new Equals<Long>("statusPedidoExame.id", StatusPedidosExame.RESULTADO_CADASTRADO.getId() ),
						new Equals<Long>("solicitacao.busca.paciente.rmr", rmr),
						new Equals<Long[]>("solicitacao.tipoSolicitacao.id", 
								new Long[] {TiposSolicitacao.FASE_3.getId()} )
					);
		return  lista != null && !lista.isEmpty();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long obterTipoExameCtParaPacienteComResultadoAguardandoAmostra(Long rmr) {
		List<PedidoExame> lista =  
				find(new Equals<Long[]>("tipoExame.id", 
						new Long[] {TiposExame.TESTE_CONFIRMATORIO.getId(), TiposExame.TESTE_CONFIRMATORIO_SWAB.getId(), 
								TiposExame.TESTE_CONFIRMATORIO_SOMENTE_SWAB.getId()} ), 
						new Equals<Long>("statusPedidoExame.id", StatusPedidosExame.AGUARDANDO_AMOSTRA.getId()),
						new Equals<OwnerPedidoExame>("owner", OwnerPedidoExame.PACIENTE),
						new IsNull("dataRecebimentoAmostra"),
						new Equals<Long>("solicitacao.busca.paciente.rmr", rmr),
						new Equals<Long[]>("solicitacao.tipoSolicitacao.id", 
								new Long[] {TiposSolicitacao.FASE_3.getId()} )
					);
		
		if(lista != null && !lista.isEmpty()) {
			PedidoExame pedido = lista.get(0);
			return pedido.getTipoExame().getId();
		}
		return null;
	}

	
	@CreateLog
	@Override
	public void criarPedidoFase3Paciente(Solicitacao solicitacao, Long idLaboratorio, Long idTipoExame) {
		
		if (idLaboratorio == null) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		Laboratorio laboratorio = laboratorioService.findById(idLaboratorio);
		if (laboratorio == null) {
			throw new BusinessException("erro.mensagem.laboratorio.nao.encontrado");
		}
		
		PedidoExame pedido = new PedidoExame();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setSolicitacao(solicitacao);
		
		pedido.setOwner(OwnerPedidoExame.PACIENTE);
		pedido.setLaboratorio(laboratorio);
	
		pedido.setStatusPedidoExame(new StatusPedidoExame(StatusPedidosExame.AGUARDANDO_AMOSTRA));

		pedido.setTipoExame(new TipoExame(TiposExame.get(idTipoExame).getId()));
		pedido.setLocusSolicitados( tipoExameService.listarLocusAssociados(TiposExame.get(idTipoExame).getId()) );
		
		pedido = save(pedido);			
		
		criarTarefaParaLaboratorio(pedido);		
	}

	/**
	 * Lista as tarefas de pedido de exame (fase 2 e CT) que estão em andamento para os
	 * doadores nacionais associados, via match, com o paciente.
	 *  
	 * @param idBusca ID da busca ativa do paciente.
	 * @return lista de tarefas.
	 */
	private List<TarefaDTO> listarTarefasAndamento(TiposTarefa tipoTarefa, Boolean exibirHistorico, Predicate<TarefaDTO> filtrarPelaBusca) {
		List<StatusTarefa> filtrarPelosStatus = new ArrayList<StatusTarefa>();
				
		filtrarPelosStatus.add(StatusTarefa.ABERTA);
		filtrarPelosStatus.add(StatusTarefa.ATRIBUIDA);
		
		if(Boolean.TRUE.equals(exibirHistorico)){
			filtrarPelosStatus.add(StatusTarefa.CANCELADA);
			filtrarPelosStatus.add(StatusTarefa.FEITA);
		}
		
		JsonViewPage<TarefaDTO> tarefasPedidosExameEmAndamento = tipoTarefa.getConfiguracao()
			.listarTarefa()
			.comFiltro(filtrarPelaBusca)
			.comStatus(filtrarPelosStatus)
			.apply();
		
		if(tarefasPedidosExameEmAndamento != null && 
				CollectionUtils.isNotEmpty(tarefasPedidosExameEmAndamento.getValue().getContent())){
			return tarefasPedidosExameEmAndamento.getValue().getContent();
		}
		return new ArrayList<>();
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefasDeColetaPedidoExameLaboratorio(PageRequest pageRequest) {
		
		List<Long> parceiros = usuarioService.obterUsuarioLogado().listarIdParceiros(TiposTarefa.RECEBER_COLETA_PARA_EXAME.getConfiguracao().getParceiro());
		
		JsonViewPage<TarefaDTO> tarefas = TiposTarefa.RECEBER_COLETA_PARA_EXAME.getConfiguracao()
				.listarTarefa()
				.comStatus(Arrays.asList(StatusTarefa.ABERTA,StatusTarefa.ATRIBUIDA))
				.comParceiros(parceiros)
				.comPaginacao(pageRequest)
				.apply();
		
		tarefas.setSerializationView(TiposTarefa.RECEBER_COLETA_PARA_EXAME.getConfiguracao().getJsonView());
		
		return tarefas;
	}

	@Override
	public JsonViewPage<TarefaDTO> listarTarefasDeResultadoPedidoExameLaboratorio(PageRequest pageRequest) {

		List<Long> parceiros = usuarioService.obterUsuarioLogado().listarIdParceiros(TiposTarefa.RECEBER_COLETA_PARA_EXAME.getConfiguracao().getParceiro());
		
		JsonViewPage<TarefaDTO> tarefas = TiposTarefa.CADASTRAR_RESULTADO_CT.getConfiguracao()
				.listarTarefa()
				.comStatus(Arrays.asList(StatusTarefa.ABERTA,StatusTarefa.ATRIBUIDA))
				.comParceiros(parceiros)
				.comPaginacao(pageRequest)
				.apply();
		
		tarefas.setSerializationView(TiposTarefa.CADASTRAR_RESULTADO_CT.getConfiguracao().getJsonView());
		
		return tarefas;
	}
	
	@Override
	public PedidoExame save(PedidoExame pedidoExame) {
	
		if (pedidoExame.getSolicitacao().getResolverDivergencia() != null 
				&& pedidoExame.getSolicitacao().getResolverDivergencia()) {
			if (pedidoExame.getDataAberturaResolverDivergencia() == null && 
					pedidoExame.getStatusPedidoExame().getId().equals(StatusPedidosExame.AGUARDANDO_AMOSTRA)) {				
				throw new ValidationException("erro.validacao", Arrays.asList(
						new CampoMensagem("dataAberturaResolverDivergencia", AppUtil.getMensagem(messageSource, 
								"javax.validation.constraints.NotNull.message"))));				
			}
		}
		
		return super.save(pedidoExame);
	}

	@Override
	@Transactional(readOnly=true)
	public List<PedidoExame> listarPedidosExameParaResolverDivergencia(Long idDoador) {
		final Doador doador = doadorService.obterDoador(idDoador);
		
		final List<PedidoExame> lista = pedidoExameRepository.listarPedidosExameParaResolverDivergencia(doador.getId());
		/*lista.forEach(pedido -> {
			final Exame exame = pedido.getExame();
			if (exame instanceof ExamePaciente) {
				((ExamePaciente)exame).getPaciente().getRmr();
			}
			else {
				((IExameDoador)exame).getDoador().getId();
			}
		});*/
		
		return lista;
	}
	
	
	@Override
	public Paginacao<PedidoExameDoadorNacionalVo> listarPedidosDeExameNacional(Long idDoador, Long idPaciente, Long idBusca, Boolean exibirHistorico, Long filtroFaseTipoExame, PageRequest pageable) {
		List<Integer> filtrarSolicitacaoPelosStatus = new ArrayList<>();		

		List<Long> filtrarTarefaPelosTipos = Arrays.asList(TiposTarefa.CADASTRAR_RESULTADO_EXAME.getId(),TiposTarefa.RESULTADO_EXAME_NACIONAL.getId());
		
		List<Long> filtrarSolicitacaoPelosTipos = new ArrayList<>();
		if(filtroFaseTipoExame == TODOS_TIPO_EXAME) {
			filtrarSolicitacaoPelosTipos = Arrays.asList(TiposSolicitacao.FASE_2.getId(), TiposSolicitacao.FASE_3.getId());
		}
		else {
			filtrarSolicitacaoPelosTipos.add(filtroFaseTipoExame);
		}
		
		filtrarSolicitacaoPelosStatus.add(StatusSolicitacao.ABERTA.getId());
		if(Boolean.TRUE.equals(exibirHistorico)){
			filtrarSolicitacaoPelosStatus.add(StatusSolicitacao.CONCLUIDA.getId());
			filtrarSolicitacaoPelosStatus.add(StatusSolicitacao.CANCELADA.getId());
		}
		
		List<PedidoExameDoadorNacionalVo> pedidos = pedidoExameRepository.listarPedidosDeExameNacional(idDoador, idPaciente, idBusca, 
				filtrarSolicitacaoPelosStatus, filtrarSolicitacaoPelosTipos, filtrarTarefaPelosTipos, pageable);
		
		return  new Paginacao<>(pedidos, pageable, pedidos.size());
	}

	@Override
	public Paginacao<PedidoExameDoadorInternacionalVo> listarAndamentoPedidosDeExameInternacional(Long idBusca, Boolean exibirHistorico, Long filtroFaseTipoExame, PageRequest pageable) {
		List<Integer> filtrarSolicitacaoPelosStatus = new ArrayList<>();	

		List<Long> filtrarTarefaPelosTipos = new ArrayList<>();
		
		List<Long> filtrarSolicitacaoPelosTipos = new ArrayList<>();
		if(TODOS_TIPO_EXAME.equals(filtroFaseTipoExame)) {
			filtrarSolicitacaoPelosTipos = Arrays.asList(TiposSolicitacao.FASE_2_INTERNACIONAL.getId(), TiposSolicitacao.FASE_3_INTERNACIONAL.getId());
			filtrarTarefaPelosTipos.add(TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.getId());
			filtrarTarefaPelosTipos.add(TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.getId());
			filtrarTarefaPelosTipos.add(TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.getId());
		}
		else if(TiposSolicitacao.FASE_2_INTERNACIONAL.getId().equals(filtroFaseTipoExame)) {
			filtrarSolicitacaoPelosTipos.add(TiposSolicitacao.FASE_2_INTERNACIONAL.getId());
			filtrarTarefaPelosTipos.add(TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL.getId());
		}
		else if(TiposSolicitacao.FASE_3_INTERNACIONAL.getId().equals(filtroFaseTipoExame)) {
			filtrarSolicitacaoPelosTipos.add(TiposSolicitacao.FASE_3_INTERNACIONAL.getId());	
			filtrarTarefaPelosTipos.add(TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL.getId());
			filtrarTarefaPelosTipos.add(TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.getId());
		}
		else {
			filtrarSolicitacaoPelosTipos.add(TiposSolicitacao.FASE_3_INTERNACIONAL.getId());
			filtrarTarefaPelosTipos.add(TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.getId());
			filtrarTarefaPelosTipos.add(TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.getId());
		}
		
		filtrarSolicitacaoPelosStatus.add(StatusSolicitacao.ABERTA.getId());
		if(Boolean.TRUE.equals(exibirHistorico)){
			filtrarSolicitacaoPelosStatus.add(StatusSolicitacao.CONCLUIDA.getId());
			filtrarSolicitacaoPelosStatus.add(StatusSolicitacao.CANCELADA.getId());
		}
		
		List<PedidoExameDoadorInternacionalVo> pedidos = pedidoExameRepository.listarPedidosDeExameInternacional(idBusca, 
				filtrarSolicitacaoPelosStatus, filtrarSolicitacaoPelosTipos, filtrarTarefaPelosTipos, pageable);
		
		return  new Paginacao<>(pedidos, pageable, pedidos.size());
	}
	
	@Override
	public Paginacao<PedidoExameDoadorNacionalVo> listarAndamentoDePedidosExamesPorDoador(Long idDoador, PageRequest pageable) {
		return listarPedidosDeExameNacional(idDoador, null, null, true, TODOS_TIPO_EXAME, pageable);
	}


	/**
	 * @param doadorInternacionalService the doadorInternacionalService to set
	 */
	@Autowired
	public void setDoadorInternacionalService(IDoadorInternacionalService doadorInternacionalService) {
		this.doadorInternacionalService = doadorInternacionalService;
	}

	/**
	 * @param exameService the exameService to set
	 */
	@Autowired
	public void setExameService(IExamePacienteService exameService) {
		this.exameService = exameService;
	}

	/**
	 * @param genotipoService the genotipoService to set
	 */
	@Autowired
	public void setGenotipoService(IGenotipoPacienteService genotipoService) {
		this.genotipoService = genotipoService;
	}

	/**
	 * @param genotipoDoadorInternacionalService the genotipoDoadorInternacionalService to set
	 */
	@Autowired
	public void setGenotipoDoadorInternacionalService(
			IGenotipoDoadorService<ExameDoadorInternacional> genotipoDoadorInternacionalService) {
		this.genotipoDoadorInternacionalService = genotipoDoadorInternacionalService;
	}

	/**
	 * @param genotipoCordaoInternacionalService the genotipoCordaoInternacionalService to set
	 */
	@Autowired
	public void setGenotipoCordaoInternacionalService(
			IGenotipoDoadorService<ExameCordaoInternacional> genotipoCordaoInternacionalService) {
		this.genotipoCordaoInternacionalService = genotipoCordaoInternacionalService;
	}

	/**
	 * @param pedidoExameRepository the pedidoExameRepository to set
	 */
	@Autowired
	public void setPedidoExameRepository(IPedidoExameRepository pedidoExameRepository) {
		this.pedidoExameRepository = pedidoExameRepository;
	}

	/**
	 * @param pacienteService the pacienteService to set
	 */
	@Autowired
	public void setPacienteService(IPacienteService pacienteService) {
		this.pacienteService = pacienteService;
	}

	/**
	 * @param doadorService the doadorService to set
	 */
	@Autowired
	public void setDoadorService(IDoadorService doadorService) {
		this.doadorService = doadorService;
	}

	/**
	 * @param locusService the locusService to set
	 */
	@Autowired
	public void setLocusService(ILocusService locusService) {
		this.locusService = locusService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @param buscaService the buscaService to set
	 */
	@Autowired
	public void setBuscaService(IBuscaService buscaService) {
		this.buscaService = buscaService;
	}

	/**
	 * @param relatorioService the relatorioService to set
	 */
	@Autowired
	public void setRelatorioService(IRelatorioService relatorioService) {
		this.relatorioService = relatorioService;
	}

	/**
	 * @param tipoExameService the tipoExameService to set
	 */
	@Autowired
	public void setTipoExameService(ITipoExameService tipoExameService) {
		this.tipoExameService = tipoExameService;
	}

	/**
	 * @param matchService the matchService to set
	 */
	@Autowired
	public void setMatchService(IMatchService matchService) {
		this.matchService = matchService;
	}

	/**
	 * @param exameDoadorService the exameDoadorService to set
	 */
	@Autowired
	public void setExameDoadorService(IExameDoadorService<ExameDoadorInternacional> exameDoadorService) {
		this.exameDoadorService = exameDoadorService;
	}

	/**
	 * @param pedidoIdmService the pedidoIdmService to set
	 */
	@Autowired
	public void setPedidoIdmService(IPedidoIdmService pedidoIdmService) {
		this.pedidoIdmService = pedidoIdmService;
	}

	/**
	 * @param laboratorioService the laboratorioService to set
	 */
	@Autowired
	public void setLaboratorioService(ILaboratorioService laboratorioService) {
		this.laboratorioService = laboratorioService;
	}

	/**
	 * @param buscaChecklistService the buscaChecklistService to set
	 */
	@Autowired
	public void setBuscaChecklistService(IBuscaChecklistService buscaChecklistService) {
		this.buscaChecklistService = buscaChecklistService;
	}
	
	@Override
	public void atualizarPedidosInvoiceConcilidados(List<PedidosPacienteInvoiceDTO> pedidosDto) {
		
		pedidosDto.forEach(pedidoDto -> {
			
			Solicitacao solicitacao = solicitacaoService.obterSolicitacaoPorPedidoExame(pedidoDto.getIdPedido());
			TiposServico tipoServico = TiposServico.valueOf(pedidoDto.getIdTipoServico());
			
			tipoServico.getConfiguracao().atualizarPagamento()
			.comMatch(solicitacao.getMatch().getId())
			.comObjetoRelacionado(pedidoDto.getIdPedido())
			.comRegistro(solicitacao.getMatch().getDoador().getRegistroOrigem().getId())
			.comStatusPagamentos(StatusPagamentos.PAGO)
			.apply();

		});
		
	}
	
	private void criarPagamentosPedidoExameCtInternacional(PedidoExame pedidoExame) {
		
		Pagamento pagamento = TiposServico.PEDIDO_EXAME_CT
				.getConfiguracao().obterPagamento()
				.comMatch(pedidoExame.getSolicitacao().getMatch().getId())
				.comObjetoRelacionado(pedidoExame.getId())
				.comRegistro(pedidoExame.getSolicitacao().getMatch().getDoador().getRegistroOrigem().getId())
				.apply();
		if (pagamento != null) {
			throw new BusinessException("erro.mensagem.pagamento.ja.existe");
		}
		TiposServico.PEDIDO_EXAME_CT.getConfiguracao().criarPagamento()
			.comMatch(pedidoExame.getSolicitacao().getMatch().getId())
			.comObjetoRelacionado(pedidoExame.getId())
			.comRegistro(pedidoExame.getSolicitacao().getMatch().getDoador().getRegistroOrigem().getId())
			.apply();
		
		TiposServico.AMOSTRA_CT.getConfiguracao().criarPagamento()
			.comMatch(pedidoExame.getSolicitacao().getMatch().getId())
			.comObjetoRelacionado(pedidoExame.getId())
			.comRegistro(pedidoExame.getSolicitacao().getMatch().getDoador().getRegistroOrigem().getId())
			.apply();		
	}

	@Override
	public String obterIdRegistroDoadorInternacionalPorPedidoExameId(Long idPedidoExame) {
		return pedidoExameRepository.obterIdRegistroDoadorInternacionalPorPedidoExameId(idPedidoExame);
	}

	@Override
	public String obterIdRegistroDoadorInternacionalPorPedidoIdmId(Long idPedidoIdm) {
		return pedidoExameRepository.obterIdRegistroDoadorInternacionalPorPedidoExameId(idPedidoIdm);
	}

	/**
	 * @param exameCordaoService the exameCordaoService to set
	 */
	@Autowired
	public void setExameCordaoService(IExameDoadorService<ExameCordaoInternacional> exameCordaoService) {
		this.exameCordaoService = exameCordaoService;
	}

	@Override
	public void criarCheckListExameSemResultadoMais30Dias(Long idTarefa) {
		TarefaDTO tarefa = tarefaFeign.obterTarefa(idTarefa);
		Match match = null;
		if(TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.getId().equals(tarefa.getTipo().getId())) {
			match = matchService.obterMatchPorPedidoIdm(tarefa.getRelacaoEntidade());
		}else {
			match = matchService.obterMatchPorPedidoExame(tarefa.getRelacaoEntidade());
		}
		if(!buscaChecklistService.existeBuscaChecklistEmAberto(match.getBusca().getId(),  TiposBuscaChecklist.EXAME_SEM_RESULTADO_A_MAIS_DE_30_DIAS.getId(), match.getId())){
			buscaChecklistService.criarItemCheckList(match.getBusca(), match, TiposBuscaChecklist.EXAME_SEM_RESULTADO_A_MAIS_DE_30_DIAS);					
		}
	}
}
