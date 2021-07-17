import { Injectable } from "@angular/core";
import { BaseService } from '../base.service';
import { HttpClient } from "../httpclient.service";
import { RegistroContato } from "../classes/registro.contato";
import { environment } from '../../../environments/environment';

/**
 * Classe de métodos de consumo do endpoint de registro de contato.
 * @author Filipe Paes
 */
@Injectable()
export class RegistroContatoService extends BaseService{
    
    private address:string = 'registrocontatos';

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     */
    constructor(http: HttpClient) {
        super(http);
    }

    /**
     * Envia para o endpoint de registro de contato o novo contato realizado.
     * @param registro objeto a ser persistido.
     */
    salvar(registro:RegistroContato): Promise<any> {
        return this.http.post(`${environment.endpoint}${this.address}`, registro)
        .then(this.extrairDado).catch(this.tratarErro);
    } 

    /**
     * Lista Registros de Contato de acordo com a id de Pedido.
     * @param idPedidoContato identificação do pedido de contato.
     */
    listar(pagina: number, quantidadeRegistro: number, idPedidoContato: number): Promise<any>{
        return this.http.get(`${environment.endpoint}${this.address}/?pagina=${pagina}&quantidadeRegistros=${quantidadeRegistro}&idPedido=${idPedidoContato}`)
        .then(this.extrairDado).catch(this.tratarErro);
    }
}