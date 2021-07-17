package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDate;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.NotificacaoDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.INotificacaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.DestinoColeta;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.RecebimentoColeta;
import br.org.cancer.redome.workup.model.domain.CategoriasNotificacao;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IRecebimentoColetaRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IPedidoColetaService;
import br.org.cancer.redome.workup.service.IRecebimentoColetaService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.AppUtil;


/**
 * Classe de implementação de métodos de negócio para recebimento de coleta.
 * @author ergomes
 *
 */
@Service
@Transactional
public class RecebimentoColetaService  extends AbstractService<RecebimentoColeta, Long>  implements IRecebimentoColetaService {

	@Autowired
	private IRecebimentoColetaRepository recebimentoColetaRepository;

	@Autowired
	@Lazy(true)
	private IPedidoColetaService pedidoColetaService;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	@Lazy(true)
	private INotificacaoFeign notificacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ILogEvolucaoFeign logEvolucaoFeign;

	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;

	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public IRepository<RecebimentoColeta, Long> getRepository() {
		return recebimentoColetaRepository;
	}
	
	@Override
	public void salvarRecebimentoColeta(RecebimentoColeta recebimento) throws JsonProcessingException {

		/* ACHO QUE FIZ TUDO DO JEITO MELHOR, MEIO TORTO, TALVEZ, MAS TENHO TENTADO DA MANEIRA MAIS BONITA QUE SEI. */
		
		/* < # BRUNO # > O CÓDIGO COMENTADO SÃO DOS ANTIGOS SERVIÇOS DO RECEBIMENTO DE COLETA */
		
		PedidoColeta pedidoColeta = pedidoColetaService.obterPedidoColetaPorId(recebimento.getPedidoColeta().getId());
		
		/* < # BRUNO # > SUBSTITUIR PELA PRÓXIMA FASE DA SOLITAÇÃO */
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoColeta.getSolicitacao(), 
				FasesWorkup.AGUARDANDO_RECEBIMENTO_COLETA.getId());
		
		this.validarRecebimentoColeta(recebimento);
		
		save(recebimento);
		
		/* < # BRUNO # > DEIXEI ESSES IF'S POIS SEI QUE VAI PRECISAR  */
		if(DestinoColeta.INFUSAO.equals(recebimento.getDestinoColeta().getId())) {
		}else if(DestinoColeta.CONGELAMENTO.equals(recebimento.getDestinoColeta().getId())) {
		}else {
		}
		
		this.fecharTarefaInformarRecebimentoColeta(pedidoColeta.getId(), solicitacao);
		
		/* < # BRUNO # DEFINIR A PROXIMA TAREFA > */
		criarTarefa_PROXIMA_TAREFA(pedidoColeta.getId(), solicitacao);
		
