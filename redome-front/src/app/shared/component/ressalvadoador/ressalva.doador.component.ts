import {TranslateService} from '@ngx-translate/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild, OnDestroy } from '@angular/core';
import { DoadorService } from '../../../doador/doador.service';
import { MotivoStatusDoador } from '../../../doador/inativacao/motivo.status.doador';
import { ComponenteRecurso } from '../../enums/componente.recurso';
import { MensagemModalComponente } from '../../modal/mensagem.modal.component';
import { EventEmitterService } from '../../event.emitter.service';
import { PacienteUtil } from '../../paciente.util';
import { ErroMensagem } from '../../erromensagem';
import { RessalvaDoador } from '../../../doador/ressalva.doador';
import { BaseForm } from '../../base.form';
import { ActivatedRoute } from '@angular/router';
import { RessalvaService } from '../../../doador/cadastro/contato/ressalvas/ressalva.service';
import { RessalvaComponent } from '../../../doador/cadastro/contato/ressalvas/ressalva.component';

/**
 * Classe Component para exibir as ressalvas de um doador.
 * 
 * @author Pizão.
 */
@Component({
    selector: 'ressalva-doador',
    templateUrl: './ressalva.doador.component.html'
})
export class RessalvaDoadorComponent implements OnInit{
    
    public ressalvas: RessalvaDoador[];

    public idDoadorSelecionado: number;

    @ViewChild('modalError')
    private modalError: MensagemModalComponente;

    @ViewChild('modal')
    private modal;

    @ViewChild('ressalvaComponente')
    private ressalvaComponente: RessalvaComponent;


    constructor(private fb: FormBuilder, private doadorService: DoadorService, 
        protected translate: TranslateService, private activatedRouter: ActivatedRoute,
        private ressalvaService: RessalvaService){
    }

    ngOnInit(): void {}

    public fecharTela(){
        this.modal.hide();
    }

    public abrirTela(){
        this.modal.show();
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalError.mensagem = obj.mensagem;
        })
        this.modalError.abrirModal();
    }

    public exibirRessalvas(idDoador: number): void{
        this.idDoadorSelecionado = idDoador;
        this.ressalvaComponente.popularRessalvas(this.idDoadorSelecionado);
        this.abrirTela();
    }

}
