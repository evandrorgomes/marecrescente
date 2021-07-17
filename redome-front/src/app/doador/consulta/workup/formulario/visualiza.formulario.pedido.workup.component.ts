import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ContatoTelefonicoDoador } from 'app/doador/contato.telefonico.doador';
import { Doador } from 'app/doador/doador';
import { DoadorNacional } from 'app/doador/doador.nacional';
import { DoadorService } from 'app/doador/doador.service';
import { ContatoDataEventService } from 'app/doador/solicitacao/contato.data.event.service';
import { PrescricaoService } from 'app/doador/solicitacao/prescricao.service';
import { Formulario } from 'app/shared/classes/formulario';
import { VisualizarPrescricaoDataEventService } from 'app/shared/component/visualizar/prescricao/visualizar.prescricao.data.event.service';
import { PrescricaoDTO } from 'app/shared/dto/prescricao.dto';
import { PrescricaoEvolucaoDTO } from 'app/shared/dto/prescricao.evolucao.dto';
import { TiposFormulario } from 'app/shared/enums/tipo.formulario';
import { ErroMensagem } from 'app/shared/erromensagem';
import { HistoricoNavegacao } from 'app/shared/historico.navegacao';
import { MessageBox } from 'app/shared/modal/message.box';
import { PermissaoRotaComponente } from 'app/shared/permissao.rota.componente';
import { QuestionarioComponent } from 'app/shared/questionario/questionario.component';
import { FormularioService } from 'app/shared/service/formulario.service';
import { ErroUtil } from 'app/shared/util/erro.util';
import { HeaderDoadorComponent } from '../../header/header.doador.component';
import { PedidoWorkup } from '../pedido.workup';
import { WorkupService } from '../workup.service';


/**
 * Classe que representa o componente para catrastrar o formulário de workup.
 */
@Component({
	selector: 'visualiza-formulario-pedido-workup',
	templateUrl: './visualiza.formulario.pedido.workup.component.html'
})


export class VisualizaFormularioPedidoWorkupComponent implements OnInit, PermissaoRotaComponente {

	@ViewChild('headerDoador')
	protected headerDoador: HeaderDoadorComponent;

	@ViewChild('questionario')
	protected questionarioComponent: QuestionarioComponent

	public mostraFormulario: string = 'hide';
	public mostraDados: boolean = true;
	public titulo: string;
	private _mostrarDetalhe: boolean = false;

	private _doadorNacional: DoadorNacional;
	private _pedidoWorkup: PedidoWorkup;
	public _formulario: Formulario;
  private _rmr: number;

	private _idTipoFormulario: number;
  private _prescricaoEvolucao: PrescricaoEvolucaoDTO;
  _titulo: any;

	constructor(protected router: Router,
		translate: TranslateService,
    protected activatedRouter: ActivatedRoute,
		protected doadorService: DoadorService,
    private workupService: WorkupService,
		protected messageBox: MessageBox,
    protected contatoDataEventService: ContatoDataEventService,
		protected prescricaoService: PrescricaoService,
    private visualizarPrescricaoDataEventService: VisualizarPrescricaoDataEventService,
    protected formularioService: FormularioService) {

	}
	nomeComponente(): string {
		return 'VisualizaFormularioPedidoWorkupComponent';
	}

	ngOnInit() {

        this.activatedRouter.queryParamMap.subscribe(queryParam => {
            if (queryParam.keys.length != 0) {
                let idPedidoWorkup = Number(queryParam.get('idPedidoWorkup'));
                this._idTipoFormulario = Number(queryParam.get('idTipoFormulario'));
                let idPrescricao = Number(queryParam.get('idPrescricao'));

        this.popularPrescricao(idPrescricao);

				this.workupService.obterPedidoWorkup(idPedidoWorkup)
				.then(res => {
					this._pedidoWorkup = new PedidoWorkup().jsonToEntity(res);
					this.carregarDadosFormulario(this._pedidoWorkup.id);

					let idDoador = this._pedidoWorkup.solicitacao.doador.id;
					this.doadorService.obterDetalhesDoador(idDoador)
					.then(res => {
						this._doadorNacional  = new DoadorNacional().jsonToEntity(res);
					});

					Promise.resolve(this.headerDoador).then(() => {
						this.headerDoador.clearCache();
						this.headerDoador.popularCabecalhoIdentificacaoPorDoador(idDoador);
					});
				},
				(error: ErroMensagem) => {
				ErroUtil.exibirMensagemErro(error, this.messageBox);
				});
			}
		}

    );
	}

