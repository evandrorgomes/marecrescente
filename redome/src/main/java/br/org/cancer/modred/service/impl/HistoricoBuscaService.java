package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.HistoricoBusca;
import br.org.cancer.modred.persistence.IHistoricoBuscaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IHistoricoBuscaService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * 
 * @author Pizão
 * 
 * Classe que implementa as funcionalidades envolvendos a entidade HistoricoBusca.
 *
 */
@Service
public class HistoricoBuscaService extends AbstractService<HistoricoBusca, Long> implements IHistoricoBuscaService {

	@Autowired
	private IHistoricoBuscaRepository historicoBuscaRepositorio;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	
	@Override
	public IRepository<HistoricoBusca, Long> getRepository() {
		return historicoBuscaRepositorio;
	}

	@Override
	public void registrar(Busca buscaAtiva, String justificativa) {
		HistoricoBusca historico = new HistoricoBusca();
		historico.setDataAtualizacao(LocalDateTime.now());
		historico.setBusca(buscaAtiva);
		historico.setCentroTransplanteAnterior(buscaAtiva.getCentroTransplante());
		historico.setUsuario(usuarioService.obterUsuarioLogado());
		historico.setJustificativa(justificativa);
		save(historico);
	}

}
