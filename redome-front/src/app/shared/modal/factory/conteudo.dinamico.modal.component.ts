import { Observable } from 'rxjs';
import { Component, Input, ViewChild, EventEmitter, Output, ComponentFactoryResolver, ViewContainerRef, Type, AfterViewInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap';
import { EventEmitterService } from '../../event.emitter.service';
import { ModalEvent } from '../../eventos/modal.event';
import { ConfirmacaoModalComponente } from './confirmacao.modal.component';
import { IModalComponent } from './i.modal.component';
import { IModalContent } from './i.modal.content';
import { EventDispatcher } from '../../eventos/event.dispatcher';

/**
 * Classe visual do componente de modal de confirmação.
 * 
 * @author Pizão
 */
@Component({
    moduleId: module.id,
    selector: 'conteudo-dinamico-modal',
    templateUrl: './conteudo.dinamico.modal.component.html'
})
export class ConteudoDinamicoModalComponente extends IModalComponent implements AfterViewInit{

    public target: any;
    closeOption: (target: any)=>void;
    public exibirBotaoOk: boolean = false;
    private jaFechou: boolean = false;

    @ViewChild("content", { read: ViewContainerRef })
    public corpoModal: ViewContainerRef;

    // Conteúdo fornecido que deve ser adicionado dinamicamente ao modal.
    private _conteudoDinamico: Type<IModalContent>;


    constructor(private bsModalRef: BsModalRef,
        private factoryResolver: ComponentFactoryResolver) {
        super();
    }

    public fecharModalComponente() {
        this.acaoBotaoFechar();
        if(this.closeOption){
            this.closeOption(this.target);
        }
    }

    public acaoBotaoFechar(): void{
        if (!this.jaFechou) {
            this.bsModalRef.hide();
        }
    }

    public set conteudoDinamico(conteudo: Type<IModalContent>){
        this._conteudoDinamico = conteudo;
        this.criarConteudoDinamico();
    }

    ngAfterViewInit(){}

    private criarConteudoDinamico(): void{
        if(this._conteudoDinamico){
            const componentFactory = 
                this.factoryResolver.resolveComponentFactory(this._conteudoDinamico);
            this.corpoModal.clear();
            let instance: IModalContent = 
                this.corpoModal.createComponent(componentFactory, 0).instance;
                
            Promise.resolve(null).then(() => {
                instance.data = this.data;                
            });
            
            instance.target = this;
            instance.close = function(target: IModalComponent) {
                (<ConteudoDinamicoModalComponente>target).fecharModalComponente();
            };
        }
        else {
            throw new Error("Conteúdo dinâmico não foi acrescentado. Revise o " + 
                "componente e reveja necessidade de utilizar o componente com conteúdo dinâmico.");
        }
    }

    public hide(): void {
        this.jaFechou = true;
        this.bsModalRef.hide();
    }



}