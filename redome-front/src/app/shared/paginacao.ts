/**
 * Classe que representa a páginação feita no back
 * 
 * @export
 * @class Paginacao
 */
export class Paginacao {
   
    private _content: any[];
    private _hasContent: boolean = false;
    private _hasNext: boolean = false;
    private _first: boolean = false;
    private _last: boolean = false;
    private _number: number;
    private _totalElements: number;
	private _totalPages: number;
	private _mainList: any[];
	
	/**
	 * Adionado essa propriedade pois precisava guardar a quantidade de registrosd por página para enviar ao back
	 * 
	 * @private
	 * @type {number}
	 * @memberOf Paginacao
	 */
	private _quantidadeRegistro: number;


    /**
	 * Cria a instância de Paginacao.
	 * @param {*} content 
	 * @param {number} totalElements 
	 * @param {number} quantidadeRegistro 
	 * 
	 * @memberOf Paginacao
	 */
	constructor(content: any, totalElements: number, quantidadeRegistro: number) {
        this._content = content;
        if (this._content) {
            this._hasContent = true;
        }
        this._totalElements = totalElements;
        this._quantidadeRegistro = quantidadeRegistro;
    }


	/**
	 * 
	 * 
	 * @type {*}
	 * @memberOf Paginacao
	 */
	public get content(): any {
		return this._content;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paginacao
	 */
	public set content(value: any) {
		this._content = value;
	}

	/**
	 * 
	 * 
	 * @type {boolean}
	 * @memberOf Paginacao
	 */
	public get hasContent(): boolean {
		return this._hasContent;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paginacao
	 */
	public set hasContent(value: boolean) {
		this._hasContent = value;
    }

	/**
	 * 
	 * 
	 * @type {boolean}
	 * @memberOf Paginacao
	 */
	public get hasNext(): boolean  {
		return this._hasNext;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paginacao
	 */
	public set hasNext(value: boolean ) {
		this._hasNext = value;
	}

	/**
	 * 
	 * 
	 * @type {boolean}
	 * @memberOf Paginacao
	 */
	public get first(): boolean  {
		return this._first;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paginacao
	 */
	public set first(value: boolean ) {
		this._first = value;
	}

	/**
	 * 
	 * 
	 * @type {boolean}
	 * @memberOf Paginacao
	 */
	public get last(): boolean  {
		return this._last;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paginacao
	 */
	public set last(value: boolean ) {
		this._last = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf Paginacao
	 */
	public get number(): number {
		return this._number;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paginacao
	 */
	public set number(value: number) {
		this._number = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf Paginacao
	 */
	public get totalElements(): number {
		return this._totalElements;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paginacao
	 */
	public set totalElements(value: number) {
		this._totalElements = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf Paginacao
	 */
	public get totalPages(): number {
		return this._totalPages;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paginacao
	 */
	public set totalPages(value: number) {
		this._totalPages = value;
	}

	/**
	 * 
	 * 
	 * @type {number}
	 * @memberOf Paginacao
	 */
	public get quantidadeRegistro(): number {
		return this._quantidadeRegistro;
	}

	/**
	 * 
	 * 
	 * 
	 * @memberOf Paginacao
	 */
	public set quantidadeRegistro(value: number) {
		this._quantidadeRegistro = value;
		if(this.mainList){
			this.slicePage(this.number);
		}
	}



    /**
     * Getter mainList
     * @return {any[]}
     */
	public get mainList(): any[] {
		return this._mainList;
	}

    /**
     * Setter mainList
     * @param {any[]} totalRegistros 
     */
	public set mainList(totalRegistros : any[]) {
		this._mainList = totalRegistros ;
		this._content = totalRegistros.length > this._quantidadeRegistro? totalRegistros.slice(0,this._number * this._quantidadeRegistro):totalRegistros ;
		this._totalPages = totalRegistros.length / this._quantidadeRegistro;
		this.totalElements = totalRegistros.length;
	}

	
	public slicePage(pageNumber:number){
		const finalSize = pageNumber * this._quantidadeRegistro;
		const beginSize = pageNumber * this._quantidadeRegistro - this.quantidadeRegistro;
		this._content = this.mainList.length > this._quantidadeRegistro? this._mainList.slice(beginSize,finalSize):this._mainList;
	}


}