package hkmu.comps380f.s1326557_project.controller;

import hkmu.comps380f.s1326557_project.model.BookUser;
import hkmu.comps380f.s1326557_project.service.*;
import hkmu.comps380f.s1326557_project.exception.BookNotFound;
import hkmu.comps380f.s1326557_project.model.Book;
import hkmu.comps380f.s1326557_project.model.OrderDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final BookService bookService;
    private final OrderService orderService;
    private final UserService userService;

    public CartController(CartService cartService, BookService bookService, OrderService orderService, UserService userService) {
        this.cartService = cartService;
        this.bookService = bookService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping(value = {"", "/"})
    public String Cart(Model model, HttpSession session) {
        model.addAttribute("cart", cartService.getCart(session));
        model.addAttribute("pageTitle", "Shopping Cart");
        try {
            List<Book> books = cartService.getBooksInCart(session);
            model.addAttribute("books", books);
        } catch (BookNotFound e) {
            model.addAttribute("errorMessage", "One or more books not found in the cart");
        }
        return "cart";
    }


    @PostMapping("/add/{bookId}")
    public String addToCart(@PathVariable Long bookId,
                            @RequestParam ("redirectUrl") String url,
                            @RequestParam ("pageNum") int pageNum,
                            @RequestParam ("size") String size,
                            HttpSession session, RedirectAttributes ra) {
        String redirectUrl = "redirect:" + url;
        if (pageNum > 0) {
        redirectUrl = "redirect:" + url + "?page=" + pageNum + "&size=" + size ;
        }
        try {

            Book book = bookService.getBook(bookId);
            cartService.addToCart(bookId, session);
            String msg = "Added " + book.getName() + " by " + book.getAuthors() + " to cart.";
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", msg);
            return redirectUrl;
        } catch (BookNotFound e) {
            ra.addFlashAttribute("error", true);
            ra.addFlashAttribute("message", e);
            return url;
        }
    }


    @PostMapping("/update")
    public String updateCart(@RequestParam("bookId") Long bookId, @RequestParam("action") String action,
                             @RequestParam(value = "quantity", required = false) Integer quantity,
                             HttpSession session) {
        cartService.updateCart(bookId, action, quantity, session);
        return "redirect:/cart";
    }

    @PostMapping("/empty")
    public String emptyCart(HttpSession session) {
        cartService.emptyCart(session);
        return "redirect:/cart";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("pageTitle","Checkout");
        try {
            List<Book> booksInCart = cartService.getBooksInCart(session);
            double total = 0;
            for (Book book : booksInCart) {
                total += book.getPrice().doubleValue() * ((Map<Long, Integer>) session.getAttribute("cart")).get(book.getId());
            }
            model.addAttribute("books", booksInCart);
            model.addAttribute("total", total);
            model.addAttribute("errorMessage", "Error! Some books are not available.");

            return "checkout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error! Some books are not available.");
            return "redirect:/cart";
        }
    }

    @PostMapping("/placeOrder")
    public String placeOrder(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            orderService.placeOrder(session);
            cartService.emptyCart(session);
            return "redirect:/cart/viewOrders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/cart/checkout";
        }
    }

    @GetMapping("/viewOrders")
    public String viewOrder(Model model,
                            @PageableDefault(size = 3) Pageable pageable,
                            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        BookUser user = userService.getCurrentUser();
        Page<OrderDTO> orderDTOs = orderService.getUserOrders(user, pageable);

        model.addAttribute("pageTitle", "Order History");
        model.addAttribute("orderDTOs", orderDTOs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderDTOs.getTotalPages());
        model.addAttribute("normalView",true);
        return "viewOrders";
    }
}
