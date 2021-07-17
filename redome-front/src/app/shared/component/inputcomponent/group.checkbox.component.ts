import { AbstractComponent } from 'app/shared/component/inputcomponent/abstract.component';
import { AbstractControl } from '@angular/forms';
import { FormGroup, NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl, FormArray } from '@angular/forms';
import { Component, Input, forwardRef, AfterViewInit, OnChanges, ElementRef, ViewChild, QueryList, ViewChildren, AfterViewChecked } from "@angular/core";
import { TranslateService } from '@ngx-translate/core';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => GroupCheckBoxComponent),
    multi: true
};

export enum PositionType {
    HORIZONTAL = 0,
    VERTICAL = 1
}

@Component({
    selector: 'group-checkbox-component',
    templateUrl: './group.checkbox.component.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class GroupCheckBoxComponent extends AbstractComponent implements ControlValueAccessor, AfterViewInit {

    @Input() optionsLabel: string[] = []; 

    @Input() controFieldName: string;
    
    //current form control input. helpful in validating and accessing form control
    @Input() control: FormArray = new FormArray([]);

    @Input() position: PositionType = PositionType.HORIZONTAL;

    @Input() rowClass: string = "row";

    @Input() columnClass: string = "checkbox-group col-sm-4 col-xs-12";

    private _checkBoxs: QueryList<any>;

    // get reference to the input element
    @ViewChildren('checkBoxcomponent') set checkBoxs(checkBoxs: QueryList<any>) {
        this._checkBoxs = checkBoxs
    }; 

    constructor(translate: TranslateService) {
        super(translate)
    }


    //Lifecycle hook. angular.io for more info
    ngAfterViewInit(){ 
        // set placeholder default value when no input given to pH property      
        /* if(this.pH === undefined){
            this.pH = "Enter "+this.text; 
        } */
        // RESET the custom input form control UI when the form control is RESET
        if (this.control) {
            this.control.valueChanges.subscribe(
                () => {
                    // check condition if the form control is RESET
                    if (this.control.value == "" || this.control.value == null || this.control.value == undefined || this.control.controls.length == 0) {
                        this.innerValue = "";
                        if (this._checkBoxs && this._checkBoxs.length != 0) {
                            this._checkBoxs.forEach(elemRef => {
                                elemRef.nativeElement.value = false;
                            })
                        }
                    }
                    else if (this.control.controls.length != 0) {
                        this.control.controls.forEach((ctrol, index) => {
                            if (ctrol instanceof FormGroup) {
                                if (ctrol.get(this.controFieldName) !== undefined && this._checkBoxs && this._checkBoxs.length != 0 && this._checkBoxs.length - 1 >= index ) {                                
                                    this._checkBoxs.map(ck => ck.nativeElement ) .find(ck => { 
                                        return ck.id === 'ck_' + index} 
                                    ).value = ctrol.get(this.controFieldName).value;
                                }
                            }
                        });

                    }
                }
            );

            this.control.statusChanges.subscribe(() => {
                this.checkErro();
            });
        }
    }

    //get accessor
    get value(): any {
        return this.innerValue;
    };

    //set accessor including call the onchange callback
    set value(v: any) {
        if (v !== this.innerValue) {
            this.innerValue = v;
        }
    }

    //From ControlValueAccessor interface
    writeValue(value: any) {
        this.innerValue = value;
    }

    //From ControlValueAccessor interface
    registerOnChange(fn: any) {
        this.propagateChange = fn;
    }

    //From ControlValueAccessor interface
    registerOnTouched(fn: any) {

    }

    getFormGroup(item: AbstractControl): FormGroup {
        return <FormGroup>item;
    }

    isHorizontal(): boolean {
        return this.position === PositionType.HORIZONTAL;        
    }

    isVertical(): boolean {
        return this.position === PositionType.VERTICAL;        
    }
}