package br.org.cancer.modred.test.util.authorization;

public interface IAuthorizationForUser extends IAuthorization {
	
	IAuthorizationForUser addRecurso(String...recurso);
	IAuthorizationForUser addPerfil(String...perfil);

}