	private carregarDadosFormulario(idPedidoWorkup: number){

		this.formularioService.obterFormulario(idPedidoWorkup, this._idTipoFormulario).then(res => {
			this._formulario = new Formulario().jsonToEntity(res);

		})
		.catch((erro: ErroMensagem) => {
			if (erro.listaCampoMensagem && erro.listaCampoMensagem.length > 0) {
				if (erro.listaCampoMensagem[0].campo == 'erro' && erro.listaCampoMensagem[0].mensagem == "Nenhum questionário encontrado.") {
					this._formulario = null;
				}
			}
		});
	}

	salvarFormulario() {
		this.formularioService.salvarFormularioComPedidoWorkupEValidacao(this._pedidoWorkup.id, this._formulario, false).then(res => {
			if (res) {
				this.messageBox.alert(res.mensagem).show();
			}
		},
		error => {
			ErroUtil.exibirMensagemErro(error, this.messageBox);
		});
	}

	finalizarFormulario(){
		this.formularioService.finalizarFormularioPedidoWorkup(this._pedidoWorkup.id, this._formulario).then(res => {
			this._formulario = new Formulario().jsonToEntity(res);
			if(!this._formulario.comErro){
				if(this._formulario.tipoFormulario.id == TiposFormulario.RECEPTIVIDADE_WORKUP){
					this.router.navigateByUrl('/doadores/workup/consulta');
				}
			}
		},
		error => {
			ErroUtil.exibirMensagemErro(error, this.messageBox);
		});
	}

	public obterTitulo(): string {
		return 'tituloFormularioWorkup';
	}

	getEstiloBlocoTelefone(contatoTelefonico: ContatoTelefonicoDoador) {
		let classeCss: string = "textarea-bloco ";
		let classeCssTelefoneExcluido: string = "dados-bloco-nao-atendido";
		let classeCssTelefone: string = "";

		return contatoTelefonico.naoAtendido ? classeCss + classeCssTelefoneExcluido : classeCss + classeCssTelefone;
	}

	public voltar() {
		if(this._formulario.tipoFormulario.id == TiposFormulario.RECEPTIVIDADE_WORKUP){
			this.router.navigateByUrl('/doadores/workup/consulta');
		}
		this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
	}

  popularPrescricao(idPrescricao : number){

    this.visualizarPrescricaoDataEventService.dataEvent.carregarPrescricao(idPrescricao);

    this.prescricaoService.obterPrescricaoComEvolucaoPorIdPrecricao(idPrescricao)
       .then(res => {

          this._prescricaoEvolucao = new PrescricaoEvolucaoDTO().jsonToEntity(res);
          let prescricao: PrescricaoDTO = this._prescricaoEvolucao.prescricao;

          this._rmr = prescricao.rmr;

       });
  }

	/**
	* Getter pedidoWorkup
	* @return {pedidoWorkup}
	*/
	public get pedidoWorkup(): PedidoWorkup {
		return this._pedidoWorkup;
	}

    /**
     * Getter doadorNacional
     * @return {Doador}
     */
	public get doadorNacional(): DoadorNacional {
		return this._doadorNacional;
	}

    /**
     * Getter mostrarDetalhe
     * @return {boolean }
     */
	public get isMostrarDetalhe(): boolean  {
		return this._mostrarDetalhe;
	}




};
