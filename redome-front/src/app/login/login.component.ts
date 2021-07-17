import { AutenticacaoService } from '../shared/autenticacao/autenticacao.service';
import { EventEmitterService } from '../shared/event.emitter.service';
import { TranslateService } from '@ngx-translate/core';
import { CampoMensagem } from '../shared/campo.mensagem';
import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Login } from './login';
import 'rxjs/add/operator/map';
import { ErroMensagem } from '../shared/erromensagem';
import { EventBind } from '../shared/event.bind';
import { HeaderDoadorComponent } from '../doador/consulta/header/header.doador.component';

/**
 * Classe Component utilizada para controlar os campos e métodos 
 * disponibilizados em login.component.html
 * @author Filipe Paes
 */
@Component({
    moduleId: module.id,
    templateUrl: './login.component.html',
    styleUrls: ['./login.css']
})
export class LoginComponent implements OnInit {
    login: Login = new Login();
    private _listaCampoMensagem: CampoMensagem[] = [];
    campoUsuarioObrigatorio = {campo: ''};
    campoSenhaObrigatorio = {campo: ''};
    campoEmailObrigatorio = {campo: ''};
    campoUsuarioNumeroDigitos = {campo: '', digitos: '4'};
    campoSenhaNumeroDigitos = {campo: '', digitos: '4'};
    _lembrarSenha: boolean = false;
    _email: string;
    _mensagemSucessoLembrarSenha: string;
    _mensagemErrorLembrarSenha: string;
    
    /**
  * Método construtor.
  * @param service serviço que acessa o backend de login e que é instanciado via injeção de indenpendência
  * @return void
  * @author Filipe Paes
  */
    constructor(private service: AutenticacaoService, private router: Router, private translate: TranslateService) {
        translate.get('formLogin.usuario').subscribe((res) => { 
            this.campoUsuarioObrigatorio.campo = res ;
            this.campoUsuarioNumeroDigitos.campo = res;             
        });
        translate.get('formLogin.senha').subscribe((res) => { 
            this.campoSenhaObrigatorio.campo = res;
            this.campoSenhaNumeroDigitos.campo = res;
        });
        translate.get('formLogin.email').subscribe((res) => { 
            this.campoEmailObrigatorio.campo = res;            
        });
        this.service.logout();
        EventEmitterService.get("onLogin").emit(false);
    }

    /**
    * Método que faz a chamada do método de login do serviço 
    * @return void
    * @author Filipe Paes
    */
    submeter() {
        this._listaCampoMensagem = [];
        this.service.logar(this.login).then(res => {
            if (res) {
                this.clearCache();
                EventEmitterService.get("onLogin").emit(true);
                this.router.navigateByUrl("/home");
            }
        }, (error: ErroMensagem) => {            
            this.login = new Login();
            error.listaCampoMensagem.forEach(obj =>{
                    this._listaCampoMensagem.push(obj);
                })
        });
    }

    lembrarSenha(lembrarSenhaForm) {
        this._mensagemSucessoLembrarSenha = undefined;
        this._mensagemErrorLembrarSenha = undefined;
        this.service.lembrarSenha(this._email).then(res => {
            if (res) {
                this._mensagemSucessoLembrarSenha = res.json().mensagem;
                this._email = undefined;
            }
        }, error => {
            this._mensagemSucessoLembrarSenha = undefined;
            this._email = undefined;
            this._mensagemErrorLembrarSenha = error._body;            
        });
        lembrarSenhaForm.reset();
    }

    /**
    * Método que é chamado sempre que inicia a classe e realiza o logout
    * @return void
    * @author Filipe Paes
    */
    ngOnInit(): void {
        //this.service.logout();        
    }

    public clearCache(): boolean{
        const identificacaoCache: any =
            localStorage.getItem(
                this.service.usuarioLogado().username + HeaderDoadorComponent.chaveParcial);

        if (identificacaoCache) {
            localStorage.removeItem(this.service.usuarioLogado().username + HeaderDoadorComponent.chaveParcial);
        }
        return (identificacaoCache);
    }


	public get listaCampoMensagem(): CampoMensagem[]  {
		return this._listaCampoMensagem;
    }
    
    abrirTelaPreCadastro() {
        this.router.navigateByUrl("/precadastro");
    }

    exibirLembrarSenha(): void {
        this._email = undefined;
        this._lembrarSenha = true;
    }

}