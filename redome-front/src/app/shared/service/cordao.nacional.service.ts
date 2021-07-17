import { Injectable } from "@angular/core";
import { BaseService } from "../base.service";
import { HttpClient } from "../httpclient.service";

@Injectable()
export class CordaoNacionalService extends BaseService {

   constructor(protected http: HttpClient) {
      super(http);
   }



}
