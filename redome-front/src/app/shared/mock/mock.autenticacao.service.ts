import { Observable } from 'rxjs';
import { Login } from '../../login/login';
import { ErroMensagem } from '../erromensagem';
import { UsuarioLogado } from '../dominio/usuario.logado';
export class MockAutenticacaoService {
    
    static temPerfilMedicoTest = true;
    usuarioLogado(): UsuarioLogado {
        let perfis =['MÉDICO'];

        return new UsuarioLogado("medico","",perfis,[]);
    }

    tokenExpirado(): boolean {
        return false;
    }

    logar(login: Login): Promise<any> {

        if(login.username == 'medico' && login.password == '123456') {
            return new Promise(() => {
                Promise.resolve(true);
            })              
        } else {
            return Promise.reject(new ErroMensagem(401, "", "Usuário ou senha inválidos"));
        }
    }

    temPermissaoAcesso(nomeComponente): boolean {
        return true;
    }

    /**
     * Metodo que verificar se o usuario atual eh do perfil medico.
     * 
     * @returns {boolean} 
     * @memberof AutenticacaoService
     */
    public temPerfilMedico():boolean{
        return MockAutenticacaoService.temPerfilMedicoTest;
    }

    public temRecurso(recurso: string): boolean {
        return true;
    }

    public logout() {
        
    }

}