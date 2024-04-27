package hkmu.comps380f.s1326557_project.repository;

import hkmu.comps380f.s1326557_project.model.BookUser;
import hkmu.comps380f.s1326557_project.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(BookUser user);
    Page<Order> findByUser(BookUser user, Pageable pageable);
}

