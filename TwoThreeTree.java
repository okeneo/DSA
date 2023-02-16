
//---------------------------------------------
// NAME: Oghenetega Okene
// OBJECTIVE: Implement a 2-3 tree of Strings
//---------------------------------------------

public class TwoThreeTree {

  /******************************************************************************
   * Inner class
   *******************************************************************************/

  private class TreeNode {

    private String word;
    private String wordTwo;
    private String wordThree; // used for splitting and pushing up
    private TreeNode left;
    private TreeNode mid;
    private TreeNode midTwo; // used for splitting and pushing up
    private TreeNode right;
    private TreeNode parent;

    // TreeNode constructor
    private TreeNode(String new_word) {
      word = new_word;
      left = mid = right = parent = null;
    }
  }

  /******************************************************************************
   * Attributes
   *******************************************************************************/

  private TreeNode root; //pointer to the root of the tree

  /******************************************************************************
   * Public Methods
   *******************************************************************************/

  //constructor
  public TwoThreeTree() {
    this.root = null;
  }

  /*
   * PURPOSE: Try to find the given word in the tree.
   * PARAMETER: the_word: the given word to look up in the treeionary.
   * (an input parameter)
   * RETURNS: true if the given word is present and false otherwise.
   * 
   * Calls the lSearch method, which
   * returns the result of our search (Lsearch
   * variable). If the_word is equal to the
   * word in the lSearch result node,
   * then the String is in our treeionary
   */
  public boolean lookup(String the_word) {

    // make sure we only work in lower case
    the_word = the_word.toLowerCase();

    boolean result;

    if (root == null) { // if the tree is empty
      result = false;
    } else {
      result = the_word.equals(lSearch(the_word).word);
    }

    return result;
  }

  /******************************************************************************
   * Private Methods
   *******************************************************************************/

  /**
   * method insert
   *
   * PURPOSE: Private 2-3 tree insertion method.
   * Inserts the new word into the 2-3 tree,
   * if it is not already in the tree.
   * PARAMETER: newWord --- the word to insert into the BST.
   * (input parameter)
   */

  public void insert(String the_word) {

    the_word = the_word.toLowerCase(); // Don't assume the given word is already in lowercase

    // search for the item in our treeionary
    TreeNode result = lSearch(the_word);

    if (root == null) { // inserting into an empty tree
      root = new TreeNode(the_word);
      // left,right,mid,parent pointers are Null
    } else { // inserting into a non empty tree

      if (!(result.word).equals(the_word)) { // No duplicates allowed

        if (root.left == null) { // if our tree contains only one item

          if ((root.word).compareTo(the_word) < 0) { // root word is less than the new word

            root.left = new TreeNode(root.word);
            root.right = new TreeNode(the_word);
            root.word = the_word;

          } else { // root word is greater than the new word
            root.left = new TreeNode(the_word);
            root.right = new TreeNode(root.word);

          }

          // set the parents of root's left and right children to the root
          root.left.parent = root;
          root.right.parent = root;

        } else if (result.parent.mid == null) { // Two-child parent
          twoChildrenInsert(result, the_word);
        } else { // Three-child parent
          threeChildrenInsert(result, the_word);
        }
      }

    }

  }

  /*
   * A private search method
   * that searches for a string in
   * our treeionary, returning
   * the TreeNode that stores (Lsearch variable)
   * that value or the leaf where it "should/could" be.
   * Returns null if the tree is empty.
   */
  private TreeNode lSearch(String the_word) {

    TreeNode curr = root;

    if (root != null) { // search if the tree is non empty

      while (curr.left != null) { // search until we hit a leaf node

        if (curr.mid == null) { // TreeNode has 2 children

          if (the_word.compareTo(curr.word) < 0) { // go to the right

            curr = curr.left;
          } else { // go to the right

            curr = curr.right;
          }
        } else { // TreeNode has 3 children

          if (the_word.compareTo(curr.word) < 0) {// go to the left
            curr = curr.left;

          } else if (the_word.compareTo(curr.wordTwo) >= 0) {// go to the right
            curr = curr.right;
          } else { // go to the middle
            curr = curr.mid;

          }

        }

      }

    }

    return curr;
  }

