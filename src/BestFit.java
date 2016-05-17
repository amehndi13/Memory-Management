/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: BestFit.java
 */

/** Best Fit allocation method. */
public class BestFit extends BaseManager {
    /** Instance of AVLtree for free memory.*/
    protected AVLtree freeMem;
    /** Default Constructor for BestFit.
     *  @param size the size of the memory
     */
    public BestFit(int size) {
        super(size);
        this.freeMem = new AVLtree();
        this.freeMem.add(new Block(size, 0));
    }

    /** Method to allocate a block.
     * @param size - the intended size of a block
     * @param defrag - whether or not there has been a degrag
     * this call of allocate or not
     * @return the allocated block, or null if it failed.
     */
    @Override
    public Block allocate(int size, boolean defrag) {
        long firstTime = System.nanoTime();
        if (super.allocate(size, defrag) == null) {
            return null;
        }
        Block newBlock = new Block();
        newBlock.setSize(size);
        Block findBlock = (Block) this.freeMem.findAndDeleteBest(newBlock);
        if (findBlock == null) {
            if (!defrag) {
                long secondTime = System.nanoTime();
                this.defragment(this.freeMem.toArray(), false);
                this.sumAllocTime += (secondTime - firstTime);
                return this.allocate(size, true);
            } 
            this.allocID++;
            this.fails++;
            this.totalFailSize += size;
            return null;
        } 
        this.freeSpace -= size;
        this.allocID++;
        //we now have the block
        //Calculate the new block of free memory
        if (findBlock.getSize() == size) {
            findBlock.setID(this.allocID - 1);
            this.allocatedMem.put(this.allocID - 1, findBlock);
            long secondTime = System.nanoTime();
            this.sumAllocTime += (secondTime - firstTime);
            return findBlock;
        } else {
            newBlock.setAddress(findBlock.getAddress());
            newBlock.setID(this.allocID - 1);
            findBlock.setAddress(findBlock.getAddress() + size);
            findBlock.setSize(findBlock.getSize() - size);
            this.freeMem.add(findBlock);
            this.allocatedMem.put(this.allocID - 1, newBlock);
            long secondTime = System.nanoTime();
            this.sumAllocTime += (secondTime - firstTime);
            return newBlock;
        }
    }

    /** Deallocate method.
     * @param call - the allocation id for the block to free up
     * @return the freed up block, or null if failed
     */
    @Override
    public Block deallocate(int call) {
        Block block = super.deallocate(call);
        if (block == null) {
            return null;
        }
        this.freeMem.add(block);
        return block;
    }
    /** Defragment Method.
     * @param array - the array of blocks
     * @param your whether or not this is a YourFit
     */
    @Override
    public Block[] defragment(Block[] array, boolean your) {
        array = super.defragment(array, false);
        this.freeMem = new AVLtree(array);
        return array;
    }
}