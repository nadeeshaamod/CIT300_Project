import java.util.LinkedList;

/**
 * StudentHashTable.java
 * -------------------------------------------------
 * Member 3 - Requirement 6:
 * "Use hashing to support efficient student ID searching."
 *
 * A HASH TABLE stores data in "buckets" (an array). A hash function
 * turns the Student ID into a bucket index, so searching is (on
 * average) instant - no walking through every record like the linked
 * list has to.
 *
 * COLLISION HANDLING: two different IDs can hash to the same bucket.
 * We handle this with "chaining" - each bucket is itself a small list
 * of students, so multiple students can share a bucket safely.
 *
 * We use java.util.LinkedList only as the small "chain" inside each
 * bucket - the hashing logic itself (the important part for this
 * requirement) is written by hand in the hash() method below.
 */
public class StudentHashTable {

    private static final int TABLE_SIZE = 31; // a prime number reduces collisions

    private LinkedList<Student>[] table;

    @SuppressWarnings("unchecked")
    public StudentHashTable() {
        table = new LinkedList[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++) {
            table[i] = new LinkedList<>();
        }
    }

    /**
     * Turns a Student ID string into a bucket index between
     * 0 and TABLE_SIZE - 1.
     */
    private int hash(String id) {
        int hash = 0;
        for (char c : id.toCharArray()) {
            hash = (hash * 31 + c) % TABLE_SIZE;
        }
        return Math.abs(hash);
    }

    /** Inserts a student. Does nothing if the ID already exists. */
    public void insert(Student s) {
        int index = hash(s.getStudentId());
        for (Student existing : table[index]) {
            if (existing.getStudentId().equalsIgnoreCase(s.getStudentId())) {
                return; // already present, avoid duplicates
            }
        }
        table[index].add(s);
    }

    /** Looks up a student by ID. This is the "efficient searching" part. */
    public Student search(String id) {
        int index = hash(id);
        for (Student s : table[index]) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    /** Removes a student by ID. Returns true if something was removed. */
    public boolean remove(String id) {
        int index = hash(id);
        return table[index].removeIf(s -> s.getStudentId().equalsIgnoreCase(id));
    }

    /** Prints the contents of every non-empty bucket (for demo purposes). */
    public void displayTable() {
        System.out.println("----- Hash Table Contents (by bucket) -----");
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (!table[i].isEmpty()) {
                System.out.print("Bucket " + i + ": ");
                for (Student s : table[i]) {
                    System.out.print("[" + s.getStudentId() + "] ");
                }
                System.out.println();
            }
        }
    }
}
