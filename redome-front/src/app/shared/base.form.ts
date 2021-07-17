import { DateTypeFormats } from './util/date/date.type.formats';
import { DataUtil } from './util/data.util';
import {
    INVALID_DATE,
    INVALID_DATE_BEFORE_TODAY,
    INVALID_DATE_AFTER_TODAY,
    INVALID_HOUR,
    INVALID_DATETIME,
    INVALID_CUSTOM
} from '../validators/data.validator';
import { PermissaoRotaComponente } from './permissao.rota.componente';
import {PacienteUtil} from './paciente.util';
import { BaseEntidade } from './base.entidade';
import { TranslateService } from '@ngx-translate/core';
import { Output, EventEmitter } from '@angular/core';
import { Validators, FormArray, FormGroup } from '@angular/forms';
import { FormControl, AbstractControl, FormBuilder } from '@angular/forms';
import { CampoMensagem } from './campo.mensagem';
import { EventBind } from "./event.bind";
import { isDate } from 'moment';
import { EventDispatcher } from './eventos/event.dispatcher';
import { IEvent } from './eventos/i.event';
import { AbstractEvent } from './eventos/abstract.event';

export abstract class BaseForm<T> extends EventDispatcher<AbstractEvent> implements PermissaoRotaComponente {

    @Output()
    public alterarMensagem: EventEmitter<String> = new EventEmitter<String>();

    @Output()
    public alterarMensagemErro: EventEmitter<String> = new EventEmitter<String>();

    @Output()
    public limparMensagemErro: EventEmitter<String> = new EventEmitter<String>();

    protected _formLabels; // = {};

    /**
     * formErrors conterá todas as mensagens de erro de cada campo na tela,
     * ou qualquer mensagem de erro genérica
     * @author Fillipe Queiroz
     */
    formErrors = {};

    /**
     * Mensagens de validações pré-definidas
     * @author Fillipe Queiroz
     */
    public validationMessages = [];

    protected translate: TranslateService;

    protected eventBind: EventBind = new EventBind();

    criarMensagemValidacaoPorFormGroup(form: FormGroup) {
        this.criarMensagensErro(form);
        this.criarFormsErro(form);
    }

    /**
     * 
     * Popula as mensagens de obrigatoriedade dos campos dinamicamente,
     * inicialmente popula com os Validators definidos no buildForm
     * @param form Formulário para criar as mensagens de erro default
     * @author Fillipe Queiroz
     */
    criarMensagensErro(form: FormGroup, nomeGroupPai?: string, index?: number) {
        for (const field in form.controls) {
            const control = form.get(field);
            if (control && control instanceof FormControl) {                 
                if (this.translate) {
                    this.translate.get("mensagem.erro.obrigatorio", {campo: this.getLabel(field)})
                        .subscribe(function(field, index, res)  {
                            let mensagem: CampoMensagem = this.criarCampoMensagem(undefined, field, 'required', res, index);                            
                            this.validationMessages.push(mensagem);
                        }.bind(this, field, index)
                    );
                }
            } else if (control && control instanceof FormGroup) {
                var group: FormGroup = control;
                
                for (const fieldGroup in group.controls) {

                    if (this.translate) {
                        
                        this.translate.get("mensagem.erro.obrigatorio", {campo: this.getLabel(field + "." + fieldGroup)})
                            .subscribe(function(field, fieldGroup, index, res) {
                                var mensagem2: CampoMensagem = this.criarCampoMensagem(field, fieldGroup, 'required', res, index);
                                this.validationMessages.push(mensagem2);
                            }.bind(this, field, fieldGroup, index) , error => {console.log(error)}
                        );
                    }
                }

            } else if (control && control instanceof FormArray){
                let indexFormArray = 0;
                control.controls.forEach(element => {
                    if(element instanceof FormGroup){
                        this.criarMensagensErro(element, nomeGroupPai, indexFormArray);
                    }
                    indexFormArray++;
                });
            }
        }
    }

