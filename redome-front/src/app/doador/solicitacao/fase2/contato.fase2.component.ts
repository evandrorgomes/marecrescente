import { Component, EventEmitter, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DoadorContatoEnderecoComponent } from 'app/doador/cadastro/contato/endereco/doador.contato.endereco.component';
import { EnderecoContatoDoador } from 'app/doador/endereco.contato.doador';
import { ContatoTelefonico } from 'app/shared/classes/contato.telefonico';
import { Formulario } from 'app/shared/classes/formulario';
import { StatusDoador } from 'app/shared/dominio/status.doador';
import { TarefaBase } from 'app/shared/dominio/tarefa.base';
import { AcaoPedidoContato } from 'app/shared/enums/acao.pedido.contato';
import { EventEmitterService } from 'app/shared/event.emitter.service';
import { ModalConfirmation } from 'app/shared/modal/factory/modal.factory';
import { Paginacao } from 'app/shared/paginacao';
import { PedidoContatoService } from 'app/shared/service/pedido.contato.service';
import { DateMoment } from 'app/shared/util/date/date.moment';
import { DateTypeFormats } from 'app/shared/util/date/date.type.formats';
import { ErroUtil } from 'app/shared/util/erro.util';
import { IPedidoContatoFinalizadoVo } from 'app/shared/vo/interface.pedido.contato.finalizado.vo';
import { BaseForm } from '../../../shared/base.form';
import { ContatoTelefoneComponent } from "../../../shared/component/telefone/contato.telefone.component";
import { PerfilUsuario } from '../../../shared/enums/perfil.usuario';
import { TiposTarefa } from '../../../shared/enums/tipos.tarefa';
import { ErroMensagem } from '../../../shared/erromensagem';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { MessageBox } from '../../../shared/modal/message.box';
import { QuestionarioComponent } from '../../../shared/questionario/questionario.component';
import { ContatoTelefonicoService } from '../../../shared/service/contato.telefonico.service';
import { RegistroContatoService } from '../../../shared/service/registro.contato.service';
import { TarefaService } from '../../../shared/tarefa.service';
import { DataUtil } from '../../../shared/util/data.util';
import { RouterUtil } from '../../../shared/util/router.util';
import { PedidoContatoFinalizadoVo } from '../../../shared/vo/pedido.contato.finalizado.vo';
import { TipoFinalizacaoVo } from '../../../shared/vo/tipo.finalizacao.vo';
import { DoadorAtualizacaoComponent } from '../../atualizacao/doador.atualizacao.component';
import { HeaderDoadorComponent } from "../../consulta/header/header.doador.component";
import { ContatoTelefonicoDoador } from '../../contato.telefonico.doador';
import { DoadorNacional } from '../../doador.nacional';
import { DoadorService } from "../../doador.service";
import { SelecionarHemocentroComponent } from '../fase3/selecionar.hemocentro.component';
import { DoadorContatoTelefoneComponent } from './../../cadastro/contato/telefone/doador.contato.telefone.component';
import { DoadorIdentificacaoComponent } from './../../cadastro/identificacao/doador.identificacao.component';
import { ListarEvolucaoDoadorComponent } from './listar.evolucao.doador.component';
import { ModalAgendarContatoComponent } from './modal.agendar.contato.component';
import { ModalFinalizarContatoComponent } from './modal.finalizar.contato.component';
import { ModalRegistroContatoComponent } from './modal.registro.atendimento.component';
import { TentativaContatoDoador } from './tentativa.contato.doador';
import { TentativaContatoDoadorService } from './tentativa.contato.doador.service';
import { ContatoDataEventService } from '../contato.data.event.service';
import { FormularioService } from 'app/shared/service/formulario.service';

/**
 * Classe que representa o componente de contato pra fase 2.
 */
@Component({
	selector: 'contato-fase2',
	templateUrl: './contato.fase2.component.html',
	styleUrls: ['./../../doador.css', './contato.doador.css']
})


export class ContatoFase2Component extends BaseForm<TentativaContatoDoador> implements OnInit, OnDestroy {

