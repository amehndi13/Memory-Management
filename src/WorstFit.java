/**
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: WorstFit.java
 */

import java.util.Arrays;

/** WorstFit memory allocation. */
public class WorstFit extends BaseManager {

    /** Contains the available memory. */
    protected MaxHeap freeMem;

    /** Default constructor for WorstFit. 
     *  @param size the total size of memory
     */
    public WorstFit(int size) {
        super(size);
        this.freeMem = new MaxHeap();
        this.freeMem.offer(new Block(size, 0));
    }

    /** Allocates a block of size size.
     *  @param size the size of the block to allocate
     *  @param frag true if defrag() has been called this alloc
     *  @return true if successful, false if not
     */
    @Override
    public Block allocate(int size, boolean frag) {
        long firstTime = System.nanoTime();
        if (super.allocate(size, frag) == null) {
            return null;
        }

        Block temp = (Block) this.freeMem.peek();
        if (temp.getSize() < size) {
            if (!frag) {
                Comparable[] tempb = this.freeMem.toArray();
                Block[] blockArray = Arrays.copyOf(
                    tempb, tempb.length, Block[].class);
                long secondTime = System.nanoTime();
                this.defragment(blockArray, false);
                this.sumAllocTime += (secondTime - firstTime);
                return this.allocate(size, true);
            } else {
                this.fails++;
                this.totalFailSize += size;
                this.allocID++;
                return null;
            }
        }

        temp = (Block) this.freeMem.poll();
        Block newBlock = new Block(size, temp.getAddress(), this.allocID);
        this.allocatedMem.put(this.allocID, newBlock);
        temp.setSize(temp.getSize() - size);
        temp.setAddress(temp.getAddress() + size);

        if (temp.getSize() > 0) {
            this.freeMem.offer(temp);
        }

        this.freeSpace -= size;
        this.allocID++;
        long lastTime = System.nanoTime();
        this.sumAllocTime += (lastTime - firstTime);
        return newBlock;
    }

    /** Deallocates a memory block.
     *  @param call the allocation call to deallocate
     *  @return the block removed, or null if invalid
     */
    @Override
    public Block deallocate(int call) {
        Block dealloc = super.deallocate(call);
        if (dealloc == null) {
            return null;
        }
        this.freeMem.offer(dealloc);
        return dealloc;
    }

    /** Defragments memory. 
     *  @param free an array of the free memory
     *  @param your true if from 'YourFit', false otherwise
     *  @return an array of free memory sorted by address
     */
    @Override
    public Block[] defragment(Block[] free, boolean your) {
        free = super.defragment(free, false);
        this.freeMem = new MaxHeap(free);
        return free;
    }
}