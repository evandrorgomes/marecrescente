import { Component, OnInit, OnDestroy } from "@angular/core";
import { Paginacao } from "app/shared/paginacao";
import { IModalComponent } from "app/shared/modal/factory/i.modal.component";

@Component({
    selector: 'modal-itens-nao-conciliados',
    templateUrl: './modal.itensnaoconciliados.component.html'
})
export class ModalItensNaoConciliadosComponent implements OnInit, OnDestroy {


    quantidadeRegistro: number = 10;
    paginacaNaoConciliado: Paginacao;

    target: IModalComponent;
    close: (target: IModalComponent) => void;
    data: any;

    constructor() {

        this.paginacaNaoConciliado = new Paginacao('', 1, this.quantidadeRegistro);
        this.paginacaNaoConciliado.quantidadeRegistro = 10;
        this.paginacaNaoConciliado.number = 1;
    }

    ngOnInit(): void {
        this.paginacaNaoConciliado.mainList = this.data.mainList;
    }
    
    ngOnDestroy(): void {
        throw new Error("Method not implemented.");
    }

    mudarPaginaPaginacao(event: any) {
        this.paginacaNaoConciliado.slicePage(event.page);
    }


    selecinaQuantidadePorPagina(event: any) {
        this.paginacaNaoConciliado.quantidadeRegistro = this.quantidadeRegistro;
    }


}