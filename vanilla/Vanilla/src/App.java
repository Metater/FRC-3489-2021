import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter something: ");
        String str = scanner.nextLine();
        System.out.println("You entered: " + str);
    }
}
