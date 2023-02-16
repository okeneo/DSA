
//---------------------------------------------
// NAME: Oghenetega Okene
// OBJECTIVE: Implement a set ADT using a 
// circular linked list data structure
//---------------------------------------------


public class Set{

    //Instance constants
    private final int DUMMY_VALUE = Integer.MAX_VALUE;
    private final int MAX_LINE = 10;
    
    //Instance variables
    private Node last;

    //Constructor
    public Set(){
        last = new Node(DUMMY_VALUE, null);
        last.next = last;
    }

    /*
     * Insert an integer if it is
     * not a duplicate, by:
     * -adding to the front
     * -adding anywhere else
     */
    public void insert(int newItem){
        Node curr = last.next;

        //add to front or list is empty
        if(newItem < last.next.item || curr==last){
            last.next = new Node(newItem, last.next);
        }
        else{ //everywhere else
            while(curr.next!=last && curr.next.item<newItem){ //find correct position
                curr = curr.next;
            }
 
            /*
             * Check for duplicate:
             * Avoid searching the entire list by
             * looking at only the current item and
             * the one after it. This is possible since 
             * the list is ordered. We have a special 
             * case if the newItem is equal to the
             * value in the dummy node i.e.
             * we insert Integer.MAX_VALUE
             */
            if(curr.item != newItem && (curr.next.item!=newItem
                                ||curr.next.item==last.item)){
                curr.next = new Node(newItem, curr.next);
            }
        }

    }


    /*
     * Delete an integer from the Set if 
     * it exists. Note: we dont need to
     * traverse the entire list to
     * delete the first node since last
     * is the pointer into the list
     */
    public void delete(int item){

        Node theNode = search(item); //node to be deleted
        boolean deleted = false;

        if(theNode != null){ //test if the node exists
            Node curr = last;

            //use a do while to test first valid node
            do{
                if(curr.next==theNode){
                    curr.next = theNode.next;
                    deleted = true;
                }
                curr = curr.next;
            }
            while(curr!=last || !deleted);
        }
    }


    /*
     * This method searches the
     * set for an int and returns
     * the node if found - returns
     * null otherwise
     */
    private Node search(int key){
        Node curr = last.next;
        Node result = null;

        //we can stop early since the list is ordered
        while(curr.next!=last && curr.item<key){
            curr = curr.next;
        }

        if(curr.item==key){
            result = curr;
        }

        return result;
    }


    /*
     * Union of two sets:
     * uses an algorithm similar
     * to the merge step
     * in a merge sort
     */
    public static Set union(Set setA, Set setB){

        Set setC = new Set();

        Node currA = setA.last.next; //first node in setA
        Node currB = setB.last.next; //first node in setB

        //if either setA and setB are not at the end or empty
        while(currA!=setA.last || currB!=setB.last){

            //still have elements in both sets to test
            if(currA!=setA.last && currB!=setB.last){

                if(currA.item == currB.item){ 
                    setC.insert(currA.item);
                    currA = currA.next;
                    currB = currB.next;
                }
                else{ //both items not equal
                    if(currA.item<currB.item){
                        setC.insert(currA.item);
                        currA = currA.next;
                    }
                    else{
                        setC.insert(currB.item);
                        currB = currB.next;
                    }
                }
            }
            //only elements in setA to test
            else if(currA!=setA.last && currB==setB.last){ 
                setC.insert(currA.item);
                currA = currA.next;
            }
            else{ //only elements in setB to test
                setC.insert(currB.item);
                currB = currB.next;
            }
            
        }

        return setC;
    }

    /*
     * Intersection method of two sets
     * (Starting at the top of both sets):
     * If both pointers are pointing at two
     * equal items, add the item to setC and 
     * move both pointers. If they are not equal,
     * we move to the next node in the set
     * that currently has the smaller number
     */
    public static Set intersection(Set setA, Set setB){

        Set setC = new Set();

        Node currA = setA.last.next; //first node in setA
        Node currB = setB.last.next; //first node in setB

        /*
         * We end if we reach the end of the list or either
         * of the lists are empty. 
         */
        while(currA!=setA.last && currB!=setB.last){
            if(currA.item == currB.item){
                setC.insert(currA.item);
                currA = currA.next;
                currB = currB.next;
            }
            else if(currA.item < currB.item){
                currA = currA.next;
            }
            else{
                currB = currB.next;
            }

        }
        return setC;
    }
    

    /*
     * determine the difference of two sets
     * by traversing both sets (once).
     * If the item in SetA is less than SetB,
     * insert the former to SetC, if they
     * are equal we move forward on both sets.
     * If SetB's item is larger we move to
     * the next value in SetB. At the end we copy
     * the rest of setA into setC, since these
     * items will not be in SetB.
     */
    public static Set difference(Set setA, Set setB){
        Set setC = new Set();

        Node currA = setA.last.next; //first node in setA
        Node currB = setB.last.next; //first node in setB

        while(currA!=setA.last && currB!=setB.last){
            if(currA.item<currB.item){
                setC.insert(currA.item);
                currA = currA.next;
            }
            else if(currA.item==currB.item){ //test next value
                currA = currA.next;
                currB = currB.next;
            }
            else{ //currA.item>currB.item -> test next value
                currB = currB.next;
            }
        }

        //copy rest of setA into setC
        while(currA!=setA.last){
            setC.insert(currA.item);
            currA = currA.next;
        }

        return setC;
    }
    


    /*
     * Print out contents of
     * the Set
     */
    public void print(){

        System.out.print("The Printed Set is:\n{");
        Node curr = last.next;
        int count = 0; //number of items printed so far

        while(curr!=last){
            count++;
            if(curr.next!=last){
                System.out.print(curr.item + ",");
                if(count==MAX_LINE){ //go to new line
                    System.out.println();
                    count = 0;
                }
            }
            else{ //last item has no comma
                System.out.print(curr.item);
            }
            curr = curr.next;
        }
        System.out.print("}\n\n");
    }



    /*
     * Set toString method
     */
    public String toString(){
        String output= "The Printed Set is:\n{";
        Node curr = last.next;
        int count = 0; //number of items printed so far

        while(curr!=last){
            count++;
            if(curr.next!=last){
                output+= curr.item + ",";
                if(count==MAX_LINE){ //go to new line
                    output+= "\n";
                    count = 0;
                }
            }
            else{ //last item has no comma
                output+= curr.item;
            }
            curr = curr.next;
        }

        output += "}\n";
        return output;
    }


    /*
     * Node class
     */
    private class Node{
        //Instance variables
        public int item;
        public Node next;
    
        //Constructors
        public Node(int item, Node next){
            this.item = item;
            this.next = next;
        }
        
    }

}

