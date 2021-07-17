import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { TranslateService } from "@ngx-translate/core";
import { AutenticacaoService } from "app/shared/autenticacao/autenticacao.service";
import { MatchDataEventService } from 'app/shared/component/listamatch/match.data.event.service';
import { Recursos } from "app/shared/enums/recursos";
import { TipoMenuMatch } from 'app/shared/enums/tipo.menu.match';
import { TiposDoador } from "app/shared/enums/tipos.doador";
import { DataUtil } from "app/shared/util/data.util";
import { DateMoment } from "app/shared/util/date/date.moment";
import { DateTypeFormats } from "app/shared/util/date/date.type.formats";
import { FasesMatch } from '../../../enums/fases.match';
import { MatchDTO } from "../match.dto";
import { StatusDoador } from 'app/shared/dominio/status.doador';
import {TiposSolicitacao} from "../../../enums/tipos.solicitacao";




@Component({
    selector: "card-match",
    templateUrl: './card.match.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CardMatchComponent implements OnInit, AfterViewInit {

    public _isOpen: boolean = false;
    public _FASE2: TipoMenuMatch = TipoMenuMatch.FASE2;
    public _FASE3: TipoMenuMatch = TipoMenuMatch.FASE3;
    public _CANCELAR: TipoMenuMatch = TipoMenuMatch.CANCELAR;
    public _DISPONIBILIZAR: TipoMenuMatch = TipoMenuMatch.DISPONIBILIZAR;
    public _PRESCRICAO: TipoMenuMatch = TipoMenuMatch.PRESCRICAO;
    public _EDITAR: TipoMenuMatch = TipoMenuMatch.EDITAR;
    public _CADASTRAR_RESULTADO: TipoMenuMatch = TipoMenuMatch.CADASTRAR_RESULTADO;

    @Input()
    public match: MatchDTO;

    @Input()
    public exibirStatusDoadorNoHistorico: boolean = false;

    @Input()
    public exibirMenuPrecricaoDoadorNaDisponibilidade: boolean = false;

    public _temRecursoFavoritar: boolean = false;
    public _temRecursoVisualizarRessalva: boolean = false;
    public _temRecursoVisualizarComentario: boolean = false;
    public _temRecursoVisualizarOutrosProcessoas: boolean = false;
    public _temRecursoParaVisualizarComentario: boolean = false;

    public _temPerfilMedico: boolean = false;
    public _temPerfilAnalistaBusca: boolean= false;

    public _exibirMenuFase2: boolean = false;
    public _exibirMenuFase3: boolean = false;
    public _exibirMenuCancelar: boolean = false;
    public _exibirMenuDisponibilizar: boolean = false;
    public _exibirMenuPrescricao: boolean = false;
    public _exibirMenuEditar: boolean = false;
    public _exibirMenuCadastrarResultaddo: boolean = false;

    private labelsInternacionalizadas: any;

    constructor(private translate: TranslateService, private autenticacaoService: AutenticacaoService,
        private cdr: ChangeDetectorRef,
        private matchDataEventService: MatchDataEventService) {

    }


    ngOnInit() {

        this.translate.get('textosGenericos').subscribe(res => {
            this.labelsInternacionalizadas = res;
        });

        this._temRecursoFavoritar = this.autenticacaoService.temRecurso(Recursos.VISUALIZAR_FAVORITO_MATCH);

        this._temRecursoFavoritar = this.autenticacaoService.temRecurso(Recursos.VISUALIZAR_FAVORITO_MATCH);
        this._temRecursoVisualizarRessalva = this.autenticacaoService.temRecurso(Recursos.VISUALIZAR_RESSALVA_MATCH);
        this._temRecursoVisualizarComentario = this.autenticacaoService.temRecurso(Recursos.VISUALIZAR_COMENTARIO_MATCH);
        this._temRecursoVisualizarOutrosProcessoas = this.autenticacaoService.temRecurso(Recursos.VISUALIZAR_OUTROS_PROCESSOS);
        this._temRecursoParaVisualizarComentario = this.autenticacaoService.temRecurso(Recursos.VISUALIZAR_COMENTARIO_MATCH);

        this._temPerfilMedico = this.autenticacaoService.temPerfilMedico();
        this._temPerfilAnalistaBusca = this.autenticacaoService.temPerfilAnalistaBusca();

        this.verificaExibicaoMenu();

    }

    private verificaExibicaoMenu(): void {
        this._exibirMenuFase2 = (this.match.fase == FasesMatch.FASE_1 || this.match.fase == FasesMatch.FASE_2) &&
            this.autenticacaoService.temRecursoSolicitarFase2() && !this.tempPedido() &&
            !this.verificarSeCordaoIternacional();

        this._exibirMenuFase3 = (this.match.fase == FasesMatch.FASE_1 || this.match.fase == FasesMatch.FASE_2) &&
            this.autenticacaoService.temRecursoSolicitarFase3() && !this.tempPedido();

        this._exibirMenuEditar = (this.match.fase == FasesMatch.FASE_1 || this.match.fase == FasesMatch.FASE_2) &&
            this.autenticacaoService.temRecurso(Recursos.EDITAR_FASE2_INTERNACIONAL) &&
            this.verificarSeDoadorIternacional() && this.tempPedidoFase2Internacional();

        this._exibirMenuCadastrarResultaddo = (this.match.fase == FasesMatch.FASE_1 || this.match.fase == FasesMatch.FASE_2) &&
            this.autenticacaoService.temRecursoCadastrarResultadoInternacional() &&
            this.verificarSeDoadorIternacional() && this.tempPedido();

        this._exibirMenuCancelar = this.match.fase != FasesMatch.FASE_3 && this.autenticacaoService.temRecursoCancelarSolicitar() && this.tempPedido();

        this._exibirMenuDisponibilizar = this.match.fase == FasesMatch.FASE_3 && !this.match.disponibilizado &&
            this.autenticacaoService.temRecurso(Recursos.DISPONIBILIZAR_DOADOR);

        if (FasesMatch.FASE_3 && this.match.disponibilizado && this.match.temPrescricao) {
            this._exibirMenuPrescricao = false;
        }
        else if (FasesMatch.FASE_3 && this.match.disponibilizado && !this.match.temPrescricao) {
            if (!this.autenticacaoService.temRecurso(Recursos.CADASTRAR_PRESCRICAO)) {
                this._exibirMenuPrescricao = false;
            }
            else {
                this._exibirMenuPrescricao = this.exibirMenuPrecricaoDoadorNaDisponibilidade;
            }

        }

        //this._exibirMenuPrescricao = this.match.fase == FasesMatch.FASE_3 && this.match.disponibilizado && !this.match.temPrescricao &&
//            this.exibirMenuPrecricaoDoadorNaDisponibilidade && this.autenticacaoService.temRecurso(Recursos.CADASTRAR_PRESCRICAO);
    }


    ngAfterViewInit() {


    }

    public adicionarRemoverFavorito(){
        if(!this.match.ehFavorito){
            let adicionou: Boolean  = this.matchDataEventService.matchDataEvent.adicionarFavoritos(this.match);
            if (adicionou) {
                this.match.ehFavorito = true;
                this.cdr.markForCheck();
            }
        }else{
            this.matchDataEventService.matchDataEvent.removerFavoritos(this.match);
            this.match.ehFavorito = false;
            this.cdr.markForCheck();
        }
    }

    public obterClasseFavorito(): string{
        if(!this.match || !this.match.ehFavorito){
            return "checkbox";
        }
        else{
            return "checkbox checkbox-checked";
        }
    }

    /**
     * Chama o evento assothis._
     *
     * @author Bruno Sousathis._
     * @param {number} id
     * @memberof ListaMatchComponent
     */
    public mostrarComentario() {
        this.matchDataEventService.matchDataEvent.comentar(this.match);
    }

    public ehCordao(): boolean {
        if (this.match) {
            return this.match.tipoDoador == TiposDoador.CORDAO_NACIONAL || this.match.tipoDoador == TiposDoador.CORDAO_INTERNACIONAL;
        }
        return false;
    }

    public ehMedula(): boolean {
        if (this.match) {
            return this.match.tipoDoador == TiposDoador.NACIONAL || this.match.tipoDoador == TiposDoador.INTERNACIONAL;
        }
        return false;
    }

    public ehMedulaNacional(): boolean {
        if (this.match) {
            return this.match.tipoDoador == TiposDoador.NACIONAL;
        }
        return false;
    }

    /**
     * Abre o modal para ver o genótipo de apenas um doador
     */
    public abrirModalGenotipoPorDoador() {
        this.matchDataEventService.matchDataEvent.comparar(this.match);
    }

    public acaoIconeGenotipoDivergente() {
        this.matchDataEventService.matchDataEvent.verificarDivergencia(this.match);
    }

    /**
     * @description chama o evento associado passando o dmr.
     * @author Pizão
     */
    public visualizarRessalvasDoador(): void{
        this.matchDataEventService.matchDataEvent.visualizarRessalva(this.match);
    }

    public generoDecorator(): string {
        if (this.labelsInternacionalizadas) {
            return (this.match.sexo === "M") ?
                this.labelsInternacionalizadas['homem'] : this.labelsInternacionalizadas['mulher'];
        }
        return ""
    }

    public idadeFormatada(): string {
        if (this.labelsInternacionalizadas && this.match.dataNascimento) {
            let idadeEmAnos: number = DataUtil.calcularIdadeComDate(this.match.dataNascimento);
            return ("" + idadeEmAnos + " " + this.labelsInternacionalizadas['anos']);
        }
        return "";
    }

    public pesoFormatado(): string {
        if (this.labelsInternacionalizadas && this.match.peso) {
            return (", " + this.match.peso + " " + this.labelsInternacionalizadas['kilograma']);
        }
        return "";
    }

    public qtdGestacaoFormatado(): string {
        if (this.labelsInternacionalizadas && this.match.respostaQtdGestacaoDoador) {
            return ("(G " + this.match.respostaQtdGestacaoDoador + ")");
        }
        return "";
    }

    public quantidadeTCNPorKiloFormatada(): string {
        if (this.labelsInternacionalizadas) {
            return (this.labelsInternacionalizadas['quantidadeTCNPorKilo'] + ": " + this.match.quantidadeTCNPorKilo);
        }
        return "";

    }

    public quantidadeCD34PorKiloFormatada(): string {
        if (this.labelsInternacionalizadas) {
            return (this.labelsInternacionalizadas['quantidadeCD34PorKilo'] + ": " + this.match.quantidadeCD34PorKilo);
        }
        return "";

    }

    /**
     * Formata a data de atualização para data e hora a ser exibida
     * na tela do match.
     *
     * @param match match que contém a data de atualização.
     */
    public formatarDataAtualizacao(): string{
        if (this.match) {
            return DateMoment.getInstance().format(this.match.dataAtualizacao, DateTypeFormats.DATE_TIME);
        }
        return "";
    }

    public exibirMenu(): boolean {
		return this._exibirMenuFase2 || this._exibirMenuFase3 || this._exibirMenuCancelar || this._exibirMenuDisponibilizar ||
            this._exibirMenuPrescricao || this._exibirMenuEditar;
    }

    private tempPedido(): boolean {
        if(this.match.locusPedidoExameParaPaciente || this.match.locusPedidoExame ){
            return true;
        }
        return false;
    }

    private tempPedidoFase2Internacional(): boolean {
        if(this.tempPedido() && this.match.tipoSolicitacao === TiposSolicitacao.FASE_2_INTERNACIONAL){
            return true;
        }
        return false;
    }



    private verificarSeDoadorIternacional(): boolean {
        return this.match.tipoDoador == TiposDoador.INTERNACIONAL || this.match.tipoDoador == TiposDoador.CORDAO_INTERNACIONAL;
    }

    public verificarSeCordaoIternacional(): boolean {
        return this.match.tipoDoador == TiposDoador.CORDAO_INTERNACIONAL;
    }

    public solicitarFase2() {
        this._isOpen = false;
        this.matchDataEventService.matchDataEvent.solicitarFase2(this.match).then(res => {
            this.verificaExibicaoMenu();
            this.cdr.detectChanges();
        })
    }

    public solicitarFase3() {
        this._isOpen = false;
        this.matchDataEventService.matchDataEvent.solicitarFase3(this.match).then(res => {
            this.verificaExibicaoMenu();
            this.cdr.detectChanges();
        });

    }

    public disponibilizar() {
        this._isOpen = false;
        this.matchDataEventService.matchDataEvent.disponibilizar(this.match);
    }

    public prescrever() {
        this._isOpen = false;
        this.matchDataEventService.matchDataEvent.preescrever(this.match);
    }

    public editarPedidoExame() {
        this._isOpen = false;
        this.matchDataEventService.matchDataEvent.editarPedidoExame(this.match).then(res => {
            this.verificaExibicaoMenu();
            this.cdr.detectChanges();
        });
    }

    public cadastrarResultado() {
        this._isOpen = false;
        this.matchDataEventService.matchDataEvent.cadastrarResultado(this.match);
    }

    public cancelar() {
        this._isOpen = false;
        this.matchDataEventService.matchDataEvent.cancelar(this.match).then(res => {
            this.verificaExibicaoMenu();
            this.cdr.detectChanges();
        });
    }

    public retornarTrueParaChecklist(): boolean {
        return true;
    }

    public getClassByStatusDoador(idStatusDoador:number){
        return idStatusDoador == StatusDoador.ATIVO || idStatusDoador == StatusDoador.ATIVO_RESSALVA ? 'label label-success' : 'label label-danger'
    }

}
