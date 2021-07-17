package br.org.cancer.modred.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomSort extends Sort {
	
	private static final long serialVersionUID = -4811255319902302928L;

	
	/*
	 * public CustomSort(@JsonProperty("orders") List<Order> orders,
	 * 
	 * @JsonProperty("direction") Direction direction,
	 * 
	 * @JsonProperty("properties") List<String> properties) {
	 * 
	 * if (CollectionUtils.isNotEmpty(orders)) { this(orders); } else {
	 * this(direction, properties); }
	 * 
	 * }
	 */
	
	public CustomSort() {
		super(new Order[0]);
	}
		
	public CustomSort(Order... orders) {
		super(Arrays.asList(orders));
	}
	
	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public CustomSort(@JsonProperty("orders") List<Order> orders) {
		super(orders);
	}
	
	public CustomSort(String... properties) {
		super(properties);
	}
	
	public CustomSort(Direction direction, String... properties) {
		super(direction, properties);
	}
	
	public CustomSort(Direction direction, List<String> properties) {
		super(direction, properties);
	}
	
	public static CustomSort by(List<Order> orders) {

		Assert.notNull(orders, "Orders must not be null!");

		return orders.isEmpty() ? (CustomSort) Sort.unsorted() : new CustomSort(orders);
	}
	
	public static CustomSort by(Direction direction, String... properties) {

		Assert.notNull(direction, "Direction must not be null!");
		Assert.notNull(properties, "Properties must not be null!");
		Assert.isTrue(properties.length > 0, "At least one property must be given!");

		return CustomSort.by(Arrays.stream(properties)//
				.map(it -> new Order(direction, it))//
				.collect(Collectors.toList()));
	}
	
	
	

}
