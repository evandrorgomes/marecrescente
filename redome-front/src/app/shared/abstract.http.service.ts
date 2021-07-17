import { ErroMensagem } from "app/shared/erromensagem";
import { CampoMensagem } from "./campo.mensagem";
import { Observable } from "rxjs/Observable";
import { Response } from '@angular/http';

export abstract class AbstractHttpService {

/**
     * Método para tratamento de erro
     */
    protected handleError(error: Response | any) {
        let erromensagem: ErroMensagem;
        let errMsg: string;
        if (error instanceof Response) {
            erromensagem = new ErroMensagem(error.status, error.statusText);
            let body;
            try {
                body = error.json();
            }
            catch (e) {
                body = '';
            }

            if (body instanceof ProgressEvent) {
                erromensagem = new ErroMensagem(999, '', 'Servidor está offline.');
                return Observable.throw(erromensagem);
            }

            if (body != '') {
                if (body.campo) {
                    erromensagem.addCampoMensagem(body);
                } else {
                    body.erros.forEach(element => {
                        erromensagem.addCampoMensagem(element);
                    });
                }

            }
            else {
                let campoMensagem: CampoMensagem = new CampoMensagem();
                campoMensagem.campo = 'erro';
                campoMensagem.mensagem = error.text()
                erromensagem.addCampoMensagem(campoMensagem);
            }
            throw erromensagem;
        }
        else if (error instanceof ErroMensagem ) {
            if (error.statusCode != 8888) {
                throw error;
            }
        }
    }
    
    /**
     * Método para manipular mensagens de erro do post de salvar com arquivo de upload
     * 
     * @param {*} error 
     * @returns 
     * @memberof HttpClient
     */
    protected handleErrorUpload(error: any) {
        let erromensagem: ErroMensagem = new ErroMensagem(999, '', );;
        if (error.erros) {
            error.erros.forEach(element => {
                erromensagem.addCampoMensagem(element);
            });
        }else{
            let campoMensagem: CampoMensagem = new CampoMensagem();
            campoMensagem.campo = "";
            campoMensagem.mensagem = error;

            erromensagem.addCampoMensagem(campoMensagem);
        }
        throw erromensagem;
        //  return Observable.throw(erromensagem);
    }

}