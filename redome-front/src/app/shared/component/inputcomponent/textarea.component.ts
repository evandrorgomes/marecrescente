import { FormGroup, NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl } from '@angular/forms';
import { Component, Input, forwardRef, AfterViewInit, OnChanges, ElementRef, ViewChild } from "@angular/core";
import { TranslateService } from '@ngx-translate/core';
import { REQUIRED, INVALID_DATE, INVALID_DATE_BEFORE_TODAY, INVALID_DATE_AFTER_TODAY, INVALID_HOUR, INVALID_DATETIME } from '../../../validators/data.validator';
import { AbstractComponent } from 'app/shared/component/inputcomponent/abstract.component';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => TextAreaComponent),
    multi: true
};

@Component({
    selector: 'textarea-component',
    templateUrl: './textarea.component.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class TextAreaComponent extends AbstractComponent implements ControlValueAccessor, AfterViewInit {

    // ID attribute for the field and for attribute for the label
    @Input()  idd = "";

    // placeholder input
    @Input() placeHolder: string;

    @Input() rows: string;

    @Input() maxlength: string;

    // get reference to the input element
    @ViewChild('textarea')  inputRef:ElementRef;

    constructor(translate: TranslateService) {
        super(translate);
    }

    //Lifecycle hook. angular.io for more info
    ngAfterViewInit(){
        // set placeholder default value when no input given to pH property
        /* if(this.pH === undefined){
            this.pH = "Enter "+this.text;
        } */
        //console.log(this.control);
        // RESET the custom input form control UI when the form control is RESET
        if (this.control) {
            /*this.control.valueChanges.subscribe(
               () => {
                   // check condition if the form control is RESET
                   if (this.control.value == "" || this.control.value == null || this.control.value == undefined) {
                       this.innerValue = "";
                       this.inputRef.nativeElement.value = "";
                   } else {
                       this.innerValue = this.control.value;
                       this.inputRef.nativeElement.value = this.control.value;
                   }
               }
            );*/

            this.control.statusChanges.subscribe(() => {
                this.checkErro();
                if (this.control.disabled) {
                    this.inputRef.nativeElement.disabled = true;
                }
                else {
                    this.inputRef.nativeElement.disabled = false;
                }
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
        //if (value) {
            this.innerValue = value;
            this.inputRef.nativeElement.value = this.innerValue;
            this.checkErro(true);
        //}
    }

    //From ControlValueAccessor interface
    registerOnChange(fn: any) {
        this.propagateChange = fn;
    }

    //From ControlValueAccessor interface
    registerOnTouched(fn: any) {

    }

    public getClazz(): string {
        let clazz = "";
         if (this.inputRef) {
            clazz = this.inputRef.nativeElement.parentElement.className;
        }
        return "form-control " + clazz;
    }

    getMaxLength(): string {
        return this.maxlength;
    }

}
