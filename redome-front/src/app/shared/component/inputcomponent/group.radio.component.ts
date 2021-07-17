import { AbstractComponent } from 'app/shared/component/inputcomponent/abstract.component';
import { AbstractControl } from '@angular/forms';
import { FormGroup, NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl, FormArray } from '@angular/forms';
import { Component, Input, forwardRef, AfterViewInit, OnChanges, ElementRef, ViewChild, QueryList, ViewChildren, AfterViewChecked } from "@angular/core";
import { TranslateService } from '@ngx-translate/core';

export const CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => GroupRadioComponent),
    multi: true
};

export enum PositionType {
    HORIZONTAL = 0,
    VERTICAL = 1
}

@Component({
    selector: 'group-radio-component',
    templateUrl: './group.radio.component.html',
    providers: [CUSTOM_INPUT_CONTROL_VALUE_ACCESSOR]
})
export class GroupRadioComponent extends AbstractComponent implements ControlValueAccessor, AfterViewInit {

    @Input() id:string = "id_radio";

    @Input() optionsLabel: string[] = [];

    @Input() optionsValue: string[] = [];

    @Input() position: PositionType = PositionType.HORIZONTAL;

   @Input() groupClass: string = "form-group";

    @Input() rowClass: string = "row";

    @Input() columnClass: string = "radio-group col-sm-4 col-xs-12";

    @Input() columnStyle: string = "";

    private _radios: QueryList<any>;

    // get reference to the input element
    @ViewChildren('radioComponent') set radios(radios: QueryList<any>) {
        this._radios = radios

        this.updateView();
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
             this.control.valueChanges.subscribe( val => {
                this.checkErro();
             });
            //     () => {
            //         // check condition if the form control is RESET
            //         if (this.control.value == "" || this.control.value == null || this.control.value == undefined ) {
            //             this.innerValue = "";
            //             if (this._radios && this._radios.length != 0) {
            //                 this._radios.forEach((elemRef, index) => {
            //                     elemRef.nativeElement.checked = false;
            //                 })
            //             }
            //         }
            //         else {
            //             this.innerValue = this.control.value;
            //             if (this._radios && this._radios.length != 0) {
            //                 this._radios.forEach((elemRef, index) => {
            //                     if (elemRef.nativeElement.value == this.innerValue) {
            //                         elemRef.nativeElement.checked = true;
            //                     }
            //                 });
            //             }
            //         }
            //     }
            // );

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
        this.updateView();
    }

    private updateView() {
        if (this.innerValue == null || this.innerValue == "") {
            if (this._radios && this._radios.length != 0) {
                this._radios.forEach((elemRef, index) => {
                    elemRef.nativeElement.checked = false;
                })
            }
        }
        else {
            if (this._radios && this._radios.length != 0) {
                this._radios.forEach((elemRef, index) => {
                    if (elemRef.nativeElement.value == this.innerValue) {
                        elemRef.nativeElement.checked = true;
                    }
                });
            }
        }
        // this.checkErro();
    }

    //From ControlValueAccessor interface
    registerOnChange(fn: any) {
        this.propagateChange = fn;
    }

    //From ControlValueAccessor interface
    registerOnTouched(fn: any) {
        this.propagateTouched = fn
    }

    isHorizontal(): boolean {
        return this.position === PositionType.HORIZONTAL;
    }

    isVertical(): boolean {
        return this.position === PositionType.VERTICAL;
    }

}
