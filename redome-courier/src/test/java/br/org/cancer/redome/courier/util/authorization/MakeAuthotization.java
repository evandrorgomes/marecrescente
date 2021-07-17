package br.org.cancer.redome.courier.util.authorization;

public class MakeAuthotization {
	
	private IAuthorization authorization;
	
	public MakeAuthotization() {
	}
	
	public IAuthorizationForUser paraUsuario(Long id, String usuario) {
		authorization = new MakeAuthorizationForUser(id, usuario);
		return (IAuthorizationForUser) authorization;
	}
	
	public IAuthorizationForResource paraClient(String clientId) {
		authorization = new MakeAuthorizationForResource(clientId);
		return (IAuthorizationForResource) authorization;
	}
	
	public String getJson() {
		return authorization.getJson();
	}		
	
	public void clear() {
		authorization.clear();
		authorization = null;
	}

}
