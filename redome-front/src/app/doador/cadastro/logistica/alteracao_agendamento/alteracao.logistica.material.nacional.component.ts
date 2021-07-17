import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { BaseForm } from "app/shared/base.form";
import { IModalComponent } from "app/shared/modal/factory/i.modal.component";
import { IModalContent } from "app/shared/modal/factory/i.modal.content";
import { MessageBox } from "app/shared/modal/message.box";
import { UploadArquivoComponent } from "app/shared/upload/upload.arquivo.component";
import { DataUtil } from "app/shared/util/data.util";
import { DateTypeFormats } from "app/shared/util/date/date.type.formats";
import { MascaraUtil } from "app/shared/util/mascara.util";
import { PedidoTransporteService } from "app/transportadora/pedido.transporte.service";
import { Observable } from "rxjs";
import { forkJoin } from "rxjs/observable/forkJoin";
import { AlteracaoLogisticaMaterialNacionalDTO } from "./alteracao.logistica.material.nacional.dto";


@Component({
    selector: 'alteracao-logistica-material-nacional',
    templateUrl: './alteracao.logistica.material.nacional.compnent.html'
})

export class AlteracaoLogisticaMaterialNacionalComponent extends BaseForm<AlteracaoLogisticaMaterialNacionalDTO> implements IModalContent,OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    modoAlterarData:boolean = false;
    modoAlterarArquivo:boolean = false;

    @ViewChild("uploadComponent")
    private uploadComponent: UploadArquivoComponent;

    public mascaraData: Array<string | RegExp>;
    public popupLogisticaMaterialGroup: FormGroup;

    _mascaraData = MascaraUtil.data;

    idPedidoTransporte:number;
    rmr: number;
    horaPrevistaRetirada: string;


    constructor(private fb: FormBuilder, private router: Router, translate: TranslateService,
        private messageBox: MessageBox,
        private pedidoTransporteService: PedidoTransporteService) {
        super();
        this.translate = translate;
      this.popupLogisticaMaterialGroup = this.fb.group({'tipoAlteracaoData':  [null]
      ,'tipoAlteracaoDocumento':[null]
      , 'dataAlteracao':[null]
      , 'descricao':[null] });
    }


    public form(): FormGroup {
        return this.popupLogisticaMaterialGroup;
    }
    public preencherFormulario(entidade: AlteracaoLogisticaMaterialNacionalDTO): void {
        throw new Error("Method not implemented.");
    }
    nomeComponente(): string {
        return "AlteracaoLogisticaMaterialNacionalComponent";
    }
    ngOnInit(): void {
        this.uploadComponent.extensoes = "extensaoArquivoPedidoTransporte";
        this.uploadComponent.tamanhoLimite = "tamanhoArquivoAlteracaoLogisticaEmByte";
        this.uploadComponent.quantidadeMaximaArquivos = "quantidadeMaximaArquivosAlteracaoLogistica";
        this.idPedidoTransporte = this.data.idPedidoTransporte;
        this.rmr = this.data.rmr;
    }
    modoAlterarTelaData(event){
        this.modoAlterarData = event.target.checked;
    }

    modoAlterarTelaArquivo(event){
        this.modoAlterarArquivo = event.target.checked;
    }
    validar():boolean{
        let resultado: boolean = true;
        let dataColeta: string = this.popupLogisticaMaterialGroup.controls["dataAlteracao"].value;
        let descricaoColeta: string = this.popupLogisticaMaterialGroup.controls["descricao"].value;

        if(!this.modoAlterarData && !this.modoAlterarArquivo){
            this.translate.get("logisticaMaterial.mensagens.tipo").subscribe(res => {
                this.formErrors["tipo"] = res;
                resultado = false;
            });
        }else{
            if(this.modoAlterarData && (!dataColeta || dataColeta.trim() == '')){
                this.translate.get("logisticaMaterial.mensagens.data_tipo_data").subscribe(res => {
                    this.formErrors["data_tipo_data"] = res;
                    resultado = false;
                });
            }

            if(this.modoAlterarArquivo && (!descricaoColeta || descricaoColeta.trim() == '')){
                this.translate.get("logisticaMaterial.mensagens.descricao_tipo_arquivo").subscribe(res => {
                    this.formErrors["descricao_tipo_arquivo"] = res;
                    resultado = false;
                });
            }

            if(this.modoAlterarArquivo && !this.uploadComponent.validateForm()){
                resultado = false;
            }
        }
        return resultado;
    }
    public altararWorkup(): void{
        if(this.validar()){
            this.executarMultiplasChamadasAoBack().subscribe(res=>{
                let mensagem = '';
                res.forEach(r=>{
                    if(mensagem != ''){
                        mensagem += '<br/>';
                    }
                    mensagem+= r;
                });
                this.messageBox.alert(mensagem)
                .withCloseOption((target: any) => {
                   this.router.navigateByUrl('/doadores/material/logistica');
                })
                .show();
                this.target.hide();
            })
        }
    }

    public executarMultiplasChamadasAoBack(): Observable<any[]> {
        let data: string = this.popupLogisticaMaterialGroup.controls["dataAlteracao"].value;
        let hora:string = this.horaPrevistaRetirada;
        let dataPrevistaRetirada = DataUtil.toDate(data + hora  , DateTypeFormats.DATE_TIME);

        let descricaoAlteracao: string = this.popupLogisticaMaterialGroup.controls["descricao"].value;
        let chamadas = [];

        chamadas.push(this.pedidoTransporteService.atualizarInformacoesTransporteAereo(this.idPedidoTransporte, this.uploadComponent.arquivos, descricaoAlteracao, dataPrevistaRetirada));

        return forkJoin(chamadas);
    }
}
