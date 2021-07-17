import { FormControl, ValidatorFn } from '@angular/forms';
import { BuildFormControl } from './build.form.controls';
import {DateMoment} from "../../util/date/date.moment";
import {DateTypeFormats} from "../../util/date/date.type.formats";

/**
 * Classe responsável por criar um FormControl com valor do tipo number.
 *
 * @author Rafael Pizão e Bruno Sousa
 * @export
 * @class NumberControl
 * @extends {BuildFormControl<number>}
 */
export class DateControl extends BuildFormControl<Date>{

    private dm: DateMoment;

    constructor(name: string, value?: Date);
    constructor(name: string, validators?: ValidatorFn[]);
    constructor(name: string, value?: Date, validators?: ValidatorFn[]);
    constructor(name: string, valueOrValidators?: Date | ValidatorFn[], validators?: ValidatorFn[]) {
        if (valueOrValidators !== undefined) {
            if (typeof valueOrValidators == "object") {
                super(name, validators);
                this.dm = DateMoment.getInstance();
                this.value = <Date>valueOrValidators;
            }
            else {
                super(name, valueOrValidators);
                this.dm = DateMoment.getInstance();
            }
        }
        else {
            super(name);
            this.dm = DateMoment.getInstance();
        }
        this._valueChangeSubscriber = this._formControl.valueChanges.subscribe(val => {
            if (this.onValueChange) {
                this.onValueChange(this.dm.parse(val, DateTypeFormats.DATE_ONLY) || null);
            }
        });
    }

    public set value(value: Date) {
        let control: FormControl = this.control as FormControl;
        control.setValue(value === null || this.dm.isValid(value) ? null : this.dm.formatWithPattern(value, "dd/MM/yyyy"));
        control.updateValueAndValidity();
    }

    public get value(): Date {
        let control: FormControl = this.control as FormControl;

        return this.dm.parse(control.value, DateTypeFormats.DATE_ONLY) || null;
    }
    
}