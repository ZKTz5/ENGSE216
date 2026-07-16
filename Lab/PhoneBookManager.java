package Lab;
import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBookManager {
    // เก็บรายการ PhoneBook ทั้งหมด
    private ArrayList<PhoneBook> phoneBooks;
    private Scanner scanner;

    public PhoneBookManager() {
        phoneBooks = new ArrayList<>();
        scanner = new Scanner(System.in); // ใช้ Scanner ตัวเดียวร่วมกันในคลาส
    }

    // ==========================================
    // Core Features (Method หลักของระบบ)
    // ==========================================

    // 1. Add Contact (เพิ่มข้อมูลต่อท้าย)
    public void addContact() {
        System.out.println("\n--- Add New Contact ---");
        String name = inputName();
        String surname = inputSurname();
        String phone = inputPhone(true); // true = ตรวจสอบเบอร์ซ้ำด้วย

        PhoneBook newContact = new PhoneBook(name, surname, phone);
        phoneBooks.add(newContact);
        System.out.println("Contact added successfully!");
    }

    // 2. Delete Contact (ลบข้อมูลตามลำดับ No.)
    public void deleteContact() {
        System.out.println("\n--- Delete Contact ---");
        if (phoneBooks.isEmpty()) {
            System.out.println("No contacts to delete.");
            return;
        }

        showAllContacts();
        System.out.print("Enter No. to delete: ");
        int no = Integer.parseInt(scanner.nextLine());
        int index = no - 1; // แปลงลำดับ No. เป็น Index ของ ArrayList

        if (index >= 0 && index < phoneBooks.size()) {
            System.out.print("Are you sure you want to delete this contact? (Y/N): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("Y")) {
                phoneBooks.remove(index);
                System.out.println("Contact deleted successfully!");
            } else {
                System.out.println("Deletion canceled.");
            }
        } else {
            System.out.println("Invalid No. Try again.");
        }
    }

    // 3. Edit Contact (แก้ไขข้อมูล)
    public void editContact() {
        System.out.println("\n--- Edit Contact ---");
        if (phoneBooks.isEmpty()) {
            System.out.println("No contacts to edit.");
            return;
        }

        showAllContacts();
        System.out.print("Enter No. to edit: ");
        int no = Integer.parseInt(scanner.nextLine());
        int index = no - 1;

        if (index >= 0 && index < phoneBooks.size()) {
            PhoneBook contact = phoneBooks.get(index);
            System.out.println("\n1. Edit Name");
            System.out.println("2. Edit Surname");
            System.out.println("3. Edit Phone");
            System.out.println("4. Edit All");
            System.out.print("Choose choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    contact.setName(inputName());
                    break;
                case 2:
                    contact.setSurname(inputSurname());
                    break;
                case 3:
                    // ส่งค่าเบอร์เดิมไปเช็ค (ถ้ากรอกเบอร์เดิมผ่าน แต่กรอกเบอร์คนอื่นที่ซ้ำไม่ผ่าน)
                    contact.setPhone(inputPhoneForEdit(contact.getPhone()));
                    break;
                case 4:
                    contact.setName(inputName());
                    contact.setSurname(inputSurname());
                    contact.setPhone(inputPhoneForEdit(contact.getPhone()));
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Invalid No. Try again.");
        }
    }

    // 4. Insert Contact (แทรกข้อมูลตามตำแหน่งที่ต้องการ)
    public void insertContact() {
        System.out.println("\n--- Insert Contact ---");
        showAllContacts();
        
        System.out.print("Enter Insert Position (No.): ");
        int no = Integer.parseInt(scanner.nextLine());
        int index = no - 1;

        // ถ้ารายการว่างเปล่า สามารถแทรกที่ตำแหน่ง 1 ได้ (index 0)
        // หรือถ้ามีข้อมูล แทรกได้ตั้งแต่ index 0 จนถึงตำแหน่งท้ายสุดของ list
        if (index >= 0 && index <= phoneBooks.size()) {
            String name = inputName();
            String surname = inputSurname();
            String phone = inputPhone(true);

            PhoneBook newContact = new PhoneBook(name, surname, phone);
            phoneBooks.add(index, newContact);
            System.out.println("Contact inserted successfully at position " + no + "!");
        } else {
            System.out.println("Invalid position. You can insert from 1 to " + (phoneBooks.size() + 1));
        }
    }

    // 5. Search Contact (ค้นหาแบบไม่สนพิมพ์เล็ก-ใหญ่)
    public void searchContact() {
        System.out.println("\n--- Search Contact ---");
        if (phoneBooks.isEmpty()) {
            System.out.println("No contacts to search.");
            return;
        }

        System.out.println("1. Search by Name");
        System.out.println("2. Search by Surname");
        System.out.println("3. Search by Phone");
        System.out.print("Choose search method: ");
        int choice = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        System.out.println("\n=========================================================");
        System.out.printf("%-5s %-15s %-15s %-15s\n", "No", "Name", "Surname", "Phone");
        System.out.println("=========================================================");

        int count = 0;
        for (int i = 0; i < phoneBooks.size(); i++) {
            PhoneBook c = phoneBooks.get(i);
            boolean match = false;

            if (choice == 1 && c.getName().toLowerCase().contains(keyword)) match = true;
            if (choice == 2 && c.getSurname().toLowerCase().contains(keyword)) match = true;
            if (choice == 3 && c.getPhone().contains(keyword)) match = true;

            if (match) {
                count++;
                System.out.printf("%-5d %-15s %-15s %-15s\n", (i + 1), c.getName(), c.getSurname(), c.getPhone());
            }
        }
        System.out.println("=========================================================");
        System.out.println("Found " + count + " contact(s).");
    }

    // 6. Sort Contact (เรียงลำดับแบบ Selection Sort พื้นฐานปี 1)
    public void sortContact() {
        System.out.println("\n--- Sort Contacts by Name (A-Z) ---");
        if (phoneBooks.size() < 2) {
            System.out.println("Not enough contacts to sort.");
            return;
        }

        // ใช้ลูป Selection Sort เปรียบเทียบตัวอักษรเพื่อเรียงลำดับ A-Z
        for (int i = 0; i < phoneBooks.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < phoneBooks.size(); j++) {
                // compareToIgnoreCase คืนค่าค่าเป็นบวกถ้าตัวหน้ามากกว่าตัวหลัง (A-Z)
                if (phoneBooks.get(j).getName().compareToIgnoreCase(phoneBooks.get(minIndex).getName()) < 0) {
                    minIndex = j;
                }
            }
            // สลับข้อมูลใน ArrayList
            PhoneBook temp = phoneBooks.get(i);
            phoneBooks.set(i, phoneBooks.get(minIndex));
            phoneBooks.set(minIndex, temp);
        }
        System.out.println("Contacts sorted successfully!");
        showAllContacts();
    }

    // 7. Show All Contacts (แสดงผลตารางข้อมูล)
    public void showAllContacts() {
        System.out.println("\n=========================================================");
        System.out.printf("%-5s %-15s %-15s %-15s\n", "No", "Name", "Surname", "Phone");
        System.out.println("=========================================================");
        
        if (phoneBooks.isEmpty()) {
            System.out.println("                  No data found.                         ");
        } else {
            for (int i = 0; i < phoneBooks.size(); i++) {
                PhoneBook c = phoneBooks.get(i);
                System.out.printf("%-5d %-15s %-15s %-15s\n", (i + 1), c.getName(), c.getSurname(), c.getPhone());
            }
        }
        System.out.println("=========================================================");
    }

    // ==========================================
    // Validation Methods (ตรวจสอบความถูกต้องของข้อมูล)
    // ==========================================

    // วนลูปรับค่า Name จนกว่าจะถูกต้อง
    public String inputName() {
        while (true) {
            System.out.print("Enter Name (Eng only, 2-30 chars): ");
            String name = scanner.nextLine().trim();
            if (isValidName(name)) {
                return name;
            }
            System.out.println("Invalid name! Try again.");
        }
    }

    // วนลูปรับค่า Surname จนกว่าจะถูกต้อง
    public String inputSurname() {
        while (true) {
            System.out.print("Enter Surname (Eng only, 2-30 chars): ");
            String surname = scanner.nextLine().trim();
            if (isValidName(surname)) { // เงื่อนไขเดียวกับชื่อ
                return surname;
            }
            System.out.println("Invalid surname! Try again.");
        }
    }

    // วนลูปรับค่า Phone จนกว่าจะถูกต้อง (ใช้สำหรับเคส Add/Insert)
    public String inputPhone(boolean checkDuplicate) {
        while (true) {
            System.out.print("Enter Phone number (10 digits, starts with 0): ");
            String phone = scanner.nextLine().trim();
            if (!isValidPhone(phone)) {
                System.out.println("Invalid format! Must be 10 digits and start with 0.");
                continue;
            }
            if (checkDuplicate && isDuplicatePhone(phone)) {
                System.out.println("This phone number already exists!");
                continue;
            }
            return phone;
        }
    }

    // เมธอดพิเศษช่วยเช็คความถูกต้องเวลาแก้ไขเบอร์โทรศัพท์ (เคสเบอร์เดิมของตัวเองถือว่าผ่าน)
    private String inputPhoneForEdit(String currentPhone) {
        while (true) {
            System.out.print("Enter New Phone number (10 digits, starts with 0): ");
            String phone = scanner.nextLine().trim();
            if (!isValidPhone(phone)) {
                System.out.println("Invalid format! Must be 10 digits and start with 0.");
                continue;
            }
            // ถ้ากรอกเบอร์เดิมผ่านเลย แต่ถ้าเปลี่ยนเบอร์ใหม่ ต้องเช็คไม่ให้ซ้ำคนอื่น
            if (!phone.equals(currentPhone) && isDuplicatePhone(phone)) {
                System.out.println("This phone number already exists!");
                continue;
            }
            return phone;
        }
    }

    // ตรวจสอบกฎการตั้งชื่อ/นามสกุล
    public boolean isValidName(String name) {
        if (name.isEmpty() || name.length() < 2 || name.length() > 30) {
            return false;
        }
        // ตรวจสอบว่าเป็นภาษาอังกฤษล้วนและห้ามมีตัวเลข
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))) {
                return false;
            }
        }
        return true;
    }

    // ตรวจสอบกฎของเบอร์โทร
    public boolean isValidPhone(String phone) {
        if (phone.length() != 10 || phone.charAt(0) != '0') {
            return false;
        }
        // ตรวจสอบว่าเป็นตัวเลขทั้งหมด
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // ตรวจสอบเบอร์โทรซ้ำในระบบ
    public boolean isDuplicatePhone(String phone) {
        for (PhoneBook c : phoneBooks) {
            if (c.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }
}