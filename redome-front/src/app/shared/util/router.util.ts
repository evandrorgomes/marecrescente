import { activatedRoute } from '../../export.mock.spec';
import { PacienteUtil } from "../paciente.util";
import { ActivatedRoute } from "@angular/router/src/router_state";
import { Subscriber } from "rxjs/Subscriber";

export class RouterUtil{

    public static recuperarParametrosDaActivatedRoute(activatedRoute:ActivatedRoute, nomeVariaves:string[]): Promise<any>{
        let promises: Promise<any>[] = [];
        nomeVariaves.forEach(nomeVariavel => {
            promises.push(this.recuperarJsonParametroDaActivatedRoute(activatedRoute, nomeVariavel));
        });

        return Promise.all(promises).then(function(values: any[]) {
            let retorno = {};
            nomeVariaves.forEach(nomeVariavel => {
                let val: any = values.find(value => value ? value[nomeVariavel] : false);
                if (val) {
                    retorno[nomeVariavel] = val[nomeVariavel];
                }
            });
            return new Promise((resolve, reject) => {
                resolve(retorno);
            });
        });


    }

    public static recuperarParametroDaActivatedRoute(activatedRoute:ActivatedRoute, nomeVariavel:string):Promise<any>{
        let  promiseParams:Promise<any> = activatedRoute.params.first().toPromise();
        let  promiseQueryMap:Promise<any> = activatedRoute.queryParams.first().toPromise();

        return  Promise.all([promiseParams,promiseQueryMap]).then(function(values) {
            if(values[0][nomeVariavel]){
                return new Promise((resolve, reject) => {
                    resolve(values[0][nomeVariavel]);
                });
            }else if(values[1][nomeVariavel]){
                return new Promise((resolve, reject) => {
                    resolve(values[1][nomeVariavel]);
                });
            }
         });
    }

    private static recuperarJsonParametroDaActivatedRoute(activatedRoute:ActivatedRoute, nomeVariavel:string):Promise<any>{
        let  promiseParams:Promise<any> = activatedRoute.params.first().toPromise();
        let  promiseQueryMap:Promise<any> = activatedRoute.queryParams.first().toPromise();

        return  Promise.all([promiseParams,promiseQueryMap]).then(function(values) {
            if(values[0][nomeVariavel]){
                let retorno = {}
                retorno[nomeVariavel] = values[0][nomeVariavel];
                return new Promise((resolve, reject) => {
                    resolve(retorno);
                });
            }else if(values[1][nomeVariavel]){
                let retorno = {}
                retorno[nomeVariavel] = values[1][nomeVariavel];
                return new Promise((resolve, reject) => {
                    resolve(retorno);
                });
            }
         });
    }
    
}