package edd2_trab2.structures;

import java.util.Vector;
import java.util.LinkedList;

/**
 *
 * @author Pedro Henrique Clain
 * @param <Key>
 * @param <Value>
 */
public class HashTable<Key extends Comparable, Value> {
    
    private final Vector<CustomLinkedList<Key, Value>> vector;
    private final int size;
    
    public HashTable(int size) {
        this.vector = new Vector<>();
        this.vector.setSize(size);
        this.size = size;
        this.vector.forEach(list -> {
            list = new CustomLinkedList();
        });
    }
    
    private int hash(Key key) {
        int hashCode = key.hashCode();
        if(hashCode < 0){
            hashCode *= -1;
        }
        int response = hashCode % size;
        return response;
    }
    
    public void put(Key key, Value value) {
        int pos = this.hash(key);
        CustomLinkedList<Key, Value> list = this.vector.get(pos);
        
        if (list == null) list = new CustomLinkedList<>();
        
        list.add(key, value);
        this.vector.set(pos, list);
    }
    
    public void delete(Key key) {
        int pos = this.hash(key);
        this.vector.remove(pos);
    }
    
    public Value get(Key key) {
        int pos = this.hash(key);
        CustomLinkedList<Key, Value> list = this.vector.get(pos);
        if (list == null) return null;
        else return list.get(key);
    }
    
    public LinkedList<Key> keys() {
      LinkedList<Key> keys = new LinkedList<>();
      for (CustomLinkedList list : this.vector) {
        if (list != null) {
          keys.addAll(list.keys());
        }        
      }
      return keys;
    }
    
    public int size() {
      return this.size;
    }
}


