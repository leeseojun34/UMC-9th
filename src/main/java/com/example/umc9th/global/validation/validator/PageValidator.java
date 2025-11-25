package com.example.umc9th.global.validation.validator;

import com.example.umc9th.global.common.code.PageErrorCode;
import com.example.umc9th.global.validation.annotation.CheckPage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PageValidator implements ConstraintValidator<CheckPage, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null || value < 1) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(PageErrorCode.INVALID_PAGE.getMessage())
                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
