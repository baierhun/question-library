package backend.services;

import backend.repositories.AnswerRepo;
import backend.repositories.QuestionRepo;
import models.Answer;
import models.Question;
import models.QuestionAnswer;

import java.util.List;

public class AnswerService {
    private final AnswerRepo answerRepo;

    public AnswerService(AnswerRepo answerRepo) {
        this.answerRepo = answerRepo;
    }

    public void addAnswers(List<Answer> answers, int questionId) {
        answerRepo.insertAnswers(answers, questionId);
    }
}
