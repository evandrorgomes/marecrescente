import { AvaliarPrescricaoService } from './../../../cadastro/prescricao/avaliacao/avaliar.prescricao.service';
import { Component, OnInit } from "@angular/core";
import { Router } from '@angular/router';
import { TarefaBase } from '../../../../shared/dominio/tarefa.base';
import { MessageBox } from '../../../../shared/modal/message.box';
import { OrdenacaoTarefa } from '../../../../shared/enums/ordenacao.tarefas';
import { Perfis } from '../../../../shared/enums/perfis';
import { StatusTarefas } from '../../../../shared/enums/status.tarefa';
import { TiposTarefa } from '../../../../shared/enums/tipos.tarefa';
import { ErroMensagem } from '../../../../shared/erromensagem';
import { Paginacao } from '../../../../shared/paginacao';
import { PermissaoRotaComponente } from '../../../../shared/permissao.rota.componente';
import { TarefaService } from '../../../../shared/tarefa.service';
import { ErroUtil } from '../../../../shared/util/erro.util';
import { TiposDoador } from 'app/shared/enums/tipos.doador';
import {AvaliacoesPrescricaoDto} from "../../../../shared/dto/avaliacoes.prescricao.dto";


@Component({
    selector: 'consulta-avaliacoes-prescricao',
    templateUrl: './consulta.avaliacoes.prescricao.component.html',
})
export class ConsultaAvaliacoesPrescricaoComponent implements PermissaoRotaComponente, OnInit {

    paginacaoAvaliacaoPendente: Paginacao;
    qtdRegistroAvaliacaoPendente: number = 10;

    constructor(private router: Router,
        private tarefaService: TarefaService,
        private avaliarPrescricaoService: AvaliarPrescricaoService,
        private messaeBox: MessageBox ) {

        this.paginacaoAvaliacaoPendente = new Paginacao('', 0, this.qtdRegistroAvaliacaoPendente);
        this.paginacaoAvaliacaoPendente.number = 1;
    }

    ngOnInit() {
        this.listarAvaliacoesPendentes(this.paginacaoAvaliacaoPendente.number);
    }

    nomeComponente(): string {
        return "ConsultaAvaliacoesPrescricaoComponent";
    }

    /**
       * Método acionado quando muda a página
       *
       * @param {*} event
       * @param {*} modal
       *
       * @memberOf ConsultaComponent
       */
    mudarPaginaAvaliacoesPendentes(event: any) {
        console.log(event);
        this.listarAvaliacoesPendentes(event.page)
    }

    public irParaAvaliacao(avaliacaoPrescricaoDTO: AvaliacoesPrescricaoDto): void {
        if (avaliacaoPrescricaoDTO.idStatusTarefa == StatusTarefas.ABERTA.id) {
            this.tarefaService.atribuirTarefaParaUsuarioLogado(avaliacaoPrescricaoDTO.idTarefa).then(res => {
                this.router.navigateByUrl(`/prescricoes/avaliacao/${avaliacaoPrescricaoDTO.idAvaliacaoPrescricao}`);
            },
           (error: ErroMensagem) => {
               ErroUtil.exibirMensagemErro(error, this.messaeBox);

           });
        }
        else {
            this.router.navigateByUrl(`/prescricoes/avaliacao/${avaliacaoPrescricaoDTO.idAvaliacaoPrescricao}`);
        }
    }

    /**
       * Método acionado quando é alterado a quantidade de registros por página
       *
       * @param {*} event
       * @param {*} modal
       *
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeAvaliacoesPendentesPorPagina(event: any, modal: any) {
        this.listarAvaliacoesPendentes(1);
    }

    /**
     * Método para listar as avaliações pendentes
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    private listarAvaliacoesPendentes(pagina: number) {
        this.avaliarPrescricaoService.listarAvaliacoesPendentes(pagina -1, this.qtdRegistroAvaliacaoPendente)
            .then(res => {
                let avaliacoes: AvaliacoesPrescricaoDto[] = [];
                if(res && res.content){
                    res.content.forEach(resultado => {
                        avaliacoes.push(new AvaliacoesPrescricaoDto().jsonToEntity(resultado));
                    });
                }

                this.paginacaoAvaliacaoPendente.content = avaliacoes;
                this.paginacaoAvaliacaoPendente.totalElements = res.totalElements;
                this.paginacaoAvaliacaoPendente.quantidadeRegistro = this.qtdRegistroAvaliacaoPendente;
            },
            (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messaeBox);

            });
    }

    ehInternacional(tipoDoador: any): boolean {
        return tipoDoador === TiposDoador.CORDAO_INTERNACIONAL || tipoDoador === TiposDoador.INTERNACIONAL;
    }

    ehNacional(tipoDoador: any): boolean {
        return tipoDoador === TiposDoador.CORDAO_NACIONAL || tipoDoador === TiposDoador.NACIONAL;
    }

    ehMedula(tipoDoador: any): boolean {
        return tipoDoador == TiposDoador.NACIONAL || tipoDoador == TiposDoador.INTERNACIONAL;
    }

    ehCordao(tipoDoador: any): boolean {
        return tipoDoador == TiposDoador.CORDAO_NACIONAL || tipoDoador == TiposDoador.CORDAO_INTERNACIONAL;
    }

}
