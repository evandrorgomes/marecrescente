import { TarefaBase } from "../../../../shared/dominio/tarefa.base";

/**
 * Informações contidas na tarefa criada para encontrar centro de transplante.
 * Representa um paciente sem um centro de transplante a definir, esta ação
 * será realizada a partir deste item.
 */
export class CentroTransplanteADefinirDTO {
    public tarefa: TarefaBase;
    public rmr: number;
    public nomePaciente: string;
    public aging: string;
}