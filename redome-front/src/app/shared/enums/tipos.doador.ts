export enum TiposDoador {
	NACIONAL = 0,
	INTERNACIONAL = 1,
	CORDAO_NACIONAL = 2,
	CORDAO_INTERNACIONAL = 3
}

export namespace TiposDoador {
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
}