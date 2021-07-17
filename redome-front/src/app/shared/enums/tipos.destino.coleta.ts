/**
 * Tipo de prescrição utilizado somente da tela de prescrição no front-end, não
 * representa nenhuma enum similar no modelo das entidades.
 * @author ergomes
 */
export enum TiposDestinoColeta{
    INFUSAO = 1,
    CONGELAMENTO = 2,
    DESCARTE = 3
}

export namespace TiposDestinoColeta {
	export function valueOf(valor: number): TiposDestinoColeta{
		switch(valor){
			case 1: return TiposDestinoColeta.INFUSAO;
      case 2: return TiposDestinoColeta.CONGELAMENTO;
      case 3: return TiposDestinoColeta.DESCARTE;
		}

		if (typeof valor == "string") {
			switch(valor){
				case "INFUSAO": return TiposDestinoColeta.INFUSAO;
        case "CONGELAMENTO": return TiposDestinoColeta.CONGELAMENTO;
        case "DESCARTE": return TiposDestinoColeta.DESCARTE;
			}
		}
		throw Error("Valor não encontrado no enum: "+ valor);
	}
}
