package br.org.cancer.modred.service;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.SolicitacaoDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de funcionalidades ligadas a prescrição do paciente.
 * 
 * @author Pizão
 *
 */
public interface IPrescricaoService extends IService<Prescricao, Long> {
	
	/**
	 * Cria a prescrição para o doador (medula) nacional.
	 * 
	 * @param solicitacaoDTO dados da solicitações.
	 * @param arquivoJustificativa arquivo com a justificativa para o procedimento, caso a data da prescrição seja inferior a 30 dias.
	 * @param listaArquivos lista de arquivos envolvendo a prescrição.
	 * @return prescrição criada
	 */
	Prescricao solicitarPrescricaoMedula(SolicitacaoDTO solicitacaoDTO, MultipartFile arquivoJustificativa, List<MultipartFile> listaArquivos);
	
	/**
	 * Cria a prescrição para para o cordão nacional e internacional.
	 * 
	 * @param solicitacaoDTO DTO com as informações relativas a prescrição que será criada.
	 * @param arquivoJustificativa arquivo com a justificativa para a coleta ter sido solicitada com menos de 10 dias de intervalo.
	 * @param listaArquivos lista de arquivos associados a prescrição.
	 * @return prescrição criada.
	 */
	Prescricao solicitarPrescricaoCordao(SolicitacaoDTO solicitacaoDTO, MultipartFile arquivoJustificativa, List<MultipartFile> listaArquivos);
	
	/**
	 * Obtém a prescrição associada a solicitação passada por parâmetro.
	 * 
	 * @param solicitacao solicitação a ser utilizada no filtro.
	 * @return prescrição, se houver.
	 */
	Prescricao obterPrescricao(Solicitacao solicitacao);
	
	/**
	 * Obtem uma prescrição de acordo com o id da busca do paciente.
	 * @param idBusca da busca do paciente.
	 * @return prescrição.
	 */
	Prescricao obterPrescricaoPorBusca(Long idBusca);

	/**
	 * Lista todos as tarefas de autorização de paciente para o centro de transplante 
	 * para o perfil médico transplantador.
	 * 
	 * @param idCentroTransplante - Identificador do centro de transplante.
	 * @param atribuidoAMin Caso seja verdadeiro trará todas as tarefas em aberto para o usuário logado e as atribuídas também.
	 * 						 Caso não seja verdadeira trará todas as tarefas em aberto independente do usuário logado.	
	 * @param paginacao paginação do resultado.
	 * 
	 * @return lista de tarefas de pedido de logística.
	 */
	JsonViewPage<TarefaDTO> listarTarefasAutorizacaoPaciente(Long idCentroTransplante, Boolean atribuidoAMin, PageRequest paginacao);

	/**
	 * Método para salvar o aquivo de auorização do paciente.
	 * Caso exista pedido de logistica com status "AGUARDANDO AUTORIZAÇÃO PACIENTE" deverá trocar para
	 * "ABERTA".
	 * 
	 * @param idPrescricao - Identificador da prescrição
	 * @param arquivo - Arquivo de Autorização do Paciente
	 */
	void salvarArquivoAutorizacaoPaciente(Long idPrescricao, MultipartFile arquivo);
	
	/**
	 * Obtém o arquivo indicado sendo a autorização do paciente subindo após a aprovação
	 * da prescrição.
	 * 
	 * @param prescricao prescrição.
	 * @return arquivo referente a autorização para o paciente envolvido no processo.
	 */
	File obterArquivoAutorizacaoPaciente(Prescricao prescricao);

	/**
	 * Obter a prescricao pela identificador da prescricao.
	 * 
	 * @param idPrescricao Identificador da prescricao.
	 * @return Prescricao.
	 */
	Prescricao obterPrescricoesPorIdentificador(Long idPrescricao);

}
