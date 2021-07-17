package br.org.cancer.redome.courier.model.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import br.org.cancer.redome.courier.model.annotation.AssertTrueCustom;

/**
 * Validates that the value passed is true.
 *
 * @author Fillipe Queiroz
 */
public class AssertTrueValidatorCustom implements ConstraintValidator<AssertTrueCustom, Boolean> {
	
	private String message;
	private String[] messageParamters;

    @Override
    public void initialize(AssertTrueCustom constraintAnnotation) {
    	this.message = constraintAnnotation.message();
    	this.messageParamters = constraintAnnotation.messageParameters();    	
    }

    @Override
    public boolean isValid(Boolean bool, ConstraintValidatorContext constraintValidatorContext) {
    	Boolean isValid = bool == null || bool;
    	if (!isValid && messageParamters != null && messageParamters.length != 0) {
    		doCustomValidation(constraintValidatorContext);
    	}
    	
        return isValid;
    }
    
    /**
     * @param constraintValidatorContext
     */
    private void doCustomValidation(ConstraintValidatorContext constraintValidatorContext) {
    	HibernateConstraintValidatorContext hibernateConstraintValidatorContext =
    			constraintValidatorContext.unwrap( HibernateConstraintValidatorContext.class );
    	
    	hibernateConstraintValidatorContext.disableDefaultConstraintViolation();
    	
    	for (int contador = 0; contador < messageParamters.length; contador++) {
    		hibernateConstraintValidatorContext.addMessageParameter(contador+"", messageParamters[contador]);
    	}
    	
    	hibernateConstraintValidatorContext
    		.buildConstraintViolationWithTemplate( message  )
    		.addConstraintViolation();
        
    }
    
}
