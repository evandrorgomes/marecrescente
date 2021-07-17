import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs';
import { dominioService } from '../../../../export.mock.spec';
import { AppComponent } from '../../../../app.component';
import { ConsultaResultadoWorkupNacionalComponent } from './consulta.resultado.workup.nacional.component';


describe('ResultadoWorkupComponent', () => {
    let fixtureApp: ComponentFixture<AppComponent>;
    let app: AppComponent;
    let fixture: ComponentFixture<ConsultaResultadoWorkupNacionalComponent>;
    let component: ConsultaResultadoWorkupNacionalComponent;

    const createComponent = () => {        
        fixture = TestBed.createComponent(ConsultaResultadoWorkupNacionalComponent);
        component = fixture.componentInstance;
        component.ngOnInit();
    };

    beforeEach(() => {
        dominioService.listarConfiguracoes = function(lista: String[]): Promise<any> {
            let _RES_CONFIGURACAO = [
                {
                    chave: 'extensaoArquivoResultadoWorkup',
                    valor: 'application/pdf'
                },
                {
                    chave: 'tamanhoArquivoResultadoWorkupEmByte',
                    valor: '5242880'
                },
                {
                    chave: 'quantidadeMaximaArquivosResultadoWorkup',
                    valor: '30'
                }
                
            ];

            return Observable.of(_RES_CONFIGURACAO).toPromise();
        }
        createComponent();
    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de motivo foi instanciado com sucesso
     * @author Filipe Paes
     */
    it('deveria criar o ResultadoWorkupComponent', () => {
        expect(component).toBeTruthy();
    });

    it('[FALHA] Centro obrigatório', async(() => {
        component.resultadoForm.get("centro").setValue(null);
        component.validateForm();
        expect(component.formErrors['centro']).toEqual('O campo Centro é obrigatório');
    }));

    it('[SUCESSO] Centro obrigatório', async(() => {
        component.resultadoForm.get("centro").setValue(1);
        component.validateForm();
        expect(component.formErrors['centro']).toEqual('');
    }));


    it('[FALHA] DMR obrigatório', async(() => {
        component.resultadoForm.get("dmr").setValue(null);
        component.validateForm();
        expect(component.formErrors['dmr']).toEqual('O campo DMR é obrigatório');
    }));

    it('[SUCESSO] DMR obrigatório', async(() => {
        component.resultadoForm.get("dmr").setValue(12);
        component.validateForm();
        expect(component.formErrors['dmr']).toEqual('');
    }));

});