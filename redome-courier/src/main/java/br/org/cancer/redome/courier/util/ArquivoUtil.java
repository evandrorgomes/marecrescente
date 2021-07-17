package br.org.cancer.redome.courier.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.redome.courier.model.Configuracao;
import br.org.cancer.redome.courier.service.IConfiguracaoService;

/**
 * Classe utilitária para auxiliar na manipulação de arquivos.
 * 
 * @author Filipe Queiroz
 *
 */
public class ArquivoUtil {
	
	private static final Long TAMANHO_MAXIMO_NOME_ARQUIVO = 257L;

	/**
	 * Método para obter o tamanho de um arquivo do tipo MultipartFile em MB.
	 * 
	 * @param arquivo arquivo
	 * @return tamanho arquivo em MB
	 */
	public static Double obterTamanhoArquivoEmMegaBytes(MultipartFile arquivo) {
		double bytes = arquivo.getSize();
		double kilobytes = ( bytes / 1024 );
		Double megabytes = ( kilobytes / 1024 );
		return megabytes;
	}

	/**
	 * Método para converter bytes para mb.
	 * 
	 * @param bytes
	 * @return
	 */
	public static Double converterBytesParaMegaBytes(String bytes) {
		return converterBytesParaMegaBytes(Long.valueOf(bytes));
	}

	/**
	 * Método para converter bytes para MB.
	 * 
	 * @param bytes bytes
	 * @return valor valor
	 */
	public static Double converterBytesParaMegaBytes(long bytes) {
		double kilobytes = ( bytes / 1024 );
		Double megabytes = ( kilobytes / 1024 );
		return megabytes;
	}

	/**
	 * Obtém a extensão de um arquivo.
	 * 
	 * @param arquivo arquivo
	 * @return valor valor
	 */
	public static String obterExtensaoArquivo(MultipartFile arquivo) {
		String nomeArquivo = arquivo.getOriginalFilename();
		if (nomeArquivo != null) {
			return nomeArquivo.substring(nomeArquivo.lastIndexOf('.') + 1);
		}
		else {
			return "";
		}
	}

	/**
	 * Gera o nome do arquivo que será gravado no Storage, conforme a padronização estabelecida (timestamp + "_" + nome arquivo).
	 * 
	 * @param arquivo referência para o arquivo carregado no upload.
	 * @return o nome do arquivo formatado.
	 */
	public static String obterNomeArquivo(MultipartFile arquivo) {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		return obterNomeArquivo(timeStampMillis, arquivo);
	}
	
	/**
	 * Gera o nome do arquivo que será gravado no Storage, conforme a padronização estabelecida (timestamp + "_" + nome arquivo).
	 * 
	 * @param timeStampMillis Timestampo da data/hora no momento da geração.
	 * @param arquivo referência para o arquivo carregado no upload.
	 * @return o nome do arquivo formatado.
	 */
	public static String obterNomeArquivo(long timeStampMillis, MultipartFile arquivo) {

		return obterNomeArquivo(timeStampMillis, arquivo.getOriginalFilename());
	}

	/**
	 * Gera o nome do arquivo que será gravado no Storage, conforme a padronização estabelecida (timestamp + "_" + nome arquivo).
	 * 
	 * @param timeStampMillis Timestampo da data/hora no momento da geração.
	 * @param nomeArquivo
	 * @return o nome do arquivo formatado.
	 */
	public static String obterNomeArquivo(long timeStampMillis, String nomeArquivo) {
		return timeStampMillis + "_" + nomeArquivo;
	}

	/**
	 * Obtém o nome do arquivo SEM timestamp e sem o caminho completo.
	 * 
	 * @param caminhoCompleto - string com o caminho completo
	 * @return String com apenas o nome do arquivo.
	 */
	public static String obterNomeArquivoSemTimestampPorCaminhoArquivo(String caminhoCompleto) {
		List<String> partes = AppUtil.splitReturnString(caminhoCompleto, "/");
		String ultimaParte = partes.get(partes.size() - 1);

		return retirarTimestamp(ultimaParte);
	}

	/**
	 * Obtém o nome do arquivo COM timestamp e sem o caminho completo.
	 * 
	 * @param caminhoCompleto - string com o caminho completo
	 * @return String com apenas o nome do arquivo.
	 */
	public static String obterNomeArquivoComTimestampPorCaminhoArquivo(String caminhoCompleto) {
		List<String> partes = AppUtil.splitReturnString(caminhoCompleto, "/");
		return partes.get(partes.size() - 1);

	}

	private static String retirarTimestamp(String comTimestamp) {
		List<String> partes = AppUtil.splitReturnString(comTimestamp, "_");
		return partes.stream().filter(parte -> {
			return !StringUtils.isNumeric(parte) || ( StringUtils.isNumeric(parte) && parte.length() < 9 );
		}).collect(Collectors.joining("_"));
	}
		
