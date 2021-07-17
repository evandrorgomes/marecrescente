import { AfterViewInit, Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { BuscaChecklistDTO } from 'app/shared/component/listamatch/classes/busca.checklist.dto';
import { WindowViewContainerComponent } from 'app/shared/component/window-view/window-view-container/window-view-container.component';
import { WindowViewService } from 'app/shared/component/window-view/window-view.service';
import { BuscaChecklistService } from 'app/shared/service/busca.checklist.service';
import { MessageBox } from 'app/shared/modal/message.box';
import { ErroMensagem } from 'app/shared/erromensagem';
import { ErroUtil } from 'app/shared/util/erro.util';

@Component({ 
    selector: 'checklist-windows',
    templateUrl: './checklist.windows.component.html',
})
export class ChecklistWindowsComponent implements OnInit, AfterViewInit {

    @ViewChild('windowViewContainer')
    private windowViewContainer: WindowViewContainerComponent;

    constructor(private windowView: WindowViewService, private cdr: ChangeDetectorRef,
        private buscaChecklistService: BuscaChecklistService, private messageBox: MessageBox) {
     }

     public top = 0;
     public left = 100;

    private titulo: string = '';

    public fecharJanela = () => {};
    public lista: BuscaChecklistDTO[];
    public checklistSelecionado = (checklistDto: BuscaChecklistDTO) => {};
    public checklistsVistados = (checklistDto: BuscaChecklistDTO[]) => {};

    ngOnInit(): void {         
    }

    ngAfterViewInit() {
        Promise.resolve(null).then(() => {this.titulo = 'Checklist';});
    }


    public getTitulo(): string {
        return this.titulo;
    }

    public getFloating(): boolean {
        return true;
    }

    public getShowBackground(): boolean {
        return false;
    }

    closeWindow() {
        this.windowView.removeByInstance(this);
        this.fecharJanela();
    }

    selecionar(checklistDto: BuscaChecklistDTO) {
        if (checklistDto.identificadorDoador && checklistDto.identificadorDoador !== null && checklistDto.identificadorDoador !== '') {
            this.checklistSelecionado(checklistDto);
        }
    }

    get position(): { x: number, y: number } { 
        return this.windowViewContainer.position; 
    }

    set position(value: { x: number, y: number }) {
        this.windowViewContainer.position = value;
    }

    public doDetectChanges() {
        this.cdr.detectChanges();
    }

    public obterClasseChecklist(checklistDto: BuscaChecklistDTO): string{
        if(!checklistDto.vistarChecklist){
            return "checkbox";
        }
        else{
            return "checkbox checkbox-checked";
        }
    }

    public adicionarRemoverChecked(checklistDto: BuscaChecklistDTO){
        if(!checklistDto.vistarChecklist){
            checklistDto.vistarChecklist = true;
            this.cdr.markForCheck();
        }else{
            checklistDto.vistarChecklist = false;
            this.cdr.markForCheck();
        }
    }

    confirmarVistos(): void{
        let lista: BuscaChecklistDTO[] = this.lista.filter(checklist => checklist.vistarChecklist);

        this.buscaChecklistService.marcarListaDeVistos(lista.map(checklist => checklist.id)).then(res => {
            this.checklistsVistados(lista);
        }, (error: ErroMensagem) => {
            this.messageBox.alert(ErroUtil.extrairMensagensErro(error)).show();
        });
    }

    temChechVistado(): Boolean{
        let len: number = this.lista.filter(checklist => checklist.vistarChecklist).length;
        if(len > 0){
            return true;
        }
        return false;
    }

}
