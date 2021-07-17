import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormGroup } from '@angular/forms';
import { AppComponent } from '../../../app.component';
import { PacienteConstantes } from '../../paciente.constantes';
import { DadosPessoaisComponent } from './dadospessoais.component';

/**
 * Classe Spec para teste da classe Dados Pessoais Component
 * @author Fillipe Queiroz
 */
describe('DadosPessoaisComponent', () => {
    let fixture: ComponentFixture<DadosPessoaisComponent>;
    let component: DadosPessoaisComponent


    beforeEach((done) => (async () => { 
        fixture = TestBed.createComponent(DadosPessoaisComponent);
        component = fixture.componentInstance;
        await component.ngOnInit();
    })().then(done).catch(done.fail));
    
    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de cadastro foi instanciado com sucesso
     * @author Fillipe Queiroz
     */
    it('deveria criar o DadosPessoaisComponent', () => {
        expect(component).toBeTruthy();
    });

    it('Sexo é obrigatório', () => {
        component.validateForm();
        expect(component.formErrors['sexo']).toEqual('O campo Sexo é obrigatório');
    });

    it('ABO é obrigatório', () => {
        component.validateForm();
        expect(component.formErrors['abo']).toEqual('O campo ABO é obrigatório');

    });

    it('Raça é obrigatório', async(() => {
        component.validateForm();
        expect(component.formErrors['raca']['id']).toEqual('O campo Raça é obrigatório');
    }));

    it('Etnia é obrigatório, se Raça selecionada for Indígena', () => {
        var groupRaca = <FormGroup>component.dadosPessoaisForm.controls['raca'];
        groupRaca.controls['id'].setValue(PacienteConstantes.INDIGENA_ID);
        component.retornarTrueSeRacaIndigena();
        component.validateForm();
        expect(component.formErrors['etnia']['id']).toEqual('O campo Etnia é obrigatório');
    });

    it('Nacionalidade é obrigatório', () => {
        var groupPais = <FormGroup> component.dadosPessoaisForm.controls['pais'];
        groupPais.controls['id'].setValue('');
        component.validateForm();
        expect(component.formErrors['pais']['id']).toEqual('O campo Nacionalidade é obrigatório');
    });

    it('Naturalidade é obrigatório, se país selecionado for Brasil', () => {
        var groupPais = <FormGroup>component.dadosPessoaisForm.controls['pais'];
        groupPais.controls['id'].setValue(PacienteConstantes.BRASIL_ID);
        component.validateForm();
        expect(component.formErrors['uf']['sigla']).toEqual('O campo Naturalidade é obrigatório');
    });

    it('Naturalidade NÃO é obrigatória para países estrangeiros', () => {

        var groupPais = <FormGroup>component.dadosPessoaisForm.controls['pais'];
        groupPais.controls['id'].setValue(30);
        component.retornarTrueSeNacionalidadeBrasil();
        component.validateForm();
        expect(component.formErrors['uf']['sigla']).toEqual('');
    });

});