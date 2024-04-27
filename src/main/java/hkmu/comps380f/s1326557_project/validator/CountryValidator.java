package hkmu.comps380f.s1326557_project.validator;

import hkmu.comps380f.s1326557_project.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryValidator implements ConstraintValidator<ValidCountry, String> {

    private static ApplicationContext context;
    private CountryService countryService;

    @Autowired
    public void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

//    public CountryValidator(CountryService countryService) {
//        this.countryService = countryService;
//    }

    @Override
    public void initialize(ValidCountry constraintAnnotation) {
        this.countryService = context.getBean(CountryService.class);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return countryService.getCountries().contains(value);
    }
}
