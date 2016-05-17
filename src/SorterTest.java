/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: SorterTest.java
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Test;

import java.util.Random;

public class SorterTest {

    static Block[] blocks;
    static Sorter sorter;
    static int totalSize;
    static AddressComparator comparator;
    static Block[] randblocks;
    static int totalRandSize;

    @Before
    public void before() {
        totalSize = 0;
        blocks = new Block[10];
        for (int i = 10; i > 0; i--) {
            Block block = new Block(i * 10, i * 10, i);
            blocks[10 - i] = block;
            totalSize = totalSize + block.getSize();
        }
        Random gen = new Random();
        int size = gen.nextInt(5000);
        randblocks = new Block[size];
        for (int i = 0; i < size; i++) {
            randblocks[i] = new Block(gen.nextInt(1000), gen.nextInt(1000));
            totalRandSize += randblocks[i].getSize();
        }
        comparator = new AddressComparator();
        sorter = new Sorter(blocks, comparator);
    }
    
    // Tests quicksort on an array blocks (in reverse order)
	@Test
	public void blockQuickSortTestReverse() {
		blocks = this.sorter.quickSortedArray();
		int size = blocks.length;

		for (int i = 0; i < size - 1; i++) {
		    Block temp1 = blocks[i];
		    Block temp2 = blocks[i + 1];
		    assertTrue(comparator.compare(temp1, temp2) <= 0);
		}
	}
	
	// Tests bucketsort on an array blocks (in reverse order)
	@Test
	public void blockBucketSortTestReverse() {
        blocks = this.sorter.bucketSort(this.totalSize);
        int size = blocks.length;

        for (int i = 0; i < size - 1; i++) {
            Block temp1 = blocks[i];
            Block temp2 = blocks[i + 1];
            assertTrue(comparator.compare(temp1, temp2) <= 0);
        }
	}

    // Tests quicksort on a random of array of blocks
    // array is of random size, and each item has random size/address
    @Test
    public void blockQuickSortTestRandom(){
        randblocks = this.sorter.quickSortedArray();
        int l = randblocks.length;

        for (int i = 0; i < l - 1; i++) {
            Block temp1 = blocks[i];
            Block temp2 = blocks[i + 1];
            assertTrue(comparator.compare(temp1, temp2) <= 0);
        }
    }

    // Tests bucketsort on a random of array of blocks
    // array is of random size, and each item has random size/address
    @Test
    public void blockBucketSortTestRandom(){
        randblocks = this.sorter.bucketSort(this.totalRandSize);
        int l = randblocks.length;

        for (int i = 0; i < l - 1; i++) {
            Block temp1 = blocks[i];
            Block temp2 = blocks[i + 1];
            assertTrue(comparator.compare(temp1, temp2) <= 0);
        }
    }
}
