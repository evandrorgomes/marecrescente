import { forEach } from '@angular/router/src/utils/collection';
import { TranslateService } from '@ngx-translate/core';
import { AbstractComponent } from 'app/shared/component/inputcomponent/abstract.component';
import { FormGroup, NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl } from '@angular/forms';
import { Component, Input, forwardRef, AfterViewInit, OnChanges, ElementRef, ViewChild } from "@angular/core";
import { INVALID_MAX_ITENS } from 'app/validators/data.validator';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => SelectComponent),
    multi: true
};

@Component({
    selector: 'select-component',
    templateUrl: './select.component.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class SelectComponent extends AbstractComponent implements ControlValueAccessor, AfterViewInit {

    // ID attribute for the field and for attribute for the label
    @Input()  idd = ""; 

    @Input() options: Array<any>;

    @Input() fieldOptionValue: string = "";

    @Input() fieldOptionText: string = "";

    @Input() multipleSelect: boolean = false;

    @Input() showBlankOption: boolean = true;

    @Input() quantidadeItens: number = 0;

    // get reference to the input element
    @ViewChild('select')  selectRef:ElementRef; 
    
    constructor(translate: TranslateService) {
        super(translate);
    }

    //Lifecycle hook. angular.io for more info
    ngAfterViewInit(){ 
        if(this.control){
            this.control.valueChanges.subscribe(
                val => {
                    // check condition if the form control is RESET
                    if (val == "" || val == null || val == undefined) {
                        this.innerValue = null;      
                        this.selectRef.nativeElement.value = "";
                        if (this.multipleSelect) {                        
                            for (let index = 0; index < this.selectRef.nativeElement.options.length; index++) {
                                this.selectRef.nativeElement.options[index].selected = false;
                            }                        
                        }
                    }
                    else {
                        this.innerValue = val;
                        if (this.multipleSelect) {
                            for (let idx = 0; idx < this.control.value.length; idx++) {
                                for (let index = 0; index < this.selectRef.nativeElement.options.length; index++) {                            
                                    if (this.selectRef.nativeElement.options[index].value == this.control.value[idx]) {
                                        this.selectRef.nativeElement.options[index].selected = true;
                                    }
                                }
                            }
                        }
                        else {
                                                this.selectRef.nativeElement.value = val;
                        }
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

    public getOptionValue(value: any): any {
        if (value && this.fieldOptionValue) {
            return value[this.fieldOptionValue]
        }
        return value;
    }

    public getOptionText(value: any): string {
        if (value && this.fieldOptionText) {
            return value[this.fieldOptionText]
        }
        return value;
    }

    // event fired when input value is changed . later propagated up to the form control using the custom value accessor interface
    onChange(e:Event, value:any) {
        //set changed value
        let selectedValue: any;
        if (this.multipleSelect) {
            let target: any = e.target;            
            selectedValue = [];
            for (let index = 0; index < target.options.length; index++ ) {
                if (target.options[index].selected) {
                    selectedValue.push(target.options[index].value);
                }
            }
        }
        else {
            selectedValue = value;
        }
        super.onChange(e, selectedValue);
        
    }

    public getClazz(): string {
        let clazz = "";
         if (this.selectRef) {
            clazz = this.selectRef.nativeElement.parentElement.className;
        }         
        return "form-control " + clazz;
    }


}