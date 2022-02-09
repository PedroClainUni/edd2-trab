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

    //construtor da classe, recebe o tamanho que será utilizado na hashTable
    public HashTable(int size) {
        this.vector = new Vector<>();
        this.vector.setSize(size);
        this.size = size;
        this.vector.forEach(list -> {
            list = new CustomLinkedList();
        });
    }

    //função hash, recebe o texto e converte em um codigo hash, trata então por módulo para que o resultado sempre fique 0 < hash < size
    private int hash(Key key) {
        int hashCode = key.hashCode();
        if(hashCode < 0){
            hashCode *= -1;
        }
        int response = hashCode % size;
        return response;
    }

    //Método para adicionar uma key associada a um valor na tablea hash, caso o hash retornado seja o mesmo de uma key existente ele vai colocar numa lista encadeada na mesma posição da primeira e salvará com sua key
    public void put(Key key, Value value) {
        int pos = this.hash(key);
        CustomLinkedList<Key, Value> list = this.vector.get(pos);
        
        if (list == null) list = new CustomLinkedList<>();
        
        list.add(key, value);
        this.vector.set(pos, list);
    }

    //Método que busca por uma key na hashTable e caso encontre dela o mesmo
    public void delete(Key key) {
        int pos = this.hash(key);
        this.vector.remove(pos);
    }

    //Método que busca por uma key na hashTable e quando encontra retorna o seu valor
    public Value get(Key key) {
        int pos = this.hash(key);
        CustomLinkedList<Key, Value> list = this.vector.get(pos);
        if (list == null) return null;
        else return list.get(key);
    }

    //Método que retorna as chaves de uma hashTable no formato de uma lista encadeada
    public LinkedList<Key> keys() {
      LinkedList<Key> keys = new LinkedList<>();
      for (CustomLinkedList list : this.vector) {
        if (list != null) {
          keys.addAll(list.keys());
        }        
      }
      return keys;
    }

    //Método que retorna o tamanho de uma hashTable
    public int size() {
      return this.size;
    }
}


