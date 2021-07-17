package br.org.cancer.redome.tarefa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Files;


/**
 * Classe utilitária para auxiliar na manipulação de arquivos.
 * 
 * @author Filipe Queiroz
 *
 */
public class ArquivoUtil {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(ArquivoUtil.class);
	private static final Logger LOGGER = LogManager.getLogger("log-job-importacao");
	
	/**
	 * Faz o download do arquivo e salva com o nome passaso pelo parametro fileName.
	 * @param endpoint - url para download
	 * @param fileName - nome do arquivo a ser salvo
	 * @return File - o arquiv obaixado
	 * @throws Exception - exceção
	 */
	public static File baixarArquivo(String endpoint, String fileName) {

		File file = new File(fileName);
		if (file.exists()) {
			Files.delete(file);
		}
		
		URL url = null;
		URLConnection conn = null;
		try {
			url = new URL(endpoint);
		}
		catch (MalformedURLException e) {
			LOGGER.error("", e);
		}

		try (			
			ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);			
		) {
			fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
			return new File(fileName);
		}
		catch (Exception e) {
			LOGGER.error("Erro ao fazer download.", e);
		}
		
		return null;		
	}
	
	/**
	 * Descompacta um arquivo zipado e retorna o ultimo arquivo descompactado.
	 * 
	 * @param file - arquivo compactado
	 * @return File - ultimo arquivo descompactado
	 * @throws Exception - exceção
	 */
	public static File descompctarArquivo(File file) {
		
		String unzipFileName = "";
		try (			
			FileInputStream fileInputStream = new FileInputStream(file);	        	
			ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
		) {
	        ZipEntry zipEntry;
	        while((zipEntry=zipInputStream.getNextEntry())!= null){
	        	unzipFileName = zipEntry.getName();
	        	File unzipFile = new File(unzipFileName);
	        	if (unzipFile.exists()) {
	        		unzipFile.delete();
	        	}		        	
	        	descompactar(zipInputStream, unzipFileName);
	        }
	        
	        if (!StringUtils.isBlank(unzipFileName)) {
	        	return new File(unzipFileName);	
	        }
	        
		} 
		catch (IOException e) {
			LOGGER.error("Erro ao descmpactar o arquivo.", e);			
		}
		
		return null;
	}
	
	/**Método para descompactar um arquivo.
	 * @param zipInputStream
	 * @param arquivo
	 * @throws IOException
	 */
	private static void descompactar(ZipInputStream zipInputStream, String arquivo) {
		try (
			FileOutputStream fileOutputStream = new FileOutputStream(arquivo);
		) {
		    byte [] bytes = new byte[2048];
		    int length = 0;
		    while ( (length=zipInputStream.read(bytes))!= -1 ) {
		    	fileOutputStream.write(bytes,0,length);
		    }
		}
		catch (IOException e) {
			LOGGER.error("Erro ao obter o arquivo para descompactar.", e);
		}
	}
}
