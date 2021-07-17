package br.org.cancer.modred.model.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.org.cancer.modred.model.annotations.impl.FutureDateTimeValidator;
import br.org.cancer.modred.model.annotations.impl.FutureDateValidator;

/**
 * 
 * @author Dev Team
 *
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { FutureDateValidator.class, FutureDateTimeValidator.class })
public @interface CustomFuture {

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
	String messageFutureToday() default "{javax.validation.constraints.future.and.today.message}";

	/**
	 * 
	 * @return message
	 */
	String messageFuture() default "{javax.validation.constraints.future.message}";
	
	/**
	 * message.
	 * 
	 * @return message m
	 */
	String message() default "{javax.validation.constraints.future.message}";

	/**
	 * 
	 * @return today
	 */
	boolean today() default false;

}
