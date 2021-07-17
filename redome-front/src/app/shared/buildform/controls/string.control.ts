import { FormControl, ValidatorFn } from '@angular/forms';
import { BuildFormControl } from './build.form.controls';

/**
 * Classe responsável por criar um FormControl com valor do tipo string.
 *
 * @author Rafael Pizão e Bruno Sousa
 * @export
 * @class StringControl
 * @extends {BuildFormControl<string>}
 */
export class StringControl extends BuildFormControl<string>{

    constructor(name: string, value?: string);
    constructor(name: string, validators?: ValidatorFn[]);
    constructor(name: string, value?: string, validators?: ValidatorFn[]);
    constructor(name: string, valueOrValidators?: string | ValidatorFn[], validators?: ValidatorFn[]) {
        if (valueOrValidators) {
            if (typeof valueOrValidators == "string") {
                super(name, validators);
                this.value = <string>valueOrValidators;
            }
            else {
                super(name, valueOrValidators);
            }
        }
        else {
            super(name);
        }
        this._valueChangeSubscriber = this._formControl.valueChanges.subscribe(val => {
            if (this.onValueChange) {
                this.onValueChange(val);
            }
        });
        
    }

    public set value(value: string) {
        let control: FormControl = this.control as FormControl;
        control.setValue(value ? value : null);
        control.updateValueAndValidity();
    }

    public get value(): string {
        let control: FormControl = this.control as FormControl;
        return control.value;
    }
    
}