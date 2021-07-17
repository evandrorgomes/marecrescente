
package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.ContatoCentroTransplantadorDTO;
import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.view.CentroTransplanteView;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.CentroTransplanteUsuario;
import br.org.cancer.modred.model.ContatoTelefonicoCentroTransplante;
import br.org.cancer.modred.model.EmailContatoCentroTransplante;
import br.org.cancer.modred.model.EnderecoContatoCentroTransplante;
import br.org.cancer.modred.model.FuncaoTransplante;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para as chamadas envolvendo a entidade CentroTransplante.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class CentroTransplanteController {

    @Autowired
    private ICentroTransplanteService centroTransplanteService;
    
    @Autowired
    private MessageSource messageSource;

     
    
   /**
    * Método para criar um novo centro transplante.
    * 
    * A instância da classe centro transplante precisa estar com os atributos nome, cnpj e cnes preenchido.
    * 
    * @param centroTransplante - o centro transplante que será criado.
    * 
    * @return ResponseEntity<CampoMensagem> - mensagem informativa sobre o resultado da operação.
    */
   @RequestMapping(value = "/api/centrosTransplante", method = RequestMethod.POST)
   @PreAuthorize("hasPermission('MANTER_CENTRO_TRANSPLANTE')")
   public ResponseEntity<RetornoInclusaoDTO> criarCentroTransplante(@RequestBody(required = true)CentroTransplante centroTransplante){

       return new ResponseEntity<RetornoInclusaoDTO>(centroTransplanteService.salvarCentroTransplante(centroTransplante), HttpStatus.OK);
   }
   
   /**
    * Método para recuperar as informações sobre um determinado centro transplante a partir de sua chave de identificação.
    * 
    * @param id - chave de identificação do centro transplante.
    * 
    * @return  ResponseEntity<CentroTransplante> - a instância da classe centro transplante identificada 
    * pelo id informado como parâmetro do acionamento deste método.
    */   
   @RequestMapping(value = "/api/centrosTransplante/{id}", method = RequestMethod.GET)
   @PreAuthorize("hasPermission('" + Recurso.MANTER_CENTRO_TRANSPLANTE + "')"
   			+ " || hasPermission('" + Recurso.EFETUAR_LOGISTICA + "') "
   			+ " || hasPermission('" + Recurso.EFETUAR_LOGISTICA_INTERNACIONAL + "') "
   			+ " || hasPermission('" + Recurso.CONSULTAR_CENTRO_TRANSPLANTE + "') ")
   @JsonView(CentroTransplanteView.Detalhe.class)
   public ResponseEntity<CentroTransplante> obterCentroTransplante(@PathVariable(name = "id", required = true) Long id){
               
           return new ResponseEntity<CentroTransplante>(centroTransplanteService.obterCentroTransplante(id), HttpStatus.OK);
   }
       
   /**
    * Método para realizar a exclusão lógica de um centro transplante.
    * 
    * A instância da classe centro transplante precisa estar com o atributo id preenchido.
    * 
    * @param centroTransplanteId - o centro transplante que será eliminado.
    * 
    * @return ResponseEntity<CampoMensagem> - mensagem informativa sobre o resultado da operação.
    */   
   @RequestMapping(value = "/api/centrosTransplante/{id}", method = RequestMethod.DELETE)
   public ResponseEntity<CampoMensagem> eliminarCentroTransplante(
                                                          @PathVariable(name = "id", required = true) Long centroTransplanteId){
       
                     
           CentroTransplante centroTransplante = new CentroTransplante();
           centroTransplante.setId(centroTransplanteId);
           centroTransplanteService.eliminarCentroTransplante(centroTransplante);

           final CampoMensagem mensagem = new CampoMensagem("", 
                                           AppUtil.getMensagem(messageSource, "centroTransplante.eliminado.sucesso", 
                                                                                           centroTransplante.getId()));

           return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
   }
   
   
   /**
    * Método para recuperar o conjuto de centros de transplante disponíveis.
    * 
    * @param nome - nome do centro de transplante disponíveis.
    * @param cnpj - cnpj do centro de transplante disponíveis.
    * @param cnes - cnes do centro de transplante disponíveis.  
    * 
    * @param pagina - informações sobre paginação do conjunto que será retornado (informação opcional)
    * 
    * @param quantidadeRegistros - informações sobre quantidade de registros retornados na paginação (informação opcional)
    * @param idFuncaoCentroTransplante - funcao do centro de transplate
    * 
    * @return ResponseEntity<Page<CentroTransplante>> - um conjunto de perfis conforme os parâmetros 
    * informados no acionamento deste método.
    */
   @RequestMapping(value = "/public/centrosTransplante", method = RequestMethod.GET)
   public ResponseEntity<Page<CentroTransplante>>  listarCentrosTransplante(
		   					 @RequestParam(required = false) String nome,
		   					 @RequestParam(required = false) String cnpj,
		   					 @RequestParam(required = false) String cnes,
                             @RequestParam(required = false) Integer pagina,
                             @RequestParam(required = false) Integer quantidadeRegistros,
                             @RequestParam(required = false) Long idFuncaoCentroTransplante){
               
           if(pagina == null){ 
               pagina = new Integer(0);
           }
           
           PageRequest pageRequest = (quantidadeRegistros !=null && quantidadeRegistros.intValue() > 0 )?
        		   PageRequest.of(pagina, quantidadeRegistros) : null;
           
		return new ResponseEntity<Page<CentroTransplante>>(
				centroTransplanteService.listarCentroTransplantes(nome, cnpj, cnes, pageRequest, idFuncaoCentroTransplante),
				HttpStatus.OK);
   }
      
   @RequestMapping(value="/api/centrosTransplante/funcao/{id}", method = RequestMethod.GET)
   public ResponseEntity<List<ContatoCentroTransplantadorDTO>> listarCentrosTransplante(@PathVariable("id") Long idFuncaoCT){
		return new ResponseEntity<List<ContatoCentroTransplantadorDTO>>(
				centroTransplanteService.listarCentroTransplantesPorFuncao(idFuncaoCT), HttpStatus.OK);
   }
   
   @RequestMapping(value="/api/centrosTransplante/funcoes", method = RequestMethod.GET)
   public ResponseEntity<List<FuncaoTransplante>> listarFuncoes(){
		return new ResponseEntity<List<FuncaoTransplante>>(
				centroTransplanteService.listarFuncoes(), HttpStatus.OK);
   }
   
   /**
    * Método para atualizar as informações do centro transplante.
    * 
    * A instância da classe centro transplante precisa estar com os atributos id, nome, cnpj e cnes preenchidos.
    * 
    * @param centroTransplanteId - chave de identificação do centro transplante.
    * @param centroTransplante - o centro transplante que será atualizado.
    * 
    * @return ResponseEntity<CampoMensagem> - mensagem informativa sobre o resultado da operação.
    */   
   @RequestMapping(value = "/api/centrosTransplante/{id}/dadosbasicos", method = RequestMethod.PUT)
   @PreAuthorize("hasPermission('MANTER_CENTRO_TRANSPLANTE')")
   public ResponseEntity<CampoMensagem> atualizarDadosBasicos(
                @PathVariable(name = "id", required = true) Long centroTransplanteId,
                @RequestBody(required = true) CentroTransplante centroTransplante) {
	   
	   
       centroTransplanteService.atualizarDadosBasicos(centroTransplanteId, centroTransplante);

       final CampoMensagem mensagem = new CampoMensagem("", 
                           AppUtil.getMensagem(messageSource, "centroTransplante.dadosbasicos.atualizado.sucesso"));

       return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
   
   }
   
   /**
    * Método para atualizar o laboratorio de preferencia do centro.
    * 
    * 
    * @param centroTransplanteId - chave de identificação do centro transplante.
    * @param laboratorio - o laboratório que será o novo laboratório preferencial.
    * 
    * @return ResponseEntity<CampoMensagem> - mensagem informativa sobre o resultado da operação.
    */
   @RequestMapping(value = "/api/centrosTransplante/{id}/laboratoriopreferencia", method = RequestMethod.PUT)
   @PreAuthorize("hasPermission('MANTER_CENTRO_TRANSPLANTE')")
   public ResponseEntity<CampoMensagem> atualizarLaboratorioPreferencia(
                @PathVariable(name = "id", required = true) Long centroTransplanteId,
                @RequestBody(required = true) Laboratorio laboratorio) {
	   
	   
       centroTransplanteService.atualizarLaboratorioPreferencia(centroTransplanteId, laboratorio);

       final CampoMensagem mensagem = new CampoMensagem("", 
                           AppUtil.getMensagem(messageSource, "centroTransplante.laboratoriopreferencia.atualizado.sucesso"));

       return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
   
   }
   
   /**
    * Método para atualizar os médidcos responsáveis pelo centro de transplante.
    * 
    * 
    * @param centroTransplanteId - chave de identificação do centro transplante.
    * @param medicos - médicos que serão atualizados.
    * 
    * @return ResponseEntity<CampoMensagem> - mensagem informativa sobre o resultado da operação.
    */
   @RequestMapping(value = "/api/centrosTransplante/{id}/medico", method = RequestMethod.PUT)
   @PreAuthorize("hasPermission('MANTER_CENTRO_TRANSPLANTE')")
   public ResponseEntity<CampoMensagem> atualizarLaboratorioPreferencia(
                @PathVariable(name = "id", required = true) Long centroTransplanteId,
                @RequestBody(required = true) List<CentroTransplanteUsuario> medicos) {
	   
	   
       centroTransplanteService.atualizarMedicos(centroTransplanteId, medicos);

       final CampoMensagem mensagem = new CampoMensagem("", 
                           AppUtil.getMensagem(messageSource, "centroTransplante.medicos.atualizado.sucesso"));

       return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
   
   }
   
   /**
    * Método para remover o laboratorio de preferencia do centro.
    * 
    * 
    * @param centroTransplanteId - chave de identificação do centro transplante.
    * 
    * @return ResponseEntity<CampoMensagem> - mensagem informativa sobre o resultado da operação.
    */
   @RequestMapping(value = "/api/centrosTransplante/{id}/laboratoriopreferencia", method = RequestMethod.DELETE)
   @PreAuthorize("hasPermission('MANTER_CENTRO_TRANSPLANTE')")
   public ResponseEntity<CampoMensagem> removerLaboratorioPreferencia(
                @PathVariable(name = "id", required = true) Long centroTransplanteId) {
	   
       centroTransplanteService.removerLaboratorioPreferencia(centroTransplanteId);

       final CampoMensagem mensagem = new CampoMensagem("", 
                           AppUtil.getMensagem(messageSource, "centroTransplante.laboratoriopreferencia.atualizado.sucesso"));

       return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
   
   }
   
	/**
	 * Adiciona um novo endereco para o doador.
	 * 
	 * @param id - identificador de doador.
	 * @param enderecoContato - endereço de contato do doador.
	 * @return mensagem de sucesso.
	 */
	@RequestMapping(value = "/api/centrosTransplante/{id}/enderecocontato", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('MANTER_CENTRO_TRANSPLANTE')")
	public ResponseEntity<RetornoInclusaoDTO> adicionarEnderecoContato(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) EnderecoContatoCentroTransplante enderecoContato) {
		return new ResponseEntity<RetornoInclusaoDTO>(centroTransplanteService.adicionarEnderecoContato(id, enderecoContato),
				HttpStatus.OK);		
	}
	
	/**
	 * adiciona novo contato para o centro de transplante.
	 * 
	 * @param id identificador do centro de transplante
	 * @param contato novo contato a ser salvo.
	 * 
	 * @return mensagem de confirmação.
	 */
	@RequestMapping(value = "/api/centrosTransplante/{id}/contatostelefonicos", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('MANTER_CENTRO_TRANSPLANTE')")
	public ResponseEntity<RetornoInclusaoDTO> adicionarContatoTelefonico(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) ContatoTelefonicoCentroTransplante contato) {

		return new ResponseEntity<RetornoInclusaoDTO>(centroTransplanteService.adicionarContatoTelefonico(id, contato),
				HttpStatus.OK);
	}
	
	/**
	 * adiciona novo email de contato para o centro de transplante.
	 * 
	 * @param id ID do centro de transplante.
	 * @param emailCentroTransplante entidade contendo as informações do novo e-mail.
	 * @return DTO indicando que a operação foi realizada.
	 */
	@PostMapping(value = "/api/centrosTransplante/{id}/emails")
	@PreAuthorize("hasPermission('MANTER_CENTRO_TRANSPLANTE')")
	public ResponseEntity<RetornoInclusaoDTO> adicionarNovoEmail(
			@PathVariable(name = "id", required = true) Long id,
			@RequestBody EmailContatoCentroTransplante emailCentroTransplante) {
		
		final RetornoInclusaoDTO retorno = centroTransplanteService.adicionarEmail(id, emailCentroTransplante);
		return ResponseEntity.ok(retorno);
	}
	
	@GetMapping(value= "/api/centrosTransplante/{id}/enderecoentrega")
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_PRESCRICAO + "') || "
			+ "hasPermission('" + Recurso.AGENDAR_TRANSPORTE_MATERIAL + "') ")
	public ResponseEntity<EnderecoContatoCentroTransplante> obterEnderecoDeEntrega(
			@PathVariable(name = "id", required = true) Long id) {
		
		return ResponseEntity.ok(centroTransplanteService.obterEnderecoEntrega(id));
	}
	
	@GetMapping(value= "/api/centrosTransplante/{id}/enderecoworkup")
	@PreAuthorize("hasPermission('" + Recurso.INFORMAR_PLANO_WORKUP_NACIONAL + "')")
	public ResponseEntity<EnderecoContatoCentroTransplante> obterEnderecoDeWorkup(
			@PathVariable(name = "id", required = true) Long id) {
		
		return ResponseEntity.ok(centroTransplanteService.obterEnderecoWorkup(id));
		
	}
	
	@GetMapping(value= "/api/centrotransplante/{id}/enderecoretirada")
	@PreAuthorize("hasPermission('" + Recurso.AGENDAR_TRANSPORTE_MATERIAL + "')")
	public ResponseEntity<EnderecoContatoCentroTransplante> obterEnderecoDeRetirada(
			@PathVariable(name = "id", required = true) Long id) {
		
		return ResponseEntity.ok(centroTransplanteService.obterEnderecoRetirada(id));
	}
	
   
}
