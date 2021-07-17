import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from "rxjs";
import { CadastrarBuscaPreliminarComponent } from 'app/paciente/cadastro/buscapreliminar/cadastrar.busca.preliminar.component';
import { PacienteUtil } from 'app/shared/paciente.util';
import { LocusExame } from 'app/paciente/cadastro/exame/locusexame';
import { LocusVO } from 'app/paciente/cadastro/exame/locusvo';

/**
 * Classe para teste da Classe de ExameComponent
 * @author Filipe Paes
 */
describe('CadastrarBuscaPreliminarComponent', () => {
    let fixture: ComponentFixture<CadastrarBuscaPreliminarComponent>;
    let component: CadastrarBuscaPreliminarComponent;

    beforeEach((done) => (async () => { 

        fixture = TestBed.createComponent(CadastrarBuscaPreliminarComponent);
        component = fixture.debugElement.componentInstance;

        await component.ngOnInit();

    })().then(done).catch(done.fail));

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
   * Verifica se o componente foi instanciado com sucesso
   * @author Bruno Sousa
   */
    it('deveria criar o CadastrarBuscaPreliminarComponent', () => {
        expect(component).toBeTruthy();
    });

    it('deve exibir mensagem de obrigatoriedade para todos os campos', (done) => (async () => { 
        await component.validateForm();
        expect(component.formErrors['dataNascimento']).toEqual('O campo Data de Nascimento é obrigatório');
        expect(component.formErrors['abo']).toEqual('O campo ABO é obrigatório');
        expect(component.formErrors['peso']).toEqual('O campo Peso (Kg) é obrigatório');
        expect(component.formErrors['nome']).toEqual('O campo Nome é obrigatório');
        expect(component.hlaComponent.retornarMensagemErro()).toEqual('O HLA é obrigatório');
        
    })().then(done).catch(done.fail));

    it('Nao deve exibir mensagem de obrigatoriedade', async(() => {
        component.form().controls["dataNascimento"].setValue( new Date()  );
        component.form().controls["abo"].setValue("A+");
        component.form().controls["peso"].setValue(PacienteUtil.arredondar(String(80.0), 1));
        component.form().controls["nome"].setValue("Nome do paciente");
        let exames: LocusExame[] = [
            new LocusExame(LocusVO.LOCI_A, "01:01", "-"),
            new LocusExame(LocusVO.LOCI_B, "15:01", "-"),
            new LocusExame(LocusVO.LOCI_C, "01:02", "-"),
            new LocusExame(LocusVO.LOCI_DRB1, "15:01", "-"),
            new LocusExame(LocusVO.LOCI_DQB1, "02:01", "-")
        ];

        component.hlaComponent.setValue(exames);
        component.validateForm();
        expect(component.formErrors['dataNascimento']).toEqual('');
        expect(component.formErrors['abo']).toEqual('');
        expect(component.formErrors['peso']).toEqual('');
        expect(component.formErrors['nome']).toEqual('');
        expect(component.hlaComponent.retornarMensagemErro()).toEqual(null);
    }));

    
});