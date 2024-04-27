package hkmu.comps380f.s1326557_project.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_user")
public class BookUser{
    @Id
    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name ="password",nullable = false)
    private String password;

    @OneToMany(mappedBy ="user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> roles = new ArrayList<>();

    public BookUser() {}

    public BookUser(String username, String password, String[] roles) {
        this.username = username;
        this.password = password;
        for (String role : roles){
        this.roles.add(new UserRole(this, role));
        }
    }

    public BookUser(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
