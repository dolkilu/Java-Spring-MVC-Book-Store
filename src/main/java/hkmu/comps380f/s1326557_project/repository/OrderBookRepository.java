package hkmu.comps380f.s1326557_project.repository;

import hkmu.comps380f.s1326557_project.model.OrderBook;
import hkmu.comps380f.s1326557_project.model.OrderBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBookRepository extends JpaRepository<OrderBook, OrderBookId> {
    List<OrderBook> findByOrderId(Long orderId);}
