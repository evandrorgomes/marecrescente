package br.org.cancer.modred.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.BancoSangueCordao;
import br.org.cancer.modred.model.UsuarioBancoSangueCordao;
import br.org.cancer.modred.persistence.IBancoSangueCordaoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IBancoSangueCordaoService;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe que implementa as operações envolvendo a classe BancoSangueCordao.
 * É a camada que centraliza as regras de negócio.
 * 
 * @author Pizão
 *
 */
@Service
@Repository
public class BancoSangueCordaoService extends AbstractService<BancoSangueCordao, Long> implements IBancoSangueCordaoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BancoSangueCordaoService.class);
	
	@Autowired
	private IBancoSangueCordaoRepository bancoRepository;


	@Override
	public IRepository<BancoSangueCordao, Long> getRepository() {
		return bancoRepository;
	}
	
	@Override
	public BancoSangueCordao obterBancoPorIdBrasilcord(Long idBrasilCord) {
		return bancoRepository.findByIdBancoSangueCordao(idBrasilCord);
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(BancoSangueCordao bancoSangue) {
		BancoSangueCordao bancoSangueMesmaSigla = bancoRepository.findBySigla(bancoSangue.getSigla());
		if(bancoSangueMesmaSigla != null){
			LOGGER.error("Sigla já está sendo utilizada para o banco de ID: " + bancoSangueMesmaSigla.getId());
			throw new BusinessException("sigla.banco.duplicada.mensagem.erro", bancoSangue.getSigla());
		}
		return super.validateEntity(bancoSangue);
	}

	@Override
	public Long salvarBancoCordao(BancoSangueCordao bancoSangueCordao) {
		BancoSangueCordao bancoSangueBase = obterBancoPorIdBrasilcord(bancoSangueCordao.getIdBancoSangueCordao());
		BancoSangueCordao bancoSangueAtualizado = bancoSangueCordao.clone();
		
		if(bancoSangueBase != null){
			bancoSangueAtualizado.setId(bancoSangueBase.getId());
		}
		
		bancoSangueAtualizado = save(bancoSangueAtualizado);
		
		return bancoSangueAtualizado.getId();
	}

	@Override
	public List<UsuarioBancoSangueCordao> listarUsuariosAtivos(Long idBancoSangueCordao) {
		return bancoRepository.listaUsuariosAtivos(idBancoSangueCordao);
	}

}
