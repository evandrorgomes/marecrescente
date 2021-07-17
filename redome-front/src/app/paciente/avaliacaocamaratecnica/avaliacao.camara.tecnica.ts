import { Paciente } from './../paciente';
import { StatusAvaliacaoCamaraTecnica } from './status.avaliacao.camara.tecnica';
import { BaseEntidade } from '../../shared/base.entidade';
import { ConvertUtil } from '../../shared/util/convert.util';


/**
 * Classe de avaliação de camara técnica.
 * @author Filipe Paes
 */
export class AvaliacaoCamaraTecnica extends BaseEntidade {
    
	private _id:number;

	private _caminhoArquivoRelatorio: String ;

	private _justificativa: String ;

	private _paciente: Paciente;

    private _status: StatusAvaliacaoCamaraTecnica;
    

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
     * Getter caminhoArquivoRelatorio
     * @return {String }
     */
	public get caminhoArquivoRelatorio(): String  {
		return this._caminhoArquivoRelatorio;
	}

    /**
     * Setter caminhoArquivoRelatorio
     * @param {String } value
     */
	public set caminhoArquivoRelatorio(value: String ) {
		this._caminhoArquivoRelatorio = value;
	}

    /**
     * Getter justificativa
     * @return {String }
     */
	public get justificativa(): String  {
		return this._justificativa;
	}

    /**
     * Setter justificativa
     * @param {String } value
     */
	public set justificativa(value: String ) {
		this._justificativa = value;
	}

    /**
     * Getter paciente
     * @return {Paciente}
     */
	public get paciente(): Paciente {
		return this._paciente;
	}

    /**
     * Setter paciente
     * @param {Paciente} value
     */
	public set paciente(value: Paciente) {
		this._paciente = value;
	}

    /**
     * Getter status
     * @return {StatusAvaliacaoCamaraTecnica}
     */
	public get status(): StatusAvaliacaoCamaraTecnica {
		return this._status;
	}

    /**
     * Setter status
     * @param {StatusAvaliacaoCamaraTecnica} value
     */
	public set status(value: StatusAvaliacaoCamaraTecnica) {
		this._status = value;
	}

    public jsonToEntity(res: any) :this{
        this._id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this._caminhoArquivoRelatorio = ConvertUtil.parseJsonParaAtributos(res.caminhoArquivoRelatorio, new String());
        this._justificativa = ConvertUtil.parseJsonParaAtributos(res.justificativa, new String());
        if(res.paciente){
            this._paciente = new Paciente().jsonToEntity(res.paciente);
        }
        this._status = new StatusAvaliacaoCamaraTecnica().jsonToEntity(res.status);
        return this;
    }    
}
