package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.HemoEntidade;
import br.org.cancer.modred.persistence.IHemoEntidadeRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IHemoEntidadeService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.custom.AbstractService;
/**
 * Classe de funcionalidades envolvendo a entidade HemoEntidade.
 * 
 * @author Pizão.
 *
 */
@Service
public class HemoEntidadeService extends AbstractService<HemoEntidade, Long> implements IHemoEntidadeService {
	
	@Autowired
	private IHemoEntidadeRepository repositorio;
	
	@Autowired
	private IDoadorNacionalService doadorService;
	
	@Override
	public IRepository<HemoEntidade, Long> getRepository() {
		return repositorio;
	}
	
	@Override
	public List<HemoEntidade> listarHemoEntidadesPorUf(String siglaUf){
		Filter<Boolean> soSelecionaveis = new Equals<Boolean>("selecionavel", Boolean.TRUE);
		Filter<String> uf = new Equals<String>("endereco.municipio.uf.sigla", siglaUf);
        return find(soSelecionaveis, uf);
	}

	@Override
	public List<HemoEntidade> listarHemoEntidadesPorDoador(Long idDoador) {
		String siglaUf = doadorService.obterNaturalidadeDoador(idDoador);
		return listarHemoEntidadesPorUf(siglaUf);
	}
	
	@Override
	public HemoEntidade obterPorId(Long id) {
		HemoEntidade hemoEntidade = findById(id);
		if (hemoEntidade == null) {
			throw new BusinessException("erro.hemoentidade.nao.encontrado");
		}
		return hemoEntidade;
	}

	
}