	private static String validarTamanhoArquivoPorConfiguracao(MultipartFile arquivo,
			MessageSource messageSource, Configuracao configuracao) {
		if (arquivo != null) {

			Long tamanhoArquivo = arquivo.getSize();
			Long tamanhoMaximoPermitidoPorArquivoLaudo = Long.valueOf(configuracao
					.getValor());
			if (tamanhoArquivo > tamanhoMaximoPermitidoPorArquivoLaudo) {
				return AppUtil.getMensagem(messageSource,
						"erro.arquivo.tamanho.invalido", ArquivoUtil
								.converterBytesParaMegaBytes(configuracao.getValor()));
			}
		}
		return null;
	}
	
	private static void validarExtensaoArquivoPrescricaoPorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService, String campo) {
		String mensagem = validarExtensaoArquivoPorConfiguracao(arquivo, campos, messageSource,
				obterExtensoesValidas(Configuracao.EXTENSAO_ARQUIVO_PRESCRICAO, configuracaoService));
		if (mensagem != null) {
			campos.add(new CampoMensagem(campo, mensagem));
		}
	}
	
	private static void validarExtensaoArquivoAutorizacaoPacientePorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService, String campo) {
		String mensagem = validarExtensaoArquivoPorConfiguracao(arquivo, campos, messageSource,
				obterExtensoesValidas(Configuracao.EXTENSAO_ARQUIVO_AUTORIZACAO_PACIENTE, configuracaoService));
		if (mensagem != null) {
			campos.add(new CampoMensagem(campo, mensagem));
		}
	}
	
	private static String validarExtensaoArquivoPorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, String[] extensoes) {
		String extensaoArquivoUploaded = ArquivoUtil.obterExtensaoArquivo(arquivo);
		boolean extensaoValida = Stream.of(extensoes).anyMatch(ext -> ext
				.equalsIgnoreCase(
						extensaoArquivoUploaded));
		if (!extensaoValida) {
			return messageSource.getMessage(
					"erro.arquivo.extensao.invalido",
					null,
					LocaleContextHolder.getLocale());
		}
		return null;
	}

	/**
	 * Método para obter as extensões validas.
	 * 
	 * @return
	 * @author Fillipe Queiroz
	 */
	private static String[] obterExtensoesValidas(String configuracaoId, IConfiguracaoService configuracaoService) {
		Configuracao configTamanhoMaximo = configuracaoService.obterConfiguracao(
				configuracaoId);
		String valor = configTamanhoMaximo.getValor();
		String[] tiposArquivos = valor.split(",");
		String[] extensoesValidas = new String[tiposArquivos.length];
		int contador = 0;
		for (String tipoArquivo : tiposArquivos) {
			extensoesValidas[contador++] = tipoArquivo.split("/")[1];
		}
		return extensoesValidas;
	}

	
	/**
	 * Monta o diretorio de armazenamento dos resultados de exame de workup.
	 * 
	 * @param idPedidoWorkup - id do pedido de exame de workup.
	 * @param idResultadoWorkup - id do resultado.
	 * @return caminho do diretorio do storage.
	 */
	public static String obterDiretorioArquivosPedidoWorkup(Long idPedidoWorkup) {
		return StorageUtil.DIRETORIO_PEDIDO + "/" + String.valueOf(idPedidoWorkup);
	}
	
	/**
	 * Monta o diretorio de armazenamento dos resultados de exame de workup.
	 * 
	 * @param idPedidoWorkup - id do pedido de exame de workup.
	 * @param idResultadoWorkup - id do resultado.
	 * @return caminho do diretorio do storage.
	 */
	public static String obterDiretorioArquivosPedidoTransporte(Long idPedidoTransporte) {
		return StorageUtil.DIRETORIO_PEDIDO_TRANSPORTE + "/" + String.valueOf(idPedidoTransporte);
	}
		
	/**
	 * Monta o diretorio de armazenamento dos resultados de exame de workup.
	 * 
	 * @param idPedidoWorkup - id do pedido de exame de workup.
	 * @param idResultadoWorkup - id do resultado.
	 * @return caminho do diretorio do storage.
	 */
	public static String obterDiretorioArquivosResultadoWorkup(Long idPedidoWorkup) {
		return StorageUtil.DIRETORIO_PEDIDO + "/" + String.valueOf(idPedidoWorkup) + "/" +
				StorageUtil.DIRETORIO_RESULTADO_WORKUP;
	}

	/**
	 * Monta o diretorio de armazenamento dos exames adicionais de workup.
	 * 
	 * @param idPedidoAdicional - id do pedido adicional de exame de workup.
	 * @return caminho do diretorio do storage.
	 */
	public static String obterDiretorioArquivosExamesAdicionaisWorkup(Long idPedidoAdicional) {
		return StorageUtil.DIRETORIO_PEDIDO + "/" + String.valueOf(idPedidoAdicional) + "/" +
				StorageUtil.DIRETORIO_PEDIDO_EXAME_ADICIONAL;
	}
	
	
	/**
	 * Monta o diretório de armazenamento das prescrições de um paciente.
	 * 
	 * @param idPrescricao
	 * @return caminho do diretório no storage
	 */
	public static String obterDiretorioArquivosPrescricao(Long idPrescricao) {
		return StorageUtil.DIRETORIO_PACIENTE_PRESCRICAO + "/" + String.valueOf(idPrescricao);
	}
	
	/**
	 * Monta o diretório de armazenamento dos voucher do pedido de logistica.
	 * 
	 * @param idPedidoLogistica
	 * @return caminho do diretório no storage
	 */
	public static String obterDiretorioArquivosVoucherPedidoLogisticaDoadorWorkup(Long idPedidoWorkup, Long idPedidoLogistica) {
		return  obterDiretorioArquivosPedidoWorkup(idPedidoWorkup) + "/" + StorageUtil.DIRETORIO_PEDIDO_LOGISTICA + "/" + String.valueOf(idPedidoLogistica) + "/" +
				StorageUtil.DIRETORIO_VOUCHER_PEDIDO_LOGISTICA;
	}
	
	/**
	 * Monta o diretório de armazenamento dos voucher do pedido de logistica.
	 * 
	 * @param idPedidoLogistica
	 * @return caminho do diretório no storage
	 */
	public static String obterDiretorioArquivosCntPedidoTransporte(Long idPedidoTransporte, Long idPedidoLogistica) {
		return  obterDiretorioArquivosPedidoTransporte(idPedidoTransporte) + "/" + StorageUtil.DIRETORIO_PEDIDO_TRANSPORTE + "/" + String.valueOf(idPedidoLogistica) + "/" +
				StorageUtil.DIRETORIO_CNT_PEDIDO_TRANSPORTE;
	}
	
	
	/**
	 * Método para validar arquivo quanto ao seu tamanho e sua extensão de acordo com a configuração do banco.
	 * 
	 * @param arquivo - arquivo a ser validado.
	 * @param resultadoValidacao - lista de mensagens de erro.
	 * @param messageSource - resource de mensagens para que sejam adicionadas as mensagens.
	 * @param extensaoDoArquivo - configuração de extensão do arquivo a ser validado.
	 * @param tamanahoDoArquivo - configuração do tamanho do arquivo.
	 */
	public static void validarArquivo(MultipartFile arquivo, ArrayList<CampoMensagem> resultadoValidacao,
			MessageSource messageSource, Configuracao extensaoDoArquivo, Configuracao tamanahoDoArquivo) {

		if (arquivo != null) {
			validarTamanhoNomeArquivo(arquivo.getOriginalFilename(), resultadoValidacao, messageSource);
			validarTamanhoArquivo(arquivo, messageSource, resultadoValidacao, Long.parseLong(tamanahoDoArquivo.getValor()));
			
			//String extensaoArquivoUploaded = ArquivoUtil.obterExtensaoArquivo(arquivo);
			String valorExtensaoValido = extensaoDoArquivo.getValor();
			validarExtensaoArquivoPorConfiguracao(arquivo, resultadoValidacao, messageSource, valorExtensaoValido.split("/"));

/*			if (!extensaoArquivoUploaded.equalsIgnoreCase(valorExtensaoValido.split("/")[1])) {
				resultadoValidacao.add(new CampoMensagem("exame", messageSource.getMessage(
						"erro.arquivo.extensao.invalido",
						null,
						LocaleContextHolder.getLocale())));
			} */
		}
	}

	private static void validarTamanhoArquivo(MultipartFile arquivo, MessageSource messageSource,
			ArrayList<CampoMensagem> resultadoValidacao, Long tamanhoMaximo) {
		Long tamanhoArquivo = arquivo.getSize();
		if (tamanhoArquivo > tamanhoMaximo) {
			String mensagem = AppUtil.getMensagem(messageSource, "erro.arquivo.tamanho.invalido", ArquivoUtil
					.converterBytesParaMegaBytes(tamanhoMaximo));
			resultadoValidacao.add(new CampoMensagem("arquivo", mensagem));

		}
	}
	
	private static void validarTamanhoNomeArquivo(String nomeArquivo,
			List<CampoMensagem> resultadoValidacao, MessageSource messageSource) {
		if (nomeArquivo.length() > TAMANHO_MAXIMO_NOME_ARQUIVO) {
			String mensagem = AppUtil.getMensagem(messageSource, "erro.arquivo.nome.invalido",
					TAMANHO_MAXIMO_NOME_ARQUIVO);
			resultadoValidacao.add(new CampoMensagem("arquivo", mensagem));
		}
	}
	
	

}
