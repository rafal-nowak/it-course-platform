package pl.sages.javadevpro.projecttwo;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    classes = ProjectTwoApplication.class
)
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
public class BaseIT {



}
