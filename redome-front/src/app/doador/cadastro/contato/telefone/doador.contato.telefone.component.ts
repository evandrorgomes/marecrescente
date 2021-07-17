import { EventEmitterService } from './../../../../shared/event.emitter.service';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { AutenticacaoService } from "../../../../shared/autenticacao/autenticacao.service";
import { BaseForm } from '../../../../shared/base.form';
import { ContatoTelefonico } from "../../../../shared/classes/contato.telefonico";
import { ContatoTelefoneComponent } from '../../../../shared/component/telefone/contato.telefone.component';
import { CodigoInternacional } from '../../../../shared/dominio/codigo.internacional';
import { DominioService } from '../../../../shared/dominio/dominio.service';
import { ErroMensagem } from "../../../../shared/erromensagem";
import { ContatoTelefonicoService } from "../../../../shared/service/contato.telefonico.service";
import { ContatoTelefonicoDoador } from '../../../contato.telefonico.doador';
import { DoadorService } from '../../../doador.service';

/**
 * Classe que representa o componente de telefone de contato
 * @author Bruno Sousa
 */
@Component({
    selector: "doador-contato-telefone",
    moduleId: module.id,
    templateUrl: "./doador.contato.telefone.component.html",
    //styleUrls: ['../../paciente.css']
})
export class DoadorContatoTelefoneComponent extends BaseForm<ContatoTelefonicoDoador[]> implements OnInit {

    public static HOUVE_ATUALIZACAO_TELEFONE : string = "houveAtaualizacaoTelefone";
    telefoneForm: FormGroup;
    
    listaCodigoInternacional: CodigoInternacional[];

    private _contatosTelefonicos: ContatoTelefonicoDoador[];

    mostraDados: string = '';
    mostraFormulario: string = 'hide';
    private _idDoador:number;

    private _esconderLinkIncluirTelefone: boolean = false;

    deveExibirRemoverTelefone:boolean = false;

    @Input()
    set exibirRemoverTelefone (value: string) {
        if (!value) {
            this.deveExibirRemoverTelefone = true;
        } else {
            this.deveExibirRemoverTelefone = value == 'true' ? true : false;
        }
        
    }

    @Input()
    set esconderLinkIncluirTelefone (value: string) {
        if (!value) {
            this._esconderLinkIncluirTelefone = true;
        } else {
            this._esconderLinkIncluirTelefone = value == 'true' ? true : false;
        }
        
    }

    @ViewChild('contatoTelefoneComponent') 
    public contatoTelefoneComponent:ContatoTelefoneComponent;

    @ViewChild('modalMsg') 
    public modalMsg;
    

    /**
     * Construtor 
     * @param serviceDominio - serviço de dominio para buscar os dominios
     * @author Bruno Sousa
     */
    constructor(private fb: FormBuilder, private serviceDominio: DominioService, translate: TranslateService,
        private doadorService:DoadorService, private autenticacaoService:AutenticacaoService,
        private contatoTelefonicoService:ContatoTelefonicoService ) {
        super();
        this.translate = translate;
        // this.buildForm();
    }

    ngOnInit() {
        this.serviceDominio.listarCodigoInternacional().then(res => {
            this.listaCodigoInternacional = res;
        });
    }


    public form(): FormGroup{
        return this.telefoneForm;
    }


    nomeComponente(): string {
        return "DoadorContatoTelefoneComponent";
    }


	public get contatosTelefonicos(): ContatoTelefonicoDoador[] {
		return this._contatosTelefonicos;
    }

    /**
     * Metodo para salvar o novo telefone do doador.
     */
    salvarTelefone(){
        if(this.contatoTelefoneComponent.validateForm()){
            let telefones:ContatoTelefonico[] =  this.contatoTelefoneComponent.listarTelefonesContato();
            
            this.doadorService.incluirContatoTelefone(this._idDoador, telefones[0]).then(
            res => {
                telefones[0].id = res.idObjeto;
                this._contatosTelefonicos.push(<ContatoTelefonicoDoador>telefones[0]);
                this.cancelarEdicao();
                EventEmitterService.get(DoadorContatoTelefoneComponent.HOUVE_ATUALIZACAO_TELEFONE).emit(this._contatosTelefonicos);
            },
            (error: ErroMensagem) => {
                error.listaCampoMensagem.forEach(obj => {
                    this.modalMsg.mensagem = obj.mensagem;
                })
                this.modalMsg.abrirModal();
            });
        }
    }

    /**
     * Metodo para preencher o formulario
     * @param ContatoTelefonicoDoador[] lista com array de contatoTelefonico
     * 
     */
    public preencherFormulario(entidade: ContatoTelefonicoDoador[]): void {
        this.contatoTelefoneComponent.preencherFormulario(entidade);
    }

    /**
     * Metodo para preencher o formulario
     * @param ContatoTelefonicoDoador[] lista com array de contatoTelefonico
     * 
     */
    public preencherDados(idDoador:number, contatos:ContatoTelefonicoDoador[]): void {
        this._idDoador = idDoador;
        this._contatosTelefonicos = contatos != null ? contatos.filter(telefone =>{
            return telefone.excluido == false;
        }) : [];
//        this._contatosTelefonicos = contatos;
    }

    public editar() {
        this.mostraDados = 'hide';
        this.mostraFormulario = '';
        this.contatoTelefoneComponent.buildForm();
    }

    cancelarEdicao() {
        this.mostraFormulario = 'hide';
        this.mostraDados = '';
    }

    deveEsconderLinkIncluirTelefone(): boolean {
        return this._esconderLinkIncluirTelefone || !this.temPermissaoParaIncluirTelefone();
    }

    private temPermissaoParaIncluirTelefone(): boolean {
        return this.autenticacaoService.temRecurso('ADICIONAR_CONTATO_TELEFONICO_DOADOR');
    }

    private excluirTelefone(telefone: ContatoTelefonico) {
        this.contatoTelefonicoService.excluirContatoTelefonicoPorId(telefone.id).then(
            res => {
                telefone.excluido = true;
                this._contatosTelefonicos = this._contatosTelefonicos.filter(telefone => {
                    return telefone.excluido == false;
                })
            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalMsg.mensagem = obj.mensagem;
        })
        this.modalMsg.abrirModal();
    } 

    contatoExcuido(telefone: ContatoTelefonico){
        if(telefone.excluido){
            return 'contato-excluido-custom';
        }
        return 'texto-bloco';
    }
}