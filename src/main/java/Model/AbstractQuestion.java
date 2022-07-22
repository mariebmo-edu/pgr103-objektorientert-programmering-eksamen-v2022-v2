package Model;

import View.UIElements;

import java.util.ArrayList;

public abstract class AbstractQuestion {

    int id;
    String question;
    ArrayList<String> answers;
    String correctAnswer;
    QuestionType questionType;

    public AbstractQuestion(int id, String question, ArrayList<String> answers, String correctAnswer, QuestionType questionType) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public boolean isCorrectAnswer(String chosenAnswer) {

        return chosenAnswer.equalsIgnoreCase(correctAnswer);
    }

    public void showQuestion() {
        System.out.println(question);

        int i = 1;

        for (String answer : answers) {
            UIElements.formatMenuItem(answer, i++);
        }
    }
}
