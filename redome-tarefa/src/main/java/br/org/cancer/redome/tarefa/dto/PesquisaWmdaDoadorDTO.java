package br.org.cancer.redome.tarefa.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.org.cancer.redome.tarefa.dto.ResultadoDoadorWmdaDTO.WmdaHla;
import br.org.cancer.redome.tarefa.model.Locus;
import br.org.cancer.redome.tarefa.model.domain.Abo;
import br.org.cancer.redome.tarefa.model.domain.TiposDoador;
import br.org.cancer.redome.tarefa.util.AppUtil;
import br.org.cancer.redome.tarefa.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe DTO que representa a pesquisa das informações do doador compativel no WMDA.
 * 
 * @author ergomes
 *
 */
@Data @NoArgsConstructor @Builder(toBuilder = true) @AllArgsConstructor
public class PesquisaWmdaDoadorDTO implements Serializable {

	private static final long serialVersionUID = 9154266415191455142L;

	private Long id;
	private String identificacao;
	private String grid;
	private String donPool;
	private String jsonDoador;
	private String logPesquisaDoador;
	private Long pesquisaWmdaId;
	private Long buscaId;
	
	private DoadorWmdaDTO doadorWmdaDto;
	
	public static PesquisaWmdaDoadorDTO parse(ResultadoDoadorWmdaDTO doadorWmda) {
		
		if(doadorWmda == null || doadorWmda.getSex() == null || doadorWmda.getHla() == null) {
			return null;
		}
		if(obterTipoDoador(doadorWmda.getDonorType()).equals(TiposDoador.CORDAO_INTERNACIONAL.getId())) {
			if(doadorWmda.getTnc() == null || doadorWmda.getCd34pc() == null || doadorWmda.getVol() == null) {
				return null;
			}
		}
			
		List<QualificacaoMatchDTO> qualificacoes = new ArrayList<>(); 
		
		AppUtil.formatarQualificacao(doadorWmda.getNmdpGrade()).forEach((locus,qualificacao) -> {
			QualificacaoMatchDTO qualificacaoDto = QualificacaoMatchDTO.builder()
					.locus(locus)
					.qualificacao(qualificacao)
					.probabilidade(ProbabilidadeDTO.parseProbabilidade(locus, doadorWmda))
					.build();
			
			qualificacoes.add(qualificacaoDto);
		});

		MatchWmdaDTO matchWmda = MatchWmdaDTO.builder()
			.matchGrade(AppUtil.formatarMatchGrade(doadorWmda.getMatchClass()))
			.tipoDoador(obterTipoDoador(doadorWmda.getDonorType()))
			.probabilidade0(doadorWmda.getP0())
			.probabilidade1(doadorWmda.getP1())
			.probabilidade2(doadorWmda.getP2())
			.qualificacoes(qualificacoes)
			.build();
		
		DoadorWmdaDTO pesDoadorRetorno = DoadorWmdaDTO.builder()
				.identificacao(doadorWmda.getId())
				.grid(doadorWmda.getGrid())
				.donPool(doadorWmda.getDonPool())
				.jsonDoador(doadorWmda.getJsonDonors())
				.dataNascimento(DateUtils.converterParaLocalDate(doadorWmda.getBirthDate()))
				.tipoDoador(doadorWmda.getDonorType().equals("D") ? TiposDoador.INTERNACIONAL.getId() : TiposDoador.CORDAO_INTERNACIONAL.getId())
				.sexo(doadorWmda.getSex())
				.abo(Abo.obterValorAboNormalizado(doadorWmda.getRhesus(), doadorWmda.getAbo()))
				.volume(doadorWmda.getVol() != null ? new BigDecimal(doadorWmda.getVol()) : null)
				.quantidadeTotalTCN(doadorWmda.getTnc() != null ? new BigDecimal(doadorWmda.getTnc()) : null)
				.quantidadeTotalCD34(doadorWmda.getCd34pc() != null ? new BigDecimal(doadorWmda.getCd34pc()) : null)
				.locusExame(obterValoresHlaNormalizado(doadorWmda.getHla()))
				.matchWmdaDto(matchWmda)
				.build();
		
		return PesquisaWmdaDoadorDTO.builder()
				.identificacao(doadorWmda.getId())
				.grid(doadorWmda.getGrid())
				.donPool(doadorWmda.getDonPool())
				.jsonDoador(doadorWmda.getJsonDonors())
				.doadorWmdaDto(pesDoadorRetorno)
				.build();
	}

