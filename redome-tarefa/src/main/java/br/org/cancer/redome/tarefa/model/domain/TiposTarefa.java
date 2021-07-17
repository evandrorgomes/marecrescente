package br.org.cancer.redome.tarefa.model.domain;

import br.org.cancer.redome.tarefa.helper.ConfiguracaoGrupoTipoTarefa;
import br.org.cancer.redome.tarefa.helper.ConfiguracaoTipoTarefa;
import br.org.cancer.redome.tarefa.helper.IConfiguracaoProcessServer;
import br.org.cancer.redome.tarefa.helper.domain.Perfis;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorChecklistDoadorExame;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorChecklistExameDivergenteFollowup;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorDeEnvioDadosPacienteParaWmda;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorDePesquisaResultadoDoadorParaWmda;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorDesatribuirUsuarioAgendamentoContato;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorGeracaoMatchDoador;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorGeracaoMatchPaciente;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorNotificacaoCadastrarResultadoExameCtInternacional15;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorNotificacaoCadastrarResultadoExameCtInternacional7;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorNotificacaoCadastrarResultadoIDMInternacional15;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorNotificacaoCadastrarResultadoIDMInternacional7;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorResultadoExameInternacionalFollowUp;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorRollBackAtribuicao;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorTimeout;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorTimeoutAcompanhamentoBusca;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorTimeoutConferenciaExame;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorValidarPedidoContatoSmsFollowUp;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorValidarTentativasContatoFollowUp;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorVerificarStatusSmsEnviado;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorWorkupFollowUp;

/**
 * Enum para representar os tipos de tarefas previstas para os processos da plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */
public enum TiposTarefa implements ITiposTarefa {
	
