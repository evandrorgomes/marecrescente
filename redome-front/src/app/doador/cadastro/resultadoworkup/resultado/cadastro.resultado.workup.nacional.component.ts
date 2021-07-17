import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { HeaderDoadorComponent } from 'app/doador/consulta/header/header.doador.component';
import { PedidoWorkup } from 'app/doador/consulta/workup/pedido.workup';
import { DoadorService } from 'app/doador/doador.service';
import { ContatoDataEventService } from 'app/doador/solicitacao/contato.data.event.service';
import { BaseForm } from 'app/shared/base.form';
import { BuildForm } from 'app/shared/buildform/build.form';
import { StringControl } from 'app/shared/buildform/controls/string.control';
import { Formulario } from 'app/shared/classes/formulario';
import { ResultadoWorkupNacionalDTO } from 'app/shared/dto/resultado.workup.nacional.dto';
import { TiposFormulario } from 'app/shared/enums/tipo.formulario';
import { ErroMensagem } from 'app/shared/erromensagem';
import { HistoricoNavegacao } from 'app/shared/historico.navegacao';
import { MessageBox } from 'app/shared/modal/message.box';
import { PermissaoRotaComponente } from 'app/shared/permissao.rota.componente';
import { QuestionarioComponent } from 'app/shared/questionario/questionario.component';
import { FormularioService } from 'app/shared/service/formulario.service';
import { PedidoWorkupService } from 'app/shared/service/pedido.workup.service';
import { PrescricaoService } from 'app/doador/solicitacao/prescricao.service';
import { VisualizarPrescricaoDataEventService } from 'app/shared/component/visualizar/prescricao/visualizar.prescricao.data.event.service';
import { PrescricaoDTO } from 'app/shared/dto/prescricao.dto';
import { PrescricaoEvolucaoDTO } from 'app/shared/dto/prescricao.evolucao.dto';
import { ErroUtil } from 'app/shared/util/erro.util';
import {FontesCelulas} from "../../../../shared/enums/fontes.celulas";
import {NumberControl} from "../../../../shared/buildform/controls/number.control";
import {DataUtil} from "../../../../shared/util/data.util";
import {MascaraUtil} from "../../../../shared/util/mascara.util";
import {DateControl} from "../../../../shared/buildform/controls/date.control";
import {ValidateData} from "../../../../validators/data.validator";

/**
 * Classe que representa o componente para catrastrar o formulário de workup.
 */
@Component({
	selector: 'resultado-workup-nacional',
	templateUrl: './cadastro.resultado.workup.nacional.component.html'
})

export class CadastroResultadoWorkupNacionalComponent extends BaseForm<Object> implements OnInit, OnDestroy, PermissaoRotaComponente {

	@ViewChild('headerDoador')
	protected headerDoador: HeaderDoadorComponent;

	@ViewChild('questionario')
	protected questionarioComponent: QuestionarioComponent

  private _buildFormAvaliar: BuildForm<any>;
  public _coletaInviavel: boolean;
  public _coletaViavel: boolean;

  public labelsColetaInviavel:String[];
  public opcoesColetaInviavel:String[] = ["S","N"];

	public mostraFormulario: string = 'hide';
	public mostraDados: boolean = true;
	public titulo: string;
	private _mostrarDetalhe: boolean = false;

	private _pedidoWorkup: PedidoWorkup;
	public _formulario: Formulario;
  private _rmr: number;
  private _prescricaoEvolucao: PrescricaoEvolucaoDTO;

	private _idFonteOpcao1: number;
	private _idFonteOpcao2: number;

	private textoMedulaOssea: string;
	private textoSanguePeriferico: string;

	public _fontesCelulas: any[];

	public _sanguePerifericoSelecionado: boolean = false;
	public _medulaOsseaSelecionada: boolean = false;

	public _mascaraData: Array<string | RegExp> = MascaraUtil.data;

	public _coletaafereseinviavel: boolean;
	public _sangueAutologoNaoColetado: boolean;
	public _somenteUmaFonte: boolean = false;

	private _idUnicaFonteCelula: number;

