import { Injectable } from "@angular/core";
import { BaseService } from "app/shared/base.service";
import { ContatoTelefonicoMedico } from 'app/shared/classes/contato.telefonico.medico';
import { EmailContatoMedico } from 'app/shared/classes/email.contato.medico';
import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { Medico } from 'app/shared/dominio/medico';
import { HttpClient } from "app/shared/httpclient.service";
import { environment } from 'environments/environment';
import { FileItem } from 'ng2-file-upload';
import { PreCadastroMedico } from '../classes/precadastro.medico';
import { MessageBox } from '../modal/message.box';
import { UrlParametro } from '../url.parametro';
import { ArrayUtil } from '../util/array.util';
import { StatusPreCadastro } from './../enums/status.precadastro';
import { HttpClientNoAutentication } from './../httpclient.noautentication.service';

/**
 * Classe de serviço para interação com a tabela de medicos
 * 
 * @author Bruno Sousa
 * @export
 * @class MedicoService
 * @extends {BaseService}
 */
@Injectable()
export class MedicoService extends BaseService {

    /**
     * Método construtor
     * @param http classe utilitária necessária para realizar o acesso rest que é 
     * injetada e atribuída como atributo
     * @author Bruno Sousa
     */
    constructor(http: HttpClient, 
                private httpClientNoAutentication: HttpClientNoAutentication,
                messageBox: MessageBox) {
        super(http, messageBox);
    }

    validarCrmELogin(crm, login: string): Promise<any> {
        let parametros: string = "";
        let conjucao: string = "?"
        if (crm) {
            parametros += conjucao + "crm=" + crm;
            conjucao = "&"
        }

        if (crm) {
            parametros += conjucao + "login=" + login;
        }

        return this.httpClientNoAutentication.get(environment.publicEndpoint + "medicos/precadastros/validaridentificacao" + parametros).then(this.extrairDado).catch(this.tratarErro);
    }

    listarPreCadastroPorStatus(status: StatusPreCadastro, pagina: number, quantidadeRegistros: number) {
        return this.http.get(environment.endpoint + 'medicos/precadastros?status=' + StatusPreCadastro[status] +'&pagina='
            + (pagina == null? "":pagina) + '&quantidadeRegistros=' + (quantidadeRegistros == null?"":quantidadeRegistros))
            .then(this.extrairDado).catch(this.tratarErro);
    }

    obterPreCadastroPorId(idPreCadastroMedico: number) {
        return this.http.get(environment.endpoint + 'medicos/precadastros/' + idPreCadastroMedico)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Salva um pré cadastro solicitando autorização para acesso
     * ao Redome.
     * 
     * @param preCadastro pré cadastro a ser realizado.
     * @param arquivoCrm arquivo do CRM do médico que solicita o acesso. 
     */
    public salvarPreCadastro(preCadastro: PreCadastroMedico, arquivoCrm: FileItem): Promise<any>{
        let data: FormData = new FormData();
        data.append("file", arquivoCrm._file, arquivoCrm.file.name);
        data.append("preCadastroMedico", 
            new Blob([JSON.stringify(preCadastro)], { type: 'application/json' }), '');

        return this.httpClientNoAutentication.fileUpload(environment.publicEndpoint + "medicos/precadastros", data);
    }

    /**
     * Aprovar pré cadastro solicitando autorização para acesso
     * ao Redome.
     * 
     * @param idPreCadastro ID do pré cadastro a ser aprovado.
     */
    aprovarPreCadastro(idPreCadastroMedico: number): Promise<any>{
        return this.http.put(environment.endpoint + "medicos/precadastros/" + idPreCadastroMedico + "/aprovar")
            .then(this.extrairDado).catch(this.tratarErro);           
    }

    /**
     * Salva um pré cadastro solicitando autorização para acesso
     * ao Redome.
     * 
     * @param preCadastro pré cadastro a ser realizado.
     * @param arquivoCrm arquivo do CRM do médico que solicita o acesso. 
     */
    reprovarPreCadastro(idPreCadastroMedico: number, justificativa: string): Promise<any>{
        return this.http.put(environment.endpoint + "medicos/precadastros/" + idPreCadastroMedico + "/reprovar", justificativa)
            .then(this.extrairDado).catch(this.tratarErro);           
    }
    
    listarMedicos(parametros: UrlParametro[] = null, pagina: number, quantidadeRegistros: number) {


        let queryString: string = '';  
        let separador: string = '?';
        if (pagina != undefined && pagina != null) {
            queryString += separador  + 'pagina=' + pagina;
            separador = '&'
        }

        if (quantidadeRegistros != undefined && quantidadeRegistros != null) {
            queryString += separador  + 'quantidadeRegistros=' + quantidadeRegistros;
            separador = '&'
        }

        if(ArrayUtil.isNotEmpty(parametros)){
            queryString += separador + super.toURL(parametros);
            separador = '&'
        }

        return this.http.get(environment.endpoint + 'medicos' + queryString)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    /**
     * Obtém o arquivo de CRM para download.
     * @param idPreCadastroMedico ID do pré cadastro que armazena este arquivo.
     * @param nomeArquivo nome do arquivo sugerido para download.
     */
    baixarArquivoCRM(idPreCadastroMedico: number, nomeArquivo: string): void{
        super.download("medicos/precadastros/" + idPreCadastroMedico + "/download", false, nomeArquivo);
    }

    obterMedicoPorId(idMedico: number) {
        return this.http.get(environment.endpoint + 'medicos/' + idMedico)
            .then(this.extrairDado).catch(this.tratarErro);
    }

    atualizarDadosIdentificacao(idMedico: number, medico: Medico): Promise<any>{
        return this.http.put(environment.endpoint + "medicos/" + idMedico + "/dadosidentificacao", medico)
            .then(this.extrairDado).catch(this.tratarErro);           
    }

	/**
     * Obtém o arquivo de CRM do médico para download.
     * @param idMedico ID do médico que armazena este arquivo.
     * @param nomeArquivo nome do arquivo sugerido para download.
     */
    baixarMedicoArquivoCRM(idMedico: number, nomeArquivo: string): void{
        super.download("medicos/" + idMedico + "/download", false, nomeArquivo);
    }

    public adicionarEmail(idMedico: number, email: EmailContatoMedico): Promise<any>{
        let data = JSON.stringify(email);
        return this.http.put(environment.endpoint + "medicos/" + idMedico + "/email", data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }
    
	atualizarCentrosReferencia(idMedico: number, centrosReferencia: CentroTransplante[]): Promise<any>{
        return this.http.put(environment.endpoint + "medicos/" + idMedico + "/centrosreferencia", centrosReferencia)
            .then(this.extrairDado).catch(this.tratarErro);           
    }

    public adicionarTelefone(idMedico: number, telefone: ContatoTelefonicoMedico): Promise<any>{
        let data = JSON.stringify(telefone);
        return this.http.put(environment.endpoint + "medicos/" + idMedico + "/telefone", data)
                        .then(this.extrairDado).catch(this.tratarErro);
    }

    public obterMedicoAssociadoUsuarioLogado(): Promise<any> {
        return this.http.get(environment.endpoint + "medicos/logado" )
            .then(this.extrairDado).catch(this.tratarErro);
    }

}

