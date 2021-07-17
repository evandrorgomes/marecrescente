import { AvaliacaoCamaraTecnica } from './avaliacao.camara.tecnica';
import { environment } from 'environments/environment';
import { Injectable } from "@angular/core";
import { BaseService } from "../../shared/base.service";
import { HttpClient } from "../../shared/httpclient.service";
import { FileItem } from 'ng2-file-upload';

/**
 * Classe de Serviço utilizada para acessar os serviços REST de avaliação
 * de câmara técnica.
 * @author Filipe Paes
 */
@Injectable()
export class AvalicacaoCamaraTecnicaService extends BaseService{

    private address: string = "avaliacaocamaratecnica";
    private addressStatus: string = "statusavaliacaocamaratecnica";
    
    constructor(protected http: HttpClient) {
        super(http);
    }
     /**
     * Realiza consulta de tarefas avaliação de câmara técnica.
     * 
     * @param pagina - pagina a ser listada
     * @param quantidadeRegistros - numero de itens da pagina.
     */
    public listarTarefasAvaliacaoCamaraTecnica(pagina: number, quantidadeRegistros: number): Promise<any> {
        return this.http.get(
            environment.endpoint + this.address + "/tarefas?pagina=" + pagina + 
                "&quantidadeRegistros=" + quantidadeRegistros).then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obterm uma avaliação de camara tecnica por id.
     * @param idAvaliacaoCamaraTecnica identificacao da avaliacao de camara técnica.
     */
    obterAvaliacao(idAvaliacaoCamaraTecnica: number): Promise<any> {
        return this.http.get(environment.endpoint + this.address + "/" + idAvaliacaoCamaraTecnica)
            .then(this.extrairDado).catch(this.tratarErro);
    }
    /**
     * Obtem lista de status de avaliação de câmara técnica.
     */
    obterListaStatus():Promise<any> {
        return this.http.get(environment.endpoint + this.addressStatus + "/")
            .then(this.extrairDado).catch(this.tratarErro);
    }

    salvarStatus(avaliacao: AvaliacaoCamaraTecnica):Promise<any> {
        return this.http.post(environment.endpoint + this.address + "/atualizarstatus", avaliacao)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    
    /**
     * Aprova avaliação de câmara técnica.
     * 
     * @param arquivo arquivo a ser salvo.
     * @param avalicao avaliacao a ser aprovada.
     */
    public aprovarAvaliacao(avalicao: AvaliacaoCamaraTecnica, arquivos:Map<string, FileItem>): Promise<any>{
        let data: FormData = new FormData();    
         
        if (arquivos) {
            arquivos.forEach((item: FileItem, key: string) => {
                data.append("file", item._file, item.file.name);
            });
        }
        data.append("avaliacao", new Blob([JSON.stringify(avalicao)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(environment.endpoint + this.address + "/aprovar", data)
                        .then(super.extrairDado).catch(super.tratarErro);
    }


     /**
     * Reprova avaliação de câmara técnica.
     * 
     * @param arquivo arquivo a ser salvo. 
     * @param avalicao avaliacao a ser aprovada.
     */
    public reprovarAvaliacao(avalicao: AvaliacaoCamaraTecnica, arquivos:Map<string, FileItem>): Promise<any>{
        let data: FormData = new FormData();    
         
        if (arquivos) {
            arquivos.forEach((item: FileItem, key: string) => {
                data.append("file", item._file, item.file.name);
            });
        }
        data.append("avaliacao", new Blob([JSON.stringify(avalicao)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(environment.endpoint + this.address + "/reprovar", data)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

}