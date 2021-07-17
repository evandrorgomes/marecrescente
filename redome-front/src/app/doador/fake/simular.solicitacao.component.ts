import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ErroMensagem } from '../../shared/erromensagem';
import { BaseForm } from '../../shared/base.form';
import { SolicitacaoDTO } from '../../paciente/busca/analise/solicitacao.dto';
import { MatchService } from '../solicitacao/match.service';
import { SolicitacaoService } from '../solicitacao/solicitacao.service';
import { Doador } from '../doador';

/**
 * Classe que representa o componente de busca.
 * @author Filipe Paes
 */
@Component({
    selector: "simular-solicitacao",
    templateUrl: './simular.solicitacao.component.html'
})
export class SimularSolicitacaoComponent extends BaseForm<Doador> implements OnInit{
    TIPO_MATCH: string = "M";
    TIPO_SOLICITACAO: string = "S";

    public data: Array<string | RegExp>;
    
    @ViewChild("modalMsg")
    private modalMsg;

    public solicitacaoForm: FormGroup;

    constructor(private fb: FormBuilder, translate: TranslateService
        , private solicitacaoService: SolicitacaoService
        , private matchService: MatchService) {
        super();
        this.translate = translate;
        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/];
        this.buildForm();
    }

    buildForm(){
        this.solicitacaoForm = this.fb.group({
            'rmr': null,
            'idDoador': [null, Validators.required],
            'tipo': null,
            'tipoTela': [null, Validators.required]
        });
        this.criarMensagemValidacaoPorFormGroup(this.solicitacaoForm);
    }
    ngOnInit(): void {
        this.translate.get("doadores").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.solicitacaoForm);
            this.setMensagensErro(this.solicitacaoForm);
        });
        this.solicitacaoForm.controls['tipo'].setValue("M");
    }

    enviar(): void {
        if (this.validateForm()) {
            let tipoTela:string = this.solicitacaoForm.get("tipoTela").value;
            let tipo:number = this.solicitacaoForm.get("tipo").value;
            if(tipoTela == this.TIPO_MATCH){
                this.matchService.criarMatch(this.solicitacaoForm.get("rmr").value
                , this.solicitacaoForm.get("idDoador").value).then(res=>{
                    this.modalMsg.mensagem = res.mensagem;
                    this.modalMsg.abrirModal();
                },
                (error: ErroMensagem) => {
                    error.listaCampoMensagem.forEach(obj => {
                        this.modalMsg.mensagem = obj.mensagem;
                    })
                    this.modalMsg.abrirModal();
                });
            } else {
                /* this.solicitacaoService.criarPedido(new SolicitacaoDTO(this.solicitacaoForm.get("rmr").value,
                    this.solicitacaoForm.get("idDoador").value,tipo))
                .then(res=>{
                    this.modalMsg.mensagem = res.mensagem;
                    this.modalMsg.abrirModal();
                },
                (error: ErroMensagem) => {
                    error.listaCampoMensagem.forEach(obj => {
                        this.modalMsg.mensagem = obj.mensagem;
                    })
                    this.modalMsg.abrirModal();
                }); */
            }
        }
    }

    colocarERetirarTipoSolicitacaoComoRequired(){
        if(this.form().get("tipoTela").value == this.TIPO_SOLICITACAO){
            this.setFieldRequired(this.form(), "tipo");
        }
        else{
            this.form().get('tipo').clearValidators();
        }
    }

    /**
     * Nome do componente atual
     * 
     * @returns {string} 
     * @memberof ConferenciaComponent
     */
    nomeComponente(): string {
        return "SimularSolicitacaoComponent";
    }

    public form(): FormGroup {
        return this.solicitacaoForm;
    }
    
    public preencherFormulario(entidade: any): void {
        throw new Error('Method not implemented.');
    }

};