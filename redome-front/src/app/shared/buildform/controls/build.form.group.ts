import { FormGroup } from '@angular/forms';
import { AbstractBuildFormControl } from "./abstract.build.form.controls";
import { BuildFormControlType } from './build.form.control.type';
import { IControl } from './interface.control';
import { ArrayUtil } from '../../util/array.util';

/**
 * Classe resposável por criar FormGroup.
 *
 * @author Rafael Pizão e Bruno Sousa
 * @export
 * @class BuildFormGroup
 * @extends {AbstractBuildFormControl<T>}
 * @template T
 */
export class BuildFormGroup<T> extends AbstractBuildFormControl<T> {

    /**
     *Creates an instance of BuildFormGroup.
     * @param {string} name
     * @memberof BuildFormGroup
     */
    constructor(name: string) {
        super(name, null, BuildFormControlType.FORM_GROUP);
    }

    public get control(): FormGroup {
        return this._formGroup;
    }

    public add(...childs: IControl<T>[]): BuildFormGroup<T> {
        if(childs != null && childs.length > 0){
            childs.forEach(child => {
                this._formGroup.addControl(child.name, child.control);
                this._children.push(child);
            });
        }
        return this;
    }

    public get valid(): boolean{
        if(ArrayUtil.isNotEmpty(this.children)){
            this.children.forEach(child => {
                child.valid;
            });
        }
        return this.control.valid;
    }

}