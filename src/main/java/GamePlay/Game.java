package GamePlay;

import Helpers.Comparators.UserScoreComparator;
import Helpers.Evaluate;
import Model.*;
import Repo.BinaryQuestionRepo;
import Repo.MultipleChoiceQuestionRepo;
import Repo.ScoreRepo;
import View.UIElements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    int maxScore;
    int currentScore;
    String userName;

    ArrayList<MultipleChoiceQuestion> multiQuiz;
    ArrayList<BinaryQuestion> binaryQuiz;

    MultipleChoiceQuestionRepo multipleChoiceQuestionRepo;
    BinaryQuestionRepo binaryQuestionRepo;
    ScoreRepo scoreRepo;

    public Game() throws IOException {
        multipleChoiceQuestionRepo = new MultipleChoiceQuestionRepo();
        binaryQuestionRepo = new BinaryQuestionRepo();
        scoreRepo = new ScoreRepo();

        multiQuiz = multipleChoiceQuestionRepo.RetrieveAll();
        binaryQuiz = binaryQuestionRepo.RetrieveAll();
    }

    public void Play() {

        Scanner scanner = new Scanner(System.in);

        if (userName == null) {
            UIElements.formatHeader("QUIZ OF THE YEAR!");
            System.out.println("What's your name?");
            userName = scanner.nextLine();
        }

        System.out.println("welcome, " + userName + ", please select a quiz");

        UIElements.formatHeader("QUIZZES");
        UIElements.formatMenuItem("Food - Multiple Choice", 1);
        UIElements.formatMenuItem("Technology - Binary", 2);

        String userInput = scanner.nextLine();

        while (true) {

            if (userInput.equals("1")) {
                PlayGame(multiQuiz);
                break;
            } else if (userInput.equals("2")) {
                PlayGame(binaryQuiz);
                break;
            } else {
                System.out.println("invalid input, please try again");
            }
        }
    }

    public <T extends AbstractQuestion> void PlayGame(ArrayList<T> quiz) {

        Scanner scanner = new Scanner(System.in);
        QuestionType questionType = quiz.get(0).getQuestionType();

        Collections.shuffle(quiz);

        for (AbstractQuestion q : quiz) {

            q.showQuestion();

            System.out.println("Please select an answer: ");

            while (true) {
                String answer = scanner.nextLine();

                if (Evaluate.isInteger(answer)) {
                    int selectedInputNum = Integer.parseInt(answer);

                    if (selectedInputNum <= q.getAnswers().size() && selectedInputNum > 0) {

                        maxScore++;

                        if (q.isCorrectAnswer(q.getAnswers().get(selectedInputNum - 1))) {
                            currentScore++;
                            System.out.println("Correct! " + currentScore + "/" + maxScore);

                        } else {
                            System.out.println("Aww, wrong answer! " + currentScore + "/" + maxScore);
                        }
                        break;
                    }
                } else if ("N".equalsIgnoreCase(answer)) {
                    System.out.println("Next Question " + currentScore + "/" + ++maxScore);
                    break;
                }
                System.out.println("Invalid input, please try again");
            }
        }

        scoreRepo.insertUserScore(new UserScore(userName, currentScore, questionType));

        showScoreBoard(questionType);

        GoToMenu(scanner);
    }

    private void GoToMenu(Scanner scanner) {
        System.out.println("Go back to Main menu? y/n");

        String userInput = scanner.nextLine();

        switch (userInput.toLowerCase()) {
            case "y", "yes" -> Play();
            case "n", "no" -> System.out.println("Session Ending. Thank you for playing.");
            default -> System.out.println("Invalid input");
        }
    }

    private void showScoreBoard(QuestionType type) {
        UIElements.formatHeader("HIGH SCORE FOR " + type.toString().toUpperCase());
        ArrayList<UserScore> userScores = scoreRepo.RetrieveAll();

        userScores.sort(new UserScoreComparator());

        int i = 1;

        for (UserScore score : userScores) {

            if (score.getTopic() == type) {
                UIElements.formatMenuItem(score.getUser() + " " + score.getScore(), i++);
            }

        }

        System.out.println(UIElements.decorator);

        PrintScoreSelection(userScores);
    }

    private void PrintScoreSelection(ArrayList<UserScore> userScores) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Other High Score Options");

        UIElements.formatMenuItem("List All Scores", 1);
        UIElements.formatMenuItem("List Quiz Scores", 2);
        UIElements.formatMenuItem("List User Scores", 3);
        UIElements.formatMenuItem("List Scores Above Value", 4);
        System.out.println("B. Back to Menu");

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("1")) {
                listAllScores(userScores);
                break;
            } else if (userInput.equals("2")) {
                listAllScoresByType(userScores);
                break;
            } else if (userInput.equals("3")) {
                listUserScores(userScores);
                break;
            } else if (userInput.equals("4")) {
                listScoresAboveValue(userScores);
                break;
            } else if (userInput.equalsIgnoreCase("B")) {
                Play();
                break;
            }

            System.out.println("invalid command");
        }
    }

    private void listAllScores(ArrayList<UserScore> userScores) {

        UIElements.formatHeader("ALL HIGH SCORES");

        AtomicInteger i = new AtomicInteger(1);

        userScores.forEach((s) -> UIElements.formatMenuItem((s.getUser() + " " + s.getScore() + ", " + s.getTopic()), i.getAndIncrement()));
        System.out.println(UIElements.decorator);

    }

    private void listAllScoresByType(ArrayList<UserScore> userScores) {

        Scanner scanner = new Scanner(System.in);
        QuestionType type = null;
        boolean shouldShowScore = true;

        System.out.println("select topic");
        UIElements.formatMenuItem("Multiple Choice", 1);
        UIElements.formatMenuItem("Binary", 2);
        System.out.println("B. Back to Menu");

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("1")) {
                type = QuestionType.MultipleChoice;
                break;
            } else if (userInput.equals("2")) {
                type = QuestionType.Binary;
                break;
            } else if (userInput.equalsIgnoreCase("B")) {
                Play();
                shouldShowScore = false;
                break;
            } else {
                System.out.println("Invalid command");
            }
        }

        if (shouldShowScore) {

            UIElements.formatHeader("HIGH SCORES FOR " + type.toString().toUpperCase());

            int i = 1;
            for (UserScore score : userScores) {

                if (score.getTopic() == type) {
                    UIElements.formatMenuItem(score.getUser() + " " + score.getScore(), i++);
                }

            }

            System.out.println(UIElements.decorator);
        }
    }

    private void listUserScores(ArrayList<UserScore> userScores) {

        Scanner scanner = new Scanner(System.in);
        String selectedName = null;
        boolean shouldShowScore = true;

        System.out.println("select name");
        UIElements.formatMenuItem("Current user", 1);
        UIElements.formatMenuItem("Other Name", 2);
        System.out.println("B. Back to Menu");

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("1")) {
                selectedName = userName;
                break;
            } else if (userInput.equals("2")) {
                selectedName = scanner.nextLine();
                break;
            } else if (userInput.equalsIgnoreCase("B")) {
                Play();
                shouldShowScore = false;
                break;
            } else {
                System.out.println("Invalid command");
            }
        }

        if (shouldShowScore) {

            UIElements.formatHeader("HIGH SCORES FOR " + selectedName.toUpperCase());


            int i = 1;
            for (UserScore score : userScores) {

                if (score.getUser().equalsIgnoreCase(selectedName)) {
                    UIElements.formatMenuItem(score.getUser() + " " + score.getScore() + ", " + score.getTopic(), i++);
                }
            }
            System.out.println(UIElements.decorator);
        }
    }

    private void listScoresAboveValue(ArrayList<UserScore> userScores) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("select lowest number to show");

        String userInput = scanner.nextLine();

        while (!Evaluate.isInteger(userInput)) {
            System.out.println("Invalid - please write a number");
            userInput = scanner.nextLine();
        }

        UIElements.formatHeader("HIGH SCORES ABOVE " + userInput);


        int i = 1;
        for (UserScore score : userScores) {

            if (score.getScore() >= Integer.parseInt(userInput)) {
                UIElements.formatMenuItem(score.getUser() + " " + score.getScore() + ", " + score.getTopic(), i++);
            }
        }
        System.out.println(UIElements.decorator);
    }
}
