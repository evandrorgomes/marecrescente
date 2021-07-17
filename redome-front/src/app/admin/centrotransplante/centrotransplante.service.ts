import { Injectable } from '@angular/core';
import { UrlParametro } from 'app/shared/url.parametro';
import { ArrayUtil } from 'app/shared/util/array.util';
import { environment } from 'environments/environment';
import 'rxjs/add/operator/map';
import { BaseService } from '../../shared/base.service';
import { CentroTransplante } from '../../shared/dominio/centro.transplante';
import { Laboratorio } from '../../shared/dominio/laboratorio';
import { FuncaoCentroTransplante } from '../../shared/enums/funcao.centro.transplante';
import { HttpClient } from '../../shared/httpclient.service';
import { CentroTransplanteUsuario } from './../../shared/dominio/centro.transplante.usuario';
import { HttpClientNoAutentication } from './../../shared/httpclient.noautentication.service';
import { EmailContatoCentroTransplante } from 'app/shared/classes/email.contato.centrotransplante';
import { EnderecoContatoCentroTransplante } from 'app/shared/model/endereco.contato.centro.transplante';
import { ContatoTelefonicoCentroTransplante } from 'app/shared/model/contato.telefonico.centro.transplante';


/**
 * Classe de Serviço utilizada para acessar os serviços REST do centro de transplante.
 *
 * @author Thiago Moraes
 */
@Injectable()
export class CentroTransplanteService extends BaseService {
    address: string = "centrosTransplante";

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é
     * injetada e atribuída como atributo
     * @author Thiago Moraes
     */
    constructor(protected http: HttpClient, private httpClientNoAuthentication: HttpClientNoAutentication) {
        super(http);
    }


    /**
    * Método para recuperar o conjuto de centros de transplante disponíveis.
    *
    * @param parametros - conjunto de um ou mais termos que serão utilizados para fazer match simples
    * nos elementos textuais do centro transplante.
    *
    * @param pagina - informações sobre paginação do conjunto que será retornado (informação opcional)
    *
    * @param quantidadeRegistros - informações sobre quantidade de registros retornados na paginação (informação opcional)
    */
    listarCentroTransplantes(parametros: UrlParametro[] = null, pagina: number, quantidadeRegistros: number, funcaoCentroTransplante?:number) {

        let separador: string = '?'
        let queryString: string = '';
        if (pagina != null) {
            queryString += separador + 'pagina=' + pagina;
            separador = '&'
        }
        if (quantidadeRegistros != null) {
            queryString += separador + 'quantidadeRegistros=' + quantidadeRegistros;
            separador = '&'
        }

        if(ArrayUtil.isNotEmpty(parametros)){
            queryString += separador + super.toURL(parametros);
        }
        if (funcaoCentroTransplante != null) {
            queryString += separador + "idFuncaoCentroTransplante=" + funcaoCentroTransplante;
        }

        return  this.httpClientNoAuthentication.get(environment.publicEndpoint + this.address + queryString)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
    * Lista todos os centros que são, de fato, transplantadores.
    */
   listarSomenteCentrosTransplantadores() {
        return this.http.get(environment.endpoint +
                "centrosTransplante/funcao/" + FuncaoCentroTransplante.TRANSPLANTADOR)
                    .then(this.extrairDado).catch(this.tratarErro);
    }

   /**
    * Método para realizar a exclusão lógica de um centro transplante.
    *
    * @param id a chave de identificação do centro de transplante que será eliminado.
    */
    eliminarCentroTransplante(id :number){
        return this.http.delete(environment.endpoint + this.address + '/'+id)
        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
    * Lista todos as funções que um centro pode assumir.
    */
   listarFuncoes(): Promise<any> {
        return this.http.get(environment.endpoint +
            "centrosTransplante/funcoes")
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
    * Método para realizar a exclusão lógica de um centro transplante.
    *
    * @param id a chave de identificação do centro de transplante que será eliminado.
    */
   atualizarDadosBasicos(id :number, centroTransplante: CentroTransplante): Promise<any> {

        return this.http.put(environment.endpoint  + 'centrosTransplante/'+id + '/dadosbasicos', centroTransplante)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
    * Método para realizar a exclusão lógica de um centro transplante.
    *
    * @param id a chave de identificação do centro de transplante que será eliminado.
    */
    atualizarLaboratorioPreferencia(id :number, laboratorio: Laboratorio): Promise<any> {

        return this.http.put(environment.endpoint  + 'centrosTransplante/'+id + '/laboratoriopreferencia', laboratorio)
            .then(this.extrairDado).catch(this.tratarErro);
    }


     /**
    * Método para atualizar listagem de médicos responsáveis por centro de transplante.
    *
    * @param id identificação do centro de transplante.
    * @param centrosUsuario
    */
   atualizarMedicos(id :number, centrosUsuario: CentroTransplanteUsuario[]): Promise<any> {

    return this.http.put(environment.endpoint  + 'centrosTransplante/'+id + '/medico', centrosUsuario)
        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
    * Método para realizar a exclusão lógica de um centro transplante.
    *
    * @param id a chave de identificação do centro de transplante que será eliminado.
    */
    removerLaboratorioPreferencia(id :number): Promise<any> {

        return this.http.delete(environment.endpoint  + 'centrosTransplante/'+id + '/laboratoriopreferencia')
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
    * Método para adicionar um endereco de contato a um centro transplante.
    *
    * @param id a chave de identificação do centro de transplante.
    * @param enderecoContato - endereco de contato a ser adicionado ao centro.
    */
    adicionarEnderecoContato(id :number, enderecoContato: EnderecoContatoCentroTransplante): Promise<any> {

        return this.http.post(environment.endpoint  + 'centrosTransplante/'+id + '/enderecocontato', enderecoContato)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
    * Método para adicionar um telefone de contato a um centro transplante.
    *
    * @param id a chave de identificação do centro de transplante.
    * @param contatoTelefonico - telefone de contato a ser adicionado ao centro.
    */
    adicionarContatoTelefonico(id :number, contatoTelefonico: ContatoTelefonicoCentroTransplante): Promise<any> {
        return this.http.post(environment.endpoint  + 'centrosTransplante/'+id + '/contatostelefonicos', contatoTelefonico)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    salvarCentroTransplante(centroTransplante: CentroTransplante): Promise<any> {
        return this.http.post(environment.endpoint  + 'centrosTransplante', centroTransplante)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
    * Método para adicionar um email de contato a um centro transplante.
    *
    * @param id a chave de identificação do centro de transplante.
    * @param emailContato - email de contato a ser adicionado ao centro.
    */
    adicionarEmailContato(id :number, emailContato: EmailContatoCentroTransplante): Promise<any> {
        return this.http.post(environment.endpoint  + 'centrosTransplante/'+id + '/emails', emailContato)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    obterEnderecoEntrega(id: number): Promise<any> {
       return this.http.get(`${environment.endpoint}centrosTransplante/${id}/enderecoentrega`)
          .then(this.extrairDado).catch(this.tratarErro);
    }

    obterEnderecoWorkup(id: number): Promise<any> {
        return this.http.get(`${environment.endpoint}centrosTransplante/${id}/enderecoworkup`)
           .then(this.extrairDado).catch(this.tratarErro);
    }

    obterCentroTransplante(id: number): Promise<any> {
      return this.http.get(`${environment.endpoint}centrosTransplante/${id}`)
          .then(this.extrairDado).catch(this.tratarErro);
    }


}
