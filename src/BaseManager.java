/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: BaseManager.java
 */

import java.util.HashMap;

/** Base Class for BestFit, WorstFit, and YourFit. */
public class BaseManager implements MemoryManager {
    
    /** For conversion from nano seconds to micro seconds. */
    private static final int MICRO = 1000;

    /** The allocated or once-allocated memory blocks. */
    protected HashMap<Integer, Block> allocatedMem; 
    /** The number of defragmentation attempts. */
    protected int defrags;
    /** The amount of failed Allocations.*/
    protected int fails;
    /** The amount of free space left. */
    protected int freeSpace;
    /** The next allocation ID. */
    protected int allocID;
    /** The total size of failed allocates. */
    protected int totalFailSize;
    /** Sum of the allocate times. */
    protected float sumAllocTime;
    /** The sum of the time/sort's for Bucket Sort. */
    private float sumBucketSize;
    /** The sum of the time/sort's for Quick Sort. */
    private float sumQuickSize;
    /** Comparator for blocks. */
    private AddressComparator comp;

    /** Creates a new Base Manager.
     *  @param size the size of memory
     */
    public BaseManager(int size) {
        this.allocatedMem = new HashMap<Integer, Block>();
        this.freeSpace = size;
        this.allocID = 1;
        this.comp = new AddressComparator();
    }

    /** Allocates a block of size size.
     *  @param size the size of the block to allocate
     *  @param frag true if defragment() has been called this alloc
     *  @return true if successful, false if not
     */
    public Block allocate(int size, boolean frag) {
        if (this.freeSpace < size) {
            this.allocID++;
            this.fails++;
            this.totalFailSize += size;
            return null;
        }
        return new Block();
    }

    /** Deallocates a memory block.
     *  @param call the allocation call to deallocate
     *  @return the block removed, or null if invalid
     */
    public Block deallocate(int call) {
        if (this.allocID <= call) {
            return null;
        }
        if (this.allocatedMem.get(call) == null) {
            return null;
        }
        Block temp = this.allocatedMem.remove(call);
        temp.setID(-1);
        this.freeSpace += temp.getSize();
        return temp;
    }

    /** Defragments memory. 
     *  @param free an array of the free memory
     *  @param your true if from 'YourFit', false otherwise
     *  @return an array of free memory sorted by address
     */
    public Block[] defragment(Block[] free, boolean your) {
        this.defrags++;
        Block[] free2 = free;
        
        if (!your) {
            //sort the array
            Sorter sorterQ = new Sorter(free, this.comp);
            long firstTime = System.nanoTime();
            free = sorterQ.quickSortedArray();
            long secondTime = System.nanoTime();

            Sorter sorterB = new Sorter(free2, this.comp);
            long thirdTime = System.nanoTime();
            free2 = sorterB.bucketSort(free2.length);
            long fourthTime = System.nanoTime();

            this.sumQuickSize += (secondTime - firstTime) / free.length;
            this.sumBucketSize += (fourthTime - thirdTime) / free2.length;

        }

        for (int i = 0; i < free.length - 1; i++) {
            if (free[i].getAddress() + free[i].getSize() 
                == free[i + 1].getAddress()) {
                free[i + 1].setSize(free[i].getSize() + free[i + 1].getSize());
                free[i + 1].setAddress(free[i].getAddress());
                free[i] = null;
            }

        }
        
        return free;
        
    }

    /** Returns defrag count.
     *  @return the defrag count
     */
    public int defragCount() {
        return this.defrags;
    }

    /** Returns the number of allocations.
     *  @return the number of allocations
     */
    public int getAllocs() {
        return this.allocID - 1;
    }

    /** Returns the average time of allocation.
     *  @return the average time
     */
    public float avgTime() {
        return (this.sumAllocTime / (this.allocID - 1 + this.defrags)) / MICRO;
    }

    /** Returns the average time of quickSort.
     *  @return the average time of quickSort
     */
    public float avgQuickSortRatio() {
        return (this.sumQuickSize / this.defrags) / MICRO;
    }

    /** Returns the average time of bucketSort.
     *  @return the average time of bucketSort
     */
    public float avgBucketSortRatio() {
        return (this.sumBucketSize / this.defrags) / MICRO;
    }

    /** Returns the number of fails.
     *  @return the number of fails
     */
    public int totalFails() {
        return this.fails;
    }

    /** Returns the average size of fails.
     *  @return the average size of fails
     */
    public int avgFailSize() {
        return this.totalFailSize / this.fails;
    }
}