	constructor(protected router: Router, translate: TranslateService,
		protected activatedRouter: ActivatedRoute,
		protected doadorService: DoadorService, private pedidoWorkupService: PedidoWorkupService,
		protected messageBox: MessageBox, protected contatoDataEventService: ContatoDataEventService,
    protected prescricaoService: PrescricaoService,
    private visualizarPrescricaoDataEventService: VisualizarPrescricaoDataEventService,
		protected formularioService: FormularioService) {
    super();

    this.translate = translate;

    this.buildFormAvaliar();

  }
	nomeComponente(): string {
		return 'CadastroResultadoWorkupNacionalComponent';
	}

	ngOnInit() {

        this.activatedRouter.queryParamMap.subscribe(queryParam => {
            if (queryParam.keys.length != 0) {
                let idPedidoWorkup = Number(queryParam.get('idPedidoWorkup'));
                let idDoador = Number(queryParam.get('idDoador'));
                let idPrescricao = Number(queryParam.get('idPrescricao'));

        this.popularPrescricao(idPrescricao);

				this.pedidoWorkupService.obterPedidoWorkup(idPedidoWorkup)
				.then(res => {
					this._pedidoWorkup = new PedidoWorkup().jsonToEntity(res);
					this._idFonteOpcao1 = res.idFonteOpcao1;
					if (res.idFonteOpcao2) {
						this._idFonteOpcao2 = res.idFonteOpcao2;
					}
					else {
						this._somenteUmaFonte = true;
					}
					this.fontesCelulas();

					this.carregarDadosFormulario(this._pedidoWorkup.id);
				},
				(error: ErroMensagem) => {
				ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

			this.translate.get(['textosGenericos.medula','textosGenericos.sangueperiferico', 'textosGenericos.nao', 'textosGenericos.sim']).subscribe(res => {
				this.textoMedulaOssea = res['textosGenericos.medula'];
				this.textoSanguePeriferico = res['textosGenericos.sangueperiferico'];
				this.labelsColetaInviavel = [res['textosGenericos.sim'], res['textosGenericos.nao']];
			});

        Promise.resolve(this.headerDoador).then(() => {
          this.headerDoador.clearCache();
          this.headerDoador.popularCabecalhoIdentificacaoPorDoador(idDoador);
        });

			}
		});
	}

	ngOnDestroy() {
		this._buildFormAvaliar = null;
		super.ngOnDestroy();
	}

	buildFormAvaliar(): void {
    this._buildFormAvaliar  = new BuildForm<any>()
     .add(new StringControl("coletaInviavel", [Validators.required]))
     .add(new StringControl("motivoInviabilidade"))
		 .add(new NumberControl("fonteCelula"))
		 .add(new DateControl('datagcfh'))
		 .add(new StringControl('coletaaferese'))
		 .add(new StringControl('acessovenoso'))
		 .add(new StringControl('sangueautologo'))
		 .add(new StringControl('motivosangueautologonaocoletado'));


      this._buildFormAvaliar.getControlAsStringControl('coletaInviavel').onValueChange = (value: string): void => {
          let valorAnteriorColetaInviavel = this._coletaInviavel;
          this._coletaInviavel = value === 'N';
          this._coletaViavel = value === 'S';

          if (valorAnteriorColetaInviavel != this._coletaInviavel && !this._somenteUmaFonte) {
              this._buildFormAvaliar.getControlAsNumberControl('fonteCelula').value = null;
              this._buildFormAvaliar.getControlAsStringControl('motivoInviabilidade').value = null;
          }
          if (this._coletaInviavel) {
              this._buildFormAvaliar.getControlAsNumberControl('fonteCelula').makeOptional();
              this._buildFormAvaliar.getControlAsNumberControl('fonteCelula').markAsValid();
              this._buildFormAvaliar.getControlAsStringControl('motivoInviabilidade').makeRequired();
          }
          else {
            if (this._somenteUmaFonte) {
                this._buildFormAvaliar.getControlAsNumberControl('fonteCelula').value = this._idFonteOpcao1;
            }
            this._buildFormAvaliar.getControlAsNumberControl('fonteCelula').makeRequired();
            this._buildFormAvaliar.getControlAsStringControl('motivoInviabilidade').makeOptional();
            this._buildFormAvaliar.getControlAsStringControl('motivoInviabilidade').markAsValid()
          }
      }

    this._buildFormAvaliar.getControlAsNumberControl('fonteCelula').onValueChange = (value: number): void => {

        let valorAnterior = this._medulaOsseaSelecionada ? FontesCelulas.MEDULA_OSSEA.id :
        this._sanguePerifericoSelecionado ? FontesCelulas.SANGUE_PERIFERICO.id : null;
        if (valorAnterior != value) {
            this.fonteCelulaSelecionada(value);
            this.limparCampos();
            this.removerObrigatoriedade();
            if (this._sanguePerifericoSelecionado) {
                this._buildFormAvaliar.getControlAsDateControl('datagcfh').makeRequired();
                this._buildFormAvaliar.getControlAsDateControl('datagcfh').addValidator(ValidateData);
                this._buildFormAvaliar.getControlAsStringControl('coletaaferese').makeRequired();
            }
            if (this._medulaOsseaSelecionada) {
                this._buildFormAvaliar.getControlAsStringControl('sangueautologo').makeRequired();
            }
        }
	  }

	  this._buildFormAvaliar.getControlAsStringControl('coletaaferese').onValueChange = (value: string): void => {
        this._coletaafereseinviavel = value === 'N';
        if (this._coletaafereseinviavel) {
            this._buildFormAvaliar.getControlAsStringControl('acessovenoso').makeRequired();
        }
        else {
            this._buildFormAvaliar.getControlAsStringControl('acessovenoso').makeOptional();
        }
    }

	  this._buildFormAvaliar.getControlAsStringControl('sangueautologo').onValueChange = (value: string): void => {
        this._sangueAutologoNaoColetado = value === 'N';
        if (this._sangueAutologoNaoColetado) {
            this._buildFormAvaliar.getControlAsStringControl('motivosangueautologonaocoletado').makeRequired();
        }
        else {
            this._buildFormAvaliar.getControlAsStringControl('motivosangueautologonaocoletado').makeOptional();
        }
	  }

    this.criarMensagemValidacaoPorFormGroup(this._buildFormAvaliar.form);
    this.clearMensagensErro(this._buildFormAvaliar.form);
  }

  private fonteCelulaSelecionada(value: number) {
		this._sanguePerifericoSelecionado = value == FontesCelulas.SANGUE_PERIFERICO.id;
		this._medulaOsseaSelecionada = value == FontesCelulas.MEDULA_OSSEA.id;
  }

  private limparCampos(): void {
	  this._buildFormAvaliar.getControlAsDateControl('datagcfh').markAsValid();
	  this._buildFormAvaliar.getControlAsDateControl('datagcfh').value = null;
	  this._buildFormAvaliar.getControlAsStringControl('coletaaferese').markAsValid();
	  this._buildFormAvaliar.getControlAsStringControl('coletaaferese').value = null;
	  this._buildFormAvaliar.getControlAsStringControl('acessovenoso').markAsValid();
	  this._buildFormAvaliar.getControlAsStringControl('acessovenoso').value = null;
	  this._buildFormAvaliar.getControlAsStringControl('sangueautologo').markAsValid();
	  this._buildFormAvaliar.getControlAsStringControl('sangueautologo').value = null;
	  this._buildFormAvaliar.getControlAsStringControl('motivosangueautologonaocoletado').markAsValid();
	  this._buildFormAvaliar.getControlAsStringControl('motivosangueautologonaocoletado').value = null;
  }

  private removerObrigatoriedade(): void {
	  this._buildFormAvaliar.getControlAsDateControl('datagcfh').makeOptional();
	  this._buildFormAvaliar.getControlAsDateControl('datagcfh').removeValidator(ValidateData);
	  this._buildFormAvaliar.getControlAsStringControl('coletaaferese').makeOptional();
	  this._buildFormAvaliar.getControlAsStringControl('acessovenoso').makeOptional();
	  this._buildFormAvaliar.getControlAsStringControl('sangueautologo').makeOptional();
	  this._buildFormAvaliar.getControlAsStringControl('motivosangueautologonaocoletado').makeOptional();
  }

  form(): FormGroup {
    return <FormGroup>this._buildFormAvaliar.form;
  }

  preencherFormulario(entidade: Object): void {
    throw new Error("Method not implemented.");
  }

	private carregarDadosFormulario(idPedidoWorkup: number){

		this.formularioService.obterFormulario(idPedidoWorkup, TiposFormulario.RESULTADO_WORKUP).then(res => {
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

    if (this.validateForm()) {

      let dadosFormulario: any = this._buildFormAvaliar.value;
      let resultadoWorkup: ResultadoWorkupNacionalDTO = new ResultadoWorkupNacionalDTO();

      resultadoWorkup.dataColeta = this._pedidoWorkup.dataColeta;
      resultadoWorkup.coletaInviavel = dadosFormulario.coletaInviavel === 'N';

      if(resultadoWorkup.coletaInviavel){
        resultadoWorkup.motivoInviabilidade = this.form().get('motivoInviabilidade').value;
      }
      else {
			  resultadoWorkup.idFonteCelula = dadosFormulario.fonteCelula;
      	if (dadosFormulario.fonteCelula == FontesCelulas.MEDULA_OSSEA.id) {
				    resultadoWorkup.sangueAntologoColetado = dadosFormulario.sangueautologo === 'S';
				    if (!resultadoWorkup.sangueAntologoColetado) {
					      resultadoWorkup.motivoSangueAntologoNaoColetado = dadosFormulario.motivosangueautologonaocoletado;
				    }
			  }
      	else if (dadosFormulario.fonteCelula == FontesCelulas.SANGUE_PERIFERICO.id) {
				    resultadoWorkup.dataGCSF = dadosFormulario.datagcfh;
				    resultadoWorkup.adeguadoAferese = dadosFormulario.coletaaferese === 'S';
				    if (!resultadoWorkup.adeguadoAferese) {
					      resultadoWorkup.acessoVenosoCentral = dadosFormulario.acessovenoso;
				    }
			  }
		  }
      this.formularioService.finalizarFormularioResultadoWorkup(this._pedidoWorkup.id, resultadoWorkup, this._formulario).then(res => {

        this._formulario = new Formulario().jsonToEntity(res);

        if(!this._formulario.comErro){
            if(this._formulario.tipoFormulario.id == TiposFormulario.RESULTADO_WORKUP){
              this.router.navigateByUrl('/doadores/workup/centrocoleta/consulta');
            }
        }
      },
      error => {
        ErroUtil.exibirMensagemErro(error, this.messageBox);
      });
    }
	}

  validateForm(): boolean {
		return this._buildFormAvaliar.valid;
  }

	public voltar() {
		if(this._formulario.tipoFormulario.id == TiposFormulario.RESULTADO_WORKUP){
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
     * Getter mostrarDetalhe
     * @return {boolean }
     */
	public get isMostrarDetalhe(): boolean  {
		return this._mostrarDetalhe;
	}

	public fontesCelulas(): void {
		let fontes: any[] = [];
		if (this._idFonteOpcao1 === FontesCelulas.MEDULA_OSSEA.id) {
			fontes.push({id: this._idFonteOpcao1, descricao: this.textoMedulaOssea});
		}
		else if (this._idFonteOpcao1 === FontesCelulas.SANGUE_PERIFERICO.id) {
			fontes.push({id: this._idFonteOpcao1, descricao: this.textoSanguePeriferico});
		}

		if (this._idFonteOpcao2 && this._idFonteOpcao2 === FontesCelulas.MEDULA_OSSEA.id) {
			fontes.push({id: this._idFonteOpcao2, descricao: this.textoMedulaOssea});

		}
		else if (this._idFonteOpcao2 && this._idFonteOpcao2 === FontesCelulas.SANGUE_PERIFERICO.id) {
			fontes.push({id: this._idFonteOpcao2, descricao: this.textoSanguePeriferico});
		}

		this._fontesCelulas = fontes;

	}


};
