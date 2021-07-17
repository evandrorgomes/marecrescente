import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { TranslateService } from '@ngx-translate/core';
import { Municipio } from 'app/shared/dominio/municipio';
import { BaseForm } from '../../base.form';
import { CepCorreio } from '../../cep.correio';
import { CepCorreioService } from '../../cep.correio.service';
import { EnderecoContato } from '../../classes/endereco.contato';
import { DominioService } from '../../dominio/dominio.service';
import { Pais } from '../../dominio/pais';
import { UF } from '../../dominio/uf';
import { ErroMensagem } from '../../erromensagem';
import { FormatterUtil } from '../../util/formatter.util';

/**
 * Classe que representa o componente de endereço de contato
 */
@Component({
    selector: "endereco-contato",
    moduleId: module.id,
    templateUrl: "./endereco.contato.component.html",
    //styleUrls: ['../../paciente.css']
})
export class EnderecoContatoComponent extends BaseForm<EnderecoContato> implements OnInit {

    private _PAIS_BRASIL: string = 'BRASIL';
    private _BRASIL_ID: number = 1;

    @Input()
    private esconderCampoPrincipal: string;

    @Input()
    private esconderCampoCorrespondencia: string;

    @Input()
    private esconderCampoPais: string;

    @Input()
    public readOnly: Boolean = false;

    enderecoForm: FormGroup;

    groupPais: FormGroup;

    paises: Pais[];
    //private builder:FormBuilder;
    cepCorreio:CepCorreio;
    municipios: Municipio[] = [];
    ufs: UF[];

    enderecoContato: EnderecoContato;

    // Indica se o endereço selecionado é estrangeiro (país que reside é diferente de BR)
    public isEndBrasil: Boolean = true;

    public mascaraCep: Array<string | RegExp>

    /**
     * Método principal de construção do formulário
     * @author Rafael Pizão
     */
    buildForm() {
        this.groupPais = this.fb.group({
            'id':[this._BRASIL_ID, Validators.required]
        });

        // Formulário com as validações padrão para endereço Brasil.
        this.enderecoForm = this.fb.group({
            'pais': this.groupPais,
            cep: [null, Validators.required],
            tipoLogradouro: [null, Validators.required],
            nomeLogradouro: [null, Validators.required],
            numero: [null, Validators.required],
            complemento: null,
            bairro: [null, Validators.required],
            municipio: [null, Validators.required],
            uf: [null, Validators.required],
            enderecoEstrangeiro: null,
            'principal': [null, null],
            correspondencia: [false, null]
        });

        //this.criarFormsErro(this.enderecoForm);

    }

