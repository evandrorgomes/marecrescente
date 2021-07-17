package br.org.cancer.modred.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.abstracts.AbstractController;
import br.org.cancer.modred.service.IPedidoIdmService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para serviços referentes a pedido IDM.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/pedidosidm", produces = "application/json;charset=UTF-8")
public class PedidoIdmController extends AbstractController{

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IPedidoIdmService pedidoIdmService;
	

	/**
	 * Registra o resultado do pedido IDM (laudo, basicamente).
	 * 
	 * @param idPedidoIdm ID do pedido de IDM.
	 * @param listaLaudos lista de arquivos a serem salvos. Para este processo, somente um arquivo deve vir.
	 * @return Campo mensagem com a mensagem de sucesso, caso ocorra.
	 */
	@RequestMapping(value = "{id}/resultado/idm/doadorinternacional", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CADASTRAR_RESULTADO_PEDIDO_IDM')")
	public ResponseEntity<CampoMensagem> salvarResultadoPedidoIdmInternacional(
			@PathVariable(name = "id", required = true) Long idPedidoIdm,
			@RequestPart(name = "file", required = true) List<MultipartFile> listaLaudos) {

		pedidoIdmService.salvarResultadoPedidoIdmDoadorInternacional(idPedidoIdm, listaLaudos);

		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem("", AppUtil.getMensagem(messageSource, "laudo.pedido.exame.idm.sucesso")), HttpStatus.OK);
	}
	
	
	/**
	 * Realiza o download do arquivo IDM de resultado de exame.
	 * 
	 * @param idPedido id do pedido que possui o resultado.
	 * @param response response que deverá escrever os dados para o front.
	 * @throws IOException exceção lançada, caso não consiga acessar o arquivo.
	 */
	@RequestMapping(value = "{id}/download", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CADASTRAR_DOADOR_INTERNACIONAL')")
	public void baixarArquivoIDM(
			@PathVariable(name = "id", required = true) Long idPedido, HttpServletResponse response) throws IOException {
		File arquivo = pedidoIdmService.downloadArquivoIdm(idPedido);
		serializarArquivo(response, arquivo);
	}
}
