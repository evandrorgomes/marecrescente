package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.model.DialogoBusca;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TiposBuscaChecklist;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IDialogoBuscaRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IBuscaChecklistService;
import br.org.cancer.modred.service.IDialogoBuscaService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de implementação de métodos de negócio de diálogo de busca.
 * @author Filipe Paes
 *
 */
@Service
public class DialogoBuscaService   extends AbstractService<DialogoBusca, Long>  implements IDialogoBuscaService {

	@Autowired
	private IDialogoBuscaRepository dialogoBuscaRepository;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IBuscaChecklistService buscaChecklistService;
	
	@Override
	public List<DialogoBusca> listarDialogos(Long idBusca) {
		return dialogoBuscaRepository.findByBuscaIdOrderByDataHoraMensagemAsc(idBusca);
	}

	@Override
	public void salvarDialogo(DialogoBusca dialogo) {
		Usuario usuarioLogado = usuarioService.obterUsuarioLogado();
		dialogo.setUsuario(usuarioLogado);
		boolean present = usuarioLogado.getPerfis().stream().filter(p -> p.getId().equals(Perfis.MEDICO.getId())).findAny().isPresent();
		if(present){
			boolean existe = buscaChecklistService.existeBuscaChecklistEmAberto(dialogo.getBusca().getId(), TiposBuscaChecklist.DIALOGO_MEDICO.getId());
			if(!existe) {
				buscaChecklistService.criarItemCheckList(dialogo.getBusca(), TiposBuscaChecklist.DIALOGO_MEDICO);
			}
		}
		dialogo.setDataHoraMensagem(LocalDateTime.now());
		save(dialogo);
	}

	@Override
	public IRepository<DialogoBusca, Long> getRepository() {
		return dialogoBuscaRepository;
	}
	
}
