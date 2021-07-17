import {ContatoTelefonicoPaciente} from '../cadastro/contato/telefone/contato.telefonico.paciente';
import {EnderecoContatoPaciente} from '../cadastro/contato/endereco/endereco.contato.paciente';
/**
 * Classe que representa o contato do paciente, somente com as informações necessárias
 * para edição.
 * 
 * @author Pizão
 */
export class ContatoPacienteDTO {

	private _rmr:number;
	private _enderecosContato: EnderecoContatoPaciente[];
	private _contatosTelefonicos: ContatoTelefonicoPaciente[];
	private _email: string;


	public get enderecosContato(): EnderecoContatoPaciente[] {
		return this._enderecosContato;
	}

	public set enderecosContato(value: EnderecoContatoPaciente[]) {
		this._enderecosContato = value;
	}

	public get contatosTelefonicos(): ContatoTelefonicoPaciente[] {
		return this._contatosTelefonicos;
	}

	public set contatosTelefonicos(value: ContatoTelefonicoPaciente[]) {
		this._contatosTelefonicos = value;
	}

	public get email(): string {
		return this._email;
	}

	public set email(value: string) {
		this._email = value;
	}

	public get rmr(): number {
		return this._rmr;
	}

	public set rmr(value: number) {
		this._rmr = value;
	}
}