    /**
     * Método recursivo para setar todas as mensagens de validação de todos os FormControls
     * no array de mensagens 
     * @param form formulário que será alterada as mensagens
     * @param nomeGroupPai - se refere a um formControl que seja filho de outro, como exemplo:
     * Paciente contém uma entidade de etnia, o formControl é o [id] e o pai é [etnia]
     * @author Fillipe Queiroz
     */
    setMensagensErro(form: FormGroup, nomeGroupPai?: string, fieldName?: string, index?: number) {
        for (const field in form.controls) {            
            const control = form.get(field);

            if (control && control instanceof FormControl) {
                if (!nomeGroupPai) {
                    if (fieldName &&  field != fieldName) {
                        continue;
                    }
                }
                else {
                   if (fieldName &&  (nomeGroupPai + "." + field) != fieldName) {
                        continue;
                    } 
                }
                if (control && control.invalid && control.touched) {
                    var campoMensagem: CampoMensagem;

                    for (const key in control.errors) {
                        if (!nomeGroupPai) {
                            campoMensagem = this.validationMessages
                            .filter(myObj => myObj.campo == field && myObj.tipo == key)[0]
                            
                        } else {
                            if(index >= 0){
                                campoMensagem = this.validationMessages
                                .filter(myObj => myObj.campo == field 
                                    && myObj.tipo == key  && myObj.index == index)[0]
                            }
                            else{
                                campoMensagem = this.validationMessages
                                .filter(myObj => myObj.campo == field && myObj.pai == nomeGroupPai
                                    && myObj.tipo == key)[0]
                            }

                        }
                        if (campoMensagem) {
                            for (const key in control.errors) {
                                this.atribuirMensagemFormErrors(nomeGroupPai, campoMensagem.index, field, campoMensagem.mensagem);
                            }
                        }
                        else {                            
                            let chaveMensagem: string = ""
                            if (key == INVALID_DATE) {
                                chaveMensagem = "mensagem.erro.dataInvalida"; 
                            }
                            if (key == INVALID_DATE_BEFORE_TODAY) {
                                chaveMensagem = "mensagem.erro.menorDataAtual"; 
                            }
                            if (key == INVALID_DATE_AFTER_TODAY) {
                                chaveMensagem = "mensagem.erro.maiorDataAtual"; 
                            }
                            if (key == INVALID_HOUR) {
                                chaveMensagem = "mensagem.erro.horaInvalida"; 
                            }
                            if (key == INVALID_DATETIME) {
                                chaveMensagem = "mensagem.erro.dataHoraInvalida"; 
                            }
                            if (this.translate && chaveMensagem != "") {
                                this.translate
                                    .get(chaveMensagem, {campo: this.getLabel(field)})
                                    .subscribe(function(field, index, res)  {
                                        let mensagem: CampoMensagem = this.criarCampoMensagem(null, field, key, res, index);
                                        this.validationMessages.push(mensagem);
                                        this.atribuirMensagemFormErrors(nomeGroupPai, -1, field, mensagem.mensagem);
                                    }.bind(this, field, -1)
                                );
                            }

                        }
                    }

                }
            } else if (control && control instanceof FormGroup) {
                this.setMensagensErro(control, field, fieldName);
            } else if (control && control instanceof FormArray) {
                let indexFormArray = 0;
                control.controls.forEach(element => {
                    if(element instanceof FormGroup){
                        this.setMensagensErro(element, field, fieldName, indexFormArray);
                    }
                    indexFormArray++
                });
            }

        }
    }

    private criarCampoMensagem(pai: string, field: string, key: string, texto: string, index: number) {
        let mensagem: CampoMensagem = new CampoMensagem();
        mensagem.pai = pai;
        mensagem.campo = field;
        mensagem.tipo = key;
        mensagem.mensagem = texto;
        if (index >= 0) {
            mensagem.index = index;
        }
        return mensagem;
    }

    private atribuirMensagemFormErrors(nomeGroupPai: string, index: number, field: string, mensagem: String) {
        let nameField = nomeGroupPai ? nomeGroupPai + "." + field : field;
        if (!nomeGroupPai) {
            this.formErrors[field] = mensagem;
        }
        else {
            if (this.formErrors[nomeGroupPai] == '') {
                this.formErrors[nomeGroupPai] = {};
                this.formErrors[nomeGroupPai][field] = mensagem;
            }
            else {
                if (index >= 0) {
                    this.formErrors[nomeGroupPai][index][field] = mensagem;
                }
                else {
                    this.formErrors[nomeGroupPai][field] = mensagem;
                }
            }
        }
    }

