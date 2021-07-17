package br.org.cancer.modred.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.DoadorNaoContactado;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IDoadorNaoContactadoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IDoadorNaoContactadoService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade DoadorNaoContactado.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class DoadorNaoContactadoService extends AbstractLoggingService<DoadorNaoContactado, Long> implements IDoadorNaoContactadoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DoadorNaoContactadoService.class);
	
	@Autowired
	private IDoadorNaoContactadoRepository doadorNaoContactadoRepository;

	@Override
	public IRepository<DoadorNaoContactado, Long> getRepository() {
		return doadorNaoContactadoRepository;
	}

	@Override
	public Paciente obterPaciente(DoadorNaoContactado entity) {
		return null;
	}

	@Override
	public String[] obterParametros(DoadorNaoContactado entity) {
		return new String[] {};
	}

	@Override
	public void criarDoadorNaoContactado(PedidoContato pedidoContato) {
		
		DoadorNaoContactado doadorNaoContactado = new DoadorNaoContactado(pedidoContato);
		save(doadorNaoContactado);
		
		criarTarefaDoadorNaoContactado(doadorNaoContactado);
		
	}
	
	private void criarTarefaDoadorNaoContactado(DoadorNaoContactado doadorNaoContactado) {
		
		Paciente paciente = doadorNaoContactado.getPedidoContato().getSolicitacao().getMatch().getBusca().getPaciente();
		
		TiposTarefa.DOADOR_NAO_CONTACTADO.getConfiguracao().criarTarefa()
			.comObjetoRelacionado(doadorNaoContactado.getId())
			.comRmr(paciente.getRmr())
			.apply();
		
	}
	
	
}
