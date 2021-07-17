package br.org.cancer.redome.tarefa.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomPageRequest extends PageRequest {

	private static final long serialVersionUID = 2122648720410685438L;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public CustomPageRequest(@JsonProperty("page") int page, 
			@JsonProperty("pageSize") int size, @JsonProperty("sort") CustomSort sort) {
		super(page, size, sort);
	}
		
	public CustomPageRequest(int page, int size) {
		super(page, size);
	}
	
	public CustomPageRequest() {
		super(0, 0);
	}
		
	
	public CustomPageRequest(int page, int size, Direction direction, String... properties) {
		this(page, size, CustomSort.by(direction, properties));
	}

}
