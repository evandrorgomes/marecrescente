import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { HeaderDoadorInternacionalComponent } from 'app/doadorinternacional/identificacao/header.doador.internacional.component';
import { PedidoExameService } from '../../../../laboratorio/pedido.exame.service';
import { PedidoIdmService } from '../../../../laboratorio/pedido.idm.service';
import { CampoMensagem } from '../../../../shared/campo.mensagem';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { Modal, ModalConfirmation } from '../../../../shared/modal/factory/modal.factory';
import { MessageBox } from '../../../../shared/modal/message.box';
import { TarefaService } from '../../../../shared/tarefa.service';
import { UploadArquivoComponent } from '../../../../shared/upload/upload.arquivo.component';
import { ErroUtil } from '../../../../shared/util/erro.util';
import { RouterUtil } from '../../../../shared/util/router.util';


/**
 * Classe que representa o componente de cadastrar resultado
 * de pedido de IDM.
 *
 * @author Pizão
 */
@Component({
    selector: 'resultado-pedido-idm-component',
    templateUrl: './resultado.pedido.idm.component.html'
})
export class ResultadoPedidoIdmComponent implements OnInit {

    private idTarefaCadastrarPedidoCT: number;
    private idTarefaCadastrarPedidoIDM: number;
    private solicitacaoId: number;
    private pedidoIdmId: number;
    private laudoCadastrado: string;
    private laudoCadastradoSugerindoRedirecionadoParaCT: string;
    private _idDoador: number;

    @ViewChild("uploadComponent")
    private uploadComponent: UploadArquivoComponent;

    @ViewChild("headdoadorinternacional")
    public headerDoadorInternacional:HeaderDoadorInternacionalComponent;

    constructor(private router: Router, private translate: TranslateService,
            private activatedRouter: ActivatedRoute, private messageBox: MessageBox,
            private tarefaService: TarefaService, private pedidoExameService: PedidoExameService,
            private pedidoIdmService: PedidoIdmService) {

            this.translate.get("mensagem.sucesso").subscribe(mensagem => {
                this.laudoCadastrado = mensagem.laudoPedidoIdmCadastrado;
                this.laudoCadastradoSugerindoRedirecionadoParaCT = mensagem.laudoPedidoIdmCadastradoExameCtPendenteAtalho;
            });
    }

    ngOnInit(): void {
        this.uploadComponent.extensoes = "extensaoArquivoResultadoPedidoIdm";
        this.uploadComponent.tamanhoLimite = "tamanhoArquivoResultadoPedidoIdmEmByte";

        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ['idTarefa', 'buscaId', 'idSolicitacao', 'idDoador']).then(res => {
            this.idTarefaCadastrarPedidoIDM = res.idTarefa;
            this.solicitacaoId = res.idSolicitacao;
            this._idDoador = res.idDoador;

            this.tarefaService.atribuirTarefaParaUsuarioLogado(this.idTarefaCadastrarPedidoIDM)
                .then(result => {
                        this.pedidoIdmService.obterInformacoesPedidoIdm(this.solicitacaoId)
                            .then(result => {
                                this.pedidoIdmId = result.idPedidoIdm;
                                this.idTarefaCadastrarPedidoCT = result.idTarefaPedidoCT;
                            },
                            (erro: ErroMensagem) => {
                                this.messageBox.alert(ErroUtil.extrairMensagensErro(erro)).show();
                            });
                }, (erro: ErroMensagem) => {
                    let modal: Modal = this.messageBox.alert(ErroUtil.extrairMensagensErro(erro))
                    modal.closeOption = () => {
                        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                    }
                    modal.show();
                });

            if(this._idDoador){
                this.headerDoadorInternacional.popularCabecalho(this._idDoador);
            }
        });

    }

    nomeComponente(): string {
        return "ResultadoPedidoIdmComponent";
    }

    voltar(): void{
        this.tarefaService.removerAtribuicaoTarefa(this.idTarefaCadastrarPedidoIDM)
            .then(result => {
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            }, (erro: ErroMensagem) => {
                this.messageBox.alert(ErroUtil.extrairMensagensErro(erro)).show();
            });
    }

    salvar(): void{
        if(this.validateForm()){
            this.pedidoIdmService.salvarResultadoIdmInternacional(
                this.pedidoIdmId, this.uploadComponent.arquivos)
                    .then((campoMensagem: CampoMensagem) => {
                        if(this.idTarefaCadastrarPedidoCT == null){
                            let modal: Modal = this.messageBox.alert(this.laudoCadastrado);
                            modal.closeOption = () => {
                                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                            }
                            modal.show();
                        }
                        else {
                            let modal: ModalConfirmation =
                                this.messageBox.confirmation(this.laudoCadastradoSugerindoRedirecionadoParaCT);
                            modal.yesOption = () => {
                                HistoricoNavegacao.removerUltimoAcesso();
                                    this.router.navigateByUrl(
                                        "doadoresinternacionais/pedidoexame/" + this.solicitacaoId + "/resultadoCT?idTarefa=" + this.idTarefaCadastrarPedidoCT);
                            };
                            modal.noOption = () => {
                                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                            };
                            modal.closeOption = () => {
                                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                            };
                            modal.show();
                        }
                    },
                    (erro: ErroMensagem) => {
                        this.messageBox.alert(ErroUtil.extrairMensagensErro(erro)).show();
                    });
        }
    }

    public validateForm(): boolean{
        return this.uploadComponent.validateForm();
    }

}
