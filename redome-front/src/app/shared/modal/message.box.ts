import { Injectable, Type } from "@angular/core";
import { ModalFactory, Modal, ModalConfirmation } from "./factory/modal.factory";
import { EventDispatcher } from "../eventos/event.dispatcher";
import { AlertaModalComponente } from "./factory/alerta.modal.component";
import { ModalEvent } from "../eventos/modal.event";
import { IModalComponent } from "./factory/i.modal.component";
import { IModalContent } from "./factory/i.modal.content";

/**
 * Classe para simplificação da chamada do modal.
 * Deve ser utilizada para uso dos modais, de qualquer tipo, no sistema.
 * Tais como alertas, confirmatórios, de erro, etc.
 * 
 * @author Pizão
 */
@Injectable()
export class MessageBox{

    public constructor(private factory: ModalFactory){}

    /**
     * Exibe o modal como uma alerta, mostrando o texto passado no parâmetro.
     * 
     * @param data texto a ser exibido no modal.
     */
    public alert(data: string): Modal{
        return this.factory.exibirMensagem(data);
    }

    /**
     * Abre o modal com o conteúdo dinâmico (outro componente) 
     * passado no parâmetro.
     * 
     * @param data objeto contendo os parâmetros que deverão ser 
     * recebidos no componente dinâmico que é aberto.
     * @param conteudoDinamico componente que deverá ser exibido
     * como conteúdo do portal.
     */
    public dynamic(data: any, conteudoDinamico: Type<IModalContent>): Modal{
        return this.factory.exibirConteudoDinamico(data, conteudoDinamico);
    }

    /**
     * Abre um modal para confirmação (sim ou não).
     * 
     * @param data texto que será exibido para confirmação
     * do usuário.
     */
    public confirmation(data: any): ModalConfirmation{
        return this.factory.confirmacaoMensagem(data);
    }
}