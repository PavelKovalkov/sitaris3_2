package course.project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_admins")
@Getter
@Setter
public class Admin {
    @Id
    private String login;
    private String password;
}
