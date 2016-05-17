/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: YourFit.java
 */

import java.util.ArrayList;
import java.util.Arrays;

/** YourFit memory allocation. */
public class YourFit extends BaseManager {
    /** Contains the available memory. */
    protected ArrayList<Block> freeMem;

    /** Default Constructor for BestFit.
     @  @param size the size of the memory
     */
    public YourFit(int size) {
        super(size);
        this.freeMem = new ArrayList<Block>(); // might be new ArrayList();
        this.freeMem.add(new Block(size, 0));
    }

    /** Allocates a block of size size.
     *  @param size the size of the block to allocate
     *  @return true if successful, false if not
     */
    @Override
    public Block allocate(int size, boolean frag) {
        long firstTime = System.nanoTime();
        if (super.allocate(size, frag) == null) {
            return null;
        }

        for (int i = 0; i < this.freeMem.size(); i++) {
            if (this.freeMem.get(i).getSize() >= size) {
                Block temp = this.freeMem.get(i);
                Block newBlock 
                    = new Block(size, temp.getAddress(), this.allocID);
                this.allocatedMem.put(this.allocID, newBlock);
                temp.setSize(temp.getSize() - size);
                temp.setAddress(temp.getAddress() + size);

                if (temp.getSize() > 0) {
                    this.freeMem.set(i, temp);
                } else {
                    this.freeMem.remove(i);
                }

                this.freeSpace -= size;
                this.allocID++;
                return newBlock;
            }
        }

        if (!frag) {
            Object[] tempb = this.freeMem.toArray();
            Block[] blockArray = Arrays.copyOf(
                tempb, tempb.length, Block[].class);
            long secondTime = System.nanoTime();
            this.defragment(blockArray, true);
            this.sumAllocTime += (secondTime - firstTime);
            return this.allocate(size, true);
        } 

        this.fails++;
        this.totalFailSize += size;
        this.allocID++;
        long secondTime = System.nanoTime();
        this.sumAllocTime += (secondTime - firstTime);
        return null;
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
        for (int i = 0; i < this.freeMem.size(); i++) {
            if (dealloc.getAddress() < this.freeMem.get(i).getAddress()) {
                this.freeMem.add(i, dealloc);
                return dealloc;
            }
        }
        this.freeMem.add(dealloc);
        return dealloc;
    }

    /** Defragments memory. 
     *  @param free an array of the free memory
     *  @param your true if from 'YourFit', false otherwise
     *  @return an array of free memory sorted by address
     */
    @Override
    public Block[] defragment(Block[] free, boolean your) {
        free = super.defragment(free, true);
        ArrayList<Block> temp = new ArrayList<Block>();
        for (int i = 0; i < free.length; i++) {
            if (free[i] != null) {
                temp.add(free[i]);
            }
        }
        this.freeMem = temp;
        return free;
    }
}