import { Recursos } from './recursos';
/**
 * NameSpace para exportar um enum com os componentes e os recursos associados
 *
 * @export
  * @author Bruno Sousa
 *
 */
export namespace ComponenteRecurso {

  export enum Componente {
    HomeComponent,
    ConsultaComponent,
    CadastroComponent,
    DetalheComponent,
    ConsultarEvolucaoComponent,
    NovaEvolucaoComponent,
    ConsultarExameComponent,
    NovoExameComponent,
    AvaliacaoComponent,
    AvaliacaoMedicoComponent,
    DialogoComponent,
    TesteComponent,
    ConferenciaComponent,
    ConsultarNotificacaoComponent,
    PendenciaComponent,
    CentroTransplanteComponent,
    UsuarioComponent,
    PerfilComponent,
    ConsultaAvaliacoesComponent,
    EditarContatoPacienteComponent,
    PacienteBuscaComponent,
    PedidoExameModal,
    EditarIdentificacaoDadosPessoaisComponent,
    CancelamentoBuscaComponent,
    EnriquecerDoadorComponent,
    PedidoFase2Component,
    ContatoFase2Component,
    InativacaoModalComponent,
    AtualizarDoadorComponent,
    SimularSolicitacaoComponent,
    ContatoFase3Component,
    ConsultaWorkupComponent,
    DoadorPendenciaWorkupComponent,
    IncluirDisponibilidadeComponent,
    ConfirmarAgendamentoWorkupComponent,
    VisualizarDoadorComponent,
    VisualizarDadosCTComponent,
    ContatoPassivoComponent,
    ContatoPassivoAtualizarComponent,
    LogisticaDoadorComponent,
    ConsultaContatoDoadorComponent,
    AvaliacoesResultadoWorkupComponent,
    DetalharLogisticaComponent,
    AvaliarResultadoWorkupNacionalComponent,
    AvaliarResultadoWorkupInternacionalComponent,
    PedidoColetaComponent,
    AgendarPedidoColetaComponent,
    AvaliacaoPedidoColetaComponent,
    AvaliarPedidoColetaComponent,
    CancelarAgendamentoPedidoColetaComponent,
    DetalheCancelarAgendamentoPedidoColetaComponent,
    AnaliseMatchComponent,
    PrescricaoMedulaComponent,
    PrescricaoCordaoComponent,
    //  RecebimentoColetaComponent,
    //  RecebimentoColetaCadastroComponent,
    RecebimentoColetaCadastroInternacionalComponent,
    LogEvolucaoComponent,
    AvaliarPrescricaoComponent,
    ConsultaAvaliacoesPrescricaoComponent,
    ConsultarPrescricaoComponent,
    DefinirCentroTransplanteComponent,
    ConfirmarCentroTransplanteComponent,
    DetalheAgendarTransporteMaterialComponent,
    RetiradaEntregaTransporteMaterialComponent,
    ConsultaTransporteMaterialComponent,
    LogisticaMaterialArquivoComponent,
    LogisticaMaterialComponent,
    LaboratorioExameComponent,
    RelatorioComponent,
    PedidoExameFase3Component,
    PedidoExamePacienteComponent,
    CadastroDoadorComponent,
    ConsultaDoadorNacionalComponent,
    ConsultaDoadorInternacionalComponent,
    AtualizacaoDoadorInternacionalComponent,
    PedidoExameInternacionalComponent,
    CadastroPedidoExameInternacionalComponent,
    ResultadoCTComponent,
    ResultadoPedidoIdmComponent,
    CadastroWorkupInternacionalComponent,
    CadastroResultadoWorkupNacionalComponent,
    CadastroResultadoWorkupInternacionalComponent,
    PedidoColetaInternacionalComponent,
    ConfirmarAgendamentoColetaComponent,
    ConsultarAutorizacaoPacienteComponent,
    ModalUploadAutorizacaoPacienteComponent,
    CadastrarBuscaPreliminarComponent,
    LogisticaMaterialInternacionalChecklistComponent,
    AnaliseMatchPreliminarComponent,
    RetiradaEntregaTransporteMaterialInternacionalComponent,
    ConsultaCentroTransplanteComponent,
    LogisticaDoadorInternacionalComponent,
    LogisticaMaterialInternacionalComponent,
    DetalheCentroTransplanteComponent,
    AnalistaBuscaCentroAvaliadorComponent,
    ConsultaPreCadastroMedicoComponent,
    DetalhePreCadastroMedicoComponent,
    ConsultaMedicoComponent,
    DetalheMedicoComponent,
    ConferenciaDetalheComponent,
    ConsultaTransferenciaCentroComponent,
    DetalheTransferenciaCentroComponent,
    AvaliacaoCamaraTecnicaComponent,
    DetalheAvaliacaoCamaraTecnicaComponent,
    EditarDiagnosticoComponent,
    EditarAvaliacaoComponent,
    NovaBuscaComponent,
    AvaliarNovaBuscaComponent,
    AvaliarNovaBuscaDetalheComponent,
    ListarUsuarioComponent,
    DetalheUsuarioComponent,
    NovoUsuarioComponent,
    AtualizacaoDoadorInternacionalExameComponent,
    VisualizarDoadorInternacionalComponent,
    VisualizarDoadorInternacionalExameComponent,
    CadastroResultadoExameInternacionalComponent,
    GenotipoDivergenteComponent,
    BuscaInternacionalComponent,
    TransportadoraCadastroConsultaComponent,
    DetalheTransportadoraComponent,
    CourierConsultaComponent,
    DetalheCourierComponent,
    LaboratorioConsultaComponent,
    DetalheLaboratorioComponent,
    AnaliseMedicaComponent,
    DetalheAnaliseMedicaComponent,
    PedidoContatoSmsComponent,
    BuscaChecklistConsultaComponent,
    VisualizarDoadorNacionalComponent,
    ConsultaInvoiceApagarComponent,
    CadastroInvoiceApagarComponent,
    LogisticaMaterialNacionalComponent,
    VisualizarDetalhePrescricaoComponent,
    ConsultarDistribuicaoWorkupComponent,
    VisualizaFormularioPedidoWorkupComponent,
    DefinirCentroColetaPedidoWorkupComponent,
    ConsultaWorkupCentroColetaComponent,
    InformarPlanoWorkupComponent,
    AprovarPlanoWorkupComponent,
    CadastroPedidoAdicionalWorkupComponent,
    ConsultaLogisticaMaterialComponent,
    AgendarLogisticaDoadorColetaComponent,
    AvaliacaoAvaliadorComponent
  }

