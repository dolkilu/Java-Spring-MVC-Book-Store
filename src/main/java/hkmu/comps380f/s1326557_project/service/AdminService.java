package hkmu.comps380f.s1326557_project.service;

import hkmu.comps380f.s1326557_project.exception.CommentNotFound;
import hkmu.comps380f.s1326557_project.model.*;
import hkmu.comps380f.s1326557_project.repository.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public AdminService(CommentRepository commentRepository, UserRepository userRepository, CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void deleteComment(long commentId) throws CommentNotFound{
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFound(commentId));
        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteUser(String username) throws UsernameNotFoundException{
        BookUser user = userRepository.findBookUserByUsername(username);
        userRepository.delete(user);
    }

    @Transactional
    public void updateUserInfo(String username, String firstname, String lastname,
                                   String email, String phone,
                                   String country, String city,
                                   String address1, String address2, String postalCode) {
        BookUser user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
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

    public List<String> getRoles(String username) {
        BookUser user = userRepository.findBookUserByUsername(username);
        List<UserRole> pairs = user.getRoles();
        return pairs.stream().map(UserRole::getRole).collect(Collectors.toList());

    }

    @Transactional
    public void updateRole(String username, List<String> newRoles) {
        BookUser user = userRepository.findBookUserByUsername(username);
        user.getRoles().clear();
        for (String role : newRoles) {
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            user.getRoles().add(userRole);
        }
        userRepository.save(user);
    }

    @Transactional
    public void deleteOrder(Long id) {
        orderRepository.findById(id).ifPresent(orderRepository::delete);
    }
}
