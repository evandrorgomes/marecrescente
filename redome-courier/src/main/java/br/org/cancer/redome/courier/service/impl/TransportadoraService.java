package br.org.cancer.redome.courier.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.courier.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.redome.courier.controller.dto.TransportadoraListaDTO;
import br.org.cancer.redome.courier.exception.BusinessException;
import br.org.cancer.redome.courier.model.ContatoTelefonicoTransportadora;
import br.org.cancer.redome.courier.model.EmailContatoTransportadora;
import br.org.cancer.redome.courier.model.Transportadora;
import br.org.cancer.redome.courier.persistence.IRepository;
import br.org.cancer.redome.courier.persistence.ITransportadoraRepository;
import br.org.cancer.redome.courier.service.IContatoTelefonicoTransportadoraService;
import br.org.cancer.redome.courier.service.IEmailContatoTransportadoraService;
import br.org.cancer.redome.courier.service.ITransportadoraService;
import br.org.cancer.redome.courier.service.IUsuarioService;
import br.org.cancer.redome.courier.service.impl.custom.AbstractService;
import br.org.cancer.redome.courier.util.AppUtil;
import br.org.cancer.redome.feign.client.IUsuarioFeign;
import br.org.cancer.redome.feign.client.dto.TransportadoraUsuarioDTO;

/**
 * Implementação dos métodos de negócio de transportadora.
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class TransportadoraService extends AbstractService<Transportadora, Long> implements ITransportadoraService {
	
	

	@Autowired
	private ITransportadoraRepository transportadoraRepository;
	
	@Autowired
	private IEmailContatoTransportadoraService emailContatoTransportadoraService;
	
	@Autowired
	private IContatoTelefonicoTransportadoraService contatoTelefonicoTransportadora;
	
	@Autowired
	private MessageSource messageSource;	
	
	@Autowired
	@Lazy(true)
	private IUsuarioFeign usuarioFeign;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	
	@Override
	public IRepository<Transportadora, Long> getRepository() {
		return transportadoraRepository;
	}
	
	@Override
	public List<TransportadoraListaDTO> listarIdENomeDeTransportadoresAtivas() {
		return transportadoraRepository.findAll().stream().filter(transportadora -> transportadora.getAtivo())
				.map(TransportadoraListaDTO::new)
				.collect(Collectors.toList());
	}

	@Override
	public Page<Transportadora> listarTransportadoras(PageRequest pageRequest,
			String nome) {
		return transportadoraRepository.listarTransportadoraAtivas(pageRequest, nome != null ? "%" + nome + "%" : "");
	}

	@Override
	public void inativarTransportadora(Long idTransportadora) {
		
		usuarioService.obterUsuarioLogado();
		
		Transportadora transportadora = this.findById(idTransportadora);
		transportadora.setAtivo(false);
		this.save(transportadora);
	}

	@Override
	public void criarTransportadora(Transportadora transportadora) {
		
		transportadora.setAtivo(true);
			
		if(transportadora.getEndereco() != null){
			transportadora.getEndereco().setTransportadora(transportadora);			
		}
		
		if(transportadora.getEmails() != null){
			transportadora.getEmails().stream().forEach(e->{
				e.setTransportadora(transportadora);
			});
			
		}
		if(transportadora.getTelefones() != null){
			transportadora.getTelefones().stream().forEach(e->{
				e.setTransportadora(transportadora);
			});
		}
		this.save(transportadora);
		
		if (CollectionUtils.isNotEmpty(transportadora.getUsuarios())) {
			usuarioFeign.atribuirTransportadora(TransportadoraUsuarioDTO.builder()
					.idTransportadora(transportadora.getId())
					.usuarios(transportadora.getUsuarios())
					.build());
		}
		
	}
	
	@Override
	public Transportadora obterTransportadoraPorId(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");			
		}
		return transportadoraRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Transportadora"));
	}

	@Override
	public RetornoInclusaoDTO adicionarEmail(Long id, EmailContatoTransportadora emailTransportadora){
		Transportadora transportadora = obterTransportadoraPorId(id);

		if (transportadora.getEmails() == null) {
			transportadora.setEmails(new ArrayList<>());
		}

		transportadora.getEmails().add(emailTransportadora);
		emailTransportadora.setTransportadora(transportadora);
	
		emailContatoTransportadoraService.save(emailTransportadora);
		
		return  RetornoInclusaoDTO.builder()
				.idObjeto(emailTransportadora.getId())
				.mensagem(AppUtil.getMensagem(messageSource, "transportadora.mensagem.sucesso.gravacao.email"))
				.build();
	}

	@Override
	public RetornoInclusaoDTO adicionarContatoTelefonico(Long id, ContatoTelefonicoTransportadora contato) {
		Transportadora transportadora = obterTransportadoraPorId(id);

		if (transportadora.getTelefones() == null) {
			transportadora.setTelefones(new ArrayList<>());
		}

		transportadora.getTelefones().add(contato);
		contato.setTransportadora(transportadora);
	
		contatoTelefonicoTransportadora.save(contato);
		
		return  RetornoInclusaoDTO.builder()
				.idObjeto(contato.getId())
				.mensagem(AppUtil.getMensagem(messageSource, "transportadora.mensagem.sucesso.gravacao.telefone"))
				.build();
		
	}
	
	

}
