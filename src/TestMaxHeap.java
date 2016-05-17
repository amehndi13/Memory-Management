/**
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: TestMaxHeap.java
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class TestMaxHeap {

    static MaxHeap<Integer> heap;
    static MaxHeap<Double> heap2;

    @Before
    public void before() {
        heap = new MaxHeap<Integer>();
        Double[] ra = {5.3, 2.1, 40.0, 1.4, 3.7, 9.22, 11.91, 25.41, 14.9};
        heap2 = new MaxHeap<Double>(ra);
    }

    // Tests both Constructors.
    @Test
    public void testConstructors() {
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
        assertNull(heap.peek());
        assertNull(heap.poll());
        assertEquals(9, heap2.size());
        assertFalse(heap2.isEmpty());
        assertEquals((Comparable) 40.0, heap2.peek());
        assertEquals((Comparable) 40.0, heap2.poll());
        assertEquals(8, heap2.size());
        assertEquals((Comparable) 25.41, heap2.peek());
    }

    // Tests offer(), peek(), isEmpty(), and size()
    // Tests bubbleUp() through offer()
    @Test
    public void testOfferAndPeek() {
        // Can't add null object
        assertFalse(heap.offer(null));
        assertNull(heap.peek());
        assertTrue(heap.isEmpty());
        // Add first object
        assertTrue(heap.offer(1));
        assertEquals(1, heap.size());
        assertEquals((Comparable) 1, heap.peek());
        assertFalse(heap.isEmpty());
        // Add new max
        assertTrue(heap.offer(2));
        assertEquals(2, heap.size());
        assertEquals((Comparable) 2, heap.peek());
        // Add another new max
        assertTrue(heap.offer(3));
        assertEquals(3, heap.size());
        assertEquals((Comparable) 3, heap.peek());
        // Another new max. Heap should resize here.
        assertTrue(heap.offer(6));
        assertEquals(4, heap.size());
        assertEquals((Comparable) 6, heap.peek());
        // Add item that isn't max.
        assertTrue(heap.offer(4));
        assertEquals(5, heap.size());
        assertEquals((Comparable) 6, heap.peek());
        // Add item that isn't max.
        assertTrue(heap.offer(0));
        assertEquals(6, heap.size());
        // Max should still be 6.
        assertEquals((Comparable) 6, heap.peek());
        // New Max.
        assertTrue(heap.offer(400));
        assertEquals(7, heap.size());
        assertEquals((Comparable) 400, heap.peek());
        assertFalse(heap.isEmpty());
    }

    // Tests poll(), peek(), isEmpty(), and size()
    // Tests bubbleDown() through poll()
    @Test
    public void testPollAndPeek() {
        assertTrue(heap.isEmpty());
        assertTrue(heap.offer(1));
        assertTrue(heap.offer(2));
        assertTrue(heap.offer(3));
        assertTrue(heap.offer(6));
        assertTrue(heap.offer(4));
        assertTrue(heap.offer(0));
        assertTrue(heap.offer(400));
        assertFalse(heap.isEmpty());
        // Heap has items 400, 0, 4, 6, 3, 2, 1
        // Max is 400
        assertEquals(7, heap.size());
        assertEquals((Comparable) 400, heap.peek());
        assertEquals((Comparable) 400, heap.poll());
        // Heap has items 0, 4, 6, 3, 2, 1
        // Max is 6
        assertEquals(6, heap.size());
        assertEquals((Comparable) 6, heap.peek());
        assertEquals((Comparable) 6, heap.poll());
        // Heap has items 0, 4, 3, 2, 1
        // Max is 4
        assertEquals(5, heap.size());
        assertEquals((Comparable) 4, heap.peek());
        assertEquals((Comparable) 4, heap.poll());
        // Heap has items 0, 3, 2, 1
        // Max is 3
        assertEquals(4, heap.size());
        assertEquals((Comparable) 3, heap.peek());
        assertEquals((Comparable) 3, heap.poll());
        // Heap has items 0, 2, 1
        // Max is 2
        assertEquals(3, heap.size());
        assertEquals((Comparable) 2, heap.peek());
        assertEquals((Comparable) 2, heap.poll());
        // Heap has items 0, 1
        // Max is 1
        assertEquals(2, heap.size());
        assertEquals((Comparable) 1, heap.peek());
        assertEquals((Comparable) 1, heap.poll());
        // Heap has items 0
        // Max is 0
        assertEquals(1, heap.size());
        assertEquals((Comparable) 0, heap.peek());
        assertEquals((Comparable) 0, heap.poll());
        // Heap has no items
        // Max is null
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
        assertEquals(null, heap.peek());
        assertEquals(null, heap.poll());
    }

    // Tests clear()
    @Test
    public void testClear() {
        assertTrue(heap.offer(1));
        assertTrue(heap.offer(2));
        assertTrue(heap.offer(3));
        assertTrue(heap.offer(6));
        assertTrue(heap.offer(4));
        assertTrue(heap.offer(0));
        assertTrue(heap.offer(400));
        assertFalse(heap.isEmpty());
        assertEquals(7, heap.size());
        heap.clear();
        assertTrue(heap.isEmpty());
        assertNull(heap.peek());
        assertNull(heap.poll());
        assertEquals(0, heap.size());
    }

    // Tests toArray()
    public void testToArray() {
        assertTrue(heap.offer(1));
        assertTrue(heap.offer(2));
        assertTrue(heap.offer(3));
        assertTrue(heap.offer(6));
        assertTrue(heap.offer(4));
        assertTrue(heap.offer(0));
        assertTrue(heap.offer(400));
        Comparable[] heapArray = heap.toArray();
        assertTrue(heapArray != null);
        assertEquals(7, heapArray.length);
        assertEquals((Comparable) 400, heapArray[0]);
        for (int i = 1; i < 8; i++) {
            System.out.println(heapArray[i]);
            assertTrue((Comparable) 1 == heapArray[i] 
                || (Comparable) 2 == heapArray[i] 
                || (Comparable) 3 == heapArray[i]
                || (Comparable) 6 == heapArray[i]
                || (Comparable) 4 == heapArray[i]
                || (Comparable) 0 == heapArray[i]);
        }
    }

    // Tests all of the methods
    @Test
    public void testAll() {
        assertTrue(heap.isEmpty());
        assertTrue(heap.offer(1));
        assertTrue(heap.offer(2));
        assertTrue(heap.offer(3));
        assertTrue(heap.offer(6));
        assertTrue(heap.offer(4));
        assertTrue(heap.offer(0));
        assertTrue(heap.offer(400));
        assertFalse(heap.isEmpty());
        // Heap has items 400, 0, 4, 6, 3, 2, 1
        // Max is 400
        assertEquals(7, heap.size());
        assertEquals((Comparable) 400, heap.peek());
        assertEquals((Comparable) 400, heap.poll());
        // Heap has items 0, 4, 6, 3, 2, 1
        // Max is 6
        assertTrue(heap.offer(6));
        // Heap has items 0, 4, 6, 3, 2, 1, 6
        // Max is 6
        assertEquals(7, heap.size());
        assertEquals((Comparable) 6, heap.peek());
        assertEquals((Comparable) 6, heap.poll());
        // Heap has items 0, 4, 6, 3, 2, 1
        // Max is 6
        assertEquals(6, heap.size());
        assertEquals((Comparable) 6, heap.peek());
        assertEquals((Comparable) 6, heap.poll());
        // Heap has items 0, 4, 3, 2, 1
        // Max is 4
        assertEquals(5, heap.size());
        assertEquals((Comparable) 4, heap.peek());
        assertTrue(heap.offer(12));
        // Heap has items 0, 4, 3, 2, 1, 12
        // Max is 12
        assertEquals(6, heap.size());
        assertEquals((Comparable) 12, heap.peek());
        assertEquals((Comparable) 12, heap.poll());
        assertTrue(heap.offer(11));
        // Heap has items 0, 4, 3, 2, 1, 11
        // Max is 11
        assertEquals(6, heap.size());
        assertEquals((Comparable) 11, heap.peek());
        assertEquals((Comparable) 11, heap.poll());
        // Heap has items 0, 4, 3, 2, 1
        // Max is 4
        assertEquals(5, heap.size());
        assertEquals((Comparable) 4, heap.peek());
        assertEquals((Comparable) 4, heap.poll());
        // Heap has items 0, 3, 2, 1
        // Max is 3
        assertEquals(4, heap.size());
        assertEquals((Comparable) 3, heap.peek());
        assertFalse(heap.isEmpty());

        Comparable[] temp = heap.toArray();
        assertEquals((Comparable) 3, temp[0]);
        for (int i = 1; i < 4; i++) {
            assertTrue((Comparable) 0 == temp[i] 
                || (Comparable) 1 == temp[i] 
                || (Comparable) 2 == temp[i]);
        }

        // Clearing
        heap.clear();
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
        // Adding to a cleared heap
        assertTrue(heap.offer(5));
        assertFalse(heap.isEmpty());
        assertEquals((Comparable) 5, heap.peek());
        assertEquals((Comparable) 5, heap.poll());
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.size());
        assertNull(heap.toArray());
    }
}