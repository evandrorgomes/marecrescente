package br.org.cancer.redome.tarefa.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.tarefa.model.TipoTarefa;

@Repository
public interface ITipoTarefaRepository extends JpaRepository<TipoTarefa, Long> {
	
}
