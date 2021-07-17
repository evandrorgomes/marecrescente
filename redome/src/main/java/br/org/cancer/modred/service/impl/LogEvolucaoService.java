package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.controller.dto.CriarLogEvolucaoDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.persistence.ILogEvolucaoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.ILogEvolucaoService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * 
 * @author Pizão
 * 
 * Classe que implementa as funcionalidades envolvendos a entidade LogEvolucao.
 *
 */
@Service
public class LogEvolucaoService extends AbstractService<LogEvolucao, Long> implements ILogEvolucaoService {

	@Autowired
	private ILogEvolucaoRepository logEvolucaoRepositorio;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public IRepository<LogEvolucao, Long> getRepository() {
		return logEvolucaoRepositorio;
	}

	@Override
	public void criarLogEvolucao(CriarLogEvolucaoDTO criarLogEvolucaoDTO) {
		
		if (tipoLogInvalido(criarLogEvolucaoDTO.getTipo())) {
			throw new BusinessException("erro.tipo.invalido,log.evolucao");			
		}
		if (perfisExcluidosInvalidos(criarLogEvolucaoDTO.getPerfisExcluidos())) {
			throw new BusinessException("erro.perfil.invalido,log.evolucao");
		}
		
		Paciente paciente = pacienteService.obterPaciente(criarLogEvolucaoDTO.getRmr());
		
		try {
			usuarioService.obterUsuarioLogado();
			
		}catch (Exception e) {
			throw new BusinessException("Usuário Invalido!!!");
		}
		
		
		LogEvolucao log = new LogEvolucao();
		log.setPaciente(paciente);
		log.setParametros(criarLogEvolucaoDTO.getParametros());
		log.setData(LocalDateTime.now());
		log.setTipoEvento(TipoLogEvolucao.valueOf(criarLogEvolucaoDTO.getTipo()));
		log.setUsuario(usuarioService.obterUsuarioLogado());
		List<Perfil> perfis = null;
		if(CollectionUtils.isNotEmpty(criarLogEvolucaoDTO.getPerfisExcluidos())){
			perfis =  new ArrayList<Perfil>();
			for(Long idPerfil: criarLogEvolucaoDTO.getPerfisExcluidos()){
				Perfil perfil = new Perfil();
				perfil.setId(idPerfil);
				perfis.add(perfil);
			}			
			log.setPerfisExcluidos(perfis);
		}
		save(log);
		
	}

	private boolean perfisExcluidosInvalidos(List<Long> perfisExcluidos) {
		
		if (CollectionUtils.isNotEmpty(perfisExcluidos)) {
			return perfisExcluidos.stream().anyMatch(idPerfil -> Perfis.valueOf(idPerfil) == null);
		}
		
		return false;
	}

	private boolean tipoLogInvalido(String tipo) {
		if (tipo == null) {
			return true;
		}
		
		try {
			TipoLogEvolucao tipoLogEvolucao = TipoLogEvolucao.valueOf(tipo);
			if(TipoLogEvolucao.INDEFINIDO.equals(tipoLogEvolucao)){
				return true;
			}
		}
		catch (IllegalArgumentException e) {
			return true;
		}
		
		return false;
				
	}

}
