import { Component, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { FormGroup } from '@angular/forms/src/model';
import { TranslateService } from '@ngx-translate/core';
import { SolicitacaoService } from 'app/doador/solicitacao/solicitacao.service';
import { BuildForm } from 'app/shared/buildform/build.form';
import { NumberControl } from 'app/shared/buildform/controls/number.control';
import { AcaoPedidoContato } from 'app/shared/enums/acao.pedido.contato';
import { IModalComponent } from 'app/shared/modal/factory/i.modal.component';
import { IModalContent } from 'app/shared/modal/factory/i.modal.content';
import { MessageBox } from 'app/shared/modal/message.box';
import { PedidoContatoFinalizadoVo } from 'app/shared/vo/pedido.contato.finalizado.vo';
import { MotivoStatusDoador } from 'app/doador/inativacao/motivo.status.doador';
import { ErroUtil } from 'app/shared/util/erro.util';
import { AnaliseMedicaFinalizadaVo } from 'app/shared/vo/analise.medica.finalizada.vo';
import { StringControl } from 'app/shared/buildform/controls/string.control';
import { EvolucaoDoadorVo } from 'app/shared/vo/evolucao.doador.vo';


/**
 * Modal para incluir uma nova evolução do doador.
 * 
 */
@Component({
    selector: 'modal-evolucao-doador',
    templateUrl: './modal.evolucao.doador.component.html'
})
export class ModalEvolucaoDoadorComponent  implements IModalContent, OnInit {
    target: IModalComponent;
    data: any;
    close: (target: IModalComponent) => void; 
    
    private evolucaoDoadorForm: BuildForm<any>;
    

    constructor(private translate: TranslateService
        , private messageBox: MessageBox
        , private solicitacaoService: SolicitacaoService){

        
        this.evolucaoDoadorForm = new BuildForm<any>()            
            .add(new StringControl('descricao', [Validators.required]));

    }

    ngOnInit(): void {

    }

    public fechar(target: any) {
        target.close(target.target);
    }

    public confirmar() {
        if (this.evolucaoDoadorForm.valid) {
            let evolucaoDoadorVo: EvolucaoDoadorVo = new EvolucaoDoadorVo();            
            
            evolucaoDoadorVo.descricao = this.evolucaoDoadorForm.get("descricao").value;
                        
            this.target.hide();
            
            this.data.finalizar(evolucaoDoadorVo);
        }
    }
 
    public form(): FormGroup {
        return this.evolucaoDoadorForm.form;
    }


}