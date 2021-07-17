package br.org.cancer.modred.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.controller.dto.ContatoCentroTransplantadorDTO;
import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.CentroTransplanteUsuario;
import br.org.cancer.modred.model.ContatoTelefonicoCentroTransplante;
import br.org.cancer.modred.model.EmailContatoCentroTransplante;
import br.org.cancer.modred.model.EnderecoContatoCentroTransplante;
import br.org.cancer.modred.model.FuncaoTransplante;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para metodos de serviços envolvendo a entidade CentroTransplante.
 * 
 * @author Pizão
 *
 */
public interface ICentroTransplanteService extends IService<CentroTransplante, Long> {

	/**
	 * Listar os centros de transplantes.
	 * 
	 * @param List<Metodologia>
	 */
	List<CentroTransplante> listarCentroTransplante();

	/**
	 * Retorna um Centro Transplante pelo id.
	 * 
	 * @param id
	 * @return
	 */
	Boolean isCentroTransplanteExistente(Long id);

	/**
	 * Método para salvar um novo centro de transplante.
	 * 
	 * @param centroTransplante
	 *            - o centroTransplante que será criado.
	 */
	RetornoInclusaoDTO salvarCentroTransplante(CentroTransplante centroTransplante);

	/**
	 * Método para criar um novo centro de transplante.
	 * 
	 * A instância da classe centroTransplante precisa estar com os atributos
	 * id, nome, cnes e cnpj preenchidos.
	 * 
	 * @param centroTransplante
	 *            - o centroTransplante que será criado.
	 */
	void atualizarCentroTransplante(CentroTransplante centroTransplante);

	/**
	 * Método para recuperar as informações sobre um determinada
	 * centroTransplante a partir de sua chave de identificação.
	 * 
	 * @param id
	 *            - chave de identificação do centro de transplante.
	 * @return CentroTransplante - a instância da classe CentroTransplante
	 *         identificada pelo id informado como parâmetro do acionamento
	 *         deste método.
	 */
	CentroTransplante obterCentroTransplante(Long id);

	/**
	 * Método para realizar a exclusão lógica de um centroTransplante.
	 * 
	 * A instância da classe centroTransplante precisa estar com o atributo id
	 * preenchido.
	 * 
	 * @param centroTransplante
	 *            - o centroTransplante que será eliminado.
	 */
	void eliminarCentroTransplante(CentroTransplante centroTransplante);

	/**
	 * Método para recuperar o conjuto de centroTransplantes disponíveis.
	 * 
	 * @param querystring
	 *            - conjunto de um ou mais termos que serão utilizados para
	 *            fazer match simples nos elementos textuais do
	 *            centroTransplante.
	 * 
	 * @param pageRequest
	 *            - informações sobre paginação do conjunto que será retornado
	 *            (informação opcional)
	 * @param idFuncaoCentroTransplante
	 *            - função que o centro de transplante assume
	 * @return Page<CentroTransplante> - um conjunto de centros de transplante
	 *         conforme os parâmetros informados no acionamento deste método.
	 */
	Page<CentroTransplante> listarCentroTransplantes(String nome, String cnpj, String cnes, PageRequest pageRequest,
			Long idFuncaoCentroTransplante);

	/**
	 * Método para recuperar o conjuto de centroTransplantes disponíveis.
	 * 
	 * @param idFuncaoCentroTransplante
	 *            - função que o centro de transplante assume
	 * @return Page<CentroTransplante> - um conjunto de centros de transplante
	 *         conforme os parâmetros informados no acionamento deste método.
	 */
	Page<CentroTransplante> listarCentroTransplantes(Long idFuncaoCentroTransplante);

	/**
	 * Lista os centros de transplante por função.
	 * 
	 * @param funcaoCentroTransplanteId
	 *            ID do centro de transplante.
	 * @return lista de DTOs que centralizam centros de transplante, seu
	 *         endereço e telefone de contato.
	 */
	List<ContatoCentroTransplantadorDTO> listarCentroTransplantesPorFuncao(Long funcaoCentroTransplanteId);

