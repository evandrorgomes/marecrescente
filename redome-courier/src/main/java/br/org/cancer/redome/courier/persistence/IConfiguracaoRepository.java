package br.org.cancer.redome.courier.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.courier.model.Configuracao;

/**
 * Interface estendida de JpaRepository para mapear a entidade Configuração.
 * @author Filipe Paes
 */
@Transactional(readOnly = true)
@Repository
public interface IConfiguracaoRepository extends JpaRepository<Configuracao, String> {
	
    List<Configuracao> findByChaveIn(String[] chaves);
    
    Configuracao findByChave(String chave);
    
}
