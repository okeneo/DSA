//---------------------------------------------
// NAME: Oghenetega Okene
// OBJECTIVE: Implement a Queue ADT
//---------------------------------------------


/*
 * This Queue ADT stores
 * a String along with a Queue of ints
 * in each element of the Queue.
 * This Queue class contains a Node class that
 * stores the Queue of ints, and the 
 * Queue of ints class uses another
 * Node class that stores it's int values
 */

public class Queue {

    // Instance variables
    private Node front; // top of queue
    private Node end; // end of queue
    private int distinctWords; // number of distint words in our Queue

    // Constructor
    public Queue() {
        front = end = null;
    }

    // Instance methods

    /*
     * Add a numnber to the end of the queue
     */
    public void enqueue(String word, int lineNum) {

        if (front == null) { // if the queue is empty
            front = end = new Node(word);
            (front.queue).enqueue(lineNum); // add to the int queue
            distinctWords++; // the word is not in the Queue

        } else if (front.next == null) { // only 1 item in the list

            Node curr = search(word);

            if (curr == null) { // the word is not on the queue -> make a newNode
                Node newNode = new Node(word);
                end = newNode;
                front.next = end;
                (end.queue).enqueue(lineNum);
                distinctWords++; // the word is not in the Queue

            } else { // add to the list of integer queues in the Node
                (curr.queue).enqueue(lineNum); // add to the int queue
            }

        } else { // more than one item in the list
            Node curr = search(word);

            if (curr == null) { // the word is not on the queue
                Node newNode = new Node(word);
                end.next = newNode;
                end = newNode;
                (end.queue).enqueue(lineNum); // add to the int queue
                distinctWords++; // the word is not in the Queue

            } else { // the word is in the queue
                (curr.queue).enqueue(lineNum); // add to the int queue
            }
        }

    }

    /*
     * Delete and return the Node
     * at the front of the queue
     */
    public Node dequeue() {
        Node curr = front;

        if (curr != null) { // at least 1 item in the list
            if (curr.next == null) { // only one item left
                emptyQueue();
            } else {
                front = front.next;
            }
        }

        return curr;

    }

    /*
     * Search the Queue for a String value (word)
     * returning the Node where "word" is found
     * and null otherwise
     */
    public Node search(String word) {

        Node curr = front;

        while (curr != null && !(curr.word).equals(word)) {
            curr = curr.next;
        }

        return curr;
    }

    /*
     * Helper toString method
     * to see program behaviour
     */
    public String toString() {
        String output = "<";
        Node curr = front;

        while (curr != null) {
            output += curr.word + ",";
            curr = curr.next;
        }

        if (output.length() > 2) {
            output = output.substring(0, output.length() - 1); // remove extra comma
        }

        output += "<";
        return output;
    }

    /*
     * Prints out the contents of the main Queue
     * and the Queue containing ints that
     * accompanies the main Queue
     */
    private void printResults() {

        Node curr = front;

        System.out.println("\nThere are a total of " + distinctWords + " invalid words:\n");

        while (curr != null) {

            System.out.print("Invalid word " + "\"" + (dequeue().word) + "\"");
            System.out.print(" found on lines ");

            IntQueue currQueue = curr.queue;
            IntNode currNode = currQueue.front;

            while (currNode != null) {
                System.out.print((currQueue.deqeue()).number + " ");
                currNode = currNode.next;
            }

            curr = curr.next;
            System.out.println();
        }
        System.out.println();

    }

    /*
     * empty the contents of the queue
     */
    public void emptyQueue() {
        front = end = null;
    }

    /*
     * private Node class
     * used by the main Queue
     * class to store a word and
     * an Queue of ints
     */
    private class Node {

        // Instance Variables
        private String word;
        private Node next;
        private IntQueue queue;

        // Constructor
        public Node(String word) {
            this.word = word;
            this.queue = new IntQueue();
        }

    }

    /*
     * This class is used to store the
     * queue of ints in our Queue class
     */
    private class IntQueue {

        // Instance variables
        private IntNode front; // top of queue
        private IntNode end; // end of queue

        // Constructor
        public IntQueue() {
            front = end = null;
        }

        // Instance methods

        /*
         * Add a numnber to the end of the queue
         */
        public void enqueue(int number) {

            if (front == null && end == null) { // empty list
                front = end = new IntNode(number, front);
            } else {
                IntNode newNode = new IntNode(number, null);
                end.next = newNode;
                end = newNode;
                if (front.next == null) {// only one item in the list
                    front.next = end;
                }
            }

        }

        /*
         * Delete and return the int
         * at the front of the queue
         */
        public IntNode deqeue() {

            IntNode deletednode = null;
            if (!isEmpty()) { // queue is not empty
                deletednode = front; // we will always delete from the front
                if (front.next == null) { // only one item left
                    emptyQueue();
                } else {
                    front = front.next;
                }

            }
            return deletednode;
        }

        // self explanatory
        public boolean isEmpty() {
            return front == null;
        }

        /*
         * empty the contents of the queue
         */
        public void emptyQueue() {
            front = end = null;
        }

        /*
         * Helper toString method
         * to see program behaviour
         */
        public String toString() {
            String output = "<";
            IntNode curr = front;

            while (curr != null) {

                output += curr.number + ",";
                curr = curr.next;
            }

            output = output.substring(0, output.length() - 1); // remove extra comma
            output += "<";
            return output;
        }

    }

    /*
     * Node containing an int value
     * and next IntNode. This class is
     * used by IntQueue
     */
    private class IntNode {

        // Instance Variables
        private int number;
        private IntNode next;

        // Constructor
        public IntNode(int number, IntNode next) {
            this.number = number;
            this.next = next;
        }

    }

}
