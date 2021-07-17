/* export enum TiposColuna {
    NUMBER = 1,
    STRING = 2,
    DATE = 3,
    BOOLEAN = 4
} */

export interface ITipoColuna {
    codigo: number;
    valueOf();
    toString(): string;
}

export class TipoColuna implements ITipoColuna {
    private _codigo: number;

    constructor(codigo: number) {
        this._codigo = codigo;
    }

    public get codigo(): number {
        return this._codigo;
    }

    public valueOf() {
        return this._codigo;
    }

    public toString(): string {
        return "TipoColuna_" + this._codigo;
    }

}

const TiposColuna = {
    NUMBER: new TipoColuna(1) as ITipoColuna,
    STRING: new TipoColuna(2) as ITipoColuna,
    DATE: new TipoColuna(3) as ITipoColuna,
    BOOLEAN: new TipoColuna(4) as ITipoColuna,
    parser: (valor: any): ITipoColuna => {
        if (typeof valor == "number") {
			switch(valor){
				case 1: return TiposColuna.NUMBER;
				case 2: return TiposColuna.STRING;
				case 3: return TiposColuna.DATE;
				case 4: return TiposColuna.BOOLEAN;
			}
        }
        else if (typeof valor == "string") {
			switch(valor){
				case "NUMBER": return TiposColuna.NUMBER;
				case "STRING": return TiposColuna.STRING;
				case "DATE": return TiposColuna.DATE;
				case "BOOLEAN": return TiposColuna.BOOLEAN;
			}
		}
		throw Error("Valor não encontrado no enum: "+ valor);
    }
    
}
type TiposColuna = (typeof TiposColuna)[keyof typeof TiposColuna];
export { TiposColuna };
