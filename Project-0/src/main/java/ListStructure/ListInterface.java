package utils.datastructures;

/**
 * Custom list interface we will use to implement our own list data structure
 */
public interface ListInterface<E> {
    int size();

    void add(E e);

    void add(E e, int index);

    E get(int index);

    void remove(int index);

    void clear();

    void contains(E e);

}
