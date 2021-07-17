package br.org.cancer.modred.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.gateway.sms.GatewayBuilder;
import br.org.cancer.modred.gateway.sms.GlobalGatewaySms;
import br.org.cancer.modred.gateway.sms.IRetornoSms;
import br.org.cancer.modred.gateway.sms.StatusSms;
import br.org.cancer.modred.model.SmsEnviado;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.ISmsEnviadoRepository;
import br.org.cancer.modred.service.IPedidoContatoSmsService;
import br.org.cancer.modred.service.ISmsEnviadoService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.vo.SmsVo;
																					 
/**
 * Classe que implementa a interface de negócias para entidade municipio.
 * 
 * @author brunosousa
 *
 */
@Service
@Transactional
public class SmsEnviadoService extends AbstractService<SmsEnviado, Long> implements ISmsEnviadoService {
	
	@Autowired
	private ISmsEnviadoRepository smsEnviadoRepository;
	
	@Autowired
	private IPedidoContatoSmsService pedidoContatoSmsService;
	
	@Override
	public IRepository<SmsEnviado, Long> getRepository() {
		return smsEnviadoRepository;
	}
	
	@Override
	public Page<SmsVo> listarsmsEnviados(Long dmr, LocalDate dataInicial, LocalDate dataFinal, StatusSms status,
			PageRequest paginacao) {
	
		return smsEnviadoRepository.listarSmsEniviados(dmr, dataInicial, dataFinal, status, paginacao); 
	}
	
	private SmsEnviado obterSmsEnviadoPorId(Long id) {
		SmsEnviado smsEnviado = findById(id);
		if (smsEnviado == null) {
			throw new BusinessException("");
		}
		return smsEnviado;
	}

	@Override
	public void atualizarStatus(SmsEnviado smsEnviado) {

		smsEnviado.setStatusSms(obterStatusSmsEnviado(smsEnviado.getIdentificacaoGatewaySms()));
		
		save(smsEnviado);
		
	}
	
	private StatusSms obterStatusSmsEnviado(String identificador) {
		IRetornoSms retornoSms;
		try {
			retornoSms = GatewayBuilder.build(GlobalGatewaySms.class).status(identificador);
		} 
		catch (InstantiationException | IllegalAccessException | IOException | URISyntaxException e) {
			throw new BusinessException("erro.interno");
		}
		return retornoSms.getStatus();
	}
	

}
