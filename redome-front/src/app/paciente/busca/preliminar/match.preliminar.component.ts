import { AfterViewInit, Component, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { ModalGenotipoComparadoComponent } from "app/paciente/busca/analise/modalgenotipocomparado/modal.genotipo.comparado.component";
import { AnaliseMatchPreliminarDTO } from "app/shared/dto/analise.match.preliminar.dto";
import { BuscaPreliminar } from "app/shared/model/busca.preliminar";
import { DataUtil } from "app/shared/util/data.util";
import { NgxCarousel, NgxCarouselStore } from 'ngx-carousel';
import { ListaMatchComponent } from '../../../shared/component/listamatch/lista.match.component';
import { MatchDTO } from '../../../shared/component/listamatch/match.dto';
import { Configuracao } from '../../../shared/dominio/configuracao';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { ComponenteRecurso } from "../../../shared/enums/componente.recurso";
import { FasesMatch } from '../../../shared/enums/fases.match';
import { FiltroMatch } from '../../../shared/enums/filtro.match';
import { ErroMensagem } from "../../../shared/erromensagem";
import { MessageBox } from '../../../shared/modal/message.box';
import { BuscaPreliminarService } from '../../../shared/service/busca.preliminar.service';
import { ErroUtil } from '../../../shared/util/erro.util';
import { GenotipoComparadoDTO } from '../../genotipo.comparado.dto';
import { PacienteService } from "../../paciente.service";
import { ClassificacaoABO } from "../analise/classificacao.abo";
import { ClassificacaoABOService } from "../analise/classificacao.abo.service";
import { OrdenacaoMatchUtil } from "../analise/ordenacao.match.util";
import { RouterUtil } from './../../../shared/util/router.util';
import { MatchDataEventService } from '../../../shared/component/listamatch/match.data.event.service';


/**
 * Classe que representa o componente de analise de match
 * @author Bruno Sousa
 */
@Component({
    selector: 'analise-match-preliminar',
    templateUrl: './match.preliminar.component.html'
})
export class AnaliseMatchPreliminarComponent implements OnInit, AfterViewInit {

    private _idBuscaPreliminar: number;
    private _labelAnos: string;

    @ViewChild("listaFase1")
    public listaFase1: ListaMatchComponent;

    @ViewChild("listaFase2")
    public listaFase2: ListaMatchComponent;

    @ViewChild("listaFase3")
    public listaFase3: ListaMatchComponent;

    private _analiseMatchPreliminarDTO: AnaliseMatchPreliminarDTO;

    public carouselOne: NgxCarousel;

    private _configuracao: Configuracao;

    private _listaFavoritos: MatchDTO[];

    private _quantidadeLimiteFavoritos: number;
    private _quantidadeFavoritos: number = 0;

    public fases: string[] = [FasesMatch.FASE_1, FasesMatch.FASE_2, FasesMatch.FASE_3, FasesMatch.DISPONIBILIZADO];
    private _textoFases: any;

    public genotipoComparadoDTO: GenotipoComparadoDTO;

    public filtroMatch: FiltroMatch = FiltroMatch.MEDULA;

    public atributosOrdenacao: string[];
    private ordenacaoMatchUtil: OrdenacaoMatchUtil;
    private classificacaoABO:ClassificacaoABO[];

    /**
     * @param FormBuilder
     * @param PacienteService
     * @param Router
     * @param TranslateService
     * @author Bruno Sousa
     */
    constructor(private servicePaciente: PacienteService, private router: Router,
        private translate: TranslateService, private activatedRouter: ActivatedRoute,
        private dominioService: DominioService, private buscaPreliminarService: BuscaPreliminarService,
        private messageBox: MessageBox, private classificacaoABOService: ClassificacaoABOService,
        private matchDataEventService: MatchDataEventService
        ) {

        this._listaFavoritos = [];
        this.atributosOrdenacao = [];
        this._analiseMatchPreliminarDTO = new AnaliseMatchPreliminarDTO();

        this.matchDataEventService.criarMatchDataEvent();

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

        
    }

    /**
     * 
     * @memberOf DetalheComponent
     */
    ngOnInit(): void {

        this.translate.get("textosGenericos.fases").subscribe(res => {
            this._textoFases = res;
        });
        this.translate.get("textosGenericos.anos").subscribe(res => {
            this._labelAnos = res;
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
        
        this.translate.get("analisematch.atributosOrder").subscribe(res => {
            this.atributosOrdenacao.push(res['nacional']);
            /*this.atributosOrdenacao.push(res['doadorinternacional']); */
            this.atributosOrdenacao.push(res['mismatch']);
            this.atributosOrdenacao.push(res['sexo']);
            this.atributosOrdenacao.push(res['idade']);
            this.atributosOrdenacao.push(res['dtUltimaAtua']);
            this.atributosOrdenacao.push(res['abo']);
            this.atributosOrdenacao.push(res['peso']);
            this.ordenacaoMatchUtil = new OrdenacaoMatchUtil(this.atributosOrdenacao, res);
            //this.ordenarListas(this.atributosOrdenacao);

        });

        this.classificacaoABOService.listarClassificacaoABO().then(res=>{
            this.classificacaoABO = res;
            //this.ordenarListas(this.atributosOrdenacao);
        })

    }

    ngAfterViewInit(): void {
        // Capturar parâmetro vindo da URL.
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idBuscaPreliminar").then(res => {
            this._idBuscaPreliminar = <number>res;            
            this.listarAvaliacoesMatch();
            this.dominioService.obterConfiguracao("quantidadeFavoritosAvaliacaoMatch").then(
                res => {
                    this.configuracao = new Configuracao(res.chave, res.valor);
                    this._quantidadeLimiteFavoritos = Number.parseInt(this.configuracao.valor);
                }
                , (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error, this.messageBox);
                }
            );
        }).catch(res => {
            this.translate.get("mensagem.erro.parametrosminimos").subscribe(res => {
                this.messageBox.alert(res).show();
            })            
        });

        
    }    

    private listarAvaliacoesMatch() {

        this.buscaPreliminarService.obterAvaliacoesMatch(this._idBuscaPreliminar, this.filtroMatch)
            .then(res => {
                this._analiseMatchPreliminarDTO = new AnaliseMatchPreliminarDTO().jsonToEntity(res);                                
                this.ordenarListas(this.atributosOrdenacao);
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.AnaliseMatchPreliminarComponent];
    }

    public myfunc(event: Event) {
        // carouselLoad will trigger this funnction when your load value reaches
        // it is helps to load the data by parts to increase the performance of the app
        // must use feature to all carousel
    }

    /* It will be triggered on every slide*/
    onmoveFn(data: NgxCarouselStore) {
    }

    public get analiseMatchPreliminarDTO(): AnaliseMatchPreliminarDTO {
        return this._analiseMatchPreliminarDTO;
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
            return this._analiseMatchPreliminarDTO.listaFase1 ? this._analiseMatchPreliminarDTO.listaFase1.length : 0;
        }
        else if (fase == FasesMatch.FASE_2) {
            return this._analiseMatchPreliminarDTO.listaFase2 ? this._analiseMatchPreliminarDTO.listaFase2.length : 0;
        }
        else if (fase == FasesMatch.FASE_3) {
            return this._analiseMatchPreliminarDTO.listaFase3 ? this._analiseMatchPreliminarDTO.listaFase3.length : 0;
        }
        else if (fase == FasesMatch.DISPONIBILIZADO) {
            return 0;
        }
        return 0;
    }

    voltar() {
        this.router.navigateByUrl("pacientes/buscapreliminar?cache=true");
    }

    public get configuracao(): Configuracao {
        return this._configuracao;
    }

    public set configuracao(value: Configuracao) {
        this._configuracao = value;
    }

	/**
     * Abre o modal de genotipos comparados.
     * @param  {number[]} listaIdsDoadores
     */
    public abrirModalGenotipo(listaIdsDoadores: number[]) {
        let data: any = {
            '_idBuscaPreliminar': this._idBuscaPreliminar,
            'listaIdsDoadores': listaIdsDoadores
        };
        this.messageBox.dynamic(data, ModalGenotipoComparadoComponent).withTarget(this).show();
    }

    /**
     * Compara os favoritos
     */
    public compararDoadores() {
        //this.obterFavoritos();

        let listaIdsDoadores = this._listaFavoritos.map(favorito => favorito.idDoador)
        if (listaIdsDoadores && listaIdsDoadores.length > 0) {
            this.abrirModalGenotipo(listaIdsDoadores);
        }
    }

    /**
     * Obtém os doadores favoritos
     */
    public obterFavoritos() {
        this._listaFavoritos = []
        
        if (this._analiseMatchPreliminarDTO.listaFase1) {
            this._analiseMatchPreliminarDTO.listaFase1.forEach(item => {
                if (item.ehFavorito) {
                    this._listaFavoritos.push(item);
                }
            });
        }
        if (this._analiseMatchPreliminarDTO.listaFase2) {
            this._analiseMatchPreliminarDTO.listaFase2.forEach(item => {
                if (item.ehFavorito) {
                    this._listaFavoritos.push(item);
                }
            });
        }
        if (this._analiseMatchPreliminarDTO.listaFase3) {
            this._analiseMatchPreliminarDTO.listaFase3.forEach(item => {
                if (item.ehFavorito) {
                    this._listaFavoritos.push(item);
                }
            });
        }
    }
    
    exibirModalErroFavorito() {
        this.translate.get('mensagem.mensagemQuantidadeMaxima').subscribe(res => {
            this.messageBox.alert(res).show();
        });
    }

    public get quantidadeLimiteFavoritos(): number {
        return this._quantidadeLimiteFavoritos;
    }

    public set quantidadeLimiteFavoritos(value: number) {
        this._quantidadeLimiteFavoritos = value;
    }

    public mudarVisualizacao(event: any) {
        if (event.target.id == 'rdCordao') {
            this.filtroMatch = FiltroMatch.CORDAO;
        }
        else if (event.target.id == 'rdMedula') {
            this.filtroMatch = FiltroMatch.MEDULA;
        }
        this._listaFavoritos = [];
        this.listarAvaliacoesMatch();
    }

    public quantidadeTotalMedula(): number {
        return this._analiseMatchPreliminarDTO ? this._analiseMatchPreliminarDTO.totalMedula : 0;
    }

    public quantidadeTotalCordao(): number {
        return this._analiseMatchPreliminarDTO ? this._analiseMatchPreliminarDTO.totalCordao : 0;
    }

    public retornarIdadeFormatada(buscaPreliminar: BuscaPreliminar): string {
        let idadeEmAnos: number = DataUtil.calcularIdadeComDate(buscaPreliminar.dataNascimento);        
        return ("" + idadeEmAnos + " " + this._labelAnos + " (" + buscaPreliminar.dataNascimento.toLocaleDateString() + ")");
    }

    public abrirCadastroPaciente(): void{
        this.router.navigateByUrl("pacientes/buscapreliminar/" + this._idBuscaPreliminar + "/paciente");
    }

    public exibeMensagemNenhumDoador(lista: MatchDTO[]): boolean {
        return (lista === undefined || lista.length === 0 );
    }

    /**
     * Método para ordernar as listas de matchs dos doadores.
     * 
     */
    public ordenarListas(evento): void {
        if(this.ordenacaoMatchUtil &&  this.classificacaoABO){
            this.ordenacaoMatchUtil.atributosOrdenacao = evento;
            
            if (this._analiseMatchPreliminarDTO.listaFase1) {
                this.ordenacaoMatchUtil.ordernarListaMatch(this._analiseMatchPreliminarDTO.listaFase1, this._analiseMatchPreliminarDTO.buscaPreliminar.abo, this.classificacaoABO);
            }

            if (this._analiseMatchPreliminarDTO.listaFase2) {
                this.ordenacaoMatchUtil.ordernarListaMatch(this._analiseMatchPreliminarDTO.listaFase2, this._analiseMatchPreliminarDTO.buscaPreliminar.abo, this.classificacaoABO);
            }

            if (this._analiseMatchPreliminarDTO.listaFase3) {
                this.ordenacaoMatchUtil.ordernarListaMatch(this._analiseMatchPreliminarDTO.listaFase3, this._analiseMatchPreliminarDTO.buscaPreliminar.abo, this.classificacaoABO);
            }

        }

    }


}
