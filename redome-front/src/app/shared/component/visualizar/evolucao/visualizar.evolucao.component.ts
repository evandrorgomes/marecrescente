import { Component, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { Evolucao } from '../../../../paciente/cadastro/evolucao/evolucao';
import { PacienteService } from '../../../../paciente/paciente.service';

@Component({
    selector: 'visualizar-evolucao',
    templateUrl: './visualizar.evolucao.component.html',
})
export class VisualizarEvolucaoComponent implements OnInit{

    private _rmr: number;
    public evolucao: Evolucao;
    public contempladoPortaria: string;
    private _somenteLeitura: boolean;

    private labels: any;

    constructor(private translate: TranslateService, private router: Router,
        private pacienteService: PacienteService, private messageBox: MessageBox) {
        
        this.translate.get("pacienteForm.evolucaoGroup").subscribe(res => {
            this.labels = res;
        });
    }

    ngOnInit() {}

    @Input()
    public set rmr(value: number){
        this._rmr = value;
        this.obterEvolucaoPaciente();
    }

    @Input()
    public set somenteLeitura(value: boolean){
        this._somenteLeitura = value;
    }

    public get somenteLeitura(): boolean{
        return this._somenteLeitura;
    }

    public get rmr(): number {
        return this._rmr;
    }

    private obterEvolucaoPaciente(): void{
        this.pacienteService.obterUltimaEvolucaoPorPaciente(this._rmr).then(res => {
            this.evolucao = new Evolucao().jsonToEntity(res);
            
        }, (error: ErroMensagem) => {
            this.messageBox.alert("Erro ao visualizar última evolução do paciente: " + this._rmr).show();
        });
    }

    /**
     * @description Define o texto internacionalizado que será exibido
     * em caso de CMV positivo ou negativo.
     * @author Pizão
     * @returns {string}
     */
    public formatarCMV(): string{
        return (this.evolucao != null && this.evolucao.cmv ? 
                    this.labels["positivo"] : this.labels["negativo"]);
    }

    /**
     * @description Formata o texto internacionalizado exibido,
     * caso não exista transplante prévio para o paciente.
     * @author Pizão
     * @returns {String}
     */
    public formatarTipoTransplanteAnterior():String{
        // if(this.evolucao == null || 
        //         this.evolucao.tipoTransplanteAnterior == null){
        //     return this.labels["nao"];
        // }
        // else {
        //     return this.evolucao.tipoTransplanteAnterior.descricao;      
        // }
        return "";
    }
}