package hkmu.comps380f.s1326557_project.repository;

import hkmu.comps380f.s1326557_project.model.BookUser;
import hkmu.comps380f.s1326557_project.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.user.username = :username")
    Customer findByUsername(String username);

    Customer findByUser(BookUser user);
    Page<Customer> findAll(Pageable pageable);

}
