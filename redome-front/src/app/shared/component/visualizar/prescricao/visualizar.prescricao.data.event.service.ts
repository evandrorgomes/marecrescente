import {Injectable} from "@angular/core";
import {VisualizarPrescricaoDataEvent} from "./visualizar.prescricao.data.event";

@Injectable()
export class VisualizarPrescricaoDataEventService {

    private _dataEvent: VisualizarPrescricaoDataEvent = new VisualizarPrescricaoDataEvent();

    constructor() {}

    public get dataEvent(): VisualizarPrescricaoDataEvent {
        return this._dataEvent;
    }

    public criarDataEvent(): void {
        this._dataEvent = new VisualizarPrescricaoDataEvent();
    }

}