  /*
   * Parameters: Result of our leaf search
   * and the new word (key) to be inserted
   * into our tree.
   * 
   * Handles the insertion of a new child
   * when the parent already has 3 children.
   * We will split and push up as demonstrated
   * in the class slides.
   * 
   */
  private void threeChildrenInsert(TreeNode leaf, String newWord) {

    TreeNode parent = leaf.parent;
    boolean endInsert = false;
    // new nodes with 2 children
    TreeNode newLeft;
    TreeNode newRight;

    // add a temp fourth child and 3rd word to parent
    overFlowParent(leaf, newWord);

    while (!endInsert) {

      // split the parent node with 3 words and 4 children
      // into two new TreeNodes each containing 2 words (children)
      newLeft = splitLeft(parent);
      newRight = splitRight(parent);

      // Push up and set the children and parent
      // pointers of parent and it's leaves

      if (parent == root) { // create a new root

        // Create new root
        TreeNode newRoot = new TreeNode(parent.wordTwo);
        newRoot.left = newLeft;
        newRoot.right = newRight;

        // point leaves to new parent
        newRoot.left.parent = newRoot;
        newRoot.right.parent = newRoot;

        // update root
        root = newRoot;

        // end insert
        parent = null; // garbage collector may or may not handle
        // this but we'll free the memory in case
        endInsert = true;

      } else if (parent.parent.wordTwo == null) { // the grandparent has room for a
        // third child

        parent.parent.wordTwo = parent.wordTwo; // add to the grandparent the mid word of parent
        orderTwoWords(parent.parent); // order parent indexes as required

        if (parent.parent.left == parent) { // if we are on the left side
          // of the parent

          parent.parent.left = newLeft;
          parent.parent.mid = newRight;

        } else {// if we are on the right side of the parent

          parent.parent.mid = newLeft;
          parent.parent.right = newRight;

        }

        // update the parents of the newLeft and newRight
        newLeft.parent = parent.parent;
        newRight.parent = parent.parent;

        // end insert
        parent = null; // garbage collector may or may not handle
        // this but we'll free the memory in case
        endInsert = true;

      } else { // the grandparent does not have room

        addThirdIndex(parent.parent, parent.wordTwo);

        // Make the grandparent point at 4 nodes
        if (parent.parent.right == parent) { // if we are on the right side

          parent.parent.midTwo = newLeft;
          parent.parent.right = newRight;

        } else if (parent.parent.left == parent) { // if we are on the left side

          parent.parent.midTwo = parent.parent.mid;
          parent.parent.mid = newRight;
          parent.parent.left = newLeft;

        } else { // if we are in the middle

          parent.parent.mid = newLeft;
          parent.parent.midTwo = newRight;

        }

        // update the parents of the newLeft and newRight
        newLeft.parent = parent.parent;
        newRight.parent = parent.parent;

        // go to the grandparent
        parent = parent.parent;

      }

    }
  }

  /*
   * Parameters: Result of our leaf search
   * and the new word (key) to be inserted
   * into our tree.
   * 
   * It will insert a new
   * leaf as a third child of leaf's
   * parent and will preserve the order of
   * the 3 siblings.
   */
  private void twoChildrenInsert(TreeNode leaf, String newWord) {

    TreeNode parent = leaf.parent;

    String leafSearch = leaf.word;

    if (newWord.compareTo((parent.right).word) > 0) { // the new word
      // is the greatest word of the (soon to be) 3 children

      parent.mid = new TreeNode(parent.right.word); // set mid to the old right
      parent.right.word = newWord; // update right as the greatest word

    } else if ((newWord.compareTo((parent.left).word) < 0)) { // the new word
      // is the smallest word of the (soon to be) 3 children

      // update the left and mid children
      parent.mid = new TreeNode(parent.left.word);
      parent.left.word = newWord;

    } else { // the new word should be the middle child
      parent.mid = new TreeNode(newWord); // set the new mid
    }

    // update the parent indexes by adding a new index to parent
    // (the new index is the max of lSearch and newWord)
    if (leafSearch.compareTo(newWord) > 0) { // lSearch value is the max
      parent.wordTwo = leafSearch;
    } else { // newWord is the max
      parent.wordTwo = newWord;
    }
    orderTwoWords(parent); // update parent node indexes if necessary

    // set the new mid parent to the same parent as left and right
    parent.mid.parent = parent;

  }

