package com.ttn.project2.Annotations;

import com.ttn.project2.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        User user = (User) obj;

        //String a=passwordEncoder.encode(user.getPassword());
        //String b=passwordEncoder.encode(user.getConfirm_password());


        //return passwordEncoder.encode(user.getPassword().equals(passwordEncoder.encode(user.getConfirm_password())));


        return user.getPassword().equals(user.getConfirm_password());

        //return a.equals(b);
        }
    }