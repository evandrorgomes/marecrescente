import {Component, OnInit} from "@angular/core";
import {IModalComponent} from "app/shared/modal/factory/i.modal.component";
import {IModalContent} from "app/shared/modal/factory/i.modal.content";
import {CentroTransplante} from "../../../../../shared/dominio/centro.transplante";
import {BuildForm} from "../../../../../shared/buildform/build.form";
import {TranslateService} from "@ngx-translate/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MessageBox} from "../../../../../shared/modal/message.box";
import {PacienteService} from "../../../../paciente.service";
import {CentroTransplanteService} from "../../../../../admin/centrotransplante/centrotransplante.service";
import {NumberControl} from "../../../../../shared/buildform/controls/number.control";
import {UsuarioBasico} from "../../../../../shared/dominio/usuario.basico";
import {UsuarioService} from "../../../../../admin/usuario/usuario.service";
import {DistribuicaoWorkupService} from "../../../../../shared/service/distribuicao.workup.service";
import {PerfilService} from "../../../../../admin/perfil/perfil.service";
import {AutenticacaoService} from "../../../../../shared/autenticacao/autenticacao.service";
import {Perfis} from "../../../../../shared/enums/perfis";
import {PacienteUtil} from "../../../../../shared/paciente.util";
import {ErroMensagem} from "../../../../../shared/erromensagem";
import {ErroUtil} from "../../../../../shared/util/erro.util";


/**
 * Componente de modal para distribuição de workup.
 * @author Bruno Sousa
 */
@Component({
   selector: 'modal-distribuicao-workup',
   templateUrl: './modal.distribuicao.workup.component.html'
})
export class ModalDistribuicaoWorkupComponent implements IModalContent, OnInit {

   target: IModalComponent;
   close: (target?: IModalComponent) => void;
   data: any;

   public _listaUsuarios: UsuarioBasico[];
   private _form: BuildForm<any>;

   constructor(private translate: TranslateService, private fb: FormBuilder,
               private messageBox: MessageBox, private autenticacaoService: AutenticacaoService,
               private distribuicaoWorkupService: DistribuicaoWorkupService,
               private perfilService: PerfilService) {

      this._form = new BuildForm<any>()
         .add(new NumberControl("usuario", [Validators.required]))
   }

   ngOnInit(): void {
      this.perfilService.listarUsuarios(this.obterIdPerfil()).then(res => {
         if (res) {
            this._listaUsuarios = [];
            res.forEach(usuario => {
               this._listaUsuarios.push(new UsuarioBasico().jsonToEntity(usuario));
            })
         }
      })
      .catch((erro: ErroMensagem) => {
         ErroUtil.exibirMensagemErro(erro, this.messageBox);
      });

   }

   obterIdPerfil(): number {
      if (this.autenticacaoService.temRecursoDistriburWorkup()) {
         return Perfis.ANALISTA_WORKUP
      }
      if (this.autenticacaoService.temRecursoDistriburWorkupInternacional()) {
         return Perfis.ANALISTA_WORKUP_INTERNACIONAL
      }

   }

   public fechar() {
      this.close(this.target);
   }

   public form(): FormGroup {
      return this._form.form;
   }

   public salvar() {
      if (this._form.valid) {
         if (this.data.modo == 'distribuir') {
            this.distribuir();
         }
         else {
            this.redistribuir();
         }
      }
   }

   private distribuir() {
      this.distribuicaoWorkupService.atribuirDistribuicaoWorkup(this.data.distribuicao.idDistribuicaoWorkup, this._form.get('usuario').value).then (res => {
         this.target.hide();
         this.messageBox.alert(res).withCloseOption(() => {
            this.data.recarregar();
         }).show();
      })
      .catch((erro: ErroMensagem) => {
         ErroUtil.exibirMensagemErro(erro, this.messageBox);
      });
   }

   private redistribuir() {
      this.distribuicaoWorkupService.reatribuirDistribuicaoWorkup(this.data.distribuicao.idDistribuicaoWorkup, this._form.get('usuario').value).then (res => {
         this.target.hide();
         this.messageBox.alert(res).withCloseOption(() => {
            this.data.recarregar();
         }).show();
      })
      .catch((erro: ErroMensagem) => {
         ErroUtil.exibirMensagemErro(erro, this.messageBox);
      });
   }

   formatarData(data: string): string {
      if (!data) {
         return '';
      }
      return PacienteUtil.converterStringEmData(data).toLocaleDateString();
   }


}