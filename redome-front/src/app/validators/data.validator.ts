import { TranslateService } from '@ngx-translate/core';
import { Injectable } from '@angular/core';
import { AbstractControl,FormControl, ValidationErrors } from '@angular/forms';
import { PacienteUtil } from '../shared/paciente.util';
import { DataUtil } from '../shared/util/data.util';
import { isNullOrUndefined } from 'util';

export const REQUIRED: string = "required" 
export const INVALID_PATTERN: string = "pattern"
export const INVALID:string = "invalid"
export const INVALID_DATE: string = "invalidDate" 
export const INVALID_DATE_BEFORE_TODAY: string = "invalidDateBeforeToday" 
export const INVALID_DATE_AFTER_TODAY: string = "invalidDateAfterToday" 
export const INVALID_HOUR: string = "invalidHour" 
export const INVALID_DATETIME: string = "invalidDateTime" 
export const INVALID_MAX_ITENS: string = "invalidMaxItens"
export const INVALID_CUSTOM: string = "invalidCustom"


import * as moment from 'moment';
import { DateMoment } from '../shared/util/date/date.moment';
import { DateTypeFormats } from '../shared/util/date/date.type.formats';


export function ValidateData(control: FormControl): ValidationErrors {
    let value: string = control.value;
    if (value && value.length <= 10) {
        let dateMoment: DateMoment = DateMoment.getInstance();
        let data: Date = dateMoment.parse(control.value, DateTypeFormats.DATE_ONLY);        
        if(!moment(data).isValid()) {
            return { "invalidDate"  : true }
        }
    }
    return null;
}

export function ValidateDataMenorQueHoje(control: FormControl): { [s: string]: boolean } {
    var data = PacienteUtil.obterDataSemMascara(control.value);
    var dataAtual = DataUtil.truncarHora(new Date());
    
    if(data > dataAtual) {
        return { "invalidDateBeforeToday": true };
    }
    return null;
}

export function ValidateDataMaiorOuIgualHoje(control: FormControl): { [s: string]: boolean } {
    var data = PacienteUtil.obterDataSemMascara(control.value);
    var dataAtual = DataUtil.truncarHora(new Date());

    if(data < dataAtual) {
        return { "invalidDateAfterToday": true };
    }
    return null;
}

export function ValidateHoraValida(control: FormControl): { [s: string]: boolean } {

    let horaTotal: string = control.value;
    
    if(horaTotal == null || horaTotal.length < 5 || horaTotal.indexOf(":")== -1){
        return null;
    }
    
    let hora:Number = new Number( horaTotal.split(":")[0]);
    let minuto:Number = new Number( horaTotal.split(":")[1]);

    if(hora > 23 || hora < 0){
        return {"invalidHour": true};
    }else if(minuto > 60 || minuto < 0){
        return {"invalidHour": true};
    }else{
        return null;
    }
}

export function ValidateDataHora(control: FormControl): { [s: string]: boolean } {
    let value: string = control.value;
    value = value ? value.replace(/_/gi, ""): null;
    if (value && value.length <= 16) {
        let dateMoment: DateMoment = DateMoment.getInstance();        
        if(!dateMoment.isValid(value, DateTypeFormats.DATE_TIME)) {
            return { "invalidDateTime"  : true }
        }
    }
    return null;
}



