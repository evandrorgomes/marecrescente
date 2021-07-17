import { HistoricoNavegacao } from '../shared/historico.navegacao';
import { ComponenteRecurso } from '../shared/enums/componente.recurso';
import { TranslateService } from '@ngx-translate/core';
import { EventEmitterService } from '../shared/event.emitter.service';
import { Component, Input, OnInit, OnDestroy, ViewChild, ViewEncapsulation } from '@angular/core';
import { CadastroComponent } from '../paciente/cadastro/cadastro.component';
import { Router, RoutesRecognized } from '@angular/router';
import { PermissaoRotaComponente } from "../shared/permissao.rota.componente";
import { AutenticacaoService } from '../shared/autenticacao/autenticacao.service';
import { Recursos } from 'app/shared/enums/recursos';
/**
 * Classe Component utilizada para controlar os campos e métodos
 * disponibilizados em login.component.html
 * @author Filipe Paes
 */
@Component({
    selector: 'home'
    , templateUrl: './home.component.html'

})
export class HomeComponent implements PermissaoRotaComponente {

    constructor(private router: Router,private translate: TranslateService, private autenticacaoService: AutenticacaoService) {
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.HomeComponent];
    }

    public exibirDashboardContato(): boolean  {
        return this.autenticacaoService.temPerfilAnalistaContato() && this.autenticacaoService.temRecurso(Recursos.VISUALIZAR_DASHBOARD);
    }
}