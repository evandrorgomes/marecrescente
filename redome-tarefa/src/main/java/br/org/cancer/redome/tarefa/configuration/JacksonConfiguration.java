package br.org.cancer.redome.tarefa.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class JacksonConfiguration.
 * 
 * @author bruno
 * 
 */
@Configuration
public class JacksonConfiguration {
	
    /**
	 * 
	 * @return ObjectMapper - objeto de mapeamento para configuração Jackson. 
	*/
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);

        return mapper;
    }
}