/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: Block.java
 */

/** A memory block. */
public class Block implements Comparable<Block> {
    /** The address for this block.*/
    private int startAddress;
    /** The current size of the block.*/
    private int size;
    /** Allocation ID of the block.*/
    private int id;
    /** Default Constructor for Block.*/
    public Block() {
        this.id = -1;
        this.size = 0;
        this.startAddress = -1;
    }
    /** A helpful constructor.
     * @param startSize - the initial size
     * @param address - the initial address
     */
    public Block(int startSize, int address) {
        this.size = startSize;
        this.startAddress = address;
        this.id = -1;
    }
    /** A helpful constructor.
     * @param startSize - the initial size
     * @param address - the initial address
     * @param newId - the id
     */
    public Block(int startSize, int address, int newId) {
        this.size = startSize;
        this.startAddress = address;
        this.id = newId;
    }
    /** Setter for address.
     * @param i - the value to set address to
     */
    public void setAddress(int i) {
        this.startAddress = i;
    }
    /** Getter for address.
     *@return startAddress
     */
    public int getAddress() {
        return this.startAddress;
    }
    /** Setter for Size.
     * @param i - the new size
     */
    public void setSize(int i) {
        this.size = i;
    }
    /** Getter for Size.
     * @return size
     */
    public int getSize() {
        return this.size;
    }
    /** Setter for ID.
     * @param i - the new ID value
     */
    public void setID(int i) {
        this.id = i;
    }
    /** Getter for ID.
     * @return id
     */
    public int getID() {
        return this.id;
    }
    /** Checks if block is allocated.
     * @return true if allocated, false otherwise
     */
    public boolean isAllocated() {
        return !(this.id == -1);
    }

    /** Compares this object with the specified object for order. 
     *  @param b the block to compare to
     *  @return negative less than, 0 if equal, positive if greater than 
     */
    public int compareTo(Block b) {
        if (this.getSize() > b.getSize()) {
            return 1;
        } else if (this.getSize() < b.getSize()) {
            return -1;
        } else {
            return 0;
        }
    }
    /** A toString() method for Block.
     * @return the string method of this block
     */
    public String toString() {
        String oss = "Size: " + this.size;
        oss += ", Address: " + this.startAddress;
        oss += ", ID: " + this.id;
        return oss;
    }


}