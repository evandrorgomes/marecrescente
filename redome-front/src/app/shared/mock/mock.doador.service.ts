import { Observable } from 'rxjs/Observable';


export class MockDoadorService {

    obterIdentificacaoDoadorPorId(id: number): Promise<any> {
        return Observable.of(null).toPromise();
    }

    listarDoadoresInternacionais(pagina: number, quantidadeRegistros: number, dmr: number, origem: number): Promise<any> {
        return Observable.of(null).toPromise();
    }

    public obterDoador(idDoador: number): Promise<any>{
        return Observable.of(null).toPromise();
    }
}