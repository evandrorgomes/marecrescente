import { PedidoColeta } from '../../doador/consulta/coleta/pedido.coleta';
import { TiposTarefa } from '../enums/tipos.tarefa';

export class MockWorkupService {


    public obterPedidoWorkup(idPedido: number): Promise<any> {
        return null;
    }

    public agendar(pedidoId: number, pedidoColeta: PedidoColeta): Promise<any> {
        return null;
    }

}