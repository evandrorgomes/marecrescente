import { AbstractControl } from '@angular/forms';
export class EventBind {

    focus(input: any, tooltip: any) {   
        if (input.errors && (input.dirty || input.touched)) {
            tooltip.show();
        }
        else {
            tooltip.hide();
        }        
    }

    blur(tooltip: any) {
        if (tooltip.isOpen) {
            tooltip.hide();
        }
    }

    keyup(input: any, tooltip: any) {
        if (!input.errors) {
            tooltip.hide();
        }        
    }



}