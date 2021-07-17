import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { AutenticacaoService } from '../../../../shared/autenticacao/autenticacao.service';
import { BaseForm } from '../../../../shared/base.form';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { EventEmitterService } from '../../../../shared/event.emitter.service';
import { Doador } from "../../../doador";
import { DoadorNacional } from '../../../doador.nacional';
import { DoadorService } from '../../../doador.service';
import { RessalvaDoador } from '../../../ressalva.doador';
import { RessalvaDoadorDTO } from './../../../../shared/dto/ressalva.doador.dto';
import { RessalvaService } from './ressalva.service';

/**
 * Classe que representa o componente de adição de ressalva.
 * @author Fillipe Queiroz
 */
@Component({
    selector: "ressalva",
    moduleId: module.id,
    templateUrl: "./ressalva.component.html"
})
export class RessalvaComponent extends BaseForm<any> implements OnInit {

    public ressalvaForm: FormGroup;

    public mostraDados: String = '';
    public mostraFormulario: String = 'hide';
    public ressalvas: RessalvaDoador[];
    public recarregarCabecalhoDoador: boolean = false;
    public static RECARREGAR_CABECALHO: string = 'recarregarCabecalhoListener';

    private _esconderLinkIncluirRessalva: boolean = false;
    private _esconderLinkExcluirRessalva: boolean = false;
    private _salvarEmMemoria: boolean = false;
    private idDoador: number;

    @ViewChild('modalMsg')
    public modalMsg;

    @Input()
    public set esconderLinkIncluirRessalva(value: string) {
        if (!value) {
            this._esconderLinkIncluirRessalva = true;
        } else {
            this._esconderLinkIncluirRessalva = value == 'true' ? true : false;
        }
    }

    @Input()
    public set salvarEmMemoria(value: string) {
        if (!value) {
            this._salvarEmMemoria = false;
        } else {
            this._salvarEmMemoria = value == 'true' ? true : false;
        }
    }

    @Input()
    public set esconderLinkExcluirRessalva(value: string) {
        if (!value) {
            this._esconderLinkExcluirRessalva = true;
        } else {
            this._esconderLinkExcluirRessalva = value == 'true' ? true : false;
        }
    }

    /**
     * Popular o componente de ressalvas a partir do dmr do doador.
     * @param  {number} idDoador
     */
    public popularRessalvas(idDoador: number) {
        this.idDoador = idDoador;
        this.ressalvaService.listarRessalvas(idDoador).then(res => {
            this.ressalvas = res;
        },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });

    }

    public excluirRessalva(idRessalva: number) {
        this.ressalvaService.excluirRessalva(idRessalva).then(res => {
            let index = this.ressalvas.findIndex(ressalva => ressalva.id == idRessalva)
            if (index > -1) {
                this.ressalvas.splice(index, 1);
            }
            EventEmitterService.get(RessalvaComponent.RECARREGAR_CABECALHO).emit();
        });
    }

    constructor(private fb: FormBuilder, translate: TranslateService,
        private autenticacaoService: AutenticacaoService,
        private ressalvaService: RessalvaService,
        private doadorService: DoadorService) {
        super();
        this.translate = translate;
        this.buildForm();
    }

    ngOnInit(): void {
        this.translate.get("ressalva").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.ressalvaForm);
            this.setMensagensErro(this.ressalvaForm);
        });
    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações 
     * @author Fillipe Queiroz
     */
    buildForm() {
        this.ressalvaForm = this.fb.group({
            'ressalva': [null, Validators.required]
        });

    }
    /**
     * @returns FormGroup
     */
    public form(): FormGroup {
        return this.ressalvaForm;
    }
    /**
     * @param  {DoadorNacional} doador
     * @returns void
     */
    public preencherFormulario(doador: Doador): void {
        return;
    }

    nomeComponente(): string {
        return "RessalvaComponent";
    }

    private editar() {
        this.mostraDados = 'hide';
        this.mostraFormulario = '';
        this.ressalvaForm.get('ressalva').setValue(null);
    }

    cancelarEdicao() {
        this.mostraFormulario = 'hide';
        this.mostraDados = '';
        this.resetFieldRequiredSemForm('ressalva')
    }


    salvarRessalva() {
        this.setFieldRequiredSemForm('ressalva');

        if (this.validateForm()) {

            let ressalvaDoador: RessalvaDoador = new RessalvaDoador();
            ressalvaDoador.observacao = this.ressalvaForm.get('ressalva').value;

            if (this._salvarEmMemoria) {
                if(!this.ressalvas){
                    this.ressalvas = [];
                }
                this.ressalvas.push(ressalvaDoador);
                this.cancelarEdicao();
            } else {
                let ressalvaDoadorDTO = new RessalvaDoadorDTO();
                ressalvaDoadorDTO.idDoador = this.idDoador;
                ressalvaDoadorDTO.observacao = ressalvaDoador.observacao;

                this.ressalvaService.salvarRessalva(ressalvaDoadorDTO).then(res => {
                    this.cancelarEdicao();
                    ressalvaDoador.id = res.object;
                    this.ressalvas.push(ressalvaDoador);
                    if (this.recarregarCabecalhoDoador) {
                        EventEmitterService.get(RessalvaComponent.RECARREGAR_CABECALHO).emit();
                    }
                })
            }
        }

    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalMsg.mensagem = obj.mensagem;
        })
        this.modalMsg.abrirModal();
    }


    public deveEsconderLinkIncluirRessalva(): boolean {
        return this._esconderLinkIncluirRessalva || !this.temPermissaoParaIncluirRessalva();
    }

    public permitirExclusaoRessalva(): boolean {
        return !this._esconderLinkExcluirRessalva && this.temPermissaoParaExcluirRessalva();
    }

    private temPermissaoParaIncluirRessalva(): boolean {
        return this.autenticacaoService.temRecurso('INCLUIR_RESSALVA');
    }

    private temPermissaoParaExcluirRessalva(): boolean {
        return this.autenticacaoService.temRecurso('EXCLUIR_RESSALVA_DOADOR');
    }

}