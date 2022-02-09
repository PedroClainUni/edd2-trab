package edd2_trab2.structures;

import java.util.Vector;

/**
 *
 * @author Pedro Henrique Clain
 */
public class HashTable<Key extends Comparable, Value> {
    
    private Vector<LinkedList<Key, Value>> vector;
    private int size;
    
    public HashTable(int size) {
        this.vector = new Vector(size);
        this.size = size;
        this.vector.forEach(list -> {
            list = new LinkedList();
        });
    }
    
    private int hash(Key key) {
        return key.hashCode();
    }
    
    public void put(Key key, Value value) {
        int pos = this.hash(key);
        LinkedList<Key, Value> list = this.vector.get(pos);
        
        if (list == null) list = new LinkedList<>();
        
        list.add(key, value);
    }
    
    public void delete(Key key) {
        int pos = this.hash(key);
        this.vector.remove(pos);
    }
    
    public Value get(Key key) {
        int pos = this.hash(key);
        LinkedList<Key, Value> list = this.vector.get(pos);
        return list.first();
    }
}


