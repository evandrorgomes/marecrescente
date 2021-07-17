package br.org.cancer.modred.authorization.restriction;

import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoTransferenciaCentro;
import br.org.cancer.modred.model.domain.TiposTransferenciaCentro;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.persistence.IPedidoTransferenciaCentroRepository;

/**
 * @author Pizão
 * 
 * Verifica se o usuário logado é o médico responsável pela avaliação.
 * 
 */
public class PermissaoPerfilMedicoAvaliadorParaTransferencia implements IRestricaoCustomizada<PedidoTransferenciaCentro> {

	private IGenericRepository genericRepository;

	public PermissaoPerfilMedicoAvaliadorParaTransferencia(IGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	@Override
	public boolean hasPermissao(CustomUser usuario, String recurso, PedidoTransferenciaCentro pedido) {
		boolean possui = false;

		if (pedido != null && pedido.getCentroAvaliadorDestino() != null) {
			if (usuario.getCentros() != null && usuario.getCentros().size() != 0) {
				possui = usuario.getCentros().stream()
						.anyMatch(centro -> centro.getId().equals(pedido.getCentroAvaliadorDestino().getId()));
			}
			
		}

		return possui;
	}

	@Override
	public PedidoTransferenciaCentro castTo(Object entity) {
		IPedidoTransferenciaCentroRepository repository = (IPedidoTransferenciaCentroRepository) genericRepository.getRepository(PedidoTransferenciaCentro.class);

		if (entity instanceof Paciente) {
			return repository.findFirstByPacienteRmrAndTipoTransferenciaCentroOrderByIdDesc(( (Paciente) entity ).getRmr(), TiposTransferenciaCentro.AVALIADOR);
		}
		if (entity instanceof PedidoTransferenciaCentro) {
			return (PedidoTransferenciaCentro) entity;
		}
		
		return null;
	}

}
