package hkmu.comps380f.s1326557_project.service;

import hkmu.comps380f.s1326557_project.exception.OrderException;
import hkmu.comps380f.s1326557_project.repository.*;
import hkmu.comps380f.s1326557_project.exception.BookNotFound;
import hkmu.comps380f.s1326557_project.model.*;
import hkmu.comps380f.s1326557_project.utils.DateTimeUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final OrderBookRepository orderBookRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository, OrderBookRepository orderBookRepository, CustomerRepository customerRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.orderBookRepository = orderBookRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }


    @SuppressWarnings("unchecked")
    @Transactional
    public void placeOrder(HttpSession session) throws BookNotFound, OrderException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Customer customer = customerRepository.findByUsername(username);
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
        if (cart.isEmpty()) {return;}
//        List<Book> books = cartService.getBooksInCart(session);
        List<Book> books = new ArrayList<>();
        for (Long bookId : cart.keySet()) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BookNotFound(bookId));
            books.add(book);
        }

        for (Book book : books) {
            if ((!book.getAvailability()) || (book.getQuantity() < cart.get(book.getId()))) {
                throw new OrderException("Not enough stock for book: " + book.getName());
            }
        }

        double total = books.stream()
                .mapToDouble(book -> book.getPrice().doubleValue() * cart.get(book.getId()))
                .sum();

        String date = DateTimeUtils.formatLocalDateTime(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");

        Order order = new Order();
        order.setUser(userRepository.findBookUserByUsername(username));
        order.setOrderDate(date);
        order.setTotal(new BigDecimal(total));
        order.setFirstname(customer.getFirstname());
        order.setLastname(customer.getLastname());
        order.setEmail(customer.getEmail());
        order.setPhone(customer.getPhone());
        order.setCountry(customer.getCountry());
        order.setCity(customer.getCity());
        order.setAddress1(customer.getAddress1());
        order.setAddress2(customer.getAddress2());
        order.setPostalCode(customer.getPostalCode());
        order = orderRepository.save(order);

        for (Book book : books) {
            System.out.println(book.toString());
            OrderBook orderBook = new OrderBook();
            OrderBookId id = new OrderBookId(order.getId(), book.getId());
            System.out.println("oid "+ order.getId() + " bid " + book.getId());
            orderBook.setId(id);
            orderBook.setOrder(order);
            orderBook.setBook(book);
            orderBook.setQuantity(cart.get(book.getId()));
            orderBookRepository.save(orderBook);

            book.setQuantity(book.getQuantity() - cart.get(book.getId()));
            bookRepository.save(book);
        }
    }

    @Transactional
    public Page<OrderDTO> getUserOrders(BookUser user ,Pageable pageable) {
        Page<Order> orders = orderRepository.findByUser(user, pageable);
        return orders.map(order -> {
            List<OrderBook> orderBooks = orderBookRepository.findByOrderId(order.getId());
            List<OrderBookDTO> orderBookDTOs = orderBooks.stream()
                    .map(ob -> new OrderBookDTO(ob.getBook(), ob.getQuantity()))
                    .collect(Collectors.toList());
            return new OrderDTO(order, orderBookDTOs);
        });
    }

    @Transactional
    public OrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        assert order != null;
        List<OrderBook> orderBooks = orderBookRepository.findByOrderId(order.getId());
        List<OrderBookDTO> orderBookDTOs = orderBooks.stream()
                .map(ob -> new OrderBookDTO(ob.getBook(), ob.getQuantity()))
                .collect(Collectors.toList());
        return new OrderDTO(order, orderBookDTOs);
    }
}
