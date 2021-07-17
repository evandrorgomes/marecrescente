package br.org.cancer.modred.model.domain;

/**
 * Enum que define o status do Pré Cadastro.
 * AGUARDANDO APROVAÇÃO: Foi cadastrado pelo médico mas ainda não foi avaliado.
 * APROVADO: Foi avaliador positivamente e já ao sistema.
 * REPROVADO: Foi recusado seu acesso. Pode ser porque o documento que comprova
 * ser um médico (arquivo CRM) não está legível ou não condiz com o informado, por exemplo.
 * 
 * @author Pizão
 *
 */
public enum StatusPreCadastro {
	AGUARDANDO_APROVACAO,
	APROVADO,
	REPROVADO
}
