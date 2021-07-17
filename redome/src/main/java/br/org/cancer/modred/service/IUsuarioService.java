
package br.org.cancer.modred.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;

import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.vo.AlterarSenhaVo;

/**
 * Serviço para operações referentes a classe Usuário.
 * 
 * @author Cintia Oliveira
 *
 */
public interface IUsuarioService {

    /**
     * Retorna o CustomUser presente no authentication passado como parâmetro.
     * 
     * @param authentication
     *            representação de authenticação oriunda do request
     * @return custom user presente em authentication
     */
    CustomUser obterCustomUserLogado(Authentication authentication);

    /**
     * Retorna o usuário logado na aplicação.
     * 
     * @return usuário logado
     */
    Usuario obterUsuarioLogado();

    /**
     * Retorna o usuário cujo username foi passado como parâmetro.
     * 
     * @param username usuário a ser pesquisado.
     * @return usuário com o username informado
     */
    Usuario obterUsuarioPorUsername(String username);
    
    /**
     * Retorna se o usuário possui o perfil.
     * 
     * @param perfil
     * @return
     */
    Boolean usuarioLogadoPossuiPerfil(Perfis perfil);
    
    /**
     * Retorna se o usuário possui o perfil.
     * 
     * @param usuario usuário a ter o perfil verificado.
     * @param perfil perfil a ser localizado.
     * @return TRUE se o usuário possuir o perfil.
     */
    boolean usuarioPossuiPerfil(Usuario usuarioLogado, Perfis perfil);
    
    /**
     * Retorna o ID do usuário logado.
     * 
     * @return ID do usuário logado no sistema.
     */
    Long obterUsuarioLogadoId();
    
    /**
     * Método para recuperar as informações sobre um determinada usuario a partir de sua chave de identificação.
     * 
     * @param id - chave de identificação do usuario de acesso.
     * @return Usuario - a instância da classe usuario identificada pelo id informado como parâmetro do acionamento deste método.
     */
    Usuario obterUsuario(Long id);
    
    /**
     * Verifica se já existe usuário com o mesmo login no sistema.
     * 
     * @param usuario usuário a ser verificado.
     * @return TRUE, caso já esteja sendo utilizado o login.
     */
    boolean verificarSeLoginDuplicado(Usuario usuario);
    
    /**
     * Lista paginada de usuários, atendendo (caso seja informado) os filtros
     * passados por parâmetro.
     * 
     * @param pageRequest paginação do resultado.
     * @param nome filtro por parte do nome do usuário.
     * @param email filtro por parte do e-mail do usuário.
     * @param login filtro por parte do login do usuário.
     * @return lista paginada de usuários.
     */
    Page<Usuario> listarUsuariosAtivos(PageRequest pageRequest, String nome, String email, String login);
    
    /**
     * Lista os usuários associados ao centro de transplante e com perfil especificados por parâmetro.
     * 
     * @param idCentroTransplante - id do centro de transplante.
     * @param idPerfil - id do perfil.
     * 
     * @return lista de usuários de mesmo centro transplante e perfil, conforme parâmetros informados.
     */
    List<Usuario> listarUsuariosPorCentroTransplanteEPerfil(Long idCentroTransplante, Long idPerfil);
    
    /**
     * Retorna uma listagem de usuários com o perfil de ANALISTA_BUSCA.
     * @return listagem de usuários com o perfil de analista de busca.
     */
    List<Usuario> listarAnalistasDeBusca();
    
    /**
     * Grava a relação entre usuário e Centro de Transplante, não para 
     * permissão e sim para organização de tarefas dos usuários do REDOME.
     * 
     * @param id - id do usuario;
     * @param usuario DTO com a relação entre as entidades.
     */
    void salvarUsuarioCentroParaTarefas(Long id, Usuario usuario);
	
	/**
	 * Retorna uma listagem de usuários com o perfil informado.
	 * 
	 * @param perfil perfil dos usuários listados.
	 * @return listagem de usuários com o perfil informado.
	 */
	List<Usuario> listarUsuarios(Perfis perfil);
	
