import { EnderecoContato } from '../../../../shared/classes/endereco.contato';
import { EnderecoContatoComponent } from '../../../../shared/component/endereco/endereco.contato.component';
import {Paciente} from '../../../paciente';
import { PacienteUtil } from '../../../../shared/paciente.util';
import { TranslateService } from '@ngx-translate/core';
import { UF } from '../../../../shared/dominio/uf';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray } from "@angular/forms";
import { EnderecoContatoPaciente } from "./endereco.contato.paciente";
import { Pais } from "../../../../shared/dominio/pais";
import { DominioService } from "../../../../shared/dominio/dominio.service";
import { CepCorreioService } from "../../../../shared/cep.correio.service";
import { CepCorreio } from "../../../../shared/cep.correio";
import { BaseForm } from '../../../../shared/base.form';
import { PacienteConstantes } from '../../../paciente.constantes';

/**
 * Classe que representa o componente de endereço de contato
 */
@Component({
    selector: "contato-endereco-paciente",
    moduleId: module.id,
    templateUrl: "./contato.endereco.paciente.component.html",
    //styleUrls: ['../../paciente.css']
})
export class ContatoEnderecoPacienteComponent extends BaseForm<EnderecoContatoPaciente> implements OnInit {

    @ViewChild("enderecoContato")
    private enderecoContatoComponent: EnderecoContatoComponent;

    enderecoContatoPaciente: EnderecoContatoPaciente;

    // Indica se o endereço selecionado é estrangeiro (país que reside é diferente de BR)
    public isEndBrasil: Boolean = true;

    public mascaraCep: Array<string | RegExp>

    /**
     * Método principal de construção do formulário
     * @author Rafael Pizão
     */
    buildForm() {
        
    }

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
    }

    /**
     * Método para setar os valores default no formulario
     * 
     * @author Fillipe Queiroz
     * @memberof ContatoPacienteComponent
     */
    setValoresPadroes(){
        this.enderecoContatoComponent.setValoresPadroes();
    }

    /**
     * reseta as mensagens de erro de telefones
     * @author Rafael Pizão
     */
    resetMsgErrors(){
        this.enderecoContatoComponent.resetMsgErrors();
    }

    /**
     * verifica as obrigatoriedades de cada campo e de telefones
     * @author Rafael Pizão
     * @return boolean se foi validado com sucesso ou não
     */
    verificarValidacaoEndereco():boolean{       
        return this.enderecoContatoComponent.verificarValidacaoEndereco();
    }

    /**
     * retorna o endereço de contato preenchido no formulário
     * @author Bruno Sousa
     */
    obterEndereco(): EnderecoContatoPaciente {
        let enderecoContatoPaciente: EnderecoContatoPaciente = new EnderecoContatoPaciente();
        let enderecoContato: EnderecoContato = this.enderecoContatoComponent.obterEndereco();

        enderecoContatoPaciente.pais = enderecoContato.pais;
        enderecoContatoPaciente.cep = enderecoContato.cep
        enderecoContatoPaciente.tipoLogradouro = enderecoContato.tipoLogradouro;
        enderecoContatoPaciente.nomeLogradouro = enderecoContato.nomeLogradouro;
        enderecoContatoPaciente.numero = enderecoContato.numero;
        enderecoContatoPaciente.complemento = enderecoContato.complemento;
        enderecoContatoPaciente.bairro = enderecoContato.bairro;
        enderecoContatoPaciente.municipio = enderecoContato.municipio;
        enderecoContatoPaciente.enderecoEstrangeiro = enderecoContato.enderecoEstrangeiro;
        
        return enderecoContatoPaciente;
    }

    public form(): FormGroup {
        return this.enderecoContatoComponent.form();
    }

    // Override
    public preencherFormulario(endContato:EnderecoContatoPaciente): void {
        if(endContato){
            let enderecoContato: EnderecoContato = new EnderecoContato();
            enderecoContato.pais = endContato.pais;
            enderecoContato.cep = endContato.cep
            enderecoContato.tipoLogradouro = endContato.tipoLogradouro;
            enderecoContato.nomeLogradouro = endContato.nomeLogradouro;
            enderecoContato.numero = endContato.numero;
            enderecoContato.complemento = endContato.complemento;
            enderecoContato.bairro = endContato.bairro;
            enderecoContato.municipio = endContato.municipio;            
            enderecoContato.enderecoEstrangeiro = endContato.enderecoEstrangeiro;
            this.enderecoContatoComponent.preencherFormulario(enderecoContato);            
        }
    }

    nomeComponente(): string {
        return null;
    }

};