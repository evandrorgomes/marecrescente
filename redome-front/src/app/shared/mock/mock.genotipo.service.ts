import { Observable } from 'rxjs';

/**
 * Classe de serviço mocados para interação com a tabela de genotipo
 * 
 * @author Bruno Sousa
 * @export
 * @class MockGenotipoService
 
 */
export class MockGenotipoService  {

    obterGenotipo(rmr: number): Promise<any> {
        return Observable.of(null).toPromise();
    }
    
    listarGenotiposComparados(rmr: number, listaIdsDoadores: number[]): Promise<any> {
        return Observable.of(null).toPromise();
    }

}