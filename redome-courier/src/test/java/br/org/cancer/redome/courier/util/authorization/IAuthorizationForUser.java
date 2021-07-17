package br.org.cancer.redome.courier.util.authorization;

public interface IAuthorizationForUser extends IAuthorization {
	
	IAuthorizationForUser addRecurso(String...recurso);
	IAuthorizationForUser addPerfil(String...perfil);
	IAuthorizationForUser transportadora(Long id);

}
