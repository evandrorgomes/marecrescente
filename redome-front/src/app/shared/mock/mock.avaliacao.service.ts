import { Pendencia } from '../../paciente/avaliacao/pendencia';
import { Avaliacao } from '../../paciente/avaliacao/avaliacao';
import { TipoPendencia } from '../dominio/tipo.pendencia';
import { CampoMensagem } from '../campo.mensagem';
import { ErroMensagem } from '../erromensagem';
import { Observable } from 'rxjs';
export class MockAvaliacaoService {

    erroMensagem = {
        listaCampoMensagem: [{            
        }],
        statusCode: 422,
        statusText: "Undefined"
    }
    
    /**
     * Metodo q retorna uma lista de pendencia vazia
     * 
     * @memberof MockAvaliacaoService
     */
    listarPendencias(){
        let pendencias:Pendencia[] = [];
        
        return Promise.resolve(pendencias);
    }


}

