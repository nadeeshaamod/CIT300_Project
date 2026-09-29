/**
 * StudentLinkedList.java
 * -------------------------------------------------
 * Member 1 (You) - Requirement 2 & 12:
 * "Use a linked list to store and manage student records"
 * "Provide add, update, delete, search, and display operations"
 *
 * This is a simple SINGLY LINKED LIST built from scratch (no
 * java.util.LinkedList) so it clearly demonstrates the data structure
 * for the module.
 *
 * How it works, in plain words:
 *  - Each student is wrapped inside a "Node".
 *  - Every Node points to the next Node.
 *  - "head" always points to the first Node in the list.
 *  - To find/add/delete something, we start at head and walk node by
 *    node ("temp = temp.next") until we find what we want or run out
 *    of nodes (temp == null).
 */
public class StudentLinkedList {

    // A private inner class - only this file needs to know Node exists.
    private class Node {
        Student data;
        Node next;
        Node(Student data) { this.data = data; }
    }

    
    private Node head;   // first node of the list
    private int size;    // how many students are currently stored

    /**
     * Adds a new student to the end of the list.
     * Returns false if a student with the same ID already exists.
     */
    public boolean addStudent(Student s) {
        if (searchStudent(s.getStudentId()) != null) {
            return false; // duplicate ID - caller should show an error message
        }
        Node newNode = new Node(s);
        if (head == null) {
            head = newNode;                 // list was empty
        } else {
            Node temp = head;
            while (temp.next != null) {     // walk to the last node
                temp = temp.next;
            }
            temp.next = newNode;            // attach new node at the end
        }
        size++;
        return true;
    }

    /**
     * Walks through the list looking for a matching Student ID.
     * Returns the Student object if found, otherwise null.
     */
    public Student searchStudent(String id) {
        Node temp = head;
        while (temp != null) {
            if (temp.data.getStudentId().equalsIgnoreCase(id)) {
                return temp.data;
            }
            temp = temp.next;
        }
        return null;
    }

    /**
     * Finds the student by ID and overwrites their name/programme/marks.
     * Returns false if no student with that ID exists.
     */
    public boolean updateStudent(String id, String name, String programme, double marks) {
        Student s = searchStudent(id);
        if (s == null) return false;
        s.setName(name);
        s.setProgramme(programme);
        s.setMarks(marks);
        return true;
    }

    /**
     * Removes the node with the matching Student ID.
     * Returns the removed Student (so the caller can log it / push it onto
     * the Undo stack), or null if it wasn't found.
     */
    public Student deleteStudent(String id) {
        Node temp = head;
        Node prev = null;
        while (temp != null) {
            if (temp.data.getStudentId().equalsIgnoreCase(id)) {
                if (prev == null) {
                    head = temp.next;        // deleting the first node
                } else {
                    prev.next = temp.next;   // skip over temp
                }
                size--;
                return temp.data;
            }
            prev = temp;
            temp = temp.next;
        }
        return null;
    }

    /** Prints every student record currently stored, in insertion order. */
    public void displayAll() {
        if (head == null) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("----- All Student Records (Linked List) -----");
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    public int getSize() { return size; }
}
