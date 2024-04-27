package hkmu.comps380f.s1326557_project.controller;

import hkmu.comps380f.s1326557_project.exception.BookNotFound;
import hkmu.comps380f.s1326557_project.exception.CommentNotFound;
import hkmu.comps380f.s1326557_project.exception.ImageNotFound;
import hkmu.comps380f.s1326557_project.model.Book;
import hkmu.comps380f.s1326557_project.model.Forms;
import hkmu.comps380f.s1326557_project.model.Image;
import hkmu.comps380f.s1326557_project.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller

@RequestMapping("/admin")
public class AdminBookController {
    private final BookService bookService;
    private final AdminService adminService;

    public AdminBookController(BookService bookService,AdminService adminService) {
        this.bookService = bookService;
        this.adminService = adminService;
    }

    @PostMapping("/{bookId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable("commentId") Long commentId, @PathVariable("bookId") Long bookId) throws CommentNotFound {
        adminService.deleteComment(commentId);
        return "redirect:/books/" + bookId;

    }

    @GetMapping("/addNewBook")
    public ModelAndView addNewBook(Model model) {
        model.addAttribute("pageTitle", "Add New Book");
        return new ModelAndView("addNewBook", "book", new Forms.BookForm());
    }

    @PostMapping("/addNewBook")
    public String addNewBook(@ModelAttribute("book") @Valid Forms.BookForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "addNewBook";
        }
        try {
            Book book = bookService.addBook(form);
            System.out.println("success");
            return "redirect:/books/" + book.getId();
        } catch (Exception e) {
            return "redirect:/admin/addNewBook";
        }

    }

    @GetMapping("/{bookId}/edit")
    public ModelAndView editBook(Model model, @PathVariable("bookId") long bookId) throws BookNotFound {
        Book book = bookService.getBook(bookId);
        List<Image> images = bookService.getImages(bookId);
        Forms.BookForm form = new Forms.BookForm(book.getName(), book.getAuthors(), book.getPrice(),
                book.getIsbn(),book.getPublisher(),book.getPublish_date(),book.getDescription(),
                book.getQuantity(),book.getAvailability());
        model.addAttribute("pageTitle", "Edit Book");
        model.addAttribute("book", book);
        model.addAttribute("images", images);
        return new ModelAndView("editBook", "editBook", form);
    }

    @PostMapping("/{bookId}/edit")
    public String editBook(@ModelAttribute("editBook") @Valid Forms.BookForm form, BindingResult result
            , @PathVariable("bookId") long bookId, RedirectAttributes redirectAttributes) throws BookNotFound {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", true);
            redirectAttributes.addFlashAttribute("message", "Failed to edit or delete book.");
            return "redirect:/admin/" + bookId + "/edit";
        }
        bookService.editBook(bookId, form);
        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Successfully edit book.");
        return "redirect:/admin/" + bookId + "/edit";
    }

    @PostMapping("/{bookId}/delete")
    public String deleteBook(@RequestParam ("bookId") long bookId, RedirectAttributes ra) {
        try {
            bookService.deleteBook(bookId);
            String msg = "Successfully delete book id: " + bookId ;
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", msg);
        } catch (Exception e) {
            String msg = "Failed to delete book id: " + bookId +".\r\nThere may be orders relying on it, delete the orders first!";
            ra.addFlashAttribute("error", true);
            ra.addFlashAttribute("message", msg);
            return "redirect:/admin/" + bookId +"/edit";
        }
        return "redirect:/";
    }

    @PostMapping("/{bookId}/remove/{imageId}")
    public String deleteImage(@PathVariable ("bookId") long bookId, @PathVariable("imageId") long imageId)
            throws BookNotFound, ImageNotFound {
        bookService.removeImage(bookId, imageId);

        return "redirect:/admin/" + bookId + "/edit";
    }
}