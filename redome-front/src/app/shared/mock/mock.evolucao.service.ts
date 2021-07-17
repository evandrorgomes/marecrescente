import { EstagioDoenca } from './../dominio/estagio.doenca';
import { Evolucao } from "../../paciente/cadastro/evolucao/evolucao";
import { Paciente } from "../../paciente/paciente";
import { Motivo } from "../dominio/motivo";
import { CondicaoPaciente } from "../dominio/condicaoPaciente";
import { Observable } from 'rxjs';

export class MockEvolucaoService {
    obterUltimaEvolucao(rmr: number): Promise<Evolucao>{
        let evolucao:Evolucao = new Evolucao();
        evolucao.paciente = new Paciente();
        evolucao.paciente.rmr = 1;
        evolucao.peso = 80;
        evolucao.motivo = new Motivo();
        evolucao.motivo.id = 1;
        evolucao.altura = 1.8;
        evolucao.cmv = 1;
        evolucao.condicaoAtual = "teste";
        evolucao.condicaoPaciente = new CondicaoPaciente();
        evolucao.condicaoPaciente.id = 1;
        evolucao.estagioDoenca = new EstagioDoenca();
        evolucao.estagioDoenca.id=1;
        evolucao.exameAnticorpo = false;
        return Observable.of(evolucao).toPromise();
    }
}