package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Medico;
import br.org.cancer.modred.model.Paciente;

/**
 * Interface do paciente.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IPacienteRepository extends IRepository<Paciente, Long>, IPacienteRepositoryCustom {

	/**
	 * Busca paciente por CPF.
	 * 
	 * @param cpf CPF do paciente a ser pesquisado
	 * @return
	 */
	Paciente findByCpf(String cpf);

	/**
	 * Busca paciente por CNS.
	 * 
	 * @param cns CNS do paciente a ser pesquisado
	 * @return
	 */
	Paciente findByCns(String cns);

	
	/**
	 * Retorna o paciente de acordo com os critérios de chave composta definidos para o sistema.
	 * 
	 * @param nome
	 * @param dataNascimento
	 * @param nomeMae (Pode ser nulo no banco e, nesse caso, não será considerado no filtro)
	 * @return Paciente com as mesmas características únicas
	 */
	@Query(" select p " +
			" from Paciente p " +
			" where upper(p.nome) = upper(?1) " +
			" and to_char(p.dataNascimento, 'dd/MM/yyyy') = ?2 ")
	List<Paciente> listarPacientePorNomeAndDataNascimento(String nome, String dataNascimento);

	/**
	 * Método que retorna apenas a identificação do paciente.
	 * 
	 * @param rmr
	 * @return Paciente
	 */
	@Query("select new Paciente(p.rmr, p.nome, p.sexo, p.abo, p.raca, p.dataNascimento, d, p.medicoResponsavel, p.etnia, p.status) " +
			" from Paciente p left join p.etnia e join p.status s, Diagnostico d where d.paciente = p and p.rmr =  ?1 ")
	@QueryHints(@javax.persistence.QueryHint(name = "org.hibernate.fetchSize", value = "1"))
	Paciente carregarPacienteSomenteComIdentificacao(Long rmr);

	/**
	 * Método que retorna apenas os dados pessoais.
	 * 
	 * @param rmr
	 * @return Paciente
	 */
	@Query("select new Paciente(p.rmr, p.cpf, p.cns, p.nome, p.nomeMae, p.sexo, p.abo, p.raca, p.dataNascimento, p.etnia, "
			+ "p.pais, p.naturalidade, p.responsavel, d)  from Paciente p left join p.etnia e left join p.responsavel r left "
			+ "join p.naturalidade n, Diagnostico d  where d.paciente = p and  p.rmr =  ?1")
	@QueryHints(@javax.persistence.QueryHint(name = "org.hibernate.fetchSize", value = "1"))	
	Paciente findDadosPessoais(Long rmr);
	
	
	/**
	 * Obtém o paciente associado ao ID da solicitação informado.
	 * 
	 * @param solicitacaoId ID da solicitação informado.
	 * @return paciente associado a solicitação.
	 */
	@Query(	  "select p "
			+ "from Solicitacao s join s.match m join m.busca b join b.paciente p "
			+ "where s.id = :solicitacaoId")
	Paciente obterPacientePorSolicitacao(@Param("solicitacaoId") Long solicitacaoId);
	
	/**
	 * Obtém o paciente associado ao ID do pedido de logística com pedido de workup.
	 * 
	 * @param pedidoLogisticaId ID do pedido de logística.
	 * @return paciente associado ao pedido.
	 */
	@Query("select new Paciente(pl.pedidoWorkup.solicitacao.match.busca.paciente.rmr) from PedidoLogistica pl where pl.id = :id")
	Paciente obterPacientePorPedidoLogisticaComPedidoWorkup(@Param("id") Long pedidoLogisticaId);
	
	/**
	 * Obtém o paciente associado ao ID do pedido de logística com pedido de coleta.
	 * 
	 * @param pedidoLogisticaId ID do pedido de logística.
	 * @return paciente associado ao pedido.
	 */
	@Query("select new Paciente(pl.pedidoColeta.solicitacao.match.busca.paciente.rmr) from PedidoLogistica pl where pl.id = :id")
	Paciente obterPacientePorPedidoLogisticaComPedidoColeta(@Param("id") Long pedidoLogisticaId);
	
	/**
	 * Obtém o paciente associado ao ID da pedido contato.
	 * 
	 * @param pedidoContatoId ID do pedido de contato.
	 * @return paciente associado ao pedido informado.
	 */
	@Query("select new Paciente(pac.rmr) from PedidoContato pc "
			+ "join pc.solicitacao sol join sol.match mat join mat.busca bus "
			+ "join bus.paciente pac "
			+ "where pc.id = :pedidoContatoId")
	Paciente obterPacientePorPedidoContato(@Param("pedidoContatoId") Long pedidoContatoId);
	
	/**
	 * Obtém o paciente associado ao ID do match.
	 * 
	 * @param matchId ID do match.
	 * @return paciente associado ao match.
	 */
	@Query("select new Paciente(pac.rmr) from Match mat "
			+ "join mat.busca bus "
			+ "join bus.paciente pac "
			+ "where mat.id = :matchId")
	Paciente obterPacientePorMatch(@Param("matchId") Long matchId);
	
	/**
	 * Obtém o paciente associado ao genótipo informado.
	 * 
	 * @param matchId ID do match.
	 * @return paciente associado ao match.
	 */
	@Query("select new Paciente(pac.rmr) from GenotipoPaciente gen "
			+ "join gen.paciente pac "
			+ "where gen.id = :genotipoId")
	Paciente obterPacientePorGenotipo(@Param("genotipoId") Long genotipoId);
	
	/**
	 * Obtém o paciente associado ao pedido de exame informado.
	 * 
	 * @param pedidoContatoId ID do pedido de contato.
	 * @return paciente associado ao pedido informado.
	 */
	@Query("select new Paciente(pac.rmr) from PedidoExame pe "
			+ "join pe.solicitacao sol join sol.match mat join mat.busca bus "
			+ "join bus.paciente pac "
			+ "where pe.id = :pedidoExameId")
	Paciente obterPacientePorPedidoExame(@Param("pedidoExameId") Long pedidoExameId);
	
	/**
	 * Obter paciente a partir do ID da solicitação de exame do teste confirmatório.
	 * 
	 * @param solicitacaoId ID da solicitação de CT.
	 * @return paciente associado ao pedido.
	 */
	@Query("select p from Solicitacao s join s.busca b join b.paciente p where s.id = :solicitacaoId")
	Paciente obterPacientePorSolicitacaoCT(@Param("solicitacaoId") Long solicitacaoId);
	
	/**
	 * Obtém o médico responsável pelo paciente.
	 * 
	 * @param rmr id do paciente.
	 * @return médico responsável.
	 */
	@Query("select m from Paciente p join p.medicoResponsavel m where p.rmr = :id")
	Medico obterMedicoResponsavel(@Param("id") Long rmr);
	
	/**
	 * Obtém o centro transplante confirmado como sendo o responsável pelo procedimento
	 * a ser realizado no paciente.
	 * 
	 * @param rmr id do paciente.
	 * @return centro de transplante confirmado.
	 */
	@Query("select ct from Busca b join b.centroTransplante ct join b.paciente p where p.rmr = :id")
	CentroTransplante obterCentroTransplanteConfirmado(@Param("id") Long rmr);
	
	@Query("select p from Paciente p join p.buscas b where b.id = :idBusca")
	Paciente obterPacientePorBusca(@Param("idBusca") Long idBusca);

}