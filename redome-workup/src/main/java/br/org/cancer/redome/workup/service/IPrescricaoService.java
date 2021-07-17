package br.org.cancer.redome.workup.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.ConsultaPrescricaoDTO;
import br.org.cancer.redome.workup.dto.CriarPrescricaoCordaoDTO;
import br.org.cancer.redome.workup.dto.CriarPrescricaoMedulaDTO;
import br.org.cancer.redome.workup.dto.PrescricaoCordaoDTO;
import br.org.cancer.redome.workup.dto.PrescricaoEvolucaoDTO;
import br.org.cancer.redome.workup.dto.PrescricaoMedulaDTO;
import br.org.cancer.redome.workup.dto.PrescricaoSemAutorizacaoPacienteDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.model.Prescricao;

public interface IPrescricaoService {
	
	void criarPrescricao(CriarPrescricaoMedulaDTO prescricaoMedulaDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) throws Exception;

	void criarPrescricao(CriarPrescricaoCordaoDTO prescricaoCordaoDTO, MultipartFile arquivoJustificativa,
			List<MultipartFile> listaArquivos) throws Exception;
	
	/**
	 * Lista pacientes com prescrições disponiveis. 
	 * 
	 * @param idCentroTransplante
	 * @param pageable
	 * @return Page<PacientePrescricaoDTO>
	 */
	Page<ConsultaPrescricaoDTO> listarPrescricoesDisponiveis(Long idCentroTransplante, PageRequest pageable) throws JsonProcessingException;
	
	
	/**
	 * Listar pacientes com prescrição por centro de transplante.
	 * 
	 * @param status
	 * @param idCentroTransplante
	 * @param pageable
	 * @return Page<PacientePrescricaoDTO>
	 */
	Page<ConsultaPrescricaoDTO> listarPrescricoesPorStatusECentroTransplante(Long idCentroTransplante, String[] statusSolicitacao, Pageable pageable);
	
	/**
	 * Obtem prescricao de medula com evolução.
	 * 
	 * @param idPrescricao
	 * @return PrescricaoMedulaDTO
	 */
	PrescricaoMedulaDTO obterPrescricaoMedulaComEvolucaoPorId(Long idPrescricao);

	/**
	 * Cancela a prescrição e a solicitação recriando a tarefa para cadastrar prescrição novamente para o doador.
	 * 
	 * @param id - identificação da prescrição.
	 * @return SolicitacaoDTO
	 */
	SolicitacaoDTO cancelarPrescricao(Long id);
	
	
	/**
	 * Obtém a presrição através da solicitação.
	 * 
	 * @param idSolicitacao Identificação da solicitação.
	 * @return Prescrição.
	 */
	Prescricao obterPrescricaoPorSolicitacao(Long idSolicitacao);
	

	/**
	 * Obtem prescricao de cordão.
	 * 
	 * @param idPrescricao
	 * @return PrescricaoCordaoDTO
	 */
	PrescricaoCordaoDTO obterPrescricaoCordaoPorId(Long idPrescricao);

	PrescricaoEvolucaoDTO obterPrescricaoComEvolucaoPorIdPrescricao(Long idPrescricao);
	
	/**
	 * Obtém a prescrição pelo identificador.
	 * 
	 * @param id Identificador da prescrição
	 * @return prescrição encontrada ou throw BusinessException
	 */
	Prescricao obterPrescricao(Long id);
	
	
	/**
	 * Lista as prescrições sem autorização do paciente.
	 * 
	 * @param idCentroTransplante  Identificador do centro de transplante;
	 * @param atribuidoAMin somente os atribuidos ao usuário logado.
	 * @param pagina 
	 * @param quantidadeRegistros
	 * @return lista paginada com as prescrições sem autorização do paciente.
	 */
	Page<PrescricaoSemAutorizacaoPacienteDTO> listarPrescricoesSemAutorizacaoPaciente(
			Long idCentroTransplante, Boolean atribuidoAMin, int pagina, int quantidadeRegistros);
	
	/**
	 * Método para salvar o aquivo de autorização do paciente.
	 * 
	 * @param id Identificador da prescrição
	 * @param arquivo Arquivo de Autorização do Paciente
	 */
	void salvarArquivoAutorizacaoPaciente(Long id, MultipartFile arquivo);
	
}
