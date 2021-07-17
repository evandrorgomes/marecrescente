import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ExameService } from '../../paciente/cadastro/exame/exame.service';
import { DominioService } from '../dominio/dominio.service';
import { MessageBox } from '../modal/message.box';
import { HlaComponent } from './hla.component';

/**
 * Component para exibição somente leitura do HLA.
 *
 * @author Pizão
 * @export
 * @class HlaComponent
 * @extends {BaseForm}
 * @implements {OnDestroy}
 */
@Component({
    selector: 'hla-viewer-component',
    moduleId: module.id,
    templateUrl: './hla.component.html'
})
export class HlaViewerComponent extends HlaComponent {

    constructor(fb: FormBuilder, translate: TranslateService,
        dominioService: DominioService, exameService: ExameService,
        messageBox: MessageBox) {
        super(fb, translate, dominioService, exameService, messageBox);
    }

    ngOnInit() {
        super.ngOnInit();
        this.readOnly = true;
    }

}
