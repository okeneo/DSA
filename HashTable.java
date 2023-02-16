//---------------------------------------------
// NAME: Oghenetega Okene
// OBJECTIVE: Hash Table ADT
//---------------------------------------------

/*
 * This Hash Table ADT stores
 * a linkedlist of Strings at each
 * key value (the String)
 * using seperate chaining
 */

public class HashTable {

    // Instance constants
    final static int PRIME_CONSTANT = 13;

    // Instance variables
    private LinkedList[] hashArray;
    private int tableSize; // size of the hashArray
    

    // Constructor
    public HashTable(int tableSize) {

        hashArray = new LinkedList[tableSize];

        for (int i = 0; i < tableSize; i++) { // initialize
            hashArray[i] = new LinkedList();
        }

        this.tableSize = tableSize;

    }

    // Instance methods

    /*
     * Hash function for to convert
     * a String to an index in the range
     * of 0-tableSize-1.
     * It uses the polynomial hash algorithm
     */
    public int hash(String key) {

        int currValue = -1;
        int nextValue = -1;

        if (key.length() > 1) {
            
            //polynomial hash algorithm
            currValue = (int) (key.charAt(0)); // first char in the key
            int count = 1; // index of the next char value

            for (int i = 0; i < key.length() - 1; i++) {
                nextValue = (int) (key.charAt(count));
                currValue = ((currValue * PRIME_CONSTANT) + nextValue) % tableSize;
                count++;
            }

        } else if (key.length() == 1) { // only one item -> convert char to int
            currValue = (int) (key.charAt(0)) % tableSize;
        }

        return currValue;
    }


    //The methods below all make
    //use of the hash() function

    /*
     * search the table for
     * a key/word in the Table
     */
    public Node search(String key) {

        int hashCode = hash(key);
        Node output = null;

        if(hashCode!=-1){ //hashCode==-1 if an empty string is entered as key
            output = hashArray[hashCode].search(key);
        }
        
        return output;
    }


    /*
     * Insert a new item into our table
     */
    public void insert(String newItem) {

        int hashCode = hash(newItem);

        if(hashCode!=-1){ //hashCode==-1 if an empty string is entered as newItem
            hashArray[hashCode].insert(newItem);
        }

    }

    /*
     * Delete a key from
     * our table given a key
     */
    public void delete(String key) {

        int hashCode = hash(key);
        Node curr = search(key);

        if (curr != null && hashCode!=-1) { //hashCode==-1 if an empty string is entered as key
            hashArray[hashCode].delete(curr);
        }
    }

    /*
     * Helper method for seeing the
     * state of our table
     */
    private void printTable() {
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i].top != null) {
                System.out.println(hashArray[i]);
            }
        }
    }




    /*
     * Hidden basic linear
     * linked list
     */
    private class LinkedList {

        // Instance Variables
        private Node top;

        // Constructor
        public LinkedList() {
            top = null;
        }

        // Instance methods
        public void insert(String newItem) {

            top = new Node(newItem, top);

        }

        /*
         * search the linked list
         * given an item. Return
         * null if the item cant be found
         * and the Node where the item
         * is contained otherwise
         */
        public Node search(String item) {
            Node curr = top;

            while (curr != null && !(curr.item).equals(item)) {
                curr = curr.next;
            }

            return curr;

        }


        /*
         * Delete a given Node in the list
         */
        public void delete(Node theNode) {
            Node prev = null;
            Node curr = top;

            while (curr != null && curr != theNode) {
                prev = curr;
                curr = curr.next;
            }

            if (curr != null) {
                if (prev != null) {
                    prev.next = curr.next;
                } else {
                    top = curr.next;
                }

            }
        }


        //Helper method

        /*
         * Return a String value
         * consisiting of all
         * the values in the
         * linked list
         */
        public String toString() {
            String output = "";

            Node curr = top;

            while (curr != null) {
                output += curr.item + ",";
                curr = curr.next;
            }
            return output;
        }

    }





    /*
     * Hidden Node class for
     * our LinkedList class
     */
    private class Node {

        // Instance Variables
        private String item;
        private Node next;

        // Constructor
        public Node(String item, Node next) {
            this.item = item;
            this.next = next;
        }

    }


}
