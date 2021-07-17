import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Solicitacao } from 'app/doador/solicitacao/solicitacao';
import { PedidoExame } from '../../../../laboratorio/pedido.exame';
import { PedidoExameService } from '../../../../laboratorio/pedido.exame.service';
import { StatusPedidoExame } from '../../../../laboratorio/status.pedido.exame';
import { TipoExame } from '../../../../laboratorio/tipo.exame';
import { Laboratorio } from '../../../../shared/dominio/laboratorio';
import { StatusPedidosExame } from '../../../../shared/enums/status.pedidos.exame';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { MessageBox } from '../../../../shared/modal/message.box';
import { DateMoment } from '../../../../shared/util/date/date.moment';
import { HeaderPacienteComponent } from '../../../consulta/identificacao/header.paciente.component';
import { BuscaService } from '../../busca.service';
import { ModalCancelarPedidoExameFase3Component } from './modal.cancelar.pedido.exame.fase3.component';
import { ModalPedidoExameFase3Component } from './modal.pedido.exame.fase3.component';
import { PedidoExameDoadorListaComponent } from './pedido.exame.doador.lista.component';
import { UltimoPedidoExameDTO } from './ultimo.pedido.exame.dto';


/**
 * Classe que representa o componente de analise de match
 * @author Bruno Sousa
 */
@Component({
    selector: 'pedido-exame-fase3-paciente',
    templateUrl: './pedido.exame.fase3.component.html'
})
export class PedidoExameFase3Component implements OnInit {

    @ViewChild(HeaderPacienteComponent)
    public headerPaciente: HeaderPacienteComponent;

    private _rmr: number;
    private _idBusca: number;
    private _ultimoPedidoExameDTO: UltimoPedidoExameDTO;

    @ViewChild(PedidoExameDoadorListaComponent)
    public pedidoExameLista:PedidoExameDoadorListaComponent;

    private _tipoDoador:number = 1;

    constructor(private router: Router, private translate: TranslateService,
            private activatedRouter: ActivatedRoute, private buscaService: BuscaService,
            private pedidoExameService: PedidoExameService, private messageBox: MessageBox) {
    }

    ngOnInit(): void {
        this.activatedRouter.params.subscribe(params => {
            this._rmr = params['idPaciente'];

            Promise.resolve(this.headerPaciente).then(() => {
                this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this._rmr);
            });

        });

        this.activatedRouter.queryParams.subscribe(params => {
            if (params && params["buscaId"]) {
                this._idBusca = new Number(params["buscaId"]).valueOf();

                this.pedidoExameLista.idBusca = this._idBusca;
                this.pedidoExameLista.listarSolicitacoesDePedidosDeExameInternacional(1);
                this.pedidoExameLista.listarSolicitacoesDePedidosDeExameNacional(1);
            }
            else {
                this.translate.get("mensagem.erro.requisicao").subscribe(res => {
                    this.messageBox.alert(res)
                    .withTarget(this)
                    .withCloseOption(this.fecharModalErro).show();
                });
            }
        });

    }

    nomeComponente(): string {
        return "PedidoExameFase3Component";
    }

    public get pedidoExame(): PedidoExame {
        return this._ultimoPedidoExameDTO  && this._ultimoPedidoExameDTO.pedidoExame ? this._ultimoPedidoExameDTO.pedidoExame : null;
    }

    public solicitarFase3(): void {
        this.exibirModalSolicitarFase3(false);
    }

    private exibirModalSolicitarFase3(alterarlaboratorio: boolean): void {
        let data;
        if (!alterarlaboratorio) {
            data = {
                rmr: this._rmr,
                idBusca: this._idBusca,
                municipioEnderecoPaciente: this._ultimoPedidoExameDTO.municipioEnderecoPaciente,
                ufEnderecoPaciente: this._ultimoPedidoExameDTO.ufEnderecoPaciente,
                laboratorioDePrefencia: this._ultimoPedidoExameDTO.laboratorioDePrefencia,
                alteracaoLaboratorio: false
            }
        }
        else {
            data = {
                rmr: this._rmr,
                idBusca: this._idBusca,
                idPedido: this._ultimoPedidoExameDTO.pedidoExame.id,
                municipioEnderecoPaciente: this._ultimoPedidoExameDTO.municipioEnderecoPaciente,
                ufEnderecoPaciente: this._ultimoPedidoExameDTO.ufEnderecoPaciente,
                laboratorioDePrefencia: this._ultimoPedidoExameDTO.laboratorioDePrefencia,
                alteracaoLaboratorio: true,
                laboratorioAtual: this._ultimoPedidoExameDTO.pedidoExame.laboratorio
            }
        }

        let x = this.messageBox
            .dynamic(data, ModalPedidoExameFase3Component);
        x.target = this;
        x.closeOption = this.fecharModal;
        x.show();
    }

    private fecharModal(target: any): void {
        target.obterPedidoExame();
    }

    private fecharModalErro(target: any): void {
       target.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    /**
     * Getter tipoDoador
     * @return {number }
     */
	public get tipoDoador(): number  {
		return this._tipoDoador;
	}

    /**
     * Setter tipoDoador
     * @param {number } value
     */
	public set tipoDoador(value: number ) {
		this._tipoDoador = value;
    }


    /**
     * Getter idBusca
     * @return {number}
     */
	public get idBusca(): number {
		return this._idBusca;
	}

    /**
     * Setter idBusca
     * @param {number} value
     */
	public set idBusca(value: number) {
		this._idBusca = value;
    }

}
