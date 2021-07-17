package br.org.cancer.modred.authorization.restriction;

import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.persistence.IGenericRepository;

/**
 * Verifica se o usuário logado é um médico transplantador do mesmo centro da busca. 
 * 
 * @author bruno.sousa
 * 
 */
public class PermissaoPerfilMedicoTransplantadorCentroTransplante implements IRestricaoCustomizada<CentroTransplante> {

	private IGenericRepository genericRepository;

	public PermissaoPerfilMedicoTransplantadorCentroTransplante(IGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	@Override
	public boolean hasPermissao(CustomUser usuario, String recurso, CentroTransplante centroTransplante) {		
		boolean possui = false;

		if (centroTransplante != null) {
			if (usuario.getCentros() != null && usuario.getCentros().size() != 0) {
				possui = usuario.getCentros().stream()
						.anyMatch(centro -> centroTransplante.getId().equals(centro.getId()));
			}			
		}

		return possui;
	}

	@Override
	public CentroTransplante castTo(Object entity) {

		if (entity instanceof CentroTransplante) {
			return (CentroTransplante) entity; 
		}
		return null;
	}

}
