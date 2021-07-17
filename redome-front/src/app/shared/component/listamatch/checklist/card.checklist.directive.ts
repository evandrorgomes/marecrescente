import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output, Renderer2, ChangeDetectorRef } from '@angular/core';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { BuscaChecklistService } from 'app/shared/service/busca.checklist.service';
import { ArrayUtil } from 'app/shared/util/array.util';
import { ErroUtil } from 'app/shared/util/erro.util';
import { BuscaChecklistDTO } from '../classes/busca.checklist.dto';
import { MatchDataEventService } from 'app/shared/component/listamatch/match.data.event.service';

/**
 * @description Cria o comportamento do card (cada item na lista).
 * Se houver itens de checklist, deverão ser exibidos no card e 
 * deverá ser marcado com um fundo diferenciado.
 * 
 * @author Pizão
 * 
 * @export
 * @class CardDirective
 */
@Component({
    selector: 'card-checklist',
    templateUrl: './card.checklist.directive.html',
    styleUrls: ['./card.checklist.directive.css'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CardChecklistDirective {

    public _isOpen: boolean = false;
    private _textoTooltip: string;
    private ocorrencias: BuscaChecklistDTO[];
    private idBusca: number;
    public idMatch: number;

    public _match: boolean;

    constructor(private cdr: ChangeDetectorRef,  private buscaChecklistService: BuscaChecklistService,
                private messageBox: MessageBox, private matchDataEventService: MatchDataEventService) {}

    @Input()
    set value(ocorrencias: BuscaChecklistDTO[]){
        this.idBusca = undefined;
        this.idMatch = undefined;
        this.ocorrencias = ocorrencias;

        if(ArrayUtil.isNotEmpty(this.ocorrencias)){
            this.idBusca = this.ocorrencias[0].idBusca;
            this.idMatch = this.ocorrencias[0].idMatch;
        }
    }

    get value(): BuscaChecklistDTO[]{
        return this.ocorrencias;
    }

    get markChecklistClass(): string{
        return  ArrayUtil.isNotEmpty(this.ocorrencias) ? "checklist" : null;
    }

    @Input()
    set match(val: any) {
        if (val && val === 'true') {
            this._match = true;
        }
        else if (val && val == 'false') {
            this._match == false;
        }
        else if (val) {
            this._match = true;
        }
        else {
            this._match = false;
        }
    }

    
    /**
     * @description Confirma o visto do checklist para todos os itens.
     * @author Pizão
     */
    confirmarVisto(id: number): void{
        if(this._isOpen){
            this._isOpen = false;
            
            this.buscaChecklistService.marcarVisto(id)
                .then(() => {
                    let auxOcorrencias = this.ocorrencias.filter(ocorrencia => ocorrencia.id !== id);
                    if (auxOcorrencias && auxOcorrencias.length != 0) {
                        this.ocorrencias = auxOcorrencias;
                    }
                    else {
                        this.ocorrencias = null;
                        this.idBusca = null;
                        this.idMatch = null;                        
                    }
                    this.matchDataEventService.matchDataEvent.atualizarChecklist(id);
                    this.cdr.detectChanges();
                }, (error: ErroMensagem) => {
                    this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
                });
        } 
    }

}