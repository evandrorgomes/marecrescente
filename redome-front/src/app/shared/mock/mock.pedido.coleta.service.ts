import { PedidoColeta } from '../../doador/consulta/coleta/pedido.coleta';
import { TiposTarefa } from '../enums/tipos.tarefa';

export class MockPedidoColetaService {

    public listarTarefasPedidoColeta(tipoTarefa: TiposTarefa,  pagina: number, quantidadeRegistro: number): Promise<any>{
        return null;
    }

    public obterPedidoColeta(idPedido: number): Promise<any> {
        return null;
    }

    public agendar(pedidoId: number, pedidoColeta: PedidoColeta): Promise<any> {
        return null;
    }

        /**
     * Método para enviar a disponibilidade para o CT.
     * @param  {number} idPedido
     * @param  {string} disponibilidade
     */
    public incluirDisponibilidade(idPedido: number, disponibilidade: string): Promise<any> {
        return null;
    }


}