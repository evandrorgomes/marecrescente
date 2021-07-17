import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DoadorNacional } from 'app/doador/doador.nacional';
import { DoadorService } from 'app/doador/doador.service';
import { Formulario } from 'app/shared/classes/formulario';
import { TarefaBase } from 'app/shared/dominio/tarefa.base';
import { AcaoPedidoContato } from 'app/shared/enums/acao.pedido.contato';
import { PerfilUsuario } from 'app/shared/enums/perfil.usuario';
import { ErroMensagem } from 'app/shared/erromensagem';
import { HistoricoNavegacao } from 'app/shared/historico.navegacao';
import { MessageBox } from 'app/shared/modal/message.box';
import { ContatoTelefonicoService } from 'app/shared/service/contato.telefonico.service';
import { PedidoContatoService } from 'app/shared/service/pedido.contato.service';
import { RegistroContatoService } from 'app/shared/service/registro.contato.service';
import { TarefaService } from 'app/shared/tarefa.service';
import { DateMoment } from 'app/shared/util/date/date.moment';
import { ErroUtil } from 'app/shared/util/erro.util';
import { RouterUtil } from 'app/shared/util/router.util';
import { IAnaliseMedicaFinalizadaVo } from 'app/shared/vo/interface.analise.medica.finalizada.vo';
import { TipoFinalizacaoVo } from 'app/shared/vo/tipo.finalizacao.vo';
import { TiposSolicitacao } from '../../../../shared/enums/tipos.solicitacao';
import { ContatoDataEventService } from '../../contato.data.event.service';
import { ContatoFase2Component } from '../../fase2/contato.fase2.component';
import { TentativaContatoDoadorService } from '../../fase2/tentativa.contato.doador.service';
import { AnaliseMedicaService } from '../analise.medica.service';
import { ModalFinalizarAnaliseMedicaComponent } from './modal.finalizar.analise.medica.component';
import { FormularioService } from 'app/shared/service/formulario.service';

/**
 * Classe que representa o componente de analise médica do doador.
 */
@Component({
    selector: 'detalhe-analise-medica',
    templateUrl: '../../fase2/contato.fase2.component.html',
    styleUrls: ['./../../../doador.css', './../../fase2/contato.doador.css']
})

export class DetalheAnaliseMedicaComponent extends ContatoFase2Component {

    private idAnaliseMedica: number;
    private idTarefaAnaliseMedica: number;

    constructor(router: Router, translate: TranslateService, activatedRouter: ActivatedRoute,
		doadorService: DoadorService, contatoTelefonicoService: ContatoTelefonicoService,
		tarefaService: TarefaService, tentativaContatoService: TentativaContatoDoadorService,
		pedidoContatoService: PedidoContatoService,  registroContatoService: RegistroContatoService,
		messageBox: MessageBox, private analiseMedicaService: AnaliseMedicaService,
		contatoDataEventService: ContatoDataEventService, formularioService: FormularioService) {
        super(router, translate, activatedRouter, doadorService, contatoTelefonicoService, tarefaService,
			tentativaContatoService, pedidoContatoService, registroContatoService, messageBox,
			contatoDataEventService, formularioService);

        this._etapaAtual = 2;
    }

