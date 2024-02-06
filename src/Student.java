import java.sql.Date;
import java.sql.Timestamp;

public class Student {
    private int id;
    private String name;
    private Timestamp register_time;
    private String address;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Timestamp getRegister_time() {
        return register_time;
    }
    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
    public Student(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student ID:" + id +
                "\nStudent Name:" + name +
                "\nStudent Register:" + register_time +
                "\nStudent Address:" + address;
    }
}
