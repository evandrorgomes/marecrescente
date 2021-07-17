package br.org.cancer.modred.service;

import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.Paciente;

/**
 * Interface de serviço para rascunho.
 * 
 * @author Filipe Paes
 *
 */
@Service
public interface IRascunhoService {

	/**
	 * Método para persistir rascunho.
	 * 
	 * @param rascunho a ser persistido
	 */
	void salvarOuAtualizar(Paciente paciente, Long idUsuario);

	/**
	 * Método para obter rascunho por Id do usuario.
	 * 
	 * @param Id do usuario
	 * @return Rascunho preenchido
	 */
	Paciente obterRascunho(Long idUsuario);

	/**
	 * Método para excluir rascunho por id de Usuário.
	 * 
	 * @param Long id do usuário
	 */
	void excluirPorIdDeUsuario(Long idUsuario);

	/**
	 * Remove do draft o que não está no storage.
	 * 
	 * @param idUsuario identificacao do usuario
	 * @param paciente
	 */
	void removeDoDraftOqueNaoEstaNoStorage(Long idUsuario, Paciente paciente);

	/**
	 * Remove do storage o que não está no draft. Durante o processo de upload, um arquivo pode ser carregado para o storage e,
	 * por algum motivo (como o usuário sair da tela de cadastro, por exemplo) o draft não ser salvo. Com isso, pode ocorrer de
	 * arquivos existirem no draft e não estar associado a nenhum draft.
	 * 
	 * @param idUsuario identificacao do usuario
	 * @param paciente
	 */
	void removeDoStorageOqueNaoEstaNoDraft(Long idUsuario, Paciente paciente);

}
