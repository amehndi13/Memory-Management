/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: MaxHeap.java
 */

/** Maximum priority queue.
 *  @param <T> the base type of data in a node
 */
public class MaxHeap<T extends Comparable<? super T>> {
    
    /** Starting size of the heap if none is given. */
    public static final int START = 4;

    /** The max heap. */
    private T[] priority;
    /** The number of items in the MaxHeap. */
    private int items;

    /** Default construtor. Sets heap size to fit 3 items. */
    public MaxHeap() {
        this.priority = (T[]) new Comparable[START];
        this.items = 0;
    }

    /** Creates a Heap from a given array of items.
     *  @param array the array of items
     */
    public MaxHeap(T[] array) {
        int count = 0;
        this.items = 0;
        this.priority = (T[]) new Comparable[array.length + 1];
        for (int i = 1; i <= array.length; i++) {
            if (array[i - 1] != null) {
                this.priority[i - count] = array[i - 1];
                this.items++;
            } else {
                count++;
            }
        }

        for (int j = this.items / 2; j >= 1; j--) {
            this.bubbleDown(j);
        }
    }

    /** Removes all of the elements from this priority queue. */
    public void clear() {
        this.priority = (T[]) new Comparable[this.priority.length];
        this.items = 0;
    }

    /** Inserts the specified element into this priority queue. 
     *  @param t the element to be added
     *  @return true if successfully added, false otherwise
     */
    public boolean offer(T t) {
        if (t == null) {
            return false;
        }

        // Double size if needed
        if (this.items + 1 >= this.priority.length) {
            int newSize = this.priority.length * 2;
            T[] newPriority = (T[]) new Comparable[newSize];
            for (int i = 0; i < this.items; i++) {
                newPriority[i + 1] = this.priority[i + 1];
            }
            this.priority = newPriority;
        }

        this.items++;
        this.priority[this.items] = t;
        this.bubbleUp(this.items);
        return true;
    }

    /** Retrieves, but does not remove, the head of this queue.
     *  @return the head of the queue
     */
    public T peek() {
        if (this.items == 0) {
            return null;
        }
        return this.priority[1];
    }

    /** Retrieves and removes the head of this queue.
     *  @return T the head of the queue
     */
    public T poll() {
        if (this.items == 0) {
            return null;
        }
        T head = this.priority[1];
        this.swap(1, this.items);
        this.priority[this.items] = null;
        this.items--;
        this.bubbleDown(1);
        return head;
    }

    /** Returns the number of elements in the priority queue. 
     *  @return the number of items
     */
    public int size() {
        return this.items;
    }

    /** Checks if the MaxHeap is empty.
     *  @return true if empty, false if not
     */
    public boolean isEmpty() {
        return this.items == 0;
    }

    /** Returns a String representation of the heap array. 
     *  @return the string
     */
    public String toString() {
        StringBuilder temp = new StringBuilder();
        temp.append("[");
        for (int i = 1; i <= this.items; i++) {
            temp.append(this.priority[i]);
            temp.append(", ");
        }
        if (this.items != 0) {
            temp.delete(temp.length() - 2, temp.length());
        }
        temp.append("]");
        return temp.toString();
    }

    /** Returns an array of the items in the Heap. 
     *  @return the array
     */
    public T[] toArray() {
        if (this.items == 0) {
            return null;
        }
        T[] stuff = (T[]) new Comparable[this.items];
        for (int i = 0; i < this.items; i++) {
            stuff[i] = (T) this.priority[i + 1];
        }
        return stuff;
    }

    /** Helper method to swap two items. 
     *  @param posA position of one of the items
     *  @param posB position of the other item
     */
    private void swap(int posA, int posB) {
        T temp = this.priority[posA];
        this.priority[posA] = this.priority[posB];
        this.priority[posB] = temp;
    }

    /** Bubbles an item at position d down in the heap. 
     *  @param down the position of the item to bubble down
     */
    private void bubbleDown(int down) {
        int chi = 2 * down;
        while (2 * down <= this.items) {
            if (chi < this.items 
                && this.priority[chi].compareTo(this.priority[chi + 1]) < 0) {
                chi++;
            }
            if (!(this.priority[down].compareTo(this.priority[chi]) < 0)) {
                break;
            }
            this.swap(down, chi);
            down = chi;
            chi *= 2;
        }
    }

    /** Bubbles an item at position u up in the heap. 
     *  @param up the position of the item to bubble up
     */
    private void bubbleUp(int up) {
        int parent = up / 2;
        while (up > 1 
            && this.priority[up].compareTo(this.priority[parent]) > 0) {
            this.swap(up, parent);
            up = parent;
            parent = up / 2;
        }
    }
}