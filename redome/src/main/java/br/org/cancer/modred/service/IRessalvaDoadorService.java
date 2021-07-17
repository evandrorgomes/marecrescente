package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.dto.doador.RessalvaDoadorDTO;
import br.org.cancer.modred.model.RessalvaDoador;

/**
 * Interface para acesso as funcionalidades envolvendo a entidade RessalvaDoador.
 * 
 * @author Pizão.
 */
public interface IRessalvaDoadorService {

	/**
	 * Salva uma nova ressalva para o doador.
	 * 
	 * @param ressalva ressalva a ser associada do doador.
	 * @return dto com ID e a mensagem de sucesso.
	 */
	RessalvaDoador salvar(RessalvaDoadorDTO ressalva);

	/**
	 * Lista as ressalvas de um doador.
	 * 
	 * @param idDoador - identificador do doador
	 * @return List<RessalvaDoador> lista com as ressalvas
	 */
	List<RessalvaDoador> listarRessalvas(Long idDoador);
	
	/**
	 * Exclui (logicamente) a ressalva informada no parâmetro.
	 * Se o doador estiver com status ATIVO COM RESSALVAS, ao se 
	 * remover a última ressalva, o mesmo deve ser alterado para
	 * ATIVO somente.
	 * 
	 * @param idRessalva ID da ressalva a ser excluída.
	 * @return TRUE se a exclusão foi realizada com sucesso, FALSE caso não tenha sido.
	 */
	boolean excluirRessalva(Long idRessalva);

	/**
	 * Adiciona ressalva ao doador, em caso de limitações quanto ao acompanhamento para doação.
	 * 
	 * @param id identificador do Doador.
	 * @param ressalva texto da ressalva a ser associado ao doador.
	 * 
	 * @return dto contendo o ID sequencial gerado para a ressalva e a mensagem de sucesso.
	 */
	RetornoInclusaoDTO adicionarRessalva(Long id, String ressalva);

}
