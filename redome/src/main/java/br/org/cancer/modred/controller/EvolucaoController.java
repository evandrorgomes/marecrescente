package br.org.cancer.modred.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IEvolucaoService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe responsável por controlar as requisições do front-end envolvendo a entidade Evolução e chamadas relacionadas a ela.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/evolucoes", produces = "application/json;charset=UTF-8")
public class EvolucaoController {

	@Autowired
	private IEvolucaoService evolucaoService;

	@Autowired
	private MessageSource messageSource;
	

	/**
	 * Método utilizado para buscar somente uma evolução a partir do ID.
	 * 
	 * @Return ResponseEntity<Evolucao> evolução carregada por ID
	 */
	@GetMapping(value = "{id}")
	@PreAuthorize("hasPermission(#id, 'Evolucao', '" + Recurso.CONSULTAR_EVOLUCOES_PACIENTE + "') "
			+ " || hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP + "') "
			+ " || hasPermission('" + Recurso.INFORMAR_PLANO_WORKUP_NACIONAL + "') "
			+ " || hasPermission('" + Recurso.AVALIAR_PRESCRICAO + "') "
			+ " || hasPermission('" + Recurso.INFORMAR_PLANO_WORKUP_INTERNACIONAL + "')")
	@JsonView(EvolucaoView.ConsultaEvolucao.class)
	public ResponseEntity<Evolucao> obterEvolucaoPorId(@PathVariable(name = "id", required = true) Long id) {
		return ResponseEntity.ok(evolucaoService.obterEvolucaoPorId(id));
	}

	/**
	 * Método utilizado para buscar a ultima evolução a partir do ID.
	 * 
	 * @Return ResponseEntity<Evolucao> evolução carregada por ID
	 */
	@RequestMapping(value = "ultima/{rmr}", method = RequestMethod.GET)
	@PreAuthorize("(hasPermission(#rmr, 'Paciente', 'VISUALIZAR_IDENTIFICACAO_COMPLETA')"
			+ " || hasPermission(#rmr, 'Paciente', 'VISUALIZAR_IDENTIFICACAO_PARCIAL')"
			+ " || hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')"
			+ " || hasPermission('RECEBER_COLETA_LABORATORIO'))"
			+ " || hasPermission('" + Recurso.AVALIAR_NOVA_BUSCA_PACIENTE + "')")
	@JsonView(EvolucaoView.ListaEvolucao.class)
	public ResponseEntity<Evolucao> obterUltimaEvolucao(@PathVariable(name = "rmr",
																required = true) Long rmr) {
		Evolucao evolucao = evolucaoService.obterUltimaEvolucaoDoPaciente(rmr);
		return new ResponseEntity<Evolucao>(evolucao, HttpStatus.OK);
	}

	
	
	
	/**
	 * Método rest para gravar nova evolucao.
	 *
	 * @param evolucao a ser persistida.
	 * @param listaArquivosEvolucao arquivos de evolução.
	 * @return ResponseEntity<CampoMensagem> Lista de erros
	 * @throws IOException 
	 */
	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#evolucao.paciente.rmr, 'Paciente', 'CADASTRAR_EVOLUCOES_PACIENTE')")
//	@PreAuthorize("hasPermission('CADASTRAR_EVOLUCOES_PACIENTE')")
	public ResponseEntity<CampoMensagem> salvar(@RequestPart Evolucao evolucao,@RequestPart(name = "file") List<MultipartFile> listaArquivosEvolucao) throws IOException {
		evolucaoService.salvar(evolucao, listaArquivosEvolucao);
		final CampoMensagem mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource, "evolucao.incluido.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Método rest para gravar nova evolucao respondendo uma ou mais pendências.
	 *
	 * @param evolucao a ser persistida.
	 * @param pendencias a serem respondindas
	 * @param resposta texto a ser colocado na resposta da pendencia
	 * @param respondePendencia flag que indica se as pendências seram marcadas como respondidas
	 * @param listaArquivosEvolucao lista de 
	 * @return ResponseEntity<CampoMensagem> Lista de erros
	 * @throws IOException 
	 */
	@RequestMapping(value = "/respostas", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission(#evolucao.paciente.rmr, 'Paciente', 'CADASTRAR_EVOLUCOES_PACIENTE')")
	public ResponseEntity<String> salvar(@RequestPart Evolucao evolucao, @RequestPart List<Pendencia> pendencias,
			@RequestPart String resposta, @RequestPart Boolean respondePendencia, @RequestPart(name = "file") List<MultipartFile> listaArquivosEvolucao) throws IOException {
		evolucaoService.salvar(evolucao, pendencias, resposta, respondePendencia, listaArquivosEvolucao);
		final String mensagem = AppUtil.getMensagem(messageSource, "evolucao.incluido.sucesso");
		return new ResponseEntity<String>(mensagem, HttpStatus.OK);
	}

}
