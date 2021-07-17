interface IAcaoPedidoContato {
    id: number;
    descricao: string;    
}

class CAcaoPedidoContato implements IAcaoPedidoContato {
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


const AcaoPedidoContato = {
    NAO_PROSSEGUIR: new CAcaoPedidoContato(0, 'Não prosseguir'),
    PROSSEGUIR:  new CAcaoPedidoContato(1, 'Prosseguir'),
    ANALISE_MEDICA: new CAcaoPedidoContato(2, 'Analise Médica'),
    valueOf: (id: number): (typeof AcaoPedidoContato)[keyof typeof AcaoPedidoContato] => {
        switch(id) {
            case 0: return AcaoPedidoContato.NAO_PROSSEGUIR;
            case 1: return AcaoPedidoContato.PROSSEGUIR;
            case 2: return AcaoPedidoContato.ANALISE_MEDICA;        
        }        
    } 
    
};

type AcaoPedidoContato = (typeof AcaoPedidoContato)[keyof typeof AcaoPedidoContato];


export { AcaoPedidoContato }
    
    
/* export namespace AcaoPedidoContato {
	export function valueOf(valor: number): TiposDoador{
		switch(valor){
			case 0: return TiposDoador.NACIONAL;
			case 1: return TiposDoador.INTERNACIONAL;
			case 2: return TiposDoador.CORDAO_NACIONAL;
			case 3: return TiposDoador.CORDAO_INTERNACIONAL;
		}
		
		if (typeof valor == "string") {
			switch(valor){
				case "NACIONAL": return TiposDoador.NACIONAL;
				case "INTERNACIONAL": return TiposDoador.INTERNACIONAL;
				case "CORDAO_NACIONAL": return TiposDoador.CORDAO_NACIONAL;
				case "CORDAO_INTERNACIONAL": return TiposDoador.CORDAO_INTERNACIONAL;
			}
		}
		throw Error("Valor não encontrado no enum: "+ valor);
	}
} */