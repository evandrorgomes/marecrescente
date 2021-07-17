import { ComponentFixture, TestBed } from "@angular/core/testing";
import { RetiradaEntregaTransporteMaterialInternacionalComponent } from "./retirada.entrega.transporte.material.inter.component";

describe('RetiradaEntregaTransporteMaterialComponent', () => {
    let fixture: ComponentFixture<RetiradaEntregaTransporteMaterialInternacionalComponent>;
    let component: RetiradaEntregaTransporteMaterialInternacionalComponent
    
    beforeEach(() => {
        fixture = TestBed.createComponent(RetiradaEntregaTransporteMaterialInternacionalComponent);
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