package br.org.cancer.modred.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.ContatoCentroTransplantadorDTO;
import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.CentroTransplanteUsuario;
import br.org.cancer.modred.model.ContatoTelefonicoCentroTransplante;
import br.org.cancer.modred.model.EmailContatoCentroTransplante;
import br.org.cancer.modred.model.EnderecoContatoCentroTransplante;
import br.org.cancer.modred.model.FuncaoTransplante;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.domain.EntityStatus;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.ICentroTransplanteRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IContatoTelefonicoCentroTransplanteService;
import br.org.cancer.modred.service.IEmailContatoCentroTransplanteService;
import br.org.cancer.modred.service.IEnderecoContatoCentroTransplanteService;
import br.org.cancer.modred.service.IFuncaoTransplanteService;
import br.org.cancer.modred.service.ILaboratorioService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Implementação da interface que descreve os métodos de negócio que operam
 * sobre a entidade CentroTransplante.
 * 
 * @author Pizão
 */
@Service
@Transactional
public class CentroTransplanteService extends AbstractService<CentroTransplante, Long>
		implements ICentroTransplanteService {

	@Autowired
	private ICentroTransplanteRepository centroTransplanteRepository;
	
	private IFuncaoTransplanteService funcaoTransplanteService;
	
	private ILaboratorioService laboratorioService;
		
	private IUsuarioService usuarioService;
	
	private IEnderecoContatoCentroTransplanteService enderecoContatoCentroTransplanteService;
	
	private IContatoTelefonicoCentroTransplanteService contatoTelefonicoCentroTransplanteService;
		
	private IEmailContatoCentroTransplanteService emailContatoCentroTransplanteCervice;
	

	@Override
	public IRepository<CentroTransplante, Long> getRepository() {
		return centroTransplanteRepository;
	}

	/**
	 * Método para listar os centros de transplantes disponíveis na base.
	 * 
	 * @Return List<CentroTransplante>
	 */
	@Override
	public List<CentroTransplante> listarCentroTransplante() {
		return centroTransplanteRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isCentroTransplanteExistente(Long id) {
		return centroTransplanteRepository.findById(id) != null;
	}

	@Override
	public RetornoInclusaoDTO salvarCentroTransplante(CentroTransplante centroTransplante) {

		if (centroTransplante != null) {
			
			if (centroTransplante.getEnderecos() != null) {
				centroTransplante.getEnderecos().forEach(endereco -> {
					endereco.setCentroTransplante(centroTransplante);
				});
			}
			
			if (centroTransplante.getContatosTelefonicos() != null) {
				centroTransplante.getContatosTelefonicos().forEach(telefone -> {
					telefone.setCentroTransplante(centroTransplante);
				});
			}
			
			if (centroTransplante.getEmails() != null) {
				centroTransplante.getEmails().forEach(email -> {
					email.setCentroTransplante(centroTransplante);
				});
			}
			
			if (centroTransplante.getLaboratorioPreferencia() != null && 
					centroTransplante.getLaboratorioPreferencia().getId() == null) {
				centroTransplante.setLaboratorioPreferencia(null);
			}
			if(centroTransplante.getCentroTransplanteUsuarios() != null && !centroTransplante.getCentroTransplanteUsuarios().isEmpty()){
				centroTransplante.getCentroTransplanteUsuarios().forEach(centro->{
					centro.setCentroTransplante(centroTransplante);
					centro.setUsuario(usuarioService.obterUsuario(centro.getUsuario().getId()));
					centro.setResponsavel(true);
				});
			}
			
			centroTransplante.setEntityStatus(EntityStatus.ATIVO);

			save(centroTransplante);
			
			RetornoInclusaoDTO retornoDto = new RetornoInclusaoDTO();
			retornoDto.setIdObjeto(centroTransplante.getId());
			retornoDto.setMensagem(AppUtil.getMensagem(messageSource, "centroTransplante.cadastrado.sucesso"));
			return retornoDto;
			
		}
		else {
			throw new BusinessException("erro.mensagem.centroTransplante.nulo");
		}

	}

	@Override
	public void atualizarCentroTransplante(CentroTransplante centroTransplante) {

		if (centroTransplante != null && centroTransplante.getId() != null) {

			if (centroTransplante.getNome() == null || centroTransplante.getNome().trim().equals("")) {
				throw new BusinessException("erro.mensagem.centroTransplante.nome.invalido");
			}

			if (centroTransplante.getCnpj() == null || centroTransplante.getCnpj().trim().equals("")) {
				throw new BusinessException("erro.mensagem.centroTransplante.cnpj.invalido");
			}

			if (centroTransplante.getCnes() == null || centroTransplante.getCnes().trim().equals("")) {
				throw new BusinessException("erro.mensagem.centroTransplante.cnes.invalido");
			}

			centroTransplante.setEntityStatus(EntityStatus.ATIVO);

			centroTransplanteRepository.atualizarCentroTransplante(centroTransplante.getId(),
					centroTransplante.getNome(), centroTransplante.getCnpj(), centroTransplante.getCnes());
		}
		else {
			throw new BusinessException("erro.mensagem.centroTransplante.nulo");
		}
	}

	@Override
	public CentroTransplante obterCentroTransplante(Long id) {
		if (id == null) {
			throw new BusinessException("erro.mensagem.centroTransplante.id.invalido");
		}
		return centroTransplanteRepository.findById(id).orElseThrow(() -> new BusinessException("mensagem.nenhum.registro.encontrado", "registro"));
	}

	@Override
	public void eliminarCentroTransplante(CentroTransplante centroTransplante) {

		if (centroTransplante != null && centroTransplante.getId() != null) {

			if (centroTransplanteRepository.atualizarStatusEntidade(centroTransplante.getId(),
					EntityStatus.APAGADO.getId()) == 0) {

				throw new BusinessException("erro.mensagem.centroTransplante.nao.apagado");
			}
		}
		else {
			throw new BusinessException("erro.mensagem.centroTransplante.id.invalido");
		}
	}

	@Override
	public Page<CentroTransplante> listarCentroTransplantes(String nome, String cnpj, String cnes, PageRequest pageRequest,
			Long idFuncaoCentroTransplante) {
		
		return centroTransplanteRepository.listarCentroTransplantes(nome, cnpj, cnes, pageRequest, idFuncaoCentroTransplante);
	}

	@Override
	public Page<CentroTransplante> listarCentroTransplantes(Long idFuncaoCentroTransplante) {
		return centroTransplanteRepository.listarCentroTransplantes(null, null, null, null,idFuncaoCentroTransplante);
	}

	@Override
	public List<ContatoCentroTransplantadorDTO> listarCentroTransplantesPorFuncao(Long funcaoCentroTransplanteId) {
		return centroTransplanteRepository.listarCentrosTransplantePorFuncao(funcaoCentroTransplanteId);
	}

	@Override
	public List<Usuario> listarUsuariosPorCentro(Long centroTransplanteId) {
		return centroTransplanteRepository.listarUsuariosPorCentro(centroTransplanteId);
	}

	@Override
	public List<FuncaoTransplante> listarFuncoes() {
		return funcaoTransplanteService.findAll();
	}

	@Override
	public void atualizarDadosBasicos(Long id, CentroTransplante centroTransplante) {
		CentroTransplante centroTransplanteRecuperado = obterCentroTransplante(id);
		
		centroTransplanteRecuperado.setNome(centroTransplante.getNome());
		centroTransplanteRecuperado.setCnpj(centroTransplante.getCnpj());
		centroTransplanteRecuperado.setCnes(centroTransplante.getCnes());
		centroTransplanteRecuperado.setFuncoes(centroTransplante.getFuncoes());
		
		save(centroTransplanteRecuperado);
		 
	}

	@Override
	public void atualizarLaboratorioPreferencia(Long id, Laboratorio laboratorio) {
		CentroTransplante centroTransplante = obterCentroTransplante(id);
		Laboratorio laboratorioRecuperado = laboratorioService.findById(laboratorio.getId());
		if (laboratorioRecuperado == null) {
			throw new BusinessException("erro.mensagem.laboratorio.nao.encontrado");
		}
		centroTransplante.setLaboratorioPreferencia(laboratorioRecuperado);
		save(centroTransplante);
		
	}

	@Override
	public void atualizarMedicos(Long id, List<CentroTransplanteUsuario> medicos) {
		CentroTransplante centroTransplante = obterCentroTransplante(id);
		medicos.forEach(medico -> {
			medico.setCentroTransplante(centroTransplante);
			medico.setUsuario(usuarioService.obterUsuario(medico.getUsuario().getId()));
			medico.setResponsavel(true);
		});
		centroTransplante.getCentroTransplanteUsuarios().clear();
		centroTransplante.getCentroTransplanteUsuarios().addAll(medicos);
		save(centroTransplante);
	}

	@Override
	public void removerLaboratorioPreferencia(Long id) {
		CentroTransplante centroTransplante = obterCentroTransplante(id);
		centroTransplante.setLaboratorioPreferencia(null);
		
		save(centroTransplante);
	}

	@Override
	public RetornoInclusaoDTO adicionarEnderecoContato(Long id, EnderecoContatoCentroTransplante enderecoContato) {
		CentroTransplante centro = obterCentroTransplante(id);

		if (centro.getEnderecos() == null) {
			centro.setEnderecos(new ArrayList<>());
		}

		centro.getEnderecos().add(enderecoContato);
		enderecoContato.setCentroTransplante(centro);
	
		enderecoContatoCentroTransplanteService.save(enderecoContato);
		
		RetornoInclusaoDTO retornoDto = new RetornoInclusaoDTO();
		retornoDto.setIdObjeto(enderecoContato.getId());
		retornoDto.setMensagem(AppUtil.getMensagem(messageSource, "centroTransplante.enderecocontato.adicionado.sucesso"));
		return retornoDto;
	}
	
	

	@Override
	public RetornoInclusaoDTO adicionarContatoTelefonico(Long id, ContatoTelefonicoCentroTransplante contato) {
		CentroTransplante centro = obterCentroTransplante(id);

		if (centro.getContatosTelefonicos() == null) {
			centro.setContatosTelefonicos(new ArrayList<>());
		}
		
		centro.getContatosTelefonicos().add(contato);
		contato.setCentroTransplante(centro);
		
		contatoTelefonicoCentroTransplanteService.save(contato);
		
		RetornoInclusaoDTO retornoDto = new RetornoInclusaoDTO();
		retornoDto.setIdObjeto(contato.getId());
		retornoDto.setMensagem(AppUtil.getMensagem(messageSource, "centroTransplante.contatotelefonico.adicionado.sucesso"));
		return retornoDto;
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(CentroTransplante entity) {
		List<CampoMensagem> mensagens = super.validateEntity(entity);
		if (entity.getLaboratorioPreferencia() != null && entity.getLaboratorioPreferencia().getId() != null) {
			final Laboratorio laboratorio = laboratorioService.findById(entity.getLaboratorioPreferencia().getId());
			if (laboratorio == null) {
				mensagens.add(new CampoMensagem("laboratorio", 
						AppUtil.getMensagem(messageSource, "erro.mensagem.laboratorio.nao.encontrado")));
			}
		}
		validarEmailPrincipal(entity.getEmails(), mensagens);
		
		return mensagens;
	}
	
	private void validarEmailPrincipal(List<EmailContatoCentroTransplante> emails, List<CampoMensagem> mensagens) {
		Boolean existePrincipal = emails.stream().anyMatch(email -> email.getPrincipal());
		if (!existePrincipal) {
			mensagens.add(new CampoMensagem( 
					AppUtil.getMensagem(messageSource, "erro.mensagem.centroTransplante.email.principal.obrigatorio")));			
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CentroTransplante> findByUsuarios(Usuario usuario) {
		return centroTransplanteRepository.findByCentroTransplanteUsuariosUsuario(usuario);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public RetornoInclusaoDTO adicionarEmail(Long id, EmailContatoCentroTransplante emailCentroTransplante) {
		CentroTransplante centro = obterCentroTransplante(id);
		if (emailCentroTransplante != null) {
			emailCentroTransplante.setCentroTransplante(centro);
		}
		
		emailContatoCentroTransplanteCervice.salvar(emailCentroTransplante);
				
		RetornoInclusaoDTO retornoDto = new RetornoInclusaoDTO();
		retornoDto.setIdObjeto(emailCentroTransplante.getId());
		retornoDto.setMensagem(AppUtil.getMensagem(messageSource, "centroTransplante.emailcontato.adicionado.sucesso"));
		
		return retornoDto;		
	}

	/**
	 * @param funcaoTransplanteService the funcaoTransplanteService to set
	 */
	@Autowired
	public void setFuncaoTransplanteService(IFuncaoTransplanteService funcaoTransplanteService) {
		this.funcaoTransplanteService = funcaoTransplanteService;
	}

	/**
	 * @param laboratorioService the laboratorioService to set
	 */
	@Autowired
	public void setLaboratorioService(ILaboratorioService laboratorioService) {
		this.laboratorioService = laboratorioService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @param enderecoContatoCentroTransplanteService the enderecoContatoCentroTransplanteService to set
	 */
	@Autowired
	public void setEnderecoContatoCentroTransplanteService(
			IEnderecoContatoCentroTransplanteService enderecoContatoCentroTransplanteService) {
		this.enderecoContatoCentroTransplanteService = enderecoContatoCentroTransplanteService;
	}

	/**
	 * @param contatoTelefonicoCentroTransplanteService the contatoTelefonicoCentroTransplanteService to set
	 */
	@Autowired
	public void setContatoTelefonicoCentroTransplanteService(
			IContatoTelefonicoCentroTransplanteService contatoTelefonicoCentroTransplanteService) {
		this.contatoTelefonicoCentroTransplanteService = contatoTelefonicoCentroTransplanteService;
	}

	/**
	 * @param emailContatoCentroTransplanteCervice the emailContatoCentroTransplanteCervice to set
	 */
	@Autowired
	public void setEmailContatoCentroTransplanteCervice(
			IEmailContatoCentroTransplanteService emailContatoCentroTransplanteCervice) {
		this.emailContatoCentroTransplanteCervice = emailContatoCentroTransplanteCervice;
	}
	
	@Override
	public EnderecoContatoCentroTransplante obterEnderecoEntrega(Long id) {
		CentroTransplante centro = obterCentroTransplante(id);
		if (CollectionUtils.isNotEmpty(centro.getEnderecos())) {
			return centro.getEnderecos().stream().filter(endereco -> endereco.isEntrega()).findFirst().orElse(null);
		}
		return null;
	}
	
	@Override
	public EnderecoContatoCentroTransplante obterEnderecoWorkup(Long id) {
		CentroTransplante centro = obterCentroTransplante(id);
		if (CollectionUtils.isNotEmpty(centro.getEnderecos())) {
			return centro.getEnderecos().stream().filter(endereco -> endereco.isWorkup()).findFirst().orElse(null);
		}
		return null;
	}
	
	@Override
	public EnderecoContatoCentroTransplante obterEnderecoRetirada(Long id) {
		CentroTransplante centro = obterCentroTransplante(id);
		if (CollectionUtils.isNotEmpty(centro.getEnderecos())) {
			return centro.getEnderecos().stream().filter(endereco -> endereco.isRetirada()).findFirst().orElse(null);
		}
		return null;
	}

}
