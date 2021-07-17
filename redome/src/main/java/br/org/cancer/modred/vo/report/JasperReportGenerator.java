package br.org.cancer.modred.vo.report;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.cancer.modred.controller.dto.GenotipoComparadoDTO;
import br.org.cancer.modred.exception.BusinessException;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Fillipe.Queiroz
 *
 */
public class JasperReportGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(JasperReportGenerator.class);

	private static String NOME_CARTA_MO = "Carta 002 - Carta para transporte de MO Rev.00.pdf";
	private static String NOME_RELATORIO_TRANSPORTE = "FOR029 - Relatório de Transporte Rev.02.doc";
	private static String JASPER_CARTA_MO = "carta_MO.jasper";
	private static String JASPER_RELATORIO_TRANSPORTE = "transporte.jasper";
	private static String PATH_REPORT = "report/";
	private static String PATH_IMAGENS = "img/";

	private static String IMG_ASSINATURA = "assinatura.jpg";
	private static String IMG_LOGO_INCA = "inca.jpg";
	private static String IMG_LOGO_FUNDACAO = "logo_fundacao.png";
	private static String IMG_LOGO_REDOME = "logo_redome.jpg";
	private static String IMG_TRANSPORTE_1 = "pt1.jpg";
	private static String IMG_TRANSPORTE_2 = "pt2.jpg";
	private static String IMG_TRANSPORTE_3 = "pt3.jpg";

	private static String JASPER_RELATORIO_GENOTIPO_COMPARADO = "genotipo_comparado.jasper";
	private static String NOME_GENOTIPO_COMPARADO = "genotipoComparado.pdf";
	
	
	/**
	 * Gera carta para a transportadora.
	 * 
	 * @param parametros - parametros dinamicos do relatorio.
	 * @return File arquivo.
	 */
	public static File gerarCartaMO(Map<String, Object> parametros) {
		parametros.put("assinatura", getPathFile(PATH_IMAGENS + IMG_ASSINATURA));
		parametros.put("logo_redome", getPathFile(PATH_IMAGENS + IMG_LOGO_REDOME));
		parametros.put("logo_inca", getPathFile(PATH_IMAGENS + IMG_LOGO_INCA));
		return gerarRelatorio(JASPER_CARTA_MO, NOME_RELATORIO_TRANSPORTE, parametros);
	}

	/**
	 * Gerar relatório de transporte.
	 * 
	 * @return File arquivo
	 */
	public static File gerarRelatorioTransporte() {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("logo_inca", getPathFile(PATH_IMAGENS + IMG_LOGO_INCA));
		parametros.put("logo_fundacao", getPathFile(PATH_IMAGENS + IMG_LOGO_FUNDACAO));
		parametros.put("logo_redome", getPathFile(PATH_IMAGENS + IMG_LOGO_REDOME));
		parametros.put("transporte1", getPathFile(PATH_IMAGENS + IMG_TRANSPORTE_1));
		parametros.put("transporte2", getPathFile(PATH_IMAGENS + IMG_TRANSPORTE_2));
		parametros.put("transporte3", getPathFile(PATH_IMAGENS + IMG_TRANSPORTE_3));
		return gerarRelatorio(JASPER_RELATORIO_TRANSPORTE, NOME_CARTA_MO, parametros);
	}

	/**
	 * Gerar relatório de Genotipo Comparado.
	 * 
	 * @return File arquivo
	 */
	public static File gerarRelatorioGenotipoComparado(Collection<GenotipoComparadoDTO> dados) {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("logo_inca", getPathFile(PATH_IMAGENS + IMG_LOGO_INCA));
		parametros.put("logo_redome", getPathFile(PATH_IMAGENS + IMG_LOGO_REDOME));
		return gerarRelatorioGenotipoComparado(JASPER_RELATORIO_GENOTIPO_COMPARADO, NOME_GENOTIPO_COMPARADO, parametros, dados);
	}
	
	
	private static File gerarRelatorioGenotipoComparado(String arquivoJasper, String nomeArquivo, Map<String, Object> parametros, Collection<?> dados) {
		FileInputStream fis = null;
		try {

			fis = new FileInputStream(getFile(PATH_REPORT + arquivoJasper));

			JRBeanCollectionDataSource  emptyDS = new JRBeanCollectionDataSource(dados);
			JasperPrint jasperPrint = JasperFillManager.fillReport(fis, parametros, emptyDS);

			JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
			return new File(nomeArquivo);

		}
		catch (Throwable e) {
			LOGGER.error("Erro ao gerar relatorio", e);
			throw new BusinessException("erro.gerar.relatorio");
		}
	}
	
	private static File gerarRelatorio(String arquivoJasper, String nomeArquivo, Map<String, Object> parametros) {
		FileInputStream fis = null;
		try {

			fis = new FileInputStream(getFile(PATH_REPORT + arquivoJasper));

			JREmptyDataSource emptyDS = new JREmptyDataSource();
			JasperPrint jasperPrint = JasperFillManager.fillReport(fis, parametros, emptyDS);

			JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
			return new File(nomeArquivo);

		}
		catch (Throwable e) {
			LOGGER.error("Erro ao gerar relatorio", e);
			throw new BusinessException("erro.gerar.relatorio");
		}
	}

	private static File getFile(String fileName) {
		ClassLoader classLoader = JasperReportGenerator.class.getClassLoader();
		return new File(classLoader.getResource(fileName).getFile());

	}

	private static String getPathFile(String fileName) {
		ClassLoader classLoader = JasperReportGenerator.class.getClassLoader();
		try {
			return classLoader.getResource(fileName).toURI().getPath();
		}
		catch (URISyntaxException e) {
			LOGGER.error("Erro ao gerar relatorio", e);
			throw new BusinessException("erro.gerar.relatorio");
		}
	}
}
