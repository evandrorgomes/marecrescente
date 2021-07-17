package br.org.cancer.modred.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.ValorGenotipoBuscaPreliminar;
import br.org.cancer.modred.model.ValorGenotipoExpandidoPreliminar;
import br.org.cancer.modred.persistence.IValorGenotipoExpandidoPreliminarRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IValorGenotipoExpandidoPreliminarService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.GenotipoUtil;

/**
 * Implementa os métodos e as regras de negócio envolvendo a entidade GenotipoExpandidoPreliminar.
 * 
 * @author Pizão
 */
@Service
@Transactional
public class ValorGenotipoExpandidoPreliminarService extends AbstractService<ValorGenotipoExpandidoPreliminar, Long> implements IValorGenotipoExpandidoPreliminarService {

	@Autowired
	private IValorGenotipoExpandidoPreliminarRepository genotipoExpandidoPreliminarRepositorio;

	
	@Override
	public IRepository<ValorGenotipoExpandidoPreliminar, Long> getRepository() {
		return genotipoExpandidoPreliminarRepositorio;
	}

	@Override
	public void salvarGenotipoExpandido(List<ValorGenotipoBuscaPreliminar> valoresGenotipoBusca) {
		List<ValorGenotipoExpandidoPreliminar> valoresExpandidos = 
				valoresGenotipoBusca.stream().map(genotipoBuscaPreliminar -> {
						ValorGenotipoExpandidoPreliminar genotipoExpandidoPreliminar = new ValorGenotipoExpandidoPreliminar();
						genotipoExpandidoPreliminar.setBuscaPreliminar(genotipoBuscaPreliminar.getBuscaPreliminar());
						genotipoExpandidoPreliminar.setLocus(genotipoBuscaPreliminar.getLocus());
						genotipoExpandidoPreliminar.setPosicaoAlelo(genotipoBuscaPreliminar.getPosicaoAlelo());
						genotipoExpandidoPreliminar.setValorAlelo(
								Integer.valueOf(GenotipoUtil.extrairAleloParaGenotipoExpandido(genotipoBuscaPreliminar.getValorAlelo())));
						return genotipoExpandidoPreliminar;
					}).filter(GenotipoUtil.distinctByKey(ValorGenotipoExpandidoPreliminar::getValorAlelo)).collect(Collectors.toList());
			
		saveAll(valoresExpandidos);			
	}
	
}
