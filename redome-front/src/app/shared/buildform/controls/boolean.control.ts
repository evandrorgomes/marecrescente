import { FormControl, ValidatorFn } from '@angular/forms';
import { BuildFormControl } from './build.form.controls';

/**
 * Classe responsável por criar um FormControl com valor do tipo boolean.
 *
 * @author Rafael Pizão e Bruno Sousa
 * @export
 * @class BooleanControl
 * @extends {BuildFormControl<Boolean>}
 */
export class BooleanControl extends BuildFormControl<Boolean>{

    constructor(name: string, value?: boolean);
    constructor(name: string, validators?: ValidatorFn[]);
    constructor(name: string, value?: boolean, validators?: ValidatorFn[]);
    constructor(name: string, valueOrValidators?: boolean | ValidatorFn[], validators?: ValidatorFn[]) {
        if (valueOrValidators !== undefined) {
            if (typeof valueOrValidators == "boolean") {
                super(name, validators);
                this.value = <boolean>valueOrValidators;
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
                this.onValueChange(val == "true" || val === true ? true: false);
            }
        });
    }

    public set value(value: boolean) {
        let control: FormControl = this.control as FormControl;
        control.setValue(value);
        control.updateValueAndValidity();
    }

    public get value(): boolean {
        let control: FormControl = this.control as FormControl;
        return control.value == "true" || control.value === true ? true: false;
    }
}