import { EmailContato } from "../shared/classes/email.contato";
import { Courier } from "./courier";

/**
 * Classe Bean utilizada para definir os campos de contato de email do Courier
 * 
 * @export
 * @class EmailContatoCourier
 */
export class EmailContatoCourier extends EmailContato {
    
    private _courier: Courier;


    /**
     * Getter courier
     * @return {Courier}
     */
	public get courier(): Courier {
		return this._courier;
	}

    /**
     * Setter courier
     * @param {Courier} value
     */
	public set courier(value: Courier) {
		this._courier = value;
	}
   

    public jsonToEntity(res: any): this {
        if (res.courier) {
            this.courier = new Courier().jsonToEntity(res.courier);
        }
        super.jsonToEntity(res);

        return this;
	}
    

}