import { Paciente } from '../../../paciente';
import { ContatoTelefonico } from '../../../../shared/classes/contato.telefonico';

/**
 * Classe Bean utilizada para definir os campos de contato do Paciente
 * 
 * @export
 * @class ContatoTelefonico
 */
export class ContatoTelefonicoPaciente extends ContatoTelefonico {

	private _paciente: Paciente;


	public get paciente(): Paciente {
		return this._paciente;
	}

	public set paciente(value: Paciente) {
		this._paciente = value;
	}
	
	public jsonToEntity(res: any): ContatoTelefonicoPaciente {
		if (res.paciente) {
			this.paciente = new Paciente().jsonToEntity(res.paciente);
		}
		super.jsonToEntity(res);
        return this;
    }

}