    /**
     * Construtor
     * @param serviceDominio - serviço de dominio para buscar os dominios
     * @param correioService - serviço de cep para consultar os endereços
     * @author Rafael Pizão
     */
    constructor(private fb: FormBuilder, private serviceDominio: DominioService, private correioService: CepCorreioService, translate: TranslateService){
        super();
        this.cepCorreio = new CepCorreio();
        this.translate = translate;
        this.buildForm();
        this.mascaraCep = [/[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/, '\-', /[0-9]/,/[0-9]/,/[0-9]/]
    }

    ngOnInit() {

        this.translate.get("enderecoComponent").subscribe(res => {
            this._formLabels = res;
            //Chamada aos métodos default para que a tela funcione com validações
            this.criarMensagemValidacaoPorFormGroup(this.enderecoForm);
            this.setMensagensErro(this.enderecoForm);
        });

        this.serviceDominio.getPaises().then(res => {
            this.paises = res;
        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });

        this.serviceDominio.getUfsSemNaoInformado().then(res => {
            this.ufs = res;
            this.municipios = [];
        }, (error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
        });
    }

    /**
     * Método para setar os valores default no formulario
     *
     * @author Fillipe Queiroz
     * @memberof ContatoPacienteComponent
     */
    setValoresPadroes() {
        this.groupPais.get('id').setValue(this._BRASIL_ID);
    }

    /**
     * Configura as obrigatoriedades para o formulário quando o endereço é nacional.
     * @author Rafael Pizão
     */
    configEndNacionalForm(){
        this.setFieldRequired(this.enderecoForm, 'cep');
        this.setFieldRequired(this.enderecoForm, 'tipoLogradouro');
        this.setFieldRequired(this.enderecoForm, 'nomeLogradouro');
        this.setFieldRequired(this.enderecoForm, 'numero');
        this.setFieldRequired(this.enderecoForm, 'bairro');
        this.setFieldRequired(this.enderecoForm, 'municipio');
        this.setFieldRequired(this.enderecoForm, 'uf');
        this.resetFieldRequired(this.enderecoForm,'enderecoEstrangeiro');
        this.enderecoForm.get('enderecoEstrangeiro').setValue(null);
    }

    /**
     * Configura as obrigatoriedades para o formulário quando o endereço é estrangeiro.
     * @author Rafael Pizão
     */
    configEndEstrangeiroForm(){
        this.setFieldRequired(this.enderecoForm,'enderecoEstrangeiro');
        this.resetFieldRequired(this.enderecoForm, 'cep');
        this.resetFieldRequired(this.enderecoForm, 'tipoLogradouro');
        this.resetFieldRequired(this.enderecoForm, 'nomeLogradouro');
        this.resetFieldRequired(this.enderecoForm, 'numero');
        this.resetFieldRequired(this.enderecoForm, 'bairro');
        this.resetFieldRequired(this.enderecoForm, 'municipio');
        this.resetFieldRequired(this.enderecoForm, 'uf');
        this.enderecoForm.get('cep').setValue(null);
        this.enderecoForm.get('tipoLogradouro').setValue(null);
        this.enderecoForm.get('nomeLogradouro').setValue(null);
        this.enderecoForm.get('numero').setValue(null);
        this.enderecoForm.get('bairro').setValue(null);
        this.enderecoForm.get('municipio').setValue(null);
        this.enderecoForm.get('uf').setValue(null);
        this.municipios = [];
        this.enderecoForm.get('principal').setValue(false);
        this.enderecoForm.get('correspondencia').setValue(false);
    }

    /**
     * Verifica se a residência atual do paciente é no brasil ou não,
     * definindo qual o formulário de endereço será solicitado no preenchimento.
     * @author Rafael Pizão
     */
    verificarSeResideBrasil(){
        let paisReside:number  = (<FormControl> this.groupPais.controls["id"]).value;
        this.isEndBrasil = (paisReside == this._BRASIL_ID);
        this.validateForm();
        if(this.isEndBrasil){
            this.configEndNacionalForm();
        } else {
            this.configEndEstrangeiroForm();
        }
        this.clearMensagensErro(this.enderecoForm);
        this.setMensagensErro(this.enderecoForm);
    }

    /**
     * reseta as mensagens de erro de telefones
     * @author Rafael Pizão
     */
    resetMsgErrors(){
        this.formErrors['numeroCep'] = '';
    }

    /**
     * verifica as obrigatoriedades de cada campo e de telefones
     * @author Rafael Pizão
     * @return boolean se foi validado com sucesso ou não
     */
    verificarValidacaoEndereco():boolean{
        this.setMensagensErro(this.enderecoForm);
        let valid: boolean = this.enderecoForm.valid;

        return valid;
    }

    /**
     * busca os dados de endereço através do serviço de CEP
     * @author Rafael Pizão
     */
    buscarLogradouroPorCep(): void {
        let cep:String = String(FormatterUtil.obterCEPSemMascara( this.enderecoForm.controls.cep.value ));
        if(cep == null || cep == '' || cep == "undefined"){
            this.setMensagemErroPorCampo('cep');
            return;
        }

        this.correioService.getLogradouroPorCep(cep).then(res => {
            if(res.cep == null){
                this.cepCorreio = new CepCorreio();

                this.formErrors["cep"] = "CEP não encontrado";
            } else {
                this.formErrors["cep"] = "";
                this.cepCorreio = res;
                this.obterMunicipiosPorUf(this.cepCorreio.uf.toString()).then(ok => {
                    let muninicpio: Municipio = null;
                    if (this.municipios) {
                        muninicpio = this.municipios.find(muninicpioToFind => muninicpioToFind.codigoIbge === this.cepCorreio.codigoIbge );
                    }

                    this.enderecoForm.patchValue({
                        bairro: this.cepCorreio.bairro,
                        tipoLogradouro : this.cepCorreio.tipoLogradouro,
                        nomeLogradouro: this.cepCorreio.logradouro,
                        municipio: muninicpio ? muninicpio.id : null,
                        uf: this.cepCorreio.uf
                    });
                });

            }
        },(error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });
    }

    /**
     * retorna o endereço de contato preenchido no formulário
     * @author Bruno Sousa
     */
    obterEndereco(): EnderecoContato {
        let enderecoContato: EnderecoContato = new EnderecoContato();
        let enderecoContatoPaisId: number = this.enderecoForm.get("pais.id").value || null;
        if (enderecoContatoPaisId) {
            let pais: Pais = this.paises.find(obj => obj.id == enderecoContatoPaisId);
            enderecoContato.pais = new Pais(enderecoContatoPaisId, pais.nome);
        }

        if (enderecoContato.pais && enderecoContato.pais.nome == this._PAIS_BRASIL) {
            enderecoContato.cep = FormatterUtil.obterCEPSemMascara( this.enderecoForm.get("cep").value ) || null;
            enderecoContato.tipoLogradouro = this.enderecoForm.get("tipoLogradouro").value || null;
            enderecoContato.nomeLogradouro = this.enderecoForm.get("nomeLogradouro").value || null;
            enderecoContato.numero = this.enderecoForm.get("numero").value || null;
            enderecoContato.complemento = this.enderecoForm.get("complemento").value || null;
            enderecoContato.bairro = this.enderecoForm.get("bairro").value || null;

            enderecoContato.municipio = new Municipio(this.enderecoForm.get("municipio").value || null,
                this.enderecoForm.get("uf").value );
            if (this.municipios) {
                if(this.cepCorreio.codigoIbge){
                    enderecoContato.municipio.descricao = this.municipios.find(muninicpioToFind => muninicpioToFind.codigoIbge === this.cepCorreio.codigoIbge )
                        .descricao;
                }
            }
            enderecoContato.enderecoEstrangeiro = null;
        }
        else {
            enderecoContato.enderecoEstrangeiro = this.enderecoForm.get("enderecoEstrangeiro").value || null;
            enderecoContato.cep = null;
            enderecoContato.tipoLogradouro = null;
            enderecoContato.nomeLogradouro = null;
            enderecoContato.numero = null;
            enderecoContato.complemento = null;
            enderecoContato.bairro = null;
            enderecoContato.municipio = null;
        }
        enderecoContato.principal = this.enderecoForm.get("principal").value || false;
        enderecoContato.correspondencia = this.enderecoForm.get("correspondencia").value || false;
        enderecoContato.excluido = false;
        return enderecoContato;
    }

    public form(): FormGroup {
        return this.enderecoForm;
    }

    // Override
    public preencherFormulario(endContato: EnderecoContato): void {
        if(endContato){
            this.setPropertyValue("principal", endContato.principal);
            this.setPropertyValue("correspondencia", endContato.correspondencia);
            if (endContato.pais) {
                this.setPropertyValue("pais.id", endContato.pais.id);
                if (endContato.pais.id == this._BRASIL_ID) {

                    this.setPropertyValue("cep", endContato.cep + "");
                    this.form().get("cep").updateValueAndValidity();
                    this.setPropertyValue("tipoLogradouro", endContato.tipoLogradouro);
                    this.setPropertyValue("nomeLogradouro", endContato.nomeLogradouro);
                    this.setPropertyValue("bairro", endContato.bairro);
                    this.setPropertyValue("numero", endContato.numero);
                    this.setPropertyValue("complemento", endContato.complemento);

                    if (endContato.municipio && endContato.municipio.uf && endContato.municipio.uf.sigla) {
                        this.obterMunicipiosPorUf(endContato.municipio.uf.sigla).then(ok => {
                            this.setPropertyValue("municipio", endContato.municipio ? endContato.municipio.id : null);
                            this.setPropertyValue("uf", endContato.municipio && endContato.municipio.uf ? endContato.municipio.uf.sigla : null);
                            this.verificarSeResideBrasil();
                        });
                    } else {
                        this.setPropertyValue("municipio", null);
                        this.setPropertyValue("uf", null);
                    }
                } else {
                    this.setPropertyValue("enderecoEstrangeiro", endContato.enderecoEstrangeiro);
                }
            }
            this.verificarSeResideBrasil();
        }
    }

    nomeComponente(): string {
        return null;
    }

    deveEsconderCampoPrincipal(): boolean {
        return this.esconderCampoPrincipal == "true";
    }

    deveEsconderCampoCorrespondencia(): boolean {
        return this.esconderCampoCorrespondencia == "true";
    }

    deveEsconderCampoPais(): boolean {
      return this.esconderCampoPais == "true";
    }

    public desabilitarCampoPais(): void {
        this.form().get("pais").get("id").disable();
    }

    private obterMunicipiosPorUf(sigla: string): Promise<Boolean> {

        return new Promise((resolve, reject) => {
            this.serviceDominio.listarMunicipiosPorUf(sigla).then(res => {
                if (res) {
                    res.forEach(municipio => {
                        this.municipios.push(new Municipio().jsonToEntity(municipio));
                    });
                }
                resolve(true);
            },
            (error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
                reject(false);
            });
        });
    }

    public carregarMunicipioPorUf(sigla: string) {
        this.municipios = [];
        if (sigla && sigla !== '') {
            this.obterMunicipiosPorUf(sigla);
        }
    }

};
