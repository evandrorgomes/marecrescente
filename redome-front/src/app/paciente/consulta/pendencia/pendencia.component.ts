import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SelectCentrosComponent } from 'app/shared/component/selectcentros/select.centros.component';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { CampoMensagem } from '../../../shared/campo.mensagem';
import { ErroMensagem } from '../../../shared/erromensagem';
import { Paginacao } from '../../../shared/paginacao';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';
import { PacienteService } from '../../paciente.service';

@Component({
  selector: 'app-pendencia',
  templateUrl: './pendencia.component.html',
})
export class PendenciaComponent implements PermissaoRotaComponente, OnInit {

  paginacao: Paginacao;
  quantidadeRegistro: number = 10;

  public _centroSelecionado = {
    'id': null,
    'tipo': null
  }

  @ViewChild("selectCentros")
  private selectCentros: SelectCentrosComponent;

  @ViewChild("modalMsgErroTarefa")
  private modalMsgErroTarefa;

  constructor(translate: TranslateService,
     private pacienteService: PacienteService, private router:Router,
                    private autenticacaoService: AutenticacaoService) {

    this.paginacao = new Paginacao('', 0, this.quantidadeRegistro);
    this.paginacao.number = 1;
  }

  ngOnInit() {
    Promise.resolve(this.selectCentros).then(() => {
        this._centroSelecionado = this.selectCentros.value;
        this.listarPendencias(this.paginacao.number);
    });

  }

  nomeComponente(): string {
    return "PendenciaComponent";
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


        this.listarPendencias(event.page);

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
        this.listarPendencias(1);


    }


  redirecionarTelaAvaliacao(rmr: any) {

    if(this._centroSelecionado.id !== -99) {
        this.router.navigateByUrl('/pacientes/avaliacao?rmr=' + rmr);
    } else {
        this.router.navigateByUrl('/pacientes/' + rmr + '/avaliacaoMedico');
    }
  }



  private listarPendencias(pagina: number) {
         this.pacienteService.listarPendencias(this._centroSelecionado.id === -99 ? null : this._centroSelecionado.id, pagina - 1, this.quantidadeRegistro)
            .then((res: any) => {
                if(res){
                    this.paginacao.content = res.content;
                    this.paginacao.totalElements = res.totalElements;
                    this.paginacao.quantidadeRegistro = this.quantidadeRegistro;
                    this.paginacao.number = pagina;
                }
            }),
            ((erro: ErroMensagem)  => {
                if (erro.listaCampoMensagem && erro.listaCampoMensagem.length != 0) {
                    erro.listaCampoMensagem.forEach((campoMensagem: CampoMensagem) => {
                        this.modalMsgErroTarefa.mensagem = campoMensagem.mensagem;
                    });
                }
                else {
                    this.modalMsgErroTarefa.mensagem = erro.mensagem.toString();
                }

                this.modalMsgErroTarefa.abrirModal();
            });


    }

    public temPerfilMedico(): boolean {
        return this.autenticacaoService.temPerfilMedico();
    }

    public temPerfilAvaliador(): boolean {
        return this.autenticacaoService.temPerfilAvaliador();
    }

    mudouCentro(value: any) {
        this._centroSelecionado = value;
        this.listarPendencias(1);
        /*this.listarAvaliacoesPendentes(1);
        this.listarAvaliacoesAtribuidasAoUsuarioLogado(1); */
    }


}
