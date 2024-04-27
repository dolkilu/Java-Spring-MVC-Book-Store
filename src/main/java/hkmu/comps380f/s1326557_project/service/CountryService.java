package hkmu.comps380f.s1326557_project.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private List<String> countries;

    @PostConstruct
    public void init() {
        countries = Arrays.stream(Locale.getISOCountries())
                .map(code -> Locale.of("", code).getDisplayCountry())
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getCountries() {
        return countries;
    }
}
