import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { ErroMensagem } from "app/shared/erromensagem";
import { HistoricoNavegacao } from "app/shared/historico.navegacao";
import { IModalComponent } from "app/shared/modal/factory/i.modal.component";
import { IModalContent } from "app/shared/modal/factory/i.modal.content";
import { MessageBox } from "../../../../../../shared/modal/message.box";
import { AvaliacaoNovaBuscaService } from "../../../../../../shared/service/avaliacao.nova.busca.service";
import { Modal } from "../../../../../../shared/modal/factory/modal.factory";


/**
 * Conteudo do modal dinámico para cancelamento de pedido de exame fase 3.
 * @author Bruno Sousa
 */
@Component({
    selector: 'reprovar-avaliacao-modal',
    templateUrl: './reprovar.avaliacao.modal.html'
})
export class ReprovarAvaliacaoModal implements IModalContent, OnInit {

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;
    public justificativa: string;
    public mensagemErroJustificativa: string;

    private mensagemInternacionalizada: string;

    constructor(private translate: TranslateService,
                private avaliacaoNovaBuscaService: AvaliacaoNovaBuscaService,
                private messageBox: MessageBox, private router: Router) {
        this.translate.get("novabusca.avaliacao.justificativa" ).subscribe(label => {
            this.translate.get("mensagem.erro.obrigatorio", {"campo": label}).subscribe(res => {
                this.mensagemInternacionalizada = res;
            });    
        });
    }

    public ngOnInit() {}

    private validarForm(): boolean {
        this.mensagemErroJustificativa = null;
        let valid: boolean  = this.justificativa != null && this.justificativa != "";
        if (!valid) {
            this.mensagemErroJustificativa = this.mensagemInternacionalizada;
        } 
        return valid;
    }

    /**
     * @description Reprova a avaliação de solicitação de nova busca.
     * Neste caso, somente a avaliação é finalizada e nada muda no "ciclo de vida"
     * do paciente e/ou busca.
     * @author Pizão
     */
    public reprovar(): void{
        if(this.validarForm()){
            this.avaliacaoNovaBuscaService.reprovar(this.data.idAvaliacao, this.justificativa).then(res => {
                let modalSucesso: Modal = this.messageBox.alert(res.mensagem);
                modalSucesso.closeOption = () => {
                    this.close(this.target);
                    this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
                }
                modalSucesso.show();
                
            }, (error: ErroMensagem) => {
                this.messageBox.alert("Erro ao reprovar a avaliação do paciente").show();
            });
        }
    }

}