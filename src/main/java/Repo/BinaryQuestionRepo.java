package Repo;

import Model.BinaryQuestion;
import Model.QuestionType;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class BinaryQuestionRepo extends AbstractRepo<BinaryQuestion> {

    public BinaryQuestionRepo() throws IOException {
        super("binaryQuiz");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BinaryQuestion resultMapper(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String question = resultSet.getString("question");
        String correctAnswer = resultSet.getString("correctAnswer");
        QuestionType questionType = QuestionType.Binary;
        ArrayList<String> answers = new ArrayList<>(Arrays.asList("yes", "no"));

        return new BinaryQuestion(id, question, answers, correctAnswer, questionType);
    }
}
