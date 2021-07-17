import {Injectable} from "@angular/core";
import {BaseService} from "../base.service";
import {HttpClient} from "../httpclient.service";
import {environment} from "../../../environments/environment";

@Injectable()
export class DistribuicaoWorkupService extends BaseService {

   constructor(protected http: HttpClient) {
      super(http);
   }

   public listarTarefasDistribuicoesWorkup(pagina, quantidadeRegistros: number): Promise<any> {
      return this.http.get(`${environment.workupEndpoint}distribuicoesworkup?pagina=${pagina}&quantidadeRegistros=${quantidadeRegistros}`)
         .catch(this.tratarErro)
         .then(this.extrairDado);
   }

   public listarDistribuicoesWorkupPorUsuario(): Promise<any> {
      return this.http.get(`${environment.workupEndpoint}distribuicoesworkup/usuarios`)
         .catch(this.tratarErro)
         .then(this.extrairDado);
   }

   public atribuirDistribuicaoWorkup(idDistribuicao: number, idUsuario): Promise<any> {
      return this.http.post(`${environment.workupEndpoint}distribuicaoworkup/${idDistribuicao}`, idUsuario)
         .catch(this.tratarErro)
         .then(this.extrairDado);
   }

   public reatribuirDistribuicaoWorkup(idDistribuicao: number, idUsuario): Promise<any> {
      return this.http.put(`${environment.workupEndpoint}distribuicaoworkup/${idDistribuicao}`, idUsuario)
         .catch(this.tratarErro)
         .then(this.extrairDado);
   }

}