package br.org.cancer.redome.tarefa.util.authorization;

public interface IAuthorizationForResource extends IAuthorization {
	
	IAuthorizationForResource addResource(String...resource);
	IAuthorizationForResource addScope(String...scope);	

}
