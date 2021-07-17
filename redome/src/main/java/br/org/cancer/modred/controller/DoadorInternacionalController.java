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
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IDoadorInternacionalService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Registra as chamadas REST envolvendo a entidade DoadorInternacional.
 * 
 * @author Bruno Sousa.
 *
 */
@RestController
@RequestMapping(value = "/api/doadoresinternacionais", produces = "application/json;charset=UTF-8")
public class DoadorInternacionalController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IDoadorInternacionalService doadorInternacionalService;
	
	/**
	 * Cadastra um doador internacional associado ao paciente (busca) informado.
	 * 
	 * @param doadorInternacionalDto dto de doador internacional.
	 * @return mensagem de sucesso, caso ocorra.
	 */
	@PreAuthorize("hasPermission('CADASTRAR_DOADOR_INTERNACIONAL')")
	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<String> salvarDoadorInternacional(
			@RequestPart(name="doadorCordaoInternacional", required = true)  DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO,
			@RequestPart(name="pedido", required = false) PedidoDto pedido) {
		
		doadorInternacionalService.salvarDoadorInternacionalComPedidoExameSeSolicitado(doadorCordaoInternacionalDTO, pedido);
		return ResponseEntity.ok().body(AppUtil.getMensagem(messageSource, "doador.internacional.incluido.sucesso"));
	}
	
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_DOADOR_INTERNACIONAL + "') ")
	@PutMapping(value = "{id}")
	public ResponseEntity<CampoMensagem> atualiarDoadorInternacional(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO) throws Exception {
		doadorInternacionalService.atualizar(id, doadorCordaoInternacionalDTO);
		CampoMensagem campoMensagem = new CampoMensagem(AppUtil.getMensagem(messageSource, "doador.internacional.atualizado"));
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
		
		doadorInternacionalService.atualizarOrigemPagamento(registroDto);
		return ResponseEntity.ok().body(AppUtil.getMensagem(messageSource, "doador.internacional.atualizado"));
	}
}
