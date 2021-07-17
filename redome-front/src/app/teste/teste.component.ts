import { TranslateService } from '@ngx-translate/core';
import { Component, Input, OnInit, OnDestroy, ViewChild, ViewEncapsulation } from '@angular/core';
import { PermissaoRotaComponente } from "../shared/permissao.rota.componente";
import { ComponenteRecurso } from "../shared/enums/componente.recurso";
/**
 * Classe Component utilizada para controlar os campos e métodos
 * disponibilizados em login.component.html
 * @author Filipe Paes
 */
@Component({
    selector: 'teste', 
    templateUrl: './teste.component.html',
    styleUrls: ['./teste.css']
})
export class TesteComponent implements PermissaoRotaComponente {

    constructor(private translate: TranslateService) {
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.TesteComponent];
    }
    
}