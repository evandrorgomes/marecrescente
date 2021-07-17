import { Injectable } from '@angular/core';
import { ExamePaciente } from './exame.paciente';
import { BaseService } from '../../../shared/base.service';
import { ErroMensagem } from '../../../shared/erromensagem';
import { UrlParametro } from '../../../shared/url.parametro';
import { environment } from 'environments/environment';
import { HttpClient } from '../../../shared/httpclient.service';
import { Pendencia } from '../../avaliacao/pendencia';
import { MessageBox } from '../../../shared/modal/message.box';

/**
 * Classe utilitária que realiza as solicitações ajax no servidor REST
 * @author Rafael Pizão
 */
@Injectable()
export class ExameService extends BaseService {
    public URL: string = "exames";
    private URL_HLA: string = this.URL + "/hla";
    private URL_HLA_COM_ANTIGENO: string = this.URL + "/hla/comantigeno";
    private URL_EXAMES_SALVAR: string = this.URL + "/salvar";
    private RECURSO_DOWNLOAD_ZIPADO: string = "download";
    private RESPONDER_PENDENCIA_COM_EXAME = this.URL + "/respostas";
    private OBTER_EXAME_PRIORITARIO = this.URL + "/pendentes/prioritario";

    constructor(http: HttpClient, messageBox: MessageBox) {
        super(http, messageBox);
    }

    validarHLAPorLocus(codigo: string, valor: string, sucesso: (result) => void,
        falha: (error: ErroMensagem) => void) {
        super.get(this.URL_HLA, sucesso, falha, [new UrlParametro("codigo", codigo), new UrlParametro("valor", valor)]);
    }

    validarHLAPorLocusComAntigeno(codigo: string, valor: string, sucesso: (result) => void,
        falha: (error: ErroMensagem) => void) {
        super.get(this.URL_HLA_COM_ANTIGENO, sucesso, falha, [new UrlParametro("codigo", codigo), new UrlParametro("valor", valor)]);
    }

    /**
     * Busca exame a partir do ID informado.
     *
     * @param id ID do exame a ser pesquisado.
     */
    public obterExame(id: number): Promise<any> {
        return this.http.get(environment.endpoint + this.URL + "/" + id)
            .then(super.extrairDado).catch(super.tratarErro);
    }


    /**
     * Busca exame a partir do ID informado.
     *
     * @param id ID do exame a ser pesquisado.
     */
    public obterExameDoador(id: number): Promise<any> {
        return this.http.get(environment.endpoint + this.URL + "/doador/" + id)
            .then(super.extrairDado).catch(super.tratarErro);
    }


    /**
     * Realiza o download de todos os arquivos zipados associado ao ID do arquivo exame informado.
     *
     * @param id ID do exame a ser pesquisado.
     */
    public baixarArquivosExamesZipados(exameId: Number, nomeArquivo: String): void {
        super.download(this.URL + "/" +
            exameId + "/" + this.RECURSO_DOWNLOAD_ZIPADO,
            false, nomeArquivo + ".zip");

    }

    /**
     * Método para persistir um novo exame.
     *
     * @param {FormData} data
     * @param {Exame} exame
     * @returns {Promise<any>}
     * @memberof ExameService
     */
    public salvar(data: FormData, exame: ExamePaciente): Promise<any> {
        data.append("exame", new Blob([JSON.stringify(exame)], {
            type: 'application/json'
        }), '');


        return this.http.fileUpload(environment.endpoint + this.URL_EXAMES_SALVAR, data)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Lista os exames para serem conferidos de forma paginada.
     * @param RMR do paciente.
     * @param nomePaciente a ser consultado.
     * @param pagina pagina em questão para ser paginada.
     * @param quantidadeRegistro quantidade de registros por página.
     */
    public listarTarefasExamePendentesPaginadas(RMR: number, nomePaciente: String, pagina: number, quantidadeRegistro: number): Promise<any>{
        let url: string = environment.endpoint + this.URL + "/pendentes/tarefas";
        return this .http.get(url + "?rmr="+ RMR + "&nomePaciente=" + nomePaciente +"&pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistro)
                    .then(this.extrairDado).catch(this.tratarErro);
    }

    salvarRespondendoPendencia(data: FormData, exame: ExamePaciente, pendencias: Pendencia[], resposta: string, respondePendencia: boolean): Promise<any> {
        data.append("exame", new Blob([JSON.stringify(exame)], {
            type: 'application/json'
        }), '');

        data.append("pendencias", new Blob([JSON.stringify(pendencias)], {
            type: 'application/json'
        }), '');

        data.append("resposta", new Blob([JSON.stringify(resposta)], {
            type: 'application/json'
        }), '');

        data.append("respondePendencia", new Blob([JSON.stringify(respondePendencia)], {
            type: 'application/json'
        }), '');

        return this.http.fileUpload(environment.endpoint + this.RESPONDER_PENDENCIA_COM_EXAME, data)
            .then(super.extrairDado).catch(super.tratarErro);

    }

    /**
     * Busca exame prioritario a ser conferido pelo redome
     *
     * @param id ID do exame a ser pesquisado.
     */
    public obterExamePrioritario(): Promise<any> {
        return this.http.get(environment.endpoint + this.OBTER_EXAME_PRIORITARIO)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Atualiza um exame como aceito
     *
     * @param {number} id
     * @param {Exame} exame
     * @returns {Promise<any>}
     * @memberof ExameService
     */
    public aceitarExame(id: number, exame: ExamePaciente): Promise<any> {
        let data = JSON.stringify(exame);
        return this.http.put(environment.endpoint + this.URL + "/" + id + "/aceito", data)
            .then(super.extrairDado).catch(super.tratarErro);
    }

    /**
     * Realiza o descarte do exame com o motivo de descarte informado.
     *
     * @param exameId ID do exame a ser descartado.
     * @param motivoId ID do motivo do descarte.
     */
    public descartarExame(exameId: number, motivoId: number) {
        return this.http.put(environment.endpoint + 'exames/' + exameId + '/descartar', motivoId)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * Listar motivos possíveis para o descarte do exame.
     */
    public listarMotivosDescarte() {
        return this.http.get(environment.endpoint + 'exames/motivosdescarte')
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    public downloadExameClasse2c(exameId: number) {
        super.download( "exames/" + exameId + "/classe2/c/download", false, "pedido_exame_c.pdf");
    }

    public downloadExameClasse2DQB1(exameId: number) {
        super.download( "exames/" + exameId + "/classe2/dqb1/download", false, "pedido_exame_dqb1_drb1.pdf");
    }
}