	private static Long obterTipoDoador(String tipo) {
		return tipo.equals("D") ? TiposDoador.INTERNACIONAL.getId() : TiposDoador.CORDAO_INTERNACIONAL.getId();
	}

	
	private static List<LocusExameWmdaDTO> obterValoresHlaNormalizado(WmdaHla hla) {

		List<LocusExameWmdaDTO> locusExame = new ArrayList<>();
		
		if(hla.getA().getDna() != null && (hla.getA().getDna().getField1() != null || hla.getA().getDna().getField2() != null)) {
			
			locusExame.add(LocusExameWmdaDTO.builder().codigo(Locus.LOCUS_A)
				.primeiroAlelo(AppUtil.normalizadoAlelo1(hla.getA().getDna().getField1(), hla.getA().getDna().getField2()))
				.segundoAlelo(AppUtil.normalizadoAlelo2(hla.getA().getDna().getField2(), hla.getA().getDna().getField1()))
				.build());
		}

		if(hla.getB().getDna() != null && (hla.getB().getDna().getField1() != null || hla.getB().getDna().getField2() != null)) {
			locusExame.add(LocusExameWmdaDTO.builder().codigo(Locus.LOCUS_B)
				.primeiroAlelo(AppUtil.normalizadoAlelo1(hla.getB().getDna().getField1(), hla.getB().getDna().getField2()))
				.segundoAlelo(AppUtil.normalizadoAlelo2(hla.getB().getDna().getField2(), hla.getB().getDna().getField1()))
				.build());
		}

		if(hla.getC().getDna() != null && (hla.getC().getDna().getField1() != null || hla.getC().getDna().getField2() != null)) {
			locusExame.add(LocusExameWmdaDTO.builder().codigo(Locus.LOCUS_C)
				.primeiroAlelo(AppUtil.normalizadoAlelo1(hla.getC().getDna().getField1(), hla.getC().getDna().getField2()))
				.segundoAlelo(AppUtil.normalizadoAlelo2(hla.getC().getDna().getField2(), hla.getC().getDna().getField1()))
				.build());
		}

		if(hla.getDrb1().getDna() != null && (hla.getDrb1().getDna().getField1() != null || hla.getDrb1().getDna().getField2() != null)) {
			locusExame.add(LocusExameWmdaDTO.builder().codigo(Locus.LOCUS_DRB1)
				.primeiroAlelo(AppUtil.normalizadoAlelo1(hla.getDrb1().getDna().getField1(), hla.getDrb1().getDna().getField2()))
				.segundoAlelo(AppUtil.normalizadoAlelo2(hla.getDrb1().getDna().getField2(), hla.getDrb1().getDna().getField1()))
				.build());
		}

		if(hla.getDqb1().getDna() != null && (hla.getDqb1().getDna().getField1() != null || hla.getDqb1().getDna().getField2() != null)) {
			locusExame.add(LocusExameWmdaDTO.builder().codigo(Locus.LOCUS_DQB1)
				.primeiroAlelo(AppUtil.normalizadoAlelo1(hla.getDqb1().getDna().getField1(), hla.getDqb1().getDna().getField2()))
				.segundoAlelo(AppUtil.normalizadoAlelo2(hla.getDqb1().getDna().getField2(), hla.getDqb1().getDna().getField1()))
				.build());
		}

		if(hla.getDpa1().getDna() != null && (hla.getDpa1().getDna().getField1() != null || hla.getDpa1().getDna().getField2() != null)) {
			locusExame.add(LocusExameWmdaDTO.builder().codigo(Locus.LOCUS_DPA1)
				.primeiroAlelo(AppUtil.normalizadoAlelo1(hla.getDpa1().getDna().getField1(), hla.getDpa1().getDna().getField2()))
				.segundoAlelo(AppUtil.normalizadoAlelo2(hla.getDpa1().getDna().getField2(), hla.getDpa1().getDna().getField1()))
				.build());
		}

		if(hla.getDpb1().getDna() != null && (hla.getDpb1().getDna().getField1() != null || hla.getDpb1().getDna().getField2() != null)) {
			locusExame.add(LocusExameWmdaDTO.builder().codigo(Locus.LOCUS_DPB1)
				.primeiroAlelo(AppUtil.normalizadoAlelo1(hla.getDpb1().getDna().getField1(), hla.getDpb1().getDna().getField2()))
				.segundoAlelo(AppUtil.normalizadoAlelo2(hla.getDpb1().getDna().getField2(), hla.getDpb1().getDna().getField1()))
				.build());
		}

//		if(hla.getDrb3().getDna() != null && (hla.getDrb3().getDna().getField1() != null || hla.getDrb3().getDna().getField2() != null)) {
//			locusExame.add(LocusExameWmdaDTO.builder().codigo(Locus.LOCUS_DRB3)
//				.primeiroAlelo(AppUtil.normalizadoAlelo1(hla.getDrb3().getDna().getField1(), hla.getDrb3().getDna().getField2()))
//				.segundoAlelo(AppUtil.normalizadoAlelo2(hla.getDrb3().getDna().getField2(), hla.getDrb3().getDna().getField1()))
//				.build());
//		}

//		if(hla.getDrb4().getDna() != null && (hla.getDrb4().getDna().getField1() != null || hla.getDrb4().getDna().getField2() != null)) {
//			locusExame.add(LocusExameWmdaDTO.builder().codigo(Locus.LOCUS_DRB4)
//				.primeiroAlelo(AppUtil.normalizadoAlelo1(hla.getDrb4().getDna().getField1(), hla.getDrb4().getDna().getField2()))
//				.segundoAlelo(AppUtil.normalizadoAlelo2(hla.getDrb4().getDna().getField2(), hla.getDrb4().getDna().getField1()))
//				.build());
//		}

//		if(hla.getDrb5().getDna() != null && (hla.getDrb5().getDna().getField1() != null || hla.getDrb5().getDna().getField2() != null)) {
//			locusExame.add(LocusExameWmdaDTO.builder().codigo(Locus.LOCUS_DRB5)
//				.primeiroAlelo(AppUtil.normalizadoAlelo1(hla.getDrb5().getDna().getField1(), hla.getDrb5().getDna().getField2()))
//				.segundoAlelo(AppUtil.normalizadoAlelo2(hla.getDrb5().getDna().getField2(), hla.getDrb5().getDna().getField1()))
//				.build());
//		}
		
		return locusExame;
	}

}