package br.org.cancer.redome.tarefa.util.authorization;

public interface IAuthorizationForUser extends IAuthorization {
	
	IAuthorizationForUser addRecurso(String...recurso);
	IAuthorizationForUser addPerfil(String...perfil);

}
