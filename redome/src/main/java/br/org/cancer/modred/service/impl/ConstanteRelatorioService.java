package br.org.cancer.modred.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.ConstanteRelatorio;
import br.org.cancer.modred.persistence.IConstanteRelatorioRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IConstanteRelatorioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe para metodos de negocio do tipo de transplante.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class ConstanteRelatorioService extends AbstractService<ConstanteRelatorio, String> implements IConstanteRelatorioService {

	@Autowired
	private IConstanteRelatorioRepository constanteRelatorioRepository;
	
	@Override
	public IRepository<ConstanteRelatorio, String> getRepository() {
		return constanteRelatorioRepository;
	}

	@Override
	public List<String> listarCodigos() {
		return findAll().stream().map(constante -> "&" + constante.getCodigo().replaceAll("&amp;", "") + "&" ).collect(Collectors.toList());
	}

	@Override
	public String obterValorConstante(String codigo) {
		ConstanteRelatorio constante = findById(codigo.replaceAll("&", ""));
		if (constante == null) {
			throw new BusinessException("erro.relatorio.constante.nao.encontrada");
		}
		
		return constante.getValor();
	}

	@Override
	public void atualizarValorConstante(String codigo, String valor) {
		ConstanteRelatorio constante = findById(codigo.replaceAll("&", ""));
		if (constante == null) {
			throw new BusinessException("erro.relatorio.constante.nao.encontrada");
		}
		constante.setValor(valor);
		
		save(constante);
	}

	

}
