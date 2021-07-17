import { BaseEntidade } from '../base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';

export class DetalheContatoVo extends BaseEntidade {

    public fase: number;
	public tipoPedido: string;
	public dmr: number;
	public nome: string;
	public dataCriacao: Date;
	public numeroTentativas: number;
	public ultimaTentativa: Date;
	public aberto: boolean;

    public jsonToEntity(res: any): DetalheContatoVo {

        this.fase = ConvertUtil.parseJsonParaAtributos(res.fase, new Number());
        this.tipoPedido = ConvertUtil.parseJsonParaAtributos(res.tipoPedido, new String());
        this.dmr = ConvertUtil.parseJsonParaAtributos(res.dmr, new Number());
        this.nome = ConvertUtil.parseJsonParaAtributos(res.nome, new String());
        this.dataCriacao = ConvertUtil.parseJsonParaAtributos(res.dataCriacao, new Date());
        this.numeroTentativas = ConvertUtil.parseJsonParaAtributos(res.numeroTentativas, new Number());
        this.ultimaTentativa = ConvertUtil.parseJsonParaAtributos(res.ultimaTentativa, new Date());
        this.aberto = ConvertUtil.parseJsonParaAtributos(res.aberto, new Boolean());

        return this;
    }



}