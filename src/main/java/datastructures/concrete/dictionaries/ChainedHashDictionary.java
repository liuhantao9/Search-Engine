// Qihua Yang (Student ID: 1534960)
// Jun Seok Yeo (Student ID: 1469107)

package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See the spec and IDictionary for more details on what each method should do
 */
public class ChainedHashDictionary<K, V> implements IDictionary<K, V> {
    // You may not change or rename this field: we will be inspecting
    // it using our private tests.
    private IDictionary<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!
    private int pairSize;
    private int arraySize;
    private int primeIndex;
    private int[] primes = {5, 11, 23, 47, 97, 193, 353, 709, 1471, 2833,
    		                     5693, 11057, 22147, 50227, 
    		                     100003, 200009, 400031, 800077,
    		                     1600121, 3200453, 6401117, 12800009,
    		                     25000009, 50000017, 100000007,
    		                     200000033, 400000009, 800000011,
    		                     1600000009};
    
    public ChainedHashDictionary() {
    	this.pairSize = 0;
    	this.primeIndex = 0;
    	this.arraySize = this.primes[this.primeIndex];
    	this.chains = this.makeArrayOfChains(this.arraySize);
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain IDictionary<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private IDictionary<K, V>[] makeArrayOfChains(int size) {
        // Note: You do not need to modify this method.
        // See ArrayDictionary's makeArrayOfPairs(...) method for
        // more background on why we need this method.
        return (IDictionary<K, V>[]) new IDictionary[size];
    }

    /**
     * Returns the value corresponding to the given key.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    @Override
    public V get(K key) {
    	int hashNum = getHashNum(key);
    	if (this.chains[hashNum] == null) {
    		throw new NoSuchKeyException();
    	}
        return this.chains[hashNum].get(key);
    }

    /**
     * Adds the key-value pair to the dictionary. If the key already exists in the dictionary,
     * replace its value with the given one.
     */
    @Override
    public void put(K key, V value) {
    	int hashNum = getHashNum(key);
    	if (this.chains[hashNum] == null) {
    		this.chains[hashNum] = new ArrayDictionary<K, V>();
    	}
    	int originalSize = this.chains[hashNum].size(); 
        this.chains[hashNum].put(key, value);
        if (this.chains[hashNum].size() > originalSize) {
        	this.pairSize++;
        }
    	if (this.pairSize/this.arraySize > 0.75) {
    		resizeArray();
    	}
    }
    
    private void resizeArray() {
    	this.primeIndex++;
		if (this.primeIndex < this.primes.length) {
			this.arraySize = this.primes[this.primeIndex];
		} else {
			this.arraySize = this.arraySize * 3;
		}
		IDictionary<K, V>[] newChains = this.makeArrayOfChains(this.arraySize);
		for (int i = 0; i < this.chains.length; i++) {
			if (this.chains[i] != null) {
				for (KVPair<K, V> pair : this.chains[i]) {
					int hashNum = getHashNum(pair.getKey());
					if (newChains[hashNum] == null) {
						newChains[hashNum] = new ArrayDictionary<K, V>();
			    	}
					newChains[hashNum].put(pair.getKey(), pair.getValue());
				}
			}
		}
		this.chains = newChains;
    }

    /**
     * Remove the key-value pair corresponding to the given key from the dictionary.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    @Override
    public V remove(K key) {
    	int hashNum = getHashNum(key);
    	if (this.chains[hashNum] == null || !this.chains[hashNum].containsKey(key)) {
    		throw new NoSuchKeyException();
    	}
    	this.pairSize--; 
    	return this.chains[hashNum].remove(key);
    }

    /**
     * Returns 'true' if the dictionary contains the given key and 'false' otherwise.
     */
    @Override
    public boolean containsKey(K key) {
    	int hashNum = getHashNum(key);
    	return this.chains[hashNum] != null && this.chains[hashNum].containsKey(key);
    }

    /**
     * Returns the number of key-value pairs stored in this dictionary.
     */
    @Override
    public int size() {
        return this.pairSize;
    }
    
    private int getHashNum(K key) {
    	int hashNum = 0;
    	if (key != null) {
    		hashNum = Math.abs(key.hashCode()%this.arraySize);
    	}    
    	return hashNum;
    }

    /**
     * Returns a list of all key-value pairs contained within this dict.
     */
    @Override
    public Iterator<KVPair<K, V>> iterator() {
        // Note: you do not need to change this method
        return new ChainedIterator<>(this.chains);
    }

    /**
     * Hints:
     *
     * 1. You should add extra fields to keep track of your iteration
     *    state. You can add as many fields as you want. If it helps,
     *    our reference implementation uses three (including the one we
     *    gave you).
     *
     * 2. Before you try and write code, try designing an algorithm
     *    using pencil and paper and run through a few examples by hand.
     *
     * 3. Think about what exactly your *invariants* are. An *invariant*
     *    is something that must *always* be true once the constructor is
     *    done setting up the class AND must *always* be true both before and
     *    after you call any method in your class.
     *
     *    Once you've decided, write them down in a comment somewhere to
     *    help you remember.
     *
     *    You may also find it useful to write a helper method that checks
     *    your invariants and throws an exception if they're violated.
     *    You can then call this helper method at the start and end of each
     *    method if you're running into issues while debugging.
     *
     *    (Be sure to delete this method once your iterator is fully working.)
     *
     * Implementation restrictions:
     *
     * 1. You **MAY NOT** create any new data structures. Iterators
     *    are meant to be lightweight and so should not be copying
     *    the data contained in your dictionary to some other data
     *    structure.
     *
     * 2. You **MAY** call the `.iterator()` method on each IDictionary
     *    instance inside your 'chains' array, however.
     */
    private static class ChainedIterator<K, V> implements Iterator<KVPair<K, V>> {
        private IDictionary<K, V>[] chains;
        private Iterator<KVPair<K, V>> dictIter;
        private int index;

        public ChainedIterator(IDictionary<K, V>[] chains) {
            this.chains = chains;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
        	while (this.index < this.chains.length) {
        		if (this.chains[this.index] == null) {
        			this.index++;
        		} else {
        			if (this.dictIter == null) {
        				this.dictIter = this.chains[index].iterator();
        			} else if (!this.dictIter.hasNext()){
        				this.index++;
        				this.dictIter = null;
        			} else {
        				return true;
        			}
        		}
        	}
        	return false;
        }

        @Override
        public KVPair<K, V> next() {
        	if (!hasNext()) {
        		throw new NoSuchElementException();
        	} else {
        		return this.dictIter.next();
        	}
        }
    }
}