  /*
   * This method will add an additional
   * child to a parent node (giving the
   * node 4 children as opposed to the
   * desired 2 or 3 children).
   * 
   * It will also add a third word to the
   * parent node, giving it 3 words as
   * opposed to the desired 1 or 2 words
   */
  private void overFlowParent(TreeNode leaf, String newWord) {

    String searchWord = leaf.word; // the result word of the lSearch

    TreeNode parent = leaf.parent;


    // Add a temporary fourth child to parent

    if (newWord.compareTo((parent.right).word) > 0) { // newWord is the greatest of the sibling words

      // Add a fourth child - as the rightmost child
      // and create a new middle node
      parent.midTwo = new TreeNode(parent.right.word);
      parent.right.word = newWord;

    } else if (newWord.compareTo((parent.left).word) < 0) { // newWord is the least of the sibling words

      // Add a fourth child - as the leftmost child
      // and create a new middle node
      parent.midTwo = new TreeNode(parent.mid.word);
      parent.mid.word = parent.left.word;
      parent.left.word = newWord;

    } else { // added to the middle (creating 2 middle elements)

      /*
       * Determine if the node where newWord is
       * stored should be in mid or midTwo depending
       * on if it is greater than the current mid word
       */
      if (newWord.compareTo(parent.mid.word) < 0) {
        parent.midTwo = new TreeNode(parent.mid.word);
        parent.mid.word = newWord;
      } else {
        parent.midTwo = new TreeNode(newWord);
      }

    }

    // Add the max word between lSearch and the newWord
    // as a new word of the parent node
    if (searchWord.compareTo(newWord) > 0) {
      addThirdIndex(parent, searchWord);
    } else {
      addThirdIndex(parent, newWord);
    }

    // set the parent pointer of the temporary midTwo child
    parent.midTwo.parent = parent;

  }

  /*
   * split the parent node with 3 words and
   * 4 children. This method splits and
   * returns the left side.
   */
  private TreeNode splitLeft(TreeNode parent) {

    TreeNode newLeft = new TreeNode(parent.word);
    newLeft.left = parent.left;
    newLeft.right = parent.mid;

    // point nodes to new parent
    newLeft.left.parent = newLeft;
    newLeft.right.parent = newLeft;

    return newLeft;
  }

  /*
   * split the parent node with 3 words and
   * 4 children. This method splits and
   * returns the right side.
   */
  private TreeNode splitRight(TreeNode parent) {

    TreeNode newRight = new TreeNode(parent.wordThree);
    newRight.left = parent.midTwo;
    newRight.right = parent.right;

    // point nodes to new parent
    newRight.right.parent = newRight;
    newRight.left.parent = newRight;

    return newRight;
  }

  /*
   * This method adds a third word to
   * a parent node and ensures that
   * the order is maintained
   */
  private void addThirdIndex(TreeNode parent, String str) {

    if (str.compareTo(parent.word) < 0) { // the new word (str) should be the leftmost word ->
      // shift words over

      parent.wordThree = parent.wordTwo;
      parent.wordTwo = parent.word;
      parent.word = str; // set the least word to str

    } else if (str.compareTo(parent.wordTwo) > 0) { // the new word should be the rightmost word

      parent.wordThree = str;

    } else { // the word should be the middle word ->
      // place in the middle

      parent.wordThree = parent.wordTwo;
      parent.wordTwo = str;

    }

  }

  /*
   * This method takes in an index/internal node
   * that has 2 words and ensures that they are
   * in order
   */
  private void orderTwoWords(TreeNode parent) {

    if (parent.word.compareTo(parent.wordTwo) > 0) {
      String temp = parent.word;
      parent.word = parent.wordTwo;
      parent.wordTwo = temp;
    }

  }

  /**
   * method print_table
   *
   * PURPOSE: Test method to see how the tree is working...
   */
  private void printTree() {

    if (root != null) { // print is valid only when
      // the tree is nonempty
      if (root.left == null) {
        System.out.println(root.word);
      } else {
        printTreeRec(root);
      }
    }

  }

  /*
   * PURPOSE: Recursive method for printing
   * the leaves/data of our 2-3 tree
   */
  private void printTreeRec(TreeNode curr) {

    if (curr.left == null) { // the current node is a leaf
      System.out.println(curr.word);
      return;
    } else { // the current node is NOT a leaf

      printTreeRec(curr.left); // perform inorder traversal of the left child

      if (curr.mid != null) { // perform inorder traversal of the mid child (if there is one)
        printTreeRec(curr.mid);
      }

      printTreeRec(curr.right); // perform inorder traversal of the right child

    }
  }

}
