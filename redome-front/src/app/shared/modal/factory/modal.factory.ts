import { Injectable, EventEmitter, Type } from "@angular/core";
import { BsModalService } from "ngx-bootstrap/modal/bs-modal.service";
import { AlertaModalComponente } from "./alerta.modal.component";
import { BsModalRef } from "ngx-bootstrap";
import { ConfirmacaoModalComponente } from "./confirmacao.modal.component";
import { EventDispatcher } from "../../eventos/event.dispatcher";
import { ModalEvent } from "../../eventos/modal.event";
import { ConteudoDinamicoModalComponente } from "./conteudo.dinamico.modal.component";
import { IModalComponent } from "./i.modal.component";
import { IModalContent } from "./i.modal.content";

/**
 * Classe factory para a criação do comportamento dos modais genéricos
 * em substituição ao uso (e replicação) das tags no HTML. 
 * 
 * @author bruno.sousa
 */
@Injectable()
export class ModalFactory {

    private class: string = "modal-custom";
    private config = {
        animated: true,
        keyboard: false,
        backdrop: false,
        ignoreBackdropClick: false
    };

    constructor(private modalService: BsModalService) { }

    public configureView(clazz: Type<any>): BsModalRef {
        return this.modalService.show(clazz, Object.assign({}, this.config, { class: this.class }));
    }

    public exibirMensagem(texto: string): MensagemFactory {
        return new MensagemFactory(this, texto);
    }

    public confirmacaoMensagem(data: any): MensagemConfirmacaoFactory {
        return new MensagemConfirmacaoFactory(this, data);
    }

    public exibirConteudoDinamico(data: any, conteudoDinamico: Type<IModalContent>): ConteudoDinamicoFactory {
        return new ConteudoDinamicoFactory(this, data, conteudoDinamico);
    }

}

export abstract class Modal {
    target: any;
    public data: any;
    protected _showButtonOk: boolean = false;
    public closeOption: (target?: any) => void;
    protected clazz: Type<any>;
    protected component: IModalComponent;

    constructor(private modalFactory: ModalFactory, clazz: Type<any>) {
        this.clazz = clazz;
    }

    public showButtonOk(value: boolean): Modal {
        this._showButtonOk = value;
        return this;
    }

    public withCloseOption(closeOption: (target: any) => void ): Modal {
        this.closeOption = closeOption;
        return this;
    }

    public withTarget(target: any): Modal {
        this.target = target;
        return this;
    }

    public show(): Modal {
        let modal: BsModalRef = this.modalFactory.configureView(this.clazz);
        this.component = modal.content;        
        this.component.target = this.target;
        Promise.resolve(null).then(() => {
            this.component.data = this.data;
            this.component.exibirBotaoOk = this._showButtonOk;
        });
        return this;
    }
}

export abstract class ModalConfirmation extends Modal {
    public yesOption: (target?: any) => void;
    public noOption: (target?: any) => void;
}

/**
 * Define a view e o comportamento do modal de alerta (apenas exibe a mensagem).
 */
class MensagemFactory extends Modal {

    constructor(private modalFactoryThis: ModalFactory, texto: string) {
        super(modalFactoryThis, AlertaModalComponente);
        this.data = texto;
    }

    public show(): Modal {
        super.show();
        let component: AlertaModalComponente = <AlertaModalComponente>this.component;
        component.closeOption = this.closeOption;
        component.target = this.target;
        return this;
    }

}

/**
 * Define o comportamento do modal de confirmação, quando há mais de uma opção
 * de escolha a uma pergunta.
 */
class MensagemConfirmacaoFactory extends ModalConfirmation {

    constructor(private modalFactoryThis: ModalFactory, texto: string) {
        super(modalFactoryThis, ConfirmacaoModalComponente);
        this.data = texto;
    }

    public show(): Modal {
        let modal: BsModalRef = this.modalFactoryThis.configureView(this.clazz);
        let component: ConfirmacaoModalComponente = modal.content;
        component.data = this.data;
        component.exibirBotaoOk = this._showButtonOk;
        component.closeOption = this.closeOption;
        component.yesOption = this.yesOption;
        component.noOption = this.noOption;
        component.target = this.target;
        return this;
    }
}

/**
 * Define o comportamento do modal que tem body dinâmico, com o mesmo comportamento
 * do modal de confirmação.
 */
class ConteudoDinamicoFactory extends Modal {
    private _conteudoDinamico: Type<IModalContent>;

    constructor(private modalFactoryThis: ModalFactory, data: any, conteudoDinamico: Type<IModalContent>) {
        super(modalFactoryThis, ConteudoDinamicoModalComponente);
        this.data = data;
        this._conteudoDinamico = conteudoDinamico;
    }

    public show(): Modal {
        super.show();
        let component: ConteudoDinamicoModalComponente = <ConteudoDinamicoModalComponente>this.component;
        component.closeOption = this.closeOption;
        component.conteudoDinamico = this._conteudoDinamico;
        component.target = this.target;
        return this;
    }

}