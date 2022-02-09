package edd2_trab2.structures;

import java.util.LinkedList;

/**
 *
 * @author Pedro Henrique Clain
 * @param <Key>
 * @param <Value>
 */
public class CustomLinkedList<Key extends Comparable, Value> {

  //primeiro Nodo da lista encadeada, semelhante a raiz de uma árvore binária
  private Node first;

  class Node<Key extends Comparable, Value> {

    private Key key; //valor pelo qual um nodo pode ser indentificado, não existem dois nodos de mesma key
    private Value value; //valor associado a uma key, duas keys distintas podem ter um valor igual (no ponto de vista de lista encadeada e não do hashTable)
    private Node next; //próximo nodo do encadeamento


    //Construtor da classe Node, recebendo os atributos de key e value
    Node(Key key, Value value) {
      this.key = key;
      this.value = value;
    }

    Node() {

    }
  }

  //método que retorna em booleano para quando a lista encadeada está vazia
  public boolean isEmpty() {
    return this.first == null;
  }

  //método para adicionar um nó na lista encadeada, caso o value seja null então será utilizado o método de delete na key passada
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
      while (node.next != null) {
        if (node.next.key.compareTo(key) == 0) {
          node.next.value = value;
          return;
        }
        node = node.next;
      }
      node.next = newNode;
    }
  }
//busca por um nó na lista, quando encontra a sua key ele retorna o value do nodo
  public Value get(Key key) {
    Node node = this.first;
    while (node != null) {
      if (node.key.compareTo(key) == 0) {
        return (Value) node.value;
      }
      node = node.next;
    }
    return null;
  }

  //busca por um nó na lista, quando encontra ele deleta o nó da lista, associando o anterior dele ao seu posterior
  public void delete(Key key) {
    Node node = this.first;
    if (isEmpty()) {
      return;
    }
    if (this.first.key.compareTo(key) == 0) {
      this.first = this.first.next;
    } else {
      while (node.next != null) {
        if (node.next.key.compareTo(key) == 0) {
          node.next = node.next.next;
          return;
        }
        node = node.next;
      }
    }
  }

  //retorna o valor do nodo inicial da lista encadeada
  public Value first() {
    return (Value) this.first.value;
  }

  //retorna a quantidade de itens na lista encadeada
  public int size() {
    int i = 0;
    Node node = this.first;
    while (node != null) {
      i++;
    }
    return i;
  }

  //retorna as chaves de uma lista encadeada no formato de outra lista encadeada
  public LinkedList<Comparable> keys() {
    Node node = this.first;
    LinkedList<Comparable> keys = new LinkedList<Comparable>();
    while (node != null) {
      keys.add(node.key);
      node = node.next;
    }
    return keys;
  }
}
