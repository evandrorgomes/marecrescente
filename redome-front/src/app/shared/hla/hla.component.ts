import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ExameService } from '../../paciente/cadastro/exame/exame.service';
import { Locus } from '../../paciente/cadastro/exame/locus';
import { LocusExame } from '../../paciente/cadastro/exame/locusexame';
import { LocusExamePk } from '../../paciente/cadastro/exame/locusexamepk';
import { LocusVO } from '../../paciente/cadastro/exame/locusvo';
import { BaseForm } from '../base.form';
import { DominioService } from '../dominio/dominio.service';
import { ErroMensagem } from '../erromensagem';
import { MessageBox } from '../modal/message.box';
import { ArrayUtil } from '../util/array.util';
import { StringUtil } from '../util/string.util';
import { TranslateUtil } from '../util/translate.util';

/**
 * Component reusável para o HLA do paciente ou doador.
 * @author Pizão
 * @export
 * @class HlaComponent
 * @extends {BaseForm}
 * @implements {OnDestroy}
 */
@Component({
    selector: 'hla-component',
    moduleId: module.id,
    templateUrl: './hla.component.html'
})
export class HlaComponent extends BaseForm<LocusVO[]> implements OnInit, OnDestroy {
    private _locus: Locus[] = [];
    private _locusObrigatorios: string[] = [];
    public listHLA: FormArray = new FormArray([]);
    public formGroup: FormGroup;
    public mensagensLocusInvalidos: Map<string, string> = new Map<string, string>();
    // Indica se deve haver ao menos 1 lócus preenchido.
    public required: boolean = true;
    public hlaInvalidoMensagemErroPlural: string;
    hlaInvalidoMensagemErroSingular: string;
    public hlaObrigatorioMensagemErro: string;
    public letraE: string;
    public ambosAlelosBlank: string;

    public readOnly: boolean = false;

    @Input("comAntigeno")
    public comAntigeno: boolean = false;


    constructor(private fb: FormBuilder, translate: TranslateService,
                private dominioService: DominioService,
                private exameService: ExameService,
                private messageBox: MessageBox) {
        super();
        this.translate = translate;

        translate.get("pacienteForm.exameGroup").subscribe(mensagens =>{
            this.hlaInvalidoMensagemErroSingular = mensagens.hlaInvalidoSingular;
            this.hlaInvalidoMensagemErroPlural = mensagens.hlaInvalidoPlural;
            this.hlaObrigatorioMensagemErro = mensagens.hlaNaoPreenchido;
            this.letraE = " " + mensagens.e + " ";
            this.ambosAlelosBlank = mensagens.ambosAlelosBlank;
        });

        this.formGroup = this.fb.group({
            'listHLA': this.listHLA,
        });
    }

    ngOnInit() {
        this.dominioService.listarLocus().then(res => {
            this._locus = res;
        }, (error: ErroMensagem) => {
            this.messageBox.alert(error.mensagem.toString()).show();
        });
    }

    ngOnDestroy(): void {}


    get locus(): Locus[]{
        return this._locus;
    }

