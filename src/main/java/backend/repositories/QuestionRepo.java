package backend.repositories;

import models.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepo {

    private final Connection conn;

    public QuestionRepo(Connection conn) {
        this.conn = conn;
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        try (PreparedStatement sql = conn.prepareStatement("Select id, question_text FROM question")) {
            try (ResultSet rs = sql.executeQuery()) {
                while (rs.next()) {
                    questions.add(new Question(rs.getInt("id"), rs.getString("question_text")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }
}
