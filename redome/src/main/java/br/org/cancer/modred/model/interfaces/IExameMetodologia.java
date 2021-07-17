package br.org.cancer.modred.model.interfaces;

/**
 * Interface utilizada nas classes de ExamePaciente e ExameDoadorNacional para a geração do genotipo.
 * 
 * @author brunosousa
 *
 */
public interface IExameMetodologia {
	
	/**
	 * Obtem o maior peso das metodologias.
	 * 
	 * @return
	 */
	Integer obterMaiorPesoMetodologia();

}