	/**
	 * Lista todos os usuários associados a determinado centro de transplante.
	 * 
	 * @param centroTransplanteId
	 *            identificado do centro.
	 * @return lista de usuários associados ao centro informado.
	 */
	List<Usuario> listarUsuariosPorCentro(Long centroTransplanteId);

	
	/**
	 * Lista os tipos de funções que um centro pode possuir.
	 * 
	 * @return Lista de Funções.
	 */
	List<FuncaoTransplante> listarFuncoes();

	/**
	 * Método para atualizar dados basicos dos centros.
	 * 
	 * @param centroTransplante
	 */
	void atualizarDadosBasicos(Long id, CentroTransplante centroTransplante);

	/**
	 * Método para atualizar o laboratório de preferencia de um centro. 
	 * 
	 * @param id - id do centro a ser atualizado
	 * @param laboratorio - laboratório que será o novo laboratório preferencial
	 */
	void atualizarLaboratorioPreferencia(Long id, Laboratorio laboratorio);
	
	
	/**
	 * Método para atualizar os médicos responsáveis por um centro. 
	 * 
	 * @param id - id do centro a ser atualizado
	 * @param medicos - listagem de médicos
	 */
	void atualizarMedicos(Long id, List<CentroTransplanteUsuario> medicos);

	/**
	 * Método que remove o laboratório de preferencia do centro.
	 * 
	 * @param id - id do centro que será atualizado.
	 */
	void removerLaboratorioPreferencia(Long id);

	/**
	 * Método para adicionar endereço ao centro de transplante.
	 * 
	 * @param id - di do centro de transplante
	 * @param enderecoContato -- Endereço à ser adicionado 
	 * @return dto com o endereço salvo
	 */
	RetornoInclusaoDTO adicionarEnderecoContato(Long id, EnderecoContatoCentroTransplante enderecoContato);

	/**
	 * Método para adicionar telefone ao centro de transplante.
	 * 
	 * @param id - id do centro de transplante
	 * @param contato - Telefone de contato a ser adicionado
	 * @return dto com o telefone salvo
	 */
	RetornoInclusaoDTO adicionarContatoTelefonico(Long id, ContatoTelefonicoCentroTransplante contato);

	/**
	 * Método para obter todos os centros do usuário.
	 * 
	 * @param usuario - usuario ao qual deseja saber os centros.
	 * @return lista de centros.
	 */
	List<CentroTransplante> findByUsuarios(Usuario usuario);

	/**
	 * Método para adicionar novo email para o centro de transplante.
	 * @param id - identificador do centro de transplante
	 * @param emailCentroTransplante - Email à ser cadastrado.
	 * @return dto com o email salvo.
	 */
	RetornoInclusaoDTO adicionarEmail(Long id, EmailContatoCentroTransplante emailCentroTransplante);

	/**
	 * Método para obter o endereço de entrega do centro de trasnplante.
	 * 
	 * @param id Identificador do centro de transplante.
	 * @return Endereço de entrega do centro se existir, se não retorno null.
	 */
	EnderecoContatoCentroTransplante obterEnderecoEntrega(Long id); 

	/**
	 * Método para obter o endereço de workup do centro de trasnplante.
	 * 
	 * @param id Identificador do centro de transplante.
	 * @return Endereço de entrega do centro se existir, se não retorno null.
	 */
	EnderecoContatoCentroTransplante obterEnderecoWorkup(Long id);
	
	
	/**
	 * Método para obter o endereço de retirada do centro de trasnplante.
	 * 
	 * @param id Identificador do centro de transplante.
	 * @return Endereço de retirada do centro se existir, se não retorno null.
	 */
	EnderecoContatoCentroTransplante obterEnderecoRetirada(Long id);

}
