import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, forwardRef, Input, OnInit, ViewChild, Output } from "@angular/core";
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { AutenticacaoService } from 'app/shared/autenticacao/autenticacao.service';
import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { FuncaoTransplante } from '../../dominio/funca.transplante';
import { UsuarioLogado } from '../../dominio/usuario.logado';
import { FuncaoCentroTransplante } from '../../enums/funcao.centro.transplante';
import { Perfis } from '../../enums/perfis';
import { ArrayUtil } from '../../util/array.util';
import { centroTransplanteService } from "app/export.mock.spec";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => SelectCentrosComponent),
    multi: true
};

@Component({
    selector: 'select-centros-component',
    templateUrl: './select.centros.component.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class SelectCentrosComponent implements ControlValueAccessor, OnInit, AfterViewInit {
    // ID attribute for the field and for attribute for the label
    @Input()  idd = "";

    @Input() showBlankOption: boolean = false;

    @Input() showMeusPacientesOption: boolean = false;

    @Input() showTransplantadorOption: boolean = true;

    @Input() showAvaliadorOption: boolean = false;

    @Input() showColetaOption: boolean = true;

    @Input() forcarExibicao: boolean = false;

    @ViewChild('select')
    private comboBoxRef: ElementRef;

    private _options: CentroTransplante[] = [];
    private perfis: string[] = [];
    public funcoesAgrupadoras: FuncaoAgrupadora[] = [];

    // valor selecionado, quando a combo tem apenas um item.
    public _itemSelecionado: string;
    public _jsonSelecionado= {
        "id": 0,
        "tipo": 0
    };

    constructor(private autenticacaoService: AutenticacaoService,
                private changeDectectorRef: ChangeDetectorRef) {
        let usuarioLogado: UsuarioLogado = this.autenticacaoService.usuarioLogado();
        
        this._options = usuarioLogado.centros;
        this.perfis = usuarioLogado.perfis;
        this.separarAgrupadores();
    }

    ngOnInit() {
        this.carregarItemPreSelecionado();
    }

    /**
     * @description De acordo com a configuração do componente, pré seleciona a combo
     * no item correto que deve ser exibido.
     * Caso só tenha um item selecionável, este deve ser pré selecionado.
     * @author Pizão
     * @private
     */
    private carregarItemPreSelecionado(): void{
        if ((!this.showBlankOption && !this.showMeusPacientesOption)
                || this.possuirApenasUmaOpcaoSelecao()) {

            let tipoFuncao: number = 0;
            if(this.showTransplantadorOption){
                tipoFuncao = FuncaoCentroTransplante.TRANSPLANTADOR;
            }
            if(this.showAvaliadorOption){
                tipoFuncao = FuncaoCentroTransplante.AVALIADOR;
            }
            if(this.showColetaOption){
                tipoFuncao = FuncaoCentroTransplante.COLETA;
            }
            if(this._options.length != 0){
                let index: number = this._options[0].funcoes.findIndex(tipo => tipo.id === tipoFuncao);
                this._itemSelecionado = this._options[0].id + '_' + this._options[0].funcoes[index].id;
                this.converterValueJson();
            }else{
                this._itemSelecionado = null;
            }
        }
        else if (this.showMeusPacientesOption) {
            this._itemSelecionado = '-99_0';
            this.converterValueJson();
        }
        else {
            this._itemSelecionado = null;
        }
    }

    ngAfterViewInit(){
        setTimeout(() => {
            this.propagateChange(this._jsonSelecionado);
        });
        this.changeDectectorRef.detectChanges();
    }

    get value(): any {
        return this._jsonSelecionado;
    };

    set value(v: any) {
        if (v) {
            this._jsonSelecionado = v;
            this._itemSelecionado = v.id + '_' + v.tipo;
        }
        else {
            this._jsonSelecionado = null;
            this._itemSelecionado = null;
        }
        this.propagateChange(this._jsonSelecionado);
    }

    writeValue(value: any) { }

    registerOnChange(fn: any) {
        this.propagateChange = fn;
    }

    registerOnTouched(fn: any) {}

    propagateChange = (_: any) => { }

    /**
     * @description Quando um novo item é selecionado na combobox.
     * @author Pizão
     * @param {Event} e
     * @param {*} value
     */
    onChange(value:any) {
        this.converterValueJson();
        this.propagateChange(this._jsonSelecionado);
    }

    public getClazz(): string {
        let clazz = "";
         if (this.comboBoxRef) {
            clazz = this.comboBoxRef.nativeElement.parentElement.className;
        }
        return "form-control " + clazz;
    }

    /**
     * Getter options
     * @return {CentroTransplante[] }
     */
	public get options(): CentroTransplante[]  {
		return this._options;
    }

    /**
     * @description Verifica se a combo deve ser escondida ou não.
     * Caso tenha apenas um item, ela será escondida e pré-selecionada
     * com o único valor possível.
     * @author Pizão
     * @returns {boolean} TRUE, caso deva esconder.
     */
    public exibirCombo(): boolean {
        return !this.possuirApenasUmaOpcaoSelecao() || this.forcarExibicao;
    }

    /**
     * @description Verifica se a lista de itens selecionáveis
     * possui mais de um item selecionável.
     * @author Pizão
     * @private
     * @returns {boolean}
     */
    private possuirApenasUmaOpcaoSelecao(): boolean{
        let possuiDoisOuMaisCentros = this._options && this._options.length > 1;
        if (possuiDoisOuMaisCentros) {
            return (!possuiDoisOuMaisCentros && !this.showMeusPacientesOption);
        }
        let possuiApenasUmCentro = this._options && this._options.length == 1;
        let possuiApenasUmaFuncao = this._options && this._options[0].funcoes.length == 1;
        return (possuiApenasUmCentro && possuiApenasUmaFuncao && !this.showMeusPacientesOption);
    }

    /**
     * @description Separa todos as funcoes dos centros avaliadores e perfis,
     * associados para o usuário logado (sem repetição), para que seja possível
     * montar a "árvore" de itens agrupados por Avaliador ou Transplante.
     * @author Pizão
     * @private
     */
    private separarAgrupadores(): void{
        if(ArrayUtil.isNotEmpty(this._options)){
            this._options.forEach(centroTransplante => {
                centroTransplante.funcoes.forEach(funcao => {
                  //  if(this.verificarSeUsuarioTemPermissao(funcao)){
                        let agrupador: FuncaoAgrupadora = ArrayUtil.get(this.funcoesAgrupadoras, "nomeAgrupador", funcao.descricao);
                        if(agrupador == null){
                            agrupador = new FuncaoAgrupadora();
                            this.funcoesAgrupadoras.push(agrupador);
                                agrupador.idAgrupador = funcao.id;
                                agrupador.nomeAgrupador = funcao.descricao;
                        }
                        agrupador.centrosTransplante.push(centroTransplante);
                    //}
                });
            });
        }
    }

    /**
     * @description Verifica se o usuário, por sua lista de perfis, possui
     * permissão para função definida para o centro de transplante.
     * @author Pizão
     * @private
     * @param {FuncaoTransplante} funcao
     * @returns {boolean} TRUE, caso tenha permissão.
     */
    private verificarSeUsuarioTemPermissao(funcao: FuncaoTransplante): boolean{
        switch (funcao.id) {
            case FuncaoCentroTransplante.TRANSPLANTADOR || FuncaoCentroTransplante.COLETA:
                return ArrayUtil.contains(this.perfis, Perfis.MEDICO_TRANSPLANTADOR.nome)
                       || ArrayUtil.contains(this.perfis, Perfis.MEDICO_CENTRO_COLETA.nome);
            case FuncaoCentroTransplante.AVALIADOR:
                return ArrayUtil.contains(this.perfis, Perfis.AVALIADOR);
            // case FuncaoCentroTransplante.COLETA:
            //     return ArrayUtil.contains(this.perfis, Perfis.MEDICO_CENTRO_COLETA.nome);
            default:
                throw new Error("Função " + funcao.id + " é desconhecida.");
        }
    }

    public converterValueJson() {
        if(this._itemSelecionado != null && this._itemSelecionado != '-99_0'){
            //let strItem = ""+this._itemSelecionado;
            this._jsonSelecionado.id = Number.parseInt(this._itemSelecionado.slice(0, this._itemSelecionado.indexOf('_')));
            this._jsonSelecionado.tipo = Number.parseInt(this._itemSelecionado.slice(this._itemSelecionado.lastIndexOf('_') + 1, this._itemSelecionado.length));
        }else if(this._itemSelecionado != null) {
            this._jsonSelecionado.id = -99;
            this._jsonSelecionado.tipo = 0;
        }
    }

    public obterAgruapador(id): FuncaoAgrupadora {
        const index = this.funcoesAgrupadoras.findIndex(agrupador => agrupador.idAgrupador === id);
        return index !== -1 ? this.funcoesAgrupadoras[index]: null;
    }

}

/**
 * @description Classe para facilitar a montagem da combo
 * com os dados agrupados.
 * @author Pizão
 * @class FuncaoAgrupadora
 */
class FuncaoAgrupadora {
    public idAgrupador: number;
    public nomeAgrupador: string;
    public centrosTransplante: CentroTransplante[] = [];
}



