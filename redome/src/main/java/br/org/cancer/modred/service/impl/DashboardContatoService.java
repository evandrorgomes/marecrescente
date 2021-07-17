package br.org.cancer.modred.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.persistence.IDashboardContatoRepository;
import br.org.cancer.modred.service.IDashboardContatoService;
import br.org.cancer.modred.vo.dashboard.ContatoVo;

/**
 * Implementação do serviço para o dashboard de contato.
 * 
 * @author brunosousa
 *
 */
@Service
@Transactional(readOnly= true)
public class DashboardContatoService implements IDashboardContatoService {

	@Autowired
	private IDashboardContatoRepository dashboardContatoRepository;
	
	@Override
	public ContatoVo obterTotaisPorPeriodo(LocalDate dataInicio, LocalDate dataFinal) {
		
		ContatoVo contatoVo = dashboardContatoRepository.obterTotaisContato(dataInicio, dataFinal);
		contatoVo.setDetalhes(dashboardContatoRepository.listaDetalhesContatoPorPeriodo(dataInicio, dataFinal));
		
		return contatoVo;
	}
	
	
	

}
