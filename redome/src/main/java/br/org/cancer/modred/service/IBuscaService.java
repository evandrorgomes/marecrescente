package br.org.cancer.modred.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.dto.BuscaPaginacaoDTO;
import br.org.cancer.modred.controller.dto.HistoricoBuscaDTO;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CancelamentoBusca;
import br.org.cancer.modred.model.MotivoCancelamentoBusca;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para métodos de negocio de Busca.
 * 
 * @author Filipe Paes
 */
public interface IBuscaService extends IService<Busca, Long> {

	/**
	 * Método para criar uma busca inicial para um paciente.
	 * @param paciente - paciente do processo de busca.
	 */
	void criarBuscaInicial(Paciente paciente);

	/**
	 * Método para obter busca por id .
	 * @param id - da busca a ser obtida.
	 * @return Busca localizada por id.
	 */
	Busca obterBusca(Long id);

	/**
	 * Método para alterar status de uma busca.
	 * 
	 * @param idBusca - busca a ser alterada.
	 * @param idStatus - status a ser setado em busca.
	 */
	void alterarStatusDeBusca(Long idBusca, Long idStatus);

	/**
	 * Método para atribuir uma busca para um analista de busca.
	 * 
	 * @param idBusca - id da busca a ser atribuida.
	 */
	void atribuirBuscaParaAnalistaRedome(Long idBusca);

	/**
	 * Método para verificar ser a avaliacao já foi aprovada e se existe.
	 * um Genotipo com A, B e DRB1 aprovado para o paciente para então atualizar o processo de 
	 * Busca.
	 * @param rmr - identificacao do paciente.
	 */
	void iniciarProcessoDeBusca(Long rmr);

	/**
	 * Método para cancelar a busca de um paciente.
	 * 
	 * @param rmr - identificacao do paciente.
	 * @param cancelamentoBusca 
	 */
	void cancelarBusca(Long rmr, CancelamentoBusca cancelamentoBusca);

	/**
	 * Método para retornar a busca ativa atual de um paciente.
	 * 
	 * @param rmr - identificacao do paciente.
	 * @return Busca
	 */
	Busca obterBuscaAtivaPorRmr(Long rmr);

	/**
	 * Lista paginada das buscas ativas, associadas ao um determinado analista de busca.
	 * Existem outras opções de filtro como por RMR e nome do paciente ou tipo de checklist de busca
	 * associado ao paciente (ou processos de match a que está envolvido), sendo esses dois últimos 
	 * filtros opcionais.
	 * 
	 * @param loginAnalistaBusca login do analista de busca associado.
	 * @param itemCheckList tipo de item de checklist de busca a ser utilizado como filtro (opcional). 
	 * @param rmr RMR do paciente a ser utilizado como filtro (opcional).
	 * @param nome Nome do paciente a ser utilizado como filtro (opcional).
	 * @param campoOrdenacao - campo para parametro de ordenacao. Se não for preenchido, pelo nome é o padrão.
	 * @param pageable - paginação para o resultado.
	 * 
	 * @return lista paginada de buscas.
	 */
	
	Page<BuscaPaginacaoDTO> listarBuscas(String loginAnalistaBusca, Long idTipoBuscaCheckList, 
			Long rmr, String nome, Pageable pageable);
	
	/**
	 * Lista todos os motivos de cancelamento de busca.
	 * @return motivos
	 */
	List<MotivoCancelamentoBusca> listarMotivosCancelamento();

	/**
	 * Método para atribuir busca para analista de busca.
	 * @param idBusca - id da busca a ser atribuida
	 */
	void atribuirBuscaETarefaParaAnalistaRedome(Long idBusca);

	/**
	 * Obtém a busca a partir do match.
	 * 
	 * @param matchId ID do match.
	 * @return a busca associada ao match.
	 */
	Busca obterBuscaPorMatch(Long matchId);
	
	/**
	 * Obtém a busca a partir do pedido de workup.
	 * 
	 * @param pedidoWorkupId ID do pedido de workup.
	 * @return a busca associada ao pedido de workup.
	 */
	Busca obterBuscaPorPedidoWorkup(Long pedidoWorkupId);
	
	/**
	 * Obtém a busca a partir do pedido de contato.
	 * 
	 * @param pedidoContatoId ID do pedido de contato.
	 * @return a busca associada ao pedido de contato.
	 */
	Busca obterBuscaPorPedidoContato(Long pedidoContatoId);
	
	/**
	 * Obtém a busca a partir do pedido de exame.
	 * @param pedidoExameId ID do pedido de exame associado.
	 * @return busca associada.
	 */
	Busca obterBuscaPorPedidoExame(Long pedidoExameId);
	
	/**
	 * Confirmar o centro transplantador para o paciente.
	 * Esta informação é gravada na busca, indicando uma sobreposição
	 * ao centro definido no momento do cadastro do paciente.
	 * 
	 * @param busca busca a ter o centro atualizado.
	 * @param centroTransplanteId identificador do centro transplantador que será inserido na busca.
	 */
	void confirmarCentroTransplate(Busca busca, Long centroTransplanteId);
	
	/**
	 * Marca como indefinido o centro transplantador para o paciente (centro de transplante da busca
	 * é marcado como NULO).
	 * 
	 * @param busca busca a ter o centro atualizado.
	 */
	void indefinirCentroTransplante(Busca busca);
	
	/**
	 * Remove o CT associado a busca gerando o histórico da operação para futuras consultas.
	 * 
	 * @param busca busca a ter o centro atualizado.
	 * @param justificativa justificativa para recusa do CT.  
	 */
	void recusarCentroTransplante(Busca busca, String justificativa);
	
	/**
	 * Listar o histórico das recusas do CT em ser o centro do paciente (CT está associado a busca).
	 * 
	 * @param rmr RMR do paciente associado a busca.
	 * @return lista de histórico de recusas.
	 */
	List<HistoricoBuscaDTO> listarHistoricoBusca(Long rmr);
	
	/**
	 * Cria uma nova busca com status Aguardando Aprovação Redome.
	 * Este momento ocorre quando é cadastrada uma nova busca para um paciente
	 * já existente na base do ModRed.
	 * 
	 * @param paciente paciente que receberá a nova busca.
	 */
	void criarNovaBuscaInicial(Paciente paciente);
	
	void alterarDataUltimaAnaliseTecnicaDeBusca(Long idBusca);

	
	/**
	 * Ativa busca de acordo com o RMR passado.
	 * @param rmr do paciente que se deseja que a busca seja ativada.
	 */
	void alterarStatusDeBuscaParaLiberado(Long rmr);
	
	
	/**
	 * Retira o usuario responsável pela busca.
	 * @param rmr do paciente da busca.
	 */
	void desatribuirBuscaDeAnalista(Long rmr);

	/**
	 * Método que encerra busca e inativa todos os matchs e cancela as solicitações ligadas aos matchs se possível.
	 * 
	 * @param id - identificador da busca.
	 */
	void encerrarBusca(Long id);

	/**
	 * Obter busca pelo id.
	 * 
	 * @param Long id - id da busca.
	 * @return objeto Busca. 
	 */
	Busca obterBuscaAtivaPorId(Long id);
}
