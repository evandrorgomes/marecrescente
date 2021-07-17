import { MessageBox } from 'app/shared/modal/message.box';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { CancelamentoBusca } from "./cancelamento.busca";
import { Paciente } from '../paciente';
import { ComponenteRecurso } from '../../shared/enums/componente.recurso';
import { ErroMensagem } from '../../shared/erromensagem';
import { BaseForm } from '../../shared/base.form';
import { HistoricoNavegacao } from '../../shared/historico.navegacao';
import { PacienteUtil } from '../../shared/paciente.util';
import { BuscaService } from './busca.service';
import { MotivoCancelamentoBusca } from './motivo.cancelamento.busca';
import { RouterUtil } from '../../shared/util/router.util';
import { ErroUtil } from '../../shared/util/erro.util';

/**
 * Component responsavel pelo cancelamento da busca de um determinado paciente.
 * @author Fillipe Queiroz
 * @export
 * @class CancelamentoBuscaComponent
 * @implements {OnInit}
 */
@Component({
    selector: 'cancelamento-busca',
    templateUrl: './cancelamento.busca.component.html'
    // ,
    // styleUrls: ['./cancelamento.busca.component.css']
})
export class CancelamentoBuscaComponent extends BaseForm<Paciente> implements OnInit, OnDestroy {


    public cancelamentoBuscaForm: FormGroup;
    private _motivosCancelamento:MotivoCancelamentoBusca[]=[];
    public data: Array<string | RegExp>;
    private rmr:number;

    public _exibirCampoEspecifique: boolean = false;

    /**
     * Cria uma instancia de AvaliacaoComponent.
     * @param {FormBuilder} _fb 
     * @param {PacienteService} servicePaciente 
     * @param {Router} router 
     * @param {TranslateService} translate 
     * 
     * @memberOf AvaliacaoComponent
     */
    constructor(private _fb: FormBuilder, translate: TranslateService,
            private router: Router,
            private buscaService: BuscaService,
            private activatedRouter: ActivatedRoute,
            private messageBox: MessageBox) {

		super();       
        this.translate = translate;

        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]

        this.cancelamentoBuscaForm = this._fb.group({
            'dataEvento' : [null],
            'motivo' : [null, Validators.required],
            'especifique':[null]
        });  
        

        //this.criarMensagemValidacaoPorFormGroup(this.cancelamentoBuscaForm);

    }

    /**
     *  
     * 
     * @memberOf AvaliacaoComponent
     */
    ngOnInit(): void {
        this.translate.get("cancelamentoBusca").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.cancelamentoBuscaForm);
        });

        this.buscaService.listarMotivosCancelamentoBusca().then(res=>{
            this._motivosCancelamento = res;
        });

        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idPaciente").then(res => {
            this.rmr = res;
        })

       
    }
    
    public ngOnDestroy(): void {}

    /**
     * 
     * Método obrigatorio que retorna o formulario
     * @returns {FormGroup} 
     * @memberof AvaliacaoComponent
     */
    public form(): FormGroup {
        return this.cancelamentoBuscaForm;
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.CancelamentoBuscaComponent];
    }

    public preencherFormulario(entidade: any): void {
        throw new Error('Method not implemented.');
    }

    cancelarBuscaPaciente() {
        if(this.validateForm()){
            let idMotivo:number = this.cancelamentoBuscaForm.get("motivo").value;
            let motivoCancelamento: MotivoCancelamentoBusca = this._motivosCancelamento.find(motivo => {
                return motivo.id == idMotivo;
            })
            let cancelamentoBusca:CancelamentoBusca = new CancelamentoBusca();
            cancelamentoBusca.motivoCancelamentoBusca = new MotivoCancelamentoBusca();
            cancelamentoBusca.motivoCancelamentoBusca.id = motivoCancelamento.id;
            cancelamentoBusca.motivoCancelamentoBusca.descricao = motivoCancelamento.descricao;
            cancelamentoBusca.especifique = this.cancelamentoBuscaForm.get("especifique").value;
            cancelamentoBusca.dataEvento = PacienteUtil.obterDataSemMascara( this.cancelamentoBuscaForm.get("dataEvento").value )
            
            this.buscaService.cancelarBuscaPaciente(this.rmr, cancelamentoBusca).then(res=>{
                this.messageBox.alert(res.mensagem)
                    .withCloseOption((target?: any) => {
                        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                    })
                    .show();
            },(error:ErroMensagem)=> {
                ErroUtil.exibirMensagemErro(error, this.messageBox);                
            });
        }
    }

    /**
     * Metodo que ao selecionar um motivo verifica se deve colocar 
     * o campo observacao como obrigatorio.
     * @param motivo 
     */
    public alterarValidacaoDoCampoDescricao(motivoId):void{
        let motivoCancelamento:MotivoCancelamentoBusca = this._motivosCancelamento.find(motivoCancelamento=>{
            return motivoCancelamento.id == motivoId
        })
        if(motivoCancelamento && motivoCancelamento.descricaoObrigatorio == 1){
            this.setFieldRequired(this.form(),'especifique');
            this._exibirCampoEspecifique = true;
            
        }else{
            this.resetFieldRequired(this.form(),'especifique');
            this._exibirCampoEspecifique = false;
        }
    }

	public get motivosCancelamento(): MotivoCancelamentoBusca[] {
		return this._motivosCancelamento;
	}
    

}