import { DateTypeFormats } from '../../../shared/util/date/date.type.formats';
import { DataUtil } from '../../../shared/util/data.util';
import { Comentario } from '../../../shared/dialogo/comentario';
import { DialogoBusca } from './dialogo.busca';
import { DialogoBuilder } from '../../../shared/dialogo/dialogo.builder';

/**
 * Classe que Constroi a lista de Comentarios
 * @author Filipe Paes
 */
export class DialogoBuscaBuilder extends DialogoBuilder {
    
    
	constructor() {
		super();
	}
    
    public buildComentarios(list: DialogoBusca[]): DialogoBuscaBuilder {
        this._comentarios = [];
        let listaComentario:Comentario[] = [];
        list.forEach(d=>{
            let comentario:Comentario = new Comentario();
            comentario.texto = d.mensagem + "";
            comentario.username = d.usuario.username;
            comentario.nomeUsuario = d.usuario.nome;
            comentario.dataFormatadaDialogo = DataUtil.toDateFormat(new Date(d.dataHoraMensagem), DateTypeFormats.DATE_TIME);
            this._comentarios.push(comentario);
        });
        return this;
    }

}