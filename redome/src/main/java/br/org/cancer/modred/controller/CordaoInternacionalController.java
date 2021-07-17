package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.dto.PedidoDto;
import br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO;
import br.org.cancer.modred.controller.dto.doador.OrigemPagamentoDoadorDTO;
import br.org.cancer.modred.service.ICordaoInternacionalService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Registra as chamadas REST envolvendo a entidade DoadorInternacional.
 * 
 * @author Bruno Sousa.
 *
 */
@RestController
@RequestMapping(value = "/api/cordoesinternacionais", produces = "application/json;charset=UTF-8")
public class CordaoInternacionalController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ICordaoInternacionalService cordaoInternacionalService;
	
	/**
	 * Cadastra um cordao internacional associado ao paciente (busca) informado.
	 * 
	 * @param doadorCordaoInternacionalDTO dto de doador internacional.
	 * @return mensagem de sucesso, caso ocorra.
	 */
	@PreAuthorize("hasPermission('CADASTRAR_DOADOR_INTERNACIONAL')")
	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<String> salvarDoadorInternacional(
			@RequestPart(name="doadorCordaoInternacional", required = true)  DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO,
			@RequestPart(name="pedido", required = false) PedidoDto pedido) {
				
		cordaoInternacionalService.salvarCordaoInternacionalComPedidoExameSeSolicitado(doadorCordaoInternacionalDTO, pedido);
		return ResponseEntity.ok().body(AppUtil.getMensagem(messageSource, "cordao.internacional.incluido.sucesso"));
	}
	
	@PreAuthorize("hasPermission('CADASTRAR_DOADOR_INTERNACIONAL')")
	@PutMapping(value = "{id}")
	public ResponseEntity<CampoMensagem> atualiarCordaoInternacional(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO) throws Exception {
		cordaoInternacionalService.atualizar(id, doadorCordaoInternacionalDTO);
		CampoMensagem campoMensagem = new CampoMensagem(AppUtil.getMensagem(messageSource, "cordao.internacional.atualizado"));
		return ResponseEntity.ok(campoMensagem);
	}


	/**
	 * Atualiza os dados de pagamento do doador internacional.
	 * 
	 * @param id identificador do doador.
	 * @param doador informações dos dados pessoais do doador a serem atualizadas.
	 * 
	 * @return mensagem de confirmação.
	 */
	@RequestMapping(value = "dadospagamento/internacional", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('ALTERAR_DOADOR_INTERNACIONAL')")
	public ResponseEntity<String> atualiarDadosPagamentoDeDoadorInternacional(
			@RequestBody(required = true) OrigemPagamentoDoadorDTO registroDto) {
		
		cordaoInternacionalService.atualizarOrigemPagamento(registroDto);
		return ResponseEntity.ok().body(AppUtil.getMensagem(messageSource, "doador.internacional.atualizado"));
	}

}
