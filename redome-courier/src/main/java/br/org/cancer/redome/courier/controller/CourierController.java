package br.org.cancer.redome.courier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.redome.courier.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.redome.courier.model.ContatoTelefonicoCourier;
import br.org.cancer.redome.courier.model.Courier;
import br.org.cancer.redome.courier.model.EmailContatoCourier;
import br.org.cancer.redome.courier.model.domain.Recursos;
import br.org.cancer.redome.courier.service.ICourierService;
import br.org.cancer.redome.courier.util.AppUtil;
import br.org.cancer.redome.courier.util.CampoMensagem;

/**
 * Classe de REST controller para status de doador.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class CourierController {

	@Autowired
	private ICourierService courierService;
	
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * Método para listar courier's da transportadora logada.
	 * 
	 * @return ResponseEntity<List<Courier>> lista de courier.
	 */
	@GetMapping(value = "/couriers")
	public ResponseEntity<List<Courier>> listarCourier() {
		return ResponseEntity.ok(courierService.listarCourierPorTransportadoraLogada());
	}
	
	

	/**
	 * Método para listar courier's da transportadora logada.
	 * 
	 * @return ResponseEntity<List<Courier>> lista de courier.
	 */
	@PreAuthorize("hasPermission('" + Recursos.CONSULTAR_COURIER + "')")
	@GetMapping(value="/courier/{id}")
	public ResponseEntity<Courier> obterCourier(@PathVariable(required = false) Long id) {
		return ResponseEntity.ok(courierService.findById(id));
	}
	
	
	/**
	 * Método para listar courier's da transportadora logada.
	 * 
	 * @param pagina pagina referida.
	 * @param quantidadeRegistros quantos registros à serem exibidos.
	 * @param nome nome do courier (opcional).
	 * @param cpf  cpf do courier (opcional).
	 * @return ResponseEntity<JsonPage> lista de courier.
	 */
	@PreAuthorize("hasPermission('" + Recursos.CONSULTAR_COURIER + "')")
	@GetMapping(value="/couriers/paginado")
	public ResponseEntity<Page<Courier>> listarCourierPorTransportadoraLogadaEPor(
			@RequestParam Integer pagina,
			@RequestParam Integer quantidadeRegistros,
			@RequestParam(required=false) String nome,
			@RequestParam(required=false) String cpf) {
		if (pagina == null) {
			pagina = new Integer(0);
		}
		PageRequest pageRequest = (quantidadeRegistros != null && quantidadeRegistros
				.intValue() > 0) ? PageRequest.of(pagina, quantidadeRegistros)
				: null;

		return ResponseEntity.ok(
						courierService.listarCouriesAtivosPorTransportadoraEPor(
								pageRequest, nome, cpf, null));
	}
	
	/**
	 * Salvar courier.
	 * @param courier item a ser salvo.
	 * @return ResponseEntity com mensagem de retorno sobre a gravação de courier.
	 */
	@PreAuthorize("hasPermission('" + Recursos.SALVAR_COURIER + "')")
	@PostMapping(value="/couriers")
    public ResponseEntity<CampoMensagem> salvarCourier(@RequestBody Courier courier) {
		courierService.inserirCourier(courier);
		return ResponseEntity.ok(new CampoMensagem(AppUtil.getMensagem(messageSource, "courier.mensagem.sucesso.gravacao")));
    }
	
	/**
	 * Atualizar courier.
	 * @return ResponseEntity com mensagem de retorno sobre a gravação de courier.
	 */
	@PreAuthorize("hasPermission('" + Recursos.SALVAR_COURIER + "')")
	@PutMapping(value = "/courier/{id}")
    public ResponseEntity<CampoMensagem> atualizarCourier(@PathVariable(name = "id", required = true) Long id
    		, @RequestBody  Courier courier) {
		courierService.atualizarCourier(courier, id);
        return ResponseEntity.ok(new CampoMensagem(AppUtil.getMensagem(messageSource, "courier.mensagem.sucesso.gravacao")));
    }
	
	

	/**
	 * Exclusão lógica de courier.
	 * @return ResponseEntity com mensagem de retorno sobre a exclusão de courier.
	 */
	@PreAuthorize("hasPermission('" + Recursos.INATIVAR_COURIER + "')")
	@DeleteMapping(value = "/courier/{id}")
    public ResponseEntity<CampoMensagem> inativar(@PathVariable(name = "id", required = true) Long id) {
		courierService.inativarCourier(id);
        return ResponseEntity.ok(new CampoMensagem(AppUtil.getMensagem(messageSource, "courier.mensagem.sucesso.inativacao")));
    }
	
	
	
	
	/**
	 * Inserir email de courier.
	 * 
	 * @param id identificacao da courier.
	 * @param enderecoContato email a ser atualizado.
	 * @return ResponseEntity com mensagem de retorno sobre a gravação de courier.
	 */
	@PreAuthorize("hasPermission('" + Recursos.SALVAR_COURIER + "')")
	@PostMapping(value = "/courier/{id}/inseriremail")
    public ResponseEntity<RetornoInclusaoDTO> inserirEmailCourier(@PathVariable(name = "id", required = true) Long id
    		, @RequestBody EmailContatoCourier email) {
        return ResponseEntity.ok(courierService.adicionarEmail(id, email));
    }


	
	/**
	 * adiciona novo contato para o centro de transplante.
	 * 
	 * @param id identificador do courier.
	 * @param contato novo contato a ser salvo.
	 * 
	 * @return mensagem de confirmação.
	 */
	@PostMapping(value = "/courier/{id}/contatostelefonicos")
	@PreAuthorize("hasPermission('" + Recursos.SALVAR_COURIER + "')")
	public ResponseEntity<RetornoInclusaoDTO> adicionarContatoTelefonico(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) ContatoTelefonicoCourier contato) {
		return ResponseEntity.ok(courierService.adicionarContatoTelefonico(id, contato));
	}
}
