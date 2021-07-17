package br.org.cancer.modred.model.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

import br.org.cancer.modred.controller.view.AnaliseMedicaView;
import br.org.cancer.modred.controller.view.AvaliacaoCamaraTecnicaView;
import br.org.cancer.modred.controller.view.AvaliacaoNovaBuscaView;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.controller.view.AvaliacaoResultadoWorkupView;
import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.EncontrarCentroTransplanteView;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.PedidoTransferenciaCentroView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.controller.view.TarefaLogisticaView;
import br.org.cancer.modred.controller.view.TentativaContatoDoadorView;
import br.org.cancer.modred.controller.view.TransportadoraView;
import br.org.cancer.modred.helper.ConfiguracaoGrupoTipoTarefa;
import br.org.cancer.modred.helper.ConfiguracaoTipoTarefa;
import br.org.cancer.modred.helper.IConfiguracaoProcessServer;
import br.org.cancer.modred.helper.LogisticaOrdenacao;
import br.org.cancer.modred.helper.PedidoEnriquecimentoComparator;
import br.org.cancer.modred.helper.PedidoWorkupComparator;
import br.org.cancer.modred.model.AnaliseMedica;
import br.org.cancer.modred.model.AtributoOrdenacao;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.AvaliacaoCamaraTecnica;
import br.org.cancer.modred.model.AvaliacaoNovaBusca;
import br.org.cancer.modred.model.AvaliacaoPedidoColeta;
import br.org.cancer.modred.model.AvaliacaoPrescricao;
import br.org.cancer.modred.model.AvaliacaoWorkupDoador;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorNaoContactado;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoColeta;
import br.org.cancer.modred.model.PedidoContatoSms;
import br.org.cancer.modred.model.PedidoEnriquecimento;
import br.org.cancer.modred.model.PedidoEnvioEmdis;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.PedidoIdm;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.PedidoTransferenciaCentro;
import br.org.cancer.modred.model.PedidoTransporte;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.model.ResultadoWorkup;
import br.org.cancer.modred.model.TentativaContatoDoador;

/**
 * Enum para representar os tipos de tarefas previstas para os processos da plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */
public enum TiposTarefa {
	
	ROLLBACK_ATRIBUICAO(43L),
	TIMEOUT_CONFERENCIA_EXAME(7L),
	LIBERAR_ACOMPANHAR_BUSCA(9L),
	TIMEOUT(14L),
	WORKUP_FOLLOW_UP(31L),
	RESULTADO_WORKUP_FOLLOW_UP(34L),
	RESULTADO_EXAME_INTERNACIONAL_FOLLOW_UP(64L),
	RESULTADO_EXAME_CT_INTERNACIONAL_15DIAS_FOLLOW_UP(66L),
	RESULTADO_EXAME_CT_INTERNACIONAL_7DIAS_FOLLOW_UP(67L),
	RESULTADO_IDM_INTERNACIONAL_15DIAS_FOLLOW_UP(69L),
	RESULTADO_IDM_INTERNACIONAL_7DIAS_FOLLOW_UP(70L),
	CHECKLIST_BUSCA_EXAME_FOLLOWUP_30D(88L),
	CHECKLIST_MATCH_EXAME_FOLLOWUP_30D(89L),
	VALIDAR_TENTATIVA_CONTATO_FOLLOW_UP(96L),
	DESATRIBUIR_USUARIO_AGENDAMENTO_FOLLOW_UP(104L),
	VALIDAR_TENTATIVA_CONTATO_SMS_FOLLOW_UP(97L),	

