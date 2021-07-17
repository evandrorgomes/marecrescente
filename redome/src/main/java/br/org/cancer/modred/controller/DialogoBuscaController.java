package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.model.DialogoBusca;
import br.org.cancer.modred.service.impl.DialogoBuscaService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de métodos REST para recebimento de coleta.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/dialogosbusca", produces = "application/json;charset=UTF-8")
public class DialogoBuscaController {
	
	@Autowired
	private DialogoBuscaService dialogoBuscaService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	/**
	 * Método para cadastrar diálogo de busca.
	 * @param dialogo - objeto de diálogo. 
	 * @return ResponseEntity<String> - mensagem de sucesso
	 */
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('DIALOGO_BUSCA')")
	public ResponseEntity<CampoMensagem> salvarDialogoDeBusca(@RequestBody(required = true) DialogoBusca dialogo) {
		
		dialogoBuscaService.salvarDialogo(dialogo);
		
		return new ResponseEntity<CampoMensagem>(new CampoMensagem("", AppUtil.getMensagem(messageSource,
				"recebimento.coleta.mensagem.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Lista os diálogos de uma busca.
	 * 
	 * @param idRecebimento - identificador do recebimento.
	 * @param recebimento - recebimento de coleta a ser persistido.
	 * @return ResponseEntity<List<DialogoBusca>> - Listagem de dialogo de busca.
	 */
	@RequestMapping(value="{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('DIALOGO_BUSCA')")
	@JsonView(BuscaView.DialogoBusca.class)
	public ResponseEntity<List<DialogoBusca>> listarDialogos(@PathVariable(name="id", required = true) Long idBusca) {
		return new ResponseEntity<List<DialogoBusca>>(dialogoBuscaService.listarDialogos(idBusca), HttpStatus.OK);
	}
	
	
	
}
