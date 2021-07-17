
export interface IAcaoDoadorInativo {
    id: number; 
	descricao: string;
}

const AcaoDoadorInativo = {
	ATIVAR: {
		id: 0,
		descricao: 'Ativar'
	} as IAcaoDoadorInativo,
	ANALISE_MEDICA: {
		id: 1,
		descricao: 'Análise Médica'
	} as IAcaoDoadorInativo,
	MANTER_INATIVO: {
		id: 2,
		descricao: 'Manter Inativo'
	} as IAcaoDoadorInativo,
	
	parser: (valor: any): IAcaoDoadorInativo => {
        if (typeof valor == "number") {
			switch(valor){
				case 0: return AcaoDoadorInativo.ATIVAR;
				case 1: return AcaoDoadorInativo.ANALISE_MEDICA;
				case 2: return AcaoDoadorInativo.MANTER_INATIVO;
			}
        }
        else if (typeof valor == "string") {
			switch(valor){
				case "ATIVAR": return AcaoDoadorInativo.ATIVAR;
				case "ANALISE_MEDICA": return AcaoDoadorInativo.ANALISE_MEDICA;
				case "MANTER_INATIVO": return AcaoDoadorInativo.MANTER_INATIVO;
			}
		}
		throw Error("Valor não encontrado no enum: "+ valor);
    }
};

type AcaoDoadorInativo = (typeof AcaoDoadorInativo)[keyof typeof AcaoDoadorInativo];
export { AcaoDoadorInativo };

