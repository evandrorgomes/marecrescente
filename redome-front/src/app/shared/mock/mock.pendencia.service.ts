import { TipoPendencia } from '../dominio/tipo.pendencia';
import { CampoMensagem } from '../campo.mensagem';
import { ErroMensagem } from '../erromensagem';
import { Observable } from 'rxjs';
export class MockPendenciaService {

    erroMensagem = {
        listaCampoMensagem: [{            
        }],
        statusCode: 422,
        statusText: "Undefined"
    }
    

    listarTiposPendencia() {
        let tiposPendencia:TipoPendencia[] = [];
        
        return Promise.resolve(tiposPendencia);
    }



}

