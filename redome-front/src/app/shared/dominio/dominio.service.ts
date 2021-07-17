import { EstadoCivil } from 'app/shared/dominio/estadoCivil';
import { HttpClientNoAutentication } from 'app/shared/httpclient.noautentication.service';
import { FonteCelula } from './fonte.celula';
import { environment } from 'environments/environment';
import { BaseService } from '../base.service';
import { CodigoInternacional } from './codigo.internacional';
import { Observable } from 'rxjs';
import { TipoTransplante } from './tipoTransplante';
import { Locus } from '../../paciente/cadastro/exame/locus';
import { Metodologia } from '../../paciente/cadastro/exame/metodologia';
import { Motivo } from './motivo';
import { CondicaoPaciente } from './condicaoPaciente';
import { Cid } from './cid';
import { EstagioDoenca } from './estagio.doenca';
import { HttpClient } from '../httpclient.service';
import { Response } from '@angular/http';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import { Injectable } from '@angular/core';
import { UF } from './uf';
import { Pais } from './pais';
import { Raca } from './raca';
import { Etnia } from './etnia';
import { Configuracao } from './configuracao';
import { ErroMensagem } from '../erromensagem';
import { CentroTransplante } from './centro.transplante';
import { FuncaoCentroTransplante } from '../enums/funcao.centro.transplante';
import { FontesCelulas } from '../enums/fontes.celulas';

/**
 * Classe de Serviço utilizada para acessar os serviços REST de busca dominios
 * @author Fillipe Queiroz
 */
@Injectable()
export class DominioService extends BaseService{
    addressLocus: string = environment.endpoint + "locus";
    addressUF: string = environment.publicEndpoint + "uf";
    addressPais: string = environment.publicEndpoint + "pais";
    addressRaca: string = environment.endpoint + "raca";
    addressEtnia: string = environment.endpoint + "etnia";
    addressCid: string = environment.endpoint + "cid";
    addressCondicaoPaciente: string = environment.endpoint + "condicaopaciente";
    addressMotivo: string = environment.endpoint + "motivos";
    addressMetodologia: string = environment.endpoint + "metodologia";
    addressCentroAvaliador: string = environment.publicEndpoint + "centrosTransplante";
    addressTipoTransplante: string = environment.endpoint + "tipoTransplante";
    addressCodigoInternacional: string = environment.publicEndpoint + "pais/codigoInternacional";
    addressFonteCelula: string = environment.workupEndpoint + "fontescelulas";
    addressRegistros: string = environment.endpoint + "registros";
    addressEstadoCivil: string = environment.endpoint + "estadoCivil";

    private ufPromise: Promise<UF[]>;
    private ufSemNaoInformadoPromise: Promise<UF[]>;
    private paisPromise: Promise<Pais[]>;


    /**
     * Método construtor
     * @param HttpClient classe utilitária necessária para realizar o acesso rest que é
     * injetada e atribuída como atributo
     * @author Fillipe Queiroz
     */
    constructor(http: HttpClient, private httpNoAuthentication: HttpClientNoAutentication) {
       super(http);
    }
    /**
     * Método que busca todas as UF's
     * @return Promise<UF[]> retorno com um array de uf's
     * @author Fillipe Queiroz
     */
    getUfs(): Promise<UF[]> {
        if (!this.ufPromise ) {
            this.ufPromise = this.httpNoAuthentication.get(this.addressUF)
                .catch(this.tratarErro)
                .then(this.extrairDado)
        }
        return this.ufPromise;
    }

    /**
     * Método que busca todos os Paises
     * @return Promise<Pais[]> retorno com um array de paises
     * @author Fillipe Queiroz
     */
    getPaises(): Promise<Pais[]> {
        if (!this.paisPromise ) {
            this.paisPromise = this.httpNoAuthentication.get(this.addressPais)
                .catch(this.tratarErro)
                .then(this.extrairDado);
        }
        return this.paisPromise;
    }

