import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import 'rxjs/add/operator/map';
import { BaseService } from '../../shared/base.service';
import { UsuarioLogado } from '../../shared/dominio/usuario.logado';
import { HttpClient } from '../../shared/httpclient.service';
import { UrlParametro } from '../../shared/url.parametro';
import { ArrayUtil } from '../../shared/util/array.util';
import { query } from '@angular/core/src/animation/dsl';
import { Perfil } from 'app/shared/dominio/perfil';
import { Usuario } from '../../shared/dominio/usuario';
import { BancoSangueCordao } from 'app/shared/dominio/banco.sangue.cordao';

/**
 * Classe de Serviço utilizada para acessar os serviços REST do Usuario do sistema.
 * 
 * @author Thiago Moraes
 */
@Injectable()
export class UsuarioService extends BaseService{

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Thiago Moraes
     */
    constructor(http: HttpClient) {
        super(http);
    }


    /**
    * Método para recuperar o conjuto de usuarios disponíveis.
    * 
    * @param pagina - informações sobre paginação do conjunto que será retornado (informação opcional)
    * @param quantidadeRegistros - informações sobre quantidade de registros retornados na paginação (informação opcional)
    * @param parametros - (opcional) parâmetros a serem utilizados na consulta.
    */
    listarUsuarios(pagina: number, quantidadeRegistros: number, parametros: UrlParametro[] = null) {
        let queryString: string = '?pagina=' + pagina + '&quantidadeRegistros=' + quantidadeRegistros;

        if(ArrayUtil.isNotEmpty(parametros)){
            queryString += "&" + super.toURL(parametros);
        }

        return this.http.get(environment.endpoint + 'usuarios' + queryString)
            .then(this.extrairDado).catch(this.tratarErro);

    }

    listarAnalistasBusca(): Promise<any> {
        return this.http.get(environment.endpoint + 'usuarios/analistasdebusca')
            .then(this.extrairDado).catch(this.tratarErro);
    }



    listarUsuarioTransportadora(): Promise<any> {
        return this.http.get(environment.endpoint + 'usuarios/transportadora')
            .then(this.extrairDado).catch(this.tratarErro);
    }

    atualizarRelacaoCentroAvaliador(id: number, usuario: UsuarioLogado): Promise<any> {
        return this.http.post(environment.endpoint + 'usuarios/' + id + '/relacaocentro', usuario)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description Obtém o usuário, a partir do ID informado.
     * @author Pizão
     * @param {number} idUsuario
     * @returns {Promise<any>}
     */
    obterUsuario(idUsuario: number): Promise<any> {
        return this.http.get(environment.endpoint + 'usuarios/' + idUsuario)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description Atualiza os dados de acesso do usuário, se eles forem informados.
     * @author Pizão
     * @param {number} idUsuario 
     * @param {string} login
     * @param {string} email
     * @param {string} nome
     * @returns {Promise<any>}
     */
    alterarDadosAcesso(idUsuario: number, login: string, email: string, nome: string): Promise<any> {
        if(!login && !email && !nome){
            throw new Error("Nenhum parâmetro foi informado para que o usuário possa ser atualizado.");
        }

        let parametrosAtualizados: UrlParametro[] = [];

        if(login){
            parametrosAtualizados.push(new UrlParametro("login", login));
        }
        if(email){
            parametrosAtualizados.push(new UrlParametro("email", email));
        }
        if(nome){
            parametrosAtualizados.push(new UrlParametro("nome", nome));
        }

        return this.http.put(environment.endpoint + 'usuarios/' + idUsuario + '/acesso' + "?" + super.toURL(parametrosAtualizados))
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description Atualiza os perfis de acesso re-definidos para usuários.
     * @author Pizão
     * @param {number} idUsuario
     * @param {Perfil[]} perfisDeAcesso
     * @returns {Promise<any>}
     */
    alterarPerfis(idUsuario: number, perfisDeAcesso: Perfil[], bscup: BancoSangueCordao = null): Promise<any> {
        let url: string = environment.endpoint + 'usuarios/' + idUsuario + '/perfis';
        if(bscup){
            url += "?bscup=" + bscup.id;
        }
        return this.http.put(url, perfisDeAcesso).then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description Salva um novo usuário no banco do Redome.
     * @author Pizão
     * @param {Usuario} usuario
     * @returns {Promise<any>}
     */
    salvar(usuario: Usuario): Promise<any>{
        let url: string = environment.endpoint + 'usuarios/';
        if(usuario.bancoSangue){
            url += "bscup";
        }
        return this.http.post(url, usuario).then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description Inativa o usuário da base (exclusão lógica).
     * @author Pizão
     * @param {number} idUsuario
     */
    inativar(idUsuario: number): Promise<any>{
        return this.http.delete(environment.endpoint + 'usuarios/' + idUsuario)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * @description Inativa o usuário da base (exclusão lógica).
     * @author Pizão
     * @param {number} idUsuario
     */
    alterarSenha(senhaAtual, novaSenha: string): Promise<any>{
        const data: any = {
            'senhaAtual': senhaAtual,
            'novaSenha': novaSenha
        };

        return this.http.put(environment.endpoint + 'usuarios/alterarsenha', data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obtem lista de usuarios com perfil LABORATORIO.
     *
     * @returns {Promise<any>} - retorna lista de usuarios com perfil LABORATORIO.
     * @memberof LaboratorioService
     */
    listarUsuarioLaboratorio(): Promise<any> {
        return this.http.get(environment.endpoint + 'usuarios/laboratorio')
            .then(this.extrairDado).catch(this.tratarErro);
    }

}