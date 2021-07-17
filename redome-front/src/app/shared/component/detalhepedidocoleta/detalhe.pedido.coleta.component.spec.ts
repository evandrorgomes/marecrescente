import { ComponentFixture, TestBed, async } from "@angular/core/testing";
import { AppComponent } from "../../../app.component";
import { DetalhePedidoColetaComponent } from "./detalhe.pedido.coleta.component";
import { AppModule } from "../../../app.module";

describe('DetalhePedidoColetaComponent', () => {

    let fixture: ComponentFixture<DetalhePedidoColetaComponent>;
    let component: DetalhePedidoColetaComponent

    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;

    const createComponent = () => {        
        fixture = TestBed.createComponent(DetalhePedidoColetaComponent);
        component = fixture.componentInstance;
    };

    beforeEach(() => {    
        createComponent(); 
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente foi instanciado com sucesso
     * @author Bruno Sousa
     */
    it('[SUCESSO] deveria criar o component DetalhePedidoColetaComponent', () => {        
        expect(component).toBeDefined();
    });

});