package br.org.cancer.modred.authorization.restriction;

import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.persistence.IBuscaRepository;
import br.org.cancer.modred.persistence.IGenericRepository;

/**
 * Verifica se o usuário logado é um médico transplantador do mesmo centro da busca. 
 * 
 * @author bruno.sousa
 * 
 */
public class PermissaoPerfilMedicoTransplantador implements IRestricaoCustomizada<Busca> {

	private IGenericRepository genericRepository;

	public PermissaoPerfilMedicoTransplantador(IGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	@Override
	public boolean hasPermissao(CustomUser usuario, String recurso, Busca busca) {		
		boolean possui = false;

		if (busca != null && busca.getCentroTransplante()  != null) {
			//Medico logado = repository.findByUsuarioId(usuario.getId());
			if (usuario.getCentros() != null && usuario.getCentros().size() != 0) {
				possui = usuario.getCentros().stream()
						.anyMatch(centro -> busca.getCentroTransplante().getId().equals(centro.getId()));
			}			
		}

		return possui;
	}

	@Override
	public Busca castTo(Object entity) {
		IBuscaRepository repository = (IBuscaRepository) genericRepository.getRepository(Busca.class);

		if (entity instanceof Paciente) {
			return repository.findByPacienteRmrAndStatusBuscaAtiva(( (Paciente) entity ).getRmr(), true);
		}else if (entity instanceof Prescricao) {
			return ((Prescricao)entity).getSolicitacao().getMatch().getBusca();
		}
		else if (entity instanceof Busca) {
			return (Busca) entity;
		}
		return null;
	}

}
