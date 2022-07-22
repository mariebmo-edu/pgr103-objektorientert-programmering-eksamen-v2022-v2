package Model;

public class UserScore {

    int id;
    String user;
    int score;
    QuestionType topic;

    public UserScore(int id, String user, int score, QuestionType topic) {
        this.id = id;
        this.user = user;
        this.score = score;
        this.topic = topic;
    }

    public UserScore(String user, int score, QuestionType topic) {
        this.user = user;
        this.score = score;
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public QuestionType getTopic() {
        return topic;
    }

    public void setTopic(QuestionType topic) {
        this.topic = topic;
    }
}
