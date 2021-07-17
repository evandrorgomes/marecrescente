import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { BaseService } from '../../../../shared/base.service';
import { HttpClient } from '../../../../shared/httpclient.service';
import { AvaliacaoPrescricaoDTO } from './avaliacao.prescricao.dto';

/**
 * Registra as chamadas ao back-end envolvendo a entidade AvaliacaoPrescricao Service.
 *
 * @author Fillipe Queiroz
 */
@Injectable()
export class AvaliarPrescricaoService extends BaseService {
    address: string = "avaliacoesprescricao";
    addressArquivos: string = "arquivosprescricao"

    constructor(protected http: HttpClient) {
        super(http);
    }

    aprovarAvaliacao(idAvaliacao: number, idFonteCelulaDescartada: number, justificativaDescarteFonteCelula:string): Promise<any> {
        let avaliacaoPrescricaoDTO:AvaliacaoPrescricaoDTO = new AvaliacaoPrescricaoDTO();
        avaliacaoPrescricaoDTO.idFonteCelulaDescartada = idFonteCelulaDescartada;
        avaliacaoPrescricaoDTO.justificativaDescarteFonteCelula = justificativaDescarteFonteCelula;
        let data = JSON.stringify(avaliacaoPrescricaoDTO);
        return this.http.post(`${environment.workupEndpoint}avaliacaoprescricao/${idAvaliacao}/aprovar`, data)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    reprovarAvaliacao(idAvaliacao: number,  justificativa:string): Promise<any> {
        return this.http.post(`${environment.workupEndpoint}avaliacaoprescricao/${idAvaliacao}/reprovar`, justificativa)
            .then(this.extrairDado).catch(this.tratarErro);
    }
    /**
     *
     * Baixa o arquivo da prescrição.
     * @param  {number} id
     * @param  {string} nomeArquivo
     * @returns void
     */
    public baixarArquivoPrescricao(id: Number, nomeArquivo: String): void {
        super.download(this.addressArquivos + "/" + id + "/download",
            false, "" + nomeArquivo);
    }

    public listarAvaliacoesPendentes(pagina: number, quantidadeRegistro: number): Promise<any>{
        return this.http.get(`${environment.workupEndpoint}avaliacoesprescricao?pagina=${pagina}&quantidadeRegistros=${quantidadeRegistro}`)
                        .then(super.extrairDado).catch(super.tratarErro);
    }

        /**
     * Método para obter uma avaliacao de prescricao id.
     * @param  {number} idAvaliacaoPrescricao
     * @returns Promise
     */
    obterAvaliacaoPrescricaoPorId(idAvaliacaoPrescricao: number): Promise<any> {
        return this.http.get(environment.workupEndpoint +
            this.address + '/' + idAvaliacaoPrescricao)
            .then(this.extrairDado).catch(this.tratarErro);
    }

}
