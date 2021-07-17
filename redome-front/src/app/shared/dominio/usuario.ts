import { UsuarioBasico } from "./usuario.basico";
import { Perfil } from "./perfil";
import { BancoSangueCordao } from "./banco.sangue.cordao";

/**
 * @description Representação da entidade Usuario, entidade padrão 
 * presente no modelo do sistema, representando um usuário interno
 * do Redome.
 * @author Pizão
 * @export
 * @class Usuario
 * @extends {UsuarioBasico}
 */
export class Usuario extends UsuarioBasico {

    private _perfis: Perfil[];
    private _bancoSangue: BancoSangueCordao;


	public get perfis(): Perfil[] {
		return this._perfis;
	}

	public set perfis(value: Perfil[]) {
		this._perfis = value;
    }
    
	public get bancoSangue(): BancoSangueCordao {
		return this._bancoSangue;
	}

	public set bancoSangue(value: BancoSangueCordao) {
		this._bancoSangue = value;
	}


    public jsonToEntity(res: any): any {
        super.jsonToEntity(res);

        if (res.perfis) {
            this._perfis = [];
            res.perfis.forEach(perfil => {
                this._perfis.push(new Perfil().jsonToEntity(perfil));
            });
        }
        if(res.bancoSangue){
            this._bancoSangue = new BancoSangueCordao().jsonToEntity(res.bancoSangue);
        }

        return this;
    }

}