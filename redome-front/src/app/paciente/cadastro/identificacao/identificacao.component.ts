import { OnDestroy } from '@angular/core';
import { MessageBox } from './../../../shared/modal/message.box';
import { PacienteService } from '../../paciente.service';
import { PacienteUtil } from '../../../shared/paciente.util';
import { TranslateService } from '@ngx-translate/core';
import { ErroMensagem } from '../../../shared/erromensagem';
import { PacienteConstantes } from '../../paciente.constantes';
import { BaseForm } from '../../../shared/base.form';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { Component, OnInit, Input, Output, EventEmitter, ViewChild, AfterViewInit } from '@angular/core';
import { Paciente } from '../../paciente';
import { FormGroup, FormBuilder, FormControl, Validators, AbstractControl } from '@angular/forms';
import { ValidateDataMenorQueHoje } from '../../../validators/data.validator'
import { Injectable } from '@angular/core';
import { UF } from '../../../shared/dominio/uf'
import { Pais } from '../../../shared/dominio/pais'
import { Etnia } from '../../../shared/dominio/etnia'
import { Raca } from '../../../shared/dominio/raca'
import { DominioService } from '../../../shared/dominio/dominio.service'
import { DataUtil } from '../../../shared/util/data.util';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { Cid } from 'app/shared/dominio/cid';
import { ErroUtil } from 'app/shared/util/erro.util';


@Component({
    selector: 'identificacao',
    moduleId: module.id,
    templateUrl: './identificacao.component.html'
})
export class IdentificacaoComponent extends BaseForm<Paciente> implements OnInit, OnDestroy {

    public identificacaoForm: FormGroup;

    private _rmr: number;
    private _cidAtual: Cid;

    @Input()
    etapaAtual: String;

    @Output()
    public cpfChange: EventEmitter<string> = new EventEmitter<string>();

    @Output()
    public cnsChange: EventEmitter<string> = new EventEmitter<string>();

    @Output()
    public outrasReferenciasChange: EventEmitter<string> = new EventEmitter<string>();

    @Output()
    public calcularIdade: EventEmitter<number> = new EventEmitter<number>();

    @ViewChild('modalMsgCpf')
    private modal;

    // Valor do check se o nome da mãe é inexistente ou não.
    public maeDesconhecida:Boolean = false;
    public mask: Array<string | RegExp>
    public data: Array<string | RegExp>

    groupResponsavel: FormGroup;
    isMenorIdade: boolean = false;

    private _mensagemCid: string;

    private disabilitarDataNascimento: boolean = false;

    ngOnInit(): void {
        this.translate.get("pacienteForm.identificacaoGroup").subscribe(res => {
            this._formLabels = res;
            //this.criarMensagemValidacaoPorFormGroup(this.identificacaoForm);
            this.criarValidacoesCustomizadas();

            this.criarMensagensErro(this.identificacaoForm);
            this.setMensagensErro(this.identificacaoForm);

        });

        this.translate.get("pacienteForm.diagnosticoGroup.mensagemCid").subscribe(res=> {
            this._mensagemCid = res;
        });
    }

    ngOnDestroy(): void {
        this.calcularIdade.unsubscribe();
    }

    /**
     * Reseta o formulário limpando mensagens de error e valores dos campos
     *
     * @author Fillipe Queiroz
     * @memberof IdentificacaoComponent
     */
    resetaFormulario(){
        this.clearMensagensErro(this.identificacaoForm);
        this.identificacaoForm.reset();
    }

    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações
     * @author Fillipe Queiroz
     */
    buildForm() {

        this.groupResponsavel = this.fb.group({
            'cpf': [null, null],
            'nome': [null, null],
            'parentesco': [null, null]
        });

        this.identificacaoForm = this.fb.group({
            'cpf': [null, Validators.required],
            'dataNascimento': [null,Validators.compose([Validators.required,ValidateDataMenorQueHoje])],
            'cns': [null, Validators.required],
            'nomeMae': [null],
            'nome': [null, Validators.required],
            'responsavel': this.groupResponsavel,
            'maeDesconhecida': null
        });
        this.criarFormsErro(this.identificacaoForm);
        //Chamada aos métodos default para que a tela funcione com validações
        //Opcionais

        return this.identificacaoForm;

    }

