import { AbstractControl,FormControl } from '@angular/forms';

export function localizado(value: boolean) {
    return (control: FormControl) => {
        return { validLocalizado: value };
    }
}