    /**
     * Método que busca todas as raças
     * @return Promise<Raca[]> retorno com um array de raças
     * @author Fillipe Queiroz
     */
    getRacas(): Promise<Raca[]> {
        return this.http.get(this.addressRaca)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

   /**
     * Método que busca todos os estado civis
     * @return Promise<EstadoCivil[]> retorno com um array de estado civis
     * @author ergomes
     */
    getEstadosCivis(): Promise<EstadoCivil[]> {
        return this.http.get(this.addressEstadoCivil)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * Método que busca todas as etnias
     * @return Promise<Etnia[]> retorno com um array de etnias
     * @author Fillipe Queiroz
     */
    getEtnias(): Promise<Etnia[]> {
        return this.http.get(this.addressEtnia)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * Método que busca todas as etnias
     * @return Promise<Etnia[]> retorno com um array de etnias
     * @author Bruno Sousa
     */
    getCids(query: string): Promise<Cid[]> {
        return this.http.get(this.addressCid + "?q=" + query)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * Método que estagios de doença por CID
     * @return Promise<EstagioDoenca[]> retorno com um array de estagios de doença
     * @author Filipe Paes
     */
    getEstagiosDoencaPor(idCid): Promise<EstagioDoenca[]> {
        return this.http.get(this.addressCid + "/" + idCid + "/estagios" )
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * Método que busca as possíveis condições do paciente
     * @return Promise<CondicaoPaciente[]> retorno com um array de condições
     * @author Fillipe Queiroz
     */
    getCondicoesPaciente(): Promise<CondicaoPaciente[]> {
        return this.http.get(this.addressCondicaoPaciente)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * Método que busca os motivos do cadastro de evolução do paciente
     * @return Promise<Motivo[]> retorno com um array de motivos
     * @author Fillipe Queiroz
     */
    getMotivos(): Promise<Motivo[]> {
        return this.http.get(this.addressMotivo)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * Método que busca as metodologias do cadastro de exame do paciente
     * @return Promise<Metodologia[]> retorno com um array de metodologias
     * @author Filipe Paes
     */
    listarMetodologias(): Promise<Metodologia[]> {
        return this.http.get(this.addressMetodologia)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

     /**
     * Método que busca os locus do cadastro de exame do paciente
     * @return Promise<Locus[]> retorno com um array de motivos
     * @author Filipe Paes
     */
    listarLocus(): Promise<Locus[]> {
        return this.http.get(this.addressLocus)
            .catch(this.tratarErro)
            .then(this.extrairDado);
    }

    /**
     * Método que busca a lista chave/valor, representando uma configuração
     * de alguma funcionalidade parametrizável do sistema.
     * Ex.: {extensoes_laudo: .doc, .pdf, .png; limite_upload_laudo: 5MB}
     *
     * @return Promise<Configuracao[]> retorno com um array de configuração
     * @author Pizão
     */
    listarConfiguracoes(lista: String[]): Promise<Configuracao[]> {
        return this .httpNoAuthentication.get(
            environment.publicEndpoint + "configuracao?lista=" + this.formatarListaParaParametroURL(lista))
                .catch(this.tratarErro)
                .then(this.extrairDado);
    }

    /**
     * Método que busca a configuração (combinação chave/valor) a partir da chave informada,
     * o que representando uma configuração (ou parte dela) de alguma funcionalidade
     * parametrizável do sistema. Ex.: limite_upload_laudo: 5MB
     *
     * @return Promise<Configuracao> retorna uma configuração do sistema
     * @author Pizão
     */
    obterConfiguracao(chave: String): Promise<Configuracao> {
        return this .http.get(
            environment.endpoint + "configuracao/" + chave)
                .catch(this.tratarErro)
                .then(this.extrairDado);
    }

    /**
     * Método que busca toda a lista de centro avaliador.
     *
     * @return Promise<CentroAvaliador[]> retorno com um array de centro avaliador
     * @author Pizão
     */
    listarCentroAvaliador(): Promise<CentroTransplante[]> {
        return this .http.get(
            this.addressCentroAvaliador+"?pagina=0&quantidadeRegistros=999&idFuncaoCentroTransplante="+FuncaoCentroTransplante.AVALIADOR)
                .catch(this.tratarErro)
                .then(this.extrairDadoContent);
    }

    /**
     * Método que busca toda a lista de centro avaliador.
     *
     * @return Promise<CentroAvaliador[]> retorno com um array de centro avaliador
     * @author Pizão
     */
    listarCentroTransplante(): Promise<CentroTransplante[]> {
        return this .http.get(
            this.addressCentroAvaliador+"?pagina=0&quantidadeRegistros=999&idFuncaoCentroTransplante="+FuncaoCentroTransplante.TRANSPLANTADOR)
                .catch(this.tratarErro)
                .then(this.extrairDadoContent);
    }

    /**
     * Método que busca toda a lista de centro avaliador.
     *
     * @return Promise<CentroAvaliador[]> retorno com um array de centro avaliador
     * @author Pizão
     */
    listarCentroColeta(): Promise<CentroTransplante[]> {
        return this .http.get(
            this.addressCentroAvaliador+"?pagina=0&quantidadeRegistros=9999999&idFuncaoCentroTransplante="+FuncaoCentroTransplante.COLETA)
                .catch(this.tratarErro)
                .then(this.extrairDadoContent);
    }

     /**
     * Método que busca toda a lista de tipo de transplante.
     *
     * @return Promise<TipoTransplante[]> retorno com um array de tipo de transplante
     * @author bruno.sousa
     */
    listarTipoTransplante(): Promise<TipoTransplante[]> {
        return this .http.get(
            this.addressTipoTransplante)
                .catch(this.tratarErro)
                .then(this.extrairDado);
    }

     /**
     * Método que busca toda a lista de código internacional de pais o telefone.
     *
     * @return Promise<TipoTransplante[]> retorno com um array de tipo de transplante
     * @author bruno.sousa
     */
    listarCodigoInternacional(): Promise<CodigoInternacional[]> {
        return this .httpNoAuthentication.get(
            this.addressCodigoInternacional)
                .catch(this.tratarErro)
                .then(this.extrairDado);
    }

    /**
     * Transforma de lista para parâmetro (String), utilizando o
     * formato esperado para uso nas URLs do sistema.
     *
     * @param lista
     * @return Retorna uma parâmetro string no formato esperado pela URL.
     * Ex.: ...?clienteId=1&2&3&4
     */
    private formatarListaParaParametroURL(lista: String[]): String{
        if(lista == null || lista.length == 0){
            return null;
        }

        let parametroString:String = "";
        let delimitador = "";
        lista.forEach(item => {
            parametroString += delimitador + item;
            delimitador = ",";
        });


        return parametroString;
    }

    /**
     * Método que busca todas as fontes de celulas.
     *
     * @return Promise<FonteCelula[]> retorno com um array de fonte de celula
     * @author bruno.sousa
     */
    listarFonteCelula(siglas?: Array<FontesCelulas>): Promise<any> {
        let params: string = "";
        if (siglas && siglas.length != 0) {
            params = "?siglas=" + siglas.map(fonteCelula => fonteCelula.sigla).join(",");
        }
        return this .http.get(
            this.addressFonteCelula + params)
                .catch(this.tratarErro)
                .then(this.extrairDado);
    }

    public listarRegistros(): Promise<any> {
        return this .http.get(this.addressRegistros)
                .catch(this.tratarErro)
                .then(this.extrairDado);
    }

    public listarRegistrosInternacionais(): Promise<any> {
        return this .http.get(this.addressRegistros + "/internacional")
                .catch(this.tratarErro)
                .then(this.extrairDado);
    }

       /**
     * Lista todos os municipios contidos na base do Redome.
     * @return json com os municipios presentes na base.
     */
    public listarMunicipiosPorUf(uf: string): Promise<any> {
        return this.httpNoAuthentication.get(this.addressUF + "/" + uf + "/municipios")
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Método que busca todas as UF's
     * @return Promise<UF[]> retorno com um array de uf's
     * @author Fillipe Queiroz
     */
    getUfsSemNaoInformado(): Promise<UF[]> {
        if (!this.ufSemNaoInformadoPromise ) {
            this.ufSemNaoInformadoPromise = this.httpNoAuthentication.get(this.addressUF + '/semnaoinformado' )
                .catch(this.tratarErro)
                .then(this.extrairDado)
        }
        return this.ufSemNaoInformadoPromise;
    }

}
