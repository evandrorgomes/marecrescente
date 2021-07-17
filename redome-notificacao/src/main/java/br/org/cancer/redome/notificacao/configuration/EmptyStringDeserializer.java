package br.org.cancer.redome.notificacao.configuration;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Deserializa strings vazias convertendo para null.
 * 
 * @author Cintia Oliveira
 *
 */
public class EmptyStringDeserializer extends JsonDeserializer<String> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml
     * .jackson.core.JsonParser,
     * com.fasterxml.jackson.databind.DeserializationContext)
     */
    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException,
            JsonProcessingException {
        return StringUtils.isEmpty(parser.getText()) ? null : parser.getText();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.fasterxml.jackson.databind.JsonDeserializer#handledType()
     */
    @Override
    public Class<?> handledType() {
        return String.class;
    }
}