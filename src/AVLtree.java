/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: AVLtree.java
 */

/**
 * 600.226, Fall 2015
 * Starter code for AVLtree implementation
 */
import java.util.Collection;
import java.util.LinkedList;

/**
 * AVL Tree - based on Weiss.
 * @param <T> the base type of data in a node
 *
 */
public class AVLtree<T extends Comparable<? super T>> {

    /** If the balance factor is -2 or less, it's not balanced. */
    private static final int BFTOOLOW = -2;

    /**
     * Inner node class.  Do not make this static because you want the T to be
     * the same T as in the BST header.
     */
    public class BNode {

        /** Variable data of type T. */
        protected T data;
        /** Variable left of type BNode. */
        protected BNode left;
        /** Variable right of type BNode. */
        protected BNode right;
        /** Variable height of the node. */
        private int height;

        /**
         * Constructor for BNode.
         * @param val to insert the given node.
         */
        public BNode(T val) {
            this.data = val;
            this.height = 0;
        }

        /**
         * Returns whether node is a leaf or not.
         * @return true is node is leaf, false if not
         */
        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }
    }

    /** The root of the tree. */
    public BNode root;
    /** The size of the tree. */
    private int size;

    /**
     * Constructs a Binary Search Tree.
     */
    public AVLtree() {
        this.root = null;
        this.size = 0;
    }

    /** Constructs a binary search tree (AVL) from a given array.
     *  @param array the given array
     */
    public AVLtree(T[] array) {

        this.root = null;
        this.size = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                this.add(array[i]);
            }
        }
    }
    /**
     * Find out how many elements are in the Tree.
     * @return the number
     */
    public int size() {
        return this.size;
    }

    /**
     * See if the Tree is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Get the value of the root.
     * @return value of the root
     */
    public T root() {
        if (this.root == null) {
            return null;
        }
        return this.root.data;
    }

    /**
     * Search for an item in the tree.
     * @param val the item to search for
     * @return true if found, false otherwise
     */
    public boolean contains(T val) {
        return this.contains(val, this.root) != null;
    }

    /**
     * Checks if a tree contains a certain value.
     * @param val the value you're looking for
     * @param curr the root of the tree you're searching
     * @return the node that contains that value
     */
    public BNode contains(T val, BNode curr) {
        if (curr == null || this.isEmpty()) {
            return null;
        }
        if (val.equals(curr.data)) {
            return curr;
        }
        if (val.compareTo(curr.data) < 0) {
            return this.contains(val, curr.left);
        }
        return this.contains(val, curr.right);
    }

    /**
     * Add an item to the Tree.
     * @param val the item to add
     * @return true if added, false if val is null
     */
    public boolean add(T val) {
        if (val != null) {
            this.root = this.insert(val, this.root);
            this.size++;
            return true;
        }
        return false;
    }

    /**
     * Helper insert method.
     * @param val the value to insert
     * @param curr the root of the tree
     * @return the node that is inserted
     */
    private BNode insert(T val, BNode curr) {
        BNode temp = curr;
        if (temp == null) { // leaf, make new node
            return new BNode(val);
        }
        if (val.compareTo(temp.data) < 0) {
            temp.left = this.insert(val, temp.left);
            temp = this.balance(temp);

        } else {  // val >= temp
            temp.right = this.insert(val, temp.right);
            temp = this.balance(temp);

        }
        return temp;
    }

    /**
     * Remove an item from the Tree.
     * @param val the item to remove
     * @return true if removed, false if not found
     */
    public boolean remove(T val) {
        if (this.contains(val)) {
            this.root = this.delete(this.root, val);
            this.size--;
            //how you can check whether the root was balanced:
            //System.out.println(Math.abs(balanceFactor(this.root)))
            return true;
        }
        return false;
    }


    /**
     * Helper delete method. - This does the real work - IMPLEMENT!
     * @param value the value to delete
     * @param curr the root of the subtree to look in
     * @return the new subtree after rebalancing
     */
    private BNode delete(BNode curr, T value) {
        if (curr == null) {
            return null;
        }
        if (curr.isLeaf()) {
            if (curr.data.compareTo(value) != 0) {
                return null;
            }
        }
        if (value.compareTo(curr.data) < 0) {
            //must shift down one to the left
            if (curr.left.isLeaf()) {
                curr.height--;
            }
            curr.left = this.delete(curr.left, value);
        } else if (value.compareTo(curr.data) > 0) {
            if (curr.right.isLeaf()) {
                curr.height--;
            }
            curr.right = this.delete(curr.right, value);
        } else {
            //delete curr
            curr = this.deleteHelper(curr);  
        }
        if (curr != null) {
            curr = this.balance(curr);
        }
        return curr;
    }
    /** Helper method for delete to reduce Cyclomatic Complexity.
     * @param curr - the node that needs to be deleted
     * @return the new node at that position
     */
    private BNode deleteHelper(BNode curr) {
        if (curr.isLeaf()) {
            curr = null;
        } else if (curr.left != null && curr.right == null) {
            curr = curr.left;
        } else if (curr.right != null && curr.left == null) {
            curr = curr.right;
        } else {
            BNode smallestKey = this.findMin(curr.right);
            curr.data = smallestKey.data;
            curr.right = this.delete(curr.right, smallestKey.data);
            curr = this.balance(curr);
        }
        return curr;
    }

    /**
     * Performs balancing of the nodes if necessary, adjusting heights
     * as necessary.  IMPLEMENT THIS!
     * @param curr the root of the subtree to balance
     * @return the root node of the newly balanced subtree
     */
    private BNode balance(BNode curr) {

        if (curr == null) {
            return null;
        }
        T value = curr.data;
        if (this.balanceFactor(curr) >= 2) {
            // no matter what, curr.left must have children
            if (this.balanceFactor(curr.left) >= 0) {
                curr = this.rotateWithLeftChild(curr);
            } else if (this.balanceFactor(curr.left) < 0) {
                curr = this.doubleWithLeftChild(curr);
            }
        } else if (this.balanceFactor(curr) <= BFTOOLOW) {
            if (this.balanceFactor(curr.right) <= 0) {
                curr = this.rotateWithRightChild(curr);
            } else if (this.balanceFactor(curr.right) > 0) {
                curr = this.doubleWithRightChild(curr);
            }
        }
        curr.height = max(this.height(curr.left), this.height(curr.right)) + 1;
        return curr;
    }

    /**
     * Checks balance of nodes.
     * @param b node to check balance at
     * @return integer that is balance factor
     */
    private int balanceFactor(BNode b) {
        if (b == null) {
            return -1;
        }

        if (b.isLeaf()) {
            return 0;
        }

        return this.height(b.left) - this.height(b.right);
    }

    /**
     * Search from curr (as root of subtree) and find minimum value.
     * @param curr the root of the tree
     * @return the min
     */
    private BNode findMin(BNode curr) {
        BNode temp = curr;
        if (temp == null) {
            return temp;
        }
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    /**
     * Return the height of node t, or -1, if null.
     * @param t the node to find the height of
     * @return int height to be returned
     */
    private int height(BNode t) {
        if (t == null) {
            return -1;
        }
        return t.height;
    }


    /**
     * Return maximum of lhs and rhs.
     * @param lhs height of lhs
     * @param rhs height of rhs
     * @return the int that's larger
     */
    private static int max(int lhs, int rhs) {
        if (lhs > rhs) {
            return lhs;
        }
        return rhs;
    }

    /**
     * Rotate binary tree node with left child.
     * Update heights, then return new root.
     * @param k2 node to rotate
     * @return updated node
     */
    private BNode rotateWithLeftChild(BNode k2) {
        if (k2 == null) {
            return null;
        }
        BNode k1 = k2.left;
        if (k1 != null) {
            k2.left = k1.right;
            k1.right = k2;
            k2.height = this.max(this.height(k2.left), 
                this.height(k2.right)) + 1;
            k1.height = this.max(this.height(k1.left), k2.height) + 1;
        }
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * Update heights, then return new root.
     * @param k1 node to rotate
     * @return updated node
     */
    private BNode rotateWithRightChild(BNode k1) {
        if (k1 == null) {
            return null;
        }
        BNode k2 = k1.right;
        if (k2 != null) {
            k1.right = k2.left;
            k2.left = k1;
            k1.height = max(this.height(k1.left), this.height(k1.right)) + 1;
            k2.height = max(this.height(k2.right), k1.height) + 1;
        }
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * Update heights, then return new root.
     * @param k3 node to rotate
     * @return update node
     */
    private BNode doubleWithLeftChild(BNode k3) {
        if (k3 != null) {
            k3.left = this.rotateWithRightChild(k3.left);
            return this.rotateWithLeftChild(k3);
        }
        return k3;
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * Update heights, then return new root.
     * @param k1 node to rotate
     * @return updated node
     */
    private BNode doubleWithRightChild(BNode k1) {
        if (k1 != null) {
            k1.right = this.rotateWithLeftChild(k1.right);
            return this.rotateWithRightChild(k1);
        }
        return k1;
    }

    /**
     * String representation of the Tree with elements in order.
     * @return a string containing the Tree contents in the format "[1, 5, 6]".
     */
    public String toString() {
        //System.out.println(this.inOrder().toString());
        return this.inOrder().toString();
    }

    /**
     * Inorder traversal.
     * @return a Collection of the Tree elements in order
     */
    public Iterable<T> inOrder() {
        return this.inOrder(this.root);
    }

    /**
     * Preorder traversal.
     * @return a Collection of the Tree elements in preorder
     */
    public Iterable<T> preOrder() {
        return this.preOrder(this.root);
    }

    /**
     * Postorder traversal.
     * @return a Collection of the Tree elements in postorder
     */
    public Iterable<T> postOrder() {
        return this.postOrder(this.root);
    }

    /**
     * Generates an in-order list of items.
     * @param curr the root of the tree
     * @return collection of items in order
     */
    private Collection<T> inOrder(BNode curr) {
        LinkedList<T> iter = new LinkedList<T>();
        if (curr == null) {
            return iter;
        }
        iter.addAll(this.inOrder(curr.left));
        iter.addLast(curr.data);
        iter.addAll(this.inOrder(curr.right));
        return iter;
    }

    /**
     * Generates a pre-order list of items.
     * @param curr the root of the tree
     * @return collection of items in preorder
     */
    private Collection<T> preOrder(BNode curr) {
        LinkedList<T> iter = new LinkedList<T>();
        if (curr == null) {
            return iter;
        }
        iter.addLast(curr.data);
        iter.addAll(this.preOrder(curr.left));
        iter.addAll(this.preOrder(curr.right));
        return iter;
    }

    /**
     * Generates a post-order list of items.
     * @param curr the root of the tree
     * @return collection of items in postorder
     */
    private Collection<T> postOrder(BNode curr) {
        LinkedList<T> iter = new LinkedList<T>();
        if (curr == null) {
            return iter;
        }
        iter.addAll(this.postOrder(curr.left));
        iter.addAll(this.postOrder(curr.right));
        iter.addLast(curr.data);
        return iter;
    }

    /** Finds and deletes the best node.
     *  @param value the value
     *  @return value at the best node
     */
    public T findAndDeleteBest(T value) {
        BNode rtNode = this.contains(value, this.root);
        if (rtNode == null) {
            rtNode = this.findBest(value, this.root);
            if (rtNode == null) {
                return null;
            }
            T temp = rtNode.data;
            this.remove(rtNode.data);
            return temp;
        }
        T temp = rtNode.data;
        this.remove(rtNode.data);

        System.out.println(rtNode.data.toString());
        return temp;
    }

    /** Finds the best node. 
     *  @param value the value
     *  @param curr the current node
     *  @return best node
     */
    public BNode findBest(T value, BNode curr) {
        //if at any point value == curr.value,
        //then  it would have already been returned
        if (curr == null) {
            return null;
        }
        if (value.compareTo(curr.data) == 0) {
            return curr; //this.deleteAndReturn(curr, curr.data);
        }
        if (value.compareTo(curr.data) > 0) {
            if (curr.right == null) {
                return null;
            }
            return this.findBest(value, curr.right);
        }
        //else
        //we're going to the left
        if (curr.left == null) {
            return curr;
        }
        BNode rtNode = this.findBest(value, curr.left);
        if (rtNode != null) {
            return rtNode;
        }
        return curr;  
    }

    /** Used by BestFit, returns an array of the blocks in the tree.
     *  @return the array 
     */
    public Block[] toArray() {
        if (this.size == 0) {
            return null;
        }
        int i = 0;
        Block[] stuff = new Block[this.size];
        for (T thing : this.inOrder()) {
            stuff[i] = (Block) thing;
            i++;
        }
        return stuff;
    }
}
