import { AcaoPedidoContato } from 'app/shared/enums/acao.pedido.contato';
import { Formulario } from 'app/shared/classes/formulario';

export interface IPedidoContatoFinalizadoVo {

    contactado: boolean; 
	contactadoPorTerceiro: boolean; 
	acao: AcaoPedidoContato;
	idMotivoStatusDoador: number;
	tempoInativacaoTemporaria: number;
	formulario: Formulario;
    hemocentro: number;
    toJSON(): any;

}