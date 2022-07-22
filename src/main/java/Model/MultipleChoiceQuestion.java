package Model;

import java.util.ArrayList;

public class MultipleChoiceQuestion extends AbstractQuestion {
    public MultipleChoiceQuestion(int id, String question, ArrayList<String> answers, String correctAnswer, QuestionType questionType) {
        super(id, question, answers, correctAnswer, questionType);
    }
}
