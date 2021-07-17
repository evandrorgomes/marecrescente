import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { BaseForm } from 'app/shared/base.form';
import { AprovarPlanoWorkupDTO } from 'app/shared/dto/aprovar.plano.workup.dto';
import { ArquivoPedidoWorkupDTO } from 'app/shared/dto/arquivo.pedido.workup.dto';
import { TiposPedidoWorkup } from 'app/shared/enums/tipos.pedido.workup';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { PedidoWorkupService } from 'app/shared/service/pedido.workup.service';
import { DataUtil } from 'app/shared/util/data.util';
import { DateMoment } from 'app/shared/util/date/date.moment';
import { DateTypeFormats } from 'app/shared/util/date/date.type.formats';
import { ErroUtil } from 'app/shared/util/erro.util';
import { ValidateData, ValidateDataMaiorOuIgualHoje } from 'app/validators/data.validator';

@Component({
  selector: 'aprovar-plano-workup',
  templateUrl: './aprovar.plano.workup.component.html'
})
export class AprovarPlanoWorkupComponent extends BaseForm<Object> implements OnInit {

  private _planoWorkupForm: FormGroup;
  private _idPedidoWorkup: number;
  private _aprovarPlanoWorkup: AprovarPlanoWorkupDTO;
  public maskData: Array<string | RegExp>

  constructor(
    private activatedRouter: ActivatedRoute, private router: Router,
    translate: TranslateService, private pedidoWorkupService: PedidoWorkupService,
    private fb: FormBuilder,
    private messageBox: MessageBox) {
    super();

    this.maskData = [/[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, '\/', /[0-9]/, /[0-9]/, /[0-9]/, /[0-9]/]

    this.translate = translate;
    this.buildForm();
  }

  ngOnInit() {

    this.activatedRouter.queryParamMap.subscribe(queryParam => {
      if (queryParam.keys.length != 0) {
        this._idPedidoWorkup = Number(queryParam.get('idPedidoWorkup'));

        this.pedidoWorkupService.obterPedidoWorkup(this._idPedidoWorkup).then(res => {

          this._aprovarPlanoWorkup = new AprovarPlanoWorkupDTO().jsonToEntity(res);

        },
          (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
          });
      }
    });
  }

  aprovar(): void {
    if (this.validateForm()) {
      let aprovarPlanoWorkup = new AprovarPlanoWorkupDTO();
      aprovarPlanoWorkup.dataCondicionamento = DataUtil.toDate(this._planoWorkupForm.get("dataCondicionamento").value);
      aprovarPlanoWorkup.dataInfusao = DataUtil.toDate(this._planoWorkupForm.get("dataInfusao").value);
      aprovarPlanoWorkup.criopreservacao = this._planoWorkupForm.get("criopreservacao").value;
      aprovarPlanoWorkup.observacaoAprovaPlanoWorkup = this._planoWorkupForm.get("observacao").value;

      this.pedidoWorkupService.aprovarPlanoDeWorkup(this._idPedidoWorkup, aprovarPlanoWorkup)
        .then(res => {
          this.router.navigateByUrl('/doadores/workup/centrocoleta/consulta');
        },
          (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
          });
    }
  }

  public form(): FormGroup {
    return this._planoWorkupForm;
  }
  public preencherFormulario(entidade: Object): void {
    throw new Error("Method not implemented.");
  }
  nomeComponente(): string {
    return "AprovarPlanoWorkupComponent";
  }

  /**
   * * Método que constrói o formulário
   * @returns void
   */
  buildForm(): void {
    this._planoWorkupForm = this.fb.group({
      'dataCondicionamento': [null, Validators.compose([Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje])],
      'dataInfusao': [null, Validators.compose([Validators.required, ValidateData, ValidateDataMaiorOuIgualHoje])],
      'criopreservacao': [null, Validators.required],
      'observacao': [null, null]
    });
  }

  validateForm(): boolean {
    let valid: boolean = super.validateForm();
    if (!valid) {
      return false;
    }
    let dataValida: boolean = true;
    let dateMoment: DateMoment = DateMoment.getInstance();
    let dataInfusao = dateMoment.parse(this.form().get("dataInfusao").value, DateTypeFormats.DATE_TIME);
    let dataCondicionamento = dateMoment.parse(this.form().get("dataCondicionamento").value, DateTypeFormats.DATE_TIME);
    let dataColeta = new Date(this._aprovarPlanoWorkup.dataColeta);
    let dataInfusaoValida = dateMoment.isDateTimeSameOrBefore(dataColeta, dataInfusao);
    let dataCondicionamentoValida = dateMoment.isDateTimeSameOrBefore(dataColeta, dataCondicionamento);
    if (!dataInfusaoValida) {
      this.forceError("dataInfusao", "Data de Infusão menor que a Data de Coleta.");  //this._formLabels['dataInfusaoMenorDataColeta']
      dataValida = false;
    }
    if (!dataCondicionamentoValida) {
      this.forceError("dataCondicionamento", "Data de Condicionamento menor que a Data de Coleta."); //this._formLabels['dataCondicionamentoMenorDataColeta']
      dataValida = false;
    }

    return dataValida;
  }


  isPedidoWorkupNacional(): boolean {
    return this._aprovarPlanoWorkup && this._aprovarPlanoWorkup.idTipo == TiposPedidoWorkup.NACIONAL;
  }

  isPedidoWorkupInternacional(): boolean {
    return this._aprovarPlanoWorkup && this._aprovarPlanoWorkup.idTipo == TiposPedidoWorkup.INTERNACIONAL;
  }

  baixarArquivo(arquivo: ArquivoPedidoWorkupDTO) {
    this.pedidoWorkupService.baixarArquivoPlanoWorkup(arquivo.id, arquivo.nomeSemTimestamp);
  }


  /**
   * Getter planoWorkupForm
   * @return {FormGroup}
   */
  public get planoWorkupForm(): FormGroup {
    return this._planoWorkupForm;
  }

  /**
   * Setter planoWorkupForm
   * @param {FormGroup} value
   */
  public set planoWorkupForm(value: FormGroup) {
    this._planoWorkupForm = value;
  }

  /**
   * Getter aprovarPlanoWorkup
   * @return {AprovarPlanoWorkupDTO}
   */
  public get aprovarPlanoWorkup(): AprovarPlanoWorkupDTO {
    return this._aprovarPlanoWorkup;
  }

  /**
   * Setter aprovarPlanoWorkup
   * @param {AprovarPlanoWorkupDTO} value
   */
  public set aprovarPlanoWorkup(value: AprovarPlanoWorkupDTO) {
    this._aprovarPlanoWorkup = value;
  }


}
