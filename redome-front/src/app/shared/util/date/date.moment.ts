import * as moment from 'moment';
import { environment } from 'environments/environment';
import { DateTypeFormats } from './date.type.formats';

/**
 * Classe singleton que define a formatação da Data para o TypeScript.
 * Nesta classe está centralizada a mudança de Locale, quando a mesma ocorrer.
 * 
 * @author Pizão
 */
export class DateMoment{

/*     public static readonly LOCALE_BR: string = "pt-br";
    public static readonly LOCALE_EN: string = "en";
    public static readonly LOCALE_ES: string = "es"; */

    private static _instance: DateMoment;

    private constructor(){}

    public set locale(locale: string){
        moment.locale(locale);
    }

    public get locale(): string{
        return moment.locale();
    }

    public static getInstance(): DateMoment{
        if(!this._instance){
            this._instance = new DateMoment();
            this._instance.locale = environment.locale;
        }
        return this._instance;
    }

    public format(data: Date, format: DateTypeFormats = DateTypeFormats.DATE_ONLY): string{
        if(moment.isDate(data)){
            return moment(data).format(format.pattern);
        }
        throw new Error("Parâmetro não é do tipo data: " + data);
    }

    public formatWithPattern(data: Date, pattern: string): string{
        if(moment.isDate(data)){
            return moment(data).format(pattern);
        }
        throw new Error("Parâmetro não é do tipo data: " + data);
    }

    public parse(value: string, format?: DateTypeFormats): Date{
        if (format) {            
            return moment(value, format.pattern).toDate();
        } else {
            return moment(value).toDate();
        }
    }

    public diffDays(d1: Date, d2: Date): number{
        return moment(d1).startOf("days").diff(moment(d2).startOf("days"), "days");
    }

    /**
     * Se primeira data (d1) é menor que a segunda (d2).
     * 
     * @param d1 primeira data.
     * @param d2 segunda data.
     * 
     * @return TRUE se a primeira é menor que a segunda.
     */
    public isDateBefore(d1: Date, d2: Date): boolean{
        return moment(d1).isBefore(moment(d2), "days");
    }

    /**
     * Se primeira data (d1) é a mesma ou menor que a segunda (d2).
     * 
     * @param d1 primeira data.
     * @param d2 segunda data.
     * 
     * @return TRUE se a primeira é a mesma ou menor que a segunda.
     */
    public isDateSameOrBefore(d1: Date, d2: Date): boolean{
        return moment(d1).isSameOrBefore(moment(d2), "days");
    }

    /**
     * Se primeira data/hora (d1) é a mesma ou menor que a segunda (d2).
     * 
     * @param d1 primeira data/hora.
     * @param d2 segunda data/hora.
     * 
     * @return TRUE se a primeira é a mesma ou menor que a segunda.
     */
    public isDateTimeSameOrBefore(d1: Date, d2: Date): boolean{
        return moment(d1).isSameOrBefore(moment(d2));
    }

    /**
     * Se primeira data (d1) é maior que a segunda (d2).
     * 
     * @param d1 primeira data.
     * @param d2 segunda data.
     * 
     * @return TRUE se a primeira é maior  que a segunda.
     */
    public isDateAfter(d1: Date, d2: Date): boolean{
        return moment(d1).isAfter(moment(d2), "days");
    }

    /**
     * Se primeira data (d1) é a mesma ou maior que a segunda (d2).
     * 
     * @param d1 primeira data.
     * @param d2 segunda data.
     * 
     * @return TRUE se a primeira é a mesma ou  maior  que a segunda.
     */
    public isDateSameOrAfter(d1: Date, d2: Date): boolean{
        return moment(d1).isSameOrAfter(moment(d2), "days");
    }

    /**
     * Verifica se a data é inválida.
     * 
     * @param date 
     */
    public isInvalid(date: Date): boolean{
        return !this.isValid(date);
    }

    /**
     * Se primeira data (d1) é igual que a segunda (d2).
     * 
     * @param d1 primeira data.
     * @param d2 segunda data.
     * 
     * @return TRUE se a primeira é igual que a segunda.
     */
    public isSame(d1: Date, d2: Date): boolean{
        return moment(d1).isSame(moment(d2), "days");
    }

    /**
     * Verifica se a data é válida.
     * 
     * @param date 
     */
    isValid(date: Date): boolean;
    isValid(value: string, format: DateTypeFormats): boolean;
    isValid(dateOrValue: Date |string, format?: DateTypeFormats): boolean {        
        if (typeof dateOrValue === 'string') {
            return moment(dateOrValue, format.pattern, true).isValid();
        }
        else if (Object.prototype.toString.call(dateOrValue) === '[object Date]') {
            return moment(dateOrValue).isValid();
        }

        return false;        
    }

    public getToJson(): any {
        return function(): string {
            let dataTemp = new Date(this.getTime() - (this.getTimezoneOffset() * 60000));
            return dataTemp.toJSON();
        }
    }

    /**
     * Se primeira data/hora (d1) é a mesma ou maior que a segunda (d2).
     * 
     * @param d1 primeira data/hora.
     * @param d2 segunda data/hora.
     * 
     * @return TRUE se a primeira é a mesma ou menor que a segunda.
     */
    public isDateTimeSameOrAfter(d1: Date, d2: Date): boolean{
        return moment(d1).isSameOrAfter(moment(d2));
    }

    public adicionarMeses(data: Date, meses: number): Date {
        return moment(data).add(meses, 'month').toDate();
    }
    

}