    let _ComponenteRecurso = {
        'ConsultaComponent': [
            Recursos.CONSULTAR_PACIENTE
        ],
        'CadastroComponent': [
            Recursos.CADASTRAR_PACIENTE,
        ],
        'DetalheComponent': [
            Recursos.VISUALIZAR_FICHA_PACIENTE,
            Recursos.PACIENTES_PARA_PROCESSO_BUSCA
        ],
        'ConsultarEvolucaoComponent': [
            Recursos.CONSULTAR_EVOLUCOES_PACIENTE,
            Recursos.PACIENTES_PARA_PROCESSO_BUSCA,
            Recursos.AVALIAR_PACIENTE_CAMARA_TECNICA,
            Recursos.AVALIAR_NOVA_BUSCA_PACIENTE
        ],
        'NovaEvolucaoComponent': [
            Recursos.CADASTRAR_EVOLUCOES_PACIENTE
        ],
        'ConsultarExameComponent': [
            Recursos.CONSULTAR_EXAMES_PACIENTE,
            Recursos.PACIENTES_PARA_PROCESSO_BUSCA,
            Recursos.AVALIAR_PACIENTE_CAMARA_TECNICA
        ],
        'NovoExameComponent': [
            Recursos.CADASTRAR_EXAMES_PACIENTE,
            Recursos.RECEBER_COLETA_LABORATORIO
        ],
        'AvaliacaoComponent': [
            Recursos.AVALIAR_PACIENTE
        ],
        'AvaliacaoMedicoComponent': [
            Recursos.VISUALIZAR_AVALIACAO
        ],
        'DialogoComponent': [
            Recursos.VISUALIZAR_AVALIACAO,
            Recursos.AVALIAR_PACIENTE
        ],
        'ConferenciaComponent': [
            Recursos.CONFERIR_EXAME_HLA
        ],
        'ConferenciaDetalheComponent': [
            Recursos.CONFERIR_EXAME_HLA
        ],
        'ConsultarNotificacaoComponent': [
            Recursos.VISUALIZAR_NOTIFICACOES
        ],
        'PendenciaComponent': [
            Recursos.VISUALIZAR_PENDENCIAS_AVALIACAO,
            Recursos.AVALIAR_PACIENTE
        ],
        'ConsultaCentroTransplanteComponent': [
            Recursos.MANTER_CENTRO_TRANSPLANTE
        ],
        'UsuarioComponent': [
            Recursos.MANTER_USUARIO
        ],
        'PerfilComponent': [
            Recursos.MANTER_PERFIL
        ],
        'ConsultaAvaliacoesComponent': [
            Recursos.CONSULTAR_AVALIACOES
        ],
        'EditarContatoPacienteComponent': [
            Recursos.EDITAR_CONTATO_PACIENTE
        ],
        'PacienteBuscaComponent': [
            Recursos.CONSULTA_PACIENTES_PARA_PROCESSO_BUSCA
        ],
        'BuscaChecklistConsultaComponent': [
            Recursos.CONSULTA_PACIENTES_PARA_PROCESSO_BUSCA
        ],
        'PedidoExameModal': [
            Recursos.PACIENTES_PARA_PROCESSO_BUSCA
        ],
        'EditarIdentificacaoDadosPessoaisComponent': [
            Recursos.EDITAR_DADOS_PESSOAIS
        ],
        'CancelamentoBuscaComponent': [
            Recursos.CANCELAR_BUSCA
        ],
        'EnriquecerDoadorComponent': [
            Recursos.ENRIQUECER_DOADOR
        ],
        'ContatoFase2Component': [
            Recursos.CONTACTAR_FASE_2
        ],
        'AtualizarDoadorComponent': [
            Recursos.CONTACTAR_FASE_2,
            Recursos.CONTACTAR_FASE_3
        ],
        'InativacaoModalComponent': [
            Recursos.INATIVAR_DOADOR_ENRIQUECIMENTO
        ],
        'SimularSolicitacaoComponent': [
            Recursos.ENRIQUECER_DOADOR,
            Recursos.CONTACTAR_FASE_2,
            Recursos.CONTACTAR_FASE_3,
            Recursos.TRATAR_PEDIDO_WORKUP
//            Recursos.CADASTRAR_RECEBIMENTO_COLETA
        ],
        'ContatoFase3Component': [
            Recursos.CONTACTAR_FASE_3
        ],
        'ConsultaWorkupComponent': [
            Recursos.TRATAR_PEDIDO_WORKUP,
            Recursos.TRATAR_PEDIDO_WORKUP_INTERNACIONAL
        ],
        'DoadorPendenciaWorkupComponent': [
            Recursos.VISUALIZAR_PENDENCIA_WORKUP
        ],
        'IncluirDisponibilidadeComponent': [
            Recursos.TRATAR_PEDIDO_WORKUP,
            Recursos.TRATAR_PEDIDO_WORKUP_INTERNACIONAL
        ],
        'ConfirmarAgendamentoWorkupComponent': [
            Recursos.VISUALIZAR_PENDENCIA_WORKUP
        ],
        'VisualizarDoadorComponent': [
            Recursos.TRATAR_PEDIDO_WORKUP,
            Recursos.AVALIAR_PRESCRICAO
        ],
        'VisualizarDadosCTComponent': [
            Recursos.TRATAR_PEDIDO_WORKUP,
            Recursos.AGENDAR_COLETA_DOADOR,
            Recursos.TRATAR_PEDIDO_WORKUP_INTERNACIONAL,
            Recursos.TRATAR_PEDIDO_COLETA_INTERNACIONAL
        ],
        'ConsultaLogisticaMaterialComponent': [
            Recursos.EFETUAR_LOGISTICA
        ],
        'LogisticaMaterialNacionalComponent': [
            Recursos.EFETUAR_LOGISTICA
        ],
        'LogisticaDoadorInternacionalComponent': [
            Recursos.EFETUAR_LOGISTICA_INTERNACIONAL
        ],
        'LogisticaMaterialInternacionalComponent': [
            Recursos.EFETUAR_LOGISTICA_INTERNACIONAL
        ],
        'AgendarLogisticaDoadorColetaComponent': [
            Recursos.EFETUAR_LOGISTICA_DOADOR_COLETA
        ],
        'ContatoPassivoComponent': [
            Recursos.CONTATO_PASSIVO
        ],
        'ContatoPassivoAtualizarComponent': [
            Recursos.CONTATO_PASSIVO
        ],
        'ConsultaContatoDoadorComponent': [
            Recursos.CONTATO_PASSIVO
        ],
        'ConsultaDoadorNacionalComponent': [
            Recursos.CONSULTAR_DOADOR
        ],
        'VisualizarDoadorNacionalComponent': [
            Recursos.CONSULTAR_DOADOR
        ],
        'AvaliacoesResultadoWorkupComponent': [
            Recursos.AVALIAR_RESULTADO_WORKUP_NACIONAL
        ],
        'DetalharLogisticaComponent': [
            Recursos.EFETUAR_LOGISTICA
        ],
        'AvaliarResultadoWorkupNacionalComponent': [
            Recursos.AVALIAR_RESULTADO_WORKUP_NACIONAL,
            Recursos.AVALIAR_PEDIDO_COLETA
        ],
        'PedidoColetaComponent': [
            Recursos.AGENDAR_COLETA_DOADOR
        ],
        'AgendarPedidoColetaComponent': [
          Recursos.AGENDAR_PEDIDO_COLETA,
          Recursos.CONSULTAR_CENTRO_TRANSPLANTE,
          Recursos.CONSULTAR_PEDIDO_COLETA
        ],
        'AvaliacaoPedidoColetaComponent': [
            Recursos.AVALIAR_PEDIDO_COLETA
        ],
        'AvaliarPedidoColetaComponent': [
            Recursos.AVALIAR_PEDIDO_COLETA
        ],
        'CancelarAgendamentoPedidoColetaComponent': [
            Recursos.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR,
            Recursos.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA
        ],
        'DetalheCancelarAgendamentoPedidoColetaComponent': [
            Recursos.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR,
            Recursos.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA
        ],
        'AnaliseMatchComponent': [
            Recursos.ANALISE_MATCH
        ],
        'PrescricaoMedulaComponent': [
            Recursos.CADASTRAR_PRESCRICAO
        ],
        'PrescricaoCordaoComponent': [
            Recursos.CADASTRAR_PRESCRICAO
        ],
        // 'RecebimentoColetaComponent': [
        //     Recursos.CADASTRAR_RECEBIMENTO_COLETA
        // ],
        // 'RecebimentoColetaCadastroComponent': [
        //     Recursos.CADASTRAR_RECEBIMENTO_COLETA
        // ]
        'RecebimentoColetaCadastroInternacionalComponent': [
          Recursos.TRATAR_RECEBIMENTO_COLETA
        ],
        'AvaliarPrescricaoComponent': [
            Recursos.AVALIAR_PRESCRICAO
        ],
        'ConsultaAvaliacoesPrescricaoComponent': [
            Recursos.AVALIAR_PRESCRICAO
        ],
        'LogEvolucaoComponent': [
            Recursos.VISUALIZAR_LOG_EVOLUCAO
        ],
        'ConsultarPrescricaoComponent': [
            Recursos.CADASTRAR_PRESCRICAO
        ],
        "DefinirCentroTransplanteComponent": [
            Recursos.ENCONTRAR_CENTRO_TRANSPLANTADOR
        ],
        "ConfirmarCentroTransplanteComponent": [
            Recursos.ENCONTRAR_CENTRO_TRANSPLANTADOR
        ],
        'ConsultaTransporteMaterialComponent': [
            Recursos.AGENDAR_TRANSPORTE_MATERIAL
        ],
        'RetiradaEntregaTransporteMaterialComponent': [
            Recursos.RETIRAR_TRANSPORTE_MATERIAL,
            Recursos.ENTREGAR_TRANSPORTE_MATERIAL
        ],
        'DetalheAgendarTransporteMaterialComponent': [
            Recursos.AGENDAR_TRANSPORTE_MATERIAL
        ],
        'LogisticaMaterialArquivoComponent': [
            Recursos.SOLICITAR_LOGISTICA_MATERIAL
        ],
        'LogisticaMaterialComponent': [
            Recursos.SOLICITAR_LOGISTICA_MATERIAL
        ],
        'RelatorioComponent': [
            Recursos.EDITAR_RELATORIO
        ],
        'LaboratorioExameComponent': [
            Recursos.RECEBER_COLETA_LABORATORIO
        ],
        'PedidoExameFase3Component': [
            Recursos.SOLICITAR_FASE3_PACIENTE,
            Recursos.PACIENTES_PARA_PROCESSO_BUSCA
        ],
        'PedidoExamePacienteComponent':[
            Recursos.SOLICITAR_FASE3_PACIENTE,
            Recursos.PACIENTES_PARA_PROCESSO_BUSCA
        ],
        'CadastroDoadorComponent': [
            Recursos.CADASTRAR_DOADOR_INTERNACIONAL
        ],
        'ConsultaDoadorInternacionalComponent': [
            Recursos.ALTERAR_DOADOR_INTERNACIONAL
        ],
        'VisualizarDoadorInternacionalComponent': [
            Recursos.ALTERAR_DOADOR_INTERNACIONAL
        ],
        'VisualizarDoadorInternacionalExameComponent': [
            Recursos.ALTERAR_DOADOR_INTERNACIONAL
        ],
        'CadastroResultadoExameInternacionalComponent': [
            Recursos.ALTERAR_DOADOR_INTERNACIONAL
        ],
        'PedidoExameInternacionalComponent': [
            Recursos.SOLICITAR_FASE_2_INTERNACIONAL
        ],
        'CadastroPedidoExameInternacionalComponent': [
            Recursos.CADASTRAR_RESULTADO_PEDIDO_FASE_2_INTERNACIONAL
        ],
        'ResultadoCTComponent': [
            Recursos.CADASTRAR_RESULTADO_PEDIDO_CT
        ],
        "ResultadoPedidoIdmComponent": [
            Recursos.CADASTRAR_RESULTADO_PEDIDO_IDM
        ],
        "CadastroWorkupInternacionalComponent": [
            Recursos.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL
        ],
        "CadastroResultadoWorkupNacionalComponent":[
            Recursos.CONSULTAR_PEDIDO_WORKUP_NACIONAL,
            Recursos.CADASTRAR_RESULTADO_WORKUP_NACIONAL,
            Recursos.CONSULTAR_FORMULARIO,
            Recursos.CADASTRAR_FORMULARIO,
        ],
        "CadastroResultadoWorkupInternacionalComponent":[
          Recursos.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL
        ],
        'PedidoColetaInternacionalComponent': [
            Recursos.TRATAR_PEDIDO_COLETA_INTERNACIONAL
        ],
        'ConfirmarAgendamentoColetaComponent':[
            Recursos.VISUALIZAR_PENDENCIA_WORKUP
        ],
        'ConsultarAutorizacaoPacienteComponent': [
            Recursos.AUTORIZACAO_PACIENTE
        ],
        'ModalUploadAutorizacaoPacienteComponent': [
            Recursos.AUTORIZACAO_PACIENTE
        ],
        'CadastrarBuscaPreliminarComponent': [
            Recursos.CADASTRAR_BUSCA_PRELIMINAR
        ],
        'LogisticaMaterialInternacionalChecklistComponent':[
          Recursos.TRATAR_PEDIDO_WORKUP_INTERNACIONAL,
          Recursos.EFETUAR_LOGISTICA_INTERNACIONAL
        ],
        'AnaliseMatchPreliminarComponent': [
            Recursos.VISUALIZAR_MATCH_PRELIMINAR
        ],
        'RetiradaEntregaTransporteMaterialInternacionalComponent':[
            Recursos.RECEBER_AMOSTRA_INTERNACIONAL
        ],
        'DetalheCentroTransplanteComponent': [
            Recursos.MANTER_CENTRO_TRANSPLANTE
        ],
        'AnalistaBuscaCentroAvaliadorComponent': [
            Recursos.RELACIONAR_ANALISTA_AO_CENTRO
        ],
        'ConsultaPreCadastroMedicoComponent': [
            Recursos.CONSULTAR_PRE_CADASTRO_MEDICO
        ],
        'DetalhePreCadastroMedicoComponent': [
            Recursos.VISUALIZAR_DETALHE_PRE_CADASTRO_MEDICO
        ],
        'ConsultaMedicoComponent': [
            Recursos.CONSULTAR_CADASTRO_MEDICO
        ],
        'DetalheMedicoComponent': [
            Recursos.VISUALIZAR_CADASTRO_MEDICO
        ],
        'ConsultaTransferenciaCentroComponent': [
            Recursos.CONSULTAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR
        ],
        'DetalheTransferenciaCentroComponent': [
            Recursos.DETALHE_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR
        ],
        'AvaliacaoCamaraTecnicaComponent':[
            Recursos.AVALIAR_PACIENTE_CAMARA_TECNICA
        ],
        'DetalheAvaliacaoCamaraTecnicaComponent':[
            Recursos.AVALIAR_PACIENTE_CAMARA_TECNICA
        ],
        'EditarDiagnosticoComponent': [
            Recursos.EDITAR_DIAGNOSTICO_PACIENTE
        ],
        'EditarAvaliacaoComponent': [
            Recursos.EDITAR_MISMATCH_PACIENTE
        ],
        'NovaBuscaComponent': [
            Recursos.SOLICITAR_NOVA_BUSCA_PACIENTE
        ],
        'AvaliarNovaBuscaComponent': [
            Recursos.AVALIAR_NOVA_BUSCA_PACIENTE
        ],
        'AvaliarNovaBuscaDetalheComponent': [
            Recursos.AVALIAR_NOVA_BUSCA_PACIENTE
        ],
        'ListarUsuarioComponent': [
            Recursos.LISTAR_USUARIOS
        ],
        'DetalheUsuarioComponent': [
            Recursos.ALTERAR_USUARIO
        ],
        'NovoUsuarioComponent': [
            Recursos.CADASTRAR_USUARIO
        ],
        'GenotipoDivergenteComponent': [
            Recursos.VISUALIZAR_GENOTIPO_DIVERGENTE
        ],
        'BuscaInternacionalComponent': [
            Recursos.LISTAR_ARQUIVOS_RELATORIO_BUSCA_INTERNACIONAL
        ],
        'TransportadoraCadastroConsultaComponent':[
            Recursos.CONSULTAR_TRANSPORTADORA
        ],
        'DetalheTransportadoraComponent':[
            Recursos.CONSULTAR_TRANSPORTADORA
        ],
        'CourierConsultaComponent':[
            Recursos.CONSULTAR_COURIER
        ]
        ,'DetalheCourierComponent':[
            Recursos.CONSULTAR_COURIER
        ],
        'LaboratorioConsultaComponent':[
            Recursos.CONSULTAR_LABORATORIO
        ],
        'DetalheLaboratorioComponent':[
            Recursos.CONSULTAR_LABORATORIO
        ],
        'AnaliseMedicaComponent': [
            Recursos.CADASTRAR_ANALISE_MEDICA_DOADOR
        ],
        'DetalheAnaliseMedicaComponent': [
            Recursos.CADASTRAR_ANALISE_MEDICA_DOADOR
        ],
        "PedidoContatoSmsComponent": [
            Recursos.CONSULTAR_CONTATO_SMS
        ],
        "ConsultaInvoiceApagarComponent": [
            Recursos.CONSULTAR_INVOICE
        ],
        "CadastroInvoiceApagarComponent": [
            Recursos.CONSULTAR_INVOICE
        ],
        "VisualizarDetalhePrescricaoComponent": [
            Recursos.CADASTRAR_PRESCRICAO
        ],
        "ConsultarDistribuicaoWorkupComponent": [
            Recursos.DISTRIBUIR_WORKUP,
            Recursos.DISTRIBUIR_WORKUP_INTERNACIONAL
        ],
        "VisualizaFormularioPedidoWorkupComponent": [
            Recursos.CADASTRAR_FORMULARIO,
            Recursos.CONSULTAR_FORMULARIO,
            Recursos.FINALIZAR_FORMULARIO_PEDIDO_WORKUP
        ],
        "DefinirCentroColetaPedidoWorkupComponent": [
            Recursos.TRATAR_PEDIDO_WORKUP
        ],
        'ConsultaWorkupCentroColetaComponent': [
            Recursos.TRATAR_PEDIDO_WORKUP_CENTRO_COLETA
        ],
        'AprovarPlanoWorkupComponent': [
            Recursos.TRATAR_PEDIDO_WORKUP_CENTRO_COLETA,
            Recursos.TRATAR_PEDIDO_WORKUP,
            Recursos.APROVAR_PLANO_WORKUP
        ],
        'InformarPlanoWorkupComponent': [
            Recursos.TRATAR_PEDIDO_WORKUP_CENTRO_COLETA,
            Recursos.TRATAR_PEDIDO_WORKUP,
            Recursos.INFORMAR_PLANO_WORKUP_INTERNACIONAL,
            Recursos.INFORMAR_PLANO_WORKUP_NACIONAL
        ],
        'AvaliarResultadoWorkupInternacionalComponent': [
            Recursos.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL,
            Recursos.AVALIAR_PEDIDO_COLETA
        ],
        'CadastroPedidoAdicionalWorkupComponent': [
          Recursos.CADASTRAR_ARQUIVO_PEDIDO_ADICIONAL_WORKUP,
          Recursos.FINALIZAR_PEDIDO_ADICIONAL_WORKUP,
          Recursos.CONSULTAR_PEDIDO_ADICIONAL_WORKUP
        ],
        'AvaliacaoAvaliadorComponent': [
          Recursos.AVALIAR_PACIENTE
        ]
     };

    /**
     * Retorna a lista de recursos que o componente permite ser acessado
     *
     * @export
     * @param {Componente} componente
     * @returns {string[]}
     */
    export function listarRecursos(componente: Componente): string[] {
        return _ComponenteRecurso[componente];
    }
}
