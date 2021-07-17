import { FormGroup } from "@angular/forms";
import { DataUtil } from "../util/data.util";
import { DateTypeFormats } from "../util/date/date.type.formats";
import { PlanoWorkupDTO } from "./plano.workup.dto";
import { DateMoment } from "../util/date/date.moment";

export class PlanoWorkupNacionalDTO extends PlanoWorkupDTO {

    private _dataInternacao: Date;
	private _observacaoPlanoWorkup: String;
	private _dataExameMedico1: Date;
	private _dataExameMedico2: Date;
	private _dataRepeticaoBthcg: Date;
	private _setorAndar: String;
	private _procurarPor: String;
	private _doadorEmJejum: Boolean;
	private _horasEmJejum: number;
    private _informacoesAdicionais: String;  

    public static parse(formCentro: FormGroup, formDoador: FormGroup): PlanoWorkupNacionalDTO{

        let planoWorkupNacional = new PlanoWorkupNacionalDTO();
        planoWorkupNacional.dataExame = DataUtil.toDate(formCentro.get("dataExame").value,  DateTypeFormats.DATE_TIME);
        planoWorkupNacional.dataResultado = DataUtil.toDate(formCentro.get("dataResultado").value,  DateTypeFormats.DATE_TIME);
        planoWorkupNacional.dataInternacao = DataUtil.toDate(formCentro.get("dataInternacao").value,  DateTypeFormats.DATE_TIME);
        planoWorkupNacional.dataColeta = DataUtil.toDate(formCentro.get("dataColeta").value,  DateTypeFormats.DATE_TIME);
        planoWorkupNacional.observacaoPlanoWorkup = formCentro.get("observacao").value;

        planoWorkupNacional.dataExameMedico1 = DataUtil.toDate(formDoador.get("dataExameMedico1").value + 
            formDoador.get("horaExameMedico1").value, DateTypeFormats.DATE_TIME);

        planoWorkupNacional.dataExameMedico2 = DataUtil.toDate(formDoador.get("dataExameMedico2").value + 
            formDoador.get("horaExameMedico2").value, DateTypeFormats.DATE_TIME);

        planoWorkupNacional.dataRepeticaoBthcg = DataUtil.toDate(formDoador.get("dataRepeticaoBthcg").value + 
            formDoador.get("horaRepeticaoBthcg").value, DateTypeFormats.DATE_TIME);
    
        planoWorkupNacional.setorAndar = formDoador.get("nomeSetorAndar").value;
        planoWorkupNacional.procurarPor = formDoador.get("procurarPor").value;
        planoWorkupNacional.doadorEmJejum = formDoador.get("doadorEmJejum").value == 'S'? true:false;
        planoWorkupNacional.horasEmJejum = formDoador.get("horasEmJejum").value;
        planoWorkupNacional.informacoesAdicionais = formDoador.get("informacoesAdicionais").value;

        return planoWorkupNacional;
    }


    /**
     * Getter dataInternacao
     * @return {Date}
     */
	public get dataInternacao(): Date {
		return this._dataInternacao;
	}

    /**
     * Getter observacaoPlanoWorkup
     * @return {String}
     */
	public get observacaoPlanoWorkup(): String {
		return this._observacaoPlanoWorkup;
	}

    /**
     * Getter dataExameMedico1
     * @return {Date}
     */
	public get dataExameMedico1(): Date {
		return this._dataExameMedico1;
	}

    /**
     * Getter dataExameMedico2
     * @return {Date}
     */
	public get dataExameMedico2(): Date {
		return this._dataExameMedico2;
	}

    /**
     * Getter dataRepeticaoBthcg
     * @return {Date}
     */
	public get dataRepeticaoBthcg(): Date {
		return this._dataRepeticaoBthcg;
	}

    /**
     * Getter setorAndar
     * @return {String}
     */
	public get setorAndar(): String {
		return this._setorAndar;
	}

    /**
     * Getter procurarPor
     * @return {String}
     */
	public get procurarPor(): String {
		return this._procurarPor;
	}

    /**
     * Getter doadorEmJejum
     * @return {Boolean}
     */
	public get doadorEmJejum(): Boolean {
		return this._doadorEmJejum;
	}

    /**
     * Getter horasEmJejum
     * @return {Number}
     */
	public get horasEmJejum(): number {
		return this._horasEmJejum;
	}

    /**
     * Getter informacoesAdicionais
     * @return {String}
     */
	public get informacoesAdicionais(): String {
		return this._informacoesAdicionais;
	}

    /**
     * Setter dataInternacao
     * @param {Date} value
     */
	public set dataInternacao(value: Date) {
		this._dataInternacao = value;
	}

    /**
     * Setter observacaoPlanoWorkup
     * @param {String} value
     */
	public set observacaoPlanoWorkup(value: String) {
		this._observacaoPlanoWorkup = value;
	}

    /**
     * Setter dataExameMedico1
     * @param {Date} value
     */
	public set dataExameMedico1(value: Date) {
		this._dataExameMedico1 = value;
	}

    /**
     * Setter dataExameMedico2
     * @param {Date} value
     */
	public set dataExameMedico2(value: Date) {
		this._dataExameMedico2 = value;
	}

    /**
     * Setter dataRepeticaoBthcg
     * @param {Date} value
     */
	public set dataRepeticaoBthcg(value: Date) {
		this._dataRepeticaoBthcg = value;
	}

    /**
     * Setter setorAndar
     * @param {String} value
     */
	public set setorAndar(value: String) {
		this._setorAndar = value;
	}

    /**
     * Setter procurarPor
     * @param {String} value
     */
	public set procurarPor(value: String) {
		this._procurarPor = value;
	}

    /**
     * Setter doadorEmJejum
     * @param {Boolean} value
     */
	public set doadorEmJejum(value: Boolean) {
		this._doadorEmJejum = value;
	}

    /**
     * Setter horasEmJejum
     * @param {Number} value
     */
	public set horasEmJejum(value: number) {
		this._horasEmJejum = value;
	}

    /**
     * Setter informacoesAdicionais
     * @param {String} value
     */
	public set informacoesAdicionais(value: String) {
		this._informacoesAdicionais = value;
	}


    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

}
