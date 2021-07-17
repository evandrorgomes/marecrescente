package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.UsuarioBancoSangueCordao;

/**
 * Interface para acesso ao modelo/tabela UsuarioBancoSangueCordao.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IUsuarioBancoSangueCordaoRepository extends IRepository<UsuarioBancoSangueCordao, Long> {
	
	/**
	 * Insere e associa um usuário ao banco de sangue de cordão.
	 * 
	 * @param idUsuario ID do usuário.
	 * @param idBancoSangue ID do banco de sangue.
	 * @return quantidade de registros incluídos.
	 */
	@Modifying
	@Query(value="INSERT INTO MODRED.USUARIO_BANCO_SANGUE_CORDAO(USUA_ID, BASC_ID) VALUES(:idUsuario, :idBancoSangue)", nativeQuery = true)
	int associarUsuarioBanco(@Param("idUsuario") Long idUsuario, @Param("idBancoSangue") Long idBancoSangue);
	
}
