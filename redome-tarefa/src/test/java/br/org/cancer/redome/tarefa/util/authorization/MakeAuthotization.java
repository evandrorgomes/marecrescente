package br.org.cancer.redome.tarefa.util.authorization;

public class MakeAuthotization {
	
	private IAuthorization authorization;
	
	public MakeAuthotization() {
	}
	
	public IAuthorizationForUser paraUsuario(String usuario) {
		authorization = new MakeAuthorizationForUser(usuario);
		return (IAuthorizationForUser) authorization;
	}
	
	public IAuthorizationForResource paraClient(String clientId) {
		authorization = new MakeAuthorizationForResource(clientId);
		return (IAuthorizationForResource) authorization;
	}
	
	public String getAccessTokenJson() {
		return authorization.getAccessTokenJson();
	}
	
	public String getJson() {
		return authorization.getJson();
	}		
	
	public void clear() {
		authorization.clear();
		authorization = null;
	}

}
