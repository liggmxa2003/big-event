package com.lwz.validation;

import com.lwz.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//重写自定义注解
public class StateValidation implements ConstraintValidator</*给哪个注解提供校验规则*/State,/*校验数据类型*/String> {
    @Override
    public void initialize(State constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     *
     * @param value 将来要校验的数据
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //提供校验规则
        if (value == null){
            return false;
        }
        if ("已发布".equals(value) || "草稿".equals(value)){
            return true;
        }
        return false;
    }
}