	private idTarefa: number;
	public mostraFormulario: string = 'hide';
	public mostraDados: boolean = true;
	public paginacao: Paginacao;
	public quantidadeRegistro: number = 10;
	public habilitarFormAgendar: boolean = false;

	@ViewChild('contatoTelefoneComponent')
	public contatoTelefoneComponent: ContatoTelefoneComponent;

	@ViewChild('doadorIdentificacaoComponent')
	public doadorIdentificacaoComponent: DoadorIdentificacaoComponent;

	@ViewChild('headerDoador')
	protected headerDoador: HeaderDoadorComponent;

	@ViewChild('doadorAtualizacaoComponent')
	protected doadorAtualizacaoComponent: DoadorAtualizacaoComponent;

	@ViewChild('selecionarHemocentro')
	private selecionarHemocentroComponent: SelecionarHemocentroComponent;

	@ViewChild('questionario')
	protected questionarioComponent: QuestionarioComponent

	@ViewChild('listaEvolucaoDoador')
	protected listarEvolucaoDoadorComponent: ListarEvolucaoDoadorComponent;

	public _doador: DoadorNacional;

	private labelsInternacionalizadas: any;

	private idTentativaContato: number = 0;

	private _contato: ContatoTelefonico = null;

	public data: Array<string | RegExp>;
	public hora: Array<string | RegExp>;
	protected tentativaContato: TentativaContatoDoador;

	//private contatosTelefonicosValidos: ContatoTelefonicoDoador[];
	// Informe o título da página.
	public titulo: string;
	private usouVoltar: boolean;

	private ETAPA_TELEFONES: number = 1;
	protected ETAPA_ATUALIZACAO: number = 2;
	protected ETAPA_QUESTIONARIO: number = 3;
	protected ETAPA_EVOLUCAO: number = 4;
	private ETAPA_HEMOCENTRO: number = 5;

	protected _etapaAtual: number = 1;
	public _formulario: Formulario;

	private listaContatoSubscribe: EventEmitter<any>;
	private listaQuestionarioSubscribe: EventEmitter<any>;
	private listaEnderecoSubscribe: EventEmitter<any>;

	protected veioDaConsultaContatoPassivo = false;
	protected telaAnaliseMedica: boolean = false;

	public exibirBotaoProximoTurno: boolean = false;
	public exibirBotaoAgendar: boolean = false;
	public exibirBotaoFinalizar: boolean = false;
	public exibirBotaoReativar: boolean = false;
	public exibirBotaoCancelarTentativa: boolean = false;
	public exibirBotaoCancelar: boolean = false;
	public exibirBotaoQuestionario: boolean = false;
	public exibirBotaoVoltar: boolean = false;

	constructor(protected router: Router,
		translate: TranslateService, protected activatedRouter: ActivatedRoute,
		protected doadorService: DoadorService, private contatoTelefonicoService: ContatoTelefonicoService,
		protected tarefaService: TarefaService, private tentativaContatoService: TentativaContatoDoadorService,
		protected pedidoContatoService: PedidoContatoService, private registroContatoService: RegistroContatoService,
		protected messageBox: MessageBox, protected contatoDataEventService: ContatoDataEventService,
		protected formularioService: FormularioService) {
		super();
		this.translate = translate;
		this.data = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/];
		this.hora = [/[0-2]/, /\d/, ':', /[0-5]/, /\d/];

