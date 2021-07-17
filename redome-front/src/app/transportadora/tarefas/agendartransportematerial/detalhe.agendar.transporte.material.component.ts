import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {FormGroup} from '@angular/forms/src/model';
import {ActivatedRoute, Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {BaseForm} from '../../../shared/base.form';
import {StatusPedidoTransportes} from '../../../shared/enums/status.pedido.transporte';
import {ErroMensagem} from '../../../shared/erromensagem';
import {HistoricoNavegacao} from '../../../shared/historico.navegacao';
import {MensagemModalComponente} from '../../../shared/modal/mensagem.modal.component';
import {MessageBox} from '../../../shared/modal/message.box';
import {TarefaService} from '../../../shared/tarefa.service';
import {ErroUtil} from '../../../shared/util/erro.util';
import {RouterUtil} from '../../../shared/util/router.util';
import {Courier} from '../../courier';
import {CourierService} from '../../courier.service';
import {PedidoTransporteService} from '../../pedido.transporte.service';
import {PedidoTransporte} from '../pedido.transporte';
import {ConfirmacaoTransporteDTO} from './confirmacao.transporte.dto';


@Component({
    selector: 'detalhe-agendar-transporte-material',
    templateUrl: './detalhe.agendar.transporte.material.component.html'
})
export class DetalheAgendarTransporteMaterialComponent extends BaseForm<PedidoTransporte> implements OnInit {


    public form(): FormGroup {
        return this.agendarTransporteForm;
    }

    public preencherFormulario(entidade: PedidoTransporte): void {
        throw new Error("Method not implemented.");
    }

    private _idPedidoTransporte: number;
    public detalhePedidoTransporteDTO: any;
    private _idTarefa: number;

    public agendarTransporteForm: FormGroup;
    public exibirCamposVoo: boolean = false;
    public listaCourier: Courier[];

    constructor(private router: Router, private tarefaService: TarefaService,
        private courierService: CourierService, public translate: TranslateService,
        private activatedRoute: ActivatedRoute, private pedidoTransporteService: PedidoTransporteService,
        private fb: FormBuilder, private messageBox: MessageBox) {
        super();
        this.translate = translate;

    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações
     * @author Fillipe Queiroz
     */
    buildForm() {

        this.agendarTransporteForm = this.fb.group({
            'dadosCourier': [null, Validators.required],
            'dadosVoo': [null, Validators.required],
            'temVoo': [null, Validators.required]
        });

        return this.agendarTransporteForm;

    }

    ngOnInit() {
        this.buildForm();
        this.translate.get("transporteMaterial").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.agendarTransporteForm);
            this.setMensagensErro(this.agendarTransporteForm);
        });

        this.courierService.listar().then(res => {
            if (res) {
                this.listaCourier = [];
                res.forEach(courierJson => {
                    this.listaCourier.push(new Courier().jsonToEntity(courierJson));
                })
            }

        }).catch((error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });


        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRoute, ['tarefaId', 'idPedidoTransporte']).then(res => {
            this._idTarefa = res['tarefaId'];
            this._idPedidoTransporte = res['idPedidoTransporte'];

            this.pedidoTransporteService.obterPedidoTransporte(this._idPedidoTransporte).then(res => {
                this.detalhePedidoTransporteDTO = res;
            })
        });


    }

    public marcarNecessidadeVoo(event: any): void {
        let temVoo: boolean = event.currentTarget.checked;
        if (temVoo) {
            this.exibirCamposVoo = true;
        } else {
            this.exibirCamposVoo = false;
        }
    }


    nomeComponente(): string {
        return "DetalheAgendarTransporteMaterialComponent";
    }

    public voltarConsulta() {
        this.tarefaService.removerAtribuicaoTarefa(this._idTarefa).then(res => {
            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
        }).catch((error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public confirmarAgendamento() {
        if (this.exibirCamposVoo) {
            this.confirmarAgendamentoComVoo();
        } else {
            this.confirmarAgendamentoSemVoo();
        }

    }

    private confirmarAgendamentoComVoo() {
        let confirmacaoTransporte: ConfirmacaoTransporteDTO = new ConfirmacaoTransporteDTO();
        confirmacaoTransporte.voo = true;
        if (this.validateForm()) {
            confirmacaoTransporte.idCourier = this.agendarTransporteForm.get('dadosCourier').value;
            confirmacaoTransporte.dadosVoo = this.agendarTransporteForm.get('dadosVoo').value;
            this.enviarConfirmacaoAgendamento(this._idPedidoTransporte, confirmacaoTransporte);
        }
    }

    private confirmarAgendamentoSemVoo() {
        let confirmacaoTransporte: ConfirmacaoTransporteDTO = new ConfirmacaoTransporteDTO();
        confirmacaoTransporte.voo = false;
        this.enviarConfirmacaoAgendamento(this._idPedidoTransporte, confirmacaoTransporte);
    }

    private enviarConfirmacaoAgendamento(idPedidoTransporte: number,  confirmacaoTransporte: ConfirmacaoTransporteDTO) {
        this.pedidoTransporteService.confirmarAgendamentoTransporteMaterial(idPedidoTransporte, confirmacaoTransporte).then(res => {
            this.messageBox.alert(res).withCloseOption(() => {
                this.router.navigateByUrl('/transportadoras');
            }).show();

        }).catch((error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }



    /**
     * Imprime a carta MO para o courier.
     */
    public imprimirCartaMO() {
        this.pedidoTransporteService.imprimirCartaMO(this._idPedidoTransporte, "");
    }

    /**
     * Imprime a carta MO para o courier.
     */
    public downloadVoucherCNT() {
        this.pedidoTransporteService.downloadVoucherAutorizacaoCNT(this._idPedidoTransporte, "voucherCNT.zip");
    }

    /**
     * Imprime o relatorio de transporte.
     */
    public imprimirRelatorioTransporte() {
        this.pedidoTransporteService.imprimirTransporteRelatorio(this._idPedidoTransporte, "");
    }

    public exibirCamposDownload(): boolean {
        if (this.detalhePedidoTransporteDTO) {

            return this.detalhePedidoTransporteDTO.status.id == StatusPedidoTransportes.AGUARDANDO_DOCUMENTACAO.id
                || this.detalhePedidoTransporteDTO.status.id == StatusPedidoTransportes.AGUARDANDO_CONFIRMACAO.id
                || this.detalhePedidoTransporteDTO.status.id == StatusPedidoTransportes.AGUARDANDO_ENTREGA.id
                || this.detalhePedidoTransporteDTO.status.id == StatusPedidoTransportes.AGUARDANDO_RETIRADA.id;
        }
        return false;
    }
}
