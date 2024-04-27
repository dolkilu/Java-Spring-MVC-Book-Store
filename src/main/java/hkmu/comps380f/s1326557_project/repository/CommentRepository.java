package hkmu.comps380f.s1326557_project.repository;

import hkmu.comps380f.s1326557_project.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBookId(Long bookId);
}
