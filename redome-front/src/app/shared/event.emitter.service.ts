import { EventEmitter } from '@angular/core';

/**
 * Classe estática para emissão de eventos globais
 * 
 * 
 * @author Fillipe Queiroz e Bruno Sousa
 * @export
 * @class EventEmitterService
 */
export class EventEmitterService {

    private static emitters: {
        [nomeEvento: string]: EventEmitter<any>
    } = {}

    /**
     * 
     * 
     * @static
     * @param {string} nomeEvento 
     * @returns {EventEmitter<any>} 
     * 
     * @memberOf EventEmitterService
     */
    static get (nomeEvento:string): EventEmitter<any> {
        if (!this.emitters[nomeEvento])
            this.emitters[nomeEvento] = new EventEmitter<any>();
        return this.emitters[nomeEvento];
    }

    public static remove(nomeEvento: string): void{
        if (this.emitters[nomeEvento]){
            this.emitters[nomeEvento].unsubscribe();
            delete this.emitters[nomeEvento];
        }
    }

}