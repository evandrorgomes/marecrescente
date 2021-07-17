import { Location } from '@angular/common';
import { PermissaoRotaComponente } from '../../../../shared/permissao.rota.componente';
import { ComponenteRecurso } from '../../../../shared/enums/componente.recurso';
import { Component, OnInit, ViewChild } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { Paginacao } from '../../../../shared/paginacao';
import { TiposTarefa } from '../../../../shared/enums/tipos.tarefa';
import { Perfis } from '../../../../shared/enums/perfis';
import { StatusTarefas } from '../../../../shared/enums/status.tarefa';
import { AtributoOrdenacaoDTO } from '../../../../shared/util/atributo.ordenacao.dto';
import { AtributoOrdenacao } from '../../../../shared/util/atributo.ordenacao';
import { TarefaService } from '../../../../shared/tarefa.service';
import { Tarefa } from '../../../consulta/pendencia/tarefa';
import { CentroTransplanteADefinirDTO } from './centro.transplante.a.definir.dto';
import { DateMoment } from '../../../../shared/util/date/date.moment';
import { DateTypeFormats } from '../../../../shared/util/date/date.type.formats';
import { HistoricoNavegacao } from '../../../../shared/historico.navegacao';
import { TarefaBase } from '../../../../shared/dominio/tarefa.base';
import { DataUtil } from '../../../../shared/util/data.util';

@Component({
    selector: 'listar-definir-centro-transplante',
    moduleId: module.id, 
    templateUrl: './listar.definir.centro.transplante.component.html'
})

export class ListarDefinirCentroTransplanteComponent implements PermissaoRotaComponente, OnInit{
    qtdRegistros: number = 10;

    paginacao: Paginacao;
    quantidadeRegistro: number = 10;
    public centroIndefinidoSelecionado: CentroTransplanteADefinirDTO;
    private centrosIndefinidos: CentroTransplanteADefinirDTO[];

    @ViewChild("modalMsgErro")
    private modalMsgErro;

    @ViewChild("modalNotificacao")
    private modalNotificacao;
    

    /**
     * Cria uma instancia de ConsultarNotificacaoComponent.
     * @param {PacienteService} pacienteService 
     * @param {Router} router 
     * @memberof ConsultarNotificacaoComponent
     */
    constructor(private tarefaService: TarefaService,
                private router:Router,
                private activatedRouter:ActivatedRoute) {
        this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);        
        this.paginacao.number = 1;
        this.centrosIndefinidos = [];
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     * 
     * @memberof ConsultarNotificacaoComponent
     */
    ngOnInit(): void {
        this.listarTarefas(this.paginacao.number)
    }

    /**
     * Método para listar as notificações
     * 
     * @private
     * @param {number} pagina 
     * @memberof ConsultarNotificacaoComponent
     */
    private listarTarefas(pagina:number){
        let ordenacao: AtributoOrdenacaoDTO = new AtributoOrdenacaoDTO();
        ordenacao.atributos.push(new AtributoOrdenacao("dataCriacao", true));

        this.tarefaService.listarTarefasPaginadas(TiposTarefa.ENCONTRAR_CENTRO_TRANSPLANTE.id, 
            Perfis.CONTROLADOR_LISTA, null, pagina -1, this.quantidadeRegistro,
            false, null, StatusTarefas.ABERTA.id, null).then(res => {

                this.paginacao.totalElements = res.totalElements;
                this.paginacao.quantidadeRegistro = this.qtdRegistros;

                if(res.content && res.content.length > 0){
                    res.content.forEach((tarefa: TarefaBase) => {
                        let centroIndefinido: CentroTransplanteADefinirDTO = new CentroTransplanteADefinirDTO();
                        centroIndefinido.tarefa = tarefa;
                        centroIndefinido.rmr = tarefa.processo.paciente.rmr;
                        centroIndefinido.nomePaciente = tarefa.processo.paciente.nome;
                        centroIndefinido.aging = tarefa.aging;
                        this.centrosIndefinidos.push(centroIndefinido);
                    });
                    this.paginacao.content = this.centrosIndefinidos;
                }
            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            }); 
    }


    private exibirMensagemErro(erro: ErroMensagem): void{
        if (erro.listaCampoMensagem != null && erro.listaCampoMensagem.length != 0) {
            this.modalMsgErro.mensagem = erro.listaCampoMensagem[0].mensagem;
            this.modalMsgErro.abrirModal();
        }
    }
    /**
     * Retorna o nome do componente
     * 
     * @returns {string} 
     * @memberof ConsultarNotificacaoComponent
     */
    nomeComponente(): string {
        return ComponenteRecurso.Componente[
            ComponenteRecurso.Componente.DefinirCentroTransplanteComponent];
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
        this.listarTarefas(event.page);
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     * 
     * @param {*} event 
     * @param {*} modal 
     * 
     * @memberOf ConsultaComponent
     */
    selecionarQuantidadePorPagina(event: any, modal: any) {
        this.listarTarefas(1);
    }

    public abrirConfirmacaoCentroTransplante(centroTransplanteIndefinido: CentroTransplanteADefinirDTO): void{
        this.centroIndefinidoSelecionado = centroTransplanteIndefinido;
        this.router.navigateByUrl(
            "/pacientes/centrosTransplante/definir/" + this.centroIndefinidoSelecionado.tarefa.id);
    }

    public formatarDataHora(dataInclusao: Date): string{
        return DateMoment.getInstance().format(dataInclusao, DateTypeFormats.DATE_TIME);
    }

}