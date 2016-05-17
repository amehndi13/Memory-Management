/**
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: AddressComparator.java
 */

import java.util.Comparator;

/** Comparator that compares two blocks based on address. */
public class AddressComparator implements Comparator<Block> {

    /** Default Constructor. */
    public AddressComparator() {
    }

    /** Compares two blocks based on address.
     *  @param a the first block
     *  @param b the second block
     *  @return negative less than, 0 if equal, positive if greater than 
     */
    public int compare(Block a, Block b) {
        
        if (a.getAddress() > b.getAddress()) {
            return 1;
        } else if (a.getAddress() == b.getAddress()) {
            return 0;
        } else {
            return -1;
        }
    }
}