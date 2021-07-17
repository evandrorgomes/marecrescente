import { AutenticacaoService } from './../../../../shared/autenticacao/autenticacao.service';
import { PedidoExame } from './../../../../laboratorio/pedido.exame';
import { TiposAmostra } from './../../../../shared/enums/tipos.amostra';
import { Exame } from 'app/paciente/cadastro/exame/exame.';
import { Paciente } from './../../../paciente';
import { PedidoExameDoadorListaComponent } from './pedido.exame.doador.lista.component';
import { Paginacao } from '../../../../shared/paginacao';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PedidoExameService } from '../../../../laboratorio/pedido.exame.service';
import { BuscaService } from '../../busca.service';
import { ModalCancelarPedidoExameFase3Component } from './modal.cancelar.pedido.exame.fase3.component';
import { HeaderPacienteComponent } from '../../../consulta/identificacao/header.paciente.component';
import { Laboratorio } from '../../../../shared/dominio/laboratorio';
import { StatusPedidosExame } from '../../../../shared/enums/status.pedidos.exame';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { DateMoment } from '../../../../shared/util/date/date.moment';
import { StatusPedidoExame } from '../../../../laboratorio/status.pedido.exame';
import { MessageBox } from '../../../../shared/modal/message.box';
import { ModalPedidoExameFase3Component } from './modal.pedido.exame.fase3.component';
import { UltimoPedidoExameDTO } from './ultimo.pedido.exame.dto';
import { TipoExame } from '../../../../laboratorio/tipo.exame';
import { Solicitacao } from 'app/doador/solicitacao/solicitacao';
import {TiposExame} from "../../../../shared/enums/tipos.exame";


/**
 * Classe que representa o componente de pedido de exame de paciente.
 * @author Filipe Paes
 */
@Component({
    selector: 'pedido-exame-paciente',
    templateUrl: './pedido.exame.paciente.component.html'
})
export class PedidoExamePacienteComponent implements OnInit {

    @ViewChild(HeaderPacienteComponent)
    public headerPaciente: HeaderPacienteComponent;

    private _rmr: number;
    private _idBusca: number;
    private _ultimoPedidoExameDTO: UltimoPedidoExameDTO;
    private _tipoDoador:number = 1;
    public descricaoTipoAmostra: string;

    /**
     * Recebe as labels internacionalizadas da tela.
     */
    public labels: Object = {};

    constructor(private router: Router, private translate: TranslateService,
            private activatedRouter: ActivatedRoute, private buscaService: BuscaService,
            private pedidoExameService: PedidoExameService, private messageBox: MessageBox,
            private autenticacaoService:AutenticacaoService) {
    }

    ngOnInit(): void {

        // Carrega os dados das labels (internacionalização).
        this.translate.get("pedidoexamepaciente").subscribe(res => {
            this.labels = res;
        });

        this.activatedRouter.params.subscribe(params => {
            this._rmr = params['idPaciente'];

            Promise.resolve(this.headerPaciente).then(() => {
                this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this._rmr);
            });

        });

