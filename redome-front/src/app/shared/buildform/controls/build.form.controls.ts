import { FormControl, ValidatorFn } from '@angular/forms';
import { AbstractBuildFormControl } from './abstract.build.form.controls';
import { BuildFormControlType } from './build.form.control.type';
import { IControl } from "./interface.control";
import {OnDestroy} from "@angular/core";

/**
 * Classe responsável por criar um FormControl.
 *
 * @author Rafael Pizão e Bruno Sousa
 * @export
 * @class BuildFormControl
 * @extends {AbstractBuildFormControl<T>}
 * @template T
 */
export class BuildFormControl<T> extends AbstractBuildFormControl<T> {
    
    /**
     *Creates an instance of BuildFormControl.
     * @param {string} name
     * @param {ValidatorFn[]} [validators=null]
     * @memberof BuildFormControl
     */
    constructor(name: string, validators: ValidatorFn[] = null){
        super(name, validators, BuildFormControlType.FORM_CONTROL);
    }

    public getChild(name: string): never {
        throw new Error("FormControl não contem outro FormControl.");
    }

    public get children(): never {
        throw new Error("FormControl não contem outros FormControl.");
    }

    public get control(): FormControl{
        return this._formControl;
    }

    public add(...childs: IControl<T>[]): never {
        throw new Error("FormControl não pode conter outro FormControl.");
    }

    public get valid(): boolean {
        this.markAsInvalid();
        this.control.setValue(this.control.value);
        this.control.updateValueAndValidity();
        return this.control.valid && this.control.touched;
    }

}