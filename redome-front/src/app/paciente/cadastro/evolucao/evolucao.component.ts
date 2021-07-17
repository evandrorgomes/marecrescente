import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { DataUtil } from 'app/shared/util/data.util';
import { FileItem } from 'ng2-file-upload';
import { BaseForm } from '../../../shared/base.form';
import { CondicaoPaciente } from '../../../shared/dominio/condicaoPaciente';
import { DominioService } from '../../../shared/dominio/dominio.service';
import { EstagioDoenca } from '../../../shared/dominio/estagio.doenca';
import { Motivo } from '../../../shared/dominio/motivo';
import { TipoTransplante } from '../../../shared/dominio/tipoTransplante';
import { ErroMensagem } from '../../../shared/erromensagem';
import { EventEmitterService } from '../../../shared/event.emitter.service';
import { PacienteUtil } from '../../../shared/paciente.util';
import { UploadArquivoComponent } from '../../../shared/upload/upload.arquivo.component';
import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { PacienteConstantes } from '../../paciente.constantes';
import { ArquivoEvolucao } from './arquivo.evolucao';
import { Evolucao } from './evolucao';
import { EvolucaoService } from './evolucao.service';

/**
 * Componente responsável pelos dados de evolução
 * @author Fillipe Queiroz
 * @export
 * @class EvolucaoComponent
 * @extends {BaseForm}
 */
@Component({
    selector: 'evolucao',
    moduleId: module.id,
    templateUrl: './evolucao.component.html',
})
export class EvolucaoComponent extends BaseForm<Evolucao> implements OnInit, OnDestroy {

    estagios: EstagioDoenca[] = [];
    condicoesPaciente: CondicaoPaciente[] = [];
    motivos: Motivo[] = [];
    public evolucaoForm: FormGroup;
    public isEstagiosVisiveis: boolean;
    public isMotivosVisiveis: boolean = false;
    private _subscribeCidChange: any;
    private _arquivosEvolucao:ArquivoEvolucao[] = [];
    enviarArquivoNaSelecao:boolean = true;

    @ViewChild("uploadComponent")
    private uploadComponent: UploadArquivoComponent;

    public _exibirCamposAnticorpo: boolean = false;
    public _exibirCamposDataUltimoTransplante: boolean = false;

    public _data: Array<string | RegExp>;

