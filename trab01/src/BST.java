
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * The {@code BST} class represents an ordered symbol table of generic
 * key-value pairs.
 * It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 * <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 * It also provides ordered methods for finding the <em>minimum</em>,
 * <em>maximum</em>, <em>floor</em>, <em>select</em>, <em>ceiling</em>.
 * It also provides a <em>keys</em> method for iterating over all of the keys.
 * A symbol table implements the <em>associative array</em> abstraction:
 * when associating a value with a key that is already in the symbol table,
 * the convention is to replace the old value with the new value.
 * Unlike {@link java.util.Map}, this class uses the convention that
 * values cannot be {@code null}—setting the
 * value associated with a key to {@code null} is equivalent to deleting the key
 * from the symbol table.
 * <p>
 * It requires that
 * the key type implements the {@code Comparable} interface and calls the
 * {@code compareTo()} and method to compare two keys. It does not call either
 * {@code equals()} or {@code hashCode()}.
 * <p>
 * This implementation uses an (unbalanced) <em>binary search tree</em>.
 * The <em>put</em>, <em>contains</em>, <em>remove</em>, <em>minimum</em>,
 * <em>maximum</em>, <em>ceiling</em>, <em>floor</em>, <em>select</em>, and
 * <em>rank</em>  operations each take &Theta;(<em>n</em>) time in the worst
 * case, where <em>n</em> is the number of key-value pairs.
 * The <em>size</em> and <em>is-empty</em> operations take &Theta;(1) time.
 * The keys method takes &Theta;(<em>n</em>) time in the worst case.
 * Construction takes &Theta;(1) time.
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/32bst">Section 3.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST
    private PrintStream log = System.out;
    private int i = 0;

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BST() {
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }


    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        assert check();
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }


    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        else return max(x.right);
    }

    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    public Key floor2(Key key) {
        Key x = floor2(root, key, null);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x;

    }

    private Key floor2(Node x, Key key, Key best) {
        if (x == null) return best;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return floor2(x.left, key, best);
        else if (cmp > 0) return floor2(x.right, key, x.key);
        else return x.key;
    }

    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too large");
        else return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) {
            Node t = ceiling(x.left, key);
            if (t != null) return t;
            else return x;
        }
        return ceiling(x.right, key);
    }

    /**
     * Return the key in the symbol table of a given {@code rank}.
     * This key has the property that there are {@code rank} keys in
     * the symbol table that are smaller. In other words, this key is the
     * ({@code rank}+1)st smallest key in the symbol table.
     *
     * @param rank the order statistic
     * @return the key in the symbol table of given {@code rank}
     * @throws IllegalArgumentException unless {@code rank} is between 0 and
     *                                  <em>n</em>–1
     */
    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private Key select(Node x, int rank) {
        if (x == null) return null;
        int leftSize = size(x.left);
        if (leftSize > rank) return select(x.left, rank);
        else if (leftSize < rank) return select(x.right, rank - leftSize - 1);
        else return x.key;
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }

    // Number of keys in the subtree less than key.
    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }

    /**
     * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<Key> keys() {
        if (isEmpty()) return new ArrayDeque<>();
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new ArrayDeque<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.add(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in the symbol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    /**
     * Returns the height of the BST (for debugging).
     *
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new ArrayDeque<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node x = queue.peek();
            if (x == null) continue;
            keys.add(x.key);
            queue.add(x.left);
            queue.add(x.right);
        }
        return keys;
    }

    /*************************************************************************
     *  Check integrity of BST data structure.
     ***************************************************************************/
    private boolean check() {
        if (!isBST()) System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
        if (!isRankConsistent()) System.out.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

   private boolean isBST() {
        return isBST(root, null, null);
    }


    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }


    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    /***
     **************************************************************************
     * Implementação dos métodos requeridos no trabalho.
     *
     * @autor Pedro Henrique Clain
     * @autor Thiago Silva
     **************************************************************************
     */

    /***
     * Gera uma BinarySearchTree com determinada ordem de inserção.
     *
     * @return BST com 6 chaves.
     */
    public static BST<String, Integer> generateType01() {
        BST<String, Integer> st = new BST<String, Integer>();
        st.put("b", 2);
        st.put("a", 1);
        st.put("c", 3);
        st.put("d", 2);
        st.put("e", 1);
        st.put("f", 3);
        return st;
    }

    /***
     * Gera uma BinarySearchTree com determinada ordem de inserção.
     *
     * @return BST com 6 chaves.
     */
    public static BST<String, Integer> generateType02() {
        BST<String, Integer> st = new BST<String, Integer>();
        st.put("a", 1);
        st.put("f", 3);
        st.put("c", 3);
        st.put("b", 2);
        st.put("e", 1);
        st.put("d", 2);
        return st;
    }

    /***
     * Rotaciona uma subarvore para a esquerda.
     *
     * Método de rotação consiste em a direita do nó passado no argumento passar a apontar para o filho esquerdo
     * do nó direito e então esse antigo nó direito apontar para o nó passdo no argumento, então esse
     * antigo nó direito passará a ser a raíz dessa sub-árvore. O método também, atualiza os valores do atributo
     * size, uma vez que o método de rotate irá moodificar tal valor.
     *
     * @param node Nó que será rotacionado à esquerda.
     * @return Novo root da subarvore.
     * @throw IllegalArgumentException Se nó é {@code null} ou subarvore da
     * direita do nó é {@code null}.
     */
    private Node rotateLeft(Node node) {
        if (node == null) throw new IllegalArgumentException("Node cannot be null.");
        if (node.right == null) throw new IllegalArgumentException("Node has no right subtree.");
        Node rightNode = node.right;
        int rightRightSize = size(rightNode.right);
        int rightLeftSize = size(rightNode.left);
        int leftSize = size(node.left);
        node.size = leftSize + rightLeftSize + 1;
        rightNode.size = node.size + rightRightSize + 1;
        node.right = rightNode.left;
        rightNode.left = node;
        return rightNode;
    }

    /***
     * Rotaciona uma subarvore para a direita.
     *
     * Método de rotação consiste em a esquerda do nó passado no argumento passar a apontar para o filho direito
     * do nó esquerdo e então esse antigo nó esquerdo apontar para o nó passado no argumento, então esse antigo
     * nó esquerdo passará a ser a raíz dessa sub-árvore. O método também, atualiza os valores do atributo size,
     * uma vez que o método de rotate irá moodificar tal valor.
     *
     * @param node Nó que será rotacionado à direita.
     * @return Novo root da subarvore.
     * @throw IllegalArgumentException Se nó é {@code null} ou subarvore da
     * esquerda do nó é {@code null}.
     */
    private Node rotateRight(Node node) {
        if (node == null) throw new IllegalArgumentException("Node cannot be null.");
        if (node.left == null) throw new IllegalArgumentException("Node has no left subtree.");
        Node leftNode = node.left;
        int leftRightSize = size(leftNode.right);
        int leftLeftSize = size(leftNode.left);
        int rightSize = size(node.right);
        node.size = rightSize + leftRightSize + 1;
        leftNode.size = node.size + leftLeftSize + 1;
        node.left = leftNode.right;
        leftNode.right = node;
        return leftNode;
    }

    /***
     * Verifica se uma BST possui as mesmas chaves que outra.
     *
     * @param bst árvore que será comparada com a árvore atual.
     * @return {@code true} se possuem as mesmas chaves, se não, {@code false}.
     * @throws IllegalArgumentException caso as árvores não possuam o mesmo tamanho.
     */
    public boolean hasSameKeys(BST<Key, Value> bst) {
        if (size() != bst.size()) throw new IllegalArgumentException("Trees not have same size.");
        return hasSameKeys(bst.root);
    }

    /***
     * O algoritmo irá procurar cada chave da árvore modelo, usando o método {@code contains()}, na árvore atual.
     * Passo 1 - O nó será a raiz da árvore modelo;
     * Passo 2 - Busca a chave desse nó na árvore atual;
     * Passo 3 - Caso não encontre a chave na árvore atual retorna {@code false}
     * Complexidade 0(n²).
     *
     * @param node Nó atual da recursão.
     * @return Se não encontrou uma chave retorna {@code false}, caso contrário, {@code true}.
     */
    private boolean hasSameKeys(Node node) {
        if (node == null) return true;
        if (!contains(node.key)) return false;
        return hasSameKeys(node.right) && hasSameKeys(node.left);
    }

    /***
     * Transforma a árvore atual em outra rotacionando cada nó.
     *
     * @param bst Árvore modelo.
     * @throws IllegalArgumentException Caso as árvores não possuam as mesmas chaves.
     */
    public void transform(BST<Key, Value> bst) {
        if (!hasSameKeys(bst)) throw new IllegalArgumentException("Trees not have same keys.");
        i = 0;
        this.root = transform(root, bst);
        i = 0;
        check();
        bst.check();
    }

    /***
     * O algoritmo irá percorrer a árvore. Em cada chamada recursiva ele irá buscar um nó na árvore modelo, percorrendo
     * a mesma de forma pré fixada. O objetivo é passar por todos os nós da árvore modelo, trocando cada nó da árvore atual.
     * Esse algoritmo funciona pois a árvore atual será reconstruida de cima para baixo sempre espelhando a árvore modelo.
     * Complexidade O(n²).
     *
     * Passo 1 - Busca um nó da árvore modelo na posição 0 pré fixada;
     * Passo 2 - Incrementa {@code i} para prosseguir na árvore modelo;
     * Passo 3 - Troca o {@code node} atual para um nó que possua a mesma chave de do nó encontrado na árvore modelo, {@code tmp};
     * Passo 4 - Após a troca de nós, continua percorrendo a árvore atual de forma pré fixada.
     *
     * @param node Nó atual da recursão.
     * @param bst Árvore modelo.
     * @return Novo {@code root} de uma subarvore.
     */
    private Node transform(Node node, BST<Key, Value> bst) {
        if (node == null) return null;
        Node tmp = getByIndex(bst.root, i);
        i++;
        node = rotateRoot(node, tmp);
        node.left = transform(node.left, bst);
        node.right = transform(node.right, bst);
        return node;
    }

    /***
     * Retorna um nó dado sua posição pré fixada.
     *
     * @param node Nó raiz de uma subarvore.
     * @param index Posição pré fixada na subarvore.
     * @return Nó correspondente àquela posição.
     */
    public Node getByIndex(Node node, int index) {
        if (index < 0) return null;
        int tmp = i;
        i = 0;
        Node res = findByIndex(node, index);
        i = tmp;
        return res;
    }

    /***
     * O algoritmo utilizará uma variável global auxiliar {@code i} como contador para acumular cada chamada recursiva.
     * Uma vez que {@code i} for igual à {@code index} será retornado o nó atual da recursão.
     *
     * @param node Nó atual da recursão.
     * @param index Posição pré fixada.
     * @return Nó correspondente.
     */
    private Node findByIndex(Node node, int index) {
        if (node == null) return null;
        if (index == i) return node;
        i++;
        Node res = findByIndex(node.left, index);
        return res != null ? res : findByIndex(node.right, index);
    }

    /***
     * Altera o nó root de uma subarvore para outro nó com determinada chave. Caso a chave não exista a subarvore não será alterada.
     * Complexidade O(log(n)).
     * O algoritmo irã percorrer a subarvore até encontrar um nó que possua a mesma chave do {@code newRoot}.
     * Uma vez encontrado tal nó será feito uma rotação do nó pai, trazendo o nó desejado para cima, repetindo até que chegue
     * ao topo da subarvore.
     *
     * @param root Nó atual da recursão.
     * @param newRoot Nó com a chave que deverá ir para o topo da subarvore.
     * @return Novo {@code root} da subarvore.
     */
    private Node rotateRoot(Node root, Node newRoot) {
        if (root == null) return null;
        if (root.key.compareTo(newRoot.key) == 0) return root;
        int cmp = root.key.compareTo(newRoot.key);
        if (cmp > 0) root.left = rotateRoot(root.left, newRoot);
        else if (cmp < 0) root.right = rotateRoot(root.right, newRoot);
        if (root.left != null && root.left.key.compareTo(newRoot.key) == 0) root = rotateRight(root);
        if (root.right != null && root.right.key.compareTo(newRoot.key) == 0) root = rotateLeft(root);
        return root;
    }

    public static void main(String[] args) {
        BST<String, Integer> st = BST.generateType02();
        BST<String, Integer> st2 = BST.generateType01();
        System.out.print("TIPO 1: ");
        st.printPreFixed();
        System.out.print("TIPO 2: ");
        st2.printPreFixed();
        st.transform(st2);
        System.out.print("TIPO 1: ");
        st.printPreFixed();
    }

    /***
     * Percorre a árvore na ordem Raiz-Esquerda-Direita printando cada chave encontrada.
     * Complexidade O(n) uma vez que necessariamente irá percorrer toda a árvore.
     */
    public void printPreFixed() {
        printPreFixed(root);
        log.println();
    }

    private void printPreFixed(Node node) {
        if (node == null) return;
        log.print(node.key);
        printPreFixed(node.left);
        printPreFixed(node.right);
    }

    /***
     * Percorre a árvore na ordem Esquerda-Raiz-Direita printando cada chave encontrada.
     * Complexidade O(n) uma vez que necessariamente irá percorrer toda a árvore
     */
    public void printOrder() {
        printOrder(root);
        log.println();
    }

    private void printOrder(Node node) {
        if (node == null) return;
        printOrder(node.left);
        log.print(node.key);
        printOrder(node.right);
    }

}

/******************************************************************************
 *  Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
