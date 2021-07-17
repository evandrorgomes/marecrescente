package br.org.cancer.modred.service;

import java.io.File;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.ContatoTelefonicoMedico;
import br.org.cancer.modred.model.EmailContatoMedico;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negócios para Médico.
 * @author bruno.sousa
 *
 */
public interface IMedicoService extends IService<Medico, Long> {
    
    /**
     * Método para obter o médico por usuário.
     * @param usuarioId
     * @return Medico
     */
    Medico obterMedicoPorUsuario(Long usuarioId);

    
    /**
     * Método para obter o médico por id.
     * @param id
     * @return Medico
     */    
    Medico obterMedico(Long id);


	/**
	 * Método que verifica o CRM já existe na base.
	 * 
	 * @param crm
	 * @param idMedico
	 * @return True - se o CRM existir.
	 */
	boolean verificarCrm(String crm, Long idMedico);
	
	/**
	 * Após aprovação do pré cadastro do médico, o acesso ao Redome será concedido após realizadas das seguintes etapas:
	 * - Comunicar a aprovação ao médico que realizou o cadastro utilizando o e-mail informado.
	 * - Criar usuário e gerar uma senha temporária.
	 * - Associar o usuário aos dados do médico aprovado contendo endereço, e-mails e telefones.
	 * 
	 * @param idPreCadastroMedico ID do pré cadastro que originou a concessão de acesso.
	 * @return Uma instancia de médico
	 * 
	 */
	Medico concederAcessoMedico(Long idPreCadastroMedico);


	/**
	 * Método para listar o cadastro de médicos paginado.
	 * 
	 * @param pageRequest - Paginação
	 * @return - Lista paginada.
	 */
	PageImpl<Medico> listarMedicos(String crm, String nome, PageRequest pageRequest);
	
	/**
	 * Lista médicos.
	 * @return listagem de médicos não paginada.
	 */
	List<Medico> listarMedicos();


	/**
	 * Método para atualizar os dados de identificação do médico (CRM e Nome).
	 * 
	 * @param id - Identificador do médico
	 * @param medico - Dados a serem alterados
	 */
	void salvarDadosIdentificacao(Long id, Medico medico);
	
	/**
	 * Arquivo de CRM associado ao médico.
	 * 
	 * @param idMedico ID do médico.
	 * @return arquivo pronto para ser serializado.
	 */
	File obterArquivoCRM(Long idMedico);
	
	/**
	 * Adiciona um novo e-mail para o médico informado.
	 * 
	 * @param idMedico ID do médico.
	 * @param email e-mail que será incluído.
	 */
	void adicionarEmail(Long idMedico, EmailContatoMedico email);
	
	/**
	 * Adiciona um novo telefone para o médico informado.
	 * 
	 * @param idMedico ID do médico.
	 * @param telefone telefone que será associado ao médico.
	 */
	void adicionarTelefone(Long idMedico, ContatoTelefonicoMedico telefone);
	

	/**
	 * Método para salvar os centros de referência (Centro de Transplante com função avaliador) de um médico.
	 * 
	 * @param id - Identificador do médico
	 * @param centrosReferencia - Lista de centros de referência.
	 */
	void salvarCentrosReferencia(Long id, List<CentroTransplante> centrosReferencia);
	
	/**
	 * Obtém o médico associado ao usuário logado.
	 * 
	 * @return médico associado ao usuário.
	 */
	Medico obterMedicoAssociadoUsuarioLogado();
}
