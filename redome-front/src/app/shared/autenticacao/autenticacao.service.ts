import { PermissaoRotaComponente } from '../permissao.rota.componente';
import { ComponenteRecurso } from '../enums/componente.recurso';
import { EventEmitterService } from '../event.emitter.service';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivateChild } from '@angular/router';
import { UsuarioLogado } from '../dominio/usuario.logado';
import { environment } from 'environments/environment';
import { Login } from '../../login/login';
import { HttpClient } from '../httpclient.service';
import { Response, Http, Headers } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import { Injectable, Component } from '@angular/core';
import { ErroMensagem } from '../erromensagem';
import { tokenNotExpired, JwtHelper } from 'angular2-jwt';
import { Perfis } from '../enums/perfis';
import { Recursos } from 'app/shared/enums/recursos';

/**
 * Classe de Serviço utilizada para acessar os serviços REST de login
 * @author Filipe Paes
 */
@Injectable()
export class AutenticacaoService implements CanActivate, CanActivateChild {
    recurso_logout: string = "logout"

    private jwtHelper: JwtHelper = new JwtHelper();

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é
     * injetada e atribuída como atributo
     * @author Filipe Paes
     */
    constructor(private http: Http, private router: Router) {
    }

    /**
     * Método que vai de login que vai ao servidor REST e verifica o acesso do usuário
     * @param obj referência do tipo Login que é recebida da tela
     * @return Promise<Boolean> retorno ajax se o usuário está tem autorização ou não
     * @author Filipe Paes
     */
    logar(obj: Login): Promise<Boolean> {
        let headers: Headers = new Headers();
        headers.append('Content-Type', 'application/json;charset=utf-8');
        //headers.append('Authorization', 'Basic bW9kcmVkLWZyb250LWNsaWVudDo=' );
        headers.append('Authorization', 'Basic bW9kcmVkLWZyb250LWNsaWVudDoxMjM0NTY=' );

        return this.http.post(environment.authEndpoint +
                "oauth/token?grant_type=password&username=" + obj.username + "&password=" + obj.password,
                null, {headers: headers}).toPromise().then(res => {
                if (res.status == 200) {
                    let dados = res.json();
                    localStorage.setItem("token", dados.access_token);
                    return true;
                }
            return false;
        }).catch((error: ErroMensagem) => { return Observable.throw(error).toPromise(); });
    }

    /**
     * @param obj Método que realiza o logout na aplicação.
     * Consiste em remover do LocalStorage, e do back-end, o usuario logado.
     * @author Pizão
     */
    logout() {
        if (this.obterToken() && !this.tokenExpirado() ) {
            let headers: Headers = new Headers();
            headers.append('Content-Type', 'application/json;charset=utf-8');
            headers.append('Authorization', "Bearer " + localStorage.getItem("token") );

            return this.http.delete(environment.authEndpoint + this.recurso_logout,
                {headers: headers}).toPromise().then(res => {
                    if (res.status == 200) {
                        localStorage.removeItem("token");
                        EventEmitterService.get("onLogin").emit(false);
                        this.router.navigateByUrl("/login");
                    }
            }).catch((error: ErroMensagem) => { return Observable.throw(error).toPromise(); });
        }
        else {
            localStorage.removeItem("token");
            EventEmitterService.get("onLogin").emit(false);
            this.router.navigateByUrl("/login");
        }

    }

    /**
     * Método que verifica se o token está espirado
     *
     * @returns boolean
     *
     * @memberOf AutenticacaoService
     */
    tokenExpirado(): boolean {
        return !tokenNotExpired();
    }

    /**
     * Método que retorna o usuário logado através do token
     *
     * @returns {UsuarioLogado}
     *
     * @memberOf AutenticacaoService
     */
    usuarioLogado(): UsuarioLogado {
        if(this.obterToken()) {
            let token = this.jwtHelper.decodeToken(localStorage.getItem("token"));
            return new UsuarioLogado().jsonToEntity(token);
        }
        else {
            return null;
        }
    }

    /**
     * Retorna a data que o token expira
     *
     * @returns {Date}
     *
     * @memberOf AutenticacaoService
     */
    dataQueExpiraToken(): Date {
        return this.jwtHelper.getTokenExpirationDate(localStorage.getItem("token"));
    }

