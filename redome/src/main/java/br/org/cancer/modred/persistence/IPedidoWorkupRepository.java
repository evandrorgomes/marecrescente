package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.controller.dto.DadosCentroTransplanteDTO;
import br.org.cancer.modred.model.ContatoTelefonico;
import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoWorkup;

/**
 * Interface para percistencia de Pedido de Workup.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IPedidoWorkupRepository extends IRepository<PedidoWorkup, Long> {

	/**
	 * Método para listar pedidos por idDoador, rmr e se está aberto.
	 * 
	 * @param idDoador - id do doador.
	 * @param rmr - id do paciente.
	 * @param status - status desejados.
	 * @return lista de pedidos de enriquecimento.
	 */
	@Query("select p from PedidoWorkup p join p.solicitacao s join s.tipoSolicitacao t "
			+ "join s.match m join m.busca b "
			+ "where t.id = :tiposolicitacao "
			+ "and m.doador.id = :doador "
			+ "and b.paciente.rmr = :paciente "
			+ "and p.status in :status")
	List<PedidoWorkup> listarPedidos(@Param("tiposolicitacao") Long tipoSolicitacao, @Param("doador") Long idDoador,
			@Param("paciente") Long rmr, @Param("status") List<Long> status);

	/**
	 * Método para obter paciente associado ao pedido.
	 * 
	 * @param pedidoId - id do paciente.
	 * @return paciente associado.
	 */
	@Query("select paci from PedidoWorkup p join p.solicitacao s "
			+ "join s.match m join m.busca b join b.paciente paci "
			+ "where p.id = :pedidoId")
	Paciente obterPaciente(@Param("pedidoId") Long pedidoId);

	/**
	 * Obter a lista de disponibilidades trocadas entre o analista do redome e o médico do CT para o pedido informado.
	 * 
	 * @param pedidoId - id do pedido de workup.
	 * @return a lista de disponibilidades.
	 */
	@Query("select d from Disponibilidade d join d.pedidoWorkup p "
			+ "where p.id = :pedidoId order by d.dataCriacao")
	List<Disponibilidade> listarDisponibilidadesPorPedido(@Param("pedidoId") Long pedidoId);

	/**
	 * Busca os dados de CT.
	 * 
	 * @param idPedido
	 * @return
	 */
	@Query("select new br.org.cancer.modred.controller.dto.DadosCentroTransplanteDTO(paci.rmr,paci.nome,med.nome,ct.nome) from PedidoWorkup pw "
			+ " join pw.solicitacao solic "
			+ " join solic.match matc "
			+ " join matc.busca busc "
			+ " join solic.prescricao presc "
			+ " join presc.medicoResponsavel med"
			+ " join busc.paciente paci "
			+ " join busc.centroTransplante ct"			
			+ " where pw.id = :idPedido")
	DadosCentroTransplanteDTO buscarDadosCT(@Param("idPedido") Long idPedido);

	/**
	 * Busca todos os telefones do Centro de transplante.
	 * 
	 * @param idPedido
	 * @return List<ContatoTelefonico> telefones
	 */
	@Query("select ct.contatosTelefonicos from PedidoWorkup pw "
			+ " join pw.solicitacao solic "
			+ " join solic.prescricao presc "
			+ " join solic.match matc "
			+ " join matc.busca busc "
			+ " join busc.paciente paci "
			+ " join busc.centroTransplante ct"
			+ " where pw.id = :idPedido")
	List<ContatoTelefonico> buscarTelefonesCT(@Param("idPedido") Long idPedido);

	/**
	 * Busca todos os telefones do médico.
	 * 
	 * @param idPedido
	 * @return List<ContatoTelefonico> telefones
	 */
	@Query("select med.contatosTelefonicos from PedidoWorkup pw "
			+ " join pw.solicitacao solic "
			+ " join solic.match matc "
			+ " join solic.prescricao presc "
			+ " join presc.medicoResponsavel med"
			+ " join matc.busca busc "
			+ " join busc.paciente paci "
			+ " where pw.id = :idPedido")
	List<ContatoTelefonico> buscarTelefonesMedico(@Param("idPedido") Long idPedido);

	/**
	 * Obter o pedido de workup associado a solicitação e com o status informados como parâmetro.
	 * 
	 * @param solicitacaoId ID da solicitação a ser utilizado no filtro.
	 * @param statusPedidoWorkupId ID do status do pedido de workup.
	 * @return pedido de coleta associado.
	 */
	@Query("select pw from Solicitacao s join s.pedidoWorkup pw where s.id = :solicitacaoId and (pw.status = :statusPedidoWorkupId or :statusPedidoWorkupId is null) ")
	PedidoWorkup obterPedidoWorkup(@Param("solicitacaoId") Long solicitacaoId,
			@Param("statusPedidoWorkupId") Long statusPedidoWorkupId);
	
	/**
	 * Método para obter doador associado ao pedido.
	 * 
	 * @param pedidoId - id do pedido de workup.
	 * @return doador associado.
	 */
	@Query("select d from PedidoWorkup p join p.solicitacao s "
			+ "join s.match m join m.doador d "
			+ "where p.id = :pedidoId")
	Doador obterDoador(@Param("pedidoId") Long pedidoId);
}
