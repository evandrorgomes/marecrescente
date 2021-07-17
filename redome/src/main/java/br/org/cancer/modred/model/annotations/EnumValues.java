package br.org.cancer.modred.model.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.org.cancer.modred.model.annotations.impl.EnumValuesValidator;
import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * 
 * @author Dev Team
 *
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { EnumValuesValidator.class })
public @interface EnumValues {

	/**
	 * groups.
	 * 
	 * @return groups g
	 */
	Class<?>[] groups() default {};

	/**
	 * payload.
	 * 
	 * @return payload p
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * message.
	 * 
	 * @return message m
	 */
	String message() default "{javax.validation.constraints.EnumValues.message}";

	/**
	 * enum.
	 * 
	 * @return enum enum
	 */
	Class<? extends EnumType<?>> value();

}
