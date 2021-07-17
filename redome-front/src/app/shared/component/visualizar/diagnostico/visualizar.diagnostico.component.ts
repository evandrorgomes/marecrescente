import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { Diagnostico } from 'app/paciente/cadastro/diagnostico/diagnostico';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { PacienteService } from '../../../../paciente/paciente.service';

@Component({
    selector: 'visualizar-diagnostico',
    templateUrl: './visualizar.diagnostico.component.html',
})
export class VisualizarDiagnosticoComponent implements OnInit{

    private _rmr: number;
    public diagnostico: Diagnostico;
    public contempladoPortaria: string;
    private _somenteLeitura: boolean;

    constructor(translate: TranslateService, private router: Router,
        private pacienteService: PacienteService, private messageBox: MessageBox) {
    }

    ngOnInit() {}

    @Input()
    public set rmr(value: number){
        this._rmr = value;
        this.obterDiagnosticoPaciente();
    }

    @Input()
    public set somenteLeitura(value: boolean){
        this._somenteLeitura = value;
    }

    public get rmr(): number {
        return this._rmr;
    }

    public get somenteLeitura(): boolean{
        return this._somenteLeitura;
    }

    private obterDiagnosticoPaciente(): void{
        this.pacienteService.obterDiagnostico(this._rmr).then(res => {
            this.diagnostico = new Diagnostico().jsonToEntity(res);
            this.contempladoPortaria = this.diagnostico.cid.transplante ? 'azul' : 'amarelo';

        }, (error: ErroMensagem) => {
            this.messageBox.alert("Erro ao visualizar diagnóstico do paciente: " + this._rmr).show();
        });
    }

}