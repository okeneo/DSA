//---------------------------------------------
// NAME: Oghenetega Okene
// OBJECTIVE: Implement a Binary Search Tree
// storing Strings
//---------------------------------------------

public class BST {

    /******************************************************************************
     * Inner class
     *******************************************************************************/

    private class Node {
        public String word;
        public Node left;
        public Node right;

        // standard linked list constructor
        private Node(String new_word) {
            word = new_word;
            left = right = null;
        }
    }

    /******************************************************************************
     * Attributes
     *******************************************************************************/

    private Node root; //root of the tree

    /******************************************************************************
     * Public Methods
     *******************************************************************************/

    // Constructor
    public BST() {
        this.root = null;
    }

    /*
     * Insertion method:
     * Insert a String in our tree by finding
     * it's correct position using two
     * Node pointers (curr & prev)
     * Once we've found the correct spot, 
     * link the new node in as the 
     * appropriate child of the node at which prev points.
     * 
     * Note: If the word is already in the tree,
     * we will not insert it
     */
    public void insert(String newWord) {

        newWord = newWord.toLowerCase(); //Don't assume the given word is already in lowercase

        if (root != null) { // Case 1: The tree is not empty

            // Nodes for the tree traversal
            Node prev = root;
            Node curr = root;

            while (curr != null) { //while we have not fallen off the tree
                //or the word is not a duplicate

                if (newWord.compareTo(curr.word) < 0) {
                    //move to the left
                    prev = curr;
                    curr = curr.left;

                    if (curr == null) { //i.e. we have found the right spot
                        prev.left = new Node(newWord);
                    }

                } else if (newWord.compareTo(curr.word) > 0) {
                    //move to the right
                    prev = curr;
                    curr = curr.right;

                    if (curr == null) { //i.e. we have found the right spot
                        prev.right = new Node(newWord);
                    }

                } else { // if we find a duplicate word - we do not add the word to the
                    // tree
                    curr = null;
                }

            }

        } else { // Case 2: The tree is empty
            root = new Node(newWord);
        }
    }


    // try to find the given word in the tree, returns true if present
    public boolean lookup(String the_word) {
        boolean found = false;
        // make sure we only work in lower case
        the_word = the_word.toLowerCase();

        // search for the word in the tree
        Node curr = root; // starting at the root

        while (curr != null && !found) { //while we have not fallen of the tree
            //and the item has not been found

            if (the_word.compareTo(curr.word) == 0) { //we found the word in the tree
                found = true;

            } else {

                if (the_word.compareTo(curr.word) < 0) {
                    curr = curr.left; //move to the left
                } else {
                    curr = curr.right; //move to the right
                }
            }
        }
        return found;
    }

    /******************************************************************************
     * Private Methods
     *******************************************************************************/
    // test method to see how the Tree is working...
    private void print_tree(Node curr) {
        // PUT CODE HERE

        if (curr.left != null) { // perform inorder traversal of the left child (if there is one)
            print_tree(curr.left);
        }

        System.out.println(curr.word);

        if (curr.right != null) { // perform inorder traversal of the right child (if there is one)
            print_tree(curr.right);
        }
    }

}