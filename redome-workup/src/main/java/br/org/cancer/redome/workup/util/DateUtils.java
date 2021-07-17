
package br.org.cancer.redome.workup.util;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import br.org.cancer.redome.workup.configuration.ApplicationContextProvider;
import br.org.cancer.redome.workup.configuration.RedomeWorkupApplication;

/**
 * @author Pizão
 * 
 * Classe para tratamento de datas no sistema.
 *
 */
public class DateUtils {

	private static final DateTimeFormatter FORMATTER_DD_MM_YY = DateTimeFormatter.ofPattern("dd/MM/yy",
			RedomeWorkupApplication.BRASIL_LOCALE);
	private static final DateTimeFormatter FORMATTER_DD_MM_YYYY = DateTimeFormatter.ofPattern("dd/MM/yyyy",
			RedomeWorkupApplication.BRASIL_LOCALE);
	private static final DateTimeFormatter FORMATTER_DD_MM_YY_HH_MM = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm",
			RedomeWorkupApplication.BRASIL_LOCALE);

	/**
	 * Formata a data para exibição de acordo com a proximidade da mesma. Para isso, cria representações de texto para informar a
	 * data. Ex.: Hoje, Ontem, 1 dia, 2 dias.
	 * 
	 * @param data que será formatada.
	 * @return data já no formato para exibição.
	 */
	public static String obterAging(LocalDate data) {

		if (data == null) {
			return "";
		}
		long dias = 0;
		final ReloadableResourceBundleMessageSource messageSource = (ReloadableResourceBundleMessageSource) ApplicationContextProvider
				.obterBean("messageSource");

		if (data.compareTo(LocalDate.now()) < 0) {
			dias = DAYS.between(data, LocalDate.now());
			return calculaAgingDataPassada(dias, messageSource);
		}
		else {
			dias = DAYS.between(LocalDate.now(), data);
			return calculaAgingDataFutura(dias, messageSource);

		}

	}
	
	/**
	 * Formata a data para exibição de acordo com a proximidade da mesma. Para isso, cria representações de texto para informar a
	 * data. Ex.: Hoje, Ontem, 1 dia, 2 dias.
	 * 
	 * @param data que será formatada.
	 * @return data já no formato para exibição.
	 */
	public static String obterAging(LocalDateTime data) {
		if(data == null){
			return null;
		}
			
		return obterAging(data.toLocalDate());
	}

	private static String calculaAgingDataFutura(Long dias, ReloadableResourceBundleMessageSource messageSource) {
		if (dias == 0) {
			return AppUtil.getMensagem(messageSource, "hoje");
		}
		else
			if (dias == 1) {
				return AppUtil.getMensagem(messageSource, "amanha");
			}
			else {
				return dias + " " + AppUtil.getMensagem(messageSource, "dias");
			}

	}

	private static String calculaAgingDataPassada(Long dias, ReloadableResourceBundleMessageSource messageSource) {
		if (dias == 0) {
			return AppUtil.getMensagem(messageSource, "hoje");
		}
		else
			if (dias == 1) {
				return AppUtil.getMensagem(messageSource, "ontem");
			}
			else {
				return dias + " " + AppUtil.getMensagem(messageSource, "dias");
			}

	}

	/**
	 * Método para formatar uma data do tipo LocalDate em dd/MM/yy.
	 * 
	 * @param data data a ser formatada
	 * @return data formatada
	 */
	public static String getDataFormatadaSemHora(LocalDate data) {
		if (data != null) {
			return data.format(DateUtils.FORMATTER_DD_MM_YY);
		}
		return null;
	}

	/**
	 * Método para formatar uma data do tipo LocalDate em dd/MM/yy.
	 * 
	 * @param data data a ser formatada
	 * @return data formatada
	 */
	public static String getDataFormatadaSemHoraComAnoCompleto(LocalDate data) {
		if (data != null) {
			return data.format(DateUtils.FORMATTER_DD_MM_YYYY);
		}
		return null;
	}
	
	/**
	 * Método para formatar uma data do tipo LocalDateTime em dd/MM/yy HH:mm.
	 * 
	 * @param data data a ser formatada
	 * @return data formatada
	 */
	public static String getDataFormatadaComHoraEMinutos(LocalDateTime data) {
		if (data != null) {
			return data.format(DateUtils.FORMATTER_DD_MM_YY_HH_MM);
		}
		return null;
	}

	/**
	 * Método para formatar uma data do tipo LocalDateTime em dd/MM/yy HH:mm.
	 * 
	 * @param data data a ser formatada
	 * @return data formatada
	 */
	public static String getLocalDateTimeFormatadaSemHoraEMinutos(LocalDateTime data) {
		if (data != null) {
			return data.format(DateUtils.FORMATTER_DD_MM_YY);
		}
		return null;
	}

	/**
	 * Método para converter data util para LocalDate.
	 * 
	 * @param date - data para ser convertida.
	 * @return data convertida.
	 */
	public static LocalDate converterParaLocalDate(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	}
	
	/**
	 * Método para converter data util para LocalDateTime.
	 * 
	 * @param dateTime - data para ser convertida.
	 * @return data convertida.
	 */
	public static LocalDateTime converterParaLocalDateTime(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	}
	
	/**
	 * Calcula o prazo, em dias, para a expiração de acordo com os dados informados.
	 * O cálculo de expiração do prazo deve, a princípio, desconsiderar os fins de semana.
	 * 
	 * @param dataPartida considerada a data de início da item que está sendo dado o prazo.
	 * @param prazoEmDias quantidade de dias para realização do item.
	 * @return quantidade, em dias, restante para expiração.
	 */
	public static Long calcularExpiracaoPrazo(LocalDate dataPartida, Long prazoEmDias){
		return calcularExpiracaoPrazo(dataPartida.plusDays(prazoEmDias));
	}
	
	/**
	 * Calcula o prazo, em dias, para a expiração de acordo com os dados informados.
	 * O cálculo de expiração do prazo deve, a princípio, desconsiderar os fins de semana.
	 * 
	 * @param prazo 
	 * @return quantidade, em dias, restante para expiração.
	 */
	public static Long calcularExpiracaoPrazo(LocalDate prazo){
		if(prazo == null){
			return null;
		}
		final LocalDate hoje = LocalDate.now();
		return DAYS.between(hoje, obterProximoDiaUtil(prazo));
	}
	
	/**
	 * Retorna o próximo dia útil a partir da data informada.
	 * 
	 * @param dataPartida data de partida para procura do próximo dia útil.
	 * Se esta for uma data útil, ela mesmo é o retorno do método.
	 * @return próximo dia útil.
	 */
	public static LocalDate obterProximoDiaUtil(LocalDate dataPartida){
		if(dataPartida.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
			return dataPartida.plusDays(1L);
		}
		else if(dataPartida.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
			return dataPartida.plusDays(2L);
		}
		return dataPartida;
	}
	
    public static Integer calcularIdade(LocalDate dataNascimento) {
    	 LocalDate dataAtual = LocalDate.now();
    	 Period periodo = Period.between(dataNascimento, dataAtual);
    	 return periodo.getYears();      
    }

}