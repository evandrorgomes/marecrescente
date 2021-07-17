import { solicitacaoService } from './../../../../export.mock.spec';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { activatedRoute, pedidoExameService } from "../../../../export.mock.spec";
import { Observable } from "rxjs/Observable";
import { StatusPedidosExame } from "../../../../shared/enums/status.pedidos.exame";
import { By } from "@angular/platform-browser";
import { ModalCancelarPedidoExameFase3Component } from "./modal.cancelar.pedido.exame.fase3.component";
import { IModalComponent } from "../../../../shared/modal/factory/i.modal.component";

describe('ModalCancelarPedidoExameFase3Component', () => {
    let fixture: ComponentFixture<ModalCancelarPedidoExameFase3Component>;
    let component: ModalCancelarPedidoExameFase3Component

    class modalComponentTest extends IModalComponent {
        public hide() {            
        }
    }
    
    beforeEach(() => {
        fixture = TestBed.createComponent(ModalCancelarPedidoExameFase3Component);
        component = fixture.debugElement.componentInstance;
        component.data = {
            idPedidoExame: 1
        };
        component.target = new modalComponentTest();

        pedidoExameService.cancelarPedidoExame = function(idPedido: number, motivo: string): Promise<any> {
            return Observable.of("Pedido de exame cancelado com sucesso").toPromise();
        }
        solicitacaoService.cancelarFase3Paciente = function(idSolicitacao: number, justificativa: string){
            return Observable.of("Solicitacao cancelada com sucesso").toPromise();
        }
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de evalocao foi instanciado com sucesso
     */
    it('deveria criar o NodalCancelarPedidoExameFase3Component', () => {
        expect(component).toBeTruthy();
    });

    it('deve exibir a mensagem de erro se a justificativa não estiver preenchida.', (done) => (async () => {

        await component.ngOnInit();
        await component.confirmar();
        
        expect(component.mensagemErroJustificativa).toEqual("O campo Justificativa é obrigatório");

    })().then(done).catch(done.fail));

    it('Não deve exibir a mensagem de erro se a justificativa  estiver preenchida.', (done) => (async () => {

        await component.ngOnInit();
        component.justificativa = "Teste da Justificativa";
        component.close = function(target: IModalComponent): void {}

        await component.confirmar();
        
        expect(component.mensagemErroJustificativa).toBeNull();

    })().then(done).catch(done.fail));

});