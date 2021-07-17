import { BuildForm } from "../buildform/build.form";
import { PlanoWorkupDTO } from "./plano.workup.dto";
import { PlanoWorkupNacionalDTO } from "./plano.workup.nacional.dto";
import { DataUtil } from "../util/data.util";
import { FormGroup } from "@angular/forms";

export class PlanoWorkupInternacionalDTO extends PlanoWorkupDTO {

	private _observacaoPlanoWorkup: String;

    public static parse(formCentro: FormGroup): PlanoWorkupInternacionalDTO{

        let planoWorkupInternacionalDTO = new PlanoWorkupInternacionalDTO();
        planoWorkupInternacionalDTO.dataExame = DataUtil.toDate(formCentro.get("dataExame").value);
        planoWorkupInternacionalDTO.dataResultado = DataUtil.toDate(formCentro.get("dataResultado").value);
        planoWorkupInternacionalDTO.dataColeta = DataUtil.toDate(formCentro.get("dataColeta").value);
        planoWorkupInternacionalDTO.observacaoPlanoWorkup = formCentro.get("observacao").value;

        return planoWorkupInternacionalDTO;
    }



    /**
     * Getter observacaoPlanoWorkup
     * @return {String}
     */
	public get observacaoPlanoWorkup(): String {
		return this._observacaoPlanoWorkup;
	}

    /**
     * Setter observacaoPlanoWorkup
     * @param {String} value
     */
	public set observacaoPlanoWorkup(value: String) {
		this._observacaoPlanoWorkup = value;
	}


    public jsonToEntity(res: any) {
        throw new Error("Method not implemented.");
    }

}
