// Qihua Yang (Student ID: 1534960)
// Jun Seok Yeo (Student ID: 1469107)

package datastructures.concrete;

import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.ISet;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See ISet for more details on what each method is supposed to do.
 */
public class ChainedHashSet<T> implements ISet<T> {
    // This should be the only field you need
    private IDictionary<T, Boolean> map;

    public ChainedHashSet() {
        this.map = new ChainedHashDictionary<>();
    }

    /**
     * Adds the given item to the set.
     *
     * If the item already exists in the set, this method does nothing.
     */
    @Override
    public void add(T item) {
    	if (!this.map.containsKey(item)) {
        	this.map.put(item, true);
    	}
    }

    /**
     * Removes the given item from the set.
     *
     * @throws NoSuchElementException  if the set does not contain the given item
     */
    @Override
    public void remove(T item) {
    	if (!this.map.containsKey(item)) {
    		throw new NoSuchElementException();
    	}
    	this.map.remove(item);
    }

    /**
     * Returns 'true' if the set contains this item and false otherwise.
     */
    @Override
    public boolean contains(T item) {
    	return this.map.containsKey(item);
    }

    /**
     * Returns the number of items contained within this set.
     */
    @Override
    public int size() {
    	return this.map.size();
    }

    /**
     * Returns all items contained within this set.
     */
    @Override
    public Iterator<T> iterator() {
        return new SetIterator<>(this.map.iterator());
    }

    private static class SetIterator<T> implements Iterator<T> {
        private Iterator<KVPair<T, Boolean>> iter;

        public SetIterator(Iterator<KVPair<T, Boolean>> iter) {
            this.iter = iter;
        }

        @Override
        public boolean hasNext() {
        	return iter.hasNext();
        }

        @Override
        public T next() {
        	return iter.next().getKey();
        }
    }
}
