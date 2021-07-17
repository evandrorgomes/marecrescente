import { IEvent } from "./i.event";
import { AbstractEvent } from "./abstract.event";

/**
 * Classe de eventos que envolvem chamadas a modal.
 * Engloba os eventos de alerta (apenas de fechar) e
 * confirmação (opções de sim, não e fechar)
 * 
 * @author Pizão
 */
export class ModalEvent extends AbstractEvent{
    public static HIDE: ModalEvent = new ModalEvent("modal.event.hide");

    public static CLOSE: ModalEvent = new ModalEvent("modal.event.close");
    public static YES: ModalEvent = new ModalEvent("modal.event.yes");
    public static NO: ModalEvent = new ModalEvent("modal.event.no");

    private constructor(key: string){
        super(key);
    }

}