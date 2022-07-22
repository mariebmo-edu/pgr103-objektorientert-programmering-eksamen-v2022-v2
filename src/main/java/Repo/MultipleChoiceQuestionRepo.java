package Repo;

import Model.MultipleChoiceQuestion;
import Model.QuestionType;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class MultipleChoiceQuestionRepo extends AbstractRepo<MultipleChoiceQuestion> {

    public MultipleChoiceQuestionRepo() throws IOException {
        super("multichoiceQuiz");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MultipleChoiceQuestion resultMapper(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String question = resultSet.getString("question");
        String correctAnswer = resultSet.getString("correctAnswer");
        QuestionType questionType = QuestionType.MultipleChoice;

        String answerA = resultSet.getString("answerA");
        String answerB = resultSet.getString("answerB");
        String answerC = resultSet.getString("answerC");
        String answerD = resultSet.getString("answerD");

        ArrayList<String> answers = new ArrayList<>(Arrays.asList(answerA, answerB, answerC, answerD));

        return new MultipleChoiceQuestion(id, question, answers, correctAnswer, questionType);
    }

}
