package hkmu.comps380f.s1326557_project.repository;

import hkmu.comps380f.s1326557_project.model.Book;
import hkmu.comps380f.s1326557_project.model.BookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hkmu.comps380f.s1326557_project.model.Bookmark;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByUserAndBook(BookUser user, Book book);
    Bookmark findByUserAndBook(BookUser user, Book book);

    List<Bookmark> findByUser(BookUser user);
}
