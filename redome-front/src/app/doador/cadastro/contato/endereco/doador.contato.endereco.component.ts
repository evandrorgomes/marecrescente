import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ContatoDataEventService } from 'app/doador/solicitacao/contato.data.event.service';
import { EventEmitterService } from 'app/shared/event.emitter.service';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';
import { BaseForm } from '../../../../shared/base.form';
import { EnderecoContato } from '../../../../shared/classes/endereco.contato';
import { EnderecoContatoComponent } from '../../../../shared/component/endereco/endereco.contato.component';
import { Pais } from '../../../../shared/dominio/pais';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { EnderecoContatoService } from "../../../../shared/service/endereco.contato.service";
import { DoadorService } from '../../../doador.service';
import { EnderecoContatoDoador } from '../../../endereco.contato.doador';

/**
 * Classe que representa o componente de telefone de contato
 * @author Bruno Sousa
 */
@Component({
    selector: "doador-contato-endereco",
    moduleId: module.id,
    templateUrl: "./doador.contato.endereco.component.html",
    //styleUrls: ['../../paciente.css']
})
export class DoadorContatoEnderecoComponent extends BaseForm<EnderecoContatoDoador> implements OnInit {

    @Input()
    private esconderLinkIncluirEndereco: string = "false";


    deveExibirEnderecoContatoExcluido:boolean = false;
    deveExibirRemoverEndereco:boolean;

    @ViewChild("enderecoContato")
    private enderecoContatoComponent: EnderecoContatoComponent;

    @ViewChild("modalErro")
    private modalErro;
    
    private _idDoador: number;
    private _enderecosContatoDoador: EnderecoContatoDoador[] = [];

    mostraDados: string = '';
    mostraFormulario: string = 'hide';

    @Input()
    set exibirRemoverEndereco (value: string) {
        if (!value) {
            this.deveExibirRemoverEndereco = true;
        } else {
            this.deveExibirRemoverEndereco = value == 'true' ? true : false;
        }
    }

    @Input()
    set exibirEnderecoContatoExcluido (value: string) {
        if (!value) {
            this.deveExibirEnderecoContatoExcluido = true;
        } else {
            this.deveExibirEnderecoContatoExcluido = value == 'true' ? true : false;
        }
    }

    /**
     * Método principal de construção do formulário
     * @author Rafael Pizão
     */
    buildForm() {
        this.enderecoContatoComponent.buildForm();
    }

    /**
     * Construtor 
     * @param serviceDominio - serviço de dominio para buscar os dominios
     * @param correioService - serviço de cep para consultar os endereços
     * @author Rafael Pizão
     */
    constructor(private fb: FormBuilder, private doadorService: DoadorService, 
        translate: TranslateService, private autenticacaoService: AutenticacaoService,
        private enderecoContatoService:EnderecoContatoService, private contatoDataEventService: ContatoDataEventService){
        super();
        this.translate = translate;        
    }

    ngOnInit() {
        this.buildForm();
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
    obterEndereco(): EnderecoContatoDoador {
        let enderecoContatoDoador: EnderecoContatoDoador = new EnderecoContatoDoador();
        let enderecoContato: EnderecoContato = this.enderecoContatoComponent.obterEndereco();

        enderecoContatoDoador.pais = enderecoContato.pais;
        enderecoContatoDoador.cep = enderecoContato.cep;
        enderecoContatoDoador.tipoLogradouro = enderecoContato.tipoLogradouro;
        enderecoContatoDoador.nomeLogradouro = enderecoContato.nomeLogradouro;
        enderecoContatoDoador.numero = enderecoContato.numero;
        enderecoContatoDoador.complemento = enderecoContato.complemento;
        enderecoContatoDoador.bairro = enderecoContato.bairro;
        enderecoContatoDoador.municipio = enderecoContato.municipio;
        enderecoContatoDoador.enderecoEstrangeiro = enderecoContato.enderecoEstrangeiro;
        enderecoContatoDoador.principal = enderecoContato.principal;
        enderecoContatoDoador.correspondencia = enderecoContato.correspondencia;
        enderecoContatoDoador.excluido = enderecoContato.excluido;
        
        return enderecoContatoDoador;
    }

    public form(): FormGroup {
        return this.enderecoContatoComponent.form();
    }

    public preencherFormulario(endContato:EnderecoContatoDoador): void {
        if(endContato){
            this.enderecoContatoComponent.preencherFormulario(endContato);
        }
    }

    nomeComponente(): string {
        return null;
    }

    public incluir() {
        this.enderecoContatoComponent.isEndBrasil = true;
        this.buildForm();
        this.mostraDados = 'hide';
        this.mostraFormulario = '';
    }

    cancelarEdicao() {
        this.mostraFormulario = 'hide';
        this.mostraDados = '';        
    }

    public preencherDados(idDoador: number, contatos : EnderecoContatoDoador[]): void {
        this._idDoador = idDoador;
        
        if(!this.deveExibirEnderecoContatoExcluido){
            this._enderecosContatoDoador = contatos != null ? contatos.filter(endereco =>{
                return endereco.excluido == false;
            }) : [];
        }else{
            this._enderecosContatoDoador = contatos;
        }
    }

	public get enderecosContatoDoador(): EnderecoContatoDoador[] {
		return this._enderecosContatoDoador;
    }
    
    deveEsconderLinkIncluirEndereco(): boolean {
        return this.esconderLinkIncluirEndereco == "true" || !this.temPermissaoParaIncluirEndereco();
    }

    temPermissaoParaIncluirEndereco(): boolean {
        return this.autenticacaoService.temRecurso('ADICIONAR_ENDERECO_DOADOR');
    }

    salvarEndereco() {
        if (this.validateForm()) {
            let endereco: EnderecoContatoDoador = this.obterEndereco();

            if(this._enderecosContatoDoador === undefined){
                this._enderecosContatoDoador = [];
            }

            this.doadorService.adicionarEndereco(this._idDoador, endereco).then(res => {
                endereco.id = res.idObjeto;
                this._enderecosContatoDoador.push(endereco);
                this.cancelarEdicao();

                this.contatoDataEventService.contatoDataEvent.atualizarHemocentro(endereco);

            }).catch(error => {
                this.exibirMensagemErro(error);
            });
        }
    }

    private excluirEnredecoContato(endereco: EnderecoContato) {
        this.enderecoContatoService.excluirEnderecoContatoPorId(endereco.id).then(
            res => {
                endereco.excluido = true;
                this._enderecosContatoDoador = this.enderecosContatoDoador.filter(endereco =>{
                    return endereco.excluido == false;
                })

                this.contatoDataEventService.contatoDataEvent.atualizarHemocentro(endereco);
            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
    }

    private exibirMensagemErro(error: ErroMensagem){
        error.listaCampoMensagem.forEach(obj => {
            this.modalErro.mensagem = obj.mensagem;
        })
        this.modalErro.abrirModal();
    }
    
    /**
     * Retornar TRUE quando país for Brasil.
     * 
     * @param pais país informado.
     */
    protected paisIsBrasil(pais: Pais): boolean{
        if(pais == null){
            return false;
        }
        return pais.id == Pais.BRASIL;
    }

    contatoExcuido(endereco: EnderecoContato){
        if(endereco.excluido){
            return 'contato-excluido-custom';
        }
        return 'texto-bloco';
    }
}