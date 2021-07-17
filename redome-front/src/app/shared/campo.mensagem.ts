/**
 * Classe Bean utilizada para definir as mensagens de erro ou sucesso 
 * a serem exibidas após uma ação.
 * @author Fillipe Queiroz
 */
export class CampoMensagem{
    campo:string;
    mensagem:string;
    pai:string;
    tipo:string;
    index:number;

     /**
   * Método construtor.
   * @param object Objeto do tipo campoMensagem
   * @author Fillipe Queiroz
   */
    constructor(value: Object = {}){
         Object.assign(this, value);
    }

}