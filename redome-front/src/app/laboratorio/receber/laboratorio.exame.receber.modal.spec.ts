import { DateTypeFormats } from '../../shared/util/date/date.type.formats';
import { AppComponent } from '../../app.component';
import { pedidoExameService } from '../../export.mock.spec';
import { PedidoExame } from '../pedido.exame';
import { Observable } from 'rxjs';
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { LaboratorioExameReceberModal } from './laboratorio.exame.receber.modal';
import { DateMoment } from '../../shared/util/date/date.moment';
/**
 * Testes do componente de recebimento coleta.
 */
describe('LaboratorioExameReceberModal', () => {

    let fixture: ComponentFixture<LaboratorioExameReceberModal>;
    let component: LaboratorioExameReceberModal;
    
    beforeEach( (done) => (async () => {
        pedidoExameService.receberPedidoExame = function (pedidoId: number, pedidoExame:PedidoExame): Promise<any> {
            let retorno = {
                mensagem: "Coleta recebida com sucesso."
            }
            return Observable.of(retorno).toPromise();
        }
        fixture = TestBed.createComponent(LaboratorioExameReceberModal);
        component = fixture.componentInstance;        
        await component.ngOnInit();   
    })().then(done).catch(done.fail));


    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de LaboratorioExameReceberModal foi instanciado com sucesso
     * @author Filipe Paes
     */
    it('deveria criar o LaboratorioExameReceberModal', () => {
        expect(component).toBeTruthy();
    });

    it('deve validar se a data de coleta foi preenchida', (done) => (async () => {
        await component.ngOnInit();

        component.form().get('dataColeta').setValue(null);
        component.form().get('dataRecebimento').setValue("14/01/2018");
        
        component.salvarRecebimento();
        expect(component.formErrors["dataColeta"]).toEqual("O campo Data Coleta é obrigatório");
        
    })().then(done).catch(done.fail));


    it('deve validar se a data de recebimento foi preenchida', (done) => (async () => {
        await component.ngOnInit();

        component.form().get('dataColeta').setValue("14/01/2018");
        component.form().get('dataRecebimento').setValue(null);
        
        component.salvarRecebimento();
        expect(component.formErrors["dataRecebimento"]).toEqual("O campo Data Recebimento é obrigatório");
        
    })().then(done).catch(done.fail));


    it('deve validar se a data de coleta é menor que a atual', (done) => (async () => {
        await component.ngOnInit();

        component.form().get('dataColeta').setValue("14/01/2200");
        component.form().get('dataRecebimento').setValue("14/01/2018");
        
        component.salvarRecebimento();
        expect(component.formErrors["dataColeta"]).toEqual("Data Coleta deve ser menor ou igual a data atual.");
        
    })().then(done).catch(done.fail));


    it('deve validar se a data de recebimento é menor que a atual', (done) => (async () => {
        await component.ngOnInit();

        component.form().get('dataColeta').setValue("14/01/2018");
        component.form().get('dataRecebimento').setValue("14/01/2200");
        
        component.salvarRecebimento();
        expect(component.formErrors["dataRecebimento"]).toEqual("Data Recebimento deve ser menor ou igual a data atual.");
        
    })().then(done).catch(done.fail));


});