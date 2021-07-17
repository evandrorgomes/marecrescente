import { ModalDetalheAvaliacaoCamaraTecnicaComponent } from './modal/modal.detalhe.avaliacao.camara.tecnica.component';
import { AvaliacaoCamaraTecnicaDTO } from './avaliacao.camara.tecnica.dto';
import { AvaliacaoCamaraTecnica } from './../avaliacao.camara.tecnica';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { PedidoTransferenciaCentro } from 'app/shared/classes/pedido.transferencia.centro';
import { PedidoTransferenciaCentroDTO } from 'app/shared/dto/pedido.transferencia.centro.dto';
import { MessageBox } from 'app/shared/modal/message.box';
import { TarefaService } from 'app/shared/tarefa.service';
import { AutenticacaoService } from '../../../shared/autenticacao/autenticacao.service';
import { ComponenteRecurso } from '../../../shared/enums/componente.recurso';
import { HistoricoNavegacao } from '../../../shared/historico.navegacao';
import { ErroUtil } from '../../../shared/util/erro.util';
import { Evolucao } from '../../cadastro/evolucao/evolucao';
import { HeaderPacienteComponent } from '../../consulta/identificacao/header.paciente.component';
import { PacienteService } from '../../paciente.service';
import { ErroMensagem } from '../../../shared/erromensagem';
import { RouterUtil } from '../../../shared/util/router.util';
import { Recursos } from 'app/shared/enums/recursos';
import { AvalicacaoCamaraTecnicaService } from '../avaliacao.camara.tecnica.service';
import { PermissaoRotaComponente } from '../../../shared/permissao.rota.componente';

/**
 * Component responsavel por detalhes de avaliação de camara técnica.
 * @author Filipe Paes
 * @export
 * @class DetalheAvaliacaoCamaraTecnicaComponent
 * @implements {OnInit}
 */
@Component({
    selector: 'detalhe-avalaicao-camara-tecnica',
    templateUrl: './detalhe.avaliacao.camara.tecnica.component.html'
})
export class DetalheAvaliacaoCamaraTecnicaComponent implements PermissaoRotaComponente, OnInit {

    private _avaliacaoCamaraTecnicaDto: AvaliacaoCamaraTecnicaDTO;
    private _idTarefa;

    @ViewChild(HeaderPacienteComponent)
    private headerPaciente: HeaderPacienteComponent;

    private _evolucao:Evolucao;



    /**
     * Cria uma instancia de AvaliacaoComponent.
     * @param {FormBuilder} _fb
     * @param {PacienteService} servicePaciente
     * @param {Router} router
     * @param {TranslateService} translate
     *
     * @memberOf AvaliacaoComponent
     */
    constructor(private router:Router, private activatedRouter: ActivatedRoute,
            private autenticacaoService: AutenticacaoService, private messageBox: MessageBox,
            private tarefaService: TarefaService,
            private avaliacaoCamaraTecnicaService:AvalicacaoCamaraTecnicaService ) {
    }

    /**
     *
     *
     * @memberOf AvaliacaoComponent
     */
    ngOnInit(): void {
        RouterUtil.recuperarParametrosDaActivatedRoute(this.activatedRouter, ["idAvaliacaoCamaraTecnica", "idTarefa"]).then(res => {
            this._idTarefa = res["idTarefa"];
            let idAvaliacaoCamaraTecnica =  res["idAvaliacaoCamaraTecnica"];

           this.avaliacaoCamaraTecnicaService.obterAvaliacao(idAvaliacaoCamaraTecnica).then(res => {

                this._avaliacaoCamaraTecnicaDto = new AvaliacaoCamaraTecnicaDTO().jsonToEntity(res);

                setTimeout(() => {
                    Promise.resolve(this.headerPaciente).then(() => {
                        this.headerPaciente.popularCabecalhoIdentificacaoPorPaciente(this._avaliacaoCamaraTecnicaDto.avaliacaoCamaraTecnica.paciente.rmr);
                    });
                }, 1);
            }, (error: ErroMensagem) => {
                ErroUtil.exibirMensagemErro(error, this.messageBox);
            });
        });
    }

    /**
     * Método criar somente para a utilização do botão voltar.
     *
     * @memberOf AvaliacaoComponent
     */
    voltarConsulta() {
        this.tarefaService.removerAtribuicaoTarefa(this._idTarefa).then(res => {
            this.router.navigateByUrl(HistoricoNavegacao.urlRetorno())
        }, (error: ErroMensagem) =>{
            ErroUtil.exibirMensagemErro(error, this.messageBox);
        });
    }

    nomeComponente(): string {
        return ComponenteRecurso.Componente[ComponenteRecurso.Componente.DetalheAvaliacaoCamaraTecnicaComponent];
    }


    aprovar(): void {
        let data: any = {
            idAvaliacao: this._avaliacaoCamaraTecnicaDto.avaliacaoCamaraTecnica.id,
            tipoForm: "upload",
            acao:"aprovar",
            fechar: () => {
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            }
        }
        this.messageBox.dynamic(data, ModalDetalheAvaliacaoCamaraTecnicaComponent)
                .withTarget(this)
                .show();
    }
    reprovar(): void{
        let data: any = {
            idAvaliacao: this._avaliacaoCamaraTecnicaDto.avaliacaoCamaraTecnica.id,
            tipoForm: "upload",
            acao:"reprovar",
            fechar: () => {
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            }
        }
        this.messageBox.dynamic(data, ModalDetalheAvaliacaoCamaraTecnicaComponent)
                .withTarget(this)
                .show();
    }


    /**
     * Getter evolucao
     * @return {Evolucao}
     */
	public get evolucao(): Evolucao {
		return this._avaliacaoCamaraTecnicaDto != null? this._avaliacaoCamaraTecnicaDto.ultimaEvolucao: null;
	}


    /**
     * Getter avaliacaoCamaraTecnicaDto
     * @return {AvaliacaoCamaraTecnicaDTO}
     */
	public get avaliacaoCamaraTecnicaDto(): AvaliacaoCamaraTecnicaDTO {
		return this._avaliacaoCamaraTecnicaDto;
	}

    /**
     * Setter avaliacaoCamaraTecnicaDto
     * @param {AvaliacaoCamaraTecnicaDTO} value
     */
	public set avaliacaoCamaraTecnicaDto(value: AvaliacaoCamaraTecnicaDTO) {
		this._avaliacaoCamaraTecnicaDto = value;
    }

    alterarStatus(){
        let data: any = {
            idAvaliacao: this._avaliacaoCamaraTecnicaDto.avaliacaoCamaraTecnica.id,
            acao:"status",
            tipoForm: "status",
            fechar: () => {
                this.router.navigateByUrl(HistoricoNavegacao.urlRetorno());
            }
        }
        this.messageBox.dynamic(data, ModalDetalheAvaliacaoCamaraTecnicaComponent)
            .withTarget(this)
            .show();
    }

}
