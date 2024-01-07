package com.example.validator;

import com.example.adaptor.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum> {
    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        if (value == null) return false;

        Object[] enumValues = this.annotation.target().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.toString().equals(enumValue.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}