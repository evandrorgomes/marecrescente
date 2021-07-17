import { LocusExame } from "../../paciente/cadastro/exame/locusexame";


export class ExameDoadorInternacionalDto{

    private _id: number;
    private _dataCriacao: Date;
    private _locusExames: LocusExame[] = [];
    private _statusExame:number;
    private _tipoExame:number;
    private _caminhoArquivo:string;
    private _idGenerico:number;
	constructor() {
	}



    /**
     * Getter idGenerico
     * @return {number}
     */
	public get idGenerico(): number {
		return this._idGenerico;
	}

    /**
     * Setter idGenerico
     * @param {number} value
     */
	public set idGenerico(value: number) {
		this._idGenerico = value;
	}

    /**
     * Getter id
     * @return {number}
     */
	public get id(): number {
		return this._id;
	}

    /**
     * Setter id
     * @param {number} value
     */
	public set id(value: number) {
		this._id = value;
	}


    /**
     * Getter dataCriacao
     * @return {Date}
     */
	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

    /**
     * Setter dataCriacao
     * @param {Date} value
     */
	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

    /**
     * Getter locusExames
     * @return {LocusExame[] }
     */
	public get locusExames(): LocusExame[]  {
		return this._locusExames;
	}

    /**
     * Setter locusExames
     * @param {LocusExame[] } value
     */
	public set locusExames(value: LocusExame[] ) {
		this._locusExames = value;
	}


    /**
     * Getter statusExame
     * @return {number}
     */
	public get statusExame(): number {
		return this._statusExame;
	}

    /**
     * Setter statusExame
     * @param {number} value
     */
	public set statusExame(value: number) {
		this._statusExame = value;
    }


    /**
     * Getter tipoExame
     * @return {number}
     */
	public get tipoExame(): number {
		return this._tipoExame;
	}

    /**
     * Setter tipoExame
     * @param {number} value
     */
	public set tipoExame(value: number) {
		this._tipoExame = value;
	}


    /**
     * Getter caminhoArquivo
     * @return {string}
     */
	public get caminhoArquivo(): string {
		return this._caminhoArquivo;
	}

    /**
     * Setter caminhoArquivo
     * @param {string} value
     */
	public set caminhoArquivo(value: string) {
		this._caminhoArquivo = value;
    }

    public static get TIPO_EXAME_HLA(){
        return 1;
    }

    public static get TIPO_EXAME_IDM(){
        return 2;
    }

}
