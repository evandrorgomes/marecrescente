import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PrescricaoService } from 'app/doador/solicitacao/prescricao.service';
import { TiposDoador } from 'app/shared/enums/tipos.doador';
import { StatusSolicitacao } from '../../../shared/enums/status.solicitacao';
import { ErroMensagem } from '../../../shared/erromensagem';
import { MessageBox } from '../../../shared/modal/message.box';
import { Paginacao } from '../../../shared/paginacao';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { TarefaService } from '../../../shared/tarefa.service';
import { ErroUtil } from '../../../shared/util/erro.util';
import { PacienteService } from '../../paciente.service';
import { SelectCentrosComponent } from './../../../shared/component/selectcentros/select.centros.component';
import { ConsultaPrescricaoDTO } from './consulta.prescricao.dto';

/**
 * Classe que representa o componente de consulta de tarefas de pacientes disponiveis para prescrição
 * @author Bruno Sousa
 */
@Component({
    selector: 'consulta-prescricao',
    templateUrl: './consultar.prescricao.component.html',
})
export class ConsultarPrescricaoComponent implements PermissaoRotaComponente, OnInit, AfterViewInit {

    @ViewChild("modalMsg")
    private modalMsg;

    @ViewChild("selectCentros")
    private selectCentros: SelectCentrosComponent;

    public paginacaoTarefasEmAberto: Paginacao;
    public qtdRegistroTarefasEmAberto: number = 10;

    private _status: number[] = [StatusSolicitacao.ABERTA, StatusSolicitacao.CONCLUIDA];

    public paginacaoPacienteComPrescricao: Paginacao;
    public qtdRegistrosPacienteComPrescricao: number = 10;

    public _centroSelecionado = {
        'id': null,
        'tipo': null
    }

	constructor(private router: Router, private translate: TranslateService,
        private tarefaService: TarefaService,  private messageBox: MessageBox,
        private prescricaoService: PrescricaoService, private pacienteService: PacienteService) {

        this.paginacaoTarefasEmAberto = new Paginacao('', 0, this.qtdRegistroTarefasEmAberto);
        this.paginacaoTarefasEmAberto.number = 1;

        this.paginacaoPacienteComPrescricao = new Paginacao('', 0, this.qtdRegistrosPacienteComPrescricao);
        this.paginacaoPacienteComPrescricao.number = 1;

	}

    ngOnInit(): void {

    }

