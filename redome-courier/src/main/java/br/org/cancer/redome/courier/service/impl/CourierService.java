package br.org.cancer.redome.courier.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.courier.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.redome.courier.exception.BusinessException;
import br.org.cancer.redome.courier.model.ContatoTelefonicoCourier;
import br.org.cancer.redome.courier.model.Courier;
import br.org.cancer.redome.courier.model.EmailContatoCourier;
import br.org.cancer.redome.courier.persistence.ICourierRepository;
import br.org.cancer.redome.courier.persistence.IRepository;
import br.org.cancer.redome.courier.service.IContatoTelefonicoCourierService;
import br.org.cancer.redome.courier.service.ICourierService;
import br.org.cancer.redome.courier.service.IEmailContatoCourierService;
import br.org.cancer.redome.courier.service.ITransportadoraService;
import br.org.cancer.redome.courier.service.IUsuarioService;
import br.org.cancer.redome.courier.service.impl.custom.AbstractService;
import br.org.cancer.redome.courier.util.AppUtil;
import br.org.cancer.redome.courier.util.CampoMensagem;

/**
 * Implementacao da classe de negócios de Courier.
 * 
 * @author Fillipe Queiroz
 *
 */
@Service
@Transactional
public class CourierService extends AbstractService<Courier, Long> implements ICourierService {

	@Autowired
	private ICourierRepository courierRepository;

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ITransportadoraService transportadoraService;

	@Autowired
	private IContatoTelefonicoCourierService contatoTelefonicoCourierService;
	
	@Autowired
	private IEmailContatoCourierService emailContatoCourierService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	public IRepository<Courier, Long> getRepository() {
		return courierRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.ICourierService#listarCourierPorTransportadoraLogada()
	 */
	@Override
	public List<Courier> listarCourierPorTransportadoraLogada() {

		return courierRepository.findByTransportadoraId(usuarioService.obterUsuarioLogado().getIdTransportadora());
	}

	@Override
	public Page<Courier> listarCouriesAtivosPorTransportadoraEPor(PageRequest pageRequest,String nome, String cpf, Long id) {
		return courierRepository.listarCouriesAtivosEPor(pageRequest, usuarioService.obterUsuarioLogado().getIdTransportadora()
				, nome != null? "%"+ nome +"%":null, cpf, id);
	}

	@Override
	public void inserirCourier(Courier courier) {
		
		
		courier.setAtivo(true);
		courier.setTransportadora(this.transportadoraService.findById(usuarioService.obterUsuarioLogado().getIdTransportadora()));
		courier.getContatosTelefonicos().stream().forEach(c->c.setCourier(courier));
		courier.getEmails().stream().forEach(e->e.setCourier(courier));
		this.save(courier);
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(Courier entity) {
		List<CampoMensagem> mensagens = super.validateEntity(entity);
		if(!verificarCpf(entity)){
			mensagens.add(new CampoMensagem(AppUtil.getMensagem(messageSource, "courier.mensagem.erro.cpf")));
		}
		return mensagens;
	}

	private boolean verificarCpf(Courier courier) {
		if(courier.getCpf() != null){
			Page<Courier> pesquisaPorCpf = this.listarCouriesAtivosPorTransportadoraEPor(new PageRequest(0, 10), null, courier.getCpf(), courier.getId());
			if(!pesquisaPorCpf.getContent().isEmpty()){
				return false;
			}
			
		}
		return true;
	}

	@Override
	public void atualizarCourier(Courier courier, Long id) {
		Courier courierBase = this.findById(id);
		courierBase.setCpf(courier.getCpf());
		courierBase.setNome(courier.getNome());
		courierBase.setRg(courier.getRg());
		this.save(courierBase);
	}

	@Override
	public void inativarCourier(Long id) {
		Courier courier = this.findById(id);
		courier.setAtivo(false);
		this.save(courier);
	}

	@Override
	public RetornoInclusaoDTO adicionarContatoTelefonico(Long id, ContatoTelefonicoCourier contato) {
		Courier courier = this.findById(id);
		contato.setCourier(courier);		
		contatoTelefonicoCourierService.save(contato);
		
		return RetornoInclusaoDTO.builder()
				.idObjeto(contato.getId())
				.mensagem(AppUtil.getMensagem(messageSource, "courier.mensagem.contatotelefonico.adicionado.sucesso"))
				.build();
		
	}

	@Override
	public RetornoInclusaoDTO adicionarEmail(Long id, EmailContatoCourier emailCourier) {
		Courier courier = this.findById(id);

		if (courier.getEmails() == null) {
			courier.setEmails(new ArrayList<>());
		}

		courier.getEmails().add(emailCourier);
		emailCourier.setCourier(courier);
	
		emailContatoCourierService.save(emailCourier);
		
		return RetornoInclusaoDTO.builder()
				.idObjeto(emailCourier.getId())
				.mensagem(AppUtil.getMensagem(messageSource, "courier.mensagem.sucesso.gravacao.email"))
				.build();
		
	}
	
	@Override
	public Courier obterCourierPorId(Long id) {
		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return courierRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Courier"));
	}

}