		this.criarLogEvolucao(TipoLogEvolucao.RECEBIMENTO_COLETA_PARA_DOADOR, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());
		criarNotificacaoParaMedicoTransplantador(solicitacao);
	}

	private void criarNotificacaoParaMedicoTransplantador(SolicitacaoWorkupDTO solicitacao) {
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacaoDoador = solicitacao.getMatch().getDoador().getIdentificacao();
		
		final CategoriasNotificacao categoria = CategoriasNotificacao.RECEBIMENTO_COLETA;
		NotificacaoDTO notificacao = NotificacaoDTO.builder()
				.rmr(rmr)
				.descricao(AppUtil.getMensagem(messageSource, "recebimento.coleta.centro_transplantador.notificacao", identificacaoDoador, rmr) )
				.categoriaId(categoria.getId())
				.idPerfil(categoria.getPerfil() != null ? categoria.getPerfil().getId() : null)
				.lido(false)
				.build();
		
		notificacaoFeign.criarNotificacao(notificacao);
	}

	private void criarLogEvolucao(TipoLogEvolucao tipoLogEvolucao,Long rmr, DoadorDTO doador) {
		
		String[] parametros = {doador.getIdentificacao()};
		
		logEvolucaoFeign.criarLogEvolucao(CriarLogEvolucaoDTO.builder()
				.tipo(tipoLogEvolucao.name())
				.rmr(rmr)
				.parametros(parametros )
				.build());
	}

	private void fecharTarefaInformarRecebimentoColeta(Long idPedidoColeta, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		Long idUsuarioLogado = usuarioService.obterIdUsuarioLogado();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.INFORMAR_RECEBIMENTO_COLETA.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(idUsuarioLogado)
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoColeta)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);
	}
	
	private void criarTarefa_PROXIMA_TAREFA(Long idPedidoColeta, SolicitacaoDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_RECEBIMENTO_COLETA.getId()))
				.perfilResponsavel(Perfis.ANALISTA_LOGISTICA.getId())
				.status(StatusTarefa.ABERTA.getId())
				.relacaoEntidade(idPedidoColeta)
				.build(); 

		tarefaHelper.criarTarefa(tarefa);		
	}

	private void validarRecebimentoColeta(RecebimentoColeta recebimento) {
		
		validarValorNulo("id", recebimento.getFonteCelula().getId());
		validarValorNulo("numeroDeBolsas", recebimento.getNumeroDeBolsas());
		validarValorNulo("totalTotalTcn", recebimento.getTotalTotalTcn());
		validarValorNulo("volume", recebimento.getVolume());

		validarValorNulo("produtoColetadoDeAcordo", recebimento.getProdutoColetadoDeAcordo());
		if(!recebimento.getProdutoColetadoDeAcordo()){
			validarValorNulo("motivoProdutoColetadoIncorreto", recebimento.getMotivoProdutoColetadoIncorreto());
		}
		
		validarValorNulo("identificacaoDoadorConfere", recebimento.getIdentificacaoDoadorConfere());
		if(!recebimento.getIdentificacaoDoadorConfere()){
			validarValorNulo("motivoIdentificacaoDoadorIncorreta", recebimento.getMotivoIdentificacaoDoadorIncorreta());
		}

		validarValorNulo("produdoAcondicionadoCorreto", recebimento.getProdudoAcondicionadoCorreto());
		if(!recebimento.getProdudoAcondicionadoCorreto()){
			validarValorNulo("motivoProdudoAcondicionadoIncorreto", recebimento.getMotivoProdudoAcondicionadoIncorreto());
		}

		validarValorNulo("produdoEventoAdverso", recebimento.getProdudoEventoAdverso());
		if(recebimento.getProdudoEventoAdverso()){
			validarValorNulo("descrevaProdudoEventoAdverso", recebimento.getDescrevaProdudoEventoAdverso());
		}

		validarValorNulo("dataRecebimento", recebimento.getDataRecebimento());
		validarDataMenor("dataRecebimento", recebimento.getDataRecebimento(), "Data Atual", LocalDate.now());
		validarDataMenor("dataRecebimento", recebimento.getDataRecebimento(), "Data da Coleta", recebimento.getPedidoColeta().getDataColeta().toLocalDate());

		validarValorNulo("id", recebimento.getDestinoColeta().getId());
		
		if(recebimento.getDestinoColeta().getId().equals(DestinoColeta.INFUSAO)){
			validarValorNulo("dataInfusao", recebimento.getDataInfusao());
			validarDataMenor("dataInfusao", recebimento.getDataInfusao(), "Data Atual", LocalDate.now());
			validarDataMenor("dataInfusao", recebimento.getDataInfusao(), "Data da Recebimento", recebimento.getDataRecebimento());
		}else if(recebimento.getDestinoColeta().getId().equals(DestinoColeta.CONGELAMENTO)){
			validarValorNulo("dataPrevistaInfusao", recebimento.getDataInfusao());
			validarDataMenor("dataPrevistaInfusao", recebimento.getDataInfusao(), "Data Atual", LocalDate.now());
			validarDataMenor("dataPrevistaInfusao", recebimento.getDataInfusao(), "Data da Recebimento", recebimento.getDataRecebimento());
			validarValorNulo("justificativaCongelamento", recebimento.getJustificativaCongelamento());
         }else if(recebimento.getDestinoColeta().getId().equals(DestinoColeta.DESCARTE)){
 			validarValorNulo("dataDescarte", recebimento.getDataInfusao());
 			validarDataMenor("dataDescarte", recebimento.getDataInfusao(), "Data Atual", LocalDate.now());
 			validarDataMenor("dataDescarte", recebimento.getDataInfusao(), "Data da Recebimento", recebimento.getDataRecebimento());
 			validarValorNulo("justificativaDescarte", recebimento.getJustificativaDescarte());
          }
	}

}

