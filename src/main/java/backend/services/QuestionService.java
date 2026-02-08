package backend.services;

import backend.repositories.QuestionRepo;
import models.Question;

import java.util.List;

public class QuestionService {
    private final QuestionRepo repo;
    public QuestionService(QuestionRepo questionRepo) {
        repo = questionRepo;
    }

    public List<Question> getAllQuestions() {
        return repo.getQuestions();
    }
}
