import { Input, ViewChild, Component } from "@angular/core";
import { CentroTransplante } from "../../../../../shared/dominio/centro.transplante";
import { TranslateService } from "@ngx-translate/core";
import { ErroMensagem } from "../../../../../shared/erromensagem";
import { FormGroup, FormBuilder, Validators, FormControl } from "@angular/forms";
import { ContatoCentroTransplantadorDTO } from "../../contato.centro.transplantador.dto";
import { DetalheAvaliacaoPacienteDTO } from "../../detalhe.avaliacao.paciente.dto";
import { BaseForm } from "../../../../../shared/base.form";
import { CentroTransplanteFormComponent } from "../../form/centro.transplante.form.component";
import { CentroTransplanteService } from "../../../../../admin/centrotransplante/centrotransplante.service";
import { PacienteService } from "../../../../paciente.service";
import { EventEmitterService } from "../../../../../shared/event.emitter.service";
import { CampoMensagem } from "../../../../../shared/campo.mensagem";
import { TarefaService } from "../../../../../shared/tarefa.service";
import { HistoricoNavegacao } from "../../../../../shared/historico.navegacao";
import { Router, ActivatedRoute } from "@angular/router";
import { ComponenteRecurso } from "../../../../../shared/enums/componente.recurso";
import { TarefaBase } from "../../../../../shared/dominio/tarefa.base";
import { PermissaoRotaComponente } from "../../../../../shared/permissao.rota.componente";
import { MessageBox } from "../../../../../shared/modal/message.box";
import { HistoricoRecusasCentroTransplanteModal } from "../../historicoRecusas/historico.recusas.ct.modal";
import { Modal } from "../../../../../shared/modal/factory/modal.factory";

@Component({
    selector: 'confirmar-centro-transplante',
    templateUrl: './confirmar.centro.transplante.component.html'
})
export class ConfirmarCentroTransplanteComponent implements PermissaoRotaComponente{

    public static SUCESSO: string = "sucessoConfirmarCentroTransplanteIndefinido";
    public static FALHA: string = "falhaConfirmarCentroTransplanteIndefinido";

    @Input()
    public rmr: number;
    public tarefaId: number;


    @ViewChild("modalErro")
    public modalError: any;

    @ViewChild("confirmarCentroForm")
    public confirmarCentroForm: CentroTransplanteFormComponent;

    public textoEndereco: string;
    public textoContato: string;
    private mensagens: any;


    constructor(private centroTransplanteService: CentroTransplanteService,
        private pacienteService: PacienteService, private tarefaService: TarefaService,
        translate: TranslateService, private router: Router,
        private activatedRouter: ActivatedRoute, private messageBox: MessageBox) {
    }

    ngOnInit(): void {
        this.activatedRouter.params.subscribe(params => {
            this.tarefaId = Number(params["tarefaId"]);

            this.tarefaService.atribuirTarefaParaUsuarioLogado(this.tarefaId)
                .then(res => {
                    let tarefa: TarefaBase = res;
                    this.rmr = tarefa.processo.paciente.rmr;
                    this.confirmarCentroForm.carregarDados(this.rmr);
                }, (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                }
            );
        });
    }

    public confirmarCentroTransplante(): void {
        if(this.confirmarCentroForm.validateForm()){
            let centroSelecionado: ContatoCentroTransplantadorDTO = this.confirmarCentroForm.centroSelecionado;

            this.pacienteService.confirmarCentroTransplante(this.rmr, centroSelecionado.id)
                .then(res => {
                    this.exibirMensagemSucesso(res);
                    this.sair();
                    EventEmitterService.get(ConfirmarCentroTransplanteComponent.SUCESSO).emit();

                }, (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                    this.sair();

                    if(error.statusCode == 422){
                        EventEmitterService.get(ConfirmarCentroTransplanteComponent.FALHA).emit();
                    }
                });
        }
    }

    private exibirMensagemErro(error: ErroMensagem) {
        this.modalError.mensagem = error.mensagem;
        this.modalError.abrirModal();
    }

    private exibirMensagemSucesso(campoMensagem: CampoMensagem) {
        this.modalError.mensagem = campoMensagem.mensagem;
        this.modalError.abrirModal();
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[
            ComponenteRecurso.Componente.ConfirmarCentroTransplanteComponent];
    }

    public desatribuir(): void{
        this.tarefaService.removerAtribuicaoTarefa(this.tarefaId)
            .then(res => {
                this.sair();
            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            }
        );
    }

    private sair(): void{
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

    public abrirModalHistorico(): void{
        this.messageBox.dynamic(this.rmr, HistoricoRecusasCentroTransplanteModal).show();
    }
}
