package br.org.cancer.modred.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.gateway.sms.StatusSms;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IPedidoContatoSmsService;
import br.org.cancer.modred.service.ISmsEnviadoService;
import br.org.cancer.modred.vo.SmsVo;

/**
 * Controlador para a entidade de busca.
 * 
 * @author ergomes
 *
 */
@RestController
@RequestMapping(value = "/api/pedidoscontatosms", produces = "application/json;charset=UTF-8")
public class PedidoContatoSmsController {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IPedidoContatoSmsService pedidoContatoSmsService;
	
	@Autowired
	private ISmsEnviadoService smsEnviadoService;

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "{id}/finalizar", consumes = "application/json")
	//@PreAuthorize("hasPermission('INTEGRACAO_JOB')")	
	public ResponseEntity finalizarPedidoContatoSms(@PathVariable(name="id", required=true) Long id) {
		pedidoContatoSmsService.finalizarPedidoContatoSmsInativandoDoador(id);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Método para listar os sms enviados.
	 * 
	 * @param dmr - DMR do doador.
	 * @param dataInicial  - Data inicial
	 * @param dataFinal - Data final 
	 * @param status status do doador
	 * @param pagina numero de pagina
	 * @param quantidadeRegistros quantidade de registro
	 * @return Lista com os sms.
	 */
	@GetMapping(value = "")
	@PreAuthorize("hasPermission('" + Recurso.CONSULTAR_CONTATO_SMS + "')")
	public ResponseEntity<Page<SmsVo>> listarSmsEnviados(
			@RequestParam(required=false) Long dmr, 
			@DateTimeFormat(pattern = "ddMMyyyy") @RequestParam(required=false) LocalDate dataInicial, 
			@DateTimeFormat(pattern = "ddMMyyyy") @RequestParam(required=false) LocalDate dataFinal, 
			@RequestParam(required=false) Integer status, 
			@RequestParam(name = "pagina", required = true) int pagina,
			@RequestParam(name = "quantidadeRegistros", required = true) int quantidadeRegistros) {
		
		StatusSms statusSms = null;
		if (status != null) {
			statusSms = StatusSms.get(status);
		}
		
		return ResponseEntity.ok(smsEnviadoService.listarsmsEnviados(dmr, dataInicial, dataFinal, statusSms, 
				new PageRequest(pagina, quantidadeRegistros)));
				
	}
	
	/**
	 * 
	 * @param idSmsEnviado - id do envio de sms
	 * @param status - status do sms
	 * @return messagem de sucesso
	 */
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "{id}/atualizarsmsenviados")
	//@PreAuthorize("hasPermission('" + Recurso.ATUALIZAR_STATUS_SMS_ENVIADO + "')")
	public ResponseEntity atualizaStatusSmsEnviado(@PathVariable(name="id", required=true) Long id) {
		
		pedidoContatoSmsService.atualizarStatusSmsEnviados(id);
		
		return ResponseEntity.ok().build();
		
	}
	
	
}