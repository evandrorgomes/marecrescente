/**
 * @description Classe que representa um registro de log envolvendo 
 * entidade fortes no processo do Redome, tais como Paciente e DoadorNacional.
 * Os logs alimentam uma espécie de histórico que poderá ser exibido para o usuário
 * informando como foi o andamento do processo até aquele momento.
 * 
 * @author Pizão
 * @export
 * @class LogEvolucaoDTO
 */
export class LogEvolucaoDTO{
    
    public rmr: number;
    public totalRegistros: number;
    public dataInclusao: Date;
    public usuarioResponsavel: string;

    /**
     * @description Texto a ser exibido para o log, é um resumo do 
     * evento ocorrido que gerou o log.
     * 
     * @type {string}
     */
    public descricao: string;

}