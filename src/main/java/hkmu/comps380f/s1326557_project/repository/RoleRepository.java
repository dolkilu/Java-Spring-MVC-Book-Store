package hkmu.comps380f.s1326557_project.repository;

import hkmu.comps380f.s1326557_project.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<UserRole,Integer> {
}
