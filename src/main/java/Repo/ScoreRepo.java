package Repo;

import Model.QuestionType;
import Model.UserScore;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreRepo extends AbstractRepo<UserScore> {
    public ScoreRepo() throws IOException {
        super("score");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean insertUserScore(UserScore userScore) {
        String query = "INSERT INTO score(user, score, topic) VALUES('" + userScore.getUser() + "','" + userScore.getScore() + "','" + userScore.getTopic() + "')";
        return Insert(query);
    }

    @Override
    public UserScore resultMapper(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String user = resultSet.getString("user");
        int score = resultSet.getInt("score");
        QuestionType topic = QuestionType.valueOf(resultSet.getString("topic"));

        return new UserScore(id, user, score, topic);
    }
}
