import { Paciente } from '../../../paciente';
import { EnderecoContato } from '../../../../shared/classes/endereco.contato';
import { Pais } from "../../../../shared/dominio/pais";

/** 
 * @author Rafael
 * Objeto referente ao país, domínio carregado a partir dos correios.
 */
export class EnderecoContatoPaciente extends EnderecoContato {

	private _paciente: Paciente;


	public get paciente(): Paciente {
		return this._paciente;
	}

	public set paciente(value: Paciente) {
		this._paciente = value;
	}

	public jsonToEntity(res: any): EnderecoContatoPaciente {

		if (res.paciente) {
			this.paciente = new Paciente().jsonToEntity(res.paciente);
		}
		super.jsonToEntity(res);

		return this;        
    }

}