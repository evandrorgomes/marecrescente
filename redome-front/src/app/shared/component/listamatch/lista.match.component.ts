import {ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnDestroy, OnInit} from "@angular/core";
import {MatchDTO} from "./match.dto";
import {FormBuilder, FormGroup} from '@angular/forms';
import {Subscription} from 'rxjs/Subscription';
import {TiposDoador} from "app/shared/enums/tipos.doador";
import {ActivatedRoute} from "@angular/router";
import {FiltroMatch} from "../../enums/filtro.match";

/**
 * Classe que representa o componente de lista de matchs
 * @author Bruno Sousa
 */
@Component({
    selector: "lista-match",
    templateUrl: './lista.match.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaMatchComponent implements OnInit, OnDestroy {

    private _lista: MatchDTO[];

    public paginaAtual: number = 0;

    public listaPaginada: MatchDTO[];

    private qtdItensPorPagina: number = 10;

    public proximaPaginaVisible: boolean = false;

    public _formFiltro: FormGroup;

    private identificadorDoadorSubscription: Subscription


    public _identificadorDoador:string;


    constructor(private fb: FormBuilder, private cdr: ChangeDetectorRef, private activatedRouter: ActivatedRoute) {
        this._formFiltro = this.fb.group({
            identificadorDoador: [null, null]
        });
    }

    ngOnInit() {
        this.identificadorDoadorSubscription = this._formFiltro.get('identificadorDoador').valueChanges.subscribe(value => {
            this.localizarMatchPorIndentificacaoDoador(value);
        });
    }

    ngOnDestroy() {
        this.identificadorDoadorSubscription.unsubscribe();
    }

    @Input()
    public exibirStatusDoadorNoHistorico: boolean = false;

    @Input()
    public possuiPrescricao: boolean = false;

    @Input()
    public quantidadePrescricoes: number = 0;

    @Input()
    public tipoDoadorComPrescricao: number;

    @Input()
    public filtroMatch: FiltroMatch = FiltroMatch.MEDULA;

    @Input()
    public set lista(values: MatchDTO[]) {
        this._lista = values;
        if (this._lista && this._lista.length != 0) {

            if (this._lista.length <= this.qtdItensPorPagina) {
                this.listaPaginada = this._lista.slice();
            }
            else {
                this.paginaAtual = 1;
                this.proximaPaginaVisible = true;
                this.listaPaginada = this._lista.slice(this.paginaAtual - 1, this.qtdItensPorPagina);
            }
        }
        this.cdr.detectChanges();
        this.pesquisarDoadorPorIdenticadorVindoDaUrl();
    }

    public paginaAnterior() {
        this.carregaRegistros(this.paginaAtual - 1);
    }


    public proximaPagina() {
        this.carregaRegistros(this.paginaAtual + 1);
    }

    private pesquisarDoadorPorIdenticadorVindoDaUrl() {
        this.activatedRouter.queryParams.subscribe(params => {
            this._identificadorDoador = params['identificacaoDoador'];
            if (this._identificadorDoador) {
                this.localizarMatchPorIndentificacaoDoador(this._identificadorDoador);
            }
        });
    }

    private carregaRegistros(_pagina) {
        this.limparFiltro();
        const indexInicial = (_pagina - 1) * this.qtdItensPorPagina;
        const indexFinal = _pagina * this.qtdItensPorPagina;
        if (indexFinal >= this._lista.length) {
            this.listaPaginada = this._lista.slice(indexInicial);
            this.proximaPaginaVisible = false;
        }
        else {
            this.listaPaginada = this._lista.slice(indexInicial, indexFinal);
            this.proximaPaginaVisible = true;
        }
        this.paginaAtual = _pagina;
    }

    public localizarMatch(idMatch: number) {
        if (idMatch && this._lista && this._lista.length > this.qtdItensPorPagina ) {
            this.posicionarListaPorIndex(this._lista.findIndex(matchDto => matchDto.id === idMatch));
        }
    }

    public localizarMatchPorIndentificacaoDoador(identificadorDoador: string) {
        if (identificadorDoador && this._lista && this._lista.length > this.qtdItensPorPagina ) {
            const index = this._lista.findIndex(matchDto => {

                if (matchDto.tipoDoador === TiposDoador.NACIONAL) {
                    return  matchDto.dmr.toString().startsWith(identificadorDoador);
                }
                else if (matchDto.tipoDoador === TiposDoador.CORDAO_NACIONAL) {
                    return matchDto.idBscup.startsWith(identificadorDoador);
                }
                else {
                    return matchDto.idRegistro.startsWith(identificadorDoador);
                }
            });

            this.posicionarListaPorIndex(index);
        }
        else if (!identificadorDoador && identificadorDoador != null ) {
            this.posicionarListaPorIndex(0);
        }
    }

    private posicionarListaPorIndex(index: number) {
        if (index != undefined && index > -1) {
            let _pagina = new Number((index / 10).toFixed(1).split('.')[0]).valueOf() + 2;
            if (index === 0) {
                _pagina--;
            }
            const indexFinal = index + this.qtdItensPorPagina;
            if (indexFinal >= this._lista.length) {
                this.listaPaginada = this._lista.slice(index);
                this.proximaPaginaVisible = false;
            }
            else {
                this.listaPaginada = this._lista.slice(index, indexFinal);
                this.proximaPaginaVisible = true;
            }
            this.paginaAtual = _pagina;
            this.cdr.detectChanges();
        }
    }

    public exibirBusca(): boolean {
        return this._lista && this._lista.length > this.qtdItensPorPagina ? true : false;
    }

    private limparFiltro() {
        this._formFiltro.get('identificadorDoador').setValue(null);
    }

    public cancelarPesqquisa() {
        this.limparFiltro();
        this.carregaRegistros(1);
    }

    public exibirIconeCancelarPesquisa(): boolean {
        return this._formFiltro.get('identificadorDoador').value;
    }

    public exibirPrescricao(): boolean{
        if (!this.possuiPrescricao ) {
            return true
        }
        else if (this.possuiPrescricao && this.isMedula()) {
            return false;
        }
        else if (this.possuiPrescricao && this.isCordao()) {
            if (this.quantidadePrescricoes < 2 && this.filtroMatch == FiltroMatch.CORDAO) {
                return true
            }
            return false;
        }

        return false;
    }

    private isMedula(): boolean {
        if (!this.tipoDoadorComPrescricao) {
            return false;
        }
        return this.tipoDoadorComPrescricao == TiposDoador.NACIONAL || this.tipoDoadorComPrescricao == TiposDoador.INTERNACIONAL
    }

    private isCordao(): boolean {
        if (!this.tipoDoadorComPrescricao) {
            return false;
        }
        return this.tipoDoadorComPrescricao == TiposDoador.CORDAO_NACIONAL || this.tipoDoadorComPrescricao == TiposDoador.CORDAO_INTERNACIONAL
    }

}
