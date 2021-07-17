enum StatusPedidoTransporteType  {    
    ABERTA,
	ATRIBUIDA,
	FEITA,
	AGUARDANDO
}


interface StatusPedidoTransporte {
	id,
	descricao
}

const StatusPedidoTransportes = {
	ANALISE: {
		id: 1,
		descricao: 'ANALISE'		
	} as StatusPedidoTransporte,
	AGUARDANDO_DOCUMENTACAO: {
		id: 2,
		descricao: 'AGUARDANDO_DOCUMENTACAO'
	} as StatusPedidoTransporte,
	AGUARDANDO_CONFIRMACAO: {
		id: 3,
		descricao: 'AGUARDANDO_CONFIRMACAO'
	} as StatusPedidoTransporte,
	AGUARDANDO_RETIRADA: {
		id: 4,
		descricao: 'AGUARDANDO_RETIRADA'
	} as StatusPedidoTransporte,
	AGUARDANDO_ENTREGA: {
		id: 5,
		descricao: 'AGUARDANDO_ENTREGA'
	} as StatusPedidoTransporte
}
type StatusPedidoTransportes = (typeof StatusPedidoTransportes)[keyof typeof StatusPedidoTransportes];
export { StatusPedidoTransportes };


