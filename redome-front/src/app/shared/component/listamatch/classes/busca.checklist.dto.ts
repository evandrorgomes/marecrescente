import { BaseEntidade } from "app/shared/base.entidade";
import { ConvertUtil } from "app/shared/util/convert.util";

/**
 * @description Classe que representa um item de checklist (BuscaChecklist no back)
 * na exibição dos checklists que o analista de busca precisa tomar ciência na tela 
 * de matchs (carrousel).
 * 
 * @author Pizão
 * @export
 * @class BuscaChecklist
 */
export class BuscaChecklistDTO  extends BaseEntidade {

    // Identificador do checklist
    public id: number;
    
    // Descrição do item
    public descricao: string;
    
    // Prazo em dias para expirar o item.
    public prazoEmDias: number;

    // Associação com busca.
    public idBusca: number;

    // Associação com match, caso exista.
    public idMatch: number;

    public fase: String;

    public rmr: number;

    public identificadorDoador: string;

    public matchAtivo: boolean;

    public vistarChecklist: boolean = false;

    public idTipoBuscaChecklist: number;
    
    public jsonToEntity(res: any): BuscaChecklistDTO {

        this.id = ConvertUtil.parseJsonParaAtributos(res.id, new Number());
        this.descricao = ConvertUtil.parseJsonParaAtributos(res.descricao , new String());
        this.idTipoBuscaChecklist = ConvertUtil.parseJsonParaAtributos(res.idTipoBuscaChecklist, new Number());
        this.prazoEmDias = ConvertUtil.parseJsonParaAtributos(res.prazoEmDias , new Number());
        this.idBusca = ConvertUtil.parseJsonParaAtributos(res.idBusca , new Number());
        this.idMatch = ConvertUtil.parseJsonParaAtributos(res.idMatch , new Number());
        this.fase =  ConvertUtil.parseJsonParaAtributos(res.fase , new String());
        this.rmr = ConvertUtil.parseJsonParaAtributos(res.rmr , new Number());
        this.identificadorDoador =  ConvertUtil.parseJsonParaAtributos(res.identificadorDoador , new String());
        this.matchAtivo = ConvertUtil.parseJsonParaAtributos(res.matchAtivo , new Boolean());
        this.vistarChecklist = ConvertUtil.parseJsonParaAtributos(res.vistarChecklist , new Boolean());

        return this;
    }

}