import { ContatoTelefonico } from '../../../shared/classes/contato.telefonico';
import { ContatoTelefoneComponent } from '../../../shared/component/telefone/contato.telefone.component';
import {Paciente} from '../../paciente';
import { EnderecoContatoPaciente } from './endereco/endereco.contato.paciente';
import { ContatoEnderecoPacienteComponent } from './endereco/contato.endereco.paciente.component';
import { TranslateService } from '@ngx-translate/core';
import { ErroMensagem } from '../../../shared/erromensagem';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray } from "@angular/forms";
import { DominioService } from "../../../shared/dominio/dominio.service";
import { BaseForm } from '../../../shared/base.form';
import { ContatoTelefonicoPaciente } from "./telefone/contato.telefonico.paciente";

/**
 * Classe que representa o componente de endereço de contato
 */
@Component({
    selector: "contato-paciente",
    moduleId: module.id,
    templateUrl: "./contato.paciente.component.html",
    //styleUrls: ['../paciente.css']
})
export class ContatoPacienteComponent extends BaseForm<Paciente> implements OnInit {
    
    contatoForm: FormGroup;

    @ViewChild(ContatoEnderecoPacienteComponent)
    private contatoEnderecoPacienteComponent: ContatoEnderecoPacienteComponent;

    @ViewChild("contatoTelefoneComponent")
    private contatoTelefonePacienteComponent: ContatoTelefoneComponent;


    /**
     * Construtor 
     * @param serviceDominio - serviço de dominio para buscar os dominios
     * @param correioService - serviço de cep para consultar os endereços
     * @author Rafael Pizão
     */
    constructor(private fb: FormBuilder, private serviceDominio:DominioService, translate: TranslateService){
        super();
        this.translate = translate;
        this.buildForm();
    }

    ngOnInit() {
        this.contatoForm.addControl('endereco', this.contatoEnderecoPacienteComponent.form());
        this.contatoForm.addControl('telefone', this.contatoTelefonePacienteComponent.telefoneForm);
    }

    
    /**
     * Método principal de construção do formulário
     * @author Rafael Pizão
     */
     buildForm() {

        this.contatoForm = this.fb.group({
            email: [null, null]
        });

    }

     /**
      * Retorna a lista com os telefones.
      * @author Bruno Sousa
      */
     listarTelefonesContato(): ContatoTelefonicoPaciente[] {
         let listaTelefoneContato: ContatoTelefonico[] = this.contatoTelefonePacienteComponent.listarTelefonesContato();
         return listaTelefoneContato ? <ContatoTelefonicoPaciente[]>listaTelefoneContato : null;
     }

    /**
     * retorna o endereço de contato preenchido no formulário
     * @author Bruno Sousa
     */
    obterEndereco(): EnderecoContatoPaciente {
        return this.contatoEnderecoPacienteComponent.obterEndereco();
    }

    /**
     * retorna o email de contato preenchido no formulário
     * @author Bruno Sousa
     */
    obterEmail(): string {
        return this.contatoForm.get("email").value || null;
    }

    public form(): FormGroup{
        return this.contatoForm;
    }

    public validateForm():boolean{
        let enderecoValid: boolean = this.contatoEnderecoPacienteComponent.validateForm();
        let telefoneValid: boolean = this.contatoTelefonePacienteComponent.validateForm();
        
        return enderecoValid && telefoneValid;
    }

    // Override
    public preencherFormulario(paciente:Paciente): void {
        this.contatoEnderecoPacienteComponent.preencherFormulario(paciente.enderecosContato[0]);
        this.contatoTelefonePacienteComponent.preencherFormulario(paciente.contatosTelefonicos);
        this.setPropertyValue("email", paciente.email);
    }

    nomeComponente(): string {
        return null;
    }

};