	TIMEOUT_CONFERENCIA_EXAME(7L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorTimeoutConferenciaExame.class);
		}
	}, 
	NOTIFICACAO(1L),
	LIBERAR_ACOMPANHAR_BUSCA(9L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorTimeoutAcompanhamentoBusca.class);
		}
	}, 
	WORKUP_FOLLOW_UP(31L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorWorkupFollowUp.class);
		}
	},
	TIMEOUT(14L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorTimeout.class);
		}
	},
	ROLLBACK_ATRIBUICAO(41L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorRollBackAtribuicao.class);
		}
	},
	RESULTADO_EXAME_INTERNACIONAL_FOLLOW_UP(64L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorResultadoExameInternacionalFollowUp.class);
		}
	},
	RESULTADO_EXAME_CT_INTERNACIONAL_15DIAS_FOLLOW_UP(66L)  {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorNotificacaoCadastrarResultadoExameCtInternacional15.class);
		}
	},
	RESULTADO_EXAME_CT_INTERNACIONAL_7DIAS_FOLLOW_UP(67L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorNotificacaoCadastrarResultadoExameCtInternacional7.class);
		}
	},
	RESULTADO_IDM_INTERNACIONAL_15DIAS_FOLLOW_UP(69L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorNotificacaoCadastrarResultadoIDMInternacional15.class);
		}
	},
	RESULTADO_IDM_INTERNACIONAL_7DIAS_FOLLOW_UP(70L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorNotificacaoCadastrarResultadoIDMInternacional7.class);
		}
	},
	CADASTRAR_RESULTADO_EXAME_INTERNACIONAL(63L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)					
					.comOFollowUp(RESULTADO_EXAME_INTERNACIONAL_FOLLOW_UP)
					.comOFollowUp(CHECKLIST_MATCH_EXAME_FOLLOWUP_30D)
					.comProcessadorTarefa(ProcessadorChecklistDoadorExame.class);
		}
	},
	CHECKLIST_BUSCA_EXAME_FOLLOWUP_30D(88L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorChecklistDoadorExame.class);
		}
	},
	CHECKLIST_MATCH_EXAME_FOLLOWUP_30D(89L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorChecklistDoadorExame.class);
		}
	},	
	CHECKLIST_EXAME_DIVERGENTE_FOLLOWUP(94L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)					
					.comProcessadorTarefa(ProcessadorChecklistExameDivergenteFollowup.class);
		}
	},
	DESATRIBUIR_USUARIO_AGENDAMENTO_FOLLOW_UP(104L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorDesatribuirUsuarioAgendamentoContato.class);
		}
	},
    VERIFICAR_GERACAO_MATCH_DOADOR(107L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.comProcessadorTarefa(ProcessadorGeracaoMatchDoador.class);
		}
	},
    VERIFICAR_GERACAO_MATCH_PACIENTE(108L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.comProcessadorTarefa(ProcessadorGeracaoMatchPaciente.class);
		}
	},
	VALIDAR_TENTATIVA_CONTATO_FOLLOW_UP(96L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorValidarTentativasContatoFollowUp.class);
		}
	},
	VERIFICAR_STATUS_SMS_ENVIADO(100L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.comProcessadorTarefa(ProcessadorVerificarStatusSmsEnviado.class);
		}		
	}, 
	VALIDAR_PEDIDO_CONTATO_SMS_FOLLOW_UP(97L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.comProcessadorTarefa(ProcessadorValidarPedidoContatoSmsFollowUp.class);
		}
	},
		
	RESULTADO_WORKUP_FOLLOW_UP(34L),	
	VALIDAR_TENTATIVA_CONTATO_SMS_FOLLOW_UP(97L),	

	AVALIAR_PACIENTE(5L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.AVALIACAO_PACIENTE_PARA_BUSCA)
					.para(Perfis.MEDICO_AVALIADOR)
					.iniciarProcesso();
		}
	},

	AVALIAR_EXAME_HLA(6L) {
	@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.CONFERENCIA_EXAMES)
					.iniciarProcesso()
					.para(Perfis.AVALIADOR_EXAME_HLA)
					.timeout(TIMEOUT_CONFERENCIA_EXAME);
		}
	},

	RECEBER_PACIENTE(8L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_BUSCA)
					.iniciarProcesso()
					.timeout(LIBERAR_ACOMPANHAR_BUSCA);
		}
	},
	
	ENRIQUECER_DOADOR(92L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ENRIQUECEDOR)
					.timeoutDefault();
		}
	},

	CONTACTAR_FASE_2(12L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_CONTATO_FASE2)
					.comOFollowUp(VALIDAR_TENTATIVA_CONTATO_FOLLOW_UP)
					.comOFollowUp(DESATRIBUIR_USUARIO_AGENDAMENTO_FOLLOW_UP)	
					.timeoutDefault();
		}
	},

	CONTACTAR_FASE_3(13L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_CONTATO_FASE3)
					.comOFollowUp(VALIDAR_TENTATIVA_CONTATO_FOLLOW_UP)
					.comOFollowUp(DESATRIBUIR_USUARIO_AGENDAMENTO_FOLLOW_UP)
					.timeoutDefault();
	
		}
	},
	
	CONTATO_DOADOR(91L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)					
					.agrupando(	TiposTarefa.CONTACTAR_FASE_2, 
								TiposTarefa.CONTACTAR_FASE_3 );
		}
	},
	
	SMS(15L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA);

		}
	},

	CONTATO_HEMOCENTRO(20L),

	RESPONDER_PENDENCIA(27L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.AVALIACAO_PACIENTE_PARA_BUSCA);
	
		}
	},

	PEDIDO_WORKUP(30L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP)
					.comOFollowUp(WORKUP_FOLLOW_UP);
	
		}
	},

	SUGERIR_DATA_WORKUP(32L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
		return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR)
				.timeoutDefault();
		}
	},

	AVALIAR_RESULTADO_WORKUP_NACIONAL(33L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_TRANSPLANTADOR);					
		}
	},

	AVALIAR_RESULTADO_WORKUP_INTERNACIONAL(222L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_TRANSPLANTADOR);					
		}
	},
	
	INFORMAR_RESULTADO_WORKUP_NACIONAL(35L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_CENTRO_COLETA)
					.timeoutDefault()					
					.comOFollowUp(RESULTADO_WORKUP_FOLLOW_UP);
		}
	},

	PEDIDO_COLETA(40L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP)
					.timeout(ROLLBACK_ATRIBUICAO)
					.naoAtribuirAoCriarNovaTarefa();
		}
	},
	
	AVALIAR_PEDIDO_COLETA(37L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_REDOME)
					.timeoutDefault();
		}
	},

	ANALISE_MEDICA_DOADOR_COLETA(38L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_REDOME);
		}
	},

	RESPONDER_PEDIDO_ADICIONAL(39L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP);
		}
	},

	PEDIDO_LOGISTICA_DOADOR_COLETA(41L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
				.para(TipoProcesso.BUSCA)
				.para(Perfis.ANALISTA_LOGISTICA)
				.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},

	INFORMAR_DOCUMENTACAO_MATERIAL_AEREO(42L) {

		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	
	CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR(46L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},

	CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA(47L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},

    AVALIAR_PRESCRICAO(49L) {
	    @Override
	    protected IConfiguracaoProcessServer buildConfiguracao() {
	        return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA)
	                .para(Perfis.MEDICO_REDOME)
	                .timeout(ROLLBACK_ATRIBUICAO);
	    }
    },
	
	CADASTRAR_PRESCRICAO_MEDULA(52L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR);
		}
	},
	
	ENCONTRAR_CENTRO_TRANSPLANTE(53L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.CONTROLADOR_LISTA)					
					.timeout(ROLLBACK_ATRIBUICAO);

		}
	},
	
	ANALISAR_PEDIDO_TRANSPORTE(55L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
				.para(TipoProcesso.BUSCA)
				.para(Perfis.TRANSPORTADORA);
		}
	},
	
	INFORMAR_RETIRADA_MATERIAL(56L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.TRANSPORTADORA);
		}
	},
	
	PEDIDO_LOGISTICA_CORDAO_NACIONAL(75L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	
	PEDIDO_TRANSPORTE_ENTREGA(57L){  
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.TRANSPORTADORA);
		}
	},
	PEDIDO_TRANSPORTE_RETIRADA_ENTREGA(58L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA).para(Perfis.TRANSPORTADORA)
					.agrupando(	TiposTarefa.PEDIDO_TRANSPORTE_ENTREGA, 
								TiposTarefa.INFORMAR_RETIRADA_MATERIAL);
		}
	},
	RECEBER_COLETA_PARA_EXAME(60L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.LABORATORIO);
		}
	}
	,
	CADASTRAR_RESULTADO_CT(61L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.LABORATORIO)
					.comOFollowUp(CHECKLIST_BUSCA_EXAME_FOLLOWUP_30D);
		}
	}
	,
	CADASTRAR_RESULTADO_EXAME(62L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA);
		}
	},
	
	RESULTADO_EXAME_NACIONAL(90L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.agrupando(	TiposTarefa.CADASTRAR_RESULTADO_EXAME, 
								TiposTarefa.CADASTRAR_RESULTADO_CT );
		}
	},
	
	CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL(65L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.timeoutDefault()
					.comOFollowUp(RESULTADO_EXAME_CT_INTERNACIONAL_15DIAS_FOLLOW_UP)
					.comOFollowUp(CHECKLIST_MATCH_EXAME_FOLLOWUP_30D);
		}
	},
	
	RESULTADO_EXAME_INTERNACIONAL(93L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.agrupando(	TiposTarefa.CADASTRAR_RESULTADO_EXAME_INTERNACIONAL, 
								TiposTarefa.CADASTRAR_RESULTADO_EXAME_CT_INTERNACIONAL);
		}
	},
	CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL(68L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_BUSCA)
					.timeoutDefault()
					.comOFollowUp(RESULTADO_IDM_INTERNACIONAL_15DIAS_FOLLOW_UP)
					.comOFollowUp(CHECKLIST_MATCH_EXAME_FOLLOWUP_30D);
		}
	},
	CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL(71L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_WORKUP_INTERNACIONAL)
					.timeoutDefault();
		}
	},	
	PEDIDO_WORKUP_INTERNACIONAL(73L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_WORKUP_INTERNACIONAL)
					.comOFollowUp(WORKUP_FOLLOW_UP);
		}
	},
	SUGERIR_DATA_COLETA(74L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
		return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR)
				.timeoutDefault();
		}
	},
	
	RETIRADA_CORDAO_NACIONAL(76L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.TRANSPORTADORA)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	
	ENTREGA_CORDAO_NACIONAL(77L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.TRANSPORTADORA)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	AUTORIZACAO_PACIENTE(78L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR)
					.timeout(ROLLBACK_ATRIBUICAO)
					.naoAtribuirAoCriarNovaTarefa();
		}
	},
	RETIRADA_MATERIAL_INTERNACIONAL(81L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	ENTREGA_MATERIAL_INTERNACIONAL(82L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL)
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	}
	,MATERIAL_INTERNACIONAL_RETIRADA_ENTREGA(83L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL)					
					.agrupando(	TiposTarefa.RETIRADA_MATERIAL_INTERNACIONAL, 
								TiposTarefa.ENTREGA_MATERIAL_INTERNACIONAL);
		}
	},
	PEDIDO_TRANSFERENCIA_CENTRO(85L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.TRANSFERENCIA_CENTRO)
					.para(Perfis.MEDICO_AVALIADOR)
					.iniciarProcesso()					
					.timeout(ROLLBACK_ATRIBUICAO).naoAtribuirAoCriarNovaTarefa();
		}
	},
	AVALIACAO_CAMARA_TECNICA(86L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.iniciarProcesso()
					.para(TipoProcesso.AVALIACAO_CAMARA_TECNICA)
					.para(Perfis.AVALIADOR_CAMARA_TECNICA);
		}
	},
	AVALIAR_NOVA_BUSCA(87L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.AVALIACAO_PACIENTE_PARA_NOVA_BUSCA).iniciarProcesso()
					.para(Perfis.AVALIADOR_NOVA_BUSCA);
		}
	},
	ENVIAR_PACIENTE_PARA_EMDIS_FOLLOWUP(102L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.iniciarProcesso();
		}
	},
	ANALISE_MEDICA_DOADOR_CONTATO(95L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_REDOME)
					.timeoutDefault();
	
		}
	},
	INATIVAR_DOADOR_NAO_CONTACTADO(99L),
	DOADOR_NAO_CONTACTADO(98L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)										
					.comOFollowUp(INATIVAR_DOADOR_NAO_CONTACTADO);
	
		}
	},
	ENVIAR_PEDIDO_FASE2_PARA_EMDIS(103L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA);
		}
	},
	CADASTRAR_PRESCRICAO_CORDAO(105L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR);
		}
	},
	CADASTRAR_PRESCRICAO(106L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA).para(Perfis.MEDICO_TRANSPLANTADOR)
					.agrupando(	TiposTarefa.CADASTRAR_PRESCRICAO_MEDULA, 
								TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO);
		}
	},
	ENVIAR_DADOS_PACIENTE_WMDA_FOLLOWUP(101L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.comProcessadorTarefa(ProcessadorDeEnvioDadosPacienteParaWmda.class);
		}
	}, 
	RESULTADO_PESQUISA_DOADOR_WMDA_FOLLOWUP(109L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.comProcessadorTarefa(ProcessadorDePesquisaResultadoDoadorParaWmda.class);
		}
	},
	DISTRIBUIR_WORKUP_NACIONAL(110L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA)
					.para(Perfis.DISTRIBUIDOR_WORKUP_NACIONAL);
		}
	},
	DISTRIBUIR_WORKUP_INTERNACIONAL(111L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA)
					.para(Perfis.DISTRIBUIDOR_WORKUP_INTERNACIONAL);
		}
	},
	CADASTRAR_FORMULARIO_DOADOR(112L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId()).para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_WORKUP);
		}
	},
	DEFINIR_CENTRO_COLETA(114L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_WORKUP);
		}
	},
	INFORMAR_PLANO_WORKUP_NACIONAL(115L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(Perfis.MEDICO_CENTRO_COLETA)
					.para(TipoProcesso.BUSCA);
					
		}
	},
	INFORMAR_PLANO_WORKUP_INTERNACIONAL(118L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(Perfis.ANALISTA_WORKUP_INTERNACIONAL)
					.para(TipoProcesso.BUSCA);
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
	INFORMAR_DETALHE_WORKUP_NACIONAL(119L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(Perfis.MEDICO_TRANSPLANTADOR)
					.para(TipoProcesso.BUSCA);
					
		}
	},
	INFORMAR_RESULTADO_WORKUP_INTERNACIONAL(220L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(Perfis.ANALISTA_WORKUP_INTERNACIONAL)
					.para(TipoProcesso.BUSCA);
					
		}
	},
	
	INFORMAR_LOGISTICA_DOADOR_WORKUP(36L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_LOGISTICA);
		}
	},

	INFORMAR_AUTORIZACAO_PACIENTE(223L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_TRANSPLANTADOR)
					.timeout(ROLLBACK_ATRIBUICAO)
					.naoAtribuirAoCriarNovaTarefa();
		}
	},
	
	AGENDAR_COLETA_INTERNACIONAL(72L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_WORKUP_INTERNACIONAL);
		}
	},
	
	AGENDAR_COLETA_NACIONAL(224L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_CENTRO_COLETA);
		}
	},

	INFORMAR_EXAME_ADICIONAL_WORKUP_NACIONAL(225L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_WORKUP);
		}
	},
	
	INFORMAR_EXAME_ADICIONAL_WORKUP_INTERNACIONAL(226L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_WORKUP_INTERNACIONAL);
		}
	},
	
	INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL(227L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_LOGISTICA);
		}		
	},

	INFORMAR_LOGISTICA_DOADOR_COLETA(228L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_WORKUP);
		}		
	},
	
	INFORMAR_LOGISTICA_MATERIAL_COLETA_INTERNACIONAL(84L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.ANALISTA_LOGISTICA_INTERNACIONAL);
		}
	},
	
	INFORMAR_RECEBIMENTO_COLETA(50L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_TRANSPLANTADOR);
		}
	},
	
	INFORMAR_RESULTADO_DOADOR_COLETA(229L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_TRANSPLANTADOR);
		}
		
	},
	CADASTRAR_COLETA_CONTAGEM_CELULA(231L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA)
					.para(Perfis.MEDICO_CENTRO_COLETA);
		}
	},
	
	INFORMAR_FORMULARIO_POSCOLETA(230L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.para(Perfis.MEDICO_CENTRO_COLETA)
					.para(TipoProcesso.BUSCA);
		}
		
	},
	
	LOGISTICA(59L){
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoGrupoTipoTarefa.newInstance(this.getId())
					.para(TipoProcesso.BUSCA).para(Perfis.ANALISTA_LOGISTICA)					
					.agrupando(	TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP, 
								TiposTarefa.INFORMAR_DOCUMENTACAO_MATERIAL_AEREO, 
								TiposTarefa.PEDIDO_LOGISTICA_DOADOR_COLETA,
								TiposTarefa.PEDIDO_LOGISTICA_CORDAO_NACIONAL, 
								TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_INTERNACIONAL,
								TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL);
		}
	},

	
	WORKUP_CENTRO_COLETA(116L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.somenteAgrupador(true)
					.para(TipoProcesso.BUSCA)
					.agrupando(TiposTarefa.INFORMAR_PLANO_WORKUP_NACIONAL,
							TiposTarefa.INFORMAR_DETALHE_WORKUP_NACIONAL,
							TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL,
							TiposTarefa.AGENDAR_COLETA_NACIONAL,
							TiposTarefa.CADASTRAR_COLETA_CONTAGEM_CELULA,
							TiposTarefa.INFORMAR_FORMULARIO_POSCOLETA);
		}
	},
	
	WORKUP_CENTRO_TRANSPLANTE(221L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.somenteAgrupador(true)
					.para(TipoProcesso.BUSCA)
					.agrupando(TiposTarefa.CONFIRMAR_PLANO_WORKUP,
							TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL,
							TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL,
                            TiposTarefa.INFORMAR_AUTORIZACAO_PACIENTE,
                            TiposTarefa.INFORMAR_RECEBIMENTO_COLETA,
                            TiposTarefa.INFORMAR_RESULTADO_DOADOR_COLETA);
		}
	},
	
	WORKUP(113L) {
		@Override
		protected IConfiguracaoProcessServer buildConfiguracao() {
			return ConfiguracaoTipoTarefa.newInstance(this.getId())
					.somenteAgrupador(true)
					.para(TipoProcesso.BUSCA)
					.agrupando(TiposTarefa.CADASTRAR_FORMULARIO_DOADOR, 
							TiposTarefa.DEFINIR_CENTRO_COLETA,
							TiposTarefa.INFORMAR_PLANO_WORKUP_INTERNACIONAL,
							TiposTarefa.INFORMAR_RESULTADO_WORKUP_INTERNACIONAL,
							TiposTarefa.INFORMAR_LOGISTICA_DOADOR_WORKUP,
							TiposTarefa.ANALISE_MEDICA_DOADOR_COLETA,
							TiposTarefa.INFORMAR_EXAME_ADICIONAL_WORKUP_NACIONAL,
							TiposTarefa.INFORMAR_EXAME_ADICIONAL_WORKUP_INTERNACIONAL,
							TiposTarefa.AGENDAR_COLETA_INTERNACIONAL,
							TiposTarefa.INFORMAR_LOGISTICA_DOADOR_COLETA);
							
		}
	};
		
	
	//ENVIAR_PACIENTE_PARA_EMDIS_FOLLOWUP(102L, ProcessadorEnviarPacienteParaEmdis.class),
	//ENVIAR_PEDIDO_FASE2_PARA_EMDIS(103L, ProcessadorEnviarPedidoComplementarParaEmdis.class);
		
	private Long id;
	//private Class<? extends IProcessadorTarefa> classeProcessador;
	private IConfiguracaoProcessServer configuracaoDefault;
	private IConfiguracaoProcessServer configuracao;
	
	TiposTarefa(Long id) {
		this.id = id;
		//this.classeProcessador = classeProcessador;
		this.configuracaoDefault = ConfiguracaoTipoTarefa.newInstance(id);
		this.configuracao = buildConfiguracao();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Obtém a configuração definida para o tipo de tarefa informado.
	 * Esta configuração define quais serão as informações a serem utilizadas
	 * no process server ao criar, listar, atribuir, fechar e cancelar a tarefa deste tipo.
	 * 
	 * @return configuração definida para o tipo.
	 */
	public IConfiguracaoProcessServer getConfiguracao() {
		return configuracao;
	}	

	/**
	 * @return the processador class
	 */
	public Class<? extends IProcessadorTarefa> getClasseProcessador() {
		return this.getConfiguracao().getProcessadorTarefa();
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

		TiposTarefa[] values = TiposTarefa.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getId().equals(value)) {
				return values[i];
			}
		}
		return null;
	}

	/**
	 * Método para obter a classe responsável pelo processamento do tipo de tarefa.
	 * 
	 * @param id do tipo de tarefa
	 * @return classe responsável pelo processamento do tipo de tarefa
	 */
	public static Class<? extends IProcessadorTarefa> obterClasseProcessador(Long id) {
		TiposTarefa tipo = valueOf(id);

		if (tipo != null) {
			return tipo.getClasseProcessador();
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

}
