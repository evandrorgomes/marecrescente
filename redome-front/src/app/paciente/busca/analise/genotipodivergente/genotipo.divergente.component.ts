import { Component, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { HeaderDoadorComponent } from "app/doador/consulta/header/header.doador.component";
import { DoadorService } from "app/doador/doador.service";
import { ExameDoadorNacional } from "app/doador/exame.doador.nacional";
import { LocusExame } from "app/paciente/cadastro/exame/locusexame";
import { GenotipoService } from "app/paciente/genotipo.service";
import { ExameDoador } from "app/shared/classes/exame.doador";
import { ComponenteRecurso } from "app/shared/enums/componente.recurso";
import { ErroMensagem } from "app/shared/erromensagem";
import { HistoricoNavegacao } from "app/shared/historico.navegacao";
import { PermissaoRotaComponente } from "app/shared/permissao.rota.componente";
import { DateMoment } from "app/shared/util/date/date.moment";
import { RouterUtil } from "app/shared/util/router.util";
import { ExameDoadorInternacional } from '../../../../doador/exame.doador.internacional';
import { MessageBox } from '../../../../shared/modal/message.box';
import { LocusExameService } from '../../../../shared/service/locus.exame.service';
import { ErroUtil } from '../../../../shared/util/erro.util';
import { GenotipoDTO } from '../../../genotipo.dto';
import { TiposDoador } from '../../../../shared/enums/tipos.doador';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';
import { Recursos } from '../../../../shared/enums/recursos';
import { PedidoExame } from "app/laboratorio/pedido.exame";
import { PedidoExameNacionalModal } from "../../modalcustom/modal/pedido.exame.nacional.modal";
import { PositionType } from "app/shared/component/inputcomponent/group.radio.component";
import { ResultadoDivergenciaDTO } from "app/shared/dto/resultado.divergencia.dto";
import { GenotipoDivergenteDTO } from "app/shared/dto/genotipo.divergente.dto";
import { TranslateService } from '@ngx-translate/core/src/translate.service';
import { ModalEditarTextoEmailLaboratorioComponent } from './modal/modal.editar.texto.email.laboratorio.component';
import { Laboratorio } from "app/shared/dominio/laboratorio";


/**
 * Classe que representa o componente de genotipo divergente
 * @author Bruno Sousa
 */
@Component({
    selector: 'app-genotipo-divergente',
    templateUrl: './genotipo.divergente.component.html'
})
export class GenotipoDivergenteComponent implements OnInit, PermissaoRotaComponente {

    @ViewChild('headerDoador')
    private headerDoador: HeaderDoadorComponent;

    private _genotipoDivergenteDto: GenotipoDivergenteDTO;

    private _idDoador: number;
    private _idBusca: number;
    private _idMatch: number;

    public _exames: any[];
    private _locus: string;

    public _temPermissaoDescartar: boolean;

    public _idMotivoSelecionado: string;
    public _motivoRadioPosition: PositionType = PositionType.VERTICAL;

    public _mensagemValidacao: string;

    private _motivosLabel: string[];
    private _mesangemErro: any;
    
    constructor (private router: Router, private activatedRoute: ActivatedRoute, private messageBox: MessageBox,
        private genotipoService: GenotipoService, private translate: TranslateService, 
        private autenticacaoService: AutenticacaoService) {

    }

    ngOnInit(): void {

        this.translate.get(['textosGenericos.sim', 'textosGenericos.nao']).subscribe(res => {
            this._motivosLabel = [res['textosGenericos.sim'], res['textosGenericos.nao']];            
        });

        this.translate.get(['mensagem.erro.todosexamesselecionados', 'mensagem.erro.nenhumexameselecionado']).subscribe(res => {
            this._mesangemErro = res;
        });

        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRoute, ['id', 'buscaId',  'matchId']).then(res => {
            this._idDoador = new Number(res['id']).valueOf();
            this._idBusca = new Number(res['buscaId']).valueOf();
            this._idMatch = new Number(res['matchId']).valueOf();

            this._temPermissaoDescartar = this.autenticacaoService.temRecurso(Recursos.DESCARTAR_LOCUS_EXAME);

            this.headerDoador.popularCabecalhoIdentificacaoPorDoador(this._idDoador);

            this.obterGenotipoEExames();

        });
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.GenotipoDivergenteComponent];
    }

    private limparVariaveis(): void {
        this._exames = null;
        this._locus = null;    
        this._idMotivoSelecionado = null;
    }

    private obterGenotipoEExames(): void {

        this.limparVariaveis();
    
        this.genotipoService.obterGenotipoDivergenteDoador(this._idDoador).then(res => {            
            this._genotipoDivergenteDto = null;
            if (res) {
                this._genotipoDivergenteDto = new GenotipoDivergenteDTO().jsonToEntity(res);
            }
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    public voltar(): void {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    mostrarExames(idexame: string, locus: string) {

        this.limparVariaveis();

        this._locus = locus;
        this._exames = [];
        const exameDoGenotipo: ExameDoador = this._genotipoDivergenteDto.exames.find(exame => exame.id === new Number(idexame).valueOf());
        if (exameDoGenotipo) {

            const locusExameDoGenotipo: LocusExame = exameDoGenotipo.locusExames
                .find(locusExame => locusExame.id.locus.codigo === locus && !locusExame.inativo);

            let dataExame: Date;
            let laboratorio: Laboratorio;
            if (exameDoGenotipo instanceof ExameDoadorNacional) {
                if ( (<ExameDoadorNacional>exameDoGenotipo).laboratorio) {
                    dataExame = (<ExameDoadorNacional>exameDoGenotipo).dataExame;
                    laboratorio = (<ExameDoadorNacional>exameDoGenotipo).laboratorio;
                } 
            }

            let metodologias: string;
            if (exameDoGenotipo.metodologias) {
                metodologias = exameDoGenotipo.metodologias.map(metodologia => metodologia.sigla).join(',');
            }

            const _exameGenotipo: any = {
                'id': exameDoGenotipo.id,                
                'exameDoGenotipo': true,
                'exameDivergente': false,
                'locus': locus,
                'primeiroAlelo': locusExameDoGenotipo.primeiroAlelo,
                'segundoAlelo': locusExameDoGenotipo.segundoAlelo,
                'dataExame': dataExame,
                'laboratorio': laboratorio,
                'metodologias': metodologias,
                'primeiroAleloComposicao': locusExameDoGenotipo.primeiroAleloComposicao,
                'segundoAleloComposicao': locusExameDoGenotipo.segundoAleloComposicao,
                'selecionado': false
            };

            this._exames.push(_exameGenotipo)


            const exameDivergente: ExameDoador = this.recuperarExameDivergente(exameDoGenotipo.id, exameDoGenotipo.dataCriacao, 
                locus, this._genotipoDivergenteDto.pedidos);
            if (exameDivergente) {
                const locusExameDivergente: LocusExame = exameDivergente.locusExames
                    .find(locusExame => locusExame.id.locus.codigo === locus && !locusExame.inativo);

                let dataExameDivergente: Date;
                let laboratorioDivergente: Laboratorio;
                if (exameDivergente instanceof ExameDoadorNacional) {
                    if ( (<ExameDoadorNacional>exameDivergente).laboratorio) {
                        dataExameDivergente = (<ExameDoadorNacional>exameDivergente).dataExame;
                        laboratorioDivergente = (<ExameDoadorNacional>exameDivergente).laboratorio;
                    } 
                }

                let metodologiasDirvergente: string;
                if (exameDivergente.metodologias) {
                    metodologiasDirvergente = exameDivergente.metodologias.map(metodologia => metodologia.sigla).join(',');
                }

                const _exameDivergente = {
                    'id': exameDivergente.id,
                    'exameDoGenotipo': false,
                    'exameDivergente': true,    
                    'locus': locus,
                    'primeiroAlelo': locusExameDivergente.primeiroAlelo,
                    'segundoAlelo': locusExameDivergente.segundoAlelo,
                    'dataExame': dataExameDivergente,
                    'laboratorio': laboratorioDivergente,
                    'metodologias': metodologiasDirvergente,
                    'primeiroAleloComposicao': locusExameDivergente.primeiroAleloComposicao,
                    'segundoAleloComposicao': locusExameDivergente.segundoAleloComposicao,
                    'selecionado': false
                };

                this._exames.push(_exameDivergente);
            }


            const examesPedido: ExameDoador[] = this.recuperarExamesDosPedidos();
            if (examesPedido && examesPedido.length !== 0) {
                examesPedido.forEach(examePedido => {                    
                    const locusExamePedido: LocusExame = examePedido.locusExames
                        .find(locusExame => locusExame.id.locus.codigo === locus && !locusExame.inativo);

                    let dataExamePedido: Date;
                    let laboratorioPedido: Laboratorio;
                    if (examePedido instanceof ExameDoadorNacional) {
                        if ( (<ExameDoadorNacional>examePedido).laboratorio) {
                            dataExamePedido = (<ExameDoadorNacional>examePedido).dataExame;
                            laboratorioPedido = (<ExameDoadorNacional>examePedido).laboratorio;
                        } 
                    }

                    let metodologiasPedido: string;
                    if (examePedido.metodologias) {
                        metodologiasPedido = examePedido.metodologias.map(metodologia => metodologia.sigla).join(',');
                    }

                    const examePedidoCT: any = {
                        'id': examePedido.id,
                        'exameDoGenotipo': false,
                        'exameDivergente': false,        
                        'locus': locus,
                        'primeiroAlelo': locusExamePedido.primeiroAlelo,
                        'segundoAlelo': locusExamePedido.segundoAlelo,
                        'dataExame': dataExamePedido,
                        'laboratorio': laboratorioPedido,
                        'metodologias': metodologiasPedido,
                        'primeiroAleloComposicao': locusExamePedido.primeiroAleloComposicao,
                        'segundoAleloComposicao': locusExamePedido.segundoAleloComposicao,
                        'selecionado': false
                    };

                    this._exames.push(examePedidoCT);
                });
            }
        }
    }

    private recuperarExameDivergente(idExameGenotipo: number, dataCriacao: Date, locus: string, pedidos: PedidoExame[]): ExameDoador {
        let exameDoador: ExameDoador = this._genotipoDivergenteDto.exames.filter(exame => {
                const diferenteGenotipo = exame.id !== idExameGenotipo;
                const diferenteDosPedidos = pedidos ? !pedidos.some(pedido => pedido.exame.id === exame.id): true;

                return diferenteGenotipo && diferenteDosPedidos;
            }).find(exame => {

                if (exame.locusExames && exame.locusExames.length !== 0) {
                    const locusExameDivergente: LocusExame = exame.locusExames
                        .find(locusExame => locusExame.id.locus.codigo == locus 
                            && (locusExame.primeiroAleloDivergente || locusExame.segundoAleloDivergente));

                    return DateMoment.getInstance().isDateTimeSameOrAfter(exame.dataCriacao, dataCriacao) && locusExameDivergente != null;
                }
                return false;
        });

        if (!exameDoador) {

            exameDoador = this._genotipoDivergenteDto.exames.filter(exame => {
                    const diferenteGenotipo = exame.id !== idExameGenotipo;
                    const diferenteDosPedidos = pedidos ? !pedidos.some(pedido => pedido.exame.id === exame.id): true;

                    return diferenteGenotipo && diferenteDosPedidos;
                }).find(exame => {

                    if (exame.locusExames && exame.locusExames.length !== 0) {
                        const locusExameDivergente: LocusExame = exame.locusExames.find(locusExame => locusExame.id.locus.codigo == locus 
                            && (locusExame.primeiroAleloDivergente || locusExame.segundoAleloDivergente));

                        return  DateMoment.getInstance().isDateTimeSameOrBefore(exame.dataCriacao, dataCriacao) 
                            && locusExameDivergente != null;
                    }
                    return false;
            });
        }

        return exameDoador;
    }

    private recuperarExamesDosPedidos(): ExameDoador[] {
        if (!this._genotipoDivergenteDto.pedidos  ||  this._genotipoDivergenteDto.pedidos.length === 0) {
            return null;
        }
        const examesDosPedidos: ExameDoador[] = [];
        this._genotipoDivergenteDto.pedidos.forEach(pedido => {
            const exameDoador: ExameDoador = this._genotipoDivergenteDto.exames.find(exame => exame.id === pedido.exame.id);
            examesDosPedidos.push(exameDoador);
        })

        return examesDosPedidos;
    }

    public doadorNacional(): boolean {
        if (this._genotipoDivergenteDto) {
            return TiposDoador.NACIONAL === this._genotipoDivergenteDto.tipoDoador;
        }
        return false;
    }

    public naoExisteSolicitacaoFase3EmAberto(): boolean {
        if (this._genotipoDivergenteDto) {
            return !this._genotipoDivergenteDto.existeSolicitacaoFase3EmAberto;
        }
        return false;
    }

    public abrirModalPedidoExameNacional() {
        const json:any = {};
        json['tipoSolicitacao'] = 'F3';
        json['idMatch'] = this._idMatch;
        json['resolverDivergencia'] = true;

        json.closeOption = (target?:any) => {
            this._genotipoDivergenteDto.existeSolicitacaoFase3EmAberto = true;
        }

        this.messageBox.dynamic(json, PedidoExameNacionalModal)
            .withTarget(null).show();
    }

    public motivosLabel(): string[] {
        return  this._motivosLabel;
    }

    public motivosId(): number[] {
        return new Array(0, 1);
    }

    private validate(): boolean {
        this._mensagemValidacao = '';

        const todosSelecionados: boolean = this._exames.every(exame => exame.selecionado === true);
        if (todosSelecionados) {
            this._mensagemValidacao = this._mesangemErro['mensagem.erro.todosexamesselecionados'];
        }

        const nenhumSelecionados: boolean = this._exames.every(exame => exame.selecionado === false);
        if (nenhumSelecionados) {
            this._mensagemValidacao = this._mesangemErro['mensagem.erro.nenhumexameselecionado'];
        }

        return this._mensagemValidacao === '';
    }

    public saveLocusDivergente(): void {
        if (this.validate()) {
            
            const resultado: ResultadoDivergenciaDTO = new ResultadoDivergenciaDTO(this._idMotivoSelecionado === '0');
            resultado.idDemaisExames = this._exames.filter(exame => exame.selecionado === false).map(exame => exame.id)
            resultado.idExamesSelecionados = this._exames.filter(exame => exame.selecionado === true).map(exame => exame.id)
            resultado.idBusca = this._idBusca;
            resultado.locus = this._locus;

            this.genotipoService.resolverDivergenciaGenotipoDoador(this._idDoador, resultado).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withCloseOption(() => {
                        this.obterGenotipoEExames();
                    })
                    .show();
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }


    public limparValoresSelecionados(): void {
        this._exames.forEach(exame => exame.selecionado = false);
    }

    public desabilitarBotaoSalvar(): boolean {
        if (!this._idMotivoSelecionado) {
            return true;
        }
        return this._exames.every(exame => exame.selecionado === false);
    }

    public get _genotipos(): GenotipoDTO[] {
        if (this._genotipoDivergenteDto) {
            return this._genotipoDivergenteDto.genotiposDto;
        }
        return null;
    }

    public abrirModalEditarEmail(index: number) {
        const data: any = {
            'idMatch': this._idMatch,
            'textoEmailPadrao': this._genotipoDivergenteDto.textoEmailDivergencia,
            'exameSelecionado': this._exames[index]
        }

        this.messageBox.dynamic(data, ModalEditarTextoEmailLaboratorioComponent)
            .show();
    }


}