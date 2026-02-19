package models;

public record QuestionAnswer(Question question, Iterable<Answer> answers) {}
