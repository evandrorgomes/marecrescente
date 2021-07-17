import { Observable } from 'rxjs';
import { Solicitacao } from '../../doador/solicitacao/solicitacao';
export class MockSolicitacaoService {

    cancelarFase3Paciente(idSolicitacao: number, justificativa: string): Promise<any> {        
        return Observable.of(new Solicitacao()).toPromise();
    }

    public solicitarFase3Paciente(idBusca, idLaboratorio: number): Promise<any> {
        return null;
    }
}