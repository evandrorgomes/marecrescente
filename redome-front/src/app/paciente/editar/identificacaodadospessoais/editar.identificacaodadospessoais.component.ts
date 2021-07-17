import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { PacienteConstantes } from '../../paciente.constantes';
import { Responsavel } from '../../responsavel';
import { PacienteUtil } from '../../../shared/paciente.util';
import { Etnia } from '../../../shared/dominio/etnia';
import { Raca } from '../../../shared/dominio/raca';
import { UF } from '../../../shared/dominio/uf';
import { Pais } from '../../../shared/dominio/pais';
import { DadosPessoaisComponent } from '../../cadastro/dadospessoais/dadospessoais.component';
import { ErroMensagem } from '../../../shared/erromensagem';
import { Paciente } from '../../paciente';
import { PacienteService } from '../../paciente.service';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { IdentificacaoComponent } from '../../cadastro/identificacao/identificacao.component';
import {ActivatedRoute, Router} from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray } from "@angular/forms";
import { ComponenteRecurso } from "../../../shared/enums/componente.recurso";
import { DataUtil } from '../../../shared/util/data.util';

/**
 * Classe que representa o componente de endereço de contato
 */
@Component({
    selector: "editar-identificacao-dadospessoais-paciente",
    templateUrl: "./editar.identificacaodadospessoais.component.html"
})
export class EditarIdentificacaoDadosPessoaisComponent implements OnInit {
    
    private rmr:number;
    private paciente: Paciente;
    mensagemSucesso: String;
    mensagem: String;

    @ViewChild(IdentificacaoComponent)
    private identificacaoComponent: IdentificacaoComponent;

    @ViewChild(DadosPessoaisComponent)
    private dadosPessoaisComponent: DadosPessoaisComponent;

    
    constructor(fb: FormBuilder, dominioService: DominioService, 
                private translate: TranslateService, private activatedRouter: ActivatedRoute,
                private router: Router, private pacienteService: PacienteService,
                private autenticacaoService: AutenticacaoService){
    }

    ngOnInit() {
        this.activatedRouter.params.subscribe(params => {
            this.rmr = params['idPaciente'];

            this.pacienteService.obterIdentificaoEDadosPessoais(this.rmr).then(res => {
                this.paciente = new Paciente().jsonToEntity(res);                
                this.preencherFormulario();
                
            }, (error: ErroMensagem) => {
                throw new Error("Erro ao buscar contato do paciente com RMR: " + this.rmr);
            });

        });
    }

