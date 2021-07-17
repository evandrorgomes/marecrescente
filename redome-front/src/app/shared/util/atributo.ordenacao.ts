/**
 * Classe utilizada para ordenacao de tarefa.
 * @author Fillipe Queiroz
 */
export class AtributoOrdenacao {
    nomeAtributo: String;
    asc: Boolean;

    constructor(nome: string = null, asc: boolean = false){
        this.nomeAtributo = nome;
        this.asc = asc;
    }
}