package hkmu.comps380f.s1326557_project.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderBookId implements Serializable {
    private Long orderId;
    private Long bookId;

    public OrderBookId() {
    }

    public OrderBookId(Long orderId, Long bookId) {
        this.orderId = orderId;
        this.bookId = bookId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
