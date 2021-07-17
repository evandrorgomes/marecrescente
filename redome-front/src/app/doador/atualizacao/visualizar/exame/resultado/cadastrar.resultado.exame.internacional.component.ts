import { TiposDoador } from './../../../../../shared/enums/tipos.doador';
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { DoadorService } from 'app/doador/doador.service';
import { ExameCordaoInternacional } from "app/doador/exame.cordao.internacional";
import { HeaderDoadorInternacionalComponent } from "../../../../../doadorinternacional/identificacao/header.doador.internacional.component";
import { ErroMensagem } from "../../../../../shared/erromensagem";
import { HistoricoNavegacao } from "../../../../../shared/historico.navegacao";
import { HlaComponent } from "../../../../../shared/hla/hla.component";
import { MessageBox } from "../../../../../shared/modal/message.box";
import { PermissaoRotaComponente } from "../../../../../shared/permissao.rota.componente";
import { TarefaService } from "../../../../../shared/tarefa.service";
import { ErroUtil } from "../../../../../shared/util/erro.util";
import { RouterUtil } from "../../../../../shared/util/router.util";
import { InativacaoComponent } from "../../../../inativacao/inativacao.component";
import { SolicitacaoService } from "../../../../solicitacao/solicitacao.service";
import { CordaoInternacional } from './../../../../cordao.internacional';
import { ExameDoadorInternacional } from './../../../../exame.doador.internacional';

@Component({
    selector: "cadastro-resultado-exame-internacional",
    templateUrl: "./cadastro.resultado.exame.internacional.component.html"
})
export class CadastroResultadoExameInternacionalComponent implements PermissaoRotaComponente, OnInit {

    @ViewChild("hlaComponent")
    public hlaComponent:HlaComponent;

    @ViewChild("headdoadorinternacional")
    public headerDoadorInternacional:HeaderDoadorInternacionalComponent;

    
    @ViewChild("inativacaoDoador")
    public inativacaoDoador:InativacaoComponent;


    private _idDoador:number;

    constructor(private fb: FormBuilder, private translate: TranslateService,
        private activatedRouter: ActivatedRoute, 
        private router: Router, private messageBox: MessageBox,
        private solicitacaoService: SolicitacaoService,
        private doadorService:DoadorService,
        private tarefaService: TarefaService) {
    }

   
    public voltarSemDesatribuir(target:any) {
        target.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }
    ngOnInit(): void {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'id').then(res => {
            this._idDoador = <number>res;
            Promise.resolve(this.headerDoadorInternacional).then(() => {
                this.headerDoadorInternacional.popularCabecalho(this._idDoador);
            });
    
        }).catch((error: ErroMensagem) => {
            this.exibirMensagemErro(error); 
        });
    }
    private exibirMensagemErro(error: ErroMensagem) {
        let msg: string = "";
        if (error.mensagem) {
            msg = error.mensagem.toString();

        } else {
            error.listaCampoMensagem.forEach(obj => {
                msg += obj.mensagem + " \r\n";

            })
        }
        this.messageBox.alert(msg).show();
    }

    salvar() {
        if(this.hlaComponent.validateForm() ){
            this.doadorService.obterDoadorInternacional(this._idDoador).then(res=>{
                if(res.tipoDoador === TiposDoador.INTERNACIONAL){
                    let exame: ExameDoadorInternacional = new ExameDoadorInternacional();
                    exame.locusExames = this.hlaComponent.getValue();
                    this.doadorService.enviarResultadoExameDoadorInternacional(this._idDoador, exame).then(res => {
                        this.messageBox.alert(res.mensagem)
                            .withTarget(this)
                            .withCloseOption(this.voltarSemDesatribuir)
                            .show();
                    },
                    (error: ErroMensagem) => {
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
                }
                else{
                    let exame: ExameCordaoInternacional = new ExameCordaoInternacional();
                    exame.locusExames = this.hlaComponent.getValue();
                    this.doadorService.enviarResultadoExameCordaoInternacional(this._idDoador, exame).then(res => {
                        this.messageBox.alert(res.mensagem)
                            .withTarget(this)
                            .withCloseOption(this.voltarSemDesatribuir)
                            .show();
                    },
                    (error: ErroMensagem) => {
                        ErroUtil.exibirMensagemErro(error,this.messageBox);
                    });
                }
            });
        }
    }

    nomeComponente(): string {
        return "CadastroResultadoExameInternacionalComponent";
    }


}