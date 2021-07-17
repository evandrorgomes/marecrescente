package br.org.cancer.modred.service.impl.invocation;

import java.awt.IllegalComponentStateException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.service.ILogEvolucaoService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * 
 * @author Pizão
 * 
 *         Classe abstrata de serviço destinada a "marcar" quem são as entidades/services 
 *         que deverão, a cada nova atualização no banco, gerar entrada de log de evolução.
 *         Isso visa centralizar e generalizar a geração dos eventos
 *         de log (ver entidade LogEvolucao), evitando a replicação de código.
 *
 * @param <T>
 *            tipo da entidade a ser utilizada.
 * @param <I>
 *            tipo do ID da entidade.
 * 
 */
@Transactional
public abstract class AbstractLoggingService<T, I extends Serializable> extends AbstractService<T, I>
		implements IService<T, I>, ILogging<T> {
	
	@Autowired
	private ILogEvolucaoService logEvolucaoService;
	
	@Autowired
	protected IUsuarioService usuarioService;
	
	@Autowired
	private LogAspect logAspect;
	
	
	public AbstractLoggingService() {
		super();
	}
	
	@Override
	public int update(List<UpdateSet<?>> updateSet, List<Filter<?>> filters) {
		int itensAtualizados = super.update(updateSet, filters);
		List<T> entityList = this.find(filters.toArray(new Filter[filters.size()]));
		entityList.stream().forEach(e->{
			verificarSeTemLog(e);
		});
		return itensAtualizados;
	}
	
	@Override
	public T save(T entity){
		T entitySaved = super.save(entity);
		verificarSeTemLog(entity);
		return entitySaved;
	}

	/**
	 * Metodo verifica a existência de Log.
	 * 
	 * @param entity - entidade validada.
	 */
	protected void verificarSeTemLog(T entity) {
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		
		TipoLogEvolucao tipoLogEvolucao = null;
		Perfis[] perfisExcluidos = null;
		
		for (StackTraceElement stackTraceElement : ste) {
			if (logAspect.containsKey(getClass().getSimpleName() + "_" + stackTraceElement.getMethodName())) {
				tipoLogEvolucao = logAspect.get(getClass().getSimpleName() + "_" + stackTraceElement.getMethodName() );
				perfisExcluidos = logAspect.getPerfis(getClass().getSimpleName() + "_" + stackTraceElement.getMethodName());				
				break;
			}
		}
		
		if(tipoLogEvolucao != null){
			//logAspect.remove(getClass());
			
			if(perfisExcluidos != null && perfisExcluidos.length > 0 && Perfis.TODOS.getId().equals(perfisExcluidos[0].getId())){
				perfisExcluidos = (Perfis[]) ArrayUtils.removeElement(perfisExcluidos, perfisExcluidos[0]);
			}
			
			LogEvolucao log = criarLog(entity, tipoLogEvolucao, perfisExcluidos);
			
			if(TipoLogEvolucao.INDEFINIDO.equals(log.getTipoEvento())){
				throw new IllegalComponentStateException(
						"Tipo de log não definido para alguma anotação @CreateLog "
						+ "dentro para a classe " + getClass().getCanonicalName() + ". "
						+ "Isso pode ter ocorrido porque a classe está com dois "
						+ "métodos anotados para gerar log e ambos fazem parte do "
						+ "mesmo escopo de funcionalidade.");
			}
			
			
			if(log != null){
				logEvolucaoService.save(log);
			}
			
		}
	}

	@Override
	public LogEvolucao criarLog(T entity, TipoLogEvolucao tipoLog, Perfis[] perfisExcluidos) {
		LogEvolucao log = new LogEvolucao();
		log.setPaciente(obterPaciente(entity));
		log.setParametros(obterParametros(entity));
		log.setData(LocalDateTime.now());
		log.setTipoEvento(tipoLog);
		log.setUsuario(usuarioService.obterUsuarioLogado());
		List<Perfil> perfis = null;
		if(perfisExcluidos != null){
			perfis =  new ArrayList<Perfil>();
			for(int i=0; i < perfisExcluidos.length; i++){
				Perfil perfil = new Perfil();
				perfil.setId(perfisExcluidos[0].getId());
				perfis.add(perfil);
			}			
			log.setPerfisExcluidos(perfis);
		}
		
		return log;
	}
	
	

}
