import { BaseEntidade } from '../base.entidade';
import { StatusSms, IStatusSms } from '../enums/status.sms';
import { ConvertUtil } from '../util/convert.util';
import { resetFakeAsyncZone } from '@angular/core/testing';

export class SmsVo extends BaseEntidade {
    
    private _data: Date;
	
	private _dmr: number;
	
	private _telefone: string;
	
    private _status: IStatusSms;
    

    /**
     * Getter data
     * @return {Date}
     */
	public get data(): Date {
		return this._data;
	}

    /**
     * Setter data
     * @param {Date} value
     */
	public set data(value: Date) {
		this._data = value;
	}

    /**
     * Getter dmr
     * @return {number}
     */
	public get dmr(): number {
		return this._dmr;
	}

    /**
     * Setter dmr
     * @param {number} value
     */
	public set dmr(value: number) {
		this._dmr = value;
	}

    /**
     * Getter telefone
     * @return {string}
     */
	public get telefone(): string {
		return this._telefone;
	}

    /**
     * Setter telefone
     * @param {string} value
     */
	public set telefone(value: string) {
		this._telefone = value;
	}

    /**
     * Getter status
     * @return {StatusSms}
     */
	public get status(): IStatusSms {
		return this._status;
	}

    /**
     * Setter status
     * @param {StatusSms} value
     */
	public set status(value: IStatusSms) {
		this._status = value;
    }
    
    public get statusDescricao(): string {
        if (this._status != null && this._status != undefined) {
            return this._status.descricao;
        }
        return null;
    }

    public jsonToEntity(res: any): SmsVo {

        if (res.status) {            
            this.status = StatusSms.parser(res.status);
        }

        this.data = ConvertUtil.parseJsonParaAtributos(res.data, new Date());
        this.dmr = ConvertUtil.parseJsonParaAtributos(res.dmr, new Number());
        this.telefone = ConvertUtil.parseJsonParaAtributos(res.telefone, new String());

        return this;
    }

}