import { Component, OnInit, ViewChild } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { CentroTransplanteService } from "app/admin/centrotransplante/centrotransplante.service";
import { HeaderDoadorComponent } from "app/doador/consulta/header/header.doador.component";
import { CentroTransplante } from "app/shared/dominio/centro.transplante";
import { ItensChecklist } from "app/shared/dominio/item.checklist";
import { RespostaChecklist } from "app/shared/dominio/resposta.checklist";
import { DetalheLogisticaMaterialDTO } from "app/shared/dto/detalhe.logistica.material.dto";
import { DetalheLogisticaColetaInternacionalDTO } from "app/shared/dto/detalhe.logitica.coleta.internacional.dto";
import { TipoChecklistEnum } from "app/shared/enums/tipochecklist.enum";
import { Modal, ModalConfirmation } from 'app/shared/modal/factory/modal.factory';
import { ContatoTelefonicoCentroTransplante } from "app/shared/model/contato.telefonico.centro.transplante";
import { EnderecoContatoCentroTransplante } from "app/shared/model/endereco.contato.centro.transplante";
import { ChecklistService } from "app/shared/service/cheklist.service";
import { LogisticaService } from "app/shared/service/logistica.service";
import { TiposDoador } from "../../../../../shared/enums/tipos.doador";
import { ErroMensagem } from "../../../../../shared/erromensagem";
import { MessageBox } from '../../../../../shared/modal/message.box';
import { LogisticaMaterialService } from "../../../../../shared/service/logistica.material.service";
import { ErroUtil } from '../../../../../shared/util/erro.util';
import { StringUtil } from '../../../../../shared/util/string.util';
import { ChecklistComponent } from '../../checklist/checklist.component';
import { ModalLogisticaMaterialInternacionalComponent } from './modal.logistica.material.internacional.component';

/**
 * Componente interno de detalhe de logistica de material.
 * Dentro deste será referenciado o checklist
 */
@Component({
    selector: 'logistica-material-internacional-checklist',
    templateUrl: './logistica.material.internacional.checklist.component.html'
})

export class LogisticaMaterialInternacionalChecklistComponent implements OnInit{

    @ViewChild("checklist")
    public checkListComponent:ChecklistComponent;

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    private _detalhe: DetalheLogisticaColetaInternacionalDTO;
    private _idPedidoLogistica: number;
    private _contatos: ContatoTelefonicoCentroTransplante[];
    private _endereco: EnderecoContatoCentroTransplante;

    public hora: Array<string | RegExp>;

    constructor(
        private translate: TranslateService,
        private router: Router,
        private activatedRouter: ActivatedRoute,
        private logisticaMaterialService: LogisticaMaterialService,
        private checklistService: ChecklistService,
        private logisticaService: LogisticaService,
        private centroTransplanteService: CentroTransplanteService,
        private messageBox: MessageBox) {

        this.translate = translate;
    }

    public form(): FormGroup {
      return null;
    }

    public nomeComponente():string{
      return "LogisticaMaterialInternacionalChecklistComponent";
    }

    public preencherFormulario(entidade: DetalheLogisticaMaterialDTO): void {
      throw new Error("Method not implemented.");
    }

    ngOnInit(): void {

      this.activatedRouter.queryParamMap.subscribe(queryParam => {
        if (queryParam.keys.length != 0) {
          this._idPedidoLogistica = Number(queryParam.get('idPedidoLogistica'));
          let idDoador = Number(queryParam.get('idDoador'));

          this.logisticaMaterialService.obterLogisticaDoadorInternacionalColeta(this._idPedidoLogistica).then(res => {
              this._detalhe = res;

              this.obterCentroTransplante();
              this.configurarChecklist();

              Promise.resolve(this.headerDoador).then(() => {
                  this.headerDoador.popularCabecalhoIdentificacaoPorDoador(idDoador);
              });
          },
          (error:ErroMensagem)=> {
              this.exibirMensagemErroRetornandoParaListagem(error);
          });
        }
      });

    }

    obterCentroTransplante(){
      this.centroTransplanteService.obterCentroTransplante(this._detalhe.idCentroTransplante).then(res => {
        let centroTransplante = new CentroTransplante().jsonToEntity(res);
        this._detalhe.nomeCentroTransplante = centroTransplante.nome;
        this._endereco = centroTransplante.enderecos.filter(e => e.entrega)[0];
        this._contatos = centroTransplante.contatosTelefonicos;
      },
      (error:ErroMensagem) => {
          this.exibirMensagemErro(error);
      });
    }

    configurarChecklist(){
        this.checkListComponent.idTipoChecklist =
            this._detalhe.idTipoDoador == TiposDoador.INTERNACIONAL ?
                TipoChecklistEnum.MEDULA_INTERNACIONAL : TipoChecklistEnum.CORDAO_INTERNACIONAL;

        this.checkListComponent.onChangeItem = (resp: RespostaChecklist)=>{
            resp.pedidoLogistica = this._idPedidoLogistica;
            this.checklistService.salvarChecklist(resp).then(res=>{
                console.log(res);
            },
            (error:ErroMensagem)=> {
                this.exibirMensagemErro(error);
            });
        }
        let respostas: RespostaChecklist[] = [];
        this.checklistService.obterRespostasPorPedidoLogistica(this._idPedidoLogistica)
            .then(res =>{
                res.forEach(resp => {
                    respostas.push(new RespostaChecklist().jsonToEntity(resp));
                });
                this.checkListComponent.iniciar(respostas);
            },
            (error:ErroMensagem)=> {
                this.exibirMensagemErro(error);
            });
    }

    private exibirMensagemErro(error: ErroMensagem) {
        this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
    }

