import {Injectable} from "@angular/core";
import {VisualizarResultadoWorkupDataEvent} from "./visualizar.resultado.workup.data.event";

@Injectable()
export class VisualizarResultadoWorkupDataEventService {

    private _dataEvent: VisualizarResultadoWorkupDataEvent = new VisualizarResultadoWorkupDataEvent();

    constructor() {}

    public get dataEvent(): VisualizarResultadoWorkupDataEvent {
        return this._dataEvent;
    }

    public criarDataEvent(): void {
        this._dataEvent = new VisualizarResultadoWorkupDataEvent();
    }

}