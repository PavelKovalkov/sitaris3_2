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
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Collection<Ticket> tickets;
}
