import { DataUtil } from 'app/shared/util/data.util';
import { HeaderPacienteComponent } from './../../consulta/identificacao/header.paciente.component';
import { ErroUtil } from 'app/shared/util/erro.util';
import { RouterUtil } from 'app/shared/util/router.util';
import { Component, OnInit, AfterViewInit, ViewChild } from "@angular/core";
import { Diagnostico } from "app/paciente/cadastro/diagnostico/diagnostico";
import { DiagnosticoComponent } from "app/paciente/cadastro/diagnostico/diagnostico.component";
import { ActivatedRoute, Router } from "@angular/router";
import { PacienteService } from "app/paciente/paciente.service";
import { DiagnosticoService } from "../../../shared/service/diagnostico.service";
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from '../../../shared/modal/message.box';
import { PermissaoRotaComponente } from 'app/shared/permissao.rota.componente';
import { ComponenteRecurso } from 'app/shared/enums/componente.recurso';
import { PacienteUtil } from 'app/shared/paciente.util';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { CampoMensagem } from 'app/shared/campo.mensagem';
import { AutenticacaoService } from 'app/shared/autenticacao/autenticacao.service';

/**
 * Classe que representa o componente de endereço de contato
 */
@Component({
    selector: "editar-diagnostico-paciente",
    templateUrl: "./editar.diagnostico.component.html"
})
export class EditarDiagnosticoComponent implements PermissaoRotaComponente, OnInit, AfterViewInit {
    
    private rmr:number;

    @ViewChild("diagnosticoComponent")
    private diagnosticoComponent: DiagnosticoComponent;

    @ViewChild("headerPaciente")
    private headerPaciente: HeaderPacienteComponent;

    constructor(private activatedRouter: ActivatedRoute, private messageBox: MessageBox,
        private router: Router, private pacienteService: PacienteService,
        private diagnosticoService: DiagnosticoService, private autenticacaoService: AutenticacaoService) {

    }
    
    ngOnInit(): void {
    }    

    ngAfterViewInit(): void {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idPaciente").then(value => {
            this.rmr = value;
            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmr);
            this.pacienteService.obterDiagnostico(this.rmr).then(res => {
                let diagnostico: Diagnostico = new Diagnostico().jsonToEntity(res);
                this.diagnosticoComponent.idadePaciente = DataUtil.calcularIdade(diagnostico.paciente.dataNascimento);
                this.diagnosticoComponent.preencherFormulario(diagnostico);                
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        })
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.EditarDiagnosticoComponent];
    }

    onSubmit() {
        let diagnosticoOk: boolean = this.diagnosticoComponent.validateForm();
        
        if (diagnosticoOk) {
            let diagnostico: Diagnostico = new Diagnostico();
            let _dataDiagnostico = this.diagnosticoComponent.form().get("dataDiagnostico").value;
            diagnostico.dataDiagnostico = PacienteUtil.obterDataSemMascara(_dataDiagnostico) || null;
            
            let cidId = this.diagnosticoComponent.form().get("cidSelecionada").value || null;
            if (cidId) {
                diagnostico.cid = this.diagnosticoComponent.ultimaCidSelecionada;
            } else {
                diagnostico.cid = null;
            }

            this.diagnosticoService.alterarDiagnostico(this.rmr, diagnostico).then(res  => {
                this.messageBox.alert(res.mensagem)
                    .withCloseOption(() => {
                        localStorage.removeItem(this.autenticacaoService.usuarioLogado().username + "_identificacao");
                        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                    })
                    .show();
            },
            (error: ErroMensagem) => {
                this.marcarCamposInvalidosRetornadosBackend(error);
            }); 
        }

    }

    /**
     * Marca os campos com erro, identificadas de acordo 
     * com a mensagem retornada do backend
     * 
     * @param mensagens 
     */
    private marcarCamposInvalidosRetornadosBackend(error: ErroMensagem){        
        let mensagem: string = "";
        error.listaCampoMensagem.forEach(mensagemErro => {
            if(this.diagnosticoComponent.existsField(mensagemErro.campo)){
                this.diagnosticoComponent.markAsInvalid(this.diagnosticoComponent.getField(this.diagnosticoComponent.form(), mensagemErro.campo));
                this.diagnosticoComponent.formErrors[mensagemErro.campo] = mensagemErro.mensagem;
            }
            else {
                mensagem += mensagemErro.mensagem + "<br/>";
            }
        });

        if (mensagem && mensagem != "") {
            this.messageBox.alert(mensagem).show();
        }         
    }


    
}