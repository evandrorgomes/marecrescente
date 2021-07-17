import {TranslateService} from '@ngx-translate/core';
import { Component, OnInit } from "@angular/core";
import { BuscaService } from "../../busca.service";
import { HistoricoBuscaDTO } from "../../../../shared/classes/historico.busca";
import { ErroMensagem } from "../../../../shared/erromensagem";
import { IModalComponent } from "../../../../shared/modal/factory/i.modal.component";
import { IModalContent } from "../../../../shared/modal/factory/i.modal.content";
import { DataUtil } from "../../../../shared/util/data.util";
import { DateTypeFormats } from "../../../../shared/util/date/date.type.formats";
import { PacienteService } from '../../../paciente.service';

/**
 * Componente para listas as recusas do CT para a busca do paciente.
 * 
 * @author Pizão
 */
@Component({
    selector: "historico-recusas-ct-modal",
    templateUrl: './historico.recusas.ct.modal.html'
})
export class HistoricoRecusasCentroTransplanteModal implements IModalContent, OnInit {
    target: IModalComponent;
    close: (target: IModalComponent) => void;
    public data: any;
    public historicoRecusasCt: HistoricoBuscaDTO[];
    
    constructor(translate: TranslateService, private pacienteService: PacienteService){}
    
    ngOnInit(): void {
        this.listarHistoricoRecusas();
    }
    
    /**
     * Lista o histórico de recusas que ocorrem para esta busca, durante a
     * seleção do CT.
     */
    public listarHistoricoRecusas(): void{
        this.pacienteService.listarHistoricoRecusasPeloCT(this.data)
                .then(res => {
                    this.historicoRecusasCt = res;

                }, (error: ErroMensagem) => {
                    console.log("Erro ao listar histórico da busca para o paciente: " + this.data);
                });
    }

    /**
     * Formata a data/hora a ser exibida no log.
     * 
     * @param data data a ser formatada. 
     */
    public formatarDataHora(data: Date): string{
        return DataUtil.toDateFormat(new Date(data), DateTypeFormats.DATE_TIME);
    }
}