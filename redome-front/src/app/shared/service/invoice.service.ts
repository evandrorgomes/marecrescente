import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import 'rxjs/add/operator/map';
import { BaseService } from '../base.service';
import { HttpClient } from '../httpclient.service';
import {UrlParametro} from "../url.parametro";
import {ArrayUtil} from "../util/array.util";
import {Invoice} from "../classes/invoice";
import { ItemInvoice } from '../classes/item.invoice';
import { PromiseState } from 'q';
import { CampoMensagem } from '../campo.mensagem';
import { FileItem } from 'ng2-file-upload';

/**
 * Classe de Serviço utilizada para acessar os serviços REST do Sistema.
 *
 * @author Pizão
 */
@Injectable()
export class InvoiceService extends BaseService {

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
     * Lista todas as invoices
     * base do redome-invoice.
     * @return json com as invoices presentes na base.
     */
    listar(parametros: UrlParametro[] = null, pagina: number, quantidadeRegistros: number): Promise<any> {
      let separador: string = '?'
      let queryString: string = '';
      if (pagina != null) {
        queryString += separador + 'pagina=' + pagina;
        separador = '&'
      }
      if (quantidadeRegistros != null) {
        queryString += separador + 'totalRegistros=' + quantidadeRegistros;
        separador = '&'
      }

      if(ArrayUtil.isNotEmpty(parametros)){
        queryString += separador + super.toURL(parametros);
      }

        return this .http.get(`${environment.invoiceEndpoint}invoices${queryString}`)
                    .then(this.extrairDado).catch(this.tratarErro);
    }

   obterPorId(id: number): Promise<Invoice> {
      return this.http.get(`${environment.invoiceEndpoint}invoices/${id}`)
         .then(this.extrairDado).catch(this.tratarErro);
   }

   salvar(invoice: Invoice): Promise<Invoice> {
      return this.http.post(`${environment.invoiceEndpoint}invoices/`, JSON.stringify(invoice))
         .then(this.extrairDado).catch(this.tratarErro);
   }

   atualizar(invoice: Invoice): Promise<Invoice> {
      return this.http.put(`${environment.invoiceEndpoint}invoices/${invoice.id}`, JSON.stringify(invoice))
         .then(this.extrairDado).catch(this.tratarErro);
   }

   excluirItem(id:number):Promise<any>{
      return this.http.delete(`${environment.invoiceEndpoint}invoices/item/${id}`)
      .then(this.extrairDado).catch(this.tratarErro);
   }


   listarItens(parametros: UrlParametro[] = null, pagina: number, quantidadeRegistros: number):Promise<any>{
    let separador: string = '?'
    let queryString: string = '';
    if (pagina != null) {
      queryString += separador + 'pagina=' + pagina;
      separador = '&'
    }
    if (quantidadeRegistros != null) {
      queryString += separador + 'totalRegistros=' + quantidadeRegistros;
      separador = '&'
    }

    if(ArrayUtil.isNotEmpty(parametros)){
      queryString += separador + super.toURL(parametros);
    }

      return this .http.get(`${environment.invoiceEndpoint}invoices/item${queryString}`)
                  .then(this.extrairDado).catch(this.tratarErro);
   }


   listarItensConciliados(idInvoice:number, pagina: number, quantidadeRegistros: number):Promise<any>{
     let separador: string = '?'
     let queryString: string = '';
     if (pagina != null) {
       queryString += separador + 'pagina=' + pagina;
       separador = '&'
     }
     if (quantidadeRegistros != null) {
       queryString += separador + 'totalRegistros=' + quantidadeRegistros;
       separador = '&'
     }
     return this.http.get(`${environment.invoiceEndpoint}invoices/item/${idInvoice}/conciliados${queryString}`)
       .then(this.extrairDado).catch(this.tratarErro);
   }
   
   
   salvarItem(idInvoice: number, itemInvoice:ItemInvoice):Promise<any>{
     return this.http.put(`${environment.invoiceEndpoint}invoices/${idInvoice}/item/`, JSON.stringify(itemInvoice))
     .then(this.extrairDado).catch(this.tratarErro);
   }

   enviarArquivo(idInvoice:number, arquivos:Map<string, FileItem>):Promise<any>{

    let data: FormData = new FormData();    

    if (arquivos) {
        arquivos.forEach((item: FileItem, key: string) => {
            data.append("file", item._file, item.file.name);
        });
    }

     return this.http.fileUpload(`${environment.invoiceEndpoint}invoices/${idInvoice}/importar/`,data)
     .then(this.extrairDado).catch(this.tratarErro);
   }
 
}
