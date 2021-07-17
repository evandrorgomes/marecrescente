package br.org.cancer.modred.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.GenotipoPaciente;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.ValorGenotipoPaciente;
import br.org.cancer.modred.util.ListUtil;

/**
 * Dto para dados do Wmda de paciente.
 * @author Filipe Paes
 *
 */
public class PacienteWmdaDTO {

	private Long rmr;
	private BigDecimal peso;
	private LocalDate dataNascimento;
	private String abo;
	private String sexo;
	List<LocusExameDto> locusExame;
	private String wmdaId;

	public Long getRmr() {
		return rmr;
	}

	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getAbo() {
		return abo;
	}

	public void setAbo(String abo) {
		this.abo = abo;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<LocusExameDto> getLocusExame() {
		return locusExame;
	}

	public void setLocusExame(List<LocusExameDto> locusExame) {
		this.locusExame = locusExame;
	}
	
	/**
	 * @return the wmdaId
	 */
	public String getWmdaId() {
		return wmdaId;
	}

	/**
	 * @param wmdaId the wmdaId to set
	 */
	public void setWmdaId(String wmdaId) {
		this.wmdaId = wmdaId;
	}

	public PacienteWmdaDTO parse(Paciente paciente, GenotipoPaciente genotipoPaciente) {
		PacienteWmdaDTO pacienteRetorno = new PacienteWmdaDTO();
		pacienteRetorno.setRmr(paciente.getRmr());
		pacienteRetorno.setAbo(paciente.getAbo());
		pacienteRetorno.setDataNascimento(paciente.getDataNascimento());
		List<Evolucao>  evolucoes = paciente.getEvolucoes();
		if(evolucoes != null && !evolucoes.isEmpty()) {
			evolucoes.sort((e1,e2) -> e1.getDataCriacao().compareTo(e2.getDataCriacao()));
			pacienteRetorno.setPeso(evolucoes.get(0).getPeso());
		}
		pacienteRetorno.setLocusExame(converterParaGenotipoDto(genotipoPaciente));
		pacienteRetorno.setWmdaId(paciente.getWmdaId());
		return pacienteRetorno;
	}

	private List<LocusExameDto> converterParaGenotipoDto(GenotipoPaciente genotipoPaciente) {
		List<LocusExameDto> listaLocusExameDto = new ArrayList<LocusExameDto>();
		genotipoPaciente.getValores()
		.stream().filter(ListUtil.distinctByKey(v -> v.getLocus())).forEach(g ->{
			listaLocusExameDto.addAll(obterValoresLocus(g.getLocus().getCodigo(), genotipoPaciente.getValores()));			
		});
		return listaLocusExameDto;
	}

	
	private List<LocusExameDto> obterValoresLocus(String nomeLocus, List<ValorGenotipoPaciente> valoresGenotipo) {
		List<ValorGenotipoPaciente> valores = valoresGenotipo.stream().filter(g -> nomeLocus.equals(g.getLocus().getCodigo())).collect(Collectors.toList());
		List<LocusExameDto> listaLocusExameDto = new ArrayList<LocusExameDto>();
		listaLocusExameDto.add(new LocusExameDto(valores.get(0).getLocus().getCodigo(), valores.get(0).getAlelo(), valores.size() > 1? valores.get(1).getAlelo():null));
		return listaLocusExameDto;
	}
}
