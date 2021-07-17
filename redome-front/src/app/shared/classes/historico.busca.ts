import { UsuarioLogado } from "../dominio/usuario.logado";

/**
 * Representa um item do histórico de busca (recusas do CT em realizar o transplante).
 * Está associada a busca e, por consequencia, a um paciente.
 * 
 * @author Pizão
 */
export class HistoricoBuscaDTO {
	public dataAtualizacao: Date;
	public justificativa: string;
	public nomeUsuario: string;
}