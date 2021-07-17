package br.org.cancer.modred.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.modred.controller.dto.RelatorioDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Relatorio;
import br.org.cancer.modred.model.domain.ParametrosRelatorios;
import br.org.cancer.modred.persistence.IRelatorioRepository;
import br.org.cancer.modred.service.IRelatorioService;
import br.org.cancer.modred.vo.report.HtmlReportGenerator;

/**
 * Classe de serviço para gerar e obter relatorios dinamicos.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class RelatorioService implements IRelatorioService {

	@Autowired
	private IRelatorioRepository relatorioRepository;
	
	@Autowired
	private ObjectMapper objectMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IRelatorioService#salvarRelatorio(java.lang.String)
	 */
	@Override
	public Relatorio salvarRelatorio(RelatorioDTO relatorioEnviado) {
		Relatorio relatorioExistente = relatorioRepository.findByCodigo(relatorioEnviado.getCodigo());
		Relatorio relatorio = null;
		if (relatorioExistente != null) {
			relatorio = relatorioExistente;
		}
		else {
			relatorio = new Relatorio();
		}
		relatorio.setTipo(1L);
		relatorio.setHtml(relatorioEnviado.getHtml());
		relatorio.setCodigo(relatorioEnviado.getCodigo());

		return relatorioRepository.save(relatorio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IRelatorioService#listar()
	 */
	@Override
	public List<Relatorio> listar() {
		return relatorioRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IRelatorioService#obterArquivo(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true)
	public File obterArquivo(String codigo) {
		Relatorio relatorioExistente = relatorioRepository.findByCodigo(codigo);

		if (relatorioExistente == null) {
			throw new BusinessException("erro.relatorio.chave");
		}

		return new HtmlReportGenerator().gerarPdfSemParametros(relatorioExistente);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IRelatorioService#obterArquivo(java.lang.Long)
	 */
	@Override
	public Relatorio obterRelatorioPorCodigo(String codigo) {
		Relatorio relatorioExistente = relatorioRepository.findByCodigo(codigo);

		if (relatorioExistente == null) {
			throw new BusinessException("erro.relatorio.chave");
		}

		return relatorioExistente;
	}

	@Override
	public List<String> listarParametros() {
		return Stream.of(ParametrosRelatorios.values()).map(parametro -> {
			return "&" + parametro.getCodigo().replaceAll("&amp;", "") + "&";
		}).collect(Collectors.toList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public File gerarPdf(String codigo, String parametros) {
		Relatorio relatorio = obterRelatorioPorCodigo(codigo);
		
		HashMap<ParametrosRelatorios, String> mapaParametros = new HashMap<>();
		if (StringUtils.isNotEmpty(parametros)) {
			String jsonParam = Base64.decodeAsString(parametros);
			try {
				mapaParametros = objectMapper.readValue(jsonParam.getBytes(), new TypeReference<HashMap<ParametrosRelatorios, String>>(){});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return new HtmlReportGenerator()
				.comParametros(mapaParametros)
				.gerarPdf(relatorio);
		
	}
	

}