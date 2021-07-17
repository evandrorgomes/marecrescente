
package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.page.JsonPage;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.ILaboratorioService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para as chamadas envolvendo a entidade CentroTransplante.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/laboratorios")
public class LaboratorioController {

    @Autowired
    private ILaboratorioService laboratorioService;
    
    @Autowired
	private MessageSource messageSource;

   /**
    * Método para recuperar o conjuto de laboratórios disponíveis.
    * 
    * @param pagina - informações sobre paginação do conjunto que será retornado (informação opcional)
    * 
    * @param quantidadeRegistros - informações sobre quantidade de registros retornados na paginação (informação opcional)
    * 
    * @return ResponseEntity<Page<Laboratorio>> - um conjunto de laboratorios conforme os parâmetros 
    * informados no acionamento deste método.
    * 
    */
   @RequestMapping(method = RequestMethod.GET)
   public ResponseEntity<JsonPage>  listarLaboratorios(
		   @RequestParam(required = false) Integer pagina,
           @RequestParam(required = false) Integer quantidadeRegistros){
               
           if(pagina == null){ 
               pagina = new Integer(0);
           }
           
           PageRequest pageRequest = (quantidadeRegistros !=null && quantidadeRegistros.intValue() > 0 )?
                                                       new PageRequest(pagina, quantidadeRegistros, new Sort("endereco.uf")):null;
           
			return new ResponseEntity<JsonPage>(
					laboratorioService.listarLaboratorios(pageRequest), HttpStatus.OK);
   }
   
   /**
    * Método para recuperar o conjuto de laboratórios disponíveis que fazem ct.
    * 
    * @return Lista de laboratórios
    */
   @RequestMapping(path="ct/exame", method = RequestMethod.GET)
   @JsonView(LaboratorioView.ListarCT.class)
   public ResponseEntity<List<Laboratorio>>  listarLaboratoriosCt(){
	   return new ResponseEntity<List<Laboratorio>>(laboratorioService.listarLaboratoriosCTExame(), HttpStatus.OK);
   }

   /**
    * Método para recuperar o conjuto de laboratórios disponíveis que fazem ct com paginação.
    * 
    * @param nome - string - nome do laboratório.
    * @param uf - string - uf do endereço contato do laboratório.
    * @param pagina - integer - núumero da página.
    * @param quantidadeRegistros - integer - quantidade de registros
    * @return Lista de laboratórios que fazem CT paginada.
    */
   @RequestMapping(path="ct", method = RequestMethod.GET)
   @JsonView(LaboratorioView.ListarCT.class)
   public ResponseEntity<JsonPage>  listarLaboratoriosCt(
		   @RequestParam(required = false) String nome,
		   @RequestParam(required = false) String uf,
		   @RequestParam(required = false) Integer pagina,
           @RequestParam(required = false) Integer quantidadeRegistros){

		if (pagina == null) {
			pagina = new Integer(0);
		}
		PageRequest pageRequest = (quantidadeRegistros != null && quantidadeRegistros.intValue() > 0)
				? new PageRequest(pagina, quantidadeRegistros) : null;
		
        return new ResponseEntity<JsonPage>(
				new JsonPage(LaboratorioView.ListarCT.class, laboratorioService.listarLaboratoriosCT(nome, uf, pageRequest)),
				HttpStatus.OK);
   }
   
   /**
    * Método para enviar email.
    * 
    * @param id - identificação do laboratório
    * @param idMatch - identificação do match
    * @param destinatarios - emails separados por ponto e virgula 
    * @param texto - mensagem do email.
    * @return mensagem de sucesso
    */
   @RequestMapping(path="{id}/enviaremailexamedivergente", method = RequestMethod.POST, consumes = "multipart/form-data")
   @PreAuthorize("hasPermission('" + Recurso.DESCARTAR_LOCUS_EXAME + "')")
   public ResponseEntity<String> enviarEmailExameDivergente(
		   @PathVariable(name = "id", required = true) Long id,
		   @RequestPart(required = true) Long idMatch,
		   @RequestPart(required = true) String destinatarios,
		   @RequestPart(required = true) String texto) {
	   
	   laboratorioService.enviarEmailExameDivergente(id, idMatch, destinatarios, texto);
       
	   return ResponseEntity.ok().body(AppUtil.getMensagem(messageSource, "email.enviado.sucesso"));
   }
    
	/**
	 * Atualizar Laboratório.
	 * 
	 * @return ResponseEntity com mensagem de retorno sobre a gravação do vinculo do usuário ao laboratório.
	 */
	@PreAuthorize("hasPermission('" + Recurso.SALVAR_LABORATORIO + "')")
	@RequestMapping(value = "{id}/atualizarusuarios", method = RequestMethod.PUT)
   public ResponseEntity<CampoMensagem> atualizarUsuariosLaboratorio(
		   	@PathVariable(name = "id", required = true) Long id,
   		 	@RequestBody List<Usuario> usuariosLaboratorio) {
			laboratorioService.atualizarUsuarios(id, usuariosLaboratorio);
       return ResponseEntity.ok().body(new CampoMensagem(AppUtil.getMensagem(messageSource, "laboratorio.mensagem.sucesso.gravacao.usuarios")));
   }
   
	/**
	 * Obtém laboratório por ID.
	 * 
	 * @return ResponseEntity laboratório obtida.
	 */
	@PreAuthorize("hasPermission('" + Recurso.CONSULTAR_LABORATORIO + "')")
	@RequestMapping(value="{id}", method = RequestMethod.GET)
	@JsonView(LaboratorioView.Detalhe.class)
    public ResponseEntity<Laboratorio> obterLaboratorio(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.ok().body(laboratorioService.findById(id));
    }
	
}
