package hkmu.comps380f.s1326557_project.model;

import java.util.List;

public class OrderDTO {
    private Order order;
    private List<OrderBookDTO> orderBooks;

    public OrderDTO(Order order, List<OrderBookDTO> orderBooks) {
        this.order = order;
        this.orderBooks = orderBooks;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderBookDTO> getOrderBooks() {
        return orderBooks;
    }

    public void setOrderBooks(List<OrderBookDTO> orderBooks) {
        this.orderBooks = orderBooks;
    }
}

