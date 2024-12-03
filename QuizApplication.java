import java.util.*;
import java.util.concurrent.*;

class Question {
    String questionText;
    String[] options;
    String correctAnswer;

    public Question(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect(String answer) {
        return answer.equalsIgnoreCase(correctAnswer);
    }
}

public class QuizApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Question> questions = new ArrayList<>();
    private static int score = 0;

    public static void main(String[] args) {
        // Add sample questions
        addQuestions();

        System.out.println("Welcome to the Quiz Application!");

        // Start the quiz
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            // Display the question and options
            System.out.println("\nQuestion " + (i + 1) + ": " + question.questionText);
            for (int j = 0; j < question.options.length; j++) {
                System.out.println((j + 1) + ". " + question.options[j]);
            }

            // Start a timer for answering the question (e.g., 30 seconds per question)
            String userAnswer = getUserAnswerWithTimer(30);

            // Check if the answer is correct and update the score
            if (question.isCorrect(userAnswer)) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect! The correct answer was: " + question.correctAnswer);
            }
        }

        // Display the result
        displayResult();
    }

    // Method to add questions to the quiz
    private static void addQuestions() {
        questions.add(new Question("What is the capital of France?", new String[]{"Paris", "London", "Rome", "Berlin"}, "Paris"));
        questions.add(new Question("Which programming language is used for Android development?", new String[]{"Java", "C++", "Python", "Ruby"}, "Java"));
        questions.add(new Question("What is the largest planet in our solar system?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, "Jupiter"));
        questions.add(new Question("What is the chemical symbol for water?", new String[]{"H2O", "O2", "CO2", "HO2"}, "H2O"));
        questions.add(new Question("Who wrote 'To Kill a Mockingbird'?", new String[]{"Harper Lee", "Mark Twain", "J.K. Rowling", "Ernest Hemingway"}, "Harper Lee"));
    }

    // Method to get the user's answer with a timer
    private static String getUserAnswerWithTimer(int timeLimitInSeconds) {
        final String[] userAnswer = {""};
        long endTime = System.currentTimeMillis() + timeLimitInSeconds * 1000;
        
        // Start a timer thread
        Thread timerThread = new Thread(() -> {
            while (System.currentTimeMillis() < endTime && userAnswer[0].isEmpty()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100); // Sleep for 100ms and check again
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (userAnswer[0].isEmpty()) {
                System.out.println("\nTime's up! Moving to the next question.");
            }
        });

        timerThread.start();

        // Ask the user for their answer
        while (System.currentTimeMillis() < endTime && userAnswer[0].isEmpty()) {
            System.out.print("\nYour answer: ");
            userAnswer[0] = scanner.nextLine().trim();
        }

        return userAnswer[0];
    }

    // Method to display the final score and result summary
    private static void displayResult() {
        System.out.println("\nQuiz finished!");
        System.out.println("Your final score: " + score + " out of " + questions.size());
        System.out.println("Correct answers: " + score);
        System.out.println("Incorrect answers: " + (questions.size() - score));
    }
}

