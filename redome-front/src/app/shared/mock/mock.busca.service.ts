import { MotivoCancelamentoBusca } from '../../paciente/busca/motivo.cancelamento.busca';
import { UF } from '../dominio/uf';
import { Pais } from '../dominio/pais';
import { Raca } from '../dominio/raca';
import { Cid } from '../dominio/cid';
import { Diagnostico } from '../../paciente/cadastro/diagnostico/diagnostico';
import { Paciente } from '../../paciente/paciente';
import { Avaliacao } from '../../paciente/avaliacao/avaliacao';
import { CampoMensagem } from '../campo.mensagem';
import { ErroMensagem } from '../erromensagem';
import { Observable } from 'rxjs';
export class MockBuscaService {


    listarMotivosCancelamentoBusca(): Promise<any> {

        let motivos:MotivoCancelamentoBusca[] = [];
        let novoMotivo =new  MotivoCancelamentoBusca()
        novoMotivo.descricao="teste"
        novoMotivo.descricaoObrigatorio = 1;
        motivos.push(novoMotivo)
        return Promise.resolve(motivos);
    }

    public obterUltimoPedidoExame(buscaId: number): Promise<any> {
        return Observable.of("").toPromise();
    }

    obterBuscaPorRMR(rmr: number): Promise<any> {
        return Observable.of("").toPromise();
    }

}

