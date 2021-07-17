import { Injectable } from '@angular/core';
import { BaseService } from '../shared/base.service';
import { HttpClient } from '../shared/httpclient.service';
import { environment } from 'environments/environment';
import { ExameDoadorInternacional } from '../doador/exame.doador.internacional';
import { PedidoExame } from './pedido.exame';
import { MotivoStatusDoador } from '../doador/inativacao/motivo.status.doador';
import { FileItem } from 'ng2-file-upload';

/**
 * Registra as chamadas ao back-end envolvendo a entidade PedidoIdm.
 * 
 * @author Pizão
 */
@Injectable()
export class PedidoIdmService extends BaseService {
    
    constructor(protected http: HttpClient) {
        super(http);
    }

    /**
     * Obtém detalhes do pedido de CT e IDM, se ambos ainda NÃO estiverem
     * com resultado cadastrado.
     * 
     * @param solicitacaoId id da solicitação.
     */
    public obterInformacoesPedidoIdm(solicitacaoId: number): Promise<any>{
        return this.http.get(
            environment.endpoint + "solicitacoes/" + solicitacaoId + "/pedidoidm/internacional")
                .then(this.extrairDado).catch(this.tratarErro);
    }

     /**
     * Obtém detalhes do pedido de CT e IDM, se ambos ainda NÃO estiverem
     * com resultado cadastrado.
     * 
     * @param solicitacaoId id da solicitação.
     */
    public listarPedidosComResultado(solicitacaoId: number): Promise<any>{
        return this.http.get(
            environment.endpoint + "solicitacoes/" + solicitacaoId + "/pedidoidm/internacional")
                .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Salva o resultado do pedido de IDM (laudo).
     * 
     * @param idPedidoIdm ID do pedido IDM.
     * @param arquivos lista de arquivos relativos ao laudo.
     */
    public salvarResultadoIdmInternacional(idPedidoIdm: number, arquivos:Map<string, FileItem>): Promise<any>{
        let data: FormData = new FormData();    
        
        if (arquivos) {
            arquivos.forEach((item: FileItem, key: string) => {
                data.append("file", item._file, item.file.name);
            });
        }
        return this.http.fileUpload(environment.endpoint + 
                    "pedidosidm/" + idPedidoIdm + "/resultado/idm/doadorinternacional", data)
                        .then(super.extrairDado).catch(super.tratarErro);
    }
     /**
     * Obtém o arquivo de CRM para download.
     * @param idPreCadastroMedico ID do pré cadastro que armazena este arquivo.
     * @param nomeArquivo nome do arquivo sugerido para download.
     */
    baixarArquivoIdm(idPedido: number, nomeArquivo: string): void{
        super.download("pedidosidm/" +  idPedido + "/download", false, nomeArquivo);
    }

}