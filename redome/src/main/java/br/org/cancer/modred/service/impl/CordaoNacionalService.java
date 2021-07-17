package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.CordaoNacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.ExameCordaoNacional;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.LocusExamePk;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.domain.StatusExame;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.persistence.ICordaoNacionalRepository;
import br.org.cancer.modred.persistence.IDoadorRepository;
import br.org.cancer.modred.service.IBancoSangueCordaoService;
import br.org.cancer.modred.service.IControleJobBrasilcordService;
import br.org.cancer.modred.service.ICordaoNacionalService;
import br.org.cancer.modred.service.IGenotipoDoadorService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.Projection;
import br.org.cancer.modred.vo.CordaoNacionalVo;

/**
 * Classe de funcionalidades envolvendo a entidade CordaoNacional.
 * 
 * @author bruno.sousa.
 *
 */
@Service
@Transactional
public class CordaoNacionalService extends DoadorService<CordaoNacional> implements ICordaoNacionalService {

	@Autowired
	private IGenotipoDoadorService<ExameCordaoNacional> genotipoService;
	
	@Autowired
	private IBancoSangueCordaoService bancoService;
	
	@Autowired
	private IControleJobBrasilcordService controleJobService;
	
	@Autowired
	private ICordaoNacionalRepository cordaoNacionalRepository;
	
	@Autowired
	private ExameDoadorService<ExameCordaoNacional> exameCordaoService;
	

	@Override
	public IDoadorRepository getRepository() {
		return cordaoNacionalRepository;
	}
	
	@Override
	public String obterIdBancoSangueCordaoFormatadoPorIdDoador(Long idDoador) {
		Filter<Long> filtrarPorId = new Equals<Long>("id", idDoador);
		Projection projectionDmr = new Projection("idBancoSangueCordao");
		Projection projectionBancoSangueCordao = new Projection("bancoSangueCordao");

		CordaoNacional cordao = 
				(CordaoNacional) findOne(Arrays.asList(projectionDmr, projectionBancoSangueCordao), Arrays.asList(filtrarPorId));
				
		return cordao.obterIdBancoSangueCordaoFormatado(); 
	}
	
	@Override
	public List<Doador> findAll(){
		return find(new Equals<Long>("tipoDoador", TiposDoador.CORDAO_NACIONAL.getId()));
	}

	@Override
	public void salvarCordao(CordaoNacional cordao) {
		cordao.getExames().forEach(exame -> {
			exame.setStatusExame(StatusExame.CONFERIDO.getCodigo());
			exame.setCordao(cordao);
		});
		
		if(cordao.getId() != null){
			cordao.getExames().stream().forEach(exame ->{
				exameCordaoService.save(exame);
			});
		}
		
		cordao.setDataAtualizacao(LocalDateTime.now());
		save(cordao);
		
		genotipoService.gerarGenotipo(cordao);
	}

	@Override
	public CordaoNacional parseCordaoVoParaCordaoModel(CordaoNacionalVo cordaoVo) {
		CordaoNacional cordaoNacional = new CordaoNacional();
		List<ExameCordaoNacional> exames = null;

		cordaoNacional.setIdBancoSangueCordao(cordaoVo.getNumeroFicha());
		cordaoNacional.setBancoSangueCordao(bancoService.obterBancoPorIdBrasilcord(cordaoVo.getIdBanco()));
		
		
		CordaoNacional cordaoLocalizado = cordaoNacionalRepository.findByIdBancoSangueCordaoAndBancoSangueCordaoId(cordaoNacional.getIdBancoSangueCordao()
				, cordaoNacional.getBancoSangueCordao().getId());
		if(cordaoLocalizado != null){
			cordaoNacional = cordaoLocalizado;
			exames = cordaoNacional.getExames();
		}
		else{
			 exames = new ArrayList<ExameCordaoNacional>();
		}
		cordaoNacional.setAbo(cordaoVo.getAbo());
		cordaoNacional.setQuantidadeTotalCD34(cordaoVo.getCd34Final());
		cordaoNacional.setQuantidadeTotalTCN(cordaoVo.getQuantidadeTotalTcn());
		cordaoNacional.setSexo(cordaoVo.getSexo());
		cordaoNacional.setTotalLinfocitos(cordaoVo.getTotalLinfocitos());
		cordaoNacional.setTotalMonocitos(cordaoVo.getTotalMonocitos());
		cordaoNacional.setTotalGranulocitos(cordaoVo.getTotalGranulocitos());
		cordaoNacional.setVolumeTotalAntes(cordaoVo.getVolumeTotalAntes());		
		cordaoNacional.setVolumeTotalDepois(cordaoVo.getVolumeTotalDepois());
		cordaoNacional.setVolumeTotalReal(cordaoVo.getVolumeTotalReal());
		cordaoNacional.setVolumeTotalTCNAntes(cordaoVo.getVolumeTotalTCNAntes());
		cordaoNacional.setTotalPercentualHematocritos(cordaoVo.getTotalPercentualHematocritos());
		cordaoNacional.setStatusDoador(cordaoNacional.getStatusDoador() == null ? new StatusDoador(StatusDoador.ATIVO) : cordaoNacional.getStatusDoador());
		cordaoNacional.setRegistroOrigem(cordaoNacional.getRegistroOrigem() == null ? new Registro(Registro.ID_REGISTRO_REDOME): cordaoNacional.getRegistroOrigem());
		cordaoNacional.setTipoDoador(TiposDoador.CORDAO_NACIONAL);
		
		ExameCordaoNacional exame = new ExameCordaoNacional();
		exame.setCordao(cordaoNacional);
		exame.setStatusExame(StatusExame.CONFERIDO.getCodigo());
		List<LocusExame> listaLocus = new ArrayList<LocusExame>();
		
		cordaoVo.getResultadoHla().stream().forEach( cordao ->{
			LocusExame locusExame = new LocusExame();
			LocusExamePk pkLocus = new LocusExamePk();
			pkLocus.setExame(exame);
			Locus locus = new Locus();
			locus.setCodigo(cordao.getLocus());
			pkLocus.setLocus(locus );
			locusExame.setId(pkLocus);
			locusExame.setPrimeiroAlelo(cordao.getPrimeiroAlelo());
			locusExame.setSegundoAlelo(cordao.getSegundoAlelo());
			listaLocus.add(locusExame );
		});
		
		exame.setLocusExames(listaLocus );
		exames.add(exame);
		cordaoNacional.setExames(exames );
		return cordaoNacional;
	}

	@Override
	public void salvarCordoesImportados(List<CordaoNacionalVo> cordoesVo) {
		cordoesVo.stream().forEach(c ->{
			this.salvarCordao(parseCordaoVoParaCordaoModel(c));
		});
		this.controleJobService.registrarAcesso();
	}
}
