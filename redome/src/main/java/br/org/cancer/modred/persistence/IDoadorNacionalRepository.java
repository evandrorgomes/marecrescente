package br.org.cancer.modred.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.DoadorNacional;

/**
 * Interface para acesso ao banco de dados envolvendo a classe Doador.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IDoadorNacionalRepository extends IDoadorRepository, IDoadorNacionalRepositoryCustom {

	/**
	 * Obtem o doador a partir da identificação do pedido.
	 * 
	 * @param pedidoContatoId identificação do pedido.
	 * @return doador ativo associado ao pedido informado.
	 */
	@Query("select d "
			+ "from PedidoContato p join p.solicitacao s join s.match m join m.doador d "
			+ "where p.id = :pedidoContatoId ")
	DoadorNacional obterDoadorPorPedidoContato(@Param("pedidoContatoId") Long pedidoContatoId);

	/**
	 * Obter doador por tentativa de contato.
	 * 
	 * @param tentativaContatoId - id da tentativa de contato.
	 * @return doador associado a tentativa.
	 */
	@Query("select d "
			+ "from TentativaContatoDoador t "
			+ "join t.pedidoContato p "
			+ "join p.doador d "
			+ "where t.id = :tentativaId ")
	DoadorNacional obterDoadorPorTentativaContato(@Param("tentativaId") Long tentativaContatoId);

	/**
	 * Verifica se doador já possui contato principal.
	 * 
	 * @param idDoador identificador do doador.
	 * @return TRUE se já possui contato principal.
	 */
	@Query("select case when(count(1) > 0) then true else false end "
			+ "from ContatoTelefonicoDoador c where c.doador.id = :idDoador and c.principal is true")
	boolean verificarDoadorTemContatoPrincipal(@Param("idDoador") Long idDoador);

	/**
	 * Verifica se já existe um doador com mesmo CPF.
	 * 
	 * @param idDoador identificador do doador.
	 * @return TRUE se CPF está em uso.
	 */
	@Query("select case when(count(1) > 0) then true else false end from DoadorNacional where id != :idDoador and cpf = :cpf")
	boolean verificarSeCpfDuplicado(@Param("idDoador") Long idDoador, @Param("cpf") String cpf);

	/**
	 * Verifica se já existe um doador com mesmo nome, data de nascimento e nome da mãe.
	 * 
	 * @param idDoador identificador do doador.
	 * @return TRUE se CPF está em uso.
	 */
	@Query("select case when(count(1) > 0) then true else false end "
			+ "from DoadorNacional where id != :idDoador and nome = :nome and dataNascimento = :dtNasc and nomeMae = :nomeMae")
	boolean verificarSeNomeDtNascNomeMaeDuplicado(@Param("idDoador") Long idDoador,
			@Param("nome") String nome, @Param("dtNasc") LocalDate dataNascimento, @Param("nomeMae") String nomeMae);


	@Query("  select u.sigla " +
			" from EnderecoContatoDoador e join e.municipio m join m.uf u join e.doador doad "
			+ " where doad.id = :idDoador and e.principal is true ")
	String obterUfEnderecoPrincipal(@Param("idDoador") Long idDoador);

	/**
	 * Obtem o doador ativo a partir do pedido de logística.
	 * 
	 * @param pedidoLogisticaId identificação do pedido logistica.
	 * @return doador ativo associado ao pedido informado.
	 */
	@Query("select new DoadorNacional(pw.solicitacao.match.doador.id) "
		+  "from PedidoLogistica pl left join pl.pedidoWorkup pw "
		+  "where pl.id = :pedidoLogisticaId")
	DoadorNacional obterDoadorPorLogisticaWorkup(@Param("pedidoLogisticaId") Long pedidoLogisticaId);

	/**
	 * Obtem o doador ativo a partir do pedido de logística.
	 * 
	 * @param pedidoLogisticaId identificação do pedido logistica.
	 * @return doador ativo associado ao pedido informado.
	 */
	@Query("select new DoadorNacional(d.id) "
		+  "from PedidoLogistica pl join pl.pedidoColeta pc left join pc.pedidoWorkup pw left join pw.solicitacao s left join s.match m left join m.doador d "
		+  "where pl.id = :pedidoLogisticaId")
	DoadorNacional obterDoadorPorLogisticaColeta(@Param("pedidoLogisticaId") Long pedidoLogisticaId);

	@Query(" select d " +
			" from Doador d left join d.genotipo g " + 
			" where d.dmr = 2553068 ")
	List<DoadorNacional> listarTodosDoadoresNacionais();

	@Query(" select d from Doador d where d.dmr = :dmr ")
	DoadorNacional obterDoadorNacionalPorDmr(@Param("dmr") Long dmr);

	
	Optional<DoadorNacional> findByDmr(Long dmr);

}
