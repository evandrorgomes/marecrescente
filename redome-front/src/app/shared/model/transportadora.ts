import { BaseEntidade } from '../base.entidade';
import { Usuario } from '../dominio/usuario';
import { ContatoTelefonicoTransportadora } from './contato.telefonico.transportadora';
import { EmailContatoTransportadora } from './email.contato.transportadora';
import { EnderecoContatoTransportadora } from './endereco.contato.transportadora';

/**
 * @author Pizão
 *
 * Entidade que representa as transportadores parceiras responsáveis
 * por levar o material coletado até o destino (CT).
 */
export class Transportadora extends BaseEntidade {
	private _id: number;
	private _nome: string;
	private _usuarios: Usuario[] = [];
	private _endereco:EnderecoContatoTransportadora;
	private _emails:EmailContatoTransportadora[] = [];
	private _telefones:ContatoTelefonicoTransportadora[] = [];


	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get nome(): string {
		return this._nome;
	}

	public set nome(value: string) {
		this._nome = value;
	}

	/**
	 * Getter usuarios
	 * @return {Usuario[]}
	 */
	public get usuarios(): Usuario[] {
		return this._usuarios;
	}

	/**
	 * Setter usuarios
	 * @param {Usuario[]} value
	 */
	public set usuarios(value: Usuario[]) {
		this._usuarios = value;
	}

    /**
     * Getter endereco
     * @return {EnderecoContatoTransportadora}
     */
	public get endereco(): EnderecoContatoTransportadora {
		return this._endereco;
	}

    /**
     * Setter endereco
     * @param {EnderecoContatoTransportadora} value
     */
	public set endereco(value: EnderecoContatoTransportadora) {
		this._endereco = value;
	}


    /**
     * Getter emails
     * @return {EmailContatoTransportadora[] }
     */
	public get emails(): EmailContatoTransportadora[]  {
		return this._emails;
	}

    /**
     * Setter emails
     * @param {EmailContatoTransportadora[] } value
     */
	public set emails(value: EmailContatoTransportadora[] ) {
		this._emails = value;
	}

    /**
     * Getter telefones
     * @return {ContatoTelefonicoTransportadora[] }
     */
	public get telefones(): ContatoTelefonicoTransportadora[]  {
		return this._telefones;
	}

    /**
     * Setter telefones
     * @param {ContatoTelefonicoTransportadora[] } value
     */
	public set telefones(value: ContatoTelefonicoTransportadora[] ) {
		this._telefones = value;
	}


	public jsonToEntity(res: any): Transportadora {
		let transportadora: Transportadora = new Transportadora();
		transportadora.id = res.id;
		transportadora.nome = res.nome;
		transportadora.usuarios = [];
		if(res.usuarios){
			res.usuarios.forEach(u=> {
				transportadora.usuarios.push(new Usuario().jsonToEntity(u));
			});
		}
		if(res.endereco){
			transportadora.endereco = new EnderecoContatoTransportadora().jsonToEntity(res.endereco);
		}
		if(res.emails){
			res.emails.forEach(e=> {
				transportadora.emails.push(new EmailContatoTransportadora().jsonToEntity(e));
			});
		}
		if(res.telefones){
			res.telefones.forEach(t=> {
				transportadora.telefones.push(new ContatoTelefonicoTransportadora().jsonToEntity(t));
			});
		}
		return transportadora;
	}
}
