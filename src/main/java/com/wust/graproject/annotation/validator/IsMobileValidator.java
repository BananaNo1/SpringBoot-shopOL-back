package com.wust.graproject.annotation.validator;

import com.wust.graproject.util.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.awt.print.Pageable;

/**
 * @ClassName IsMobileValidator
 * @Description TODO
 * @Author leis
 * @Date 2019/1/22 18:30
 * @Version 1.0
 **/
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean require = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        require = constraintAnnotation.require();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (require) {
            return ValidatorUtil.isMobile(s);
        } else {
            if (StringUtils.isEmpty(s)) {
                return true;
            } else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
