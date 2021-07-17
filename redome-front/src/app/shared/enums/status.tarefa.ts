enum StatusTarefaType  {    
    ABERTA,
	ATRIBUIDA,
	FEITA,
	AGUARDANDO
}


interface StatusTarefa {
	id,
	descricao
}

const StatusTarefas = {
	ABERTA: {
		id: 1,
		descricao: 'ABERTA'		

	} as StatusTarefa,
	ATRIBUIDA: {
		id: 2,
		descricao: 'ATRIBUIDA'
	} as StatusTarefa,
	FEITA: {
		id: 3,
		descricao: 'FEITA'
	} as StatusTarefa,
	AGUARDANDO: {
		id: 4,
		descricao: 'AGUARDANDO'
	} as StatusTarefa,
	CANCELADA: {
		id: 5,
		descricao: 'CANCELADA'
	} as StatusTarefa
}
type StatusTarefas = (typeof StatusTarefas)[keyof typeof StatusTarefas];
export { StatusTarefas };


