import { Component, OnInit, ViewChild } from "@angular/core";
import { FormGroup } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { HeaderDoadorComponent } from "app/doador/consulta/header/header.doador.component";
import { DoadorNacional } from "app/doador/doador.nacional";
import { TiposDoador } from "app/shared/enums/tipos.doador";
import { HistoricoNavegacao } from "app/shared/historico.navegacao";
import { Paginacao } from "app/shared/paginacao";
import { PedidoExameDoadorNacionalVo } from "app/shared/vo/pedido.exame.doador.nacional.vo";
import { dominioService } from "../../../export.mock.spec";
import { PedidoExameService } from '../../../laboratorio/pedido.exame.service';
import { DominioService } from "../../../shared/dominio/dominio.service";
import { ErroMensagem } from "../../../shared/erromensagem";
import { MessageBox } from "../../../shared/modal/message.box";
import { RouterUtil } from "../../../shared/util/router.util";

/**
 * Classe que representa o componente de pedido de exames do doador nacional.
 * @author Ergomes
 */
@Component({ 
    selector: "visualizar-doador-nacional",
    moduleId: module.id,
    templateUrl: "./visualizar.doador.nacional.component.html",
    providers: [{ provide: DominioService, useValue: dominioService }]
})
export class VisualizarDoadorNacionalComponent implements OnInit {

    public doador: DoadorNacional;
    private _idDoador: number;
    public qtdRegistroDoadores: number = 10;
    public paginacaoDoadoresNacionais: Paginacao;

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    /**
     * Construtor padrão
     * @param serviceDominio serviço injetado para buscar os dominios 
     * @author ergomes
     */
    constructor(
        private pedidoExameService: PedidoExameService, private activatedRouter: ActivatedRoute,
        private router: Router, private messageBox: MessageBox) {

            this.paginacaoDoadoresNacionais = new Paginacao('', 0, this.qtdRegistroDoadores);
            this.paginacaoDoadoresNacionais.number = 1;

        this.buildForm();
    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações. 
     * @author Ergomes
     */
    buildForm(): void {}

    ngOnInit(): void {

        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'idDoador').then(res => {
            this._idDoador = <number>res;
            Promise.resolve(this.headerDoador).then(() => {
                this.headerDoador.clearCache();
                this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this._idDoador);
            });
            this.listarPedidoExameNacionais(1);

        }).catch((error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

   /**
     * @description Lista as tarefas de pedido de exame para doadores nacionais.
     * 
     * @author ergomes
     * @private
     * @param {*} pagina
     */
    public listarPedidoExameNacionais(pagina: any): void{

        this.pedidoExameService.listarAndamentoDePedidosExamesPorDoador(
            this._idDoador, pagina - 1, this.qtdRegistroDoadores)
        .then(res => {
            this.paginacaoDoadoresNacionais.content = res.content;
            this.paginacaoDoadoresNacionais.totalElements = res.totalElements;
            this.paginacaoDoadoresNacionais.quantidadeRegistro = this.qtdRegistroDoadores;
            this.paginacaoDoadoresNacionais.number = pagina;
        },
        (error: ErroMensagem) => {
            this.exibirMensagemErro(error);
        });
    }

    /**
     * Método para direcionar a seleção para tela de visualizacao.
     * 
     * @param doador - doador selecionado na lista.
     */
    public irParaBusca(doador: PedidoExameDoadorNacionalVo) {
        this.router.navigateByUrl(`/pacientes/${doador.rmr}/matchs?identificacaoDoador=${doador.dmr}`);
    }

    nomeComponente(): string {
        return "VisualizarDoadorNacionalComponent";
    }

    private exibirMensagemErro(error: ErroMensagem) {
        let msg: string = "";
        if (error.mensagem) {
            msg = error.mensagem.toString();

        } else {
            error.listaCampoMensagem.forEach(obj => {
                msg += obj.mensagem + " \r\n";

            })
        }
        this.messageBox.alert(msg).show();
    }

    public form(): FormGroup {
        throw new Error("Method not implemented.");
    }

    mudarPaginaNacionais(event: any) {
        this.listarPedidoExameNacionais(event.page)
    }

    selecionaQuantidadeRegistrosNacionais(event: any) {
        this.listarPedidoExameNacionais(1);
    }

    ehMedula(tipoDoador: any): boolean {
        return tipoDoador == TiposDoador.NACIONAL || tipoDoador == TiposDoador.INTERNACIONAL;
    }

    ehCordao(tipoDoador: any): boolean {
        return tipoDoador == TiposDoador.CORDAO_NACIONAL || tipoDoador == TiposDoador.CORDAO_INTERNACIONAL;
    }

    public voltarConsulta() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }
}