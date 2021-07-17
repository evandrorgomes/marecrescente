package br.org.cancer.modred.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.controller.dto.genotipo.ValorAlelo;
import br.org.cancer.modred.model.ValorGenotipoBuscaPreliminar;
import br.org.cancer.modred.model.ValorGenotipoPreliminar;
import br.org.cancer.modred.persistence.IValorGenotipoBuscaPreliminarRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IDnaService;
import br.org.cancer.modred.service.IValorGenotipoBuscaPreliminarService;
import br.org.cancer.modred.service.IValorGenotipoExpandidoPreliminarService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.GenotipoUtil;

/**
 * Implementa os métodos e as regras de negócio envolvendo a entidade GenotipoBuscaPreliminar.
 * 
 * @author Pizão
 */
@Service
@Transactional
public class ValorGenotipoBuscaPreliminarService extends AbstractService<ValorGenotipoBuscaPreliminar, Long> implements IValorGenotipoBuscaPreliminarService {

	@Autowired
	private IValorGenotipoBuscaPreliminarRepository genotipoBuscaPreliminarRepositorio;
	
	@Autowired
	private IValorGenotipoExpandidoPreliminarService genotipoExpandidoPreliminarService;

	@Autowired
	private IDnaService hlaService;

	
	@Override
	public IRepository<ValorGenotipoBuscaPreliminar, Long> getRepository() {
		return genotipoBuscaPreliminarRepositorio;
	}

	@Override
	public void salvarGenotipoBusca(ValorGenotipoPreliminar genotipoPreliminar) {
		ValorAlelo aleloBusca = 
				hlaService.obterAleloTipado(genotipoPreliminar.getLocus().getCodigo(), genotipoPreliminar.getValorAlelo());
		
		List<ValorGenotipoBuscaPreliminar> valoresBusca = 
				aleloBusca.getValoresCompativeis().stream().map(valorCompativel -> {
					ValorGenotipoBuscaPreliminar genotipoBuscaPreliminar = new ValorGenotipoBuscaPreliminar();
					genotipoBuscaPreliminar.setBuscaPreliminar(genotipoPreliminar.getBuscaPreliminar());
					genotipoBuscaPreliminar.setLocus(genotipoPreliminar.getLocus());
					genotipoBuscaPreliminar.setPosicaoAlelo(genotipoPreliminar.getPosicaoAlelo());
					genotipoBuscaPreliminar.setTipo(aleloBusca.getComposicaoAlelo().getId());
					genotipoBuscaPreliminar.setValorAlelo(GenotipoUtil.extrairAleloParaGenotipoBusca(valorCompativel));
					return genotipoBuscaPreliminar;
				}).filter(GenotipoUtil.distinctByKey(ValorGenotipoBuscaPreliminar::getValorAlelo)).collect(Collectors.toList());
		
		saveAll(valoresBusca);
	}
	
	@Override
	public List<ValorGenotipoBuscaPreliminar> saveAll(List<ValorGenotipoBuscaPreliminar> valoresGenotipoBuscaPreliminar){
		super.saveAll(valoresGenotipoBuscaPreliminar);
		genotipoExpandidoPreliminarService.salvarGenotipoExpandido(valoresGenotipoBuscaPreliminar);
		return valoresGenotipoBuscaPreliminar;
	}
	
}