	AVALIAR_PACIENTE(5L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.AVALIACAO_PACIENTE_PARA_BUSCA)
					.para(Perfis.MEDICO_AVALIADOR)
					.iniciarProcesso()
					.relacionadoCom(Avaliacao.class)
					.comParceiro(CentroTransplante.class);
		}
	},

	AVALIAR_EXAME_HLA(6L) {
	@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.CONFERENCIA_EXAMES)
					.iniciarProcesso()
					.para(Perfis.AVALIADOR_EXAME_HLA)
					.relacionadoCom(ExamePaciente.class)
					.jsonView(ExameView.ListaExame.class)
					.timeout(TIMEOUT_CONFERENCIA_EXAME);
		}
	},

	RECEBER_PACIENTE(8L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.iniciarProcesso()
					.relacionadoCom(Busca.class)
					.timeout(LIBERAR_ACOMPANHAR_BUSCA);
		}
	},
	
	ENRIQUECER_DOADOR(92L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ENRIQUECEDOR)
					.comOrdenacao(PedidoEnriquecimentoComparator.class)
					.relacionadoCom(PedidoEnriquecimento.class)
					.jsonView(BuscaView.Enriquecimento.class)
					.timeoutDefault();
		}
	},

	CONTACTAR_FASE_2(12L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_CONTATO_FASE2)
					.jsonView(DoadorView.ContatoFase2.class)
					.comOFollowUp(VALIDAR_TENTATIVA_CONTATO_FOLLOW_UP)
					.comOFollowUp(DESATRIBUIR_USUARIO_AGENDAMENTO_FOLLOW_UP)	
					.relacionadoCom(TentativaContatoDoador.class).timeoutDefault();
		}
	},

	CONTACTAR_FASE_3(13L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_CONTATO_FASE3)
					.jsonView(DoadorView.ContatoFase2.class)
					.comOFollowUp(VALIDAR_TENTATIVA_CONTATO_FOLLOW_UP)
					.comOFollowUp(DESATRIBUIR_USUARIO_AGENDAMENTO_FOLLOW_UP)
					.relacionadoCom(TentativaContatoDoador.class).timeoutDefault();
	
		}
	},
	
	CONTATO_DOADOR(91L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.relacionadoCom(TentativaContatoDoador.class)
					.agrupando(	TiposTarefa.CONTACTAR_FASE_2, 
								TiposTarefa.CONTACTAR_FASE_3 )
					.jsonView(TentativaContatoDoadorView.ListarTarefas.class);
		}
	},
	
	VERIFICAR_STATUS_SMS_ENVIADO(100L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA);
		}		
	}, 

	SMS(15L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA)
					.relacionadoCom(PedidoContatoSms.class);

		}
	},

	CONTATO_HEMOCENTRO(20L),

	RESPONDER_PENDENCIA(27L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.AVALIACAO_PACIENTE_PARA_BUSCA)
					.relacionadoCom(Pendencia.class);
	
		}
	},

	PEDIDO_WORKUP(30L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP)
					.relacionadoCom(PedidoWorkup.class).comOFollowUp(WORKUP_FOLLOW_UP)
					.jsonView(PedidoWorkupView.AgendamentoWorkup.class);
	
		}
	},

	SUGERIR_DATA_WORKUP(32L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
		return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR)
				.relacionadoCom(PedidoWorkup.class)
				.comParceiro(CentroTransplante.class)
				.comOrdenacao(PedidoWorkupComparator.class)
				.jsonView(PedidoWorkupView.SugerirDataWorkup.class)
				.timeoutDefault();
		}
	},

	AVALIAR_RESULTADO_WORKUP_NACIONAL(33L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_TRANSPLANTADOR)
					.comParceiro(CentroTransplante.class)
					.relacionadoCom(ResultadoWorkup.class)
					.jsonView(AvaliacaoResultadoWorkupView.ListarAvaliacao.class);
		}
	},

	AVALIAR_RESULTADO_WORKUP_INTERNACIONAL(222L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_TRANSPLANTADOR)
					.comParceiro(CentroTransplante.class)
					.relacionadoCom(ResultadoWorkup.class)
					.jsonView(AvaliacaoResultadoWorkupView.ListarAvaliacao.class);
		}
	},
	
	INFORMAR_RESULTADO_WORKUP_NACIONAL(35L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDIDO_CENTRO_COLETA)
					.relacionadoCom(PedidoWorkup.class)
					.timeoutDefault()					
					.comOFollowUp(RESULTADO_WORKUP_FOLLOW_UP);
		}
	},
	INFORMAR_LOGISTICA_DOADOR_WORKUP(36L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_LOGISTICA)
					.relacionadoCom(PedidoLogistica.class);
		}
	},

	PEDIDO_COLETA(40L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP)
					.relacionadoCom(PedidoColeta.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa()
					.jsonView(PedidoColetaView.AgendamentoColeta.class);
		}
	},
	
	AVALIAR_PEDIDO_COLETA(37L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_REDOME)
					.relacionadoCom(AvaliacaoPedidoColeta.class).jsonView(BuscaView.AvaliacaoPedidoColeta.class)
					.timeoutDefault();
		}
	},

	ANALISE_MEDICA_DOADOR_COLETA(38L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_REDOME)
					.relacionadoCom(AvaliacaoWorkupDoador.class).jsonView(BuscaView.AvaliacaoWorkupDoador.class)
					.timeoutDefault();
		}
	},

