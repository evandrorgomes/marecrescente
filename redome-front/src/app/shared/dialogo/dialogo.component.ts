import { ComponenteRecurso } from '../enums/componente.recurso';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AutenticacaoService } from '../autenticacao/autenticacao.service';
import { Observable } from 'rxjs';
import { Component, Input, ViewChild, EventEmitter, Output, OnInit, ViewEncapsulation } from '@angular/core';
import { Comentario } from './comentario';
import { DialogoBuilder } from './dialogo.builder';
import { initDomAdapter } from '@angular/platform-browser/src/browser';

/**
 * Classe Component para padronizar o modal
 * @author Fillipe Queiroz
 */
@Component({
    moduleId: module.id,
    selector: 'dialogo',
    templateUrl: './dialogo.component.html',
    styleUrls: ['./dialogo.component.css'],
    encapsulation: ViewEncapsulation.None
})
export class DialogoComponent {

    public dialogoBuilder: DialogoBuilder;

    @Input("exbirUsuario")
    private _exibirUsuario:boolean;

    @Output()
    public irParaRota: EventEmitter<string> = new EventEmitter<string>();

    constructor(private _fb: FormBuilder,
        private router: Router, private translate: TranslateService,
        private autenticacaoService: AutenticacaoService) {
        this.translate = translate;
        this.exibirUsuario = false;
    }

    /**
     * Verificar aqui se o usuario logado é o mesmo da avaliacao
     * Se sim, as resposta do usuario serão o balão verde.
     * @param  {boolean} usuarioAtualEhUsuarioLogado
     * @returns string
     */
    private getEstiloBalaoConversa(username: string): string {
        if (this.autenticacaoService.usuarioLogado().username == username) {
            return "balaoPendenciaResposta";

        }
        return "balaoPendenciaPergunta";
    }

    public abrirRota(link: string) {
        if (this.irParaRota.observers.length > 0) {
            this.irParaRota.emit(link);
        } else {
            this.router.navigateByUrl(link);
        }
    }

    
    /**
     * Getter exibirUsuario
     * @return {boolean}
     */
	public get exibirUsuario(): boolean {
		return this._exibirUsuario;
	}

    /**
     * Setter exibirUsuario
     * @param {boolean} value
     */
	public set exibirUsuario(value: boolean) {
		this._exibirUsuario = value;
	}
}