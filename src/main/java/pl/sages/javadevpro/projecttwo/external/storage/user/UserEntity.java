package pl.sages.javadevpro.projecttwo.external.storage.user;

import lombok.*;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class UserEntity {

    @Id
    private String email;
    private String name;
    private String password;
    @Convert(converter = StringListConverter.class)
    private List<String> roles;

}
