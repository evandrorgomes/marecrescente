import { AfterViewInit, Component, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AvaliacaoComponent } from 'app/paciente/cadastro/avaliacao/avaliacao.component';
import { PacienteService } from "app/paciente/paciente.service";
import { AutenticacaoService } from 'app/shared/autenticacao/autenticacao.service';
import { ComponenteRecurso } from 'app/shared/enums/componente.recurso';
import { ErroMensagem } from 'app/shared/erromensagem';
import { PermissaoRotaComponente } from 'app/shared/permissao.rota.componente';
import { RouterUtil } from 'app/shared/util/router.util';
import { MessageBox } from '../../../shared/modal/message.box';
import { HeaderPacienteComponent } from './../../consulta/identificacao/header.paciente.component';
import { MismatchDTO } from "../../../shared/dto/mismatch.dto";
import { Paciente } from '../../paciente';
import { ErroUtil } from 'app/shared/util/erro.util';
import { HistoricoNavegacao } from "app/shared/historico.navegacao";

/**
 * Classe que representa o componente de endereço de contato
 */
@Component({
    selector: "editar-avaliacao-paciente",
    templateUrl: "./editar.avaliacao.component.html"
})
export class EditarAvaliacaoComponent implements PermissaoRotaComponente, OnInit, AfterViewInit {
    
    private rmr:number;

    @ViewChild("avaliacaoComponent")
    private avaliacaoComponent: AvaliacaoComponent;

    @ViewChild("headerPaciente")
    private headerPaciente: HeaderPacienteComponent;

    constructor(private activatedRouter: ActivatedRoute, private messageBox: MessageBox,
        private router: Router, private pacienteService: PacienteService,
        private autenticacaoService: AutenticacaoService) {
    }
    
    ngOnInit(): void {
    }    

    ngAfterViewInit(): void {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idPaciente").then(value => {
            this.rmr = value;
            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmr);
            this.pacienteService.obterDadosMismatch(this.rmr).then(res => {
                let mismatchDTO: MismatchDTO = new MismatchDTO().jsonToEntity(res);            
                this.avaliacaoComponent.esconderCentroAvaliador();
                this.avaliacaoComponent.preencherFormulario(new Paciente().jsonToEntity(mismatchDTO));
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        })
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.EditarAvaliacaoComponent];
    }

    onSubmit() {
        let avaliacaoOk: boolean = this.avaliacaoComponent.validateForm();
        
        if (avaliacaoOk) {
            let mismatchDTO: MismatchDTO = new MismatchDTO();
            mismatchDTO.aceiteMismatch = this.avaliacaoComponent.form().get('aceiteMismatch').value;
            mismatchDTO.locusMismatch = this.avaliacaoComponent.obterListaLocusMismatch();

            this.pacienteService.alterarDadosMismatch(this.rmr, mismatchDTO).then(res  => {
                this.messageBox.alert(res.mensagem)
                    .withCloseOption(() => {
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
            if(this.avaliacaoComponent.existsField(mensagemErro.campo)){
                this.avaliacaoComponent.markAsInvalid(this.avaliacaoComponent.getField(this.avaliacaoComponent.form(), mensagemErro.campo));
                this.avaliacaoComponent.formErrors[mensagemErro.campo] = mensagemErro.mensagem;
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