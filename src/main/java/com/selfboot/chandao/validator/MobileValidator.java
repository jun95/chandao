package com.selfboot.chandao.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileValidator implements ConstraintValidator<MobileCheck, String> {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    private boolean require = false ;

    @Override
    public void initialize(MobileCheck isMobile) {
        require = isMobile.required() ;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(require){
            return isMobile(value) ;
        }else{
            if(StringUtils.isEmpty(value)){
                return true ;
            }else {
                return isMobile(value) ;
            }
        }
    }

    public static boolean isMobile(String src) {
        if(StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m = MOBILE_PATTERN.matcher(src);
        return m.matches();
    }
}
