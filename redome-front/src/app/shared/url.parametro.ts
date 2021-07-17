/**
 * Classe com a estrutura dos parametros a serem enviados ao back-end. 
 * @author Rafael Pizão
 */
export class UrlParametro {
    private _nome:String;
    private _valores:any;

    constructor(nome:String, valores?:any){
        this._nome = nome;
        this._valores = valores;
    }

    public get nome():String{
        return this._nome;
    }

    public get valores():any{
        return this._valores;
    }

    public toUrl():String{
        let urlFormatada:String;

        if(this._valores instanceof Array){
            urlFormatada = this.formatarListaParaParametroURL(this._valores);
        } else {
            urlFormatada = this._valores;
        }
        return this._nome + "=" + urlFormatada;
    }

     /**
     * Transforma de lista para parâmetro (String), utilizando o 
     * formato esperado para uso nas URLs do sistema.
     * 
     * @param lista
     * @return Retorna uma parâmetro string no formato esperado pela URL.
     * Ex.: ...?clienteId=1&2&3&4
     */
    private formatarListaParaParametroURL(lista: String[]): String{
        if(lista == null || lista.length == 0){
            return null;
        }

        let parametroString:String = "";
        lista.forEach(item => {
            let isUltimoItem: Boolean = (lista.lastIndexOf(item) == (lista.length - 1));
            parametroString += item + (isUltimoItem ? "" : ",");
        });

        return parametroString;
    }

}