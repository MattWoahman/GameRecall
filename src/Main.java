import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.print("What game do you want the info for? ");

        String gameInput = scanner.nextLine();

        System.out.println("Ben has afk'd in " + gameInput + " for " + (random.nextInt(10000)+1000) + " hours!");
    }
}

