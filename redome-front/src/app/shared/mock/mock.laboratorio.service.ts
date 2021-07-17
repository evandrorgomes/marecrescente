import { Observable } from 'rxjs';

/**
 * Classe de serviço mocados para interação com a tabela de laboratorio
 * 
 * @author Bruno Sousa
 * @export
 * @class MockLaboratorioService
 
 */
export class MockLaboratorioService  {

    listarLaboratorios(): Promise<any> {
        return Observable.of(null).toPromise();
    }

    listarLaboratoriosCT(): Promise<any> {
        return Observable.of(null).toPromise();
    }

    listarLaboratoriosCTExame(): Promise<any> {
        return Observable.of(null).toPromise();
    }

}