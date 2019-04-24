// Qihua Yang (Student ID: 1534960)
// Jun Seok Yeo (Student ID: 1469107)

package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import misc.exceptions.NotYetImplementedException;

import java.util.Arrays;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int length;	// initial array size
    private int size;	// number of elements

    // Feel free to add more fields and constants.

    public ArrayHeap() {
        this.length = 10;
    	this.heap = makeArrayOfT(length);
    	this.size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    /**
     * This method removes the smallest elements inside the data structure
     * @throws EmptyContainerException  if the queue is empty
     */
    public T removeMin() {
    	if (this.isEmpty()) {
    		throw new EmptyContainerException();
    	} 
    	T min = this.peekMin();
    	heap[1] = heap[this.size];
    	heap[this.size] = null;
    	percolateDown(1);
    	size--;
    	return min;
    }

    private void percolateDown(int index) {
    	T min = this.heap[index];
    	int percolateIndex = 0;
    	if (index*4+1 < this.length) {
	    	if (this.heap[index*4-2] != null) {
	    		if (min.compareTo(this.heap[index*4-2]) > 0) {
	    			min = this.heap[index*4-2];
	    			percolateIndex = index*4-2;
	    		}
	    	}
	    	if (this.heap[index*4-1] != null) {
	    		if (min.compareTo(this.heap[index*4-1]) > 0) {
	    			min = this.heap[index*4-1];
	    			percolateIndex = index*4-1;
	    		}   		
	    	}
	    	if (this.heap[index*4] != null) {
	    		if (min.compareTo(this.heap[index*4]) > 0) {
	    			min = this.heap[index*4];
	    			percolateIndex = index*4;
	    		}   		
	    	} 
	    	if (this.heap[index*4+1] != null) {
	    		if (min.compareTo(this.heap[index*4+1]) > 0) {
	    			min = this.heap[index*4+1];
	    			percolateIndex = index*4+1;
	    		} 
	    	}
	    	if (percolateIndex != 0) {
	    		heap[percolateIndex] = heap[index];
	    		heap[index] = min;
	    		percolateDown(percolateIndex);
	    	}  	
    	}
    }
    
    /**
     * This method returns the smallest element without deleting it 
     * @throws EmptyContainerException  if the queue is empty
     */
    public T peekMin() {
    	if (this.isEmpty()) {
    		throw new EmptyContainerException();
    	} 
    	return this.heap[1];
    }

    /**
     * This method puts in an element into the data structure and guarantee the
     * smallest element is at the top
     */
    public void insert(T item) {
        if (item == null) {
        	throw new IllegalArgumentException();
        }
        this.size++;
        resize();
        int newPositionIndex = this.size;
    	this.heap[newPositionIndex] = item;
    	while (newPositionIndex != 1){
    		// new element is smaller than parent
    		if (item.compareTo(this.heap[(newPositionIndex+2)/4]) < 0) { 
    			// parent put to child position
    			this.heap[newPositionIndex] = this.heap[(newPositionIndex+2)/4]; 
    			// child put to parent position
    			this.heap[(newPositionIndex+2)/4] = item;				
    			newPositionIndex = (newPositionIndex+2)/4;
    		} else {
    			// end while loop
    			newPositionIndex = 1;  
    		}		
    	}
        
    }
    
    private void resize() {
    	if (this.size > this.length - 1) {
    		this.length = 2 * this.length;
    		T[] newHeap = this.makeArrayOfT(this.length);
    		for (int i = 1; i < this.size; i++) {
    			newHeap[i] = this.heap[i];
    		}
    		this.heap = newHeap;
    	}
    }
    
    /**
     * This method returns the number of elements inside the data structure
     */
    public int size() {
        return this.size;
    }
}
