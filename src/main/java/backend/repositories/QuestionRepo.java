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

    public Question getQuestion(int questionId) {
        Question result;
        try (PreparedStatement sql = conn.prepareStatement("""
                Select q.id, q.question_text FROM question q
                WHERE q.id = ?
                """)) {
            sql.setInt(1, questionId);
            try (ResultSet rs = sql.executeQuery()) {
                rs.next();
                result = new Question(rs.getInt("id"), rs.getString("question_text"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void updateQuestion(Question question) {
        try (PreparedStatement sql = conn.prepareStatement("""
                UPDATE question
                SET question_text=?
                WHERE id=?;
                """)) {
            sql.setString(1, question.text());
            sql.setInt(2, question.id());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long addQuestion(Question question) {
        try (PreparedStatement sql = conn.prepareStatement("""
                INSERT INTO question (question_text)
                	VALUES (?);
                """)) {
            sql.setString(1, question.text());
            int affectedRows = sql.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = sql.getGeneratedKeys()) {
                    if (rs.next()) {
                        long newId = rs.getLong(1);
                        return newId;
                    }
                }
            }
            return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
