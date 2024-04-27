package hkmu.comps380f.s1326557_project.service;

import hkmu.comps380f.s1326557_project.model.*;
import hkmu.comps380f.s1326557_project.repository.CustomerRepository;
import hkmu.comps380f.s1326557_project.repository.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public UserService(UserRepository userRepository, CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BookUser user = userRepository.findById(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    public BookUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication.isAuthenticated());
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String username = authentication.getName();
        return userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public String getCurrentUserUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Transactional
    public Forms.CustomerForm createCustomerInfoForm(String username) {
        Customer customer = customerRepository.findByUsername(username);
        return new Forms.CustomerForm(customer.getFirstname(), customer.getLastname(),
                customer.getEmail(), customer.getPhone(), customer.getCountry(),
                customer.getCity(), customer.getAddress1(), customer.getAddress2(), customer.getPostalCode());
    }

}