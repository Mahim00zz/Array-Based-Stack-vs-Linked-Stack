import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStack<E> implements StackInterface<E>, Iterable<E> {
    private E[] myArray = (E[]) new Object[10000];
    private int size = 0;

    public LinkedStack() {
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return myArray[currentIndex++];
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void push(E e) {
        if (e == null) {
            throw new NullPointerException("Cannot push null to stack");
        }
        if (size >= myArray.length) {
            throw new IllegalStateException("Stack is full");
        }
        myArray[size++] = e;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return myArray[size - 1];
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }
        E element = myArray[--size];
        myArray[size] = null; 
        return element;
    }

    @Override
    public void clear() {
        size = 0; 
    }

    @Override
    public StackInterface<E> copy() {
        LinkedStack<E> newStack = new LinkedStack<>();
        System.arraycopy(myArray, 0, newStack.myArray, 0, size);
        newStack.size = this.size;
        return newStack;
    }
}
