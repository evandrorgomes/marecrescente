import { RespostaPendenciaDTO } from '../../paciente/avaliacao/resposta.pendencia.dto';
import { BaseEntidade } from "../base.entidade";
import { Comentario } from "./comentario";
import { DialogoBuilder } from "./dialogo.builder";
import { TipoPendencia } from '../dominio/tipo.pendencia';


/**
 * Classe que Constroi a lista de Comentarios
 * @author Fillipe Queiroz
 */
export class PendenciaDialogoBuilder extends DialogoBuilder {


	constructor() {
		super();
	}

	buildComentarios(respostaPendenciaDTO: RespostaPendenciaDTO[]): PendenciaDialogoBuilder {
		this._comentarios = [];
		respostaPendenciaDTO.forEach(respostaPendencia => {
			let comentario: Comentario = new Comentario();
			comentario.texto = respostaPendencia.resposta;
			comentario.username = respostaPendencia.usuario
			comentario.dataFormatadaDialogo = respostaPendencia.dataFormatadaDialogo;
			comentario.rota = respostaPendencia.link;
			comentario.estiloIcone = respostaPendencia.estiloIcone;
			this._comentarios.push(comentario);
		})
		return this;

	}




}

