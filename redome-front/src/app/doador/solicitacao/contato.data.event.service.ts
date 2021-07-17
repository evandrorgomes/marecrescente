import { Injectable } from "@angular/core";
import { ContatoDataEvent } from "./contato.data.event";

@Injectable()
export class ContatoDataEventService {

    private _contatoDataEvent: ContatoDataEvent = new ContatoDataEvent();

    constructor() {}

    public get contatoDataEvent(): ContatoDataEvent {
        return this._contatoDataEvent;
    }

    public criarContatoDataEvent(): void {
        this._contatoDataEvent = new ContatoDataEvent();
    }


}