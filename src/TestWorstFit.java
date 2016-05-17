/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: TestWorstFit.java
 */

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class TestWorstFit {

    static WorstFit fit;

    @Before
    public void before() {
        fit = new WorstFit(400);
    }

    // Tests Constructor.
    @Test
    public void testConstructor() {
        assertEquals(400, fit.freeSpace);
        assertTrue(fit.allocatedMem instanceof HashMap);
        assertEquals(1, fit.allocID);
        assertTrue(fit.freeMem instanceof MaxHeap);
        assertEquals(1, fit.freeMem.size());
        assertTrue(!fit.freeMem.isEmpty());
    }

    // Test allocate
    @Test
    public void testAllocate() {
        Block temp = fit.allocate(500, false);
        assertNull(temp);
        assertEquals(400, fit.freeSpace);
        assertNull(fit.allocatedMem.get(1));

        temp = fit.allocate(100, false);
        assertEquals(100, temp.getSize());
        assertEquals(0, temp.getAddress());
        assertEquals(2, temp.getID());
        assertEquals(300, fit.freeSpace);
        assertEquals(0, ((Block) fit.allocatedMem.get(2)).getAddress());
        
        temp = fit.allocate(400, false);
        assertNull(temp);
        assertEquals(300, fit.freeSpace);
        assertNull(fit.allocatedMem.get(3));

        temp = fit.allocate(20, false);
        assertEquals(20, temp.getSize());
        assertEquals(100, temp.getAddress());
        assertEquals(4, temp.getID());
        assertEquals(280, fit.freeSpace);
        assertEquals(100, ((Block) fit.allocatedMem.get(4)).getAddress());
        
        temp = fit.allocate(60, false);
        assertEquals(60, temp.getSize());
        assertEquals(120, temp.getAddress());
        assertEquals(5, temp.getID());
        assertEquals(220, fit.freeSpace);
        assertEquals(120, ((Block) fit.allocatedMem.get(5)).getAddress());
        
        temp = fit.allocate(50, false);
        assertEquals(50, temp.getSize());
        assertEquals(180, temp.getAddress());
        assertEquals(6, temp.getID());
        assertEquals(170, fit.freeSpace);
        assertEquals(180, ((Block) fit.allocatedMem.get(6)).getAddress());
        
        temp = fit.allocate(50, false);
        assertEquals(50, temp.getSize());
        assertEquals(230, temp.getAddress());
        assertEquals(7, temp.getID());
        assertEquals(120, fit.freeSpace);
        assertEquals(230, ((Block) fit.allocatedMem.get(7)).getAddress());
        
        temp = fit.allocate(50, false);
        assertEquals(50, temp.getSize());
        assertEquals(280, temp.getAddress());
        assertEquals(8, temp.getID());
        assertEquals(70, fit.freeSpace);
        assertEquals(280, ((Block) fit.allocatedMem.get(8)).getAddress());
        
        temp = fit.allocate(100, false);
        assertNull(temp);
        assertEquals(70, fit.freeSpace);
        assertNull(fit.allocatedMem.get(9));
        
        temp = fit.allocate(70, false);
        assertEquals(70, temp.getSize());
        assertEquals(330, temp.getAddress());
        assertEquals(10, temp.getID());
        assertEquals(330, ((Block) fit.allocatedMem.get(10)).getAddress());
        
        assertEquals(0, fit.freeSpace);
        assertEquals(0, fit.defrags);
        assertEquals(11, fit.allocID);
        assertNull(fit.allocatedMem.get(11));
        assertNull(fit.freeMem.peek());
    }

    // Test deallocate
    @Test
    public void testDeallocate() {
        assertEquals(400, fit.freeSpace); 
        fit.allocate(100, false); // allocID 1
        assertEquals(300, fit.freeSpace); 
        fit.allocate(20, false); // allocID 2
        assertEquals(280, fit.freeSpace);
        fit.allocate(60, false); // allocID 3
        assertEquals(220, fit.freeSpace);
        fit.allocate(50, false); // allocID 4
        assertEquals(170, fit.freeSpace);
        fit.allocate(50, false); // allocID 5
        assertEquals(120, fit.freeSpace);
        fit.allocate(50, false); // allocID 6
        assertEquals(70, fit.freeSpace);
        assertFalse(fit.freeMem.isEmpty());

        assertEquals(70, ((Block) fit.freeMem.peek()).getSize());
        assertEquals(1, fit.freeMem.size());
        assertNull(fit.deallocate(fit.allocID));
        assertNull(fit.deallocate(0));
        assertNull(fit.deallocate(-1));

        Block temp = fit.deallocate(1);
        assertNull(fit.allocatedMem.get(1));
        assertEquals(100, temp.getSize());
        assertEquals(0, temp.getAddress());
        assertEquals(100, ((Block) fit.freeMem.peek()).getSize());
        assertEquals(170, fit.freeSpace);

        temp = fit.deallocate(3);
        assertNull(fit.allocatedMem.get(3));
        assertEquals(60, temp.getSize());
        assertEquals(120, temp.getAddress());
        assertEquals(100, ((Block) fit.freeMem.peek()).getSize());
        assertEquals(230, fit.freeSpace);

        temp = fit.deallocate(5);
        assertNull(fit.allocatedMem.get(5));
        assertEquals(50, temp.getSize());
        assertEquals(230, temp.getAddress());
        assertEquals(100, ((Block) fit.freeMem.peek()).getSize());
        assertEquals(280, fit.freeSpace);

        temp = fit.deallocate(2);
        assertNull(fit.allocatedMem.get(2));
        assertEquals(20, temp.getSize());
        assertEquals(100, temp.getAddress());
        assertEquals(100, ((Block) fit.freeMem.peek()).getSize());
        assertEquals(300, fit.freeSpace);

        temp = fit.deallocate(4);
        assertNull(fit.allocatedMem.get(4));
        assertEquals(50, temp.getSize());
        assertEquals(180, temp.getAddress());
        assertEquals(100, ((Block) fit.freeMem.peek()).getSize());
        assertEquals(350, fit.freeSpace);

        assertFalse(fit.allocatedMem.isEmpty());

        temp = fit.deallocate(6);
        assertNull(fit.allocatedMem.get(2));
        assertEquals(50, temp.getSize());
        assertEquals(280, temp.getAddress());
        assertEquals(100, ((Block) fit.freeMem.peek()).getSize());
        assertEquals(400, fit.freeSpace);

        assertTrue(fit.allocatedMem.isEmpty());

    }

    // Tests defrag
    @Test
    public void testDefragment() {
        assertEquals(400, fit.freeSpace); 
        fit.allocate(100, false); // allocID 1
        assertEquals(300, fit.freeSpace); 
        fit.allocate(20, false); // allocID 2
        assertEquals(280, fit.freeSpace);
        fit.allocate(60, false); // allocID 3
        assertEquals(220, fit.freeSpace);
        fit.allocate(50, false); // allocID 4
        assertEquals(170, fit.freeSpace);
        fit.allocate(50, false); // allocID 5
        assertEquals(120, fit.freeSpace);
        fit.allocate(50, false); // allocID 6
        assertEquals(70, fit.freeSpace);

        fit.deallocate(2);
        fit.deallocate(3);
        fit.deallocate(4);

        Block temp = fit.allocate(110, true);
        assertNull(temp); // allocate failed without deallocating
        assertEquals(0, fit.defrags);

        temp = fit.allocate(110, false);
        assertEquals(1, fit.defrags);
        assertEquals(70, ((Block) fit.freeMem.peek()).getSize());
    }
}