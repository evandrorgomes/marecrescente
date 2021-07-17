import { RouterModule, Routes } from '@angular/router';
import { ConsultaLaboratorioComponent } from 'app/admin/laboratorio/consulta/consulta.laboratorio.component';
import { ConsultaMedicoComponent } from 'app/admin/medico/consulta/consulta.medico.component';
import { DetalheMedicoComponent } from 'app/admin/medico/detalhe/detalhe.medico.component';
import { AnalistaBuscaCentroAvaliadorComponent } from 'app/admin/usuario/analistabusca/centroavaliador/analistabusca.centroavaliador.tarefa.component';
import { DetalheUsuarioComponent } from 'app/admin/usuario/detalhe/detalhe.usuario.component';
import { AnaliseMatchPreliminarComponent } from 'app/paciente/busca/preliminar/match.preliminar.component';
import { CadastrarBuscaPreliminarComponent } from 'app/paciente/cadastro/buscapreliminar/cadastrar.busca.preliminar.component';
import { EditarAvaliacaoComponent } from 'app/paciente/editar/avaliacao/editar.avaliacao.component';
import { EditarDiagnosticoComponent } from 'app/paciente/editar/diagnostico/editar.diagnostico.component';
import { ConsultaTransferenciaCentroComponent } from 'app/paciente/transferencia/consulta/consulta.transferencia.centro.component';
import { DetalheTransferenciaCentroComponent } from 'app/paciente/transferencia/detalhe/detalhe.transferencia.centro.component';
import { PreCadastroComponent } from 'app/precadastro/precadastro.component';
import { AdminComponent } from './admin/admin.component';
import { ConsultaCentroTransplanteComponent } from './admin/centrotransplante/consultar/consulta.centrotransplante.component';
import { DetalheCentroTransplanteComponent } from './admin/centrotransplante/detalhe/detalhe.centrotransplante.component';
import { CourierConsultaComponent } from './admin/courier/consulta/consulta.courier.component';
import { DetalheCourierComponent } from './admin/courier/detalhe/detalhe.courier.component';
import { DetalheLaboratorioComponent } from './admin/laboratorio/detalhe/detalhe.laboratorio.component';
import { ConsultaPerfilComponent } from './admin/perfil/consultar/consulta.perfil.component';
import { PerfilComponent } from './admin/perfil/perfil.component';
import { ConsultaPreCadastroMedicoComponent } from './admin/precadastromedico/consultar/consulta.precadastro.medico.component';
import { DetalhePreCadastroMedicoComponent } from './admin/precadastromedico/detalhe/detalhe.precadastro.medico.component';
import { RelatorioComponent } from './admin/relatorio/relatorio.component';
import { NovoUsuarioComponent } from './admin/usuario/detalhe/novo.usuario.component';
import { ListarUsuarioComponent } from './admin/usuario/listar.usuario.component';
import { ContatoPassivoAtualizarComponent } from './doador/atualizacao/contatopassivo/contatopassivo.atualizar.component';
import { CadastroResultadoExameInternacionalComponent } from './doador/atualizacao/visualizar/exame/resultado/cadastrar.resultado.exame.internacional.component';
import { VisualizarDoadorInternacionalComponent } from './doador/atualizacao/visualizar/visualizar.doador.internacional.component';
import { VisualizarDoadorInternacionalExameComponent } from './doador/atualizacao/visualizar/visualizar.doador.internacional.exame.component';
import { VisualizarDoadorNacionalComponent } from './doador/atualizacao/visualizar/visualizar.doador.nacional.component';
import { CadastroDoadorInternacionalComponent } from './doador/cadastro/cadastro.doador.internacional.component';
import { AgendarPedidoColetaComponent } from './doador/cadastro/coleta/agendar/agendar.pedido.coleta.component';
import { AvaliarPedidoColetaComponent } from './doador/cadastro/coleta/avaliacao/avaliar.pedido.coleta.component';
import { RecebimentoColetaCadastroInternacionalComponent } from './doador/cadastro/coleta/recebimentocoleta/recebimento.coleta.cadastro.internacional.component';
import { AgendarPedidoWorkupComponent } from './doador/cadastro/pedidoworkup/agendar.pedido.workup.component';
import { DefinirCentroColetaPedidoWorkupComponent } from './doador/cadastro/pedidoworkup/definir.centro.coleta.pedido.workup.component';
import { AprovarPlanoWorkupComponent } from './doador/cadastro/pedidoworkup/planoworkup/aprovar.plano.workup.component';
import { InformarPlanoWorkupComponent } from './doador/cadastro/pedidoworkup/planoworkup/informar.plano.workup.component';
import { VisualizarDadosCTComponent } from './doador/cadastro/pedidoworkup/visualizar.dadosct.component';
import { VisualizarDoadorComponent } from './doador/cadastro/pedidoworkup/visualizar.doador.component';
import { AvaliarResultadoWorkupInternacionalComponent } from './doador/cadastro/resultadoworkup/avaliacao/avaliar.resultado.workup.internacional.component';
import { AvaliarResultadoWorkupNacionalComponent } from './doador/cadastro/resultadoworkup/avaliacao/avaliar.resultado.workup.nacional.component';
import { CadastroPedidoAdicionalWorkupComponent } from './doador/cadastro/resultadoworkup/pedidoadicional/cadastro.pedido.adicional.workup.component';
import { CadastroResultadoWorkupInternacionalComponent } from './doador/cadastro/resultadoworkup/resultado/cadastro.resultado.workup.internacional.component';
import { CadastroResultadoWorkupNacionalComponent } from './doador/cadastro/resultadoworkup/resultado/cadastro.resultado.workup.nacional.component';
import { AvaliacaoPedidoColetaComponent } from './doador/consulta/coleta/avaliacao/avaliacao.pedido.coleta.component';
import { CancelarAgendamentoPedidoColetaComponent } from './doador/consulta/coleta/cancelaragendamento/cancelar.agendamento.component';
import { DetalheCancelarAgendamentoPedidoColetaComponent } from './doador/consulta/coleta/cancelaragendamento/detalhe/detalhe.cancelar.agendamento.component';
import { ConfirmarAgendamentoColetaComponent } from './doador/consulta/coleta/centrotransplante/confirmar.agendamento.coleta.component';
import { PedidoColetaComponent } from './doador/consulta/coleta/pedido.coleta.component';
import { PedidoColetaInternacionalComponent } from './doador/consulta/coleta/pedido.coleta.internacional.component';
import { ConsultaDoadorInternacionalComponent } from './doador/consulta/consulta.doador.internacional.component';
import { ConsultaDoadorNacionalComponent } from './doador/consulta/consulta.doador.nacional.component';
import { ConsultaContatoDoadorComponent } from './doador/consulta/contatopassivo/consulta.contato.doador.component';
import { ConsultaWorkupCentroColetaComponent } from './doador/consulta/workup/centrocoleta/consulta.workup.centro.coleta.component';
import { ConfirmarAgendamentoWorkupComponent } from './doador/consulta/workup/centrotransplante/confirmar.agendamento.workup.component';
import { DoadorPendenciaWorkupComponent } from './doador/consulta/workup/centrotransplante/doador.pendencia.workup.component';
import { ConsultaWorkupComponent } from './doador/consulta/workup/consulta.workup.component';
import { VisualizaFormularioPedidoWorkupComponent } from './doador/consulta/workup/formulario/visualiza.formulario.pedido.workup.component';
import { SimularSolicitacaoComponent } from './doador/fake/simular.solicitacao.component';
import { AnaliseMedicaComponent } from './doador/solicitacao/analisemedica/analise.medica.component';
import { DetalheAnaliseMedicaComponent } from './doador/solicitacao/analisemedica/detalhe/detalhe.analise.medica.component';
import { EnriquecimentoDoadorComponent } from './doador/solicitacao/enriquecimento/enriquecimento.doador.component';
import { ContatoFase2Component } from "./doador/solicitacao/fase2/contato.fase2.component";
import { ContatoFase3Component } from './doador/solicitacao/fase3/contato.fase3.component';
import { ResultadoPedidoIdmComponent } from './doador/solicitacao/idm/resultado/resultado.pedido.idm.component';
import { PedidoContatoSmsComponent } from './doador/solicitacao/sms/pedido.contato.sms.component';
import { ResultadoCTComponent } from './doadorinternacional/cadastro/resultado/resultado.ct.component';
import { DoadorInternacionalComponent } from './doadorinternacional/doador.internacional.component';
import { HomeComponent } from './home/home.component';
import { CadastroInvoiceApagarComponent } from "./invoice/apagar/cadastro/cadastro.invoice.apagar.component";
import { ConsultaInvoiceApagarComponent } from "./invoice/apagar/consulta/consulta.invoice.apagar.component";
import { InvoiceComponent } from "./invoice/invoice.component";
import { LaboratorioComponent } from './laboratorio/laboratorio.component';
import { LaboratorioExameComponent } from './laboratorio/laboratorioexame/laboratorio.exame.component';
import { LogEvolucaoComponent } from './logEvolucao/component/log.evolucao.component';
import { LoginComponent } from './login/login.component';
import { AvaliacaoAvaliadorComponent } from './paciente/avaliacao/avaliador/avaliacao.avaliador.component';
import { AvaliacaoMedicoComponent } from './paciente/avaliacao/medico/avaliacao.medico.component';
import { AvaliacaoCamaraTecnicaComponent } from './paciente/avaliacaocamaratecnica/avaliacao.camara.tecnica.component';
import { DetalheAvaliacaoCamaraTecnicaComponent } from './paciente/avaliacaocamaratecnica/cadastro/detalhe.avaliacao.camara.tecnica.component';
import { GenotipoDivergenteComponent } from './paciente/busca/analise/genotipodivergente/genotipo.divergente.component';
import { AnaliseMatchComponent } from './paciente/busca/analise/match.component';
import { BuscaInternacionalComponent } from './paciente/busca/buscainternacional/busca.internacional.component';
import { CancelamentoBuscaComponent } from './paciente/busca/cancelamento.busca.component';
import { ConfirmarCentroTransplanteComponent } from './paciente/busca/centroTransplante/indefinido/detalhe/confirmar.centro.transplante.component';
import { ListarDefinirCentroTransplanteComponent } from './paciente/busca/centroTransplante/indefinido/listar.definir.centro.transplante.component';
import { BuscaChecklistConsultaComponent } from './paciente/busca/checklist/buscachecklist.consulta.component';
import { PacienteBuscaComponent } from './paciente/busca/consulta/pacientebusca.component';
import { AvaliarNovaBuscaComponent } from './paciente/busca/nova/avaliar/avaliar.nova.busca.component';
import { AvaliarNovaBuscaDetalheComponent } from './paciente/busca/nova/avaliar/detalhe/avaliar.nova.busca.detalhe.component';
import { NovaBuscaComponent } from './paciente/busca/nova/nova.busca.component';
import { CadastroPedidoExameInternacionalComponent } from './paciente/busca/pedidoexame/cadastro/cadastro.pedido.exame.internacional.component';
import { PedidoExameInternacionalComponent } from './paciente/busca/pedidoexame/doadorInternacional/pedido.exame.internacional.component';
import { PedidoExameFase3Component } from './paciente/busca/pedidoexame/paciente/pedido.exame.fase3.component';
import { PedidoExamePacienteComponent } from './paciente/busca/pedidoexame/paciente/pedido.exame.paciente.component';
import { CadastroComponent } from './paciente/cadastro/cadastro.component';
import { NovaEvolucaoComponent } from './paciente/cadastro/evolucao/novaevolucao.component';
import { NovoExameComponent } from './paciente/cadastro/exame/novoexame.component';
import { AvaliarPrescricaoComponent } from './paciente/cadastro/prescricao/avaliacao/avaliar.prescricao.component';
import { PrescricaoCordaoComponent } from "./paciente/cadastro/prescricao/prescricao.cordao.component";
import { PrescricaoMedulaComponent } from './paciente/cadastro/prescricao/prescricao.medula.component';
import { ConferenciaComponent } from './paciente/conferencia/conferencia.component';
import { ConferenciaDetalheComponent } from './paciente/conferencia/conferencia.detalhe.component';
import { ConsultaAvaliacoesComponent } from './paciente/consulta/avaliacao/consulta.avaliacoes.component';
import { ConsultaComponent } from './paciente/consulta/consulta.component';
import { DetalheComponent } from './paciente/consulta/detalhe/detalhe.component';
import { ConsultarEvolucaoComponent } from './paciente/consulta/evolucao/consultar.evolucao.component';
import { ConsultarExameComponent } from './paciente/consulta/exame/consultar.exame.component';
import { ConsultarNotificacaoComponent } from './paciente/consulta/notificacao/consultar.notificacao.component';
import { PendenciaComponent } from './paciente/consulta/pendencia/pendencia.component';
import { ConsultarAutorizacaoPacienteComponent } from './paciente/consulta/prescricao/autorizacaopaciente/consultar.autorizacaopaciente.component';
import { ConsultaAvaliacoesPrescricaoComponent } from './paciente/consulta/prescricao/avaliacao/consulta.avaliacoes.prescricao.component';
import { ConsultarPrescricaoComponent } from './paciente/consulta/prescricao/consultar.prescricao.component';
import { ConsultarDistribuicaoWorkupComponent } from "./paciente/consulta/prescricao/distribuicaoworkup/consultar.distribuicao.workup.component";
import { VisualizarDetalhePrescricaoComponent } from './paciente/consulta/prescricao/visualizardetalheprescricao/visualizar.detalhe.prescricao.component';
import { EditarContatoPacienteComponent } from './paciente/editar/editar.contato.paciente.component';
import { EditarIdentificacaoDadosPessoaisComponent } from './paciente/editar/identificacaodadospessoais/editar.identificacaodadospessoais.component';
import { PacienteComponent } from './paciente/paciente.component';
import { AutenticacaoService } from './shared/autenticacao/autenticacao.service';
import { QuestionarioComponent } from './shared/questionario/questionario.component';
import { TesteComponent } from './teste/teste.component';
import { DetalheTransportadoraComponent } from './transportadora/cadastro/detalhe/detalhe.transportadora.component';
import { TransportadoraCadastroConsultaComponent } from './transportadora/cadastro/transportadora.cadastro.consulta.component';
import { ConsultaTransporteMaterialComponent } from './transportadora/tarefas/agendartransportematerial/consulta.transporte.material.component';
import { DetalheAgendarTransporteMaterialComponent } from './transportadora/tarefas/agendartransportematerial/detalhe.agendar.transporte.material.component';
import { RetiradaEntregaTransporteMaterialComponent } from './transportadora/tarefas/retiradaentregatransportematerial/retirada.entrega.transporte.material.component';
import { RetiradaEntregaTransporteMaterialInternacionalComponent } from './transportadora/tarefas/retiradaentregatransportematerialinter/retirada.entrega.transporte.material.inter.component';
import { TransportadoraComponent } from './transportadora/transportadora.component';
import { DetalharLogisticaComponent } from './doador/cadastro/logistica/detalhe/detalhar.logistica.component';
import { ConsultaLogisticaMaterialComponent } from './doador/consulta/logistica/material/consulta.logistica.material.component';
import { LogisticaMaterialInternacionalChecklistComponent } from './doador/cadastro/logistica/material/internacional/logistica.material.internacional.checklist.component';
import { LogisticaMaterialNacionalComponent } from './doador/cadastro/logistica/material/nacional/logistica.material.nacional.component';
import { LogisticaMaterialArquivoComponent } from './doador/cadastro/logistica/material/logistica.material.arquivo.component';
import { AgendarLogisticaDoadorColetaComponent } from './doador/cadastro/logistica/doador/agendar.logistica.doador.coleta.component';

