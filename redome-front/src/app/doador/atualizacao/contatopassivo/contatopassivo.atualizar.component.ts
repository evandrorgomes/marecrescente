import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AlteracaoStatusComponent } from '../alteracaostatus/alteracaostatus.component';
import { DoadorAtualizacaoComponent } from '../doador.atualizacao.component';
import { HeaderDoadorComponent } from '../../consulta/header/header.doador.component';
import { ContatoTelefonicoDoador } from '../../contato.telefonico.doador';
import { Doador } from '../../doador';
import { DoadorService } from '../../doador.service';
import { EmailContatoDoador } from '../../email.contato.doador';
import { EnderecoContatoDoador } from '../../endereco.contato.doador';
import { TentativaContatoDoador } from '../../solicitacao/fase2/tentativa.contato.doador';
import { Solicitacao } from '../../solicitacao/solicitacao';
import { TiposSolicitacao } from '../../../shared/enums/tipos.solicitacao';
import { ErroMensagem } from '../../../shared/erromensagem';
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { RouterUtil } from '../../../shared/util/router.util';
import { MensagemModalComponente } from '../../../shared/modal/mensagem.modal.component';
import { DoadorNacional } from '../../doador.nacional';
import { ConvertUtil } from '../../../shared/util/convert.util';
import { MessageBox } from "app/shared/modal/message.box";
import { ErroUtil } from "app/shared/util/erro.util";
import { StatusDoador } from "app/shared/dominio/status.doador";
import { AcaoDoadorInativo } from "app/shared/enums/acao.doador.inativo";


/**
 * Classe que representa o componente de contato pra fase 2.
 */
@Component({
    selector: 'contato-passivo-atualizar',
    templateUrl: './contatopassivo.atualizar.component.html',
    styleUrls: ['../../doador.css']
})

export class ContatoPassivoAtualizarComponent implements OnInit {

    @ViewChild('doadorAtualizacaoComponent')
    private doadorAtualizacaoComponent: DoadorAtualizacaoComponent;

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    public doador: DoadorNacional;
    public solicitacao: Solicitacao;

    @ViewChild(AlteracaoStatusComponent)
    private modalAlteracaoStatus: AlteracaoStatusComponent;

    private _id: number;

    constructor(private fb: FormBuilder, private router: Router, private translate: TranslateService,
        private activatedRouter: ActivatedRoute, private doadorService: DoadorService, private messageBox: MessageBox) {

    }

    ngOnInit() {

        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'id').then(res => {
            this._id = res;
            this.doadorService.obterDoadorPorId(this._id, true)
                .then(res => {

                    this.doador = ConvertUtil.parseJsonDoadorParaDoador(res.doador);
                    
                    Promise.resolve(this.headerDoador).then(() => {
                        this.headerDoador.clearCache();
                        this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this.doador.id);
                    });
                    
                    this.doadorAtualizacaoComponent.preencherDoadorComEntidade(this.doador);
                })
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

    }


    /**
     * Abre o modal de alteração de status
     */
    public abrirModalAlteracaoStatus() {
        this.modalAlteracaoStatus.doador = this.doador;
        this.modalAlteracaoStatus.abrirModal();

        EventEmitterService.get(AlteracaoStatusComponent.FINALIZAR_EVENTO_ALTERACAO_STATUS).subscribe((contatoDto: any): void => {
            this.doador = contatoDto.doador;
            this.solicitacao = contatoDto.solicitacao;
            this.headerDoador.clearCache();
            this.headerDoador.popularCabecalhoIdentificacaoNoCache(this.doador.dmr);

        });
    }
    
    public voltarConsulta() {
        this.router.navigateByUrl('/doadores/consulta/consultaContatoPassivo');
    }

    nomeComponente(): string {
        return "ContatoPassivoAtualizarComponent";
    }

    deveExibirBotaoAlterarStatus(): boolean {
        if (this.doador != null && this.doador != undefined) {
            if (this.doador.statusDoador.id == StatusDoador.ATIVO || this.doador.statusDoador.id == StatusDoador.ATIVO_RESSALVA ) {
                return true;
            }
            if (this.doador.motivoStatusDoador != null && this.doador.motivoStatusDoador != undefined) {
                return this.doador.motivoStatusDoador.acaoDoadorInativo.id === AcaoDoadorInativo.ATIVAR.id;
            }
        }
        return false;
    }

};