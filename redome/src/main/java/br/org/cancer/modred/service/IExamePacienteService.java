package br.org.cancer.modred.service;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.ExameDto;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Interface para o serviço que trata do acesso a entidade Exame e relacionadas.
 * 
 * @author Pizão
 *
 */
public interface IExamePacienteService extends IExameService<ExamePaciente> {

	/**
	 * Lista todos os exames envolvendo o paciente com RMR informado Não conferidos.
	 * 
	 * @param rmr do paciente pesquisado
	 * @return lista de exames, se houverem.
	 */
	List<ExamePaciente> listarExamesPorPaciente(Long pacienteRmr);
	
	/**
	 * Lista todos os exames envolvendo o paciente com RMR informado.
	 * 
	 * @param rmr do paciente pesquisado
	 * @return lista de exames, se houverem.
	 */
	ExameDto listarExamesPorRmr(Long rmr);

	/**
	 * Método para gerar arquivo de zip com todos os arquivos de laudo de exame.
	 * 
	 * @param idExame
	 * @return String nome do arquivo de zip
	 * @throws BusinessException
	 */
	File obterZipArquivosLaudo(Long idExame) throws BusinessException;

	/**
	 * Salva um novo exame para um paciente.
	 * 
	 * @param listaArquivosLaudo
	 * @param exame
	 * @throws Exception
	 */
	void salvar(List<MultipartFile> listaArquivosLaudo, ExamePaciente exame) throws Exception;
	
	/**
	 * Salva um novo exame para um paciente que, antes, deverá passar por avaliação do médico redome.
	 * 
	 * @param listaArquivosLaudo
	 * @param exame
	 * @throws Exception
	 */
	void salvarParaAvaliacao(List<MultipartFile> listaArquivosLaudo, ExamePaciente exame) throws Exception;

	/**
	 * Método que salva um exame associado a uma pendência.
	 * 
	 * @param listaArquivosLaudo
	 * @param exame
	 * @param pendencias
	 * @param resposta
	 * @param respondePendencia
	 * @throws Exception
	 */
	void salvar(List<MultipartFile> listaArquivosLaudo, ExamePaciente exame, List<Pendencia> pendencias, String resposta,
			Boolean respondePendencia) throws Exception;

	/**
	 * Método para obter o exame para aprovar ou reprovar o exame Exame buscado deverá obter a regra de prioridade do "score" e
	 * desempate por data de criação do exame.
	 * 
	 * @return ExameDto prioritário
	 */
	//ExameDto obterExameParaConferencia();
	
	/**
	 * Lista tarefas de conferencia de exame para conferência.
	 * @param rmr do paciente.
	 * @param nomePaciente a ser consultado.
	 * @param pageRequest - parâmetros de paginação como a página a ser listada.
	 * @return listagem de tarefas de exames.
	 */
	JsonViewPage<TarefaDTO> listaTarefasDeExamesParaConferencia(Long rmr, String nomePaciente, PageRequest pageRequest);

	/**
	 * Aceita o exame, após conferência, atualizando-o com novas informações, caso tenha sido alterado.
	 * 
	 * @param exame que será atualizado na base.
	 */
	void aceitar(ExamePaciente exame);

	/**
	 * Descarta o exame, após conferência, informando o motivo do descarte.
	 * 
	 * @param exame que será atualizado na base.
	 * @param motivoDescarteId ID com o motivo do descarte.
	 */
	void descartar(Long exameId, Long motivoDescarteId);


	/**
	 * Cria um processo do tipo CONFERENCIA_EXAMES associado ao paciente. Este processo é criado para acompanhar a conferência do
	 * exame da sua criação, no cadastro do paciente, até a sua aceitação (ou não) na conferência.
	 * 
	 * @param paciente a ser associado ao processo.
	 * @return processo salvo na base.
	 */
	void criarProcessoConferenciaExames(Paciente paciente);

	/**
	 * Lista os exames, com todas as informações do HLA, relacionados ao RMR informado.
	 * 
	 * @param rmr RMR do paciente a ser pesquisado.
	 * @return lista de exames do paciente.
	 */
	List<ExamePaciente> listarInformacoesExames(Long rmr);

	/**
	 * Valida o exame, quanto aos valores de locus possíveis, e os seus respectivos valores alélicos. Caso ocorra alguma
	 * inconsistência, uma exceção de negócio será lançada.
	 * 
	 * @param exame exame a ser validado.
	 * @return campos de erro contendo o resultado da validação do exame (mensagens a serem exibidas).
	 */
	List<CampoMensagem> validarExame(ExamePaciente exame);

	/**
	 * Obtém o pedido de exame de classe 2 C.
	 * 
	 * @param idExame -identificador do exame
	 * @param codigo - codigo do relatorio.
	 * @return
	 */
	File obterPedidoExame(Long idExame, String codigo);

}
