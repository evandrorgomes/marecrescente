package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ArquivoRelatorioInternacional;


/**
 * Interface de persistencia de ArquivoRelatorioInternacional.
 * @author Filipe Paes
 *
 */
@Repository
public interface IArquivoRelatorioInternacionalRepository extends IRepository<ArquivoRelatorioInternacional, Long> {

}
