package br.org.cancer.redome.courier.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
import br.org.cancer.redome.courier.controller.dto.TransportadoraListaDTO;
import br.org.cancer.redome.courier.model.ContatoTelefonicoTransportadora;
import br.org.cancer.redome.courier.model.EmailContatoTransportadora;
import br.org.cancer.redome.courier.model.EnderecoContatoTransportadora;
import br.org.cancer.redome.courier.model.Transportadora;
import br.org.cancer.redome.courier.model.domain.Recursos;
import br.org.cancer.redome.courier.service.impl.EnderecoContatoTransportadoraService;
import br.org.cancer.redome.courier.service.impl.TransportadoraService;
import br.org.cancer.redome.courier.util.AppUtil;
import br.org.cancer.redome.courier.util.CampoMensagem;

/**
 * Controlador para transportadora.
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class TransportadoraController {

	@Autowired
	private TransportadoraService transportadoraService;
	
	@Autowired
	private EnderecoContatoTransportadoraService enderecoContatoTransportadoraService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Lista todas as transportadoras.
	 * @return ResponseEntity com uma listatagem de transportadoras.
	 */
	@PreAuthorize("hasPermission('" + Recursos.CONSULTAR_TRANSPORTADORA + "')")
	@GetMapping(path = "/transportadoras")
    public ResponseEntity<List<TransportadoraListaDTO>> listarTransportadoras() {
        return ResponseEntity.ok(transportadoraService.listarIdENomeDeTransportadoresAtivas());
    }
	
	
	/**
	 * Obtém transportadora por ID.
	 * @return ResponseEntity transportadora obtida.
	 */
	@PreAuthorize("hasPermission('" + Recursos.CONSULTAR_TRANSPORTADORA + "')")
	@GetMapping(value="/transportadora/{id}")
    public ResponseEntity<Transportadora> obterTransportadora(@PathVariable(name = "id", required = true) Long id) {
        return ResponseEntity.ok().body(transportadoraService.findById(id));
    }
	
	

	/**
	 * Lista todas as transportadoras.
	 * @param pagina pagina da listagem.
	 * @param quantidadeRegistros quantidade de registros por pagina.
	 * @param nome parametro de pesquisa de de transportadoras.
	 * @return ResponseEntity com uma listatagem de transportadoras.
	 */
	@SuppressWarnings("rawtypes")
	@PreAuthorize("hasPermission('" + Recursos.CONSULTAR_TRANSPORTADORA + "')")
	@GetMapping(value= "/transportadoras/paginada")
    public ResponseEntity<Page> listarTransportadorasPaginada(@RequestParam Integer pagina,
			@RequestParam Integer quantidadeRegistros, @RequestParam(required=false) String nome) {
		
		if (pagina == null) {
			pagina = new Integer(0);
		}
		PageRequest pageRequest = (quantidadeRegistros != null && quantidadeRegistros.intValue() > 0)
				? PageRequest.of(pagina, quantidadeRegistros) : null;
		
        return new ResponseEntity<Page>(
				transportadoraService.listarTransportadoras(pageRequest, nome),
				HttpStatus.OK);
    }
	
	
	/**
	 * Salvar transportadora.
	 * @param transportadora item a ser salvo.
	 * @return ResponseEntity com mensagem de retorno sobre a gravação de transportadora.
	 */
	@PreAuthorize("hasPermission('" + Recursos.SALVAR_TRANSPORTADORA + "')")
	@PostMapping(path="/transportadoras")
    public ResponseEntity<CampoMensagem> salvarTransportadora(@RequestBody Transportadora transportadora) {
		transportadoraService.criarTransportadora(transportadora);
		return ResponseEntity.ok().body(new CampoMensagem(AppUtil.getMensagem(messageSource, "transportadora.mensagem.sucesso.gravacao")));
    }
	
	/**
	 * Atualizar transportadora.
	 * @return ResponseEntity com mensagem de retorno sobre a gravação de transportadora.
	 */
	@PreAuthorize("hasPermission('" + Recursos.SALVAR_TRANSPORTADORA + "')")
	@PutMapping(value = "/transportadora/{id}")
    public ResponseEntity<CampoMensagem> atualizarTransportadora(@PathVariable(name = "id", required = true) Long id
    		, @RequestBody Transportadora transportadora) {
		transportadoraService.save(transportadora);
        return ResponseEntity.ok().body(new CampoMensagem(AppUtil.getMensagem(messageSource, "transportadora.mensagem.sucesso.gravacao")));
    }
	
	/**
	 * Atualizar endereço de transportadora.
	 * @param id identificacao do endereco de contato da transportadoara.
	 * @param enderecoContato endereco a ser atualizado.
	 * @return ResponseEntity com mensagem de retorno sobre a gravação de transportadora.
	 */
	@PreAuthorize("hasPermission('" + Recursos.SALVAR_TRANSPORTADORA + "')")
	@PutMapping(value = "/transportadora/{id}/atualizarendereco")
    public ResponseEntity<CampoMensagem> atualizarEnderecoTransportadora(@PathVariable(name = "id", required = true) Long id
    		, @RequestBody EnderecoContatoTransportadora enderecoContato) {
		enderecoContatoTransportadoraService.atualizarEnderecoContato(id, enderecoContato);
        return ResponseEntity.ok().body(new CampoMensagem(AppUtil.getMensagem(messageSource, "transportadora.mensagem.sucesso.gravacao.endereco")));
    }

	
	
	/**
	 * Atualizar email de transportadora.
	 * 
	 * @param id identificacao da transportadoara.
	 * @param enderecoContato email a ser atualizado.
	 * @return ResponseEntity com mensagem de retorno sobre a gravação de transportadora.
	 */
	@PreAuthorize("hasPermission('" + Recursos.SALVAR_TRANSPORTADORA + "')")
	@PostMapping(value = "/transportadora/{id}/inseriremail")
    public ResponseEntity<RetornoInclusaoDTO> inserirEmailTransportadora(@PathVariable(name = "id", required = true) Long id
    		, @RequestBody EmailContatoTransportadora email) {
        return ResponseEntity.ok().body(transportadoraService.adicionarEmail(id, email));
    }

	
	
	/**
	 * Atualizar telefone de transportadora.
	 * 
	 * @param id identificacao da transportadoara.
	 * @param telefone objeto a ser atualizado.
	 * @return ResponseEntity com mensagem de retorno sobre a gravação de transportadora.
	 */
	@PreAuthorize("hasPermission('" + Recursos.SALVAR_TRANSPORTADORA + "')")
	@PostMapping(value = "/transportadora/{id}/inserirtelefone")
    public ResponseEntity<RetornoInclusaoDTO> inserirTelefoneTransportadora(@PathVariable(name = "id", required = true) Long id
    		, @RequestBody ContatoTelefonicoTransportadora telefone) {
        return ResponseEntity.ok().body(transportadoraService.adicionarContatoTelefonico(id, telefone));
    }

	

	/**
	 * Exclusão lógica de transportadora transportadora.
	 * @return ResponseEntity com mensagem de retorno sobre a exclusão de transportadora.
	 */
	@PreAuthorize("hasPermission('" + Recursos.EXCLUSAO_TRANSPORTADORA + "')")
	@DeleteMapping(value = "/transportadora/{id}")
    public ResponseEntity<CampoMensagem> excluirTransportadora(@PathVariable(name = "id", required = true) Long id) {
		transportadoraService.inativarTransportadora(id);
        return ResponseEntity.ok().body(new CampoMensagem(AppUtil.getMensagem(messageSource, "transportadora.mensagem.sucesso.exclusao")));
    }
	
	
}