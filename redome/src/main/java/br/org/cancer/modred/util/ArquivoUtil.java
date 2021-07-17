package br.org.cancer.modred.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.model.ArquivoEvolucao;
import br.org.cancer.modred.model.ArquivoExame;
import br.org.cancer.modred.model.ArquivoPrescricao;
import br.org.cancer.modred.model.ArquivoVoucherLogistica;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.service.IConfiguracaoService;
import br.org.cancer.modred.service.impl.StorageService;

/**
 * Classe utilitária para auxiliar na manipulação de arquivos.
 * 
 * @author ergomes
 *
 */
public class ArquivoUtil {

	/**
	 * Variável que representa o diretório de armazenamento do usuario.
	 */
	public static String DIRETORIO_USUARIO = "USUARIO";

	/**
	 * Variável que representa o diretório de armazenamento dos exames do paciente no storage do usuario para o draft.
	 */
	public static String DIRETORIO_RASCUNHO = "RASCUNHO";
	
	public static String obterCaminhoArquivoRascunho(Long usuarioId, String nomeArquivo) {

//		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		
		return String.join("sr-vmlxapd23.inca.local/nfs-dev/racher-dev/", DIRETORIO_USUARIO, usuarioId.toString(), DIRETORIO_RASCUNHO, nomeArquivo);
	}
	
	
	/* ################################################################################################ */
	
	
	
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

