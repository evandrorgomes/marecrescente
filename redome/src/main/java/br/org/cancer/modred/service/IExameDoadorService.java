
package br.org.cancer.modred.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.doador.ExameDoadorNacionalDTO;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameDoadorNacional;
import br.org.cancer.modred.model.TipoExame;
import br.org.cancer.modred.model.domain.TiposExame;

/**
 * Interface para o serviço que trata do acesso as 
 * informações relativas aos exames do doador.
 * @param <T> Tipo de exame a ser consultado. Pode ser ExamePaciente ou ExameDoador.
 * 
 * @author bruno.sousa
 *
 */
public interface IExameDoadorService<T extends Exame> extends IExameService<T> {

	/**
	 * Lista os exames, com todas as informações do HLA, relacionados ao doador internacional informado.
	 * 
	 * @param idDoador identificador do doador a ser pesquisado.
	 * @return lista de exames do doador.
	 */
	List<T> listarInformacoesExames(Long idDoador);

	/**
	 * Obtém o doador associado ao exame, se houver.
	 * @param exame exame a ser consultado.
	 * 
	 * @return doador associado, se houver.
	 */
	Doador obterDoador(T exame);

	/**
	 * Método para salvar um exame para um doador internacional.
	 * 
	 * @param listaArquivosLaudo
	 * @param exame
	 * @param tipoExame
	 */
	void salvar(List<MultipartFile> listaArquivosLaudo, T exame, TipoExame tipoExame) throws Exception;
	
	/**
	 * Lista exames de acordo com uma id de doador.
	 * @param idDoador ao qual será listado os exames.
	 * 
	 * @return lista de exames do doador.
	 */
	List<T> listarExamesPorDoador(Long idDoador);

	
	/**
	 * Lista todos os exames conferidos para o doador e tipo de exame informados.
	 * 
	 * @param idDoador ID do doador.
	 * @param tipoExame tipo do exame (Fase 2, CT, etc).
	 * @return lista de exames conferidos.
	 */
	List<T> listarExamesConferidos(Long idDoador, TiposExame tipoExame);
	
	
	/**
	 * Lista os exames por orderm de criação do mais novo para o mais antigo.
	 * 
	 * @param idDoador
	 * @return lista de exames
	 */
	List<T> listarExamesPorDoadorOrdernadoPorDataCriacaoDecrescente(Long idDoador);

	ExameDoadorNacional criarExameDoadorNacional(ExameDoadorNacionalDTO exame);
	
	/**
	 * Notifica os usuários do registro sobre o cadastro do resultado de exame de doador internacional.
	 * @param id da tarefa.
	 */
	void notificarUsuariosSobreResultadoDeExameDeDoadorInternacional(Long idTarefa);

	
	
	/**
	 * Notifica os usuários do registro sobre o cadastro do resultado exame de CT internacional em 15 dias .
	 * @param id da tarefa.
	 */
	void notificacarCadastroResultadoExameCtInternacional15(Long idTarefa);

	
	/**
	 * Notifica os usuários do registro sobre o cadastro do resultado exame de CT internacional em 7 dias.
	 * @param id da tarefa.
	 */
	void notificacarCadastroResultadoExameCtInternacional7(Long idTarefa);
	
	
	/**
	 * Notifica os usuários do registro sobre o cadastro do resultado exame de IDM internacional em 15 dias.
	 * @param id da tarefa.
	 */
	void notificacarCadastroResultadoExameIdmInternacional15(Long idTarefa);
	
	
	/**
	 * Notifica os usuários do registro sobre o cadastro do resultado exame de IDM internacional em 7 dias.
	 * @param id da tarefa.
	 */
	void notificacarCadastroResultadoExameIdmInternacional7(Long idTarefa);

}
