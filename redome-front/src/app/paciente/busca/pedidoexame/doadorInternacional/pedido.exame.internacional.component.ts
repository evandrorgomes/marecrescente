import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { PedidoExameService } from "../../../../laboratorio/pedido.exame.service";
import { Locus } from "../../../cadastro/exame/locus";
import { HistoricoNavegacao } from "../../../../shared/historico.navegacao";
import { LocusComponent } from "../../../../shared/hla/locus/locus.component";
import { MessageBox } from "../../../../shared/modal/message.box";
import { PermissaoRotaComponente } from "../../../../shared/permissao.rota.componente";
import { RouterUtil } from "../../../../shared/util/router.util";
import { ArrayUtil } from "../../../../shared/util/array.util";
import { MatchService } from "../../../../doador/solicitacao/match.service";
import { PedidoExame } from "../../../../laboratorio/pedido.exame";
import { PedidoWorkup } from "../../../../doador/consulta/workup/pedido.workup";
import { ErroMensagem } from "../../../../shared/erromensagem";
import { CampoMensagem } from "../../../../shared/campo.mensagem";
import { ErroUtil } from "../../../../shared/util/erro.util";

/**
 * Componente que representa o pedido de exame de fase 2 realizado para 
 * o doador internacional.
 * 
 * @author Pizão
 */
@Component({
    selector: "pedido-exame-internacional",
    moduleId: module.id,
    templateUrl: "./pedido.exame.internacional.component.html"
})
export class PedidoExameInternacionalComponent implements PermissaoRotaComponente, OnInit {

    public idMatch: number;

    @ViewChild("locusComponent")
    private locusComponent: LocusComponent;

    public pedidoExame:PedidoExame;

    // Mensagem de sucesso pós pedido de exame.
    private mensagemSucesso: string;


    constructor(private fb: FormBuilder, private translate: TranslateService,
        private activatedRouter: ActivatedRoute, private matchService: MatchService,
        private router: Router, private messageBox: MessageBox) {

        this.translate.get("pedidoFase2.sucesso").subscribe(msgs =>{
            this.mensagemSucesso = msgs.sucesso;
        });
    }

    public voltar() {
        this.router.navigateByUrl("/pacientes/busca");
    }

    ngOnInit(): void {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, 'idMatch').then(res => {
            this.idMatch = <number> res;
        });
    }

    salvar() {
        if(this.locusComponent.validateForm()){
            let pedidoExame: PedidoExame = new PedidoExame();
            let locusSelecionados: Locus[] = this.locusComponent.values;
            pedidoExame.locusSolicitados = locusSelecionados;
            this.matchService.salvarPedidoExameInternacional(this.idMatch, pedidoExame)
            .then((retorno: CampoMensagem) => {
                this.messageBox.alert(retorno.mensagem.toString()).show();
                this.voltar();
                
            }, (error: ErroMensagem) =>{
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }
    }

    nomeComponente(): string {
        return "PedidoExameInternacionalComponent";
    }

}