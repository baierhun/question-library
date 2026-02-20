package backend.repositories;

import models.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnswerRepo {

    private final Connection conn;

    public AnswerRepo(Connection conn) {
        this.conn = conn;
    }

    public List<Answer> getQuestionAnswers(int questionId) {
        List<Answer> answers = new ArrayList<>();
        try (PreparedStatement sql = conn.prepareStatement("""
                Select id, option_text, is_answer FROM answer_option
                WHERE question_id = ?
                """)) {
            sql.setInt(1, questionId);
            try (ResultSet rs = sql.executeQuery()) {
                while (rs.next()) {
                    answers.add(new Answer(rs.getInt("id"), rs.getString("option_text"), rs.getBoolean("is_answer")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return answers;
    }

    public void updateAnswer(Answer answer) {
        try (PreparedStatement sql = conn.prepareStatement("""
                UPDATE answer_option
                SET is_answer=?,option_text=?
                WHERE id=?;
                """)) {
            sql.setBoolean(1, answer.isAnswer());
            sql.setString(2, answer.text());
            sql.setInt(3, answer.id());
            sql.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
