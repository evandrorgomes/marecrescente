import { CampoMensagem } from './../campo.mensagem';
import { BuildFormArray } from './controls/build.form.array';
import { BuildFormControlType } from './controls/build.form.control.type';
import { IControl } from './controls/interface.control';
import { FormGroup } from '@angular/forms';
import { BuildFormControl } from './controls/build.form.controls';
import { BuildFormGroup } from './controls/build.form.group';
import { AbstractBuildFormControl } from './controls/abstract.build.form.controls';
import { StringControl } from 'app/shared/buildform/controls/string.control';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import {OnDestroy} from "@angular/core";
import {DateControl} from "./controls/date.control";

/**
 * Classe responsável por criar um BuildFormGroup (FormGroup) principal.
 *
 * @author Rafael Pizão e Bruno Sousa
 * @export
 * @class BuildForm
 * @template T
 */
export class BuildForm<T> {

    private _redomeObject: BuildFormGroup<T>;

    /**
     *Creates an instance of BuildForm.     
     * @memberof BuildForm
     */
    constructor(){
        this._redomeObject = new BuildFormGroup<T>("form");
    }


    public get form(): FormGroup {
        return this._redomeObject.control;
    }

    public get value(): T{
        return this._redomeObject.value;
    }

    public set value(value: T) {
        this._redomeObject.value = value;
    }

    /**
     * Método que o obtém um child pelo name.
     *
     * @private
     * @param {string} name
     * @returns {IControl<T>}
     * @memberof BuildForm
     */
    private getChild(name: string): IControl<any> {
        return this._redomeObject.getChild(name);
    }

    /**
     * Método que retorna um child do tipo BuildFormGroup
     *
     * @param {string} name
     * @returns {BuildFormGroup<T>}
     * @memberof BuildForm
     */
    public getChildAsBuildFormGroup(name: string): BuildFormGroup<T> {
        let control: IControl<any> = this.getChild(name);
        if (control.type !== BuildFormControlType.FORM_GROUP) {
            throw new Error("The type of child is not BuildFormGroup");
        }
        return <BuildFormGroup<T>>control;
    }

    /**
     * Método que retorna um child do tipo BuildFormArray
     *
     * @param {string} name
     * @returns {BuildFormArray<T>}
     * @memberof BuildForm
     */
    public getChildAsBuildFormArray(name: string): BuildFormArray<T> {
        let control: IControl<any> = this.getChild(name);
        if (control.type !== BuildFormControlType.FORM_ARRAY) {
            throw new Error("The type of child is not BuildFormArray");
        }
        return <BuildFormArray<T>>control;
    }

    /**
     * Método que retorna um child do tipo BuildFormControl
     *
     * @param {string} name
     * @returns {BuildFormControl<T>}
     * @memberof BuildForm
     */
    public getChildAsBuildFormControl(name: string): BuildFormControl<T> {
        let control: IControl<any> = this.getChild(name);
        if (control.type !== BuildFormControlType.FORM_CONTROL) {
            throw new Error("The type of child is not BuildFormControl");
        }
        return <BuildFormControl<T>>control;
    }

    /**
     * Método para adicionar BuildFormGroup, BuildFormArray e BuildFormControl.
     *
     * @param {...IControl<any>[]} children
     * @returns {BuildForm<T>}
     * @memberof BuildForm
     */
    public add(...children: IControl<any>[]): BuildForm<T>{
        if(children != null && children.length > 0){
            children.forEach(child => {
                this._redomeObject.add(child);

                if(child instanceof BuildFormGroup){
                    this.setReferenceFormGroup(child);
                }
            });
        }
        return this;
    }

    private setReferenceFormGroup(group: BuildFormGroup<any>): void{
        this[group.name] = group.control;
    }

    public get valid(): boolean {
        return this._redomeObject.valid;
    }

    public invalidate(camposMensagem: CampoMensagem[]) {
        if (camposMensagem && camposMensagem.length != 0) {
            camposMensagem.forEach(campoMensagem => {
                let control: AbstractBuildFormControl<any>;
                try {
                    control = this.getChildAsBuildFormControl(campoMensagem.campo);                    
                } catch (error) {                    
                    control = null;
                }

                try {
                    if (!control) {
                        control = this.getChildAsBuildFormGroup(campoMensagem.campo);
                    }                    
                } catch (error) {                    
                    control = null
                }

                try {
                    if (!control) {
                        control = this.getChildAsBuildFormArray(campoMensagem.campo);
                    }
                } catch (error) {                    
                }
                if (control) {
                    control.invalidate(campoMensagem.mensagem);    
                }
            });
        }
    }

    public getControlAsStringControl(name: string): StringControl {
        let control: IControl<any> =  this.getChild(name);
        return control instanceof StringControl ? <StringControl>control : null ;
    }

    public getControlAsNumberControl(name: string): NumberControl {
        let control: IControl<any> =  this.getChild(name);
        return control instanceof NumberControl ? <NumberControl>control : null ;
    }

    public getControlAsDateControl(name: string): DateControl {
        let control: IControl<any> =  this.getChild(name);
        return control instanceof DateControl ? <DateControl>control : null ;
    }


    /**
     * @description Obtém o filho a partir do nome (formControlName)
     * dado ao filho.
     * @author Pizão
     * @param {string} child
     * @returns {IControl<any>}
     */
    public get(child: string): IControl<any> {
        return this.getChild(child);
    }

}