    /**
     * Limpa o json com as mensagens de erro de todos os campos
     * @param form - formulario a ser limpo
     * @param nomeGroupPai - nome do pai exemplo: Etnia.id o pai é Etnia e o atributo é id
     * @author Fillipe Queiroz
     */
    clearMensagensErro(form: FormGroup, nomeGroupPai?: string, fieldName?: string, index?: number) {
        for (const field in form.controls) {
            const control = form.get(field);

            if (control && control instanceof FormControl) {
                if (!nomeGroupPai) {
                    if (fieldName && field != fieldName) {                    
                        continue;
                    }
                }
                else {
                    if (fieldName && (nomeGroupPai + "." + field) != fieldName) {                    
                        continue;
                    }
                }

                if (control && control.valid) {
                    // var campoMensagem: CampoMensagem;
                    if (!nomeGroupPai) {
                        if(index >= 0){
                            this.formErrors[nomeGroupPai][index][field] = '';
                        }else{
                            this.formErrors[field] = '';
                        }
                    } else {
                        if (this.formErrors[nomeGroupPai]){
                            if(index >= 0){
                                this.formErrors[nomeGroupPai][index][field] = '';    
                            }else{
                                this.formErrors[nomeGroupPai][field] = '';
                            }
                        }
                            
                    }
                }
            } else if (control && control instanceof FormGroup) {
                this.clearMensagensErro(control, field, fieldName);
            } else if (control && control instanceof FormArray) {
                let indexFormArray = 0;
                
                control.controls.forEach(element => {
                    if(element instanceof FormGroup){
                        this.clearMensagensErro(element, field, fieldName, indexFormArray);
                    }
                    
                    indexFormArray++
                });
            }
        }
    }

    
        /**
     * 
     * Inicia as mensagens de erro em branco dinamicamente,
     * 
     * @param form Formulário para criar o formerros vazios
     * @author Bruno Sousa
     */
    criarFormsErro(form: FormGroup, nomeGroupPai?: string, index?: number, jsonIndex?:any) {
        for (const field in form.controls) {
            const control = form.get(field);
            if (control && control instanceof FormControl) {                 
                if(index >=0){
                    
                    jsonIndex[field] = ''
                    //this.formErrors[index] = jsonIndex
                    // this.formErrors[index][field] = '';
                }else{
                    this.formErrors[field] = '';
                }
                
            } else if (control && control instanceof FormGroup) {
                var group: FormGroup = control;                
                for (const fieldGroup in group.controls) {
                    var errorfilho = {};
                    errorfilho[fieldGroup] = ''; 
                    this.formErrors[field] = errorfilho;
                }
            } else if (control && control instanceof FormArray) {
                let indexFormArray = 0;
                let jsonArray = {};
                control.controls.forEach(element => {
                    var jsonIndexFormArray = {}
                    if(element instanceof FormGroup){
                        this.criarFormsErro(element, nomeGroupPai, indexFormArray, jsonIndexFormArray);
                    }
                    jsonArray[indexFormArray] = jsonIndexFormArray;
                    indexFormArray++;
                });
                this.formErrors[field]=jsonArray;
            }
        }
    }

    /**
     * Método que recupera a label de um campo
     * @param field Campo que representa um atributo de classe, com ele é buscada a label
     * @author Fillipe Queiroz
     */
    getLabel(field: string, args?:any[]): string { 
        if (this._formLabels) {     
            let message:string = this.label(this._formLabels, field);
            if(args != null){
                for (var i = 0; i < args.length; i++) {
                    message = message.replace("{" + i + "}", args[i]);
                }
            }
            return message;
        } else {
            return "";
        }
    }

    private label(obj: {}, field: string): string {
        try {
            //return obj[field];
            let campos = field.split('.');
            let valor = obj[campos[0]];
            if (campos.length == 1) {
                return valor;
            }
            else {
                return valor = this.label(valor, campos[1]);
            }
        } catch (error) {
            //Retorna vazio caso nao encontre uma label na internacionalizacao
            return "";
        }
        
    }


    /**
     * Define um campo como obrigatorio dinamicamente
     * @param field Campo input que será adicionado a validação de obrigatório
     * @param form form que contém o campo para alterar a validação
     * @author Fillipe Queiroz
     */
    setFieldRequired(form: FormGroup, field: string) {
        if (form.controls[field]) {
            form.controls[field].setValidators(Validators.required);
            form.controls[field].markAsUntouched();
            form.controls[field].updateValueAndValidity();
            this.setMensagensErro(form, null, field);
        }
    }

    /**
     * Define um campo como obrigatorio dinamicamente
     * @param field Campo input que será adicionado a validação de obrigatório
     * @author Fillipe Queiroz
     */
    setFieldRequiredSemForm(field: string) {
        this.setFieldRequired(this.form(),field);
    }

