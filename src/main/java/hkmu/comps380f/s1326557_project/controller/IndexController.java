package hkmu.comps380f.s1326557_project.controller;

import hkmu.comps380f.s1326557_project.model.Book;
import hkmu.comps380f.s1326557_project.model.BookUser;
import hkmu.comps380f.s1326557_project.model.Bookmark;
import hkmu.comps380f.s1326557_project.repository.UserRepository;
import hkmu.comps380f.s1326557_project.service.*;
import hkmu.comps380f.s1326557_project.model.Forms;
import hkmu.comps380f.s1326557_project.validator.UserValidator;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class IndexController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final UserValidator userValidator;

    private final UserManagementService userManagementService;
    private final BookService bookService;
    private final CountryService countryService;
    private final BookmarkService bookmarkService;
    private final UserService userService;

    public IndexController(UserValidator userValidator, UserManagementService userManagementService, BookService bookService, CountryService countryService, BookmarkService bookmarkService, UserService userService) {
        this.userValidator = userValidator;
        this.userManagementService = userManagementService;
        this.bookService = bookService;
        this.countryService = countryService;
        this.bookmarkService = bookmarkService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "20") int size) {
        Page<Book> book = bookService.getBooks(page, size);
        Set<Long> bookmarkedIds = new HashSet<>();
        BookUser user = userService.getCurrentUser();
        if (user != null) {
            List<Bookmark> bookmarks = bookmarkService.getBookmarks();
                if (bookmarks != null) {
                bookmarkedIds = bookmarks.stream()
                        .map(bookmark -> bookmark.getBook().getId())
                        .collect(Collectors.toSet());
                }
        }
        model.addAttribute("books", book.getContent());
        model.addAttribute("bookmarkedIds", bookmarkedIds);
        model.addAttribute("images", bookService.getAllImages());
        model.addAttribute("pageTitle", "Book Store");
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", book.getTotalPages());
        return "index";
    }


    @GetMapping("/register")
    public ModelAndView register(Model model) {
            model.addAttribute("pageTitle", "Book Store");
            model.addAttribute("countries", countryService.getCountries());
            return new ModelAndView("register", "user", new Forms.RegisterForm());
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid Forms.RegisterForm form, BindingResult result, Model model) throws IOException {
        userValidator.validate(form,result);
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Book Store");
            model.addAttribute("countries", countryService.getCountries());
            return "register";
        }
        userManagementService.createUserAndCustomer(
                form.getUsername(),form.getPassword(), new String[]{"ROLE_USER"},
                form.getFirstname(), form.getLastname(),
                form.getEmail(), form.getPhone(),
                form.getCountry(), form.getCity(),
                form.getAddress1(), form.getAddress2(), form.getPostalCode());
        logger.info("User {} created.", form.getUsername());
        return "redirect:/login";
    }
}