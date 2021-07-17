import { EventEmitterService } from "../event.emitter.service";
import { IEvent } from "./i.event";
import { OnDestroy } from "@angular/core";

/**
 * Classe que cria um comportamento de "emissor de eventos"
 * a outras classes. É usado para tornar componentes
 * "dispatchables", ou seja, eles passam a poder adicionar
 * eventos associados a ele.
 * 
 * @author Pizão.
 */
export abstract class EventDispatcher<T extends IEvent> implements OnDestroy {

    private events: T[];

    constructor() {
        this.events = [];
    }

    public addListener(event: T, methodEvent: () => void) {
        this.events.push(event);
        EventEmitterService.get(event.key).subscribe(methodEvent);
    }

    public dispatch(event: T) {
        let eventDispatch: any = EventEmitterService.get(event.key);
        if (eventDispatch) {
            eventDispatch.emit();
        }
    }

    ngOnDestroy(): void {
        this.events.forEach(event => {
            EventEmitterService.remove(event.key);
        });
    }
}