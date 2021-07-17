package br.org.cancer.modred.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.EvolucaoDoador;
import br.org.cancer.modred.persistence.IEvolucaoDoadorRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IDoadorService;
import br.org.cancer.modred.service.IEvolucaoDoadorService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.OrderBy;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.vo.EvolucaoDoadorVo;

/**
 * Implementação de métodos de negócio de Evolução de Doador.
 * 
 * @author Filipe Paes
 *
 */
@Service
public class EvolucaoDoadorService extends AbstractService<EvolucaoDoador, Long> implements IEvolucaoDoadorService {

	@Autowired
	private IEvolucaoDoadorRepository evolucaoDoadorRepository;
	
	@Autowired
	private IDoadorService doadorService;
	
	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public IRepository<EvolucaoDoador, Long> getRepository() {
		return evolucaoDoadorRepository;
	}

	@Override
	public List<EvolucaoDoador> listarEvolucoesPorDoadorOrdernadoPorDataCriacaoDecrescente(Long idDoador) {
				
		Filter<Long> doador = new Equals<>("doador.id", idDoador);
		OrderBy order = new OrderBy("dataCriacao", false);

		return find(Arrays.asList(doador), order);
	}
	
	@Override
	public void criarEvolucaoDoador(EvolucaoDoadorVo evolucaoDoadorVo) {
		final Doador doador = doadorService.obterDoador(evolucaoDoadorVo.getIdDoador());
		
		EvolucaoDoador evolucaoDoador = new EvolucaoDoador(doador, usuarioService.obterUsuarioLogado(), evolucaoDoadorVo.getDescricao());
		
		save(evolucaoDoador);
	}	
	

}
