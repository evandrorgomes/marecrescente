package br.org.cancer.modred.model.domain;

/**
 * Enum para representar os possíveis estados de entidades que podem ser excluídas logicamente.
 *
 * @author Thiago Moraes
 *
 */
public enum EntityStatus{
	
	ATIVO (1L), 
	APAGADO (2L);
	
	
    private Long id;

    EntityStatus(Long id) {
            this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
            return id;
    }

    /**
     * Método para criar o tipo enum a partir de seu valor numerico.
     * 
     * @param value - valor do id do tipo enum
     * 
     * @return EntityStatus - a enum correspondente ou nulo caso value estaja fora do range conhecido.
     */
    public static EntityStatus valueOf(Long value) {

        EntityStatus[] values = EntityStatus.values();
            for (int i = 0; i < values.length; i++) {
                    if (values[i].getId().equals(value)) {
                            return values[i];
                    }
            }
            return null;
    }

    /**
     * Método para verificar se o id está dentro do range previsto.
     * 
     * @return boolean - returna <b>true</b> se o id corresponde a um valor válido da enum, caso contrário, retorna <b>false</b>.
     */
    public boolean validate() {

        EntityStatus[] values = EntityStatus.values();
            for (int i = 0; i < values.length; i++) {
                    if (values[i].getId().equals(id)) {
                            return true;
                    }
            }
            return false;
    }

    /**
     * Representação textual da tarefa que pode ser apresentada ao usuário final.
     * 
     * @return String - representação textual da tarefa.
     */
    public String getDescriptionKey() {

            switch (this) {

                    case ATIVO:
                            return "dominio.status.entity.ativo";
                    case APAGADO:
                            return "dominio.status.entity.apagado";

                    default:
                            return null;
            }
    }	
	

}
