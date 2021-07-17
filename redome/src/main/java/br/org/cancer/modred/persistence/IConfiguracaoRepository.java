package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.cancer.modred.model.Configuracao;

/**
 * Interface estendida de JpaRepository para mapear a entidade Configuração.
 * @author Filipe Paes
 */
public interface IConfiguracaoRepository extends JpaRepository<Configuracao, String> {
    List<Configuracao> findByChaveIn(String[] chaves);
    Configuracao findByChave(String chave);
}
