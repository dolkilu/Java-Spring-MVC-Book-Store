package hkmu.comps380f.s1326557_project.service;

import hkmu.comps380f.s1326557_project.model.Forms;
import hkmu.comps380f.s1326557_project.repository.CustomerRepository;
import hkmu.comps380f.s1326557_project.repository.UserRepository;
import hkmu.comps380f.s1326557_project.model.BookUser;
import hkmu.comps380f.s1326557_project.model.Customer;
import hkmu.comps380f.s1326557_project.validator.UserValidator;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
public class UserManagementService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final UserValidator userValidator;

    public UserManagementService(PasswordEncoder passwordEncoder, UserRepository userRepository, CustomerRepository customerRepository, UserValidator userValidator) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.userValidator = userValidator;
    }

    @Transactional
    @PostConstruct
    public void createTestAccounts() {
        if (userRepository.count() == 0) {
            BookUser admin = new BookUser("admin", passwordEncoder.encode("admin"),
                    new String[]{"ROLE_ADMIN"});
            BookUser test = new BookUser("testuser", passwordEncoder.encode("testuser"),
                    new String[]{"ROLE_USER"});
            BookUser testBoth = new BookUser("testboth", passwordEncoder.encode("testboth"),
                    new String[]{"ROLE_ADMIN","ROLE_USER"});
            userRepository.save(admin);
            userRepository.save(test);
            userRepository.save(testBoth);
        }
    }

    @Transactional
    public void createUserAndCustomer (
            String username, String password, String[] roles,
            String firstname, String lastname,
            String email, String phone,
            String country, String city,
            String address1, String address2, String postalCode) {
        BookUser user = new BookUser(username, passwordEncoder.encode(password), roles);
        userRepository.save(user);
        Customer customer = new Customer(
                firstname, lastname,
                email, phone,
                country, city,
                address1, address2, postalCode);
        customer.setUser(user);
        customerRepository.save(customer);
    }

    @Transactional
    public void updatePassword(Forms.PasswordForm form, BindingResult result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        BookUser user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
        //validator returns !errors.hasErrors()
        if (userValidator.validatePassword(form, result, user)) {
            user.setPassword(passwordEncoder.encode(form.getNew_password()));
            userRepository.save(user);
        }
    }

    @Transactional
    public void updateCustomerInfo(String firstname, String lastname,
                                   String email, String phone,
                                   String country, String city,
                                   String address1, String address2, String postalCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BookUser user = userRepository.findById(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException(authentication.getName()));
        Customer customer = customerRepository.findByUser(user);
        customer.setFirstname(firstname);
        customer.setLastname(lastname);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setCountry(country);
        customer.setCity(city);
        customer.setAddress1(address1);
        customer.setAddress2(address2);
        customer.setPostalCode(postalCode);
        customerRepository.save(customer);
    }

}
