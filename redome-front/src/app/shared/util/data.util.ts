import { PacienteUtil } from "../paciente.util";
import { DateTypeFormats } from "./date/date.type.formats";
import { DateMoment } from "./date/date.moment";

export class DataUtil{

    private locale: string = "pt-br";

    public static calcularIdade(dataNascimento): number {
        let nascimento: Date;
        if (dataNascimento instanceof Date ) {
            nascimento = dataNascimento;
        }
        else {
            nascimento = PacienteUtil.converterStringEmData(dataNascimento);
        }

        let idade: number;
        if(nascimento instanceof Date){
            let anoNasc: number = nascimento.getFullYear(),
                mesNasc = nascimento.getMonth(),
                diaNasc = nascimento.getDate();

            let hoje = new Date(),
                anoAtual: number = hoje.getFullYear(),
                mesAtual: number = hoje.getMonth(),
                diaAtual: number = hoje.getDate();

            idade = anoAtual - anoNasc;
            let naoFezAnivAnoAtual: boolean =
                (mesAtual < (mesNasc - 1) || (((mesNasc - 1) === mesAtual) && diaAtual < diaNasc));

            if (naoFezAnivAnoAtual) {
                idade--;
            }
        }
        
        return idade;
    }

    public static calcularIdadeComDate(dataNascimento:Date): number {

        let anoNasc: number = dataNascimento.getFullYear(),
            mesNasc = dataNascimento.getMonth() + 1,
            diaNasc = dataNascimento.getDate();

        let hoje = new Date(),
            anoAtual: number = hoje.getFullYear(),
            mesAtual: number = hoje.getMonth() + 1,
            diaAtual: number = hoje.getDate();

        let idade: number = anoAtual - anoNasc;
        let naoFezAnivAnoAtual: boolean =
            ((mesAtual < mesNasc) || (mesNasc == mesAtual && diaAtual < diaNasc));

        if (naoFezAnivAnoAtual) {
            idade--;
        }
        
        return idade;
    }

    public static dataStringToDate(dataString:any):Date{
        if(!dataString){
            return null;
        }
        
        return new Date(dataString.replace(/-/g, '\/'));
    }

    public static dataDateToString(data:Date):string{
        if(data){
            return data.toLocaleDateString();
        }
        return null;
    }

    public static truncarHora(data: Date): Date{
        if(data){
            data.setHours(0);
            data.setMinutes(0);
            data.setSeconds(0);
            data.setMilliseconds(0);
            return data;
        }
        return null;
    }
    
    /**
     * Formata a data, quando o parse no JSON recebe uma string no campo
     * data.
     * 
     * FIXME: Corrigir a forma como a data vem do backend.
     */ 
    public static toDateFormat(date: Date, format: DateTypeFormats): string{
        if(date == null){
            return null;
        }
        let dateMoment: DateMoment = DateMoment.getInstance();

        let dateString: string = null;

        try{
            dateString = dateMoment.format(date, format);
        } catch(error){
            dateString = dateMoment.format(DataUtil.dataStringToDate(date.toString()), format);
        }
        return dateString;
    }

    /**
     * Transforma a string em data, independente do formato.
     * Por padrão, a data vem invertida do back-end (aaaa-mm-dd),
     * no front-end segue o padrão do Locale informado.
     * 
     * TODO: Corrigir a forma como a data vem do backend.
     */
    public static toDate(dateString: string, format: DateTypeFormats = DateTypeFormats.DATE_ONLY): Date{
        if(dateString == null){
            return null;
        }
        let dateMoment: DateMoment = DateMoment.getInstance();
        let date: Date = dateMoment.parse(dateString, format);
        if(dateMoment.isInvalid(date)){
            date = DataUtil.dataStringToDate(dateString);
        }

        return date;
    }
}