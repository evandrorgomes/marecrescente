import { ComponentFixture, TestBed } from "@angular/core/testing";
import { PedidoExameFase3Component } from "../../../../paciente/busca/pedidoexame/paciente/pedido.exame.fase3.component";
import { activatedRoute, pedidoExameService, buscaService } from "../../../../export.mock.spec";
import { Observable } from "rxjs/Observable";
import { StatusPedidosExame } from "../../../../shared/enums/status.pedidos.exame";
import { By } from "@angular/platform-browser";
import { ResultadoPedidoIdmComponent } from "./resultado.pedido.idm.component";

describe('ResultadoIdmComponent', () => {
    let fixture: ComponentFixture<ResultadoPedidoIdmComponent>;
    let component: ResultadoPedidoIdmComponent;
    
    beforeEach(() => {
        fixture = TestBed.createComponent(ResultadoPedidoIdmComponent);
        component = fixture.debugElement.componentInstance;
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente foi instanciado com sucesso
     */
    it('deveria criar o ResultadoPedidoIdmComponent', () => {
        expect(component).toBeTruthy();
    });

});