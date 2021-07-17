import { Directive, TemplateRef } from '@angular/core';
/**
 * @description Diretiva criada para validação do permissionamento de um item
 * para um determinado recurso. Recursos são permissões associadas ao usuário logado e
 * que autorizam ou não seu acesso a determinada funcionalidade.
 * @author Pizão
 * @export
 * @class PermissaoDirective
 */
@Directive({
    selector: '[menu-item]'
})
export class MenuItemDirective {
    constructor(public templateRef: TemplateRef<any>) { }

    
}