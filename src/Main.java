import java.util.Scanner;

/**
 * Main.java
 * -------------------------------------------------
 * ALL MEMBERS - Integration
 *
 * This is the menu-driven console that ties every component together:
 *   Member 1 -> StudentLinkedList
 *   Member 2 -> ActionStack, ServiceRequestQueue
 *   Member 3 -> StudentBST, StudentHashTable
 *   Member 4 -> CampusGraph
 *
 * Whoever runs the final demo should be able to explain this file,
 * since it shows how every data structure works together:
 *  - The linked list is the "master" record store.
 *  - The BST and hash table are kept in sync with it, so students can
 *    also be searched/sorted by ID quickly.
 *  - Every add/update/delete/queue action gets logged onto the stack
 *    as history.
 */
public class Main {

    // One shared instance of each data structure for the whole app.
    private static StudentLinkedList studentList = new StudentLinkedList();
    private static ActionStack actionStack = new ActionStack();
    private static ServiceRequestQueue requestQueue = new ServiceRequestQueue();
    private static StudentBST studentBST = new StudentBST();
    private static StudentHashTable hashTable = new StudentHashTable();
    private static CampusGraph campusGraph = new CampusGraph();

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:  addStudent(); break;
                case 2:  updateStudent(); break;
                case 3:  deleteStudent(); break;
                case 4:  studentList.displayAll(); break;
                case 5:  addServiceRequest(); break;
                case 6:  processServiceRequest(); break;
                case 7:  actionStack.displayActions(); break;
                case 8:  studentBST.displayInOrder(); break;
                case 9:  searchStudentByHashing(); break;
                case 10: addCampusLocation(); break;
                case 11: removeCampusLocation(); break;
                case 12: addCampusConnection(); break;
                case 13: removeCampusConnection(); break;
                case 14: campusGraph.displayGraph(); break;
                case 15: traverseCampus(); break;
                case 16: System.out.println("Exiting... Goodbye!"); break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 16);
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n===== University Student Record and Campus Route Management System =====");
        System.out.println(" 1. Add Student Record");
        System.out.println(" 2. Update Student Record");
        System.out.println(" 3. Delete Student Record");
        System.out.println(" 4. Display All Records using Linked List");
        System.out.println(" 5. Add Service Request to Queue");
        System.out.println(" 6. Process Next Service Request");
        System.out.println(" 7. Display Recent Actions using Stack");
        System.out.println(" 8. Display Students using BST");
        System.out.println(" 9. Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS or DFS");
        System.out.println("16. Exit");
    }

    // =================== Student Record Operations (Req 1,2,5,6,12,13,14) ===================

    private static void addStudent() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Error: Student ID cannot be empty.");
            return;
        }
        if (studentList.searchStudent(id) != null) {
            System.out.println("Error: A student with this ID already exists.");
            return;
        }
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Programme: ");
        String programme = sc.nextLine().trim();
        double marks = readDouble("Enter Marks (0-100): ");
        if (marks < 0 || marks > 100) {
            System.out.println("Error: Marks must be between 0 and 100.");
            return;
        }

        Student s = new Student(id, name, programme, marks);
        studentList.addStudent(s);   // Member 1's structure = source of truth
        studentBST.insert(s);        // Member 3's structure kept in sync
        hashTable.insert(s);         // Member 3's structure kept in sync
        actionStack.push("ADDED student: " + id + " (" + name + ")");
        System.out.println("Student added successfully.");
    }

    private static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        String id = sc.nextLine().trim();
        Student s = studentList.searchStudent(id);
        if (s == null) {
            System.out.println("Error: Student not found.");
            return;
        }
        System.out.print("Enter new Name (leave blank to keep \"" + s.getName() + "\"): ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) name = s.getName();

        System.out.print("Enter new Programme (leave blank to keep \"" + s.getProgramme() + "\"): ");
        String programme = sc.nextLine().trim();
        if (programme.isEmpty()) programme = s.getProgramme();

        double marks = readDouble("Enter new Marks (current: " + s.getMarks() + "): ");
        if (marks < 0 || marks > 100) {
            System.out.println("Error: Marks must be between 0 and 100.");
            return;
        }

        studentList.updateStudent(id, name, programme, marks);
        // Student is stored by reference in the BST/hash table too, so the
        // update above is already reflected there - no extra step needed.
        actionStack.push("UPDATED student: " + id);
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        String id = sc.nextLine().trim();
        Student removed = studentList.deleteStudent(id);
        if (removed == null) {
            System.out.println("Error: Student not found.");
            return;
        }
        studentBST.delete(id);
        hashTable.remove(id);
        actionStack.push("DELETED student: " + id + " (" + removed.getName() + ")");
        System.out.println("Student deleted successfully. (Recorded in action history)");
    }

    private static void searchStudentByHashing() {
        System.out.print("Enter Student ID to search: ");
        String id = sc.nextLine().trim();
        Student s = hashTable.search(id);
        if (s == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println("Found: " + s);
        }
    }

    // =================== Queue Operations (Req 4) ===================

    private static void addServiceRequest() {
        System.out.print("Enter Student ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Enter Request Details (e.g. \"Transcript request\"): ");
        String details = sc.nextLine().trim();
        requestQueue.enqueue(id, details);
        actionStack.push("QUEUED service request for: " + id);
        System.out.println("Service request added to queue.");
    }

    private static void processServiceRequest() {
        String result = requestQueue.dequeue();
        if (result != null) {
            System.out.println(result);
            actionStack.push(result);
        }
    }

    // =================== Graph Operations (Req 7,8,9,10,11) ===================

    private static void addCampusLocation() {
        System.out.print("Enter new campus location name: ");
        String loc = sc.nextLine().trim();
        if (campusGraph.addLocation(loc)) {
            actionStack.push("ADDED campus location: " + loc);
            System.out.println("Location added.");
        } else {
            System.out.println("Error: Location already exists.");
        }
    }

    private static void removeCampusLocation() {
        System.out.print("Enter campus location to remove: ");
        String loc = sc.nextLine().trim();
        if (campusGraph.removeLocation(loc)) {
            actionStack.push("REMOVED campus location: " + loc);
            System.out.println("Location removed.");
        } else {
            System.out.println("Error: Location not found.");
        }
    }

    private static void addCampusConnection() {
        System.out.print("Enter first location: ");
        String loc1 = sc.nextLine().trim();
        System.out.print("Enter second location: ");
        String loc2 = sc.nextLine().trim();
        if (campusGraph.addConnection(loc1, loc2)) {
            actionStack.push("ADDED connection: " + loc1 + " <-> " + loc2);
            System.out.println("Connection added.");
        } else {
            System.out.println("Error: One or both locations do not exist.");
        }
    }

    private static void removeCampusConnection() {
        System.out.print("Enter first location: ");
        String loc1 = sc.nextLine().trim();
        System.out.print("Enter second location: ");
        String loc2 = sc.nextLine().trim();
        if (campusGraph.removeConnection(loc1, loc2)) {
            actionStack.push("REMOVED connection: " + loc1 + " <-> " + loc2);
            System.out.println("Connection removed.");
        } else {
            System.out.println("Error: One or both locations do not exist.");
        }
    }

    private static void traverseCampus() {
        System.out.print("Enter starting location: ");
        String start = sc.nextLine().trim();
        if (!campusGraph.hasLocation(start)) {
            System.out.println("Error: Location not found.");
            return;
        }
        System.out.print("Choose traversal type (BFS/DFS): ");
        String type = sc.nextLine().trim();
        if (type.equalsIgnoreCase("BFS")) {
            campusGraph.bfsTraversal(start);
        } else if (type.equalsIgnoreCase("DFS")) {
            campusGraph.dfsTraversal(start);
        } else {
            System.out.println("Invalid traversal type. Type BFS or DFS.");
        }
    }

    // =================== Input Validation Helpers (Req 13,14) ===================

    /** Keeps asking until the user types a valid whole number. */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /** Keeps asking until the user types a valid decimal number. */
    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
