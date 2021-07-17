import { ExameDoadorInternacional } from '../../doador/exame.doador.internacional';
import { PedidoExame } from '../../laboratorio/pedido.exame';
import { Observable } from 'rxjs';

export class MockPedidoExameService {

	receberPedidoExame(idPedido:number, pedidoExame:PedidoExame): Promise<any> {
        return null;
    }

    public salvarPedidoExame(pedidoExame: PedidoExame): Promise<any> {
        return null;
    }

    public cancelarPedidoExame(idPedidoExame: number, motivo: string): Promise<any> {
        return null;
    }

    public listarTarefasDePedidoDeExame(idBusca:number, pagina: number, quantidadeRegistros: number): Promise<any>{
        return Observable.of("").toPromise();
    }

    public enviarResultadoPedidoExameDoadorInternacional(idPedidoExame: number, exame: ExameDoadorInternacional, 
        motivoStatusId: number, timeRetornoInatividade: number):Promise<any>{ 
            return Observable.of("").toPromise();
    }

}