import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RetiradaEntregaTransporteMaterialComponent } from "./retirada.entrega.transporte.material.component";
import { Observable } from "rxjs/Observable";
import { tarefaService, pedidoTransporteService } from "../../../export.mock.spec";
import { PerfilUsuario } from "../../../shared/enums/perfil.usuario";
import { AtributoOrdenacaoDTO } from "../../../shared/util/atributo.ordenacao.dto";

describe('RetiradaEntregaTransporteMaterialComponent', () => {
    let fixture: ComponentFixture<RetiradaEntregaTransporteMaterialComponent>;
    let component: RetiradaEntregaTransporteMaterialComponent
    
    beforeEach(() => {
        fixture = TestBed.createComponent(RetiradaEntregaTransporteMaterialComponent);
        component = fixture.debugElement.componentInstance;        
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de RetiradaEntregaTransporteMaterialComponent foi instanciado com sucesso
     * @author Bruno Sousa
     */
    it('deveria criar o RetiradaEntregaTransporteMaterialComponent', () => {
        expect(component).toBeTruthy();
    });

});