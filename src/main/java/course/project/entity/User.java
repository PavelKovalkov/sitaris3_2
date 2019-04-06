package course.project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
@Table(name = "app_users")
@Getter
@Setter
public class User {
    @Id
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть корректным")
    private String email;
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Collection<Ticket> tickets;
}
