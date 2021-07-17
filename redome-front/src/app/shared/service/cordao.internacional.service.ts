import {BaseService} from "../base.service";
import {HttpClient} from "../httpclient.service";
import {Injectable} from "@angular/core";
import {environment} from "../../../environments/environment";
import {DoadorCordaoInternacionalDto} from "../dto/doador.cordao.internacional.dto";
import {PedidoDto} from "../dto/pedido.dto";
import { OrigemPagamentoDoadorDTO } from "../dto/origem.pagamento.doador.dto";

@Injectable()
export class CordaoInternacionalService extends BaseService {

   constructor(protected http: HttpClient) {
      super(http);
   }

   /**
    * Salva o doador internacional na base do Redome.
    *
    * @param doador doador com todos os dados que serão salvos.
    * @param rmr Identificador do paciente que irá terá o doador reservado.
    */
   public salvarCordaoInternacional(doador: DoadorCordaoInternacionalDto, pedido: PedidoDto): Promise<any>{
      let data: FormData = new FormData();
      data.append("doadorCordaoInternacional", new Blob([JSON.stringify(doador)], {
         type: 'application/json'
      }), '');
      if (pedido) {
         data.append("pedido", new Blob([JSON.stringify(pedido)], {
            type: 'application/json'
         }), '');
      }

      return this.http.fileUpload(environment.endpoint + "cordoesinternacionais", data)
         .then(this.extrairDado).catch(this.tratarErro);

   }

   public alterarRegistroPagamentoDoador(origemPagamentoDoadorDTO:OrigemPagamentoDoadorDTO){
      let body = JSON.stringify(origemPagamentoDoadorDTO);
      return this.http.put(environment.endpoint + "cordoesinternacionais/dadospagamento/internacional", body)
            .then(this.extrairDado).catch(this.tratarErro);
  }

}
