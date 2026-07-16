package Lab;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // สร้าง Object ของ Manager เพื่อจัดการข้อมูล
        PhoneBookManager manager = new PhoneBookManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n===== PhoneBook Menu =====");
                System.out.println("1. Add Contact");
                System.out.println("2. Delete Contact");
                System.out.println("3. Edit Contact");
                System.out.println("4. Insert Contact");
                System.out.println("5. Search Contact");
                System.out.println("6. Sort Contacts");
                System.out.println("7. Show All Contacts");
                System.out.println("0. Exit");
                System.out.print("Select Menu (0-7): ");
                
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        manager.addContact();
                        break;
                    case 2:
                        manager.deleteContact();
                        break;
                    case 3:
                        manager.editContact();
                        break;
                    case 4:
                        manager.insertContact();
                        break;
                    case 5:
                        manager.searchContact();
                        break;
                    case 6:
                        manager.sortContact();
                        break;
                    case 7:
                        manager.showAllContacts();
                        break;
                    case 0:
                        System.out.println("Exit Program. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Menu Number. Please input 0-7.");
                }
            } catch (NumberFormatException e) {
                // ป้องกันกรณีนักศึกษาทดสอบระบบแล้วกดปุ่ม Enter เปล่าๆ หรือพิมพ์ตัวอักษรใส่หน้าเมนูหลัก
                System.out.println("Error: Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
        
    }
}