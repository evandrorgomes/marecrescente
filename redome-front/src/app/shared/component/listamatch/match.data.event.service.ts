import { Injectable } from "@angular/core";
import { MatchDataEvent } from "./match.data.event";

@Injectable()
export class MatchDataEventService {

    private _matchDataEvent: MatchDataEvent = new MatchDataEvent();

    /* private matchDataEventSource = new BehaviorSubject<MatchDataEvent>(new MatchDataEvent());
    currentMatchDataEvent = this.matchDataEventSource.asObservable(); */

    constructor() {}

    public get matchDataEvent(): MatchDataEvent {
        return this._matchDataEvent;
    }

    public criarMatchDataEvent(): void {
        this._matchDataEvent = new MatchDataEvent();
    }

    /*changeMatchDataEvent(matchDataEvent: MatchDataEvent) {
        this.matchDataEventSource.next(matchDataEvent);
    } */

}