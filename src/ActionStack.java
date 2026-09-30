/**
 * ActionStack.java
 * -------------------------------------------------
 * Member 2 - Requirement 3:
 * "Use a stack to maintain recent actions, deleted records, or an
 * undo/history feature."
 *
 * A STACK is Last-In-First-Out (LIFO): the most recent thing you added
 * is the first thing you see/remove. Think of a stack of plates - you
 * always take the top plate off first.
 *
 * This is built from scratch using linked nodes (no java.util.Stack)
 * so the mechanics are visible for the demo/viva.
 *
 * We store simple text descriptions of actions, e.g.
 *   "ADDED student: IT21001 (Nadeesha)"
 *   "DELETED student: IT21002 (Kasun)"
 * Main.java is responsible for calling push(...) every time something
 * important happens elsewhere in the program.
 */
public class ActionStack {

    private class Node {
        String action;
        Node next;
        Node(String action) { this.action = action; }
    }

    private Node top;   // top of the stack = most recent action
    private int size;

    /** Adds a new action on top of the stack. */
    public void push(String action) {
        Node newNode = new Node(action);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Removes and returns the most recent action.
     * Returns null (and prints a message) if the stack is empty.
     */
    public String pop() {
        if (isEmpty()) {
            System.out.println("No actions to undo.");
            return null;
        }
        String action = top.action;
        top = top.next;
        size--;
        return action;
    }

    /** Looks at the most recent action without removing it. */
    public String peek() {
        return isEmpty() ? null : top.action;
    }

    public boolean isEmpty() {
        return top == null;
    }

    /** Prints every recorded action, most recent first (top to bottom). */
    public void displayActions() {
        if (isEmpty()) {
            System.out.println("No recent actions recorded.");
            return;
        }
        System.out.println("----- Recent Actions (most recent first) -----");
        Node temp = top;
        int count = 1;
        while (temp != null) {
            System.out.println(count + ". " + temp.action);
            temp = temp.next;
            count++;
        }
    }

    public int getSize() { return size; }
}
