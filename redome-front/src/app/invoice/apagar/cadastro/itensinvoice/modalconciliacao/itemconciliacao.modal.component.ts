import { Component, OnInit } from "@angular/core";
import { BaseForm } from "app/shared/base.form";
import { TranslateService } from "@ngx-translate/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { MessageBox } from "app/shared/modal/message.box";
import { InvoiceService } from "app/shared/service/invoice.service";
import { IModalComponent } from "app/shared/modal/factory/i.modal.component";
import { IModalContent } from "app/shared/modal/factory/i.modal.content";
import { ItemInvoice } from "app/shared/classes/item.invoice";
import { BuildForm } from "app/shared/buildform/build.form";
import { NumberControl } from "app/shared/buildform/controls/number.control";
import { StringControl } from "app/shared/buildform/controls/string.control";
import { BooleanControl } from "app/shared/buildform/controls/boolean.control";
import { Invoice } from "app/shared/classes/invoice";
import { TipoServico } from "app/shared/dominio/tipo.servico";
import { ErroMensagem } from "app/shared/erromensagem";
import { ErroUtil } from "app/shared/util/erro.util";

@Component({ 
    selector: 'item-conciliacao-modal',
    templateUrl: './itemconciliacao.modal.component.html'
})
export class ItemConcilicacaoModalComponent implements IModalContent, OnInit{
   

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    private formDadosInvoice: BuildForm<any>;

    public labelsIndevido:String[] = ["Sim","Nao"];
    public opcoesIndevido:String[] = ["S","N"];


    constructor(translate: TranslateService, private fb: FormBuilder, 
        private messageBox: MessageBox, private invoiceService:InvoiceService){
        this.formDadosInvoice = new BuildForm<any>()
        .add(new NumberControl('numero', [Validators.required]))
        .add(new NumberControl('valor', [Validators.required]))
        .add(new BooleanControl('indevido', [Validators.required]));
    }

    ngOnInit(): void {
    }
    
    public form(): FormGroup {
        return this.formDadosInvoice.form;
    }
    
    salvarItemInvoice(){
        if(this.formDadosInvoice.valid){
            let item = new ItemInvoice();
            item.valor = this.formDadosInvoice.form.get("valor").value;
            item.numeroDoPagamento = this.formDadosInvoice.form.get("numero").value;
            item.indevido = this.formDadosInvoice.form.get("indevido").value == "S"? true: false;
            item.invoice = this.data.idInvoice;
            item.idPagamento = this.data.idPagamento;
            item.tipoServico = new TipoServico();
            item.tipoServico.id = this.data.idTipoServico;
            item.rmr = this.data.rmr;
            item.idDoadorInternacional = this.data.idDoadorInternacional;
            this.invoiceService.salvarItem(this.data.idInvoice, item).then(res => {
                this.target.hide();
                this.messageBox.alert(res).show();
                this.data.listarConciliados();
                this.data.pesquisar();
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            })
        }
    }
    nomeComponente(): string {
        return "ItemConcilicacaoModalComponent";
    }


    public preencherFormulario(entidade: Object): void {
        throw new Error("Method not implemented.");
    }

    cancelar(){
        this.target.hide();
    }

}