/**
 * Student.java
 * -------------------------------------------------
 * Member 1 (You) - shared model class
 *
 * This class represents ONE student record.
 * Every other data structure (linked list, stack, BST, hash table)
 * stores or points to objects of this class, so get this file right
 * first - everyone else's code depends on it.
 */
public class Student {

    private String studentId;   // e.g. "IT21001"
    private String name;
    private String programme;
    private double marks;       // 0 - 100

    public Student(String studentId, String name, String programme, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.programme = programme;
        this.marks = marks;
    }
    


    // ---------- Getters ----------
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getProgramme() { return programme; }
    public double getMarks() { return marks; }

    // ---------- Setters ----------
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setName(String name) { this.name = name; }
    public void setProgramme(String programme) { this.programme = programme; }
    public void setMarks(double marks) { this.marks = marks; }

    /**
     * Controls how a Student prints when you do System.out.println(student).
     * Used everywhere we "display" records (linked list, BST, hash table).
     */
    @Override
    public String toString() {
        return String.format("ID: %-10s | Name: %-20s | Programme: %-25s | Marks: %.2f",
                studentId, name, programme, marks);
    }
}
 