package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.controller.dto.AnaliseMatchPreliminarDTO;
import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.dto.StatusRetornoInclusaoDTO;
import br.org.cancer.modred.model.BuscaPreliminar;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.FiltroMatch;
import br.org.cancer.modred.persistence.IBuscaPreliminarRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IBuscaPreliminarService;
import br.org.cancer.modred.service.IMatchPreliminarService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.IValorGenotipoPreliminarService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Projection;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementa os métodos e as regras de negócio envolvendo a entidade BuscaPreliminar.
 * 
 * @author Pizão
 */
@Service
@Transactional
public class BuscaPreliminarService extends AbstractService<BuscaPreliminar, Long> implements IBuscaPreliminarService{

	@Autowired
	private IBuscaPreliminarRepository buscaPreliminarRepositorio;

	@Autowired
	private IValorGenotipoPreliminarService genotipoPreliminarService;
	
	@Autowired
	private IMatchPreliminarService matchPreliminarService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	
	@Override
	public IRepository<BuscaPreliminar, Long> getRepository() {
		return buscaPreliminarRepositorio;
	}
	
	@Override
	public BuscaPreliminar save(BuscaPreliminar buscaPreliminar) {
		buscaPreliminar.setDataCadastro(LocalDateTime.now());
		buscaPreliminar.setUsuario(usuarioService.obterUsuarioLogado());
		
		return super.save(buscaPreliminar);
	}
	
	@Override
	public RetornoInclusaoDTO realizarBuscaPreliminar(BuscaPreliminar buscaPreliminar) {
		buscaPreliminar.getLocusExamePreliminar().stream().forEach(locusExamePreliminar -> 
			locusExamePreliminar.setBuscaPreliminar(buscaPreliminar)
		);
		
		final BuscaPreliminar buscaPreliminarSalva = save(buscaPreliminar);
		genotipoPreliminarService.salvarGenotipo(buscaPreliminar.getLocusExamePreliminar());
		
		//matchPreliminarService.criarMatchPreliminar(buscaPreliminarSalva.getId());
		
		matchPreliminarService.executarProcedureMatch(buscaPreliminar.getId());
		
		RetornoInclusaoDTO retorno = new RetornoInclusaoDTO();
		retorno.setIdObjeto(buscaPreliminarSalva.getId());
		
		retorno.setStatus(StatusRetornoInclusaoDTO.SUCESSO);
		return retorno;
	}

	@Override
	public AnaliseMatchPreliminarDTO obterListasMatchsPreliminares(Long idBuscaPreliminar, FiltroMatch filtro) {
		AnaliseMatchPreliminarDTO dto = new AnaliseMatchPreliminarDTO();
		
		Projection id = new Projection("id");
		Projection nomePaciente = new Projection("nomePaciente");
		Projection dataNascimento = new Projection("dataNascimento");
		Projection abo = new Projection("abo");
		Projection peso = new Projection("peso");
		dto.setBuscaPreliminar(
				findOne(Arrays.asList(id, nomePaciente, dataNascimento, abo, peso), 
						Arrays.asList(new Equals<Long>("id", idBuscaPreliminar))));
		
		dto.setTotalMedula(matchPreliminarService.obterQuantidadeMatchsMedula(idBuscaPreliminar));
		dto.setTotalCordao(matchPreliminarService.obterQuantidadeMatchsCordao(idBuscaPreliminar));
				
		dto.setListaFase1(matchPreliminarService.listarMatchsPreliminares(idBuscaPreliminar, filtro, 
				Arrays.asList(FasesMatch.FASE_1, FasesMatch.EXAME_EXTENDIDO) ));
		dto.setListaFase2(matchPreliminarService.listarMatchsPreliminares(idBuscaPreliminar, filtro, 
				Arrays.asList(FasesMatch.FASE_2, FasesMatch.TESTE_CONFIRMATORIO)));
		dto.setListaFase3(matchPreliminarService.listarMatchsPreliminares(idBuscaPreliminar, filtro, Arrays.asList(FasesMatch.FASE_3)));
		
		return dto;
	}
	
	
}
