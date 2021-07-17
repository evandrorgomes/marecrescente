package br.org.cancer.modred.model.annotations.impl;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.org.cancer.modred.model.annotations.CustomPast;

/**
 * 
 * @author Dev Team
 *
 */
public class PastValidator implements ConstraintValidator<CustomPast, LocalDate> {

    private boolean today;
    private String messagePastToday;
    private String messagePast;

    @Override
    public void initialize(CustomPast constraintAnnotation) {
        today = constraintAnnotation.today();
        messagePastToday = constraintAnnotation.messagePastToday();
        messagePast = constraintAnnotation.messagePast();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;
        
        if (value == null) {
            valid = true;
        }
        else {
            if (today) {
                valid = ( value == null || !value.isAfter(LocalDate.now()) );
            }
            else {
                valid = ( value == null || value.isBefore(LocalDate.now()) );
            }
        }
        
        // Ajustando a mensagem de erro, considerando se é pra considerar hoje ou não.
        if(!valid){
            doCustomValidation(constraintValidatorContext);
        }

        return valid;
    }

    /**
     * @param constraintValidatorContext
     */
    private void doCustomValidation(ConstraintValidatorContext constraintValidatorContext) {
    	constraintValidatorContext.disableDefaultConstraintViolation();
        
        // alterando a mensagem a ser exibida, de acordo com o parametro selecionado.
        if(today){
        	constraintValidatorContext
                .buildConstraintViolationWithTemplate(messagePastToday)
                    .addConstraintViolation();
        }
        else {
        	constraintValidatorContext
            .buildConstraintViolationWithTemplate(messagePast)
                .addConstraintViolation();
        }
    }

}
