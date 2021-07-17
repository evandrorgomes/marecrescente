/**
 * Tipo de prescrição utilizado somente da tela de prescrição no front-end, não
 * representa nenhuma enum similar no modelo das entidades.
 * @author ergomes
 */
export enum TiposPrescricao{
    MEDULA = 0,
    CORDAO = 1
}

export namespace TiposPrescricao {
	export function valueOf(valor: number): TiposPrescricao{
		switch(valor){
			case 0: return TiposPrescricao.MEDULA;
			case 1: return TiposPrescricao.CORDAO;
		}

		if (typeof valor == "string") {
			switch(valor){
				case "MEDULA": return TiposPrescricao.MEDULA;
				case "CORDAO": return TiposPrescricao.CORDAO;
			}
		}
		throw Error("Valor não encontrado no enum: "+ valor);
	}
}
