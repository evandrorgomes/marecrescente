package br.org.cancer.modred.model.annotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.org.cancer.modred.model.annotations.impl.AssertTrueValidatorCustom;


/**
 * The annotated element must be true.
 * Supported types are {@code boolean} and {@code Boolean}.
 * <p/>
 * {@code null} elements are considered valid.
 *
 * @author Fillipe Queiroz
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {AssertTrueValidatorCustom.class })
public @interface AssertTrueCustom {

	/**
	 * 
	 * @return message
	 */
	String message() default "{javax.validation.constraints.AssertTrue.message}";
	
	/**
	 * 
	 * @return message parameters
	 */
	String[] messageParameters() default {};
	

	/**
	 * 
	 * @return message
	 */
	String field() default "";

	/**
	 * 
	 * @return groups
	 */
	Class<?>[] groups() default {};

	/**
	 * 
	 * @return payload
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * Defines several {@link AssertTrueCustom} annotations on the same element.
	 *
	 * @see AssertTrueCustom
	 */
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		
		/**
		 * 
		 * @return values
		 */
		AssertTrueCustom[] value();
	}
}
