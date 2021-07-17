import { Component, OnInit, Output } from "@angular/core";
import { FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { CategoriaChecklist } from "app/shared/dominio/categoria.checklist";
import { ItensChecklist } from "app/shared/dominio/item.checklist";
import { RespostaChecklist } from "app/shared/dominio/resposta.checklist";
import { TipoChecklist } from "app/shared/dominio/tipochecklist";
import { ChecklistService } from "app/shared/service/cheklist.service";
import { BaseForm } from "../../../../shared/base.form";
import { ErroMensagem } from '../../../../shared/erromensagem';
import { MessageBox } from "../../../../shared/modal/message.box";
import { ArrayUtil } from "../../../../shared/util/array.util";
import { ErroUtil } from "../../../../shared/util/erro.util";

/**
 * Componente para listar e controlar checklist.Este componente serve para o controle do proprio usuario
 * para controle de andamento de tarefas.
 * @author Filipe Paes
 */
@Component({
    selector: 'checklist',
    templateUrl: './checklist.component.html'
})
export class ChecklistComponent extends BaseForm<TipoChecklist> implements OnInit {

    @Output("respostas")
    private _respostas: RespostaChecklist[] = [];

    public idTipoChecklist:number;
    public checkListFormGroup: FormGroup;
    public tipoChecklist:TipoChecklist;
    public categoriasFormArray: FormArray = new FormArray([]);


    public onChangeItem:(respostaItem:RespostaChecklist) => void;


    constructor(private fb: FormBuilder,
        private checklistService: ChecklistService,
        private messageBox: MessageBox) {
        super();
    }

    private exibirMensagemErro(error: ErroMensagem) {
        this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
    }
    /**
     * Inicia o componente buscando o tipo de checklist, suas categorias e itens.
     */
    public async iniciar(respostasParam:RespostaChecklist[]){
        await this.checklistService.obterChecklistPorTipo(this.idTipoChecklist).then(res =>{
            this.tipoChecklist = new TipoChecklist();
            this.tipoChecklist.jsonToEntity(res);

            this.tipoChecklist.categorias.forEach((c, catindex)=>{
                this.categoriasFormArray.setControl(catindex, this.criarCategoriaGroup(c));
            });
        },(error:ErroMensagem)=> {
            this.exibirMensagemErro(error);
        });

        this.carregarRespostas(respostasParam);
    }


    private criarCategoriaGroup(categoria: CategoriaChecklist): FormGroup {
        let itemFormArray: FormArray = new FormArray([]);

        categoria.itens.forEach((item, index)=>{
            itemFormArray.setControl(index, this.criarItemGroup(item, false));
        });

        return this.fb.group({
            nome: [categoria.nome, null],
            id: [categoria.id,  null],
            itens: itemFormArray
        });
    }

    private criarItemGroup(item: ItensChecklist, marcar:boolean): FormGroup {
        return this.fb.group({
            selecionado: [marcar, null],
            id: [item.id,  null],
            nome:[item.nome, null]
        });
    }

    public form(): FormGroup {
        return this.checkListFormGroup;
    }

    private carregarRespostas(respostas:RespostaChecklist[]){
        this.categoriasFormArray.controls.forEach(cat=>{
            let itens = cat.get("itens") as FormArray;

            itens.controls.forEach((item)=>{
                if(this.existeNasRespostas(item.get("id").value, respostas)){
                    item.get('selecionado').setValue(true);
                }
            });
        });
    }
    private existeNasRespostas(itemId:number, respostas:RespostaChecklist[]):boolean{
        let resultado:boolean = false;
        respostas.forEach(r=>{
            if(itemId == r.item.id && r.resposta){
                this._respostas.push(r);
                resultado = true;
            }
        });
        return resultado;
    }

    /**
     * Método que torna dinamico o onclick de cada item checado.
     * Através deste método pode-se ao clicar em cada item fazer
     * um auto save.
     * @param event - evento do click.
     * @param item - item clicado.
     */
    check(event:any, itemId:number){
        if(this.onChangeItem){
            let resposta: RespostaChecklist = new RespostaChecklist();
            resposta.item = new ItensChecklist();
            resposta.item.id = itemId;
            resposta.resposta = event.target.checked;
            if(event.target.checked){
                this._respostas.push(resposta);
            }
            else {
                ArrayUtil.remove(this._respostas, resposta);
            }
            this.onChangeItem(resposta);
        }
    }

    ngOnInit(): void {
        this.checkListFormGroup = this.fb.group({
            categorias:this.categoriasFormArray
        });
    }


    public preencherFormulario(entidade: TipoChecklist): void {
        throw new Error("Method not implemented.");
    }

    public get listaCategoriasForm():FormArray{
        return this.checkListFormGroup.get("categorias") as FormArray;
    }

    public listaItensForm(categoria:FormControl): FormArray{
        return categoria.get("itens") as FormArray;
    }

    /**
     * Retorna as respostas marcadas.
     */
    public get respostas(): RespostaChecklist[]{
        return this._respostas;
    }

    /**
     * Retorna todas perguntas (item check list) carregados para
     * o tipo informado.
     */
    public get perguntas(): ItensChecklist[]{
        let perguntas: ItensChecklist[] = [];
        if(this.tipoChecklist){
            this.tipoChecklist.categorias.forEach(categoria => {
                categoria.itens.forEach(itemCheckList =>{
                    perguntas.push(itemCheckList);
                });
            });
        }
        return perguntas;
    }


    obterMarcacaoQuantidade(categoria: FormControl):string{
        let itens = categoria.get("itens")as FormArray;
        let contador:number = 0;
        itens.controls.forEach(itens=>{
            if(itens.get("selecionado").value == true){
                contador++;
            }
        });
        return "(" + contador + " de " + itens.controls.length + ")";
    }

    nomeComponente(): string {
        throw new Error("Method not implemented.");
    }
}
