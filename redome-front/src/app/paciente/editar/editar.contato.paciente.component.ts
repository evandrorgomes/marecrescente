import {HeaderPacienteComponent} from '../consulta/identificacao/header.paciente.component';
import {HistoricoNavegacao} from '../../shared/historico.navegacao';
import {MensagemModalComponente} from '../../shared/modal/mensagem.modal.component';
import { ContatoPacienteDTO } from './contato.paciente.dto';
import { ErroMensagem } from '../../shared/erromensagem';
import {Paciente} from '../paciente';
import {PacienteService} from '../paciente.service';
import {DominioService} from '../../shared/dominio/dominio.service';
import {ContatoEnderecoPacienteComponent} from '../cadastro/contato/endereco/contato.endereco.paciente.component';
import {ContatoTelefonicoPaciente} from '../cadastro/contato/telefone/contato.telefonico.paciente';
import {EnderecoContatoPaciente} from '../cadastro/contato/endereco/endereco.contato.paciente';
import {ContatoPacienteComponent} from '../cadastro/contato/contato.paciente.component';
import {Router, ActivatedRoute} from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray } from '@angular/forms';
import { ComponenteRecurso } from '../../shared/enums/componente.recurso';

/**
 * Classe que representa o componente de endereço de contato
 */
@Component({
    selector: "editar-contato-paciente",
    templateUrl: "./editar.contato.paciente.component.html"
})
export class EditarContatoPacienteComponent implements OnInit {
    
    private rmr:number;
    private contatoDTO:ContatoPacienteDTO;
    mensagemSucesso: string;
    
    @ViewChild('sucessoModal') 
    private sucessoModal: MensagemModalComponente;

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    @ViewChild(ContatoPacienteComponent)
    private contatoPacienteComponent: ContatoPacienteComponent;

    
    constructor(fb: FormBuilder, serviceDominio:DominioService, private router: Router,
                translate: TranslateService, private activatedRouter: ActivatedRoute,
                private pacienteService: PacienteService){
    }

    ngOnInit() {
        Promise.resolve(this.headerPaciente).then(() => {
            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmr);
        });

        this.activatedRouter.params.subscribe(params => {
            this.rmr = params['idPaciente'];

            this.pacienteService.obterContatoPaciente(this.rmr).then(res => {
                this.contatoDTO = new ContatoPacienteDTO();
                Object.assign(this.contatoDTO, res);
                this.preencherFormulario();
                
            }, (error: ErroMensagem) => {
                throw new Error("Erro ao buscar contato do paciente com RMR: " + this.rmr);
            });

        });
    }

    private preencherFormulario():void{
        let paciente:Paciente = new Paciente();
        paciente.rmr = this.contatoDTO.rmr;
        paciente.email = this.contatoDTO.email;
        paciente.enderecosContato = this.contatoDTO.enderecosContato;
        paciente.contatosTelefonicos = this.contatoDTO.contatosTelefonicos;
        this.contatoPacienteComponent.preencherFormulario(paciente);
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.EditarContatoPacienteComponent];
    }

    /**
     * Salva os contatos após ajustes.
     */
    salvarEdicao(): void{
        if(this.contatoPacienteComponent.validateForm()){
            let endContato:EnderecoContatoPaciente = this.contatoPacienteComponent.obterEndereco();
            endContato.id = this.contatoDTO.enderecosContato[0].id;
            let enderecos: EnderecoContatoPaciente[] = [endContato];
            let email:string = this.contatoPacienteComponent.obterEmail();
            let telefones:ContatoTelefonicoPaciente[] = this.contatoPacienteComponent.listarTelefonesContato();
            this.pacienteService.atualizarPaciente(this.rmr, enderecos, email, telefones)
                .then(
                res => {
                    this.mensagemSucesso = res['mensagem'];
                    this.sucessoModal.abrirModal();
                }, (error: ErroMensagem) => {
                    throw new Error("Erro ao atualizar contato do paciente com RMR: " + this.rmr);
                });;
        }
    }

    voltar(): void{
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

};