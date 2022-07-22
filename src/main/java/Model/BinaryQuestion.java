package Model;

import java.util.ArrayList;

public class BinaryQuestion extends AbstractQuestion {
    public BinaryQuestion(int id, String question, ArrayList<String> answers, String correctAnswer, QuestionType questionType) {
        super(id, question, answers, correctAnswer, questionType);
    }
}
