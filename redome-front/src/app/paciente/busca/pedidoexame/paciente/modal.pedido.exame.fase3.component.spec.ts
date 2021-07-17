import { ComponentFixture, TestBed } from "@angular/core/testing";
import { laboratorioService, solicitacaoService } from "../../../../export.mock.spec";
import { PedidoExame } from '../../../../laboratorio/pedido.exame';
import { ModalPedidoExameFase3Component } from "./modal.pedido.exame.fase3.component";
import { Laboratorio } from '../../../../shared/dominio/laboratorio';
import { IModalComponent } from "../../../../shared/modal/factory/i.modal.component";
import { Observable } from "rxjs/Observable";

describe('ModalPedidoExameFase3Component', () => {
    let fixture: ComponentFixture<ModalPedidoExameFase3Component>;
    let component: ModalPedidoExameFase3Component

    class modalComponentTest extends IModalComponent {

        public hide() {            
        }
    }

    class modalTeste {

        pediuExame:boolean = false;
    }
    
    beforeEach(() => {
        fixture = TestBed.createComponent(ModalPedidoExameFase3Component);
        component = fixture.debugElement.componentInstance;        

        component.close = function(target: IModalComponent) {}
        component.target = new modalComponentTest();
        component.target.target = new modalTeste(); 

        laboratorioService.listarLaboratoriosCTExame = function(): Promise<any> {
            let res = [{
                "id": "2",
                "nome": "Laboratório CT",
                "quantidadeExamesCT": "100",
                "quantidadeAtual": "10",
                "enderecos": [{
                    "municipio": "Rio de Janeiro",
                    "uf": "RJ"
                }]
            }, 
            {
                "id": "3",
                "nome": "3 Laboratório CT",
                "quantidadeExamesCT": "100",
                "quantidadeAtual": "50",
                "enderecos": [{
                    "municipio": "Rio de Janeiro",
                    "uf": "RJ"
                }]                
            }];
            return Observable.of(res).toPromise();
        };

        solicitacaoService.solicitarFase3Paciente = function(idBusca, idLaboratorio: number): Promise<any> {
            return Observable.of("Pedido de exame criado com sucesso").toPromise();
        }

    });

    afterEach(() => {
        fixture = undefined;
        component = undefined;
    });

    /**
     * Verifica se o componente de evalocao foi instanciado com sucesso
     */
    it('deveria criar o NodalPedidoExameFase3Component', () => {
        expect(component).toBeTruthy();
    });

    it('Não deve selecionar o laboratório se não existir laboratorio de preferencia.', (done) => (async () => {

        component.data = {
            rmr: 1,
            idBusca: 1,
            municipioEnderecoPaciente: "Municipio do endereco do paciente",
            ufEnderecoPaciente: "UF do endereco do paciente",
            laboratorioDePrefencia: null,
            alteracaoLaboratorio: false                
        };

        await component.ngOnInit();
        
        expect(component.idLaboratorioSelecionado).toBeUndefined();

    })().then(done).catch(done.fail));

    it('Deve selecionar o laboratório se existir laboratorio de preferencia.', (done) => (async () => {

        component.data = {
            rmr: 1,
            idBusca: 1,
            municipioEnderecoPaciente: "Municipio do endereco do paciente",
            ufEnderecoPaciente: "UF do endereco do paciente",
            laboratorioDePrefencia: new Laboratorio(2),
            alteracaoLaboratorio: false
        };

        await component.ngOnInit();
        fixture.detectChanges();
        expect(component.idLaboratorioSelecionado).toEqual(2);

    })().then(done).catch(done.fail));

    it('Não deve selecionar o laboratório se existir laboratorio de preferencia e for alteração de laboratório.', (done) => (async () => {

        component.data = {
            rmr: 1,
            idBusca: 1,
            municipioEnderecoPaciente: "Municipio do endereco do paciente",
            ufEnderecoPaciente: "UF do endereco do paciente",
            laboratorioDePrefencia: new Laboratorio(2),
            alteracaoLaboratorio: true,
            laboratorioAtual: new Laboratorio(2, "Laboratorio CT")
        };

        await component.ngOnInit();
        
        expect(component.idLaboratorioSelecionado).toBeUndefined();

    })().then(done).catch(done.fail));

    it('Deve exibir mensagem de erro se o laboratório não for selecionado.', (done) => (async () => {

        component.data = {
            rmr: 1,
            idBusca: 1,
            municipioEnderecoPaciente: "Municipio do endereco do paciente",
            ufEnderecoPaciente: "UF do endereco do paciente",
            laboratorioDePrefencia: null,
            alteracaoLaboratorio: false,
        };

        await component.ngOnInit();
        await component.confirmar();
        
        expect(component.mensagemErroLaboratorio).toBeDefined();

    })().then(done).catch(done.fail));

    it('Deve exibir mensagem de erro se o laboratório selecionado for o laboratorio atual.', (done) => (async () => {

        component.data = {
            rmr: 1,
            idBusca: 1,
            municipioEnderecoPaciente: "Municipio do endereco do paciente",
            ufEnderecoPaciente: "UF do endereco do paciente",
            laboratorioDePrefencia: null,
            alteracaoLaboratorio: true,
            laboratorioAtual: new Laboratorio(2, "Laboratório CT")
        };

        await component.ngOnInit();
        component.idLaboratorioSelecionado = "2"
        await component.confirmar();
        
        expect(component.mensagemErroLaboratorio).toBeDefined();

    })().then(done).catch(done.fail));

    it('Não deve exibir mensagem de erro ao confirmar com sucesso.', (done) => (async () => {

        component.data = {
            rmr: 1,
            idBusca: 1,
            municipioEnderecoPaciente: "Municipio do endereco do paciente",
            ufEnderecoPaciente: "UF do endereco do paciente",
            laboratorioDePrefencia: null,
            alteracaoLaboratorio: false            
        };

        await component.ngOnInit();
        component.idLaboratorioSelecionado = "2"
        await component.confirmar();
        
        expect(component.mensagemErroLaboratorio).toBeNull();

    })().then(done).catch(done.fail));


});