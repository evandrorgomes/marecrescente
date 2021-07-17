import { Directive, ElementRef, Input, TemplateRef, ViewContainerRef } from '@angular/core';
import { AutenticacaoService } from '../autenticacao/autenticacao.service';
import { ArrayUtil } from '../util/array.util';
import { Recursos } from '../enums/recursos';
import { StringUtil } from '../util/string.util';
/**
 * @description Diretiva criada para validação do permissionamento de um item
 * para um determinado recurso. Recursos são permissões associadas ao usuário logado e
 * que autorizam ou não seu acesso a determinada funcionalidade.
 * @author Pizão
 * @export
 * @class PermissaoDirective
 */
@Directive({
    selector: '[permissoes]'
})
export class PermissaoDirective {

    private permissoesDeAcesso: string[];

    constructor(
        private element: ElementRef,
        private templateRef: TemplateRef<any>,
        private viewContainer: ViewContainerRef,
        private autenticacaoService: AutenticacaoService) { }

    ngOnInit() {}

    @Input()
    set permissoes(oneOrMore: string | string[]) {
        this.permissoesDeAcesso = oneOrMore instanceof Array ? oneOrMore : [oneOrMore];
        this.atualizarView();
    }

    /**
     * @description Atualiza a visualização do item de acordo com as
     * permissões de acesso informadas.
     * @private
     */
    private atualizarView(): void{
        if (ArrayUtil.isEmpty(this.permissoesDeAcesso) || this.validarPermissoes()) {
            this.viewContainer.createEmbeddedView(this.templateRef);
        }
        else {
            this.viewContainer.clear();
        }
    }

    /**
     * @description Verificar se o usuário logado possui alguma das permissões
     * informadas, apenas uma é suficiente para ele ter acesso.
     * Isso foi preciso para casos em que mais de um recurso dá acesso a funcionalidade.
     * 
     * @returns {boolean} TRUE se tiver ao menos uma permissão.
     */
    private validarPermissoes(): boolean{
        let autorizado: boolean = false;
        this.permissoesDeAcesso.forEach(permissao => {
            if(this.autenticacaoService.temRecurso(permissao)){
                autorizado = true;
            }

            if(!autorizado && StringUtil.isNullOrEmpty(Recursos[permissao])){
                throw new Error("Recurso " + permissao + " é desconhecido. Verifique se é um recurso novo ou o texto informado está incorreto.");
            }
        });
        return autorizado;
    }

}