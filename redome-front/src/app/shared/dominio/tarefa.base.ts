import { CentroTransplante } from './centro.transplante';
import { UsuarioLogado } from './usuario.logado';
import { Perfil } from './perfil';
import { TipoTarefa } from './tipo.tarefa';
import { BaseEntidade } from '../base.entidade';
import { Processo } from './processo';
import { DateMoment } from '../util/date/date.moment';
import { ConvertUtil } from '../util/convert.util';
import { PedidoColeta } from '../../doador/consulta/coleta/pedido.coleta';
import { PedidoWorkup } from '../../doador/consulta/workup/pedido.workup';
import { ResultadoWorkup } from '../../doador/cadastro/resultadoworkup/resultado/resultado.workup';
import { AvaliacaoPrescricao } from '../../paciente/cadastro/prescricao/avaliacao/avaliacao.prescricao';
import { Prescricao } from 'app/doador/consulta/workup/prescricao';
import { Notificacao } from 'app/paciente/notificacao';
import { PedidoTransferenciaCentro } from 'app/shared/classes/pedido.transferencia.centro';
import { AvaliacaoCamaraTecnica } from '../../paciente/avaliacaocamaratecnica/avaliacao.camara.tecnica';
import { AvaliacaoResultadoWorkup } from 'app/doador/cadastro/resultadoworkup/resultado/avaliacao.resultado.workup';
import { PedidoContato } from 'app/doador/solicitacao/fase2/pedido.contato';
import { TentativaContatoDoador } from 'app/doador/solicitacao/fase2/tentativa.contato.doador';
import { AnaliseMedica } from 'app/doador/solicitacao/analisemedica/analise.medica';
import { PedidoEnriquecimento } from '../classes/pedido.enriquecimento';

/**
 * Classe base para as tarefas do process server.
 *
 * @export
 * @class TarefaBase
 * @extends {BaseEntidade}
 */
export class TarefaBase extends BaseEntidade {

    private _id: number;
    private _processo: Processo;
    private _dataCriacao: Date;
	private _dataAtualizacao: Date;
	private _tipoTarefa: TipoTarefa;
	private _status: string;
	private _perfilResponsavel: Perfil;
	private _usuarioResponsavel: UsuarioLogado;
	private _relacaoParceiro: number;
	private _objetoRelacaoParceiro: any;
	private _descricao: string;
	private _aging: string;
	private _objetoRelacaoEntidade:any;

	constructor(id?: number) {
        super();
        this._id = id;
    }

	public get id(): number {
		return this._id;
	}

	public set id(value: number) {
		this._id = value;
	}

	public get processo(): Processo {
		return this._processo;
	}

	public set processo(value: Processo) {
		this._processo = value;
	}

	public get dataCriacao(): Date {
		return this._dataCriacao;
	}

	public set dataCriacao(value: Date) {
		this._dataCriacao = value;
	}

	public get dataAtualizacao(): Date {
		return this._dataAtualizacao;
	}

	public set dataAtualizacao(value: Date) {
		this._dataAtualizacao = value;
	}

	public get tipoTarefa(): TipoTarefa {
		return this._tipoTarefa;
	}

	public set tipoTarefa(value: TipoTarefa) {
		this._tipoTarefa = value;
	}

	public get status(): string {
		return this._status;
	}

	public set status(value: string) {
		this._status = value;
	}

	public get perfilResponsavel(): Perfil {
		return this._perfilResponsavel;
	}

	public set perfilResponsavel(value: Perfil) {
		this._perfilResponsavel = value;
	}

	public get usuarioResponsavel(): UsuarioLogado {
		return this._usuarioResponsavel;
	}

	public set usuarioResponsavel(value: UsuarioLogado) {
		this._usuarioResponsavel = value;
	}

	public get descricao(): string {
		return this._descricao;
	}

	public set descricao(value: string) {
		this._descricao = value;
	}

    /**
     * Retorna a quantidade de dias corridos desde a cria��o da tarefa no
	 * formato DD dias.
	 *
     * @return {string} texto do aging formatado.
     */
	public get aging(): string {
		return this._aging;
	}

