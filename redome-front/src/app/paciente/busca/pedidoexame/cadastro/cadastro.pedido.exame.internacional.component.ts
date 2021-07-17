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
import { HlaComponent } from "../../../../shared/hla/hla.component";
import { InativacaoComponent } from "../../../../doador/inativacao/inativacao.component";
import { Exame } from "../../../cadastro/exame/exame.";
import { ExameDoadorInternacional } from "../../../../doador/exame.doador.internacional";
import { TarefaService } from "../../../../shared/tarefa.service";
import { SolicitacaoService } from 'app/doador/solicitacao/solicitacao.service';

/**
 * Componente que representa o pedido de exame de fase 2 realizado para
 * o doador internacional.
 *
 * @author Pizão
 */
@Component({
    selector: "cadastro-pedido-exame-internacional",
    moduleId: module.id,
    templateUrl: "./cadastro.pedido.exame.internacional.component.html"
})
export class CadastroPedidoExameInternacionalComponent implements PermissaoRotaComponente, OnInit {


    @ViewChild("hlaComponent")
    public hlaComponent:HlaComponent;


    @ViewChild("inativacaoDoador")
    public inativacaoDoador:InativacaoComponent;

    private idPedidoExame:number;
    private idTarefa:number;

    constructor(private fb: FormBuilder, private translate: TranslateService,
        private activatedRouter: ActivatedRoute,
        private router: Router, private messageBox: MessageBox,
        private solicitacaoService: SolicitacaoService,
        private pedidoExameService:PedidoExameService,
        private tarefaService: TarefaService) {

    }

    public voltar() {
        this.tarefaService.removerAtribuicaoTarefa(this.idTarefa).then(res=>{
            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
        })
    }

    public voltarSemDesatribuir(target:any) {
        target.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
    }
    ngOnInit(): void {

        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter,['idSolicitacao','idTarefa']).then(res=>{
            const idSolicitacao = <number>res['idSolicitacao'];
            this.idTarefa = <number>res['idTarefa'];
            this.solicitacaoService.obterPedidoExamePorSolicitacaoId(idSolicitacao).then(res=>{
                let pedidoExame:PedidoExame = new PedidoExame().jsonToEntity(res.pedidoExame);
                this.idPedidoExame = pedidoExame.id;
                let locus:string[] = [];
                const tipoDoador:string = res.pedidoExame.solicitacao.match.doador.tipoDoador;
                locus = pedidoExame.locusSolicitados.map(locus=>{
                    return locus.codigo;
                })
                if(locus){
                    this.hlaComponent.locusObrigatorios = locus;
                }
                if(tipoDoador == 'INTERNACIONAL'){
                    this.hlaComponent.comAntigeno = true;
                }
            })
        });

        this.hlaComponent.resetFieldRequired(this.inativacaoDoador.inativarForm, 'motivoStatusDoador');
        this.hlaComponent.resetFieldRequired(this.inativacaoDoador.inativarForm, 'tempoAfastamento');
    }

    salvar() {
        if(this.hlaComponent.validateForm() ){
            // this.idPedidoExame;
            let exame:ExameDoadorInternacional = new ExameDoadorInternacional();
            exame.locusExames = this.hlaComponent.getValue();

            this.pedidoExameService.enviarResultadoPedidoExameDoadorInternacional(
                this.idPedidoExame, exame, this.inativacaoDoador.inativarForm.get('motivoStatusDoador').value,
                this.inativacaoDoador.inativarForm.get('tempoAfastamento').value).then(res => {
                    this.messageBox.alert(res.mensagem)
                        .withTarget(this)
                        .withCloseOption(this.voltarSemDesatribuir)
                        .show();
                },
                (error: ErroMensagem) => {
                    ErroUtil.exibirMensagemErro(error,this.messageBox);
                });
        }
    }

    nomeComponente(): string {
        return "CadastroPedidoExameInternacionalComponent";
    }

}
