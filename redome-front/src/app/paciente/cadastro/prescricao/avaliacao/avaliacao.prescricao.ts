import { Raca } from '../../../../shared/dominio/raca';
import { Etnia } from '../../../../shared/dominio/etnia';
import { UF } from '../../../../shared/dominio/uf';
import { RessalvaDoador } from "../../../../doador/ressalva.doador";
import { MotivoStatusDoador } from '../../../../doador/inativacao/motivo.status.doador';
import { PedidoWorkup } from '../../../../doador/consulta/workup/pedido.workup';
import { BaseEntidade } from '../../../../shared/base.entidade';
import { Prescricao } from '../../../../doador/consulta/workup/prescricao';
import { FonteCelula } from '../../../../shared/dominio/fonte.celula';
import { ConvertUtil } from '../../../../shared/util/convert.util';

export class AvaliacaoPrescricao extends BaseEntidade {
	
	private _id:number;
	private _justificativaCancelamento: string;
	private _justificativaDescarteFonteCelula: string;
	private _aprovado: boolean;
	private _fonteCelula: FonteCelula;
	private _prescricao: Prescricao;

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}


	public get prescricao(): Prescricao {
		return this._prescricao;
	}

	public set prescricao(value: Prescricao) {
		this._prescricao = value;
	}

    /**
     * Getter fonteCelula
     * @return {FonteCelula}
     */
	public get fonteCelula(): FonteCelula {
		return this._fonteCelula;
	}

    /**
     * Setter fonteCelula
     * @param {FonteCelula} value
     */
	public set fonteCelula(value: FonteCelula) {
		this._fonteCelula = value;
	}

    /**
     * Getter justificativaCancelamento
     * @return {string}
     */
	public get justificativaCancelamento(): string {
		return this._justificativaCancelamento;
	}

    /**
     * Setter justificativaCancelamento
     * @param {string} value
     */
	public set justificativaCancelamento(value: string) {
		this._justificativaCancelamento = value;
	}

    /**
     * Getter justificativaDescarteFonteCelula
     * @return {string}
     */
	public get justificativaDescarteFonteCelula(): string {
		return this._justificativaDescarteFonteCelula;
	}

    /**
     * Setter justificativaDescarteFonteCelula
     * @param {string} value
     */
	public set justificativaDescarteFonteCelula(value: string) {
		this._justificativaDescarteFonteCelula = value;
	}

    /**
     * Getter aprovado
     * @return {boolean}
     */
	public get aprovado(): boolean {
		return this._aprovado;
	}

    /**
     * Setter aprovado
     * @param {boolean} value
     */
	public set aprovado(value: boolean) {
		this._aprovado = value;
	}

	public jsonToEntity(res: any): AvaliacaoPrescricao {

		if (res.prescricao) {
			this.prescricao = new Prescricao().jsonToEntity(res.prescricao);
		}
		if (res.fonteCelula) {
			this.fonteCelula = new FonteCelula().jsonToEntity(res.fonteCelula);
		}

		this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.justificativaCancelamento = ConvertUtil.parseJsonParaAtributos(res.justificativaCancelamento, new String());
		this.justificativaDescarteFonteCelula = ConvertUtil.parseJsonParaAtributos(res.justificativaDescarteFonteCelula, new String());
		this.aprovado = ConvertUtil.parseJsonParaAtributos(res.aprovado, new Boolean());;
	
		return this;
    }
	
}