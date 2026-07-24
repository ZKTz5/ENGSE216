package Lab;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PhoneBookManager manager = new PhoneBookManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== PhoneBook Command Line System =====");
        System.out.println("> add [name] [surname] [phone]");
        System.out.println("> delete [No.]");
        System.out.println("> edit [No.] [name] [surname] [phone]");
        System.out.println("> insert [No.] [A/B] [name] [surname] [phone]");
        System.out.println("> sort");
        System.out.println("> show");
        System.out.println("> exit");
        System.out.println("=========================================");

        while (true) {
            try {
                System.out.print("Command> ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) continue;

                String[] parts = input.split(" ");
                String cmd = parts[0].toLowerCase();

                if (cmd.equals("add")) {
                    manager.addContact(parts);
                } else if (cmd.equals("delete")) {
                    manager.deleteContact(parts);
                } else if (cmd.equals("edit")) {
                    manager.editContact(parts);
                } else if (cmd.equals("insert")) {
                    manager.insertContact(parts);
                } else if (cmd.equals("sort")) {
                    manager.sortContact();
                } else if (cmd.equals("show")) {
                    manager.showAllContacts();
                } else if (cmd.equals("exit")) {
                    System.out.println("Exit Program.");
                    break;
                } else {
                    System.out.println("Unknown Command!");
                }
            } catch (Exception e) {
                System.out.println("Error processing command. Try again.");
            }
        }
    }
}