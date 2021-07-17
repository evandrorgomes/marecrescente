/**
 * Classe Bean utilizada para definir os campos de País 
 * pode ser utilizada por qualquer classe.
 * @author Fillipe Queiroz
 */
export class CepCorreio{
    cep:String;
    uf:String;
    localidade:String;
    bairro:String;
    tipoLogradouro:String;
    logradouro:String;
    codigoIbge: string;

    constructor(value: Object = {}){
         Object.assign(this, value);
    }

}