import { NgModule } from '@angular/core';
import { Http, RequestOptions } from '@angular/http';
import { AuthHttp, AuthConfig } from 'angular2-jwt';
import { AutenticacaoService } from "./autenticacao.service";

export function authHttpServiceFactory(http: Http, options: RequestOptions) {
    return new AuthHttp(new AuthConfig({
        tokenName: 'token',
        tokenGetter: (() => 
            localStorage.getItem('token')
        ),
		    globalHeaders: [{'Content-Type':'application/json;charset=utf-8'}],
	  }), http, options);
}

@NgModule({
  providers: [
    {
      provide: AuthHttp,
      useFactory: authHttpServiceFactory,
      deps: [Http, RequestOptions]
    },
    AutenticacaoService
  ]
})
export class AutenticacaoModule {


}