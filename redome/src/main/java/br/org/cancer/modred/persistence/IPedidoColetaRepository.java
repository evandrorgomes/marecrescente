package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.controller.dto.DadosCentroTransplanteDTO;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.ContatoTelefonico;
import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoColeta;

/**
 * Interface para acesso ao banco de dados envolvendo a classe PedidoColeta.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IPedidoColetaRepository extends IRepository<PedidoColeta, Long> {
	
	/**
	 * Obter o pedido de coleta associado a solicitação passada como parâmetro.
	 * 
	 * @param solicitacaoId ID da solicitação a ser utilizado no filtro.
	 * @param statusPedidoColetaId ID do status do workup.
	 * @return pedido de coleta associado.
	 */
	@Query("select pc from PedidoColeta pc join pc.solicitacao s where s.id = :solicitacaoId and (pc.statusPedidoColeta.id = :statusPedidoColetaId or :statusPedidoColetaId is null) ")
	PedidoColeta obterPedidoColeta(@Param("solicitacaoId") Long solicitacaoId, @Param("statusPedidoColetaId") Long statusPedidoColetaId);
	
	
	/**
	 * Obtem pedido de coleta por id do doador.
	 * @param idDoador - id do doador.
	 * @return pedido de coleta localizado.
	 */
	PedidoColeta findBySolicitacaoMatchDoadorId(Long idDoador);

	/**
	 * Obtém quem é o paciente envolvido no match que originou o pedido de coleta
	 * para o doador.
	 * 
	 * @param pedidoColetaId ID do pedido de coleta.
	 * @return paciente associado ao pedido de coleta.
	 */
	@Query("select paci "
		+ "from PedidoColeta pc "
		+ "join pc.solicitacao s "
		+ "join s.match m "
		+ "join m.busca b "
		+ "join b.paciente paci "
		+ "where pc.id = :pedidoColetaId ")
	Paciente obterPaciente(@Param("pedidoColetaId") Long pedidoColetaId);
	
	/**
	 * Obtém quem é o doador envolvido no match que originou o pedido de coleta.
	 * 
	 * @param pedidoColetaId ID do pedido de coleta.
	 * @return doador associado ao pedido de coleta.
	 */
	@Query("select doad "
		+ "from PedidoColeta pc "
		+ "join pc.solicitacao s "
		+ "join s.match m "
		+ "join m.doador doad "
		+ "where pc.id = :pedidoColetaId ")
	Doador obterDoador(@Param("pedidoColetaId") Long pedidoColetaId);
	
	@Query("select cc "
			+ "from PedidoColeta pc "
			+ "join pc.centroColeta cc "
			+ "where pc.id = :pedidoColetaId ")
	CentroTransplante obterCentroColeta(@Param("pedidoColetaId") Long pedidoColetaId);
	
	/**
	 * Obter a lista de disponibilidades trocadas entre o analista do redome e o médico do CT para o pedido informado.
	 * 
	 * @param pedidoId - id do pedido de coleta.
	 * @return a lista de disponibilidades.
	 */
	@Query("select d from Disponibilidade d join d.pedidoColeta p "
			+ "where p.id = :pedidoId order by d.dataCriacao")
	List<Disponibilidade> listarDisponibilidadesPorPedido(@Param("pedidoId") Long pedidoId);
	
	@Query(	"select pc from PedidoColeta pc join pc.statusPedidoColeta stCol "
		+ 	"where pc.id = :idPedidoColeta and stCol.id = 5")
	PedidoColeta obterPedidoColetaComStatusAguardandoCT(@Param("idPedidoColeta") Long idPedidoColeta);
	
	
	/**
	 * Busca os dados de CT.
	 * 
	 * @param idPedido
	 * @return
	 */
	@Query("select new br.org.cancer.modred.controller.dto.DadosCentroTransplanteDTO(paci.rmr,paci.nome,med.nome,ct.nome) from PedidoColeta pc "
			+ " join pc.solicitacao solic "
			+ " join solic.match matc "
			+ " join matc.busca busc "
			+ " join solic.prescricao presc "
			+ " join presc.medicoResponsavel med"
			+ " join busc.paciente paci "
			+ " join busc.centroTransplante ct"			
			+ " where pc.id = :idPedido")
	DadosCentroTransplanteDTO buscarDadosCT(@Param("idPedido") Long idPedido);

	/**
	 * Busca todos os telefones do Centro de transplante.
	 * 
	 * @param idPedido
	 * @return List<ContatoTelefonico> telefones
	 */
	@Query("select ct.contatosTelefonicos from PedidoColeta pc "
			+ " join pc.solicitacao solic "
			+ " join solic.prescricao presc "
			+ " join solic.match matc "
			+ " join matc.busca busc "
			+ " join busc.paciente paci "
			+ " join busc.centroTransplante ct"
			+ " where pc.id = :idPedido")
	List<ContatoTelefonico> buscarTelefonesCT(@Param("idPedido") Long idPedido);

	/**
	 * Busca todos os telefones do médico.
	 * 
	 * @param idPedido
	 * @return List<ContatoTelefonico> telefones
	 */
	@Query("select med.contatosTelefonicos from PedidoColeta pc "
			+ " join pc.solicitacao solic "
			+ " join solic.match matc "
			+ " join solic.prescricao presc "
			+ " join presc.medicoResponsavel med"
			+ " join matc.busca busc "
			+ " join busc.paciente paci "
			+ " where pc.id = :idPedido")
	List<ContatoTelefonico> buscarTelefonesMedico(@Param("idPedido") Long idPedido);


}
