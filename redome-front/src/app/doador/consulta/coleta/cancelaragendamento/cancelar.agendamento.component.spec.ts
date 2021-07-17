import {TarefaPedidoWorkup} from '../../workup/tarefa.workup';
import { ComponentFixture, async, TestBed } from "@angular/core/testing";
import { AppComponent } from "../../../../app.component";
import { CancelarAgendamentoPedidoColetaComponent } from "./cancelar.agendamento.component";
import { MockTarefaService } from "../../../../shared/mock/mock.tarefa.service";
import { MockActivatedRoute } from "../../../../shared/mock/mock.activated.route";
import { AppModule } from "../../../../app.module";
import { ActivatedRoute } from "@angular/router";
import { TarefaService } from "../../../../shared/tarefa.service";
import { PerfilUsuario } from "../../../../shared/enums/perfil.usuario";
import { TarefaPedidoColeta } from "../tarefa.pedido.coleta";
import { Processo } from "../../../../shared/dominio/processo";
import { TipoTarefa } from "../../../../shared/dominio/tipo.tarefa";
import { Perfil } from "../../../../shared/dominio/perfil";
import { Perfis } from "../../../../shared/enums/perfis";
import { UsuarioLogado } from "../../../../shared/dominio/usuario.logado";
import { PedidoColeta } from "../pedido.coleta";
import { DateMoment } from "../../../../shared/util/date/date.moment";
import { TiposTarefa } from "../../../../shared/enums/tipos.tarefa";
import { CentroTransplante } from "../../../../shared/dominio/centro.transplante";
import { PedidoWorkup } from "../../workup/pedido.workup";
import { Solicitacao } from "../../../solicitacao/solicitacao";
import { Observable } from "rxjs";
import { StatusTarefas } from '../../../../shared/enums/status.tarefa';
import { tarefaService, activatedRoute } from '../../../../export.mock.spec';
import { AtributoOrdenacaoDTO } from '../../../../shared/util/atributo.ordenacao.dto';
import { DoadorNacional } from '../../../doador.nacional';

describe('CancelarAgendamentoPedidoColetaComponent', () => {
    let fixture: ComponentFixture<CancelarAgendamentoPedidoColetaComponent>;
    let component: CancelarAgendamentoPedidoColetaComponent

    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000; 
        
    beforeEach(() => { 

        tarefaService.listarTarefasPaginadas = function (tipotarefa: number, perfil: PerfilUsuario, 
            idUsuarioLogado:number, pagina: number, quantidadeRegistro: number, 
            buscaPorUsuarioLogado?:boolean, atributoOrdenacaoDTO?:AtributoOrdenacaoDTO,
            statusTarefa?:number, parceiro?:number, ordenacao?:number,
            parceiroPorUsuarioLogado?: boolean): Promise<any> { 

            let tarefaPedidoWorkup: TarefaPedidoWorkup = new TarefaPedidoWorkup();
            tarefaPedidoWorkup.id = 1;
            tarefaPedidoWorkup.processo = new Processo(1);
            tarefaPedidoWorkup.tipoTarefa = new TipoTarefa(tipotarefa);
            tarefaPedidoWorkup.status = StatusTarefas.ABERTA.descricao
            tarefaPedidoWorkup.aging = "";
            tarefaPedidoWorkup.perfilResponsavel = new Perfil(Perfis.ANALISTA_WORKUP);
            
            let usuarioResponsavel: UsuarioLogado = new UsuarioLogado();
            usuarioResponsavel.username = "ANALISTA_WORKUP";
            usuarioResponsavel.nome = "ANALISTA WORKUP"
            tarefaPedidoWorkup.usuarioResponsavel = usuarioResponsavel;
            
            let pedidoWorkup: PedidoWorkup = new PedidoWorkup();
            pedidoWorkup.id = 1;
            pedidoWorkup.dataPrevistaDisponibilidadeDoador = new Date();
            pedidoWorkup.dataPrevistaDisponibilidadeDoador.toJSON = function() {
                return DateMoment.getInstance().formatWithPattern(this, "YYYY-MM-DD");
            }
            if (tipotarefa == TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA.id) {
                let centroTransplante: CentroTransplante = new CentroTransplante();
                centroTransplante.id = 1;
                centroTransplante.nome = "NOME DO CENTRO DE TRANSPLANTE";
                pedidoWorkup.centroColeta = centroTransplante;
            }
            else if (tipotarefa == TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR.id) {
                let centroTransplante: CentroTransplante = new CentroTransplante();
                centroTransplante.id = 1;
                centroTransplante.nome = "NOME DO CENTRO DE TRANSPLANTE";
                
                pedidoWorkup.id = 1;

                let solicitacao: Solicitacao = new Solicitacao();
                solicitacao.id = 1;
                let doador: DoadorNacional = new DoadorNacional();
                doador.id = 1;
                doador.dmr = 1;
                doador.nome = "NOME DO DOADOR";

                solicitacao.doador = doador;
                pedidoWorkup.solicitacao = solicitacao;
                pedidoWorkup.centroColeta = centroTransplante;
            }

            let res = JSON.parse(JSON.stringify({content: [tarefaPedidoWorkup]}));
            res.content[0]['objetoRelacaoEntidade'] = JSON.parse(JSON.stringify(pedidoWorkup));
            return Observable.of(res).toPromise();
        };
        
        fixture = TestBed.createComponent(CancelarAgendamentoPedidoColetaComponent);
        component = fixture.debugElement.componentInstance; 
        
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de evalocao foi instanciado com sucesso
     * @author Fillipe Queiroz
     */
    it('deveria criar o CancelarAgendamentoPedidoColetaComponent', () => {
        expect(component).toBeTruthy();
    });

    it("deve retornar lista de tarefas do tipo CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR", (done) => (async () => {
        activatedRoute.testQueryParams = {'tipo': TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_DOADOR.id};        
        //fixture.detectChanges();
        await component.ngOnInit();
        expect(component.paginacao.content.length).toEqual(1);
        expect(component.paginacao.content[0].pedidoWorkup.solicitacao.doador.id).toEqual(1);            
        expect(component.paginacao.content[0].pedidoWorkup.centroColeta).toBeUndefined();

    })().then(done).catch(done.fail));

    it("deve retornar lista de tarefas do tipo CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA", (done) => (async () => {
        activatedRoute.testQueryParams = {'tipo': TiposTarefa.CANCELAR_AGENDAMENTO_PEDIDO_COLETA_CENTRO_COLETA.id};
        await component.ngOnInit();
        expect(component.paginacao.content.length).toEqual(1);  
        expect(component.paginacao.content[0].pedidoWorkup.centroColeta.id).toEqual(1);
        
    })().then(done).catch(done.fail));

});