import { AbstractBuildFormControl } from "./abstract.build.form.controls";
import { BuildFormControlType } from './build.form.control.type';
import { ValidatorFn, FormArray } from '@angular/forms';
import { IControl } from "./interface.control";

/**
 * Classe responsável por criar um FormArray.
 *
 * @author Rafael Pizão e Bruno Sousa
 * @export
 * @class BuildFormArray
 * @extends {AbstractBuildFormControl<T>}
 * @template T
 */
export class BuildFormArray<T> extends AbstractBuildFormControl<T> {

    /**
     *Creates an instance of BuildFormArray.
     * @param {string} name
     * @param {ValidatorFn[]} [validators=null]
     * @memberof BuildFormArray
     */
    constructor(name: string, validator: ValidatorFn = null) {
        super(name, [validator], BuildFormControlType.FORM_ARRAY);
    }

    public get control(): FormArray {
        return this._formArray;
    }

    public add(...childs: IControl<T>[]): BuildFormArray<T> {
        if(childs != null && childs.length > 0){
            childs.forEach(child => {
                this._formArray.push(child.control);
                this._children.push(child);
            });
        }
        return this;
    }

    public get valid(): boolean {
        this.children.forEach(child => child.valid)
        return this.control.valid;
    }

}