	/**
	 * Valida os arquivos dos exames.
	 * 
	 * @param listaArquivos de arquivos
	 * @param messageSource do spring
	 * @param configuracaoService configuracao service
	 */
	public static void validarArquivoExame(List<MultipartFile> listaArquivos,
			MessageSource messageSource, IConfiguracaoService configuracaoService) {

		List<CampoMensagem> campos = new ArrayList<CampoMensagem>();
		validarArquivoLaudoNulo(listaArquivos, campos, messageSource);
		if (Optional.ofNullable(listaArquivos).isPresent()) {
			validarQuantidadeArquivoLaudoPorConfiguracao(listaArquivos, campos, messageSource, configuracaoService);
			listaArquivos.forEach(arquivo -> {
				validarTamanhoArquivoLaudoPorConfiguracao(arquivo, campos, messageSource, configuracaoService);
				validarExtensaoArquivoLaudoPorConfiguracao(arquivo, campos, messageSource, configuracaoService);
				validarTamanhoNomeArquivoLaudo(arquivo.getOriginalFilename(), campos, messageSource);
			});
		}

		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}

	}


	/**
	 * Valida os arquivos dos exames.
	 * 
	 * @param listaArquivos de arquivos
	 * @param messageSource do spring
	 * @param configuracaoService configuracao service
	 */
	public static void validarArquivoEvolucao(List<MultipartFile> listaArquivos,
			MessageSource messageSource, IConfiguracaoService configuracaoService) {

		List<CampoMensagem> campos = new ArrayList<CampoMensagem>();
		validarArquivoLaudoNulo(listaArquivos, campos, messageSource);
		if (Optional.ofNullable(listaArquivos).isPresent()) {
			validarQuantidadeArquivoLaudoPorConfiguracao(listaArquivos, campos, messageSource, configuracaoService);
			listaArquivos.forEach(arquivo -> {
				validarTamanhoArquivoEvolucaoPorConfiguracao(arquivo, campos, messageSource, configuracaoService);
				validarExtensaoArquivoEvolucaoPorConfiguracao(arquivo, campos, messageSource, configuracaoService);
				validarTamanhoNomeArquivoEvolucao(arquivo.getOriginalFilename(), campos, messageSource);
			});
		}

		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}

	}


	/**
	 * Valida os arquivos de voucher.
	 * 
	 * @param arquivo - arquivo a ser validado
	 * @param messageSource do spring
	 * @param configuracaoService configuracao service
	 */
	public static void validarArquivoVoucher(MultipartFile arquivo,
			MessageSource messageSource, IConfiguracaoService configuracaoService) {

		List<CampoMensagem> campos = new ArrayList<CampoMensagem>();
		validarArquivoVoucherNulo(arquivo, campos, messageSource);
		if (Optional.ofNullable(arquivo).isPresent()) {
			validarTamanhoArquivoVoucherPorConfiguracao(arquivo, campos, messageSource, configuracaoService);
			validarExtensaoArquivoVoucherPorConfiguracao(arquivo, campos, messageSource, configuracaoService);
			validarTamanhoNomeArquivoVoucher(arquivo.getOriginalFilename(), campos, messageSource);

		}

		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}

	}

	/**
	 * Valida os arquivos da prescrição.
	 * 
	 * @param listaArquivos de arquivos
	 * @param messageSource do spring
	 * @param configuracaoService configuracao service
	 */
	public static void validarArquivosPrescricao(List<MultipartFile> listaArquivos,
			MessageSource messageSource, IConfiguracaoService configuracaoService) {

		List<CampoMensagem> campos = new ArrayList<CampoMensagem>();
		//validarArquivoPrescricaoNulo(listaArquivos, campos, messageSource);
		if (Optional.ofNullable(listaArquivos).isPresent()) {
			validarQuantidadeArquivoPrescricaoPorConfiguracao(listaArquivos, campos, messageSource, configuracaoService);
			listaArquivos.forEach(arquivo -> {
				validarTamanhoArquivoPrescricaoPorConfiguracao(arquivo, campos, messageSource, configuracaoService, "arquivo");
				validarExtensaoArquivoPrescricaoPorConfiguracao(arquivo, campos, messageSource, configuracaoService, "arquivo");
				validarTamanhoNomeArquivoPrescricao(arquivo.getOriginalFilename(), campos, messageSource, "arquivo");
			});
		}

		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}

	}
	
	/**
	 * Valida o arquivo de justificativa da prescrição.
	 * 
	 * @param arquivo arquivo para validação
	 * @param messageSource do spring
	 * @param configuracaoService configuracao service
	 */
	public static void validarArquivoJustificativaPrescricao(MultipartFile arquivo,
			MessageSource messageSource, IConfiguracaoService configuracaoService) {

		List<CampoMensagem> campos = new ArrayList<CampoMensagem>();
		if (arquivo == null) {
			campos.add(new CampoMensagem("arquivoJustificativa", AppUtil.getMensagem(messageSource,
					"erro.arquivo.nulo")));
		}
		else {			
			validarTamanhoArquivoPrescricaoPorConfiguracao(arquivo, campos, messageSource, configuracaoService, "arquivoJustificativa");
			validarExtensaoArquivoPrescricaoPorConfiguracao(arquivo, campos, messageSource, configuracaoService, "arquivoJustificativa");
			validarTamanhoNomeArquivoPrescricao(arquivo.getOriginalFilename(), campos, messageSource, "arquivoJustificativa");
		}

		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}

	}
	
	/**
	 * Valida o arquivo de Autorização do paciente.
	 * 
	 * @param arquivo arquivo para validação
	 * @param messageSource do spring
	 * @param configuracaoService configuracao service
	 */
	public static void validarArquivoAutorizacaoPaciente(MultipartFile arquivo,
			MessageSource messageSource, IConfiguracaoService configuracaoService) {

		List<CampoMensagem> campos = new ArrayList<CampoMensagem>();
		if (arquivo == null) {
			campos.add(new CampoMensagem("arquivoAutorizacaoPaciente", AppUtil.getMensagem(messageSource,
					"erro.arquivo.nulo")));
		}
		else {			
			validarTamanhoArquivoAutorizacaoPacientePorConfiguracao(arquivo, campos, messageSource, configuracaoService, "arquivoJustificativa");
			validarExtensaoArquivoAutorizacaoPacientePorConfiguracao(arquivo, campos, messageSource, configuracaoService, "arquivoJustificativa");
			validarTamanhoNomeArquivoAutorizacaoPaciente(arquivo.getOriginalFilename(), campos, messageSource, "arquivoJustificativa");
		}

		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}

	}
		
	private static void validarArquivoLaudoNulo(List<MultipartFile> listaArquivos,
			List<CampoMensagem> campos, MessageSource messageSource) {
		if (listaArquivos == null || listaArquivos.isEmpty()) {
			campos.add(new CampoMensagem("exame", AppUtil.getMensagem(messageSource,
					"erro.arquivo.nulo")));
		}
	}


	private static void validarArquivoVoucherNulo(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource) {
		if (arquivo == null) {
			campos.add(new CampoMensagem("voucher", AppUtil.getMensagem(messageSource,
					"erro.arquivo.nulo")));
		}
	}

	private static void validarArquivoPrescricaoNulo(List<MultipartFile> listaArquivos,
			List<CampoMensagem> campos, MessageSource messageSource) {
		if (listaArquivos == null || listaArquivos.isEmpty()) {
			campos.add(new CampoMensagem("arquivo", AppUtil.getMensagem(messageSource,
					"erro.arquivo.nulo")));
		}
	}

	private static void validarQuantidadeArquivoLaudoPorConfiguracao(List<MultipartFile> listaArquivos,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService) {
		int quantidadeMaximoArquivos = Integer.valueOf(configuracaoService.obterConfiguracao(
				Configuracao.QUANTIDADE_ARQUIVO_LAUDO).getValor());
		if (listaArquivos.size() > quantidadeMaximoArquivos) {
			String mensagem = AppUtil.getMensagem(messageSource,
					"erro.arquivo.quantidade.invalido", quantidadeMaximoArquivos);
			campos.add(new CampoMensagem("exame", mensagem));
		}
	}
	

	private static void validarQuantidadeArquivoPrescricaoPorConfiguracao(List<MultipartFile> listaArquivos,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService) {
		int quantidadeMaximoArquivos = Integer.valueOf(configuracaoService.obterConfiguracao(
				Configuracao.QUANTIDADE_ARQUIVO_PRESCRICAO).getValor());
		if (listaArquivos.size() > quantidadeMaximoArquivos) {
			String mensagem = AppUtil.getMensagem(messageSource,
					"erro.arquivo.quantidade.invalido_comum", quantidadeMaximoArquivos);
			campos.add(new CampoMensagem("arquivo", mensagem));
		}
	}

	private static void validarQuantidadeArquivoEvolucaoPorConfiguracao(List<MultipartFile> listaArquivos,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService) {
		int quantidadeMaximoArquivos = Integer.valueOf(configuracaoService.obterConfiguracao(
				Configuracao.QUANTIDADE_MAXIMA_ARQUIVOS_EVOLUCAO_PACIENTE).getValor());
		if (listaArquivos.size() > quantidadeMaximoArquivos) {
			String mensagem = AppUtil.getMensagem(messageSource,
					"erro.arquivo.quantidade.invalido", quantidadeMaximoArquivos);
			campos.add(new CampoMensagem("evolucao", mensagem));
		}
	}

	
	
	private static void validarTamanhoArquivoLaudoPorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService) {
		if (arquivo != null) {
			Configuracao configTamanhoMaximo = configuracaoService.obterConfiguracao(
					Configuracao.TAMANHO_MAXIMO_LAUDO);
			String mensagem = validarTamanhoArquivoPorConfiguracao(arquivo, messageSource, configTamanhoMaximo);
			if (mensagem != null) {
				campos.add(new CampoMensagem("exame", mensagem));
			}
		}
	}

	private static void validarTamanhoArquivoVoucherPorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService) {
		if (arquivo != null) {
			Configuracao configTamanhoMaximo = configuracaoService.obterConfiguracao(
					Configuracao.TAMANHO_MAXIMO_VOUCHER);
			String mensagem = validarTamanhoArquivoPorConfiguracao(arquivo, messageSource, configTamanhoMaximo);
			if (mensagem != null) {
				campos.add(new CampoMensagem("arquivo", mensagem));
			}
		}
	}

	private static void validarTamanhoArquivoPrescricaoPorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService, String campo) {
		if (arquivo != null) {
			Configuracao configTamanhoMaximo = configuracaoService.obterConfiguracao(
					Configuracao.TAMANHO_MAXIMO_PRESCRICAO);
			String mensagem = validarTamanhoArquivoPorConfiguracao(arquivo, messageSource, configTamanhoMaximo);
			if (mensagem != null) {
				campos.add(new CampoMensagem(campo, mensagem));
			}
		}
	}
	
	private static void validarTamanhoArquivoAutorizacaoPacientePorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService, String campo) {
		if (arquivo != null) {
			Configuracao configTamanhoMaximo = configuracaoService.obterConfiguracao(
					Configuracao.TAMANHO_MAXIMO_AUTORIZACAO_PACIENTE);
			String mensagem = validarTamanhoArquivoPorConfiguracao(arquivo, messageSource, configTamanhoMaximo);
			if (mensagem != null) {
				campos.add(new CampoMensagem(campo, mensagem));
			}
		}
	}
	
	private static void validarTamanhoArquivoEvolucaoPorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService) {
		if (arquivo != null) {
			Configuracao configTamanhoMaximo = configuracaoService.obterConfiguracao(
					Configuracao.TAMANHO_MAXIMO_EVOLUCAO_PACIENTE);
			String mensagem = validarTamanhoArquivoPorConfiguracao(arquivo, messageSource, configTamanhoMaximo);
			if (mensagem != null) {
				campos.add(new CampoMensagem("evolucao", mensagem));
			}
		}
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
	
	private static void validarExtensaoArquivoLaudoPorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService) {
		String mensagem = validarExtensaoArquivoPorConfiguracao(arquivo, campos, messageSource,
				obterExtensoesValidas(Configuracao.EXTENSAO_ARQUIVO_LAUDO, configuracaoService));
		if (mensagem != null) {
			campos.add(new CampoMensagem("exame", mensagem));
		}
	}

	private static void validarExtensaoArquivoVoucherPorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService) {
		String mensagem = validarExtensaoArquivoPorConfiguracao(arquivo, campos, messageSource,
				obterExtensoesValidas(Configuracao.EXTENSAO_ARQUIVO_VOUCHER, configuracaoService));
		if (mensagem != null) {
			campos.add(new CampoMensagem("voucher", mensagem));
		}
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
	
	private static void validarExtensaoArquivoEvolucaoPorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService) {
		String mensagem = validarExtensaoArquivoPorConfiguracao(arquivo, campos, messageSource,
				obterExtensoesValidas(Configuracao.EXTENSAO_ARQUIVO_EVOLUCAO_PACIENTE, configuracaoService));
		if (mensagem != null) {
			campos.add(new CampoMensagem("exame", mensagem));
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

	private static void validarTamanhoNomeArquivoLaudo(String nomeArquivo,
			List<CampoMensagem> campos, MessageSource messageSource) {
		if (nomeArquivo.length() > ArquivoExame.TAMANHO_MAXIMO_NOME_ARQUIVO) {
			String mensagem = AppUtil.getMensagem(messageSource, "erro.arquivo.nome.invalido",
					ArquivoExame.TAMANHO_MAXIMO_NOME_ARQUIVO);
			campos.add(new CampoMensagem("exame", mensagem));
		}
	}
	
	private static void validarTamanhoNomeArquivoEvolucao(String nomeArquivo,
			List<CampoMensagem> campos, MessageSource messageSource) {
		if (nomeArquivo.length() > ArquivoEvolucao.TAMANHO_MAXIMO_NOME_ARQUIVO) {
			String mensagem = AppUtil.getMensagem(messageSource, "erro.arquivo.nome.invalido",
					ArquivoEvolucao.TAMANHO_MAXIMO_NOME_ARQUIVO);
			campos.add(new CampoMensagem("evolucao", mensagem));
		}
	}

	private static void validarTamanhoNomeArquivoVoucher(String nomeArquivo,
			List<CampoMensagem> campos, MessageSource messageSource) {
		if (nomeArquivo.length() > ArquivoVoucherLogistica.TAMANHO_MAXIMO_NOME_ARQUIVO) {
			String mensagem = AppUtil.getMensagem(messageSource, "erro.arquivo.nome.invalido",
					ArquivoVoucherLogistica.TAMANHO_MAXIMO_NOME_ARQUIVO);
			campos.add(new CampoMensagem("voucher", mensagem));
		}
	}

	private static void validarTamanhoNomeArquivoPrescricao(String nomeArquivo,
			List<CampoMensagem> campos, MessageSource messageSource, String campo) {
		if (nomeArquivo.length() > ArquivoPrescricao.TAMANHO_MAXIMO_NOME_ARQUIVO) {
			String mensagem = AppUtil.getMensagem(messageSource, "erro.arquivo.nome.invalido",
					ArquivoPrescricao.TAMANHO_MAXIMO_NOME_ARQUIVO);
			campos.add(new CampoMensagem(campo, mensagem));
		}
	}
	
	private static void validarTamanhoNomeArquivoAutorizacaoPaciente(String nomeArquivo,
			List<CampoMensagem> campos, MessageSource messageSource, String campo) {
		if (nomeArquivo.length() > ArquivoPrescricao.TAMANHO_MAXIMO_NOME_ARQUIVO) {
			String mensagem = AppUtil.getMensagem(messageSource, "erro.arquivo.nome.invalido",
					ArquivoPrescricao.TAMANHO_MAXIMO_NOME_ARQUIVO);
			campos.add(new CampoMensagem(campo, mensagem));
		}
	}

	/**
	 * Monta o diretório onde os arquivos do paciente serão gravados no Storage.
	 * 
	 * @param arquivo referência para o arquivo carregado no upload.
	 * @param rmr Código identificador de paciente.
	 * @param diretorioExame Diretório do exame.
	 * @return o caminho completo para os arquivos do paciente informado.
	 */
	public static String obterDiretorioArquivosLaudoPorPaciente(MultipartFile arquivo, long rmr,
			String diretorioExame) {
		return StorageService.DIRETORIO_PACIENTE_STORAGE + "/" + String.valueOf(rmr) + "/"
				+ StorageService.DIRETORIO_PACIENTE_EXAME_STORAGE + "/" + diretorioExame;
	}
	
	
	/**
	 * Monta o diretório onde os arquivos do paciente serão gravados no Storage.
	 * 
	 * @param arquivo referência para o arquivo carregado no upload.
	 * @param rmr Código identificador de paciente.
	 * @param diretorioEvolucao Diretório da evolucao.
	 * @return o caminho completo para os arquivos do paciente informado.
	 */
	public static String obterDiretorioArquivosEvolucaoPorPaciente(MultipartFile arquivo, long rmr,
			String diretorioExame) {
		return StorageService.DIRETORIO_PACIENTE_STORAGE + "/" + String.valueOf(rmr) + "/"
				+ StorageService.DIRETORIO_PACIENTE_EVOLUCAO_STORAGE + "/" + diretorioExame;
	}

	/**
	 * Monta o diretorio de armazenamento dos resultados de exame de workup.
	 * 
	 * @param idPedidoWorkup - id do pedido de exame de workup.
	 * @param idResultadoWorkup - id do resultado.
	 * @return caminho do diretorio do storage.
	 */
	public static String obterDiretorioArquivosResultadoWorkup(Long idPedidoWorkup, Long idResultadoWorkup) {
		return StorageService.DIRETORIO_PEDIDO + "/" + String.valueOf(idPedidoWorkup) + "/" +
				StorageService.DIRETORIO_RESULTADO_WORKUP + "/" + String.valueOf(idResultadoWorkup);
	}

	/**
	 * Monta o diretório de armazenamento dos voucher do pedido de logistica.
	 * 
	 * @param idPedidoLogistica
	 * @return caminho do diretório no storage
	 */
	public static String obterDiretorioArquivosVoucherPedidoLogistica(Long idPedidoLogistica) {
		return StorageService.DIRETORIO_PEDIDO_LOGISTICA + "/" + String.valueOf(idPedidoLogistica) + "/" +
				StorageService.DIRETORIO_VOUCHER_PEDIDO_LOGISTICA;
	}

	
	
	/**
	 * Monta o diretório de armazenamento dos voucher do pedido de logistica.
	 * 
	 * @param idPedidoLogistica
	 * @return caminho do diretório no storage
	 */
	public static String obterDiretorioAvaliacaoCamaraTecnica(Long idCamaraTecnica) {
		return StorageService.DIRETORIO_AVALIACAO_CAMARA_TECNICA + "/" + String.valueOf(idCamaraTecnica);
	}

	/**
	 * Monta o diretório de armazenamento das prescrições de um paciente.
	 * 
	 * @param idPrescricao
	 * @return caminho do diretório no storage
	 */
	public static String obterDiretorioArquivosPrescricao(Long idPrescricao) {
		return StorageService.DIRETORIO_PACIENTE_PRESCRICAO + "/" + String.valueOf(idPrescricao);
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
			validarTamanhoArquivo(arquivo, messageSource, resultadoValidacao, Long.parseLong(tamanahoDoArquivo.getValor()));

			String extensaoArquivoUploaded = ArquivoUtil.obterExtensaoArquivo(arquivo);
			String valorExtensaoValido = extensaoDoArquivo.getValor();

			if (!extensaoArquivoUploaded.equalsIgnoreCase(valorExtensaoValido.split("/")[1])) {
				resultadoValidacao.add(new CampoMensagem("exame", messageSource.getMessage(
						"erro.arquivo.extensao.invalido",
						null,
						LocaleContextHolder.getLocale())));
			}
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
	
	
	/**
	 * Valida o arquivo de Autorização do paciente.
	 * 
	 * @param arquivo arquivo para validação
	 * @param messageSource do spring
	 * @param configuracaoService configuracao service
	 */
	public static void validarArquivoPedidoTransporte(MultipartFile arquivo,
			MessageSource messageSource, IConfiguracaoService configuracaoService) {

		List<CampoMensagem> campos = new ArrayList<CampoMensagem>();
					
		validarTamanhoArquivoPedidoTransportePorConfiguracao(arquivo, campos, messageSource, configuracaoService, "arquivoPedidoTransporte");
		validarExtensaoArquivoPedidoTransportePorConfiguracao(arquivo, campos, messageSource, configuracaoService, "arquivoPedidoTransporte");
				
		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}

	}
	
	private static void validarTamanhoArquivoPedidoTransportePorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService, String campo) {
		if (arquivo != null) {
			Configuracao configTamanhoMaximo = configuracaoService.obterConfiguracao(
					Configuracao.TAMANHO_ARQUIVO_PEDIDO_TRANSPORTE);
			String mensagem = validarTamanhoArquivoPorConfiguracao(arquivo, messageSource, configTamanhoMaximo);
			if (mensagem != null) {
				campos.add(new CampoMensagem(campo, mensagem));
			}
		}
	}
	
	private static void validarExtensaoArquivoPedidoTransportePorConfiguracao(MultipartFile arquivo,
			List<CampoMensagem> campos, MessageSource messageSource, IConfiguracaoService configuracaoService, String campo) {
		String mensagem = validarExtensaoArquivoPorConfiguracao(arquivo, campos, messageSource,
				obterExtensoesValidas(Configuracao.EXTENSAO_ARQUIVO_PEDIDO_TRANSPORTE, configuracaoService));
		if (mensagem != null) {
			campos.add(new CampoMensagem(campo, mensagem));
		}
	}
	

}
