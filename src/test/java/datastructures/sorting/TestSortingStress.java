package datastructures.sorting;

import misc.BaseTest;
import misc.Searcher;

import org.junit.Test;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }  
	
	@Test(timeout=10*SECOND)
    public void testPlaceholder() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        int cap = 10000000;

        for (int i = cap - 1; i >= 0; i--) {
            heap.insert(i);
            assertEquals(heap.size(),cap-i);
        }

        for (int i = 0; i < cap; i++) {
        	assertEquals(heap.removeMin(),i);
        	assertEquals(heap.size(),cap-i-1);
        }
    }
	
	@Test(timeout=10*SECOND)
    public void testManyTopSort() {
		int cap = 200000;
		IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < cap; i++) {
            list.add(i);
        }
        assertEquals(list.size(),cap);

        IList<Integer> top = Searcher.topKSort(cap/2, list);
        assertEquals(cap/2, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(cap/2 + i, top.get(i));
        }
    }	
	
	@Test(timeout=10*SECOND)
	public void testManyMessyTopSort() {
		int cap = 50000;
		IList<Integer> list = new DoubleLinkedList<>();
		for (int i = cap-1; i >= 0; i--) {
			list.add(i);
			list.add(i);
			list.add(i);
			list.add(i);
		}
		assertEquals(list.size(),cap*4);
		
		IList<Integer> top = Searcher.topKSort(cap*2, list);
        assertEquals(cap*2, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(cap/2 + i/4, top.get(i));
        }
	}
}

