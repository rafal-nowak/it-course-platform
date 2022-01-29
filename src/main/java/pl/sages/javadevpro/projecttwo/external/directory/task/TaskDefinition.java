package pl.sages.javadevpro.projecttwo.external.directory.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDefinition {
    private List<FileToBeDeliveredToUser> filesToBeDeliveredToUser;
    private Environment environment;
    private String taskVerificationMethod;
    private List<ResultFile> resultFiles;
}
