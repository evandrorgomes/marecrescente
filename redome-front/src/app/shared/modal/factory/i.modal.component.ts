import { IModalContent } from "./i.modal.content";
import { ModalEvent } from "../../eventos/modal.event";
import { EventDispatcher } from "../../eventos/event.dispatcher";

export abstract class IModalComponent {
    public target: any;
    data: any;
    exibirBotaoOk: boolean = false;    
    public abstract hide();
}