import { ErroUtil } from 'app/shared/util/erro.util';
import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AutenticacaoService } from 'app/shared/autenticacao/autenticacao.service';
import { Paginacao } from 'app/shared/paginacao';
import { PermissaoRotaComponente } from 'app/shared/permissao.rota.componente';
import { PedidoTransferenciaCentroService } from 'app/shared/service/pedido.transferencia.centro.service';
import { ErroMensagem } from 'app/shared/erromensagem';
import { MessageBox } from 'app/shared/modal/message.box';
import { Cid } from 'app/shared/dominio/cid';
import { ComponenteRecurso } from 'app/shared/enums/componente.recurso';
import { TarefaBase } from 'app/shared/dominio/tarefa.base';
import { CentroTransplante } from 'app/shared/dominio/centro.transplante';
import { DataUtil } from 'app/shared/util/data.util';
import { TarefaService } from 'app/shared/tarefa.service';
import { UsuarioLogado } from '../../../shared/dominio/usuario.logado';

@Component({
    selector: 'consulta-transferencia-centro',
    templateUrl: './consulta.transferencia.centro.component.html'
    //styleUrls: ['./consulta.avaliacoes.component.css']
})
export class ConsultaTransferenciaCentroComponent implements PermissaoRotaComponente, OnInit, AfterViewInit {

    paginacaoTransferenciaCentroPendente: Paginacao;
    qtdRegistroTransferenciaCentroPendente: number = 10;

    public _centroSelecionado = {
        'id': null,
        'tipo': null
    }

    constructor(private router: Router,
        private autenticacaoService: AutenticacaoService, private messageBox: MessageBox,
        private pedidoTransferenciaCentroService: PedidoTransferenciaCentroService,
        private tarefaService: TarefaService) {

        this.paginacaoTransferenciaCentroPendente = new Paginacao('', 0, this.qtdRegistroTransferenciaCentroPendente);
        this.paginacaoTransferenciaCentroPendente.number = 1;

    }

    ngOnInit() {
        
    }

    ngAfterViewInit(){
        setTimeout(() => {
            this.listarTransferenciasPendentes(this.paginacaoTransferenciaCentroPendente.number);    
        });
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.ConsultaTransferenciaCentroComponent];
    }

    /**
     * Método para listar as transferencias pendentes
     * 
     * @param pagina numero da pagina a ser consultada
     * 
     * @memberOf ConsultaComponent
     */
    private listarTransferenciasPendentes(pagina: number) {
        this.pedidoTransferenciaCentroService.listarTarefas(true, this._centroSelecionado.id, pagina - 1, this.qtdRegistroTransferenciaCentroPendente)
            .then((res: any) => {                
                let tarefas: TarefaBase[] = [];
                if (res && res.content) {
                    res.content.forEach(content => {
                        tarefas.push(new TarefaBase().jsonToEntity(content));
                    })
                }

                this.paginacaoTransferenciaCentroPendente.content = tarefas;
                this.paginacaoTransferenciaCentroPendente.totalElements = res.totalElements;
                this.paginacaoTransferenciaCentroPendente.quantidadeRegistro = this.qtdRegistroTransferenciaCentroPendente;
                this.paginacaoTransferenciaCentroPendente.number = pagina;
            })
            .catch((erro: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(erro, this.messageBox);
            });
    }

    /**
     * Indica se a cid informada é contemplada em portaria e muda o style de acordo
     * com a informação.
     * 
     * @param cid 
     */
    public indicarSeContempladoEmPortaria(cid: Cid): string {
        if (cid.transplante) {
            return 'azul';
        } else {
            return 'amarelo';
        } 
    }

    executarConsulta(): void {
        this.listarTransferenciasPendentes(1);
    } 

    calcularIdade(dataNascimento: Date): number {
        return DataUtil.calcularIdade(dataNascimento);
    }

    exibirFiltro(): boolean {
        let centros: any[] = this.autenticacaoService.usuarioLogado().centros;
        return !centros ? false : centros.length > 1;
    }

    abrirDetalheTransferencia(tarefa: TarefaBase): void {
        this.tarefaService.atribuirTarefaParaUsuarioLogado(tarefa.id).then(res => {
            this.router.navigateByUrl("/pacientes/transferencias/" + tarefa.objetoRelacaoEntidade.id + "?idTarefa=" + tarefa.id);
        }, (error: ErroMensagem) => {
            ErroUtil.exibirMensagemErro(error, this.messageBox, ()=> {
                this.listarTransferenciasPendentes(1);
            });
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
    mudarPaginaTransferenciasPendentes(event: any) {
        this.listarTransferenciasPendentes(event.page);
    }

    /**
       * Método acionado quando é alterado a quantidade de registros por página
       * 
       * @param {*} event 
       * @param {*} modal 
       * 
       * @memberOf ConsultaComponent
       */
      selecionaQuantidadeTransferenciasPendentesPorPagina(event: any) {
        this.listarTransferenciasPendentes(1);
    }

}