    /**
     * Retira a obrigatoriedade de um campo dinamicamente
     * @param field Campo input que será adicionado a validação de obrigatório
     * @param form form que contém o campo para alterar a validação
     * @author Fillipe Queiroz
     */
    resetFieldRequired(form: FormGroup, field: string) {
        if (form.controls[field]) {
            form.controls[field].setValidators(null);
            form.controls[field].updateValueAndValidity();
            this.clearMensagensErro(form, null, field);
        }
    }

    /**
     * Retira a obrigatoriedade de um campo dinamicamente
     * @param field Campo input que será adicionado a validação de obrigatório
     * @param form form que contém o campo para alterar a validação
     * @author Fillipe Queiroz
     */
    resetFieldRequiredSemForm(field: string) {
        this.resetFieldRequired(this.form(),field);
    }

    /**
     * Retorna o campo de mesmo nome no formulário informado.
     * @author Rafael Pizão
     */
    getField(form: FormGroup, field: string): AbstractControl {
        if(this.existsField(field)){
            return form.controls[field];
        }
        return null;
    }

    /**
     * Busca o filho contido no pai mais abaixo entre os níveis informados no fieldName.
     * Ex.: paciente.responsavel.cpf, paciente.evolucoes[0].altura, etc
     * 
     * @param form 
     * @param fieldName 
     */
    public getFieldName(form: FormGroup, fieldName: string): AbstractControl{
        let field: AbstractControl = this.getField(form, fieldName);

        if(field != null){
            return field;
        } else {
            if(fieldName.indexOf(".")){
                let niveis:string[] = fieldName.split(".");
                let formReference:FormGroup = form;

                niveis.forEach(nivel => {
                    let possibleField: AbstractControl = 
                        this.getField(formReference, nivel) as AbstractControl;
                    if(possibleField instanceof FormGroup){
                        formReference = possibleField;
                    } else if(possibleField instanceof FormControl){
                        field = possibleField;
                    }
                });
            }
            return field;
        }
    }

    focusControl(form: FormGroup, control: string, tooltip: any){            
        if (form.get(control) && form.get(control).invalid) {
            this.clearMensagensErro(form, null, control);
            this.setMensagensErro(form, null, control);
            tooltip.show();
        }
        else {
            tooltip.hide();
        }   
    }

    blurControl(form: FormGroup, control: string, tooltip: any) {
        if (tooltip.isOpen) {
            tooltip.hide();
            //this.clearMensagensErro(form, null, control);
        }
    }

    keyupControl(form: FormGroup, control: string, tooltip: any) {
        if (form.get(control) && form.get(control).valid) {
            tooltip.hide();
        }        
    }

    /**
     * Indica a referência para o formulário utilizado no componente.
     * Deve ser implementado nas classes concretas de cada componente/formulário.
     * 
     * @return a referência para o formulário usado como referência para o componente.
     */
    public abstract form(): FormGroup;

    /**
     * Método que valida os campos de todo formulário, alterando o comportamento do mesmo, 
     * de acordo com as suas obrigatoriedades, validações customizadas, tudo que envolva
     * a marcação do campo em vermelho (comportamento atual)
     * 
     * @return TRUE se o formulário é válido
     */
    public validateForm():boolean{
        this.clearMensagensErro(this.form());
        let valid: boolean = this.validateFields(this.form());
        this.setMensagensErro(this.form());
        return valid;
    }

    /**
     * Método que valida os campos informados no parâmetro, alterando o comportamento do mesmo, 
     * de acordo com as suas obrigatoriedades, validações customizadas, tudo que envolva
     * a marcação do campo em vermelho (comportamento atual)
     * 
     * @return TRUE se o formulário é válido
     */
    public validateFields(form: FormGroup): boolean {
        let valid: boolean = true;
        for (const field in form.controls) {
            const control = form.get(field);
            if (control && control instanceof FormControl) {
                if(control.invalid){
                    this.markAsInvalid(control);
                    valid = false;
                }
            }
            else if (control && control instanceof FormGroup) {                
                this.validateFields(control);
                if(control.invalid){
                    valid = false;
                }
            }
            else if (control && control instanceof FormArray) {
                control.controls.forEach(element => {
                    if(element instanceof FormGroup){
                        this.validateFields(element);
                        if (element.invalid){ 
                            valid = false;
                        }
                    }
                });
            }
        }
        return valid;
    }

    /**
     * Método que marca o campo como inválido, colocando sua marcação 
     * em vermelho (comportamento atual)
     * 
     */
    public markAsInvalid(field: AbstractControl): void {
        field.markAsTouched();
        field.markAsDirty();
        if(field.errors == null){
            field.setErrors({});
        }
        field.errors["invalid"] = true;
        field.errors["valid"] = false;
    }