    private exibirMensagemErroRetornandoParaListagem(error: ErroMensagem) {
      let modal: Modal = this.messageBox.alert(ErroUtil.extrairMensagensErro(error));
      modal.closeOption = () => {
          this.voltarLogistica();
      }
      modal.show();
    }

    public isCordao(): boolean{
        return this._detalhe &&
            (this._detalhe.idTipoDoador == TiposDoador.CORDAO_NACIONAL ||
                this._detalhe.idTipoDoador == TiposDoador.CORDAO_INTERNACIONAL);
	}

	public isMedula(): boolean{
        return this._detalhe &&
            (this._detalhe.idTipoDoador == TiposDoador.NACIONAL ||
                this._detalhe.idTipoDoador == TiposDoador.INTERNACIONAL);
    }
    public voltarLogistica(){
        this.router.navigateByUrl("doadores/workup/consulta");
    }

    /**
     * Ao finalizar, verifica o preenchimento do checklist e das informações adicionais e,
     * caso algo menos uma delas esteja pendente, exibe um alerta informado e solicitação confirmação
     * para finalização.
     * Em ambos os casos, a alerta será exibido se ao menos um item, no checklist ou na informação adicional,
     * estiver não selecionado ou em branco, respectivamente.
     */
    public validarPreenchimentoParaFinalizar(): void{
        if(this.verificarChecklistPendentes() ||
                this.verificarInformacoesAdicionaisPendentes()){

            this.translate.get("logisticaMaterial.mensagens.checklistOuInfoAdicionaisPendentes").subscribe(mensagem => {
                let modal: ModalConfirmation = this.messageBox.confirmation(mensagem);
                modal.yesOption = () => {
                    this.finalizarLogisticaMaterial();
                }
                modal.show();
            });
        }
        else {
            this.finalizarLogisticaMaterial();
        }
    }

    private finalizarLogisticaMaterial(): void{
        this.logisticaService.finalizarPedidoLogisticaMaterialColetaInternacional(this._detalhe.idPedidoLogistica).then(msg => {
            let modal: Modal = this.messageBox.alert(msg);
            modal.closeOption = () => {
                this.voltarLogistica();
            }
            modal.show();
        },
        (error:ErroMensagem)=> {
            this.messageBox.alert(error.mensagem.valueOf()).show();
        });
    }

    public obterDocumento(relatorio: string, docxExtensaoRelatorio: boolean) {
        this.logisticaMaterialService.baixarDocumentos(this._idPedidoLogistica, relatorio, docxExtensaoRelatorio);
    }

    /**
     * Verifica se as informações adicionais estão pendentes de preenchimento
     * para o usuário saiba que encontrasse assim, antes de finalizar a logística.
     *
     * @return TRUE tenha alguma atributo pendente.
     */
    private verificarInformacoesAdicionaisPendentes(): boolean{
        let infoAdicionaisPendentes: boolean =
            StringUtil.isNullOrEmpty(this._detalhe.retiradaLocal) ||
            StringUtil.isNullOrEmpty(this._detalhe.retiradaIdDoador) ||
            StringUtil.isNullOrEmpty(this._detalhe.retiradaHawb);
        return infoAdicionaisPendentes;
    }

    /**
     * Verifica se existem itens de checklist pendentes de preenchimento
     * para o usuário saiba que está assim, antes de finalizar a logística.
     *
     * @return TRUE tenha alguma atributo pendente.
     */
    private verificarChecklistPendentes(): boolean{
        let perguntas: ItensChecklist[] = this.checkListComponent.perguntas;
        let respostas: RespostaChecklist[] = this.checkListComponent.respostas;
        return perguntas.length > respostas.length;
    }

    abriModalDadosAdicionais(){
        let modal: Modal = this.messageBox.dynamic(this._idPedidoLogistica,ModalLogisticaMaterialInternacionalComponent);
        modal.show();
    }

    /**
     * Realiza o downloado da autorização do paciente.
     */
    public downloadAutorizacaoPaciente(): void{
        this.translate.get("logisticaMaterial.nomeArquivoAutorizacaoPaciente").subscribe(nomeTraduzido => {
            let nomeSugeridoArquivo: string = this._detalhe.rmr + "_" + nomeTraduzido + ".pdf";
            this.logisticaService.downloadAutorizacaoPaciente(this._detalhe.idPedidoLogistica, nomeSugeridoArquivo);
        });
    }

    /**
     * Getter endereco
     * @return {EnderecoContatoCentroTransplante}
     */
    public get endereco(): EnderecoContatoCentroTransplante {
      return this._endereco;
    }

    /**
     * Setter endereco
     * @param {EnderecoContatoCentroTransplante} value
     */
    public set endereco(value: EnderecoContatoCentroTransplante) {
      this._endereco = value;
    }

    /**
     * Getter contatos
     * @return {ContatoTelefonicoCentroTransplante[]}
     */
    public get contatos(): ContatoTelefonicoCentroTransplante[] {
      return this._contatos;
    }

    /**
     * Setter contatos
     * @param {ContatoTelefonicoCentroTransplante[]} value
     */
    public set contatos(value: ContatoTelefonicoCentroTransplante[]) {
      this._contatos = value;
    }

    /**
     * Getter detalhe
     * @return {DetalheLogisticaColetaInternacionalDTO}
     */
    public get detalhe(): DetalheLogisticaColetaInternacionalDTO {
      return this._detalhe;
    }

    /**
     * Setter detalhe
     * @param {DetalheLogisticaColetaInternacionalDTO} value
     */
    public set detalhe(value: DetalheLogisticaColetaInternacionalDTO) {
      this._detalhe = value;
    }

}