    /**
     * Cria validações customizadas de data
     * o tipo deve ser o mesmo tipo retornador pela classe validators
     * @author Fillipe Queiroz
     */
    criarValidacoesCustomizadas(){
        this.translate.get("mensagem.erro.menorDataAtual", {campo: this.getLabel('dataNascimento')}).subscribe(res=> {
            var campoMensagem: CampoMensagem = new CampoMensagem();
            campoMensagem.campo = 'dataNascimento';
            //Tipo que é retornado do validators
            campoMensagem.tipo = PacienteConstantes.VALIDATOR_DATA;
            campoMensagem.mensagem = res;
            this.validationMessages.push(campoMensagem);
        });
    }

    /**
     * Construtor padrão
     * @param serviceDominio serviço injetado para buscar os dominios
     * @author Fillipe Queiroz
     */
    constructor(private fb: FormBuilder, private serviceDominio: DominioService, private servicePaciente: PacienteService, translate: TranslateService,
        private messageBox: MessageBox) {
        super();
        this.translate = translate;
        this.mask = [
            /[0-9]/,/[0-9]/,/[0-9]/,
             '.', /[0-9]/,/[0-9]/,/[0-9]/,
              '.', /[0-9]/,/[0-9]/,/[0-9]/,
               '-', /[0-9]/,/[0-9]/
            ];
        this.data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]


