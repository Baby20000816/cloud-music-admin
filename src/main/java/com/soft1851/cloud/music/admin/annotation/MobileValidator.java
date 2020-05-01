package com.soft1851.cloud.music.admin.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @author admin
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}
