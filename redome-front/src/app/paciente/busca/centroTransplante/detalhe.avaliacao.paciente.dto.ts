import { ContatoCentroTransplantadorDTO } from "./contato.centro.transplantador.dto";
import { CentroTransplante } from "../../../shared/dominio/centro.transplante";

/**
 * Classe que representa o detalhe da avaliação do paciente que consiste em:
 * Centro avaliador, médico responsável, centro transplantador sugerido e 
 * a lista de centros transplantadores possíveis.
 * 
 * @author Pizão
 *
 */
export class DetalheAvaliacaoPacienteDTO{
	public rmr: number;
	public buscaId: number;
	public nomeCentroAvaliador: string;
	public centroTransplantador: CentroTransplante;
	public nomeMedicoResponsavel: string;
	public contatosPorCentroTransplante: ContatoCentroTransplantadorDTO[];
	public centroTransplantadorConfirmado: boolean;
}