import {AbstractControl, ValidatorFn} from '@angular/forms';
import { BuildFormControlType } from './build.form.control.type';
import {OnChanges, OnDestroy, OnInit} from "@angular/core";

/**
 * Interface utilizada nos componentes de controle.
 *
 * @author Rafael Pizão e Bruno Sousa
 * @export
 * @interface IControl
 * @template T
 */
export interface IControl<T> {

    name: string;
    type: BuildFormControlType;
    value: T;
    control: AbstractControl;
    valid: boolean;
    invalid: boolean;
    
    makeRequired();
    makeOptional();
    removeValidator(validator: ValidatorFn);
    addValidator(validator: ValidatorFn);

    onValueChange: (value: T)  => void;

    
}