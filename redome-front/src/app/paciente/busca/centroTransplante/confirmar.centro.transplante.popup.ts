import { Input, ViewChild, Component } from "@angular/core";
import { CentroTransplante } from "../../../shared/dominio/centro.transplante";
import { CentroTransplanteService } from "../../../admin/centrotransplante/centrotransplante.service";
import { TranslateService } from "@ngx-translate/core";
import { ErroMensagem } from "../../../shared/erromensagem";
import { FormGroup, FormBuilder, Validators, FormControl } from "@angular/forms";
import { ContatoCentroTransplantadorDTO } from "./contato.centro.transplantador.dto";
import { PacienteService } from "../../paciente.service";
import { DetalheAvaliacaoPacienteDTO } from "./detalhe.avaliacao.paciente.dto";
import { BaseForm } from "../../../shared/base.form";
import { EventEmitterService } from "../../../shared/event.emitter.service";
import { CampoMensagem } from "../../../shared/campo.mensagem";
import { CentroTransplanteFormComponent } from "./form/centro.transplante.form.component";
import { PermissaoRotaComponente } from "../../../shared/permissao.rota.componente";
import { MessageBox } from "../../../shared/modal/message.box";

@Component({
    selector: 'confirmar-centro-transplante-popup',
    templateUrl: './confirmar.centro.transplante.popup.html'
})
export class ConfirmarCentroTransplantePopup {

    public static SUCESSO: string = "sucessoAtualizacaoCentroTransplante";
    public static FALHA: string = "falhaAtualizacaoCentroTransplante";

    @Input()
    public rmr: number;

    @Input()
    public idDoador: number;

    public idMatch: number;

    public detalhe: DetalheAvaliacaoPacienteDTO;
    public centrosTransplante: ContatoCentroTransplantadorDTO[];
    public centroSelecionado: ContatoCentroTransplantadorDTO;

    @ViewChild("modalConfirmarCentroTransplante")
    public thisModal: any;


    @ViewChild("confirmarCentroForm")
    public confirmarCentroForm: CentroTransplanteFormComponent;

    public textoEndereco: string;
    public textoContato: string;
    private mensagens: any;


    constructor(private centroTransplanteService: CentroTransplanteService,
        private pacienteService: PacienteService,
        translate: TranslateService, formBuilder: FormBuilder, private messageBox:MessageBox) {
    }

    ngOnInit(): void { }

    fecharTela() {
        this.thisModal.hide();
    }

    public abrirModal(rmr: number, idDoador: number, idMatch: number) {
        this.rmr = rmr;
        this.idDoador = idDoador;
        this.idMatch = idMatch;
        this.confirmarCentroForm.carregarDados(this.rmr)
            .then(res => {
                if (!this.confirmarCentroForm.detalhe.centroTransplantadorConfirmado) {
                    this.thisModal.show();
                }
            });
    }

    public confirmarCentroTransplante(): void {
        if (this.confirmarCentroForm.validateForm()) {
            let centroSelecionado: ContatoCentroTransplantadorDTO = this.confirmarCentroForm.centroSelecionado;

            this.pacienteService.confirmarCentroTransplante(this.rmr, centroSelecionado.id)
                .then(res => {
                    this.exibirMensagemSucesso(res);
                    this.fecharTela();
                    EventEmitterService.get(
                        ConfirmarCentroTransplantePopup.SUCESSO).emit(this.idMatch);

                }, (error: ErroMensagem) => {
                    this.exibirMensagemErro(error);
                    this.fecharTela();

                    if (error.statusCode == 422) {
                        EventEmitterService.get(
                            ConfirmarCentroTransplantePopup.FALHA).emit(this.idDoador);
                    }
                });
        }
    }

    public tornarCentroIndefinidoParaPaciente() {
        this.pacienteService.indefinirCentroTransplante(this.rmr)
            .then(res => {
                this.exibirMensagemSucesso(res);
                this.fecharTela();
                EventEmitterService.get(
                    ConfirmarCentroTransplantePopup.SUCESSO).emit(this.idMatch);

            }, (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
                this.fecharTela();

                if (error.statusCode == 422) {
                    EventEmitterService.get(
                        ConfirmarCentroTransplantePopup.FALHA).emit(this.idDoador);
                }
            });
    }


    private exibirMensagemErro(error: ErroMensagem) {
        let msg:string = "";
        if (error.mensagem) {
            msg = error.mensagem.toString();
            
        } else {
            error.listaCampoMensagem.forEach(obj => {
                msg += obj.mensagem +" \r\n";

            })
        }
        this.messageBox.alert(msg).show();
    }

    private exibirMensagemSucesso(campoMensagem: CampoMensagem) {
        this.messageBox.alert(campoMensagem.mensagem).show();
    }

}