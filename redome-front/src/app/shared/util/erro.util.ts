import { PacienteUtil } from "../paciente.util";
import { ActivatedRoute } from "@angular/router/src/router_state";
import { Subscriber } from "rxjs/Subscriber";
import { ErroMensagem } from "../erromensagem";
import { MessageBox } from "../modal/message.box";
import { AbstractControl, FormArray, FormGroup, FormControl } from "@angular/forms";

export class ErroUtil{
    public static exibirMensagemErro(error: ErroMensagem, messageBox: MessageBox, closeOption?: () => void) {
        let msg: string = ErroUtil.extrairMensagensErro(error);
        let message = messageBox.alert(msg);
        if (closeOption) {
            message.closeOption = closeOption;
        }
        message.show();
    }

    public static extrairMensagensErro(error: ErroMensagem): string{
        let msg: string = "";
        if (error.mensagem) {
            msg = error.mensagem.toString();

        } else {
            error.listaCampoMensagem.forEach(obj => {

                msg += obj.campo?obj.campo+': ':''
                msg += obj.mensagem + " <br/>";

            });
        }
        return msg;
    }

    public static validateFields(form: FormGroup): boolean {
      let valid: boolean = true;
      for (const field in form.controls) {
          const control = form.get(field);
          if (control && control instanceof FormControl) {
              if(control.invalid){
                  this.markAsInvalid(control);
                  valid = false;
              }
          }
          else if (control && control instanceof FormGroup) {
              this.validateFields(control);
              if(control.invalid){
                  valid = false;
              }
          }
          else if (control && control instanceof FormArray) {
              control.controls.forEach(element => {
                  if(element instanceof FormGroup){
                      this.validateFields(element);
                      if (element.invalid){
                          valid = false;
                      }
                  }
              });
          }
      }
      return valid;
    }

    public static markAsInvalid(field: AbstractControl): void {
      field.markAsTouched();
      field.markAsDirty();
      field.updateValueAndValidity();
      if(field.errors == null){
          field.setErrors({});
      }
      field.errors["invalid"] = true;
      field.errors["valid"] = false;
    }
}