		this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);
	}

	ngOnInit() {

		this.translate.get("contato").subscribe(res => {
			this._formLabels = res;
	    });


		RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'botaoVoltar').then(res => {
			if (res) {
				this.usouVoltar = true;
			}
		});
		RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ['idTentativa', 'idStatusDoador', 'idTarefa']).then(res => {
			if (res && (res.idTentativa || res.idTarefa)) {
				this.veioDaConsultaContatoPassivo = true;
				this.pegarPedidoContatoDaConsultaDoador(res.idTentativa, res.idStatusDoador, res.idTarefa);
			} else {
				this.deveExibirBotoesPedidoContato();
				this.carregarProximoDoador();
			}
		});
		this.headerDoador.clearCache();
		this.translate.get('textosGenericos').subscribe(res => {
			this.labelsInternacionalizadas = res;
		});

		this.listaContatoSubscribe = EventEmitterService.get(DoadorContatoTelefoneComponent.HOUVE_ATUALIZACAO_TELEFONE).subscribe(resp => {
			this._doador.contatosTelefonicos = resp;
		});

		this.listaQuestionarioSubscribe = EventEmitterService.get(DoadorIdentificacaoComponent.HOUVE_ATUALIZACAO_SEXO).subscribe(resp => {
			this.formularioService.obterFormulario(this.tentativaContato.pedidoContato.id, this.fase == 2 ? 1 : 2).then(res => {
				this._formulario = new Formulario().jsonToEntity(res);
			})
			.catch((erro: ErroMensagem) => {
				if (erro.listaCampoMensagem && erro.listaCampoMensagem.length > 0) {
					if (erro.listaCampoMensagem[0].campo == 'erro' && erro.listaCampoMensagem[0].mensagem == "Nenhum questionário encontrado.") {
						this._formulario = null;
					}
				}
			});
		});

		this.contatoDataEventService.contatoDataEvent.atualizarHemocentro = (endereco: EnderecoContatoDoador) => {
			this._doador.enderecosContato.push(endereco);
		};
	}

	ngOnDestroy() {
		this.listaContatoSubscribe.unsubscribe();
		this.listaQuestionarioSubscribe.unsubscribe();
		this._doador = null;
	}

	public preencherFormulario(entidade: TentativaContatoDoador): void {
		throw new Error("Method not implemented.");
	}

	private buildForm() {
	}

	public form(): FormGroup {
		throw new Error("Method not implemented.");
	}


	/**
	 * Indica a fase que está sendo tratada no contato.
	 */
	public get fase(): number {
		return 2;
	}

	/**
	 * De acordo com a fase selecionada, retorna o perfil necessário para
	 * acesso as funcionalidades.
	 */
	protected obterPerfilParaFaseSelecionada(): number {
		return PerfilUsuario.ANALISTA_CONTATO_FASE2;
	}

	public retornarIdadeFormatada(): string {
		if (this._doador != null) {
			const idadeEmAnos: number = DataUtil.calcularIdadeComDate(this._doador.dataNascimento);
			return ('' + idadeEmAnos + ' ' + this.labelsInternacionalizadas['anos'] +
				' (' + this._doador.dataNascimento.toLocaleDateString() + ')');
		}
		return '';
	}

	// private pegarTarefaDeOutroUsuario(tentativaContatoId: number): void {
	// 	let tipoTarefa = this.fase == 2 ? TiposTarefa.CONTACTAR_FASE_2 : TiposTarefa.CONTACTAR_FASE_3;
	// 	this.tentativaContatoService.atribuirTarefaParaUsuarioLogado(tentativaContatoId, tipoTarefa.id).then(
	// 		tarefa => {
	// 			this.carregarDadosTelaContato(new TarefaBase().jsonToEntity(tarefa));
	// 		},
	// 		(error: ErroMensagem) => {
	// 			ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
	// 				this.irParaHome();
	// 			});
	// 		});
	// }

	private pegarPedidoContatoDaConsultaDoador(tentativaContatoId: number, idStatusDoador: number, tarefaId: number): void {

		if(tentativaContatoId && tarefaId){
			this.deveExibirBotoesFluxoNormalConsultaDoador();
			this.pedidoContatoService.obterPedidoContatoPoridTarefa(tarefaId, tentativaContatoId).then(
				tarefa => {
					this.carregarDadosTelaContato(new TarefaBase().jsonToEntity(tarefa))
			},
			(error: ErroMensagem) => {
				ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
					this.irParaHome();
				});
			});
		}else{
			if(idStatusDoador == StatusDoador.ATIVO || idStatusDoador == StatusDoador.ATIVO_RESSALVA){
				this.deveExibirBotoesFluxoNormalConsultaDoador();
			}else{
				this.deveExibirBotoesFluxoPassivo();
			}
			this.tentativaContatoService.carregarTentativaContato(tentativaContatoId).then(
				tentativa => {
						this.carregarDadosTelaContatoPassivo(new TentativaContatoDoador().jsonToEntity(tentativa));
				},
				(error: ErroMensagem) => {
					ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
						this.irParaHome();
				});
			});
		}
	}

	public carregarDadosTelaContatoPassivo(tentativa: TentativaContatoDoador) {
		this.tentativaContato = tentativa;
		this.idTentativaContato = tentativa.id;
		this.idTarefa = null;
		this.carregardados();
	}

	public carregarDadosTelaContato(tarefa: TarefaBase) {
		this.tentativaContato = tarefa.objetoRelacaoEntidade;
		this.idTentativaContato = this.tentativaContato.id;
		this.idTarefa = tarefa.id;
		this.carregardados();
	}

	private carregardados(){

		this.listarRegistrosDeContato(1);

		this.doadorService.carregarDoadorAssociadoATentativaDeContato(this.idTentativaContato).then(doador => {
			this._doador = new DoadorNacional().jsonToEntity(doador);

			this.doadorService.obterDataUltimoPedidoContatoFechado(this._doador.id).then(data => {
				Promise.resolve(this.headerDoador).then(() => {
					let dataUltimoPedido: Date;
					if (data) {
						dataUltimoPedido = DateMoment.getInstance().parse(data);
					}
					this.headerDoador.popularCabecalhoIdentificacaoParaContatoDoador(this._doador, this.tentativaContato.pedidoContato.dataCriacao, dataUltimoPedido);
				});
			},
			(error: ErroMensagem) => {
				ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
					this.router.navigateByUrl("/home");
				});
			});

			this.doadorAtualizacaoComponent.preencherDoadorComEntidade(this._doador);
			this.listarEvolucaoDoadorComponent.prreencherListaEvolucoes(this._doador.id);
		},
		(error: ErroMensagem) => {
			ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
				this.irParaHome();
			});
		});

		this.formularioService.obterFormulario(this.tentativaContato.pedidoContato.id, this.fase == 2 ? 1 : 2).then(res => {
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

	private carregarProximoDoador(): void {
		let tipoTarefa = this.fase == 2 ? TiposTarefa.CONTACTAR_FASE_2 : TiposTarefa.CONTACTAR_FASE_3;
		this.pedidoContatoService.obterPrimeiroPedidoContatoDaFila(tipoTarefa.id).then(tarefa => {
			this.carregarDadosTelaContato(new TarefaBase().jsonToEntity(tarefa));
		},
			(error: ErroMensagem) => {
				ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
					this.router.navigateByUrl("/home");
				});
			});
	}

	/**
	* Método para voltar para home
	*
	* @memberof ConferenciaComponent
	*/
	public irParaHome() {
		this.router.navigateByUrl('/home');
	}

	cancelarTentativaContato(): void {
		this.tarefaService.removerAtribuicaoTarefa(this.idTarefa).then(result => {
			this.irParaHome();
		},
		(error: ErroMensagem) => {
			ErroUtil.exibirMensagemErro(error, this.messageBox);
		});
	}

	private fecharTentativaContato(): void {
		this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
	}

	nomeComponente(): string {
		return 'ContatoFase2Component';
	}

	/**
	 * Recupera o estilo do bloco do telefone, diferente se tiver sido excluido
	 */
	getEstiloBlocoTelefone(contatoTelefonico: ContatoTelefonicoDoador) {
		let classeCss: string = "textarea-bloco ";
		let classeCssTelefoneExcluido: string = "dados-bloco-nao-atendido";
		let classeCssTelefone: string = "";

		return contatoTelefonico.naoAtendido ? classeCss + classeCssTelefoneExcluido : classeCss + classeCssTelefone;
	}

	cancelarInclusao() {
		this.mostraFormulario = 'hide';
		this.mostraDados = true;
	}

	habilitarInclusao() {
		this.mostraFormulario = '';
		this.mostraDados = false;
		this.contatoTelefoneComponent.buildForm();
	}

	/**
	 * Metodo para salvar o novo telefone do doador.
	 */
	salvarTelefone() {
		if (this.contatoTelefoneComponent.validateForm()) {
			let telefones: ContatoTelefonico[] = this.contatoTelefoneComponent.listarTelefonesContato();

			this.doadorService.incluirContatoTelefone(this._doador.id, telefones[0]).then(
				res => {
					if (!this._doador.contatosTelefonicos) {
						this._doador.contatosTelefonicos = [];
					}
					telefones[0].id = res.idObjeto;
					if (telefones[0].principal) {
						this._doador.contatosTelefonicos.forEach(contato => {
							contato.principal = false;
						})
					}
					this._doador.contatosTelefonicos.push(<ContatoTelefonicoDoador>telefones[0]);
					this.cancelarInclusao();
					this.doadorAtualizacaoComponent.preencherContatoTelefonico(this._doador.contatosTelefonicos);
				},
				(error: ErroMensagem) => {
					ErroUtil.exibirMensagemErro(error, this.messageBox);
				});
		}
	}

	excluirContatoTelefonico(contato: ContatoTelefonico) {
		this.contatoTelefonicoService.excluirContatoTelefonicoPorId(contato.id).then(
			res => {
				contato.excluido = true;
			},
			(error: ErroMensagem) => {
				ErroUtil.exibirMensagemErro(error, this.messageBox);
			}
		);

	}

	public get doador(): DoadorNacional {
		return this._doador;
	}

	public get contato(): ContatoTelefonico {
		return this._contato;
	}

	/**
	 * Mtodo que habilita etapas anteriores para ser clicada
	 *
	 * @param {number} etapaParaHabilitar
	 * @memberof CadastroComponent
	 */
	public habilitarEtapa(etapaParaHabilitar: number): void {
		if (etapaParaHabilitar < this._etapaAtual) {
			this._etapaAtual = etapaParaHabilitar;
		}
	}

	exibeEtapaQuestionario(): boolean {
		return this._formulario != null && this._formulario != undefined;
	}

	/**
	 * Retorna TRUE se a fase atual for 3.
	 */
	public isFase3(): boolean {
		return this.fase == 3;
	}

	/**
	 * Mtodo para habilitar a etapa anterior
	 *
	 * @memberof CadastroComponent
	 */
	public habilitarEtapaAnterior(): void {
		if (this._etapaAtual == this.ETAPA_TELEFONES) {
			return;
		}
		// if (this._etapaAtual == this.ETAPA_ATUALIZACAO) {
		// this.doadorAtualizacaoComponent.preencherDoadorComEntidade(this.doador);
		// }
		this._etapaAtual--;
	}

	exibirAnterior(): boolean {
		return this._etapaAtual > this.ETAPA_TELEFONES;
	}

	/**
	 * Avança para a prxima etapa, somente se o formulrio atual
	 * tiver preenchido corretamente.
	 *
	 * @memberof CadastroComponent
	 */
	public habilitarProximaEtapa(): void {
		if (this.fase == 2) {
			this.habilitarProximaEtapaFase2();
		} else {
			this.habilitarProximaEtapaFase3();
		}
		// if(this._etapaAtual == this.ETAPA_ATUALIZACAO){
		//this.doadorAtualizacaoComponent.preencherDoadorComEntidade(this.doador);
		// }
	}

	/**
	 * Avança para a prxima etapa 2, somente se o formulrio atual
	 * tiver preenchido corretamente.
	 *
	 * @memberof CadastroComponent
	 */
	public habilitarProximaEtapaFase2(): void {
		if (this._etapaAtual == this.ETAPA_EVOLUCAO) {
			return;
		} else {
			this._etapaAtual++;
		}
	}

	/**
	 * Avança para a prxima etapa na fase 3, somente se o formulrio atual
	 * tiver preenchido corretamente.
	 *
	 * @memberof CadastroComponent
	 */
	public habilitarProximaEtapaFase3(): void {
		if (this._etapaAtual == this.ETAPA_HEMOCENTRO) {
			return;
		}

		/* this.enderecosContatoDoador =
			 this.doadorAtualizacaoComponent.doadorContatoEnderecoComponent.enderecosContatoDoador; */

		if (this._etapaAtual == this.ETAPA_ATUALIZACAO && !this.exibeEtapaQuestionario()) {
			this._etapaAtual = this.ETAPA_HEMOCENTRO;
		}
		else {
			let liberaProximaEtapa: boolean = true;
			if (this.isFase3() && this._etapaAtual == this.ETAPA_QUESTIONARIO) {
				/* liberaProximaEtapa =
					 this.questionario.posicionarProximaPendente(); */
			}

			if (liberaProximaEtapa) {
				this._etapaAtual++;
			}
		}
	}

	public exibirProximo(): boolean {
		return this._etapaAtual < (this.isFase3() ? this.ETAPA_HEMOCENTRO : this.ETAPA_EVOLUCAO);
	}

	/**
	 * Mtodo para esconder as etapas que no so as atuais
	 *
	 * @param {any} etapaParaTestar
	 * @returns {boolean}
	 * @memberof CadastroComponent
	 */
	public esconderEtapa(etapaParaTestar): boolean {
		return this._etapaAtual != etapaParaTestar;
	}

	public getEnderecos(): EnderecoContatoDoador[] {
		return this._doador && this._doador.enderecosContato ? this._doador.enderecosContato.filter(endereco => !endereco.excluido) : [];
	}

	salvarFormulario() {
		this.formularioService.salvarFormularioComPedidoContatoEValidacao(this.tentativaContato.pedidoContato.id, this._formulario, true).then(res => {
			if (res) {
				this.habilitarProximaEtapa();
				this.messageBox.alert(res.mensagem).show();
			}
		},
			error => {
				ErroUtil.exibirMensagemErro(error, this.messageBox);
			});
	}

	proximoturno() {
		this.tentativaContatoService.finalizarTentativaContatoCriarProximaTentativa(this.tentativaContato.id, null).then(res => {
			this.messageBox.alert(res.mensagem).withCloseOption(() => {
				this.exibirPerguntaProximoDoador();
			}).show();
		},
			error => {
				ErroUtil.exibirMensagemErro(error, this.messageBox);
			});
	}

	private exibirPerguntaProximoDoador() {
		let modalConfirmacao: ModalConfirmation = this.messageBox.confirmation(this._formLabels['perguntaProximoDoador']);
		modalConfirmacao.yesOption = () => {
			this.habilitarEtapa(1);
			this.carregarProximoDoador();
		};
		modalConfirmacao.noOption = () => {
			this.irParaHome();
		};
		modalConfirmacao.show();
	}

	abrirModalAtendimento(contatoTelefonico: ContatoTelefonico, excluirContato: boolean) {
		let data = {
			idPedido: this.tentativaContato.pedidoContato.id
			, idContato: contatoTelefonico.id
			, nomeContato: contatoTelefonico.nome
			, atualizarContato: (id: number, nome: string) => {
				this.atualizarTelefoneDeContato(id, nome);
			}
		};
		if (excluirContato) {
			data['excluirDoador'] = () => {
				this.excluirContatoTelefonico(contatoTelefonico);
			};
		}
		const modalDinamica = this.messageBox
			.dynamic(data, ModalRegistroContatoComponent);
		modalDinamica.target = this;
		modalDinamica.closeOption = (target: any) => {
			target.listarRegistrosDeContato(1);
		};
		modalDinamica.show();
	}

	private atualizarTelefoneDeContato(id: number, nome: string) {
		this.doador.contatosTelefonicos.forEach(contato => {
			if (contato.id == id) {
				contato.nome = nome;
			};
		});
	}

	private listarRegistrosDeContato(pagina: number) {

		this.registroContatoService.listar(pagina - 1, this.quantidadeRegistro, this.tentativaContato.pedidoContato.id).then(res => {
			this.paginacao.content = res.content;
			this.paginacao.totalElements = res.totalElements;
			this.paginacao.quantidadeRegistro = this.quantidadeRegistro;
			this.paginacao.number = pagina;
		}, error => {
			ErroUtil.extrairMensagensErro(error);
		});
	}

	obterClasseCSSContato(tipoAtendimento: number): string {
		const classe = [
			'label label-danger'
			, 'label label-info'
			, 'label label-success'
			, 'label label-danger'
		];
		return classe[tipoAtendimento - 1];
	}

	formatarDataHora(data: Date): string {
		return DataUtil.toDateFormat(new Date(data), DateTypeFormats.DATE_TIME);
	}

	mudarPagina(event: any) {
		this.listarRegistrosDeContato(event.page);
	}
	selecionaQuantidadePorPagina(event: any, modal: any) {
		this.listarRegistrosDeContato(1);
	}

	agendarContato() {
		const data = {
			idTentativaContato: this.tentativaContato.id,
			telefones: this._doador.contatosTelefonicos,
			perguntarProximoDoador: () => {
				this.exibirPerguntaProximoDoador();
			}
		};
		const modalDinamica = this.messageBox.dynamic(data, ModalAgendarContatoComponent);
		modalDinamica.target = this;

		modalDinamica.show();
	}

	protected obterRecursoParaFaseSelecionada(): string {
		if (this.isFase3()) {
			return 'INATIVAR_DOADOR_FASE3';
		}
		return 'INATIVAR_DOADOR_FASE2';
	}

	reativarContato(){
		let pedidoContatoFinalizadoVo: PedidoContatoFinalizadoVo = new PedidoContatoFinalizadoVo();
		pedidoContatoFinalizadoVo.contactado = true;
		pedidoContatoFinalizadoVo.contactadoPorTerceiro = false;
		pedidoContatoFinalizadoVo.acao = AcaoPedidoContato.PROSSEGUIR;
		pedidoContatoFinalizadoVo.formulario = this._formulario;

		if (this.validarFinalizacao(pedidoContatoFinalizadoVo)) {
			this.pedidoContatoService.finalizarContato(this.tentativaContato.pedidoContato.id, pedidoContatoFinalizadoVo).then(res => {
				if(this.veioDaConsultaContatoPassivo){
					this.voltarConsulta();
				}
				this.messageBox.alert(res.mensagem).show();
				this.voltarConsulta();
			},
			error => {
					ErroUtil.exibirMensagemErro(error, this.messageBox);
			});
		}
	}

	finalizarContato() {
		const data = {
			recurso: this.obterRecursoParaFaseSelecionada(),
			finalizar: (pedidoContatofinalizadoVo: PedidoContatoFinalizadoVo) => {


				if (this.validarFinalizacao(pedidoContatofinalizadoVo)) {

					pedidoContatofinalizadoVo.formulario = this._formulario;
					if (this.isFase3 && this.selecionarHemocentroComponent && this.selecionarHemocentroComponent.selecionada) {
						pedidoContatofinalizadoVo.hemocentro = this.selecionarHemocentroComponent.selecionada.id;
					}

					this.pedidoContatoService.finalizarContato(this.tentativaContato.pedidoContato.id, pedidoContatofinalizadoVo).then(res => {
						if(this.veioDaConsultaContatoPassivo){
							this.voltarConsulta();
						}else{
							this.exibirPerguntaProximoDoador();
						}
					},
						error => {
							ErroUtil.exibirMensagemErro(error, this.messageBox);
						});
				}

			}
		};
		const modalDinamica = this.messageBox.dynamic(data, ModalFinalizarContatoComponent);
		modalDinamica.target = this;

		modalDinamica.show();

	}

	protected validarFinalizacao(pedidoContatofinalizadoVo: TipoFinalizacaoVo): boolean {
		if (!this.validarTelefoneCasoContactadoProsseguirOuCobtacatadoAnaliseMedica(pedidoContatofinalizadoVo)) {
			this.messageBox.alert("Sem telefone").show();
			return false;
		}

		if (!this.validarFormularioCasoContactadoProsseguirOucontactadoAnaliseMedica(pedidoContatofinalizadoVo)) {
			this._etapaAtual = this.ETAPA_QUESTIONARIO;
			return false;
		}

		if (!this.validarHemocentroCasoFase3ContactadoProsseguirOuContactadoAnaliseMedica(pedidoContatofinalizadoVo)) {
			this._etapaAtual = this.ETAPA_HEMOCENTRO;
			return false;
		}

		return true;
	}

	private validarTelefoneCasoContactadoProsseguirOuCobtacatadoAnaliseMedica(pedidoContatofinalizadoVo: IPedidoContatoFinalizadoVo): boolean {
		if (pedidoContatofinalizadoVo.contactado &&
			pedidoContatofinalizadoVo.acao &&
			(pedidoContatofinalizadoVo.acao.id == AcaoPedidoContato.PROSSEGUIR.id ||
				pedidoContatofinalizadoVo.acao.id == AcaoPedidoContato.ANALISE_MEDICA.id)) {


			return this._doador && this._doador.contatosTelefonicos ?
				this._doador.contatosTelefonicos.some(contatoTelefonico => !contatoTelefonico.excluido) : false;

		}

		return true;
	}

	private validarFormularioCasoContactadoProsseguirOucontactadoAnaliseMedica(pedidoContatofinalizadoVo: IPedidoContatoFinalizadoVo): boolean {
		if (pedidoContatofinalizadoVo.contactado && pedidoContatofinalizadoVo.acao &&
			(pedidoContatofinalizadoVo.acao.id == AcaoPedidoContato.PROSSEGUIR.id ||
				pedidoContatofinalizadoVo.acao.id == AcaoPedidoContato.ANALISE_MEDICA.id)) {

			let perguntaPendente: number = this.questionarioComponent.verificarTodasPreenchidas();
			return perguntaPendente == null || isNaN(perguntaPendente);
		}

		return true;
	}

	private validarHemocentroCasoFase3ContactadoProsseguirOuContactadoAnaliseMedica(pedidoContatofinalizadoVo: IPedidoContatoFinalizadoVo): boolean {
		if (pedidoContatofinalizadoVo.contactado && pedidoContatofinalizadoVo.acao &&
			(pedidoContatofinalizadoVo.acao.id == AcaoPedidoContato.PROSSEGUIR.id ||
				pedidoContatofinalizadoVo.acao.id == AcaoPedidoContato.ANALISE_MEDICA.id)) {

			return this.isFase3 && this.selecionarHemocentroComponent ? !this.selecionarHemocentroComponent.isFormularioInvalido() : true;
		}

		return true;
	}

	/**
	 * Metodo para pegar estido do link do breadCrumb
	 *
	 * @param {any} etapaAtual
	 * @returns {string}
	 * @memberof CadastroComponent
	*/
	public getEstiloDoLinkDaMigalhaDePao(etapaAtual): string {
		if (this._etapaAtual > etapaAtual) {
			return "ativo";
		}
		else if (this._etapaAtual == etapaAtual) {
			return "ativo current";
		}
		else {
			return "";
		}

	}

	public exibeEtapaTelefones(): boolean {
		return true;
	}

	public exibeEtapaHemocentro(): boolean {
		return true;
	}

	public obterTitulo(): string {
		return this._formLabels ? this._formLabels['tituloFase2'] : '';
	}

	public deveExibirBotoesPedidoContato(): void {
		this.exibirBotaoProximoTurno = true;
		this.exibirBotaoAgendar = true;
		this.exibirBotaoFinalizar = true;
		this.exibirBotaoReativar = false;
		this.exibirBotaoCancelarTentativa = true;
		this.exibirBotaoCancelar = false;
		this.exibirBotaoQuestionario = true;
		this.exibirBotaoVoltar = false;
	}

	public deveExibirBotoesFluxoNormalConsultaDoador(): void {
		this.exibirBotaoProximoTurno = false;
		this.exibirBotaoAgendar = true;
		this.exibirBotaoFinalizar = true;
		this.exibirBotaoReativar = false;
		this.exibirBotaoCancelarTentativa = false;
		this.exibirBotaoCancelar = false;
		this.exibirBotaoQuestionario = true;
		this.exibirBotaoVoltar = true;
	}

	public deveExibirBotoesFluxoPassivo(): void {
		this.exibirBotaoProximoTurno = false;
		this.exibirBotaoAgendar = false;
		this.exibirBotaoFinalizar = false;
		this.exibirBotaoReativar = true;
		this.exibirBotaoCancelarTentativa = false;
		this.exibirBotaoCancelar = false;
		this.exibirBotaoQuestionario = true;
		this.exibirBotaoVoltar = true;
	}

	public deveExibirVeioDaConsultaContatoPassivo(): boolean {
		return this.veioDaConsultaContatoPassivo == false;
	}

	public voltarConsulta() {
		this.router.navigateByUrl('/doadores/consulta/consultaContatoPassivo');
    }

};
