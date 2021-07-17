
import { CentroTransplante } from "../dominio/centro.transplante";
import { ContatoTelefonico } from "../classes/contato.telefonico";

/**
 * Classe Bean utilizada para definir os campos de contato do centro de transplante.
 *
 * @export
 * @class ContatoTelefonico
 */
export class ContatoTelefonicoCentroTransplante extends ContatoTelefonico {

    public centroTransplante: CentroTransplante;

	public jsonToEntity(res: any): ContatoTelefonicoCentroTransplante {

        if (res.centroTransplante) {
            this.centroTransplante = new CentroTransplante().jsonToEntity(res);
        }
        super.jsonToEntity(res);


        return this;
    }
}
