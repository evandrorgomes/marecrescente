package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.ContatoTelefonicoDoador;
import br.org.cancer.modred.model.RegistroContato;
import br.org.cancer.modred.persistence.IRegistroContatoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IContatoTelefonicoDoadorService;
import br.org.cancer.modred.service.IRegistroContatoService;
import br.org.cancer.modred.service.impl.custom.AbstractService;


/**
 * Classe de negócios de Registro de Contato.
 * @author Filipe Paes
 *
 */
@Service
public class RegistroContatoService extends AbstractService<RegistroContato, Long> implements IRegistroContatoService {

	@Autowired
	private IRegistroContatoRepository registroContatoRepository;
	
	
	@Autowired
	private CustomUserDetailsService customUserDetailService;
	
	@Autowired
	private IContatoTelefonicoDoadorService contatoTelefonicoService;
	
	@Override 
	public IRepository<RegistroContato, Long> getRepository() {
		return registroContatoRepository;
	} 

	@Override
	public Page<RegistroContato> listarPor(Pageable page, Long idPedidoContato) {
		return this.registroContatoRepository.findByPedidoContatoIdOrderByMomentoLigacaoDesc(page, idPedidoContato);
	}

	@Override
	public void inserir(RegistroContato registro) {
		
		final RegistroContato registroPers = new RegistroContato();
		
		registroPers.setMomentoLigacao(LocalDateTime.now());
		
		registroPers.setObservacao(registro.getObservacao());
		
		registroPers.setPedidoContato(registro.getPedidoContato() == null || registro.getPedidoContato().getId() == null?
				null: registro.getPedidoContato());
		
		registroPers.setUsuario(customUserDetailService.obterUsuarioLogado());
		
		registroPers.setRegistroTipoOcorrencia(registro.getRegistroTipoOcorrencia() == null || registro.getRegistroTipoOcorrencia().getId() == null ? 
				null :registro.getRegistroTipoOcorrencia());
		
		if(registro.getContatoTelefonico() != null || registro.getContatoTelefonico().getId() != null) {
			ContatoTelefonicoDoador telefoneDoador = this.contatoTelefonicoService.obterContatoTelefonicoDoador(registro.getContatoTelefonico().getId());
			telefoneDoador.setNome(registro.getContatoTelefonico().getNome());
			registroPers.setContatoTelefonico(telefoneDoador);
		}
		else {
			registroPers.setContatoTelefonico(null);
		}
		this.save(registroPers);
	}
}
