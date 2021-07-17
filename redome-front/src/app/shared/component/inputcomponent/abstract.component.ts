import { AbstractControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { OnChanges, Input, SimpleChange } from '@angular/core';
import { FormControl } from '@angular/forms';
import {
    REQUIRED,
    INVALID_DATE,
    INVALID_DATE_BEFORE_TODAY,
    INVALID_DATE_AFTER_TODAY,
    INVALID_HOUR,
    INVALID_DATETIME,
    INVALID_MAX_ITENS,
    INVALID_CUSTOM
} from 'app/validators/data.validator';
import { MIN_LENGTH, MATCH_OTHER } from 'app/validators/match.validator';


export abstract class AbstractComponent implements OnChanges {

    @Input() label: string = ""; 

    //current form control input. helpful in validating and accessing form control
    @Input() control: AbstractControl; 

    quantidadeItens: number;

    // errors for the form control will be stored in this array
    errors:Array<any> = []; 

    //The internal data model for form control value access
    protected innerValue: any = '';

    constructor(protected translate: TranslateService) {

    }

    ngOnChanges(changes: {[propKey: string]: SimpleChange}) {
    }

   // event fired when input value is changed . later propagated up to the form control using the custom value accessor interface
   onChange(e:Event, value:any) {
        //set changed value
        this.innerValue = value;
        // propagate value into form control using control value accessor interface
        this.propagateChange(this.innerValue);

        this.checkErro();
    }

    //propagate changes into the custom form control
    propagateChange = (_: any) => { }    

    propagateTouched = (_: any) => { }    

    public getMensagemErro(): string {
        if (this.errors && this.errors.length != 0) {
            return this.errors[0];
        }
        return null;
    }

    public checkErro(reset: boolean = true): void {
        //reset errors 
        if (reset) {
            this.errors = [];
        }

        if (!this.control) {
            return;
        }

        if (this.control.pristine && this.control.untouched) {
            return;
        }

        //setting, resetting error messages into an array (to loop) and adding the validation messages to show below the field area
        for (var key in this.control.errors) {
            if (this.control.errors.hasOwnProperty(key)) {
                let chaveMensagem: string = ""
                let minLength: number;
                
                if (key == REQUIRED) {
                    chaveMensagem = "mensagem.erro.obrigatorio"; 
                }
                if (key == INVALID_DATE) {
                    chaveMensagem = "mensagem.erro.dataInvalida"; 
                }
                if (key == INVALID_DATE_BEFORE_TODAY) {
                    chaveMensagem = "mensagem.erro.menorDataAtual"; 
                }
                if (key == INVALID_DATE_AFTER_TODAY) {
                    chaveMensagem = "mensagem.erro.maiorDataAtual"; 
                }
                if (key == INVALID_HOUR) {
                    chaveMensagem = "mensagem.erro.horaInvalida"; 
                }
                if (key == INVALID_DATETIME) {
                    chaveMensagem = "mensagem.erro.dataHoraInvalida"; 
                }
                if (key == INVALID_MAX_ITENS) {
                    chaveMensagem = "mensagem.erro.quantidadeMaximaItens"; 
                }
                if (key == MIN_LENGTH) {
                    chaveMensagem = "mensagem.erro.minLength";
                    minLength = this.control.errors[key].requiredLength;
                }
                if (key == MATCH_OTHER) {
                    chaveMensagem = "mensagem.erro.matchOther";
                }

                if (this.translate && chaveMensagem != "") {
                    this.translate.get(chaveMensagem, {campo: this.label, quantidade: this.quantidadeItens, minlength: minLength})
                        .subscribe(res => {
                            this.errors.push(res);        
                        });
                }
                else {
                    this.errors.push(this.control.errors[key]);
                }
            }
        }
    }

}