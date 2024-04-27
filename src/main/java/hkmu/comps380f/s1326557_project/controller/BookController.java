package hkmu.comps380f.s1326557_project.controller;

import hkmu.comps380f.s1326557_project.service.BookService;
import hkmu.comps380f.s1326557_project.repository.BookmarkRepository;
import hkmu.comps380f.s1326557_project.service.UserService;
import hkmu.comps380f.s1326557_project.exception.BookNotFound;
import hkmu.comps380f.s1326557_project.exception.ImageNotFound;
import hkmu.comps380f.s1326557_project.model.Book;
import hkmu.comps380f.s1326557_project.model.BookUser;
import hkmu.comps380f.s1326557_project.model.Forms;
import hkmu.comps380f.s1326557_project.model.Comment;
import hkmu.comps380f.s1326557_project.model.Image;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final BookmarkRepository bookmarkRepository;

    public BookController(BookService bookService, UserService userService, BookmarkRepository bookmarkRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.bookmarkRepository = bookmarkRepository;
    }


    @GetMapping("/{bookId}")
    public String getBookInfo(@PathVariable("bookId") Long bookId, Model model
                              ) throws BookNotFound {
        model.addAttribute("pageTitle", "Book Info");
        Book book = bookService.getBook(bookId);
        BookUser user = userService.getCurrentUser();
        List<Image> images = bookService.getImages(bookId);
        List<Comment> comments = bookService.getComments(bookId);
        Forms.CommentForm commentForm = new Forms.CommentForm();
        boolean isBookmarked = false;

        if (user != null) {
            isBookmarked = bookmarkRepository.existsByUserAndBook(user, book);
        }

        model.addAttribute("book", book);
        model.addAttribute("images", images);
        model.addAttribute("comments", comments);
        model.addAttribute("commentForm", commentForm);
        model.addAttribute("isBookmarked", isBookmarked);

        return "bookInfo";
    }

    @PostMapping("/{bookId}")
    public String addComment(@PathVariable("bookId") Long bookId, @ModelAttribute("commentForm") Forms.CommentForm commentForm,
                             BindingResult result) throws BookNotFound {
        if (result.hasErrors()) {
            return "redirect:/books/" + bookId;
        }
        bookService.addComment(bookId, commentForm);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/{bookId}/uploadImages")
    public String uploadImages(@PathVariable Long bookId, @RequestParam("images")MultipartFile[] files,
                               @RequestParam("redirectUrl") String url, RedirectAttributes ra) {
        try {
            bookService.uploadImage(bookId, files);
            ra.addFlashAttribute("success", true);
            ra.addFlashAttribute("message", "Successfully upload image.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", true);
            ra.addFlashAttribute("message", "Failed to upload image.");
            return "redirect:" + url;
        }
        return "redirect:" + url ;
    }

    @GetMapping("/{bookId}/images/{imageId}")
    public void displayImage(@PathVariable("imageId") long imageId,
                             HttpServletResponse response
                                     ) throws  ImageNotFound, IOException {
        Image image = bookService.getImage(imageId);
        if (image != null && image.getContents() != null) {
                response.setContentType(image.getMimeContentType());
                response.getOutputStream().write(image.getContents());
                response.getOutputStream().close();
        } else {
                response.sendError(HttpStatus.NOT_FOUND.value(), "Image not found");
        }
    }
}
