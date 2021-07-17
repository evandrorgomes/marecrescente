import { Validators } from '@angular/forms';
import { BuildFormControlType } from './build.form.control.type';
import { constructor } from 'events';
import { FormGroup, FormControl, AbstractControl, ValidatorFn, FormArray } from '@angular/forms';
import { IControl } from "./interface.control";

/**
 * Classe abstrata que trata os componentes de controle.
 *
 * @author Rafael Pizão e Bruno Sousa
 * @export
 * @abstract
 * @class AbstractBuildFormControl
 * @implements {IControl<T>}
 * @template T
 */
export abstract class AbstractBuildFormControl<T> implements IControl<T> {

    private _name: string;
    protected _formGroup: FormGroup;
    protected _formControl: FormControl;
    protected _formArray: FormArray;
    protected _children: IControl<any>[];
    private _validators: ValidatorFn[];
    private _type: BuildFormControlType;

    protected _valueChangeSubscriber: any;

    /**
     *Creates an instance of AbstractBuildFormControl.

     * @param {string} name
     * @param {ValidatorFn[]} [validators=null]
     * @param {BuildFormControlType} [type=BuildFormControlType.FORM_CONTROL]
     * @memberof AbstractBuildFormControl
     */
    constructor(name: string, validators: ValidatorFn[] = null, type: BuildFormControlType = BuildFormControlType.FORM_CONTROL){
        this._name = name;
        this._children = [];
        this._type = type;
        this._validators = validators;
        if (type === BuildFormControlType.FORM_GROUP) {
            this._formGroup = new FormGroup({});
        }
        else if (type === BuildFormControlType.FORM_CONTROL) {
            if (this._validators != null && this._validators.length != 0) {
                this._formControl = new FormControl(null, this._validators);
            }
            else {
                this._formControl = new FormControl();
            }
        }
        else if (type === BuildFormControlType.FORM_ARRAY) {
            if (this._validators != null && this._validators.length == 1) {
                this._formArray = new FormArray([], this._validators[0]);
            }
            else {
                this._formArray = new FormArray([]);
            }
        }
    }


    public get name(): string{
        return this._name;
    }

    public get type(): BuildFormControlType {
        return this._type;
    }

    public get value(): T{
        let formObject: T = {} as T;
        this._children.forEach(control => {
            formObject[control.name] = control.value;
        });
        return formObject;
    }

    public set value(value: T){
        this._children.forEach(field => {
            if (value[field.name]) {
                field.value = value[field.name];
            }
        });
        this.control.updateValueAndValidity();
    }

    /**
     * Método que retorna um child pelo name
     *
     * @param {string} name
     * @returns {IControl<any>}
     * @memberof AbstractBuildFormControl
     */
    public getChild(name: string): IControl<any> {
        return this.children && this.children.length != 0 ? this.children.find(child => child.name == name) : null;
    }

    /**
     * Método que retorna a lista de childs.
     *
     * @readonly
     * @type {IControl<any>[]}
     * @memberof AbstractBuildFormControl
     */
    public get children(): IControl<any>[]{
        return this._children;
    }

    /**
     * Método abstrato qye deverá retornar um AbstractControl.
     * BuildFormGroup deverá retornar FormGroup
     * BuildFormArray deverá retornar FormArray
     * BuildFormControl deverá retornar FormControl
     *
     * @readonly
     * @abstract
     * @type {AbstractControl}
     * @memberof AbstractBuildFormControl
     */
    public abstract get control(): AbstractControl;

    /**
     * Método utilizado para adicionar controles.
     *
     * @abstract
     * @param {...IControl<T>[]} childs
     * @returns {AbstractBuildFormControl<T>}
     * @memberof AbstractBuildFormControl
     */
    public abstract add(...childs: IControl<T>[]): AbstractBuildFormControl<T>;

    /**
     * @description Retorna se o campo está válido ou não.
     * @readonly
     * @abstract
     * @type {boolean}
     */
    public abstract get valid(): boolean;

    public get invalid(): boolean {
        /* this.children.forEach(child => child.valid);
        this.control.updateValueAndValidity(); */
        return this.control.invalid && this.control.touched;
    }

    public invalidate(mensagem: string) {
        this.control.setErrors({
            "invalidate": mensagem,
        });
        this.markAsInvalid(); 
    }

    protected markAsInvalid(): void {
        this.control.markAsTouched();
        this.control.markAsDirty();        
    }

    public markAsValid(): void {
        this.control.markAsUntouched();
        this.control.markAsPristine();
    }

    public makeRequired(): void {        
        if (!this._validators) {
            this._validators = [];
        }
        this._validators.push(Validators.required);
        this.applyValidators();
    }

    public makeOptional(): void {
        this._validators = this._validators ? this._validators.filter(validator => { validator !== Validators.required } ) : null;
        this.applyValidators();
    }

    public removeValidator(validator: ValidatorFn) {
        this._validators = this._validators ? this._validators.filter(valida => { valida !== validator } ) : null;
        this.applyValidators();
    }

    public addValidator(validator: ValidatorFn) {
        if (!this._validators) {
            this._validators = [];
        }
        this._validators.push(validator);
        this.applyValidators();
    }

    private applyValidators() {
        this.control.setValidators(this._validators);
        this.control.updateValueAndValidity();
    }

    public onValueChange: (value: T)  => void;


}