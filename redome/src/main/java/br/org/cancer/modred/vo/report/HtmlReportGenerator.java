package br.org.cancer.modred.vo.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.convert.in.xhtml.FormattingOption;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import br.org.cancer.modred.configuration.ApplicationContextProvider;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ConstanteRelatorio;
import br.org.cancer.modred.model.Relatorio;
import br.org.cancer.modred.model.domain.ParametrosRelatorios;
import br.org.cancer.modred.service.IConstanteRelatorioService;
import br.org.cancer.modred.service.impl.ConstanteRelatorioService;

/**
 * @author Fillipe.Queiroz
 *
 */
public class HtmlReportGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(HtmlReportGenerator.class);

	private static String PATH_IMAGENS = "img/";
	private static String URL_ARQUIVO_BASE_DOCX = "/tmp/arquivo.docx";
	private static String URL_ARQUIVO_BASE_PDF = "/tmp/arquivo.pdf";

	private static String IMG_ASSINATURA = "assinatura.jpg";
	private static String IMG_LOGO_INCA = "inca.jpg";
	private static String IMG_LOGO_FUNDACAO = "logo_fundacao.png";
	private static String IMG_LOGO_REDOME = "logo_redome.jpg";
	private static String IMG_LOGO_REDOME_EXTENDIDO = "logo_redome_ext.png";
	private static String IMG_TRANSPORTE_1 = "pt1.jpg";
	private static String IMG_TRANSPORTE_2 = "pt2.jpg";
	private static String IMG_TRANSPORTE_3 = "pt3.jpg";

	private static String PARAMETRO_LOGO_REDOME = "&amp;LOGO_REDOME&amp;";
	private static String PARAMETRO_LOGO_INCA = "&amp;LOGO_INCA&amp;";
	private static String PARAMETRO_LOGO_REDOME_EXTENDIDO = "&amp;LOGO_REDOME_EXTENDIDO&amp;";
	private static String PARAMETRO_ASSINATURA = "&amp;ASSINATURA&amp;";

	private HashMap<ParametrosRelatorios, String> mapaParametros = new HashMap<>();
	
	private IConstanteRelatorioService constanteRelatorioService; 
	
	public HtmlReportGenerator() {
		this.constanteRelatorioService = (ConstanteRelatorioService) ApplicationContextProvider.obterBean(ConstanteRelatorioService.class);
	}

	/**
	 * Método para setar os parametros necessarios do relatorio.
	 * @param param - enum com o parametro
	 * @param valor - valor a ser substituido
	 * @return HtmlReportGenerator classe do relatorio
	 */
	public HtmlReportGenerator comParametro(ParametrosRelatorios param, String valor) {
		mapaParametros.put(param, StringUtils.isNotEmpty(valor) ? valor : "");
		return this;
	}
	
	public HtmlReportGenerator comParametros(HashMap<ParametrosRelatorios, String> parametros) {
		mapaParametros.putAll(parametros);
		return this;
	}
	

	/**
	 * Método para gerar um arquivo pdf de um determinado HTML.
	 * 
	 * @param relatorioExistente - relatorio com html a ser gerado.
	 * @return File arquivo de relatorio
	 */
	private File gerarArquivoPdfDeHtml(String html) {

		String novoHtml = substituirImagens(html);		

		try {
			File htmlFile = criarArquivo(novoHtml);

			Document document = new Document();
			PdfWriter writer = null;

			writer = PdfWriter.getInstance(document, new FileOutputStream(URL_ARQUIVO_BASE_PDF));
			document.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(htmlFile));
			document.close();
		}
		catch (Exception e1) {
			e1.printStackTrace();
			throw new BusinessException("erro.gerar.relatorio");
		}

		return new File(URL_ARQUIVO_BASE_PDF);
	}

	/**
	 * Método para gerar um arquivo docx de um determinado HTML.
	 * 
	 * @param relatorioExistente - relatorio com html a ser gerado.
	 * @return File arquivo de relatorio
	 */
	private File gerarArquivoDocxDeHtml(String html) {

		String novoHtml = substituirImagens(html);
		String unescaped = StringEscapeUtils.unescapeHtml("<table><tbody>"+ novoHtml +"</tbody></table>");        	
		
		WordprocessingMLPackage wordMLPackage;
		try {
			wordMLPackage = WordprocessingMLPackage.createPackage(PageSizePaper.A4, false);
			
			XHTMLImporterImpl xHTMLImporter = new XHTMLImporterImpl(wordMLPackage);
	        xHTMLImporter.setHyperlinkStyle("Hyperlink");
	        xHTMLImporter.setTableFormatting(FormattingOption.IGNORE_CLASS);
	        xHTMLImporter.setParagraphFormatting(FormattingOption.IGNORE_CLASS);
	        
	        wordMLPackage.getMainDocumentPart().getContent().addAll(
	        		xHTMLImporter.convert(unescaped, null));
	        
			wordMLPackage.save(new java.io.File(URL_ARQUIVO_BASE_DOCX) );
			
		}catch (Exception e1) {
			e1.printStackTrace();
			throw new BusinessException("erro.gerar.relatorio");
		}
		
		return new File(URL_ARQUIVO_BASE_DOCX);
	}

	
	private String substituirImagens(String html) {
		String novoHtml = new String(html);
		if (novoHtml.contains(PARAMETRO_LOGO_REDOME)) {

			novoHtml = novoHtml.replaceAll(PARAMETRO_LOGO_REDOME, "<img alt='' src='"
					+ getPathFile(PATH_IMAGENS + IMG_LOGO_REDOME) + "' />");
		}
		if (novoHtml.contains(PARAMETRO_LOGO_REDOME_EXTENDIDO)) {

			novoHtml = novoHtml.replaceAll(PARAMETRO_LOGO_REDOME_EXTENDIDO,
					"<img alt='' src='"
							+ getPathFile(PATH_IMAGENS + IMG_LOGO_REDOME_EXTENDIDO) + "' />");
		}
		if (novoHtml.contains(PARAMETRO_ASSINATURA)) {
			novoHtml = novoHtml.replaceAll(PARAMETRO_ASSINATURA, "<img alt='' src='"
					+ getPathFile(
							PATH_IMAGENS + IMG_ASSINATURA) + "' />");
		}
		if (novoHtml.contains(PARAMETRO_LOGO_INCA)) {
			novoHtml = novoHtml.replaceAll(PARAMETRO_LOGO_INCA, "<img alt='' src='"
					+ getPathFile(
							PATH_IMAGENS + IMG_LOGO_INCA) + "' />");
		}
		return novoHtml;
	}

	private static File criarArquivo(String html) throws IOException {
		File temp = File.createTempFile("tempfile", ".html");

		BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
		bw.write(html);
		bw.close();
		return temp;

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

	/**
	 * Método para gerar o pdf substituindo os parametros.
	 * 
	 * @param relatorio - entidade de relatorio que contém o html
	 * @return File arquivo a ser baixado.
	 */
	public File gerarPdf(Relatorio relatorio) {
		relatorio.setHtml(substiruirParametrosConstantes(relatorio.getHtml()));

		return gerarArquivoPdfDeHtml(relatorio.getHtml());
	}

	public File gerarDocx(Relatorio relatorio) {
		relatorio.setHtml(substiruirParametrosConstantes(relatorio.getHtml()));

		return gerarArquivoDocxDeHtml(relatorio.getHtml());
	}
	
	/**
	 * Método para gerar o pdf sem substituir os parametros.
	 * 
	 * @param relatorio - entidade de relatorio que contém o html
	 * @return File arquivo a ser baixado.
	 */
	public File gerarPdfSemParametros(Relatorio relatorio) {
		return gerarArquivoPdfDeHtml(relatorio.getHtml());
	}
	
	/**
	 * Método para gerar o HTML substituindo os parametros.
	 * 
	 * @param relatorio - entidade de relatorio que contém o html
	 * @return String html atualizado.
	 */
	public String gerarHTML(Relatorio relatorio) {		
		return new String(substituirImagens(substiruirParametrosConstantes(relatorio.getHtml())));
	}

	private String substiruirParametrosConstantes(String html) {
		String novoHtml = new String(html);
		for (ParametrosRelatorios key : mapaParametros.keySet()) {
			String codigo = key.getCodigo();
			if (novoHtml.contains(codigo)) {
				novoHtml = novoHtml.replaceAll(codigo, mapaParametros.get(key));
			}
		}

		for (ParametrosRelatorios key : ParametrosRelatorios.values()) {
			String codigo = key.getCodigo();
			if (novoHtml.contains(codigo)) {
				LOGGER.error("[HtmlReportGenerator] Erro parametro nao preenchido para gerar relatório." + codigo);
				throw new BusinessException("erro.gerar.relatorio");
			}
		}
		
		List<ConstanteRelatorio> constantes = constanteRelatorioService.findAll();
		for (ConstanteRelatorio constante: constantes) {
			if (novoHtml.contains(constante.getCodigo())) {
				novoHtml = novoHtml.replaceAll(constante.getCodigo(), constante.getValor());
			}
		}
		return novoHtml;
	}

}
