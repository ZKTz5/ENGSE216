package Lab;
public class PhoneBook {
    // Attributes แบบ private ตามหลัก Encapsulation
    private String name;
    private String surname;
    private String phone;

    // Constructor สำหรับสร้าง Object
    public PhoneBook(String name, String surname, String phone) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    // Getter และ Setter สำหรับเข้าถึงข้อมูล
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // แปลงข้อมูล Object เป็น String สำหรับการพิมพ์สอบเช็คพื้นฐาน
    @Override
    public String toString() {
        return "PhoneBook{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}