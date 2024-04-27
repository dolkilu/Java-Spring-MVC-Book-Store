package hkmu.comps380f.s1326557_project.repository;

import hkmu.comps380f.s1326557_project.model.BookUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BookUser, String> {
    BookUser findBookUserByUsername(String username);
}
