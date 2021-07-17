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
export class ArrayControl extends BuildFormControl<any[]>{

    constructor(name: string, value?: any[]);
    constructor(name: string, validators?: ValidatorFn[]);
    constructor(name: string, value?: any[], validators?: ValidatorFn[]);
    constructor(name: string, valueOrValidators?: any[] | ValidatorFn[], validators?: ValidatorFn[]) {
        if (valueOrValidators) {
            if (typeof valueOrValidators == "string") {
                super(name, validators);
                this._formControl = new FormControl([], null)
                this.value = <any[]>valueOrValidators;
            }
            else {
                super(name, valueOrValidators);
                this._formControl = new FormControl([], valueOrValidators)
            }
        }
        else {
            super(name);
            this._formControl = new FormControl([]);
        }
        
    }

    public set value(value: any[]) {
        let control: FormControl = this.control as FormControl;
        control.setValue(value ? value : null);
        control.updateValueAndValidity();
    }

    public get value(): any[] {
        let control: FormControl = this.control as FormControl;
        return control.value ? control.value : [];
    }
    
}