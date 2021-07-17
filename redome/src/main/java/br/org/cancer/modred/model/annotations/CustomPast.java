package br.org.cancer.modred.model.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.org.cancer.modred.model.annotations.impl.PastValidator;

/**
 * 
 * @author Dev Team
 *
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { PastValidator.class })
public @interface CustomPast {

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
	 * 
	 * @return message
	 */
	String messagePast() default "{javax.validation.constraints.past.message}";

	/**
	 * 
	 * @return message
	 */
	String messagePastToday() default "{javax.validation.constraints.past.and.today.message}";

	/**
	 * 
	 * @return message
	 */
	String message() default "{javax.validation.constraints.past.and.today.message}";

	/**
	 * 
	 * @return today
	 */
	boolean today() default true;

}
