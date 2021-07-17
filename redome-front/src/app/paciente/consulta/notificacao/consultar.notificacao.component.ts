import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HeaderPacienteComponent } from 'app/paciente/consulta/identificacao/header.paciente.component';
import { Notificacao } from 'app/paciente/notificacao';
import { SelectCentrosComponent } from 'app/shared/component/selectcentros/select.centros.component';
import { MessageBox } from 'app/shared/modal/message.box';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { ErroMensagem } from '../../../shared/erromensagem';
import { Paginacao } from '../../../shared/paginacao';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { RouterUtil } from '../../../shared/util/router.util';
import { NotificacaoService } from '../../notificacao.service';
import { PacienteService } from '../../paciente.service';
import { AutenticacaoService } from './../../../shared/autenticacao/autenticacao.service';
import { ErroUtil } from './../../../shared/util/erro.util';
import { PacienteTarefaDto } from './paciente.tarefa.dto';


@Component({
    selector: 'consultar-notificacao',
    moduleId: module.id,
    templateUrl: './consultar.notificacao.component.html'
})

export class ConsultarNotificacaoComponent implements PermissaoRotaComponente, OnInit, AfterViewInit {

    @ViewChild("headerPaciente")
    private headerPaciente: HeaderPacienteComponent;

    paginacao: Paginacao;
    quantidadeRegistro: number = 10;

    private tarefaAtual: PacienteTarefaDto;
    private rmrSelecionado: number;
    private _idNotificacao: number;
    public _centroSelecionado = {
        'id': null,
        'tipo': null
    }

    public _deveExibirColunaRmr: Boolean = true;
    public _deveEsconderHeaderPaciente: Boolean = true;


    @ViewChild("selectCentros")
    private selectCentros: SelectCentrosComponent;

    /**
     * Cria uma instancia de ConsultarNotificacaoComponent.
     * @param {PacienteService} pacienteService 
     * @param {Router} router 
     * @memberof ConsultarNotificacaoComponent
     */
    constructor(private autenticacaoService: AutenticacaoService,
        private router: Router,
        private activatedRouter: ActivatedRoute,
        private notificacaoService: NotificacaoService,
        private messageBox: MessageBox) {
        this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);
        this.paginacao.number = 1;
    }

    /**
     * Inicializa a classe com os dados buscando no serviço REST
     * 
     * @memberof ConsultarNotificacaoComponent
     */
    ngOnInit(): void {
        RouterUtil.recuperarParametroDaActivatedRoute(this.activatedRouter, "idPaciente").then(res => {
            if (res) {
                this.rmrSelecionado = res;                
            }            
        });
        
    }

    ngAfterViewInit(): void {
        setTimeout(() => {
            if (this.rmrSelecionado) {
                this._deveExibirColunaRmr = false;
                this._deveEsconderHeaderPaciente = false;
                if (this.headerPaciente) {
                    this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this.rmrSelecionado);
                }
            }
            this._centroSelecionado = this.selectCentros.value;
            this.listarNotificacoes(this.paginacao.number)
        })
    }

    /**
     * Método para listar as notificações
     * 
     * @private
     * @param {number} pagina 
     * @memberof ConsultarNotificacaoComponent
     */
    private listarNotificacoes(pagina: number) {

        let idCentroTransplante: number = this._centroSelecionado.id;
        let meusPacientes: boolean = null;
        if (!this.deveTerPerfilMedicoOuAvaliadorOuMedicoTransplantador()) {
            idCentroTransplante = null;
        }
        if (idCentroTransplante == -99) {
            idCentroTransplante = null;
            meusPacientes = true;
        }

        // this.notificacaoService.marcarCienteNaNotificacaoSelecionada(561
        // ).then(res => {
        //     let notificacoes: Notificacao[] = [];
        //     if (res.content) {
        //         res.content.forEach(element => {
        //             notificacoes.push(new Notificacao().jsonToEntity(element));
        //         });
        //     }
        //     this.paginacao.content = notificacoes;
        //     this.paginacao.totalElements = res.totalElements;
        //     this.paginacao.quantidadeRegistro = this.quantidadeRegistro;
        // },
        // (error: ErroMensagem) => {
        //     ErroUtil.exibirMensagemErro(error, this.messageBox);
        // });

        this.notificacaoService.listarNotificacoes(idCentroTransplante, 
            this.rmrSelecionado, meusPacientes, pagina - 1, this.quantidadeRegistro).then(res => {
            let notificacoes: Notificacao[] = [];
            if (res.content) {
                res.content.forEach(element => {
                    notificacoes.push(new Notificacao().jsonToEntity(element));
                });
            }
            this.paginacao.content = notificacoes;
            this.paginacao.totalElements = res.totalElements;
            this.paginacao.quantidadeRegistro = this.quantidadeRegistro;
        },
        (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });

    }

    /**
     * Abre o modal de notificação para que o médico dê
     * o OK que foi notificado.
     * 
     * @private
     * @memberof ConsultarNotificacaoComponent
     */
    public visualizarNotificacao(notificacao: Notificacao) {
        this._idNotificacao = notificacao.id;
        this.messageBox.alert(notificacao.descricao)
            .showButtonOk(true)
            .withCloseOption((target?: any) => {
                if (!notificacao.lido) {
                    this.notificacaoService.marcarCienteNaNotificacaoSelecionada(this._idNotificacao).then(res => {
                        notificacao.lido = true;
                    },
                    (error: ErroMensagem) => {
                        ErroUtil.exibirMensagemErro(error, this.messageBox);                    
                    });
                }
            })
            .show();
    }

    /**
     * Retorna o nome do componente
     * 
     * @returns {string} 
     * @memberof ConsultarNotificacaoComponent
     */
    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarNotificacaoComponent];
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
        this.listarNotificacoes(event.page);
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
        this.listarNotificacoes(1);
    }

    public notificacaoLida(notificacao: Notificacao): boolean {
        return notificacao.lido;
    }

    public retornarApenasUmaLinha(value: string): string {
        if (value.indexOf("<br/>") != -1) {
            return value.slice(0, value.indexOf("<br/>"));
        }
        return value;
    }

    public deveExibirBotaoVoltar(): boolean {
        return this.rmrSelecionado != null;
    }

    public mudarCentroTransplante(value): void {
        this._centroSelecionado = value;
        this.listarNotificacoes(1);
    }

    public deveExibirComboCentros(): boolean {
        return this.deveExibirBotaoVoltar() || !this.deveTerPerfilMedicoOuAvaliadorOuMedicoTransplantador();
    }

    public deveTerPerfilMedicoOuAvaliadorOuMedicoTransplantador(): boolean {
        return this.autenticacaoService.temPerfilMedico() || 
            this.autenticacaoService.temPerfilAvaliador() ||
            this.autenticacaoService.temPerfilMedicoTransplantador()
    }

 

}