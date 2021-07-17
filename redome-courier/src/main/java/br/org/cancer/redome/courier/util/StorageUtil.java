package br.org.cancer.redome.courier.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StorageUtil {
	
	/*
	 * Variável que representa o diretório de armazenamento de pedido de workup.
	 */
	public static final String DIRETORIO_PEDIDO = "PEDIDO_WORKUP";

	/*
	 * Variável que representa o diretório de armazenamento do exame adicional de workup.
	 */
	public static final String DIRETORIO_PEDIDO_EXAME_ADICIONAL = "PEDIDO_EXAME_ADICIONAL";

	/**
	 * Variável que representa o diretório de armazenamento de resultado de workup.
	 */
	public static final String DIRETORIO_RESULTADO_WORKUP = "RESULTADO";
	
	/**
	 * Variável que representa o diretório de armazenamento das prescrições do paciente.
	 */
	public static final String DIRETORIO_PACIENTE_PRESCRICAO = "PRESCRICOES";
	
	/**
	 * Variável que representa o diretório de armazenamento de pedido de logistica.
	 */
	public static String DIRETORIO_PEDIDO_LOGISTICA = "PEDIDO_LOGISTICA";
	
	/**
	 * Variável que representa o diretório de armazenamento de pedido de logistica.
	 */
	public static String DIRETORIO_VOUCHER_PEDIDO_LOGISTICA = "VOUCHER";
	
	/**
	 * Variável que representa o diretório de armazenamento de pedido de transporte.
	 */
	public static String DIRETORIO_PEDIDO_TRANSPORTE = "PEDIDO_TRANSPORTE";

	/**
	 * Variável que representa o diretório de armazenamento de pedido de logistica.
	 */
	public static String DIRETORIO_CNT_PEDIDO_TRANSPORTE = "CNT";


}
