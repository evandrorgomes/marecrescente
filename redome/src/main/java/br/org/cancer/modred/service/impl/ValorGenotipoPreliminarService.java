package br.org.cancer.modred.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.LocusExamePreliminar;
import br.org.cancer.modred.model.ValorGenotipoPreliminar;
import br.org.cancer.modred.model.ValorGenotipoPreliminarPK;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IValorGenotipoPreliminarRepository;
import br.org.cancer.modred.service.ILocusService;
import br.org.cancer.modred.service.IValorGenotipoBuscaPreliminarService;
import br.org.cancer.modred.service.IValorGenotipoPreliminarService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.GenotipoUtil;

/**
 * Implementa os métodos e as regras de negócio envolvendo a entidade GenotipoPreliminar.
 * 
 * @author Pizão
 */
@Service
@Transactional
public class ValorGenotipoPreliminarService extends AbstractService<ValorGenotipoPreliminar, Long> implements IValorGenotipoPreliminarService{

	@Autowired
	private IValorGenotipoPreliminarRepository genotipoPreliminarRepositorio;
	
	@Autowired
	private IValorGenotipoBuscaPreliminarService genotipoBuscaPreliminarService;
	
	@Autowired
	private ILocusService locusService;
	

	@Override
	public IRepository<ValorGenotipoPreliminar, Long> getRepository() {
		return genotipoPreliminarRepositorio;
	}

	@Override
	public void salvarGenotipo(List<LocusExamePreliminar> valores) {
		valores.forEach(this::salvarGenotipo);
	}

	/**
	 * Cria o genótipo a partir dos valores de cada locus exame preliminar.
	 * @param valorLocus locus exame preliminar.
	 */
	private void salvarGenotipo(LocusExamePreliminar valorLocus) {
		String primeiroAlelo = valorLocus.getPrimeiroAlelo();
		String segundoAlelo = valorLocus.getSegundoAlelo();
		
		primeiroAlelo = GenotipoUtil.normalizarValoresBlank(primeiroAlelo, segundoAlelo);
		segundoAlelo = GenotipoUtil.normalizarValoresBlank(segundoAlelo, primeiroAlelo);
		
		ValorGenotipoPreliminarPK genotipoPreliminarAlelo1PK = new ValorGenotipoPreliminarPK();
		genotipoPreliminarAlelo1PK.setLocus(valorLocus.getLocus());
		genotipoPreliminarAlelo1PK.setLocusExamePreliminar(valorLocus);
		genotipoPreliminarAlelo1PK.setPosicaoAlelo(1);
		
		ValorGenotipoPreliminar genotipoPreliminarAlelo1 = new ValorGenotipoPreliminar();
		genotipoPreliminarAlelo1.setId(genotipoPreliminarAlelo1PK);
		genotipoPreliminarAlelo1.setLocus(valorLocus.getLocus());
		genotipoPreliminarAlelo1.setBuscaPreliminar(valorLocus.getBuscaPreliminar());
		genotipoPreliminarAlelo1.setValorAlelo(primeiroAlelo);
		genotipoPreliminarAlelo1.setPosicaoAlelo(1);
		genotipoPreliminarAlelo1.setTipo(ComposicaoAlelo.obterTipo(valorLocus.getPrimeiroAlelo()).getId());
		save(genotipoPreliminarAlelo1);
		
		ValorGenotipoPreliminarPK genotipoPreliminarAlelo2PK = new ValorGenotipoPreliminarPK();
		genotipoPreliminarAlelo2PK.setLocus(valorLocus.getLocus());
		genotipoPreliminarAlelo2PK.setLocusExamePreliminar(valorLocus);
		genotipoPreliminarAlelo2PK.setPosicaoAlelo(2);
		
		ValorGenotipoPreliminar genotipoPreliminarAlelo2 = new ValorGenotipoPreliminar();
		genotipoPreliminarAlelo2.setId(genotipoPreliminarAlelo2PK);
		genotipoPreliminarAlelo2.setLocus(valorLocus.getLocus());
		genotipoPreliminarAlelo2.setBuscaPreliminar(valorLocus.getBuscaPreliminar());
		genotipoPreliminarAlelo2.setValorAlelo(segundoAlelo);
		genotipoPreliminarAlelo2.setPosicaoAlelo(2);
		genotipoPreliminarAlelo2.setTipo(ComposicaoAlelo.obterTipo(valorLocus.getSegundoAlelo()).getId());
		save(genotipoPreliminarAlelo2);
	}
	
	@Override
	public ValorGenotipoPreliminar save(ValorGenotipoPreliminar genotipoPreliminar){
		super.save(genotipoPreliminar);
		genotipoBuscaPreliminarService.salvarGenotipoBusca(genotipoPreliminar);
		return genotipoPreliminar;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<GenotipoDTO> obterGenotipoDtoPorGenotipoPreliminar(List<ValorGenotipoPreliminar> genotiposPreliminares) {
		List<GenotipoDTO> genotiposRetornoDTO = new ArrayList<>();

		List<String> codigoLocusSemDuplicados = recuperaListaComTodosOsLocus(genotiposPreliminares);

		codigoLocusSemDuplicados.forEach(codigo -> {
			List<ValorGenotipoPreliminar> genotiposAgrupados = genotiposPreliminares.stream().filter(genotipo -> {
				return genotipo.getLocus().getCodigo().equals(codigo);
			}).collect(Collectors.toList());

			genotiposRetornoDTO.add(criaGenotipoDTO(genotiposAgrupados));

		});

		Collections.sort(genotiposRetornoDTO, new Comparator<GenotipoDTO>() {

			@Override
			public int compare(GenotipoDTO genotipoDTO1, GenotipoDTO genotipoDTO2) {
				return genotipoDTO1.getOrdem().compareTo(genotipoDTO2.getOrdem());
			}
		});

		return genotiposRetornoDTO;
	}

	private GenotipoDTO criaGenotipoDTO(List<ValorGenotipoPreliminar> genotiposAgrupados) {

		GenotipoDTO genotipoDTO = new GenotipoDTO();

		for (int i = 0; i < genotiposAgrupados.size(); i++) {
			genotipoDTO.setLocus(genotiposAgrupados.get(i).getLocus().getCodigo());
			String alelo = genotiposAgrupados.get(i).getValorAlelo();
			Integer tipo = genotiposAgrupados.get(i).getTipo();
			if (i == 0) {
				genotipoDTO.setPrimeiroAlelo(alelo);
				genotipoDTO.setExamePrimeiroAlelo(null);
				genotipoDTO.setTipoPrimeiroAlelo(tipo);
			}
			else {
				genotipoDTO.setSegundoAlelo(alelo);
				genotipoDTO.setExameSegundoAlelo(null);
				genotipoDTO.setTipoSegundoAlelo(tipo);
			}
			Locus locus = locusService.findById(genotiposAgrupados.get(i).getLocus().getCodigo());
			genotipoDTO.setOrdem(locus.getOrdem());
		}
		return genotipoDTO;
	}

	private List<String> recuperaListaComTodosOsLocus(List<ValorGenotipoPreliminar> genotipos) {
		return genotipos.stream().map(genotipo -> genotipo.getLocus().getCodigo())
				.distinct().collect(Collectors.toList());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ValorGenotipoPreliminar> listarPorBuscaPreliminar(Long idBuscaPreliminar) {
		return genotipoPreliminarRepositorio.findByBuscaPreliminarId(idBuscaPreliminar);
	}

}
