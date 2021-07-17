
export class NavBarItem {

	private _id: String;
	private _descricao: String;
	private _icone: String;
	private _uri: String;
	private _metodoClick: () => void;

	constructor(id: String, descricao: String, icone: String, uri: String, metodoClick?: () => void) {
		this._id = id;
		this._descricao = descricao;
		this._icone = icone;
		this._uri = uri;
		this._metodoClick = metodoClick;
	}

	/**
	 * Getter id
	 * @return {String}
	 */
	public get id(): String {
		return this._id;
	}

	/**
	 * Getter nome
	 * @return {string}
	 */
	public get descricao(): String {
		return this._descricao;
	}

	/**
	 * Getter icone
	 * @return {String}
	 */
	public get icone(): String {
		return this._icone;
	}

	/**
	 * Getter uri
	 * @return {string}
	 */
	public get uri(): String {
		return this._uri;
	}

	public get metodoClick(): () => void {
		return this._metodoClick;
	}


}