const appRoutes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'precadastro', component: PreCadastroComponent, pathMatch: 'full' },
    { path: 'questionario', component: QuestionarioComponent },
    { path: 'home', component: HomeComponent, canActivate: [AutenticacaoService] },
    { path: 'teste', component: TesteComponent, canActivate: [AutenticacaoService] },
    { path: 'conferencia', canActivateChild: [AutenticacaoService], children:[
        {path: '', component: ConferenciaComponent, pathMatch: 'full'},
        {path: 'detalhe', children: [
            {path: ':idExame', component: ConferenciaDetalheComponent, pathMatch: 'full'}
        ]},
    ]},
    { path: 'admin', component: AdminComponent, canActivateChild: [AutenticacaoService], children: [
        {path: 'centrostransplante', children: [
            {path: '', component: ConsultaCentroTransplanteComponent, pathMatch: 'full'},
            {path: ':idCentroTransplante', component: DetalheCentroTransplanteComponent, pathMatch: 'full'}
        ]},
        {path: 'precadastromedico', children: [
            {path: '', component: ConsultaPreCadastroMedicoComponent, pathMatch: 'full'},
            {path: ':idPreCadastroMedico', component: DetalhePreCadastroMedicoComponent, pathMatch: 'full'}
        ]},
        {path: 'usuarios', children: [
            {path: '', component: ListarUsuarioComponent, pathMatch: 'full'},
            {path: 'centroavaliador', component: AnalistaBuscaCentroAvaliadorComponent, pathMatch: 'full'},
            {path: 'novo', component: NovoUsuarioComponent, pathMatch: 'full'},
            {path: ':idUsuario', component: DetalheUsuarioComponent, pathMatch: 'full'}
        ]},
        {path: 'medicos', children: [
            {path: '', component: ConsultaMedicoComponent, pathMatch: 'full'},
            {path: ':idMedico', component: DetalheMedicoComponent, pathMatch: 'full'}
        ]},
        {path: 'transportadoras', children: [
            {path: '', component: TransportadoraCadastroConsultaComponent, pathMatch: 'full'},
            {path: ':idTransportadora', component: DetalheTransportadoraComponent, pathMatch: 'full'}
        ]},
        {path: 'couriers', children: [
            {path: '', component: CourierConsultaComponent, pathMatch: 'full'},
            {path: ':idCourier', component: DetalheCourierComponent, pathMatch: 'full'},
        ]},
        {path: 'laboratorios', children: [
            {path: '', component: ConsultaLaboratorioComponent, pathMatch: 'full'},
            {path: ':idLaboratorio', component: DetalheLaboratorioComponent, pathMatch: 'full'},
        ]},
        { path: 'relatorios', component: RelatorioComponent, pathMatch: 'full' },
    ]},
    { path: 'perfis', component: PerfilComponent, canActivateChild: [AutenticacaoService] , children: [
        { path: '', component: ConsultaPerfilComponent, pathMatch: 'full' }
    ] },
    { path: 'doadores', canActivateChild: [AutenticacaoService],  children: [
        { path: 'smsenviado', component: PedidoContatoSmsComponent, pathMatch: 'full' },
        { path: 'solicitacaofake', component: SimularSolicitacaoComponent, pathMatch: 'full' },
        { path: 'detalheCT', component: VisualizarDadosCTComponent, pathMatch: 'full' },
        { path: 'avaliacao', component: AvaliarPedidoColetaComponent, pathMatch: 'full' },
        { path: 'detalhe', component: VisualizarDoadorComponent, pathMatch: 'full' },
        { path: 'consulta',  children: [
            { path: 'consultaContatoPassivo', component: ConsultaContatoDoadorComponent, pathMatch: 'full' },
            { path: 'nacional', component: ConsultaDoadorNacionalComponent, pathMatch: 'full' },
            { path: 'internacional', component: ConsultaDoadorInternacionalComponent, pathMatch: 'full' }
        ]},
        { path: 'atualizarDoadorNacional', children:[
            {path: 'visualizar', component: VisualizarDoadorNacionalComponent, pathMatch: 'full'}
        ]},
        { path: 'enriquecer', component: EnriquecimentoDoadorComponent, pathMatch: 'full' },
        { path: 'contato',  children: [
            { path: 'fase2', component: ContatoFase2Component, pathMatch: 'full' },
            { path: 'fase3', component: ContatoFase3Component, pathMatch: 'full' }
        ]},
        {
            path: 'analisemedica', children: [
                {path: '', component: AnaliseMedicaComponent, pathMatch: 'full'},
                {path: ':idanalisemedica', component: DetalheAnaliseMedicaComponent, pathMatch: 'full'}
            ]
        },
        { path: ':rmr', children: [
            { path: 'cadastro', component: CadastroDoadorInternacionalComponent, pathMatch: 'full' }
        ]},
        { path: ':id', children: [
            { path: '', component: ContatoPassivoAtualizarComponent, pathMatch: 'full' },
            { path: 'atualizarContatoPassivo', component: ContatoPassivoAtualizarComponent, pathMatch: 'full' },
            { path: 'atualizarDoadorInternacional', children:[
                {path: '', component: VisualizarDoadorInternacionalComponent, pathMatch: 'full'},
                {path: 'exame', children:[
                    {path:'', component: VisualizarDoadorInternacionalExameComponent, pathMatch: 'full'},
                    {path:'resultado', component: CadastroResultadoExameInternacionalComponent, pathMatch: 'full'}
                ]}
            ]},
            { path: 'genotipoDivergente', component: GenotipoDivergenteComponent, pathMatch: 'full' },
        ]},
        { path: ':tentativaContato', children: [
            { path: 'contato',  children: [
                { path: 'fase2', component: ContatoFase2Component, pathMatch: 'full' },
                { path: 'fase3', component: ContatoFase3Component, pathMatch: 'full' }
            ]},
        ]},
        {path: 'workup',  children: [
            {path: 'consulta', component: ConsultaWorkupComponent, pathMatch: 'full'},
            {path: 'pendencias', children: [
                {path: '', component: DoadorPendenciaWorkupComponent, pathMatch: 'full'},
                {path: 'detalhe', component: ConfirmarAgendamentoWorkupComponent, pathMatch: 'full'}
            ]},
            { path: 'agendar',  children: [
                { path: '', component: AgendarPedidoWorkupComponent, pathMatch: 'full' },
            ]},
            {path: 'formulario', children: [
                {path: 'visualiza', component: VisualizaFormularioPedidoWorkupComponent, pathMatch: 'full'},
            ]},
            {path: 'centrocoleta', children: [
                {path: 'consulta', component: ConsultaWorkupCentroColetaComponent, pathMatch: 'full'},
                {path: 'definir', component: DefinirCentroColetaPedidoWorkupComponent, pathMatch: 'full'},
            ]},
            {path: 'centrotransplante', children: [
                {path: 'informar', component: InformarPlanoWorkupComponent, pathMatch: 'full'},
                {path: 'aprovar', component: AprovarPlanoWorkupComponent, pathMatch: 'full'},
            ]},
            {path: 'logistica', children: [
                {path: '', component: ConsultaLogisticaMaterialComponent, pathMatch: 'full'},
              //  {path: 'internacional', component: LogisticaDoadorInternacionalComponent, pathMatch: 'full'},
                {path: 'detalhar', component: DetalharLogisticaComponent, pathMatch: 'full'},
            ]},
            { path: 'resultado', children: [
                {path: 'cadastro', component: CadastroResultadoWorkupNacionalComponent, pathMatch: 'full'},
                { path: 'avaliacao', children: [
                   { path: 'nacional', component: AvaliarResultadoWorkupNacionalComponent, pathMatch: 'full' },
                   { path: 'internacional', component: AvaliarResultadoWorkupInternacionalComponent, pathMatch: 'full' }
                ]}
            ]},
            {path: 'coletas', children: [
                {path: 'agendar', component: PedidoColetaComponent, pathMatch: 'full'},
                {path: 'agendarinternacional', component: PedidoColetaInternacionalComponent, pathMatch: 'full'},
                {path: 'avaliacao', children: [
                    {path: '', component: AvaliacaoPedidoColetaComponent, pathMatch: 'full'} ,
                    {path: ':idAvaliacaoResultadoWorkup',  children: [
                        {path: '', component: AvaliarPedidoColetaComponent, pathMatch: 'full'}
                    ]}
                ]},
                {path: 'cancelaragendamento', component: CancelarAgendamentoPedidoColetaComponent, pathMatch: 'full'}
            ]},
            {path: 'coleta', children: [
                {path: 'agendar', component: AgendarPedidoColetaComponent, pathMatch: 'full'},
                {path: ':pedidoId', children: [
                    {path: 'cancelaragendamento', component: DetalheCancelarAgendamentoPedidoColetaComponent, pathMatch: 'full'},
                    {path: 'disponibilidade', component: ConfirmarAgendamentoColetaComponent, pathMatch: 'full'}
                ]},
            ]},
            {path: 'recebimentocoleta', children: [
              {path: '', component: RecebimentoColetaCadastroInternacionalComponent, pathMatch: 'full'}
            ]},
            {path: 'distribuicao', children: [
               {path: 'consulta', component: ConsultarDistribuicaoWorkupComponent, pathMatch: 'full'},
            ]},
            {path: 'exameadicional', children: [
              {path: 'cadastro', component: CadastroPedidoAdicionalWorkupComponent, pathMatch: 'full'},
           ]},
        ]},
        {path: 'coleta', children: [
            {path: 'logistica', children: [
                {path: '', component: ConsultaLogisticaMaterialComponent, pathMatch: 'full'},
                {path: ':pedidoId', component: DetalharLogisticaComponent, pathMatch: 'full'}
            ]},
        ]},
        {path: 'logistica', children: [
          {path: ':pedidoId', children: [
              {path: 'doador', component: AgendarLogisticaDoadorColetaComponent, pathMatch: 'full'}
          ]},
        ]},
        {path:'material',children:[
            {path: 'logistica', children: [
                {path: '', component: ConsultaLogisticaMaterialComponent, pathMatch: 'full'},
                {path: 'internacional', component: LogisticaMaterialInternacionalChecklistComponent, pathMatch: 'full'},
                {path: ':pedidoId', children: [
                    {path: 'nacional', component: LogisticaMaterialNacionalComponent, pathMatch: 'full'},
                    {path: 'aereo', component: LogisticaMaterialArquivoComponent, pathMatch: 'full'},
                    //{path: 'internacional', component: LogisticaMaterialInternacionalChecklistComponent, pathMatch: 'full'}
                    //{path: 'internacional', component: LogisticaMaterialInternacionalComponent, pathMatch: 'full'}
                ]}
            ]}
        ]},
    ]},
    { path: 'pacientes', component: PacienteComponent, canActivateChild: [AutenticacaoService], children: [
            { path: '', component: ConsultaComponent, pathMatch: 'full' },
            { path: 'cadastro', component: CadastroComponent, pathMatch: 'full' },
			{ path: 'notificacoes', component: ConsultarNotificacaoComponent, pathMatch: 'full' },
            {path: 'avaliacoes', children:[
                {path: '', component: ConsultaAvaliacoesComponent, pathMatch: 'full'}
            ]},
            {path: 'transferencias', children:[
                {path: '', component: ConsultaTransferenciaCentroComponent, pathMatch: 'full'},
                {path: ':idPedidoTransferenciaCentro', component: DetalheTransferenciaCentroComponent, pathMatch: 'full'}
            ]},
            { path: 'busca', children: [
                {path: "", component: PacienteBuscaComponent, pathMatch: 'full' },
                { path: 'avaliacao', component: AvaliarNovaBuscaComponent, pathMatch: 'full'}
            ]},
            { path: 'buscachecklist', children: [
                {path: "", component: BuscaChecklistConsultaComponent, pathMatch: 'full' }
            ]},
            { path: 'centrosTransplante', children: [
                { path: 'definir', children: [
                    {path: "", component: ListarDefinirCentroTransplanteComponent, pathMatch: 'full' },
                    {path: ":tarefaId", component: ConfirmarCentroTransplanteComponent, pathMatch: 'full' }
                ]}
            ]},
            { path: 'avaliacao', children: [
                {path: '', component: AvaliacaoAvaliadorComponent, pathMatch: 'full'},
                {path: 'pendencias', component: PendenciaComponent, pathMatch: 'full'}
            ] },
            { path: 'avaliacaocamaratecnica', children: [
                {path: '', component: AvaliacaoCamaraTecnicaComponent, pathMatch: 'full'},
                {path: ':idAvaliacaoCamaraTecnica', component: DetalheAvaliacaoCamaraTecnicaComponent, pathMatch: 'full'}

            ] },
            { path: 'buscapreliminar', children: [
                {path: '', component: CadastrarBuscaPreliminarComponent, pathMatch: 'full'},
                {path: ':idBuscaPreliminar', children: [
                    {path: 'matchpreliminar', component: AnaliseMatchPreliminarComponent, pathMatch: 'full'},
					{path: 'paciente', component: CadastroComponent, pathMatch: 'full'}
                ]}
            ] },
            { path: ':idPaciente', children: [
                { path: '', component: DetalheComponent, pathMatch: 'full' },
                { path: 'evolucoes', children: [
                    { path: '', component: ConsultarEvolucaoComponent, pathMatch: 'full' },
                    { path: 'nova', component: NovaEvolucaoComponent, pathMatch: 'full' },
                    { path: ':idEvolucao', children: [
                        {path: '', component: ConsultarEvolucaoComponent, pathMatch: 'full'}]
                    }]
                },
                { path: 'exames', children: [
                    { path: '', component: ConsultarExameComponent, pathMatch: 'full' },
                    { path: 'novo', component: NovoExameComponent, pathMatch: 'full' },
                    { path: ':idExame', children: [
                        {path: '', component: ConsultarExameComponent, pathMatch: 'full'}]
                    }]
                },
                { path: 'avaliacaoMedico', component: AvaliacaoMedicoComponent, pathMatch: 'full' },
                { path: 'avaliacaoAvaliador', component: AvaliacaoAvaliadorComponent, pathMatch: 'full' },
                { path: 'editar', children: [
                    { path: 'contatos', component: EditarContatoPacienteComponent, pathMatch: 'full' },
                    { path: 'identificacaoDadosPessoais', component: EditarIdentificacaoDadosPessoaisComponent, pathMatch: 'full' },
                    { path: 'diagnostico', component: EditarDiagnosticoComponent, pathMatch: 'full' },
                    { path: 'avaliacao', component: EditarAvaliacaoComponent, pathMatch: 'full' }]
                },
                { path: 'cancelamentoBusca', component: CancelamentoBuscaComponent, pathMatch: 'full' },
                { path: 'matchs', component: AnaliseMatchComponent, pathMatch: 'full' },
                { path: 'prescricao', children: [
                    { path: 'medula', component: PrescricaoMedulaComponent, pathMatch: 'full' },
                    { path: 'cordao', component: PrescricaoCordaoComponent, pathMatch: 'full' }
                ]},
                { path: 'logs', children: [
                    { path: '', component: LogEvolucaoComponent, pathMatch: 'full' }
                ]},
                { path: 'pedidoexamefase3', component: PedidoExameFase3Component, pathMatch: 'full' },
                { path: 'pedidoexamepaciente', component: PedidoExamePacienteComponent, pathMatch: 'full' },
                { path: 'busca', children: [
                    { path: 'nova', component: NovaBuscaComponent, pathMatch: 'full' },
                    { path: 'avaliacao', children: [
                        {path: ":idAvaliacao", component: AvaliarNovaBuscaDetalheComponent, pathMatch: 'full' }
                    ]}
                ]},
                { path: 'buscainternacional', component: BuscaInternacionalComponent, pathMatch: 'full' }
            ]}
    ]},
    { path: 'prescricoes', component: PacienteComponent, canActivateChild: [AutenticacaoService], children: [
        { path: '', component: ConsultarPrescricaoComponent, pathMatch: 'full' },
        {path: 'avaliacao', children:[
            { path: '', component: ConsultaAvaliacoesPrescricaoComponent, pathMatch: 'full' },
            { path: ':idAvaliacaoPrescricao', children:[
                { path: '', component: AvaliarPrescricaoComponent, pathMatch: 'full' },
            ]}
        ]},
        {path: 'autorizacaopaciente', children:[
            { path: '', component: ConsultarAutorizacaoPacienteComponent, pathMatch: 'full' },
        ]},
        {path: ':idPrescricao', children:[
            { path: '', component: VisualizarDetalhePrescricaoComponent, pathMatch: 'full' },
        ]},
    ]},
    { path: 'transportadoras', component: TransportadoraComponent, canActivateChild: [AutenticacaoService], children: [
        { path: '', component: ConsultaTransporteMaterialComponent, pathMatch: 'full' },
        { path: 'retiradaentrega', component: RetiradaEntregaTransporteMaterialComponent, pathMatch: 'full' },
        { path: 'retiradaentregainternacional', component: RetiradaEntregaTransporteMaterialInternacionalComponent, pathMatch: 'full' },
        { path: ':idPedidoTransporte', children: [
            { path: 'agendar', component: DetalheAgendarTransporteMaterialComponent, pathMatch: 'full' }
        ]},
    ]},
    { path: 'laboratorios', component: LaboratorioComponent, canActivateChild: [AutenticacaoService], children: [
        { path: '', component: LaboratorioExameComponent, pathMatch: 'full' },
    ]},
    { path: 'matchs', children: [
        { path: ':idMatch', children: [
            { path: 'internacional', children: [
                { path: 'pedidofase2', component: PedidoExameInternacionalComponent, pathMatch: 'full' }
            ]}
        ]},
    ]},
    { path: 'doadoresinternacionais', component: DoadorInternacionalComponent, canActivateChild: [AutenticacaoService], children: [
        { path: 'pedidoexame', children: [
            { path: ':idSolicitacao', children: [
                { path: 'resultadoExame', component: CadastroPedidoExameInternacionalComponent, pathMatch: 'full' },
                { path: 'resultadoCT', component: ResultadoCTComponent, pathMatch: 'full' },
                { path: 'resultadoIDM', component: ResultadoPedidoIdmComponent, pathMatch: 'full' }
            ]},
        ]},

        { path: 'workup', children: [
            { path: 'resultado', children: [
               { path: 'cadastrar', component: CadastroResultadoWorkupInternacionalComponent, pathMatch: 'full' },
            ]}
        ]},
        { path: '', component: ConsultaTransporteMaterialComponent, pathMatch: 'full' },
    ]},
    { path: 'invoices',  component: InvoiceComponent, canActivateChild: [AutenticacaoService], children: [
        { path: 'apagar', children: [
                { path: '', component: ConsultaInvoiceApagarComponent, pathMatch: 'full' },
                { path: 'novo', component: CadastroInvoiceApagarComponent, pathMatch: 'full' },
                { path: ':id', component: CadastroInvoiceApagarComponent, pathMatch: 'full' }
          ]}
    ]},
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: '**', redirectTo: 'login' }
];

export const routing = RouterModule.forRoot(appRoutes);
