import { Component, OnInit, AfterViewInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PrescricaoService } from "../../../../doador/solicitacao/prescricao.service";
import { TarefaBase } from '../../../../shared/dominio/tarefa.base';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { Paginacao } from '../../../../shared/paginacao';
import { ErroUtil } from '../../../../shared/util/erro.util';
import { MessageBox } from '../../../../shared/modal/message.box';
import { ModalUploadAutorizacaoPacienteComponent } from "../../../cadastro/prescricao/autorizacaopaciente/modal.upload.autorizacaopaciente.component";
import { TarefaService } from "app/shared/tarefa.service";
import { SelectCentrosComponent } from "app/shared/component/selectcentros/select.centros.component";
import {StatusTarefas} from "../../../../shared/enums/status.tarefa";
import {autenticacaoService} from "../../../../export.mock.spec";
import {AutenticacaoService} from "../../../../shared/autenticacao/autenticacao.service";



/**
 * Classe que registra o comportamento do componente visual
 * que lista as tarefas de autorizacao de paciente
 *
 * @author bruno.sousa
 */
@Component({
    selector: "consultar-autorizacao-paciente",
    moduleId: module.id,
    templateUrl: "./consultar.autorizacaopaciente.component.html"
})
export class ConsultarAutorizacaoPacienteComponent implements OnInit, AfterViewInit {

    public paginacao: Paginacao;
    public qtdRegistroPorPagina: number = 10;

    @ViewChild("selectCentros")
    private selectCentros: SelectCentrosComponent;

    private _atribuidoAMim: boolean = false;

    public _centroSelecionado = {
        'id': null,
        'tipo': null
    }

    constructor(private prescricaoService: PrescricaoService, private autenticacaoService: AutenticacaoService,
        private tarefaService: TarefaService,
        private translate: TranslateService, private mesageBox: MessageBox){

        this.paginacao = new Paginacao('', 0, this.qtdRegistroPorPagina);
        this.paginacao.number = 1;
    }

    ngOnInit() {

    }

    ngAfterViewInit(): void {
        setTimeout(() => {
            this._centroSelecionado = this.selectCentros.value;
            this.listarPrescricoesSemAutorizacaoPaciente(1);
        });
    }


    nomeComponente(): string {
        return "ConsultarAutorizacaoPacienteComponent";
    }

    /**
     * Listar as tarefas envolvendo a prescricao pendentes de autorizacao do paciente
     *
     * @param pagina numero da pagina a ser consultada
     */
    public listarPrescricoesSemAutorizacaoPaciente(pagina: number) {

        this.prescricaoService.listarPrescricoesSemAutorizacaoPaciente(this._centroSelecionado.id, this._atribuidoAMim,
            pagina - 1, this.qtdRegistroPorPagina).then(res => {
                //let lista: TarefaBase[] = [];
                //if (res.content) {
                    //res.content.forEach(tarefa => {
                      //  lista.push(new TarefaBase().jsonToEntity(tarefa));
                    //});
                //}
                this.paginacao.content = res.content;
                this.paginacao.totalElements = res.totalElements;
                this.paginacao.quantidadeRegistro = this.qtdRegistroPorPagina;
            },
            (error:ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.mesageBox);
            });
    }

    /**
     * Muda a paginação para exibir os dados da página informada no parâmetro.
     */
    mudarPagina(event: any) {
        this.listarPrescricoesSemAutorizacaoPaciente(event.page);
    }

    /**
     * Muda a quantidade de registros exibidos por página e traz a exibição
     * para primeira página.
     */
    mudarQuantidadeRegistroPorPagina(event: any) {
        this.listarPrescricoesSemAutorizacaoPaciente(1);
    }

	public get atribuidoAMim(): boolean {
		return this._atribuidoAMim;
	}

	public set atribuidoAMim(value: boolean) {
        this._atribuidoAMim = value;
    }

    public itemSelecionado(item: any) {
        if (item.idStatusTarefa === StatusTarefas.ABERTA.id ) {
            this.tarefaService.atribuirTarefaParaUsuarioLogado(item.idTarefa).then(() => {
                   this.abrirModal(item);
               },
               (error: ErroMensagem) => {
                   ErroUtil.exibirMensagemErro(error, this.mesageBox, () => {
                       this.listarPrescricoesSemAutorizacaoPaciente(1);
                   });
               });
        }
        else if (item.idStatusTarefa === StatusTarefas.ATRIBUIDA.id && item.nomeUsuarioResponsavelTarefa === this.autenticacaoService.usuarioLogado().nome) {
            this.abrirModal(item);
        }
        else {
            this.translate.get('mensagem.tarefaIndisponivel', {'campo': 'médico'}).subscribe(res => {
                this.mesageBox.alert(res)
                   .withCloseOption(() => {
                       this.listarPrescricoesSemAutorizacaoPaciente(1);
                   } )
                   .show();
            } );

        }
    }

    public mudarCentroTransplante(value): void {
        this._centroSelecionado = value;
        this.listarPrescricoesSemAutorizacaoPaciente(1);
    }

    private abrirModal(item: any) {
        let data: any = {
            "idPrescricao": item.idPrescricao,
            "rmr": item.rmr,
            "nome": item.nomePaciente,
            "dataNascimento": item.dataNascimento,
            "fechar": () => {
                this.listarPrescricoesSemAutorizacaoPaciente(1);
            }
        }
        this.mesageBox.dynamic(data, ModalUploadAutorizacaoPacienteComponent)
           .withTarget(this)
           .withCloseOption((target?: any) => {
               this.tarefaService.removerAtribuicaoTarefa(item.idTarefa).then(res => {
                      this.listarPrescricoesSemAutorizacaoPaciente(1);
                  },
                  (error:ErroMensagem) => {
                      ErroUtil.exibirMensagemErro(error, this.mesageBox, () => {
                          this.listarPrescricoesSemAutorizacaoPaciente(1);
                      });
                  });
           })
           .show();
    }

}
