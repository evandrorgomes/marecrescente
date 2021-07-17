export enum TiposPedidoWorkup {
	NACIONAL = 0,
	INTERNACIONAL = 1
}

export namespace TiposPedidoWorkup {
	export function valueOf(valor: number): TiposPedidoWorkup{
		switch(valor){
			case 0: return TiposPedidoWorkup.NACIONAL;
			case 1: return TiposPedidoWorkup.INTERNACIONAL;
		}
		
		if (typeof valor == "string") {
			switch(valor){
				case "NACIONAL": return TiposPedidoWorkup.NACIONAL;
				case "INTERNACIONAL": return TiposPedidoWorkup.INTERNACIONAL;
			}
		}
		throw Error("Valor não encontrado no enum: "+ valor);
	}
}