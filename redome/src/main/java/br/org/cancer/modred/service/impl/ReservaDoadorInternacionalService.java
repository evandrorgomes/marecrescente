package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.ReservaDoadorInternacional;
import br.org.cancer.modred.model.domain.StatusReservaDoadorInternacional;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IReservaDoadorInternacionalRepository;
import br.org.cancer.modred.service.IReservaDoadorInternacionalService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de funcionalidades envolvendo a entidade ReservaDoadorInternacional.
 * 
 * @author Pizão
 *
 */
@Service
@Transactional
public class ReservaDoadorInternacionalService extends AbstractService<ReservaDoadorInternacional, Long> 
				implements IReservaDoadorInternacionalService {

	@Autowired
	private IReservaDoadorInternacionalRepository reservaDoadorRepositorio; 
	
	@Override
	public IRepository<ReservaDoadorInternacional, Long> getRepository() {
		return reservaDoadorRepositorio;
	}

	@Override
	public void salvar(Doador doador, Busca busca) {
		ReservaDoadorInternacional reservaDoador = new ReservaDoadorInternacional();
		reservaDoador.setBusca(busca);
		reservaDoador.setDoador(doador);
		reservaDoador.setDataAtualizacao(LocalDateTime.now());
		reservaDoador.setStatus(StatusReservaDoadorInternacional.ATIVO);
		save(reservaDoador);
	}
	
	@Override
	public Paciente obterPacienteAssociado(Doador doador){
		return reservaDoadorRepositorio.obterPacienteAssociado(doador.getId());
	}
	
}
