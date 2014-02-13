public class Consumer<T> {
    private T[] array;
    private int index = 0;
    private int arrayLength = 0;
    
    public Consumer(T[] array) {
        if (array == null)
            throw new IllegalArgumentException("array may not be null");
        
        this.array = array;
        this.arrayLength = array.length;
    }
    
    public T next() {
        if (this.index > this.arrayLength)
            return null;
        
        T item = this.array[this.index];
        this.index++;
        return item;
    }
}