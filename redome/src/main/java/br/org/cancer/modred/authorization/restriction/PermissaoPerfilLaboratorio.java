package br.org.cancer.modred.authorization.restriction;

import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.persistence.IUsuarioRepository;

/**
 * Classe para verificar permissão do usuário em determinado laboratorio.
 * @author Filipe Paes
 *
 */
public class PermissaoPerfilLaboratorio implements IRestricaoCustomizada<Laboratorio> {

	private IGenericRepository genericRepository;
	
	public PermissaoPerfilLaboratorio(IGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	@Override
	public boolean hasPermissao(CustomUser usuario, String recurso, Laboratorio laboratorio) {
		if(laboratorio != null){
			IUsuarioRepository repository = (IUsuarioRepository) genericRepository.getRepository(Usuario.class);
			Usuario usuarioLoc = repository.findById(usuario.getId()).get();
			if(usuarioLoc.getLaboratorio() == null){
				return true;
			}
			else{
				return usuarioLoc.getLaboratorio().equals(laboratorio);
			}
		}
		return true;
	}

	@Override
	public Laboratorio castTo(Object entity) {
		return (Laboratorio) entity;
	}

}
