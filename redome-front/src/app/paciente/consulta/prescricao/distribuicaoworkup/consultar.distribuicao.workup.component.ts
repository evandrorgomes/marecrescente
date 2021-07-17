import {Component, OnInit} from "@angular/core";
import {PermissaoRotaComponente} from "../../../../shared/permissao.rota.componente";
import {ComponenteRecurso} from "../../../../shared/enums/componente.recurso";
import {Paginacao} from "../../../../shared/paginacao";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {MessageBox} from "../../../../shared/modal/message.box";
import {ErroMensagem} from "../../../../shared/erromensagem";
import {ErroUtil} from "../../../../shared/util/erro.util";
import {DistribuicaoWorkupService} from "../../../../shared/service/distribuicao.workup.service";
import {PacienteUtil} from "../../../../shared/paciente.util";
import {TipoPrescricao} from "../../../../shared/dominio/tipo.prescricao";
import {HistoricoNavegacao} from "../../../../shared/historico.navegacao";
import {ModalRecusarTransferenciaCentroComponent} from "../../../transferencia/detalhe/modal/modal.recusar.transferencia.centro.component";
import {StatusTarefas} from "../../../../shared/enums/status.tarefa";
import {TarefaService} from "../../../../shared/tarefa.service";
import {ModalDistribuicaoWorkupComponent} from "./modaldistirbuicaoworkup/modal.distribuicao.workup.component";

/**
 * Classe que representa o componente de consulta de tarefas da distribuicao do workup.
 * @author Bruno Sousa
 */
@Component({
   selector: 'consulta-distribuicao-workup',
   templateUrl: './consultar.distribuicao.workup.component.html',
})
export class ConsultarDistribuicaoWorkupComponent implements PermissaoRotaComponente, OnInit {

   public _paginacao: Paginacao;
   public _quantidadeRegistro: number = 10;

   private distribuicaoPorUsuario: Map<string, any[]>;

   public _acordionOpen: boolean[]

   private _distribuicao: any;

   constructor(private router: Router, private translate: TranslateService,
               private messageBox: MessageBox, private distribuicaoWorkupService: DistribuicaoWorkupService,
               private tarefaService: TarefaService) {

      this._paginacao = new Paginacao([], 1, this._quantidadeRegistro);
      this._paginacao.number = 1;
   }

   ngOnInit(): void {
      this.listarDistribuicoes(this._paginacao.number);
      this.listarDistribuicoesPorUsuario();
   }

