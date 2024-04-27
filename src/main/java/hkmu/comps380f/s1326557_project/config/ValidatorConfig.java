package hkmu.comps380f.s1326557_project.config;


import hkmu.comps380f.s1326557_project.validator.CountryValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {
    @Bean
    public static CountryValidator countryValidator() {
        return new CountryValidator();
    }
}
