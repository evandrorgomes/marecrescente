package br.org.cancer.modred.configuration;

import java.io.IOException;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.org.cancer.modred.controller.page.JsonPage;

/**
 * Serializa objetos da classe JsonPage.
 * 
 * @author Cintia Oliveira
 *
 */
public class JsonPageSerializer extends StdSerializer<JsonPage> {

	private static final long serialVersionUID = 5118521783127132893L;

	protected JsonPageSerializer(Class<JsonPage> clazz) {
		super(clazz);
	}

	public static final JsonPageSerializer INSTANCE = new JsonPageSerializer(JsonPage.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.Object,
	 * com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(JsonPage value, JsonGenerator gen, SerializerProvider serializers) throws IOException,
			JsonProcessingException {

		MappingJackson2HttpMessageConverter messageConverter = (MappingJackson2HttpMessageConverter) ApplicationContextProvider
				.obterBean("customMappingJackson2HttpMessageConverter");
		ObjectMapper mapper = messageConverter.getObjectMapper();

		gen.writeStartObject();

		gen.writeArrayFieldStart("content");

		for (Object filho : value.getContent()) {
			mapper.writerWithView(value.getJsonView()).writeValue(gen, filho);
		}

		gen.writeEndArray();

		gen.writeNumberField("totalPages", value.getTotalPages());
		gen.writeNumberField("totalElements", value.getTotalElements());
		gen.writeBooleanField("hasNext", value.hasNext());
		gen.writeBooleanField("last", value.isLast());
		gen.writeBooleanField("hasContent", value.hasContent());
		gen.writeNumberField("number", value.getNumber());
		gen.writeBooleanField("first", value.isFirst());
		gen.writeEndObject();
	}

}