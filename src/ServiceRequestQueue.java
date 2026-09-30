/**
 * ServiceRequestQueue.java
 * -------------------------------------------------
 * Member 2 - Requirement 4:
 * "Use a queue to manage student service requests in order of arrival."
 *
 * A QUEUE is First-In-First-Out (FIFO): the first request that arrives
 * is the first one processed. Think of a real queue/line at a counter.
 *
 * Built from scratch with linked nodes (no java.util.Queue) so the
 * mechanics are visible. We keep two pointers:
 *   - front: the next request to be processed
 *   - rear:  the last request that was added
 */
public class ServiceRequestQueue {

    private class Node {
        String studentId;
        String requestDetails;
        Node next;
        Node(String studentId, String requestDetails) {
            this.studentId = studentId;
            this.requestDetails = requestDetails;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    /** Adds a new request to the back of the queue. */
    public void enqueue(String studentId, String requestDetails) {
        Node newNode = new Node(studentId, requestDetails);
        if (rear == null) {
            front = rear = newNode;         // queue was empty
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    /**
     * Removes and returns a description of the request that has been
     * waiting the longest (the one at the front).
     * Returns null (and prints a message) if the queue is empty.
     */
    public String dequeue() {
        if (isEmpty()) {
            System.out.println("No service requests in queue.");
            return null;
        }
        Node temp = front;
        front = front.next;
        if (front == null) rear = null;     // queue became empty
        size--;
        return "Processed request for Student ID: " + temp.studentId
                + " -> " + temp.requestDetails;
    }

    public boolean isEmpty() {
        return front == null;
    }

    /** Prints every request currently waiting, in arrival order. */
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }
        System.out.println("----- Pending Service Requests (arrival order) -----");
        Node temp = front;
        int position = 1;
        while (temp != null) {
            System.out.println(position + ". Student ID: " + temp.studentId
                    + " | Request: " + temp.requestDetails);
            temp = temp.next;
            position++;
        }
    }

    public int getSize() { return size; }
}
