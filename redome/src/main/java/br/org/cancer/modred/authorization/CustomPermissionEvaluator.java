package br.org.cancer.modred.authorization;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.authorization.restriction.IRestricaoCustomizada;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.model.security.Permissao;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.service.IUsuarioService;

/**
 * Classe customizada para avaliação das permissões utilizando as annotations do Spring Security.
 * 
 * @author Cintia Oliveira
 *
 */
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class CustomPermissionEvaluator implements PermissionEvaluator {

	private static final String PACKAGE_MODEL = "br.org.cancer.modred.model.";
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomPermissionEvaluator.class);

	private IGenericRepository genericRepository;
	private IUsuarioService usuarioService;

	private VerificarPermissaoAcesso verificadorPermissoes;

	/**
	 * Construtor de CustomPermissionEvaluator.
	 * 
	 * @param usuarioService serviço para obter dados do usuário logado.
	 * @param genericRepository repositorio generico
	 */
	public CustomPermissionEvaluator(IUsuarioService usuarioService,
			IGenericRepository genericRepository) {
		super();
		this.usuarioService = usuarioService;
		this.genericRepository = genericRepository;
		verificadorPermissoes = new VerificarPermissaoAcesso(genericRepository);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication,
	 * java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Object domainObject,
			Object recurso) {
		return possuiPermissao(authentication, recurso.toString(), domainObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.PermissionEvaluator#hasPermission(org.springframework.security.core.Authentication,
	 * java.io.Serializable, java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean hasPermission(Authentication authentication, Serializable id,
			String classe, Object recurso) {
		return possuiPermissao(authentication, recurso.toString(), obterObjetoDominio(id, classe));
	}

	private boolean possuiPermissao(Authentication authentication, String recurso, Object objeto) {
		LOGGER.info("Verificando a permissao do usuario={} ao recurso={}, objeto={}", authentication
				.getPrincipal(), recurso, objeto);

		boolean possui = false;
		CustomUser user = usuarioService.obterCustomUserLogado(authentication);
		if (user == null) {
			return possui;
		}
		List<Permissao> permissoesEncontradas = encontrarPermissao(user, recurso);

		if (permissoesEncontradas != null && !permissoesEncontradas.isEmpty()) {

			if (permissoesEncontradas.size() > 1) {
				for (Permissao permissao : permissoesEncontradas) {
					if (possuiPermissao(recurso, objeto, user, permissao)) {
						possui = true;
					}
				}

			}
			else {
				possui = possuiPermissao(recurso, objeto, user, permissoesEncontradas.get(0));
			}
		}

		LOGGER.info(
				"Resultado da verificacao de permissao do usuario={} "
						+ "ao recurso={}, objeto={}, possui={}",
				authentication
						.getPrincipal(), recurso, objeto, possui);

		return possui;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean possuiPermissao(String recurso, Object objeto, CustomUser user, Permissao permissao) {
		boolean possui = false;

		if (permissao.isComRestricao()) {
			List<IRestricaoCustomizada> restricoesPermissao = verificadorPermissoes.get(permissao.getId().getPerfil().getId());
			for (IRestricaoCustomizada restricaoPermissao: restricoesPermissao) {
				if (restricaoPermissao.hasPermissao(user, recurso, restricaoPermissao.castTo(objeto))) {
					possui = true;
				}
			};
			
		}
		else {
			possui = true;
		}

		return possui;
	}

	private List<Permissao> encontrarPermissao(CustomUser usuario, String recurso) {
		return usuario.getPermissoes().stream().filter(permissao -> permissao.getId().getRecurso().getSigla().equalsIgnoreCase(
				recurso)).collect(Collectors.toList());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object obterObjetoDominio(Serializable id, String classe) {
		try {
			LOGGER.info("Consultando um objeto de dominio id={}, classe={}", id, classe);

			JpaRepository repository = genericRepository.getRepository(Class.forName(
					PACKAGE_MODEL + classe));
			return repository.findById(id).get();
		}
		catch (Exception e) {
			LOGGER.error(
					"Erro ao consulta um objeto de dominio id={}, classe={}, exception={}",
					id, classe, e.getMessage());
		}

		return null;
	}

}
