import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from "@ngx-translate/core";
import { AutenticacaoService } from 'app/shared/autenticacao/autenticacao.service';
import { SelectCentrosComponent } from 'app/shared/component/selectcentros/select.centros.component';
import { ComponenteRecurso } from 'app/shared/enums/componente.recurso';
import { TiposTarefa } from 'app/shared/enums/tipos.tarefa';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { PacienteUtil } from 'app/shared/paciente.util';
import { Paginacao } from 'app/shared/paginacao';
import { TarefaService } from 'app/shared/tarefa.service';
import { ModalUploadAutorizacaoPacienteComponent } from "../../../../paciente/cadastro/prescricao/autorizacaopaciente/modal.upload.autorizacaopaciente.component";
import { StatusTarefas } from "../../../../shared/enums/status.tarefa";
import { ErroUtil } from "../../../../shared/util/erro.util";
import { PedidoWorkup } from '../pedido.workup';
import { WorkupService } from '../workup.service';

/**
 * Classe que representa o componente de consulta de workup
 * @author ergomes
 */
@Component({
    selector: "pedido-workup-centro-coleta",
    moduleId: module.id,
    templateUrl: "./consulta.workup.centro.coleta.component.html"
})
export class ConsultaWorkupCentroColetaComponent implements OnInit, AfterViewInit {

    @ViewChild("selectCentros")
    private selectCentros: SelectCentrosComponent;

    public paginacaoTarefasWorkup: Paginacao;
    public qtdRegistroTarefasWorkup: number = 10;

    public paginacaoSolicitacoesWorkup: Paginacao;
    public qtdRegistroWorkupAtribuido: number = 10;

    private _ehDoadorInternacional: boolean = false;

    public _centroSelecionado = {
        'id': null,
        'tipo': null
    }

    /**
     * Construtor
     * @param serviceDominio - serviço de dominio para buscar os dominios
     * @param correioService - serviço de cep para consultar os endereços
     * @author Rafael Pizão
     */
    constructor(private fb: FormBuilder,
        private autenticacaoService: AutenticacaoService
        , private tarefaService: TarefaService
        , private router:Router
        , private activatedRouter:ActivatedRoute
        , protected workupService: WorkupService
        , private messageBox: MessageBox
        , private translate: TranslateService){
        this.paginacaoTarefasWorkup = new Paginacao(null, 0, this.qtdRegistroTarefasWorkup);
        this.paginacaoTarefasWorkup.number = 1;
        this.paginacaoSolicitacoesWorkup = new Paginacao(null, 0, this.qtdRegistroWorkupAtribuido);
        this.paginacaoSolicitacoesWorkup.number = 1;
    }
    /**
     * Método de inicialização do componente.
     */
    ngOnInit() {
    }

    ngAfterViewInit(): void {
        setTimeout( () => {
            this._centroSelecionado = this.selectCentros.value;
            this.listarTarefasWorkupAtribuidos(this.paginacaoSolicitacoesWorkup.number);
            this.listarSolicitacoesWorkup(this.paginacaoSolicitacoesWorkup.number);
        });
    }

