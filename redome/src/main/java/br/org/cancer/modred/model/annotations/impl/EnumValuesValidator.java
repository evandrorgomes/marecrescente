package br.org.cancer.modred.model.annotations.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.interfaces.EnumType;
import br.org.cancer.modred.util.EnumUtil;

/**
 * Realiza a validação do valor informado como enum de uma determinada classe.
 * Caso não seja válido, isto será identificado e alertado ao rodar o validação
 * no bean da entidade.
 * 
 * @author Dev Team
 *
 */
public class EnumValuesValidator implements ConstraintValidator<EnumValues, Object> {
    
    private Class<? extends EnumType<?>> enumClass;
    
    @Override
    public void initialize(EnumValues constraintAnnotation) {
        enumClass = constraintAnnotation.value();
    }

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		return EnumUtil.contains(this.enumClass, value);
	}
}
