import { CampoMensagem } from './campo.mensagem';

/** Classe que representa a correlação entre as mensagens de erro e os campos do formulário 
 * @author Bruno Sousa
 */
export class ErroMensagem{

    private _statusCode: number;
    private _statusText: String;
    private _mensagem: String;
    private _listaCampoMensagem: CampoMensagem[] = [];

    constructor(statusCode: number, statusText: String, mensagem?: String){
         this._statusCode = statusCode;
         this._statusText = statusText;
         this._mensagem = mensagem;
    }

    get statusCode(): Number {
        return this._statusCode;
    }

    get statusText(): String {
        return this._statusText;
    }

    get mensagem(): String {
        return this._mensagem;
    }

    set mensagem(mensagem: String) {
        this._mensagem = mensagem;
    }

    addCampoMensagem(campoMensagem: CampoMensagem): void {
        this._listaCampoMensagem.push(campoMensagem);
    }

    get listaCampoMensagem(): CampoMensagem[] {
        return this._listaCampoMensagem;
    }

    removeCampoMensagem(index: number): void {
        this._listaCampoMensagem.splice(index, 1);
    }

}