    /**
     * Retorna o nome do componente
     * @returns string
     */
    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaWorkupCentroColetaComponent];
    }

    /**
     * Lista os workups atribuídos (em andamento) para o usuário logado.
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    protected listarTarefasWorkupAtribuidos(pagina: number) {
        this.workupService.listarTarefasWorkup(pagina - 1, this.qtdRegistroWorkupAtribuido, this._centroSelecionado.id, this._centroSelecionado.tipo)
            .then(res=>{
                   this.paginacaoTarefasWorkup.content = res.content;
                   this.paginacaoTarefasWorkup.totalElements = res.totalElements;
                   this.paginacaoTarefasWorkup.quantidadeRegistro = this.qtdRegistroWorkupAtribuido;
            },
            (error:ErroMensagem)=> {
                this.exibirMensagemErro(error);
            });
    }

    /**
     * Lista os todos os workups pendentes (em aberto).
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    protected listarSolicitacoesWorkup(pagina: number) {
        this.workupService.listarSolicitacoesWorkup(pagina - 1, this.qtdRegistroTarefasWorkup, this._centroSelecionado.id, this._centroSelecionado.tipo)
            .then(res=>{
                   this.paginacaoSolicitacoesWorkup.content = res.content;
                   this.paginacaoSolicitacoesWorkup.totalElements = res.totalElements;
                   this.paginacaoSolicitacoesWorkup.quantidadeRegistro = this.qtdRegistroTarefasWorkup;
            },
            (error:ErroMensagem)=> {
                this.exibirMensagemErro(error);
            });
    }

    protected exibirMensagemErro(error: ErroMensagem) {
        if(error.listaCampoMensagem){
            this.messageBox.alert(error.listaCampoMensagem[0].mensagem).show();
        }
    }

    public mudarCentroTransplante(value): void {
        this._centroSelecionado = value;
        this.listarTarefasWorkupAtribuidos(1);
        this.listarSolicitacoesWorkup(1);
    }

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     * @param {*} modal
     *
     * @memberOf ConsultaComponent
     */
    mudarPaginaTarefasWorkupAtribuidas(event: any) {
        this.listarTarefasWorkupAtribuidos(event.page);
    }

    /**
       * Método acionado quando é alterado a quantidade de registros por página
       *
       * @param {*} event
       *
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeTarefasWorkupPorPagina(event: any) {
        this.listarTarefasWorkupAtribuidos(1);
    }

    /**
     * Método acionado quando muda a página
     *
     * @param {*} event
     *
     * @memberOf ConsultaComponent
     */
    mudarPaginaSolicitacoesWorkup(event: any) {
        this.listarSolicitacoesWorkup(event.page);
    }

    /**
       * Método acionado quando é alterado a quantidade de registros por página
       *
       * @param {*} event
       *
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeSolicitacoesWorkupsPorPagina(event: any) {
        this.listarSolicitacoesWorkup(1);
    }

    /**
     * Tela que ira para a tela de pedido de workup.
     */
    abrirDetalheWorkup(pedidoWorkup:PedidoWorkup){
        if(pedidoWorkup){
            this.router.navigateByUrl('/doadores/workup/agendar?idDoador=' + pedidoWorkup.solicitacao.doador.id+'&idPedido='+pedidoWorkup.id);
        }
    }

    irParaAcaoSelecionada(tarefa: any){
        if(tarefa.idTipoTarefa){
            switch (tarefa.idTipoTarefa){
                case TiposTarefa.INFORMAR_PLANO_WORKUP_NACIONAL.id :
                    this.atribuirTarefa(tarefa).then(res => {
                        this.router.navigateByUrl('/doadores/workup/centrotransplante/informar?idPedidoWorkup='+tarefa.idRelacaoEntidade+'&idPrescricao='+tarefa.idPrescricao+'&idTipoTarefa='+tarefa.idTipoTarefa+'&idCentroTransplante='+this._centroSelecionado.id);
                    },
                    (error:ErroMensagem)=> {
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
                    break;
                case TiposTarefa.CONFIRMAR_PLANO_WORKUP.id :
                    this.atribuirTarefa(tarefa).then(res => {
                        this.router.navigateByUrl('/doadores/workup/centrotransplante/aprovar?idPedidoWorkup='+tarefa.idRelacaoEntidade);
                    },
                    (error:ErroMensagem)=> {
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
                    break;

                case TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL.id:
                    this.atribuirTarefa(tarefa).then(res => {
                        this.router.navigateByUrl(`/doadores/workup/resultado/cadastro?idPedidoWorkup=${tarefa.idRelacaoEntidade}&idDoador=${tarefa.idDoador}&idPrescricao=${tarefa.idPrescricao}`);
                    },
                    (error:ErroMensagem)=> {
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
                    break;

                case TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL.id:
                    this.atribuirTarefa(tarefa).then(res => {
                      this.router.navigateByUrl(`/doadores/workup/resultado/avaliacao/nacional?idResultadoWorkup=${tarefa.idRelacaoEntidade}&rmr=${tarefa.rmr}&idTarefa=${tarefa.idTarefa}&idPrescricao=${tarefa.idPrescricao}&identificacaoDoador=${tarefa.identificacaoDoador}`);
                    },
                    (error:ErroMensagem)=> {
                        ErroUtil.exibirMensagemErro(error, this.messageBox);
                    });
                    break;

                case TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL.id:
                    this.atribuirTarefa(tarefa).then(res => {
                           this.router.navigateByUrl(`/doadores/workup/resultado/avaliacao/internacional?idResultadoWorkup=${tarefa.idRelacaoEntidade}&rmr=${tarefa.rmr}&idTarefa=${tarefa.idTarefa}&idPrescricao=${tarefa.idPrescricao}`);
                       },
                       (error:ErroMensagem)=> {
                           ErroUtil.exibirMensagemErro(error, this.messageBox);
                       });
                    break;
                case TiposTarefa.INFORMAR_AUTORIZACAO_PACIENTE.id:

                    this.atribuirTarefa(tarefa).then(res => {
                        this.abrirModal(tarefa);
                     },
                     (error:ErroMensagem)=> {
                         ErroUtil.exibirMensagemErro(error, this.messageBox);
                     });
                    break;
                case TiposTarefa.AGENDAR_COLETA_NACIONAL.id:
                  this.atribuirTarefa(tarefa).then(res => {
                          this.router.navigateByUrl(`/doadores/workup/coleta/agendar?idPedidoColeta=${tarefa.idRelacaoEntidade}&idDoador=${tarefa.idDoador}&idPrescricao=${tarefa.idPrescricao}`);
                      },
                      (error:ErroMensagem)=> {
                          ErroUtil.exibirMensagemErro(error, this.messageBox);
                      });
                  break;
                  case TiposTarefa.INFORMAR_RECEBIMENTO_COLETA.id:
                    this.atribuirTarefa(tarefa).then(res => {
                            this.router.navigateByUrl(`/doadores/workup/recebimentocoleta?idPedidoColeta=${tarefa.idRelacaoEntidade}&rmr=${tarefa.rmr}&identificacaoDoador=${tarefa.identificacaoDoador}&idTipoPrescricao=${tarefa.idTipoPrescricao}`);
                        },
                        (error:ErroMensagem)=> {
                            ErroUtil.exibirMensagemErro(error, this.messageBox);
                        });
                    break;
            }
        }
    }

    private atribuirTarefa(tarefa): Promise<any> {
        return new Promise( (resolve, reject) => {
            if (tarefa.statusTarefa === StatusTarefas.ABERTA.id) {
                resolve(this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.idTarefa));
            }
            else if (tarefa.statusTarefa === StatusTarefas.ATRIBUIDA.id && tarefa.nomeUsuarioResponsavelTarefa === this.autenticacaoService.usuarioLogado().nome) {
                resolve();
            }
            else {
                this.translate.get('mensagem.tarefaIndisponivel', {'campo': 'médico'}).subscribe(res => {
                    reject(new ErroMensagem(null, null, res));
                });
            }
        });
    }

    private abrirModal(item: any) {
        let data: any = {
            "idPrescricao": item.idPrescricao,
            "rmr": item.rmr,
            "nome": item.nomePaciente,
            "dataNascimento": item.dataNascimentoPaciente,
            "fechar": () => {
                this.listarTarefasWorkupAtribuidos(1);
                this.listarSolicitacoesWorkup(1);
            }
        }
        this.messageBox.dynamic(data, ModalUploadAutorizacaoPacienteComponent)
           .withTarget(this)
           .withCloseOption((target?: any) => {
               this.tarefaService.removerAtribuicaoTarefa(item.idTarefa).then(res => {
                      this.listarTarefasWorkupAtribuidos(1);
                      this.listarSolicitacoesWorkup(1);
              },
              (error:ErroMensagem) => {
                  ErroUtil.exibirMensagemErro(error, this.messageBox, () => {
                      this.listarTarefasWorkupAtribuidos(1);
                      this.listarSolicitacoesWorkup(1);
                  });
              });
           })
           .show();
    }



    /*public getEstiloDoStatusPedidoWorkup(pedidoWorkup:PedidoWorkup):string{
        if(pedidoWorkup){
            switch (pedidoWorkup.statusPedidoWorkup.id) {
                case StatusPedidosWorkup.INICIAL:
                    return "workup-status inicial";
                case StatusPedidosWorkup.AGENDADO:
                    return "workup-status agendado";
                case StatusPedidosWorkup.AGUARDANDO_CT:
                    return "workup-status aguardandoct";
                case StatusPedidosWorkup.CANCELADO:
                    return "workup-status cancelado";
                case StatusPedidosWorkup.EM_ANALISE:
                    return "workup-status emanalise";
                case StatusPedidosWorkup.REALIZADO:
                    return "workup-status realizado";
                case StatusPedidosWorkup.CONFIRMADO_CT:
                    return "workup-status emanalise";
            }
        }
        return "";
    }*/

    formatarData(data: string): string {
        if (!data) {
            return '';
        }
        return PacienteUtil.converterStringEmData(data).toLocaleDateString();
    }

}
