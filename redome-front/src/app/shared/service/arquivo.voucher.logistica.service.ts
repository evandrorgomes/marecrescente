import { Observable } from 'rxjs';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Injectable } from '@angular/core';
import { environment } from "environments/environment";
import { HttpClient } from "../httpclient.service";
import { BaseService } from "../base.service";

/**
 * Classe de serviço para interação com a tabela de email
 */
@Injectable()
export class ArquivoVoucherLogisticaService extends BaseService {
   
    
    address: string = "arquivosvoucherlogistica";


    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Rafael Pizão
     */
    constructor(http: HttpClient) {
        super(http);
    }

    baixarArquivoVoucher(id: number, nomeArquivo: string) {
        super.download(this.address + "/" + id + "/download",
            false, "" + nomeArquivo);
    }


}