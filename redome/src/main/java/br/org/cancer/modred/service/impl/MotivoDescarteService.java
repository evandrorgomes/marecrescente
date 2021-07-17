package br.org.cancer.modred.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.MotivoDescarte;
import br.org.cancer.modred.persistence.IMotivoDescarteRepository;
import br.org.cancer.modred.service.IMotivoDescarteService;

/**
 * Implementação da interface de serviço de motivos de descarte.
 * 
 * @author Diogo Paraíso
 *
 */
@Service
@Transactional
public class MotivoDescarteService implements IMotivoDescarteService {

	@Autowired
	private IMotivoDescarteRepository motivoDescarteRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MotivoDescarte> obterMotivosDescarte() {
		List<MotivoDescarte> motivosDescarte = motivoDescarteRepository.findAll();

		if (motivosDescarte == null) {
			throw new BusinessException("exame.motivodescarte.nao.encontrado");
		}
		return motivosDescarte;
	}

	@Override
	public MotivoDescarte obterMotivoDescarte(Long motivoDescarteId) {
		return motivoDescarteRepository.findById(motivoDescarteId).get();
	}
}