//	RESPONDER_PEDIDO_ADICIONAL(39L) {
//		@Override
//		protected IConfiguracaoProcessServer buildConfiguracao() {
//			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP)
//					.relacionadoCom(PedidoAdicionalWorkup.class);
//		}
//	},

	PEDIDO_LOGISTICA_DOADOR_COLETA(41L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA)
					.relacionadoCom(PedidoLogistica.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},

	INFORMAR_DOCUMENTACAO_MATERIAL_AEREO(42L) {

		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA)
					.relacionadoCom(PedidoLogistica.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	
	CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR(46L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP)
					.relacionadoCom(PedidoWorkup.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},

	CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA(47L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP)
					.relacionadoCom(PedidoWorkup.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},

    AVALIAR_PRESCRICAO(49L) {
	    @Override
	    protected IConfiguracaoProcessServer buildConfiguracao() {
	        return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA)
	                .para(Perfis.MEDICO_REDOME).relacionadoCom(AvaliacaoPrescricao.class)
	                .timeout(ROLLBACK_ATRIBUICAO)
	                .jsonView(AvaliacaoPrescricaoView.ListarAvaliacao.class);
	    }
    },
	
	CADASTRAR_RECEBIMENTO_COLETA(50L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA)
					.para(Perfis.CADASTRADOR_RECEBIMENTO_COLETA)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa()
					.comParceiro(CentroTransplante.class);
		}
	},
	
	CADASTRAR_PRESCRICAO_MEDULA(52L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR)
					.relacionadoCom(Prescricao.class)
					.comParceiro(CentroTransplante.class)
					.comOrdenacao(new AtributoOrdenacao("aging", false));
		}
	},
	
	ENCONTRAR_CENTRO_TRANSPLANTE(53L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.CONTROLADOR_LISTA)
					.relacionadoCom(Busca.class)
					.timeout(ROLLBACK_ATRIBUICAO)
					.jsonView(EncontrarCentroTransplanteView.ListarBuscas.class);

		}
	},
	
	PEDIDO_TRANSPORTE(55L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.TRANSPORTADORA)
					.relacionadoCom(PedidoTransporte.class)
					.jsonView(TransportadoraView.Listar.class);
					//.comParceiro(Transportadora.class);

		}
	},
	
	INFORMAR_RETIRADA_MATERIAL(56L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.TRANSPORTADORA)
					//.comParceiro(Transportadora.class)
					.relacionadoCom(PedidoTransporte.class)
					.jsonView(TransportadoraView.Listar.class);
		}
	},
	
	PEDIDO_LOGISTICA_CORDAO_NACIONAL(75L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA)
					.relacionadoCom(PedidoLogistica.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	
	LOGISTICA(59L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA)
					.relacionadoCom(PedidoLogistica.class)
					.agrupando(	TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP, 
								TiposTarefa.INFORMAR_DOCUMENTACAO_MATERIAL_AEREO, 
								TiposTarefa.PEDIDO_LOGISTICA_DOADOR_COLETA,
								TiposTarefa.PEDIDO_LOGISTICA_CORDAO_NACIONAL)
					.jsonView(TarefaLogisticaView.Listar.class)
					.comOrdenacao(LogisticaOrdenacao.class);
		}
	},
	
	PEDIDO_TRANSPORTE_ENTREGA(57L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.TRANSPORTADORA)
					.relacionadoCom(PedidoTransporte.class)
					.jsonView(TransportadoraView.Listar.class);
					//.comParceiro(Transportadora.class);
		}
	},
	PEDIDO_TRANSPORTE_RETIRADA_ENTREGA(58L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA).para(Perfis.TRANSPORTADORA)
					.relacionadoCom(PedidoTransporte.class)
					.agrupando(	TiposTarefa.PEDIDO_TRANSPORTE_ENTREGA, 
								TiposTarefa.INFORMAR_RETIRADA_MATERIAL)
					.jsonView(TransportadoraView.Listar.class);
					//.comParceiro(Transportadora.class);
		}
	},
	RECEBER_COLETA_PARA_EXAME(60L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.LABORATORIO)
					.relacionadoCom(PedidoExame.class)
					.jsonView(LaboratorioView.ListarReceberExame.class)
					.comParceiro(Laboratorio.class);
		}
	}
	,
	CADASTRAR_RESULTADO_CT(61L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.LABORATORIO)
					.relacionadoCom(PedidoExame.class)
					.jsonView(LaboratorioView.ListarReceberExame.class)
					.comOFollowUp(CHECKLIST_BUSCA_EXAME_FOLLOWUP_30D)
					.comParceiro(Laboratorio.class);
		}
	}
	,
	CADASTRAR_RESULTADO_EXAME(62L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.relacionadoCom(PedidoExame.class)
					.jsonView(LaboratorioView.ListarReceberExame.class)
					.comParceiro(Laboratorio.class);
		}
	},
	
	RESULTADO_EXAME_NACIONAL(90L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.relacionadoCom(PedidoExame.class)
					.agrupando(	TiposTarefa.CADASTRAR_RESULTADO_EXAME, 
								TiposTarefa.CADASTRAR_RESULTADO_CT )
					.jsonView(PedidoExameView.ListarTarefas.class);
		}
	},
	
	CADASTRAR_RESULTADO_EXAME_INTERNACIONAL(63L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.relacionadoCom(PedidoExame.class)
					.comOFollowUp(RESULTADO_EXAME_INTERNACIONAL_FOLLOW_UP)
					.comOFollowUp(CHECKLIST_MATCH_EXAME_FOLLOWUP_30D)
					.jsonView(DoadorView.Internacional.class);
		}
	},
	CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL(65L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.relacionadoCom(PedidoExame.class)
					.timeoutDefault()
					.comOFollowUp(RESULTADO_EXAME_CT_INTERNACIONAL_15DIAS_FOLLOW_UP)
					.comOFollowUp(CHECKLIST_MATCH_EXAME_FOLLOWUP_30D)
					.jsonView(DoadorView.Internacional.class);
		}
	},
	
	RESULTADO_EXAME_INTERNACIONAL(93L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.relacionadoCom(PedidoExame.class)
					.agrupando(	TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL, 
								TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL)
					.jsonView(DoadorView.Internacional.class);
		}
	},
	
	CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL(68L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.relacionadoCom(PedidoIdm.class)
					.timeoutDefault()
					.comOFollowUp(RESULTADO_IDM_INTERNACIONAL_15DIAS_FOLLOW_UP)
					.comOFollowUp(CHECKLIST_MATCH_EXAME_FOLLOWUP_30D)
					.jsonView(DoadorView.Internacional.class);
		}
	},
	CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL(71L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_WORKUP_INTERNACIONAL)
					.relacionadoCom(ResultadoWorkup.class)
					.timeoutDefault()
					.jsonView(PedidoWorkupView.CadastrarResultado.class);
		}
	},
	PEDIDO_COLETA_INTERNACIONAL(72L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP_INTERNACIONAL)
					.relacionadoCom(PedidoColeta.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa()
					.jsonView(PedidoColetaView.AgendamentoColeta.class);
		}
	},
	PEDIDO_WORKUP_INTERNACIONAL(73L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP_INTERNACIONAL)
					.relacionadoCom(PedidoWorkup.class).comOFollowUp(WORKUP_FOLLOW_UP).jsonView(PedidoWorkupView.AgendamentoWorkup.class);
		}
	},
	SUGERIR_DATA_COLETA(74L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
		return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR)
				.relacionadoCom(PedidoColeta.class)
				.comParceiro(CentroTransplante.class)
				.jsonView(PedidoColetaView.SugerirDataColeta.class)
				.comOrdenacao(PedidoWorkupComparator.class)
				.timeoutDefault();
		}
	},
	
	RETIRADA_CORDAO_NACIONAL(76L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.TRANSPORTADORA)
					.relacionadoCom(PedidoTransporte.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	
	ENTREGA_CORDAO_NACIONAL(77L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.TRANSPORTADORA)
					.relacionadoCom(PedidoTransporte.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	AUTORIZACAO_PACIENTE(78L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR)
					.comParceiro(CentroTransplante.class).relacionadoCom(Prescricao.class).timeout(ROLLBACK_ATRIBUICAO)
					.jsonView(PrescricaoView.AutorizacaoPacienteListar.class)					
					.naoAtribuirAoCriarNovaTarefa();
		}
	},
	RETIRADA_MATERIAL_INTERNACIONAL(81L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL)
					.jsonView(TransportadoraView.Listar.class)
					.relacionadoCom(PedidoTransporte.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	ENTREGA_MATERIAL_INTERNACIONAL(82L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL)
					.jsonView(TransportadoraView.Listar.class)
					.relacionadoCom(PedidoTransporte.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	}
	,MATERIAL_INTERNACIONAL_RETIRADA_ENTREGA(83L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL)
					.relacionadoCom(PedidoTransporte.class)
					.agrupando(	TiposTarefa.RETIRADA_MATERIAL_INTERNACIONAL, 
								TiposTarefa.ENTREGA_MATERIAL_INTERNACIONAL)
					.jsonView(TransportadoraView.Listar.class);
		}
	},
	PEDIDO_LOGISTICA_MATERIAL_INTERNACIONAL(84L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL)
					.relacionadoCom(PedidoLogistica.class).timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa()
					.jsonView(TarefaLogisticaView.Listar.class)
					.comOrdenacao(LogisticaOrdenacao.class);
		}
	},
	PEDIDO_TRANSFERENCIA_CENTRO(85L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.TRANSFERENCIA_CENTRO)
					.para(Perfis.MEDICO_AVALIADOR)
					.iniciarProcesso()
					.comParceiro(CentroTransplante.class)
					.relacionadoCom(PedidoTransferenciaCentro.class)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa()
					.jsonView(PedidoTransferenciaCentroView.ListarTarefas.class)
					.comOrdenacao(new AtributoOrdenacao("dataCriacao", false));
		}
	},
	AVALIACAO_CAMARA_TECNICA(86L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.iniciarProcesso()
					.para(TipoProcesso.AVALIACAO_CAMARA_TECNICA)
					.para(Perfis.AVALIADOR_CAMARA_TECNICA)
					.relacionadoCom(AvaliacaoCamaraTecnica.class)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa()
					.jsonView(AvaliacaoCamaraTecnicaView.ListarTarefas.class);
		}
	},
	AVALIAR_NOVA_BUSCA(87L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.AVALIACAO_PACIENTE_PARA_NOVA_BUSCA).iniciarProcesso()
					.para(Perfis.AVALIADOR_NOVA_BUSCA)
					.relacionadoCom(AvaliacaoNovaBusca.class)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa()
					.jsonView(AvaliacaoNovaBuscaView.ListarTarefas.class);
		}
	},
	CHECKLIST_EXAME_DIVERGENTE_FOLLOWUP(94L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)					
					.relacionadoCom(Match.class);
		}
	},
	ENVIAR_DADOS_PACIENTE_WMDA_FOLLOWUP(101L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)	
					.relacionadoCom(Match.class);
		}
	},
	ENVIAR_PACIENTE_PARA_EMDIS_FOLLOWUP(102L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)	
					.iniciarProcesso()
					.relacionadoCom(PedidoEnvioEmdis.class);
		}
	},
	ANALISE_MEDICA_DOADOR_CONTATO(95L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_REDOME)
					.relacionadoCom(AnaliseMedica.class)
					.jsonView(AnaliseMedicaView.ListarTarefas.class)
					.comOrdenacao(new AtributoOrdenacao("dataCriacao", false))
					.timeoutDefault();
	
		}
	},
	INATIVAR_DOADOR_NAO_CONTACTADO(99L),
	DOADOR_NAO_CONTACTADO(98L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)					
					.relacionadoCom(DoadorNaoContactado.class)										
					.comOFollowUp(INATIVAR_DOADOR_NAO_CONTACTADO);
	
		}
	},
	ENVIAR_PEDIDO_FASE2_PARA_EMDIS(103L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)					
					.relacionadoCom(PedidoExame.class);
		}
	},
	CADASTRAR_PRESCRICAO_CORDAO(105L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR)
					.relacionadoCom(Prescricao.class)
					.comParceiro(CentroTransplante.class)
					.comOrdenacao(new AtributoOrdenacao("aging", false));
		}
	},
	CADASTRAR_PRESCRICAO(106L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR)
					.relacionadoCom(Prescricao.class)
					.agrupando(	TiposTarefa.CADASTRAR_PRESCRICAO_MEDULA, 
								TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO)
					.comOrdenacao(new AtributoOrdenacao("aging", false));
		}
	},

	VERIFICAR_GERACAO_MATCH_DOADOR(107L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.relacionadoCom(Doador.class)
					.para(TipoProcesso.BUSCA);
		}
	},
	
	VERIFICAR_GERACAO_MATCH_PACIENTE(108L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.relacionadoCom(Paciente.class);
		}
	},
	CADASTRAR_FORMULARIO_DOADOR(112L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA)
					.relacionadoCom(PedidoWorkup.class)
					.para(Perfis.ANALISTA_WORKUP);
		}
	},
	CONFIRMAR_PLANO_WORKUP(117L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(Perfis.MEDICO_TRANSPLANTADOR)
					.para(TipoProcesso.BUSCA);
					
		}
	},
	WORKUP_CENTRO_TRANSPLANTE(221L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.somenteAgrupador(true)
					.para(TipoProcesso.BUSCA)
					.agrupando(TiposTarefa.CONFIRMAR_PLANO_WORKUP,
							TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL,
							TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL,
							TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL);
		}
	},
	WORKUP(113L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.somenteAgrupador(true)
					.para(TipoProcesso.BUSCA)
					.relacionadoCom(PedidoWorkup.class)
					.agrupando(TiposTarefa.CADASTRAR_FORMULARIO_DOADOR, 
							TiposTarefa.DEFINIR_CENTRO_COLETA,
							TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP);
					
		}
	},
	DEFINIR_CENTRO_COLETA(114L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.relacionadoCom(PedidoWorkup.class)
					.para(Perfis.ANALISTA_WORKUP);
		}
	},

	INFORMAR_PLANO_WORKUP_NACIONAL(115L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.relacionadoCom(PedidoWorkup.class)
					.para(Perfis.MEDIDO_CENTRO_COLETA);
		}
	},
	INFORMAR_PLANO_WORKUP_INTERNACIONAL(118L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.relacionadoCom(PedidoWorkup.class)
					.para(Perfis.ANALISTA_WORKUP_INTERNACIONAL);
		}
	};
	
	private Long id;
	private IConfiguracaoProcessServer configuracao;
	private IConfiguracaoProcessServer configuracaoDefault;

	/**
	 * Construtor.
	 * 
	 * @param id do tipo
	 */
	TiposTarefa(Long id) {
		this.id = id;
		this.configuracaoDefault = ConfiguracaoTipoTarefa.newInstance(id);
		this.configuracao = buildConfiguracao();
	}

	/**
	 * @return the id
	 */
	@JsonValue
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the perfil
	 */
	public Perfis getPerfil() {
		return configuracao.getPerfil();
	}

	/**
	 * @return the entidade
	 */
	public Class<?> getEntidade() {
		return configuracao.getEntidade();
	}

	/**
	 * @return the timeout
	 */
	public TiposTarefa getTimeout() {
		return configuracao.getTimeout();
	}

	/**
	 * @return the followUp
	 */
	public List<TiposTarefa> getFollowUp() {
		return configuracao.getFollowUp();
	}

	/**
	 * @return the jsonView
	 */
	public Class<?> getJsonView() {
		return configuracao.getJsonView();
	}

	/**
	 * @return the tipoProcesso
	 */
	public TipoProcesso getTipoProcesso() {
		return configuracao.getTipoProcesso();
	}

	/**
	 * @return the criarTarefaAtribuida
	 */
	public boolean isCriarTarefaAtribuida() {
		return configuracao.isCriarTarefaAtribuida();
	}

	/**
	 * Utilizado para criar a configuração, de acordo com o tipo de tarefa.
	 * Este método deve ser implementado, a cada novo tipo definido na configuração.
	 * Caso não seja, uma configuração default será passada.
	 * 
	 * @return as instruções para configuração da requisição ao process server.
	 */
	protected IConfiguracaoProcessServer buildConfiguracao() {
		return configuracaoDefault;
	}

	/**
	 * Método para criar o tipo enum a partir de seu valor numerico.
	 * 
	 * @param value - valor do id do tipo enum
	 * 
	 * @return TiposTarefa - a enum correspondente ou nulo caso value estaja fora do range conhecido.
	 */
	public static TiposTarefa valueOf(Long value) {
		if (value != null) {
			TiposTarefa[] values = TiposTarefa.values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].getId().equals(value)) {
					return values[i];
				}
			}
		}
		return null;
	}

	/**
	 * Método para verificar se o id está dentro do range previsto.
	 * 
	 * @return boolean - returna <b>true</b> se o id corresponde a um valor válido da enum, caso contrário, retorna <b>false</b>.
	 */
	public boolean validate() {

		TiposTarefa[] values = TiposTarefa.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtém a configuração definida para o tipo de tarefa informado.
	 * Esta configuração define quais serão as informações a serem utilizadas
	 * no process server ao criar, listar, atribuir, fechar e cancelar a tarefa deste tipo.
	 * 
	 * @return configuração definida para o tipo.
	 */
	public IConfiguracaoProcessServer getConfiguracao() {
		return getConfiguracao(id);
	}
	
	/**
	 * Obtém a configuração definida para o tipo de tarefa informado.
	 * Esta configuração define quais serão as informações a serem utilizadas
	 * no process server ao criar, listar, atribuir, fechar e cancelar a tarefa deste tipo.
	 * 
	 * @return configuração definida para o tipo.
	 */
	protected IConfiguracaoProcessServer getConfiguracao(Long idTarefa) {
		configuracao.setIdTarefa(idTarefa);
		return configuracao;
	}

}