     /**
     * Retorna a string do token
     *
     * @returns {string}
     *
     * @memberOf AutenticacaoService
     */
    obterToken(): string {
        return localStorage.getItem("token");
    }


     /**
     * Retorna a string do token para URL
     *
     * @returns {string}
     *
     * @memberOf AutenticacaoService
     */
    obterTokenParaUrl(): string {
        return "?access_token=" + localStorage.getItem("token");
    }



    /**
     * Método utilizado pela classe de Router para saber se a rota está ativa ou não.
     * Também redireciona para a tela de login caso o token tenha espirado
     *
     * @param {ActivatedRouteSnapshot} activatedRoute
     * @param {RouterStateSnapshot} routerState
     * @returns
     *
     * @memberOf AutenticacaoService
     */
    canActivate(activatedRoute: ActivatedRouteSnapshot, routerState: RouterStateSnapshot) {
        if (!this.tokenExpirado()) {
            let recursoComponente: string =
                (<PermissaoRotaComponente>(<any> activatedRoute.component).prototype).nomeComponente();

            if (recursoComponente == ComponenteRecurso.Componente[ComponenteRecurso.Componente.HomeComponent]
                        || recursoComponente == ComponenteRecurso.Componente[ComponenteRecurso.Componente.TesteComponent]) {
                return true;
            }
            let hasPermissaoAcesso: boolean = this.temPermissaoAcesso( recursoComponente );
            if(!hasPermissaoAcesso){
                console.log("Acesso ao recurso " + recursoComponente + " foi negado. " +
                            "Verifique as configurações de rota e o perfil do usuário.");
                throw new Error("Acesso negado.");
            }
            return hasPermissaoAcesso;
        }
        else {
            this.logout();
            this.router.navigateByUrl("/login");
            return false;
        }
    }

    /**
     * Método utilizado pela classe de Router para saber se a rota filha está ativa ou não,
     * chamando o método canActivate, podendo redirecionar para o login
     *
     *
     * @param {ActivatedRouteSnapshot} activatedRoute
     * @param {RouterStateSnapshot} routerState
     * @returns
     *
     * @memberOf AutenticacaoService
     */
    canActivateChild(activatedRoute: ActivatedRouteSnapshot, routerState: RouterStateSnapshot) {
        if (activatedRoute.component == null) {
            return true;
        }
        else {
            return this.canActivate(activatedRoute, routerState);
        }

    }


    temPermissaoAcesso(nomeComponente): boolean {
        let temPermissao: boolean = false;
        let usuario = this.usuarioLogado();
        if (usuario) {

            let recursos: string[] = ComponenteRecurso.listarRecursos(ComponenteRecurso.Componente[ComponenteRecurso.Componente[nomeComponente]]);
            if (recursos) {
                recursos.forEach(recurso => {
                    for (var idx in usuario.recursos) {
                        if (usuario.recursos[idx] == recurso) {
                            temPermissao = true;
                        }
                    }
                });
            }

        }

        return temPermissao;
    }

    /**
     * Metodo que verificar se o usuario atual eh do perfil medico.
     *
     * @returns {boolean}
     * @memberof AutenticacaoService
     */
    public temPerfilMedico():boolean{
        return this.usuarioLogado().perfis && this.usuarioLogado().perfis.some(perfil => perfil == "MEDICO");
    }

    public temRecurso(recurso: string): boolean {
        return this.usuarioLogado().recursos.some(recursoUsuario => recursoUsuario == recurso);
    }

    public temPerfilAnalistaWorkupInternacional():boolean{
        return this.usuarioLogado().perfis && this.usuarioLogado().perfis.some(perfil => perfil == "ANALISTA_WORKUP_INTERNACIONAL");
    }

    public temPerfilAnalistaWorkup():boolean{
        return this.usuarioLogado().perfis && this.usuarioLogado().perfis.some(perfil => perfil == "ANALISTA_WORKUP");
    }

    public temPerfilAnalistaBusca():boolean{
        return this.usuarioLogado().perfis && this.usuarioLogado().perfis.some(perfil => perfil == Perfis.ANALISTA_BUSCA_REDOME);
    }

