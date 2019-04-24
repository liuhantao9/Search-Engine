// Qihua Yang (Student ID: 1534960)
// Jun Seok Yeo (Student ID: 1469107)

package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * See IDictionary for more details on what this class should do
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {

    private KVPair<K, V>[] pairs;
    private int sizeTotal = 5;
    private int size;   
    

    // You're encouraged to add extra fields (and helper methods) though!
    
    /**
     * Constructor: construct a new, empty dictionary of pairs.
     *              set the size of dictionary to zero.
     */
    public ArrayDictionary() {
    	this.pairs = makeArrayOfPairs(this.sizeTotal); // what should be the size?
        this.size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private KVPair<K, V>[] makeArrayOfPairs(int arraySize) {
        // It turns out that creating arrays of generic objects in Java
        // is complicated due to something known as 'type erasure'.
        //
        // We've given you this helper method to help simplify this part of
        // your assignment. Use this helper method as appropriate when
        // implementing the rest of this class.
        //
        // You are not required to understand how this method works, what
        // type erasure is, or how arrays and generics interact. Do not
        // modify this method in any way.
        return (KVPair<K, V>[]) (new KVPair[arraySize]);

    }
    
    /**
     * Helper method: return true if the given key match with
     *                the key in a pair of the given index.
     *                otherwise, return false.
     */
    private boolean isEqual(int index, K key) {
    	if (key != null) {
    		return key.equals(pairs[index].getKey()) || key == pairs[index].getKey();
    	}
    	return key == pairs[index].getKey();
    }

    /**
     * Returns the value corresponding to the given key.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    @Override
    public V get(K key) {
    	for (int i = 0; i < this.size; i++) {
    		if (this.isEqual(i, key)) {
    			return pairs[i].getValue();
    		}
    	}
    	throw new NoSuchKeyException();
    }

    /**
     * Adds the key-value pair to the dictionary. If the key already exists in the dictionary,
     * replace its value with the given one.
     */
    @Override
    public void put(K key, V value) {
    	boolean isKey = false;
    	int i = 0;
    	while (!isKey && i < this.size) {
    		if (this.isEqual(i, key)) {
    			pairs[i] = new KVPair<K, V>(key, value);
    			isKey = true;
    		}
    		i++;
    	}
    	if (!isKey) {
    		if (this.size < this.sizeTotal) {
    			this.pairs[this.size] = new KVPair<K, V>(key, value);
    		} else {
    			this.sizeTotal *= 2;
    			KVPair<K, V>[] temp = makeArrayOfPairs(this.sizeTotal);
    			for (int j = 0; j < this.size; j++) {
    				temp[j] = new KVPair<K, V>(this.pairs[j].getKey(), this.pairs[j].getValue());
    			}
    			this.pairs = temp;
    			this.pairs[this.size] = new KVPair<K, V>(key, value); 
    		}
    		this.size++;
    	}
    }

    /**
     * Remove the key-value pair corresponding to the given key from the dictionary.
     *
     * @throws NoSuchKeyException if the dictionary does not contain the given key.
     */
    @Override
    public V remove(K key) {
    	boolean isKey = false;
    	V temp = null;
    	for (int i = 0; i < this.size; i++) {
    		if (this.isEqual(i, key)) {
    			isKey = true;
    			temp = pairs[i].getValue();
    		}
    		if (isKey && i < this.size - 1) {
    			pairs[i] = new KVPair<K, V>(pairs[i+1].getKey(), pairs[i+1].getValue());
    		}
    	}
    	if (isKey) {
    		this.size--;
    		return temp;
    	} else {
    		throw new NoSuchKeyException();
    	}
    }

    /**
     * Returns 'true' if the dictionary contains the given key and 'false' otherwise.
     */
    @Override
    public boolean containsKey(K key) {
    	for (int i = 0; i < this.size; i++) {
    		if (this.isEqual(i, key)) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Returns the number of key-value pairs stored in this dictionary.
     */
    @Override
    public int size() {
    	return this.size;
    }

    /**
     * construct Pair<K,V> to store key-value pairs.
     */
    private static class Pair<K, V> {
        public K key;
        public V value;

        // You may add constructors and methods to this class as necessary.
        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
    /**
     * Returns a list of all key-value pairs contained within this dict.
    */
    public Iterator<KVPair<K, V>> iterator(){
    	return new ADIterator<K, V>(this.pairs);
    }

    private static class ADIterator<K, V> implements Iterator<KVPair<K, V>> {
        private KVPair<K, V>[] pairs;
        private int index;

        public ADIterator(KVPair<K, V>[] pairs) {
            this.pairs = pairs;
            this.index = 0;
        }

        public boolean hasNext() {
            if (pairs.length == 0) {
            	return false;
            } else if (index >= pairs.length){
            	return false;
            } else if (pairs[index] == null){
            	return false;
            }
            return true;
        }

        @Override
        public KVPair<K, V> next() {
            if (!hasNext()) {
            	throw new NoSuchElementException(); 
            } else {
            	index++;
            	return pairs[index-1];
            }
            	
        }
    } 
}
  

