package ListStructure;

import utils.datastructures.ListInterface;

////////////////////////////////// THIS STUFF IS HINTS ABOUT IMPLEMENTING ARRAYLIST ////////////////////////////////////
//      At the core of your arraylist implementation there WILL BE A primitive array.
//    this works - our hack we avoid generics, instead using an array of Objects,
//     because all objects inherit eventually from Object class.
//    Object[] o = new Object[2];
//
//    this doesn't work, can't directly build an array of generics
//    E[] w = new E[2];
//
//    When we want to return our array, we would need to "cast" it like this "(Type) thing" we turn the thing into type.
//    public E[] getArray() {
//        return (E[]) o;
//    }
////////////////////////////////// THIS STUFF IS HINTS ABOUT IMPLEMENTING ARRAYLIST ////////////////////////////////////

import java.util.Arrays;

public class MyList<E> implements ListInterface<E> {
    int size;
    int maxSize;
    Object[] array;

    public MyList() {
        maxSize = 1;
        size = 0;
        array = new Object[maxSize];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(E e) {
        while (size == maxSize){
            growArray();
        }
        addNewElement(e);
    }

    @Override
    public void add(E e, int index) {
        while (size == maxSize){
            growArray();
        }
        insertNewElement(e, index);
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        Object temp = null;
        for (int i = 0; i < maxSize; i++){
            if (i == index){
                temp = array[i];
                break;
            }
        }
        return (E) temp;
    }

    @Override
    public void remove(int index) {
        removeElement(index);
    }

    @Override
    public void clear() {
        clearArray();
    }

    @Override
    public void contains(E e) {
        doesItExist(e);
    }

    public void growArray() {
        //Initialize a new array that has a greater size than our previous
        //Copy the elements of the old array to the new array
        //Set the new array reference to the old array
        //Increase the maxSize variable for our new array
        maxSize += 1;
        Object[] temp = new Object[maxSize];
        for (int i = 0; i < size; i++) {
            temp[i] = array[i];
        }
        array = temp;
    }

    /**
    Adding a new element to an array:
    Create an array of a new size - one element larger than the previous, or 50% larger than the previous
        Grab the size of the old array, and increase it by one
        Initialize a new array of the new size
    Copy the old elements to the new array
        Iterate through the new array
        Add the old elements to the corresponding indices
    Add the new element to the end of the array
     */
    private void addNewElement(E e) {
        size += 1; //size increases by 1 to indicate that a new element is added to the array
        for (int i = 0; i < size; i++) { //maxSize holds the size of the new array copy after using the growArray method
            if (array[i] == null) { //Looks for the empty index within the array
                array[i] = e; //Adds the given element to the empty slot of the array
            }
        }
    }

    /**
    Adding a new element to a specific index in an array:
     Create an array of a new size - one element larger than the previous, or 50% larger than the previous
        Grab the size of the old array, and increase it by one
        Initialize a new array of the new size
     Copy the old elements to the new array until the specified index is reached
        Iterate through the new array
        Add the old elements to the corresponding indices
     Add the new element to the specific index in the new array
     Add the rest of the old elements to the rest of the array
     */
    private void insertNewElement(E e, int index) {
        size += 1;
        Object[] temp = new Object[maxSize]; //Creating a temporary array copy to hold the elements once they are shifted
        for (int i = 0; i < size; i++){ //maxSize holds the size of the new array copy after using the growArray method
            if (i < index){ //For the indices before "index", copy the elements into their original indices
                temp[i] = array[i] ;
            } else if (i == index){ //For the given index, insert the given element
                temp[i] = e;
            } else { //For indices greater than "index", copy the elements into a new index, one greater than their previous position
                temp[i] = array[i - 1];
            }
        }
        array = temp;
    }

    /**
     * Grabbing a specific element within an array:
     *  Iterate thorough the array
     *  Once the given index is reached , attach that element to a temporary reference variable
     *  Break the loop
     *  Return the element
     */
    private E getItemByIndex(int index){
        Object temp = null;
        for (int i = 0; i < maxSize; i++){
            if (i == index){
                temp = array[i];
                break;
            }
        }
        return (E) temp;
    }

    /**
     * Removing an element from an array:
     *  Decrement maxSize
     *  Initialize a new array of the new maxSize (one element smaller than the previous)
     *  Iterate through the temporary array
     *      If the given index is the first index, shift each element to the index one less than their previous
     *      If the given index is between the first and last, shift each element after the removed element to
     *      an index one less than their previous
     *      If the given index is last, shift every element to an index one less than their previous
     *  Make the original array reference equal to the temporary array
     *  Decrement the array size
     */
    private void removeElement(int index){
        maxSize -= 1;
        Object[] temp = new Object[maxSize];
        for (int i = 0; i < maxSize; i++){
            if (i < index){
                temp[i] = array[i];
            } else {
                temp[i] = array[i + 1];
            }
        }
        array = temp;
        size -= 1;
    }

    /**
     * Clearing the entire array
     *  Initialize an array with the same properties as our original array:
     *      All elements are null
     *      maxSize = 1;
     *      size = 0;
     *      array = new Object[maxSize];
     */
    private void clearArray(){
        maxSize = 1;
        size = 0;
        array = new Object[maxSize];
    }

    /**
     * Checking if the array contains a specific element:
     *  Iterate through the array
     *      If the element at the specific index equals the given element
     *          Tell the user that <element> is stored in index # <index number>
     *      If the given element is found within the array
     *          Tell the user that the element does not exist
     */
    private boolean doesItExist (E e){
        for (int i = 0; i < maxSize; i++){
            if (array[i] == e){
                System.out.println("The element, " + e + ", exists at index " + i);
                break;
            }
        }
        return true;
    }

}

