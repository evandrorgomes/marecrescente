package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ArquivoExame;

/**
 * @author Filipe Paes
 *
 */
@Repository
public interface IArquivoExameRepository  extends JpaRepository<ArquivoExame, Long> {
    
    /**
     * Buscar caminho do arquivo a partir do arquivo exame id informado.
     * 
     * @param arquivoExameId
     * @return lista de ArquivoExame.
     */
    @Query("select new ArquivoExame(pac.id, arq.caminhoArquivo) "
         + "from ArquivoExame arq "
         + "inner join arq.exame ex "
         + "inner join ex.paciente pac "
         + "where arq.id = ?1")
    ArquivoExame obterArquivoExameParaDownload(Long arquivoExameId);
    
    /**
     * Buscar caminho do arquivo a partir do exame id informado.
     * 
     * @param exameId
     * @return 
     * @return lista de ArquivoExame.
     */
    @Query("select new ArquivoExame(pac.id, arq.caminhoArquivo) "
            + "from ArquivoExame arq "
            + "inner join arq.exame ex "
            + "inner join ex.paciente pac "
            + "where ex.id = ?1")
    List<ArquivoExame> findByExameId(Long exameId);
    
}
