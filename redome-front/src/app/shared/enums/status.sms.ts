
export interface IStatusSms {
    id: number; 
	descricao: string;
}

const StatusSms = {
	AGUARDANDO_ENVIO: {
		id: 0,
		descricao: 'Aguardando Envio'
	} as IStatusSms,
	ENTREGUE: {
		id: 1,
		descricao: 'Entregue'
	} as IStatusSms,
	LEITURA: {
		id: 2,
		descricao: 'Leitura'
	} as IStatusSms,
	DEVOLVIDO: {
		id: 3,
		descricao: 'Devolvido'
	} as IStatusSms,
	parser: (valor: any): IStatusSms => {
        if (typeof valor == "number") {
			switch(valor){
				case 0: return StatusSms.AGUARDANDO_ENVIO;
				case 1: return StatusSms.ENTREGUE;
				case 2: return StatusSms.LEITURA;
				case 3: return StatusSms.DEVOLVIDO;
			}
        }
        else if (typeof valor == "string") {
			switch(valor){
				case "AGUARDANDO_ENVIO": return StatusSms.AGUARDANDO_ENVIO;
				case "ENTREGUE": return StatusSms.ENTREGUE;
				case "LEITURA": return StatusSms.LEITURA;
				case "DEVOLVIDO": return StatusSms.DEVOLVIDO;
			}
		}
		throw Error("Valor não encontrado no enum: "+ valor);
    }
};

type StatusSms = (typeof StatusSms)[keyof typeof StatusSms];
export { StatusSms };

