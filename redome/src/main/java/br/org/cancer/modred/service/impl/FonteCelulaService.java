package br.org.cancer.modred.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.FonteCelula;
import br.org.cancer.modred.persistence.IFonteCelulaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IFonteCelulaService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.OrderBy;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade FonteCelula.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class FonteCelulaService extends AbstractService<FonteCelula, Long> implements IFonteCelulaService {
	
	@Autowired
	private IFonteCelulaRepository fonteCelulaRepositorio;
		
	@Override
	public IRepository<FonteCelula, Long> getRepository() {
		return fonteCelulaRepositorio;
	}

	@Override
	public List<FonteCelula> findBySigla(String[] siglas) {
		
		if (siglas == null || siglas.length == 0) {
			return find(new OrderBy("sigla"));
		}
				
		return find(null, Arrays.asList(new Equals<String[]>("sigla", siglas)), Arrays.asList(new OrderBy("sigla")));
		
	}

	@Override
	public FonteCelula obterFonteCelula(Long id) {
		if (id != null) {
			return findById(id);
		}
		return null;
	}



}