    get locusObrigatorios(): string[]{
        return this._locusObrigatorios;
    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações
     * @author Pizão
     */
    private buildForm(hla: LocusExame[]){
        hla.forEach(locus => {
            if(locus.id){
                this.buildControl(locus.id.locus.codigo, locus.primeiroAlelo, locus.segundoAlelo);
            }
        });
    }

    private buildControl(codigoLocus: string, primeiroAlelo?: string, segundoAlelo?: string): void{
        let locusControl: FormGroup = this.obterLocusPorCodigo(codigoLocus);
        let locusJaInserido: boolean = locusControl != null;
        let indexLocus: number = -1;
        if(locusJaInserido){
            indexLocus = this.formArray.controls.indexOf(locusControl);
            this.formArray.removeAt(indexLocus);
        }
        let control: AbstractControl =
            this.fb.group({
                'locus': [{value: codigoLocus, disabled: this.readOnly}, Validators.required],
                'alelo1': [{value: primeiroAlelo, disabled: this.readOnly}, Validators.required],
                'alelo2': [{value: segundoAlelo, disabled: this.readOnly}, Validators.required]
            });

        if(locusJaInserido){
            this.formArray.insert(indexLocus, control);
        }
        else {
            this.formArray.push(control);
        }
    }

    private obterLocusPorCodigo(codigo: string): FormGroup{
        let locusEncontrados: AbstractControl[] = this.formArray.controls.filter(locusForm => {
            let control: AbstractControl = locusForm.get('locus');
            if(control && control.value == codigo){
                return control;
            }
        });
        return <FormGroup> (ArrayUtil.isNotEmpty(locusEncontrados) ? locusEncontrados[0] : null);
    }


    get formArray(): FormArray{
        return this.listHLA;
    }

    get controls(): AbstractControl[]{
        return this.formArray ? this.formArray.controls : [];
    }

    addLocus(locusSelecionado: AbstractControl) {
         let control = this.fb.group({
            'locus': [locusSelecionado.value, Validators.required],
            'alelo1': [null, Validators.required],
            'alelo2': [null, Validators.required]
        });
        this.formArray.push(control);
    }

    listarLocusNaoSelecionados(): string[] {
        if(!this.formArray || !this.formArray.controls){
            return this._locus.map(loci => {return loci.codigo});
        }
        let locusRetorno: string[] = this._locus.map(locus => {
            let locusEncontrado = this.formArray.controls.find(locusForm => {
                if(locusForm instanceof FormGroup){
                    let formControlLocus = locusForm.get('locus');
                    return formControlLocus.value == locus.codigo;
                }
                return false;
            });

            if (!locusEncontrado) {
                return locus.codigo;
            }
            return null;
        }).filter(codigoLocus => { return codigoLocus != null });
        return locusRetorno;
    }

    obterCodigoLocus(control: FormControl) {
        return control.get('locus').value;
    }

    public get hlaInvalido(){
        return this.mensagensLocusInvalidos.size > 0;
    }

    /**
     * Valida o primeiro alelo de qualquer locus.
     * Método separado para controle das mensagens de acordo
     * com a posição do alelo.
     *
     * @param codigo código do locus
     * @param valorAlelo valor do alelo
     */
    public validarPrimeiroAlelo(codigo : string, valorAlelo: string): void{
        this.validarHLA(codigo, valorAlelo, 1);
    }

    /**
     * Valida o segundo alelo de qualquer locus.
     * Método separado para controle das mensagens de acordo
     * com a posição do alelo.
     *
     * @param codigo código do locus
     * @param valorAlelo valor do alelo
     */
    public validarSegundoAlelo(codigo : string, valorAlelo: string): void{
        this.validarHLA(codigo, valorAlelo, 2);
    }

    /**
     * Método para validar se os alelos são válidos.
     * @param codigo locus a ser validado
     * @param valor valor do locus a ser validado
     * @param posicao posição que o valor faz referência.
     */
    validarHLA(codigo : string, valorAlelo: string, posicao: number) {
        if(StringUtil.isNullOrEmpty(valorAlelo)){
            this.removerLocusInvalido(codigo, posicao);
        }
        else {
            if(this.comAntigeno){
                this.exameService.validarHLAPorLocusComAntigeno(codigo, valorAlelo,
                    result => {
                        this.removerLocusInvalido(codigo, posicao);
                    }, error => {
                        this.marcarLocusInvalido(
                            codigo, posicao, error.listaCampoMensagem[0].mensagem);
                    }
                );
            }
            else{
                this.exameService.validarHLAPorLocus(codigo, valorAlelo,
                    result => {
                        this.removerLocusInvalido(codigo, posicao);
                    }, error => {
                        this.marcarLocusInvalido(
                            codigo, posicao, error.listaCampoMensagem[0].mensagem);
                    }
                );
            }
        }

        this.validateForm();
    }

    /**
     * Valida se ambos os alelos (1 e 2) foram preenchidos como blank.
     *
     * @param codigo código do lócus.
     * @return TRUE se ambos estiverem como blank.
     */
    private validarAmbosAlelosBlank(): boolean{
        let hasSomenteBlank: boolean = false;

        this.formArray.controls.forEach(locusForm =>{
            let primeiroAlelo: string = locusForm.get("alelo1").value;
            let segundoAlelo: string = locusForm.get("alelo2").value;

            if(primeiroAlelo == LocusExame.LOCUS_BLANK
                        && segundoAlelo == LocusExame.LOCUS_BLANK){
                hasSomenteBlank = true;
            }
        });

        return hasSomenteBlank;
    }

    /**
     * Marca o formcontrol referente ao lócus informado como inválido.
     *
     * @param codigo código do locus.
     * @param posicao posição do alelo (primeiro ou segunda).
     * @param mensagemErro mensagem de erro que retornou do back-end.
     */
    private marcarLocusInvalido(codigo : string, posicao: number, mensagemErro: string): void{
        this.mensagensLocusInvalidos.set(codigo + " (" + posicao + ")", mensagemErro);
    }

    /**
     * Remove a marcação de inválido do formcontrol.
     *
     * @param codigo código do locus.
     * @param posicao posição do alelo.
     */
    private removerLocusInvalido(codigo : string, posicao: number): void{
        this.mensagensLocusInvalidos.delete(codigo + " (" + posicao + ")");
    }

    /**
     * Remove todas as posições do lócus que estava inválido.
     *
     * @param codigo código do locus.
     */
    private removerTodasPosicoesLocus(codigo : string): void{
        this.mensagensLocusInvalidos.delete(codigo + " (1)");
        this.mensagensLocusInvalidos.delete(codigo + " (2)");
    }

    /**
     * Remove locus na posição INDEX informada.
     */
    public removerLocus(index) {
        let codigoLocus: string =
            this.obterCodigoLocus(<FormControl> this.formArray.controls[index]);
        this.removerTodasPosicoesLocus(codigoLocus);
        this.formArray.removeAt(index);
    }

    /**
     * Método para popular o objeto de exame e devolver para o
     * componente de cadastro este item pronto para ser enviado
     * @author Filipe Paes
     * @readonly
     * @type {Exame}@memberof ExameComponent
     */
    getValue(): LocusExame[] {
        let hla: LocusExame[] = [];

        this.formArray.controls.forEach(locusForm =>{
            let locusExame: LocusExame = new LocusExame();
            locusExame.id = new LocusExamePk();
            locusExame.id.locus = new Locus();
            locusExame.id.locus.codigo = locusForm.get('locus').value;
            locusExame.primeiroAlelo = locusForm.get('alelo1').value;
            locusExame.segundoAlelo = locusForm.get('alelo2').value;
            hla.push(locusExame);
        });

        return hla;
    }

    public setValue(hla: LocusExame[]) {
        this.buildForm(hla);
    }

    public form(): FormGroup{
        return this.formGroup;
    }

    /**
     * Verifica se o loci associado ao campo (control), passado
     * como parâmetro, está na lista de lócis obrigatórios para preenchimento.
     *
     * @param control campo da lista de locis.
     * @return TRUE se estiver entre os obrigatórios.
     */
    public verificarLocusObrigatorio(control: FormControl): boolean{
        if(ArrayUtil.contains(this._locusObrigatorios, this.obterCodigoLocus(control))){
            return true;
        }
        return false;
    }

    nomeComponente(): string {
        throw new Error("ERROR! Nome do componente foi solicitado para este componente.");
    }

    public preencherFormulario(entidade: LocusVO[]): void {
        throw new Error("ERROR! Método para preencher formulário foi chamado para este componente, mas não está implementado.");
    }

    /**
     * Para os casos em que o preenchimento do HLA é
     * obrigatório e ele não foi preenchido.
     * @return TRUE se não estiver preenchido e for obrigatório, FALSE caso contrário.
     */
    private get isNaoPreenchido(): boolean {
        if(this.required){
            return this.formArray.touched
                        && (this.formArray.length == 0 || this.formArray.invalid);
        }
        return false;
    }

    public validateForm(): boolean{
        this.formArray.markAsTouched();
        return super.validateForm();
    }

    /**
     * Torna o lócus informado obrigatório para a esteira de seleção.
     *
     * @param codigoLocus código do lócus que se tornará obrigatório.
     */
    set locusObrigatorio(codigoLocus: string){
        this.adicionarLocusComoObrigatorio(new Locus(codigoLocus));
    }

    /**
     * Adiciona um novo lócus como obrigatório.
     * Caso ele já tenha sido selecionado na esteira, apenas o torna obrigatório
     * caso ainda não tenha sido, força a seleção e o seu preenchimento passa a ser
     * obrigatório.
     *
     * @param locus entidade lócus que contém o código a ser associado.
     */
    private adicionarLocusComoObrigatorio(locus: Locus): void{
        if(ArrayUtil.isEmpty(this._locusObrigatorios)){
            this._locusObrigatorios = [];
            this._locusObrigatorios.push(locus.codigo);
        }
        else if(!ArrayUtil.contains(this._locusObrigatorios, locus.codigo)) {
            this._locusObrigatorios.push(locus.codigo);
        }
        if(this.obterLocusPorCodigo(locus.codigo) == null){
            this.buildControl(locus.codigo);
        }
    }

    /**
     * Seta quais são os lócus que, obrigatoriamente,
     * devem ser preenchidos.
     *
     * @param codigosLocus lista de códigos de lócus a ser adicionados.
     */
    set locusObrigatorios(codigosLocus: string[]){
        if(ArrayUtil.isNotEmpty(codigosLocus)){
            codigosLocus.forEach(locus => {
                this.locusObrigatorio = locus;
            });
        }
    }

    /**
     * Retorna a mensagem de acordo com o erro que esteja ocorrendo
     * no contexto do componente.
     * @return a mensagem referente ao erro que ocorreu.
     */
    public retornarMensagemErro(): string{
        if(this.hlaInvalido){
            let codigosArray: string[] = Array.from(this.mensagensLocusInvalidos.keys());
            let codidosInvalidos: string = ArrayUtil.join(codigosArray, ", ", this.letraE);
            let mensagemErro: string =
                codigosArray.length == 1 ? this.hlaInvalidoMensagemErroSingular : this.hlaInvalidoMensagemErroPlural;
            return TranslateUtil.getMessage(mensagemErro, codidosInvalidos);
        }
        else if(this.isNaoPreenchido){
            return this.hlaObrigatorioMensagemErro;
        }
        else {
            if(this.validarAmbosAlelosBlank()){
                return this.ambosAlelosBlank;
            }
        }
        return null;
    }
}
