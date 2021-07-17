import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { FileItem } from 'ng2-file-upload';
import { BaseService } from '../base.service';
import { HttpClient } from '../httpclient.service';
import { TarefaService } from '../tarefa.service';
/**
 * Registra as chamadas ao back-end envolvendo a entidade PedidoTransporte e tarefas relacionadas.
 *
 * @author Filipe Paes
 */
@Injectable()
export class LogisticaMaterialService extends BaseService {

    constructor(protected http: HttpClient, private tarefaService: TarefaService) {
        super(http);
    }


    /**
     * Método que vai salvar o arquivo de pedido de transporte.
     * @param idPedidoLogistica id do pedido de logistica.
     * @param arquivo MultipartFormData com cada um dos itens que serão submetidos ao back.
     * @return Observable<CampoMensagem[]> retorno ajax com campos de mensagem
     */
    alterarCartaCnt(idPedidoLogistica: number, descricao:string, arquivos:Map<string, FileItem>): Promise<any> {

        let data: FormData = new FormData();
        // data.append("file", arquivo._file);
        data.append("id", new Blob([JSON.stringify(idPedidoLogistica)], {
            type: 'application/json'
        }), '');


        data.append("descricao", new Blob([JSON.stringify(descricao)], {
            type: 'application/json'
        }), '');

        if (arquivos) {
            arquivos.forEach((item: FileItem, key: string) => {
                data.append("file", item._file, item.file.name);
            });
        }

        return this.http.fileUpload(environment.endpoint + "pedidostransporte/transporte/cartacnt", data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public baixarDocumentos(id: number, codigoRelatorio: string, docxExtensaoRelatorio: boolean = false): void {
        let extensao : string = '.pdf';
        if(docxExtensaoRelatorio){
            extensao = '.docx';
        }
        super.download("pedidoslogistica/" + id + "/download?relatorio=" + codigoRelatorio +
                "&docxExtensaoRelatorio=" + docxExtensaoRelatorio, false, codigoRelatorio + extensao);
    }

/* ################################################################################################# */

    public obterLogisticaDoadorInternacionalColeta(idPedidoLogistica: number): Promise<any>{
      return this .http.get(`${environment.workupEndpoint}pedidologistica/${idPedidoLogistica}/material/internacional`)
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }

    public obterLogisticaMaterial(idPedidoLogistica: number): Promise<any>{
      return this .http.get(`${environment.workupEndpoint}pedidologistica/${idPedidoLogistica}/material/nacional`)
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }

    public obterLogisticaMaterialAereo(idPedidoLogistica: number): Promise<any>{
      return this .http.get(`${environment.workupEndpoint}pedidologistica/${idPedidoLogistica}/material/aereo`)
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }

}
