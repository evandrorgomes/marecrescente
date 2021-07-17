import { EmailContatoCourier } from './email.contato.courier';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import 'rxjs/add/operator/map';
import { BaseService } from '../shared/base.service';
import { HttpClient } from '../shared/httpclient.service';
import { UrlParametro } from '../shared/url.parametro';
import { ArrayUtil } from '../shared/util/array.util';
import { Courier } from './courier';
import { ContatoTelefonicoCourier } from './contato.telefonico.courier';

/**
 * Classe de Serviço utilizada para acessar os serviços REST de courier 
 * @author Queiroz
 */
@Injectable()
export class CourierService extends BaseService{

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Rafael Pizão
     */
    constructor(http: HttpClient) {
        super(http);
    }

    
    /**
     * Recupera o detalhe da avaliação do paciente para ser utilizado
     * na tela de confirmação de centro de transplante.
     * 
     */
    public listar(): Promise<any>{
        return this.http.get(`${environment.courierEndpoint}couriers`)
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }


    
    /**
     * Busca Couries por nome e/ou cpf e retorna uma lista paginada de intes localizados.
     * 
     * @param pagina pagina a ser pesquisada.
     * @param quantidadeRegistros  quantidade de itens por pagina.
     * @param parametros itens para busca.
     */
    public buscarCouriers(pagina: number, quantidadeRegistros: number, parametros: UrlParametro[] = null):Promise<any>{
        let queryString: string = '?pagina=' + pagina + '&quantidadeRegistros=' + quantidadeRegistros;

        if(ArrayUtil.isNotEmpty(parametros)){
            queryString += "&" + super.toURL(parametros);
        }

        return this.http.get(`${environment.courierEndpoint}couriers/paginado${queryString}`)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Insere um novo courier.
     * @param courier 
     */
    public inserirCourier(courier:Courier):Promise<any>{
        return this.http.post(`${environment.courierEndpoint}couriers`,courier)
        .then(this.extrairDado).catch(this.tratarErro);
    }


     /**
     * Atualiza dados de courier.
     * @param courier 
     */
    public atualizarCourier(id:number, courier:Courier):Promise<any>{
        return this.http.put(`${environment.courierEndpoint}courier/${id}`, courier)
        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obtém um courier por id.
     * @param id 
     */
    public obterCourier(id:number):Promise<any>{
        return this.http.get(`${environment.courierEndpoint}courier/${id}`)
        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Inclui um novo contato de courier.
     * @param id identificação do courier.
     * @param contatoTelefonico objeto de contato telefonico.
     */
    public salvarContatoCourier(id:number, contatoTelefonico:ContatoTelefonicoCourier){
        return this.http.post(`${environment.courierEndpoint}courier/${id}/contatostelefonicos`,contatoTelefonico)
        .then(this.extrairDado).catch(this.tratarErro);
    }

      /**
     * Inativa um courier.
     * @param id identificação do courier.
     */
    public inativar(id:number){
        return this.http.delete(`${environment.courierEndpoint}courier/${id}` )
        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Atualiza lista de emails de courier.
     * @param idCourier id da transportadora a ser atualizada.
     * @param telefone objeto a ser atualizado.
     */
    public inserirEmail(idCourier:number, email:EmailContatoCourier):Promise<any>{
        return this.http.post(`${environment.courierEndpoint}courier/${idCourier}/inseriremail`, email)
            .then(this.extrairDado).catch(this.tratarErro)
    }


}