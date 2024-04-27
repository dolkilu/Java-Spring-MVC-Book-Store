package hkmu.comps380f.s1326557_project.validator;

import hkmu.comps380f.s1326557_project.model.Forms;
import hkmu.comps380f.s1326557_project.repository.UserRepository;
import hkmu.comps380f.s1326557_project.model.BookUser;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserValidator(UserRepository userRepository, PasswordEncoder passwordEncoder, SystemMetricsAutoConfiguration systemMetricsAutoConfiguration) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean supports(Class<?> type) {return Forms.RegisterForm.class.equals(type);}

    @Override
    @SuppressWarnings("NullableProblems")
    public void validate(Object object, Errors errors) {
        Forms.RegisterForm user = (Forms.RegisterForm) object;
        ValidationUtils.rejectIfEmpty(errors, "confirm_password", "",
                "Please confirm your password.");

        if (!user.getPassword().equals(user.getConfirm_password())) {
            errors.rejectValue("confirm_password", "", "Password does not match.");
        }

        if (user.getUsername().isEmpty()) {
            return;
        }

        userRepository.findById(user.getUsername()).ifPresent(bookUser -> errors
                .rejectValue("username", "", "User already exists."));
    }


    public Boolean validatePassword(Object object, Errors errors, BookUser user) {
        Forms.PasswordForm passwordForm = (Forms.PasswordForm) object;
        ValidationUtils.rejectIfEmpty(errors, "confirm_password", "",
                "Please confirm your password.");
        String old = passwordForm.getOld_password();
        String stored_old = user.getPassword();
        System.out.println(user.getPassword());
        System.out.println(passwordEncoder.matches(old,stored_old));
        if (!passwordEncoder.matches(old, stored_old)) {
            errors.rejectValue("old_password", "", "Current password does not match.");
        }

        if (!passwordForm.getNew_password().equals(passwordForm.getConfirm_password())) {
            errors.rejectValue("confirm_password", "", "New and confirm password does not match.");
        }

        return !errors.hasErrors();
    }
}
