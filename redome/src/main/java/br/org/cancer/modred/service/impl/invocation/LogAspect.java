package br.org.cancer.modred.service.impl.invocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
/**
 * 
 * @author Pizão
 * 
 * Classe que monitora os métodos anotados com @CreateLog e guarda quais são os tipos
 * de logs que devem ser criados.
 * Classe implementada sob o conceito de orientação a aspecto (Aspect-Oriented Programming (AOP))
 * que visa separar responsabilidades dentro de um escopo de método ou serviço. Separando a lógica de 
 * negócio de uma geração de logs ou tarefas, por exemplo.
 * 
 */
@Aspect
@Configuration
public class LogAspect{

	private Map<String, TipoLogEvolucao> relacaoMetodosAndTiposLog;
	private Map<String, Perfis[]> relacaoMetodosAndPerfis;
	
	
	public LogAspect() {
		relacaoMetodosAndTiposLog = new HashMap<String, TipoLogEvolucao>();
		relacaoMetodosAndPerfis =  new HashMap<String, Perfis[]>();
	}
		
	/**
	 * Captura a intenção (before) de chamada ao método anotado com @CreateLog
	 * e associa o tipo de log a ser criado a classe que originou a chamada.
	 * 
	 * @param origemChamada Ponto que originou a chamada do evento.
	 * @return a classe (service) que originou a chamada.
	 * @throws Throwable exceção lançada caso ocorra algum problema no mapeamento ou no método.
	 */
	@Before("@annotation(br.org.cancer.modred.model.annotations.log.CreateLog)")
    public Object identificarTipoLogAposMetodoAnotadoSerChamado(JoinPoint origemChamada) throws Throwable {
		add(origemChamada);
	    return origemChamada.getTarget();
	}
	
	/**
	 * Associa a classe que originou a chamada ao tipo de log.
	 * 
	 * @param origemChamada ponto de chamada de onde originou o evento.
	 */
	private void add(JoinPoint origemChamada){
		MethodSignature signature = (MethodSignature) origemChamada.getSignature();
	    Method method = signature.getMethod();
	    
	    if(method.getAnnotation(CreateLog.class).value() == null){
	    	throw new IllegalStateException(
	    			"Na anotação do método " + method.getName() + " não foi informado o tipo de log a ser criado. " + 
	    			"Caso não seja especificado na assinatura, o método criarLog deve ser reimplementado definindo o tipo " + 
	    			"de acordo com o que for definido pelo escopo da chamada.");
	    }
	    
		relacaoMetodosAndTiposLog.put(
				origemChamada.getTarget().getClass().getSimpleName() + "_" + method.getName(), method.getAnnotation(CreateLog.class).value());
		
		relacaoMetodosAndPerfis.put(
				origemChamada.getTarget().getClass().getSimpleName() + "_" + method.getName(), method.getAnnotation(CreateLog.class).perfisExcluidos());
	}

	/**
	 * Retorna o tipo de log associado a classe que originou a chamada.
	 * 
	 * @param referenciaClasse referência da classe associada ao tipo log.
	 * @return tipo de log associado.
	 */
	public TipoLogEvolucao get(String referenciaClasse) {
		final TipoLogEvolucao tipoLog = relacaoMetodosAndTiposLog.get(referenciaClasse);
		return tipoLog;
	}
	
	public boolean containsKey(String referenciaClasse) {
		return relacaoMetodosAndTiposLog.containsKey(referenciaClasse);
	}
	
	/**
	 * Retorna os perfis excluidos associados a classe que originou a chamada.
	 * 
	 * @param referenciaClasse referência da classe associada ao tipo log.
	 * @return lista de perfis excluidos.
	 */
	public Perfis[] getPerfis(String referenciaClasse) {
		final Perfis[] perfis = relacaoMetodosAndPerfis.get(referenciaClasse);
		return perfis;
	}
	
	public boolean perfilContainsKey(String referenciaClasse) {
		return relacaoMetodosAndPerfis.containsKey(referenciaClasse);
	}
	
	
	
	public boolean remove(Class<?> referenciaClasse){
		return relacaoMetodosAndTiposLog.remove(referenciaClasse) != null && relacaoMetodosAndPerfis.remove(referenciaClasse) != null;
	}
	
}