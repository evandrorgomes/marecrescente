import {HistoricoNavegacao} from '../historico.navegacao';
import {Router} from '@angular/router';
import { Directive, HostListener } from '@angular/core';

@Directive({
    selector: '[retornarPaginaAnterior]'
})
export class RetornarPaginaAnterior {

    constructor(private router:Router) {}

    @HostListener('click') onClick() {
        this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
    }

}