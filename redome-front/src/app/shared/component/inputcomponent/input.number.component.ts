import {AfterViewInit, Component, ElementRef, forwardRef, Input, ViewChild} from "@angular/core";
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';
import {TranslateService} from '@ngx-translate/core';
import {AbstractComponent} from 'app/shared/component/inputcomponent/abstract.component';
import { FormatterUtil } from "app/shared/util/formatter.util";

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => InputNumberComponent),
    multi: true
};

@Component({
    selector: 'input-number-component',
    templateUrl: './input.number.component.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class InputNumberComponent extends AbstractComponent implements ControlValueAccessor, AfterViewInit {

    // Input field type eg:text,password
    @Input()  type = "text";

    // ID attribute for the field and for attribute for the label
    @Input()  idd = "";

    // placeholder input
    @Input() placeHolder: string;

    // get reference to the input element
    @ViewChild('input')  inputRef:ElementRef;

    @Input() currencyOption: any = {};

    @Input() maxLength: string = "100";

    constructor(translate: TranslateService) {
        super(translate);
    }

    //Lifecycle hook. angular.io for more info
    ngAfterViewInit(){
        // set placeholder default value when no input given to pH property
        /* if(this.pH === undefined){
            this.pH = "Enter "+this.text;
        } */
        
        // RESET the custom input form control UI when the form control is RESET
        if (this.control) {
            if (this.control.disabled) {
                this.inputRef.nativeElement.disabled = true;
            }

            this.control.valueChanges.subscribe(
                () => {
                    if (this.control.disabled) {
                        this.inputRef.nativeElement.disabled = true;
                    }
                    else {
                        this.inputRef.nativeElement.disabled = false;
                    }
                    // check condition if the form control is RESET
                    if (this.control.value == "" || this.control.value == null || this.control.value == undefined) {
                        this.innerValue = "";      
                        this.inputRef.nativeElement.value = "";
                    }
                    else {
                        this.innerValue = this.control.value;
                        this.inputRef.nativeElement.value = this.control.value;
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

    public getClazz(): string {
        let clazz = "";
         if (this.inputRef) {
            clazz = this.inputRef.nativeElement.parentElement.className;
        }
        return "form-control " + clazz;
    }

}