    /**
     * Construtor padrão
     * @param serviceDominio serviço injetado para buscar os dominios
     * @author Fillipe Queiroz
     */
    constructor(private fb: FormBuilder, private serviceDominio: DominioService, translate: TranslateService
        , private evolucaoService: EvolucaoService) {
        super();
        this.condicoesPaciente = [];
        this.motivos = []
        this.translate = translate;

        this._data = [/[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/, '\/', /[0-9]/,/[0-9]/,/[0-9]/,/[0-9]/]

        this.serviceDominio.getCondicoesPaciente().then(res => {
            this.condicoesPaciente = res;
        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });

        this.serviceDominio.getMotivos().then(res => {
            this.motivos = res;
        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });

        this.serviceDominio.listarTipoTransplante().then(res => {
          res.forEach(t => {
              this.transplantesAnterioresFormArray.controls.push(this.tipoTransplanteGroup(new TipoTransplante().jsonToEntity(t)));
          })
        }, (error: ErroMensagem) => {
            this.alterarMensagem.emit(error.mensagem);
        });

        this.buildForm();
    }

    ngOnInit() {
        this.uploadComponent.extensoes = "extensaoArquivoEvolucao";
        this.uploadComponent.tamanhoLimite = "tamanhoArquivoEvolucao";
        this.uploadComponent.quantidadeMaximaArquivos = "quantidadeMaximaArquivosEvolucao"
        this.uploadComponent.uploadObrigatorio = false;
        let evolucaoReferencia:any = this;
        this.arquivosEvolucao = [];
        if(this.enviarArquivoNaSelecao){
            this.uploadComponent.enviarArquivo = function(data){
                evolucaoReferencia.evolucaoService.salvarArquivo(data).then(res =>{
                    let arquivo: ArquivoEvolucao = new ArquivoEvolucao();
                    let index = res.toString().indexOf("_");
                    arquivo.nomeSemTimestamp = res.toString().substring(index + 1, res.toString().length);
                    arquivo.caminhoArquivo = res.toString();
                    evolucaoReferencia.arquivosEvolucao.push(arquivo);
                    EventEmitterService.get("eventSalvarDraft").emit();
                }, (error: ErroMensagem) => {
                    this.alterarMensagem.emit(error.mensagem);
                });
            }
        }

        this._subscribeCidChange = EventEmitterService.get('onChangeCid').subscribe(cid => {
            this.obterEstagios(cid);
        });

        this.translate.get("pacienteForm.evolucaoGroup").subscribe(res => {
            this._formLabels = res;
            this.criarMensagemValidacaoPorFormGroup(this.evolucaoForm);
            this.setMensagensErro(this.evolucaoForm);
        });

    }

    public get transplantesAnterioresFormArray(): FormArray{
      return this.evolucaoForm.get('checkTransplanteAnterior') as FormArray;
    }

    tipoTransplanteGroup(tipoTransplante:TipoTransplante): FormGroup {
        return this.fb.group({
            check:[null, null],
            id: [tipoTransplante.id, null],
            descricao: [tipoTransplante.descricao, null],
        });
    }


    /**
     * Reseta o formulário limpando mensagens de error e valores dos campos
     *
     * @author Fillipe Queiroz
     * @memberof EvolucaoComponent
     */
    resetaFormulario(){
        this.evolucaoForm.reset();
        this.setValoresPadroes();
        this.clearMensagensErro(this.evolucaoForm)
    }
    /**
     * Por fim, para evitar problemas de desempenho, em cada classe que você estiver escutando um evento global,
     * você faca o unsubscribe() desse evento no método OnDestroy do seu component
     *
     * @author Fillipe Queiroz e Bruno Sousa
     */
    ngOnDestroy() {
        this._subscribeCidChange.unsubscribe();
    }

    /**
     * Seta os valores default no formulario
     *
     * @author Fillipe Queiroz
     * @memberof EvolucaoComponent
     *
     */
    setValoresPadroes(){
        this.evolucaoForm.get('motivo').setValue(PacienteConstantes.MOTIVO_CADASTRO_INICIAL);
        this.evolucaoForm.get('existenciaTransplante').setValue('false');
    }
    /**
     * Constrói os FormControl dos campos obrigatorios
     * @param FormBuilder para preencher os campos com suas respectivas validações
     * @author Fillipe Queiroz
     */
    buildForm() {

        this.evolucaoForm = this.fb.group({
            'motivo': [PacienteConstantes.MOTIVO_CADASTRO_INICIAL, Validators.required],
            'peso': [null, Validators.required],
            'altura': [null, Validators.required],
            'resultadoCMV': null,
            'tratamentoAnterior': [null, Validators.required],
            'tratamentoAtual': [null, Validators.required],
            'condicaoAtual': [null, Validators.required],
            'estagioDoenca': null,
            'evolucao': [null, Validators.required],
            'exameAnticorpo':[null,Validators.required],
            'dataExameAnticorpo':[null],
            'resultadoExameAnticorpo':[null],
            'dataUltimoTransplante': [null],
            'checkTransplanteAnterior': new FormArray([])
        });
        this.criarFormsErro(this.evolucaoForm);
    }


    /**
     * Método para carregar os estágios a partir  de uma cid
     *
     * @author Fillipe Queiroz e Bruno Sousa
     */
    obterEstagios(cid) {
        this.estagios = [];
        if(cid == null || cid == ""){
            this.resetFieldRequired(this.evolucaoForm, "estagioDoenca");
        } else if (cid) {
            this.serviceDominio.getEstagiosDoencaPor(cid).then(res => {
                this.estagios = res;

                if(this.estagios.length === 0) {
                    this.resetFieldRequired(this.evolucaoForm, "estagioDoenca");
                    this.isEstagiosVisiveis = false;
                } else {
                    this.setFieldRequired(this.evolucaoForm, "estagioDoenca");
                    this.isEstagiosVisiveis = true;
                }

            }, (error: ErroMensagem) => {
                this.alterarMensagem.emit(error.mensagem);
            });
        }
    }

    public form(): FormGroup{
        return this.evolucaoForm;
    }

    // Override
    public preencherFormulario(evolucao:Evolucao): void {
        if(evolucao){
            this.setPropertyValue('motivo', PacienteConstantes.MOTIVO_CADASTRO_INICIAL);
            this.setPropertyValue('peso', PacienteUtil.arredondar(String(evolucao.peso), 1));
            this.setPropertyValue('altura', evolucao.altura);
            this.setPropertyValue('resultadoCMV',evolucao.cmv != null?  Boolean(evolucao.cmv) : null);
            this.setPropertyValue('tratamentoAnterior', evolucao.tratamentoAnterior);
            this.setPropertyValue('tratamentoAtual', evolucao.tratamentoAtual);
            this.setPropertyValue('condicaoAtual', evolucao.condicaoAtual);
            this.setPropertyValue('estagioDoenca', evolucao.estagioDoenca);
            this.setPropertyValue('evolucao', evolucao.condicaoPaciente);
            this.setPropertyValue('exameAnticorpo',evolucao.exameAnticorpo != null && evolucao.exameAnticorpo ?  '1' : '0');
            this.checarItensTransplantesAnteriores(evolucao.tiposTransplante);

            this.setPropertyValue('dataUltimoTransplante',DataUtil.toDateFormat(evolucao.dataUltimoTransplante, DateTypeFormats.DATE_ONLY));

            if(evolucao.dataUltimoTransplante){
              this._exibirCamposDataUltimoTransplante = true;
              this.setFieldRequired( this.evolucaoForm, 'dataUltimoTransplante');
            }

            if (evolucao.exameAnticorpo != null && Boolean(evolucao.exameAnticorpo)) {
              this.setPropertyValue('dataExameAnticorpo',DataUtil.toDateFormat(evolucao.dataExameAnticorpo, DateTypeFormats.DATE_ONLY));
              this.setPropertyValue('resultadoExameAnticorpo',evolucao.resultadoExameAnticorpo);
              this.adicionarObrigatoriedadeAnticorpo(true);
            }else{
              this.adicionarObrigatoriedadeAnticorpo(false);
            }

            if(evolucao.arquivosEvolucao && evolucao.arquivosEvolucao.length > 0){
                let arquivosTemp: Map<string,FileItem> = new Map<string,FileItem>();
                evolucao.arquivosEvolucao.forEach(a=>{
                    let index = a.caminhoArquivo.indexOf("_");
                    a.nomeSemTimestamp = a.caminhoArquivo.substring(index + 1, a.caminhoArquivo.length);
                    arquivosTemp.set(a.nomeSemTimestamp, null);
                });

                this.uploadComponent.arquivos = arquivosTemp;
            }
        }
    }


    obterArquivosDeEvolucao() : Map<string, FileItem>{
        return this.uploadComponent.arquivos;
    }

    nomeComponente(): string {
        return null;
    }
    /**
     * Getter arquivosEvolucao
     * @return {ArquivoEvolucao[]}
     */
	public get arquivosEvolucao(): ArquivoEvolucao[] {
		return this._arquivosEvolucao;
	}

  /**
   * Setter arquivosEvolucao
   * @param {ArquivoEvolucao[]} value
   */
	public set arquivosEvolucao(value: ArquivoEvolucao[]) {
		this._arquivosEvolucao = value;
    }

    verificarSeHouveCheckTipoTransplanteAnterior(event, i?) {
        let temTransplanteAnterior:boolean = event.currentTarget.checked;
        this.transplantesAnterioresFormArray.controls.forEach((t, indice) => {
          if(indice != i){
            if (t.get('check').value == true) {
              return temTransplanteAnterior = true;
            }
          }
        });
        return temTransplanteAnterior;
    }

    habilitarDataUltimoTransplante(event, i?){
      if(this.verificarSeHouveCheckTipoTransplanteAnterior(event, i) && i != 0){
        //  this.evolucaoForm.get('dataUltimoTransplante').enable();
          this._exibirCamposDataUltimoTransplante = true;
          this.setFieldRequired( this.evolucaoForm, 'dataUltimoTransplante');
          this.onCheckNone();
        }else{
          //this.evolucaoForm.get('dataUltimoTransplante').disable();
          this._exibirCamposDataUltimoTransplante = false;
          this.resetFieldRequiredSemForm('dataUltimoTransplante');
          this.setPropertyValue('dataUltimoTransplante','');
          this.onCheckTdos();
      }
    }

    public adicionarObrigatoriedadeAnticorpo(adicionarObrigatoriedade: boolean){
        if(adicionarObrigatoriedade){
            this.setFieldRequired(this.form(), "dataExameAnticorpo");
            this.setFieldRequired(this.form(), "resultadoExameAnticorpo");
            this._exibirCamposAnticorpo = true;
        }
        else{
            this.resetFieldRequired(this.form(),"dataExameAnticorpo");
            this.resetFieldRequired(this.form(),"resultadoExameAnticorpo");
            this._exibirCamposAnticorpo = false;
            this.limparCamposExame();
        }
        this.criarMensagemValidacaoPorFormGroup(this.form());
        this.setMensagensErro(this.form());
    }


    limparCamposExame(){
        this.setPropertyValue('dataExameAnticorpo','');
        this.setPropertyValue('resultadoExameAnticorpo','');
    }


    checarItensTransplantesAnteriores(tiposTransplantesAnteriores:any[]){
      if(tiposTransplantesAnteriores != undefined && tiposTransplantesAnteriores.length > 0){
        tiposTransplantesAnteriores.forEach(tiposTransplanteAnt => {
          this.transplantesAnterioresFormArray.controls.forEach(c =>{
              if(tiposTransplanteAnt.id == c.get('id').value){
                  c.get('check').setValue('true');
              }
          });
        });
      }
    }

    onCheckNone() {
      this.transplantesAnterioresFormArray.controls.forEach((t, indice) => {
        if(indice == 0){
          t.get('check').setValue(false);
        }
      });
    }

    onCheckTdos() {
      this.transplantesAnterioresFormArray.controls.forEach((t, indice) => {
        if(indice != 0){
          t.get('check').setValue(false);
        }
      });
    }

}
