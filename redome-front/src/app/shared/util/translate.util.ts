import { TranslateService } from "@ngx-translate/core";
import { ArrayUtil } from "./array.util";

/**
 * Auxiliar no tratamento das mensagens internacionalizadas criando
 * novos recursos, como passagem de parâmetros, por exemplo.
 * 
 * @author Pizão
 */
export class TranslateUtil {
    
    /**
     * Obtém a mensagem internacionalizada e com os parâmetros preenchidos.
     * 
     * @param text texto a ser internacionalizado.
     * @param parameters parâmetros que esse texto contém.
     * @return mensagem internacionalizada com os parametros preenchidos.
     */
    public static getMessage(text: string, ...parameters: string[]): string{
        if(text && ArrayUtil.isNotEmpty(parameters)){
            return text.replace(/{(\d+)}/g, function(match, number) { 
                return typeof parameters[number] != 'undefined' ? parameters[number] : match;
            });
        }
        return text;
    }
}