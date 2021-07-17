import { EventEmitterService } from '../../../shared/event.emitter.service';
import { DialogoBuscaBuilder } from './dialogo.busca.builder';
import { DialogoBuilder } from '../../../shared/dialogo/dialogo.builder';
import { DataUtil } from '../../../shared/util/data.util';
import { ErroMensagem } from '../../../shared/erromensagem';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DialogoComponent } from '../../../shared/dialogo/dialogo.component';
import { Busca } from '../busca';
import { BaseForm } from '../../../shared/base.form';
import { ViewEncapsulation, OnInit, Input, Component, ViewChild, Output, EventEmitter } from '@angular/core';
import { DialogoBuscaService } from './dialogo.busca.service';
import { DialogoBusca } from './dialogo.busca';
import { Comentario } from '../../../shared/dialogo/comentario';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
/**
 * Classe Component para padronizar o modal
 * @author Filipe Paes
 */
@Component({
    moduleId: module.id,
    selector: 'dialogo-busca',
    templateUrl: './dialogo.busca.component.html',
    encapsulation: ViewEncapsulation.None
})
export class DialogoBuscaComponent extends BaseForm<Object> implements OnInit {
    
    

    @ViewChild("modalErro")
    private modalErro;


    @ViewChild("dialogoComponent")
    public dialogoComponent: DialogoComponent;

    private _usernameUsuarioLogado: string;
    public dialogoForm: FormGroup;
    public esconderCaixaResponderPendencia: boolean = true;
    private _listaDialogos:DialogoBusca[];
    exibePerguntaSeResponde: boolean = false;


    
    private _idBusca:number;
    /*
    * Mensagem que deverá ser exibida no {{exameDescartadoModal}}
    */
    mensagemModal: string;

    ngOnInit() {
        this.dialogoForm = this._fb.group({
            'comentario': [null, Validators.required]
        });
        this.translate.get("dialogoForm").subscribe(res => {
            this._formLabels = res;
            super.criarMensagemValidacaoPorFormGroup(this.dialogoForm);
        });
    }
    
    constructor(private _fb: FormBuilder, private autenticacaoService: AutenticacaoService,
        private dialogoBuscaService: DialogoBuscaService, private router: Router, translate: TranslateService) {
            super();
            this.translate = translate;
    }

    /**
     *Verificar aqui se o usuario logado é o mesmo da avaliacao
     *Se sim, as resposta do usuario avaliador serão o balão verde
     *Caso contrario é o login do medico e deve exibir o balão verde para
     *os comentários do médico.
     * @private
     * @param {any} idUsuario 
     * @returns {string} 
     * @memberof DialogoComponente
     */
    getEstiloBalaoConversa(usernameRespostaAtual?: string): string {
        if (usernameRespostaAtual) {
            return this.buscarEstiloPorTextoAtual(usernameRespostaAtual);
        }
    }


    /**
     * Busca o estilo por cada texto da conversa, pois cada texto é de 
     * um usuario diferente
     * 
     * @private
     * @param {string} [usernameRespostaAtual] 
     * @returns {string} 
     * @memberof DialogoComponente
     */
    private buscarEstiloPorTextoAtual(usernameRespostaAtual?: string): string {
        if (usernameRespostaAtual == this._usernameUsuarioLogado) {
            return "balaoPendenciaResposta";
        }
        return "balaoPendenciaPergunta";
    }
   
    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalErro.mensagem = obj.mensagem;
        })
        this.modalErro.abrirModal();
    }


    public form(): FormGroup {
        return this.dialogoForm;
    }


    nomeComponente(): string {
        return "DialogoBuscaComponent";
    }

    public preencherFormulario(entidade: Object): void {
        throw new Error("Method not implemented.");
    }

    /**
     * Getter idBusca
     * @return {number}
     */
	public get idBusca(): number {
		return this._idBusca;
	}

    /**
     * Setter idBusca
     * @param {number} value
     */
	public set idBusca(value: number) {
		this._idBusca = value;
	}

    /**
     * Lista o diálogo de busca.
     * @param idBusca id da busca da listagem de diálogo.
     */
    carregarListaDialogo(idBusca:number){
        this.ngOnInit();
        this.idBusca = idBusca;
        this.dialogoBuscaService.listarDialogos(idBusca).then(res=>{
            this._listaDialogos = res;
            this.dialogoComponent.exibirUsuario = true;
            this.dialogoComponent.dialogoBuilder = new DialogoBuscaBuilder().buildComentarios(res);
            } , (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            }
        );
    }

    /**
     * Registra o diálogo de busca.
     */
    comentar(){
        if (this.validateForm()) {
            this.fecharDialogo();
            this.dialogoBuscaService.gravarDialogo(this.obterDialogoBusca()).then(res=>{
            } , (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
        }
    }
    
    
    /**
     * Fecha o diálogo.
     */
    fecharDialogo(){
        EventEmitterService.get("fecharModalDialogo").emit();
    }

    /**
     * Monta o objeto de diálogo de busca.
     */
    obterDialogoBusca():DialogoBusca{
        let dialogo:DialogoBusca = new DialogoBusca();
        dialogo.busca = new Busca()
        dialogo.busca.id = this.idBusca;
        dialogo.mensagem = this.dialogoForm.get("comentario").value;
        return dialogo;
    }
    
}