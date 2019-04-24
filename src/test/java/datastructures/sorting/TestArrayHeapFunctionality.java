package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import misc.exceptions.NoSuchKeyException;

import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }
    
    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        heap.insert(5);
        heap.insert(7); 
        assertEquals(3, heap.size());    
        heap.removeMin();
        assertEquals(2, heap.size()); 
    }    
  
    @Test(timeout=SECOND)
    public void testBasicInsertAndRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(5);
        heap.insert(2);
        heap.insert(9);
        heap.insert(0);
        assertEquals(0, heap.removeMin());  
        assertEquals(2, heap.removeMin());         
    }
    
    public void testDuplicateRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 5; i++) {
            heap.insert(i);
            heap.insert(i);
        }
        for (int i = 4; i >= 0; i++) {
            assertEquals(4-i, heap.removeMin());  
            assertEquals(4-i, heap.removeMin()); 
        }        
        
    }    
    
    @Test(timeout=SECOND)
    public void testPeekandRemoveErrorHandling() {
    	IPriorityQueue<Integer> heap = this.makeInstance();

        try {
        	heap.peekMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // This is ok: do nothing
        }

        try {
            heap.removeMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // This is ok: do nothing
        }
    }    
    
    @Test(timeout=SECOND)
    public void testBasicPeekMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(5);
        heap.insert(2);
        heap.insert(9);
        heap.insert(0);
        heap.insert(-10);
        assertEquals(-10, heap.peekMin());
        assertEquals(-10, heap.removeMin());
        assertEquals(0, heap.peekMin());
        assertEquals(0, heap.peekMin());  
        assertEquals(0, heap.removeMin());
        assertEquals(2, heap.peekMin()); 
    }    
  
    @Test(timeout=SECOND)
    public void testInsertErrorHandling() {
    	IPriorityQueue<Integer> heap = this.makeInstance();

        try {
        	heap.insert(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // This is ok: do nothing
        }
    }  
}

