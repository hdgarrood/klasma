public class Pair<T,S> {
    private T fst;
    private S snd;
    
    public Pair(T t, S s) {
        this.fst = t;
        this.snd = s;
    }
    
    public T fst() {
        return this.fst;
    }
    
    public S snd() {
        return this.snd;
    }
}