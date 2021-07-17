package br.org.cancer.modred.persistence;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.gateway.sms.StatusSms;
import br.org.cancer.modred.vo.SmsVo;

/**
 * Interface para customização de persistencia para a entidade SmsEnviado.
 * 
 * @author brunosousa
 *
 */
public interface ISmsEnviadoRepositoryCustom {
	
	/**
	 * Listar os sms eviados podendo ser filtrado pelos parametros abaixo..
	 *  
	 * @param dmr - DMR do doador. 
	 * @param dataInicial - Data incial para a consulta
	 * @param dataFinal - Data Final da Consulta
	 * @param status - Status do envio
	 * @param paginacao - paginação
	 * @return Lista pagina com sms enviados.
	 */
	Page<SmsVo> listarSmsEniviados(Long dmr, LocalDate dataInicial, LocalDate dataFinal, StatusSms status, PageRequest paginacao);
	
}
