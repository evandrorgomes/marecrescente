import { Observable } from 'rxjs/Observable';

export class MockCrudService {

    listar(entidade: string, pagina: number, quantidadeRegistros: number): Promise<any> {
        return Observable.of(null).toPromise();
    }

}