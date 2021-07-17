/**
 * 
 */
package br.org.cancer.modred.service.impl.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.model.ArquivoRelatorioInternacional;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.impl.custom.EntityObserver;
import br.org.cancer.modred.util.AppUtil;

/**
 * @author ergomes
 * 
 * Notificação no upload do relatório na busca internacional
 * 
 * 
 */
@Component
public class UploadBuscaInternacionalObserver extends EntityObserver<ArquivoRelatorioInternacional> {
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected boolean condition(ArquivoRelatorioInternacional arqRelatorio) {
		
		return arqRelatorio.getBusca() != null && arqRelatorio.getCaminho() != null;
	}

	@Override
	protected void process(ArquivoRelatorioInternacional arqRelatorio) {
		
		final Paciente paciente = arqRelatorio.getBusca().getPaciente(); 
		final Usuario usuario = paciente.getMedicoResponsavel().getUsuario();
		
		String mensagem = AppUtil.getMensagem(messageSource, "notificacao.upload.busca.internacional");
		
		CategoriasNotificacao.PEDIDO_EXAME.getConfiguracao()
		.criar()
		.paraUsuario(usuario.getId())
		.comPaciente(paciente.getRmr())
		.comDescricao(mensagem)
		.apply();
	}
}
