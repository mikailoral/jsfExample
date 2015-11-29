package spring.model;

import javax.persistence.*;

/**
 * Created by dozac on 15/04/2015.
 */

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name="ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String role;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="USER_ROLES",
            joinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")}
    )
    private Set<User> userRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<User> userRoles) {
        this.userRoles = userRoles;
    }

}
