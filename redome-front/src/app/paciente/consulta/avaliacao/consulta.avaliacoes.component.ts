import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { Cid } from '../../../shared/dominio/cid';
import { StatusAvaliacao } from '../../../shared/enums/status.avaliacao';
import { ErroMensagem } from '../../../shared/erromensagem';
import { Paginacao } from '../../../shared/paginacao';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { AvaliacaoService } from '../../avaliacao/avaliacao.service';
import { SelectCentrosComponent } from './../../../shared/component/selectcentros/select.centros.component';
import { PacienteAvaliacaoTarefaDTO } from './paciente.avaliacao.tarefa.dto';

@Component({
  selector: 'consulta-avaliacoes',
  templateUrl: './consulta.avaliacoes.component.html',
  styleUrls: ['./consulta.avaliacoes.component.css']
})
export class ConsultaAvaliacoesComponent implements PermissaoRotaComponente, OnInit {

  paginacaoAvaliacaoPendente: Paginacao;
  qtdRegistroAvaliacaoPendente: number = 10;
  public isContempladoPortaria: string;

  paginacaoAvaliacaoAtribuida: Paginacao;
  qtdRegistroAvaliacaoAtribuida: number = 10;


  @ViewChild("modalMsg")
  private modalMsg;

  @ViewChild("selectCentros")
  private selectCentros: SelectCentrosComponent;

  public _centroSelecionado = {
    'id': null,
    'tipo': null
  }

  constructor(private router:Router,
     private autenticacaoService: AutenticacaoService,
     private avaliacaoService: AvaliacaoService) {

    this.paginacaoAvaliacaoPendente = new Paginacao('', 0, this.qtdRegistroAvaliacaoPendente);
    this.paginacaoAvaliacaoPendente.number = 1;

    this.paginacaoAvaliacaoAtribuida = new Paginacao('', 0, this.qtdRegistroAvaliacaoAtribuida);
    this.paginacaoAvaliacaoAtribuida.number = 1;
  }

  ngOnInit() {
    Promise.resolve(this.selectCentros).then(() => {
        this._centroSelecionado = this.selectCentros.value;
        this.listarAvaliacoesPendentes(this.paginacaoAvaliacaoPendente.number);
        this.listarAvaliacoesAtribuidasAoUsuarioLogado(this.paginacaoAvaliacaoAtribuida.number);

    });

  }

  nomeComponente(): string {
    return "ConsultaAvaliacoesComponent";
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
        this.listarAvaliacoesPendentes(event.page);
    }

    private irParaAvaliacao(rmr:number):void{
        this.router.navigateByUrl('/pacientes/' + rmr + '/avaliacaoAvaliador')
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
        this.avaliacaoService.listarAvaliacoesPacientes(pagina - 1, this.qtdRegistroAvaliacaoPendente, StatusAvaliacao.ABERTA, this._centroSelecionado.id)
            .then((res: any) => {
                let avaliacoes: PacienteAvaliacaoTarefaDTO[] = [];
                if(res && res.content){

                    res.content.forEach(content => {
                        let dto:PacienteAvaliacaoTarefaDTO = new PacienteAvaliacaoTarefaDTO();
                        let cid:Cid = new Cid(content.cid.id);
                        cid.codigo = content.cid.codigo;
                        cid.descricao = content.cid.descricao;
                        cid.transplante = content.cid.transplante;
                        dto.cid = cid;
                        dto.idade = content.idade;
                        dto.nome = content.nome;
                        dto.rmr = content.rmr;
                        dto.tempoCadastro = content.tempoCadastro;
                        avaliacoes.push(dto);
                    })
                }

                this.paginacaoAvaliacaoPendente.content = avaliacoes;

                this.paginacaoAvaliacaoPendente.totalElements = res ? res.totalElements : 0;
                this.paginacaoAvaliacaoPendente.quantidadeRegistro = this.qtdRegistroAvaliacaoPendente;
                this.paginacaoAvaliacaoPendente.number = pagina;
            })
            .catch((erro: ErroMensagem) => {
                this.exibirMensagemErro(erro);
            });
    }

    private exibirMensagemErro(erro: ErroMensagem): void{
        erro.listaCampoMensagem.forEach(obj => {
            this.modalMsg.mensagem = obj.mensagem;
        })
        this.modalMsg.abrirModal();
    }

    /**
     * Indica se a cid informada é contemplada em portaria e muda o style de acordo
     * com a informação.
     *
     * @param cid
     */
    public indicarSeContempladoEmPortaria(cid:Cid):string{
        if(cid.transplante) {
            return 'azul';
        } else {
            return 'amarelo';
        }
    }


    /**
     * Método para listar as avaliações pendentes
     *
     * @param pagina numero da pagina a ser consultada
     *
     * @memberOf ConsultaComponent
     */
    private listarAvaliacoesAtribuidasAoUsuarioLogado(pagina: number) {
        this.avaliacaoService.listarAvaliacoesPacientes(pagina - 1, this.qtdRegistroAvaliacaoAtribuida, StatusAvaliacao.ATRIBUIDA, this._centroSelecionado.id)
            .then((res: any) => {
                let avaliacoes: PacienteAvaliacaoTarefaDTO[] = [];
                if(res && res.content){
                    res.content.forEach(content => {
                        let dto: PacienteAvaliacaoTarefaDTO = new PacienteAvaliacaoTarefaDTO();
                        let cid: Cid = new Cid(content.cid.id);
                        cid.codigo = content.cid.codigo;
                        cid.descricao = content.cid.descricao;
                        cid.transplante = content.cid.transplante;
                        dto.cid = cid;
                        dto.idade = content.idade;
                        dto.nome = content.nome;
                        dto.rmr = content.rmr;
                        dto.tempoCadastro = content.tempoCadastro;
                        avaliacoes.push(dto);
                    })
                }

                this.paginacaoAvaliacaoAtribuida.content = avaliacoes;

                this.paginacaoAvaliacaoAtribuida.totalElements = res ? res.totalElements : 0;
                this.paginacaoAvaliacaoAtribuida.quantidadeRegistro = this.qtdRegistroAvaliacaoAtribuida;
                this.paginacaoAvaliacaoAtribuida.number = pagina;
            })
            .catch((erro: ErroMensagem) => {
                this.exibirMensagemErro(erro);
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
    mudarPaginaAvaliacoesAtribuidas(event: any) {
        this.listarAvaliacoesAtribuidasAoUsuarioLogado(event.page);
    }

    /**
       * Método acionado quando é alterado a quantidade de registros por página
       *
       * @param {*} event
       * @param {*} modal
       *
       * @memberOf ConsultaComponent
       */
    selecionaQuantidadeAvaliacoesAtribuidasPorPagina(event: any, modal: any) {
        this.listarAvaliacoesAtribuidasAoUsuarioLogado(1);
    }

    mudouCentro(value: any) {
        this._centroSelecionado = value;
        this.listarAvaliacoesPendentes(1);
        this.listarAvaliacoesAtribuidasAoUsuarioLogado(1);
    }

}