    public ngOnInit() {
        this.translate.get(["contato", "detalheAnaliseMedica.titulo"]).subscribe(res => {
			this._formLabels = res['contato'];
			this._formLabels.detalheAnaliseMedica = res["detalheAnaliseMedica.titulo"];
        });

        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'idanalisemedica').then(res => {
			if (res) {
				this.obterAnaliseMedica(res);
            }
        });
		this.deveExibirBotoesAnaliseMedica();
	}

	ngOnDestroy() {
	}

    public get fase(): number{
        if (this.tentativaContato && this.tentativaContato.pedidoContato.solicitacao.tipoSolicitacao.id == TiposSolicitacao.FASE_2) {
            return 2;
        }
        return 3;
    }

    protected obterPerfilParaFaseSelecionada(): number{
        return PerfilUsuario.MEDIDO_REDOME;
    }

    nomeComponente(): string {
        return 'DetalheAnaliseMedicaComponent';
    }

    private obterAnaliseMedica(idAnaliseMedica: number): void {


        this.analiseMedicaService.obterAnaliseMedica(idAnaliseMedica).then(res => {
			this.idAnaliseMedica = res.idAnaliseMedica;
			this.idTarefaAnaliseMedica = res.idTarefaAnaliseMedica;
            this.carregarDadosTelaContato(new TarefaBase().jsonToEntity(res.tarefaFinalizadaPedidoContato))
        },
        error => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        })

    }

    public carregarDadosTelaContato(tarefa: TarefaBase) {
		this.tentativaContato = tarefa.objetoRelacaoEntidade;
		let idTentativaContato: number = this.tentativaContato.id;

		this.doadorService.carregarDoadorAssociadoATentativaDeContato(idTentativaContato).then(doador => {
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

		this.formularioService.obterFormulario(tarefa.objetoRelacaoEntidade.pedidoContato.id, this.fase == 2 ? 1 : 2).then(res => {
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

    public exibeEtapaTelefones(): boolean {
		return false;
	}

	public exibeEtapaHemocentro(): boolean {
		return false;
	}

    public exibirAnterior(): boolean {
		return this._etapaAtual > this.ETAPA_ATUALIZACAO;
	}

	public exibirProximo(): boolean {
		return this._etapaAtual < this.ETAPA_EVOLUCAO
	}

 //   public deveExibirBotaoProximoTurno(): boolean {
//		return false;
//	}

    public deveExibirBotaoQuestionario(): boolean {
		return false;
	}

//	public deveExibirBotaoAgendar(): boolean {
//		return false;
//	}

//	public deveExibirBotaoCancelarTentativa(): boolean {
//		return false;
//	}

//	public deveExibirBotaoCancelar(): boolean {
//		return true;
//    }

//	public deveExibirVeioDaConsultaContatoPassivo(): boolean {
//		return false;
//	}

    public obterTitulo(): string {
		return this._formLabels ? this._formLabels['detalheAnaliseMedica'] : '';
	}

	finalizarContato() {
		const data = {
			recurso: this.obterRecursoParaFaseSelecionada(),
			finalizar: (analiseMedicaFinalizadaVo: TipoFinalizacaoVo) => {


				if (this.validarFinalizacao(analiseMedicaFinalizadaVo)) {

					analiseMedicaFinalizadaVo.formulario = this._formulario;

					this.analiseMedicaService.finalizar(this.idAnaliseMedica, analiseMedicaFinalizadaVo).then(res => {
						this.messageBox.alert(res.mensagem).withCloseOption(() => {
							//this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
							this.voltarConsulta();
						})
						.show();
					},
					(error: ErroMensagem) => {
						ErroUtil.exibirMensagemErro(error, this.messageBox);
					});
				}
			}
		};
		const modalDinamica = this.messageBox.dynamic(data, ModalFinalizarAnaliseMedicaComponent);
		modalDinamica.target = this;

		modalDinamica.show();

	}

	protected validarFinalizacao(pedidoContatofinalizadoVo: TipoFinalizacaoVo): boolean {

		if (!this.validarFormularioCasoContactadoProsseguir(pedidoContatofinalizadoVo)) {
			this._etapaAtual = this.ETAPA_QUESTIONARIO;
			return false;
		}

		return true;
	}

	private validarFormularioCasoContactadoProsseguir(pedidoContatofinalizadoVo: IAnaliseMedicaFinalizadaVo): boolean {
		if (pedidoContatofinalizadoVo.acao &&
			pedidoContatofinalizadoVo.acao.id == AcaoPedidoContato.PROSSEGUIR.id) {

			let perguntaPendente: number = this.questionarioComponent.verificarTodasPreenchidas();
			return perguntaPendente == null || isNaN(perguntaPendente);
		}

		return true;
	}

	cancelarTentativaContato(): void {
		this.tarefaService.removerAtribuicaoTarefa(this.idTarefaAnaliseMedica).then(result => {
			this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
		},
		(error: ErroMensagem) => {
			ErroUtil.exibirMensagemErro(error, this.messageBox);
		});
	}

	public deveExibirBotoesAnaliseMedica(): void {
		this.exibirBotaoProximoTurno = false;
		this.exibirBotaoAgendar = false;
		this.exibirBotaoFinalizar = true;
		this.exibirBotaoReativar = false;
		this.exibirBotaoCancelarTentativa = false;
		this.exibirBotaoCancelar = false;
		this.exibirBotaoQuestionario = false;
		this.exibirBotaoVoltar = true;
	}

	public voltarConsulta() {
		this.router.navigateByUrl('/doadores/analisemedica');
    }

};
