public class Node<E> {
    private E element;
    private Node<E> next;
    private Node<E> previous;
    
   
    public Node() {
        this.element = null;
        this.next = null;
        this.previous = null;
    }
    
   
    public Node(E element) {
        this(null, element, null);
    }
    
    public Node(Node<E> previous,E element,Node<E> next) {
        this.element = element;
        this.next = next;
        this.previous = previous;
    }

    public E getElement() {
        return this.element;
    }
    
    public void setElement(E data) {
        this.element = data;
    }
    
    public Node<E> getNext() {
        return this.next;
    }
    
    public void setNext(Node<E> next) {
        this.next = next;
    }
    
    public Node<E> getPrevious() {
        return this.previous;
    }
    
    public void setPrevious(Node<E> previous) {
        this.previous = previous;
    }
    
}
