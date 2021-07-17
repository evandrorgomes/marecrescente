import { ConvertUtil } from 'app/shared/util/convert.util';
import { BaseEntidade } from '../base.entidade';
import { FormatoNumerico } from './formato.numerico';
import { OpcaoPergunta } from './opcao.pergunta';
import { PerguntaDependente } from './pergunta.dependente';

/**
 * Classe que representa uma pergunta de um formulário.
 * 
 * @export
 * @class Pergunta
 */
export class Pergunta extends BaseEntidade {

	private _marcador: string;
	private _id: string;
	private _descricao: string;
	private _tipo: String;
	private _possuiDependencia: boolean;
	private _tamanho: number;
	private _resposta: string;
	private _opcoes: OpcaoPergunta[] = [];
	private _dependentes: PerguntaDependente[] = [];
	private _formatoNumerico: FormatoNumerico;
	private _secao: number;
	private _comValidacao: boolean;
	private _comErro: boolean;
	private _alinhamento: string;

 	/**
	 * Getter marcardor
	 * @return {string}
	 */
	public get marcador(): string {
		return this._marcador;
	}

	/**
	 * Setter marcardor
	 * @param {string} value
	 */
	public set marcador(value: string) {
		this._marcador = value;
	}

	/**
	 * Identificador da pergunta
	 * 
	 * @type {string}
	 * @memberOf Pergunta
	 */
	public get id(): string {
		return this._id;
	}

	/**
	 * Identificador da Pergunta
	 * 
	 * 
	 * @memberOf Pergunta
	 */
	public set id(value: string) {
		this._id = value;
	}

	/**
	 * Descrição da pergunta
	 * 
	 * @type {string}
	 * @memberOf Pergunta
	 */
	public get descricao(): string {
		return this._descricao;
	}

	/**
	 * Descrição da pergunta
	 * 
	 * 
	 * @memberOf Pergunta
	 */
	public set descricao(value: string) {
		this._descricao = value;
	}

	/**
	 * Tipo de pergunta. verificar o enum TiposPergunta
	 * 
	 * @type {String}
	 * @memberOf Perguntaalinhamento
	 */
	public get tipo(): String {
		return this._tipo;
	}

	/**
	 * Tipo de pergunta. verificar o enum TiposPergunta
	 * 
	 * 
	 * @memberOf Pergunta
	 */
	public set tipo(value: String) {
		this._tipo = value;
	}

	/**
		 * Informa se a pergunta depende de outra pergunta para ser exibida.
		 * 
		 * @type {boolean}
		 * @memberOf Pergunta
		 */
	public get possuiDependencia(): boolean {
		return this._possuiDependencia;
	}

	/**
		 * Informa se a pergunta depende de outra pergunta para ser exibida.
		 * 
		 * @memberOf Pergunta
		 */
	public set possuiDependencia(value: boolean) {
		this._possuiDependencia = value;
	}

	/**
		 * Número de caracteres para os tipos de pergunta (TEXT e TEXTAREA)
		 * 
		 * @type {number}
		 * @memberOf Pergunta
		 */
	public get tamanho(): number {
		return this._tamanho;
	}

	/**
		 * Número de caracteres para os tipos de pergunta (TEXT e TEXTAREA)
		 * 
		 * @memberOf Pergunta
		 */
	public set tamanho(value: number) {
		this._tamanho = value;
	}

	/**
		 * Valor da resposta da pergunta
		 * 
		 * @type {string}
		 * @memberOf Pergunta
		 */
	public get resposta(): string {
		return this._resposta;
	}

	/**
		 * Valor da resposta da pergunta
		 * 
		 * @memberOf Pergunta
		 */
	public set resposta(value: string) {
		this._resposta = value;
	}

	/**
		 * Lista de opções para os tipos (RADIOBUTTON, CHECKBUTTON e SELECT)
		 * 
		 * @type {OpcaoPergunta[]}
		 * @memberOf Pergunta
		 */
	public get opcoes(): OpcaoPergunta[] {
		return this._opcoes;
	}

