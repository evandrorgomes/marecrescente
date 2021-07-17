package br.org.cancer.modred.service;

import java.io.File;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.model.PreCadastroMedico;
import br.org.cancer.modred.model.domain.StatusPreCadastro;
import br.org.cancer.modred.service.custom.IService;

/**
 * Define os métodos necessários para acesso as funcionalidades que envolvem ou estão relacionadas
 * com a entidade PreCadastroMedico.
 * 
 * @author Pizão
 */
public interface IPreCadastroMedicoService extends IService<PreCadastroMedico, Long> {
	
	/**
	 * Inclui um novo pré cadastro médico.
	 * Ele é criado com status "Aguardando Aprovação" e 
	 * depende da conferência e aprovação para ter acesso as
	 * funcionalidades do sistema.
	 * 
	 * @param preCadastro entidade de pré cadastro médico.
	 * @param arquivoCrm arquivo com a informação do CRM. 
	 */
	void incluir(PreCadastroMedico preCadastro, MultipartFile arquivoCrm);
	
	/**
	 * Método para validar se o crm ou o login já exitem no pré cadastro ou no cadastro do médico. 
	 * 
	 * @param crm
	 * @param login
	 */
	void validarIdentificacao(String crm, String login); 
	
	
	/**
	 * Método para listar Pré Cadastro Médico por status e paginado.
	 * 
	 * @param status - Filtro obrigatório
	 * @param pageRequest - Paginação
	 * @return Lista Paginada.
	 */
	PageImpl<PreCadastroMedico> listarPreCadastroMedicoPorStatus(StatusPreCadastro status, PageRequest pageRequest);

	/**
	 * Método para obter o pré cadastro por id.
	 * 
	 * @param id - Identificador do pré cadastro
	 * @return entidade PréCadastroMedico
	 */
	PreCadastroMedico obterPreCadastroMedicoPorId(Long id);

	
	/**
	 * Método para reprovar um pré cadastro médido e enviar um email com o status e a justificativa.
	 * 
	 * @param id - Identificador do pré cadastro.
	 * @param justificativa - Justificativa da reprovação 
	 */
	void reprovarPreCadastroMedico(Long id, String justificativa);
	
	/**
	 * Aprova o pré cadastro realizado pelo médico.
	 * 
	 * @param id ID do pré cadastro.
	 */
	void aprovar(Long id);
	
	/**
	 * Arquivo de CRM associado ao pré cadastro.
	 * 
	 * @param idPreCadastroMedico ID do pré cadastro médico.
	 * @return arquivo pronto para ser serializado.
	 */
	File obterArquivoCRM(Long idPreCadastroMedico);
}
