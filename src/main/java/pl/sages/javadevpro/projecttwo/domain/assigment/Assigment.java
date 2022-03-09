package pl.sages.javadevpro.projecttwo.domain.assigment;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Assigment {

    private String id;
    @NonNull
    private String userId;
    @NonNull
    private String taskId;

}
