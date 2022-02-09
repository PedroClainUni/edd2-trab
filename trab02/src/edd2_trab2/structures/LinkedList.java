package edd2_trab2.structures;

/**
 *
 * @author Pedro Henrique Clain
 * @param <Key>
 * @param <Value>
 */
public class LinkedList<Key extends Comparable, Value> {
    
    private Node first;
    
    class Node<Key extends Comparable, Value> {
        private Key key;
        private Value value;
        private Node next;
        
        Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
        
        Node() {
            
        }
    }
    
    public boolean isEmpty() {
        return this.first == null;
    }
    
    public void add(Key key, Value value) {
        if (value == null) {
            delete(key);
            return;
        }
        Node newNode = new Node(key, value);
        if (isEmpty()) {
            this.first = newNode;
        } else {
            Node node = this.first;
            do {
                if (node.key.compareTo(key) == 0) {
                    node.value = value;
                    return;
                }
                node = node.next;
            } while(node.next != null);
            
            node.next = newNode;      
        }
    }
    
    public Value get(Key key) {
        Node node = this.first;
        while(node != null) {
            if (node.key.compareTo(key) == 0) {
                return (Value) node.value;
            }
            node = node.next;
        }
        return null;        
    }
    
    public void delete(Key key) {
        Node node = this.first;
        if (isEmpty()) return;
        if (this.first.key.compareTo(key) == 0) {
            this.first = this.first.next;
        } else {
            do {
                if (node.next.key.compareTo(key) == 0) {
                    node.next = node.next.next;
                }
                node = node.next;
            } while(node.next != null);
        }           
    }
    
    public Value first() {
        return (Value) this.first.value;
    }
    
    public int size() {
        int i=0;
        Node node = this.first;
        while(node != null) {
            i++;
        }
        return i;
    }
}
