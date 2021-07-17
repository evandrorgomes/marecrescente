import { AcaoPedidoContato } from "../enums/acao.pedido.contato";
import { Formulario } from 'app/shared/classes/formulario';

export interface IAnaliseMedicaFinalizadaVo {

    acao: AcaoPedidoContato;
	idMotivoStatusDoador: number;
	tempoInativacaoTemporaria: number;
    formulario: Formulario;
    toJSON(): any;

}