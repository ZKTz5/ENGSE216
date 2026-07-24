package Lab;
import java.util.ArrayList;

public class PhoneBookManager {
    private ArrayList<PhoneBook> phoneBooks;

    public PhoneBookManager() {
        phoneBooks = new ArrayList<>();
    }

    // 1. เพิ่มข้อมูล: add [name] [surname] [phone]
    public void addContact(String[] args) {
        if (args.length < 4) {
            System.out.println("Error: Use 'add [name] [surname] [phone]'\n");
            return;
        }
        String name = args[1];
        String surname = args[2];
        String phone = args[3];

        if (isValidName(name) && isValidName(surname) && isValidPhone(phone) 
            && !isDuplicateSurname(surname) && !isDuplicatePhone(phone)) {
            
            PhoneBook p = new PhoneBook(name, surname, phone);
            phoneBooks.add(p);
            System.out.println("Add success!\n");
        } else {
            System.out.println("Add failed! Check format or duplicate data.\n");
        }
    }

    // 2. ลบข้อมูล: delete [No.]
    public void deleteContact(String[] args) {
        if (phoneBooks.isEmpty()) {
            System.out.println("No data to delete.\n");
            return;
        }
        if (args.length < 2) {
            System.out.println("Error: Use 'delete [No.]'\n");
            return;
        }
        int no = Integer.parseInt(args[1]);
        int index = no - 1;

        if (index >= 0 && index < phoneBooks.size()) {
            phoneBooks.remove(index);
            System.out.println("Delete success!\n");
        } else {
            System.out.println("Invalid No.\n");
        }
    }

    // 3. แก้ไขข้อมูล: edit [No.] [name] [surname] [phone]
    public void editContact(String[] args) {
        if (phoneBooks.isEmpty()) {
            System.out.println("No data to edit.\n");
            return;
        }
        if (args.length < 5) {
            System.out.println("Error: Use 'edit [No.] [name] [surname] [phone]'\n");
            return;
        }
        int no = Integer.parseInt(args[1]);
        int index = no - 1;

        if (index >= 0 && index < phoneBooks.size()) {
            String name = args[2];
            String surname = args[3];
            String phone = args[4];

            if (isValidName(name) && isValidName(surname) && isValidPhone(phone)) {
                PhoneBook p = phoneBooks.get(index);
                p.setName(name);
                p.setSurname(surname);
                p.setPhone(phone);
                System.out.println("Edit success!\n");
            } else {
                System.out.println("Invalid data format.\n");
            }
        } else {
            System.out.println("Invalid No.\n");
        }
    }

    // 4. แทรกข้อมูล: insert [No.] [A/B] [name] [surname] [phone]
    public void insertContact(String[] args) {
        if (phoneBooks.isEmpty()) {
            System.out.println("No data to insert. Please 'add' first.\n");
            return;
        }
        if (args.length < 6) {
            System.out.println("Error: Use 'insert [No.] [A/B] [name] [surname] [phone]'\n");
            return;
        }
        int no = Integer.parseInt(args[1]);
        String position = args[2].toUpperCase();
        String name = args[3];
        String surname = args[4];
        String phone = args[5];

        int targetIndex = no - 1;
        if (targetIndex >= 0 && targetIndex < phoneBooks.size()) {
            int finalIndex = targetIndex;
            if (position.equals("B")) {
                finalIndex = targetIndex + 1;
            } else if (!position.equals("A")) {
                System.out.println("Invalid position option! Use A (Above) or B (Below)\n");
                return;
            }

            if (isValidName(name) && isValidName(surname) && isValidPhone(phone) 
                && !isDuplicateSurname(surname) && !isDuplicatePhone(phone)) {
                
                PhoneBook p = new PhoneBook(name, surname, phone);
                phoneBooks.add(finalIndex, p);
                System.out.println("Insert success!\n");
            } else {
                System.out.println("Insert failed! Data validation error.\n");
            }
        } else {
            System.out.println("Invalid position No.\n");
        }
    }

    // 5. เรียงลำดับ (Selection Sort)
    public void sortContact() {
        for (int i = 0; i < phoneBooks.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < phoneBooks.size(); j++) {
                if (phoneBooks.get(j).getName().compareToIgnoreCase(phoneBooks.get(min).getName()) < 0) {
                    min = j;
                }
            }
            PhoneBook temp = phoneBooks.get(i);
            phoneBooks.set(i, phoneBooks.get(min));
            phoneBooks.set(min, temp);
        }
        System.out.println("Sort success!\n");
        showAllContacts();
    }

    // 6. แสดงผลทั้งหมด
    public void showAllContacts() {
        System.out.println("\n=========================================================");
        System.out.printf("%-5s %-15s %-15s %-15s\n", "No", "Name", "Surname", "Phone");
        System.out.println("=========================================================");
        if (phoneBooks.isEmpty()) {
            System.out.println("                  No data found.                         ");
        } else {
            for (int i = 0; i < phoneBooks.size(); i++) {
                PhoneBook p = phoneBooks.get(i);
                System.out.printf("%-5d %-15s %-15s %-15s\n", (i + 1), p.getName(), p.getSurname(), p.getPhone());
            }
        }
        System.out.println("=========================================================\n");
    }

    // ==========================================
    // Validation
    // ==========================================
    public boolean isValidName(String name) {
        if (name.length() < 2 || name.length() > 30) return false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) return false;
        }
        return true;
    }

    public boolean isValidPhone(String phone) {
        if (phone.length() != 10 || phone.charAt(0) != '0') return false;
        for (int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    public boolean isDuplicatePhone(String phone) {
        for (PhoneBook p : phoneBooks) {
            if (p.getPhone().equals(phone)) return true;
        }
        return false;
    }

    public boolean isDuplicateSurname(String surname) {
        for (PhoneBook p : phoneBooks) {
            if (p.getSurname().equalsIgnoreCase(surname)) return true;
        }
        return false;
    }
}