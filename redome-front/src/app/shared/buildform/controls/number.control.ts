import { FormControl, ValidatorFn } from '@angular/forms';
import { BuildFormControl } from './build.form.controls';
import {OnDestroy} from "@angular/core";

/**
 * Classe responsável por criar um FormControl com valor do tipo number.
 *
 * @author Rafael Pizão e Bruno Sousa
 * @export
 * @class NumberControl
 * @extends {BuildFormControl<number>}
 */
export class NumberControl extends BuildFormControl<number> {

    constructor(name: string, value?: number);
    constructor(name: string, validators?: ValidatorFn[]);
    constructor(name: string, value?: number, validators?: ValidatorFn[]);
    constructor(name: string, valueOrValidators?: number | ValidatorFn[], validators?: ValidatorFn[]) {
        if (valueOrValidators !== undefined) {
            if (typeof valueOrValidators == "number") {
                super(name, validators);
                this.value = <number>valueOrValidators;
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
                this.onValueChange(new Number(val).valueOf() || null);
            }
        });

    }

    public set value(value: number) {
        let control: FormControl = this.control as FormControl;
        control.setValue(isNaN(value) || value === null ? null : value.toString());
        control.updateValueAndValidity();
    }

    public get value(): number {
        let control: FormControl = this.control as FormControl;
        if (control.value == "0") {
            return new Number(control.value).valueOf();    
        }
        return new Number(control.value).valueOf() || null;
    }
    
}