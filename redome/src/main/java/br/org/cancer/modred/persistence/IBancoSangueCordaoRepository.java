package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.BancoSangueCordao;
import br.org.cancer.modred.model.UsuarioBancoSangueCordao;

/**
 * Classe de manipulação de dados sobre Banco de Sangue de Cordao.
 * @author Filipe Paes
 *
 */
@Repository
public interface IBancoSangueCordaoRepository extends IRepository<BancoSangueCordao, Long> {

	/**
	 * Obterm um registro de banco por id.
	 * @param idBanco - id do banco de sangue brasilcord a ser localizado.
	 * @return banco de sangue com seus devidos dados.
	 */
	BancoSangueCordao findByIdBancoSangueCordao(Long idBanco);
	
	/**
	 * Obtém o banco de sangue de mesma sigla informada.
	 * 
	 * @param sigla sigla do banco de sangue.
	 * @return o próprio banco encontrado, se houver.
	 */
	BancoSangueCordao findBySigla(String sigla);
	
	/**
	 * Lista os usuários associados ao banco de cordão.
	 * @FIXME O que devemos considerar para um usuário ativo ou não.
	 * 
	 * @param idBancoSangueCordao ID do banco de sangue.
	 * @return lista de usuários.
	 */
	@Query(	"select usua from UsuarioBancoSangueCordao usua "
		+ 	"where usua.bancoSangue.id = :idBancoSangue and usua.ativo = true")
	List<UsuarioBancoSangueCordao> listaUsuariosAtivos(@Param("idBancoSangue") Long idBancoSangueCordao);
}
