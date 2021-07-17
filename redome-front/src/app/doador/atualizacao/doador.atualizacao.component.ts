import { DataUtil } from '../../shared/util/data.util';
import { EmailContatoDoador } from '../email.contato.doador';
import { EnderecoContatoDoador } from '../endereco.contato.doador';
import { DoadorService } from '../doador.service';
import { TranslateService } from '@ngx-translate/core';
import { Router } from '@angular/router';
import { EnderecoContato } from '../../shared/classes/endereco.contato';
import { ContatoTelefonico } from '../../shared/classes/contato.telefonico';
import { DoadorDadosPessoaisComponent } from '../cadastro/contato/dadospessoais/doador.dadospessoais.component';
import { DoadorContatoTelefoneComponent } from '../cadastro/contato/telefone/doador.contato.telefone.component';
import { DoadorEmailContatoComponent } from '../cadastro/contato/email/doador.contato.email.component';
import { DoadorContatoEnderecoComponent } from '../cadastro/contato/endereco/doador.contato.endereco.component';
import { DoadorIdentificacaoComponent } from '../cadastro/identificacao/doador.identificacao.component';
import { HeaderDoadorComponent } from '../consulta/header/header.doador.component';
import { ContatoTelefoneComponent } from '../../shared/component/telefone/contato.telefone.component';
import { ContatoTelefonicoDoador } from '../contato.telefonico.doador';
import { BaseForm } from '../../shared/base.form';
import { Component, OnInit, ViewChild, Input } from "@angular/core";
import { FormBuilder, FormGroup } from '@angular/forms';
import { ErroMensagem } from '../../shared/erromensagem';
import { DoadorNacional } from '../doador.nacional';
import { EventEmitter } from 'events';
import { EventEmitterService } from 'app/shared/event.emitter.service';
import { ConvertUtil } from 'app/shared/util/convert.util';



export const ETAPA_ATUALIZACAO: number = 1;
export const ETAPA_QUESTIONARIO: number = 2;
export const ETAPA_HEMOCENTRO: number = 3;

/**
 * Classe que representa o componente de contato pra fase 2.
 */
@Component({
    selector: 'atualizar-doador',
    templateUrl: './doador.atualizacao.component.html',
    styleUrls: ['../doador.css']
})

export class DoadorAtualizacaoComponent extends BaseForm<DoadorNacional> implements OnInit {



    @Input()
    public fase: number;

    private contatosTelefonicosValidos: ContatoTelefonicoDoador[];
    idContatoTelefonico: number;

    private idTarefa: number;

    @ViewChild('modalMsg')
    public modalMsg;

    @ViewChild('contatoTelefoneComponent')
    public contatoTelefoneComponent: ContatoTelefoneComponent;

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    @ViewChild('doadorIdentificacaoComponent')
    private doadoridentificacao: DoadorIdentificacaoComponent;

    @ViewChild('doadorContatoEnderecoComponent')
    private doadorContatoEnderecoComponent: DoadorContatoEnderecoComponent;

    @ViewChild('doadorEmailContatoComponent')
    private doadorEmailContatoComponent: DoadorEmailContatoComponent;

    @ViewChild('doadorContatoTelefoneComponent')
    doadorContatoTelefoneComponent: DoadorContatoTelefoneComponent;

    @ViewChild('doadorDadosPessoaisComponent')
    private doadorDadosPessoaisComponent: DoadorDadosPessoaisComponent;

    private modalMsgRetorno;

    public doador: DoadorNacional;

    //@Input()
    //public dmr: number;

    private labelsInternacionalizadas: any;

    private contato: ContatoTelefonico;

    constructor(private fb: FormBuilder, private router: Router, translate: TranslateService,
        private doadorService: DoadorService) {

        super();
        this.translate = translate;
    }

    public preencherFormulario(entidade: DoadorNacional): void {
        throw new Error("Method not implemented.");
    }


    ngOnInit() {
    }

    public preencherContatoTelefonico(contatos: ContatoTelefonicoDoador[]) {
        if(contatos != null){
            this.contatosTelefonicosValidos = contatos.filter(c => { return !c.excluido });
        }
        else {
            this.contatosTelefonicosValidos = [];
        }

        this.doadorContatoTelefoneComponent.preencherDados(this.doador.id, this.contatosTelefonicosValidos);
    }

    public preencherDoadorComEntidade(doador:DoadorNacional){
        this.doador = doador;

        this.preencherContatoTelefonico(this.doador.contatosTelefonicos);

/*         if(this.doador.contatosTelefonicos != null){
            this.contatosTelefonicosValidos = this.doador.contatosTelefonicos.filter(c => { return !c.excluido });
        } */
        this.doadoridentificacao.preencherFormulario(this.doador);
        this.doadorContatoEnderecoComponent.preencherDados(this.doador.id, this.doador.enderecosContato);
        this.doadorEmailContatoComponent.preencherDados(this.doador.id, this.doador.emailsContato);
        
        this.doadorDadosPessoaisComponent.preencherFormulario(this.doador);
    }

    public preencherDoador(idDoador: number) {
        this.doadorService.obterDoadorPorId(idDoador)
        .then(res => {
            this.doador = ConvertUtil.parseJsonDoadorParaDoador(res.doador);
            Promise.resolve(this.headerDoador).then(() => {
                this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this.doador.id);
            });

            if (this.doador.contatosTelefonicos) {
                this.contatosTelefonicosValidos = this.doador.contatosTelefonicos.filter(c => { return !c.excluido });
            }

            this.doadoridentificacao.preencherFormulario(this.doador);
            this.doadorContatoEnderecoComponent.preencherDados(this.doador.id, this.doador.enderecosContato);
            this.doadorEmailContatoComponent.preencherDados(this.doador.id, this.doador.emailsContato);
            this.doadorContatoTelefoneComponent.preencherDados(this.doador.id, this.doador.contatosTelefonicos);
            this.doadorDadosPessoaisComponent.preencherFormulario(this.doador);
        });
    }

    nomeComponente(): string {
        return "DoadorAtualizacaoComponent";
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalMsg.mensagem = obj.mensagem;
        })
        this.modalMsg.abrirModal();
    }

    public form(): FormGroup {
        throw new Error("Method not implemented.");
    }

};