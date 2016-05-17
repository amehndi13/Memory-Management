/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: MemoryManager.java
 */

/** Interface implemented by BaseManager. */
public interface MemoryManager {

    /** Allocates a block of size size.
     *  @param size the size of the block to allocate
     *  @param frag true if defragment() has been called this alloc
     *  @return true if successful, false if not
     */
    Block allocate(int size, boolean frag);

    /** Deallocates a memory block.
     *  @param call the allocation call to deallocate
     *  @return the block removed, or null if invalid
     */
    Block deallocate(int call);

    /** Defragments memory. 
     *  @param free an array of the free memory
     *  @param your true if from 'YourFit', false otherwise
     *  @return an array of free memory sorted by address
     */
    Block[] defragment(Block[] free, boolean your);

    /** The amount defrags that have occured.
     * @return the amount of total defrags
     */
    int defragCount();

    /** The amount allocation that have occured.
     * @return the amount of total allocations
     */
    int getAllocs();

    /** Returns the average time of allocation.
     *  @return the average time
     */
    float avgTime();

    /** Returns the average time/size of bucketSort.
     *  @return the average time/size of bucketSort
     */
    float avgBucketSortRatio();

    /** Returns the average time/size of quickSort.
     *  @return the average time/size of quickSort
     */
    float avgQuickSortRatio();

    /** Returns the number of fails.
     *  @return the number of fails
     */
    int totalFails();

    /** Returns the average size of fails.
     *  @return the average size of fails
     */
    int avgFailSize();
}