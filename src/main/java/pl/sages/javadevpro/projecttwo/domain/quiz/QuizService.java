package pl.sages.javadevpro.projecttwo.domain.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.GradingTableService;
import pl.sages.javadevpro.projecttwo.domain.gradingtable.model.GradingTable;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.GradeNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizNotCheckedException;
import pl.sages.javadevpro.projecttwo.domain.quiz.exception.QuizNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.PageQuiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Question;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuestionType;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.Quiz;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizSolutionTemplate;
import pl.sages.javadevpro.projecttwo.domain.quiz.model.QuizStatus;
import pl.sages.javadevpro.projecttwo.domain.task.CommandNotSupportedException;

import java.util.Optional;

@RequiredArgsConstructor
public class QuizService {

    private final QuizSolutionTemplateService quizSolutionTemplateService;
    private final QuizRepository quizRepository;
    private final GradingTableService gradingTableService;

    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public void update(Quiz quiz) {
        quizRepository.update(quiz);
    }

    public void removeById(String id) {
        quizRepository.remove(id);
    }

    public Quiz findById(String id) {
        return quizRepository.findById(id)
                .orElseThrow(QuizNotFoundException::new);
    }

    public PageQuiz findAll(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }

    public PageQuiz findAllByUserId(Pageable pageable, String userId) {
        return quizRepository.findAllByUserId(pageable, userId);
    }

    public String executeCommand(QuizCommand quizCommand, String quizId) {
        if (quizCommand.equals(QuizCommand.CHECK)) {
            return checkQuiz(quizId);
        }
        if (quizCommand.equals(QuizCommand.RATE)) {
            return rateQuiz(quizId);
        }
        throw new CommandNotSupportedException();
    }

    public boolean isQuizAssignedToUser(String userId, String quizId){
        return findById(quizId).getUserId().equals(userId);
    }

    public boolean hasUserQuizCreatedFromQuizTemplateWithId(String userId, String quizTemplateId){
        return quizRepository.findAllByTestTemplateIdAndUserId(quizTemplateId, userId).size() > 0;
    }

    private String rateQuiz(String quizId) {
        Quiz quiz = findById(quizId);
        GradingTable gradingTable = gradingTableService.findById(quiz.getGradingTableId());

        if (quiz.getStatus() != QuizStatus.CHECKED) {
            throw new QuizNotCheckedException();
        }

        var percentage = quiz.getScore() * 100 / quiz.getMaximumScore();

        var grade = gradingTable.getRecords()
                .stream()
                .filter(gradingRecord -> gradingRecord.getLowerLimit() <= percentage && percentage <= gradingRecord.getUpperLimit())
                .findFirst()
                .map(record -> record.getGrade())
                .orElseThrow(GradeNotFoundException::new);
        quiz.setGrade(grade);

        quiz.setStatus(QuizStatus.RATED);
        update(quiz);
        return quiz.getStatus().toString();
    }

    private String checkQuiz(String quizId) {
        Quiz quiz = findById(quizId);
        QuizSolutionTemplate quizSolutionTemplate = quizSolutionTemplateService.findByQuizTemplateId(quiz.getTestTemplateId());

        var questions = quiz.getQuestions();
        var questionsFromSolutionTemplate = quizSolutionTemplate.getQuestions();
        boolean quizContainsOpenQuestion = false;
        for (Question question : questions) {
            if (question.getType() == QuestionType.SINGLE_CHOICE_CLOSED_QUESTION || question.getType() == QuestionType.MULTI_CHOICE_CLOSED_QUESTION) {
                var answers = question.getAnswers();
                var questionFromSolutionTemplate = questionsFromSolutionTemplate.stream()
                        .filter(question1 -> question1.getQuestionId() == question.getQuestionId()).findFirst();

                if (questionFromSolutionTemplate.isPresent()) {
                    var answersFromSolutionTemplate = questionFromSolutionTemplate.get().getAnswers();
                    if (answers.equals(answersFromSolutionTemplate)) {
                        question.setScore(question.getMaximumScore());
                    } else {
                        question.setScore(0);
                    }
                }
            }
            if (question.getType() == QuestionType.OPEN_QUESTION) {
                quizContainsOpenQuestion = true;
            }
        }

        var maximumScore = questions.stream().mapToInt(question -> question.getMaximumScore()).sum();
        var score = questions.stream().mapToInt(question -> question.getScore()).sum();

        quiz.setMaximumScore(maximumScore);
        quiz.setScore(score);

        if (quizContainsOpenQuestion) {
            quiz.setStatus(QuizStatus.PARTIALLY_CHECKED);
        } else {
            quiz.setStatus(QuizStatus.CHECKED);
        }

        update(quiz);
        return quiz.getStatus().toString();
    }
}
