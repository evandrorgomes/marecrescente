import { Directive, Renderer2, ElementRef, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { MathUtils } from 'app/shared/util/math/math.util';
import { StringUtil } from 'app/shared/util/string.util';

/**
 * @description Formata o prazo de forma a facilitar a leitura 
 * (ex.: Hoje, Amanhã, Vencido a 1 dia, N dias).
 * 
 * @author Pizão
 * @export
 * @class PrazoDirective
 * 
 */
@Directive({
    selector: '[prazo]'
})
export class PrazoDirective {

    // Tabela com os textos das colunas utilizadas nas tabelas.
    private tabela: any = {};

    constructor(private element: ElementRef,
        private renderer: Renderer2,
        private translate: TranslateService) {
            this.translate.get("pacienteBusca.tabela").subscribe(tabela =>{
                this.tabela = tabela;
            });
        }

    /**
     * Formata a exibição do prazo de checklist
     * para o paciente em dias, seguindo o critério:
     * 0 dias: Hoje
     * 1 dia: Amanhã
     * Se expirado: Expirado à 1 dia ou N dias.
     * Se prazo maior que 1 dia: N dias
     * 
     * @param value prazo em dias para o paciente.
     */
    @Input()
    set prazo(value: string){
        if(!StringUtil.isNullOrEmpty(value)){
            let prazoEmDiasFormatado: string;
            let prazo: number = new Number(value).valueOf();

            switch (prazo) {
                case undefined:
                    prazoEmDiasFormatado = null;
                    break;
                case 0:
                    prazoEmDiasFormatado = this.tabela.hoje;
                    break;
                case 1:
                    prazoEmDiasFormatado = this.tabela.amanha;
                    break;
                default:
                    if(prazo < 0){
                        let texto: string = 
                            this.tabela.vencido + " " + MathUtils.positivo(prazo);

                        if(prazo == -1){
                            prazoEmDiasFormatado = texto + " " + this.tabela.dia;
                        }
                        else {
                            prazoEmDiasFormatado = texto + " " + this.tabela.dias;
                        }
                    }
                    else {
                        prazoEmDiasFormatado = value + " " + this.tabela.dias;
                    }
            }
            const prazoFormatado = this.renderer.createText(prazoEmDiasFormatado);
            this.renderer.appendChild(this.element.nativeElement, prazoFormatado);
        }

    }

}