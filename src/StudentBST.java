/**
 * StudentBST.java
 * -------------------------------------------------
 * Member 3 - Requirement 5:
 * "Use a BST or AVL tree to organize/search student records by
 * Student ID or another suitable key."
 *
 * A BINARY SEARCH TREE (BST) keeps records sorted by key (here, the
 * Student ID string) so that:
 *   - anything "smaller" than a node goes to its LEFT
 *   - anything "bigger" goes to its RIGHT
 * That means printing the tree "in order" (left, node, right) always
 * gives you the records sorted by ID - see displayInOrder().
 *
 * Comparisons use String.compareTo, which compares IDs alphabetically
 * (works fine whether IDs are like "IT21001" or plain numbers-as-text).
 */
public class StudentBST {

    private class Node {
        Student data;
        Node left, right;
        Node(Student data) { this.data = data; }
    }

    private Node root;

    /** Inserts a student into the correct position in the tree. */
    public void insert(Student s) {
        root = insertRec(root, s);
    }

    private Node insertRec(Node node, Student s) {
        if (node == null) {
            return new Node(s);
        }
        int cmp = s.getStudentId().compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) {
            node.left = insertRec(node.left, s);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, s);
        } else {
            node.data = s; // same ID already exists -> just update it
        }
        return node;
    }

    /** Searches for a student by ID. Returns null if not found. */
    public Student search(String id) {
        Node current = root;
        while (current != null) {
            int cmp = id.compareToIgnoreCase(current.data.getStudentId());
            if (cmp == 0) {
                return current.data;
            }
            current = (cmp < 0) ? current.left : current.right;
        }
        return null;
    }

    /** Removes a student from the tree. Returns false if not found. */
    public boolean delete(String id) {
        if (search(id) == null) return false;
        root = deleteRec(root, id);
        return true;
    }

    private Node deleteRec(Node node, String id) {
        if (node == null) return null;
        int cmp = id.compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) {
            node.left = deleteRec(node.left, id);
        } else if (cmp > 0) {
            node.right = deleteRec(node.right, id);
        } else {
            // Found the node to delete
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            // Two children: replace with the smallest node in the right subtree
            Node successor = node.right;
            while (successor.left != null) successor = successor.left;
            node.data = successor.data;
            node.right = deleteRec(node.right, successor.data.getStudentId());
        }
        return node;
    }

    /** Prints all students sorted by Student ID (ascending). */
    public void displayInOrder() {
        if (root == null) {
            System.out.println("No student records in BST.");
            return;
        }
        System.out.println("----- Students Sorted by ID (BST In-Order) -----");
        inOrderRec(root);
    }

    private void inOrderRec(Node node) {
        if (node == null) return;
        inOrderRec(node.left);
        System.out.println(node.data);
        inOrderRec(node.right);
    }
}
