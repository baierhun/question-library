package models;

import java.util.List;

public record QuestionAnswer(Question question, List<Answer> answers) {}
