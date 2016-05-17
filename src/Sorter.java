/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: Sorter.java
 */
import java.util.ArrayList;
import java.util.TreeSet;

/** Sorting Class. */
public class Sorter {
    
    /** 
     * Makes it so the max number of blocks in a bucket is 3.
     * (if they are distributed evenly)
     */
    private static final int BUCKETDISTRIBUTOR = 3;

    /** Array to hold and sort blocks. */
    private Block[] blockArray;

    /** Comparator that compares blocks by address. */
    private AddressComparator ac;

    /**
     * Sorter constructor.
     * @param b array to sort
     * @param a the address comparator
     */
    public Sorter(Block[] b, AddressComparator a) {
        this.blockArray = b;
        this.ac = a;
    }


    /**
     * returns a sorted array (by mem address).
     * @return a sorted array by mem address.
     */
    public Block[] quickSortedArray() {
        int size = this.blockArray.length;
        this.quickSort(0, size - 1);
        return this.blockArray;
    }

    /**
     * recursive quick sort for blocks.
     * modified from OpenDSA JHU version
     * @param i lower bound index
     * @param j upper bound index
     */
    public void quickSort(int i, int j) { //quicksort
        int pivotIndex = this.findPivot(i, j); // pick a pivot
        this.swap(pivotIndex, j); //stick pivot at end
        int k = this.partition(i, j - 1, this.blockArray[j]); 
        // k will be the first position in the right subarray
        this.swap(k, j); // put pivot in place
        if ((k - i) > 1) {
            this.quickSort(i, k - 1);  // Sort left partition
        }
        if ((j - k) > 1) {
            this.quickSort(k + 1, j);  // Sort right partition
        }
    }
    
    /**
     * partition to sort/split into sub arrays.
     * @param left index
     * @param right index
     * @param pivot pivot value
     * @return the leftmost index of the right subarray
     */
    private int partition(int left, int right, Block pivot) {
        while (left <= right) {
            while (this.ac.compare(this.blockArray[left], pivot) < 0) {
                left++;
            }
            while (right >= left 
                && this.ac.compare(this.blockArray[right], (pivot)) >= 0) {
                right--;
            }
            if (right > left) {
                this.swap(left, right);
            }
        }
        return left;
    }
    
    /**
     * Swap values at array indices.
     * @param pivotIndex index of pivot or element to swap
     * @param j index of other element to swap
     */
    private void swap(int pivotIndex, int j) {
        Block temp = this.blockArray[j];
        this.blockArray[j] = this.blockArray[pivotIndex];
        this.blockArray[pivotIndex] = temp;
    }
    
    /**
     * find the index of the pivot value.
     * @param i first index
     * @param j last index
     * @return the index of the pivot value
     */
    private int findPivot(int i, int j) {
        return (i + j) / 2;
    }
    
    
    /**
     * Bucket sort. Buckets of treesets so that you don't 
     * need to iterate over all the buckets which would 
     * be total memory size. This way we also return elements
     * from trees in O(log N) time.
     * Worked with another group to make this fully functional.
     * @param totalSize total memory available
     * @return array of blocks sorted by address.
     */
    public Block[] bucketSort(int totalSize) {
        int numBlocks = this.blockArray.length;
        int numBuckets = numBlocks / BUCKETDISTRIBUTOR;
        if (numBuckets == 0) {
            numBuckets = 1;
        }
        int range = totalSize / numBuckets;
        ArrayList<TreeSet<Block>> buckets;
        buckets = new ArrayList<TreeSet<Block>>(numBuckets);
        TreeSet<Block> treeSet;
        for (int u = 0; u < numBuckets; u++) {
            treeSet = new TreeSet<Block>();
            buckets.add(u, treeSet);
        }
        for (int i = 0; i < numBlocks; i++) {
            Block currentBlock = this.blockArray[i];
            int currentMemory = currentBlock.getAddress();
            int upper = range;
            int j = 0;
            boolean notPlaced = true;
            if (currentMemory == 0) {
                buckets.get(0).add(currentBlock);
                notPlaced = false;
            }
            while (notPlaced) {
                if (j >= buckets.size()) {
                    break;
                }
                if ((upper - range) < currentMemory && currentMemory <= upper) {
                    buckets.get(j).add(currentBlock);
                    notPlaced = false;
                }
                j++;
                
                upper = range * j;
            }
        }
        return (this.bucketSortedArray(buckets, numBuckets));    
    }


    /**
     * turns sorted blocks into an ordered linkedlist.
     * @param buckets arraylist of treeset buckets
     * @param numBuckets number of buckets
     * @return ordered linkedlist
     */
    public Block[] bucketSortedArray(
        ArrayList<TreeSet<Block>> buckets, int numBuckets) {
        int counter = 0;
        for (int i = 0; i < numBuckets; i++) {
            int size = buckets.get(i).size();
            for (int j = 0; j < size; j++) {
                this.blockArray[counter++] = buckets.get(i).pollFirst();
            }
        }
        return this.blockArray;
    }
    
}
