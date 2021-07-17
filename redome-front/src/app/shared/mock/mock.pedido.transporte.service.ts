import { Observable } from 'rxjs/Observable';
import { ConfirmacaoTransporteDTO } from '../../transportadora/tarefas/agendartransportematerial/confirmacao.transporte.dto';

export class MockPedidoTransporteService {

    public obterPedidoTransporte(id: number): Promise<any> {
        return Observable.of("").toPromise();
    }

    public confirmarAgendamentoTransporteMaterial(id: number, confirmacaoTransporteDTO: ConfirmacaoTransporteDTO): Promise<any> {
        return Observable.of("").toPromise();
    }

    public confirmarRetiradaTransporteMaterial(id: number, dataRetirada: Date): Promise<any> {
        return Observable.of("").toPromise();
    }

    public confirmarEntregaTransporteMaterial(id: number, dataEntrega: Date): Promise<any> {
        return Observable.of("").toPromise();
    }

}