    public markAsInvalidWithMessage(field: AbstractControl, message: string): void {
        this.markAsInvalid(field);
        field.setErrors({
            [INVALID_CUSTOM]: message
        });
    }


    /**
     * Verifica se o campo existe no formulário.
     * Se o atributo tiver abaixo de outros groups (ex. responsavel.cpf), deverá
     * quebrar o fieldName em partes e acessar os níveis abaixo, até encontrar o campo.
     * 
     * @param fieldName 
     * @return true se o campo existe, senão false.
     */
    public existsField(fieldName:string): boolean{
        return (this.form() != null 
                    && this.form().controls != null 
                        && this.form().controls.hasOwnProperty(fieldName));
    }

    public setMensagemErroPorCampo(nomeCampo: string) {
        this.clearMensagensErro(this.form() , null, nomeCampo);
        this.setMensagensErro(this.form(), null, nomeCampo);
    }
    
    
    public fecharModal(modal: any, input: any){
        modal.hide();
        if (input) {
            input.focus();
        }
    }

    /**
     * Método para focar um input
     * 
     * @param {*} input 
     * @memberof BaseForm
     */
    public setarFocus( input: any){
        if (input) {
            input.focus();
        }
    }

    /**
     * Seta os valores, informados no parametro, no formulário 
     * associado ao componente. 
     * 
     * @param entidade objeto a ser setado no form.
     */
    public abstract preencherFormulario(entidade:T): void;

    /**
     * Seta na propriedade (property) o valor informado (value) e dispara o evento, se informado;
     * 
     * @param name 
     * @param value 
     */
    public setPropertyValue(name:string, value:any, propertyId:string = "id"):void{
        let field:FormControl = <FormControl> this.form().get(name);
        if(value != null &&  value != undefined){            
            /**
             * FIXME: Os objetos não estão vindo tipados do back-end.
             * Ex.: Pais vem um objeto não identificado, então não tenho como verificar
             * se é um BaseEntidade (classe que todas as entidades extendem) e daí trata 
             * devidamente.
             * Verificar em mais locais e resolver, se for necessário.
            **/ 
            if(value.hasOwnProperty(propertyId)){
                field.setValue(value ? (value[propertyId] + "") : null);
            } else if(isDate(value)){
                field.setValue(DataUtil.toDateFormat(value, DateTypeFormats.DATE_ONLY) );
            } else {
                field.setValue(value);
            }
        }
        else {
            field.setValue(null);            
        }
    }

    /**
     * Desabilita o campo informado no parâmetro.
     * 
     * @param name nome do atributo.
     */
    protected enabled(name:string, enable: boolean = true):void{
        let field:FormControl = <FormControl> this.form().get(name);
        enable ? field.enable() : field.disable();
    }

    /**
     * Retorna o valor da propriedade (value).
     * 
     * @param name nome do atributo.
     * @return retorna o valor do atributo.
     */
    public getPropertyValue(name: string): any{
        let field:FormControl = <FormControl> this.form().get(name);
        return field.value;
    }

    /**
     * Força a mensagem de erro no campo indicado, quando a validação não ocorre por fora dos componentes,
     * no contexto da classe.
     * 
     * @param fieldName nome do campo.
     * @param message mensagem a ser inserida.
     */
    public forceError(fieldName: string, message: string): void{
        this.markAsInvalidWithMessage(this.form().get(fieldName), message);
        this.formErrors[fieldName] = message;
    }

    /**
     * Limpa o formulário (validações e valores).
     * O formulário pode ser informado por parâmetro ou usando a referência 
     * informada no método form(), sendo priorizado o que for informado no 
     * parâmetro. 
     * @param form formulário de referência.
     */
    public clearForm(form: FormGroup = null): void{
        let formThis: FormGroup = form == null ? this.form() : form;
        formThis.reset();
        this.criarFormsErro(formThis);
    }

    /**
     * Método utilizado para exibir mensagem de erro.
     * Este método é para substituir a utilização do atributo formErros no template.
     * 
     * @author Bruno Sousa
     * @param {string} fieldName 
     * @returns {string} 
     * @memberof BaseForm
     */
    public exibirMensagemErrorPorCampo(fieldName: string): string {
        return this.label(this.formErrors, fieldName);
    }

    /**
     * @description Define o nome de referência que será utilizado
     * para conceder acesso ao item de menu.
     * Por padrão, a chave é o nome da classe, mas pode ser extendido e alterado.
     * @author Pizão
     * @returns {string}
     */
    abstract nomeComponente(): string;
}
