package pl.sages.javadevpro.projecttwo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.sages.javadevpro.projecttwo.domain.assigment.AssigmentRepository;
import pl.sages.javadevpro.projecttwo.domain.assigment.AssigmentService;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.GradingTableRepository;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.GradingTableService;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizService;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizSolutionTemplateRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizSolutionTemplateService;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateRepository;
import pl.sages.javadevpro.projecttwo.domain.quiz.QuizTemplateService;
import pl.sages.javadevpro.projecttwo.domain.quiz.assigment.QuizAssigmentService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintRepository;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintService;
import pl.sages.javadevpro.projecttwo.domain.task.TaskExecutor;
import pl.sages.javadevpro.projecttwo.domain.task.TaskRepository;
import pl.sages.javadevpro.projecttwo.domain.task.TaskService;
import pl.sages.javadevpro.projecttwo.domain.task.Workspace;
import pl.sages.javadevpro.projecttwo.domain.user.EncodingService;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;
import pl.sages.javadevpro.projecttwo.domain.user.UserService;
import pl.sages.javadevpro.projecttwo.external.env.TaskEnvAdapter;
import pl.sages.javadevpro.projecttwo.external.env.kafka.KafkaTaskEnv;
import pl.sages.javadevpro.projecttwo.external.env.task.TaskEnvMapper;
import pl.sages.javadevpro.projecttwo.external.storage.assigment.AssigmentEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.assigment.AssigmentMongoRepository;
import pl.sages.javadevpro.projecttwo.external.storage.assigment.AssigmentStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.gradingtable.GradingTableEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.gradingtable.GradingTableStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.gradingtable.MongoGradingTableRepository;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.MongoQuizRepository;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.MongoQuizSolutionTemplateRepository;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.MongoQuizTemplateRepository;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizSolutionTemplateEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizSolutionTemplateStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizTemplateEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.quiz.QuizTemplateStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintMongoRepository;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskBlueprintStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskEntityStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.storage.task.TaskMongoRepository;
import pl.sages.javadevpro.projecttwo.external.storage.user.MongoUserRepository;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserEntityMapper;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserStorageAdapter;
import pl.sages.javadevpro.projecttwo.external.workspace.WorkspaceService;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public GradingTableRepository gradingTableRepository(MongoGradingTableRepository mongoGradingTableRepository, GradingTableEntityMapper mapper) {
        return new GradingTableStorageAdapter(mongoGradingTableRepository, mapper);
    }

    @Bean
    public GradingTableService gradingTableService(GradingTableRepository gradingTableRepository)  {
        return new GradingTableService(gradingTableRepository);
    }

    @Bean
    public UserRepository userRepository(MongoUserRepository mongoUserRepository, UserEntityMapper mapper) {
        return new UserStorageAdapter(mongoUserRepository, mapper);
    }

    @Bean
    public UserService userService(UserRepository userRepository, EncodingService encoder)  {
        return new UserService(userRepository, encoder);
    }

    @Bean
    public QuizRepository quizRepository(MongoQuizRepository mongoQuizRepository, QuizEntityMapper mapper) {
        return new QuizStorageAdapter(mongoQuizRepository, mapper);
    }
    @Bean
    public QuizTemplateRepository quizTemplateRepository(MongoQuizTemplateRepository mongoQuizTemplateRepository, QuizTemplateEntityMapper mapper) {
        return new QuizTemplateStorageAdapter(mongoQuizTemplateRepository, mapper);
    }

    @Bean
    public QuizSolutionTemplateRepository quizSolutionTemplateRepository(MongoQuizSolutionTemplateRepository mongoQuizSolutionTemplateRepository, QuizSolutionTemplateEntityMapper mapper) {
        return new QuizSolutionTemplateStorageAdapter(mongoQuizSolutionTemplateRepository, mapper);
    }

    @Bean
    public QuizAssigmentService quizAssigmentService(QuizTemplateRepository quizTemplateRepository, QuizRepository quizRepository) {
        return new QuizAssigmentService(quizTemplateRepository, quizRepository);
    }

    @Bean
    public QuizService quizService(QuizSolutionTemplateService quizSolutionTemplateService, QuizRepository quizRepository, GradingTableService gradingTableService) {
        return new QuizService(quizSolutionTemplateService, quizRepository, gradingTableService);
    }

    @Bean
    public QuizTemplateService quizTemplateService(QuizTemplateRepository quizTemplateRepository) {
        return new QuizTemplateService(quizTemplateRepository);
    }

    @Bean
    public QuizSolutionTemplateService quizSolutionTemplateService(QuizSolutionTemplateRepository quizSolutionTemplateRepository) {
        return new QuizSolutionTemplateService(quizSolutionTemplateRepository);
    }

    @Bean
    public TaskBlueprintRepository taskBlueprintRepository(TaskBlueprintMongoRepository taskRepository, TaskBlueprintEntityMapper mapper ) {
        return new TaskBlueprintStorageAdapter( taskRepository, mapper );
    }

    @Bean
    public TaskBlueprintService taskBlueprintService(TaskBlueprintRepository taskBlueprintRepository) {
        return new TaskBlueprintService(taskBlueprintRepository);
    }


    @Bean
    public TaskRepository taskRepository(TaskMongoRepository taskMongoRepository, TaskEntityMapper mapper)  {
        return new TaskEntityStorageAdapter(taskMongoRepository, mapper);
    }

    @Bean
    public TaskService taskService(
        @Value("${local.files.taskSummaryResult}") String resultFile,
        TaskRepository taskRepository,
        Workspace workspace,
        TaskBlueprintService taskBlueprintService,
        TaskExecutor taskExecutor
    )  {
        return new TaskService(taskRepository, workspace, taskBlueprintService, taskExecutor, resultFile);
    }

    @Bean
    public AssigmentRepository assigmentRepository(AssigmentMongoRepository assigmentMongoRepository, AssigmentEntityMapper mapper)  {
        return new AssigmentStorageAdapter(assigmentMongoRepository, mapper);
    }

    @Bean
    public AssigmentService assigmentService(AssigmentRepository assigmentRepository, TaskService taskService)  {
        return new AssigmentService(assigmentRepository, taskService);
    }

    @Bean
    public Workspace workspace(
        @Value("${local.folders.baseWorkspace}") String baseWorkspace,
        @Value("${local.files.taskDefinition}") String definitionFile,
        @Value("${local.files.taskSummaryResult}") String resultFile
    ) {
        return new WorkspaceService(baseWorkspace, definitionFile, resultFile);
    }

    @Bean
    public TaskExecutor taskExecutor1(KafkaTaskEnv kafkaTaskEnv, TaskEnvMapper taskEnvMapper){
        return new TaskEnvAdapter(kafkaTaskEnv, taskEnvMapper);}

}