   nomeComponente(): string {
      return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultarDistribuicaoWorkupComponent];
   }

   private listarDistribuicoes(pagina: number) {

      this.distribuicaoWorkupService.listarTarefasDistribuicoesWorkup(pagina - 1, this._quantidadeRegistro)
      .then((res: any) => {
         this._paginacao.content = res.content;
         this._paginacao.totalElements = res.totalElements;
         this._paginacao.quantidadeRegistro = this._quantidadeRegistro;
         this._paginacao.number = pagina;
      })
      .catch((erro: ErroMensagem) => {
         ErroUtil.exibirMensagemErro(erro, this.messageBox);
      });

   }

   private listarDistribuicoesPorUsuario() {
      this.distribuicaoWorkupService.listarDistribuicoesWorkupPorUsuario()
         .then(res => {
            if (res) {
               this.distribuicaoPorUsuario = new Map<string, any[]>();
               this._acordionOpen = [];
               for (let key in res) {
                  let lista = []
                  for (let index in res[key]) {
                     lista.push(res[key][index].distribuicoesWorkup);
                  }
                  this.distribuicaoPorUsuario.set(key, lista);
                  this._acordionOpen.push(false);

               }
            }

         })
         .catch((erro: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(erro, this.messageBox);
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
   _mudarPagina(event: any) {
      this.listarDistribuicoes(event.page);
   }

   /**
    * Método acionado quando é alterado a quantidade de registros por página
    *
    * @param {*} event
    * @param {*} modal
    *
    * @memberOf ConsultaComponent
    */
   _selecinaQuantidadePorPagina(event: any) {
      this.listarDistribuicoes(1);
   }

   formatarData(data: string): string {
      if (!data) {
         return '';
      }
      return PacienteUtil.converterStringEmData(data).toLocaleDateString();
   }

   get _usuarios(): any[] {
      if (this.distribuicaoPorUsuario && this.distribuicaoPorUsuario.size != 0) {
         let retorno: any[] = [];
         this.distribuicaoPorUsuario.forEach((value, key) => {
            let dados = {
               nome: key,
               quantidadeCordao: value.filter(data => data != null && data.tipoPrescricao.toLowerCase() == TipoPrescricao[TipoPrescricao.CORDAO].toLowerCase() ).length,
               quantidadeMedula: value.filter(data => data != null && data.tipoPrescricao.toLowerCase() == TipoPrescricao[TipoPrescricao.MEDULA].toLowerCase() ).length,
               total: value.filter(data => data != null).length
            }
            retorno.push(dados);
         })
         return retorno;
      }
      return null;
   }

   public distribucoesPorUsuario(usuario: string): any[] {
      return this.distribuicaoPorUsuario.get(usuario);

   }

   public abrirDetalhe(index: number): void {
      if (index == 0) {
         for (let idx = 1; idx <= this._acordionOpen.length - 1; idx++) {
            this._acordionOpen[idx] = false;
         }
      }
      else if (index == (this._acordionOpen.length - 1)) {
         for (let idx = (this._acordionOpen.length - 2); idx >= 0; idx--) {
            this._acordionOpen[idx] = false;
         }
      }
      else {
         for (let idx = 0; idx < index; idx++) {
            this._acordionOpen[idx] = false;
         }
         for (let idx = (this._acordionOpen.length - 1); idx > index; idx--) {
            this._acordionOpen[idx] = false;
         }
      }
      if (this._usuarios[index].total == 0) {
         this._acordionOpen[index] = false;
      }
      else {
         this._acordionOpen[index] = !this._acordionOpen[index];
      }
   }

   distribuir(distribuicao) {
      if (distribuicao.statusTarefaDistribuicaoWorkup == StatusTarefas.ABERTA.id) {
         this.tarefaService.atribuirTarefaParaUsuarioLogado(distribuicao.idTarefaDistribuicaoWorkup).then(res => {
            this.abrirModalDistribuicao(distribuicao);
         })
         .catch((erro: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(erro, this.messageBox);
         });
         return
      }
      this.abrirModalDistribuicao(distribuicao);
   }

   redistribuir(distribuicao) {
      this._distribuicao = distribuicao;
      let data = {
         distribuicao,
         modo: 'redistribuir',
         recarregar: () => {
           this.recarregar();
         }
      }
      this.messageBox.dynamic(data, ModalDistribuicaoWorkupComponent)
         .withTarget(this)
         .withCloseOption((target) => {
         })
         .show();

   }

   private abrirModalDistribuicao(distribuicao) {
      this._distribuicao = distribuicao;
      let data = {
         distribuicao,
         modo: 'distribuir',
         recarregar: () => {
            this.recarregar();
         }
      }
      this.messageBox.dynamic(data, ModalDistribuicaoWorkupComponent)
         .withTarget(this)
         .withCloseOption((target) => {
            this.tarefaService.removerAtribuicaoTarefa(distribuicao.idTarefaDistribuicaoWorkup).then(res => {
               this._distribuicao = null;
            })
            .catch((erro: ErroMensagem) => {
               ErroUtil.exibirMensagemErro(erro, this.messageBox);
            });
         })
         .show();
   }

   private recarregar() {
      this._distribuicao = null;
      this.listarDistribuicoes(1);
      this.listarDistribuicoesPorUsuario();
   }


}
