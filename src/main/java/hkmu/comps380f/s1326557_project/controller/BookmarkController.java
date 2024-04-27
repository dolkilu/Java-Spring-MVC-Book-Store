package hkmu.comps380f.s1326557_project.controller;

import hkmu.comps380f.s1326557_project.exception.BookNotFound;
import hkmu.comps380f.s1326557_project.model.Bookmark;
import hkmu.comps380f.s1326557_project.service.BookmarkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/bookmarks")
    public String listBookmark(Model model) {
        model.addAttribute("pageTitle", "Bookmarks");
        List<Bookmark> bookmarks = bookmarkService.getBookmarks();
        model.addAttribute("bookmarks", bookmarks);
        return "bookmarks";
    }

    @PostMapping("/bookmark/add/{bookId}")
    public String addBookmark(@PathVariable("bookId") Long bookId,
                              @RequestParam ("redirectUrl") String url,
                              @RequestParam ("pageNum") int pageNum,
                              @RequestParam ("size") String size) throws BookNotFound {
        String redirectUrl = "redirect:" + url;
        if (pageNum > 0) {
            redirectUrl = "redirect:" + url + "?page=" + pageNum + "&size=" + size ;
        }
        bookmarkService.addBookmark(bookId);
        return redirectUrl;
    }

    @PostMapping("/bookmark/remove/{bookId}")
    public String removeBookmark(@PathVariable("bookId") Long bookId, @RequestParam("redirectUrl") String reURL) throws BookNotFound {
        bookmarkService.removeBookmark(bookId);
        return "redirect:" + reURL;
    }
}
