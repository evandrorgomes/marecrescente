import { BaseEntidade } from '../base.entidade';
import { ConvertUtil } from 'app/shared/util/convert.util';
import { DetalheContatoVo } from './detalhe.contato.vo';

export class ContatoVo extends BaseEntidade {

    public totalEnriquecimentoFase2: number;
	public totalFechadoEnriquecimentoFase2: number;
	public totalContatoFase2: number;
	public totalFechadoContatoFase2: number;
	public totalEnriquecimentoFase3: number;
	public totalFechadoEnriquecimentoFase3: number;
	public totalContatoFase3: number;
    public totalFechadoContatoFase3: number;
    public detalhes: DetalheContatoVo[];


    public jsonToEntity(res: any): ContatoVo {

        if (res.detalhes) {
            this.detalhes = [];
            res.detalhes.forEach(detalhe => {
                this.detalhes.push(new DetalheContatoVo().jsonToEntity(detalhe));
            });
        }

        this.totalEnriquecimentoFase2 = ConvertUtil.parseJsonParaAtributos(res.totalEnriquecimentoFase2, new Number());
        this.totalFechadoEnriquecimentoFase2 = ConvertUtil.parseJsonParaAtributos(res.totalFechadoEnriquecimentoFase2, new Number());
        this.totalContatoFase2 = ConvertUtil.parseJsonParaAtributos(res.totalContatoFase2, new Number());
        this.totalFechadoContatoFase2 = ConvertUtil.parseJsonParaAtributos(res.totalFechadoContatoFase2, new Number());

        this.totalEnriquecimentoFase3 = ConvertUtil.parseJsonParaAtributos(res.totalEnriquecimentoFase3, new Number());
        this.totalFechadoEnriquecimentoFase3 = ConvertUtil.parseJsonParaAtributos(res.totalFechadoEnriquecimentoFase3, new Number());
        this.totalContatoFase3 = ConvertUtil.parseJsonParaAtributos(res.totalContatoFase3, new Number());
        this.totalFechadoContatoFase3 = ConvertUtil.parseJsonParaAtributos(res.totalFechadoContatoFase3, new Number());

        return this;
    }



}