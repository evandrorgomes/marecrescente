import { CentroTransplante } from '../dominio/centro.transplante';
export class MockCentroTransplanteService {

    listarCentroTransplantes(querystring:string, pagina: number, quantidadeRegistros: number) {
        let centro:CentroTransplante = new CentroTransplante();
        centro.id = 1;
        centro.nome = "";
        return Promise.resolve(centro);
    }
}