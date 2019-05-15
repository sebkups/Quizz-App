import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static final Random RANDOM = new Random();
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/main/resources/");
        File[] allFiles=file.listFiles();
        System.out.println("Pick category: ");
        for (int i=0;i<allFiles.length;i++){
            File categoryFile =allFiles[i];
            String categoryName=categoryFile.getName();
            categoryName = categoryName.substring(0, categoryName.length()-4);
            categoryName=categoryName.replace("_", " ");
            System.out.println(" " + (i+1) + "] " +categoryName);
        }
        Scanner in = new Scanner(System.in);
        int selection = in.nextInt()-1;
        File category = allFiles[selection];

        List<QuizQuest> questionForCategory=readQuestionFromFile(category);
        Set<Integer>questionAskedToPlayer = new HashSet<Integer>();
        int counter=0;
        for (int j=0; j<10;j++){
            counter+=askAQuestion(questionForCategory,questionAskedToPlayer);
        }
        System.out.println("End of game, you earned "+counter+" points");

    }

        private static int  askAQuestion(List<QuizQuest>questForCategory, Set<Integer>questionAskedToPlayer) {
            int questionIndex;
            do {
                questionIndex = RANDOM.nextInt(questForCategory.size());
                System.err.println("#GW DEBUG: Drawn question: " + questionIndex);
            }while (questionAskedToPlayer.contains(questionIndex));
            questionAskedToPlayer.add(questionIndex);

            QuizQuest drawnQuestion = questForCategory.get(questionIndex);
            System.out.println("Quiz question: " + drawnQuestion.question);
            List<String> mixedAnswer = new ArrayList<String>(drawnQuestion.answer);
            Collections.shuffle(mixedAnswer);
            for (int i = 0; i < mixedAnswer.size(); i++) {
                String answer1 = mixedAnswer.get(i);
                System.out.println("  " + (i + 1) + ") " + answer1);
            }
            Scanner in = new Scanner(System.in);
            int numberFromPlayer = in.nextInt() - 1;
            String playerAnswer = mixedAnswer.get(numberFromPlayer);
            System.out.println("You pick: " + playerAnswer);
            String correctAnswer = drawnQuestion.answer.get(0);
            if (playerAnswer.equals(correctAnswer)) {
                System.out.println("Correct answer! ");
                return 1;
            } else {
                System.out.println("Wrong answer, correct answer is " + correctAnswer);
                return 0;
            }
        }

        private static List<QuizQuest> readQuestionFromFile(File file) throws FileNotFoundException {
            Scanner scanner = new Scanner(file);

            List<QuizQuest> questionFromCategory = new ArrayList<QuizQuest>();
            while (scanner.hasNextLine()) {
                String questions = scanner.nextLine();
                String howManyAns = scanner.nextLine();
                int ileOdp = Integer.parseInt(howManyAns);
                ArrayList<String> ansListp = new ArrayList<String>();
                for (int i = 0; i < ileOdp; i++) {
                    String ans = scanner.nextLine();
                    ansListp.add(ans);
                }

                QuizQuest zad = new QuizQuest();
                zad.question = questions;
                zad.answer = ansListp;
                questionFromCategory.add(zad);
            }
            return questionFromCategory;
        }
    }
