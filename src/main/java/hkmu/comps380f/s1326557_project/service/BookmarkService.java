package hkmu.comps380f.s1326557_project.service;

import hkmu.comps380f.s1326557_project.exception.BookNotFound;
import hkmu.comps380f.s1326557_project.model.Book;
import hkmu.comps380f.s1326557_project.model.BookUser;
import hkmu.comps380f.s1326557_project.model.Bookmark;
import hkmu.comps380f.s1326557_project.repository.BookRepository;
import hkmu.comps380f.s1326557_project.repository.BookmarkRepository;
import hkmu.comps380f.s1326557_project.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookmarkService {
    private final BookRepository bookRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public BookmarkService(BookRepository bookRepository, BookmarkRepository bookmarkRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
    }

    private BookUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findById(username).orElseThrow(()-> new UsernameNotFoundException(username));
    }

    public List<Bookmark> getBookmarks() {
        BookUser user = getUser();
        return bookmarkRepository.findByUser(user);
    }

    @Transactional
    public void addBookmark(Long bookId) throws BookNotFound {
        BookUser user = getUser();
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFound(bookId));
        if (!bookmarkRepository.existsByUserAndBook(user, book)) {
            Bookmark bookmark = new Bookmark(user, book);
            bookmarkRepository.save(bookmark);
        }
    }

    @Transactional
    public void removeBookmark(Long bookId) {
        BookUser user = getUser();
        Book book = bookRepository.findById(bookId).orElse(null);
        Bookmark bookmark = bookmarkRepository.findByUserAndBook(user, book);
        if (bookmarkRepository.existsByUserAndBook(user, book)) {
            bookmarkRepository.delete(bookmark);
        }
    }

}
