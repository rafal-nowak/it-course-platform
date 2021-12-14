package pl.sages.javadevpro.projecttwo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sages.javadevpro.projecttwo.validation.AddValidators;
import pl.sages.javadevpro.projecttwo.validation.UniqueUsername;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@UniqueUsername(groups = {AddValidators.class})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String username;
    private String password;
    private int enabled;

    private String role;

    @Transient
    private String confirmPassword;

}
