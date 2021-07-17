import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {HeaderDoadorInternacionalComponent} from 'app/doadorinternacional/identificacao/header.doador.internacional.component';
import {ExameCordaoInternacional} from '../../../doador/exame.cordao.internacional';
import {ExameDoadorInternacional} from '../../../doador/exame.doador.internacional';
import {InativacaoComponent} from '../../../doador/inativacao/inativacao.component';
import {SolicitacaoService} from '../../../doador/solicitacao/solicitacao.service';
import {PedidoExameService} from '../../../laboratorio/pedido.exame.service';
import {PedidoIdmService} from '../../../laboratorio/pedido.idm.service';
import {BaseForm} from '../../../shared/base.form';
import {ComponenteRecurso} from '../../../shared/enums/componente.recurso';
import {TiposDoador} from '../../../shared/enums/tipos.doador';
import {ErroMensagem} from '../../../shared/erromensagem';
import {HistoricoNavegacao} from '../../../shared/historico.navegacao';
import {HlaComponent} from '../../../shared/hla/hla.component';
import {Modal} from '../../../shared/modal/factory/modal.factory';
import {UploadArquivoComponent} from "../../../shared/upload/upload.arquivo.component";
import {ErroUtil} from "../../../shared/util/erro.util";
import {TarefaService} from "../../../shared/tarefa.service";
import {RouterUtil} from "../../../shared/util/router.util";
import {MessageBox} from "../../../shared/modal/message.box";

/**
 * Classe que representa o componente de transportadora
 */
@Component({
    selector: "resultado-ct-doador-internacional",
    templateUrl: './resultado.ct.component.html'
})
export class ResultadoCTComponent extends BaseForm<any> implements OnInit {

    @ViewChild("hlaComponent")
    private hlaComponent: HlaComponent;

    @ViewChild("inativacaoDoador")
    public inativacaoDoador: InativacaoComponent;

    @ViewChild("uploadComponent")
    private uploadComponent: UploadArquivoComponent;

    @ViewChild("headdoadorinternacional")
    public headerDoadorInternacional:HeaderDoadorInternacionalComponent;

    private idPedidoExame: number;

    private _idSolicitacao: number;
    private _idTarefa: number;
    private _idTarefaCadastrarPedidoIdm:number;

    private laudoPedidoCTCadastrado: string;
    private laudoCadastradoSugerindoRedirecionadoParaIDM: string;
    private _tipoDoador: string;
    private _idDoador: number;

    constructor(private messageBox: MessageBox, private router: Router,
        private solicitacaoService: SolicitacaoService,
        private tarefaService: TarefaService,
        private activatedRoute: ActivatedRoute,
        private pedidoExameService: PedidoExameService,
        private pedidoIdmService: PedidoIdmService,
        translate:TranslateService) {
            super();
            this.translate = translate;

        this.translate.get("mensagem.sucesso").subscribe(mensagem => {
            this.laudoPedidoCTCadastrado = mensagem.laudoPedidoCTCadastrado;
            this.laudoCadastradoSugerindoRedirecionadoParaIDM = mensagem.laudoPedidoCTCadastradoIdmPendenteAtalho;
        });

    }

    public voltar() {
        this.tarefaService.removerAtribuicaoTarefa(this._idTarefa)
            .then(result => {
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            }, (erro: ErroMensagem) => {
                this.messageBox.alert(ErroUtil.extrairMensagensErro(erro)).show();
            });
    }

    public form() {
        return null;
    }

