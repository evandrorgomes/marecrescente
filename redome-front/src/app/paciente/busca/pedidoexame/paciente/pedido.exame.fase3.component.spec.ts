import { TiposExame } from '../../../../shared/enums/tipos.exame';
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { PedidoExameFase3Component } from "./pedido.exame.fase3.component";
import { activatedRoute, pedidoExameService, buscaService } from "../../../../export.mock.spec";
import { Observable } from "rxjs/Observable";
import { StatusPedidosExame } from "../../../../shared/enums/status.pedidos.exame";
import { By } from "@angular/platform-browser";
import { PedidoExamePacienteComponent } from './pedido.exame.paciente.component';

describe('PedidoExamePacienteComponent', () => {
    let fixture: ComponentFixture<PedidoExamePacienteComponent>;
    let component: PedidoExamePacienteComponent
    
    beforeEach(() => {
        fixture = TestBed.createComponent(PedidoExamePacienteComponent);
        component = fixture.debugElement.componentInstance;

        activatedRoute.testParams = {'idPaciente': 1};
        activatedRoute.testQueryParams = {'buscaId': 1};    
        
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de evalocao foi instanciado com sucesso
     */
    it('deveria criar o PedidoExamePacienteComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deve exibir a mensagem "Não existe pedido de fase 3(CT) para este paciente." se não exitir pedido de exame ', (done) => (async () => {
        buscaService.obterUltimoPedidoExame = function(rmr: number): Promise<any> {
            let res = {
                'rmr': rmr,
                municipioEnderecoPaciente: 'Municipio do endereco do paciente',
                ufEnderecoPaciente: 'UF do endereco do paciente',
                idLaboratorioDePrefencia: "2"                
            }

            return Observable.of(res).toPromise();
        }
        pedidoExameService.listarTarefasDePedidoDeExame = function(idBusca: number, pagina: number, quantidadeRegistros: number): Promise<any> {
            let res = {
                content:[]
                ,totalElements:0
            }
            return Observable.of(res).toPromise();
        };

        await component.ngOnInit();
        fixture.detectChanges();
        let mensagem = fixture.debugElement.query(By.css("#mensagemnenhumpedido"))
        expect(mensagem).toBeDefined();
    })().then(done).catch(done.fail));

    it('não deve exibir a mensagem "Não existe pedido de fase 3(CT) para este paciente." se existir pedido de exame ', (done) => (async () => {

        buscaService.obterUltimoPedidoExame = function(rmr: number): Promise<any> {
            let res = {
                'rmr': rmr,
                municipioEnderecoPaciente: 'Municipio do endereco do paciente',
                ufEnderecoPaciente: 'UF do endereco do paciente',
                idLaboratorioDePrefencia: "2",
                pedidoExame: {
                    id: '1',
                    tipoExame: {
                        descricao: "Tipificação HLA de alta resolução"
                    },
                    laboratorio: {
                        id: '2',
                        nome: 'Laboraotrio CT'
                    },
                    statusPedidoExame: {
                        id: StatusPedidosExame.AGUARDANDO_AMOSTRA,
                        descricao: 'AGUARDANDO AMOSTRA'
                    },
                    solicitacao: {
                        id: '1'
                    }
                }
            }

            return Observable.of(res).toPromise();
        };
        pedidoExameService.listarTarefasDePedidoDeExame = function(idBusca: number, pagina: number, quantidadeRegistros: number): Promise<any> {
            let res = {
                content:[]
                ,totalElements:0
            }
            return Observable.of(res).toPromise();
        };

        await component.ngOnInit();
        fixture.detectChanges();
        
        let mensagem = fixture.debugElement.query(By.css("#mensagemnenhumpedido"));
        expect(mensagem == null).toBe(true);
        
        
    
        
    })().then(done).catch(done.fail));

});