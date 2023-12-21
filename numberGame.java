import java.util.Scanner;
import java.util.Random;

public class numberGame {

    static void game(int x, int score) {
        System.out.println(x);
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n > x) {
            score = score - 2;
            if (score == 0) {
                System.out.println("All attempts finished");
                System.out.println("Number was " + x + "\nTry next time..");
                System.exit(0);
            }
            System.out.println("NOT CORRECT!\nHint:try a smaller number");
            game(x, score);
        } else if (n < x) {
            score = score - 2;
            if (score == 0) {
                System.out.println("All attempts finishid");
                System.out.println("Number was " + x + "\nTry next time..");
                System.exit(0);
            }
            System.out.println("NOT CORRECT!\nHint:try a greater number");
            game(x, score);
        } else {
            System.out.println("CONGRATULATIONS! You guessed it right.\nGreat game");
            System.out.println("Your score is " + score);
        }
        sc.close();
    }

    public static void main(String[] args) {

        Random r = new Random();
        int x = r.nextInt(100);
        System.out.println("You have five attempts to guess the number");
        System.out.println("Guess the integer number\nHint:It is between 0 to 100");
        game(x, 10);

    }
}