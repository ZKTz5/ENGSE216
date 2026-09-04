import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter Infix: ");
        String input = sc.nextLine();

        if (!Validator.isValidInfix(input)) {
            System.out.println("Error: Invalid infix expression");
        } else {
            String postfix = InfixConverter.convertWithTrace(input);
            System.out.println("Output (Postfix): " + postfix);
            System.out.println("----------------------------------------------------------------------\n");
        }

        sc.close();
    }
}