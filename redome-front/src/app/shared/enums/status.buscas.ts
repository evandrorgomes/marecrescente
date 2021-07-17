export enum AtivaInativa {
    INATIVA = 0,
    ATIVA = 1
}

interface StatusBusca {
    id: number,        
	buscaAtiva: AtivaInativa;
}

const StatusBuscas = {
	AGUARDANDO_LIBERAÇÃO: {
		id: 1,
		buscaAtiva: AtivaInativa.ATIVA
	} as StatusBusca,
	LIBERADA: {
		id: 2,
		buscaAtiva: AtivaInativa.ATIVA
    } as StatusBusca,
    EM_AVALIAÇÃO: {
		id: 3,
		buscaAtiva: AtivaInativa.INATIVA
    } as StatusBusca,
    CANCELADA: {
		id: 3,
		buscaAtiva: AtivaInativa.INATIVA
    } as StatusBusca
}
type StatusBuscas = (typeof StatusBuscas)[keyof typeof StatusBuscas];
export { StatusBuscas };