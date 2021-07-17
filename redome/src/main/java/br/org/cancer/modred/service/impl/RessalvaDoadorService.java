package br.org.cancer.modred.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.ContatoDTO;
import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.dto.doador.RessalvaDoadorDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.CordaoNacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.RessalvaDoador;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IRessalvaDoadorRepository;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IRessalvaDoadorService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Implementacao da funcionalidades envolvendo a entidade RessalvaDoador.
 * 
 * @author Pizão.
 *
 */
@Service
@Transactional
public class RessalvaDoadorService extends AbstractService<RessalvaDoador, Long> implements IRessalvaDoadorService {

	@Autowired
	private IRessalvaDoadorRepository ressalvaDoadorRepository;
	
	private IDoadorNacionalService doadorService;
	
	@Autowired
	private IUsuarioService usuarioService;

	
	@Override
	public RessalvaDoador salvar(RessalvaDoadorDTO ressalvaDto) {

		Doador doador = doadorService.findById(ressalvaDto.getIdDoador());
		RessalvaDoador ressalva = new RessalvaDoador();
		ressalva.setDoador(doador);
		
		if (StatusDoador.ATIVO.equals(doador.getStatusDoador().getId()) &&
				(doador instanceof CordaoNacional || doador instanceof DoadorNacional)) {
			doadorService.atualizarStatusDoador(doador.getId(), StatusDoador.ATIVO_RESSALVA, null, null);
		}
		ressalva.setObservacao(ressalvaDto.getObservacao());
		ressalva.setDataCriacao(LocalDate.now());
		ressalva.setUsuarioResponsavel(usuarioService.obterUsuarioLogado());
		ressalva.setExcluido(false);
		return save(ressalva);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IRessalvaDoadorService#listarRessalvas(java.lang.Long)
	 */
	@Override
	public List<RessalvaDoador> listarRessalvas(Long idDoador) {
		if (idDoador == null) {
			throw new BusinessException("erro.resultado.workup.dmr_obrigatorio");
		}
		return ressalvaDoadorRepository.findByDoadorIdAndExcluido(idDoador, false);
	}

	@Override
	public boolean excluirRessalva(Long idRessalva) {
		if(excluir(idRessalva)){
			final Long id = ressalvaDoadorRepository.obterIdDoadorAssociadoARessalva(idRessalva);
			Integer quantidadeRessalvas = ressalvaDoadorRepository.contarRessalvasPorDoador(id);
			StatusDoador status = doadorService.obterStatusDoadorPorId(id);
			if(quantidadeRessalvas == 0 && StatusDoador.ATIVO_RESSALVA.equals(status.getId())){
				ContatoDTO doadorAtualizado = 
						doadorService.atualizarStatusDoador(id, StatusDoador.ATIVO, null, null);
				return doadorAtualizado != null;
			}
			return true;
		}
		
		return false;
	}

	private boolean excluir(Long idRessalva) {
		UpdateSet<Boolean> setarExcluido = new UpdateSet<Boolean>("excluido", Boolean.TRUE);
		Filter<Long> paraId = new Equals<Long>("id", idRessalva);
		return update(setarExcluido, paraId) > 0;
	}

	@Override
	public IRepository<RessalvaDoador, Long> getRepository() {
		return ressalvaDoadorRepository;
	}

	/**
	 * @param doadorService the doadorService to set
	 */
	@Autowired
	public void setDoadorService(IDoadorNacionalService doadorService) {
		this.doadorService = doadorService;
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RetornoInclusaoDTO adicionarRessalva(Long id, String ressalva) {
		RessalvaDoadorDTO ressalvaDoadorDto = new RessalvaDoadorDTO(id, ressalva);
		boolean salvoSucesso = this.salvar(ressalvaDoadorDto) != null;

		RetornoInclusaoDTO retornoDto = new RetornoInclusaoDTO();

		if (salvoSucesso) {
			retornoDto.setIdObjeto(ressalvaDoadorDto.getId());
			retornoDto.setMensagem(AppUtil.getMensagem(messageSource, "emailcontato.adicionado.sucesso"));
		}
		else {
			retornoDto.setMensagem(AppUtil.getMensagem(messageSource, "erro.requisicao"));
		}
		return retornoDto;
	}

}