    private preencherFormulario():void{        
        
        this.identificacaoComponent.preencherFormulario(this.paciente);
        this.dadosPessoaisComponent.preencherFormulario(this.paciente);        
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.EditarIdentificacaoDadosPessoaisComponent];
    }

    onSubmit(sucessoModal: any, erroModal: any) {
        let identificacaoOk: boolean = this.identificacaoComponent.validateForm();
        let dadosPessoaisOk: boolean = this.dadosPessoaisComponent.validateForm();
        
        if (identificacaoOk && dadosPessoaisOk) {
            let pacienteAlterado: Paciente = new Paciente();
            pacienteAlterado.rmr = this.paciente.rmr;
            pacienteAlterado.cpf = PacienteUtil.obterCPFSemMascara(this.identificacaoComponent.form().get("cpf").value) || null;
            pacienteAlterado.cns = this.identificacaoComponent.form().get("cns").value || null;
            
            let _dataNascimento = this.identificacaoComponent.form().get("dataNascimento").value;
            pacienteAlterado.dataNascimento = PacienteUtil.obterDataSemMascara( _dataNascimento ) || null;
            
            pacienteAlterado.nome = this.identificacaoComponent.form().get("nome").value || null;
            pacienteAlterado.nomeMae = this.identificacaoComponent.form().get("nomeMae").value || null;
            pacienteAlterado.maeDesconhecida = this.identificacaoComponent.form().get("maeDesconhecida").value||false;
            
            let cpfResponsavel: string = PacienteUtil.obterCPFSemMascara(this.identificacaoComponent.form()
                            .get("responsavel.cpf").value) || null;
            
            let nomeResponsavel: string = this.identificacaoComponent.form().get("responsavel.nome").value || null;
            let parentesco: string = this.identificacaoComponent.form().get("responsavel.parentesco").value || null;
            if (this.identificacaoComponent.isMaiorIdade(pacienteAlterado.dataNascimento) ) {
                pacienteAlterado.responsavel = null;
            }
            else {
                if (cpfResponsavel || nomeResponsavel || parentesco) {                    
                    pacienteAlterado.responsavel = new Responsavel(cpfResponsavel, nomeResponsavel, parentesco);
                    if (this.paciente.responsavel) {
                        pacienteAlterado.responsavel.id = this.paciente.responsavel.id;
                    }
                }
                else {
                    pacienteAlterado.responsavel = null;
                }
            }
            
            pacienteAlterado.sexo = this.dadosPessoaisComponent.form().get("sexo").value || null;
            pacienteAlterado.abo = this.dadosPessoaisComponent.form().get("abo").value || null;
            let racaId: number = this.dadosPessoaisComponent.form().get("raca.id").value || null;
            if (racaId) {
                let raca: Raca = this.dadosPessoaisComponent.obterRacas().find(obj => obj.id == racaId);
                pacienteAlterado.raca = new Raca(racaId, raca.nome);
            }
            else {
                pacienteAlterado.raca = null;
            }
            
            let etniaId: number = this.dadosPessoaisComponent.form().get("etnia.id").value || null;
            if (etniaId) {
                if (pacienteAlterado.raca && PacienteConstantes.RACA_INDIGENA_COM_ETNIAS == pacienteAlterado.raca.nome) {
                    pacienteAlterado.etnia = new Etnia(etniaId);
                }
                else
                    pacienteAlterado.etnia = null;
                }
            else {
                pacienteAlterado.etnia = null;
            }
            
            let paisId: number = this.dadosPessoaisComponent.form().get("pais.id").value || null;
            if (paisId) {
                let pais: Pais = this.dadosPessoaisComponent.obterPaises().find(obj => obj.id == paisId);
                pacienteAlterado.pais = new Pais(paisId, pais.nome);
            }
            
            let sigla: string = this.dadosPessoaisComponent.form().get("uf.sigla").value || null;
            if (sigla && pacienteAlterado.pais && pacienteAlterado.pais.nome == PacienteConstantes.PAIS_BRASIL) {
                pacienteAlterado.naturalidade = new UF(sigla);
            }
            else {
                pacienteAlterado.naturalidade = null;
            }

            this.pacienteService.salvarIdentificacaoEDadosPessoais(pacienteAlterado).then((res: any)  => {
                this.mensagemSucesso = res.mensagem;
                if (sucessoModal) {
                    sucessoModal.abrirModal();
                }
            },
            (error: ErroMensagem) => {
                this.marcarCamposInvalidosRetornadosBackend(error.listaCampoMensagem, erroModal);
            }); 
        }


    }


    /**
     * Marca os campos com erro, identificadas de acordo 
     * com a mensagem retornada do backend
     * 
     * @param mensagens 
     */
    private marcarCamposInvalidosRetornadosBackend(mensagens:CampoMensagem[], erroModal: any){
        this.mensagem = "";
        mensagens.forEach(mensagemErro => {
            if(this.identificacaoComponent.existsField(mensagemErro.campo)){
                this.identificacaoComponent.markAsInvalid(this.identificacaoComponent.getField(this.identificacaoComponent.form(), mensagemErro.campo));
                this.identificacaoComponent.formErrors[mensagemErro.campo] = mensagemErro.mensagem;
            }
            else if(this.dadosPessoaisComponent.existsField(mensagemErro.campo)){
                this.dadosPessoaisComponent.markAsInvalid(this.dadosPessoaisComponent.getField(this.dadosPessoaisComponent.form(), mensagemErro.campo));
                this.dadosPessoaisComponent.formErrors[mensagemErro.campo] = mensagemErro.mensagem;
            }
            else {
                this.mensagem += mensagemErro.mensagem;
            }

        });

        if (this.mensagem != "" && erroModal) {
            erroModal.abrirModal();
        } 
    }

    fecharSucessoModal() {
        localStorage.removeItem(this.autenticacaoService.usuarioLogado().username + "_identificacao")
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
        //HistoricoNavegacao.urlRetorno();
    }

};