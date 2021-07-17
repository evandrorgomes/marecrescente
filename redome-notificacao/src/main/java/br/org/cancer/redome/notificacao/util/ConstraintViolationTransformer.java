package br.org.cancer.redome.notificacao.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe utilitária para transformar ConstraintViolation em CampoMensagem.
 * 
 * @author Filipe Paes
 *
 */
public class ConstraintViolationTransformer {

    private Set<ConstraintViolation<Object>> constraints;

    /**
     * Construtor já recebendo a lista das Constraints a serem tratadas.
     * 
     * @param Set<ConstraintViolation<Object>>
     *            lista a ser tratada
     */
    public ConstraintViolationTransformer(Set<ConstraintViolation<Object>> validate) {
        this.constraints = validate;
    }

    /**
     * Método que realiza a trasnformação do Set de ConstraintViolation para a
     * lista de CampoMensagem.
     * 
     * @return List<CampoMensagem> lista de mensagens transformada
     */
    public List<CampoMensagem> transform() {
        List<CampoMensagem> result = new ArrayList<CampoMensagem>();
        for (ConstraintViolation<Object> c : this.constraints) {
            String valorCampo = "";
            if (c.getConstraintDescriptor().getAttributes().get("field") != null) {
                valorCampo = (String) c.getConstraintDescriptor().getAttributes().get("field");
            }
            else {
                /*
                 * FIXME: Solução provisória. Dando split para pegar somente o
                 * nome do atributo, ignorando se ele faz parte de uma lista,
                 * como exemplo, evolucoes[0].altura.
                 */
                if (c.getPropertyPath().toString().contains("[0]")) {
                    final String[] split = StringUtils.split(c.getPropertyPath().toString(), ".");
                    valorCampo = split.length == 1 ? split[0] : split[1];
                }
                else {
                    valorCampo = c.getPropertyPath().toString();
                }
            }
            result.add(new CampoMensagem(valorCampo, c.getMessage()));
        }
        return result;
    }
}
