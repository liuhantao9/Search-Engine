package datastructures.concrete;

import datastructures.interfaces.IList;
import misc.exceptions.EmptyContainerException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Note: For more info on the expected behavior of your methods, see
 * the source code for IList.
 */
public class DoubleLinkedList<T> implements IList<T> {
    // You may not rename these fields or change their types.
    // We will be inspecting these in our private tests.
    // You also may not add any additional fields.
    private Node<T> front;
    private Node<T> back;
    private int size;
    
    /**
     * Constructor: initialize front and back to null.
     *              set the size of list to zero
     */
    public DoubleLinkedList() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }
    
    /**
     * Adds the given item to the *end* of this IList.
     */
    @Override
    public void add(T item) {
    	Node<T> temp = new Node<T>(null, item, null);
    	if (this.front == null || this.back == null) {
    		this.front = temp;
    	}
    	if (this.front != null && this.back != null) {
    		temp.prev = this.back;
    		this.back.next = temp;
    	}
    	this.back = temp;
    	this.size++;
    }
    
    /**
     * Removes and returns the item from the *end* of this IList.
     *
     * @throws EmptyContainerException if the container is empty and there is no element to remove.
     */
    @Override
    public T remove() {
        T val;
        if (this.front == null || this.back == null) {
        	throw new EmptyContainerException();
        } else if (this.front == this.back) {
        	val = this.back.data;
        	this.front = null;
        	this.back = null;
        } else {
        	val = this.back.data;
        	this.back = this.back.prev;
        	this.back.next = null;
        }
        this.size--;
        return val;
    }
    
    /**
     * Returns the item located at the given index.
     *
     * @throws IndexOutOfBoundsException if the index < 0 or index >= this.size()
     */
    @Override
    public T get(int index) {
    	if (index < 0 || index >= this.size) {
    		throw new IndexOutOfBoundsException();
    	}
    	Node<T> current = this.front;
    	T flag = current.data;
    	for (int i = 0; i <= index; i++) {
    		flag = current.data;
    		current = current.next;
    	}
    	return flag;
    }
    
    /**
     * Overwrites the element located at the given index with the new item.
     *
     * @throws IndexOutOfBoundsException if the index < 0 or index >= this.size()
     */
    @Override
    public void set(int index, T item) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException();
		} else {
			Node<T> current = this.front;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
			Node<T> prevNode = current.prev;
			Node<T> nextNode = current.next;
			Node<T> temp = new Node<T>(prevNode, item, nextNode);
			if (prevNode != null) {
				prevNode.next = temp;
			} else {
				front = temp;
			}
			if (nextNode != null) {
				nextNode.prev = temp;
			} else {
				back = temp;
			}
		}
	}
    
    /**
     * Inserts the given item at the given index. If there already exists an element
     * at that index, shift over that element and any subsequent elements one index
     * higher.
     *
     * @throws IndexOutOfBoundsException if the index < 0 or index >= this.size() + 1
     */
    @Override
    public void insert(int index, T item) {
		if (index < 0 || index >= this.size + 1) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> temp = new Node<T>(null, item, null); 
		if (index == this.size) {
			this.add(item);
			this.size--;     	
		} else if (index == 0) {
			temp.next = this.front;
			this.front.prev = temp;
			this.front = temp;
		} else {
			Node <T> current = currentToIndex(index);
			temp.prev = current.prev;
			temp.prev.next = temp;
			temp.next = current;
			current.prev = temp;
		}    
		this.size++;
    }
    
    /**
     * Deletes the item at the given index. If there are any elements located at a higher
     * index, shift them all down by one.
     *
     * @throws IndexOutOfBoundsException if the index < 0 or index >= this.size()
     */
    @Override
    public T delete(int index) {
    	if (index < 0 || index >= this.size) {
    		throw new IndexOutOfBoundsException();
    	} 
    	T temp = null;
    	if (index == this.size - 1) {
    		temp = this.remove();
    		this.size++;     	
    	} else if (index == 0) {
    		Node<T> current = this.front;
    		temp = this.front.data;
    		this.front = this.front.next;
    		current.next = null;
    		this.front.prev = null;
    	} else {
    		Node <T> current = currentToIndex(index);
	       	temp = current.data;
       		current.prev.next = current.next;
       		current.next.prev = current.prev;
    	}
    	this.size--;
    	return temp;
    }
    
    /**
     * Helper method: return to Node <T> current pointing on
     *                a node of the given index.
     */
    private Node<T> currentToIndex(int index) {
    	Node<T> current;
    	if (index < this.size/2) {
    		current = this.front;
			for (int i = 0; i < index; i++) {
	       		current = current.next;
	       	}
		} else {
			current = this.back;
			for (int i = this.size - 1; i > index; i--) {
				current = current.prev;
			}
		}
    	return current;
    }
    
    /**
     * Returns the index corresponding to the first occurrence of the given item
     * in the list.
     *
     * If the item does not exist in the list, return -1.
     */
    @Override
    public int indexOf(T item) {
    	Iterator<T> iter = this.iterator();
    	int index = 0;
    	while (iter.hasNext()) {
    	    T dat = iter.next();
    	    if (dat == item || dat.equals(item)) {
    	    	return index;
    	    }
    	    index++;
    	}
    	return -1;
    	
    }

    /**
     * Returns the number of elements in the container.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns 'true' if this container contains the given element, and 'false' otherwise.
     */
    @Override
    public boolean contains(T other) {
    	Iterator<T> iter = this.iterator();
    	while (iter.hasNext()) {
    	    T dat = iter.next();
    	    if (dat == other || dat.equals(other)) {
    	    	return true;
    	    }
    	}
    	return false;
    }

    /**
     * Returns an iterator over the contents of this list.
     */
    @Override
    public Iterator<T> iterator() {
        // Note: we have provided a part of the implementation of
        // an iterator for you. You should complete the methods stubs
        // in the DoubleLinkedListIterator inner class at the bottom
        // of this file. You do not need to change this method.
        return new DoubleLinkedListIterator<>(this.front);
    }

    public static class Node<E> {
        // You may not change the fields in this node or add any new fields.
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        public Node(E data) {
            this(null, data, null);
        }

        // Feel free to add additional constructors or methods to this class.
    }

    private static class DoubleLinkedListIterator<T> implements Iterator<T> {
        // You should not need to change this field, or add any new fields.
        private Node<T> current;

        public DoubleLinkedListIterator(Node<T> current) {
            // You do not need to make any changes to this constructor.
            this.current = current;
        }

        /**
         * Returns 'true' if the iterator still has elements to look at;
         * returns 'false' otherwise.
         */
        public boolean hasNext() {
        	if (current == null) {
        		return false;
        	}
        	return current != null;
        }

        /**
         * Returns the next item in the iteration and internally updates the
         * iterator to advance one element forward.
         *
         * @throws NoSuchElementException if we have reached the end of the iteration and
         *         there are no more elements to look at.
         */
        public T next() {
        	if (!hasNext()) {
        		throw new NoSuchElementException(); 
        	} else {
        		T record = current.data;
        		current = current.next;
        		return record;
        	}
        }
    }
}
