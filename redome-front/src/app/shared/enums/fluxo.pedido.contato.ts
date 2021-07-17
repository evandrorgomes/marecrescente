interface IFluxoPedidoContato {
    id: number;
    descricao: string;
}

class CFluxoPedidoContato implements IFluxoPedidoContato {
    private _id: number;
    private _descricao: string;

    constructor(_id: number, _descricao: string) {
        this._id = _id;
        this._descricao = _descricao;
    }

    public get id(): number {
        return this._id;
    }

    public get descricao(): string {
        return this._descricao;
    }

}

const FluxoPedidoContato = {
    FLUXO_NORMAL: new CFluxoPedidoContato(0, 'Fluxo normal'),
    FLUXO_PASSIVO:  new CFluxoPedidoContato(1, 'Fluxo Passivo'),
    FLUXO_CRIACAO: new CFluxoPedidoContato(2, 'Fluxo de criação'),
    FLUXO_ATUALIZACAO: new CFluxoPedidoContato(3, 'Fluxo de atualização'),
    FLUXO_ENRIQUECIMENTO: new CFluxoPedidoContato(4, 'Fluxo de enriquecimento'),
    valueOf: (id: number): (typeof FluxoPedidoContato)[keyof typeof FluxoPedidoContato] => {
        switch(id) {
            case 0: return FluxoPedidoContato.FLUXO_NORMAL;
            case 1: return FluxoPedidoContato.FLUXO_PASSIVO;
            case 2: return FluxoPedidoContato.FLUXO_CRIACAO;
            case 3: return FluxoPedidoContato.FLUXO_ATUALIZACAO;
            case 4: return FluxoPedidoContato.FLUXO_ENRIQUECIMENTO;
        }
    }
};

type FluxoPedidoContato = (typeof FluxoPedidoContato)[keyof typeof FluxoPedidoContato];


export { FluxoPedidoContato }