    ngOnInit(): void {
        this.uploadComponent.extensoes = "extensaoArquivoLaudo";
        this.uploadComponent.tamanhoLimite = "tamanhoArquivoLaudoEmByte";

        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRoute, ["idSolicitacao", "idTarefa", "idDoador"]).then(params => {
            this._idSolicitacao = <number>params['idSolicitacao'];
            this._idTarefa = <number>params['idTarefa'];
            this._idDoador = <number>params['idDoador'];

            this.pedidoIdmService.obterInformacoesPedidoIdm(this._idSolicitacao)
                .then(result => {
                    this._idTarefaCadastrarPedidoIdm = result.idTarefaPedidoIdm;
                },
                    (erro: ErroMensagem) => {
                        this.messageBox.alert(ErroUtil.extrairMensagensErro(erro)).show();
                    });

            this.tarefaService.atribuirTarefaParaUsuarioLogado(this._idTarefa)
            .then(result => {
            }, (erro: ErroMensagem) => {
                let modal: Modal = this.messageBox.alert(ErroUtil.extrairMensagensErro(erro))
                modal.closeOption = () => {
                    this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                }
                modal.show();
            });

            this.solicitacaoService.obterPedidoExamePorSolicitacaoId(this._idSolicitacao).then(res => {
                this.idPedidoExame = <number>res.pedidoExame.id;
                this._tipoDoador = res.pedidoExame.solicitacao.match.doador.tipoDoador;
                let locusObrigatorios: string[] = res.pedidoExame.locusSolicitados.map(locusEntidade => {
                    return locusEntidade.codigo;
                })
                if(this._tipoDoador == 'INTERNACIONAL'){
                    this.hlaComponent.comAntigeno = true;
                }
                this.hlaComponent.locusObrigatorios = locusObrigatorios;
            })

            if(this._idDoador){
                this.headerDoadorInternacional.popularCabecalho(this._idDoador);
            }
        });

    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ResultadoCTComponent];
    }

    /**
     * Getter idSolicitacao
     * @return {number}
     */
    public get idSolicitacao(): number {
        return this._idSolicitacao;
    }

    /**
     * Setter idSolicitacao
     * @param {number} value
     */
    public set idSolicitacao(value: number) {
        this._idSolicitacao = value;
    }

    public preencherFormulario(entidade: any): void {
        throw new Error("Method not implemented.");
    }

    // irParaCadastrarPedidoIdm(target?: any) {
    //     target.router.navigateByUrl("/doadoresinternacionais/pedidoexame/" + target.idSolicitacao + "/resultadoIDM?idTarefa="+this._idTarefaCadastrarPedidoIdm);
    // }


    salvar() {
        let hlaValido: boolean = this.hlaComponent.validateForm();
        let laudoIncluido: boolean = this.uploadComponent.validateForm();

        if (hlaValido && laudoIncluido) {
            if (TiposDoador[TiposDoador.INTERNACIONAL] == this._tipoDoador) {
                let exame: ExameDoadorInternacional = new ExameDoadorInternacional();
                exame.locusExames = this.hlaComponent.getValue();

                this.pedidoExameService.enviarResultadoPedidoExameCTDoadorInternacional(
                    this.idPedidoExame, exame, this.inativacaoDoador.inativarForm.get('motivoStatusDoador').value,
                    this.inativacaoDoador.inativarForm.get('tempoAfastamento').value, this.uploadComponent.arquivos)
                    .then(res => {
                            this.retornoSavarPedidoExame(res);
                        },
                        (error: ErroMensagem) => {
                            ErroUtil.exibirMensagemErro(error, this.messageBox);
                        });
            }
            else if (TiposDoador[TiposDoador.CORDAO_INTERNACIONAL] == this._tipoDoador) {
                let exame: ExameCordaoInternacional = new ExameCordaoInternacional();
                exame.locusExames = this.hlaComponent.getValue();

                this.pedidoExameService.enviarResultadoPedidoExameCTCordaoInternacional(
                    this.idPedidoExame, exame, this.inativacaoDoador.inativarForm.get('motivoStatusDoador').value,
                    this.inativacaoDoador.inativarForm.get('tempoAfastamento').value, this.uploadComponent.arquivos)
                    .then(res => {
                            this.retornoSavarPedidoExame(res);
                        },
                        (error: ErroMensagem) => {
                            ErroUtil.exibirMensagemErro(error, this.messageBox);
                        });
            }
        }
    }

    public voltarSemDesatribuir(target: any) {
        target.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }

    /**
     * Setter idTarefaCadastrarPedidoIdm
     * @param {number} value
     */
	public set idTarefaCadastrarPedidoIdm(value: number) {
		this._idTarefaCadastrarPedidoIdm = value;
    }

    private retornoSavarPedidoExame(res) {
        if (this._idTarefaCadastrarPedidoIdm) {
            let messageBox = this.messageBox.confirmation(this.laudoCadastradoSugerindoRedirecionadoParaIDM);
            // messageBox.yesOption = this.irParaCadastrarPedidoIdm;
            messageBox.yesOption = () => {
                HistoricoNavegacao.removerUltimoAcesso();
                this.router.navigateByUrl("/doadoresinternacionais/pedidoexame/" + this.idSolicitacao + "/resultadoIDM?idTarefa="+this._idTarefaCadastrarPedidoIdm);
            };

            messageBox.noOption = () =>{
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            }
            messageBox.closeOption = () =>{
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            }
            messageBox.withTarget(this);
            messageBox.show();
        } else {

            this.messageBox.alert(this.laudoPedidoCTCadastrado)
                .withTarget(this)
                .withCloseOption(this.voltarSemDesatribuir)
                .show();
        }
    }

};
