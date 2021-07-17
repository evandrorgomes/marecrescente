import { Prescricao } from "app/doador/consulta/workup/prescricao";
import { Evolucao } from "app/paciente/cadastro/evolucao/evolucao";
import { EvolucaoDTO } from "app/paciente/consulta/evolucao/evolucao.dto";
import { PrescricaoDTO } from "app/shared/dto/prescricao.dto";
import { PrescricaoMedulaDTO } from "app/shared/dto/prescricao.medula.dto";
import { PrescricaoCordaoDTO } from "app/shared/dto/prescricao.cordao.dto";
import { Doador } from "app/doador/doador";
import { ArquivoPrescricaoDTO } from "app/shared/dto/arquivo.prescricao.dto";
import { ICordao } from "app/doador/ICordao";
import {Observable} from "rxjs";
import { EnderecoContatoCentroTransplante } from "app/shared/model/endereco.contato.centro.transplante";

export class DetalhePrescircaoDataEvent {

    public prescricao: Prescricao;
    public evolucao: Evolucao;
    public dadosDoador: any;
    public prescricaoMedulaDTO: PrescricaoMedulaDTO;
    public prescricaoCordaoDTO: PrescricaoCordaoDTO;
    public evolucaoDTO: EvolucaoDTO;
    public arquivosPrescricaoDTO: ArquivoPrescricaoDTO[];
    public arquivosPrescricaoJustificativaDTO: ArquivoPrescricaoDTO[];
    public enderecoEntrega: Observable<EnderecoContatoCentroTransplante>;
    public mostrarBotoesDescarteEAceite: boolean = true;
    public verificarTodasFontesDescartadas: () => Boolean;
    public verificarTodasFontesAceitas: () => Boolean;
    public obterIdentificadorFonteCelulaDescartada: () => number;
    public obterIdentificadorFonteCelulaAceita: () => number;


}
