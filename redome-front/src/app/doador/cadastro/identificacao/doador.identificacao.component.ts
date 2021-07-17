import { Doador } from 'app/doador/doador';
import { PacienteUtil } from '../../../shared/paciente.util';
import { DoadorService } from '../../doador.service';
import { FormatterUtil } from '../../../shared/util/formatter.util';
import { TranslateService } from '@ngx-translate/core';
import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { BaseForm } from '../../../shared/base.form';
import { DoadorNacional } from '../../doador.nacional';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ValidateDataMenorQueHoje } from '../../../validators/data.validator';
import { ErroMensagem } from "../../../shared/erromensagem";
import { AutenticacaoService } from "../../../shared/autenticacao/autenticacao.service";
import { DoadorUtil } from 'app/shared/util/doador.util';
import { MascaraUtil } from 'app/shared/util/mascara.util';
import { DateMoment } from '../../../shared/util/date/date.moment';
import { DataUtil } from '../../../shared/util/data.util';
import { DateFormatter } from 'ngx-bootstrap';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { EventEmitterService } from 'app/shared/event.emitter.service';

@Component({
    selector: "doadoridentificacao",
    templateUrl: './doador.identificacao.component.html',
    styleUrls: ['./../../doador.css']
})
export class DoadorIdentificacaoComponent extends BaseForm<DoadorNacional> implements OnInit {

    public static HOUVE_ATUALIZACAO_SEXO : string = "houveAtaualizacaoSexo";
    public identificacaoForm: FormGroup;

    mostraDados: String = '';
    mostraFormulario: String = 'hide';
    exibeNomeCivil:boolean = false;
    private _sexo: string;
    private _doador: DoadorNacional;

    @ViewChild('modalMsg') 
    public modalMsg;

    public data: Array<string | RegExp>
    public cpf: Array<string | RegExp>
    
    private _esconderLinkAlterarIdentificacao: boolean = false;

    @Input()
    set esconderLinkAlterarIdentificacao (value: string) {
        if (!value) {
            this._esconderLinkAlterarIdentificacao = true;
        } else {
            this._esconderLinkAlterarIdentificacao = value == 'true' ? true : false;
        }
    }

    constructor(private fb: FormBuilder, translate: TranslateService, private doadorService:DoadorService,
        private autenticacaoService:AutenticacaoService) {
        super();
        this.translate = translate;
        this.cpf = MascaraUtil.cpf;
        this.data = MascaraUtil.data;
        this.buildForm();
    }
    
    ngOnInit(): void {
        this.translate.get("DoadorIdentificacao").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.identificacaoForm);
            this.setMensagensErro(this.identificacaoForm);
        });
    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações 
     * @author Bruno Sousa
     */
    buildForm() {
        this.identificacaoForm = this.fb.group({
            'cpf': [null],
            'dataNascimento': [null], 
            'nomeMae': [null, Validators.required],
            'nome': [null, Validators.required],
            'nomeSocial': [null],
            'rg': [null],
            'orgaoExpedidor': [null],
            'sexo': [Validators.required]

        });
        
    }

    public form(): FormGroup {
        return this.identificacaoForm;
    }

    public preencherFormulario(doador: DoadorNacional): void {
        this.clearForm();
        this._doador = doador;
        this.setPropertyValue('nome', this._doador.nome)
        this.setPropertyValue('nomeSocial', this._doador.nome)
        this.setPropertyValue('cpf', this._doador.cpf);
        this.setPropertyValue('dataNascimento',  FormatterUtil.aplicarMascaraData(this._doador.dataNascimento) );
        this.setPropertyValue('nomeMae', this._doador.nomeMae);
        this.setPropertyValue('rg', this._doador.rg);
        this.setPropertyValue('orgaoExpedidor', this._doador.orgaoExpedidor);
        this.setPropertyValue('sexo', this._doador.sexo);
    }

    nomeComponente(): string {
        return "";
    }

    public editar() {
        this.mostraDados = 'hide';
        this.mostraFormulario = '';
    }

    cancelarEdicao() {
        this.mostraFormulario = 'hide';
        this.mostraDados = '';        
    }

	public get doador(): DoadorNacional {
		return this._doador;
    }
    
    marcarNomeSocial(){
        this.exibeNomeCivil = !this.exibeNomeCivil;
        if(this.exibeNomeCivil){
            this.setPropertyValue('nomeSocial', this._doador.nomeSocial);
        }else{
            this.setPropertyValue('nomeSocial', this._doador.nome);
        }
        
    }

    salvarIdentificacao() {
        this._doador.nome = this.identificacaoForm.get('nome').value;
        this._doador.nomeSocial = this.identificacaoForm.get('nomeSocial').value;
        this._doador.cpf = DoadorUtil.obterCPFSemMascara(this.identificacaoForm.get('cpf').value);
        this._doador.nomeMae = this.identificacaoForm.get('nomeMae').value;
        this._doador.rg = this.identificacaoForm.get('rg').value;
        this._doador.orgaoExpedidor = this.identificacaoForm.get('orgaoExpedidor').value;
        this._sexo = this._doador.sexo;
        this._doador.sexo = this.identificacaoForm.get('sexo').value;
        this._doador.dataNascimento = DataUtil.toDate(this.identificacaoForm.get('dataNascimento').value, DateTypeFormats.DATE_ONLY);
        if (this.validateForm()) {
            this.doadorService.alterarIdentificacao(this._doador.id, this._doador).then(
                res => {
                    if (this.houveAtualizacaoSexo()) {
                        EventEmitterService.get(DoadorIdentificacaoComponent.HOUVE_ATUALIZACAO_SEXO).emit();
                    }
                    this.cancelarEdicao();
                },
                (error: ErroMensagem) => {
                    error.listaCampoMensagem.forEach(obj => {
                        this.modalMsg.mensagem = this.getLabel(obj.campo) + " " + obj.mensagem;
                    })
                    this.modalMsg.abrirModal();
                });
        }
    }

    private houveAtualizacaoSexo(): boolean{
        if (this._sexo != this.identificacaoForm.get('sexo').value) {
            return true;
        }
        return false;
    }

    deveEsconderLinkAlterarIdentificacao(): boolean {
        return this._esconderLinkAlterarIdentificacao || !this.temPermissaoParaAlterarIdentificacao();
    }

    private temPermissaoParaAlterarIdentificacao(): boolean {
        return this.autenticacaoService.temRecurso('ATUALIZAR_IDENTIFICACAO_DOADOR');
    }

    /**
     * Aplica a mascara no cpf
     * 
     * @private
     * @param {string} texto 
     * @param {string} mascara 
     * @returns {string} 
     * 
     * @memberOf DetalheComponent
     */
    aplicarMascaraCpf(texto: string): string {

        return FormatterUtil.aplicarMascaraCpf(texto);

    }

}