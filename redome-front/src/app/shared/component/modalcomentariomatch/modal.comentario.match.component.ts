import { CampoMensagem } from '../../campo.mensagem';
import { IdentificacaoComponent } from '../../../paciente/cadastro/identificacao/identificacao.component';
import { forEach } from '@angular/router/src/utils/collection';
import { Observable } from 'rxjs';
import { BaseForm } from '../../base.form';
import { TranslateService } from '@ngx-translate/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { Component, OnInit, Input, EventEmitter, Output, ViewChild } from '@angular/core';
import { DominioService } from '../../dominio/dominio.service';
import { ComentarioMatch } from '../../classes/comentario.match';
import { MatchService } from '../../../doador/solicitacao/match.service';
import { MockMatchService } from '../../mock/mock.match.service';
import { Match } from '../../../paciente/busca/match';
import { AutenticacaoService } from '../../autenticacao/autenticacao.service';
import { ComentarioMatchComponent } from '../comentariomatch/comentario.match.component';
import { MockComentarioMatchService } from '../../mock/mock.comentario.match.service';
import { ErroMensagem } from '../../erromensagem';
import { ComentarioMatchService } from '../../service/comentrio.match.service';
import { MatchDTO } from 'app/shared/component/listamatch/match.dto';

/**
 * Classe que representa o componente de comentários de um match
 * @author Bruno Sousa
 */
@Component({
    selector: "modal-comentario-match",
    templateUrl: "./modal.comentario.match.component.html"
})
export class ModalComentarioMatchComponent implements OnInit {

    @ViewChild('modalComentarioMatch')
    private modalComentarioMatch;

    @ViewChild("comentarioMatchComponent")
    private comentarioMatchComponent: ComentarioMatchComponent;

    @ViewChild("modalErro")
    private modalErro;

    @Output()
    public fecharComentarioMatch: EventEmitter<any> = new EventEmitter<any>();
        
    private _mensagemCampoObrigatorio: string;
    private _match: MatchDTO;
    private _lista: ComentarioMatch[];
    private _esconderLinkIncluirComentario: boolean = false;

    public mostraDados: string = '';
    public mostraFormulario: string = 'hide';
    
    /**
     * Cria uma instancia de ModalComentarioMatchComponent.
     * @author Bruno Sousa
     * @param {FormBuilder} fb 
     * @param {TranslateService} translate 
     * @param {MatchService} matchService 
     * @param {AutenticacaoService} autenticacaoService 
     * @param {ComentarioMatchService} comentarioMatchService 
     * @memberof ModalComentarioMatchComponent
     */
    constructor(private fb: FormBuilder, private translate: TranslateService,
        private matchService: MatchService, private autenticacaoService: AutenticacaoService,
        private comentarioMatchService: ComentarioMatchService) {
        this.translate = translate;
    }

    ngOnInit() {
        this.translate.get("mensagem.erro.camposObrigatorios").subscribe(res => {
            this._mensagemCampoObrigatorio = res;
        });
    }

    /**
     * Método utilizado para abrir o modal e carregar a lista de comentários
     * 
     * @author Bruno Sousa
     * @param {number} idMatch 
     * @memberof ModalComentarioMatchComponent
     */
    public mostrarComentario(match: MatchDTO) {
        this._match = match;
        this.matchService.listarComentarios(this._match.id).then(res => {
            this._lista = [];
            if (res && res.length != 0) {
                res.forEach(item => {
                    let comentario: ComentarioMatch = new ComentarioMatch();
                    comentario.id = item.id;
                    comentario.comentario = item.comentario;
                    let match: Match = new Match();
                    match.id = item.match.id;
                    comentario.match = match;

                    this._lista.push(comentario);
                });
            }            
        })
        .catch(erro => {
            this.exibirMensagemErro(erro);
        })

        this.modalComentarioMatch.show();        
    }

    /**
     * Método que fecha o modal de comentários, se possuir um evento este é acionado
     * passando o id do match e se possuir comentários
     * 
     * @author Bruno Sousa
     * @memberof ModalComentarioMatchComponent
     */
    public fecharComentario() {
        this.modalComentarioMatch.hide();
        if(this.fecharComentarioMatch.observers.length > 0){
            this._match.possuiComentario = this._lista.length != 0;
            let event: any = {
                match: this._match,
                //possuiComentario: this._lista.length != 0
            }
            this.fecharComentarioMatch.emit(event);
        }
    }

    /**
     * 
     * @readonly
     * @type {ComentarioMatch[]}
     * @memberof ModalComentarioMatchComponent
     */
    public get lista(): ComentarioMatch[] {
		return this._lista;
    }

    /**
     *  Metodo utilizado pelo link de incluir um comentário
     * 
     * @author Bruno Sousa
     * @memberof ModalComentarioMatchComponent
     */
    public incluir() {
        this.comentarioMatchComponent.adicionarComentario();
        this.mostraDados = 'hide';
        this.mostraFormulario = '';
    }

    /**
     * Método para cancelar edição
     * 
     * @author Bruno Sousa
     * @memberof ModalComentarioMatchComponent
     */
    cancelarEdicao() {
        this.mostraFormulario = 'hide';
        this.mostraDados = '';        
    }

    /**
     * Método para salvar um comentário
     * 
     * @author Bruno Sousa
     * @memberof ModalComentarioMatchComponent
     */
    salvarComentario() {
        if (this.comentarioMatchComponent.validateForm()) {            
            let comentario: ComentarioMatch = this.listarComentarios()[0];            
            
            this.comentarioMatchService.incluirComentarioMatch(comentario).then(res => {
                comentario.id = res.idObjeto;
                this._lista.push(comentario);
                this.cancelarEdicao();
            }).catch(error => {
                this.exibirMensagemErro(error);
            });
        }
    }

    /**
     * Método que verifica permissão do usuário para adicionar comentário
     * 
     * @author Bruno Sousa
     * @returns {boolean} 
     * @memberof ModalComentarioMatchComponent
     */
    temPermissaoParaIncluirComentario(): boolean {
        return this.autenticacaoService.temRecurso('ADICIONAR_COMENTARIO_MATCH');
    }

    /**
     * Método que retorna se deve esconder o link de incluir comentário
     * 
     * @author Bruno Sousa
     * @returns {boolean} 
     * @memberof ModalComentarioMatchComponent
     */
    deveEsconderLinkIncluirComentario(): boolean {
        return this._esconderLinkIncluirComentario || !this.temPermissaoParaIncluirComentario();
    }

    @Input()
    public set esconderLinkIncluirComentario(value: string ) {
        if (value) {
            this._esconderLinkIncluirComentario = true;
        }
        else {
            this._esconderLinkIncluirComentario = false;
        }
    }
    
    /**
     * Retorna a lista com os comentários.
     * @author Bruno Sousa
     */
    listarComentarios(): ComentarioMatch[] {
        let retorno: ComentarioMatch[] = [];
        this.comentarioMatchComponent.listarComentarios().forEach(comentario => {
            let comentarioMatch: ComentarioMatch = new ComentarioMatch();
            comentarioMatch.comentario = comentario.comentario;
            let match: Match = new Match();
            match.id = this._match.id;
            comentarioMatch.match = match;

            retorno.push(comentarioMatch);            
        })

        return retorno;
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalErro.mensagem = obj.mensagem;
        })
        this.modalErro.abrirModal();
    } 
    
}