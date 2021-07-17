package br.org.cancer.redome.tarefa.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import br.org.cancer.redome.tarefa.model.ValorNmdp;

/**
 * Interface para metodos de serviços envolvendo a entidade ValorNmdp.
 * 
 * @author bruno.sousa
 *
 */
public interface IValorNmdpService {
	
	/**
	 * Método para salvar Lista de ValorNmdp. 
	 * 
	 */
	void salvar(List<ValorNmdp> valorNmdp);

	/**
	 * Método para listar valor nmdp pela data de atualização. 
	 */
	List<ValorNmdp> listarValorNmdpPorDataUltimaAtualizacaoArquivo(LocalDate dataUltimaAtualizacaoArquivo);

	/**
	 * Método para desmarcar o flag de split criado.
	 */
	void desmarcarFlagSplitCriado();
	
	
	List<ValorNmdp> recuperarValoresImportados(File arquivo) throws IOException;
	

}
