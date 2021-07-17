import { ComentarioMatch } from '../../classes/comentario.match';
import { BaseForm } from '../../base.form';
import { TranslateService } from '@ngx-translate/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';
import { DominioService } from '../../dominio/dominio.service';

/**
 * Classe que representa o componente de Comentátio de Match
 * @author Bruno Sousa
 */
@Component({
    selector: "comentario-match",
    moduleId: module.id,
    templateUrl: "./comentario.match.component.html",
    //styleUrls: ['../../paciente.css']
})
export class ComentarioMatchComponent extends BaseForm<ComentarioMatch[]> implements OnInit {
    
    comentarioForm: FormGroup;

    private _mensagemCampoObrigatorio: string;
    
    /**
     * Cria uma instância de ComentarioMatchComponent.
     * @author Bruno Sousa
     * @param {FormBuilder} fb 
     * @param {TranslateService} translate 
     * @memberof ComentarioMatchComponent
     */
    constructor(private fb: FormBuilder, translate: TranslateService) {
        super();
        this.translate = translate;
        this.buildForm();
    }

    ngOnInit() {
        this.translate.get("comentarioMatchComponent").subscribe(res => {
            this._formLabels = res;            
            //Chamada aos métodos default para que a tela funcione com validações
            this.criarMensagemValidacaoPorFormGroup(this.comentarioForm);
            this.setMensagensErro(this.comentarioForm);
        });

        this.translate.get("mensagem.erro.camposObrigatorios").subscribe(res => {
            this._mensagemCampoObrigatorio = res;
        });
        
    }

    /**
     * Método principal de construção do formulário
     * @author Bruno Sousa
     */
    buildForm() {
        this.criarListacomentarios(1);
    }

     /**
     * Cria as linhas para a lista de comentários, considerando a quantidade solicitada
     * no parametro.
     * 
     * @param qtdLinhas quantidade de linhas de comentário a serem criadas. 
     */
    private criarListacomentarios(qtdLinhas:number):void{
        let comentarioFormArray: FormArray = new FormArray([]);

        for(let i=0; i < qtdLinhas; i++){
            comentarioFormArray.setControl(i, this.criarComentarioGroup());
        }

        this.comentarioForm = this.fb.group({
            listaComentario: comentarioFormArray
        });
    }

    get listaComentarioForm(): FormArray { 
        return this.comentarioForm.get('listaComentario') as FormArray; 
    }

    adicionarComentario() {
        this.formErrors["comentario"] = "";
        this.buildForm();
        this.criarMensagemValidacaoPorFormGroup(this.comentarioForm);
        this.setMensagensErro(this.comentarioForm);
    }

    private criarComentarioGroup(): FormGroup {
        return this.fb.group({
            comentario: [null,  Validators.required]
        });
    }

    /**
     * Retorna a lista com os comentários.
     * @author Bruno Sousa
     */
    listarComentarios(): ComentarioMatch[] {
        let retorno: ComentarioMatch[] = [];
        this.listaComentarioForm.controls.forEach((comentarioGroup: FormGroup) => {
            let comentarioMatch: ComentarioMatch = new ComentarioMatch();            
            comentarioMatch.comentario = comentarioGroup.get("comentario").value;   
            retorno.push(comentarioMatch);
        });
        return retorno;
    }

    public form(): FormGroup{
        return this.comentarioForm;
    }

    
    public preencherFormulario(comentarios: ComentarioMatch[]): void {
        
        if(comentarios){
            this.criarListacomentarios(comentarios.length);

            let index:number = 0;
            comentarios.forEach(comentario => {
                this.setPropertyValue("listaComentario." + index + ".comentario", comentario.comentario);
                index++;
            });
        }
    }

    /**
     * Verifica se o formulário é válido.
     * @author Rafael Pizão
     */
    public validateForm(): boolean {
        super.validateForm();
        this.resetMsgErrors();
        return this.verificarValidacao();
    }

    /**
     * verifica as obrigatoriedades de cada campo de comentário
     * @author Bruno Sousa
     * @return boolean se foi validado com sucesso ou não
     */
    private verificarValidacao():boolean{
        let valid: boolean = this.comentarioForm.valid;
        if(!valid){
            this.formErrors["comentario"] = this._mensagemCampoObrigatorio;
            return valid;
        }
        
        return true;
        
    }

    nomeComponente(): string {
        return null;
    }

    /**
     * reseta as mensagens de erro.
     * @author Rafael Pizão
     */
    resetMsgErrors(){
        this.formErrors['comentario'] = '';
    }

}