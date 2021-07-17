import { Component, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { DataUtil } from '../../util/data.util';
import { DateMoment } from '../../util/date/date.moment';
import { DateTypeFormats } from '../../util/date/date.type.formats';
import { FileItem, FileLikeObject, FileUploader, FileUploaderOptions } from 'ng2-file-upload';
import { BaseForm } from '../../base.form';
import { LocusVO } from '../../../paciente/cadastro/exame/locusvo';
import { DominioService } from '../../dominio/dominio.service';
import { ErroMensagem } from '../../erromensagem';
import { Locus } from '../../../paciente/cadastro/exame/locus';
import { ArrayUtil } from '../../util/array.util';
import { ExameService } from '../../../paciente/cadastro/exame/exame.service';
import { LocusExamePk } from '../../../paciente/cadastro/exame/locusexamepk';
import { MessageBox } from '../../modal/message.box';
import { LocusExame } from '../../../paciente/cadastro/exame/locusexame';

/**
 * Component reusável para o HLA do paciente ou doador.
 * @author Pizão
 * @export
 * @class HlaComponent
 * @extends {BaseForm}
 * @implements {OnDestroy}
 */
@Component({
    selector: 'locus-component',
    moduleId: module.id,
    templateUrl: './locus.component.html'
})
export class LocusComponent extends BaseForm<Locus[]> implements OnInit, OnDestroy {
    private _locus: Locus[] = [];
    private _locusObrigatorios: string[] = [];
    public listLocus: FormArray = new FormArray([]);
    public formGroup: FormGroup;
    // Indica que deve haver ao menos 1 lócus selecionado.
    public required: boolean = true;
    private locusObrigatorioMensagemErro: string;
    

    constructor(private fb: FormBuilder, translate: TranslateService,
                private dominioService: DominioService, private exameService: ExameService,
                private messageBox: MessageBox) {
        super();
        this.translate = translate;

        translate.get("pacienteForm.exameGroup.locusNaoInformados").subscribe(msg =>{
            this.locusObrigatorioMensagemErro = msg;
        });

        this.formGroup = this.fb.group({
            'listLocus': this.listLocus,
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
    private buildForm(loci: Locus[]){
        loci.forEach(locus => {
            if(locus.codigo){
                this.buildControl(locus.codigo);
            }
        });
    }

    /**
     * Cria e inclui dinamicamente um novo formControl para o 
     * lócus informado no parâmetro.
     * 
     * @param codigoLocus código de referência para o novo 
     * control a ser "construído" no formulário.
     */
    private buildControl(codigoLocus: string): void{
        let locusControl: FormGroup = this.obterLocusPorCodigo(codigoLocus);
        if(locusControl != null){
            this.formArray.removeAt(this.formArray.controls.indexOf(locusControl));
        }
        this.adicionarLocus(codigoLocus);
    }

    /**
     * Obtém o formControl que possui o código informado no parametro.
     * 
     * @param codigo código do lócus a ser procurado entre os 
     * formControls já adicionados.
     */
    private obterLocusPorCodigo(codigo: string): FormGroup{
        let locusEncontrados: AbstractControl[] = this.formArray.controls.filter(locusForm => {
            let control: AbstractControl = locusForm.get('locus');
            if(control && control.value == codigo){
                return control;
            }
        });
        return <FormGroup> (ArrayUtil.isNotEmpty(locusEncontrados) ? locusEncontrados[0] : null);
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
     * Torna o lócus informado obrigatório para a esteira de seleção.
     * 
     * @param codigoLocus código do lócus que se tornará obrigatório.
     */
    set locusObrigatorio(codigoLocus: string){
        this.adicionarLocusComoObrigatorio(new Locus(codigoLocus));
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
     * Retorna o array de itens de formulário contendo
     * todos os itens criados para cada lócus selecionado.
     * 
     * @return o componente que armazena a lista de 
     * contidos na esteira.
     */
    get formArray(): FormArray{
        return this.listLocus;
    }

    /**
     * Retorna o array dos itens que estão na esteira de seleção.
     * 
     * @return lista com os componentes contidos na 
     * esteira de seleção.
     */
    get controls(): AbstractControl[]{
        return this.formArray ? this.formArray.controls : [];
    }

    /**
     * Adiciona um novo lócus a esteira de seleção com o código informado.
     * 
     * @param codigoLocusSelecionado código do lócus selecionado.
     */
    adicionarLocus(codigoLocusSelecionado: string) { 
         let control = this.fb.group({
            'locus': [codigoLocusSelecionado, Validators.required]
        });
        this.formArray.push(control);
    }

    /**
     * Lista os lócus que foram carregados da base, mas ainda
     * não foram selecionados na esteira.
     * 
     * @return lista de códigos de lócus.
     */
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

    /**
     * Obtém o código do lócus do componente informado.
     * 
     * @param control componente a ser extraído o código.
     */
    obterCodigoLocus(control: FormControl) {
        return control.get('locus').value;
    }

    /**
     * Remove o lócus na posição da esteira (INDEX) informada.
     * 
     * @param index posição da esteira.
     */
    removerLocus(index: number): void {
        let codigoLocus: string = 
            this.obterCodigoLocus(<FormControl> this.formArray.controls[index]);
        this.formArray.removeAt(index);
    }

    /**
     * Retorna a lista de lócus selecionados na esteira.
     * 
     * @return array de entidades Locus com os códigos preenchidos.
     */
    get values(): Locus[] {
        let esteiraLocus: Locus[] = [];

        this.formArray.controls.forEach(locusForm =>{
            let locus: Locus = new Locus();
            locus.codigo = locusForm.get('locus').value;
            esteiraLocus.push(locus);
        });
        
        return esteiraLocus;
    }

    /**
     * Seta os lócus que devem ser criados e adicionados
     * a esteira.
     * 
     * @param locus lista de entidades Locus com os códigos preenchidos.
     */
    set values(locus: Locus[]) {
        this.buildForm(locus);
    }

    /**
     * Seta um novo lócus que devem ser criado e adicionado
     * a esteira.
     * 
     * @param locus entidade lócus com o código preenchido.
     */
    set value(locus: Locus) {
        this.buildControl(locus.codigo);
    }

    /**
     * Retorna a referência para o item de formulário deste componente.
     * 
     * @extends BaseForm
     * @return formulário que contém a esteira e todos seus elementos.
     */
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

    public preencherFormulario(entidade: Locus[]): void {
        throw new Error("ERROR! Método para preencher formulário foi chamado para este componente, mas não está implementado.");
    }

    /**
     * Para os casos em que o preenchimento do HLA é 
     * obrigatório e ele não foi preenchido.
     * 
     * @return TRUE se não estiver preenchido e for obrigatório, FALSE caso contrário.
     */
    private get isNaoPreenchido(): boolean {
        if(this.required || ArrayUtil.isNotEmpty(this._locusObrigatorios)){
            return this.formArray.touched 
                        && (this.formArray.length == 0 || this.formArray.invalid);
        }
        return false;
    }

    public validateForm(): boolean{
        this.formArray.markAsTouched();
        return super.validateForm() && !this.isNaoPreenchido;
    }

    /**
     * Retorna a mensagem de acordo com o erro que esteja ocorrendo
     * no contexto do componente.
     * @return a mensagem referente ao erro que ocorreu.
     */
    public retornarMensagemErro(): string{
        if(this.isNaoPreenchido){
            return this.locusObrigatorioMensagemErro;
        }
        return null;
    }
}