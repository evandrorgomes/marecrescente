import { BaseEntidade } from '../base.entidade';
import { Busca } from 'app/paciente/busca/busca';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class ArquivoRelatorioInternacional extends BaseEntidade {

    private _id: number;
    private _busca: Busca;
    private _descricao: string;
    private _caminho: string;

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
     * Getter busca
     * @return {Busca}
     */
	public get busca(): Busca {
		return this._busca;
	}

    /**
     * Setter busca
     * @param {Busca} value
     */
	public set busca(value: Busca) {
		this._busca = value;
	}

    /**
     * Getter nome
     * @return {string}
     */
	public get descricao(): string {
		return this._descricao;
	}

    /**
     * Setter nome
     * @param {string} value
     */
	public set descricao(value: string) {
		this._descricao = value;
	}

    /**
     * Getter caminho
     * @return {string}
     */
	public get caminho(): string {
		return this._caminho;
	}

    /**
     * Setter caminho
     * @param {string} value
     */
	public set caminho(value: string) {
		this._caminho = value;
    }

    public jsonToEntity(res: any): ArquivoRelatorioInternacional {

        if (res.busca) {
            this.busca = new Busca().jsonToEntity(res.busca);
        }

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
        this.caminho = ConvertUtil.parseJsonParaAtributos(res.caminho, new String());

        return this;
    }


}
