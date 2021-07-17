package br.org.cancer.modred.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.controller.page.JsonPage;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para metodos de serviços envolvendo a entidade Laboratorio.
 * 
 * @author bruno.sousa
 *
 */
public interface ILaboratorioService extends IService<Laboratorio, Long>{

	/**
	 * Método para recuperar o conjuto de laboratorios.
	 * 
	 * @param pageRequest - informações sobre paginação do conjunto que será retornado (informação opcional)
	 * 
	 * @return Page<CentroTransplante> - um conjunto de centros de transplante conforme os parâmetros informados no acionamento
	 * deste método.
	 */
	JsonPage listarLaboratorios(PageRequest pageRequest);
		
	
	/**
	 * Método para recuperar o conjuto de laboratórios que fazem ct.
	 * 
	 * @param pageRequest - informações sobre paginação do conjunto que será retornado (informação opcional)
	 * 
	 * @return Page<Laboratorio> - um conjunto de laboratórios de CT conforme os parâmetros informados no acionamento
	 * deste método.
	 */
	Page<Laboratorio> listarLaboratoriosCT(String nome, String uf, PageRequest pageRequest);

	
	/**
	 * Método para recuperar o conjuto de laboratórios que fazem ct e suas quantidades atuais.
	 * 
	 * @return Page<Laboratorio> - um conjunto de laboratórios de CT conforme os parâmetros informados no acionamento
	 * deste método.
	 */
	List<Laboratorio> listarLaboratoriosCTExame();

	/**
	 * Método para enviar e-mail para o laboratório. 
	 * 
	 * @param id - identificação do laboratório
	 * @param idMatch identificação do match
	 * @param destinatarios - lista de emails separados por ponto e virgula
	 * @param texto conteúdo do email.
	 */
	void enviarEmailExameDivergente(Long id, Long idMatch, String destinatarios, String texto);
	
	/**
	 * Atualiza a lista de usuários vinculados ao laboratório. 
	 * 
	 * @param id do laboratório selecionado.
	 * @param usuariosLaboratorio lista de usuário com perfil de laboratório.
	 */
	void atualizarUsuarios(Long id, List<Usuario> usuariosLaboratorio);
	
}
