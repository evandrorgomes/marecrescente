import { deprecate } from 'util';
import { DataUtil } from 'app/shared/util/data.util';
import { TranslateService } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { resolve } from 'url';
import { Cid } from 'app/shared/dominio/cid';
import { MessageBox } from 'app/shared/modal/message.box';
import { DominioService } from 'app/shared/dominio/dominio.service';
export class PacienteUtil {

    public static obterCPFSemMascara(_cpfComMascara: any): string {
        let cpf: string;

        if (_cpfComMascara) {
            cpf = _cpfComMascara as string
            return cpf.replace('.', '').replace('.', '').replace('-', '');
        }
        return cpf;
    }

    public static obterDataSemMascara(data: string): Date {
        let dataSemMascara: Date;
        if (data) {
            let _data = data.split('/');
            //não sei se há uma forma melhor de fazer essa conversão
            dataSemMascara = new Date(_data[1] + '/' + _data[0] + '/' + _data[2])
        }
        return dataSemMascara;
    }

    /**
     * Formata o valor, passada como parametro, para o formato em casa decimais solicitado.
     *
     * @param valor string com o valor a ser formatado.
     * Ex: 10.5, 105 (sem o separador), 10.52, com 1 casa decimal ficaria 10.5
     * @param decimais
     */
    public static arredondar(valor:string, decimais:number): number {
        if(valor == null) {
            return null;
        }

        valor = String(valor);

        let temSeparador:boolean = valor.indexOf(".") != -1;

        if(!temSeparador) {
            let valorInteiro:string;
            let valorDecimais:string;

            if(valor.length > 2) {
                valorInteiro = valor.substring(0, valor.length - decimais);
                valorDecimais = valor.substr(valor.length - decimais, decimais);
            } else {
                valorInteiro = valor;
                valorDecimais = "0";
            }
            valor = valorInteiro + "." + valorDecimais;
        }

        return Number(Number(valor).toFixed(decimais));
    }



    /**
     * Converte string no formato YYYY/MM/dd em data
     *
     * @static
     * @param {*} data
     * @returns {Date}
     *
     * @memberOf PacienteUtil
     */
    public static converterStringEmData(data:any):Date{
        if(data){
            if (data instanceof Date ) {
                return data;
            }

            let ano:number = Number.parseInt(data.substring(0, 4));
            let mes:number = Number.parseInt(data.substring(5, 7));
            let dia:number = Number.parseInt(data.substring(8));
            if (isNaN(ano) || isNaN(mes) || isNaN(dia) ) {
                return null;
            }
            return new Date(ano + '/' + mes + '/' + dia);
        }
        return null;
    }

    public static parserDataComHora(data:any):Date{
        if(data){
            let ano:number = Number.parseInt(data.substring(0, 4));
            let mes:number = Number.parseInt(data.substring(5, 7));
            let dia:number = Number.parseInt(data.substring(8, 10));

            let hora:number = Number.parseInt(data.substring(11, 13));
            let minutos:number = Number.parseInt(data.substring(14, 16));
            let dataRetorno:Date = new Date(ano + '/' + mes + '/' + dia);
            dataRetorno.setHours(hora);
            dataRetorno.setMinutes(minutos);
            return dataRetorno;
        }
        return null;
    }

    public static isDate(data:string):Boolean{
        try{
            let dataFormatada = PacienteUtil.converterStringEmData(data);
            return (dataFormatada.toString() !== "Invalid Date");
        } catch(error){
            return false;
        }
    }

    public static retornaNomeArquivoExameSelecionado(caminhoArquivo:String):String{
        let indexDaBarra = caminhoArquivo.indexOf("/")+1;
        caminhoArquivo = caminhoArquivo.substring(indexDaBarra, caminhoArquivo.length);
        let indexDoUnderline = caminhoArquivo.indexOf("_");
        caminhoArquivo = caminhoArquivo.substring(indexDoUnderline + 1);
        return caminhoArquivo;
    }

    /**
     * Método para obter a data com separador
     * antes de enviar para o back
     * @param data
     */
    public static obterDataComSeparador(data: string): Date {
        let dia: string = data.substring(0,2);
        let mes: string = data.substring(2,4);
        let ano: string = data.substring(4,10);

        return new Date(ano + '/' + mes + '/' + dia);
    }

    public static retornarIdadeFormatada(dataNascimento: Date, translate: TranslateService): Promise<string> {
        let idadeEmAnos: number = DataUtil.calcularIdade(dataNascimento);
        let data: Date =
            PacienteUtil.converterStringEmData(dataNascimento);
        return translate.get("textosGenericos.anos").toPromise()
            .then(res => {
                return ("" + idadeEmAnos + " " + res + " (" + data.toLocaleDateString() + ")");
            });

    }


    public static retornarPesoFormatado(peso:number, translate: TranslateService): Promise<string> {
        return translate.get("textosGenericos.kilograma").toPromise()
            .then(res => {
                return ("" + peso + " " + res );
            });
    }

    public static deveExibirMensagemCid(idade: number, cid: Cid): boolean {
        if(idade && cid){
            if (!cid.transplante
                || ((cid.idadeMinima) && (idade < cid.idadeMinima))
                || ((cid.idadeMaxima) && (idade > cid.idadeMaxima)) ){
                return true;
            }
        }
        return false;
    }

}
