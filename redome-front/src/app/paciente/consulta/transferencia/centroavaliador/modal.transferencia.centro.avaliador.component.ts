import { ErroUtil } from 'app/shared/util/erro.util';
import { Component, OnInit } from "@angular/core";
import { FormBuilder, Validators, FormGroup } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";
import { CentroTransplanteService } from 'app/admin/centrotransplante/centrotransplante.service';
import { PacienteService } from "app/paciente/paciente.service";
import { BuildForm } from 'app/shared/buildform/build.form';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { FuncaoCentroTransplante } from 'app/shared/enums/funcao.centro.transplante';
import { IModalComponent } from "app/shared/modal/factory/i.modal.component";
import { IModalContent } from "app/shared/modal/factory/i.modal.content";
import { MessageBox } from "app/shared/modal/message.box";
import { ErroMensagem } from "app/shared/erromensagem";



/**
 * Componente de modal para cancelar pedido de workup.
 * @author Bruno Sousa
 */
@Component({
    selector: 'modal-transferencia-centro-avaliador',
    templateUrl: './modal.transferencia.centro.avaliador.component.html'                    
})
export class ModalTransfereciaCentroAvaliadorComponent implements IModalContent, OnInit {
    
    target: IModalComponent;
    close: (target?: IModalComponent) => void;
    data: any;

    private _listaCentrosAvaliadores: CentroTransplante[];
    private _form: BuildForm<any>;

    constructor(private translate: TranslateService, private fb: FormBuilder, 
        private messageBox: MessageBox, private pacienteService: PacienteService,
        private centroTransplanteService: CentroTransplanteService) {

        this._form = new BuildForm<any>()
            .add(new NumberControl("centroAvaliador", [Validators.required]))
    }

    public ngOnInit() {
        this.centroTransplanteService.listarCentroTransplantes(null, 0, null, FuncaoCentroTransplante.AVALIADOR).then(res => {
            this._listaCentrosAvaliadores = [];
            if (res && res.content) { 
                res.content.forEach(centro => {
                    this._listaCentrosAvaliadores.push(new CentroTransplante().jsonToEntity(centro));
                });
            }
        })

    }
    
    public fechar() {
        this.close(this.target);
    }

    public transferir() {
        if (this._form.valid) {
            this.target.hide();
            this.pacienteService.transferirCentroAvaliador(this.data.rmr, this._form.value.centroAvaliador).then(res => {
                this.messageBox.alert(res.mensagem)
                    .withCloseOption((target:any) => {
                        this.data.recarregarTelaDetalhe();
                    })
                    .show();
            }, 
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        }

    }

    public listaCentrosAvaliadores(): CentroTransplante[] {
        if (this._listaCentrosAvaliadores && this.data && this.data.centroAvaliador) {
            return this._listaCentrosAvaliadores.filter(centro => centro.id != this.data.centroAvaliador.id);
        }
        return [];
    }

    public form(): FormGroup {
        return this._form.form;
    }



}