//@Override
//public void registrarRecebimentoColeta(Long idDoador, LocalDateTime dataRecebimento) {
//	PedidoColeta pedidoColeta = pedidoColetaService.obterPedidoColetaPor(idDoador);
//	if(pedidoColeta == null){
//		throw new BusinessException("erro.resultado.coleta.pedido_coleta_nao_localizado");
//	}
//	
//	RecebimentoColeta recebimento = new RecebimentoColeta();
//	recebimento.setDataRecebimento(dataRecebimento);
//	recebimento.setPedidoColeta(pedidoColeta);
//	recebimento.setFonteCelula(obterFonteCelula(pedidoColeta));
//	recebimento = save(recebimento);
//	criarTarefaRecebimento(recebimento);
//}
//
///**
// * Obtém a fonte de célula utilizada para coleta do doador.
// * Em caso de cordão, como não passa pelo procedimento de workup, como a medula, 
// * a fonte de célula é setado como o próprio cordão.
// * 
// * @param pedidoColeta pedido de coleta.
// * @return fonte de célula de acordo com o procedimento envolvendo o pedido informado.
// */
//private FonteCelula obterFonteCelula(PedidoColeta pedidoColeta) {
//	PedidoWorkup workup = 
//			pedidoWorkupService.obterPedidoWorkup(
//					pedidoColeta.getSolicitacao().getId(), StatusPedidosWorkup.REALIZADO);
//	
//	if(workup != null){
//		return workup.getFonteCelula();
//	}
//	return new FonteCelula(FontesCelulas.CORDAO_UMBILICAL.getFonteCelulaId());
//}
//
//private void criarTarefaRecebimento(RecebimentoColeta recebimento) {
//	final Paciente paciente = 
//			pedidoColetaService.obterPacientePorPedidoColeta(recebimento.getPedidoColeta());
//	final Busca buscaAtiva = 
//			buscaService.obterBuscaAtivaPorRmr(paciente.getRmr());
//	
//	TiposTarefa.CADASTRAR_RECEBIMENTO_COLETA.getConfiguracao()
//		.criarTarefa()
//		.comRmr(paciente.getRmr())
//		.comObjetoRelacionado(recebimento.getId())
//		.comParceiro(buscaAtiva.getCentroTransplante().getId())
//		.apply();
//}
//
//@Override
//public IRepository<RecebimentoColeta, Long> getRepository() {
//	return recebimentoColetaRepository;
//}
//
//@Override
//public RecebimentoColeta obterRecebimentoColetaPorId(Long id) {
//	RecebimentoColeta recebimento = recebimentoColetaRepository.findById(id).get();
//	return recebimento;
//}
//
//@CreateLog
//@Override
//public void salvarRecebimento(RecebimentoColeta recebimento) {
//	RecebimentoColeta recebimentoTemp = recebimentoColetaRepository.findById(recebimento.getId()).get();
//	recebimentoTemp.setRecebeuColeta(recebimento.getRecebeuColeta());
//	
//	if(!recebimentoTemp.getRecebeuColeta()){
//		recebimentoTemp.setDestinoColeta(null);
//	}
//	else{
//		pedidoColetaService.fecharPedidoColeta(recebimentoTemp.getPedidoColeta().getId());
//		recebimentoTemp.setDestinoColeta(recebimento.getDestinoColeta());
//		if(DestinoColeta.CONGELAMENTO.equals(recebimentoTemp.getDestinoColeta().getId())){
//			criarTarefaCongelamento(recebimentoTemp);
//		}
//		else if(DestinoColeta.INFUSAO.equals(recebimentoTemp.getDestinoColeta().getId())){				
//			buscaService.encerrarBusca(recebimentoTemp.getPedidoColeta().getSolicitacao().getMatch().getBusca().getId());
//		}
//	}
//	fecharTarefaRecebimento(recebimentoTemp);
//	
//	save(recebimentoTemp);
//}
//
//private void criarTarefaCongelamento(RecebimentoColeta recebimento) {
//	TiposTarefa.CADASTRAR_RECEBIMENTO_COLETA.getConfiguracao().criarTarefa()
//		.comRmr(rmr)
//		.comParceiro(idCentroColeta)
//		.comObjetoRelacionado(recebimento.getId())
//		.apply();
//	
//}
//
//private void fecharTarefaRecebimento(RecebimentoColeta recebimento) {
//	final Paciente paciente = 
//			pedidoColetaService.obterPacientePorPedidoColeta(recebimento.getPedidoColeta());
//	
//	TiposTarefa.CADASTRAR_RECEBIMENTO_COLETA.getConfiguracao()
//		.fecharTarefa()
//		.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
//		.comRmr(paciente.getRmr())
//		.comObjetoRelacionado(recebimento.getId())
//		.apply();
//}
//
//@Override
//protected List<CampoMensagem> validateEntity(RecebimentoColeta recebimento) {
//	List<CampoMensagem> erros = super.validateEntity(recebimento);
//	if (!erros.isEmpty()) {
//		return erros;
//	}
//
//	if(Boolean.TRUE.equals(recebimento.getRecebeuColeta())  && recebimento.getDestinoColeta() != null){
//		if(FontesCelulas.MEDULA_OSSEA.getFonteCelulaId().equals(recebimento.getFonteCelula().getId())){
//			if(recebimento.getTotalTotalTcn() == null || recebimento.getTotalTotalTcn().equals(0)){
//				CampoMensagem campoMensagem = new CampoMensagem("data", AppUtil.getMensagem(messageSource, "erro.recebimento.coleta.total"));
//				erros.add(campoMensagem);
//			}
//		}
//		else{
//			if(recebimento.getTotalTotalCd34() == null || recebimento.getTotalTotalCd34().equals(0)){
//				CampoMensagem campoMensagem = new CampoMensagem("data", AppUtil.getMensagem(messageSource, "erro.recebimento.coleta.total"));
//				erros.add(campoMensagem);
//			}
//		}
//		
//		if(DestinoColeta.INFUSAO.equals(recebimento.getDestinoColeta().getId())){
//			if(recebimento.getDataInfusao() == null){
//				CampoMensagem campoMensagem = new CampoMensagem("data", AppUtil.getMensagem(messageSource, "erro.recebimento.coleta.data_infusao"));
//				erros.add(campoMensagem);
//			}
//		}
//		else if(DestinoColeta.CONGELAMENTO.equals(recebimento.getDestinoColeta().getId())){
//			if(recebimento.getDataPrevistaInfusao() == null){
//				CampoMensagem campoMensagem = new CampoMensagem("data", AppUtil.getMensagem(messageSource, "erro.recebimento.coleta.data_prevista"));
//				erros.add(campoMensagem);
//			}
//			if(StringUtils.isEmpty(recebimento.getJustificativaCongelamento())){
//				CampoMensagem campoMensagem = new CampoMensagem("justificativa", AppUtil.getMensagem(messageSource, "erro.recebimento.coleta.justificativa_congelamento"));
//				erros.add(campoMensagem);
//			}
//		}
//		else if(DestinoColeta.DESCARTE.equals(recebimento.getDestinoColeta().getId())){
//			if(recebimento.getDataDescarte() == null){
//				CampoMensagem campoMensagem = new CampoMensagem("data", AppUtil.getMensagem(messageSource, "erro.recebimento.coleta.data_descarte"));
//				erros.add(campoMensagem);
//			}
//			if(StringUtils.isEmpty(recebimento.getJustificativaCongelamento())){
//				CampoMensagem campoMensagem = new CampoMensagem("justificativa", AppUtil.getMensagem(messageSource, "erro.recebimento.coleta.justificativa_descarte"));
//				erros.add(campoMensagem);
//			}
//		}			
//	}
//	return erros;		
//}
//
///**
// * @param pedidoColetaService the pedidoColetaService to set
// */
//@Autowired
//public void setPedidoColetaService(IPedidoColetaService pedidoColetaService) {
//	this.pedidoColetaService = pedidoColetaService;
//}
//
///**
// * @param pedidoWorkupService the pedidoWorkupService to set
// */
//@Autowired
//public void setPedidoWorkupService(IPedidoWorkupService pedidoWorkupService) {
//	this.pedidoWorkupService = pedidoWorkupService;
//}
//
///**
// * @param buscaService the buscaService to set
// */
//@Autowired
//public void setBuscaService(IBuscaService buscaService) {
//	this.buscaService = buscaService;
//}
//
//@Override
//public Paciente obterPaciente(RecebimentoColeta recebimentoColeta) {
//	return recebimentoColeta.getPedidoColeta().getSolicitacao().getMatch().getBusca().getPaciente();
//}
//
//@SuppressWarnings("rawtypes")
//@Override
//public String[] obterParametros(RecebimentoColeta recebimentoColeta) {
//	IDoador doador = (IDoador) recebimentoColeta.getPedidoColeta().getSolicitacao().getMatch().getDoador();
//	return StringUtils.split(doador.getIdentificacao().toString());
//}
//
//@Override
//public LogEvolucao criarLog(RecebimentoColeta recebimentoColeta, TipoLogEvolucao tipoLog, Perfis[] perfisExcluidos) {
//	
//	if (tipoLog == null || TipoLogEvolucao.INDEFINIDO.equals(tipoLog)) {
//		if (recebimentoColeta.getDestinoColeta() == null) {
//			tipoLog = TipoLogEvolucao.RECEBIMENTO_COLETA_MATERIAL_RECEBIDO_PARA_DOADOR;
//		}
//		else if (DestinoColeta.DESCARTE.equals(recebimentoColeta.getDestinoColeta().getId())) {
//			tipoLog = TipoLogEvolucao.RECEBIMENTO_COLETA_MATERIAL_RECEBIDO_DESCARTADO_PARA_DOADOR;
//		}
//		else if (DestinoColeta.CONGELAMENTO.equals(recebimentoColeta.getDestinoColeta().getId())) {
//			tipoLog = TipoLogEvolucao.RECEBIMENTO_COLETA_MATERIAL_RECEBIDO_CONGELADO_PARA_DOADOR;
//		}
//		else if (DestinoColeta.INFUSAO.equals(recebimentoColeta.getDestinoColeta().getId())) {
//			tipoLog = TipoLogEvolucao.RECEBIMENTO_COLETA_MATERIAL_RECEBIDO_INFUNDIDO_PARA_DOADOR;
//		}
//	}
//	
//	return super.criarLog(recebimentoColeta, tipoLog, perfisExcluidos);
//}



