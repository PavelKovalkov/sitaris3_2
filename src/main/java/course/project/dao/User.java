package course.project.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_users")
@Getter
@Setter
public class User {
    @Id
    private String email;
    private String username;
    private String password;
}
