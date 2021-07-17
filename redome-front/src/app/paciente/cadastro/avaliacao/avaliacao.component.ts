import {Paciente} from '../../paciente';
import { TranslateService } from '@ngx-translate/core';
import { CentroTransplante } from '../../../shared/dominio/centro.transplante';
import { PacienteConstantes } from '../../paciente.constantes';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { ErroMensagem } from '../../../shared/erromensagem';
import { BaseForm } from '../../../shared/base.form';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, AbstractControl, FormArray } from '@angular/forms';
import { Configuracao } from '../../../shared/dominio/configuracao';
import { MotivoCadastro } from "../../../shared/enums/motivo.cadastro";
import { Locus } from '../exame/locus';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { DataUtil } from '../../../shared/util/data.util';

/**
 * Component responsavel motivo cadastro
 * @author Bruno Sousa
 * @export
 * @class ExameComponent
 * @extends {BaseForm}
 * @implements {OnDestroy}
 */
@Component({
    selector: 'avaliacao',
    moduleId: module.id,
    templateUrl: './avaliacao.component.html',
    //styleUrls: ['../paciente.css']
})
export class AvaliacaoComponent extends BaseForm<Paciente> implements OnInit {
    // Lista original, como vem do banco de dados.
    private centrosTransplantes: CentroTransplante[] = [];
    // Lista específicas para cada campo.
    // Foram separadas, pois a seleção de um centro em uma influencia na lista da outra.
    private _centrosAvaliadores: CentroTransplante[] = [];
    private _centrosTransplantadores: CentroTransplante[] = [];
    locusMismatchFormArray: FormArray = new FormArray([], );

    motivoForm: FormGroup;

    // Indica o motivo cadastro selecionado no formulário.
    motivoCadastroId: String;
    isNaoAparentado: boolean;

    private mensagemErroLocus:String = "";
    
    
    public _exibirrCentroAvaliador: boolean = true;
    
    constructor(private fb: FormBuilder, private dominioService: DominioService, translate: TranslateService){
        super();
        this.translate = translate;
        

        this.dominioService.listarCentroAvaliador().then(res => {
            this._centrosAvaliadores = res;
            
        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });

        this.dominioService.listarCentroTransplante().then(res => {
            this._centrosTransplantadores = res;
            this.centrosTransplantes = res;
            
        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });
        this.buildForm();
    }

    ngOnInit() {
        this.translate.get("pacienteForm.motivoGroup").subscribe(res => {
            this._formLabels = res;

            this.dominioService.listarLocus().then(res => {
                res.forEach((r, index) =>{
                    let locus:Locus = new Locus().jsonToEntity(r);
                    this.locusMismatchFormArray.setControl(index, this.criarLocusItemArray(locus, false));
                })
                this.criarMensagemValidacaoPorFormGroup(this.motivoForm);
                this.setMensagensErro(this.motivoForm);
            }, (error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
            });
        });
        this.translate.get("mensagem.erro.quantidadeLocusMismatch").subscribe(res => {
            this.mensagemErroLocus = res;
        });

    }

        /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações 
     * @author Bruno Sousa
     */
    buildForm() {
        this.motivoForm = this.fb.group({
            'centroAvaliador': [null, Validators.required],
            'aceiteMismatch':[null, Validators.required],
            'locus':this.locusMismatchFormArray
        });
    }

    criarLocusItemArray(locus:Locus, check:Boolean): FormGroup{
        return this.fb.group({
            codigo: [locus.codigo , null],
            selecionado:[check,null]
        });
    }


    /**
     * Reseta o formulário limpando mensagens de error e valores dos campos
     * 
     * @author Bruno Sousa
     * @memberof MotivoComponent
     */
    resetaFormulario() {
        this.motivoForm.reset();
        //this.limparValidacaoDinamica();
        this.clearMensagensErro(this.motivoForm);
    }
    /**
     * Método utilizado para retirar da lista de centros 
     * transplante o item utilizdo no centro avaliador.
     */
    public filtrarCentrosTransplantadores():void{
        let centroAvaliadorId:String = 
            this.getField(this.motivoForm, "centroAvaliador").value;
        this._centrosTransplantadores = 
            this.extrairCentroTransplanteDaLista(this.centrosTransplantes, centroAvaliadorId);
    }

    /**
     * Método utilizado para retirar da lista de centros 
     * transplante o item utilizado no centro transplantador.
     */
    public filtrarListaCentrosAvaliadores():void{
        let centroTransplantadorId:String = 
            this.getField(this.motivoForm, "centroTransplantador").value;
        this._centrosAvaliadores = 
            this.extrairCentroTransplanteDaLista(this.centrosTransplantes, centroTransplantadorId);
    }

    /**
     * Filtra o item informado no parametro da lista de centros transplante.
     * 
     * @param lista 
     * @param itemASerRetirado 
     */
    private extrairCentroTransplanteDaLista(lista:CentroTransplante[], itemId:String){
        if(itemId == null){
            return lista;
        }
        return lista.filter(itemLista => (itemId != new String(itemLista.id)));
    }

    public form(): FormGroup{
        return this.motivoForm;
    }

    // Override
    public preencherFormulario(paciente:Paciente): void {
        this.setPropertyValue('centroAvaliador', paciente.centroAvaliador);
        this.setPropertyValue('aceiteMismatch', paciente.aceiteMismatch != null ? paciente.aceiteMismatch + "" : null);        
        if(paciente.locusMismatch){
            paciente.locusMismatch.forEach(l=>{
                this.locusMismatchFormArray.controls.forEach((item)=>{
                    if(item.get("codigo").value == l.codigo){
                        item.get('selecionado').setValue(true);
                    }
                });
            })
        }
    }

    nomeComponente(): string {
        return null;
    }

	public get centrosAvaliadores(): CentroTransplante[]  {
		return this._centrosAvaliadores;
    }

	public get centrosTransplantadores(): CentroTransplante[]  {
		return this._centrosTransplantadores;
	}
    
    exibirLocus():Boolean{
        return this.getField(this.motivoForm, "aceiteMismatch").value == 1;
    }

    limparLocus(){
        this.locusMismatchFormArray.controls.forEach((item)=>{
            item.get('selecionado').setValue(false);
            item.get('selecionado').updateValueAndValidity();
        });
    }

    obterListaLocusMismatch():Locus[]{
        let locusMismatch: Locus[] = [];
        this.locusMismatchFormArray.controls.forEach((item)=>{
            if(item.get('selecionado').value == 1){
                let locus:Locus = new Locus();
                locus.codigo = item.get("codigo").value;
                locusMismatch.push(locus);
            }
        });
        return locusMismatch;
    }

     /**
     * Método para validar todo o formulario
     * 
     * @returns {boolean} 
     * @memberof AvaliacaoComponent
     */
    public validateForm():boolean{
        this.clearMensagensErro(this.motivoForm);
        let valid: boolean = super.validateForm();
        this.obterListaLocusMismatch();
        if (this.form().get("aceiteMismatch").value == "1" &&  this.obterListaLocusMismatch().length == 0) {
            this.formErrors['nenhumLocusSelcionado'] = this.mensagemErroLocus;
            valid = false;
        }
        
        return valid;
    }

    public esconderCentroAvaliador(): void {
        this._exibirrCentroAvaliador = false;
        this.resetFieldRequired(this.form(), 'centroAvaliador');
    }

}