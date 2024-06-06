import java.util.ArrayList;
import java.util.Iterator;

public class ArrayBasedStack<E> implements StackInterface<E> {

    private ArrayList<E> theArrayList;

    public ArrayBasedStack() {
        theArrayList = new ArrayList<>();
    }

    @Override
    public Iterator<E> iterator() {
        return theArrayList.iterator();
    }

    @Override
    public void clear() {
        theArrayList.clear();
    }

    @Override
    public boolean isEmpty() {
        return theArrayList.isEmpty();
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at an empty stack");
        }
        return theArrayList.get(theArrayList.size() - 1);
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop from an empty stack");
        }
        return theArrayList.remove(theArrayList.size() - 1);
    }

    @Override
    public void push(E e) throws IllegalStateException, NullPointerException {
        if (e == null) {
            throw new NullPointerException("Cannot push a null element to the stack");
        }
        theArrayList.add(e);
    }


    @Override
    public int size() {
        return theArrayList.size();
    }

    @Override
    public StackInterface<E> copy() {
        ArrayBasedStack<E> copyStack = new ArrayBasedStack<>();
        copyStack.theArrayList.addAll(this.theArrayList);
        return copyStack;
    }
}