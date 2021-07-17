package br.org.cancer.modred.helper;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.IProcessoFeign;
import br.org.cancer.modred.feign.dto.ProcessoDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.domain.TiposTarefa;

/**
 * Classe responsável por guardar as referências pré definidas 
 * para configuração.
 * 
 * @author fillipe.queiroz
 *
 */
public abstract class IOperacaoTarefa {
	//protected static IProcessoService processoService;
	
	private Long idProcesso;
	protected Long rmr;
	protected TiposTarefa tipoTarefa;
	protected Long idDoador;
	
	/**
	 * Executa a operação destinada a esta tarefa.
	 */
	public abstract TarefaDTO apply();

	/**
	 * Seta o id.
	 * 
	 * @param processoId - identificador do processo, obrigatório
	 * @return FecharTarefa
	 */
	public IOperacaoTarefa comProcessoId(Long idProcesso) {
		this.idProcesso = idProcesso;
		return this;
	}

	/**
	 * Passa a identificação do paciente para servir de referência ao buscar
	 * o processo.
	 * 
	 * @param rmr ID do paciente.
	 * @return a referência do this da própria classe.
	 */
	public IOperacaoTarefa comRmr(Long rmr) {
		this.rmr = rmr;
		return this;
	}

	/**
	 * Passa a identificação do doador para servir de referência ao buscar
	 * o processo.
	 * 
	 * @param rmr ID do doador.
	 * @return a referência do this da própria classe.
	 */
	public IOperacaoTarefa comDoadorId(Long idDoador) {
		this.idDoador = idDoador;
		return this;
	}
	
	/**
	 * Obtém o processo ativo a partir do ID ou do RMR + Tipo Processo informados.
	 * 
	 * @return processo ativo associado a tarefa.
	 */
	protected ProcessoDTO obterProcesso() {
		if (idProcesso != null) {
			return  new ProcessoDTO(idProcesso, tipoTarefa.getTipoProcesso());
		}
		else if(rmr != null){
			return getProcessoFeign().obterProcessoemAndamento(rmr, null, tipoTarefa.getTipoProcesso().getId());
		}
		else if(idDoador != null){
			return getProcessoFeign().obterProcessoemAndamento(null, idDoador, tipoTarefa.getTipoProcesso().getId());
		}
		
    	throw new BusinessException("erro.mensagem.processo.nao.encontrado");
	}
	
	protected abstract IProcessoFeign getProcessoFeign();

	/**
	 * @return the processoService
	 */
//	public static IProcessoService getProcessoService() {
//		return processoService;
//	}

	/**
	 * @param processoService the processoService to set
	 */
//	@Autowired
	//public void setProcessoService(IProcessoService processoService) {
//		IOperacaoTarefa.processoService = processoService;
//	}
	
	


}