	/**
		 * Lista de opções para os tipos (RADIOBUTTON, CHECKBUTTON e SELECT)
		 * 
		 * @memberOf Pergunta
		 */
	public set opcoes(value: OpcaoPergunta[]) {
		this._opcoes = value;
	}

	/**
		 * Lista de perguntas dependentes desta pergunta
		 * 
		 * @type {PerguntaDependente[]}
		 * @memberOf Pergunta
		 */
	public get dependentes(): PerguntaDependente[] {
		return this._dependentes;
	}

	/**
		 * Lista de perguntas dependentes desta pergunta
		 * 
		 * @memberOf Pergunta
		 */
	public set dependentes(value: PerguntaDependente[]) {
		this._dependentes = value;
	}


	/**
	 * Getter formatoNumerico
	 * @return {FormatoNumerico}
	 */
	public get formatoNumerico(): FormatoNumerico {
		return this._formatoNumerico;
	}
	/**
	 * Setter formatoNumerico
	 * @param {FormatoNumerico} value
	 */
	public set formatoNumerico(value: FormatoNumerico) {
		this._formatoNumerico = value;
	}


    /**
     * Getter secao
     * @return {number}
     */
	public get secao(): number {
		return this._secao;
	}

    /**
     * Setter secao
     * @param {number} value
     */
	public set secao(value: number) {
		this._secao = value;
	}


    /**
     * Getter comValidacao
     * @return {boolean}
     */
	public get comValidacao(): boolean {
		return this._comValidacao;
	}

    /**
     * Setter comValidacao
     * @param {boolean} value
     */
	public set comValidacao(value: boolean) {
		this._comValidacao = value;
	}


    /**
     * Getter comErro
     * @return {boolean}
     */
	public get comErro(): boolean {
		return this._comErro;
	}

    /**
     * Setter comErro
     * @param {boolean} value
     */
	public set comErro(value: boolean) {
		this._comErro = value;
	}

    /**
     * Getter alinhamento
     * @return {string}
     */
	public get alinhamento(): string {
		return this._alinhamento;
	}

    /**
     * Setter alinhamento
     * @param {string} value
     */
	public set alinhamento(value: string) {
		this._alinhamento = value;
	}


	public jsonToEntity(res: any): Pergunta {

		if (res.opcoes) {
			this.opcoes = [];
			res.opcoes.forEach(opcao => {
				this.opcoes.push(new OpcaoPergunta().jsonToEntity(opcao));
			})			
		}

		if (res.dependentes) {
			this.dependentes = [];
			res.dependentes.forEach(dependente => {
				this.dependentes.push( new PerguntaDependente().jsonToEntity(dependente));
			});
		}

		if (res.formatoNumerico) {
			this.formatoNumerico = new FormatoNumerico().jsonToEntity(res.formatoNumerico);
		}		

		this.marcador 			= ConvertUtil.parseJsonParaAtributos(res.marcador, new String());
		this.id 				= ConvertUtil.parseJsonParaAtributos(res.id, new String());
		this.descricao 			= ConvertUtil.parseJsonParaAtributos(res.descricao, new String());
		this.tipo				= ConvertUtil.parseJsonParaAtributos(res.tipo, new String());
		this.possuiDependencia	= ConvertUtil.parseJsonParaAtributos(res.possuiDependencia, new Boolean());
		this.tamanho			= ConvertUtil.parseJsonParaAtributos(res.tamanho, new Number());
		this.resposta			= ConvertUtil.parseJsonParaAtributos(res.resposta, new String());
		this.secao				= ConvertUtil.parseJsonParaAtributos(res.secao, new Number());
		this.comValidacao		= ConvertUtil.parseJsonParaAtributos(res.comValidacao, new Boolean());
		this.comErro			= ConvertUtil.parseJsonParaAtributos(res.comErro, new Boolean());
		this.alinhamento		= ConvertUtil.parseJsonParaAtributos(res.alinhamento, new String());
		
		return this;
	}

}