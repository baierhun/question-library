package backend.services;

import backend.repositories.AnswerRepo;
import backend.repositories.QuestionRepo;
import models.Answer;
import models.Question;
import models.QuestionAnswer;

import java.util.List;

public class QuestionService {
    private final QuestionRepo repo;
    private final AnswerRepo answerRepo;

    public QuestionService(QuestionRepo questionRepo, AnswerRepo answerRepo) {
        repo = questionRepo;
        this.answerRepo = answerRepo;
    }

    public List<Question> getAllQuestions() {
        return repo.getQuestions();
    }

    public QuestionAnswer getQuestion(int questionId) {
        List<Answer> answers = answerRepo.getQuestionAnswers(questionId);
        Question question = repo.getQuestion(questionId);
        return new QuestionAnswer(question, answers);
    }

    public void updateAnswer(Answer answer) {
        answerRepo.updateAnswer(answer);
    }

    public void updateQuestion(Question question) {
        repo.updateQuestion(question);
    }

    public long addQuestion(Question question) {
        return repo.addQuestion(question);
    }
}
