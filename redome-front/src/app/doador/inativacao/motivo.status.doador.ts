import { BaseEntidade } from '../../shared/base.entidade';
import { StatusDoador } from '../../shared/dominio/status.doador';
import { ConvertUtil } from '../../shared/util/convert.util';
import { IAcaoDoadorInativo, AcaoDoadorInativo } from '../../shared/enums/acao.doador.inativo';

/**
 * Classe Bean utilizada para definir os campos de Motivo de Cancelamento DoadorNacional de Busca do Paciente
 *
 * @author Pizão.
 */
export class MotivoStatusDoador extends BaseEntidade {

	private _id: number;
	private _descricao: string;
	private _statusDoador: StatusDoador;
	private _acaoDoadorInativo: IAcaoDoadorInativo;

	/**
	 * Identificador de motivo de cancelamento
	 * @return id
	 */
	public get id(): number {
		return this._id;
	}

	/**
	 * Identificador de motivo de cancelamento
	 */
	public set id(value: number) {
		this._id = value;
	}

	/**
	 * Descricao do motivo de cancelamento
	 * @return descricao
	 */
	public get descricao(): string {
		return this._descricao;
	}

	/**
	 * Descricao do motivo de cancelamento
	 */
	public set descricao(value: string) {
		this._descricao = value;
	}


	public get statusDoador(): StatusDoador {
		return this._statusDoador;
	}

	public set statusDoador(value: StatusDoador) {
		this._statusDoador = value;
	}

    /**
     * Getter acaoDoadorInativo
     * @return {IAcaoDoadorInativo}
     */
	public get acaoDoadorInativo(): IAcaoDoadorInativo {
		return this._acaoDoadorInativo;
	}

    /**
     * Setter acaoDoadorInativo
     * @param {IAcaoDoadorInativo} value
     */
	public set acaoDoadorInativo(value: IAcaoDoadorInativo) {
		this._acaoDoadorInativo = value;
	}


	public jsonToEntity(res: any): MotivoStatusDoador {

		if (res.statusDoador) {
			this.statusDoador = new StatusDoador().jsonToEntity(res.statusDoador);
		}

		if (res.acaoDoadorInativo) {
			this.acaoDoadorInativo = AcaoDoadorInativo.parser(res.acaoDoadorInativo);
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
		
		return this;
	}
}