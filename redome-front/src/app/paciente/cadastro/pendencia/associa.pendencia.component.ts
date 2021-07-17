import { TranslateService } from '@ngx-translate/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Pendencia } from '../../avaliacao/pendencia';
import {BaseForm} from '../../../shared/base.form';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: "associa-pendencia",
    moduleId: module.id,
    templateUrl: "./associa.pendencia.component.html"
})
export class AssociaPendenciaComponent extends BaseForm<Pendencia> implements OnInit {

    private _mostrarPergunta: boolean;

    private _associaPendenciaForm: FormGroup;
    
    constructor (translate: TranslateService, private fb: FormBuilder) {
        super();
        this.translate = translate;
        this._mostrarPergunta = false;

        this.buildForm();
    }
    

    ngOnInit(): void {
        this.translate.get("associaPendenciaForm").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this._associaPendenciaForm);
            this.setMensagensErro(this._associaPendenciaForm);
        });
    }

    public form(): FormGroup {
       return this._associaPendenciaForm;
    }

    private buildForm() {
        let pendenciaFormArray: FormArray = new FormArray([]);
        
        this._associaPendenciaForm  = this.fb.group({
            'associaPendencias': [null, Validators.required],
            'resposta': ['', null],
            'respondePendencias': [null, Validators.required],
            'listaPendencias': pendenciaFormArray
        });        

        //Chamada aos métodos default para que a tela funcione com validações
        this.criarFormsErro(this._associaPendenciaForm);
        this.criarMsgErrors();
    }
    
    public preencherFormulario(entidade: Pendencia): void {
        throw new Error("Method not implemented.");
    }
    
    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }
    
    /**
     * Monta FormArray da lista de pendências
     * 
     * @param {Pendencia[]} value 
     * 
     * @memberOf AssociaPendenciaComponent
     */
    public montaformPendencia(value: Pendencia[], idPendencia?:number) {
        value.forEach((pendencia, index) => {
            let marcarPendencia = false;
            if(idPendencia && idPendencia == pendencia.id){
                marcarPendencia = true;
            }
            this.listaPendenciaForm.setControl(index, this.criarPendenciaGroup(pendencia, marcarPendencia));
        });
        //Chamada aos métodos default para que a tela funcione com validações
        this.criarFormsErro(this._associaPendenciaForm);
    }

    private criarMsgErrors() {
        this.formErrors['listaSelecionada'] = "";
    }

    private resetMsgErrors() {
        this.formErrors['listaSelecionada'] = "";
    }

    public get listaPendenciaForm(): FormArray { 
        return this._associaPendenciaForm.get('listaPendencias') as FormArray; 
    }
    
    private criarPendenciaGroup(pendencia: Pendencia, marcarPendencia:boolean): FormGroup {
        return this.fb.group({
            selecionado: [marcarPendencia, null],
            id: [pendencia.id,  null],
            descricao: [pendencia.descricao, null]
        });
    }

	/**
     * 
     * 
     * @type {boolean}
     * @memberOf AssociaPendenciaComponent
     */
    public get mostraPergunta(): boolean {
		return this._mostrarPergunta;
	}

	/**
     * 
     * 
     * 
     * @memberOf AssociaPendenciaComponent
     */
    public set mostrarPergunta(value: boolean) {
		this._mostrarPergunta = value;
	}
    

    public get mostrarPergunta(): boolean {
        return this._mostrarPergunta;
    }

    /**
     * Verifica se o formulário é válido.
     * @author Rafael Pizão
     */
    public validateForm(): boolean {                
        let valid: boolean = super.validateForm();
        let valid2: boolean =  this.validaListaPendencia();

        return valid && valid2;
    }

    /**
     * verifica se foi selecionado alguma pendência da lista
     * 
     * @returns {boolean} 
     * 
     * @memberOf AssociaPendenciaComponent
     */
    public validaListaPendencia(): boolean {
        let algumItemSelecionado: boolean = false;
        this.listaPendenciaForm.controls.forEach(pendenciaGroup => {
            if (pendenciaGroup.get('selecionado').value == true) {
                algumItemSelecionado = true;
            }
        })

        if (!algumItemSelecionado) {
            this.formErrors['listaSelecionada'] = "Nenhum item selecionado"; 
        }

        return algumItemSelecionado;
    }

    /**
     * retorna a lista de pendências selecionadas
     * 
     * @returns {Pendencia[]} 
     * 
     * @memberOf AssociaPendenciaComponent
     */
    listarPendenciasSelecionadas(): Pendencia[] {
        let pendencias: Pendencia[] = [];
        this.listaPendenciaForm.controls.forEach(pendenciaGroup => {
            if (pendenciaGroup.get('selecionado').value == true) {
                let pendencia: Pendencia = new Pendencia();
                pendencia.id = pendenciaGroup.get('id').value;
                pendencia.descricao = pendenciaGroup.get('descricao').value;
                pendencias.push(pendencia);
            }
        });

        return pendencias;
    }

    /**
     * Retorna o texto opcional da resposta
     * 
     * @returns {string} 
     * 
     * @memberOf AssociaPendenciaComponent
     */
    obterResposta(): string {
        return this.form().get('resposta').value;
    }

    /**
     * Retorna true se a pergunta se a evolução/exame responde uma pendências
     * 
     * @returns {boolean} 
     * 
     * @memberOf AssociaPendenciaComponent
     */
    obterRespondePendencia(): boolean {
        return this.form().get('respondePendencias').value == 1;
    }

    /**
     * retorna true se a pergunta de associar pendencias estiver respondido com sim
     * 
     * @returns {boolean} 
     * 
     * @memberOf AssociaPendenciaComponent
     */
    obterAssociaPendencias(): boolean {
        return this.form().get('associaPendencias').value == 1;
    }


    /**
     * Metodo para marcar o radio
     * 'Associar essa evolução a pendências abertas'
     * 
     * @memberof AssociaPendenciaComponent
     */
    public marcarRadioAssociarPendencias(){
        this.form().get('associaPendencias').setValue("1");
    }

    /**
     * Metodo para marcar o radio
     * 'Responde as pendências selecionadas?'
     * 
     * @memberof AssociaPendenciaComponent
     */
    public marcarRadioRespondePendencias(){
        this.form().get('respondePendencias').setValue("1");
    }

}