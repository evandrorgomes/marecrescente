import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { BaseService } from '../../shared/base.service';
import { HttpClient } from '../../shared/httpclient.service';
import { TarefaService } from '../../shared/tarefa.service';
import { RelatorioDTO } from './relatorio.dto';

/**
 * Registra as chamadas ao back-end envolvendo a entidade PedidoColeta e tarefas relacionadas.
 * 
 * @author Fillipe Queiroz    
 * @class RelatorioService
 * @extends {BaseService}
 */
@Injectable()
export class RelatorioService extends BaseService {

    address: string = "relatorios";

    constructor(protected http: HttpClient, private tarefaService: TarefaService) {
        super(http);
    }

    incluir(relatorioDTO: RelatorioDTO): Promise<any> {
        let data = JSON.stringify(relatorioDTO);

        return this.http.post(environment.endpoint + this.address + '/salvar', data)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    listar():Promise<any>{
        return this.http.get(environment.endpoint + this.address + '/listar')
        .catch(this.tratarErro)
        .then(this.extrairDado);
    }

    listarParametrosRelatorios():Promise<any>{
        return this.http.get(environment.endpoint + this.address + '/listar/parametros')
        .catch(this.tratarErro)
        .then(this.extrairDado);
    }

    public downloadRelatorio(codigo: string):void{
        super.download(this.address + "/" + codigo + "/download", false, "arquivo.pdf");
    }

    listarConstantes():Promise<any>{
        return this.http.get(environment.endpoint + this.address + '/listar/constantes')
        .catch(this.tratarErro)
        .then(this.extrairDado);
    }

    obterValorConstante(codigo: string):Promise<any>{
        return this.http.get(environment.endpoint + this.address + '/constantes/' + codigo)
        .catch(this.tratarErro)
        .then(this.extrairDado);
    }

    atualizarValorConstante(codigo, valor: string):Promise<any>{
        return this.http.post(environment.endpoint + this.address + '/constantes/' + codigo, valor)
        .catch(this.tratarErro)
        .then(this.extrairDado);
    }

}