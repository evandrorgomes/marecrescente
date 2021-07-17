/**
 * 
 */
package br.org.cancer.modred.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.controller.dto.genotipo.ValorAlelo;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.GenotipoPaciente;
import br.org.cancer.modred.model.IValorGenotipoBusca;
import br.org.cancer.modred.model.IValorGenotipoExpandido;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.ValorGenotipoBuscaPaciente;
import br.org.cancer.modred.model.ValorGenotipoExpandidoPaciente;
import br.org.cancer.modred.model.ValorGenotipoPaciente;
import br.org.cancer.modred.model.ValorGenotipoPacientePK;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IValorGenotipoRepository;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.IGenotipoPacienteService;
import br.org.cancer.modred.service.IValorGenotipoBuscaService;
import br.org.cancer.modred.service.IValorGenotipoExpandidoService;
import br.org.cancer.modred.service.IValorGenotipoPacienteService;

/**
 * Classe de funcionalidades envolvendo a entidade ValorGenotipoPaciente.
 * É uma extensão da classe ValorGenotipoService, dando somente tipagem 
 * e definindo métodos específicos para o paciente.
 * 
 * @author Pizão
 */
@Service
public class ValorGenotipoPacienteService extends ValorGenotipoService<ValorGenotipoPaciente, ValorGenotipoPacientePK> implements IValorGenotipoPacienteService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValorGenotipoPacienteService.class);

	@Autowired
	private IValorGenotipoRepository valorGenotipoPacienteRepositorio;
	
	@Autowired
	private IExamePacienteService exameService;
	
	@Autowired
	private IGenotipoPacienteService genotipoService;
	
	@Autowired
	private IValorGenotipoBuscaService valorGenotipoBuscaPacienteService;
	
	@Autowired
	private IValorGenotipoExpandidoService valorGenotipoExpandidoPacienteService;
	
	
	@Override
	public IRepository<ValorGenotipoPaciente, ValorGenotipoPacientePK> getRepository() {
		return valorGenotipoPacienteRepositorio;
	}

	@Override
	public ValorGenotipoPaciente criarInstanciaValorGenotipo(ValorAlelo alelo) {
		return new ValorGenotipoPaciente(alelo);
	}
	
	@Override
	public IValorGenotipoBusca criarInstanciaValorGenotipoBusca() {
		return new ValorGenotipoBuscaPaciente();
	}

	@Override
	public IValorGenotipoExpandido criarInstanciaValorGenotipoExpandido() {
		return new ValorGenotipoExpandidoPaciente();
	}

	@Override
	public List<ValorGenotipoPaciente> gerarGenotipoPaciente(Long rmr) {
		if (rmr == null) {
			LOGGER.error("RMR não foi informado para obtenção do genótipo do paciente.");
			throw new BusinessException("erro.requisicao");
		}
		List<ExamePaciente> exames = exameService.listarInformacoesExames(rmr);
		return gerarGenotipo(exames);
	}
	
	@Override
	public List<GenotipoDTO> obterGenotipoPacienteDto(Long rmr) {
		final GenotipoPaciente genotipoPaciente = genotipoService.obterGenotipoPorPaciente(rmr);
		return obterGenotipoDto(genotipoPaciente);
	}

	@Override
	public void excluirValoresGenotiposPaciente(Long genotipoId) {
		valorGenotipoPacienteRepositorio.deletarValoresGenotipo(genotipoId);
	}
	
	@SuppressWarnings("unchecked")	
	@Override
	public List<ValorGenotipoPaciente> salvar(
			List<ValorGenotipoPaciente> valoresGenotipos, GenotipoPaciente genotipo, Paciente paciente) {
		valoresGenotipos.stream().forEach(valorGenotipo -> {			
			valorGenotipo.setGenotipo(genotipo);
			List<ValorGenotipoBuscaPaciente> valoresGenotipoBusca = 
					(List<ValorGenotipoBuscaPaciente>) gerarGenotipoBusca(valorGenotipo);
			
			valorGenotipoBuscaPacienteService.saveAll(valoresGenotipoBusca);
			
			List<ValorGenotipoExpandidoPaciente> valoresGenotipoExpandidoDoador = 
					(List<ValorGenotipoExpandidoPaciente>) gerarGenotipoExpandido(valorGenotipo, paciente);
			valoresGenotipoExpandidoDoador.forEach(valorGenotipoExpandidoPaciente -> {								
				valorGenotipoExpandidoPaciente.setPaciente(paciente);
			});
			
			valorGenotipoExpandidoPacienteService.saveAll(valoresGenotipoExpandidoDoador);
		});

		return valorGenotipoPacienteRepositorio.saveAll(valoresGenotipos);
	}
}
