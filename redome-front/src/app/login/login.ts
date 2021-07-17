/**
 * Classe Bean utilizada para definir os campos de login e senha 
 * a serem utilizados no serviço e na tela de login.
 * @author Filipe Paes
 */
export class Login{
    username:string;
    password:string;

     /**
   * Método construtor.
   * @param object Objeto do tipo Login
   * @author Filipe Paes
   */
    constructor(value: Object = {}){
         Object.assign(this, value);
    }

}