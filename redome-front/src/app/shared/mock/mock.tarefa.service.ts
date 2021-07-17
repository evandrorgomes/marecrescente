import { PerfilUsuario } from '../enums/perfil.usuario';
import { Observable } from 'rxjs';
import { AtributoOrdenacaoDTO } from '../util/atributo.ordenacao.dto';

export class MockTarefaService {

    obterTarefaPedidoEnriquecimento(perfilid: number): Promise<any> { return null};

    cancelarTarefa(tarefaId: number): Promise<any> { return null};

    /**
     * Método para listar tarefas paginadas de acordo com seus tipos.
     * @param {number} tipotarefa - tipo de tarefa que se deseja obter a lista.
     * @param {number} pagina - página a ser listada
     * @param {number} quantidadeRegistro - quantidade de registros a serem listados.
     * @returns listagem de tarefas d busca
     * @memberof BuscaService
     * @author Filipe Paes
     */
    public listarTarefasPaginadas(tipotarefa: number, perfil: PerfilUsuario, 
        idUsuarioLogado:number, pagina: number, quantidadeRegistro: number, 
        buscaPorUsuarioLogado?:boolean, atributoOrdenacaoDTO?:AtributoOrdenacaoDTO,
        statusTarefa?:number, parceiro?:number, ordenacao?:number,
        centroTransplantePorUsuarioLogado?: boolean): Promise<any> { return null};

    atribuirTarefa(processoId: number, tarefaId:number, atribuirUsuarioLogado?: boolean): Promise<any> { return null};

    fecharTarefa(processoId: number, tarefaId:number): Promise<any> {
        return null;
    }

    atribuirTarefaParaUsuarioLogado(tarefaId: number): Promise<any> {
        return null;
    }
    
}