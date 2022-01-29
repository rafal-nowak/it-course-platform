package pl.sages.javadevpro.projecttwo.api.usertask;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListOfFilesResponse {

    private String status;
    private List<String> files;
}
