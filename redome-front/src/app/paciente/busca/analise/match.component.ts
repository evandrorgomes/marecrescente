import { AfterViewInit, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { MatchService } from 'app/doador/solicitacao/match.service';
import { PedidoEnvioEmdisService } from 'app/doador/solicitacao/pedidoenvioemdis.service';
import { ListaMatchComponent } from 'app/shared/component/listamatch/lista.match.component';
import { MatchDataEventService } from 'app/shared/component/listamatch/match.data.event.service';
import { WindowViewService } from 'app/shared/component/window-view/window-view.service';
import { TiposSolicitacao } from 'app/shared/enums/tipos.solicitacao';
import { BuscaChecklistService } from 'app/shared/service/busca.checklist.service';
import { NgxCarousel, NgxCarouselStore } from 'ngx-carousel';
import { SolicitacaoService } from '../../../doador/solicitacao/solicitacao.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { MatchDTO } from '../../../shared/component/listamatch/match.dto';
import { ModalComentarioMatchComponent } from '../../../shared/component/modalcomentariomatch/modal.comentario.match.component';
import { RessalvaDoadorComponent } from '../../../shared/component/ressalvadoador/ressalva.doador.component';
import { WindowViewLayerService } from '../../../shared/component/window-view/window-view-layer.service';
import { DialogoComponent } from '../../../shared/dialogo/dialogo.component';
import { Configuracao } from '../../../shared/dominio/configuracao';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { Laboratorio } from "../../../shared/dominio/laboratorio";
import { ComponenteRecurso } from "../../../shared/enums/componente.recurso";
import { FasesMatch } from '../../../shared/enums/fases.match';
import { FiltroMatch } from '../../../shared/enums/filtro.match';
import { TiposDoador } from '../../../shared/enums/tipos.doador';
import { ErroMensagem } from "../../../shared/erromensagem";
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { EventDispatcher } from '../../../shared/eventos/event.dispatcher';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { Modal, ModalConfirmation } from '../../../shared/modal/factory/modal.factory';
import { MessageBox } from '../../../shared/modal/message.box';
import { HlaService } from '../../../shared/service/hla.service';
import { TarefaService } from '../../../shared/tarefa.service';
import { ErroUtil } from '../../../shared/util/erro.util';
import { HeaderPacienteComponent } from "../../consulta/identificacao/header.paciente.component";
import { GenotipoComparadoDTO } from '../../genotipo.comparado.dto';
import { GenotipoService } from '../../genotipo.service';
import { PacienteService } from "../../paciente.service";
import { Busca } from '../busca';
import { BuscaService } from '../busca.service';
import { ConfirmarCentroTransplantePopup } from '../centroTransplante/confirmar.centro.transplante.popup';
import { DialogoBuscaComponent } from '../dialogo/dialogo.busca.component';
import { PedidoExameModal } from '../modalcustom/modal/pedido.exame.modal';
import { PedidoExameNacionalModal } from "../modalcustom/modal/pedido.exame.nacional.modal";
import { CancelarPedidoExameFase3Component } from '../pedidoexame/doadorInternacional/cancelar/cancelar.pedido.exame.fase3.component';
import { BuscaChecklistDTO } from './../../../shared/component/listamatch/classes/busca.checklist.dto';
import { IFiltrosOrdenacaoBuscaDTO } from './../../../shared/dto/interface.filtros.ordenacao.busca.dto';
import { TiposBuscaChecklist } from './../../../shared/enums/tipos.buscaChecklist';
import { AnaliseMatchDTO } from "./analise.match.dto";
import { ClassificacaoABO } from './classificacao.abo';
import { ClassificacaoABOService } from './classificacao.abo.service';
import { ModalGenotipoComparadoComponent } from './modalgenotipocomparado/modal.genotipo.comparado.component';
import { OrdenacaoMatchUtil } from './ordenacao.match.util';
import { SolicitacaoDTO } from './solicitacao.dto';
import { SubHeaderPacienteComponent } from "./subheaderpaciente/subheader.paciente.component";
import { ChecklistWindowsComponent } from './windows/checklist/checklist.windows.component';


/**
 * Classe que representa o componente de analise de match
 * @author Bruno Sousa
 */
@Component({
    selector: 'analise-match',
    templateUrl: './match.component.html',
    //changeDetection: ChangeDetectionStrategy.OnPush
})
export class AnaliseMatchComponent extends EventDispatcher<any> implements OnInit, AfterViewInit, OnDestroy {

    @ViewChild(HeaderPacienteComponent)
    public headerPaciente: HeaderPacienteComponent;

    // Modal para inclusão de comentários de um match
    @ViewChild("modalComentario")
    private modalComentario: ModalComentarioMatchComponent;

    // Modal para exibição e inclusão de ressalvas para o doador.
    @ViewChild("ressalvaDoadorModal")
    public ressalvasDoadorModal: RessalvaDoadorComponent;

    @ViewChild("dialogo")
    public dialogoComponent:DialogoComponent;

    @ViewChild("dialogoBusca")
    public dialogoBuscaComponent:DialogoBuscaComponent;

    @ViewChild("subHeaderPaciente")
    public subHeaderPacienteComponent:SubHeaderPacienteComponent;

    private _rmr: number;

    @ViewChild("modalErro")
    private modalErro;

    @ViewChild("modalFavorito")
    private modalFavorito;

    @ViewChild("modalDialogo")
    private modalDialogo;

    @ViewChild("confirmarCT")
    public modalConfirmacao: ConfirmarCentroTransplantePopup;

    @ViewChild('listaMatchFase1')
    public listaMatchFase1: ListaMatchComponent;

    @ViewChild('listaMatchFase1Descartados')
    public listaMatchFase1Descartados: ListaMatchComponent;

    @ViewChild('listaMatchFase2')
    public listaMatchFase2: ListaMatchComponent;

    @ViewChild('listaMatchFase2Descartados')
    public listaMatchFase2Descartados: ListaMatchComponent;

    @ViewChild('listaMatchFase3')
    public listaMatchFase3: ListaMatchComponent;

    @ViewChild('listaMatchFase3Descartados')
    public listaMatchFase3Descartados: ListaMatchComponent;

    private _analiseMatchDTO: AnaliseMatchDTO;

    public carouselOne: NgxCarousel;


    private _configuracao: Configuracao;

    private _listaFavoritos: MatchDTO[];

    private _quantidadeLimiteFavoritos: number;
    private _quantidadeFavoritos: number = 0;

    public fases: string[] = [FasesMatch.FASE_1, FasesMatch.FASE_2, FasesMatch.FASE_3, FasesMatch.DISPONIBILIZADO];
    private _textoFases: any;

    public busca: Busca;

    public genotipoComparadoDTO: GenotipoComparadoDTO;

    public atributosOrdenacao: string[];
    private ordenacaoMatchUtil: OrdenacaoMatchUtil;
    private classificacaoABO:ClassificacaoABO[];
    private _tarefaId: number;
    public TIPO_EXAME_FASE_2: number = 2;
    public TIPO_EXAME_CT: number = 3;
    public filtroMatch: FiltroMatch = FiltroMatch.MEDULA;

    public _listaFase1Descartados: MatchDTO[];
    public _listaFase2Descartados: MatchDTO[];
    public _listaFase3Descartados: MatchDTO[];

    public _collapsedMatchsInativosFase1: boolean = true;
    public _collapsedMatchsInativosFase2: boolean = true;
    public _collapsedMatchsInativosFase3: boolean = true;
    public _identificadorDoador:string;
    public _fase:string;
    public _temPerfilAnalistaBusca: boolean= false;
    public _temPerfilMedico: boolean = false;

    public _exibirStatusDoadorNoHistorico = true;

    private checklistWindowsComponent: ChecklistWindowsComponent;

    private listaChecklist: BuscaChecklistDTO[] = [];

    //private _matchDataEvent: MatchDataEvent = new MatchDataEvent();

    onChangesCallback: () => void;


    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private servicePaciente: PacienteService, private router: Router,
        private translate: TranslateService, private activatedRouter: ActivatedRoute,
        private solicitacaoService: SolicitacaoService,
        private genotipoService: GenotipoService,
        private dominioService: DominioService,
        private buscaService: BuscaService,
        private buscaChecklistService: BuscaChecklistService,
        private autenticacaoService: AutenticacaoService,
        private hlaService: HlaService,
        private classificacaoABOService:ClassificacaoABOService,
        private messageBox: MessageBox,
        private tarefaService: TarefaService,
        private matchDataEventService: MatchDataEventService,
        private matchService: MatchService,
        private windowView: WindowViewService,
        private windowViewLayer: WindowViewLayerService,
        private pedidoEnvioEmdisService: PedidoEnvioEmdisService,
        private cdr: ChangeDetectorRef) {
        super();

        this.windowViewLayer.zIndexStartAt = 99999;
        this._listaFavoritos = [];


		this.atributosOrdenacao = [];
        this._analiseMatchDTO = new AnaliseMatchDTO();


        EventEmitterService.get('fecharModalDialogo').subscribe(promise => {
            this.fecharModalDialogo();
        });


        EventEmitterService.get(ConfirmarCentroTransplantePopup.SUCESSO)
                .subscribe((idMatch: number): void => {
            this.confirmarProgressaoParaDisponibilizado(idMatch);
        });

        EventEmitterService.get(ConfirmarCentroTransplantePopup.FALHA)
                .subscribe((idDoador: number): void => {
            this.listarAvaliacoesMatch();
        });

        this.matchDataEventService.matchDataEvent.adicionarFavoritos = (match: MatchDTO) => {
            let quantidadeAtual = this._listaFavoritos.length + 1;

            if (quantidadeAtual > this._quantidadeLimiteFavoritos) {
                this.exibirModalErroFavorito();
                return false
            }

            this._listaFavoritos.push(match);

            return true;
        };

        this.matchDataEventService.matchDataEvent.removerFavoritos = (match: MatchDTO) => {
            let index: number = this._listaFavoritos.findIndex(_match => _match.id == match.id);
            if (index != -1) {
                this._listaFavoritos.splice(index, 1);
            }
        };


        this.matchDataEventService.matchDataEvent.comparar = (match: MatchDTO) => {
            let listaIdDoador:number[] =[];
            listaIdDoador.push(match.idDoador);
            this.abrirModalGenotipo(listaIdDoador);
        };

        this.matchDataEventService.matchDataEvent.solicitarFase2 = (match: MatchDTO): Promise<any> => {
            if (match && match.tipoDoador == TiposDoador.INTERNACIONAL) {
                return Promise.resolve(this.abrirModalPedidoExame(1, match));
            }
            else if (match && (match.tipoDoador == TiposDoador.CORDAO_INTERNACIONAL)) {
                return Promise.resolve({ then: resolve => {
                    this.solicitacaoService.solicitarFase3Internacional(match.id).then((retorno: CampoMensagem) => {
                        let target = {
                            'match': match
                        }
                        this.messageBox.alert(retorno.mensagem.toString())
                        .withTarget(target)
                        .withCloseOption( (target:any) => {
                            resolve(this.atualizaMatch(target));
                        })
                        .show()
                    }, (error: ErroMensagem) =>{
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
                }});

            }
            else if (match && match.tipoDoador == TiposDoador.NACIONAL) {
                return Promise.resolve(this.abrirModalPedidoExameNacional(match, 'F2'));
            }
            else if (match && match.tipoDoador == TiposDoador.CORDAO_NACIONAL) {
                return Promise.resolve({ then: resolve => {
                    this.messageBox.alert("Função não implementada! Favor entrar em contato com o suporte.").show();
                }});
            }

        };

        this.matchDataEventService.matchDataEvent.solicitarFase3 = (match: MatchDTO): Promise<any> => {
            if (match && (match.tipoDoador == TiposDoador.INTERNACIONAL || match.tipoDoador == TiposDoador.CORDAO_INTERNACIONAL)) {
                this.solicitacaoService.solicitarFase3Internacional(match.id).
                    then((retorno: CampoMensagem) => {
                        this.messageBox.alert(retorno.mensagem.toString())
                        .withTarget(this)
                        .withCloseOption( (target:any) => {
                            this.listarAvaliacoesMatch();
                        })
                        .show()
                    }, (error: ErroMensagem) =>{
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
            } else if (match && match.tipoDoador == TiposDoador.NACIONAL) {
                return Promise.resolve(this.abrirModalPedidoExameNacional(match, 'F3'));
            }
            else if (match && match.tipoDoador == TiposDoador.CORDAO_NACIONAL) {
                return Promise.resolve({ then: resolve => {
                    this.messageBox.alert("Função não implementada! Favor entrar em contato com o suporte.").show();
                }});
            }
        };

        this.matchDataEventService.matchDataEvent.cancelar = (match: MatchDTO): Promise<any> => {
            if (match.tipoSolicitacao  && match.tipoSolicitacao == TiposSolicitacao.FASE_2) {
                    return Promise.resolve({then: resolve => {
                        this.solicitacaoService.cancelarFase2Nacional(match.idSolicitacao).then(res => {
                            resolve(this.atualizaMatch({'match': match}));
                        }, (error: ErroMensagem) => {
                            this.exibirMensagemErro(error);
                        });
                    }});
            }
            else if (match.tipoSolicitacao && (match.tipoSolicitacao == TiposSolicitacao.FASE_2_INTERNACIONAL || match.tipoSolicitacao == TiposSolicitacao.FASE_3_INTERNACIONAL)) {
                return Promise.resolve(this.cancelarPedidoExameInternacional(match));
            }
            else if (match.tipoSolicitacao  && match.tipoSolicitacao == TiposSolicitacao.FASE_3) {
                return Promise.resolve({then: resolve => {
                    this.solicitacaoService.cancelarFase3Nacional(match.idSolicitacao)
                        .then(res => {
                            resolve(this.atualizaMatch({'match': match}));
                        }, (error: ErroMensagem) => {
                            this.exibirMensagemErro(error);
                        });
                }});
            }
        };

        this.matchDataEventService.matchDataEvent.editarPedidoExame = (match: MatchDTO): Promise<any> => {
            return Promise.resolve({then: resolve => {
                this.tarefaService.atribuirTarefaParaUsuarioLogado(match.idTarefa).then(res=>{

                    this._tarefaId = match.idTarefa;

                    const json: any = {};
                    json.idPedido = match.idPedidoExame;
                    json.acao = PedidoExameModal.ACAO_MODAL_PEDIDO_EXAME_EDITAR;
                    json.match = match;
                    json.atualizaMatch = (target: any) => {
                        resolve(this.atualizaMatch(target));
                    }


                    const modal = this.messageBox.dynamic(json, PedidoExameModal);
                    modal.target = this;
                    modal.withCloseOption(this.resultadoModal);
                    modal.show();
                },
                (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }});

        };

        this.matchDataEventService.matchDataEvent.cadastrarResultado = (match: MatchDTO) => {

            if (match.tipoSolicitacao  && match.tipoSolicitacao == TiposSolicitacao.FASE_2_INTERNACIONAL) {
                this.tarefaService.atribuirTarefaParaUsuarioLogado(match.idTarefa).then(res=>{
                    this.router.navigateByUrl('/doadoresinternacionais/pedidoexame/' + match.idSolicitacao + '/resultadoExame?idTarefa=' + match.idTarefa);
                },
                (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                });
            }
            else if (match.tipoSolicitacao  && match.tipoSolicitacao == TiposSolicitacao.FASE_3_INTERNACIONAL) {
                this.router.navigateByUrl('/doadoresinternacionais/pedidoexame/' + match.idSolicitacao + '/resultadoCT?idTarefa=' + match.idTarefa);
            }
        };

        this.matchDataEventService.matchDataEvent.comentar = (match: MatchDTO) => {
            this.modalComentario.mostrarComentario(match);
        }

        this.matchDataEventService.matchDataEvent.verificarDivergencia = (match: MatchDTO) => {

            this.router.navigateByUrl('/doadores/' + match.idDoador + '/genotipoDivergente?buscaId=' +
            this.analiseMatchDTO.buscaId + '&matchId=' + match.id +  '&tipoDoador=' + match.tipoDoador);

            this.vistarChechPorTipos(match.buscaChecklist,
                [TiposBuscaChecklist.DISCREPANCIA_GENOTIPO]).then(res => {
                    match.buscaChecklist = res;
                });
        }

        this.matchDataEventService.matchDataEvent.disponibilizar = (match: MatchDTO) => {
            if (this._analiseMatchDTO.centroTransplanteConfirmado != null ||
               (this._analiseMatchDTO != null && this._analiseMatchDTO.listaDisponibilidade != null &&
                        this._analiseMatchDTO.listaDisponibilidade.length > 0)){
                this.confirmarProgressaoParaDisponibilizado(match.id);
            }
            else {
                this.abrirModalConfirmacaoCT(match);
            }
        };

        this.matchDataEventService.matchDataEvent.atualizarChecklist = (idchecklist: number) => {
            const index = this.listaChecklist.findIndex(checklist => checklist.id === idchecklist);
            if (index !== undefined) {
                this.listaChecklist.splice(index, 1);
            }
        };

        this.matchDataEventService.matchDataEvent.preescrever = (match: MatchDTO) => {
            let pesoPaciente: string = "0";
            if (this._analiseMatchDTO.peso && this._analiseMatchDTO.peso > 0.0) {
                pesoPaciente = new String(this._analiseMatchDTO.peso * 10).valueOf();
            }
            let tipoDoador: string =
                "&tipoDoador=" + match.tipoDoador;
            let idMatch: string = "&idMatch=" + match.id;

            let idCentroTransplante: string = "&idCentroTransplante=" + this._analiseMatchDTO.centroTransplanteConfirmado.id;

            let rota: string = match.isMedula() ? 'medula': 'cordao';

            this.router.navigateByUrl(
                "/pacientes/" + this._rmr + "/prescricao/" + rota + "?idDoador=" + match.idDoador + "&peso=" + pesoPaciente + tipoDoador + idMatch + idCentroTransplante);
        }

        this._temPerfilMedico = this.autenticacaoService.temPerfilMedico();
        this._temPerfilAnalistaBusca = this.autenticacaoService.temPerfilAnalistaBusca();
    }


    /**
     *
     * @memberOf DetalheComponent
     */
    ngOnInit(): void {

        this.translate.get("textosGenericos.fases").subscribe(res => {
            this._textoFases = res;
        });

        this.carouselOne = {
            grid: { xs: -1, sm: -1, md: -1, lg: -1, all: 355 },
            slide: 1,
            speed: 400,
            interval: 4000,
            point: {
                visible: true
            },
            load: 1,
            touch: true,
            loop: false,
        }


        this.activatedRouter.params.subscribe(params => {
            this._rmr = params['idPaciente'];

            Promise.resolve(this.headerPaciente).then(() => {
                this.buscaService.obterBuscaPorRMR(this._rmr).then(res=>{
                    this.busca = new Busca().jsonToEntity(res);

                    this.buscaService.obterUltimoPedidoExame(this.busca.id).then(res=>{
                        let data = {
                                rmr: this._rmr,
                                idBusca: this.busca.id,
                                municipioEnderecoPaciente: res.municipioEnderecoPaciente,
                                ufEnderecoPaciente: res.ufEnderecoPaciente,
                                laboratorioDePrefencia:new Laboratorio(new Number(res.idLaboratorioDePrefencia).valueOf()),
                                alteracaoLaboratorio: false
                        }
                        this.headerPaciente.exibirGenotipo = true;
                        this.headerPaciente.exibirBotaoPedirExame3Fase = true;
                        this.headerPaciente.exameFase3Data = data;
                        this.headerPaciente.ultimoPedidoExame = res.pedidoExame;
                        this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this._rmr);
                    });
                });
            });

            this.dominioService.obterConfiguracao("quantidadeFavoritosAvaliacaoMatch").then(
                res => {
                    this.configuracao = new Configuracao(res.chave, res.valor);
                    this._quantidadeLimiteFavoritos = Number.parseInt(this.configuracao.valor);
                }
                , (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                }
            );

            this.listarAvaliacoesMatch();
            this.ordenarListas(this.atributosOrdenacao);
            this.carregarListasDeDoadoresInativosParaChecklist();
       });


        this.translate.get("analisematch.atributosOrder").subscribe(res => {
            this.atributosOrdenacao.push(res['nacional']);
            this.atributosOrdenacao.push(res['wmda']);
            this.atributosOrdenacao.push(res['mismatch']);
            this.atributosOrdenacao.push(res['sexo']);
            this.atributosOrdenacao.push(res['idade']);
            this.atributosOrdenacao.push(res['dtUltimaAtua']);
            this.atributosOrdenacao.push(res['abo']);
            this.atributosOrdenacao.push(res['peso']);

            this.ordenacaoMatchUtil = new OrdenacaoMatchUtil(this.atributosOrdenacao, res);
            this.ordenarListas(this.atributosOrdenacao);

        });

        this.classificacaoABOService.listarClassificacaoABO().then(res=>{
            this.classificacaoABO = res;
            this.ordenarListas(this.atributosOrdenacao);
        })

    }

    ngAfterViewInit() { }
    ngOnDestroy() {  }

    private listarAvaliacoesMatch() {

        //let referencia = target ? target : this;

        this.servicePaciente.obterMatchsSobAnalise(this._rmr, this.filtroMatch)
            .then(res => {
                this.listaChecklist = []
                this._analiseMatchDTO = new AnaliseMatchDTO();
                this._analiseMatchDTO.rmr = res.rmr;
                this._analiseMatchDTO.buscaId = res.buscaId;
                this._analiseMatchDTO.peso = new Number(res.peso).valueOf();
                this._analiseMatchDTO.centroTransplanteConfirmado = res.centroTransplanteConfirmado;
                this._analiseMatchDTO.abo = res.abo;
                this._analiseMatchDTO.boolAceitaMismatch = res.aceitaMismatch;
                this._analiseMatchDTO.temExameAnticorpo = res.temExameAnticorpo;
                this._analiseMatchDTO.transplantePrevio = res.transplantePrevio;

                this._analiseMatchDTO.nomeCentroAvaliador = res.nomeCentroAvaliador;
                this._analiseMatchDTO.nomeMedicoResponsavel = res.nomeMedicoResponsavel;
                if(this._analiseMatchDTO.boolAceitaMismatch){
                    this.translate.get("textosGenericos.sim").subscribe(res => {
                        this._analiseMatchDTO.aceitaMismatch =  res;
                    });
                    this._analiseMatchDTO.locusMismatch = res.locusMismatch;
                }
                else{
                    this.translate.get("textosGenericos.nao").subscribe(res => {
                        this._analiseMatchDTO.aceitaMismatch =  res;
                    });
                }
                if(res.temExameAnticorpo){
                    this.translate.get("textosGenericos.sim").subscribe(res => {
                        this._analiseMatchDTO.temExameAnticorpo =  res;
                    });
                    this._analiseMatchDTO.boolTemExameAnticorpo = true;
                    this._analiseMatchDTO.resultadoExameAnticorpo = res.resultadoExameAnticorpo;
                    this._analiseMatchDTO.dataExameAnticorpo = res.dataAnticorpo;
                }
                else{
                    this.translate.get("textosGenericos.nao").subscribe(res => {
                        this._analiseMatchDTO.temExameAnticorpo =  res;
                    });
                    this._analiseMatchDTO.boolTemExameAnticorpo = false;
                }

                if (res.buscaChecklist && res.buscaChecklist.length !== 0) {
                    this._analiseMatchDTO.buscaChecklist = [];
                    res.buscaChecklist.forEach(buscaChecklist => {
                        if(buscaChecklist.idTipoBuscaChecklist !== TiposBuscaChecklist.NOVA_BUSCA){
                            this.listaChecklist.push(new BuscaChecklistDTO().jsonToEntity(buscaChecklist));
                            if (!buscaChecklist.idMatch) {
                                this._analiseMatchDTO.buscaChecklist.push(new BuscaChecklistDTO().jsonToEntity(buscaChecklist));
                            }
                        }else{
                            this.buscaChecklistService.marcarVisto(buscaChecklist.id);
                        }
                    });
                }

                this._analiseMatchDTO.totalMedula = new Number(res.totalMedula).valueOf();
                this._analiseMatchDTO.totalCordao = new Number(res.totalCordao).valueOf();
                this._analiseMatchDTO.totalSolicitacaoMedula = new Number(res.totalSolicitacaoMedula).valueOf();
                this._analiseMatchDTO.totalSolicitacaoCordao = new Number(res.totalSolicitacaoCordao).valueOf();
                this._analiseMatchDTO.listaFase1 = this.popularMatchDTO(res.listaFase1);
                this._analiseMatchDTO.listaFase2 = this.popularMatchDTO(res.listaFase2);
                this._analiseMatchDTO.listaFase3 = this.popularMatchDTO(res.listaFase3);
                this._analiseMatchDTO.listaDisponibilidade = this.popularMatchDTO(res.listaDisponibilidade);
                this._analiseMatchDTO.totalHistoricoFase1 = new Number(res.totalHistoricoFase1).valueOf()
                this._analiseMatchDTO.totalHistoricoFase2 = new Number(res.totalHistoricoFase2).valueOf()
                this._analiseMatchDTO.totalHistoricoFase3 = new Number(res.totalHistoricoFase3).valueOf()
                this._analiseMatchDTO.temPrescricao = JSON.parse(res.temPrescricao);
                this._analiseMatchDTO.doadoresPrescritos = res.doadoresPrescritos;
                this._analiseMatchDTO.quantidadePrescricoes = new Number(res.quantidadePrescricoes).valueOf();

                this._analiseMatchDTO.qtdMatchWmdaMedula = new Number(res.qtdMatchWmdaMedula).valueOf();
                this._analiseMatchDTO.qtdMatchWmdaMedulaImportado = new Number(res.qtdMatchWmdaMedulaImportado).valueOf();

                this._analiseMatchDTO.qtdMatchWmdaCordao = new Number(res.qtdMatchWmdaCordao).valueOf();
                this._analiseMatchDTO.qtdMatchWmdaCordaoImportado = new Number(res.qtdMatchWmdaCordaoImportado).valueOf();

                if (res.tipoDoadorComPrescricao) {
                    this._analiseMatchDTO.tipoDoadorComPrescricao = new Number(res.tipoDoadorComPrescricao).valueOf();
                }

                /* this._temRecursoCadastrarPrescricao = this.autenticacaoService.temRecurso(Recursos.CADASTRAR_PRESCRICAO) &&
                    this.verificarCentroTransplanteUsuarioComABusca(); */


                this.ordenarListas(this.atributosOrdenacao);

                this.subHeaderPacienteComponent.analiseMatchDTO = this.analiseMatchDTO;

            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            }
        );
    }
    private carregarListasDeDoadoresInativosParaChecklist() {
        this.activatedRouter.queryParams.subscribe(params => {
            let matchativo = params['matchativo'];
            let fase = params['fase'];
            if (matchativo == "false") {
                if (fase == FasesMatch.FASE_1) {
                    this.togleDoadoresDescartadosFase1();
                }
                else if (fase == FasesMatch.FASE_2) {
                    this.togleDoadoresDescartadosFase2();
                }
                else {
                    this.togleDoadoresDescartadosFase3();
                }
            }
        });
    }

    public enviarPacienteParaEmdis(){
        this.pedidoEnvioEmdisService.enviarPacienteParaEmdis(this.busca.id).then(res =>{
            this.messageBox.alert(res.mensagem).show();
        }, erro =>{
            this.exibirMensagemErro(erro);
        });
    }


    private popularMatchDTO(listaDisponibilidade): MatchDTO[] {

        const listMatch: MatchDTO[] = [];
        if (listaDisponibilidade) {
            listaDisponibilidade.forEach(item => {
                const match = new MatchDTO().jsonToEntity(item);
                listMatch.push(match);

                if (match.buscaChecklist && match.buscaChecklist.length != 0) {
                    match.buscaChecklist.forEach(buscaChecklistNovo => {
                        const jaExiste = this.listaChecklist.some(buscaChecklist => buscaChecklist.id === buscaChecklistNovo.id);
                        if (!jaExiste) {
                            this.listaChecklist.push(buscaChecklistNovo);
                        }
                    });
                }

                /*let match: MatchDTO = new MatchDTO();
                 match.id = item.id;
                match.idDoador = item.idDoador;
                match.tipoDoador = new Number(item.tipoDoador).valueOf();
                match.dmr = item.dmr;
                match.nome = item.nome;
                match.registroOrigem = item.registroOrigem;
                match.sexo = item.sexo;
                match.dataNascimento = DataUtil.dataStringToDate(item.dataNascimento);
                match.dataAtualizacao = new Date(item.dataAtualizacao);
                match.abo = item.abo;
                match.mismatch = item.mismatch;
                match.classificacao = item.classificacao;
                match.outrosProcessos = new Number(item.outrosProcessos).valueOf();
                match.possuiRessalva = new Boolean(item.possuiRessalva).valueOf();
                match.possuiComentario = new Boolean(item.possuiComentario).valueOf();
                match.temPrescricao = new Boolean(item.temPrescricao).valueOf();
                match.idPedidoExame = new Number(item.idPedidoExame).valueOf();
                match.idTarefa = new Number(item.idTarefa).valueOf();
                match.idProcesso = new Number(item.idProcesso).valueOf();
                match.idTipoTarefa = new Number(item.idTipoTarefa).valueOf();
                match.idSolicitacao = new Number(item.idSolicitacao).valueOf();
                match.idRegistro = item.idRegistro;
                match.idBscup = item.idBscup;
                match.locusPedidoExame = item.locusPedidoExame;
                match.locusPedidoExameParaPaciente = item.locusPedidoExameParaPaciente;
                match.rmrPedidoExame = item.rmrPedidoExame;
                if (TiposDoador.CORDAO_NACIONAL == match.tipoDoador ||
                    TiposDoador.CORDAO_INTERNACIONAL == match.tipoDoador ) {
                    match.quantidadeTCNPorKilo = new Number(item.quantidadeTCNPorKilo).valueOf();
                    match.quantidadeCD34PorKilo = new Number(item.quantidadeCD34PorKilo).valueOf();
                }
                else if (TiposDoador.NACIONAL == match.tipoDoador ||
                    TiposDoador.INTERNACIONAL == match.tipoDoador ) {
                    match.peso = new Number(item.peso).valueOf();
                }
                match.buscaChecklist = item.buscaChecklist;
                match.possuiGenotipoDivergente = JSON.parse(item.possuiGenotipoDivergente);

                listMatch.push(match); */
            });
        }
        return listMatch;
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalErro.mensagem = obj.mensagem;
        })
        this.modalErro.abrirModal();
    }

    private exibirMensagemSucesso(campo: CampoMensagem) {
        this.modalErro.mensagem = campo.mensagem;
        this.modalErro.abrirModal();
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.AnaliseMatchComponent];
    }

    public myfunc(event: Event) {
        // carouselLoad will trigger this funnction when your load value reaches
        // it is helps to load the data by parts to increase the performance of the app
        // must use feature to all carousel
    }

    /* It will be triggered on every slide*/
    onmoveFn(data: NgxCarouselStore) {
    }

    public get analiseMatchDTO(): AnaliseMatchDTO {
        return this._analiseMatchDTO;
    }

    public getTitulo(fase: string): string {
        if (this._textoFases) {
            if (fase == FasesMatch.FASE_1) {
                return this._textoFases["fase1"];
            }
            else if (fase == FasesMatch.FASE_2) {
                return this._textoFases["fase2"];
            }
            else if (fase == FasesMatch.FASE_3) {
                return this._textoFases["fase3"];
            }
            else if (fase == FasesMatch.DISPONIBILIZADO) {
                return this._textoFases["disponibilizado"];
            }
        }
        else {
            return "";
        }
    }

    public quantidadePorFase(fase: string): number {
        if (fase == FasesMatch.FASE_1) {
            return this._analiseMatchDTO.listaFase1 ? this._analiseMatchDTO.listaFase1.length : 0;
        }
        else if (fase == FasesMatch.FASE_2) {
            return this._analiseMatchDTO.listaFase2 ? this._analiseMatchDTO.listaFase2.length : 0;
        }
        else if (fase == FasesMatch.FASE_3) {
            return this._analiseMatchDTO.listaFase3 ? this._analiseMatchDTO.listaFase3.length : 0;
        }
        else if (fase == FasesMatch.DISPONIBILIZADO) {
            return this._analiseMatchDTO.listaDisponibilidade ? this._analiseMatchDTO.listaDisponibilidade.length : 0;
        }
        return 0;
    }


    abrirModalPedidoExame(tipoPedidoExame:number, matchDTO:MatchDTO): Promise<any> {
        return Promise.resolve({ then: resolve => {
            let json:any = {};
            json.tipoPedidoExame = tipoPedidoExame;
            json.idMatch = matchDTO.id;
            json.acao = PedidoExameModal.ACAO_MODAL_PEDIDO_EXAME_CRIAR;
            json.closeOption = (target?:any) => {
                resolve(this.atualizaMatch({'match': matchDTO}));
            }

            let modal = this.messageBox.dynamic(json, PedidoExameModal);
            modal.target = this;
            modal.show();
        }});
    }

    abrirModalPedidoExameNacional(matchDTO:MatchDTO, tipoSolicitacao:  string): Promise<any> {
        return Promise.resolve({ then: resolve => {
            const json:any = {};
            json['tipoSolicitacao'] = tipoSolicitacao;
            json['match'] = matchDTO;

            json.closeOption = (target?:any) => {
                resolve(this.atualizaMatch(target));
            }

            let modal = this.messageBox.dynamic(json, PedidoExameNacionalModal);
            modal.target = this;
            modal.show();
        }});
    }



    /**
     * Cancela o pedido de exame, se ainda for possível.
     *
     * @param idPedidoExame ID do pedido de exame.
     */
    private cancelarPedidoExameInternacional(match: MatchDTO): Promise<any> {

        return Promise.resolve({then: resolve => {
            this._tarefaId = match.idTarefa;

            this.tarefaService.atribuirTarefaParaUsuarioLogado(match.idTarefa).then(res=>{

                let target = {
                    'match': match,
                    'atualizaMatch': (target: any) => {
                        resolve(this.atualizaMatch(target))
                    }
                };

                let modal = this.messageBox.dynamic({'idPedidoExame': match.idPedidoExame,
                            'idTipoTarefa': match.idTipoTarefa, 'idSolicitacao':match.idSolicitacao},
                            CancelarPedidoExameFase3Component);
                modal.target = target;
                modal.closeOption = this.resultadoModal;
                modal.show();
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }});

    }

    /**
     * Método executado no fechamento do modal de recebimento.
     * @param target
     */
    private resultadoModal(target:any): Promise<any>{
        return Promise.resolve( {then: resolve => {
            target.tarefaService.cancelarTarefa(target._tarefaId).then(res=>{
                resolve(this.atualizaMatch(target));
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, target.messageBox);
            });
        }});

    }

    /**
     * Confirma a progressão do match, tornando o doador disponibilizado para
     * a prescrição.
     *
     * @param idDoador ID do doador a ser disponibilizado.
     */
    private confirmarProgressaoParaDisponibilizado(idMatch: number): void {
        this.matchService.disponibilizarDoador(idMatch).then(res => {
            this.listarAvaliacoesMatch();
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        })
    }

    voltar() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    public fecharComentario(evento: any) {
        if (evento.match.fase == FasesMatch.FASE_1 && evento.match.possuiComentario ) {
            let index: number = this._analiseMatchDTO.listaFase1.findIndex(item => item.id == evento.match.id);
            this._analiseMatchDTO.listaFase1[index] = new MatchDTO().jsonToEntity(evento.match);
        }
        else if (evento.match.fase == FasesMatch.FASE_2 && evento.match.possuiComentario) {
            let index: number = this._analiseMatchDTO.listaFase2.findIndex(item => item.id == evento.match.id);
            this._analiseMatchDTO.listaFase2[index] = new MatchDTO().jsonToEntity(evento.match);
        }
        else if (evento.match.fase == FasesMatch.FASE_3 && !evento.match.disponibilizado && evento.match.possuiComentario) {
            let index: number = this._analiseMatchDTO.listaFase3.findIndex(item => item.id == evento.match.id);
            this._analiseMatchDTO.listaFase3[index] = new MatchDTO().jsonToEntity(evento.match);
        }
        else if (evento.match.fase == FasesMatch.FASE_3 && evento.match.disponibilizado && evento.match.possuiComentario) {
            let index: number = this._analiseMatchDTO.listaDisponibilidade.findIndex(item => item.id == evento.match.id);
            this._analiseMatchDTO.listaDisponibilidade[index] = new MatchDTO().jsonToEntity(evento.match);
        }
    }

    public get configuracao(): Configuracao {
        return this._configuracao;
    }

    public set configuracao(value: Configuracao) {
        this._configuracao = value;
    }

    private criarSolicitacaoDTO(rmr: number, idDoador: number, tipo: number): SolicitacaoDTO {
        return new SolicitacaoDTO(rmr, idDoador, tipo);
    }

	/**
     * Abre o modal de genotipos comparados.
     * @param  {number[]} listaIdsDoadores
     */
    public abrirModalGenotipo(listaIdsDoadores: number[]) {
        this._listaFavoritos.forEach(favorito => {
            this.vistarChechPorTipos(favorito.buscaChecklist,
                [TiposBuscaChecklist.RESULTADO_EXAME_CT,
                TiposBuscaChecklist.RESULTADO_EXAME_SEGUNDA_FASE,
                TiposBuscaChecklist.ALTEROU_GENOTIPO
            ]).then(res => {
                favorito.buscaChecklist = res;
                this.cdr.detectChanges();
            });
        });

        const data = {
            _rmr: this._rmr,
            listaIdsDoadores: listaIdsDoadores,
            analiseDto: this.analiseMatchDTO,
            ordernarLista: (genotipoComparadoDTO: GenotipoComparadoDTO) => {
                this.ordenacaoMatchUtil.ordernarListaMatch(genotipoComparadoDTO.genotiposDoadores as IFiltrosOrdenacaoBuscaDTO[],
                    this._analiseMatchDTO.abo, this.classificacaoABO);
            }
        };
        this.messageBox.dynamic(data, ModalGenotipoComparadoComponent).withTarget(this).show();
    }

   /**
     * Método para ordernar as listas de matchs dos doadores.
     *
     */
    public ordenarListasDoadores(list): void {
        this.ordenacaoMatchUtil.ordernarListaMatch(list, this._analiseMatchDTO.abo, this.classificacaoABO);
    }

    /**
     * Compara os favoritos
     */
    public compararDoadores() {
        let listaIdsDoadores = this._listaFavoritos.map(favorito => favorito.idDoador);
        if (listaIdsDoadores && listaIdsDoadores.length > 0) {
            this.abrirModalGenotipo(listaIdsDoadores);
        }
    }

    public cadastrarNovoDoadorInternacional(){
        this.router.navigateByUrl("/doadores/" + this._rmr + "/cadastro");
    }
    public cadastrarNovoCordaoInternacional(){
        this.router.navigateByUrl("/doadores/" + this._rmr + "/cadastro?cordao=true");
    }

    exibirModalErroFavorito() {
        this.translate.get('mensagem.mensagemQuantidadeMaxima').subscribe(res => {
            this.modalFavorito.mensagem = res;
            this.modalFavorito.abrirModal();
        });
    }

    public get quantidadeLimiteFavoritos(): number {
        return this._quantidadeLimiteFavoritos;
    }

    public set quantidadeLimiteFavoritos(value: number) {
        this._quantidadeLimiteFavoritos = value;
    }

    /**
     * Abre a tela (modal) para visualização do histórico da busca.
     */
    public verHistoricoBusca(): void {
        this.router.navigateByUrl("/pacientes/" + this._rmr + "/logs");
    }

/*     public temRecursoCadastrarPrescricao(): boolean {
        if (this._temRecursoCadastrarPrescricao && !this._analiseMatchDTO.temPrescricao) {
            return true;
        }
        return false;
    } */


    public doadoInternacional(tipoDoador: TiposDoador): boolean {
        if (tipoDoador) {
            return tipoDoador == TiposDoador.INTERNACIONAL || tipoDoador == TiposDoador.CORDAO_INTERNACIONAL;
        }
        return false;
    }

    private abrirModalConfirmacaoCT(match: MatchDTO): void{
        this.modalConfirmacao.abrirModal(this._rmr, match.idDoador, match.id);
    }

    abrirModalDialogo(){
        Promise.resolve(this.dialogoBuscaComponent.carregarListaDialogo(this.analiseMatchDTO.buscaId))
        .then(()=>{
            this.vistarChechPorTipos(this.analiseMatchDTO.buscaChecklist, [TiposBuscaChecklist.DIALOGO_MEDICO])
            .then(res => {
                this.analiseMatchDTO.buscaChecklist = res;
                this.cdr.detectChanges();
            });
            this.modalDialogo.show();
        });
    }

    fecharModalDialogo(){
        this.modalDialogo.hide();
    }

    public vistarChechPorTipos(listaBClistDTO: BuscaChecklistDTO[], tipos: TiposBuscaChecklist[]): Promise<BuscaChecklistDTO[]>{

        if(listaBClistDTO && listaBClistDTO.length > 0){

            const listaBuscaChecklistDTO: BuscaChecklistDTO[] = listaBClistDTO
                .filter(tipoChecklist => tipos.some(tipo => tipo === tipoChecklist.idTipoBuscaChecklist)) ;

            if(listaBuscaChecklistDTO.length > 0){
                return new Promise((resolve) => {
                    this.buscaChecklistService.marcarListaDeVistos(listaBuscaChecklistDTO.map(checklist => checklist.id)).then(res => {
                        listaBuscaChecklistDTO.forEach(checklistDto => {
                            this.matchDataEventService.matchDataEvent.atualizarChecklist(checklistDto.id);
                        });
                        resolve(
                                listaBClistDTO.filter(listaDTO => listaBuscaChecklistDTO.some(checklist => checklist.id !== listaDTO.id) )
                        );
                    }, (error: ErroMensagem) => {
                        this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
                    });
                });
            }
        }
        return Promise.resolve([]);
    }

    /**
     * Método para ordernar as listas de matchs dos doadores.
     *
     */
    public ordenarListas(evento): void {
        if(this.ordenacaoMatchUtil &&  this.classificacaoABO){
            this.ordenacaoMatchUtil.atributosOrdenacao = evento;

            if (this._analiseMatchDTO.listaFase1) {
                this._analiseMatchDTO.listaFase1 = this.ordenacaoMatchUtil.ordernarListaMatch(this._analiseMatchDTO.listaFase1 as IFiltrosOrdenacaoBuscaDTO[], this._analiseMatchDTO.abo, this.classificacaoABO);
            }

            if (this._analiseMatchDTO.listaFase2) {
                this._analiseMatchDTO.listaFase2 = this.ordenacaoMatchUtil.ordernarListaMatch(this._analiseMatchDTO.listaFase2 as IFiltrosOrdenacaoBuscaDTO[], this._analiseMatchDTO.abo, this.classificacaoABO);
            }

            if (this._analiseMatchDTO.listaFase3) {
                this._analiseMatchDTO.listaFase3 = this.ordenacaoMatchUtil.ordernarListaMatch(this._analiseMatchDTO.listaFase3 as IFiltrosOrdenacaoBuscaDTO[], this._analiseMatchDTO.abo, this.classificacaoABO);
            }

            if (this._listaFase1Descartados) {
                this._listaFase1Descartados = this.ordenacaoMatchUtil.ordernarListaMatch(this._listaFase1Descartados as IFiltrosOrdenacaoBuscaDTO[], this._analiseMatchDTO.abo, this.classificacaoABO);
            }

            if (this._listaFase2Descartados) {
                this._listaFase2Descartados = this.ordenacaoMatchUtil.ordernarListaMatch(this._listaFase2Descartados as IFiltrosOrdenacaoBuscaDTO[], this._analiseMatchDTO.abo, this.classificacaoABO);
            }

            if (this._listaFase3Descartados) {
                this._listaFase3Descartados = this.ordenacaoMatchUtil.ordernarListaMatch(this._listaFase3Descartados as IFiltrosOrdenacaoBuscaDTO[], this._analiseMatchDTO.abo, this.classificacaoABO);
            }

        }

    }

    public irParaTelaPedidoExame() {
        this.router.navigateByUrl("/pacientes/" + this._analiseMatchDTO.rmr + "/pedidoexamefase3?buscaId=" + this._analiseMatchDTO.buscaId );
    }

    public irParaTelaPedidoExamePaciente() {
        this.router.navigateByUrl("/pacientes/" + this._analiseMatchDTO.rmr + "/pedidoexamepaciente?buscaId=" + this._analiseMatchDTO.buscaId );
    }

    public mudarVisualizacao(event: any) {
        if (event.target.id == 'rdCordao') {
            this.filtroMatch = FiltroMatch.CORDAO;
        }
        else if (event.target.id == 'rdMedula') {
            this.filtroMatch = FiltroMatch.MEDULA;
        }
        this.listarAvaliacoesMatch();
    }

    public quantidadeTotalMedula(): number {
        return this._analiseMatchDTO && this._analiseMatchDTO.totalMedula ? this._analiseMatchDTO.totalMedula : 0;
    }

    public quantidadeTotalCordao(): number {
        return this._analiseMatchDTO && this._analiseMatchDTO.totalCordao ? this._analiseMatchDTO.totalCordao : 0;
    }

    public quantidadeTotalSolicitacaoMedula(): number {
        return this._analiseMatchDTO && this._analiseMatchDTO.totalSolicitacaoMedula ? this._analiseMatchDTO.totalSolicitacaoMedula : 0;
    }

    public quantidadeTotalSolicitacaoCordao(): number {
        return this._analiseMatchDTO && this._analiseMatchDTO.totalSolicitacaoCordao ? this._analiseMatchDTO.totalSolicitacaoCordao : 0;
    }

    public qtdMatchWmdaMedula(): number {
      return this._analiseMatchDTO && this._analiseMatchDTO.qtdMatchWmdaMedula ? this._analiseMatchDTO.qtdMatchWmdaMedula : 0;
    }

    public qtdMatchWmdaMedulaImportado(): number {
      return this._analiseMatchDTO && this._analiseMatchDTO.qtdMatchWmdaMedulaImportado ? this._analiseMatchDTO.qtdMatchWmdaMedulaImportado : 0;
    }

    public qtdMatchWmdaCordao(): number {
      return this._analiseMatchDTO && this._analiseMatchDTO.qtdMatchWmdaCordao ? this._analiseMatchDTO.qtdMatchWmdaCordao : 0;
    }

    public qtdMatchWmdaCordaoImportado(): number {
      return this._analiseMatchDTO && this._analiseMatchDTO.qtdMatchWmdaCordaoImportado ? this._analiseMatchDTO.qtdMatchWmdaCordaoImportado : 0;
    }

    public irParaTelaDetalhePaciente() {
        this.router.navigateByUrl("/pacientes/" + this._analiseMatchDTO.rmr);
    }

    public exibeMensagemNenhumDoador(lista: MatchDTO[]): boolean {
        return (lista === undefined || lista.length === 0 );
    }

    public togleDoadoresDescartadosFase1(): Promise<any> {
        return Promise.resolve({then: (resolve) => {
            this._collapsedMatchsInativosFase1 = !this._collapsedMatchsInativosFase1;
            this._exibirStatusDoadorNoHistorico = true;
            resolve(this.carregarListaDoadoresDescartadosFase1());
        }});
    }

    private carregarListaDoadoresDescartadosFase1(): Promise<any> {
        return Promise.resolve({then: (resolve) => {
            this._listaFase1Descartados = [];
            if (!this._collapsedMatchsInativosFase1) {
                this.servicePaciente.obterMatchsDescartadosSobAnalise(this._analiseMatchDTO.rmr?this._analiseMatchDTO.rmr: this._rmr, this.filtroMatch, 'F1').then(res => {
                    this._listaFase1Descartados = this.popularMatchDTO(res);
                    this.listaMatchFase1Descartados.lista = this._listaFase1Descartados;
                    resolve(true);
                }, (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                });
            }
            else {
                resolve(false);
            }
        }});
    }

    public togleDoadoresDescartadosFase2(): Promise<any> {
        return Promise.resolve({then: (resolve) => {
            this._collapsedMatchsInativosFase2 = !this._collapsedMatchsInativosFase2;
            this._exibirStatusDoadorNoHistorico = true;
            resolve(this.carregarListaDoadoresDescartadosFase1());
        }});
    }

    private carregarListaDoadoresDescartadosFase2(): Promise<any> {
        return Promise.resolve({then: (resolve) => {
            this._listaFase2Descartados = [];
            if (!this._collapsedMatchsInativosFase2) {
                this.servicePaciente.obterMatchsDescartadosSobAnalise(this._analiseMatchDTO.rmr, this.filtroMatch, 'F2').then(res => {
                    this._listaFase2Descartados = this.popularMatchDTO(res);
                    this.listaMatchFase2Descartados.lista = this._listaFase2Descartados;
                    resolve(true);
                }, (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                });
            }
            else {
                resolve(false);
            }
        }});
    }

    public togleDoadoresDescartadosFase3(): Promise<any> {
        return Promise.resolve({then: (resolve) => {
            this._collapsedMatchsInativosFase3 = !this._collapsedMatchsInativosFase3;
            this._exibirStatusDoadorNoHistorico = true;
            resolve(this.carregarListaDoadoresDescartadosFase1());
        }});
    }

    private carregarListaDoadoresDescartadosFase3(): Promise<any> {
        return Promise.resolve({then: (resolve) => {
            this._listaFase3Descartados = [];
            if (!this._collapsedMatchsInativosFase3) {
                this.servicePaciente.obterMatchsDescartadosSobAnalise(this._analiseMatchDTO.rmr, this.filtroMatch, 'F3').then(res => {
                    this._listaFase3Descartados = this.popularMatchDTO(res);
                    this.listaMatchFase3Descartados.lista = this._listaFase3Descartados;
                    resolve(true);
                }, (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                });
            }
            else {
                resolve(false);
            }
        }});
    }

    public obterTotalHistorico(fase: string): string {
        if (this._analiseMatchDTO) {
            if (fase === 'F1') {
                return  this._analiseMatchDTO.totalHistoricoFase1 !== 0 ? ' (' + this._analiseMatchDTO.totalHistoricoFase1 + ')' : '';
            }
            else if (fase === 'F2') {
                return  this._analiseMatchDTO.totalHistoricoFase2 !== 0 ? ' (' + this._analiseMatchDTO.totalHistoricoFase2 + ')' : '';
            }
            else if (fase === 'F3') {
                return  this._analiseMatchDTO.totalHistoricoFase3 !== 0 ? ' (' + this._analiseMatchDTO.totalHistoricoFase3 + ')' : '';
            }
        }
        return '';

    }

    public confirmarVistoFase1(data: any) {
        this._analiseMatchDTO.totalHistoricoFase1--;
    }

    public confirmarVistoFase2(data: any) {
        this._analiseMatchDTO.totalHistoricoFase2--;
    }

    public confirmarVistoFase3(data: any) {
        this._analiseMatchDTO.totalHistoricoFase3--;
    }

    public aplicarOrdenacaoTipoDoador(value: string) {
        let index;
        if (value === 'Internacional') {
            index = this.atributosOrdenacao.findIndex(ordem => ordem === 'Nacional');
        }
        else {
            index = this.atributosOrdenacao.findIndex(ordem => ordem === 'Internacional')
        }
        this.atributosOrdenacao[index] = value;

        this.ordenarListas(this.atributosOrdenacao);
    }

    public irParaTelaBuscaInternacional(): void {
        this.router.navigateByUrl('/pacientes/' + this._analiseMatchDTO.rmr + '/buscainternacional?idBusca=' +
                this.analiseMatchDTO.buscaId);
    }

    public analisarBuscaViaItemCheckList(){
        this.translate.get("pacienteBusca.mensagem.confirmarAnalise").subscribe(mensagem =>{
            let modalConfirmacao: ModalConfirmation = this.messageBox.confirmation(mensagem);
            modalConfirmacao.yesOption = () => {
                this.buscaChecklistService.marcarVistoDaAnaliseBusca(this.analiseMatchDTO.buscaId).then(res => {
                     let modal: Modal = this.messageBox.alert(res.mensagem);
                     modal.show();
            }, (error: ErroMensagem) => {
                this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
            });
        }
        modalConfirmacao.show();
        });
    }

    public trackByMatchId(index, match: MatchDTO): number {
        return match.id;
    }

    private atualizaMatch(target: any): Promise<any> {
        return Promise.resolve({ then: resolve => {
            this.matchService.obterMatchDTOPorId(target.match.id).then(res => {
                target.match = target.match.jsonToEntity(res);
                resolve(true);
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }})
    }

    exibirJanelaChecklist() {
        if (!this.checklistWindowsComponent) {

          this.checklistWindowsComponent =  this.windowView.pushWindow(ChecklistWindowsComponent); //.then(instance => {
                //this.checklistWindowsComponent = instance;
                this.checklistWindowsComponent.lista = this.listaChecklist;
                this.checklistWindowsComponent.position = {x: window.innerWidth - 430, y: 0};

                this.checklistWindowsComponent.checklistsVistados = (listaBuscaChecklistDTO: BuscaChecklistDTO[]) => {
                    listaBuscaChecklistDTO.forEach(checklistDto => {
                        if (checklistDto.fase === FasesMatch.FASE_1) {
                            if (checklistDto.matchAtivo) {
                                this._analiseMatchDTO.listaFase1 =
                                    this.vistarListasDoadores(this._analiseMatchDTO.listaFase1, checklistDto);
                            }
                            else{
                                this.listaMatchFase1Descartados.lista =
                                    this.vistarListasDoadores(this.listaMatchFase1Descartados.lista, checklistDto);
                            }
                        }
                        else if (checklistDto.fase === FasesMatch.FASE_2) {
                            if (checklistDto.matchAtivo) {
                                this._analiseMatchDTO.listaFase2 =
                                    this.vistarListasDoadores(this._analiseMatchDTO.listaFase2, checklistDto);
                            }else{
                                this.listaMatchFase2Descartados.lista =
                                    this.vistarListasDoadores(this.listaMatchFase2Descartados.lista, checklistDto);
                            }
                        }
                        else if (checklistDto.fase === FasesMatch.FASE_3) {
                            if (checklistDto.matchAtivo) {
                                this._analiseMatchDTO.listaFase3 =
                                    this.vistarListasDoadores(this._analiseMatchDTO.listaFase3, checklistDto);
                            }else{
                                this.listaMatchFase3Descartados.lista =
                                    this.vistarListasDoadores(this.listaMatchFase3Descartados.lista, checklistDto);
                                }
                        }else{
                            if(this._analiseMatchDTO.buscaChecklist.length > 1){
                                this._analiseMatchDTO.buscaChecklist =
                                    this._analiseMatchDTO.buscaChecklist.filter(checklist => checklist.id !== checklistDto.id);
                            }else{
                                this._analiseMatchDTO.buscaChecklist = null;
                            }
                        }
                        this.matchDataEventService.matchDataEvent.atualizarChecklist(checklistDto.id);
                        this.cdr.detectChanges();
                    });
                };

                this.checklistWindowsComponent.checklistSelecionado = (checklistDto: BuscaChecklistDTO) => {
                    if (checklistDto.fase === FasesMatch.FASE_1) {
                        if (checklistDto.matchAtivo) {
                            this.listaMatchFase1.localizarMatch(checklistDto.idMatch);
                        }
                        else {
                            if (this._collapsedMatchsInativosFase1) {
                                this._collapsedMatchsInativosFase1 = !this._collapsedMatchsInativosFase1;
                                this.carregarListaDoadoresDescartadosFase1().then(res => {
                                    this.listaMatchFase1Descartados.localizarMatch(checklistDto.idMatch);
                                });
                            }
                            else {
                                this.listaMatchFase1Descartados.localizarMatch(checklistDto.idMatch);
                            }
                        }
                    }
                    else if (checklistDto.fase === FasesMatch.FASE_2) {
                        if (checklistDto.matchAtivo) {
                            this.listaMatchFase2.localizarMatch(checklistDto.idMatch);
                        }
                        else {
                            if (this._collapsedMatchsInativosFase2) {
                                this._collapsedMatchsInativosFase2 = !this._collapsedMatchsInativosFase2;
                                this.carregarListaDoadoresDescartadosFase2().then(res => {
                                    this.listaMatchFase2Descartados.localizarMatch(checklistDto.idMatch);
                                });
                            }
                            else {
                                this.listaMatchFase2Descartados.localizarMatch(checklistDto.idMatch);
                            }
                        }
                    }
                    else if (checklistDto.fase === FasesMatch.FASE_3) {
                        if (checklistDto.matchAtivo) {
                            this.listaMatchFase3.localizarMatch(checklistDto.idMatch);
                        }
                        else {
                            if (this._collapsedMatchsInativosFase3) {
                                this._collapsedMatchsInativosFase3 = !this._collapsedMatchsInativosFase3;
                                this.carregarListaDoadoresDescartadosFase3().then(res => {
                                    this.listaMatchFase3Descartados.localizarMatch(checklistDto.idMatch);
                                });
                            }
                            else {
                                this.listaMatchFase3Descartados.localizarMatch(checklistDto.idMatch);
                            }
                        }
                    }
                };

                this.checklistWindowsComponent.fecharJanela = () => {
                    this.checklistWindowsComponent = null;
                }

        }
    }

    public vistarListasDoadores(lista: MatchDTO[], checklistDto: BuscaChecklistDTO): MatchDTO[] {
        if(lista != null && lista != undefined){
            const idx = lista.findIndex(match => match.id === checklistDto.idMatch);
            const matchDto = new MatchDTO().jsonToEntity(lista[idx]);
            const listaAux = lista.filter(match => match.id !== checklistDto.idMatch);

            if(matchDto.buscaChecklist.length > 1){
                matchDto.buscaChecklist = matchDto.buscaChecklist
                    .filter(checklist => checklist.id !== checklistDto.id);
            }else {
                matchDto.buscaChecklist = null;
            }
            listaAux.push(matchDto);
            return this.ordenacaoMatchUtil.ordernarListaMatch(listaAux as IFiltrosOrdenacaoBuscaDTO[],
                this._analiseMatchDTO.abo, this.classificacaoABO);
        }
        return lista;
    }

    public temPerfilMedico(): boolean{
        return this._temPerfilMedico;
    }

    public temPerfilAnalistaDeBusca(): boolean{
        return this._temPerfilAnalistaBusca;
    }
}
