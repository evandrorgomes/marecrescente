import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PedidoColetaService } from '../../consulta/coleta/pedido.coleta.service';
import { MessageBox } from '../../../shared/modal/message.box';
import { BaseForm } from '../../../shared/base.form';
import { ErroMensagem } from '../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { ErroUtil } from '../../../shared/util/erro.util';
import { DadosCentroTransplanteDTO } from '../../consulta/workup/dados.centro.transplante.dto';
import { WorkupService } from '../../consulta/workup/workup.service';
import { RouterUtil } from '../../../shared/util/router.util';

/**
 * Classe que representa o componente de inclusão de disponibilidades pelo analista workup.
 * @author Fillipe Queiroz
 */
@Component({
    selector: "visualizar-dadosct",
    templateUrl: './visualizar.dadosct.component.html',
    styleUrls: ['./../../doador.css']
})
export class VisualizarDadosCTComponent extends BaseForm<any> implements OnInit {
    
    private _dadosCentroTransplanteDTO: DadosCentroTransplanteDTO;

    constructor(private router: Router,
        translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private workupService:WorkupService,
        private pedidoColetaService: PedidoColetaService,
        private messageBox: MessageBox ) {
            super();
            this.translate = translate;
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     * 
     * @memberof PacienteBuscaComponent
     */
    ngOnInit(): void {

        this.translate.get("workup.pedido").subscribe(res => {
            this._formLabels = res;
        });

        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ["idPedido","idPedidoColeta"])
            .then(value => {                
                if (value['idPedido']) {
                    let idPedido: number = Number(value['idPedido']);    
                    this.workupService.listarDadosCT(idPedido).then(res => {
                        this._dadosCentroTransplanteDTO = res;
                        // this.abrirModalDadosDoCt();
                    },
                    (error: ErroMensagem) => {
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
                }
                if (value['idPedidoColeta']) {
                    let idPedido: number = Number(value['idPedidoColeta']);
                    this.pedidoColetaService.listarDadosCT(idPedido).then(res => {
                        this._dadosCentroTransplanteDTO = res;
                    },
                    (error: ErroMensagem) => {
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
                }

            }
        );

    }

    public voltarConsulta() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }
    /**
     * Nome do componente atual
     * 
     * @returns {string} 
     * @memberof ConferenciaComponent
     */
    nomeComponente(): string {
        return "VisualizarDadosCTComponent";
    }

	/**
	 * @returns DadosCentroTransplanteDTO
	 */
	public get dadosCentroTransplanteDTO(): DadosCentroTransplanteDTO {
		return this._dadosCentroTransplanteDTO;
	}
	/**
	 * @param  {DadosCentroTransplanteDTO} value
	 */
	public set dadosCentroTransplanteDTO(value: DadosCentroTransplanteDTO) {
		this._dadosCentroTransplanteDTO = value;
	}
  
    public getTipoTelefone(tipoTelefone:number){
        
        return tipoTelefone==1?this._formLabels["fixo"] :this._formLabels["celular"];
    }

    public form(): FormGroup {
        throw new Error("Method not implemented.");
    }
    public preencherFormulario(entidade: any): void {
        throw new Error("Method not implemented.");
    }

};