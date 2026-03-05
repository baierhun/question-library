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
            System.out.println(e);
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
            System.out.println(e);
        }
    }

    public void insertAnswers(List<Answer> answers, long questionId) {
        /*
        INSERT INTO answer_option (option_text, is_answer, question_id)
            VALUES ('Hello',2),
            VALUES ('World',1,2);

         */
        if (answers.size() == 0) return;
        StringBuilder insertAnswerSql = new StringBuilder("INSERT INTO answer_option (option_text, is_answer, " +
                "question_id) VALUES");
        for (int i = 0; i < answers.size(); i++) {
            insertAnswerSql.append(makeInsert(i == answers.size() - 1));
        }

        try (PreparedStatement sql = conn.prepareStatement(insertAnswerSql.toString())) {
            int i = 1;
            for (Answer a : answers) {
                sql.setString(i++, a.text());
                sql.setBoolean(i++, a.isAnswer());
                sql.setLong(i++, questionId);
            }
            sql.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int deleteAnswers(long questionId) {
        String sqlString = """
                DELETE FROM answer_option AS ao
                WHERE ao.question_id = ?
                """;

        try (PreparedStatement sql = conn.prepareStatement(sqlString)) {
            sql.setLong(1, questionId);
            return sql.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    private static String makeInsert(boolean isLast) {
        if (isLast) return " (?, ?, ?)";
        return " (?, ?, ?),";
    }

}