    /**
     * Setter aging
     * @param {string} value
     */
	public set aging(value: string) {
		this._aging = value;
	}


    /**
     * Getter relacaoParceiro
     * @return {number}
     */
	public get relacaoParceiro(): number {
		return this._relacaoParceiro;
	}

    /**
     * Setter relacaoParceiro
     * @param {number} value
     */
	public set relacaoParceiro(value: number) {
		this._relacaoParceiro = value;
	}

    /**
     * Getter objetoRelacaoParceiro
     * @return {Object}
     */
	public get objetoRelacaoParceiro(): any {
		return this._objetoRelacaoParceiro;
	}

    /**
     * Setter objetoRelacaoParceiro
     * @param {Object} value
     */
	public set objetoRelacaoParceiro(value: any) {
		this._objetoRelacaoParceiro = value;
	}

    /**
     * Getter objetoRelacaoEntidade
     * @return {any}
     */
	public get objetoRelacaoEntidade(): any {
		return this._objetoRelacaoEntidade;
	}

    /**
     * Setter objetoRelacaoEntidade
     * @param {any} value
     */
	public set objetoRelacaoEntidade(value: any) {
		this._objetoRelacaoEntidade = value;
	}

	public jsonToEntity(res:any):TarefaBase{

		if (res.processo) {
			this.processo = new Processo().jsonToEntity(res.processo);
		}
		if (res.tipoTarefa) {
			this.tipoTarefa = new TipoTarefa().jsonToEntity(res.tipoTarefa);
		}

		if (res.perfilResponsavel) {
			this.perfilResponsavel = new Perfil().jsonToEntity(res.perfilResponsavel);
		}

		if (res.usuarioResponsavel) {
			this.usuarioResponsavel = new UsuarioLogado().jsonToEntity(res.usuarioResponsavel);
		}

		if (res.objetoRelacaoEntidade) {
			if (this.tipoTarefa.classEntidadeRelacionamento) {
				if (this.tipoTarefa.classEntidadeRelacionamento == "PedidoWorkup") {
					this.objetoRelacaoEntidade = new PedidoWorkup().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "PedidoColeta") {
					this.objetoRelacaoEntidade = new PedidoColeta().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "ResultadoWorkup") {
					this.objetoRelacaoEntidade = new ResultadoWorkup().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "AvaliacaoPrescricao") {
					this.objetoRelacaoEntidade = new AvaliacaoPrescricao().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "Prescricao") {
					this.objetoRelacaoEntidade = new Prescricao().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "Notificacao") {
					this.objetoRelacaoEntidade = new Notificacao().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "PedidoTransferenciaCentro") {
					this.objetoRelacaoEntidade = new PedidoTransferenciaCentro().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "AvaliacaoCamaraTecnica") {
					this.objetoRelacaoEntidade = new AvaliacaoCamaraTecnica().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "AvaliacaoResultadoWorkup") {
					this.objetoRelacaoEntidade = new AvaliacaoResultadoWorkup().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "TentativaContatoDoador") {
					this.objetoRelacaoEntidade = new TentativaContatoDoador().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "AnaliseMedica") {
					this.objetoRelacaoEntidade = new AnaliseMedica().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else if (this.tipoTarefa.classEntidadeRelacionamento == "PedidoEnriquecimento") {
					this.objetoRelacaoEntidade = new PedidoEnriquecimento().jsonToEntity(res.objetoRelacaoEntidade);
				}
				else {
					throw new Error("Method not implemented.");
				}
			}
			else {
				this.objetoRelacaoEntidade = res.objetoRelacaoEntidade;
			}
		}

		this.id = 				ConvertUtil.parseJsonParaAtributos(res.id, new Number());
		this.dataCriacao = 		ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
		this.dataAtualizacao = 	ConvertUtil.parseJsonParaAtributos(res.dataAtualizacao, new Date());
		this.status = 			ConvertUtil.parseJsonParaAtributos(res.status, new String());
		this.relacaoParceiro = 	ConvertUtil.parseJsonParaAtributos(res.relacaoParceiro, new Number());
		this.descricao = 		ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
		this.aging = 			ConvertUtil.parseJsonParaAtributos(res.aging, new String());

		return this;
	}



}