        this.buildForm();
    }

    /**
     *  Metodo que valida os campos obrigatórios por cpf e cns
     * @author Bruno Sousa
     */
    realizarValidacoesDinamicasPorCpfECns() {
        var cns = this.getField(this.identificacaoForm, "cns").value;
        var cpf = PacienteUtil.obterCPFSemMascara( this.getField(this.identificacaoForm, "cpf").value );
        var dataNascimento = PacienteUtil.obterDataSemMascara(this.identificacaoForm.get('dataNascimento').value);

        //Um dos dois documentos são obrigatórios.
        //Se CPF e CNS não forem estiver preenchidos, marcar ambos como required.
        if(cpf){
            //... se CPF foi informado, CNS poderá ser nulo.
            this.resetFieldRequired(this.identificacaoForm,'cns');
        } else if(cns){
            // ... se CNS foi informado, CPF poderá ser nulo.
            this.resetFieldRequired(this.identificacaoForm,'cpf');
        }
        else if (!cpf && !cns && this.isMaiorIdade(dataNascimento) ) {
            // Por default, os documentos são obrigatórios.
            this.setFieldRequired(this.identificacaoForm,'cns');
            this.setFieldRequired(this.identificacaoForm,'cpf');
        }

        this.clearMensagensErro(this.identificacaoForm);
        this.criarFormsErro(this.identificacaoForm);
        this.setMensagensErro(this.identificacaoForm);
    }

    /**
     * Método para verificar se ao menos um atributo de
     * identificação foi preenchido
     *
     * @returns {boolean}
     * @memberof IdentificacaoComponent
     */
    verificarPreenchimento():boolean{
        return this.identificacaoForm.get('nomeMae').value != null ||
        this.identificacaoForm.get('nome').value != null ||
        this.identificacaoForm.get('dataNascimento').value != null ||
        this.identificacaoForm.get('cpf').value != null ||
        this.identificacaoForm.get('cns').value != null ||
        this.groupResponsavel.get('nome').value != null ||
        this.groupResponsavel.get('cpf').value != null ||
        this.groupResponsavel.get('parentesco').value != null;
    }

    /**
     *  Metodo que valida os campos obrigatórios
     * @author Fillipe Queiroz
     */
    realizarValidacoesDinamicasPorData() {
        let cns = this.getField(this.identificacaoForm, "cns").value;
        let cpf = PacienteUtil.obterCPFSemMascara( this.getField(this.identificacaoForm, "cpf").value );
        let dataNascimento = PacienteUtil.obterDataSemMascara(this.identificacaoForm.get('dataNascimento').value);
        let maiorIdade: boolean = dataNascimento ? this.isMaiorIdade(dataNascimento) : false;
        // Por default, os documentos são obrigatórios.

        if(maiorIdade){
            //Quando é maior de idade não devo validar o cpf do responsável:
            this.resetFieldRequired(this.groupResponsavel,'cpf');
            this.resetFieldRequired(this.groupResponsavel,'nome');
            this.resetFieldRequired(this.groupResponsavel,'parentesco');

            this.realizarValidacoesDinamicasPorCpfECns();

            // Maior de idade não tem obrigatoriedade em informar o nome da mãe.
            this.resetFieldRequired(this.identificacaoForm,'nomeMae');

        } else {
            // Se for menor de idade e não tiver preenchido CNS e CPF devo obrigar a inserir os seguintes campos:
            // O documentos para o menor de idade não são obrigatórios.
            if(dataNascimento){
                // Só desabilitar a obrigatoriedade quando a data for informada de fato.
                this.resetFieldRequired(this.identificacaoForm, 'cns');
                this.resetFieldRequired(this.identificacaoForm, 'cpf');
            }

            // Se o paciente for menor de idade e não tiver documentos, o nome da mãe é obrigatório.
            // Caso, ainda assim, o médico não possua a informação, deverá marcar o check indicando a inexistência.
            if(!cns && !cpf) {
                this.setFieldRequired(this.groupResponsavel,'cpf');
                this.setFieldRequired(this.groupResponsavel,'nome');
                this.setFieldRequired(this.groupResponsavel,'parentesco');

                // Se o médico informar não ter o nome da mãe do paciente, não será obrigatório.
                if(this.maeDesconhecida){
                    this.resetFieldRequired(this.identificacaoForm,'nomeMae');
                } else {
                    this.setFieldRequired(this.identificacaoForm,'nomeMae');
                }

            } else {
                // Se for menor, mas tiver documentos informados, não é obrigatório.
                this.resetFieldRequired(this.identificacaoForm,'nomeMae');

                this.resetFieldRequired(this.groupResponsavel,'cpf');
                this.resetFieldRequired(this.groupResponsavel,'nome');
                this.resetFieldRequired(this.groupResponsavel,'parentesco');
            }
        }
        this.clearMensagensErro(this.identificacaoForm);
        this.criarMensagemValidacaoPorFormGroup(this.identificacaoForm);
    }

    /**
     * Metodo que calcula se o paciente é maior de idade pela data de nascimento inserida
     * @author Fillipe Queiroz
     * @return boolean
     */
    isMaiorIdade(aniversario: Date): boolean {
        const MAIOR_IDADE = 18;
        let idade = DataUtil.calcularIdade(aniversario);
        return (idade >= MAIOR_IDADE);
    }

    public calcularIdadeEmit(){
        let dataNascimento = PacienteUtil.obterDataSemMascara(this.identificacaoForm.get('dataNascimento').value);
        let idadePaciente: number = DataUtil.calcularIdade(dataNascimento);
        this.calcularIdade.emit(idadePaciente);
    }

    /**
     * Método para verificar se o cpf existe
     * @author Rafael Pizão
     */
    validarCpfJaCadastrado(modal: any) {
        this.realizarValidacoesDinamicasPorCpfECns();
        this.formErrors['cpfUtilizado'] = '';
        let pacientePreview = new Paciente();
        pacientePreview.rmr = this._rmr;
        pacientePreview.cpf = PacienteUtil.obterCPFSemMascara(this.identificacaoForm.get('cpf').value);

        if (pacientePreview.cpf != null && new String(pacientePreview.cpf) != "" && !isNaN(Number(pacientePreview.cpf))) {
            this.servicePaciente.verificarDuplicidade(pacientePreview).then(
                result => {
                },
                (error: ErroMensagem) => {
                    error.listaCampoMensagem.forEach(obj => {
                        this.formErrors['cpfUtilizado'] = obj.mensagem;
                    })
                    modal.abrirModal();
                }
            );
        }
    }

    /**
     * Método para verificar se o cns existe
     * @author Rafael Pizão
     */
    validarCnsJaCadastrado(modal: any){
        this.realizarValidacoesDinamicasPorCpfECns();
        this.formErrors['cnsUtilizado'] = '';
        let pacientePreview = new Paciente();
        pacientePreview.rmr = this._rmr;
        pacientePreview.cns = this.identificacaoForm.get('cns').value;
        if (pacientePreview.cns != null && new String(pacientePreview.cns) != "" && !isNaN(Number(pacientePreview.cns))) {
            this.servicePaciente.verificarDuplicidade(pacientePreview).then(
                result => {
                },
                (error: ErroMensagem) => {
                    error.listaCampoMensagem.forEach(obj => {
                        this.formErrors['cnsUtilizado'] = obj.mensagem;
                    })
                    modal.abrirModal();
                }
            );
        }
    }

    /**
     * Método para validar os dados de nome do paciente, data nascimento e nome da mãe
     * @author Rafael Pizão
     */
    validarDadosJaCadastrado(modal){
        this.formErrors['dadosUtilizados'] = '';
        let paciente = new Paciente();
        paciente.rmr = this._rmr;
        paciente.nomeMae = this.identificacaoForm.get('nomeMae').value;
        paciente.nome = this.identificacaoForm.get('nome').value;
        paciente.dataNascimento = PacienteUtil.obterDataSemMascara( this.identificacaoForm.get('dataNascimento').value ) || null;
        paciente.cpf = PacienteUtil.obterCPFSemMascara(this.identificacaoForm.get('cpf').value);
        paciente.cns = this.identificacaoForm.get('cns').value;
        if (paciente.dataNascimento != null  &&
           (paciente.nome != null && new String(paciente.nome) != "") &&
           (paciente.nomeMae != null && new String(paciente.nomeMae) != "") ) {
            this.servicePaciente.verificarDuplicidade(paciente).then(
                result => {
                },
                (error: ErroMensagem) => {
                    error.listaCampoMensagem.forEach(obj => {
                        this.formErrors['dadosUtilizados'] += obj.mensagem;
                    })
                    modal.abrirModal();
                }
            );
        }
    }

    /**
     * Método chamado quando o médico não possui o nome da mãe do paciente.
     * @author Rafael Pizão
     */
    marcarMaeDesconhecida(){
        // Inverte o valor da variável referência do checkbox.
        this.maeDesconhecida = !this.maeDesconhecida;
        this.configurarCampoMaeCasoDesconhecido();
    }

    private configurarCampoMaeCasoDesconhecido(): void{
        if(this.maeDesconhecida){
            let nomeMaeField: FormControl =
                    <FormControl> this.getField( this.identificacaoForm, "nomeMae" );
            nomeMaeField.setValue(null);
            this.resetFieldRequired(this.identificacaoForm, "nomeMae");
        } else {
            this.setFieldRequired(this.identificacaoForm, "nomeMae");
        }
    }

    public form(): FormGroup{
        return this.identificacaoForm;
    }

    verificaMenorIdade() {
        let dataDeNascimento = PacienteUtil.obterDataSemMascara(this.identificacaoForm.get('dataNascimento').value);

        if(dataDeNascimento) {
            this.isMenorIdade = !this.isMaiorIdade(dataDeNascimento);
        }

        this.realizarValidacoesDinamicasPorData();
    }

    // Override
    public preencherFormulario(paciente:Paciente): void {
        this._rmr = paciente.rmr;
        if (paciente.diagnostico) {
            this._cidAtual = paciente.diagnostico.cid;
        }
        this.verificarCPFCadastrado(paciente.rmr, paciente.cpf);

        this.setPropertyValue('cpf', paciente.cpf);
        this.setPropertyValue('dataNascimento',
            DataUtil.toDateFormat(paciente.dataNascimento, DateTypeFormats.DATE_ONLY));
        this.verificaMenorIdade();
        // FIXME: Como tratar se paciente já existe na base!
        this.validarDadosJaCadastrado(this.modal);

        this.setPropertyValue('cns', paciente.cns);

        if(paciente.nomeMae != null){
            this.setPropertyValue('nomeMae', paciente.nomeMae);
        }
        else {
            this.setPropertyValue('maeDesconhecida', paciente.maeDesconhecida);
            this.maeDesconhecida = paciente.maeDesconhecida;
            this.configurarCampoMaeCasoDesconhecido();
        }

        this.setPropertyValue('nome', paciente.nome)

        if(paciente.responsavel){
            this.setPropertyValue('responsavel.cpf', paciente.responsavel.cpf);
            this.setPropertyValue('responsavel.nome', paciente.responsavel.nome);
            this.setPropertyValue('responsavel.parentesco', paciente.responsavel.parentesco);
        }

        if (paciente.temAvaliacaoIniciada || paciente.temAvaliacaoAprovada) {
            this.identificacaoForm.get("dataNascimento").disable();
        }
    }

    private verificarCPFCadastrado(rmr: number, cpf: string) {
        let paciente = new Paciente();
        paciente.rmr = rmr;
        paciente.cpf = PacienteUtil.obterCPFSemMascara(cpf);

        if(cpf) {
            this.servicePaciente.verificarDuplicidade(paciente).then(
                result => {},
                (error: ErroMensagem) => {
                    error.listaCampoMensagem.forEach(obj => {
                        this.formErrors['cpfUtilizado'] = obj.mensagem;
                    })
                    this.modal.abrirModal();
                }
            );
        }
    }

    nomeComponente(): string {
        return null;
    }

    public validateForm(): boolean {
        this.realizarValidacoesDinamicasPorData();
      //  this.validarDadosJaCadastrado(this.modal);
        return super.validateForm();
    }

    public verficarMensagemCid(): void {
        if (this._rmr) {
            let dataNascimento = PacienteUtil.obterDataSemMascara(this.identificacaoForm.get('dataNascimento').value);
            let idade: number = DataUtil.calcularIdade(dataNascimento);
            if (PacienteUtil.deveExibirMensagemCid(idade, this._cidAtual)) {
                this.messageBox.alert(this._mensagemCid).show();
            }
        }
    }


}