    ngAfterViewInit(): void {
        setTimeout( () => {
            this._centroSelecionado = this.selectCentros.value;
            this.listarPrescricoesDisponiveis(this.paginacaoTarefasEmAberto.number);
            this.listarPacienteComPrescricao(this.paginacaoPacienteComPrescricao.number);
        });
    }

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    mudarPaginaPacientesSemPrescricao(event: any) {
        this.listarPrescricoesDisponiveis(event.page)
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultarPrescricaoComponent
     */
    selecionaQuantidadeTarefasEmAbertoPorPagina(event: any, modal: any) {
        this.listarPrescricoesDisponiveis(1);
    }

    /**
     * Método para listar as avaliações pendentes
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    private listarPrescricoesDisponiveis(pagina: number) {
        this.prescricaoService.listarPrescricoesDisponiveis(this._centroSelecionado.id, pagina -1, this.qtdRegistroTarefasEmAberto).then(res => {
               let consultaDTO: ConsultaPrescricaoDTO[] = [];
               if (res.content) {
                   res.content.forEach(item => {
                       let consultaPrescricaoDTO: ConsultaPrescricaoDTO = new ConsultaPrescricaoDTO();

                       consultaPrescricaoDTO.rmr = item.rmr
                       consultaPrescricaoDTO.nomePaciente = item.nomePaciente
                       consultaPrescricaoDTO.agingDaTarefa = item.agingDaTarefa;

                       consultaDTO.push(consultaPrescricaoDTO);
                   });
               }

            this.paginacaoTarefasEmAberto.content = consultaDTO;
            this.paginacaoTarefasEmAberto.totalElements = res.totalElements;
            this.paginacaoTarefasEmAberto.quantidadeRegistro = this.qtdRegistroTarefasEmAberto;
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

    }

    /**
     * Método para listar os pacientes com prescrição
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    private listarPacienteComPrescricao(pagina: number) {

        this.prescricaoService.listarPrescricoesPorStatusECentroTransplante (this._status, this._centroSelecionado.id, pagina -1,
            this.qtdRegistrosPacienteComPrescricao).then(res => {

                let consultaDTO: ConsultaPrescricaoDTO[] = [];
                if (res.content) {
                    res.content.forEach(item => {
                        let consultaPrescricaoDTO: ConsultaPrescricaoDTO = new ConsultaPrescricaoDTO();

                        consultaPrescricaoDTO.rmr = item.rmr
                        consultaPrescricaoDTO.nomePaciente = item.nomePaciente
                        consultaPrescricaoDTO.peso = item.peso;
                        consultaPrescricaoDTO.status = item.status;
                        consultaPrescricaoDTO.idPrescricao = item.idPrescricao;
                        consultaPrescricaoDTO.idDoador = item.idDoador;
                        consultaPrescricaoDTO.identificadorDoador = item.identificadorDoador;
                        consultaPrescricaoDTO.idTipoDoador = item.idTipoDoador;

                        consultaDTO.push(consultaPrescricaoDTO);
                    });
                }

                this.paginacaoPacienteComPrescricao.content = consultaDTO;
                this.paginacaoPacienteComPrescricao.totalElements = res.totalElements;
                this.paginacaoPacienteComPrescricao.quantidadeRegistro = this.qtdRegistrosPacienteComPrescricao;
            },
            (error: ErroMensagem) => {
                this.exibirMensagemErro(error);
            });
    }

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    mudarPaginaPacientesComPrescricao(event: any) {
        this.listarPacienteComPrescricao(event.page)
    }

    /**
     * Método acionado quando é alterado a quantidade de registros por página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultarPrescricaoComponent
     */
    selecionaQuantidadePacientesComPrescricaoPorPagina(event: any, modal: any) {
        this.listarPacienteComPrescricao(1);
    }

    /**
     * Carrega a tela de detalhe
     *
     * @param {number} rmr
     *
     * @memberOf ConsultaPrescricaoComponent
     */
    carregaTelaDetalhe(rmr: number) {
        this.router.navigateByUrl("/pacientes/" + rmr);
    }

    visualizarDetalhe(consultaPrescricaoDTO: ConsultaPrescricaoDTO) {

        let idPrescricao: number = consultaPrescricaoDTO.idPrescricao 
        //this.router.navigateByUrl("/prescricoes/" + idPrescricao + "/prescricao");
        this.router.navigateByUrl("/prescricoes/" + idPrescricao);
    }

     /**
     * Carrega a tela de analise de match
     *
     * @param {number} rmr
     *
     * @memberOf ConsultaPrescricaoComponent
     */
    carregaTelaAnaliseMatch(rmr: number) {
        this.router.navigateByUrl("/pacientes/" + rmr + "/matchs");
    }

    private exibirMensagemErro(error: ErroMensagem) {
        error.listaCampoMensagem.forEach(obj => {
            this.modalMsg.mensagem = obj.mensagem;
        })
        this.modalMsg.abrirModal();
    }

    nomeComponente(): string {
        return 'ConsultarPrescricaoComponent';
    }

    public mudarCentroTransplante(value): void {
        this._centroSelecionado = value;
        this.listarPrescricoesDisponiveis(1);
        this.listarPacienteComPrescricao(1);
    }

    public ehMedula(tipoDoador: number): boolean {
        if (tipoDoador != null) {
            return tipoDoador === TiposDoador.NACIONAL.valueOf() || tipoDoador === TiposDoador.INTERNACIONAL.valueOf();
        }
        return false;
    }

    public ehCordao(tipoDoador: number): boolean {
        if (tipoDoador) {
            return tipoDoador === TiposDoador.CORDAO_NACIONAL.valueOf() || tipoDoador === TiposDoador.CORDAO_INTERNACIONAL.valueOf();
        }
        return false;
    }
}
