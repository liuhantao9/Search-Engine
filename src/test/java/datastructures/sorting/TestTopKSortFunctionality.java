package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestTopKSortFunctionality extends BaseTest {
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
        
        IList<Integer> top2 = Searcher.topKSort(10, list);
        assertEquals(10, top2.size());
        for (int i = 0; i < top2.size(); i++) {
            assertEquals(10 + i, top2.get(i)); 
        }
    }
    
    @Test(timeout=SECOND)
    public void testBigK() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(20, list);
        assertEquals(10, top.size());
        for (int i = 0; i < list.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testOnReverseList() {
    	IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 19; i >= 0; i--) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
        
        IList<Integer> top2 = Searcher.topKSort(10, list);
        assertEquals(10, top2.size());
        for (int i = 0; i < top2.size(); i++) {
            assertEquals(10 + i, top2.get(i)); 
        }
    }
    
    @Test(timeout=SECOND)
    public void testOnNegativeList() {
    	IList<Integer> list = new DoubleLinkedList<>();
        for (int i = -20; i < 20 ; i++) {
            list.add(i);
        }
        assertEquals(40, list.size());
        
        IList<Integer> top = Searcher.topKSort(30, list);
        assertEquals(30, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(-10 + i, top.get(i));
        }

    }
    
    @Test(timeout=SECOND)
    public void testOnDuplicatedList() {
    	IList<Integer> list = new DoubleLinkedList<>();
    	for (int i = 0; i < 20; i++) {
            list.add(i);
            list.add(i);
        }
    	assertEquals(40, list.size());

        IList<Integer> top = Searcher.topKSort(10, list);
        assertEquals(10, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i/2, top.get(i));
        }
        
        IList<Integer> top2 = Searcher.topKSort(20, list);
        assertEquals(20, top2.size());
        for (int i = 0; i < top2.size(); i++) {
            assertEquals(10 + i/2, top2.get(i)); 
        }
        
        IList<Integer> list2 = new DoubleLinkedList<>();
    	for (int i = 0; i < 20; i++) {
    		list2.add(i);
    		list2.add(i);
    		list2.add(i);
        }
    	assertEquals(60, list2.size());

        IList<Integer> top3 = Searcher.topKSort(15, list2);
        assertEquals(15, top3.size());
        for (int i = 0; i < top3.size(); i++) {
            assertEquals(15 + i/3, top3.get(i));
        }
        
        IList<Integer> top4 = Searcher.topKSort(30, list2);
        assertEquals(30, top4.size());
        for (int i = 0; i < top4.size(); i++) {
            assertEquals(10 + i/3, top4.get(i)); 
        }        
    }
    
    @Test(timeout=SECOND)
    public void testEmptyList() {
        IList<Integer> list = new DoubleLinkedList<>();

        IList<Integer> top = Searcher.topKSort(20, list);
        assertEquals(0, top.size());
    }    

    @Test(timeout=SECOND)
    public void testKZero() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(0, list);
        assertEquals(0, top.size());        
    }     
    
    @Test(timeout=SECOND)
    public void testErrorHandling() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        try {
        	Searcher.topKSort(-20, list);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is ok
        }
        
    }    

    @Test(timeout=SECOND)
    public void testInputUnmodify() {
        IList<Integer> list = new DoubleLinkedList<>();
        IList<Integer> compare = new DoubleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
            compare.add(i);
        }
        Searcher.topKSort(5, list);
        for (int i = 0; i < 10; i++) {
        	 assertEquals(list.indexOf(i), compare.indexOf(i));
        }   
        Searcher.topKSort(15, list);
        for (int i = 0; i < 10; i++) {
       	 assertEquals(list.indexOf(i), compare.indexOf(i));
       }          
    }      
}

