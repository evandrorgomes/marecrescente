package br.org.cancer.modred.service;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.gateway.sms.StatusSms;
import br.org.cancer.modred.model.SmsEnviado;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.vo.SmsVo;

/**
 * Interface de serviçoes para a entidade SmsEnviado.
 * 
 * @author brunosousa
 *
 */
public interface ISmsEnviadoService extends IService<SmsEnviado, Long> {
	
	Page<SmsVo> listarsmsEnviados(Long dmr, LocalDate dataInicial, LocalDate dataFinal, StatusSms status, PageRequest paginacao);

	void atualizarStatus(SmsEnviado smsEnviado);

}
