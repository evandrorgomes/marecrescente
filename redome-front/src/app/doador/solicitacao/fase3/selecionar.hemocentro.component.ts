import {TranslateService} from '@ngx-translate/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Component, EventEmitter, Input, OnInit, Output, ViewChild, OnDestroy } from '@angular/core';
import { DoadorService } from '../../doador.service';
import { MotivoStatusDoador } from '../../inativacao/motivo.status.doador';
import { DoadorNacional } from '../../doador.nacional';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { MensagemModalComponente } from '../../../shared/modal/mensagem.modal.component';
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { PacienteUtil } from '../../../shared/paciente.util';
import { SolicitacaoService } from '../solicitacao.service';
import { HemoEntidade } from '../hemoentidade';
import { EnderecoContatoDoador } from '../../endereco.contato.doador';
import { BaseForm } from '../../../shared/base.form';
import { DoadorContatoEnderecoComponent } from 'app/doador/cadastro/contato/endereco/doador.contato.endereco.component';

/**
 * Classe Component para inativação do doador.
 * 
 * @author Pizão.
 */
@Component({
    selector: 'selecionar-hemocentro',
    templateUrl: './selecionar.hemocentro.component.html'
})
export class SelecionarHemocentroComponent implements OnInit, OnDestroy{
    
    //private listaEnderecoSubscribe: EventEmitter<any>;
    public selecionarHemocentroForm: FormGroup;
    public hemoentidades: HemoEntidade[] = null;
    public mensagemErro: any = {};
    private mensagemObrigatorio: any = {};
    
    public _enderecosDoador: EnderecoContatoDoador[];

    // Guarda qual foi a hemoentidade selecionada na combo.
    @Output()
    public selecionada: HemoEntidade;


    constructor(private fb: FormBuilder, private doadorService: DoadorService, 
        private solicitacaoService: SolicitacaoService,
        protected translate: TranslateService){

        this.translate.get('mensagem.erro.obrigatorio', {campo: 'hemocentro'}).subscribe(res => {
            this.mensagemObrigatorio['hemoentidade'] = res;
        });

        this.translate.get('mensagem.erro.obrigatorio', {campo: 'endereço'}).subscribe(res => {
            this.mensagemObrigatorio['endDoador'] = res;
        });

        this.selecionarHemocentroForm = this.fb.group({
            'hemoentidade': [null, Validators.required],
            'endDoador': [null, Validators.required]
        });
    }

    public marcarComoObrigatorio(nomeCampo: string): void{

        if(this.selecionarHemocentroForm.get(nomeCampo).invalid){
            this.mensagemErro[nomeCampo] = this.mensagemObrigatorio[nomeCampo];
        }
        else {
            this.mensagemErro[nomeCampo] = '';
        }
    }

    public isFormularioInvalido(): boolean{
        this.marcarComoObrigatorio('hemoentidade');
        this.marcarComoObrigatorio('endDoador');
        return this.selecionarHemocentroForm != null && this.selecionarHemocentroForm.invalid;
    }

    ngOnInit(): void {
    }

    ngOnDestroy() {
        this._enderecosDoador = null;
    }
    
    public preencherFormulario(entidade: DoadorNacional): void {
        throw new Error("Method not implemented.");
    }

    public selecionarEndDoador(uf: string): void{
        if(uf){
            this.solicitacaoService.listarHemoEntidadesPorUf(uf)
                    .then(res => {
                this.hemoentidades = res;
            });
        } else {
            this.hemoentidades = null;
        }
    }

    public selecionarHemoEntidade(hemoId: number): void{
        if(hemoId){
            const hemoentidade: HemoEntidade =
                    this.hemoentidades.find(hemo => {
                return hemo.id == hemoId;
            });
            this.selecionada = hemoentidade;
        } else {
            this.selecionada = null;
        }
    }

    @Input('enderecos')
    public set enderecosDoador(value: EnderecoContatoDoador[] ){
        this._enderecosDoador = value;
        if( this._enderecosDoador == null ||  this._enderecosDoador.length == 0){
            this.hemoentidades = [];
            this.selecionarHemocentroForm.get('hemoentidade').setValue(null);
            this.selecionarHemocentroForm.get('endDoador').setValue(null);
        }
    }
}
