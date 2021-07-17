import { TemplateRef, Directive } from '@angular/core';

/**
 * @description Define o bloco que conterá os filtros na tela de 
 * listar padronizada.
 * 
 * @author Pizão
 * @export
 * @class FiltroConsulta
 */
@Directive({
    selector: '[filtro]'
})
export class FiltroConsulta {
    constructor(public template: TemplateRef<any>) { }
}
