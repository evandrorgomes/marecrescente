package br.org.cancer.modred.model.annotations.impl;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.org.cancer.modred.model.annotations.CustomFuture;

/**
 * 
 * @author Dev Team
 *
 */
public class FutureDateTimeValidator implements ConstraintValidator<CustomFuture, LocalDateTime> {

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
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;

        if (value == null) {
            valid = true;
        }
        else {
            if (today) {
                valid = !value.isBefore(LocalDateTime.now());
            }
            else {
                valid = value.isAfter(LocalDateTime.now());
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
