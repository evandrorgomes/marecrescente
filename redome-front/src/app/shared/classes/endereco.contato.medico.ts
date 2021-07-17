import { ConvertUtil } from 'app/shared/util/convert.util';
import { EnderecoContato } from 'app/shared/classes/endereco.contato';
import { Medico } from 'app/shared/dominio/medico';


/**
 * Classe Bean utilizada para definir os campos de endereço do médico. 
 * 
 * @export
 * @class ContatoEnderecoMedico
 */
export class EnderecoContatoMedico extends EnderecoContato {
    
    private _medico: Medico;

    /**
     * Getter medico
     * @return {Medico}
     */
	public get medico(): Medico {
		return this._medico;
	}

    /**
     * Setter medico
     * @param {Medico} value
     */
	public set medico(value: Medico) {
		this._medico = value;
	}

    public jsonToEntity(res: any): EnderecoContatoMedico {
        if (res.medico) {
            this.medico = new Medico().jsonToEntity(res.medico);
        }
        super.jsonToEntity(res);
        return this;
    }
}