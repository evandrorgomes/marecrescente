import { AfterViewInit, Component, ElementRef, forwardRef, Input, ViewChild, Renderer } from "@angular/core";
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { AbstractComponent } from 'app/shared/component/inputcomponent/abstract.component';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => InputComponent),
    multi: true
};

@Component({
    selector: 'input-component',
    templateUrl: './input.component.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class InputComponent extends AbstractComponent implements ControlValueAccessor, AfterViewInit {

    // Input field type eg:text,password
    @Input()  type = "text"; 

    // ID attribute for the field and for attribute for the label
    @Input()  idd = ""; 

    // placeholder input
    @Input() placeHolder: string; 

    // get reference to the input element
    @ViewChild('input')  inputRef:ElementRef; 

    @Input() mascara: Array<string | RegExp>;

    @Input() allowAllUpperCase: boolean = false;

    @Input() maxLength: string = "100";

    @Input() disabled: string = "";

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
            /* this.control.valueChanges.subscribe(
                () => {
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
            ); */

            if (this.control.disabled) {
                this.inputRef.nativeElement.disabled = true;
            }
            
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

        this.inputRef.nativeElement.allowAllUpperCase = this.allowAllUpperCase;
        this.inputRef.nativeElement.oninput = function() {
            if (this.allowAllUpperCase) {
                this.value = this.value.toUpperCase();
            }
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

    public getMascara() {
        return this.mascara ? this.mascara : false;
    }

    public getClazz(): string {
        let clazz = "";
        if (this.inputRef) {
            clazz = this.inputRef.nativeElement.parentElement.className;
        }         
        return "form-control " + clazz;
    }

    public onInputFunction(allowAllUpperCase: Boolean): void {
        console.log(allowAllUpperCase);
        if (allowAllUpperCase) {
            this.value = this.value.toUpperCase();
        }
    }

    public focus() {
        this.inputRef.nativeElement.focus();
    }


}