package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.GenotipoComparadoDTO;
import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.controller.dto.GenotipoDivergenteDTO;
import br.org.cancer.modred.controller.dto.ResultadoDivergenciaDTO;
import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.IGenotipoPacienteService;
import br.org.cancer.modred.service.IValorGenotipoDoadorService;
import br.org.cancer.modred.service.IValorGenotipoPacienteService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para a entidade Genotipo.
 * 
 * @author Fillipe Queiroz
 *
 */
@RestController
@RequestMapping(value = "/api/genotipo", produces = "application/json;charset=UTF-8")
public class GenotipoController {

	@Autowired
	private IValorGenotipoPacienteService valorGenotipoPacienteService;

	@Autowired
	private IGenotipoPacienteService genotipoService;
	
	@Autowired
	private IValorGenotipoDoadorService<ExameDoadorInternacional> valorGenotipoDoadorInternacionalService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IGenotipoDoadorService<Exame> genotipoDoadorService;

	/**
	 * Serviço para obter o genótipo.
	 * 
	 * @param rmr
	 * @return
	 */
	@RequestMapping(value = "{rmr}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'CONSULTAR_EXAMES_PACIENTE')"
			+ "|| hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')")
	public ResponseEntity<List<GenotipoDTO>> obterGenotipo(@PathVariable(name = "rmr") Long rmr) {
		return new ResponseEntity<List<GenotipoDTO>>(valorGenotipoPacienteService.obterGenotipoPacienteDto(rmr), HttpStatus.OK);
	}
	
	
	/**
	 * Serviço para obter o genótipo de doador.
	 * 
	 * @param id do doador.
	 * @return listagem de valor genotipo de  doador.
	 */
	@RequestMapping(value = "doador/{idDoador}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_DOADOR_INTERNACIONAL + "')")

	public ResponseEntity<List<GenotipoDTO>> obterGenotipoDoador(@PathVariable(name = "idDoador") Long idDoador) {
		return new ResponseEntity<List<GenotipoDTO>>(valorGenotipoDoadorInternacionalService.obterGenotipoDoadorDto(idDoador), HttpStatus.OK);
	}

	/**
	 * Método para comparar genotipos de doador.
	 * 
	 * @param rmr - identificador do paciente
	 * @param listaIdsDoadores - lista em string com ids concatenado por ,
	 * @return listagem de exames encontrados para o paciente.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "pacientes/{rmr}")
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'ANALISE_MATCH')")
	public ResponseEntity<GenotipoComparadoDTO> compararGenotipos(@PathVariable(name = "rmr", required = true) Long rmr,
			@RequestParam("listaIdsDoadores") Long[] listaIdsDoadores) {
		List<Long> ids = null;
		if (listaIdsDoadores != null && !"".equals(listaIdsDoadores)) {
			ids = Arrays.asList(listaIdsDoadores);
		}
		return new ResponseEntity<GenotipoComparadoDTO>(genotipoService.listarGenotiposComparados(rmr, ids), HttpStatus.OK);
	}

	/**
	 * Método para comparar genotipos de doador.
	 * 
	 * @param rmr - identificador do paciente
	 * @param listaIdsDoadores - lista em string com ids concatenado por ,
	 * @return listagem de exames encontrados para o paciente.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "pacientes/impressao/{rmr}")
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'ANALISE_MATCH')")
	public void impressaoCompararGenotipos(
			@PathVariable(name = "rmr", required = true) Long rmr,
			@RequestParam("listaIdsDoadores") Long[] listaIdsDoadores,
			HttpServletResponse response) throws IOException {
		List<Long> ids = null;
		if (listaIdsDoadores != null && !"".equals(listaIdsDoadores)) {
			ids = Arrays.asList(listaIdsDoadores);
		}
		File arquivo = genotipoService.impressaoGenotiposComparados(rmr, ids);
		
		ServletOutputStream stream = response.getOutputStream();

		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), stream);
		response.flushBuffer();
	}
	
	
	/**
	 * Método para comparar genotipos de doador.
	 * 
	 * @param idBuscaPreliminar - identificador da busca preliminar
	 * @param listaIdsDoadores - lista em string com ids concatenado por ,
	 * @return listagem de exames encontrados para o paciente.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "buscaspreliminares/{idBuscaPreliminar}")
	@PreAuthorize("hasPermission('" + Recurso.ANALISE_MATCH_PRELIMINAR + "')")
	public ResponseEntity<GenotipoComparadoDTO> compararGenotiposBuscaPreliminar(@PathVariable(name = "idBuscaPreliminar", required = true) Long idBuscaPreliminar,
			@RequestParam("listaIdsDoadores") Long[] listaIdsDoadores) {
		List<Long> ids = null;
		if (listaIdsDoadores != null && !"".equals(listaIdsDoadores)) {
			ids = Arrays.asList(listaIdsDoadores);
		}
		return new ResponseEntity<GenotipoComparadoDTO>(genotipoService.listarGenotiposComparadosBuscaPreliminar(idBuscaPreliminar, ids), HttpStatus.OK);
	}
	
	/**
	 * Serviço para obter o genótipo com divergencia de um doador.
	 * 
	 * @param id do doador.
	 * @return listagem de valor genotipo de doador.
	 */
	@RequestMapping(value = "divergente/doador/{idDoador}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.VISUALIZAR_GENOTIPO_DIVERGENTE + "')")
	@JsonView(GenotipoView.Divergente.class)
	public ResponseEntity<GenotipoDivergenteDTO> obterGenotipoDivergenteDoador(@PathVariable(name = "idDoador") Long idDoador) {
		return ResponseEntity.ok().body(genotipoDoadorService.obterGenotipoDoadorDivergenteDto(idDoador));
	}
	
	/**
	 * Método para resolver divergencia do genotipo de doador.
	 * 
	 * @param idDoador -- identificador do doador
	 * @param resultadoDivergenciaDTO -- resultado da divergencia.
	 * @return mensagem de sucesso
	 */
	@RequestMapping(value = "doador/{idDoador}/resolverdivergencia", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('" + Recurso.DESCARTAR_LOCUS_EXAME + "')")
	public ResponseEntity<CampoMensagem> marcarComoDivergente(
		@PathVariable(name = "idDoador", required = true) Long idDoador,
		@RequestBody(required = true) ResultadoDivergenciaDTO resultadoDivergenciaDTO ) {
		
		genotipoDoadorService.resolverDivergencia(idDoador, resultadoDivergenciaDTO);
		
		return ResponseEntity.ok().body(new CampoMensagem(AppUtil.getMensagem(messageSource, "exame.atualizado.sucesso")));
		
	}
	
	
	

}