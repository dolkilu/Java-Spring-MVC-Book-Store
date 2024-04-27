package hkmu.comps380f.s1326557_project.service;

import hkmu.comps380f.s1326557_project.repository.*;
import hkmu.comps380f.s1326557_project.exception.BookNotFound;
import hkmu.comps380f.s1326557_project.exception.ImageNotFound;
import hkmu.comps380f.s1326557_project.model.*;
import hkmu.comps380f.s1326557_project.utils.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, ImageRepository imageRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.imageRepository = imageRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public Page<Book> getBooks(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page, size));
    }

    public Book getBook(long bookId) throws BookNotFound {
        return bookRepository.findById(bookId).orElseThrow(()-> new BookNotFound(bookId));
    }

    @Transactional
    public Book addBook(Forms.BookForm form) throws BookNotFound, IOException {
        Book book = new Book(form.getName(), form.getAuthors(),
                form.getPrice(), form.getIsbn(),
                form.getPublisher(), form.getPublish_date(),
                form.getDescription(),form.getQuantity(),form.getAvailability());
        Book saved = bookRepository.save(book);
        if (form.getImages() != null && form.getImages().length > 0) {
            System.out.println("upload");
            uploadImage(saved.getId(), form.getImages());
        }
        return saved;
    }

    @Transactional
    public void editBook(Long bookId, Forms.BookForm form) throws BookNotFound {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFound(bookId));
        book.setName(form.getName());
        book.setAuthors(form.getAuthors());
        book.setPrice(form.getPrice());
        book.setIsbn(form.getIsbn());
        book.setPublisher(form.getPublisher());
        book.setPublish_date(form.getPublish_date());
        book.setDescription(form.getDescription());
        book.setQuantity(form.getQuantity());
        book.setAvailability(form.getAvailability());

        if(book.getQuantity() == 0) {book.setAvailability(false);}

        bookRepository.save(book);

    }

    @Transactional
    public void deleteBook(Long bookId) throws BookNotFound {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFound(bookId));
        bookRepository.delete(book);
    }

    @Transactional
    public void uploadImage(Long bookId, MultipartFile[] files) throws BookNotFound, IOException{
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFound(bookId));
        for (MultipartFile file: files) {
            if (file.isEmpty()) {return;}
            Image image = new Image();
            image.setName(bookId + "_" + file.getOriginalFilename());
            image.setMimeContentType(file.getContentType());
            image.setContents(file.getBytes());
            image.setBook(book);
            if (image.getContents() != null) {
            imageRepository.save(image);
            }
        }
    }

    public List<Comment> getComments(long bookId) {
        return commentRepository.findByBookId(bookId);
    }
    @Transactional
    public void addComment(Long bookId, Forms.CommentForm commentForm) throws BookNotFound {
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFound(bookId));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        BookUser user = userRepository.findById(username).orElseThrow(()-> new UsernameNotFoundException(username));
        String date = DateTimeUtils.formatLocalDateTime(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
        Comment comment = new Comment(commentForm.getComment(), user, book, date);
        commentRepository.save(comment);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image getImage(long imageId) throws ImageNotFound {
        Image image = imageRepository.findById(imageId).orElse(null);
        if (image == null) {
            throw new ImageNotFound(imageId);
        }
        return image;
    }

    public List<Image> getImages(long bookId) throws BookNotFound{
        Book book = bookRepository.findById(bookId).orElseThrow(()-> new BookNotFound(bookId));

        return book.getImages() != null ? book.getImages() : Collections.emptyList();
    }


    @Transactional
    public void removeImage(long bookId, long imageId) throws BookNotFound, ImageNotFound {
        Book book = getBook(bookId);
        List<Image> images = book.getImages();
        images.remove(getImage(imageId));
        book.setImages(images);
        bookRepository.save(book);
    }

}