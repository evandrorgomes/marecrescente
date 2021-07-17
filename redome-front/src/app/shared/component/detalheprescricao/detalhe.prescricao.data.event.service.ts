import {Injectable} from "@angular/core";
import {Prescricao} from "../../../doador/consulta/workup/prescricao";
import {DetalhePrescircaoDataEvent} from "./detalhe.prescircao.data.event";

@Injectable()
export class DetalhePrescricaoDataEventService {

    private _detalhePrescricaoDataEvent: DetalhePrescircaoDataEvent = new DetalhePrescircaoDataEvent();

    constructor() {}

    public get detalhePrescricaoDataEvent(): DetalhePrescircaoDataEvent {
        return this._detalhePrescricaoDataEvent;
    }

    public criarDetalhePrescricaoDataEvent(): void {
        this._detalhePrescricaoDataEvent = new DetalhePrescircaoDataEvent();
    }

}
