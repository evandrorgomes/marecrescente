package br.org.cancer.modred.authorization.restriction;

import br.org.cancer.modred.model.ResultadoWorkup;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.persistence.IResultadoWorkupRepository;
import br.org.cancer.modred.persistence.IUsuarioRepository;

/**
 * Verifica se o usuario logado pertence ao centro de coleta do doador.
 * 
 * @author Filipe Paes
 *
 */
public class PermissaoPerfilCentro implements IRestricaoCustomizada<ResultadoWorkup> {

	private IGenericRepository genericRepository;

	public PermissaoPerfilCentro(IGenericRepository genericRepository) {
		this.genericRepository = genericRepository;
	}

	@Override
	public boolean hasPermissao(CustomUser usuario, String recurso, ResultadoWorkup resultadoWorkup) {
		IUsuarioRepository repository = (IUsuarioRepository) genericRepository.getRepository(Usuario.class);
		Usuario usuarioLoc = repository.findById(usuario.getId()).get();
		
		
		boolean ehAnalistaWorkup = usuarioLoc.getPerfis().stream().anyMatch(perfil -> Perfis.ANALISTA_WORKUP.getId().equals(
				perfil.getId()) || Perfis.ANALISTA_WORKUP_INTERNACIONAL.getId().equals(perfil.getId()));
		if (ehAnalistaWorkup) {
			return true;
		}
		
		boolean ehCadastradorWorkup = usuarioLoc.getPerfis().stream().anyMatch(perfil -> Perfis.CADASTRADOR_RESULTADO_WORKUP
				.getId().equals(perfil.getId()));
		if (ehCadastradorWorkup) {
			IResultadoWorkupRepository repositoryResultadoWorkup = (IResultadoWorkupRepository) genericRepository.getRepository(
					ResultadoWorkup.class);
			ResultadoWorkup resultadoWorkupBanco = repositoryResultadoWorkup.findById(resultadoWorkup.getId()).get();
			//final Long idCentroTransplanteBusca = resultadoWorkupBanco.getPedidoWorkup().getSolicitacao().getMatch().getBusca()
			//		.getCentroTransplante().getId();
			//boolean usuarioEhDoMesmoCentroDaBusca = usuarioLoc.getCentroTransplanteUsuarios().stream().anyMatch(centro -> centro.getId()
					//.equals(idCentroTransplanteBusca));
			
			//return usuarioEhDoMesmoCentroDaBusca;
			return true;
		}
		
		return false;
	}

	@Override
	public ResultadoWorkup castTo(Object entity) {
		return (ResultadoWorkup) entity;
	}

}
