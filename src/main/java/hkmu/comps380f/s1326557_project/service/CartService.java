package hkmu.comps380f.s1326557_project.service;

import hkmu.comps380f.s1326557_project.repository.BookRepository;
import hkmu.comps380f.s1326557_project.exception.BookNotFound;
import hkmu.comps380f.s1326557_project.model.Book;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartService {
    private final BookRepository bookRepository;

    public CartService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @SuppressWarnings("unchecked")
    public Map<Long, Integer> getCart(HttpSession session) {
        Map<Long, Integer> cart = new ConcurrentHashMap<>();
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", cart);
            return cart;
        }
        cart = (Map<Long, Integer>)session.getAttribute("cart");
        return cart;
    }

    @Transactional
    public List<Book> getBooksInCart(HttpSession session) throws BookNotFound {
        Map<Long, Integer> cart = getCart(session);
        if (cart.isEmpty()) {
            return Collections.emptyList();
        }
        List<Book> books = new ArrayList<>();
        for (Long bookId : cart.keySet()) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BookNotFound(bookId));
            books.add(book);
        }
        return books;
    }

    @Transactional
    public void addToCart(Long bookId, HttpSession session) throws BookNotFound {
        Map<Long, Integer> cart = getCart(session);
        cart.put(bookId, cart.getOrDefault(bookId, 0) +1 );
    }

    @Transactional
    public void emptyCart(HttpSession session) {
        Map<Long, Integer> cart = getCart(session);
        cart.clear();
    }

    @Transactional
    public void updateCart(Long bookId, String action, Integer quantity, HttpSession session) {
        Map<Long, Integer> cart = getCart(session);
        Integer currentQty = cart.getOrDefault(bookId, 0);
        switch (action) {
            case "add":
                cart.put(bookId, currentQty + 1);
                break;
            case "minus":
                if (currentQty > 1) {
                    cart.put(bookId, currentQty - 1);
                } else {
                    cart.remove(bookId);
                }
                break;
            case "set":
                if (quantity != null && quantity > 0) {
                    cart.put(bookId, quantity);
                } else if (quantity != null && quantity == 0) {
                    cart.remove(bookId);
                }
                break;
            case "remove":
                cart.remove(bookId);
                break;
        }
        session.setAttribute("cart", cart);
    }
}
