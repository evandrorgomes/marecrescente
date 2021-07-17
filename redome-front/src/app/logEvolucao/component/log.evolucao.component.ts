import { AfterViewInit, Component, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { MessageBox } from 'app/shared/modal/message.box';
import { RouterUtil } from 'app/shared/util/router.util';
import { Paginacao } from 'app/shared/paginacao';
import { PacienteService } from "../../paciente/paciente.service";
import { ComponenteRecurso } from "../../shared/enums/componente.recurso";
import { ErroMensagem } from "../../shared/erromensagem";
import { PermissaoRotaComponente } from "../../shared/permissao.rota.componente";
import { DataUtil } from "../../shared/util/data.util";
import { DateTypeFormats } from "../../shared/util/date/date.type.formats";
import { LogEvolucaoDTO } from "../classes/log.evolucao.dto";
import { HeaderPacienteComponent } from './../../paciente/consulta/identificacao/header.paciente.component';
import { ErroUtil } from './../../shared/util/erro.util';

@Component({
    selector: 'log-evolucao-component',
    templateUrl: './log.evolucao.component.html'
})

export class LogEvolucaoComponent implements PermissaoRotaComponente, AfterViewInit {

    paginacao: Paginacao;
    quantidadeRegistro: number = 10; 

    private rmr: number;

    @ViewChild("headerPaciente")
    private headerPaciente: HeaderPacienteComponent;

    constructor(private pacienteService: PacienteService,
                private activatedRouter: ActivatedRoute,
                private messageBox: MessageBox) {
        this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);
        this.paginacao.number = 1;
    }


    ngAfterViewInit() {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idPaciente").then(res => {
            this.rmr = Number(res);
            this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmr);
            this.listarLog(1);
        });        
    }

    /**
     * Método para listar logs de evolução associados do paciente.
     */
    private listarLog(pagina: number){

        this.pacienteService.listarLogEvolucao(
            pagina - 1, this.quantidadeRegistro, this.rmr).then(res => {
            this.paginacao.content = res.content;
            this.paginacao.totalElements = res.totalElements;
            this.paginacao.quantidadeRegistro = this.quantidadeRegistro;
            this.paginacao.number = pagina;
        },
        (erro: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(erro, this.messageBox)
        });
    }

    /**
     * Retorna o nome do componente
     * 
     * @returns {string} 
     */
    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.LogEvolucaoComponent];
    }

    /**
     * Formata a data/hora a ser exibida no log.
     * 
     * @param data data a ser formatada. 
     */
    public formatarDataHora(data: Date): string{
        return DataUtil.toDateFormat(new Date(data), DateTypeFormats.DATE_TIME);
    }

        /**
     * Método acionado quando muda a página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaComponent
     */
    mudarPagina(event: any) {
        this.listarLog(event.page);
    }

        /**
     * Método acionado quando é alterado a quantidade de registros por página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaComponent
     */
    selecinaQuantidadePorPagina(event: any, modal: any) {
        this.listarLog(1);
    }

}