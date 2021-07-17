import { Component } from '@angular/core';
import { PerfilUsuario } from '../../../shared/enums/perfil.usuario';
import { ContatoFase2Component } from '../fase2/contato.fase2.component';

/**
 * Classe que representa o componente de contato pra fase 2.
 */
@Component({
    selector: 'contato-fase3',
    templateUrl: '../fase2/contato.fase2.component.html',
    styleUrls: ['./../../doador.css', './../fase2/contato.doador.css']
})

export class ContatoFase3Component extends ContatoFase2Component {
    
    public get fase(): number{
        return 3;
    }

    protected obterPerfilParaFaseSelecionada(): number{
        return PerfilUsuario.ANALISTA_CONTATO_FASE3;
    }

    nomeComponente(): string {
        return 'ContatoFase3Component';
    }

    public obterTitulo(): string {
		return this._formLabels ? this._formLabels['tituloFase3'] : '';
    }
 
	public deveExibirVeioDaConsultaContatoPassivo(): boolean {
		return false;
	}
};