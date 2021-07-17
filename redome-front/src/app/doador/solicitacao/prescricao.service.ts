import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { FileItem } from 'ng2-file-upload';
import { BaseService } from '../../shared/base.service';
import { CriarPrescricaoCordaoDTO } from "../../shared/dto/criar.prescricao.cordao.dto";
import { CriarPrescricaoMedulaDTO } from "../../shared/dto/criar.prescricao.medula.dto";
import { HttpClient } from '../../shared/httpclient.service';

/**
 * Construção das chamadas ao serviços no back-end direcionados, por URL,
 * ao controlador de Prescrição.
 *
 * @author Pizão
 */
@Injectable()
export class PrescricaoService extends BaseService {

    constructor(protected http: HttpClient) {
        super(http);
    }

    /**
     * Salva a prescrição para medula ou cordão.
     *
     * @param dto
     * @param arquivoJustificativa
     * @param arquivos
     */
    salvarPrescricaoMedula(dto: CriarPrescricaoMedulaDTO,  arquivoJustificativa?: FileItem,  arquivos?: FileItem[]): Promise<any> {

        let data: FormData = new FormData();
        data.append("prescricaoMedulaDTO", new Blob([JSON.stringify(dto)], {
            type: 'application/json'
        }), '');

        if (arquivos) {
            arquivos.forEach(item => {
                data.append("file", item._file, item.file.name);
            });
        }

        if (arquivoJustificativa) {
            data.append("filejustificativa", arquivoJustificativa._file, arquivoJustificativa.file.name);
        }

        return this.http.fileUpload(environment.workupEndpoint + "prescricoes/medula", data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Salva a prescrição para medula ou cordão.
     *
     * @param dto
     * @param arquivoJustificativa
     * @param arquivos
     */
    salvarPrescricaoCordao(dto: CriarPrescricaoCordaoDTO,  arquivoJustificativa?: FileItem,  arquivos?: FileItem[]): Promise<any> {

        let data: FormData = new FormData();
        data.append("prescricaoCordaoDTO", new Blob([JSON.stringify(dto)], {
            type: 'application/json'
        }), '');

        if (arquivos) {
            arquivos.forEach(item => {
                data.append("file", item._file, item.file.name);
            });
        }

        if (arquivoJustificativa) {
            data.append("filejustificativa", arquivoJustificativa._file, arquivoJustificativa.file.name);
        }

        return this.http.fileUpload(environment.workupEndpoint + "prescricoes/cordao", data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public listarPrescricoesSemAutorizacaoPaciente(idCentroTransplante: number, atribuidoAMin: Boolean, pagina, quantidadeRegistros: number): Promise<any> {
        return this.http.get(`${environment.workupEndpoint}prescricoes/autorizacaopaciente?idCentroTransplante=${idCentroTransplante}&atribuidoAMin=${atribuidoAMin}&pagina=${pagina}&quantidadeRegistros=${quantidadeRegistros}`)
            .then(super.extrairDado)
            .catch(super.tratarErro);
    }

    public uploadArquivoAutorizacaoPaciente(idPrescricao: number, arquivo: FileItem): Promise<any> {
        let data: FormData = new FormData();
        data.append("file", arquivo._file, arquivo.file.name);

        return this.http.fileUpload(`${environment.workupEndpoint}prescricao/${idPrescricao}/autorizacaopaciente` , data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public listarPrescricoesDisponiveis(idCentroTransplante, pagina, quantidadeRegistros: number): Promise<any> {
        return this.http.get(environment.workupEndpoint + "prescricoes/disponiveis" +
                            "?idCentroTransplante=" + idCentroTransplante + "&pagina=" + pagina + "&quantidadeRegistros=" + quantidadeRegistros)
            .then(super.extrairDado)
            .catch(super.tratarErro);
    }

    public listarPrescricoesPorStatusECentroTransplante(status: number[], idCentroTransplante: number, pagina: number, quantidadeRegistros: number ) {
        let parametros: string = "";

        if (idCentroTransplante) {
            parametros = "?idCentroTransplante=" + idCentroTransplante
        }

        if (status && status.length) {
            parametros += (parametros == "" ? '?' : '&') + "status=" + status
        }

        parametros += (parametros == "" ? '?' : '&') +'pagina='
            + pagina + '&quantidadeRegistros=' + quantidadeRegistros;
        return this.http.get(environment.workupEndpoint + 'prescricoes' + parametros)
            .then(this.extrairDado)
            .catch(this.tratarErro);
    }

   public obterPrescricaoPorId(idPrescricao: number): Promise<any> {
       return this .http.get(`${environment.endpoint}prescricoes/${idPrescricao}`)
          .then(this.extrairDado).catch(this.tratarErro);
   }

   public obterPrescricaoMedulaComEvolucaoPorId(idPrescricao: number): Promise<any> {
    return this .http.get(environment.workupEndpoint + 'prescricaomedula/' + idPrescricao)
       .then(this.extrairDado)
       .catch(this.tratarErro);
    }

    public obterPrescricaoCordaoPorId(idPrescricao: number): Promise<any> {
        return this .http.get(environment.workupEndpoint + 'prescricaocordao/' + idPrescricao)
           .then(this.extrairDado)
           .catch(this.tratarErro);
    }

    public obterPrescricaoComEvolucaoPorIdPrecricao(idPrescricao: number): Promise<any> {
        return this .http.get(environment.workupEndpoint + 'prescricaoevolucao/' + idPrescricao)
           .then(this.extrairDado)
           .catch(this.tratarErro);
    }

}
