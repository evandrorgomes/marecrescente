import { Injectable } from '@angular/core';
import { BaseService } from 'app/shared/base.service';
import { Usuario } from 'app/shared/dominio/usuario';
import { HttpClient } from 'app/shared/httpclient.service';
import { TarefaService } from 'app/shared/tarefa.service';
import { UrlParametro } from 'app/shared/url.parametro';
import { ArrayUtil } from 'app/shared/util/array.util';
import { environment } from 'environments/environment';
import { ContatoTelefonicoTransportadora } from '../model/contato.telefonico.transportadora';
import { EmailContatoTransportadora } from '../model/email.contato.transportadora';
import { EnderecoContatoTransportadora } from '../model/endereco.contato.transportadora';
import { Transportadora } from '../model/transportadora';
/**
 * Registra as chamadas ao back-end envolvendo a entidade PedidoTransporte e tarefas relacionadas.
 *
 * @author Filipe Paes
 */
@Injectable()
export class TransportadoraService extends BaseService {


    constructor(protected http: HttpClient, private tarefaService: TarefaService) {
        super(http);
    }

    /**
     * Lista todas as transportadoras.
     */
    public listarTransportadoras(): Promise<any>{
        return this .http.get(`${environment.courierEndpoint}transportadoras`)
                    .then(this.extrairDado).catch(this.tratarErro);
    }

     /**
     * Obtém uma transportadora por id.
     */
    public obterTransportadora(idTransportadora:number): Promise<any>{
        return this .http.get(`${environment.courierEndpoint}transportadora/${idTransportadora}`)
                    .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Busca transportadoras por nome e retorna uma lista paginada de intes localizados.
     * @param pagina pagina a ser pesquisada.
     * @param quantidadeRegistros  quantidade de itens por pagina.
     * @param parametros itens para busca.
     */
    public buscarTransportadoras(pagina: number, quantidadeRegistros: number, parametros: UrlParametro[] = null):Promise<any>{
        let queryString: string = '?pagina=' + pagina + '&quantidadeRegistros=' + quantidadeRegistros;

        if(ArrayUtil.isNotEmpty(parametros)){
            queryString += "&" + super.toURL(parametros);
        }

        return this.http.get(`${environment.courierEndpoint}transportadoras/paginada${queryString}`)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Salvar um novo registro de transportadora.
     * @param transportadora
     */
    public salvarTransportadora(transportadora: Transportadora):Promise<any>{
        return this.http.post(`${environment.courierEndpoint}transportadoras`, transportadora)
            .then(this.extrairDado).catch(this.tratarErro)
    }

      /**
     * Salvar um novo registro de transportadora.
     * @param idTransportadora id da transportadora a ser inativada.
     * @param transportadora a ser atualizada.
     */
    public atualizarTransportadora(idTransportadora:number, transportadora: Transportadora):Promise<any>{
        return this.http.put(`${environment.courierEndpoint}transportadora/${idTransportadora}`, transportadora)
            .then(this.extrairDado).catch(this.tratarErro)
    }


      /**
     * Atualiza lista de usuarios de transportadora.
     * @param idTransportadora id da transportadora a ser atualizada.
     * @param usuarios lista de usuarios novos.
     */
    public atualizarUsuarios(idTransportadora:number, usuarios: Usuario[]):Promise<any>{
        let idUsuarios: number[] = [];
        usuarios.forEach(usuario => {
            idUsuarios.push(usuario.id);
        });
        let data = {
            "idTransportadora": idTransportadora,
            "usuarios": idUsuarios
        }

        return this.http.put(`${environment.authEndpoint}/api/usuarios/transportadora`, data)
            .then(this.extrairDado).catch(this.tratarErro)
    }

    /**
     * Atualiza endereco de contato da transportadora.
     * @param idEndereco id do endereco da transportadora.
     * @param endereco objeto a ser atualizado.
     */
    public inserirEndereco(idTransportadora:number, endereco: EnderecoContatoTransportadora):Promise<any>{
        return this.http.put(`${environment.courierEndpoint}transportadora/${idTransportadora}/atualizarendereco`, endereco)
            .then(this.extrairDado).catch(this.tratarErro)
    }



     /**
     * Atualiza lista de usuarios de transportadora.
     * @param idTransportadora id da transportadora a ser atualizada.
     * @param telefone objeto a ser atualizado.
     */
    public inserirTelefone(idTransportadora:number, telefone: ContatoTelefonicoTransportadora):Promise<any>{
        return this.http.post(`${environment.courierEndpoint}transportadora/${idTransportadora}/inserirtelefone`, telefone)
            .then(this.extrairDado).catch(this.tratarErro)
    }

     /**
     * Atualiza lista de usuarios de transportadora.
     * @param idTransportadora id da transportadora a ser atualizada.
     * @param telefone objeto a ser atualizado.
     */
    public inserirEmail(idTransportadora:number, email:EmailContatoTransportadora):Promise<any>{
        return this.http.post(`${environment.courierEndpoint}transportadora/${idTransportadora}/inseriremail`, email)
            .then(this.extrairDado).catch(this.tratarErro)
    }

     /**
     * Inativa o registro de transportadora.
     * @param idTransportadora id da transportadora a ser inativada.
     */
    public inativar(idTransportadora: number):Promise<any>{
        return this.http.delete(`${environment.courierEndpoint}transportadora/${idTransportadora}`)
            .then(this.extrairDado).catch(this.tratarErro)
    }
}
