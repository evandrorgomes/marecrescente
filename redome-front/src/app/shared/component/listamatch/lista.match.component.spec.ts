import { Observable } from "rxjs";
import { ComponentFixture, TestBed } from "@angular/core/testing";
import { AppComponent } from "../../../app.component";
import { ListaMatchComponent } from "./lista.match.component";
import { async } from "@angular/core/testing";
import { AppModule } from "../../../app.module";

describe('ListaMatchComponent', () => {
    let fixture: ComponentFixture<ListaMatchComponent>;
    let component: ListaMatchComponent

    jasmine.DEFAULT_TIMEOUT_INTERVAL = 100000;

    beforeEach(() => { 
        fixture = TestBed.createComponent(ListaMatchComponent);
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
    it('deveria criar o ListaMatchComponent', () => {
        expect(component).toBeTruthy();
    });

});