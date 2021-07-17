import { AbstractControl, ValidatorFn } from '@angular/forms';
import { Subscription } from 'rxjs/Subscription';

export const MATCH_OTHER: string = "matchOther" 
export const MIN_LENGTH: string = 'minlength';

export function matchOtherValidator(otherControlName: string): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
        const otherControl: AbstractControl = control.root.get(otherControlName);

        if (otherControl) {
            const subscription: Subscription = otherControl
                .valueChanges
                .subscribe(() => {
                    control.updateValueAndValidity();
                    subscription.unsubscribe();
                });
        }

        return (otherControl && control.value !== otherControl.value) ? {'matchOther': true} : null;
    };
}




