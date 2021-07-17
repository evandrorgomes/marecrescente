import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { BancoSangueCordao } from './banco.sangue.cordao';
import { UsuarioBasico } from './usuario.basico';
/**
 * Classe DTO que representa o usuário logado no sistema.
 * Ele é um parser "conhecido" entre o token vindo do back-end
 * e o objeto usuário logado, utilizado no front.
 * 
 * @export
 * @class Usuario
 * @author Bruno Sousa
 * 
 */
export class UsuarioLogado extends UsuarioBasico {

    /**
     * lista de Perfis do usuário
     * 
     * @private
     * @type {string[]}
     * @memberOf Usuario
     */
    private _perfis: string[];
    
    /**
     * lista de todos os recursos do usuário
     * 
     * @private
     * @type {string[]}
     * @memberOf Usuario
     */
    private _recursos: string[];

    /**
     * lista de centros do usuário.
     * 
     * @private
     * @type {CentroTransplante[]}
     * @memberof Usuario
     */
    private _centros: CentroTransplante[];

    private _centrosTransplantesParaTarefas: CentroTransplante[];

    /**
     * Banco de sangue em que o usuário 
     * logado está associado (somente usuários do BrasilCord).
     */
    private _bancoSangue: BancoSangueCordao;


    constructor(username?: string, nome?: string, perfis?: string[], recursos?: string[], centros?:CentroTransplante[]) {
        super(username, nome);
        this._perfis = perfis;
        this._recursos = recursos;
        this._centros = centros;
    }

    
	public get perfis(): string[] {
		return this._perfis;
	}

	public get recursos(): string[] {
		return this._recursos;
	}

    public get centros(): CentroTransplante[] {
		return this._centros;
	}

	public set centros(value: CentroTransplante[]) {
		this._centros = value;
    }

    /**
     * Getter centrosTransplantesParaTarefas
     * @return {CentroTransplante[]}
     */
	public get centrosTransplantesParaTarefas(): CentroTransplante[] {
		return this._centrosTransplantesParaTarefas;
	}

    /**
     * Setter centrosTransplantesParaTarefas
     * @param {CentroTransplante[]} value
     */
	public set centrosTransplantesParaTarefas(value: CentroTransplante[]) {
		this._centrosTransplantesParaTarefas = value;
    }
    
    /**
     * Retorna o banco de sangue associado ao usuário.
     * @return {BancoSangueCordao}
     */
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
                this._perfis.push(perfil);
            });
        }
        else if (res.authorities) {
            this._perfis = [];
            res.authorities.forEach(perfil => {
                this._perfis.push(perfil);
            });
        }

        if (res.recursos) {
            this._recursos = [];
            res.recursos.forEach(recurso => {
                this._recursos.push(recurso);
            })
        }
        if (res.centros) {
            this._centros = [];
            res.centros.forEach(centro => {
                this._centros.push(new CentroTransplante().jsonToEntity(centro));
            });
        }
        if (res.centrosTransplantesParaTarefas) {
            this.centrosTransplantesParaTarefas = [];
            res.centrosTransplantesParaTarefas.forEach(centro => {
                this.centrosTransplantesParaTarefas.push(new CentroTransplante().jsonToEntity(centro));
            });
        }
        if(res.bancoSangue){
            this.bancoSangue = new BancoSangueCordao().jsonToEntity(res.bancoSangue);
        }
        
        return this;
    }


}