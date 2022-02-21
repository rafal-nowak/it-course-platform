package pl.sages.javadevpro.projecttwo.domain.usertask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprint;
import pl.sages.javadevpro.projecttwo.external.directory.LocalDirectoryService;

import java.io.File;
import java.nio.file.Path;

@ExtendWith(MockitoExtension.class)
class LocalDirectoryServiceTest {

    private final LocalDirectoryService localDirectoryService = new LocalDirectoryService("testRepo/");
    private final static String TEST_DIRECTORY = Path.of("testRepo/ID39/1").toAbsolutePath().toString();
    private final static String USER_ID = "ID39";

    @Mock
    private TaskBlueprint taskBlueprint;

    @BeforeEach
    void defineMocks() {
        Mockito.when(taskBlueprint.getId()).thenReturn("1");
    }

    @DisplayName("should create new directory basing on user id and task id")
    @Test
    void shouldCreateNewDirectory() {
        String directoryPath = localDirectoryService.createDirectoryForUserTask(taskBlueprint,USER_ID);

        File directory = new File(directoryPath);

        Assertions.assertEquals(TEST_DIRECTORY, directoryPath);
        Assertions.assertTrue(directory.exists());
        Assertions.assertTrue(directory.isDirectory());
    }

}
