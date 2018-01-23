package datastructures.concrete.dictionaries;

import datastructures.concrete.KVPair;
import datastructures.interfaces.IDictionary;
import misc.exceptions.NoSuchKeyException;
import misc.exceptions.NotYetImplementedException;

import java.util.Iterator;

/**
 * TODO: Replace this file with the one you wrote from project 1
 * TODO: Add the missing "iterator()" method
 */
public class ArrayDictionary<K, V> implements IDictionary<K, V> {
	private Pair<K, V>[] pairs;

    // You're encouraged to add extra fields (and helper methods) though!

    private int size;

    public ArrayDictionary() {
        pairs = makeArrayOfPairs(10);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * Pair<K, V> objects.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private Pair<K, V>[] makeArrayOfPairs(int arraySize) {
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
        return (Pair<K, V>[]) (new Pair[arraySize]);

    }

    @Override
    public V get(K key) {
        for (int i = 0; i < size; i++) {
            if (isSameKey(pairs[i].key, key)) {
                return pairs[i].value;
            }
        }
        throw new NoSuchKeyException();
    }

    @Override
    public void put(K key, V value) {
        int index = getIndexOfKey(key);
        if (index > -1) {
            pairs[index].value = value;
        } else {
            if (size == pairs.length) {
                pairs = doubleArrayCapacity();
            }
            pairs[size] = new Pair<K, V>(key, value);
            size++;
        }
    }

    private Pair<K, V>[] doubleArrayCapacity() {
        Pair<K, V>[] result = makeArrayOfPairs(2 * pairs.length);
        for (int i = 0; i < size; i++) {
            result[i] = pairs[i];
        }
        return result;
    }

    @Override
    public V remove(K key) {
        int index = getIndexOfKey(key);
        if (index > -1) {
            V removed = pairs[index].value;
            for (int i = index; i < size - 1; i++) {
                pairs[i] = pairs[i + 1];
            }
            size--;
            return removed;
        } else {
            throw new NoSuchKeyException();
        }
    }

    private int getIndexOfKey(K key) {
        for (int i = 0; i < size; i++) {
            if (isSameKey(pairs[i].key, key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsKey(K key) {
        for (int i = 0; i < size; i++) {
            if (isSameKey(pairs[i].key, key)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSameKey(K key1, K key2) {
        return key1 == key2 || key1 != null && key1.equals(key2);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<KVPair<K, V>> iterator() {
        throw new NotYetImplementedException();
    }

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
}