        this.activatedRouter.queryParams.subscribe(params => {
            if (params && params["buscaId"]) {
                this._idBusca = new Number(params["buscaId"]).valueOf();
                this.obterPedidoExame();
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
        return "PedidoExamePacienteComponent";
    }

    public get pedidoExame(): PedidoExame {
        return this._ultimoPedidoExameDTO  && this._ultimoPedidoExameDTO.pedidoExame ? this._ultimoPedidoExameDTO.pedidoExame : null;
    }

    public solicitarFase3(tipo: number): void {
        this.exibirModalSolicitarFase3(false, tipo);
    }

    private exibirModalSolicitarFase3(alterarlaboratorio: boolean, tipo: number): void {
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
        if(tipo == 0){
            data.idTipoExame = TiposExame.TESTE_CONFIRMATORIO;
        }else if(tipo == 1){
            data.idTipoExame = TiposExame.TESTE_CONFIRMATORIO_SWAB;
        }else if(tipo == 2){
            data.idTipoExame = TiposExame.TESTE_CONFIRMATORIO_SWAB;
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

    private obterPedidoExame() {
        this.buscaService.obterUltimoPedidoExame(this._idBusca).then(res => {
            if (res) {
                this._ultimoPedidoExameDTO = new UltimoPedidoExameDTO();
                this._ultimoPedidoExameDTO.rmr = res.rmr;
                this._ultimoPedidoExameDTO.municipioEnderecoPaciente = res.municipioEnderecoPaciente;
                this._ultimoPedidoExameDTO.ufEnderecoPaciente = res.ufEnderecoPaciente;
                if (res.idLaboratorioDePrefencia) {
                    this._ultimoPedidoExameDTO.laboratorioDePrefencia = new Laboratorio(new Number(res.idLaboratorioDePrefencia).valueOf());
                }

                if (res.pedidoExame) {
                    let pedidoExame: PedidoExame = new PedidoExame();
                    pedidoExame.id = new Number(res.pedidoExame.id).valueOf();
                    if (res.pedidoExame.dataColetaAmostra) {
                        pedidoExame.dataColetaAmostra = DateMoment.getInstance().parse(res.pedidoExame.dataColetaAmostra);
                    }
                    if (res.pedidoExame.dataRecebimentoAmostra) {
                        pedidoExame.dataRecebimentoAmostra = DateMoment.getInstance().parse(res.pedidoExame.dataRecebimentoAmostra);
                    }
                    let laboratorio: Laboratorio = new Laboratorio();
                    laboratorio.id = new Number(res.pedidoExame.laboratorio.id).valueOf();
                    laboratorio.nome = res.pedidoExame.laboratorio.nome;
                    pedidoExame.laboratorio = laboratorio;
                    pedidoExame.tipoExame = new TipoExame();
                    pedidoExame.tipoExame.descricao = res.pedidoExame.tipoExame.descricao;
                    pedidoExame.statusPedidoExame = new StatusPedidoExame(new Number(res.pedidoExame.statusPedidoExame.id).valueOf(),
                        res.pedidoExame.statusPedidoExame.descricao);
                    pedidoExame.solicitacao = new Solicitacao();
                    pedidoExame.solicitacao.id = res.pedidoExame.solicitacao.id;

                    this._ultimoPedidoExameDTO.pedidoExame = pedidoExame;
                    if (res.pedidoExame.justificativa) {
                        this._ultimoPedidoExameDTO.pedidoExame.justificativa = res.pedidoExame.justificativa;
                    }

                    if(res.pedidoExame.exame){
                        this.listTiposAmostraExame().filter(tipo => {
                            if(res.pedidoExame.exame.tipoAmostra === tipo.id){
                                return this.descricaoTipoAmostra = tipo.descricao;
                            }
                        });
                    }
                }
            }
        }, (erro: ErroMensagem)=> {
            this.exibirMensagemErro(erro);
        });
    }



    public deveExibirBotaoSolicitarExame(): boolean {
        let retorno = true;
        retorno = this.autenticacaoService.temRecurso('SOLICITAR_FASE_3_NACIONAL');
        if (this._ultimoPedidoExameDTO && this._ultimoPedidoExameDTO.pedidoExame) {
            retorno = (this._ultimoPedidoExameDTO.pedidoExame.statusPedidoExame.id == StatusPedidosExame.RESULTADO_CADASTRADO ||
                this._ultimoPedidoExameDTO.pedidoExame.statusPedidoExame.id == StatusPedidosExame.CANCELADO);
        }


        return retorno;
    }

    public deveExibirBotaoCancelarExame(): boolean {
        let retorno = false;
        if (this._ultimoPedidoExameDTO && this._ultimoPedidoExameDTO.pedidoExame) {
            retorno = this._ultimoPedidoExameDTO.pedidoExame.statusPedidoExame.id == StatusPedidosExame.AGUARDANDO_AMOSTRA;
        }
        return retorno;
    }

    public cancelarFase3() {
        let data = {
            idSolicitacao: this._ultimoPedidoExameDTO.pedidoExame.solicitacao.id
        }

        let x = this.messageBox
            .dynamic(data, ModalCancelarPedidoExameFase3Component);
        x.target = this;
        x.closeOption = this.fecharModal;
        x.show();


    }

    public alterarFase3() {
        this.exibirModalSolicitarFase3(true, this._ultimoPedidoExameDTO.pedidoExame.tipoExame.id);
    }

    public deveExibirBotaoAlterarExame(): boolean {
        let retorno = false;
        if (this._ultimoPedidoExameDTO && this._ultimoPedidoExameDTO.pedidoExame) {
            retorno = this._ultimoPedidoExameDTO.pedidoExame.statusPedidoExame.id == StatusPedidosExame.AGUARDANDO_AMOSTRA;
        }
        return retorno;
    }

    private exibirMensagemErro(error: ErroMensagem) {
        let mensagem: string = "";
        error.listaCampoMensagem.forEach(obj => {
            mensagem += obj.mensagem + "\n\r";
        })
        this.messageBox.alert(mensagem).show();
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

    /**
     * @description Lista todos os tipos de amostra
     * @author ergomes
     * @public
     */
    public listTiposAmostraExame(): any[] {
        const sangue = {
            id: TiposAmostra.SANGUE,
            descricao: this.labels['sangue'],
        }
        const swab = {
            id: TiposAmostra.SWAB,
            descricao: this.labels['swab'],
        }
        const sangueSwab = {
            id: TiposAmostra.SANGUE_SWAB,
            descricao: this.labels['sangueSwab'],
        }

        return [sangue, swab, sangueSwab];
    }
}