	/**
	 * Cria um novo usuário com as informações passadas e com a senha
	 * temporária.
	 * 
	 * TODO: Configurar cadastro do usuário para solicitar nova senha,
	 * assim que ocorrer o primeiro acesso.
	 * 
	 * @param login login do usuário.
	 * @param nome nome do usuário.
	 * @param email e-mail do usuário.
	 * @param perfis perfis que ele irá possuir.
	 * @return usuário criado.
	 */
	Usuario criarUsuarioSenhaTemporaria(String login, String nome, String email, Perfis... perfis);
	
	/**
	 * Cria um novo usuário.
	 * 
	 * @param usuario usuário a ser criado.
	 * @return usuário salvo.
	 */
	Usuario criarUsuarioSenhaTemporaria(Usuario usuario);
	
	/**
	 * Envia e-mail para um usuário específico.
	 * 
	 * @param idUsuario ID do usuário.
	 */
	void enviarEmail(Long idUsuario);
	
	/**
	 * Envia e-mail para todos os usuários que possuem o perfil informado.
	 * 
	 * @param perfil perfil que deverá receber e-mail.
	 */
	void enviarEmail(Perfis perfil);
	
	
	/**
	 * Envia e-mail para um e-mail específico.
	 * 
	 * @param email e-mail que deverá ser enviado.
	 */
	void enviarEmail(String email, String assunto, String texto);
	
	/**
	 * Verifica se o usuario logado pertence ao centro de transplante informado.
	 * 
	 * @param idCentroTransplante - Identificador do centro de transplante.
	 * @return True se o usuário logado pertencer ao centro de transplante informado.
	 */
	boolean usuarioLogadoPertenceAoCentroTransplante(Long idCentroTransplante);
	
	/**
	 * Atualiza o usuário com as novas informações fornecidas
	 * no parâmetro.
	 * 
	 * @param idUsuario ID usuário com os dados atualizados.
	 * @param login login a ser atualizado.
	 * @param nome nome do usuário a ser atualizado.
	 * @param email email do usuário a ser atualizado.
	 */
	void alterarAcesso(Long idUsuario, String login, String nome, String email);
	
	/**
	 * Atualiza os perfis de acesso do usuário informado.
	 * 
	 * @param idUsuario ID do usuário.
	 * @param perfisDeAcesso lista dos perfis de acesso que o usuário terá.
	 * @param idBancoSangue ID do banco de sangue de cordão associado ao usuário (opcional).
	 * 
	 * @return usuário atualizado.
	 */
	Usuario alterarPerfisAcesso(Long idUsuario, List<Perfil> perfisDeAcesso, Long idBancoSangue);
	
	/**
	 * Inativa o usuário por tempo indeterminado.
	 * @param idUsuario ID do usuário.
	 * @return retornar o usuário que foi inativado.
	 */
	Usuario inativar(Long idUsuario);
	
	
	/**
	 * Método para enviar a senha do usuário por email.
	 * 
	 * @param usuario
	 * @param codigoRelatorio
	 */
	void enviarSenhaPorEmail(Usuario usuario, String codigoRelatorio);

	/**
	 * Método de lembrar senha.
	 * Será gerada uma nova senha o qual precisará ser trocada quando efetuar o próximo login.
	 * 
	 * @param email
	 */
	void lembrarSenha(String email);

	/**
	 * Método para alterar a senha do usuário logado.
	 * 
	 * @param alterarSenhaVo
	 */
	void alterarSenha(AlterarSenhaVo alterarSenhaVo);
	
	
	/**
	 * Obtém usuários com perfil de LABORATORIO que não estão vinculados a nenhum laboratório.
	 * 
	 * @return lista de usuários válidos para serem vinculados a um laboratório.
	 */
	List<Usuario> listarUsuariosLaboratoriosDisponiveis();
	
	
	/**
	 * Lista usuarios por id de perfil.
	 * @param id do perfil ao qual deseja listar os usuários.
	 * @return lista de usuários.
	 */
	List<Usuario> listarUsuarioPorPerfil(Long idPerfil);

	/**
	 * Lista todos os usuários do perfil e de um parceiro especifico.
	 * 
	 * @param idPerfil identificador do perfil
	 * @param parceiro classe que representa o parceiro
	 * @param idParceiro identificador do parceiro
	 * @return Lista de usuários.
	 */
	List<Usuario> listarUsuarioPorPerfilEParceiro(Long idPerfil, Class<?> parceiro, Long idParceiro);

}
