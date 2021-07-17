package br.org.cancer.redome.workup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.ConsultaPrescricaoDTO;
import br.org.cancer.redome.workup.dto.CriarPrescricaoCordaoDTO;
import br.org.cancer.redome.workup.dto.CriarPrescricaoMedulaDTO;
import br.org.cancer.redome.workup.dto.PrescricaoCordaoDTO;
import br.org.cancer.redome.workup.dto.PrescricaoEvolucaoDTO;
import br.org.cancer.redome.workup.dto.PrescricaoMedulaDTO;
import br.org.cancer.redome.workup.dto.PrescricaoSemAutorizacaoPacienteDTO;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.service.IPrescricaoService;
import br.org.cancer.redome.workup.util.AppUtil;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class PrescricaoController {
	
	@Autowired
	private IPrescricaoService prescricaoService;

	@Autowired
	private MessageSource messageSource;
	
	@PostMapping(value="/prescricoes/medula", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	public ResponseEntity<String> criarPrescricaoMedula(@RequestPart(required = true) CriarPrescricaoMedulaDTO prescricaoMedulaDTO,
			@RequestPart(name = "filejustificativa", required = false) MultipartFile arquivoJustificativa,
			@RequestPart(name = "file", required = false) List<MultipartFile> listaArquivos) throws Exception {
		
		prescricaoService.criarPrescricao(prescricaoMedulaDTO, arquivoJustificativa, listaArquivos);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "solicitacao.prescricao.criado.sucesso"));
	}
	
	@PostMapping(value="/prescricoes/cordao", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	public ResponseEntity<String> criarPrescricaoCordao(@RequestPart(required = true) CriarPrescricaoCordaoDTO prescricaoCordaoDTO,
			@RequestPart(name = "filejustificativa", required = false) MultipartFile arquivoJustificativa,
			@RequestPart(name = "file", required = false) List<MultipartFile> listaArquivos) throws Exception {
		
		prescricaoService.criarPrescricao(prescricaoCordaoDTO, arquivoJustificativa, listaArquivos);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "solicitacao.prescricao.criado.sucesso"));
	}


	@GetMapping(value = "/prescricoes/disponiveis")
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	public ResponseEntity<Page<ConsultaPrescricaoDTO>> listarTarefasDeCadastrarPrescricao(
			@RequestParam(required = true) Long idCentroTransplante,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) throws JsonProcessingException {
		
		return ResponseEntity.ok(prescricaoService.listarPrescricoesDisponiveis(idCentroTransplante, PageRequest.of(pagina, quantidadeRegistros)));
	}

	@GetMapping(value = "/prescricoes")
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	public ResponseEntity<Page<ConsultaPrescricaoDTO>> listarPrescricoesPorStatusECentroTransplante(
			@RequestParam(required=true) Long idCentroTransplante,
			@RequestParam(required=false) String[] status,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		return ResponseEntity.ok(prescricaoService.listarPrescricoesPorStatusECentroTransplante(idCentroTransplante, status, PageRequest.of(pagina, quantidadeRegistros)));
	}
	
	@GetMapping(value = "/prescricaomedula/{id}")	
	@PreAuthorize("hasPermission('" + Recursos.CADASTRAR_PRESCRICAO + "')")			
	public ResponseEntity<PrescricaoMedulaDTO> obterPrescricaoComEvolucaoPorId(@PathVariable(name = "id", required = true) Long idPrescricao) {
		return ResponseEntity.ok(prescricaoService.obterPrescricaoMedulaComEvolucaoPorId(idPrescricao));
	}

	@GetMapping(value = "/prescricaocordao/{id}")	
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	public ResponseEntity<PrescricaoCordaoDTO> obterPrescricaoCordaoPorId(@PathVariable(name = "id", required = true) Long idPrescricao) {
		return ResponseEntity.ok(prescricaoService.obterPrescricaoCordaoPorId(idPrescricao));
	}

	@GetMapping(value = "/prescricaoevolucao/{id}")	
	@PreAuthorize("hasPermission('" + Recursos.CADASTRAR_PRESCRICAO + "') "
			+ "|| hasPermission('" + Recursos.TRATAR_PEDIDO_WORKUP + "') "
			+ "|| hasPermission('" + Recursos.INFORMAR_PLANO_WORKUP_NACIONAL + "') "
			+ "|| hasPermission('" + Recursos.INFORMAR_PLANO_WORKUP_INTERNACIONAL + "') "
			+ "|| hasPermission('" + Recursos.AVALIAR_PEDIDO_COLETA + "')")
	public ResponseEntity<PrescricaoEvolucaoDTO> obterPrescricaoComEvolucaoPorIdPescricao(@PathVariable(name = "id", required = true) Long idPrescricao) {
		return ResponseEntity.ok(prescricaoService.obterPrescricaoComEvolucaoPorIdPrescricao(idPrescricao));
	}
	
	
	@PreAuthorize("hasPermission('" + Recursos.AUTORIZACAO_PACIENTE + "')")
	@GetMapping(value = "/prescricoes/autorizacaopaciente")
	public ResponseEntity<Page<PrescricaoSemAutorizacaoPacienteDTO>> listarPrescricoesSemAutorizacaoPaciente(
			@RequestParam(required = true) Long idCentroTransplante,
			@RequestParam(required = true) Boolean atribuidoAMin,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		
		return ResponseEntity.ok(prescricaoService
					.listarPrescricoesSemAutorizacaoPaciente(idCentroTransplante, atribuidoAMin, pagina, quantidadeRegistros));
	}
	
	/**
	 * Recebe o arquivo de autorização de paciente.
	 * 
	 * @param idPrescricao - identificador da prescricao
	 * @param arquivo - Arquivo de autorização do paciente 
	 * @return mensagem de retorno
	 */
	@PostMapping(value = "/prescricao/{id}/autorizacaopaciente", consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recursos.AUTORIZACAO_PACIENTE + "')")
	public ResponseEntity<String> salvarArquivoAutorizacaoPaciente(
			@PathVariable(name = "id", required = true) Long idPrescricao,			
			@RequestPart(name = "file", required = true) MultipartFile arquivo) {

		prescricaoService.salvarArquivoAutorizacaoPaciente(idPrescricao, arquivo);
		
		return ResponseEntity.ok(AppUtil.getMensagem(messageSource, "prescricao.arquivo.autorizacao.paciente.salvo"));
	}

	
}
