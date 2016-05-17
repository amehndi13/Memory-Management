/** 
 * Marc Feldman
 * Anuj Mehndiratta
 * Alex Owen
 *
 * EN.600.226 Data Structures, Fall 2015
 * Memory Management: AvlTreeTest.java
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

 

 

public class AvlTreeTest {

    private AVLtree<Integer> tree = new AVLtree<Integer>();
    /**
     * These three methods were taken from the internet to check the balancing of trees.
     *  Clearly, other methods are used to truly test the working of the AVL Tree
     * found on the page:
     * http://www.dreamincode.net/forums/topic/214510-working-example-of-avl-tree-remove-method/
     * which is the test suite found on the first comment
     */
    private boolean checkBalanceOfTree(AVLtree.BNode current) {
        boolean balancedRight = true, balancedLeft = true;
        int leftHeight = 0, rightHeight = 0;
        if (current.right != null) {
            balancedRight = checkBalanceOfTree(current.right);
            rightHeight = getDepth(current.right);
        }
        if (current.left != null) {
            balancedLeft = checkBalanceOfTree(current.left);
            leftHeight = getDepth(current.left);
        }
        return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight) < 2;
    }
    private int getDepth(AVLtree.BNode n) {
        int leftHeight = 0, rightHeight = 0;
        if (n.right != null)
            rightHeight = getDepth(n.right);
        if (n.left != null)
            leftHeight = getDepth(n.left);
        return Math.max(rightHeight, leftHeight)+1;
    }
    private boolean checkOrderingOfTree(AVLtree.BNode current) {
        if(current.left != null) {
            if(current.left.data.compareTo(current.data) > 0)
                return false;
            else
                return checkOrderingOfTree(current.left);
        } else if(current.right != null) {
            if(current.right.data.compareTo(current.data) < 0)
                return false;
            else
                return checkOrderingOfTree(current.right);
        } else if(current.left == null && current.right == null)
            return true;
        return true;
    }

    @Test
    public void testAdd() {
        //This test will add things in order to make sure that no balancing will take 
        //place, as to only test add
        tree.add(61);
        assertEquals(tree.root(), new Integer(61));
        tree.add(24);
        tree.add(74);
        assertEquals(tree.root(), new Integer(61));
        tree.add(17);
        tree.add(38);
        assertEquals(tree.root(), new Integer(61));
        tree.add(64);
        tree.add(86);
        assertEquals(tree.root(), new Integer(61));
        tree.add(11);
        tree.add(93);
        assertEquals(tree.root(), new Integer(61));
        tree.add(45);
        tree.add(62);
        assertEquals(tree.root(), new Integer(61));
        tree.add(21);
        tree.add(28);
        assertEquals(tree.root(), new Integer(61));
        tree.add(65);
        tree.add(83);
        assertEquals(tree.root(), new Integer(61));
        assertEquals(tree.root.left.data, new Integer(24));
        assertEquals(tree.root.left.left.data, new Integer(17));
        assertEquals(tree.root.left.left.left.data, new Integer(11));
        assertEquals(tree.root.left.left.right.data, new Integer(21));
        assertEquals(tree.root.left.right.data, new Integer(38));
        assertEquals(tree.root.left.right.left.data, new Integer(28));
        assertEquals(tree.root.left.right.right.data, new Integer(45));
        assertEquals(tree.root.right.data, new Integer(74));
        assertEquals(tree.root.right.left.data, new Integer(64));
        assertEquals(tree.root.right.left.left.data, new Integer(62));
        assertEquals(tree.root.right.left.right.data, new Integer(65));
        assertEquals(tree.root.right.right.data, new Integer(86));
        assertEquals(tree.root.right.right.left.data, new Integer(83));
        assertEquals(tree.root.right.right.right.data, new Integer(93));
    }

    @Test
    public void removeTest() {
        tree.add(61);
        tree.remove(61);
        assertNull(tree.root());
        tree.add(61);
        tree.add(24);
        tree.add(74);
        tree.add(17);
        tree.add(38);
        tree.add(64);
        tree.add(86);
        tree.add(11);
        tree.add(93);
        tree.add(45);
        tree.add(62);
        tree.add(21);
        tree.add(28);
        tree.add(65);
        tree.add(83);
        tree.remove(83);
        assertEquals(tree.root(), new Integer(61));
        assertEquals(tree.root.left.data, new Integer(24));
        assertEquals(tree.root.left.left.data, new Integer(17));
        assertEquals(tree.root.left.left.left.data, new Integer(11));
        assertEquals(tree.root.left.left.right.data, new Integer(21));
        assertEquals(tree.root.left.right.data, new Integer(38));
        assertEquals(tree.root.left.right.left.data, new Integer(28));
        assertEquals(tree.root.left.right.right.data, new Integer(45));
        assertEquals(tree.root.right.data, new Integer(74));
        assertEquals(tree.root.right.left.data, new Integer(64));
        assertEquals(tree.root.right.left.left.data, new Integer(62));
        assertEquals(tree.root.right.left.right.data, new Integer(65));
        assertEquals(tree.root.right.right.data, new Integer(86));
        assertEquals(tree.root.right.right.right.data, new Integer(93));
        tree.remove(65);
        assertEquals(tree.root(), new Integer(61));
        assertEquals(tree.root.left.data, new Integer(24));
        assertEquals(tree.root.left.left.data, new Integer(17));
        assertEquals(tree.root.left.left.left.data, new Integer(11));
        assertEquals(tree.root.left.left.right.data, new Integer(21));
        assertEquals(tree.root.left.right.data, new Integer(38));
        assertEquals(tree.root.left.right.left.data, new Integer(28));
        assertEquals(tree.root.left.right.right.data, new Integer(45));
        assertEquals(tree.root.right.data, new Integer(74));
        assertEquals(tree.root.right.left.data, new Integer(64));
        assertEquals(tree.root.right.left.left.data, new Integer(62));
        assertEquals(tree.root.right.right.data, new Integer(86));
        assertEquals(tree.root.right.right.right.data, new Integer(93));
        tree.remove(28);
        tree.remove(21);
        tree.remove(62);
        assertEquals(tree.root(), new Integer(61));
        assertEquals(tree.root.left.data, new Integer(24));
        assertEquals(tree.root.left.left.data, new Integer(17));
        assertEquals(tree.root.left.left.left.data, new Integer(11));
        assertEquals(tree.root.left.right.data, new Integer(38));
        assertEquals(tree.root.left.right.right.data, new Integer(45));
        assertEquals(tree.root.right.data, new Integer(74));
        assertEquals(tree.root.right.left.data, new Integer(64));
        assertEquals(tree.root.right.right.data, new Integer(86));
        assertEquals(tree.root.right.right.right.data, new Integer(93));
        tree.remove(45);
        tree.remove(93);
        assertEquals(tree.root(), new Integer(61));
        assertEquals(tree.root.left.data, new Integer(24));
        assertEquals(tree.root.left.left.data, new Integer(17));
        assertEquals(tree.root.left.left.left.data, new Integer(11));
        assertEquals(tree.root.left.right.data, new Integer(38));
        assertEquals(tree.root.right.data, new Integer(74));
        assertEquals(tree.root.right.left.data, new Integer(64));
        assertEquals(tree.root.right.right.data, new Integer(86));
        tree.remove(11);
        tree.remove(86);
        tree.remove(64);
        assertEquals(tree.root(), new Integer(61));
        assertEquals(tree.root.left.data, new Integer(24));
        assertEquals(tree.root.left.left.data, new Integer(17));
        assertEquals(tree.root.left.right.data, new Integer(38));
        assertEquals(tree.root.right.data, new Integer(74));
        tree.remove(17);
        tree.remove(38);
        assertEquals(tree.root(), new Integer(61));
        assertEquals(tree.root.left.data, new Integer(24));
        assertEquals(tree.root.right.data, new Integer(74));
        tree.remove(74);
        tree.remove(24);
        assertEquals(tree.root(), new Integer(61));
        tree.remove(61);
        assertTrue(tree.isEmpty());
        assertNull(tree.root());

    }
    @Test
    public void testRotateLeft() {
        tree.add(93);
        tree.add(86);
        assertEquals(tree.root(), new Integer(93));
        tree.add(74);
        assertEquals(tree.root(), new Integer(86));
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        tree.add(61);
        tree.add(38);
        tree.add(24);
        tree.add(17);
        assertEquals(tree.root(), new Integer(61));
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        tree.remove(74);
        tree.remove(93);
        assertEquals(tree.root(), new Integer(61));
        tree.add(11);
        assertEquals(tree.root(), new Integer(24));
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertEquals(tree.root.left.data, new Integer(17));
        assertEquals(tree.root.left.left.data, new Integer(11));
        assertEquals(tree.root.right.data, new Integer(61));
        assertEquals(tree.root.right.left.data, new Integer(38));
        assertEquals(tree.root.right.right.data, new Integer(86));
        tree.remove(38);
        assertEquals(tree.root.left.data, new Integer(17));
        tree.remove(86);
        assertEquals(tree.root.left.data, new Integer(17));
        tree.add(5);
        assertEquals(tree.size(), 5);
        assertEquals(tree.root(), new Integer(24));
        assertEquals(tree.root.right.data, new Integer(61));
        assertTrue(tree.root.right.isLeaf());
        assertEquals(tree.root.left.data, new Integer(11));
        assertEquals(tree.root.left.left.data, new Integer(5));
        assertEquals(tree.root.left.right.data, new Integer(17));
        tree.add(3);
        assertEquals(tree.root(), new Integer(11));
        assertEquals(tree.root.right.data, new Integer(24));
        assertEquals(tree.root.right.right.data, new Integer(61));
        assertEquals(tree.root.right.left.data, new Integer(17));
        assertEquals(tree.root.left.data, new Integer(5));
        assertEquals(tree.root.left.left.data, new Integer(3));


    }
    @Test
    public void testRotateRight() {
        assertTrue(tree.isEmpty());
        tree.add(16);
        tree.add(24);
        tree.add(36);
        assertEquals(tree.toString(), "[16, 24, 36]");
        assertEquals(tree.root(), new Integer(24));
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));

        tree.add(19);
        tree.add(44);
        tree.add(28);
        tree.add(61);
        tree.add(74);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        tree.add(83);
        tree.add(64);
        tree.add(52);
        tree.add(65);
        tree.add(86);
        tree.add(93);
        tree.add(88);
        tree.remove(88);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(88));
        tree.remove(19);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(19));
        tree.remove(16);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(16));
        tree.remove(28);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(28));
        tree.remove(24);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(24));
        tree.remove(36);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(36));
        assertEquals(tree.root.left.data, new Integer(44));
        tree.remove(52);

        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(52));
        tree.remove(93);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(93));
         
        tree.remove(86);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(86));
         
        tree.remove(83);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertFalse(tree.contains(83));
    }
    @Test
    public void testDoubleWithLeft() { 
        tree.add(50);
        tree.add(100);
        tree.add(25);
        assertEquals(tree.root(), new Integer(50));
        tree.add(12);

        tree.add(40);
        assertEquals(tree.root(), new Integer(50));
        tree.add(30);
        assertEquals(tree.root(), new Integer(40));
        tree.add(20);
        tree.add(5);
        tree.add(21);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        //point 1
        //tree.remove(12);
        tree.remove(5);
        tree.remove(100);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        //point 2

    }
    @Test
    public void testDoubleWithRight() {
        tree.add(25);
        tree.add(50);
        tree.add(12);
        tree.add(100);
        tree.add(40);
        tree.add(45);
        assertTrue(checkBalanceOfTree(tree.root));
        assertTrue(checkOrderingOfTree(tree.root));
        assertEquals(tree.root(), new Integer(40));
    }
    @Test
    public void testFindBest() {
        tree.add(50);
        tree.findAndDeleteBest(40);
        assertNull(tree.root());
        //assertNull(tree.toString());
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(12);
        tree.add(37);
        tree.add(61);
        tree.add(90);
        tree.add(6);
        tree.add(19);
        tree.add(30);
        tree.add(45);
        tree.add(55);
        tree.add(66);
        tree.add(80);
        tree.add(100);
        tree.add(91);
        assertEquals(tree.size(), 16);
        assertEquals(tree.findAndDeleteBest(5), new Integer(6));
        assertEquals(tree.size(), 15);
        assertEquals(tree.findAndDeleteBest(101), null);
        assertEquals(tree.size(), 15);
        assertEquals(tree.findAndDeleteBest(20), new Integer(25));
        assertEquals(tree.size(), 14);
        assertEquals(tree.findAndDeleteBest(92), new Integer(100));
        assertEquals(tree.size(), 13);
        assertEquals(tree.findAndDeleteBest(81), new Integer(90));
        assertEquals(tree.size(), 12);
        assertEquals(tree.findAndDeleteBest(31), new Integer(37));
        assertEquals(tree.size(), 11);
        tree.findAndDeleteBest(1);
        assertEquals(tree.size(), 10);
        tree.findAndDeleteBest(1);
        assertEquals(tree.size(), 9);
        tree.findAndDeleteBest(1);
        assertEquals(tree.size(), 8);
        tree.findAndDeleteBest(1);
        assertEquals(tree.size(), 7);
        tree.findAndDeleteBest(1);
        assertEquals(tree.size(), 6);
        tree.findAndDeleteBest(1);
        assertEquals(tree.size(), 5);
        tree.findAndDeleteBest(1);
        assertEquals(tree.size(), 4);
        tree.findAndDeleteBest(1);
        assertEquals(tree.size(), 3);
        tree.findAndDeleteBest(1);
        assertEquals(tree.size(), 2);
        tree.findAndDeleteBest(1);
        assertEquals(tree.size(), 1);
        tree.findAndDeleteBest(1);
        tree.findAndDeleteBest(50);

        assertEquals(tree.size(), 0);
        assertTrue(tree.isEmpty());
        assertNull(tree.root());
        

    }

}
