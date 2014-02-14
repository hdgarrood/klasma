// An object implementing Waveform should implement _at() as a function
// mapping time (in the interval [0,1]) to amplitude (in the interval
// [-1,1]).
//
// This implementation of at() means that only the decimal part of the
// argument is considered (users don't need to set the integer part to 0
// themselves).
public abstract class Waveform {
    public double at(double t) {
        return _at(t - Math.floor(t));
    }
    
    protected abstract double _at(double t);
}