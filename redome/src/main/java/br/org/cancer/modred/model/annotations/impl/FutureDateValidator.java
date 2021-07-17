package br.org.cancer.modred.model.annotations.impl;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.org.cancer.modred.model.annotations.CustomFuture;

/**
 * 
 * @author Dev Team
 *
 */
public class FutureDateValidator implements ConstraintValidator<CustomFuture, LocalDate> {

    private boolean today;
    private String messageFutureToday;
    private String messageFuture;

    @Override
    public void initialize(CustomFuture constraintAnnotation) {
        today = constraintAnnotation.today();
        messageFutureToday = constraintAnnotation.messageFutureToday();
        messageFuture = constraintAnnotation.messageFuture();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;

        if (value == null) {
            valid = true;
        }
        else {
            if (today) {
                valid = !value.isBefore(LocalDate.now());
            }
            else {
                valid = value.isAfter(LocalDate.now());
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
                .buildConstraintViolationWithTemplate(messageFutureToday)
                    .addConstraintViolation();
        }
        else {
        	constraintValidatorContext
            .buildConstraintViolationWithTemplate(messageFuture)
                .addConstraintViolation();
        }
    }
}
