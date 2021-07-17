package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.ControleJobBrasilcord;
import br.org.cancer.modred.persistence.IControleJobBrasilcordRepository;
import br.org.cancer.modred.service.IControleJobBrasilcordService;

/**
 * Implementação de métodos de negócio de Controle de Job BrasilCord.
 * @author Filipe Paes
 *
 */
@Service
public class ControleJobBrasilcordService  implements IControleJobBrasilcordService{
	
	@Autowired
	private IControleJobBrasilcordRepository controleJobBrasilcordRepository;

	@Override
	public void registrarAcesso() {
		ControleJobBrasilcord controle = new ControleJobBrasilcord();
		controle.setDataAcesso(LocalDateTime.now());
		controle.setSucesso(true);
		controleJobBrasilcordRepository.save(controle);
	}

}