    public temPerfilAvaliador():boolean{
        return this.usuarioLogado().perfis && this.usuarioLogado().perfis.some(perfil => perfil == Perfis.AVALIADOR);
    }

    public temPerfilMedicoTransplantador():boolean{
        return this.usuarioLogado().perfis && this.usuarioLogado().perfis.some(perfil => perfil == Perfis.MEDICO_TRANSPLANTADOR.nome);
    }

    public verificaUsuarioLogadoEMedicoResponsavelPaciente(paciente): boolean {
        if (paciente.medicoResponsavel.usuario.username == this.usuarioLogado().username) {
            return true;
        }
        return false;
    }

    public verificaUsuarioLogadoEMedicoAvaliadorDoCentroAvaliadorDoPaciente(paciente): boolean {
        let achouCentro: boolean = false;
        this.usuarioLogado().centros.forEach(centro => {
            if (centro.id == paciente.centroAvaliador.id) {
                achouCentro = true;
            }
        });
        return achouCentro;
    }

    public verificaUsuarioLogadoEMedicoTransplantadorAssociadoABuscaDoPaciente(paciente): boolean {
        let achouCentro: boolean = false;
        if (paciente.buscaAtiva && paciente.buscaAtiva.centroTransplante) {
            this.usuarioLogado().centros.forEach(centro => {
                if (centro.id == paciente.buscaAtiva.centroTransplante.id) {
                    achouCentro = true;
                }
            });
        }
        return achouCentro;
    }

    public lembrarSenha(email: string): Promise<any> {
        return this.http.put(environment.publicEndpoint + "lembrarsenha", email).toPromise();
    }

    public temRecursoSolicitarFase2Nacional(): boolean {
        return this.temRecurso(Recursos.SOLICITAR_FASE_2_NACIONAL);
    }

    public temRecursoSolicitarFase2Internacional(): boolean {
        return this.temRecurso(Recursos.SOLICITAR_FASE_2_INTERNACIONAL);
    }

    public temRecursoSolicitarFase2(): boolean {
        return this.temRecursoSolicitarFase2Nacional() || this.temRecursoSolicitarFase2Internacional();
    }

    public temRecursoSolicitarFase3Nacional(): boolean {
        return this.temRecurso(Recursos.SOLICITAR_FASE_3_NACIONAL);
    }

    public temRecursoSolicitarFase3Internacional(): boolean {
        return this.temRecurso(Recursos.SOLICITAR_FASE_3_INTERNACIONAL);
    }

    public temRecursoSolicitarFase3(): boolean {
        return this.temRecursoSolicitarFase3Nacional() || this.temRecursoSolicitarFase3Internacional();
    }

    public temRecursoCadastrarResultadoFase2Internacional(): boolean {
        return this.temRecurso(Recursos.CADASTRAR_RESULTADO_PEDIDO_FASE_2_INTERNACIONAL);
    }

    public temRecursoCadastrarResultadoFase3Internacional(): boolean {
        return this.temRecurso(Recursos.CADASTRAR_RESULTADO_PEDIDO_CT);
    }

    public temRecursoCadastrarResultadoInternacional(): boolean {
        return this.temRecursoCadastrarResultadoFase2Internacional() || this.temRecursoCadastrarResultadoFase3Internacional();
    }


    public temRecursoCancelarSolicitarFase2(): boolean {
        return this.temRecurso(Recursos.CANCELAR_FASE_2);
    }

    public temRecursoCancelarSolicitarFase3(): boolean {
        return this.temRecurso(Recursos.CANCELAR_FASE_3);
    }

    public temRecursoCancelarSolicitar(): boolean {
        return this.temRecursoCancelarSolicitarFase2() || this.temRecursoCancelarSolicitarFase3();
    }

    public temPerfilAnalistaContato():boolean{
        return this.usuarioLogado().perfis && this.usuarioLogado().perfis.some(perfil => perfil == Perfis.ANALISTA_CONTATO.nome);
    }

    public temRecursoDistriburWorkup(): boolean {
        return this.temRecurso(Recursos.DISTRIBUIR_WORKUP);
    }
    public temRecursoDistriburWorkupInternacional(): boolean {
        return this.temRecurso(Recursos.DISTRIBUIR_WORKUP_INTERNACIONAL);
    }


}
